package com.siam.system.modular.package_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.entity.MerchantBillingRecord;
import com.siam.system.modular.package_user.model.example.MerchantBillingRecordExample;
import com.siam.system.modular.package_user.model.param.MerchantBillingRecordParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface MerchantBillingRecordMapper extends BaseMapper<MerchantBillingRecord> {
    int countByExample(MerchantBillingRecordExample example);

    int deleteByExample(MerchantBillingRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(MerchantBillingRecord record);

    List<MerchantBillingRecord> selectByExample(MerchantBillingRecordExample example);

    MerchantBillingRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MerchantBillingRecord record, @Param("example") MerchantBillingRecordExample example);

    int updateByExample(@Param("record") MerchantBillingRecord record, @Param("example") MerchantBillingRecordExample example);

    int updateByPrimaryKeySelective(MerchantBillingRecord record);

    int updateByPrimaryKey(MerchantBillingRecord record);

    @ResultMap("BaseResultMap")
    @Select("<script>select mbr.* from tb_merchant_billing_record mbr" +
            "<where> 1=1 " +
            "<if test=\"merchantBillingRecord.id != null\"> AND mbr.id = #{merchantBillingRecord.id} </if>" +
            "<if test=\"merchantBillingRecord.merchantId != null\"> AND mbr.merchant_id = #{merchantBillingRecord.merchantId} </if>" +
            "<if test=\"merchantBillingRecord.type != null\"> AND mbr.type = #{merchantBillingRecord.type} </if>" +
            "<if test=\"merchantBillingRecord.operateType != null\"> AND mbr.operate_type = #{merchantBillingRecord.operateType} </if>" +
            "<if test=\"merchantBillingRecord.coinType != null\"> AND mbr.coin_type = #{merchantBillingRecord.coinType} </if>" +
            "<if test=\"merchantBillingRecord.number != null\"> AND mbr.number = #{merchantBillingRecord.number} </if>" +
            "<if test=\"merchantBillingRecord.message != null and merchantBillingRecord.message != ''\"> AND mbr.message like '%${merchantBillingRecord.message}%' </if>" +
            "<if test=\"merchantBillingRecord.startCreateTime != null\"> AND DATE_FORMAT(mbr.create_time, '%Y/%m/%d') &gt;= #{merchantBillingRecord.startCreateTime} </if>" +
            "<if test=\"merchantBillingRecord.endCreateTime != null\"> AND DATE_FORMAT(mbr.create_time, '%Y/%m/%d') &lt;= #{merchantBillingRecord.endCreateTime} </if>" +
            "</where> order by mbr.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("merchantBillingRecord") MerchantBillingRecordParam merchantBillingRecord);

    @ResultMap("CustomResultMap")
    @Select("<script>select mbr.*, s.name as shopName, m.real_name as realName from tb_merchant_billing_record mbr " +
            "left join tb_shop s on s.merchant_id = mbr.merchant_id " +
            "left join tb_merchant m on m.id = mbr.merchant_id " +
            "<where> 1=1 " +
            "<if test=\"merchantBillingRecord.id != null\"> AND mbr.id = #{merchantBillingRecord.id} </if>" +
            "<if test=\"merchantBillingRecord.merchantId != null\"> AND mbr.merchant_id = #{merchantBillingRecord.merchantId} </if>" +
            "<if test=\"merchantBillingRecord.type != null\"> AND mbr.type = #{merchantBillingRecord.type} </if>" +
            "<if test=\"merchantBillingRecord.operateType != null\"> AND mbr.operate_type = #{merchantBillingRecord.operateType} </if>" +
            "<if test=\"merchantBillingRecord.coinType != null\"> AND mbr.coin_type = #{merchantBillingRecord.coinType} </if>" +
            "<if test=\"merchantBillingRecord.number != null\"> AND mbr.number = #{merchantBillingRecord.number} </if>" +
            "<if test=\"merchantBillingRecord.message != null and merchantBillingRecord.message != ''\"> AND mbr.message like '%${merchantBillingRecord.message}%' </if>" +
            "<if test=\"merchantBillingRecord.startCreateTime != null\"> AND DATE_FORMAT(mbr.create_time, '%Y/%m/%d') &gt;= #{merchantBillingRecord.startCreateTime} </if>" +
            "<if test=\"merchantBillingRecord.endCreateTime != null\"> AND DATE_FORMAT(mbr.create_time, '%Y/%m/%d') &lt;= #{merchantBillingRecord.endCreateTime} </if>" +
            "</where> order by mbr.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinShop(@Param("page") Page page, @Param("merchantBillingRecord") MerchantBillingRecordParam merchantBillingRecord);

    @Select("<script>select IFNULL(sum(mbr.number), 0) from tb_merchant_billing_record mbr " +
            "<where> 1=1 and operate_type = 1 " +
            "<if test=\"merchantBillingRecord.id != null\"> AND mbr.id = #{merchantBillingRecord.id} </if>" +
            "<if test=\"merchantBillingRecord.merchantId != null\"> AND mbr.merchant_id = #{merchantBillingRecord.merchantId} </if>" +
            "<if test=\"merchantBillingRecord.type != null\"> AND mbr.type = #{merchantBillingRecord.type} </if>" +
            "<if test=\"merchantBillingRecord.operateType != null\"> AND mbr.operate_type = #{merchantBillingRecord.operateType} </if>" +
            "<if test=\"merchantBillingRecord.coinType != null\"> AND mbr.coin_type = #{merchantBillingRecord.coinType} </if>" +
            "<if test=\"merchantBillingRecord.number != null\"> AND mbr.number = #{merchantBillingRecord.number} </if>" +
            "<if test=\"merchantBillingRecord.message != null and merchantBillingRecord.message != ''\"> AND mbr.message like '%${merchantBillingRecord.message}%' </if>" +
            "<if test=\"merchantBillingRecord.startCreateTime != null\"> AND DATE_FORMAT(mbr.create_time, '%Y/%m/%d') &gt;= #{merchantBillingRecord.startCreateTime} </if>" +
            "<if test=\"merchantBillingRecord.endCreateTime != null\"> AND DATE_FORMAT(mbr.create_time, '%Y/%m/%d') &lt;= #{merchantBillingRecord.endCreateTime} </if>" +
            "</where> order by mbr.id desc" +
            "</script>")
    BigDecimal statisticalAmountByIncome(@Param("merchantBillingRecord") MerchantBillingRecordParam merchantBillingRecord);

    @Select("<script>select IFNULL(sum(mbr.number), 0) from tb_merchant_billing_record mbr " +
            "<where> 1=1 and operate_type = 2 " +
            "<if test=\"merchantBillingRecord.id != null\"> AND mbr.id = #{merchantBillingRecord.id} </if>" +
            "<if test=\"merchantBillingRecord.merchantId != null\"> AND mbr.merchant_id = #{merchantBillingRecord.merchantId} </if>" +
            "<if test=\"merchantBillingRecord.type != null\"> AND mbr.type = #{merchantBillingRecord.type} </if>" +
            "<if test=\"merchantBillingRecord.operateType != null\"> AND mbr.operate_type = #{merchantBillingRecord.operateType} </if>" +
            "<if test=\"merchantBillingRecord.coinType != null\"> AND mbr.coin_type = #{merchantBillingRecord.coinType} </if>" +
            "<if test=\"merchantBillingRecord.number != null\"> AND mbr.number = #{merchantBillingRecord.number} </if>" +
            "<if test=\"merchantBillingRecord.message != null and merchantBillingRecord.message != ''\"> AND mbr.message like '%${merchantBillingRecord.message}%' </if>" +
            "<if test=\"merchantBillingRecord.startCreateTime != null\"> AND DATE_FORMAT(mbr.create_time, '%Y/%m/%d') &gt;= #{merchantBillingRecord.startCreateTime} </if>" +
            "<if test=\"merchantBillingRecord.endCreateTime != null\"> AND DATE_FORMAT(mbr.create_time, '%Y/%m/%d') &lt;= #{merchantBillingRecord.endCreateTime} </if>" +
            "</where> order by mbr.id desc" +
            "</script>")
    BigDecimal statisticalAmountByExpend(@Param("merchantBillingRecord") MerchantBillingRecordParam merchantBillingRecord);
}