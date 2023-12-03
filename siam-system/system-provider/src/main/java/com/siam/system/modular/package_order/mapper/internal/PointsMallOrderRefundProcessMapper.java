package com.siam.system.modular.package_order.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderRefundProcess;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderRefundProcessExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface PointsMallOrderRefundProcessMapper extends BaseMapper<PointsMallOrderRefundProcess> {
    int countByExample(PointsMallOrderRefundProcessExample example);

    int deleteByExample(PointsMallOrderRefundProcessExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PointsMallOrderRefundProcess record);

    List<PointsMallOrderRefundProcess> selectByExample(PointsMallOrderRefundProcessExample example);

    PointsMallOrderRefundProcess selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PointsMallOrderRefundProcess record, @Param("example") PointsMallOrderRefundProcessExample example);

    int updateByExample(@Param("record") PointsMallOrderRefundProcess record, @Param("example") PointsMallOrderRefundProcessExample example);

    int updateByPrimaryKeySelective(PointsMallOrderRefundProcess record);

    int updateByPrimaryKey(PointsMallOrderRefundProcess record);

    @ResultMap("BaseResultMap")
    @Select("<script>select orp.* from tb_points_mall_order_refund_process orp" +
            "<where> 1=1 " +
            "<if test=\"orderRefundProcess.id != null\"> AND orp.id = #{orderRefundProcess.id} </if>" +
            "</where> order by orp.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("orderRefundProcess") PointsMallOrderRefundProcess orderRefundProcess);

    @ResultMap("BaseResultMap")
    @Select("select orp.* from tb_points_mall_order_refund_process orp where order_refund_id = #{orderRefundId} order by create_time desc")
    List<PointsMallOrderRefundProcess> selectByPointsMallOrderRefundId(Integer orderRefundId);
}