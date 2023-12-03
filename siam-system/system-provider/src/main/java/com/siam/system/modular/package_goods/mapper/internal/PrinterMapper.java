package com.siam.system.modular.package_goods.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.internal.Printer;
import com.siam.system.modular.package_goods.model.example.internal.PrinterExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface PrinterMapper extends BaseMapper<Printer> {
    int countByExample(PrinterExample example);

    int deleteByExample(PrinterExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Printer record);

    List<Printer> selectByExample(PrinterExample example);

    Printer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Printer record, @Param("example") PrinterExample example);

    int updateByExample(@Param("record") Printer record, @Param("example") PrinterExample example);

    int updateByPrimaryKeySelective(Printer record);

    int updateByPrimaryKey(Printer record);

    @ResultMap("BaseResultMap")
    @Select("<script>select c.* from tb_printer c" +
            "<where> 1=1 " +
            "<if test=\"printer.id != null\"> AND c.id = #{printer.id} </if>" +
            "<if test=\"printer.shopId != null\"> AND c.shop_id = #{printer.shopId} </if>" +
            "<if test=\"printer.name != null and printer.name !=''\"> AND c.name like '%${printer.name}%' </if>" +
            "<if test=\"printer.number != null and printer.number !=''\"> AND c.number like '%${printer.number}%' </if>" +
            "<if test=\"printer.identifyingCode != null and printer.identifyingCode !=''\"> AND c.identifying_code like '%${printer.identifyingCode}%' </if>" +
            "<if test=\"printer.isAutoPrint != null\"> AND c.is_auto_print = #{printer.isAutoPrint} </if>" +
            "<if test=\"printer.mobileCardNumber != null and printer.mobileCardNumber !=''\"> AND c.mobile_card_number like '%${printer.mobileCardNumber}%' </if>" +
            "<if test=\"printer.cloudRegistrationStatus != null and printer.cloudRegistrationStatus !=''\"> AND c.cloud_registration_status like '%${printer.cloudRegistrationStatus}%' </if>" +
            "<if test=\"printer.type != null\"> AND c.type = #{printer.type} </if>" +
            "<if test=\"printer.brand != null\"> AND c.brand = #{printer.brand} </if>" +
            "</where> order by c.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("printer") Printer printer);

    @ResultMap("CustomResultMap")
    @Select("<script>select c.*, s.name as shopName from tb_printer c " +
            "left join tb_shop as s on s.id = c.shop_id " +
            "<where> 1=1 " +
            "<if test=\"printer.id != null\"> AND c.id = #{printer.id} </if>" +
            "<if test=\"printer.shopId != null\"> AND c.shop_id = #{printer.shopId} </if>" +
            "<if test=\"printer.name != null and printer.name !=''\"> AND c.name like '%${printer.name}%' </if>" +
            "<if test=\"printer.number != null and printer.number !=''\"> AND c.number like '%${printer.number}%' </if>" +
            "<if test=\"printer.identifyingCode != null and printer.identifyingCode !=''\"> AND c.identifying_code like '%${printer.identifyingCode}%' </if>" +
            "<if test=\"printer.isAutoPrint != null\"> AND c.is_auto_print = #{printer.isAutoPrint} </if>" +
            "<if test=\"printer.mobileCardNumber != null and printer.mobileCardNumber !=''\"> AND c.mobile_card_number like '%${printer.mobileCardNumber}%' </if>" +
            "<if test=\"printer.cloudRegistrationStatus != null and printer.cloudRegistrationStatus !=''\"> AND c.cloud_registration_status like '%${printer.cloudRegistrationStatus}%' </if>" +
            "<if test=\"printer.type != null\"> AND c.type = #{printer.type} </if>" +
            "<if test=\"printer.brand != null\"> AND c.brand = #{printer.brand} </if>" +
            "<if test=\"printer.shopName != null and printer.shopName !=''\"> AND s.name like '%${printer.shopName}%' </if>" +
            "</where> order by c.shop_id asc, c.create_time asc" +
            "</script>")
    Page<Map<String, Object>> getListJoinShop(@Param("page") Page page, @Param("printer") Printer printer);
}