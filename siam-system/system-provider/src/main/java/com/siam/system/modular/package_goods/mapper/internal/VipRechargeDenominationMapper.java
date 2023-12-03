package com.siam.system.modular.package_goods.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeDenomination;
import com.siam.system.modular.package_goods.model.example.internal.VipRechargeDenominationExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface VipRechargeDenominationMapper extends BaseMapper<VipRechargeDenomination> {
    int countByExample(VipRechargeDenominationExample example);

    int deleteByExample(VipRechargeDenominationExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(VipRechargeDenomination record);

    List<VipRechargeDenomination> selectByExample(VipRechargeDenominationExample example);

    VipRechargeDenomination selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VipRechargeDenomination record, @Param("example") VipRechargeDenominationExample example);

    int updateByExample(@Param("record") VipRechargeDenomination record, @Param("example") VipRechargeDenominationExample example);

    int updateByPrimaryKeySelective(VipRechargeDenomination record);

    int updateByPrimaryKey(VipRechargeDenomination record);

    @ResultMap("BaseResultMap")
    @Select("<script>select v.* from tb_vip_recharge_denomination v" +
            "<where> 1=1 " +
            "<if test=\"vipRechargeDenomination.id != null\"> AND v.id = #{vipRechargeDenomination.id} </if>" +
            "<if test=\"vipRechargeDenomination.name != null and vipRechargeDenomination.name !=''\"> AND v.name like '%${vipRechargeDenomination.name}%' </if>" +
            "<if test=\"vipRechargeDenomination.price != null\"> AND v.price = #{vipRechargeDenomination.price} </if>" +
            "<if test=\"vipRechargeDenomination.isSale != null\"> AND v.is_sale = #{vipRechargeDenomination.isSale} </if>" +
            "<if test=\"vipRechargeDenomination.salePrice != null\"> AND v.sale_price = #{vipRechargeDenomination.salePrice} </if>" +
            "<if test=\"vipRechargeDenomination.briefDescription != null and vipRechargeDenomination.briefDescription != ''\"> AND v.brief_description like '%${vipRechargeDenomination.briefDescription}' </if>" +
            "<if test=\"vipRechargeDenomination.description != null and vipRechargeDenomination.description != ''\"> AND v.description like '%${vipRechargeDenomination.description}' </if>" +
            "<if test=\"vipRechargeDenomination.isGiveBalance != null\"> AND v.is_giveBalance = #{vipRechargeDenomination.isGiveBalance} </if>" +
            "<if test=\"vipRechargeDenomination.giveBalance != null\"> AND v.give_balance = #{vipRechargeDenomination.giveBalance} </if>" +
            "<if test=\"vipRechargeDenomination.isGiveCoupons != null\"> AND v.is_give_coupons = #{vipRechargeDenomination.isGiveCoupons} </if>" +
            "<if test=\"vipRechargeDenomination.startCreateTime != null\"> AND DATE_FORMAT(v.create_time, '%Y/%m/%d') &gt;= #{vipRechargeDenomination.startCreateTime} </if>" +
            "<if test=\"vipRechargeDenomination.endCreateTime != null\"> AND DATE_FORMAT(v.create_time, '%Y/%m/%d') &lt;= #{vipRechargeDenomination.endCreateTime} </if>" +
            "</where> order by v.price asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("vipRechargeDenomination") VipRechargeDenomination vipRechargeDenomination);
}