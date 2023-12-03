package com.siam.system.modular.package_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.entity.Admin;
import com.siam.system.modular.package_user.model.example.AdminExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface AdminMapper extends BaseMapper<Admin> {
    int countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    @ResultMap("BaseResultMap")
    @Select("<script>select a.* from tb_admin a" +
            "<where> 1=1 " +
            "<if test=\"admin.id != null\"> AND a.id = #{admin.id} </if>" +
            "<if test=\"admin.username != null and admin.username !=''\"> AND a.username like '%${admin.username}%' </if>" +
            "<if test=\"admin.mobile != null and admin.mobile !=''\"> AND a.mobile like '%${admin.mobile}%' </if>" +
            "<if test=\"admin.password != null and admin.password !=''\"> AND a.password like '%${admin.password}%' </if>" +
            "<if test=\"admin.passwordSalt != null and admin.passwordSalt !=''\"> AND a.password_salt like '%${admin.passwordSalt}%' </if>" +
            "<if test=\"admin.nickname != null and admin.nickname !=''\"> AND a.nickname like '%${admin.nickname}%' </if>" +
            "<if test=\"admin.roles != null and admin.roles !=''\"> AND a.roles like '%${admin.roles}%' </if>" +
            "<if test=\"admin.isDisabled != null\"> AND a.is_disabled = #{admin.isDisabled} </if>" +
            "</where> order by a.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("admin") Admin admin);

    @ResultMap("BaseResultMap")
    @Select("select * from tb_admin where username = #{username} order by id desc limit 1 ")
    Admin selectByUsername(@Param("username") String username);

    @ResultMap("BaseResultMap")
    @Select("select * from tb_admin where mobile = #{mobile} order by id desc limit 1 ")
    Admin selectByMobile(@Param("mobile") String mobile);
}