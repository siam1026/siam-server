package com.siam.system.modular.package_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.entity.MemberWithdrawRecord;
import com.siam.system.modular.package_user.model.example.MemberWithdrawRecordExample;
import com.siam.system.modular.package_user.model.param.MemberWithdrawRecordParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface MemberWithdrawRecordMapper extends BaseMapper<MemberWithdrawRecord> {
    int countByExample(MemberWithdrawRecordExample example);

    int deleteByExample(MemberWithdrawRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(MemberWithdrawRecord record);

    List<MemberWithdrawRecord> selectByExample(MemberWithdrawRecordExample example);

    MemberWithdrawRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberWithdrawRecord record, @Param("example") MemberWithdrawRecordExample example);

    int updateByExample(@Param("record") MemberWithdrawRecord record, @Param("example") MemberWithdrawRecordExample example);

    int updateByPrimaryKeySelective(MemberWithdrawRecord record);

    int updateByPrimaryKey(MemberWithdrawRecord record);

    @ResultMap("BaseResultMap")
    @Select("<script>select mwa.* from tb_member_withdraw_record mwa" +
            "<where> 1=1 " +
            "<if test=\"memberWithdrawRecord.id != null\"> AND mwa.id = #{memberWithdrawRecord.id} </if>" +
            "<if test=\"memberWithdrawRecord.memberId != null\"> AND mwa.member_id = #{memberWithdrawRecord.memberId} </if>" +
            "<if test=\"memberWithdrawRecord.orderNo != null and memberWithdrawRecord.orderNo != ''\"> AND mwa.order_no like '%${memberWithdrawRecord.orderNo}' </if>" +
            "<if test=\"memberWithdrawRecord.coinType != null\"> AND mwa.coin_type = #{memberWithdrawRecord.coinType} </if>" +
            "<if test=\"memberWithdrawRecord.withdrawAmount != null\"> AND mwa.withdraw_amount = #{memberWithdrawRecord.withdrawAmount} </if>" +
            "<if test=\"memberWithdrawRecord.platformFee != null\"> AND mwa.platform_fee = #{memberWithdrawRecord.platformFee} </if>" +
            "<if test=\"memberWithdrawRecord.auditStatus != null and memberWithdrawRecord.auditStatus != -1\"> AND mwa.audit_status = #{memberWithdrawRecord.auditStatus} </if>" +
            "<if test=\"memberWithdrawRecord.auditStatus != null and memberWithdrawRecord.auditStatus == -1\"> AND mwa.audit_status in(1, 2, 3) </if>" +
            "<if test=\"memberWithdrawRecord.auditReason != null and memberWithdrawRecord.auditReason != ''\"> AND mwa.audit_reason like '%${memberWithdrawRecord.auditReason}' </if>" +
            "<if test=\"memberWithdrawRecord.startCreateTime != null\"> AND DATE_FORMAT(mwa.create_time, '%Y/%m/%d') &gt;= #{memberWithdrawRecord.startCreateTime} </if>" +
            "<if test=\"memberWithdrawRecord.endCreateTime != null\"> AND DATE_FORMAT(mwa.create_time, '%Y/%m/%d') &lt;= #{memberWithdrawRecord.endCreateTime} </if>" +
            "</where> order by mwa.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("memberWithdrawRecord") MemberWithdrawRecordParam memberWithdrawRecord);

    @ResultMap("CustomResultMap")
    @Select("<script>select mwa.*, m.username, m.mobile from tb_member_withdraw_record mwa " +
            "left join tb_member m on m.id = mwa.member_id " +
            "<where> 1=1 " +
            "<if test=\"memberWithdrawRecord.id != null\"> AND mwa.id = #{memberWithdrawRecord.id} </if>" +
            "<if test=\"memberWithdrawRecord.memberId != null\"> AND mwa.member_id = #{memberWithdrawRecord.memberId} </if>" +
            "<if test=\"memberWithdrawRecord.orderNo != null and memberWithdrawRecord.orderNo != ''\"> AND mwa.order_no like '%${memberWithdrawRecord.orderNo}' </if>" +
            "<if test=\"memberWithdrawRecord.coinType != null\"> AND mwa.coin_type = #{memberWithdrawRecord.coinType} </if>" +
            "<if test=\"memberWithdrawRecord.withdrawAmount != null\"> AND mwa.withdraw_amount = #{memberWithdrawRecord.withdrawAmount} </if>" +
            "<if test=\"memberWithdrawRecord.platformFee != null\"> AND mwa.platform_fee = #{memberWithdrawRecord.platformFee} </if>" +
            "<if test=\"memberWithdrawRecord.auditStatus != null and memberWithdrawRecord.auditStatus != -1\"> AND mwa.audit_status = #{memberWithdrawRecord.auditStatus} </if>" +
            "<if test=\"memberWithdrawRecord.auditStatus != null and memberWithdrawRecord.auditStatus == -1\"> AND mwa.audit_status in(1, 2, 3) </if>" +
            "<if test=\"memberWithdrawRecord.auditReason != null and memberWithdrawRecord.auditReason != ''\"> AND mwa.audit_reason like '%${memberWithdrawRecord.auditReason}' </if>" +
            "<if test=\"memberWithdrawRecord.startCreateTime != null\"> AND DATE_FORMAT(mwa.create_time, '%Y/%m/%d') &gt;= #{memberWithdrawRecord.startCreateTime} </if>" +
            "<if test=\"memberWithdrawRecord.endCreateTime != null\"> AND DATE_FORMAT(mwa.create_time, '%Y/%m/%d') &lt;= #{memberWithdrawRecord.endCreateTime} </if>" +
            "</where> order by mwa.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinMember(@Param("page") Page page, @Param("memberWithdrawRecord") MemberWithdrawRecordParam memberWithdrawRecord);

    @Select("<script>select IFNULL(sum(mwa.actual_amount), 0) from tb_member_withdraw_record mwa " +
            "<where> 1=1 and audit_status = 2 " +
            "<if test=\"memberWithdrawRecord.id != null\"> AND mwa.id = #{memberWithdrawRecord.id} </if>" +
            "<if test=\"memberWithdrawRecord.memberId != null\"> AND mwa.member_id = #{memberWithdrawRecord.memberId} </if>" +
            "<if test=\"memberWithdrawRecord.orderNo != null and memberWithdrawRecord.orderNo != ''\"> AND mwa.order_no like '%${memberWithdrawRecord.orderNo}' </if>" +
            "<if test=\"memberWithdrawRecord.coinType != null\"> AND mwa.coin_type = #{memberWithdrawRecord.coinType} </if>" +
            "<if test=\"memberWithdrawRecord.withdrawAmount != null\"> AND mwa.withdraw_amount = #{memberWithdrawRecord.withdrawAmount} </if>" +
            "<if test=\"memberWithdrawRecord.platformFee != null\"> AND mwa.platform_fee = #{memberWithdrawRecord.platformFee} </if>" +
            "<if test=\"memberWithdrawRecord.auditStatus != null and memberWithdrawRecord.auditStatus != -1\"> AND mwa.audit_status = #{memberWithdrawRecord.auditStatus} </if>" +
            "<if test=\"memberWithdrawRecord.auditStatus != null and memberWithdrawRecord.auditStatus == -1\"> AND mwa.audit_status in(1, 2, 3) </if>" +
            "<if test=\"memberWithdrawRecord.auditReason != null and memberWithdrawRecord.auditReason != ''\"> AND mwa.audit_reason like '%${memberWithdrawRecord.auditReason}' </if>" +
            "<if test=\"memberWithdrawRecord.startCreateTime != null\"> AND DATE_FORMAT(mwa.create_time, '%Y/%m/%d') &gt;= #{memberWithdrawRecord.startCreateTime} </if>" +
            "<if test=\"memberWithdrawRecord.endCreateTime != null\"> AND DATE_FORMAT(mwa.create_time, '%Y/%m/%d') &lt;= #{memberWithdrawRecord.endCreateTime} </if>" +
            "</where> order by mwa.id desc" +
            "</script>")
    BigDecimal statisticalAmountByWithdrawalSuccessful(@Param("memberWithdrawRecord") MemberWithdrawRecordParam memberWithdrawRecord);

    @ResultMap("BaseResultMap")
    @Select("select * from tb_member_withdraw_record where audit_status = 1 and coin_type = 1 and withdraw_amount < #{memberWithdrawAuditThreshold}")
    List<MemberWithdrawRecord> selectByNeedAutoPayment(@Param("memberWithdrawAuditThreshold") BigDecimal memberWithdrawAuditThreshold);
}