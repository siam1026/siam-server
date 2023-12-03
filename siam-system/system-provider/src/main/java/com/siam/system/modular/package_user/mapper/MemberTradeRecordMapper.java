package com.siam.system.modular.package_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.entity.MemberTradeRecord;
import com.siam.system.modular.package_user.model.example.MemberTradeRecordExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MemberTradeRecordMapper extends BaseMapper<MemberTradeRecord> {
    int countByExample(MemberTradeRecordExample example);

    int deleteByExample(MemberTradeRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(MemberTradeRecord record);

    List<MemberTradeRecord> selectByExample(MemberTradeRecordExample example);

    MemberTradeRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberTradeRecord record, @Param("example") MemberTradeRecordExample example);

    int updateByExample(@Param("record") MemberTradeRecord record, @Param("example") MemberTradeRecordExample example);

    int updateByPrimaryKeySelective(MemberTradeRecord record);

    int updateByPrimaryKey(MemberTradeRecord record);

    @ResultMap("BaseResultMap")
    @Select("<script>select mtr.* from tb_member_trade_record mtr" +
            "<where> 1=1 " +
            "<if test=\"memberTrade.id != null\"> AND mtr.id = #{memberTrade.id} </if>" +
            "<if test=\"memberTrade.memberId != null\"> AND mtr.member_id = #{memberTrade.memberId} </if>" +
            "<if test=\"memberTrade.tradeNo != null and memberTrade.tradeNo !=''\"> AND mtr.trade_no like '%${memberTrade.tradeNo}%' </if>" +
            "<if test=\"memberTrade.outTradeNo != null and memberTrade.outTradeNo !=''\"> AND mtr.out_trade_no like '%${memberTrade.outTradeNo}%' </if>" +
            "<if test=\"memberTrade.type != null\"> AND mtr.type = #{memberTrade.type} </if>" +
            "<if test=\"memberTrade.paymentMode != null\"> AND mtr.payment_mode = #{memberTrade.paymentMode} </if>" +
            "<if test=\"memberTrade.amount != null\"> AND mtr.amount = #{memberTrade.amount} </if>" +
            "<if test=\"memberTrade.status != null\"> AND mtr.status = #{memberTrade.status} </if>" +
            "</where> order by mtr.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("memberTrade") MemberTradeRecord memberTradeRecord);

    @ResultMap("BaseResultMap")
    @Select("select mtr.* from tb_member_trade_record mtr where mtr.out_trade_no = #{outTradeNo} order by create_time desc limit 1")
    MemberTradeRecord selectByOutTradeNo(String outTradeNo);

    @Select("<script>select IFNULL(sum(mtr.amount), 0) as amount from tb_member_trade_record as mtr " +
            "<where> 1=1 and mtr.type in (1, 2, 3, 6) and mtr.payment_mode in (1, 2) and status = 2 and (mtr.create_time between #{startTime} and #{endTime})" +
            "</where>"+
            "</script>")
    BigDecimal selectSumIncome(@Param("memberTrade") MemberTradeRecord memberTradeRecord, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Select("<script>select IFNULL(sum(mtr.amount), 0) as amount from tb_member_trade_record as mtr " +
            "<where> 1=1 and ((mtr.type in (4, 5) and mtr.payment_mode in (1, 2)) or (mtr.type in (1, 3, 6) and mtr.payment_mode in (3, 4))) and status = 2 and (mtr.create_time between #{startTime} and #{endTime})" +
            "</where>"+
            "</script>")
    BigDecimal selectSumExpense(@Param("memberTrade") MemberTradeRecord memberTradeRecord, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}