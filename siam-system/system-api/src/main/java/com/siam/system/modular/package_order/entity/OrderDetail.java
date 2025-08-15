package com.siam.system.modular.package_order.entity;

import com.baomidou.mybatisplus.annotation.IdType; import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value = "订单商品详情表")
@TableName("tb_order_detail")
public class OrderDetail {

    @ApiModelProperty(notes = "主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    List<String> ids;

    @ApiModelProperty(notes = "订单id")
    private Integer orderId;

    @ApiModelProperty(notes = "商品id")
    private Integer goodsId;

    @ApiModelProperty(notes = "商品名称")
    private String goodsName;

    @ApiModelProperty(notes = "商品主图")
    private String mainImage;

    @ApiModelProperty(notes = "商品规格 JSON格式")
    private String specList;

    @ApiModelProperty(notes = "价格")
    private BigDecimal price;

    @ApiModelProperty(notes = "购买数量")
    private Integer number;

    @ApiModelProperty(notes = "小计")
    private BigDecimal subtotal;

    private BigDecimal packingCharges;

    private Boolean isUsedCoupons;

    private BigDecimal couponsDiscountPrice;

    private BigDecimal afterCouponsDiscountPrice;

    private Boolean isDeleted;

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

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage == null ? null : mainImage.trim();
    }

    public String getSpecList() {
        return specList;
    }

    public void setSpecList(String specList) {
        this.specList = specList == null ? null : specList.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getPackingCharges() {
        return packingCharges;
    }

    public void setPackingCharges(BigDecimal packingCharges) {
        this.packingCharges = packingCharges;
    }

    public Boolean getIsUsedCoupons() {
        return isUsedCoupons;
    }

    public void setIsUsedCoupons(Boolean isUsedCoupons) {
        this.isUsedCoupons = isUsedCoupons;
    }

    public BigDecimal getCouponsDiscountPrice() {
        return couponsDiscountPrice;
    }

    public void setCouponsDiscountPrice(BigDecimal couponsDiscountPrice) {
        this.couponsDiscountPrice = couponsDiscountPrice;
    }

    public BigDecimal getAfterCouponsDiscountPrice() {
        return afterCouponsDiscountPrice;
    }

    public void setAfterCouponsDiscountPrice(BigDecimal afterCouponsDiscountPrice) {
        this.afterCouponsDiscountPrice = afterCouponsDiscountPrice;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}