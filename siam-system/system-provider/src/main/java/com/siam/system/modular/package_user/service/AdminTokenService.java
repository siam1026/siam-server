package com.siam.system.modular.package_user.service;

import com.siam.system.modular.package_user.entity.AdminToken;
import com.siam.system.modular.package_user.model.example.AdminTokenExample;

import java.util.List;

public interface AdminTokenService {
    int countByExample(AdminTokenExample example);

    void insertSelective(AdminToken record);

    List<AdminToken> selectByExample(AdminTokenExample example);

    AdminToken selectByPrimaryKey(Integer id);

    void updateByExampleSelective(AdminToken record, AdminTokenExample example);

    void updateByPrimaryKeySelective(AdminToken record);

    void deleteByToken(String token);

    /**
     * 获取当前登录管理员信息
     * @return
     **/
    AdminToken getLoginAdminInfo(String token);
}