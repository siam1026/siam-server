package com.siam.system.modular.package_goods.controller.merchant;

import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.util.OSSUtils;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.system.modular.package_user.entity.Merchant;
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
@RequestMapping(value = "/rest/merchant")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端上传图片模块相关接口", description = "MerchantUploadController")
public class MerchantUploadController {

    @Autowired
    private OSSUtils ossUtils;

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    @ApiOperation(value = "用户上传单张图片")
    @PostMapping(value = "/uploadSingleImage")
    public BasicResult uploadSingleImage(@RequestParam("file") MultipartFile file) {
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        if(file==null || file.getSize()==0){
            throw new StoneCustomerException(500, "图片不能为空");
        }

        String imageUrl = ossUtils.uploadImage(file, "merchant", loginMerchant.getId());
        return BasicResult.success(imageUrl);
    }

    @ApiOperation(value = "用户上传多张图片")
    @PostMapping(value = "/uploadMultipleImage")
    public BasicResult uploadMultipleImage(@RequestParam("fileList") List<MultipartFile> fileList) {
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());
        String imageUrl = null;
        for (MultipartFile file : fileList) {
            if (file == null || file.getSize() == 0) {
                throw new StoneCustomerException(500, "图片上传失败");
            }
            if (imageUrl == null) {
                imageUrl = ossUtils.uploadImage(file, "merchant", loginMerchant.getId());
            } else {
                imageUrl = imageUrl + "," + ossUtils.uploadImage(file, "merchant", loginMerchant.getId());
            }
        }
        return BasicResult.success(imageUrl);
    }
}