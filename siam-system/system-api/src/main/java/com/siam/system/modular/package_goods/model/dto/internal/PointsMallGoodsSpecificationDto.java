package com.siam.system.modular.package_goods.model.dto.internal;

import com.siam.system.modular.package_goods.entity.internal.PointsMallGoods;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoods;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class PointsMallGoodsSpecificationDto extends PointsMallGoods {
    @ApiModelProperty(notes = "规格id")
    private Integer specificationId;

    @ApiModelProperty(notes = "规格名称")
    private String specificationName;

    @ApiModelProperty(notes = "商品规格选项id")
    private Integer specificationOptionId;

    @ApiModelProperty(notes = "商品规格选项名称")
    private String specificationOptionName;

    @ApiModelProperty(notes = "商品规格选项单价/加价金额")
    private BigDecimal specificationOptionPrice;

    @ApiModelProperty(notes = "商品规格选项库存 1代表有货，0代表无货")
    private Integer specificationOptionStock;

    public Integer getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Integer specificationId) {
        this.specificationId = specificationId;
    }

    public String getSpecificationName() {
        return specificationName;
    }

    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName;
    }

    public Integer getSpecificationOptionId() {
        return specificationOptionId;
    }

    public void setSpecificationOptionId(Integer specificationOptionId) {
        this.specificationOptionId = specificationOptionId;
    }

    public String getSpecificationOptionName() {
        return specificationOptionName;
    }

    public void setSpecificationOptionName(String specificationOptionName) {
        this.specificationOptionName = specificationOptionName;
    }

    public BigDecimal getSpecificationOptionPrice() {
        return specificationOptionPrice;
    }

    public void setSpecificationOptionPrice(BigDecimal specificationOptionPrice) {
        this.specificationOptionPrice = specificationOptionPrice;
    }

    public Integer getSpecificationOptionStock() {
        return specificationOptionStock;
    }

    public void setSpecificationOptionStock(Integer specificationOptionStock) {
        this.specificationOptionStock = specificationOptionStock;
    }
}