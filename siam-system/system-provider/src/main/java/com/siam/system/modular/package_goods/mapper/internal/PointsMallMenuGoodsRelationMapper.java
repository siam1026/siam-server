package com.siam.system.modular.package_goods.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.internal.PointsMallMenuGoodsRelation;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallMenuGoodsRelationExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface PointsMallMenuGoodsRelationMapper extends BaseMapper<PointsMallMenuGoodsRelation> {
    int countByExample(PointsMallMenuGoodsRelationExample example);

    int deleteByExample(PointsMallMenuGoodsRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PointsMallMenuGoodsRelation record);

    List<PointsMallMenuGoodsRelation> selectByExample(PointsMallMenuGoodsRelationExample example);

    PointsMallMenuGoodsRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PointsMallMenuGoodsRelation record, @Param("example") PointsMallMenuGoodsRelationExample example);

    int updateByExample(@Param("record") PointsMallMenuGoodsRelation record, @Param("example") PointsMallMenuGoodsRelationExample example);

    int updateByPrimaryKeySelective(PointsMallMenuGoodsRelation record);

    int updateByPrimaryKey(PointsMallMenuGoodsRelation record);

    @ResultMap("BaseResultMap")
    @Select("<script>select mgr.* from tb_points_mall_menu_goods_relation mgr" +
            "<where> 1=1 " +
            "<if test=\"menuGoodRelation.id != null\"> AND mgr.id = #{menuGoodRelation.id} </if>" +
            "<if test=\"menuGoodRelation.goodsId != null\"> AND mgr.goods_id = #{menuGoodRelation.goodsId} </if>" +
            "<if test=\"menuGoodRelation.menuId != null\"> AND mgr.menu_id = #{menuGoodRelation.menuId} </if>" +
            "</where> order by mgr.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("menuGoodRelation") PointsMallMenuGoodsRelation menuPointsMallGoodsRelation);

    @Delete("delete from tb_points_mall_menu_goods_relation where goods_id = #{goodsId}")
    void deleteByPointsMallGoodsId(@Param("goodsId") int goodsId);
}