package com.siam.system.modular.package_user.controller.member;

import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_user.model.param.MemberParam;
import com.siam.system.modular.package_user.model.result.MemberResult;
import com.siam.system.modular.package_user.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户模块相关接口
 *
 * @author 暹罗
 */
@Slf4j
@RestController
@RequestMapping(value = "/rest/member")
@Transactional(rollbackFor = Exception.class)
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 登录-账号密码
     *
     * @author 暹罗
     */

    @PostMapping(value = "/login")
    public BasicResult login(@RequestBody @Validated(value = {}) MemberParam param) {
        MemberResult result = memberService.login(param);
        return BasicResult.success(result);
    }

    /**
     * 验证码登录
     *
     * @author 暹罗
     */
    @PostMapping(value = "/verification/login")
    public BasicResult verificationLogin(@RequestBody @Validated(value = {}) MemberParam param) {
        MemberResult result = memberService.verificationLogin(param);
        return BasicResult.success(result);
    }

    /**
     * 微信登录
     *
     * @author 暹罗
     */
    @PostMapping(value = "/weChat/login")
    public BasicResult wxLogin(@RequestBody @Validated(value = {}) MemberParam param) {
        MemberResult result = memberService.wxLogin(param);
        return BasicResult.success(result);
    }

    /**
     * 微信静默登录
     *
     *  @author 暹罗
     */
    @PostMapping(value = "/weChat/silence/login")
    public BasicResult wxSilenceLogin(@RequestBody @Validated(value = {}) MemberParam param) {
        MemberResult result = memberService.wxSilenceLogin(param);
        return BasicResult.success(result);
    }

    /**
     * 获取登录用户信息
     *
     * @author 暹罗
     */
    @PostMapping(value = "/getLoginMemberInfo")
    public BasicResult getLoginMemberInfo(@RequestBody @Validated(value = {}) MemberParam param) {
        MemberResult result = memberService.getLoginMemberInfo(param);
        return BasicResult.success(result);
    }

    /**
     * 解除账号绑定的微信
     *
     * @author 暹罗
     */
    @PostMapping(value = "/removeBindingWx")
    public BasicResult removeBindingWx(@RequestBody @Validated(value = {}) MemberParam param) {
        memberService.removeBindingWx(param);
        return BasicResult.success();
    }

    /**
     * 上传头像
     *
     * @author 暹罗
     */
    @PostMapping(value = "/uploadHeadImg")
    public BasicResult uploadHeadImg(@RequestBody @Validated(value = {}) MemberParam param) {
        memberService.uploadHeadImg(param);
        return BasicResult.success();
    }

    /**
     * 修改登录密码
     *
     * @author 暹罗
     */
    @PostMapping(value = "/updatePassword")
    public BasicResult updatePassword(@RequestBody @Validated(value = {}) MemberParam param) {
        memberService.updatePassword(param);
        return BasicResult.success();
    }

    /**
     * 登出
     *
     * @author 暹罗
     */
    @PostMapping(value = "/logout")
    public BasicResult logout(@RequestBody @Validated(value = {}) MemberParam param) {
        memberService.logout(param);
        return BasicResult.success();
    }

    /**
     * 修改个人资料(邮箱地址、性别、真实姓名)
     *
     * @author 暹罗
     */
    @PostMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) MemberParam param) {
        memberService.update(param);
        return BasicResult.success();
    }

    /**
     * 修改是否需要弹出新人引导提示为否
     *
     * @author 暹罗
     */
    @PostMapping(value = "/updateIsRemindNewPeople")
    public BasicResult updateIsRemindNewPeople(@RequestBody @Validated(value = {}) MemberParam param) {
        memberService.updateIsRemindNewPeople(param);
        return BasicResult.success();
    }

    /**
     * 修改用户的是否需要请求授权服务通知状态为否/告知已发起过请求授权服务通知
     *
     * @author 暹罗
     */
    @PostMapping(value = "/updateIsRequestWxNotify")
    public BasicResult updateIsRequestWxNotify(@RequestBody @Validated(value = {}) MemberParam param) {
        memberService.updateIsRequestWxNotify(param);
        return BasicResult.success();
    }

    /**
     * 上报使用/进入小程序的定位地址
     *
     * @author 暹罗
     */
    @PostMapping(value = "/updateLastUseAddress")
    public BasicResult updateLastUseAddress(@RequestBody @Validated(value = {}) MemberParam param) {
        memberService.updateLastUseAddress(param);
        return BasicResult.success();
    }

    /**
     * 设置/找回支付密码第一步
     *
     * @author 暹罗
     */
    @RequestMapping(value = "/forgetPaymentPassword/step1",method = RequestMethod.POST)
    public BasicResult forgetPaymentPasswordStep1(@RequestBody @Validated(value = {}) MemberParam param) {
        memberService.forgetPaymentPasswordStep1(param);
        return BasicResult.success();
    }

    /**
     * 设置/找回支付密码第二步
     *
     * @author 暹罗
     */
    @RequestMapping(value =  "/forgetPaymentPassword/step2",method = RequestMethod.POST)
    public BasicResult forgetPaymentPasswordStep2(@RequestBody @Validated(value = {}) MemberParam param) {
        memberService.forgetPaymentPasswordStep2(param);
        return BasicResult.success();
    }

    /**
     * 验证支付密码是否正确(场景：支付、提现)
     *
     * @author 暹罗
     */
    @RequestMapping(value =  "/verifyPaymentPassword",method = RequestMethod.POST)
    @ApiOperation(value = "")
    public BasicResult verifyPaymentPassword(@RequestBody @Validated(value = {}) MemberParam param) {
        memberService.verifyPaymentPassword(param);
        return BasicResult.success();
    }



//    /**
//     * 登录-手机验证码
//     *
//     * @author 暹罗
//     */
//
//    @PostMapping(value = "/loginByMobile")
//    public BasicResult loginByMobile(@RequestBody @Validated(value = {}) MemberParam param) {
//        MemberResult result = memberService.loginByMobile(param);
//        return BasicResult.success(result);
//    }
}