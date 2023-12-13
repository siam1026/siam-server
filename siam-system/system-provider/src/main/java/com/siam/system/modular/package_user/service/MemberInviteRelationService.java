package com.siam.system.modular.package_user.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.entity.MemberInviteRelation;
import com.siam.system.modular.package_user.model.param.MemberInviteRelationParam;

import java.util.Map;

public interface MemberInviteRelationService {

    /**
     * 创建邀请关系
     * @param memberInviteRelation
     */
    void insertSelective(MemberInviteRelation memberInviteRelation);

//    /**
//     * 通过用户手机号和邀请者id创建邀请关系
//     * @param mobile
//     * @param inviterId
//     */
//    void insertSelective(String mobile,Integer inviterId);

    Page<Member> getMemberListByInviterId(MemberInviteRelationParam param);

    /**
     * 查询用户的邀请人(三层邀请关系)
     *
     * @return
     */
    Map<String, Integer> selectInviter(Integer memberId);
}
