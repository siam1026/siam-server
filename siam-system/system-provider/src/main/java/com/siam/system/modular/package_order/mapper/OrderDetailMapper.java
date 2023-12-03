package com.siam.system.modular.package_order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_order.entity.OrderDetail;
import com.siam.system.modular.package_order.model.example.OrderDetailExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    int countByExample(OrderDetailExample example);

    int deleteByExample(OrderDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrderDetail record);

    List<OrderDetail> selectByExample(OrderDetailExample example);

    OrderDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderDetail record, @Param("example") OrderDetailExample example);

    int updateByExample(@Param("record") OrderDetail record, @Param("example") OrderDetailExample example);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);

    @ResultMap("BaseResultMap")
    @Select("<script>select od.* from tb_order_detail od" +
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
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("orderDetail") OrderDetail orderDetail);

    @ResultMap("BaseResultMap")
    @Select("select od.* from tb_order_detail od where order_id = #{orderId}")
    List<OrderDetail> selectByOrderId(@Param("orderId") Integer orderId);
}