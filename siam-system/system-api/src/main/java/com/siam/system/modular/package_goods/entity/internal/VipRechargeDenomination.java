package com.siam.system.modular.package_goods.entity.internal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tb_vip_recharge_denomination")
public class VipRechargeDenomination {

    /* ##################################### START 扩展字段 #################################### */

    //开始日期
    @TableField(select = false)
    private Date startCreateTime;

    //结束日期
    @TableField(select = false)
    private Date endCreateTime;

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

    private String name;

    private BigDecimal price;

    private Boolean isSale;

    private BigDecimal salePrice;

    private String briefDescription;

    private String description;

    private Boolean isGiveBalance;

    private BigDecimal giveBalance;

    private Boolean isGiveCoupons;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsSale() {
        return isSale;
    }

    public void setIsSale(Boolean isSale) {
        this.isSale = isSale;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription == null ? null : briefDescription.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Boolean getIsGiveBalance() {
        return isGiveBalance;
    }

    public void setIsGiveBalance(Boolean isGiveBalance) {
        this.isGiveBalance = isGiveBalance;
    }

    public BigDecimal getGiveBalance() {
        return giveBalance;
    }

    public void setGiveBalance(BigDecimal giveBalance) {
        this.giveBalance = giveBalance;
    }

    public Boolean getIsGiveCoupons() {
        return isGiveCoupons;
    }

    public void setIsGiveCoupons(Boolean isGiveCoupons) {
        this.isGiveCoupons = isGiveCoupons;
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