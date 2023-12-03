package com.siam.system.modular.package_order.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderLogistics;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderLogisticsExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface PointsMallOrderLogisticsMapper extends BaseMapper<PointsMallOrderLogistics> {
    int countByExample(PointsMallOrderLogisticsExample example);

    int deleteByExample(PointsMallOrderLogisticsExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PointsMallOrderLogistics record);

    List<PointsMallOrderLogistics> selectByExample(PointsMallOrderLogisticsExample example);

    PointsMallOrderLogistics selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PointsMallOrderLogistics record, @Param("example") PointsMallOrderLogisticsExample example);

    int updateByExample(@Param("record") PointsMallOrderLogistics record, @Param("example") PointsMallOrderLogisticsExample example);

    int updateByPrimaryKeySelective(PointsMallOrderLogistics record);

    int updateByPrimaryKey(PointsMallOrderLogistics record);

    @ResultMap("BaseResultMap")
    @Select("<script>select ol.* from tb_points_mall_order_logistics ol" +
            "<where> 1=1 " +
            "<if test=\"orderLogistics.id != null\"> AND ol.id = #{orderLogistics.id} </if>" +
            "<if test=\"orderLogistics.orderId != null\"> AND ol.order_id = #{orderLogistics.orderId} </if>" +
            "</where> order by ol.description_time desc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("orderLogistics") PointsMallOrderLogistics orderLogistics);
}