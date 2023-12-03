package com.siam.system.modular.package_goods.model.dto;

import com.siam.system.modular.package_goods.entity.GoodsSpecification;
import com.siam.system.modular.package_goods.entity.GoodsSpecification;
import io.swagger.annotations.ApiModelProperty;

public class GoodsSpecificationDto11 extends GoodsSpecification {
    @ApiModelProperty(notes = "商品名称")
    private String goodsName;

    @ApiModelProperty(notes = "商品主图")
    private String goodsMainImage;

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