package com.siam.system.modular.package_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.entity.Merchant;
import com.siam.system.modular.package_user.model.example.MerchantExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface MerchantMapper extends BaseMapper<Merchant> {
    int countByExample(MerchantExample example);

    int deleteByExample(MerchantExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Merchant record);

    List<Merchant> selectByExample(MerchantExample example);

    Merchant selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Merchant record, @Param("example") MerchantExample example);

    int updateByExample(@Param("record") Merchant record, @Param("example") MerchantExample example);

    int updateByPrimaryKeySelective(Merchant record);

    int updateByPrimaryKey(Merchant record);

    @ResultMap("BaseResultMap")
    @Select("<script>select m.* from tb_merchant a" +
            "<where> 1=1 " +
            "<if test=\"merchant.id != null\"> AND m.id = #{merchant.id} </if>" +
            "<if test=\"merchant.username != null and merchant.username !=''\"> AND m.username like '%${merchant.username}%' </if>" +
            "<if test=\"merchant.mobile != null and merchant.mobile !=''\"> AND m.mobile like '%${merchant.mobile}%' </if>" +
            "<if test=\"merchant.password != null and merchant.password !=''\"> AND m.password like '%${merchant.password}%' </if>" +
            "<if test=\"merchant.passwordSalt != null and merchant.passwordSalt !=''\"> AND m.password_salt like '%${merchant.passwordSalt}%' </if>" +
            "<if test=\"merchant.nickname != null and merchant.nickname !=''\"> AND m.nickname like '%${merchant.nickname}%' </if>" +
            "<if test=\"merchant.roles != null and merchant.roles !=''\"> AND m.roles like '%${merchant.roles}%' </if>" +
            "<if test=\"merchant.isDisabled != null\"> AND m.is_disabled = #{merchant.isDisabled} </if>" +
            "</where> order by m.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("merchant") Merchant merchant);

    @ResultMap("BaseResultMap")
    @Select("select * from tb_merchant where username = #{username} or mobile = #{username} order by id desc limit 1 ")
    Merchant selectByUsernameOrMobile(@Param("username") String username);

    @ResultMap("BaseResultMap")
    @Select("select * from tb_merchant where mobile = #{mobile} order by id desc limit 1 ")
    Merchant selectByMobile(@Param("mobile") String mobile);
}