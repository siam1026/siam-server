package com.siam.system.modular.package_order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_order.entity.OrderRefundGoods;
import com.siam.system.modular.package_order.model.example.OrderRefundGoodsExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface OrderRefundGoodsMapper extends BaseMapper<OrderRefundGoods> {
    int countByExample(OrderRefundGoodsExample example);

    int deleteByExample(OrderRefundGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrderRefundGoods record);

    List<OrderRefundGoods> selectByExample(OrderRefundGoodsExample example);

    OrderRefundGoods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderRefundGoods record, @Param("example") OrderRefundGoodsExample example);

    int updateByExample(@Param("record") OrderRefundGoods record, @Param("example") OrderRefundGoodsExample example);

    int updateByPrimaryKeySelective(OrderRefundGoods record);

    int updateByPrimaryKey(OrderRefundGoods record);

    @ResultMap("BaseResultMap")
    @Select("<script>select org.* from tb_order_refund_goods org" +
            "<where> 1=1 " +
            "<if test=\"orderRefundGoods.id != null\"> AND org.id = #{orderRefundGoods.id} </if>" +
            "<if test=\"orderRefundGoods.orderId != null\"> AND org.order_id = #{orderRefundGoods.orderId} </if>" +
            "<if test=\"orderRefundGoods.goodsId != null\"> AND org.goods_id = #{orderRefundGoods.goodsId} </if>" +
            "<if test=\"orderRefundGoods.goodsName != null and orderRefundGoods.goodsName !=''\"> AND org.goods_name like '%${orderRefundGoods.goodsName}%' </if>" +
            "<if test=\"orderRefundGoods.specList != null\"> AND org.spec_list = #{orderRefundGoods.specList} </if>" +
            "<if test=\"orderRefundGoods.price != null\"> AND org.price = #{orderRefundGoods.price} </if>" +
            "<if test=\"orderRefundGoods.number != null\"> AND org.number = #{orderRefundGoods.number} </if>" +
            "<if test=\"orderRefundGoods.subtotal != null\"> AND org.subtotal = #{orderRefundGoods.subtotal} </if>" +
            "<if test=\"orderRefundGoods.isDeleted != null\"> AND org.is_deleted = #{orderRefundGoods.isDeleted} </if>" +
            "</where> order by org.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("orderRefundGoods") OrderRefundGoods orderRefundGoods);

    @ResultMap("BaseResultMap")
    @Select("select org.* from tb_order_refund_goods org where order_refund_id = #{orderRefundId}")
    List<OrderRefundGoods> selectByOrderRefundId(Integer orderRefundId);
}