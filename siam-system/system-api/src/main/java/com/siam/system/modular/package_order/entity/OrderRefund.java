package com.siam.system.modular.package_order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tb_order_refund")
public class OrderRefund {

    Date startTime;
    Date endTime;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer orderId;

    private Integer type;

    private Integer refundWay;

    private Integer refundReason;

    private String refundReasonDescription;

    private String evidenceImages;

    private BigDecimal refundAmount;

    private Integer refundAccount;

    private Integer status;

    private Integer goodsTotalQuantity;

    private BigDecimal goodsTotalPrice;

    private BigDecimal packingCharges;

    private Boolean isRefundDeliveryFee;

    private BigDecimal deliveryFee;

    private Boolean isUsedCoupons;

    private Boolean isUsedFullReductionRule;

    private Date createTime;

    private Date updateTime;

    //页码
    private Integer pageNo = 1;

    //页面大小
    private Integer pageSize = 20;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRefundWay() {
        return refundWay;
    }

    public void setRefundWay(Integer refundWay) {
        this.refundWay = refundWay;
    }

    public Integer getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(Integer refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundReasonDescription() {
        return refundReasonDescription;
    }

    public void setRefundReasonDescription(String refundReasonDescription) {
        this.refundReasonDescription = refundReasonDescription == null ? null : refundReasonDescription.trim();
    }

    public String getEvidenceImages() {
        return evidenceImages;
    }

    public void setEvidenceImages(String evidenceImages) {
        this.evidenceImages = evidenceImages == null ? null : evidenceImages.trim();
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Integer getRefundAccount() {
        return refundAccount;
    }

    public void setRefundAccount(Integer refundAccount) {
        this.refundAccount = refundAccount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Boolean getIsRefundDeliveryFee() {
        return isRefundDeliveryFee;
    }

    public void setIsRefundDeliveryFee(Boolean isRefundDeliveryFee) {
        this.isRefundDeliveryFee = isRefundDeliveryFee;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Boolean getIsUsedCoupons() {
        return isUsedCoupons;
    }

    public void setIsUsedCoupons(Boolean isUsedCoupons) {
        this.isUsedCoupons = isUsedCoupons;
    }

    public Boolean getIsUsedFullReductionRule() {
        return isUsedFullReductionRule;
    }

    public void setIsUsedFullReductionRule(Boolean isUsedFullReductionRule) {
        this.isUsedFullReductionRule = isUsedFullReductionRule;
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