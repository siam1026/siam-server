package com.siam.system.modular.package_goods.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.internal.PointsMallMenu;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallMenuExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface PointsMallMenuMapper extends BaseMapper<PointsMallMenu> {
    int countByExample(PointsMallMenuExample example);

    int deleteByExample(PointsMallMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PointsMallMenu record);

    List<PointsMallMenu> selectByExample(PointsMallMenuExample example);

    PointsMallMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PointsMallMenu record, @Param("example") PointsMallMenuExample example);

    int updateByExample(@Param("record") PointsMallMenu record, @Param("example") PointsMallMenuExample example);

    int updateByPrimaryKeySelective(PointsMallMenu record);

    int updateByPrimaryKey(PointsMallMenu record);

    @ResultMap("BaseResultMap")
    @Select("<script>select m.* from tb_points_mall_menu m" +
            "<where> 1=1 " +
            "<if test=\"menu.id != null\"> AND m.id = #{menu.id} </if>" +
            "<if test=\"menu.name != null and menu.name !=''\"> AND m.name like '%${menu.name}%' </if>" +
            "<if test=\"menu.sortNumber != null\"> AND m.sort_number = #{menu.sortNumber} </if>" +
            "<if test=\"menu.description != null and menu.description != ''\"> AND m.description like '%${menu.description}%' </if>" +
            "</where> order by m.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("menu") PointsMallMenu menu);

    @ResultMap("CustomResultMap")
    @Select("<script>select m.*, g.id as goodsId, g.name as goodsName, g.main_image as mainImage, g.price as goodsPrice, g.sale_price as salePrice, g.stock, g.status as goodsStatus, g.is_sale as isSale, g.packing_charges as packingCharges " +
            "from tb_points_mall_menu m " +
            "left join tb_points_mall_menu_goods_relation mgr on mgr.menu_id = m.id " +
            "left join tb_points_mall_goods g on g.id = mgr.goods_id " +
            "<where> 1=1 " +
            "<if test=\"menu.id != null\"> AND m.id = #{menu.id} </if>" +
            "<if test=\"menu.name != null and menu.name !=''\"> AND m.name like '%${menu.name}%' </if>" +
            "<if test=\"menu.sortNumber != null\"> AND m.sort_number = #{menu.sortNumber} </if>" +
            "<if test=\"menu.description != null and menu.description != ''\"> AND m.description like '%${menu.description}%' </if>" +
            "</where> order by m.id desc" +
            "</script>")
    List<Map<String, Object>> getListByPageJoinPointsMallGoods(@Param("menu") PointsMallMenu menu);
}