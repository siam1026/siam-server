package com.siam.system.modular.package_goods.model.dto.internal;

import com.siam.system.modular.package_goods.entity.internal.PointsMallGoodsSpecification;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoodsSpecification;
import io.swagger.annotations.ApiModelProperty;

public class PointsMallGoodsSpecificationDto11 extends PointsMallGoodsSpecification {
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