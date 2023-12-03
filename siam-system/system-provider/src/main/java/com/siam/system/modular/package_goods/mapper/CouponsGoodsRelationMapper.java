package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.CouponsGoodsRelation;
import com.siam.system.modular.package_goods.model.example.CouponsGoodsRelationExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface CouponsGoodsRelationMapper extends BaseMapper<CouponsGoodsRelation> {
    int countByExample(CouponsGoodsRelationExample example);

    int deleteByExample(CouponsGoodsRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(CouponsGoodsRelation record);

    List<CouponsGoodsRelation> selectByExample(CouponsGoodsRelationExample example);

    CouponsGoodsRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CouponsGoodsRelation record, @Param("example") CouponsGoodsRelationExample example);

    int updateByExample(@Param("record") CouponsGoodsRelation record, @Param("example") CouponsGoodsRelationExample example);

    int updateByPrimaryKeySelective(CouponsGoodsRelation record);

    int updateByPrimaryKey(CouponsGoodsRelation record);

    @Delete("delete from tb_coupons_goods_relation where coupons_id = #{couponsId}")
    void deleteByCouponsId(@Param("couponsId") int couponsId);

    @ResultMap("BaseResultMap")
    @Select("<script>select c.* from tb_coupons_goods_relation c" +
            "<where> 1=1 " +
            "<if test=\"couponsGoodsRelation.id != null\"> AND c.id = #{couponsGoodsRelation.id} </if>" +
            "<if test=\"couponsGoodsRelation.couponsId != null\"> AND c.coupons_id = #{couponsGoodsRelation.couponsId} </if>" +
            "<if test=\"couponsGoodsRelation.goodsId != null\"> AND c.goods_id = #{couponsGoodsRelation.goodsId} </if>" +
            "</where> order by c.id asc" +
            "</script>")
    Page<CouponsGoodsRelation> getListByPage(@Param("page") Page page, @Param("couponsGoodsRelation") CouponsGoodsRelation couponsGoodsRelation);

    @Select("select goods_id as goodsId from tb_coupons_goods_relation where coupons_id=#{couponsId}")
    List<Integer> getGoodsIdByCouponsId(@Param("couponsId") Integer couponsId);

    @Delete("delete from tb_coupons_goods_relation where goods_id = #{goodsId}")
    int deleteByGoodsId(@Param("goodsId") int goodsId);
}