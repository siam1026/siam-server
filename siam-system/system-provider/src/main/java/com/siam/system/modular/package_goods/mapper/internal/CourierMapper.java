package com.siam.system.modular.package_goods.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.internal.Courier;
import com.siam.system.modular.package_goods.model.example.internal.CourierExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface CourierMapper extends BaseMapper<Courier> {
    int countByExample(CourierExample example);

    int deleteByExample(CourierExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Courier record);

    List<Courier> selectByExample(CourierExample example);

    Courier selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Courier record, @Param("example") CourierExample example);

    int updateByExample(@Param("record") Courier record, @Param("example") CourierExample example);

    int updateByPrimaryKeySelective(Courier record);

    int updateByPrimaryKey(Courier record);

    @ResultMap("BaseResultMap")
    @Select("<script>select c.* from tb_courier c" +
            "<where> 1=1 " +
            "<if test=\"courier.id != null\"> AND c.id = #{courier.id} </if>" +
            "<if test=\"courier.shopId != null\"> AND c.shop_id = #{courier.shopId} </if>" +
            "<if test=\"courier.realname != null and courier.realname !=''\"> AND c.realname like '%${courier.realname}%' </if>" +
            "<if test=\"courier.phone != null and courier.phone !=''\"> AND c.phone like '%${courier.phone}%' </if>" +
            "<if test=\"courier.sex != null\"> AND c.sex = #{courier.sex} </if>" +
            "<if test=\"courier.startCreateTime != null\"> AND DATE_FORMAT(c.create_time, '%Y/%m/%d') &gt;= #{courier.startCreateTime} </if>" +
            "<if test=\"courier.endCreateTime != null\"> AND DATE_FORMAT(c.create_time, '%Y/%m/%d') &lt;= #{courier.endCreateTime} </if>" +
            "</where> order by c.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("courier") Courier courier);

    @ResultMap("CustomResultMap")
    @Select("<script>select c.*, s.name as shopName from tb_courier c " +
            "left join tb_shop as s on s.id = c.shop_id " +
            "<where> 1=1 " +
            "<if test=\"courier.id != null\"> AND c.id = #{courier.id} </if>" +
            "<if test=\"courier.shopId != null\"> AND c.shop_id = #{courier.shopId} </if>" +
            "<if test=\"courier.realname != null and courier.realname !=''\"> AND c.realname like '%${courier.realname}%' </if>" +
            "<if test=\"courier.phone != null and courier.phone !=''\"> AND c.phone like '%${courier.phone}%' </if>" +
            "<if test=\"courier.sex != null\"> AND c.sex = #{courier.sex} </if>" +
            "<if test=\"courier.shopName != null and courier.shopName !=''\"> AND s.name like '%${courier.shopName}%' </if>" +
            "<if test=\"courier.startCreateTime != null\"> AND DATE_FORMAT(c.create_time, '%Y/%m/%d') &gt;= #{courier.startCreateTime} </if>" +
            "<if test=\"courier.endCreateTime != null\"> AND DATE_FORMAT(c.create_time, '%Y/%m/%d') &lt;= #{courier.endCreateTime} </if>" +
            "</where> order by c.shop_id asc, c.create_time asc" +
            "</script>")
    Page<Map<String, Object>> getListJoinShop(@Param("page") Page page, @Param("courier") Courier courier);
}