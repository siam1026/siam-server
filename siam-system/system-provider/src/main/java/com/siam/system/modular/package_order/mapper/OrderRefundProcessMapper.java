package com.siam.system.modular.package_order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_order.entity.OrderRefundProcess;
import com.siam.system.modular.package_order.model.example.OrderRefundProcessExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface OrderRefundProcessMapper extends BaseMapper<OrderRefundProcess> {
    int countByExample(OrderRefundProcessExample example);

    int deleteByExample(OrderRefundProcessExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrderRefundProcess record);

    List<OrderRefundProcess> selectByExample(OrderRefundProcessExample example);

    OrderRefundProcess selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderRefundProcess record, @Param("example") OrderRefundProcessExample example);

    int updateByExample(@Param("record") OrderRefundProcess record, @Param("example") OrderRefundProcessExample example);

    int updateByPrimaryKeySelective(OrderRefundProcess record);

    int updateByPrimaryKey(OrderRefundProcess record);

    @ResultMap("BaseResultMap")
    @Select("<script>select orp.* from tb_order_refund_process orp" +
            "<where> 1=1 " +
            "<if test=\"orderRefundProcess.id != null\"> AND orp.id = #{orderRefundProcess.id} </if>" +
            "</where> order by orp.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("orderRefundProcess") OrderRefundProcess orderRefundProcess);

    @ResultMap("BaseResultMap")
    @Select("select orp.* from tb_order_refund_process orp where order_refund_id = #{orderRefundId} order by create_time desc")
    List<OrderRefundProcess> selectByOrderRefundId(Integer orderRefundId);
}