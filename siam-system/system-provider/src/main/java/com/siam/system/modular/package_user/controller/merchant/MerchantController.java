package com.siam.system.modular.package_user.controller.merchant;

import com.siam.package_common.annoation.MerchantPermission;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_user.model.param.MerchantParam;
import com.siam.system.modular.package_user.model.result.MerchantResult;
import com.siam.system.modular.package_user.service.MerchantService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/merchant")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台商家账号模块相关接口", description = "MerchantController")
public class MerchantController {
    
    @Autowired
    private MerchantService merchantService;

    /**
     * 登录-账号密码
     *
     * @author 暹罗
     */
    @PostMapping(value = "/login")
    public BasicResult login(@RequestBody @Validated(value = {}) MerchantParam param) {
        MerchantResult result = merchantService.login(param);
        return BasicResult.success(result);
    }

    /**
     * 登录-手机号验证码
     *
     * @author 暹罗
     */
    @PostMapping(value = "/loginByMobile")
    public BasicResult loginByMobile(@RequestBody @Validated(value = {}) MerchantParam param) {
        MerchantResult result = merchantService.loginByMobile(param);
        return BasicResult.success(result);
    }

    /**
     * 注册并登录-手机号验证码
     *
     * @author 暹罗
     */
    @PostMapping(value = "/registerByMobile")
    public BasicResult registerByMobile(@RequestBody @Validated(value = {}) MerchantParam param) {
        MerchantResult result = merchantService.registerByMobile(param);
        return BasicResult.success(result);
    }

    /**
     * 登出
     *
     * @author 暹罗
     */
    @PostMapping(value = "/logout")
    public BasicResult logout(@RequestBody @Validated(value = {}) MerchantParam param) {
        merchantService.logout(param);
        return BasicResult.success();
    }

    /**
     * 获取登录商家账号信息登录-账号密码
     *
     * @author 暹罗
     */
    @PostMapping(value = "/getLoginMerchantInfo")
    public BasicResult getLoginMerchantInfo(@RequestBody @Validated(value = {}) MerchantParam param) {
        MerchantResult result = merchantService.getLoginMerchantInfo(param);
        return BasicResult.success(result);
    }

    /**
     * 修改商家账号信息
     *
     * @author 暹罗
     */
    @PostMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) MerchantParam param) {
        merchantService.update(param);
        return BasicResult.success();
    }

    /**
     * 修改商家账号密码
     *
     * @author 暹罗
     */
    @MerchantPermission
    @PostMapping(value = "/updatePassword")
    public BasicResult updatePassword(@RequestBody @Validated(value = {}) MerchantParam param) {
        merchantService.updatePassword(param);
        return BasicResult.success();
    }

    /**
     * 找回密码第一步
     *
     * @author 暹罗
     */
    @RequestMapping(value = "/forgetPassword/step1",method = RequestMethod.POST)
    public BasicResult forgetPasswordSep1(@RequestBody @Validated(value = {}) MerchantParam param) {
        merchantService.forgetPasswordStep1(param);
        return BasicResult.success();
    }

    /**
     * 找回密码第二步
     *
     * @author 暹罗
     */
    @RequestMapping(value =  "/forgetPassword/step2",method = RequestMethod.POST)
    public BasicResult forgetPasswordSep2(@RequestBody @Validated(value = {}) MerchantParam param) {
        merchantService.forgetPasswordStep2(param);
        return BasicResult.success();
    }
}