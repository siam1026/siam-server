package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface ShopMapper extends BaseMapper<Shop> {

    @ResultMap("BaseResultMap")
    @Select("<script>select s.* from tb_shop s" +
            "<where> 1=1 " +
            "<if test=\"shop.id != null\"> AND s.id = #{shop.id} </if>" +
            "<if test=\"shop.code != null and shop.code !=''\"> AND s.code like '%${shop.code}%' </if>" +
            "<if test=\"shop.name != null and shop.name !=''\"> AND s.name like '%${shop.name}%' </if>" +
            "<if test=\"shop.province != null and shop.province !=''\"> AND s.province like '%${shop.province}%' </if>" +
            "<if test=\"shop.city != null and shop.city !=''\"> AND s.city like '%${shop.city}%' </if>" +
            "<if test=\"shop.area != null and shop.area !=''\"> AND s.area like '%${shop.area}%' </if>" +
            "<if test=\"shop.street != null and shop.street !=''\"> AND s.street like '%${shop.street}%' </if>" +
            "<if test=\"shop.auditStatus != null and shop.auditStatus != -1\"> AND s.audit_status = #{shop.auditStatus} </if>" +
            "<if test=\"shop.auditStatus != null and shop.auditStatus == -1\"> AND (s.audit_status = 1 or s.audit_status = 3) </if>" +
            "<if test=\"shop.status != null\"> AND s.status = #{shop.status} </if>" +
            "<if test=\"shop.startCreateTime != null\"> AND DATE_FORMAT(s.create_time, '%Y/%m/%d') &gt;= #{shop.startCreateTime} </if>" +
            "<if test=\"shop.endCreateTime != null\"> AND DATE_FORMAT(s.create_time, '%Y/%m/%d') &lt;= #{shop.endCreateTime} </if>" +
            "</where> order by s.id desc" +
            "</script>")
    Page<Shop> getListByPage(@Param("page") Page page, @Param("shop") Shop shop);

    @ResultMap("CustomResultMap")
    @Select("<script>select s.*, m.mobile as merchantMobile, mb.vip_no as vipNo, mb.mobile as memberMobile, if(mb.wx_public_platform_open_id is not null, true, false) as isConcernWxPublicPlatform " +
            "from tb_shop s " +
            "left join tb_merchant m on m.id = s.merchant_id " +
            "left join tb_member mb on mb.id = m.member_id " +
            "<where> 1=1 " +
            "<if test=\"shop.id != null\"> AND s.id = #{shop.id} </if>" +
            "<if test=\"shop.code != null and shop.code !=''\"> AND s.code like '%${shop.code}%' </if>" +
            "<if test=\"shop.name != null and shop.name !=''\"> AND s.name like '%${shop.name}%' </if>" +
            "<if test=\"shop.province != null and shop.province !=''\"> AND s.province like '%${shop.province}%' </if>" +
            "<if test=\"shop.city != null and shop.city !=''\"> AND s.city like '%${shop.city}%' </if>" +
            "<if test=\"shop.area != null and shop.area !=''\"> AND s.area like '%${shop.area}%' </if>" +
            "<if test=\"shop.street != null and shop.street !=''\"> AND s.street like '%${shop.street}%' </if>" +
            "<if test=\"shop.auditStatus != null and shop.auditStatus != -1\"> AND s.audit_status = #{shop.auditStatus} </if>" +
            "<if test=\"shop.auditStatus != null and shop.auditStatus == -1\"> AND (s.audit_status = 1 or s.audit_status = 3) </if>" +
            "<if test=\"shop.status != null\"> AND s.status = #{shop.status} </if>" +
            "<if test=\"shop.startCreateTime != null\"> AND DATE_FORMAT(s.create_time, '%Y/%m/%d') &gt;= #{shop.startCreateTime} </if>" +
            "<if test=\"shop.endCreateTime != null\"> AND DATE_FORMAT(s.create_time, '%Y/%m/%d') &lt;= #{shop.endCreateTime} </if>" +
            "</where> order by s.id desc" +
            "</script>")
    Page<Map<String, Object>> getMapListByPageJoinMerchant(@Param("page") Page page, @Param("shop") Shop shop);

    @ResultMap("CustomResultMap")
    @Select("<script>select s.* from tb_shop s" +
            "<where> 1=1 " +
            "<if test=\"shop.id != null\"> AND s.id = #{shop.id} </if>" +
            "<if test=\"shop.code != null and shop.code !=''\"> AND s.code like '%${shop.code}%' </if>" +
            "<if test=\"shop.name != null and shop.name !=''\"> AND s.name like '%${shop.name}%' </if>" +
            "<if test=\"shop.province != null and shop.province !=''\"> AND s.province like '%${shop.province}%' </if>" +
            "<if test=\"shop.city != null and shop.city !=''\"> AND s.city like '%${shop.city}%' </if>" +
            "<if test=\"shop.area != null and shop.area !=''\"> AND s.area like '%${shop.area}%' </if>" +
            "<if test=\"shop.street != null and shop.street !=''\"> AND s.street like '%${shop.street}%' </if>" +
            "<if test=\"shop.auditStatus != null and shop.auditStatus != -1\"> AND s.audit_status = #{shop.auditStatus} </if>" +
            "<if test=\"shop.auditStatus != null and shop.auditStatus == -1\"> AND (s.audit_status = 1 or s.audit_status = 3) </if>" +
            "<if test=\"shop.status != null\"> AND s.status = #{shop.status} </if>" +
            "<if test=\"shop.shopIdList != null\">" +
            " and s.id in <foreach collection=\"shop.shopIdList\" index=\"index\" item=\"shopId\" open=\"(\" separator=\",\" close=\")\">" +
            "#{shopId}" +
            "</foreach>" +
            "</if>" +
            "<if test=\"shop.startCreateTime != null\"> AND DATE_FORMAT(s.create_time, '%Y/%m/%d') &gt;= #{shop.startCreateTime} </if>" +
            "<if test=\"shop.endCreateTime != null\"> AND DATE_FORMAT(s.create_time, '%Y/%m/%d') &lt;= #{shop.endCreateTime} </if>" +
            "</where> order by s.id desc" +
            "</script>")
    Page<Map<String, Object>> getMapListByPage(@Param("page") Page page, @Param("shop") Shop shop);

    @ResultMap("BaseResultMap")
    @Select("select s.* from tb_shop s where s.name = #{name}")
    Shop selectByName(@Param("name") String name);

    @ResultMap("BaseResultMap")
    @Select("select s.* from tb_shop s where s.merchant_id = #{merchantId} limit 1")
    Shop selectByMerchantId(Integer merchantId);

    @ResultMap("BaseResultMap")
    @Select("select g.* from tb_shop as g,tb_coupons_shop_relation as cgr where g.id=cgr.shop_id and cgr.coupons_id=#{couponsId}")
    List<Shop> getListByCouponsId(Integer couponsId);

    Page<Shop> selectByDistance(@Param("page") Page page, @Param("paramCondition") Shop shop);

    Page<Map<String, Object>> selectMapByDistance(@Param("page") Page page, @Param("paramCondition") Shop shop);
}