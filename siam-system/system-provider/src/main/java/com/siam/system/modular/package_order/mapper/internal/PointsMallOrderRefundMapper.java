package com.siam.system.modular.package_order.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderRefund;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderRefundExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface PointsMallOrderRefundMapper extends BaseMapper<PointsMallOrderRefund> {
    int countByExample(PointsMallOrderRefundExample example);

    int deleteByExample(PointsMallOrderRefundExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PointsMallOrderRefund record);

    List<PointsMallOrderRefund> selectByExample(PointsMallOrderRefundExample example);

    PointsMallOrderRefund selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PointsMallOrderRefund record, @Param("example") PointsMallOrderRefundExample example);

    int updateByExample(@Param("record") PointsMallOrderRefund record, @Param("example") PointsMallOrderRefundExample example);

    int updateByPrimaryKeySelective(PointsMallOrderRefund record);

    int updateByPrimaryKey(PointsMallOrderRefund record);

    @ResultMap("BaseResultMap")
    @Select("<script>select or1.* from tb_points_mall_order_refund or1" +
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
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("orderRefund") PointsMallOrderRefund orderRefund);

    @ResultMap("BaseResultMap")
    @Select("select or1.* from tb_points_mall_order_refund or1 where order_id = #{orderId} limit 1 ")
    PointsMallOrderRefund selectByPointsMallOrderId(Long orderId);

    @Select("<script>select IFNULL(sum(or.refund_amount), 0) as refundAmount from tb_points_mall_order_refund as `or` " +
            "<where> 1=1 and STATUS in (7) and or.refund_account in (1, 2) and (or.create_time between #{startTime} and #{endTime})" +
            "</where>"+
            "</script>")
    BigDecimal selectSumRefundAmount(@Param("orderRefund") PointsMallOrderRefund orderRefund, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Select("<script>select IFNULL(sum(or.refund_amount), 0) as refundAmount from tb_points_mall_order_refund as `or` " +
            "<where> 1=1 and STATUS in (7) and or.refund_account in (3, 4) and (or.create_time between #{startTime} and #{endTime})" +
            "</where>"+
            "</script>")
    BigDecimal selectSumRefundAmountByPlatformCoin(@Param("orderRefund") PointsMallOrderRefund orderRefund, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}