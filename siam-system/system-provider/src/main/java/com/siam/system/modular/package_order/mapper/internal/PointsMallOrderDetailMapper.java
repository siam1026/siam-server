package com.siam.system.modular.package_order.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderDetail;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderDetailExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface PointsMallOrderDetailMapper extends BaseMapper<PointsMallOrderDetail> {
    int countByExample(PointsMallOrderDetailExample example);

    int deleteByExample(PointsMallOrderDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PointsMallOrderDetail record);

    List<PointsMallOrderDetail> selectByExample(PointsMallOrderDetailExample example);

    PointsMallOrderDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PointsMallOrderDetail record, @Param("example") PointsMallOrderDetailExample example);

    int updateByExample(@Param("record") PointsMallOrderDetail record, @Param("example") PointsMallOrderDetailExample example);

    int updateByPrimaryKeySelective(PointsMallOrderDetail record);

    int updateByPrimaryKey(PointsMallOrderDetail record);

    @ResultMap("BaseResultMap")
    @Select("<script>select od.* from tb_points_mall_order_detail od" +
            "<where> 1=1 " +
            "<if test=\"orderDetail.id != null\"> AND od.id = #{orderDetail.id} </if>" +
            "<if test=\"orderDetail.orderId != null\"> AND od.order_id = #{orderDetail.orderId} </if>" +
            "<if test=\"orderDetail.goodsId != null\"> AND od.goods_id = #{orderDetail.goodsId} </if>" +
            "<if test=\"orderDetail.goodsName != null and orderDetail.goodsName !=''\"> AND od.goods_name like '%${orderDetail.goodsName}%' </if>" +
            "<if test=\"orderDetail.specList != null\"> AND od.spec_list = #{orderDetail.specList} </if>" +
            "<if test=\"orderDetail.price != null\"> AND od.price = #{orderDetail.price} </if>" +
            "<if test=\"orderDetail.number != null\"> AND od.number = #{orderDetail.number} </if>" +
            "<if test=\"orderDetail.subtotal != null\"> AND od.subtotal = #{orderDetail.subtotal} </if>" +
            "<if test=\"orderDetail.isDeleted != null\"> AND od.is_deleted = #{orderDetail.isDeleted} </if>" +
            "</where> order by od.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("orderDetail") PointsMallOrderDetail orderDetail);

    @ResultMap("BaseResultMap")
    @Select("select od.* from tb_points_mall_order_detail od where order_id = #{orderId}")
    List<PointsMallOrderDetail> selectByPointsMallOrderId(@Param("orderId") Long orderId);
}