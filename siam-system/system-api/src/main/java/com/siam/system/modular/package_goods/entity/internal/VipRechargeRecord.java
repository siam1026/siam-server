package com.siam.system.modular.package_goods.entity.internal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tb_vip_recharge_record")
@ApiModel(value = "会员充值记录表")
public class VipRechargeRecord {

    /* ##################################### START 扩展字段 #################################### */

    //开始日期
    @TableField(select = false)
    private Date startCreateTime;

    //结束日期
    @TableField(select = false)
    private Date endCreateTime;

    //充值时长(月)
    @TableField(select = false)
    private int rechargeTime;

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    //页码
    @TableField(select = false)
    private Integer pageNo = 1;

    //页面大小
    @TableField(select = false)
    private Integer pageSize = 20;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    /* ##################################### END 扩展字段 #################################### */

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer memberId;

    private String orderNo;

    private Integer channel;

    private Integer denominationId;

    private BigDecimal amount;

    private String denominationName;

    private BigDecimal denominationPrice;

    private Boolean denominationIsSale;

    private BigDecimal denominationSalePrice;

    private Boolean denominationIsGiveBalance;

    private BigDecimal denominationGiveBalance;

    private Boolean denominationIsGiveCoupons;

    private String denominationGiveCouponsDescription;

    private String denominationGiveCouponsJson;

    private Integer tradeId;

    private Integer status;

    private Date createTime;

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

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getDenominationId() {
        return denominationId;
    }

    public void setDenominationId(Integer denominationId) {
        this.denominationId = denominationId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDenominationName() {
        return denominationName;
    }

    public void setDenominationName(String denominationName) {
        this.denominationName = denominationName == null ? null : denominationName.trim();
    }

    public BigDecimal getDenominationPrice() {
        return denominationPrice;
    }

    public void setDenominationPrice(BigDecimal denominationPrice) {
        this.denominationPrice = denominationPrice;
    }

    public Boolean getDenominationIsSale() {
        return denominationIsSale;
    }

    public void setDenominationIsSale(Boolean denominationIsSale) {
        this.denominationIsSale = denominationIsSale;
    }

    public BigDecimal getDenominationSalePrice() {
        return denominationSalePrice;
    }

    public void setDenominationSalePrice(BigDecimal denominationSalePrice) {
        this.denominationSalePrice = denominationSalePrice;
    }

    public Boolean getDenominationIsGiveBalance() {
        return denominationIsGiveBalance;
    }

    public void setDenominationIsGiveBalance(Boolean denominationIsGiveBalance) {
        this.denominationIsGiveBalance = denominationIsGiveBalance;
    }

    public BigDecimal getDenominationGiveBalance() {
        return denominationGiveBalance;
    }

    public void setDenominationGiveBalance(BigDecimal denominationGiveBalance) {
        this.denominationGiveBalance = denominationGiveBalance;
    }

    public Boolean getDenominationIsGiveCoupons() {
        return denominationIsGiveCoupons;
    }

    public void setDenominationIsGiveCoupons(Boolean denominationIsGiveCoupons) {
        this.denominationIsGiveCoupons = denominationIsGiveCoupons;
    }

    public String getDenominationGiveCouponsDescription() {
        return denominationGiveCouponsDescription;
    }

    public void setDenominationGiveCouponsDescription(String denominationGiveCouponsDescription) {
        this.denominationGiveCouponsDescription = denominationGiveCouponsDescription == null ? null : denominationGiveCouponsDescription.trim();
    }

    public String getDenominationGiveCouponsJson() {
        return denominationGiveCouponsJson;
    }

    public void setDenominationGiveCouponsJson(String denominationGiveCouponsJson) {
        this.denominationGiveCouponsJson = denominationGiveCouponsJson == null ? null : denominationGiveCouponsJson.trim();
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}