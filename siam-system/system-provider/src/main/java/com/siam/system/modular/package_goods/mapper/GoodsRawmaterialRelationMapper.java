package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.GoodsRawmaterialRelation;
import com.siam.system.modular.package_goods.model.example.GoodsRawmaterialRelationExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface GoodsRawmaterialRelationMapper extends BaseMapper<GoodsRawmaterialRelation> {
    int countByExample(GoodsRawmaterialRelationExample example);

    int deleteByExample(GoodsRawmaterialRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(GoodsRawmaterialRelation record);

    List<GoodsRawmaterialRelation> selectByExample(GoodsRawmaterialRelationExample example);

    GoodsRawmaterialRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsRawmaterialRelation record, @Param("example") GoodsRawmaterialRelationExample example);

    int updateByExample(@Param("record") GoodsRawmaterialRelation record, @Param("example") GoodsRawmaterialRelationExample example);

    int updateByPrimaryKeySelective(GoodsRawmaterialRelation record);

    int updateByPrimaryKey(GoodsRawmaterialRelation record);

    @Delete("delete from tb_goods_rawmaterial_relation where goods_id = #{goodsId}")
    void deleteByGoodsId(@Param("goodsId") int goodsId);

    @ResultMap("BaseResultMap")
    @Select("<script>select grr.* from tb_goods_rawmaterial_relation grr" +
            "<where> 1=1 " +
            "<if test=\"goodsRawmaterialRelation.id != null\"> AND grr.id = #{goodsRawmaterialRelation.id} </if>" +
            "<if test=\"goodsRawmaterialRelation.goodsId != null\"> AND grr.goods_id = #{goodsRawmaterialRelation.goodsId} </if>" +
            "<if test=\"goodsRawmaterialRelation.rawmaterialId != null\"> AND grr.rawmaterial_id = #{goodsRawmaterialRelation.rawmaterialId} </if>" +
            "<if test=\"goodsRawmaterialRelation.consumedQuantity != null\"> AND grr.consumed_quantity = #{goodsRawmaterialRelation.consumedQuantity} </if>" +
            "</where> order by grr.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("goodsRawmaterialRelation") GoodsRawmaterialRelation goodsRawmaterialRelation);

    @ResultMap("CustomResultMap")
    @Select("<script>select tgrr.id as goods_rawmaterial_relation_id, tgrr.rawmaterial_id, tgrr.consumed_quantity,tg.id as goods_id,tg.name as goodsName, tr.name as rawmaterial_name, tr.description, tr.unit, " +
            "tr.price, tr.stock from tb_goods tg left join tb_goods_rawmaterial_relation tgrr " +
            "on tgrr.goods_id = tg.id " +
            "left join tb_rawmaterial tr on tgrr.rawmaterial_id = tr.id " +
            "<where> 1=1" +
            "<if test=\"listGoodsIds != null\"> and tg.id in  <foreach collection=\"listGoodsIds\" index=\"index\" item=\"listGoodsId\" open=\"(\" separator=\",\" close=\")\">  \n" +
            "#{listGoodsId}\n" +
            "</foreach></if>" +
            "</where> order by tgrr.id desc" +
            "</script>")
    List<Map<String, Object>> findGoodsRawmaterialRelation(@Param("listGoodsIds") List<String> listGoodsIds);

    @ResultMap("CustomResultMap")
    @Select("select grr.*, r.name as rawmaterialName, r.unit as rawmaterialUnit from tb_goods_rawmaterial_relation grr " +
            "left join tb_rawmaterial r on grr.rawmaterial_id = r.id " +
            "where grr.goods_id = #{goodsId} order by r.id asc")
    Page<Map<String, Object>> selectByGoodsId(@Param("page") Page page, int goodsId);
}