package com.siam.system.modular.package_goods.model.dto.internal;

import com.siam.system.modular.package_goods.entity.internal.PointsMallGoodsSpecificationOption;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoodsSpecificationOption;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class PointsMallGoodsSpecificationOptionDto extends PointsMallGoodsSpecificationOption {
    @ApiModelProperty(notes = "规格名称")
    private String specificationName;

    @ApiModelProperty(notes = "商品名称")
    private String goodsName;

    @ApiModelProperty(notes = "商品主图")
    private String goodsMainImage;

    public PointsMallGoodsSpecificationOptionDto() {
    }

    public PointsMallGoodsSpecificationOptionDto(String specificationName, String name, BigDecimal price) {
        this.specificationName = specificationName;
        super.setName(name);
        super.setPrice(price);
    }

    public String getSpecificationName() {
        return specificationName;
    }

    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsMainImage() {
        return goodsMainImage;
    }

    public void setGoodsMainImage(String goodsMainImage) {
        this.goodsMainImage = goodsMainImage;
    }
}