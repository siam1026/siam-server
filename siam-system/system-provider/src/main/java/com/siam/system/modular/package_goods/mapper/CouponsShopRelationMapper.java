package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.CouponsShopRelation;
import com.siam.system.modular.package_goods.model.example.CouponsShopRelationExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface CouponsShopRelationMapper extends BaseMapper<CouponsShopRelation> {
    int countByExample(CouponsShopRelationExample example);

    int deleteByExample(CouponsShopRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(CouponsShopRelation record);

    List<CouponsShopRelation> selectByExample(CouponsShopRelationExample example);

    CouponsShopRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CouponsShopRelation record, @Param("example") CouponsShopRelationExample example);

    int updateByExample(@Param("record") CouponsShopRelation record, @Param("example") CouponsShopRelationExample example);

    int updateByPrimaryKeySelective(CouponsShopRelation record);

    int updateByPrimaryKey(CouponsShopRelation record);

    @ResultMap("BaseResultMap")
    @Select("<script>select c.* from tb_coupons_shop_relation c" +
            "<where> 1=1 " +
            "<if test=\"couponsShopRelation.id != null\"> AND c.id = #{couponsShopRelation.id} </if>" +
            "<if test=\"couponsShopRelation.couponsId != null\"> AND c.coupons_id = #{couponsShopRelation.couponsId} </if>" +
            "<if test=\"couponsShopRelation.shopId != null\"> AND c.shop_id = #{couponsShopRelation.shopId} </if>" +
            "</where> order by c.id asc" +
            "</script>")
    Page<CouponsShopRelation> getListByPage(@Param("page") Page page, @Param("couponsShopRelation") CouponsShopRelation couponsShopRelation);

    @Delete("delete from tb_coupons_shop_relation where shop_id = #{shopId}")
    int deleteByShopId(@Param("shopId") int shopId);

    @Delete("delete from tb_coupons_shop_relation where coupons_id = #{couponsId}")
    void deleteByCouponsId(@Param("couponsId") int couponsId);

    @Select("select shop_id as shopId from tb_coupons_shop_relation where coupons_id=#{couponsId}")
    List<Integer> getShopIdByCouponsId(@Param("couponsId") Integer couponsId);
}