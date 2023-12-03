package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.MenuGoodsRelation;
import com.siam.system.modular.package_goods.model.example.MenuGoodsRelationExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface MenuGoodsRelationMapper extends BaseMapper<MenuGoodsRelation> {
    int countByExample(MenuGoodsRelationExample example);

    int deleteByExample(MenuGoodsRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(MenuGoodsRelation record);

    List<MenuGoodsRelation> selectByExample(MenuGoodsRelationExample example);

    MenuGoodsRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MenuGoodsRelation record, @Param("example") MenuGoodsRelationExample example);

    int updateByExample(@Param("record") MenuGoodsRelation record, @Param("example") MenuGoodsRelationExample example);

    int updateByPrimaryKeySelective(MenuGoodsRelation record);

    int updateByPrimaryKey(MenuGoodsRelation record);

    @ResultMap("BaseResultMap")
    @Select("<script>select mgr.* from tb_menu_goods_relation mgr" +
            "<where> 1=1 " +
            "<if test=\"menuGoodRelation.id != null\"> AND mgr.id = #{menuGoodRelation.id} </if>" +
            "<if test=\"menuGoodRelation.goodsId != null\"> AND mgr.goods_id = #{menuGoodRelation.goodsId} </if>" +
            "<if test=\"menuGoodRelation.menuId != null\"> AND mgr.menu_id = #{menuGoodRelation.menuId} </if>" +
            "</where> order by mgr.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("menuGoodRelation") MenuGoodsRelation menuGoodsRelation);

    @Delete("delete from tb_menu_goods_relation where goods_id = #{goodsId}")
    void deleteByGoodsId(@Param("goodsId") int goodsId);
}