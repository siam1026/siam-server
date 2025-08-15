package com.siam.system.modular.package_goods.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.siam.system.modular.package_goods.entity.GoodsSpecificationOption;
import com.siam.system.modular.package_goods.entity.GoodsSpecificationOption;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

public class GoodsSpecificationOptionDto extends GoodsSpecificationOption {
    @ApiModelProperty(notes = "规格名称")
    private String specificationName;

    @ApiModelProperty(notes = "商品名称")
    private String goodsName;

    @ApiModelProperty(notes = "商品主图")
    private String goodsMainImage;

    List<Integer> ids;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public GoodsSpecificationOptionDto() {
    }

    public GoodsSpecificationOptionDto(String specificationName, String name, BigDecimal price) {
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