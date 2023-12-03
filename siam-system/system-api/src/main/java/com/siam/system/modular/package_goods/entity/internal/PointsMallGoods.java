package com.siam.system.modular.package_goods.entity.internal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName("tb_points_mall_goods")
@ApiModel(value = "商品表")
public class PointsMallGoods {

    @TableField(select = false)
    private Integer menuId;

    /**
     * 状态 1=待上架 2=已上架 3=已下架 4=售罄
     */
    public static final int STATUS_WAIT_ON_SHELF = 1;
    public static final int STATUS_ON_SHELF = 2;
    public static final int STATUS_OFF_SHELF = 3;
    public static final int STATUS_SELL_OUT = 4;

    private List<Integer> ids;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "商品名称")
    private String name;

    @ApiModelProperty(notes = "商品主图")
    private String mainImage;

    @ApiModelProperty(notes = "商品子图")
    private String subImages;

    @ApiModelProperty(notes = "商品详情")
    private String detail;

    @ApiModelProperty(notes = "详情图片")
    private String detailImages;

    @ApiModelProperty(notes = "一口价")
    private BigDecimal price;

    @ApiModelProperty(notes = "库存")
    private Integer stock;

    @ApiModelProperty(notes = "是否热门")
    private Boolean isHot;

    @ApiModelProperty(notes = "是否新品")
    private Boolean isNew;

    @ApiModelProperty(notes = "状态 1=上架 2=下架")
    private Integer status;

    @ApiModelProperty(notes = "是否开启促销 0-否 1-是")
    private Boolean isSale;

    @ApiModelProperty(notes = "折扣价")
    private BigDecimal salePrice;

    @ApiModelProperty(notes = "月销量")
    private Integer monthlySales;

    @ApiModelProperty(notes = "累计销量")
    private Integer totalSales;

    @ApiModelProperty(notes = "累计评价")
    private Integer totalComments;

    @ApiModelProperty(notes = "优惠名称")
    private String preferentialName;

    @ApiModelProperty(notes = "包装费")
    private BigDecimal packingCharges;

    @ApiModelProperty(notes = "制作时长(分钟)")
    private BigDecimal productTime;

    @ApiModelProperty(notes = "兑换商品所需积分数量")
    private Integer exchangePoints;

    @ApiModelProperty(notes = "排序号")
    private Integer sortNumber;

    @ApiModelProperty(notes = "创建时间")
    private Date createTime;

    @ApiModelProperty(notes = "修改时间")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage == null ? null : mainImage.trim();
    }

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages == null ? null : subImages.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getDetailImages() {
        return detailImages;
    }

    public void setDetailImages(String detailImages) {
        this.detailImages = detailImages == null ? null : detailImages.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(Integer monthlySales) {
        this.monthlySales = monthlySales;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public Integer getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Integer totalComments) {
        this.totalComments = totalComments;
    }

    public String getPreferentialName() {
        return preferentialName;
    }

    public void setPreferentialName(String preferentialName) {
        this.preferentialName = preferentialName == null ? null : preferentialName.trim();
    }

    public BigDecimal getPackingCharges() {
        return packingCharges;
    }

    public void setPackingCharges(BigDecimal packingCharges) {
        this.packingCharges = packingCharges;
    }

    public BigDecimal getProductTime() {
        return productTime;
    }

    public void setProductTime(BigDecimal productTime) {
        this.productTime = productTime;
    }

    public Integer getExchangePoints() {
        return exchangePoints;
    }

    public void setExchangePoints(Integer exchangePoints) {
        this.exchangePoints = exchangePoints;
    }
    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
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