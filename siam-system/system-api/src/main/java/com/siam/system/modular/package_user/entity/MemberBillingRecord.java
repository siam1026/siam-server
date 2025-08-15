package com.siam.system.modular.package_user.entity;

import com.baomidou.mybatisplus.annotation.IdType; import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户账单记录表
 *
 * @author 暹罗
 */
@Data
@TableName("tb_member_billing_record")
public class MemberBillingRecord {

    /**
     * 账单类型 账单类型 1=积分兑换商品 2=下单奖励积分 3=新用户注册赠送积分 4=会员充值奖励余额 5=邀请新用户注册获得奖励金额 6=邀请新用户注册奖励金额提现 7=邀请新用户注册奖励金额提现失败退回 8=一级邀请人佣金奖励 9=二级邀请人佣金奖励 10=下单用户佣金奖励 11=新用户注册赠送奖励金额 12=订单使用余额支付 13=一分钟内取消订单-余额退回 14=用户申请退款-余额退回 15=积分商城订单使用积分支付 16=未发货订单申请退款-积分退回 17=已发货订单申请退款-积分退回 18=会员充值原价金额同等转入余额 19=订单退款-下单奖励积分退回 20=订单退款-一级邀请人佣金奖励退回 21=订单退款-二级邀请人佣金奖励退回 22=订单退款-下单用户佣金奖励退回
     */
    public static final Integer TYPE_POINTS_EXCHANGE_GOODS = 1;
    public static final Integer TYPE_ORDER_REWARD_POINTS = 2;
    public static final Integer TYPE_REGISTER_REWARD_POINTS = 3;
    public static final Integer TYPE_VIP_RECHARGE_REWARD_BALANCE = 4;
    public static final Integer TYPE_INVITE_REGISTER_REWARD_INVITE_REWARD_AMOUNT = 5;
    public static final Integer TYPE_INVITE_REWARD_AMOUNT_WITHDRAW = 6;
    public static final Integer TYPE_INVITE_REWARD_AMOUNT_WITHDRAW_FAIL_RETURN = 7;
    public static final Integer TYPE_FIRST_LEVEL_INVITER_COMMISSION = 8;
    public static final Integer TYPE_SECOND_LEVEL_INVITER_COMMISSION = 9;
    public static final Integer TYPE_OWN_COMMISSION = 10;
    public static final Integer TYPE_REGISTER_REWARD_INVITE_REWARD_AMOUNT = 11;
    public static final Integer TYPE_ORDER_PAYMENT_BY_BALANCE = 12;
    public static final Integer TYPE_ORDER_CANCEL_RETURN_BALANCE = 13;
    public static final Integer TYPE_ORDER_REFUND_RETURN_BALANCE = 14;
    public static final Integer TYPE_POINTSMALL_ORDER_PAYMENT_BY_POINTS = 15;
    public static final Integer TYPE_POINTSMALL_ORDER_UNDELIVERED_REFUND_RETURN_POINTS = 16;
    public static final Integer TYPE_POINTSMALL_ORDER_DELIVERED_REFUND_RETURN_POINTS = 17;
    public static final Integer TYPE_VIP_RECHARGE_PRICE_SWITCH_TO_BALANCE = 18;
    public static final Integer TYPE_ORDER_REWARD_POINTS_RETURN = 19;
    public static final Integer TYPE_FIRST_LEVEL_INVITER_COMMISSION_RETURN = 20;
    public static final Integer TYPE_SECOND_LEVEL_INVITER_COMMISSION_RETURN = 21;
    public static final Integer TYPE_OWN_COMMISSION_RETURN = 22;

    /**
     * 货币类型 1=积分 2=余额 3=邀请新用户注册奖励金额 4=未到账-积分 5=未到账-邀请新用户注册奖励金额
     */
    public static final Integer COIN_TYPE_POINTS = 1;
    public static final Integer COIN_TYPE_BALANCE = 2;
    public static final Integer COIN_TYPE_INVITE_REWARD_AMOUNT = 3;
    public static final Integer COIN_TYPE_UNRECEIVED_POINTS = 4;
    public static final Integer COIN_TYPE_UNRECEIVED_INVITE_REWARD_AMOUNT = 5;

    /**
     * 操作类型 1-加，2-减
     */
    public static final Integer OPERATE_TYPE_ADD = 1;
    public static final Integer OPERATE_TYPE_SUB = 2;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer memberId;

    private Integer type;

    private Integer operateType;

    private Integer coinType;

    private BigDecimal number;

    private BigDecimal serviceFee;

    private String message;

    private Integer orderId;

    private Long pointsMallOrderId;

    private Boolean isReturn;

    private Boolean isSettled;

    private Date createTime;
}