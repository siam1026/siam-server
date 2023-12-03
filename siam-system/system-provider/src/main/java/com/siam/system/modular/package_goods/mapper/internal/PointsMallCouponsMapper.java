package com.siam.system.modular.package_goods.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallCouponsDto;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCoupons;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallCouponsExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface PointsMallCouponsMapper extends BaseMapper<PointsMallCoupons> {
    int countByExample(PointsMallCouponsExample example);

    int deleteByExample(PointsMallCouponsExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PointsMallCoupons record);

    List<PointsMallCoupons> selectByExample(PointsMallCouponsExample example);

    PointsMallCoupons selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PointsMallCoupons record, @Param("example") PointsMallCouponsExample example);

    int updateByExample(@Param("record") PointsMallCoupons record, @Param("example") PointsMallCouponsExample example);

    int updateByPrimaryKeySelective(PointsMallCoupons record);

    int updateByPrimaryKey(PointsMallCoupons record);

    @ResultMap("BaseResultMap")
    @Select("<script>select c.* from tb_points_mall_coupons c" +
            "<where> 1=1 " +
            "<if test=\"coupons.id != null\"> AND c.id = #{coupons.id} </if>" +
            "<if test=\"coupons.name != null and coupons.name !=''\"> AND c.name like '%${coupons.name}%' </if>" +
            "<if test=\"coupons.preferentialType != null\"> AND c.preferential_type = #{coupons.preferentialType} </if>" +
            "<if test=\"coupons.discountAmount != null\"> AND c.discount_amount = #{coupons.discountAmount} </if>" +
            "<if test=\"coupons.reducedPrice != null\"> AND c.reduced_price = #{coupons.reducedPrice} </if>" +
            "<if test=\"coupons.description != null and PointsMallCoupons.description !=''\"> AND c.description = #{coupons.description} </if>" +
            "<if test=\"coupons.validType != null\"> AND c.valid_type = #{coupons.validType} </if>" +
            "<if test=\"coupons.validDays != null\"> AND c.valid_days = #{coupons.validDays} </if>" +
            "<if test=\"coupons.isDelete != null\"> AND c.is_delete = #{coupons.isDelete} </if>" +
            "<if test=\"coupons.source != null\"> AND c.source = #{coupons.source} </if>" +
            "</where> order by c.id asc" +
            "</script>")
    Page<PointsMallCoupons> getListByPage(@Param("page") Page page, @Param("coupons") PointsMallCoupons coupons);

    @ResultMap("CustomResultMap")
    @Select("<script>SELECT c.*, " +
            "(SELECT COUNT(*) FROM tb_points_mall_coupons_member_relation WHERE coupons_id = c.id) as gaveCount, " +
            "(SELECT COUNT(*) FROM tb_points_mall_coupons_member_relation WHERE coupons_id = c.id AND is_used = 1) as usedCount " +
            "FROM tb_points_mall_coupons c " +
            "<where> 1=1 " +
            "<if test=\"coupons.id != null\"> AND c.id = #{coupons.id} </if>" +
            "<if test=\"coupons.name != null and coupons.name !=''\"> AND c.name like '%${coupons.name}%' </if>" +
            "<if test=\"coupons.preferentialType != null\"> AND c.preferential_type = #{coupons.preferentialType} </if>" +
            "<if test=\"coupons.discountAmount != null\"> AND c.discount_amount = #{coupons.discountAmount} </if>" +
            "<if test=\"coupons.reducedPrice != null\"> AND c.reduced_price = #{coupons.reducedPrice} </if>" +
            "<if test=\"coupons.description != null and PointsMallCoupons.description !=''\"> AND c.description = #{coupons.description} </if>" +
            "<if test=\"coupons.validType != null\"> AND c.valid_type = #{coupons.validType} </if>" +
            "<if test=\"coupons.validDays != null\"> AND c.valid_days = #{coupons.validDays} </if>" +
            "<if test=\"coupons.isDelete != null\"> AND c.is_delete = #{coupons.isDelete} </if>" +
            "<if test=\"coupons.source != null\"> AND c.source = #{coupons.source} </if>" +
            "</where> order by c.id asc" +
            "</script>")
    Page<Map<String, Object>> getMapListByPage(@Param("page") Page page, @Param("coupons") PointsMallCoupons coupons);

    @ResultMap("CustomResultMap")
    @Select("<script>SELECT c.*, " +
            "(SELECT COUNT(*) FROM tb_points_mall_coupons_member_relation WHERE coupons_id = c.id) as gaveCount, " +
            "(SELECT COUNT(*) FROM tb_points_mall_coupons_member_relation WHERE coupons_id = c.id AND is_used = 1) as usedCount " +
            "FROM tb_points_mall_coupons c " +
            "LEFT JOIN tb_points_mall_coupons_shop_relation csr on csr.coupons_id = c.id" +
            "<where> 1=1 " +
            "<if test=\"couponsDto.id != null\"> AND c.id = #{couponsDto.id} </if>" +
            "<if test=\"couponsDto.name != null and couponsDto.name !=''\"> AND c.name like '%${couponsDto.name}%' </if>" +
            "<if test=\"couponsDto.preferentialType != null\"> AND c.preferential_type = #{couponsDto.preferentialType} </if>" +
            "<if test=\"couponsDto.discountAmount != null\"> AND c.discount_amount = #{couponsDto.discountAmount} </if>" +
            "<if test=\"couponsDto.reducedPrice != null\"> AND c.reduced_price = #{couponsDto.reducedPrice} </if>" +
            "<if test=\"couponsDto.description != null and PointsMallCoupons.description !=''\"> AND c.description = #{couponsDto.description} </if>" +
            "<if test=\"couponsDto.validType != null\"> AND c.valid_type = #{couponsDto.validType} </if>" +
            "<if test=\"couponsDto.validDays != null\"> AND c.valid_days = #{couponsDto.validDays} </if>" +
            "<if test=\"couponsDto.isDelete != null\"> AND c.is_delete = #{couponsDto.isDelete} </if>" +
            "<if test=\"couponsDto.source != null\"> AND c.source = #{couponsDto.source} </if>" +
            "</where> order by c.id asc" +
            "</script>")
    Page<Map<String, Object>> getListJoinPointsMallCouponsShopRelationByPage(@Param("page") Page page, @Param("couponsDto") PointsMallCouponsDto couponsDto);
}