package com.siam.system.modular.package_user.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.AdminPermission;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_user.model.param.AdminParam;
import com.siam.system.modular.package_user.model.result.AdminResult;
import com.siam.system.modular.package_user.service.AdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/admin")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台管理员模块相关接口", description = "AdminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 后台管理员列表
     *
     * @author 暹罗
     */
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) AdminParam param) {
        Page page = adminService.getListByPage(param);
        return BasicResult.success(page);
    }

    /**
     * 登录-账号密码
     *
     * @author 暹罗
     */
    @PostMapping(value = "/login")
    public BasicResult login(@RequestBody @Validated(value = {}) AdminParam param) {
        AdminResult result = adminService.login(param);
        return BasicResult.success(result);
    }

    /**
     * 登录-手机号验证码
     *
     * @author 暹罗
     */
    @PostMapping(value = "/loginByMobile")
    public BasicResult loginByMobile(@RequestBody @Validated(value = {}) AdminParam param) {
        AdminResult result = adminService.loginByMobile(param);
        return BasicResult.success(result);
    }

    /**
     * 登出
     *
     * @author 暹罗
     */
    @PostMapping(value = "/logout")
    public BasicResult logout(@RequestBody @Validated(value = {}) AdminParam param) {
        adminService.logout(param);
        return BasicResult.success();
    }

    /**
     * 获取登录管理员信息
     *
     * @author 暹罗
     */
    @PostMapping(value = "/getLoginAdminInfo")
    public BasicResult getLoginAdminInfo(@RequestBody @Validated(value = {}) AdminParam param) {
        AdminResult result = adminService.getLoginAdminInfo(param);
        return BasicResult.success(result);
    }

    /**
     * 修改登录密码
     *
     * @author 暹罗
     */
    @AdminPermission
    @PostMapping(value = "/updatePassword")
    public BasicResult updatePassword(@RequestBody @Validated(value = {}) AdminParam param) {
        adminService.updatePassword(param);
        return BasicResult.success();
    }


    /**
     * 找回密码第一步
     *
     * @author 暹罗
     */
    @RequestMapping(value = "/forgetPassword/step1",method = RequestMethod.POST)
    public BasicResult forgetPasswordSep1(@RequestBody @Validated(value = {}) AdminParam param) {
        adminService.forgetPasswordStep1(param);
        return BasicResult.success();
    }

    /**
     * 找回密码第二步
     *
     * @author 暹罗
     */
    @RequestMapping(value =  "/forgetPassword/step2",method = RequestMethod.POST)
    public BasicResult forgetPasswordSep2(@RequestBody @Validated(value = {}) AdminParam param) {
        adminService.forgetPasswordStep2(param);
        return BasicResult.success();
    }
}