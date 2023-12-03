package com.siam.system.modular.package_user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

@TableName("tb_merchant")
@ApiModel(value = "商家表")
public class Merchant {

    @ApiModelProperty(notes = "主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "门店信息id")
    private Integer shopId;

    @ApiModelProperty(notes = "用户名称")
    private String username;

    @ApiModelProperty(notes = "手机号码")
    private String mobile;

    @ApiModelProperty(notes = "用户密码")
    private String password;

    @ApiModelProperty(notes = "密码加盐")
    private String passwordSalt;

    @ApiModelProperty(notes = "昵称")
    private String nickname;

    @ApiModelProperty(notes = "权限")
    private String roles;

    @ApiModelProperty(notes = "头像")
    private String headImg;

    @ApiModelProperty(notes = "性别")
    private Integer sex;

    @ApiModelProperty(notes = "邮箱")
    private String email;

    @ApiModelProperty(notes = "证件类型")
    private String certificateType;

    @ApiModelProperty(notes = "证件照片")
    private String certificateImg;

    @ApiModelProperty(notes = "是否禁用 0=启用 1=禁用")
    private Boolean isDisabled;

    @ApiModelProperty(notes = "是否删除 0=正常 1=删除")
    private Boolean isDeleted;

    private Integer auditStatus;

    private String realName;

    private String idCard;

    private String openingBankAddress;

    private String openingBankName;

    private String bankCard;

    private String alipayAccount;

    private String wechatAccount;

    private BigDecimal balance;

    private BigDecimal withdrawableBalance;

    private BigDecimal frozenBalance;

    private BigDecimal orderFrozenBalance;

    private Integer memberId;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt == null ? null : passwordSalt.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles == null ? null : roles.trim();
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType == null ? null : certificateType.trim();
    }

    public String getCertificateImg() {
        return certificateImg;
    }

    public void setCertificateImg(String certificateImg) {
        this.certificateImg = certificateImg == null ? null : certificateImg.trim();
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getOpeningBankAddress() {
        return openingBankAddress;
    }

    public void setOpeningBankAddress(String openingBankAddress) {
        this.openingBankAddress = openingBankAddress == null ? null : openingBankAddress.trim();
    }

    public String getOpeningBankName() {
        return openingBankName;
    }

    public void setOpeningBankName(String openingBankName) {
        this.openingBankName = openingBankName == null ? null : openingBankName.trim();
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount == null ? null : alipayAccount.trim();
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount == null ? null : wechatAccount.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getWithdrawableBalance() {
        return withdrawableBalance;
    }

    public void setWithdrawableBalance(BigDecimal withdrawableBalance) {
        this.withdrawableBalance = withdrawableBalance;
    }

    public BigDecimal getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(BigDecimal frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public BigDecimal getOrderFrozenBalance() {
        return orderFrozenBalance;
    }

    public void setOrderFrozenBalance(BigDecimal orderFrozenBalance) {
        this.orderFrozenBalance = orderFrozenBalance;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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