package com.siam.system.modular.package_goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tb_shop_change_record")
public class ShopChangeRecord {

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

    /* ##################################### END 扩展字段 #################################### */

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

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer shopId;

    private String name;

    private String province;

    private String city;

    private String area;

    private String street;

    private String managePrimary;

    private String manageMinor;

    private String shopImg;

    private String shopWithinImg;

    private String shopLogoImg;

    private String certificateType1;

    private String certificateImg1;

    private String certificateType2;

    private String certificateImg2;

    private String specialType;

    private String specialImg;

    private String takeOutPhone;

    private String contactRealname;

    private String contactPhone;

    private String announcement;

    private String briefIntroduction;

    private String businessLicense;

    private String idCardFrontSide;

    private String idCardBackSide;

    private String houseNumber;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String applyChangeContent;

    private Integer auditStatus;

    private String auditReason;

    private Date auditTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street == null ? null : street.trim();
    }

    public String getManagePrimary() {
        return managePrimary;
    }

    public void setManagePrimary(String managePrimary) {
        this.managePrimary = managePrimary == null ? null : managePrimary.trim();
    }

    public String getManageMinor() {
        return manageMinor;
    }

    public void setManageMinor(String manageMinor) {
        this.manageMinor = manageMinor == null ? null : manageMinor.trim();
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg == null ? null : shopImg.trim();
    }

    public String getShopWithinImg() {
        return shopWithinImg;
    }

    public void setShopWithinImg(String shopWithinImg) {
        this.shopWithinImg = shopWithinImg == null ? null : shopWithinImg.trim();
    }

    public String getShopLogoImg() {
        return shopLogoImg;
    }

    public void setShopLogoImg(String shopLogoImg) {
        this.shopLogoImg = shopLogoImg == null ? null : shopLogoImg.trim();
    }

    public String getCertificateType1() {
        return certificateType1;
    }

    public void setCertificateType1(String certificateType1) {
        this.certificateType1 = certificateType1 == null ? null : certificateType1.trim();
    }

    public String getCertificateImg1() {
        return certificateImg1;
    }

    public void setCertificateImg1(String certificateImg1) {
        this.certificateImg1 = certificateImg1 == null ? null : certificateImg1.trim();
    }

    public String getCertificateType2() {
        return certificateType2;
    }

    public void setCertificateType2(String certificateType2) {
        this.certificateType2 = certificateType2 == null ? null : certificateType2.trim();
    }

    public String getCertificateImg2() {
        return certificateImg2;
    }

    public void setCertificateImg2(String certificateImg2) {
        this.certificateImg2 = certificateImg2 == null ? null : certificateImg2.trim();
    }

    public String getSpecialType() {
        return specialType;
    }

    public void setSpecialType(String specialType) {
        this.specialType = specialType == null ? null : specialType.trim();
    }

    public String getSpecialImg() {
        return specialImg;
    }

    public void setSpecialImg(String specialImg) {
        this.specialImg = specialImg == null ? null : specialImg.trim();
    }

    public String getTakeOutPhone() {
        return takeOutPhone;
    }

    public void setTakeOutPhone(String takeOutPhone) {
        this.takeOutPhone = takeOutPhone == null ? null : takeOutPhone.trim();
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

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement == null ? null : announcement.trim();
    }

    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction == null ? null : briefIntroduction.trim();
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense == null ? null : businessLicense.trim();
    }

    public String getIdCardFrontSide() {
        return idCardFrontSide;
    }

    public void setIdCardFrontSide(String idCardFrontSide) {
        this.idCardFrontSide = idCardFrontSide == null ? null : idCardFrontSide.trim();
    }

    public String getIdCardBackSide() {
        return idCardBackSide;
    }

    public void setIdCardBackSide(String idCardBackSide) {
        this.idCardBackSide = idCardBackSide == null ? null : idCardBackSide.trim();
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber == null ? null : houseNumber.trim();
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getApplyChangeContent() {
        return applyChangeContent;
    }

    public void setApplyChangeContent(String applyChangeContent) {
        this.applyChangeContent = applyChangeContent == null ? null : applyChangeContent.trim();
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason == null ? null : auditReason.trim();
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
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