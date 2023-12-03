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
@TableName("tb_goods_rawmaterial_relation")
@ApiModel(value = "原料配比表/商品原料关联表")
public class GoodsRawmaterialRelation {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "商品id")
    private Integer goodsId;

    @ApiModelProperty(notes = "原料id")
    private Integer rawmaterialId;

    @ApiModelProperty(notes = "耗量")
    private BigDecimal consumedQuantity;

    @ApiModelProperty(notes = "创建时间")
    private Date createTime;

    @ApiModelProperty(notes = "修改时间")
    private Date updateTime;

    private String goodsName;

    //页码
    private Integer pageNo = 1;

    //页面大小
    private Integer pageSize = 20;

    String goodsIdListStr;
    String relationListStr;

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

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getRawmaterialId() {
        return rawmaterialId;
    }

    public void setRawmaterialId(Integer rawmaterialId) {
        this.rawmaterialId = rawmaterialId;
    }

    public BigDecimal getConsumedQuantity() {
        return consumedQuantity;
    }

    public void setConsumedQuantity(BigDecimal consumedQuantity) {
        this.consumedQuantity = consumedQuantity;
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