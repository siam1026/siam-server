package com.siam.system.modular.package_order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.entity.Order;
import com.siam.system.modular.package_order.model.example.OrderExample;
import com.siam.system.modular.package_order.model.param.OrderParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderMapper extends BaseMapper<Order> {
    int countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    @ResultMap("BaseResultMap")
    @Select("<script>select o.* from tb_order o" +
            "<where> 1=1 " +
            "<if test=\"orderDto.id != null\"> AND o.id = #{orderDto.id} </if>" +
            "<if test=\"orderDto.memberId != null\"> AND o.member_id = #{orderDto.memberId} </if>" +
            "<if test=\"orderDto.orderNo != null and orderDto.orderNo !=''\"> AND o.order_no like '%${orderDto.orderNo}%' </if>" +
            "<if test=\"orderDto.goodsTotalQuantity != null\"> AND o.goods_total_quantity = #{orderDto.goodsTotalQuantity} </if>" +
            "<if test=\"orderDto.goodsTotalPrice != null\"> AND o.goods_total_price = #{orderDto.goodsTotalPrice} </if>" +
            "<if test=\"orderDto.packingCharges != null\"> AND o.packing_charges = #{orderDto.packingCharges} </if>" +
            "<if test=\"orderDto.deliveryFee != null\"> AND o.delivery_fee = #{orderDto.deliveryFee} </if>" +
            "<if test=\"orderDto.actualPrice != null\"> AND o.actual_price = #{orderDto.actualPrice} </if>" +
            "<if test=\"orderDto.shoppingWay != null\"> AND o.shopping_way = #{orderDto.shoppingWay} </if>" +
            "<if test=\"orderDto.deliveryAddressId != null\"> AND o.delivery_address_id = #{orderDto.deliveryAddressId} </if>" +
            "<if test=\"orderDto.contactRealname != null and orderDto.contactRealname !=''\"> AND o.contact_realname like '%${orderDto.contactRealname}%' </if>" +
            "<if test=\"orderDto.contactPhone != null and orderDto.contactPhone !=''\"> AND o.contact_phone like '%${orderDto.contactPhone}%' </if>" +
            "<if test=\"orderDto.contactProvince != null and orderDto.contactProvince !=''\"> AND o.contact_province like '%${orderDto.contactProvince}%' </if>" +
            "<if test=\"orderDto.contactCity != null and orderDto.contactCity !=''\"> AND o.contact_city like '%${orderDto.contactCity}%' </if>" +
            "<if test=\"orderDto.contactArea != null and orderDto.contactArea !=''\"> AND o.contact_area like '%${orderDto.contactArea}%' </if>" +
            "<if test=\"orderDto.contactStreet != null and orderDto.contactStreet !=''\"> AND o.contact_street like '%${orderDto.contactStreet}%' </if>" +
            "<if test=\"orderDto.contactSex != null\"> AND o.contact_sex = #{orderDto.contactSex} </if>" +
            "<if test=\"orderDto.remark != null and orderDto.remark !=''\"> AND o.remark like '%${orderDto.remark}%' </if>" +
            "<if test=\"orderDto.description != null and orderDto.description !=''\"> AND o.description like '%${orderDto.description}%' </if>" +
            "<if test=\"orderDto.status != null and orderDto.status != -1\"> AND o.status = #{orderDto.status} </if>" +
            "<if test=\"orderDto.status == -1\"> AND o.status in (7, 9, 11)</if>" +
            "<if test=\"orderDto.tradeId != null\"> AND o.trade_id = #{orderDto.tradeId} </if>" +
            "<if test=\"orderDto.orderLogisticsId != null\"> AND o.order_logistics_id = #{orderDto.orderLogisticsId} </if>" +
            "<if test=\"orderDto.isInvoice != null\"> AND o.is_invoice = #{orderDto.isInvoice} </if>" +
            "<if test=\"orderDto.invoiceId != null\"> AND o.invoice_id = #{orderDto.invoiceId} </if>" +
            "<if test=\"orderDto.isDeleted != null\"> AND o.is_deleted = #{orderDto.isDeleted} </if>" +
            "<if test=\"orderDto.shopId != null\"> AND o.shop_id = #{orderDto.shopId} </if>" +
            "<if test=\"orderDto.shopName != null and orderDto.shopName !=''\"> AND o.shop_name like '%${orderDto.shopName}%' </if>" +
            "<if test=\"orderDto.cancelReason != null\"> AND o.cancel_reason = #{orderDto.cancelReason} </if>" +
            "<if test=\"orderDto.isPrinted != null\"> AND o.is_printed = #{orderDto.isPrinted} </if>" +
            "<if test=\"orderDto.queueNo != null\"> AND o.queue_no = #{orderDto.queueNo} </if>" +
            "<if test=\"orderDto.fullReductionRuleId != null\"> AND o.full_reduction_rule_id = #{orderDto.fullReductionRuleId} </if>" +
            "<if test=\"orderDto.fullReductionRuleDescription != null and orderDto.fullReductionRuleDescription !=''\"> AND o.full_reduction_rule_description like '%${orderDto.fullReductionRuleDescription}%' </if>" +
            "<if test=\"orderDto.couponsId != null\"> AND o.coupons_id = #{orderDto.couponsId} </if>" +
            "<if test=\"orderDto.couponsDescription != null and orderDto.couponsDescription !=''\"> AND o.coupons_description like '%${orderDto.couponsDescription}%' </if>" +
            "<if test=\"orderDto.couponsMemberRelationId != null\"> AND o.coupons_member_relation_id = #{orderDto.couponsMemberRelationId} </if>" +
            "<if test=\"orderDto.isChangeToDelivery != null\"> AND o.is_change_to_delivery = #{orderDto.isChangeToDelivery} </if>" +
            "<if test=\"orderDto.changeToDeliveryOutTradeNo != null and orderDto.changeToDeliveryOutTradeNo !=''\"> AND o.change_to_delivery_out_trade_no like '%${orderDto.changeToDeliveryOutTradeNo}%' </if>" +
            "<if test=\"orderDto.changeToDeliveryTradeId != null\"> AND o.change_to_delivery_trade_id = #{orderDto.changeToDeliveryTradeId} </if>" +
            "<if test=\"orderDto.startCreateTime != null\"> AND DATE_FORMAT(o.create_time, '%Y/%m/%d') &gt;= #{orderDto.startCreateTime} </if>" +
            "<if test=\"orderDto.endCreateTime != null\"> AND DATE_FORMAT(o.create_time, '%Y/%m/%d') &lt;= #{orderDto.endCreateTime} </if>" +
            "</where> order by o.create_time desc" +
            "</script>")
    Page<Order> getListByPageWithAsc(@Param("page") Page page, @Param("orderDto") OrderParam param);

    @ResultMap("CustomResultMap")
    @Select("<script>select o.*, " +
            "(SELECT if(COUNT(*)>0, false, true) FROM tb_appraise WHERE order_id = o.id AND member_id = #{order.memberId}) as isAllowAppraise, " +
            "if(o.status in (2, 4) and <![CDATA[ TIMESTAMPDIFF(MINUTE, o.payment_success_time, now()) < 1 ]]>, true, false) as isAllowCancelNoReason, " +
            "if(o.status not in (1, 7, 8, 9, 10, 11) and <![CDATA[ TIMESTAMPDIFF(HOUR, o.payment_success_time, now()) < 24 ]]>, true, false) as isAllowApplyRefund " +
            "from tb_order o " +
            "<where> 1=1 " +
            "<if test=\"order.id != null\"> AND o.id = #{order.id} </if>" +
            "<if test=\"order.memberId != null\"> AND o.member_id = #{order.memberId} </if>" +
            "<if test=\"order.orderNo != null and order.orderNo !=''\"> AND o.order_no like '%${order.orderNo}%' </if>" +
            "<if test=\"order.goodsTotalQuantity != null\"> AND o.goods_total_quantity = #{order.goodsTotalQuantity} </if>" +
            "<if test=\"order.goodsTotalPrice != null\"> AND o.goods_total_price = #{order.goodsTotalPrice} </if>" +
            "<if test=\"order.packingCharges != null\"> AND o.packing_charges = #{order.packingCharges} </if>" +
            "<if test=\"order.deliveryFee != null\"> AND o.delivery_fee = #{order.deliveryFee} </if>" +
            "<if test=\"order.actualPrice != null\"> AND o.actual_price = #{order.actualPrice} </if>" +
            "<if test=\"order.shoppingWay != null\"> AND o.shopping_way = #{order.shoppingWay} </if>" +
            "<if test=\"order.deliveryAddressId != null\"> AND o.delivery_address_id = #{order.deliveryAddressId} </if>" +
            "<if test=\"order.contactRealname != null and order.contactRealname !=''\"> AND o.contact_realname like '%${order.contactRealname}%' </if>" +
            "<if test=\"order.contactPhone != null and order.contactPhone !=''\"> AND o.contact_phone like '%${order.contactPhone}%' </if>" +
            "<if test=\"order.contactProvince != null and order.contactProvince !=''\"> AND o.contact_province like '%${order.contactProvince}%' </if>" +
            "<if test=\"order.contactCity != null and order.contactCity !=''\"> AND o.contact_city like '%${order.contactCity}%' </if>" +
            "<if test=\"order.contactArea != null and order.contactArea !=''\"> AND o.contact_area like '%${order.contactArea}%' </if>" +
            "<if test=\"order.contactStreet != null and order.contactStreet !=''\"> AND o.contact_street like '%${order.contactStreet}%' </if>" +
            "<if test=\"order.contactSex != null\"> AND o.contact_sex = #{order.contactSex} </if>" +
            "<if test=\"order.remark != null and order.remark !=''\"> AND o.remark like '%${order.remark}%' </if>" +
            "<if test=\"order.description != null and order.description !=''\"> AND o.description like '%${order.description}%' </if>" +
            "<if test=\"order.status != null and order.status != -1\"> AND o.status = #{order.status} </if>" +
            "<if test=\"order.status == -1\"> AND o.status in (7, 8, 9)</if>" +
            "<if test=\"order.tradeId != null\"> AND o.trade_id = #{order.tradeId} </if>" +
            "<if test=\"order.orderLogisticsId != null\"> AND o.order_logistics_id = #{order.orderLogisticsId} </if>" +
            "<if test=\"order.isInvoice != null\"> AND o.is_invoice = #{order.isInvoice} </if>" +
            "<if test=\"order.invoiceId != null\"> AND o.invoice_id = #{order.invoiceId} </if>" +
            "<if test=\"order.isDeleted != null\"> AND o.is_deleted = #{order.isDeleted} </if>" +
            "<if test=\"order.shopId != null\"> AND o.shop_id = #{order.shopId} </if>" +
            "<if test=\"order.shopName != null and order.shopName !=''\"> AND o.shop_name like '%${order.shopName}%' </if>" +
            "<if test=\"order.cancelReason != null\"> AND o.cancel_reason = #{order.cancelReason} </if>" +
            "<if test=\"order.isPrinted != null\"> AND o.is_printed = #{order.isPrinted} </if>" +
            "<if test=\"order.queueNo != null\"> AND o.queue_no = #{order.queueNo} </if>" +
            "<if test=\"order.fullReductionRuleId != null\"> AND o.full_reduction_rule_id = #{order.fullReductionRuleId} </if>" +
            "<if test=\"order.fullReductionRuleDescription != null and order.fullReductionRuleDescription !=''\"> AND o.full_reduction_rule_description like '%${order.fullReductionRuleDescription}%' </if>" +
            "<if test=\"order.couponsId != null\"> AND o.coupons_id = #{order.couponsId} </if>" +
            "<if test=\"order.couponsDescription != null and order.couponsDescription !=''\"> AND o.coupons_description like '%${order.couponsDescription}%' </if>" +
            "<if test=\"order.couponsMemberRelationId != null\"> AND o.coupons_member_relation_id = #{order.couponsMemberRelationId} </if>" +
            "<if test=\"order.isChangeToDelivery != null\"> AND o.is_change_to_delivery = #{order.isChangeToDelivery} </if>" +
            "<if test=\"order.changeToDeliveryOutTradeNo != null and order.changeToDeliveryOutTradeNo !=''\"> AND o.change_to_delivery_out_trade_no like '%${order.changeToDeliveryOutTradeNo}%' </if>" +
            "<if test=\"order.changeToDeliveryTradeId != null\"> AND o.change_to_delivery_trade_id = #{order.changeToDeliveryTradeId} </if>" +
            "<if test=\"order.tabType != null and order.tabType =='waitPayment'\"> AND o.status in (1) </if>" +
            "<if test=\"order.tabType != null and order.tabType =='waitReceived'\"> AND o.status in (4, 5) </if>" +
            "<if test=\"order.tabType != null and order.tabType =='waitPickedUp'\"> AND o.status in (2, 3) </if>" +
            "<if test=\"order.tabType != null and order.tabType =='afterSales'\"> AND o.status in (7, 8, 9, 11) </if>" +
            "<if test=\"order.keyWords != null and order.keyWords !=''\"> AND (o.contact_phone like '%${order.keyWords}%' or o.order_no like '%${order.keyWords}%') </if>" +
            "<if test=\"order.startCreateTime != null\"> AND DATE_FORMAT(o.create_time, '%Y/%m/%d') &gt;= #{order.startCreateTime} </if>" +
            "<if test=\"order.endCreateTime != null\"> AND DATE_FORMAT(o.create_time, '%Y/%m/%d') &lt;= #{order.endCreateTime} </if>" +
            "</where> order by o.create_time desc" +
            "</script>")
    Page<Map<String, Object>> getListByPageWithDesc(@Param("page") Page page, @Param("order") OrderParam param);

    @ResultMap("BaseResultMap")
    @Select("select o.* from tb_order o where o.order_no = #{orderNo}")
    Order selectByOrderNo(@Param("orderNo") String orderNo);

    //查询超时未支付订单
    @ResultMap("BaseResultMap")
    @Select("select o.* from tb_order o where o.status = 1 and payment_deadline < now()")
    List<Order> selectOverdueOrder();

    @Update("<script>update tb_order o set is_printed = 1 " +
            "where o.id in <foreach collection=\"idList\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach>" +
            "</script>")
    int batchUpdateIsPrintedTrue(@Param("idList") List<Integer> idList);

    @Select("select max(queue_no) from tb_order where to_days(create_time) = to_days(now())")
    Integer findMaxQueueNo();

    @ResultMap("BaseResultMap")
    @Select("<script>select o.* from tb_order o" +
            "<where> 1=1 " +
            "<if test=\"orderDto.id != null\"> AND o.id = #{orderDto.id} </if>" +
            "<if test=\"orderDto.memberId != null\"> AND o.member_id = #{orderDto.memberId} </if>" +
            "<if test=\"orderDto.orderNo != null and orderDto.orderNo !=''\"> AND o.order_no like '%${orderDto.orderNo}%' </if>" +
            "<if test=\"orderDto.goodsTotalQuantity != null\"> AND o.goods_total_quantity = #{orderDto.goodsTotalQuantity} </if>" +
            "<if test=\"orderDto.goodsTotalPrice != null\"> AND o.goods_total_price = #{orderDto.goodsTotalPrice} </if>" +
            "<if test=\"orderDto.packingCharges != null\"> AND o.packing_charges = #{orderDto.packingCharges} </if>" +
            "<if test=\"orderDto.deliveryFee != null\"> AND o.delivery_fee = #{orderDto.deliveryFee} </if>" +
            "<if test=\"orderDto.actualPrice != null\"> AND o.actual_price = #{orderDto.actualPrice} </if>" +
            "<if test=\"orderDto.shoppingWay != null\"> AND o.shopping_way = #{orderDto.shoppingWay} </if>" +
            "<if test=\"orderDto.deliveryAddressId != null\"> AND o.delivery_address_id = #{orderDto.deliveryAddressId} </if>" +
            "<if test=\"orderDto.contactRealname != null and orderDto.contactRealname !=''\"> AND o.contact_realname like '%${orderDto.contactRealname}%' </if>" +
            "<if test=\"orderDto.contactPhone != null and orderDto.contactPhone !=''\"> AND o.contact_phone like '%${orderDto.contactPhone}%' </if>" +
            "<if test=\"orderDto.contactProvince != null and orderDto.contactProvince !=''\"> AND o.contact_province like '%${orderDto.contactProvince}%' </if>" +
            "<if test=\"orderDto.contactCity != null and orderDto.contactCity !=''\"> AND o.contact_city like '%${orderDto.contactCity}%' </if>" +
            "<if test=\"orderDto.contactArea != null and orderDto.contactArea !=''\"> AND o.contact_area like '%${orderDto.contactArea}%' </if>" +
            "<if test=\"orderDto.contactStreet != null and orderDto.contactStreet !=''\"> AND o.contact_street like '%${orderDto.contactStreet}%' </if>" +
            "<if test=\"orderDto.contactSex != null\"> AND o.contact_sex = #{orderDto.contactSex} </if>" +
            "<if test=\"orderDto.remark != null and orderDto.remark !=''\"> AND o.remark like '%${orderDto.remark}%' </if>" +
            "<if test=\"orderDto.description != null and orderDto.description !=''\"> AND o.description like '%${orderDto.description}%' </if>" +
            "<if test=\"orderDto.status != null and orderDto.status != -1\"> AND o.status = #{orderDto.status} </if>" +
            "<if test=\"orderDto.status == -1\"> AND o.status in (7, 9, 11)</if>" +
            "<if test=\"orderDto.tradeId != null\"> AND o.trade_id = #{orderDto.tradeId} </if>" +
            "<if test=\"orderDto.orderLogisticsId != null\"> AND o.order_logistics_id = #{orderDto.orderLogisticsId} </if>" +
            "<if test=\"orderDto.isInvoice != null\"> AND o.is_invoice = #{orderDto.isInvoice} </if>" +
            "<if test=\"orderDto.invoiceId != null\"> AND o.invoice_id = #{orderDto.invoiceId} </if>" +
            "<if test=\"orderDto.isDeleted != null\"> AND o.is_deleted = #{orderDto.isDeleted} </if>" +
            "<if test=\"orderDto.shopId != null\"> AND o.shop_id = #{orderDto.shopId} </if>" +
            "<if test=\"orderDto.shopName != null and orderDto.shopName !=''\"> AND o.shop_name like '%${orderDto.shopName}%' </if>" +
            "<if test=\"orderDto.cancelReason != null\"> AND o.cancel_reason = #{orderDto.cancelReason} </if>" +
            "<if test=\"orderDto.isPrinted != null\"> AND o.is_printed = #{orderDto.isPrinted} </if>" +
            "<if test=\"orderDto.queueNo != null\"> AND o.queue_no = #{orderDto.queueNo} </if>" +
            "<if test=\"orderDto.fullReductionRuleId != null\"> AND o.full_reduction_rule_id = #{orderDto.fullReductionRuleId} </if>" +
            "<if test=\"orderDto.fullReductionRuleDescription != null and orderDto.fullReductionRuleDescription !=''\"> AND o.full_reduction_rule_description like '%${orderDto.fullReductionRuleDescription}%' </if>" +
            "<if test=\"orderDto.couponsId != null\"> AND o.coupons_id = #{orderDto.couponsId} </if>" +
            "<if test=\"orderDto.couponsDescription != null and orderDto.couponsDescription !=''\"> AND o.coupons_description like '%${orderDto.couponsDescription}%' </if>" +
            "<if test=\"orderDto.couponsMemberRelationId != null\"> AND o.coupons_member_relation_id = #{orderDto.couponsMemberRelationId} </if>" +
            "<if test=\"orderDto.isChangeToDelivery != null\"> AND o.is_change_to_delivery = #{orderDto.isChangeToDelivery} </if>" +
            "<if test=\"orderDto.changeToDeliveryOutTradeNo != null and orderDto.changeToDeliveryOutTradeNo !=''\"> AND o.change_to_delivery_out_trade_no like '%${orderDto.changeToDeliveryOutTradeNo}%' </if>" +
            "<if test=\"orderDto.changeToDeliveryTradeId != null\"> AND o.change_to_delivery_trade_id = #{orderDto.changeToDeliveryTradeId} </if>" +
            "<if test=\"orderDto.startCreateTime == null and orderDto.endCreateTime == null\"> and date(o.create_time) = date(now()) </if>"+
            "<if test=\"orderDto.startCreateTime != null\"> AND DATE_FORMAT(o.create_time, '%Y/%m/%d') &gt;= #{orderDto.startCreateTime} </if>" +
            "<if test=\"orderDto.endCreateTime != null\"> AND DATE_FORMAT(o.create_time, '%Y/%m/%d') &lt;= #{orderDto.endCreateTime} </if>" +
            "</where> order by o.create_time desc" +
            "</script>")
    Page<Order> getListByTodayOrderWithAsc(@Param("page") Page page, @Param("orderDto") OrderParam param);

    @Update("update tb_order set order_completion_time=#{updateTime}, update_time=#{updateTime}, status=#{status} where payment_success_time < #{overdueTime} and status in(2, 3, 4, 5)")
    int updateFinish(@Param("overdueTime") Date overdueTime,@Param("updateTime") Date updateTime,@Param("status") Integer status);

    @ResultMap("CustomResultMap")
    @Select("<script>\n" +
            "SELECT\n" +
            "sum( IF ( STATUS = 10 AND shopping_way = 1, 1, 0 ) ) AS completePickUp,\n" +
            "sum( IF ( STATUS = 7 AND shopping_way = 1, 1, 0 ) ) AS cancelPickUp,\n" +
            "sum( IF ( STATUS = 8 AND shopping_way = 1, 1, 0 ) ) AS refundPickUp,\n" +
            "sum( IF ( STATUS = 10 AND shopping_way = 2, 1, 0 ) ) AS completeTakeOut,\n" +
            "sum( IF ( STATUS = 7 AND shopping_way = 2, 1, 0 ) ) AS cancelTakeOut,\n" +
            "sum( IF ( STATUS = 8 AND shopping_way = 2, 1, 0 ) ) AS refundTakeOut\n" +
            "FROM\n" +
            "tb_order\n" +
            "<where> 1=1 " +
            "<if test=\"order.startCreateTime != null\"> AND DATE_FORMAT(o.create_time, '%Y/%m/%d') &gt;= #{order.startCreateTime} </if>" +
            "<if test=\"order.endCreateTime != null\"> AND DATE_FORMAT(o.create_time, '%Y/%m/%d') &lt;= #{order.endCreateTime} </if>"+
            "<if test=\"order.shopId != null\"> AND o.shop_id = #{order.shopId} </if>" +
            "</where>"+
            "</script>")
    Map countOrder(@Param("order") OrderParam param);

    @ResultMap("BaseResultMap")
    @Select("select o.* from tb_order o where o.change_to_delivery_out_trade_no = #{changeToDeliveryOutTradeNo}")
    Order selectByChangeToDeliveryOutTradeNo(@Param("changeToDeliveryOutTradeNo") String changeToDeliveryOutTradeNo);

    @Select("select count(*) as latelyMonthlySales from tb_order as o "+
            "where shop_id = #{shopId} and o.status = 6 and (o.create_time between #{startTime} and #{endTime})")
    Integer selectLatelyMonthlySalesByShopId(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("shopId") Integer shopId);

    @Select("<script>select IFNULL(sum(o.merchant_income), 0) from tb_order as o "+
            "<where> 1=1 and STATUS in (6, 7, 9) and (o.create_time between #{order.startTime} and #{order.endTime})" +
            "<if test=\"order.shopId != null\"> AND o.shop_id = #{order.shopId} </if>" +
            "</where>"+
            "</script>")
    BigDecimal selectSumMerchantIncome(@Param("order") OrderParam param);

    @Select("<script>select IFNULL(sum(o.actual_price), 0) from tb_order as o "+
            "<where> 1=1 and STATUS in (6, 7, 9) and (o.create_time between #{order.startTime} and #{order.endTime})" +
            "<if test=\"order.shopId != null\"> AND o.shop_id = #{order.shopId} </if>" +
            "</where>"+
            "</script>")
    BigDecimal selectSumActualPrice(@Param("order") OrderParam param);

    @Select("<script>select count(*) from tb_order as o "+
            "<where> 1=1 and STATUS in (6, 7, 9) and (o.create_time between #{order.startTime} and #{order.endTime})" +
            "<if test=\"order.memberId != null\"> AND o.member_id = #{order.memberId} </if>" +
            "<if test=\"order.shopId != null\"> AND o.shop_id = #{order.shopId} </if>" +
            "</where>"+
            "</script>")
    Integer selectCountCompleted(@Param("order") OrderParam param);

    @Select("<script>select count(*) from tb_order as o "+
            "<where> 1=1 and STATUS not in (1, 10) and (o.create_time between #{order.startTime} and #{order.endTime})" +
            "<if test=\"order.memberId != null\"> AND o.member_id = #{order.memberId} </if>" +
            "<if test=\"order.shopId != null\"> AND o.shop_id = #{order.shopId} </if>" +
            "<if test=\"order.excludeOrderId != null\"> AND o.id != #{order.excludeOrderId} </if>" +
            "</where>"+
            "</script>")
    Integer selectCountPaid(@Param("order") OrderParam param);

    @Select("SELECT if(o.status in (2, 4) and TIMESTAMPDIFF(MINUTE, o.payment_success_time, now()) < 1, true, false) FROM tb_order as o WHERE o.id = #{order.id}")
    boolean getIsAllowCancelNoReason(@Param("order") OrderParam param);

    @Select("SELECT if(o.status not in (1, 7, 8, 9, 10, 11) and TIMESTAMPDIFF(HOUR, o.payment_success_time, now()) < 24, true, false) FROM tb_order as o WHERE o.id = #{order.id}")
    boolean getIsAllowApplyRefund(@Param("order") OrderParam param);

    @ResultMap("CustomResultMap")
    @Select("<script>select o.*, or1.type as refundType, or1.refund_way as refundWay, or1.refund_reason as refundReason, or1.refund_amount as refundAmount, or1.status as refundStatus, or1.create_time as refundCreateTime from tb_order o " +
            "left join tb_order_refund or1 on or1.order_id = o.id " +
            "<where> 1=1 AND o.status in (7, 9, 11) " +
            "<if test=\"orderDto.id != null\"> AND o.id = #{orderDto.id} </if>" +
            "<if test=\"orderDto.memberId != null\"> AND o.member_id = #{orderDto.memberId} </if>" +
            "<if test=\"orderDto.orderNo != null and orderDto.orderNo !=''\"> AND o.order_no like '%${orderDto.orderNo}%' </if>" +
            "<if test=\"orderDto.goodsTotalQuantity != null\"> AND o.goods_total_quantity = #{orderDto.goodsTotalQuantity} </if>" +
            "<if test=\"orderDto.goodsTotalPrice != null\"> AND o.goods_total_price = #{orderDto.goodsTotalPrice} </if>" +
            "<if test=\"orderDto.packingCharges != null\"> AND o.packing_charges = #{orderDto.packingCharges} </if>" +
            "<if test=\"orderDto.deliveryFee != null\"> AND o.delivery_fee = #{orderDto.deliveryFee} </if>" +
            "<if test=\"orderDto.actualPrice != null\"> AND o.actual_price = #{orderDto.actualPrice} </if>" +
            "<if test=\"orderDto.shoppingWay != null\"> AND o.shopping_way = #{orderDto.shoppingWay} </if>" +
            "<if test=\"orderDto.deliveryAddressId != null\"> AND o.delivery_address_id = #{orderDto.deliveryAddressId} </if>" +
            "<if test=\"orderDto.contactRealname != null and orderDto.contactRealname !=''\"> AND o.contact_realname like '%${orderDto.contactRealname}%' </if>" +
            "<if test=\"orderDto.contactPhone != null and orderDto.contactPhone !=''\"> AND o.contact_phone like '%${orderDto.contactPhone}%' </if>" +
            "<if test=\"orderDto.contactProvince != null and orderDto.contactProvince !=''\"> AND o.contact_province like '%${orderDto.contactProvince}%' </if>" +
            "<if test=\"orderDto.contactCity != null and orderDto.contactCity !=''\"> AND o.contact_city like '%${orderDto.contactCity}%' </if>" +
            "<if test=\"orderDto.contactArea != null and orderDto.contactArea !=''\"> AND o.contact_area like '%${orderDto.contactArea}%' </if>" +
            "<if test=\"orderDto.contactStreet != null and orderDto.contactStreet !=''\"> AND o.contact_street like '%${orderDto.contactStreet}%' </if>" +
            "<if test=\"orderDto.contactSex != null\"> AND o.contact_sex = #{orderDto.contactSex} </if>" +
            "<if test=\"orderDto.remark != null and orderDto.remark !=''\"> AND o.remark like '%${orderDto.remark}%' </if>" +
            "<if test=\"orderDto.description != null and orderDto.description !=''\"> AND o.description like '%${orderDto.description}%' </if>" +
            "<if test=\"orderDto.tradeId != null\"> AND o.trade_id = #{orderDto.tradeId} </if>" +
            "<if test=\"orderDto.orderLogisticsId != null\"> AND o.order_logistics_id = #{orderDto.orderLogisticsId} </if>" +
            "<if test=\"orderDto.isInvoice != null\"> AND o.is_invoice = #{orderDto.isInvoice} </if>" +
            "<if test=\"orderDto.invoiceId != null\"> AND o.invoice_id = #{orderDto.invoiceId} </if>" +
            "<if test=\"orderDto.isDeleted != null\"> AND o.is_deleted = #{orderDto.isDeleted} </if>" +
            "<if test=\"orderDto.shopId != null\"> AND o.shop_id = #{orderDto.shopId} </if>" +
            "<if test=\"orderDto.shopName != null and orderDto.shopName !=''\"> AND o.shop_name like '%${orderDto.shopName}%' </if>" +
            "<if test=\"orderDto.cancelReason != null\"> AND o.cancel_reason = #{orderDto.cancelReason} </if>" +
            "<if test=\"orderDto.isPrinted != null\"> AND o.is_printed = #{orderDto.isPrinted} </if>" +
            "<if test=\"orderDto.queueNo != null\"> AND o.queue_no = #{orderDto.queueNo} </if>" +
            "<if test=\"orderDto.fullReductionRuleId != null\"> AND o.full_reduction_rule_id = #{orderDto.fullReductionRuleId} </if>" +
            "<if test=\"orderDto.fullReductionRuleDescription != null and orderDto.fullReductionRuleDescription !=''\"> AND o.full_reduction_rule_description like '%${orderDto.fullReductionRuleDescription}%' </if>" +
            "<if test=\"orderDto.couponsId != null\"> AND o.coupons_id = #{orderDto.couponsId} </if>" +
            "<if test=\"orderDto.couponsDescription != null and orderDto.couponsDescription !=''\"> AND o.coupons_description like '%${orderDto.couponsDescription}%' </if>" +
            "<if test=\"orderDto.couponsMemberRelationId != null\"> AND o.coupons_member_relation_id = #{orderDto.couponsMemberRelationId} </if>" +
            "<if test=\"orderDto.isChangeToDelivery != null\"> AND o.is_change_to_delivery = #{orderDto.isChangeToDelivery} </if>" +
            "<if test=\"orderDto.changeToDeliveryOutTradeNo != null and orderDto.changeToDeliveryOutTradeNo !=''\"> AND o.change_to_delivery_out_trade_no like '%${orderDto.changeToDeliveryOutTradeNo}%' </if>" +
            "<if test=\"orderDto.changeToDeliveryTradeId != null\"> AND o.change_to_delivery_trade_id = #{orderDto.changeToDeliveryTradeId} </if>" +
            "<if test=\"orderDto.startCreateTime != null\"> AND DATE_FORMAT(o.create_time, '%Y/%m/%d') &gt;= #{orderDto.startCreateTime} </if>" +
            "<if test=\"orderDto.endCreateTime != null\"> AND DATE_FORMAT(o.create_time, '%Y/%m/%d') &lt;= #{orderDto.endCreateTime} </if>" +
            "</where> order by o.create_time desc" +
            "</script>")
    Page<Order> getAfterSalesListByPageWithAsc(@Param("page") Page page, @Param("orderDto") OrderParam param);

    @ResultMap("BaseResultMap")
    @Select("SELECT o.* FROM tb_order o where o.status in (6, 9) and TIMESTAMPDIFF(HOUR, o.payment_success_time, now()) > 24 and o.is_pay_to_merchant = 0")
    List<Order> selectByNeedPayOrderFrozenBalanceOfMerchant();

    @Select("SELECT o.shop_id FROM tb_order o WHERE o.status IN (2, 4) AND TIMESTAMPDIFF(MINUTE, o.payment_success_time, NOW()) > 10 GROUP BY o.shop_id")
    List<Integer> selectShopIdByOvertimeOrder();

    @Select("<script>SELECT DATE_FORMAT(create_time, '%Y-%m-%d') AS orderDate, COUNT(*) AS orderCount, IFNULL(SUM(actual_price), 0) AS orderAmount, IFNULL(SUM(merchant_income), 0) AS merchantIncome FROM tb_order " +
            "<where> STATUS in (6, 7, 9) " +
            "<if test=\"shopId != null\"> AND shop_id = #{shopId} </if>" +
            "</where> GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')" +
            "</script>")
    List<Map<String, Object>> selectStatisticOrder(@Param("shopId") Integer shopId);

    @Select("<script>SELECT DATE_FORMAT(create_time, '%Y-%m-%d') AS orderDate FROM tb_order " +
            "<where> STATUS = 6 " +
            "<if test=\"shopId != null\"> AND shop_id = #{shopId} </if>" +
            "</where> order by create_time asc limit 1 " +
            "</script>")
    String selectStartDateOrder(@Param("shopId") Integer shopId);

    //查询支付人数
    @Select("<script>select count(DISTINCT(o.member_id)) from tb_order as o "+
            "<where> 1=1 and STATUS not in (1, 10) and (o.create_time between #{order.startTime} and #{order.endTime})" +
            "<if test=\"order.shopId != null\"> AND o.shop_id = #{order.shopId} </if>" +
            "</where>"+
            "</script>")
    int selectCountPayers(@Param("order") OrderParam param);

    //查询下单人数
    @Select("<script>select count(DISTINCT(o.member_id)) from tb_order as o "+
            "<where> 1=1 and (o.create_time between #{order.startTime} and #{order.endTime})" +
            "<if test=\"order.shopId != null\"> AND o.shop_id = #{order.shopId} </if>" +
            "</where>"+
            "</script>")
    int selectCountOrderPeoples(@Param("order") OrderParam param);

    @Select("<script>select IFNULL(sum(o.actual_price), 0) as totalActualPrice, IFNULL(sum(o.delivery_fee), 0) as totalDeliveryFee from tb_order as o "+
            "<where> 1=1 and STATUS not in (1, 10)" +
            "</where>"+
            "</script>")
    Map<String, Object> selectSumField(@Param("order") OrderParam param);

    @Select("<script>select count(*) from tb_order as o "+
            "<where> 1=1 and STATUS in (2, 3, 4, 5, 7, 8)" +
            "<if test=\"order.memberId != null\"> AND o.member_id = #{order.memberId} </if>" +
            "<if test=\"order.shopId != null\"> AND o.shop_id = #{order.shopId} </if>" +
            "</where>"+
            "</script>")
    Integer selectCountUnCompleted(@Param("order") OrderParam param);

    @Select("<script>select count(*) from tb_order as o "+
            "<where> 1=1 and STATUS in (7, 8)" +
            "<if test=\"order.memberId != null\"> AND o.member_id = #{order.memberId} </if>" +
            "<if test=\"order.shopId != null\"> AND o.shop_id = #{order.shopId} </if>" +
            "</where>"+
            "</script>")
    Integer selectCountWaitHandle(@Param("order") OrderParam param);

}