package com.siam.system.modular.package_goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tb_setting")
public class Setting {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private BigDecimal purchaseRewardPoints;

    private BigDecimal registrationRewardPoints;

    private Integer newMemberCouponsId;

    private Integer defaultShopId;

    private BigDecimal merchantWithdrawFee;

    private BigDecimal startDeliveryPrice;

    private BigDecimal deliveryStartingDistance;

    private BigDecimal deliveryStartingPrice;

    private BigDecimal deliveryKilometerPrice;

    private BigDecimal deliveryDistanceLimit;

    private BigDecimal orderSystemExtractionRatio;

    private BigDecimal merchantMealPreparationTime;

    private BigDecimal memberWithdrawFee;

    private BigDecimal registrationRewardInviteRewardAmount;

    private BigDecimal memberWithdrawMeetAmount;

    private BigDecimal memberWithdrawAuditThreshold;

    private String customerServicePhone;

    private String customerServiceWechat;

    private String customerServiceWechatQrcode;

    private BigDecimal freightInsurancePaidAmount;

    private BigDecimal inviteeConsumeCommission;

    private BigDecimal caseoneOwnCommission;

    private BigDecimal casetwoOwnCommission;

    private BigDecimal casetwoFirstLevelInviterCommission;

    private BigDecimal casethreeOwnCommission;

    private BigDecimal casethreeFirstLevelInviterCommission;

    private BigDecimal casethreeSecondLevelInviterCommission;

    private BigDecimal pointsMallInviteeConsumeCommission;

    private BigDecimal pointsMallCaseoneOwnCommission;

    private BigDecimal pointsMallCasetwoOwnCommission;

    private BigDecimal pointsMallCasetwoFirstLevelInviterCommission;

    private BigDecimal pointsMallCasethreeOwnCommission;

    private BigDecimal pointsMallCasethreeFirstLevelInviterCommission;

    private BigDecimal pointsMallCasethreeSecondLevelInviterCommission;

    private String inviteFriendsActivityRule;

    private String commissionRule;

    private String vipRule;

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

    public BigDecimal getPurchaseRewardPoints() {
        return purchaseRewardPoints;
    }

    public void setPurchaseRewardPoints(BigDecimal purchaseRewardPoints) {
        this.purchaseRewardPoints = purchaseRewardPoints;
    }

    public BigDecimal getRegistrationRewardPoints() {
        return registrationRewardPoints;
    }

    public void setRegistrationRewardPoints(BigDecimal registrationRewardPoints) {
        this.registrationRewardPoints = registrationRewardPoints;
    }

    public Integer getNewMemberCouponsId() {
        return newMemberCouponsId;
    }

    public void setNewMemberCouponsId(Integer newMemberCouponsId) {
        this.newMemberCouponsId = newMemberCouponsId;
    }

    public Integer getDefaultShopId() {
        return defaultShopId;
    }

    public void setDefaultShopId(Integer defaultShopId) {
        this.defaultShopId = defaultShopId;
    }

    public BigDecimal getMerchantWithdrawFee() {
        return merchantWithdrawFee;
    }

    public void setMerchantWithdrawFee(BigDecimal merchantWithdrawFee) {
        this.merchantWithdrawFee = merchantWithdrawFee;
    }

    public BigDecimal getStartDeliveryPrice() {
        return startDeliveryPrice;
    }

    public void setStartDeliveryPrice(BigDecimal startDeliveryPrice) {
        this.startDeliveryPrice = startDeliveryPrice;
    }

    public BigDecimal getDeliveryStartingDistance() {
        return deliveryStartingDistance;
    }

    public void setDeliveryStartingDistance(BigDecimal deliveryStartingDistance) {
        this.deliveryStartingDistance = deliveryStartingDistance;
    }

    public BigDecimal getDeliveryStartingPrice() {
        return deliveryStartingPrice;
    }

    public void setDeliveryStartingPrice(BigDecimal deliveryStartingPrice) {
        this.deliveryStartingPrice = deliveryStartingPrice;
    }

    public BigDecimal getDeliveryKilometerPrice() {
        return deliveryKilometerPrice;
    }

    public void setDeliveryKilometerPrice(BigDecimal deliveryKilometerPrice) {
        this.deliveryKilometerPrice = deliveryKilometerPrice;
    }

