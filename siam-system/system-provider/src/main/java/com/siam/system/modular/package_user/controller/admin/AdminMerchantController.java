package com.siam.system.modular.package_user.controller.admin;

import com.siam.system.modular.package_user.service.MerchantService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/admin/merchant")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台商家账号模块相关接口", description = "AdminMerchantController")
public class AdminMerchantController {

    @Autowired
    private MerchantService merchantService;

}