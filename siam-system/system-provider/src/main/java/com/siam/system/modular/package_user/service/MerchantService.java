package com.siam.system.modular.package_user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.entity.Merchant;
import com.siam.system.modular.package_user.model.example.MerchantExample;
import com.siam.system.modular.package_user.model.param.MerchantParam;
import com.siam.system.modular.package_user.model.result.MerchantResult;

import java.util.List;

public interface MerchantService {
    int countByExample(MerchantExample example);

    void insertSelective(Merchant record);

    List<Merchant> selectByExample(MerchantExample example);

    Merchant selectByPrimaryKey(Integer id);

    void updateByExampleSelective(Merchant record, MerchantExample example);

    void updateByPrimaryKeySelective(Merchant record);

    /**
     * 对密码进行盐值加密（废弃）
     * @return
     **/
    String encryptionBySalt(String password, String passwordSalt);

    Page getListByPage(int pageNo, int pageSize, Merchant merchant);

    Merchant selectByUsernameOrMobile(String username);

    /**
     * 通过手机号查询商家账号信息
     * @return
     **/
    Merchant selectByMobile(String mobile);

    /**
     * 增加/减少商家余额
     * @return
     **/
    /*void updateBalance(MerchantBillingRecord merchantBillingRecord);*/

    /**
     * 登录-账号密码
     *
     * @author 暹罗
     */
    MerchantResult login(MerchantParam param);

    /**
     * 登录-手机号验证码
     *
     * @author 暹罗
     */
    MerchantResult loginByMobile(MerchantParam param);

    /**
     * 注册并登录-手机号验证码
     *
     * @author 暹罗
     */
    MerchantResult registerByMobile(MerchantParam param);

    /**
     * 获取登录管理员信息
     *
     * @author 暹罗
     */
    MerchantResult getLoginMerchantInfo(MerchantParam param);

    /**
     * 修改商家账号信息
     *
     * @author 暹罗
     */
    void update(MerchantParam param);

    /**
     * 修改登录密码
     *
     * @author 暹罗
     */
    void updatePassword(MerchantParam param);

    /**
     * 登出
     *
     * @author 暹罗
     */
    void logout(MerchantParam param);

    /**
     * 设置/找回密码第一步
     *
     * @author 暹罗
     */
    void forgetPasswordStep1(MerchantParam param);

    /**
     * 设置/找回密码第二步
     *
     * @author 暹罗
     */
    void forgetPasswordStep2(MerchantParam param);
}