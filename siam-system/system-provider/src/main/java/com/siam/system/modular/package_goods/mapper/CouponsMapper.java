package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.model.dto.CouponsDto;
import com.siam.system.modular.package_goods.entity.Coupons;
import com.siam.system.modular.package_goods.model.example.CouponsExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface CouponsMapper extends BaseMapper<Coupons> {
    int countByExample(CouponsExample example);

    int deleteByExample(CouponsExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Coupons record);

    List<Coupons> selectByExample(CouponsExample example);

    Coupons selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Coupons record, @Param("example") CouponsExample example);

    int updateByExample(@Param("record") Coupons record, @Param("example") CouponsExample example);

    int updateByPrimaryKeySelective(Coupons record);

    int updateByPrimaryKey(Coupons record);

    @ResultMap("BaseResultMap")
    @Select("<script>select c.* from tb_coupons c" +
            "<where> 1=1 " +
            "<if test=\"coupons.id != null\"> AND c.id = #{coupons.id} </if>" +
            "<if test=\"coupons.name != null and coupons.name !=''\"> AND c.name like '%${coupons.name}%' </if>" +
            "<if test=\"coupons.preferentialType != null\"> AND c.preferential_type = #{coupons.preferentialType} </if>" +
            "<if test=\"coupons.discountAmount != null\"> AND c.discount_amount = #{coupons.discountAmount} </if>" +
            "<if test=\"coupons.reducedPrice != null\"> AND c.reduced_price = #{coupons.reducedPrice} </if>" +
            "<if test=\"coupons.description != null and Coupons.description !=''\"> AND c.description = #{coupons.description} </if>" +
            "<if test=\"coupons.validType != null\"> AND c.valid_type = #{coupons.validType} </if>" +
            "<if test=\"coupons.validDays != null\"> AND c.valid_days = #{coupons.validDays} </if>" +
            "<if test=\"coupons.isDelete != null\"> AND c.is_delete = #{coupons.isDelete} </if>" +
            "<if test=\"coupons.source != null\"> AND c.source = #{coupons.source} </if>" +
            "<if test=\"coupons.startCreateTime != null\"> AND DATE_FORMAT(c.create_time, '%Y/%m/%d') &gt;= #{coupons.startCreateTime} </if>" +
            "<if test=\"coupons.endCreateTime != null\"> AND DATE_FORMAT(c.create_time, '%Y/%m/%d') &lt;= #{coupons.endCreateTime} </if>" +
            "</where> order by c.id asc" +
            "</script>")
    Page<Coupons> getListByPage(@Param("page") Page page, @Param("coupons") Coupons coupons);

    @ResultMap("CustomResultMap")
    @Select("<script>SELECT c.*, " +
            "(SELECT COUNT(*) FROM tb_coupons_member_relation WHERE coupons_id = c.id) as gaveCount, " +
            "(SELECT COUNT(*) FROM tb_coupons_member_relation WHERE coupons_id = c.id AND is_used = 1) as usedCount " +
            "FROM tb_coupons c " +
            "<where> 1=1 " +
            "<if test=\"coupons.id != null\"> AND c.id = #{coupons.id} </if>" +
            "<if test=\"coupons.name != null and coupons.name !=''\"> AND c.name like '%${coupons.name}%' </if>" +
            "<if test=\"coupons.preferentialType != null\"> AND c.preferential_type = #{coupons.preferentialType} </if>" +
            "<if test=\"coupons.discountAmount != null\"> AND c.discount_amount = #{coupons.discountAmount} </if>" +
            "<if test=\"coupons.reducedPrice != null\"> AND c.reduced_price = #{coupons.reducedPrice} </if>" +
            "<if test=\"coupons.description != null and Coupons.description !=''\"> AND c.description = #{coupons.description} </if>" +
            "<if test=\"coupons.validType != null\"> AND c.valid_type = #{coupons.validType} </if>" +
            "<if test=\"coupons.validDays != null\"> AND c.valid_days = #{coupons.validDays} </if>" +
            "<if test=\"coupons.isDelete != null\"> AND c.is_delete = #{coupons.isDelete} </if>" +
            "<if test=\"coupons.source != null\"> AND c.source = #{coupons.source} </if>" +
            "<if test=\"coupons.startCreateTime != null\"> AND DATE_FORMAT(c.create_time, '%Y/%m/%d') &gt;= #{coupons.startCreateTime} </if>" +
            "<if test=\"coupons.endCreateTime != null\"> AND DATE_FORMAT(c.create_time, '%Y/%m/%d') &lt;= #{coupons.endCreateTime} </if>" +
            "</where> order by c.id asc" +
            "</script>")
    Page<Map<String, Object>> getMapListByPage(@Param("page") Page page, @Param("coupons") Coupons coupons);

    @ResultMap("CustomResultMap")
    @Select("<script>SELECT c.*, " +
            "(SELECT COUNT(*) FROM tb_coupons_member_relation WHERE coupons_id = c.id) as gaveCount, " +
            "(SELECT COUNT(*) FROM tb_coupons_member_relation WHERE coupons_id = c.id AND is_used = 1) as usedCount " +
            "FROM tb_coupons c " +
            "LEFT JOIN tb_coupons_shop_relation csr on csr.coupons_id = c.id" +
            "<where> 1=1 " +
            "<if test=\"couponsDto.id != null\"> AND c.id = #{couponsDto.id} </if>" +
            "<if test=\"couponsDto.name != null and couponsDto.name !=''\"> AND c.name like '%${couponsDto.name}%' </if>" +
            "<if test=\"couponsDto.preferentialType != null\"> AND c.preferential_type = #{couponsDto.preferentialType} </if>" +
            "<if test=\"couponsDto.discountAmount != null\"> AND c.discount_amount = #{couponsDto.discountAmount} </if>" +
            "<if test=\"couponsDto.reducedPrice != null\"> AND c.reduced_price = #{couponsDto.reducedPrice} </if>" +
            "<if test=\"couponsDto.description != null and Coupons.description !=''\"> AND c.description = #{couponsDto.description} </if>" +
            "<if test=\"couponsDto.validType != null\"> AND c.valid_type = #{couponsDto.validType} </if>" +
            "<if test=\"couponsDto.validDays != null\"> AND c.valid_days = #{couponsDto.validDays} </if>" +
            "<if test=\"couponsDto.isDelete != null\"> AND c.is_delete = #{couponsDto.isDelete} </if>" +
            "<if test=\"couponsDto.source != null\"> AND c.source = #{couponsDto.source} </if>" +
            "<if test=\"couponsDto.shopId != null\"> AND csr.shop_id = #{couponsDto.shopId} </if>" +
            "</where> order by c.id asc" +
            "</script>")
    Page<Map<String, Object>> getListJoinCouponsShopRelationByPage(@Param("page") Page page, @Param("couponsDto") CouponsDto couponsDto);
}