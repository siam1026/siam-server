package com.siam.system.modular.package_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_user.entity.MemberToken;
import com.siam.system.modular.package_user.model.example.MemberTokenExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberTokenMapper extends BaseMapper<MemberToken> {
    int countByExample(MemberTokenExample example);

    int deleteByExample(MemberTokenExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(MemberToken record);

    List<MemberToken> selectByExample(MemberTokenExample example);

    MemberToken selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberToken record, @Param("example") MemberTokenExample example);

    int updateByExample(@Param("record") MemberToken record, @Param("example") MemberTokenExample example);

    int updateByPrimaryKeySelective(MemberToken record);

    int updateByPrimaryKey(MemberToken record);

    @ResultMap("BaseResultMap")
    @Select("select * from tb_member_token where token = #{token} order by id desc limit 1 ")
    MemberToken getLoginMemberInfo(@Param("token") String token);
}