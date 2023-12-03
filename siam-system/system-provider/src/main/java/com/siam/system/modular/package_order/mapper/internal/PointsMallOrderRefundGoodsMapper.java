package com.siam.system.modular.package_order.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderRefundGoods;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderRefundGoodsExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface PointsMallOrderRefundGoodsMapper extends BaseMapper<PointsMallOrderRefundGoods> {
    int countByExample(PointsMallOrderRefundGoodsExample example);

    int deleteByExample(PointsMallOrderRefundGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PointsMallOrderRefundGoods record);

    List<PointsMallOrderRefundGoods> selectByExample(PointsMallOrderRefundGoodsExample example);

    PointsMallOrderRefundGoods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PointsMallOrderRefundGoods record, @Param("example") PointsMallOrderRefundGoodsExample example);

    int updateByExample(@Param("record") PointsMallOrderRefundGoods record, @Param("example") PointsMallOrderRefundGoodsExample example);

    int updateByPrimaryKeySelective(PointsMallOrderRefundGoods record);

    int updateByPrimaryKey(PointsMallOrderRefundGoods record);

    @ResultMap("BaseResultMap")
    @Select("<script>select org.* from tb_points_mall_order_refund_goods org" +
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
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("orderRefundGoods") PointsMallOrderRefundGoods orderRefundGoods);

    @ResultMap("BaseResultMap")
    @Select("select org.* from tb_points_mall_order_refund_goods org where order_refund_id = #{orderRefundId}")
    List<PointsMallOrderRefundGoods> selectByPointsMallOrderRefundId(Integer orderRefundId);
}