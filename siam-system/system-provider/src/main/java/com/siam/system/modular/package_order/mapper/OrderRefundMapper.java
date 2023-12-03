package com.siam.system.modular.package_order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_order.entity.OrderRefund;
import com.siam.system.modular.package_order.model.example.OrderRefundExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface OrderRefundMapper extends BaseMapper<OrderRefund> {
    int countByExample(OrderRefundExample example);

    int deleteByExample(OrderRefundExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrderRefund record);

    List<OrderRefund> selectByExample(OrderRefundExample example);

    OrderRefund selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderRefund record, @Param("example") OrderRefundExample example);

    int updateByExample(@Param("record") OrderRefund record, @Param("example") OrderRefundExample example);

    int updateByPrimaryKeySelective(OrderRefund record);

    int updateByPrimaryKey(OrderRefund record);

    @ResultMap("BaseResultMap")
    @Select("<script>select or1.* from tb_order_refund or1" +
            "<where> 1=1 " +
            "<if test=\"orderRefund.id != null\"> AND or1.id = #{orderRefund.id} </if>" +
            "<if test=\"orderRefund.orderId != null\"> AND or1.order_id = #{orderRefund.orderId} </if>" +
            "<if test=\"orderRefund.goodsId != null\"> AND or1.goods_id = #{orderRefund.goodsId} </if>" +
            "<if test=\"orderRefund.goodsName != null and orderRefund.goodsName !=''\"> AND or1.goods_name like '%${orderRefund.goodsName}%' </if>" +
            "<if test=\"orderRefund.specList != null\"> AND or1.spec_list = #{orderRefund.specList} </if>" +
            "<if test=\"orderRefund.price != null\"> AND or1.price = #{orderRefund.price} </if>" +
            "<if test=\"orderRefund.number != null\"> AND or1.number = #{orderRefund.number} </if>" +
            "<if test=\"orderRefund.subtotal != null\"> AND or1.subtotal = #{orderRefund.subtotal} </if>" +
            "<if test=\"orderRefund.isDeleted != null\"> AND or1.is_deleted = #{orderRefund.isDeleted} </if>" +
            "</where> order by or1.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("orderRefund") OrderRefund orderRefund);

    @ResultMap("BaseResultMap")
    @Select("select or1.* from tb_order_refund or1 where order_id = #{orderId} limit 1 ")
    OrderRefund selectByOrderId(Integer orderId);

    @Select("<script>select IFNULL(sum(or.refund_amount), 0) as totalRefundAmount, IFNULL(sum(or.delivery_fee), 0) as totalRefundDeliveryFee from tb_order_refund as `or` " +
            "<where> 1=1 and STATUS in (7)" +
            "</where>"+
            "</script>")
    Map<String, Object> selectSumField(@Param("orderRefund") OrderRefund orderRefund);

    @Select("<script>select IFNULL(sum(or.refund_amount), 0) as refundAmount from tb_order_refund as `or` " +
            "<where> 1=1 and STATUS in (7) and or.refund_account in (1, 2) and (or.create_time between #{startTime} and #{endTime})" +
            "</where>"+
            "</script>")
    BigDecimal selectSumRefundAmount(@Param("orderRefund") OrderRefund orderRefund, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Select("<script>select IFNULL(sum(or.refund_amount), 0) as refundAmount from tb_order_refund as `or` " +
            "<where> 1=1 and STATUS in (7) and or.refund_account in (3, 4) and (or.create_time between #{startTime} and #{endTime})" +
            "</where>"+
            "</script>")
    BigDecimal selectSumRefundAmountByPlatformCoin(@Param("orderRefund") OrderRefund orderRefund, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}