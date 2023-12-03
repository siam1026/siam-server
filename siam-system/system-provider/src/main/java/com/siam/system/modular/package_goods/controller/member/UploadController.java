package com.siam.system.modular.package_goods.controller.member;

import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.util.OSSUtils;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/member/upload")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "用户上传图片模块相关接口", description = "AdminUploadController")
public class UploadController {

    @Autowired
    private OSSUtils ossUtils;

    @Autowired
    private MemberSessionManager memberSessionManager;

    @ApiOperation(value = "用户上传单张图片")
    @PostMapping(value = "/uploadSingleImage")
    public BasicResult uploadSingleImage(@RequestParam("file") MultipartFile file) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        if(file==null || file.getSize()==0){
            throw new StoneCustomerException(500, "图片不能为空");
        }

        String imageUrl = ossUtils.uploadImage(file, "member", loginMember.getId());
        return BasicResult.success(imageUrl);
    }

    @ApiOperation(value = "用户上传多张图片")
    @PostMapping(value = "/uploadMultipleImage")
    public BasicResult uploadMultipleImage(@RequestParam("fileList") List<MultipartFile> fileList) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        String imageUrl = null;
        for (MultipartFile file : fileList) {
            if (file == null || file.getSize() == 0) {
                throw new StoneCustomerException(500, "图片上传失败");
            }
            if (imageUrl == null) {
                imageUrl = ossUtils.uploadImage(file, "member", loginMember.getId());
            } else {
                imageUrl = imageUrl + "," + ossUtils.uploadImage(file, "member", loginMember.getId());
            }
        }
        return BasicResult.success(imageUrl);
    }
}