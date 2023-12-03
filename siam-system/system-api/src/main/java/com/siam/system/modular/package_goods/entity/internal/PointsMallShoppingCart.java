package com.siam.system.modular.package_goods.entity.internal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("tb_points_mall_shopping_cart")
@ApiModel(value = "购物车表")
public class PointsMallShoppingCart {

    private List<Integer> ids;

    private int type;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "用户id")
    private Integer memberId;

    @ApiModelProperty(notes = "商品id")
    private Integer goodsId;

    @ApiModelProperty(notes = "商品规格 JSON格式")
    private String specList;

    @ApiModelProperty(notes = "购买数量")
    private Integer number;

    @ApiModelProperty(notes = "商品是否有效 0=无效 1=有效")
    private Boolean isGoodsExists;

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

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getSpecList() {
        return specList;
    }

    public void setSpecList(String specList) {
        this.specList = specList == null ? null : specList.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean getIsGoodsExists() {
        return isGoodsExists;
    }

    public void setIsGoodsExists(Boolean isGoodsExists) {
        this.isGoodsExists = isGoodsExists;
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