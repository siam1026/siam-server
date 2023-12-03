package com.siam.system.modular.package_user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.model.example.MemberExample;
import com.siam.system.modular.package_user.model.param.MemberParam;
import com.siam.system.modular.package_user.model.result.MemberResult;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface MemberService {

//    /**
//     * 登录-手机验证码
//     *
//     * @author 暹罗
//     */
//    MemberResult loginByMobile(MemberParam param);

    /**
     * 对密码进行盐值加密（废弃）
     *
     * @author 暹罗
     */
    String encryptionBySalt(String password, String passwordSalt);

    /**
     * 微信一键登录
     *
     * @author 暹罗
     */
    Integer findMemberByOpenId(String openId);

    /**
     * 微信一键登录
     *
     * @author 暹罗
     */
    Member findMemberByOneKeyLogin(Member member) throws Exception;

    /**
     * 将新用户的"是否需要弹出新人引导提示"字段值改成是
     *
     * @author 暹罗
     */
    String getNextVipNo();

    /**
     * 将新用户的"是否需要弹出新人引导提示"字段值改成是
     *
     * @author 暹罗
     */
    List<Member> selectAllMemberNoneCoupons();

    /**
     * 将新用户的"是否需要弹出新人引导提示"字段值改成是
     *
     * @author 暹罗
     */
    List<Member> selectAllMemberNoneCouponsByPointsMall();

    /**
     * 将新用户的"是否需要弹出新人引导提示"字段值改成是
     *
     * @author 暹罗
     */
    void updateIsRemindNewPeople();

    /**
     * 查询普通注册人数(微信一键登录)
     *
     * @author 暹罗
     */
    int selectCountWeChatRegister(Date startTime, Date endTime);

    /**
     * 查询普通注册人数(手机验证码注册)
     *
     * @author 暹罗
     */
    int selectCountMobileCodeRegister(Date startTime, Date endTime);

    /**
     * 查询普通注册人数(微信一键登录+手机验证码注册)
     *
     * @author 暹罗
     */
    int selectCountGeneralRegister(Date startTime, Date endTime);

    /**
     * 查询所有注册人数
     *
     * @author 暹罗
     */
    int selectCountRegister(Date startTime, Date endTime);

    /**
     * 查询邀请注册人数
     *
     * @author 暹罗
     */
    int selectCountInviteRegister(Date startTime, Date endTime);

    /**
     * 修改用户的是否需要请求授权服务通知状态
     *
     * @author 暹罗
     */
    void updateIsRequestWxNotify();

    /**
     *
     *
     * @author 暹罗
     */
    String generateVipNo();

    /**
     *
     *
     * @author 暹罗
     */
    void queryWxPublicPlatformOpenId() throws IOException;

    /**
     *
     *
     * @author 暹罗
     */
    BasicResult querySingleWxPublicPlatformOpenId(Integer id) throws IOException;

    int countByExample(MemberExample example);

    void insertSelective(Member record);

    List<Member> selectByExample(MemberExample example);

    Member selectByPrimaryKey(Integer id);

    void updateByExampleSelective(Member record, MemberExample example);

    void updateByPrimaryKeySelective(Member record);

    /**
     * 通过手机号查询用户信息
     *
     * @author 暹罗
     */
    Member selectByMobile(String mobile);

    /**
     * 微信一键登录
     *
     * @author 暹罗
     */
    Page getListByPage(MemberParam param);

    /**
     * 条件查询集合
     *
     * @author 暹罗
     */
    Page getList(int pageNo, int pageSize, MemberParam member);

    /**
     *
     *
     * @author 暹罗
     */
    Page<Member> purchasedList(MemberParam param);

    /**
     *
     *
     * @author 暹罗
     */
    Page<Member> unPurchasedList(MemberParam param);

    /**
     * 登录-账号密码
     *
     * @author 暹罗
     */
    MemberResult login(MemberParam param);

    /**
     * 验证码登录
     * 1) 现在验证码登录接口关联了openid属性，目的是为了在下单时直接获取openid
     * 2) 其实不应该这样做，应该是在下单时获取当前运行小程序的微信号的openid，
     * 3) 由于每次切换微信账号时小程序必定是会重新修改openid的，所以也不会造成bug，暂时先这样写就不改动了
     *
     * @author 暹罗
     */
    MemberResult verificationLogin(MemberParam param);

    /**
     * 微信登录
     *
     * @author 暹罗
     */
    MemberResult wxLogin(MemberParam param);

    /**
     * 获取登录用户信息
     *
     * @author 暹罗
     */
    MemberResult getLoginMemberInfo(MemberParam param);

    /**
     * 解除账号绑定的微信
     *
     * @author 暹罗
     */
    void removeBindingWx(MemberParam param);

    /**
     * 上传头像
     *
     * @author 暹罗
     */
    void uploadHeadImg(MemberParam param);

    /**
     * 修改登录密码
     *
     * @author 暹罗
     */
    void updatePassword(MemberParam param);

    /**
     * 登出
     *
     * @author 暹罗
     */
    void logout(MemberParam param);

    /**
     * 修改个人资料(邮箱地址、性别、真实姓名)
     *
     * @author 暹罗
     */
    void update(MemberParam param);

    /**
     * 修改是否需要弹出新人引导提示为否
     *
     * @author 暹罗
     */
    void updateIsRemindNewPeople(MemberParam param);

    /**
     * 修改用户的是否需要请求授权服务通知状态为否/告知已发起过请求授权服务通知
     *
     * @author 暹罗
     */
    void updateIsRequestWxNotify(MemberParam param);

    /**
     * 上报使用/进入小程序的定位地址
     *
     * @author 暹罗
     */
    void updateLastUseAddress(MemberParam param);

    /**
     * 设置/找回支付密码第一步
     *
     * @author 暹罗
     */
    void forgetPaymentPasswordStep1(MemberParam param);

    /**
     * 设置/找回支付密码第二步
     *
     * @author 暹罗
     */
    void forgetPaymentPasswordStep2(MemberParam param);

    /**
     * 验证支付密码是否正确(场景：支付、提现)
     *
     * @author 暹罗
     */
    void verifyPaymentPassword(MemberParam param);
}