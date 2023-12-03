package com.siam.system.modular.package_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_user.entity.MemberInviteRelation;
import com.siam.system.modular.package_user.model.example.MemberInviteRelationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberInviteRelationMapper extends BaseMapper<MemberInviteRelation> {
    int countByExample(MemberInviteRelationExample example);

    int deleteByExample(MemberInviteRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(MemberInviteRelation record);

    List<MemberInviteRelation> selectByExample(MemberInviteRelationExample example);

    MemberInviteRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberInviteRelation record, @Param("example") MemberInviteRelationExample example);

    int updateByExample(@Param("record") MemberInviteRelation record, @Param("example") MemberInviteRelationExample example);

    int updateByPrimaryKeySelective(MemberInviteRelation record);

    int updateByPrimaryKey(MemberInviteRelation record);
}