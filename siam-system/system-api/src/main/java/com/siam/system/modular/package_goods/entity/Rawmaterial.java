package com.siam.system.modular.package_goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tb_rawmaterial")
@ApiModel(value = "原料表")
public class Rawmaterial {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "原料名称")
    private String name;

    @ApiModelProperty(notes = "原料主图")
    private String mainImage;

    @ApiModelProperty(notes = "原料描述")
    private String description;

    @ApiModelProperty(notes = "采购单位")
    private String unit;

    @ApiModelProperty(notes = "采购单价")
    private BigDecimal price;

    @ApiModelProperty(notes = "库存")
    private BigDecimal stock;

    @ApiModelProperty(notes = "库存过低线/库存下限")
    private BigDecimal stockLowerLimit;

    @ApiModelProperty(notes = "库存超出线/库存上限")
    private BigDecimal stockUpperLimit;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public BigDecimal getStockLowerLimit() {
        return stockLowerLimit;
    }

    public void setStockLowerLimit(BigDecimal stockLowerLimit) {
        this.stockLowerLimit = stockLowerLimit;
    }

    public BigDecimal getStockUpperLimit() {
        return stockUpperLimit;
    }

    public void setStockUpperLimit(BigDecimal stockUpperLimit) {
        this.stockUpperLimit = stockUpperLimit;
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