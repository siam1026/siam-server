package com.siam.system.modular.package_user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.entity.Admin;
import com.siam.system.modular.package_user.model.example.AdminExample;
import com.siam.system.modular.package_user.model.param.AdminParam;
import com.siam.system.modular.package_user.model.result.AdminResult;

import java.util.List;

public interface AdminService {
    int countByExample(AdminExample example);

    void insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer id);

    void updateByExampleSelective(Admin record, AdminExample example);

    void updateByPrimaryKeySelective(Admin record);

    /**
     * 对密码进行盐值加密（废弃）
     * @return
     **/
    String encryptionBySalt(String password, String passwordSalt);

    Page getListByPage(AdminParam param);

    Admin selectByUsername(String username);

    /**
     * 登录-账号密码
     *
     * @author 暹罗
     */
    AdminResult login(AdminParam param);

    /**
     * 登录-手机号验证码
     *
     * @author 暹罗
     */
    AdminResult loginByMobile(AdminParam param);

    /**
     * 获取登录管理员信息
     *
     * @author 暹罗
     */
    AdminResult getLoginAdminInfo(AdminParam param);

    /**
     * 修改登录密码
     *
     * @author 暹罗
     */
    void updatePassword(AdminParam param);

    /**
     * 登出
     *
     * @author 暹罗
     */
    void logout(AdminParam param);

    /**
     * 设置/找回密码第一步
     *
     * @author 暹罗
     */
    void forgetPasswordStep1(AdminParam param);

    /**
     * 设置/找回密码第二步
     *
     * @author 暹罗
     */
    void forgetPasswordStep2(AdminParam param);
}