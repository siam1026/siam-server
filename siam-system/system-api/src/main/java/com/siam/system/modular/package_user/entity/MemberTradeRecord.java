package com.siam.system.modular.package_user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tb_member_trade_record")
@ApiModel(value = "用户交易表")
public class MemberTradeRecord {

    Date startTime;
    Date endTime;

    @ApiModelProperty(notes = "主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "商家账号id")
    private Integer merchantId;

    @ApiModelProperty(notes = "用户id")
    private Integer memberId;

    @ApiModelProperty(notes = "交易单号(支付平台的订单号)")
    private String tradeNo;

    @ApiModelProperty(notes = "商户单号(网站平台的订单号)")
    private String outTradeNo;

    @ApiModelProperty(notes = "交易类型 1=用户订单付款 2=用户会员充值 3=用户自取订单改为配送 4=商家余额提现")
    private Integer type;

    @ApiModelProperty(notes = "支付方式 1=微信 2=支付宝 3=平台余额 4=网银 5=银行转账")
    private Integer paymentMode;

    @ApiModelProperty(notes = "金额")
    private BigDecimal amount;

    @ApiModelProperty(notes = "交易状态 1=待支付 2=支付成功 3=支付失败 3=交易超时自动关闭")
    private Integer status;

    @ApiModelProperty(notes = "创建时间")
    private Date createTime;

    @ApiModelProperty(notes = "修改时间")
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

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}