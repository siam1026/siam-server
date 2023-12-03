package com.siam.system.modular.package_order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getGoodsTotalQuantity() {
        return goodsTotalQuantity;
    }

    public void setGoodsTotalQuantity(Integer goodsTotalQuantity) {
        this.goodsTotalQuantity = goodsTotalQuantity;
    }

    public BigDecimal getGoodsTotalPrice() {
        return goodsTotalPrice;
    }

    public void setGoodsTotalPrice(BigDecimal goodsTotalPrice) {
        this.goodsTotalPrice = goodsTotalPrice;
    }

    public BigDecimal getPackingCharges() {
        return packingCharges;
    }

    public void setPackingCharges(BigDecimal packingCharges) {
        this.packingCharges = packingCharges;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Integer getShoppingWay() {
        return shoppingWay;
    }

    public void setShoppingWay(Integer shoppingWay) {
        this.shoppingWay = shoppingWay;
    }

    public Integer getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(Integer deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public String getContactRealname() {
        return contactRealname;
    }

    public void setContactRealname(String contactRealname) {
        this.contactRealname = contactRealname == null ? null : contactRealname.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getContactProvince() {
        return contactProvince;
    }

    public void setContactProvince(String contactProvince) {
        this.contactProvince = contactProvince == null ? null : contactProvince.trim();
    }

    public String getContactCity() {
        return contactCity;
    }

    public void setContactCity(String contactCity) {
        this.contactCity = contactCity == null ? null : contactCity.trim();
    }

    public String getContactArea() {
        return contactArea;
    }

    public void setContactArea(String contactArea) {
        this.contactArea = contactArea == null ? null : contactArea.trim();
    }

    public String getContactStreet() {
        return contactStreet;
    }

    public void setContactStreet(String contactStreet) {
        this.contactStreet = contactStreet == null ? null : contactStreet.trim();
    }

    public Integer getContactSex() {
        return contactSex;
    }

    public void setContactSex(Integer contactSex) {
        this.contactSex = contactSex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getOrderLogisticsId() {
        return orderLogisticsId;
    }

    public void setOrderLogisticsId(Integer orderLogisticsId) {
        this.orderLogisticsId = orderLogisticsId;
    }

    public Boolean getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Boolean isInvoice) {
        this.isInvoice = isInvoice;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress == null ? null : shopAddress.trim();
    }

    public Integer getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(Integer cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Date getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setPaymentDeadline(Date paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    public Boolean getIsPrinted() {
        return isPrinted;
    }

    public void setIsPrinted(Boolean isPrinted) {
        this.isPrinted = isPrinted;
    }

    public Integer getQueueNo() {
        return queueNo;
    }

    public void setQueueNo(Integer queueNo) {
        this.queueNo = queueNo;
    }

    public Integer getFullReductionRuleId() {
        return fullReductionRuleId;
    }

    public void setFullReductionRuleId(Integer fullReductionRuleId) {
        this.fullReductionRuleId = fullReductionRuleId;
    }

    public String getFullReductionRuleDescription() {
        return fullReductionRuleDescription;
    }

    public void setFullReductionRuleDescription(String fullReductionRuleDescription) {
        this.fullReductionRuleDescription = fullReductionRuleDescription == null ? null : fullReductionRuleDescription.trim();
    }

    public Integer getCouponsId() {
        return couponsId;
    }

    public void setCouponsId(Integer couponsId) {
        this.couponsId = couponsId;
    }

    public String getCouponsDescription() {
        return couponsDescription;
    }

    public void setCouponsDescription(String couponsDescription) {
        this.couponsDescription = couponsDescription == null ? null : couponsDescription.trim();
    }

    public Integer getCouponsMemberRelationId() {
        return couponsMemberRelationId;
    }

    public void setCouponsMemberRelationId(Integer couponsMemberRelationId) {
        this.couponsMemberRelationId = couponsMemberRelationId;
    }

    public Boolean getIsChangeToDelivery() {
        return isChangeToDelivery;
    }

    public void setIsChangeToDelivery(Boolean isChangeToDelivery) {
        this.isChangeToDelivery = isChangeToDelivery;
    }

    public String getChangeToDeliveryOutTradeNo() {
        return changeToDeliveryOutTradeNo;
    }

    public void setChangeToDeliveryOutTradeNo(String changeToDeliveryOutTradeNo) {
        this.changeToDeliveryOutTradeNo = changeToDeliveryOutTradeNo == null ? null : changeToDeliveryOutTradeNo.trim();
    }

    public Integer getChangeToDeliveryTradeId() {
        return changeToDeliveryTradeId;
    }

    public void setChangeToDeliveryTradeId(Integer changeToDeliveryTradeId) {
        this.changeToDeliveryTradeId = changeToDeliveryTradeId;
    }

    public BigDecimal getPlatformExtractRatio() {
        return platformExtractRatio;
    }

    public void setPlatformExtractRatio(BigDecimal platformExtractRatio) {
        this.platformExtractRatio = platformExtractRatio;
    }

    public BigDecimal getPlatformExtractPrice() {
        return platformExtractPrice;
    }

    public void setPlatformExtractPrice(BigDecimal platformExtractPrice) {
        this.platformExtractPrice = platformExtractPrice;
    }

    public BigDecimal getPlatformDeliveryFee() {
        return platformDeliveryFee;
    }

    public void setPlatformDeliveryFee(BigDecimal platformDeliveryFee) {
        this.platformDeliveryFee = platformDeliveryFee;
    }

    public BigDecimal getPlatformIncome() {
        return platformIncome;
    }

    public void setPlatformIncome(BigDecimal platformIncome) {
        this.platformIncome = platformIncome;
    }

    public BigDecimal getMerchantDeliveryFee() {
        return merchantDeliveryFee;
    }

    public void setMerchantDeliveryFee(BigDecimal merchantDeliveryFee) {
        this.merchantDeliveryFee = merchantDeliveryFee;
    }

    public BigDecimal getCourierIncome() {
        return courierIncome;
    }

    public void setCourierIncome(BigDecimal courierIncome) {
        this.courierIncome = courierIncome;
    }

    public BigDecimal getMerchantIncome() {
        return merchantIncome;
    }

    public void setMerchantIncome(BigDecimal merchantIncome) {
        this.merchantIncome = merchantIncome;
    }

    public Date getPaymentSuccessTime() {
        return paymentSuccessTime;
    }

    public void setPaymentSuccessTime(Date paymentSuccessTime) {
        this.paymentSuccessTime = paymentSuccessTime;
    }

    public Date getOrderCompletionTime() {
        return orderCompletionTime;
    }

    public void setOrderCompletionTime(Date orderCompletionTime) {
        this.orderCompletionTime = orderCompletionTime;
    }

    public Integer getPaidOrderCancelReason() {
        return paidOrderCancelReason;
    }

    public void setPaidOrderCancelReason(Integer paidOrderCancelReason) {
        this.paidOrderCancelReason = paidOrderCancelReason;
    }

    public BigDecimal getLimitedPrice() {
        return limitedPrice;
    }

    public void setLimitedPrice(BigDecimal limitedPrice) {
        this.limitedPrice = limitedPrice;
    }

    public BigDecimal getReducedPrice() {
        return reducedPrice;
    }

    public void setReducedPrice(BigDecimal reducedPrice) {
        this.reducedPrice = reducedPrice;
    }

    public BigDecimal getCouponsDiscountPrice() {
        return couponsDiscountPrice;
    }

    public void setCouponsDiscountPrice(BigDecimal couponsDiscountPrice) {
        this.couponsDiscountPrice = couponsDiscountPrice;
    }

    public Integer getDeliveryWay() {
        return deliveryWay;
    }

    public void setDeliveryWay(Integer deliveryWay) {
        this.deliveryWay = deliveryWay;
    }

    public Boolean getIsPayToMerchant() {
        return isPayToMerchant;
    }

    public void setIsPayToMerchant(Boolean isPayToMerchant) {
        this.isPayToMerchant = isPayToMerchant;
    }

    public BigDecimal getBeforeReducedDeliveryFee() {
        return beforeReducedDeliveryFee;
    }

    public void setBeforeReducedDeliveryFee(BigDecimal beforeReducedDeliveryFee) {
        this.beforeReducedDeliveryFee = beforeReducedDeliveryFee;
    }

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getContactHouseNumber() {
        return contactHouseNumber;
    }

    public void setContactHouseNumber(String contactHouseNumber) {
        this.contactHouseNumber = contactHouseNumber == null ? null : contactHouseNumber.trim();
    }

    public BigDecimal getContactLongitude() {
        return contactLongitude;
    }

    public void setContactLongitude(BigDecimal contactLongitude) {
        this.contactLongitude = contactLongitude;
    }

    public BigDecimal getContactLatitude() {
        return contactLatitude;
    }

    public void setContactLatitude(BigDecimal contactLatitude) {
        this.contactLatitude = contactLatitude;
    }

    public String getShopLogoImg() {
        return shopLogoImg;
    }

    public void setShopLogoImg(String shopLogoImg) {
        this.shopLogoImg = shopLogoImg == null ? null : shopLogoImg.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}