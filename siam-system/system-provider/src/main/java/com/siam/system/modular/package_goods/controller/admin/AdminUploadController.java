package com.siam.system.modular.package_goods.controller.admin;

import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.util.OSSUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/admin")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台上传图片模块相关接口", description = "AdminUploadController")
public class AdminUploadController {

    @Autowired
    private OSSUtils ossUtils;

    /**
     * 后台商家账号上传单张图片
     *
     * @author 暹罗
     */
    @PostMapping(value = "/uploadSingleImage")
    public BasicResult uploadSingleImage(@RequestParam("file") MultipartFile file) {
        if(file==null || file.getSize()==0){
            throw new StoneCustomerException(500, "图片不能为空");
        }

        String imageUrl = ossUtils.uploadImage(file, "admin", "1");

        return BasicResult.success(imageUrl);
    }

    /**
     * 后台商家账号上传多张图片
     *
     * @author 暹罗
     */
    @PostMapping(value = "/uploadMultipleImage")
    public BasicResult uploadMultipleImage(@RequestParam("fileList") List<MultipartFile> fileList) {
        String imageUrl = null;
        for(MultipartFile file : fileList){
            if(file==null || file.getSize()==0){
                throw new StoneCustomerException(500, "图片上传失败");
            }
            if(imageUrl == null){
                imageUrl = ossUtils.uploadImage(file, "admin", "1");
            }else{
                imageUrl = imageUrl + "," + ossUtils.uploadImage(file, "admin", "1");
            }
        }
        return BasicResult.success(imageUrl);
    }
}