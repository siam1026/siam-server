package com.siam.system.modular.package_goods.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCouponsGoodsRelation;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallCouponsGoodsRelationExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface PointsMallCouponsGoodsRelationMapper extends BaseMapper<PointsMallCouponsGoodsRelation> {
    int countByExample(PointsMallCouponsGoodsRelationExample example);

    int deleteByExample(PointsMallCouponsGoodsRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PointsMallCouponsGoodsRelation record);

    List<PointsMallCouponsGoodsRelation> selectByExample(PointsMallCouponsGoodsRelationExample example);

    PointsMallCouponsGoodsRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PointsMallCouponsGoodsRelation record, @Param("example") PointsMallCouponsGoodsRelationExample example);

    int updateByExample(@Param("record") PointsMallCouponsGoodsRelation record, @Param("example") PointsMallCouponsGoodsRelationExample example);

    int updateByPrimaryKeySelective(PointsMallCouponsGoodsRelation record);

    int updateByPrimaryKey(PointsMallCouponsGoodsRelation record);

    @Delete("delete from tb_points_mall_coupons_goods_relation where coupons_id = #{couponsId}")
    void deleteByPointsMallCouponsId(@Param("couponsId") int couponsId);

    @ResultMap("BaseResultMap")
    @Select("<script>select c.* from tb_points_mall_coupons_goods_relation c" +
            "<where> 1=1 " +
            "<if test=\"couponsGoodsRelation.id != null\"> AND c.id = #{couponsGoodsRelation.id} </if>" +
            "<if test=\"couponsGoodsRelation.couponsId != null\"> AND c.coupons_id = #{couponsGoodsRelation.couponsId} </if>" +
            "<if test=\"couponsGoodsRelation.goodsId != null\"> AND c.goods_id = #{couponsGoodsRelation.goodsId} </if>" +
            "</where> order by c.id asc" +
            "</script>")
    Page<PointsMallCouponsGoodsRelation> getListByPage(@Param("page") Page page, @Param("couponsGoodsRelation") PointsMallCouponsGoodsRelation couponsGoodsRelation);

    @Select("select goods_id as goodsId from tb_points_mall_coupons_goods_relation where coupons_id=#{couponsId}")
    List<Integer> getGoodsIdByCouponsId(@Param("couponsId") Integer couponsId);

    @Delete("delete from tb_points_mall_coupons_goods_relation where goods_id = #{goodsId}")
    int deleteByPointsMallGoodsId(@Param("goodsId") int goodsId);
}