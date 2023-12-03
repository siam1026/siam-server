package com.siam.system.modular.package_goods.entity.internal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("tb_printer")
public class Printer {

    List<Integer> ids;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer shopId;

    private String name;

    private String number;

    private String identifyingCode;

    private Boolean isAutoPrint;

    private String mobileCardNumber;

    private String cloudRegistrationStatus;

    private Integer type;

    private Integer brand;

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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getIdentifyingCode() {
        return identifyingCode;
    }

    public void setIdentifyingCode(String identifyingCode) {
        this.identifyingCode = identifyingCode == null ? null : identifyingCode.trim();
    }

    public Boolean getIsAutoPrint() {
        return isAutoPrint;
    }

    public void setIsAutoPrint(Boolean isAutoPrint) {
        this.isAutoPrint = isAutoPrint;
    }

    public String getMobileCardNumber() {
        return mobileCardNumber;
    }

    public void setMobileCardNumber(String mobileCardNumber) {
        this.mobileCardNumber = mobileCardNumber == null ? null : mobileCardNumber.trim();
    }

    public String getCloudRegistrationStatus() {
        return cloudRegistrationStatus;
    }

    public void setCloudRegistrationStatus(String cloudRegistrationStatus) {
        this.cloudRegistrationStatus = cloudRegistrationStatus == null ? null : cloudRegistrationStatus.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBrand() {
        return brand;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
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