package com.siam.system.modular.package_user.service;

import com.siam.system.modular.package_user.entity.MemberToken;
import com.siam.system.modular.package_user.model.example.MemberTokenExample;

import java.util.List;

public interface MemberTokenService {
    int countByExample(MemberTokenExample example);

    void insertSelective(MemberToken record);

    List<MemberToken> selectByExample(MemberTokenExample example);

    MemberToken selectByPrimaryKey(Integer id);

    void updateByExampleSelective(MemberToken record, MemberTokenExample example);

    void updateByPrimaryKeySelective(MemberToken record);

    void deleteByToken(String token);

    /**
     * 获取当前登录用户信息
     * @return
     **/
    MemberToken getLoginMemberInfo(String token);
}
