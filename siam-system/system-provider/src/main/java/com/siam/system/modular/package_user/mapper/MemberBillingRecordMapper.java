package com.siam.system.modular.package_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.entity.MemberBillingRecord;
import com.siam.system.modular.package_user.model.dto.MemberBillingRecordDto;
import com.siam.system.modular.package_user.model.example.MemberBillingRecordExample;
import com.siam.system.modular.package_user.model.param.MemberBillingRecordParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MemberBillingRecordMapper extends BaseMapper<MemberBillingRecord> {
    int countByExample(MemberBillingRecordExample example);

    int deleteByExample(MemberBillingRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(MemberBillingRecord record);

    List<MemberBillingRecord> selectByExample(MemberBillingRecordExample example);

    MemberBillingRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberBillingRecord record, @Param("example") MemberBillingRecordExample example);

    int updateByExample(@Param("record") MemberBillingRecord record, @Param("example") MemberBillingRecordExample example);

    int updateByPrimaryKeySelective(MemberBillingRecord record);

    int updateByPrimaryKey(MemberBillingRecord record);

    @ResultMap("BaseResultMap")
    @Select("<script>select m.* from tb_member_billing_record m" +
            "<where> 1=1 " +
            "<if test=\"memberBillingRecord.id != null\"> AND m.id = #{memberBillingRecord.id} </if>" +
            "<if test=\"memberBillingRecord.memberId != null\"> AND m.member_id = #{memberBillingRecord.memberId} </if>" +
            "<if test=\"memberBillingRecord.type != null\"> AND m.type = #{memberBillingRecord.type} </if>" +
            "<if test=\"memberBillingRecord.operateType != null\"> AND m.operate_type = #{memberBillingRecord.operateType} </if>" +
            "<if test=\"memberBillingRecord.coinType != null\"> AND m.coin_type = #{memberBillingRecord.coinType} </if>" +
            "<if test=\"memberBillingRecord.number != null\"> AND m.number = #{memberBillingRecord.number} </if>" +
            "<if test=\"memberBillingRecord.message != null and memberBillingRecord.message != ''\"> AND m.message like '%${memberBillingRecord.message}' </if>" +
            "<if test=\"memberBillingRecord.isReturn != null\"> AND m.is_return = #{memberBillingRecord.isReturn} </if>" +
            "<if test=\"memberBillingRecord.isSettled != null\"> AND m.is_settled = #{memberBillingRecord.isSettled} </if>" +
            "</where> order by m.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("memberBillingRecord") MemberBillingRecordParam memberBillingRecord);

    @Select("<script>select IFNULL(sum(m.number), 0) from tb_member_billing_record as m "+
            "<where> 1=1 and (m.create_time between #{startTime} and #{endTime})" +
            "<if test=\"memberBillingRecordDto.memberId != null\"> AND m.member_id = #{memberBillingRecordDto.memberId} </if>" +
            "<if test=\"memberBillingRecordDto.typeList != null\"> and type in <foreach collection=\"memberBillingRecordDto.typeList\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach> </if>" +
            "<if test=\"memberBillingRecordDto.operateType != null\"> AND m.operate_type = #{memberBillingRecordDto.operateType} </if>" +
            "<if test=\"memberBillingRecordDto.coinType != null\"> AND m.coin_type = #{memberBillingRecordDto.coinType} </if>" +
            "</where>"+
            "</script>")
    BigDecimal selectSumNumber(@Param("memberBillingRecordDto") MemberBillingRecordDto memberBillingRecordDto, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    //外卖系统-可结算奖励
    @ResultMap("BaseResultMap")
    @Select("<script>select m.* from tb_member_billing_record m " +
            "left join tb_order o on m.order_id = o.id " +
            "<where> 1=1 and o.status in (6) and type in (2, 8, 9, 10) and m.operate_type = 1 and m.coin_type in (4, 5) and order_id is not null " +
            "and is_return = 0 and is_settled = 0 and <![CDATA[ TIMESTAMPDIFF(HOUR, m.create_time, now()) > 7*24 ]]>" +
            "</where>" +
            "</script>")
    List<MemberBillingRecord> getListBySettledReward(@Param("memberBillingRecord") MemberBillingRecord memberBillingRecord);

    //积分商城-可结算奖励
    @ResultMap("BaseResultMap")
    @Select("<script>select m.* from tb_member_billing_record m " +
            "left join tb_points_mall_order pmo on m.points_mall_order_id = pmo.id " +
            "<where> 1=1 and pmo.status in (6) and type in (2, 8, 9, 10) and m.operate_type = 1 and m.coin_type in (4, 5) and points_mall_order_id is not null " +
            "and is_return = 0 and is_settled = 0 and <![CDATA[ TIMESTAMPDIFF(HOUR, m.create_time, now()) > 25*24 ]]>" +
            "</where>" +
            "</script>")
    List<MemberBillingRecord> getListBySettledRewardPointsMall(@Param("memberBillingRecord") MemberBillingRecord memberBillingRecord);
}