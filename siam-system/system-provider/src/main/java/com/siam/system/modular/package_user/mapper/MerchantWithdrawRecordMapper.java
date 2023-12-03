package com.siam.system.modular.package_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.entity.MerchantWithdrawRecord;
import com.siam.system.modular.package_user.model.example.MerchantWithdrawRecordExample;
import com.siam.system.modular.package_user.model.param.MerchantWithdrawRecordParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface MerchantWithdrawRecordMapper extends BaseMapper<MerchantWithdrawRecord> {
    int countByExample(MerchantWithdrawRecordExample example);

    int deleteByExample(MerchantWithdrawRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(MerchantWithdrawRecord record);

    List<MerchantWithdrawRecord> selectByExample(MerchantWithdrawRecordExample example);

    MerchantWithdrawRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MerchantWithdrawRecord record, @Param("example") MerchantWithdrawRecordExample example);

    int updateByExample(@Param("record") MerchantWithdrawRecord record, @Param("example") MerchantWithdrawRecordExample example);

    int updateByPrimaryKeySelective(MerchantWithdrawRecord record);

    int updateByPrimaryKey(MerchantWithdrawRecord record);

    @ResultMap("BaseResultMap")
    @Select("<script>select mwa.* from tb_merchant_withdraw_record mwa" +
            "<where> 1=1 " +
            "<if test=\"merchantWithdrawRecord.id != null\"> AND mwa.id = #{merchantWithdrawRecord.id} </if>" +
            "<if test=\"merchantWithdrawRecord.merchantId != null\"> AND mwa.merchant_id = #{merchantWithdrawRecord.merchantId} </if>" +
            "<if test=\"merchantWithdrawRecord.withdrawAmount != null\"> AND mwa.withdraw_amount = #{merchantWithdrawRecord.withdrawAmount} </if>" +
            "<if test=\"merchantWithdrawRecord.platformFee != null\"> AND mwa.platform_fee = #{merchantWithdrawRecord.platformFee} </if>" +
            "<if test=\"merchantWithdrawRecord.actualAmount != null\"> AND mwa.actual_amount = #{merchantWithdrawRecord.actualAmount} </if>" +
            "<if test=\"merchantWithdrawRecord.auditStatus != null\"> AND mwa.audit_status = #{merchantWithdrawRecord.auditStatus} </if>" +
            "<if test=\"merchantWithdrawRecord.auditReason != null and merchantWithdrawRecord.auditReason != ''\"> AND mwa.audit_reason like '%${merchantWithdrawRecord.auditReason}' </if>" +
            "<if test=\"merchantWithdrawRecord.startCreateTime != null\"> AND DATE_FORMAT(mwa.create_time, '%Y/%m/%d') &gt;= #{merchantWithdrawRecord.startCreateTime} </if>" +
            "<if test=\"merchantWithdrawRecord.endCreateTime != null\"> AND DATE_FORMAT(mwa.create_time, '%Y/%m/%d') &lt;= #{merchantWithdrawRecord.endCreateTime} </if>" +
            "</where> order by mwa.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("merchantWithdrawRecord") MerchantWithdrawRecord merchantWithdrawRecord);

    @ResultMap("CustomResultMap")
    @Select("<script>select mwa.*, s.name as shopName, m.real_name as realName from tb_merchant_withdraw_record mwa " +
            "left join tb_shop s on s.merchant_id = mwa.merchant_id " +
            "left join tb_merchant m on m.id = mwa.merchant_id " +
            "<where> 1=1 " +
            "<if test=\"merchantWithdrawRecord.id != null\"> AND mwa.id = #{merchantWithdrawRecord.id} </if>" +
            "<if test=\"merchantWithdrawRecord.merchantId != null\"> AND mwa.merchant_id = #{merchantWithdrawRecord.merchantId} </if>" +
            "<if test=\"merchantWithdrawRecord.withdrawAmount != null\"> AND mwa.withdraw_amount = #{merchantWithdrawRecord.withdrawAmount} </if>" +
            "<if test=\"merchantWithdrawRecord.platformFee != null\"> AND mwa.platform_fee = #{merchantWithdrawRecord.platformFee} </if>" +
            "<if test=\"merchantWithdrawRecord.auditStatus != null and merchantWithdrawRecord.auditStatus != -1\"> AND mwa.audit_status = #{merchantWithdrawRecord.auditStatus} </if>" +
            "<if test=\"merchantWithdrawRecord.auditStatus != null and merchantWithdrawRecord.auditStatus == -1\"> AND (mwa.audit_status = 2 or mwa.audit_status = 3) </if>" +
            "<if test=\"merchantWithdrawRecord.auditReason != null and merchantWithdrawRecord.auditReason != ''\"> AND mwa.audit_reason like '%${merchantWithdrawRecord.auditReason}' </if>" +
            "<if test=\"merchantWithdrawRecord.startCreateTime != null\"> AND DATE_FORMAT(mwa.create_time, '%Y/%m/%d') &gt;= #{merchantWithdrawRecord.startCreateTime} </if>" +
            "<if test=\"merchantWithdrawRecord.endCreateTime != null\"> AND DATE_FORMAT(mwa.create_time, '%Y/%m/%d') &lt;= #{merchantWithdrawRecord.endCreateTime} </if>" +
            "</where> order by mwa.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinShop(@Param("page") Page page, @Param("merchantWithdrawRecord") MerchantWithdrawRecordParam merchantWithdrawRecord);

    @Select("<script>select IFNULL(sum(mwa.actual_amount), 0) from tb_merchant_withdraw_record mwa " +
            "<where> 1=1 and audit_status = 2 " +
            "<if test=\"merchantWithdrawRecord.id != null\"> AND mwa.id = #{merchantWithdrawRecord.id} </if>" +
            "<if test=\"merchantWithdrawRecord.merchantId != null\"> AND mwa.merchant_id = #{merchantWithdrawRecord.merchantId} </if>" +
            "<if test=\"merchantWithdrawRecord.withdrawAmount != null\"> AND mwa.withdraw_amount = #{merchantWithdrawRecord.withdrawAmount} </if>" +
            "<if test=\"merchantWithdrawRecord.platformFee != null\"> AND mwa.platform_fee = #{merchantWithdrawRecord.platformFee} </if>" +
            "<if test=\"merchantWithdrawRecord.auditStatus != null and merchantWithdrawRecord.auditStatus != -1\"> AND mwa.audit_status = #{merchantWithdrawRecord.auditStatus} </if>" +
            "<if test=\"merchantWithdrawRecord.auditStatus != null and merchantWithdrawRecord.auditStatus == -1\"> AND (mwa.audit_status = 2 or mwa.audit_status = 3) </if>" +
            "<if test=\"merchantWithdrawRecord.auditReason != null and merchantWithdrawRecord.auditReason != ''\"> AND mwa.audit_reason like '%${merchantWithdrawRecord.auditReason}' </if>" +
            "<if test=\"merchantWithdrawRecord.startCreateTime != null\"> AND DATE_FORMAT(mwa.create_time, '%Y/%m/%d') &gt;= #{merchantWithdrawRecord.startCreateTime} </if>" +
            "<if test=\"merchantWithdrawRecord.endCreateTime != null\"> AND DATE_FORMAT(mwa.create_time, '%Y/%m/%d') &lt;= #{merchantWithdrawRecord.endCreateTime} </if>" +
            "</where> order by mwa.id desc" +
            "</script>")
    BigDecimal statisticalAmountByWithdrawalSuccessful(@Param("merchantWithdrawRecord") MerchantWithdrawRecordParam merchantWithdrawRecord);
}