    public BigDecimal getDeliveryDistanceLimit() {
        return deliveryDistanceLimit;
    }

    public void setDeliveryDistanceLimit(BigDecimal deliveryDistanceLimit) {
        this.deliveryDistanceLimit = deliveryDistanceLimit;
    }

    public BigDecimal getOrderSystemExtractionRatio() {
        return orderSystemExtractionRatio;
    }

    public void setOrderSystemExtractionRatio(BigDecimal orderSystemExtractionRatio) {
        this.orderSystemExtractionRatio = orderSystemExtractionRatio;
    }

    public BigDecimal getMerchantMealPreparationTime() {
        return merchantMealPreparationTime;
    }

    public void setMerchantMealPreparationTime(BigDecimal merchantMealPreparationTime) {
        this.merchantMealPreparationTime = merchantMealPreparationTime;
    }

    public BigDecimal getMemberWithdrawFee() {
        return memberWithdrawFee;
    }

    public void setMemberWithdrawFee(BigDecimal memberWithdrawFee) {
        this.memberWithdrawFee = memberWithdrawFee;
    }

    public BigDecimal getRegistrationRewardInviteRewardAmount() {
        return registrationRewardInviteRewardAmount;
    }

    public void setRegistrationRewardInviteRewardAmount(BigDecimal registrationRewardInviteRewardAmount) {
        this.registrationRewardInviteRewardAmount = registrationRewardInviteRewardAmount;
    }

    public BigDecimal getMemberWithdrawMeetAmount() {
        return memberWithdrawMeetAmount;
    }

    public void setMemberWithdrawMeetAmount(BigDecimal memberWithdrawMeetAmount) {
        this.memberWithdrawMeetAmount = memberWithdrawMeetAmount;
    }

    public BigDecimal getMemberWithdrawAuditThreshold() {
        return memberWithdrawAuditThreshold;
    }

    public void setMemberWithdrawAuditThreshold(BigDecimal memberWithdrawAuditThreshold) {
        this.memberWithdrawAuditThreshold = memberWithdrawAuditThreshold;
    }

    public String getCustomerServicePhone() {
        return customerServicePhone;
    }

    public void setCustomerServicePhone(String customerServicePhone) {
        this.customerServicePhone = customerServicePhone == null ? null : customerServicePhone.trim();
    }

    public String getCustomerServiceWechat() {
        return customerServiceWechat;
    }

    public void setCustomerServiceWechat(String customerServiceWechat) {
        this.customerServiceWechat = customerServiceWechat == null ? null : customerServiceWechat.trim();
    }

    public String getCustomerServiceWechatQrcode() {
        return customerServiceWechatQrcode;
    }

    public void setCustomerServiceWechatQrcode(String customerServiceWechatQrcode) {
        this.customerServiceWechatQrcode = customerServiceWechatQrcode == null ? null : customerServiceWechatQrcode.trim();
    }

    public BigDecimal getFreightInsurancePaidAmount() {
        return freightInsurancePaidAmount;
    }

    public void setFreightInsurancePaidAmount(BigDecimal freightInsurancePaidAmount) {
        this.freightInsurancePaidAmount = freightInsurancePaidAmount;
    }

    public BigDecimal getInviteeConsumeCommission() {
        return inviteeConsumeCommission;
    }

    public void setInviteeConsumeCommission(BigDecimal inviteeConsumeCommission) {
        this.inviteeConsumeCommission = inviteeConsumeCommission;
    }

    public BigDecimal getCaseoneOwnCommission() {
        return caseoneOwnCommission;
    }

    public void setCaseoneOwnCommission(BigDecimal caseoneOwnCommission) {
        this.caseoneOwnCommission = caseoneOwnCommission;
    }

    public BigDecimal getCasetwoOwnCommission() {
        return casetwoOwnCommission;
    }

    public void setCasetwoOwnCommission(BigDecimal casetwoOwnCommission) {
        this.casetwoOwnCommission = casetwoOwnCommission;
    }

    public BigDecimal getCasetwoFirstLevelInviterCommission() {
        return casetwoFirstLevelInviterCommission;
    }

    public void setCasetwoFirstLevelInviterCommission(BigDecimal casetwoFirstLevelInviterCommission) {
        this.casetwoFirstLevelInviterCommission = casetwoFirstLevelInviterCommission;
    }

