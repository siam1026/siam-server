package com.siam.system.modular.package_goods.entity;

import com.baomidou.mybatisplus.annotation.IdType; import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName("tb_shop")
@ApiModel(value = "门店表")
public class Shop {

    // 结账模式 1=先付款后就餐 2=先就餐后付款
    public static final Integer CHECKOUT_MODE_PAY_FIRST = 1;
    public static final Integer CHECKOUT_MODE_EAT_FIRST = 2;

    /* ##################################### START 扩展字段 #################################### */

    @TableField(exist = false)
    String position;

    @TableField(exist = false)
    private List<Integer> ids;

    //开始日期
    @TableField(exist = false)
    private Date startCreateTime;

    //结束日期
    @TableField(exist = false)
    private Date endCreateTime;

    /*//配送距离上限(KM)
    private BigDecimal deliveryDistanceLimit;*/

    //定位的经度
    @TableField(exist = false)
    private BigDecimal positionLongitude;

    //定位的维度
    @TableField(exist = false)
    private BigDecimal positionLatitude;

    //页码
    @TableField(exist = false) private Integer pageNo = 1;

    //页面大小
    @TableField(exist = false) private Integer pageSize = 20;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

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

    public BigDecimal getPositionLongitude() {
        return positionLongitude;
    }

    public void setPositionLongitude(BigDecimal positionLongitude) {
        this.positionLongitude = positionLongitude;
    }

    public BigDecimal getPositionLatitude() {
        return positionLatitude;
    }

    public void setPositionLatitude(BigDecimal positionLatitude) {
        this.positionLatitude = positionLatitude;
    }
    /* ##################################### END 扩展字段 #################################### */

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Start 特殊字段标识 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    //店铺id列表
    @TableField(exist = false)
    private List<Integer> shopIdList;

    public List<Integer> getShopIdList() {
        return shopIdList;
    }

    public void setShopIdList(List<Integer> shopIdList) {
        this.shopIdList = shopIdList;
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ End ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer merchantId;

    private String name;

    private String code;

    private String province;

    private String city;

    private String area;

    private String street;

    private Boolean isOperating;

    private String startTime;

    private String endTime;

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

    private Integer auditStatus;

    private String auditReason;

    private Date auditTime;

    private String takeOutPhone;

    private String contactRealname;

    private String contactPhone;

    private String announcement;

    private BigDecimal startDeliveryPrice;

    private BigDecimal deliveryStartingPrice;

    private BigDecimal deliveryKilometerPrice;

    private BigDecimal deliveryDistanceLimit;

    private BigDecimal serviceRating;

    private String businessLicense;

    private String idCardFrontSide;

    private String idCardBackSide;

    private Integer status;

    private BigDecimal reducedDeliveryPrice;

    private Boolean isOpenOrderAudio;

    private Boolean isOpenLocalPrint;

    private Boolean isOpenCloudPrint;

    private String firstPoster;

    private String houseNumber;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String briefIntroduction;

    private Integer checkoutMode;

    private Integer kitchenTotalOrderPrinterId;

    private Integer checkoutPrinterId;

    private Date createTime;

    private Date updateTime;
}