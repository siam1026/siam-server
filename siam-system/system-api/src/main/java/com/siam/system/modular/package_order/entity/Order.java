package com.siam.system.modular.package_order.entity;

import com.baomidou.mybatisplus.annotation.IdType; import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "订单表")
@TableName("tb_order")
public class Order {

    //订单状态 1=未付款 2=待处理 3=待自取(已处理) 4=待配送(已处理) 5=已配送 6=已完成 7=售后处理中 8=已退款(废弃选项) 9=售后处理完成 10=已取消(未支付) 11=已取消(已支付)
    public static final int STATUS_OF_WAIT_PAYMENT = 1;
    public static final int STATUS_OF_WAIT_HANDLE = 2;
    public static final int STATUS_OF_WAIT_PICKUP = 3;
    public static final int STATUS_OF_WAIT_DELIVERY = 4;
    public static final int STATUS_OF_DELIVERYED = 5;
    public static final int STATUS_OF_COMPLETED = 6;
    public static final int STATUS_OF_WAIT_AFTER_SALE = 7;
    public static final int STATUS_OF_REFUND = 8;
    public static final int STATUS_OF_AFTER_SALE_COMPLETED = 9;
    public static final int STATUS_OF_CANCLED_NOT_PAY = 10;
    public static final int STATUS_OF_CANCLED_WITH_PAY = 11;

    //(未支付)订单取消原因 1=您主动取消 2=订单超时未支付 3=系统异常
    public static final int CANCEL_REASON_OF_YOUSELF = 1;
    public static final int CANCEL_REASON_OF_OVERDUE = 2;
    public static final int CANCEL_REASON_OF_ERROR = 3;

    //下单渠道 1=微信小程序 2=支付宝小程序 3=H5 4=APP 5=收银台下单
    public static final int ORDER_CHANNEL_OF_WXAPPLET = 1;
    public static final int ORDER_CHANNEL_OF_ALIPAYAPPLET = 2;
    public static final int ORDER_CHANNEL_OF_H5 = 3;
    public static final int ORDER_CHANNEL_OF_APP = 4;
    public static final int ORDER_CHANNEL_OF_CASHIER = 5;

    //购物方式 1=自取 2=配送
    public static final int SHOPPING_WAY_OF_PICKUP = 1;
    public static final int SHOPPING_WAY_OF_DELIVERY = 2;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer memberId;

    private String orderNo;

    private Integer goodsTotalQuantity;

    private BigDecimal goodsTotalPrice;

    private BigDecimal packingCharges;

    private BigDecimal deliveryFee;

    private BigDecimal actualPrice;

    private Integer shoppingWay;

    private Integer deliveryAddressId;

    private String contactRealname;

    private String contactPhone;

    private String contactProvince;

    private String contactCity;

    private String contactArea;

    private String contactStreet;

    private Integer contactSex;

    private String remark;

    private String description;

    private Integer status;

    private Integer tradeId;

    private Integer orderLogisticsId;

    private Boolean isInvoice;

    private Integer invoiceId;

    private Boolean isDeleted;

    private Integer shopId;

    private String shopName;

    private String shopAddress;

    private Integer cancelReason;

    private Date paymentDeadline;

    private Boolean isPrinted;

    private Integer queueNo;

    private Integer fullReductionRuleId;

    private String fullReductionRuleDescription;

    private Integer couponsId;

    private String couponsDescription;

    private Integer couponsMemberRelationId;

    private Boolean isChangeToDelivery;

    private String changeToDeliveryOutTradeNo;

    private Integer changeToDeliveryTradeId;

    private BigDecimal platformExtractRatio;

    private BigDecimal platformExtractPrice;

    private BigDecimal platformDeliveryFee;

    private BigDecimal platformIncome;

    private BigDecimal merchantDeliveryFee;

    private BigDecimal courierIncome;

    private BigDecimal merchantIncome;

    private Date paymentSuccessTime;

    private Date orderCompletionTime;

    private Integer paidOrderCancelReason;

    private BigDecimal limitedPrice;

    private BigDecimal reducedPrice;

    private BigDecimal couponsDiscountPrice;

    private Integer deliveryWay;

    private Boolean isPayToMerchant;

    private BigDecimal beforeReducedDeliveryFee;

    private Integer paymentMode;

    private String contactHouseNumber;

    private BigDecimal contactLongitude;

    private BigDecimal contactLatitude;

    private String shopLogoImg;

    private Integer orderChannel;

    private Integer merchantId;

    private Integer checkoutMode;

    private String tableNo;

    private String tableName;

    private Boolean isPayment;

    private Date createTime;

    private Date updateTime;
}