package com.siam.system.modular.package_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_user.entity.AdminToken;
import com.siam.system.modular.package_user.model.example.AdminTokenExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminTokenMapper extends BaseMapper<AdminToken> {
    int countByExample(AdminTokenExample example);

    int deleteByExample(AdminTokenExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(AdminToken record);

    List<AdminToken> selectByExample(AdminTokenExample example);

    AdminToken selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AdminToken record, @Param("example") AdminTokenExample example);

    int updateByExample(@Param("record") AdminToken record, @Param("example") AdminTokenExample example);

    int updateByPrimaryKeySelective(AdminToken record);

    int updateByPrimaryKey(AdminToken record);

    @ResultMap("BaseResultMap")
    @Select("select * from tb_admin_token where token = #{token} order by id desc limit 1 ")
    AdminToken getLoginAdminInfo(@Param("token") String token);
}