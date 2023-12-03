package com.siam.system.modular.package_goods.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeRecord;
import com.siam.system.modular.package_goods.model.example.internal.VipRechargeRecordExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface VipRechargeRecordMapper extends BaseMapper<VipRechargeRecord> {
    int countByExample(VipRechargeRecordExample example);

    int deleteByExample(VipRechargeRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(VipRechargeRecord record);

    List<VipRechargeRecord> selectByExample(VipRechargeRecordExample example);

    VipRechargeRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VipRechargeRecord record, @Param("example") VipRechargeRecordExample example);

    int updateByExample(@Param("record") VipRechargeRecord record, @Param("example") VipRechargeRecordExample example);

    int updateByPrimaryKeySelective(VipRechargeRecord record);

    int updateByPrimaryKey(VipRechargeRecord record);

    @ResultMap("BaseResultMap")
    @Select("<script>select v.* from tb_vip_recharge_record v" +
            "<where> 1=1 " +
            "<if test=\"vipRechargeRecord.id != null\"> AND v.id = #{vipRechargeRecord.id} </if>" +
            "<if test=\"vipRechargeRecord.memberId != null\"> AND v.member_id = #{vipRechargeRecord.memberId} </if>" +
            "<if test=\"vipRechargeRecord.orderNo != null and vipRechargeRecord.orderNo !=''\"> AND v.order_no like '%${vipRechargeRecord.orderNo}%' </if>" +
            "<if test=\"vipRechargeRecord.channel != null\"> AND v.channel = #{vipRechargeRecord.channel} </if>" +
            "<if test=\"vipRechargeRecord.denominationId != null\"> AND v.denomination_id = #{vipRechargeRecord.denominationId} </if>" +
            "<if test=\"vipRechargeRecord.amount != null\"> AND v.amount = #{vipRechargeRecord.amount} </if>" +
            "<if test=\"vipRechargeRecord.denominationName != null and vipRechargeRecord.denominationName !=''\"> AND v.denomination_name like '%${vipRechargeRecord.denominationName}%' </if>" +
            "<if test=\"vipRechargeRecord.denominationPrice != null\"> AND v.denomination_price = #{vipRechargeRecord.denominationPrice} </if>" +
            "<if test=\"vipRechargeRecord.denominationIsSale != null\"> AND v.denomination_is_sale = #{vipRechargeRecord.denominationIsSale} </if>" +
            "<if test=\"vipRechargeRecord.denominationSalePrice != null\"> AND v.denomination_sale_price = #{vipRechargeRecord.denominationSalePrice} </if>" +
            "<if test=\"vipRechargeRecord.denominationIsGiveBalance != null\"> AND v.denomination_is_give_balance = #{vipRechargeRecord.denominationIsGiveBalance} </if>" +
            "<if test=\"vipRechargeRecord.denominationGiveBalance != null\"> AND v.denomination_give_balance = #{vipRechargeRecord.denominationGiveBalance} </if>" +
            "<if test=\"vipRechargeRecord.denominationIsGiveCoupons != null\"> AND v.denomination_is_give_coupons = #{vipRechargeRecord.denominationIsGiveCoupons} </if>" +
            "<if test=\"vipRechargeRecord.denominationGiveCouponsDescription != null and vipRechargeRecord.denominationGiveCouponsDescription !=''\"> AND v.denomination_give_coupons_description like '%${vipRechargeRecord.denominationGiveCouponsDescription}%' </if>" +
            "<if test=\"vipRechargeRecord.denominationGiveCouponsJson != null and vipRechargeRecord.denominationGiveCouponsJson !=''\"> AND v.denomination_give_coupons_json like '%${vipRechargeRecord.denominationGiveCouponsJson}%' </if>" +
            "<if test=\"vipRechargeRecord.tradeId != null\"> AND v.trade_id = #{vipRechargeRecord.tradeId} </if>" +
            "<if test=\"vipRechargeRecord.status != null\"> AND v.status = #{vipRechargeRecord.status} </if>" +
            "<if test=\"vipRechargeRecord.startCreateTime != null\"> AND DATE_FORMAT(v.create_time, '%Y/%m/%d') &gt;= #{vipRechargeRecord.startCreateTime} </if>" +
            "<if test=\"vipRechargeRecord.endCreateTime != null\"> AND DATE_FORMAT(v.create_time, '%Y/%m/%d') &lt;= #{vipRechargeRecord.endCreateTime} </if>" +
            "</where> order by v.create_time desc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("vipRechargeRecord") VipRechargeRecord vipRechargeRecord);

    @ResultMap("CustomResultMap")
    @Select("<script>select v.*, m.username, m.mobile from tb_vip_recharge_record v " +
            "left join tb_member m on m.id = v.member_id " +
            "<where> 1=1 " +
            "<if test=\"vipRechargeRecord.id != null\"> AND v.id = #{vipRechargeRecord.id} </if>" +
            "<if test=\"vipRechargeRecord.memberId != null\"> AND v.member_id = #{vipRechargeRecord.memberId} </if>" +
            "<if test=\"vipRechargeRecord.orderNo != null and vipRechargeRecord.orderNo !=''\"> AND v.order_no like '%${vipRechargeRecord.orderNo}%' </if>" +
            "<if test=\"vipRechargeRecord.channel != null\"> AND v.channel = #{vipRechargeRecord.channel} </if>" +
            "<if test=\"vipRechargeRecord.denominationId != null\"> AND v.denomination_id = #{vipRechargeRecord.denominationId} </if>" +
            "<if test=\"vipRechargeRecord.amount != null\"> AND v.amount = #{vipRechargeRecord.amount} </if>" +
            "<if test=\"vipRechargeRecord.denominationName != null and vipRechargeRecord.denominationName !=''\"> AND v.denomination_name like '%${vipRechargeRecord.denominationName}%' </if>" +
            "<if test=\"vipRechargeRecord.denominationPrice != null\"> AND v.denomination_price = #{vipRechargeRecord.denominationPrice} </if>" +
            "<if test=\"vipRechargeRecord.denominationIsSale != null\"> AND v.denomination_is_sale = #{vipRechargeRecord.denominationIsSale} </if>" +
            "<if test=\"vipRechargeRecord.denominationSalePrice != null\"> AND v.denomination_sale_price = #{vipRechargeRecord.denominationSalePrice} </if>" +
            "<if test=\"vipRechargeRecord.denominationIsGiveBalance != null\"> AND v.denomination_is_give_balance = #{vipRechargeRecord.denominationIsGiveBalance} </if>" +
            "<if test=\"vipRechargeRecord.denominationGiveBalance != null\"> AND v.denomination_give_balance = #{vipRechargeRecord.denominationGiveBalance} </if>" +
            "<if test=\"vipRechargeRecord.denominationIsGiveCoupons != null\"> AND v.denomination_is_give_coupons = #{vipRechargeRecord.denominationIsGiveCoupons} </if>" +
            "<if test=\"vipRechargeRecord.denominationGiveCouponsDescription != null and vipRechargeRecord.denominationGiveCouponsDescription !=''\"> AND v.denomination_give_coupons_description like '%${vipRechargeRecord.denominationGiveCouponsDescription}%' </if>" +
            "<if test=\"vipRechargeRecord.denominationGiveCouponsJson != null and vipRechargeRecord.denominationGiveCouponsJson !=''\"> AND v.denomination_give_coupons_json like '%${vipRechargeRecord.denominationGiveCouponsJson}%' </if>" +
            "<if test=\"vipRechargeRecord.tradeId != null\"> AND v.trade_id = #{vipRechargeRecord.tradeId} </if>" +
            "<if test=\"vipRechargeRecord.status != null\"> AND v.status = #{vipRechargeRecord.status} </if>" +
            "<if test=\"vipRechargeRecord.startCreateTime != null\"> AND DATE_FORMAT(v.create_time, '%Y/%m/%d') &gt;= #{vipRechargeRecord.startCreateTime} </if>" +
            "<if test=\"vipRechargeRecord.endCreateTime != null\"> AND DATE_FORMAT(v.create_time, '%Y/%m/%d') &lt;= #{vipRechargeRecord.endCreateTime} </if>" +
            "</where> order by v.create_time desc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinMember(@Param("page") Page page, @Param("vipRechargeRecord") VipRechargeRecord vipRechargeRecord);

    @Select("<script>select IFNULL(sum(v.amount), 0) from tb_vip_recharge_record v " +
            "<where> 1=1 and status = 2 " +
            "<if test=\"vipRechargeRecord.id != null\"> AND v.id = #{vipRechargeRecord.id} </if>" +
            "<if test=\"vipRechargeRecord.memberId != null\"> AND v.member_id = #{vipRechargeRecord.memberId} </if>" +
            "<if test=\"vipRechargeRecord.orderNo != null and vipRechargeRecord.orderNo !=''\"> AND v.order_no like '%${vipRechargeRecord.orderNo}%' </if>" +
            "<if test=\"vipRechargeRecord.channel != null\"> AND v.channel = #{vipRechargeRecord.channel} </if>" +
            "<if test=\"vipRechargeRecord.denominationId != null\"> AND v.denomination_id = #{vipRechargeRecord.denominationId} </if>" +
            "<if test=\"vipRechargeRecord.amount != null\"> AND v.amount = #{vipRechargeRecord.amount} </if>" +
            "<if test=\"vipRechargeRecord.denominationName != null and vipRechargeRecord.denominationName !=''\"> AND v.denomination_name like '%${vipRechargeRecord.denominationName}%' </if>" +
            "<if test=\"vipRechargeRecord.denominationPrice != null\"> AND v.denomination_price = #{vipRechargeRecord.denominationPrice} </if>" +
            "<if test=\"vipRechargeRecord.denominationIsSale != null\"> AND v.denomination_is_sale = #{vipRechargeRecord.denominationIsSale} </if>" +
            "<if test=\"vipRechargeRecord.denominationSalePrice != null\"> AND v.denomination_sale_price = #{vipRechargeRecord.denominationSalePrice} </if>" +
            "<if test=\"vipRechargeRecord.denominationIsGiveBalance != null\"> AND v.denomination_is_give_balance = #{vipRechargeRecord.denominationIsGiveBalance} </if>" +
            "<if test=\"vipRechargeRecord.denominationGiveBalance != null\"> AND v.denomination_give_balance = #{vipRechargeRecord.denominationGiveBalance} </if>" +
            "<if test=\"vipRechargeRecord.denominationIsGiveCoupons != null\"> AND v.denomination_is_give_coupons = #{vipRechargeRecord.denominationIsGiveCoupons} </if>" +
            "<if test=\"vipRechargeRecord.denominationGiveCouponsDescription != null and vipRechargeRecord.denominationGiveCouponsDescription !=''\"> AND v.denomination_give_coupons_description like '%${vipRechargeRecord.denominationGiveCouponsDescription}%' </if>" +
            "<if test=\"vipRechargeRecord.denominationGiveCouponsJson != null and vipRechargeRecord.denominationGiveCouponsJson !=''\"> AND v.denomination_give_coupons_json like '%${vipRechargeRecord.denominationGiveCouponsJson}%' </if>" +
            "<if test=\"vipRechargeRecord.tradeId != null\"> AND v.trade_id = #{vipRechargeRecord.tradeId} </if>" +
            "<if test=\"vipRechargeRecord.status != null\"> AND v.status = #{vipRechargeRecord.status} </if>" +
            "<if test=\"vipRechargeRecord.startCreateTime != null\"> AND DATE_FORMAT(v.create_time, '%Y/%m/%d') &gt;= #{vipRechargeRecord.startCreateTime} </if>" +
            "<if test=\"vipRechargeRecord.endCreateTime != null\"> AND DATE_FORMAT(v.create_time, '%Y/%m/%d') &lt;= #{vipRechargeRecord.endCreateTime} </if>" +
            "</where> order by v.create_time desc" +
            "</script>")
    BigDecimal statisticalAmountByRechargeSuccessful(@Param("vipRechargeRecord") VipRechargeRecord vipRechargeRecord);

    @ResultMap("BaseResultMap")
    @Select("select v.* from tb_vip_recharge_record v where v.order_no = #{orderNo}")
    VipRechargeRecord selectByOrderNo(@Param("orderNo") String orderNo);

    @ResultMap("BaseResultMap")
    @Select("select v.* from tb_vip_recharge_record v where v.member_id = #{memberId} and v.status = 2 order by v.create_time desc limit 1")
    VipRechargeRecord selectLastestPaid(@Param("memberId") Integer memberId);
}