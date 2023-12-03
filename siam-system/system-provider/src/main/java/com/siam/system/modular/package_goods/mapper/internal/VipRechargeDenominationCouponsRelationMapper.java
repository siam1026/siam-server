package com.siam.system.modular.package_goods.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeDenominationCouponsRelation;
import com.siam.system.modular.package_goods.model.example.internal.VipRechargeDenominationCouponsRelationExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface VipRechargeDenominationCouponsRelationMapper extends BaseMapper<VipRechargeDenominationCouponsRelation> {
    int countByExample(VipRechargeDenominationCouponsRelationExample example);

    int deleteByExample(VipRechargeDenominationCouponsRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(VipRechargeDenominationCouponsRelation record);

    List<VipRechargeDenominationCouponsRelation> selectByExample(VipRechargeDenominationCouponsRelationExample example);

    VipRechargeDenominationCouponsRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VipRechargeDenominationCouponsRelation record, @Param("example") VipRechargeDenominationCouponsRelationExample example);

    int updateByExample(@Param("record") VipRechargeDenominationCouponsRelation record, @Param("example") VipRechargeDenominationCouponsRelationExample example);

    int updateByPrimaryKeySelective(VipRechargeDenominationCouponsRelation record);

    int updateByPrimaryKey(VipRechargeDenominationCouponsRelation record);

    @Delete("delete from tb_vip_recharge_denomination_coupons_relation where vip_recharge_denomination_id = #{vipRechargeDenominationId}")
    int deleteByVipRechargeDenominationId(@Param("vipRechargeDenominationId") int vipRechargeDenominationId);

    @ResultMap("BaseResultMap")
    @Select("<script>select vcr.* from tb_vip_recharge_denomination_coupons_relation vcr" +
            "<where> 1=1 " +
            "<if test=\"vipRechargeDenominationCouponsRelation.id != null\"> AND vcr.id = #{vipRechargeDenominationCouponsRelation.id} </if>" +
            "<if test=\"vipRechargeDenominationCouponsRelation.vipRechargeDenominationId != null\"> AND vcr.vip_recharge_denomination_id = #{vipRechargeDenominationCouponsRelation.vipRechargeDenominationId} </if>" +
            "<if test=\"vipRechargeDenominationCouponsRelation.couponsId != null\"> AND vcr.coupons_id = #{vipRechargeDenominationCouponsRelation.couponsId} </if>" +
            "<if test=\"vipRechargeDenominationCouponsRelation.giveQuantity != null\"> AND vcr.give_quantity = #{vipRechargeDenominationCouponsRelation.giveQuantity} </if>" +
            "</where> order by vcr.id desc" +
            "</script>")
    Page<VipRechargeDenominationCouponsRelation> getListByPage(@Param("page") Page page, @Param("vipRechargeDenominationCouponsRelation") VipRechargeDenominationCouponsRelation vipRechargeDenominationCouponsRelation);
}