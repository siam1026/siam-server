package com.siam.system.modular.package_order.controller.member;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 平台支付实体对象
 */
@Data
public class PlatformPayDto {

    /**
     * 交易类型 1=订单付款 (2=会员充值) 3=自取订单改为配送 4=积分商城订单付款
     */
    @NotNull(message = "交易类型不能为空")
    @Range(min = 1, max = 4, message = "交易类型必须处于[1~4]范围内")
    private Integer type;

    /**
     * 商户单号(网站平台的订单号)
     */
    private String out_trade_no;

    /**
     * 付款金额
     */
    @NotNull(message = "付款金额不能为空")
    @DecimalMin(value = "0.01", message = "付款金额不允许小于等于0")
    private BigDecimal total_fee;

    /**
     * 收货地址id
     */
    private Integer deliveryAddressId;

    private String paymentPassword;
}