    public BigDecimal getCasethreeOwnCommission() {
        return casethreeOwnCommission;
    }

    public void setCasethreeOwnCommission(BigDecimal casethreeOwnCommission) {
        this.casethreeOwnCommission = casethreeOwnCommission;
    }

    public BigDecimal getCasethreeFirstLevelInviterCommission() {
        return casethreeFirstLevelInviterCommission;
    }

    public void setCasethreeFirstLevelInviterCommission(BigDecimal casethreeFirstLevelInviterCommission) {
        this.casethreeFirstLevelInviterCommission = casethreeFirstLevelInviterCommission;
    }

    public BigDecimal getCasethreeSecondLevelInviterCommission() {
        return casethreeSecondLevelInviterCommission;
    }

    public void setCasethreeSecondLevelInviterCommission(BigDecimal casethreeSecondLevelInviterCommission) {
        this.casethreeSecondLevelInviterCommission = casethreeSecondLevelInviterCommission;
    }

    public BigDecimal getPointsMallInviteeConsumeCommission() {
        return pointsMallInviteeConsumeCommission;
    }

    public void setPointsMallInviteeConsumeCommission(BigDecimal pointsMallInviteeConsumeCommission) {
        this.pointsMallInviteeConsumeCommission = pointsMallInviteeConsumeCommission;
    }

    public BigDecimal getPointsMallCaseoneOwnCommission() {
        return pointsMallCaseoneOwnCommission;
    }

    public void setPointsMallCaseoneOwnCommission(BigDecimal pointsMallCaseoneOwnCommission) {
        this.pointsMallCaseoneOwnCommission = pointsMallCaseoneOwnCommission;
    }

    public BigDecimal getPointsMallCasetwoOwnCommission() {
        return pointsMallCasetwoOwnCommission;
    }

    public void setPointsMallCasetwoOwnCommission(BigDecimal pointsMallCasetwoOwnCommission) {
        this.pointsMallCasetwoOwnCommission = pointsMallCasetwoOwnCommission;
    }

    public BigDecimal getPointsMallCasetwoFirstLevelInviterCommission() {
        return pointsMallCasetwoFirstLevelInviterCommission;
    }

    public void setPointsMallCasetwoFirstLevelInviterCommission(BigDecimal pointsMallCasetwoFirstLevelInviterCommission) {
        this.pointsMallCasetwoFirstLevelInviterCommission = pointsMallCasetwoFirstLevelInviterCommission;
    }

    public BigDecimal getPointsMallCasethreeOwnCommission() {
        return pointsMallCasethreeOwnCommission;
    }

    public void setPointsMallCasethreeOwnCommission(BigDecimal pointsMallCasethreeOwnCommission) {
        this.pointsMallCasethreeOwnCommission = pointsMallCasethreeOwnCommission;
    }

    public BigDecimal getPointsMallCasethreeFirstLevelInviterCommission() {
        return pointsMallCasethreeFirstLevelInviterCommission;
    }

    public void setPointsMallCasethreeFirstLevelInviterCommission(BigDecimal pointsMallCasethreeFirstLevelInviterCommission) {
        this.pointsMallCasethreeFirstLevelInviterCommission = pointsMallCasethreeFirstLevelInviterCommission;
    }

    public BigDecimal getPointsMallCasethreeSecondLevelInviterCommission() {
        return pointsMallCasethreeSecondLevelInviterCommission;
    }

    public void setPointsMallCasethreeSecondLevelInviterCommission(BigDecimal pointsMallCasethreeSecondLevelInviterCommission) {
        this.pointsMallCasethreeSecondLevelInviterCommission = pointsMallCasethreeSecondLevelInviterCommission;
    }

    public String getInviteFriendsActivityRule() {
        return inviteFriendsActivityRule;
    }

    public void setInviteFriendsActivityRule(String inviteFriendsActivityRule) {
        this.inviteFriendsActivityRule = inviteFriendsActivityRule == null ? null : inviteFriendsActivityRule.trim();
    }

    public String getCommissionRule() {
        return commissionRule;
    }

    public void setCommissionRule(String commissionRule) {
        this.commissionRule = commissionRule == null ? null : commissionRule.trim();
    }

    public String getVipRule() {
        return vipRule;
    }

    public void setVipRule(String vipRule) {
        this.vipRule = vipRule == null ? null : vipRule.trim();
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