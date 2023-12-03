package com.siam.system.modular.package_order.entity.internal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_points_mall_order_logistics")
public class PointsMallOrderLogistics {

    //系统默认插入的两条物流状态描述常量值
    public static final String DESCRIPTION_OF_FIRST_COMMIT_ORDER = "您已提交订单，请等待系统确认";
    public static final String DESCRIPTION_OF_SECOND_DELIVERED = "平台已发货，快递公司运输中";

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Long orderId;

    private String description;

    private Date descriptionTime;

    private Date createTime;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getDescriptionTime() {
        return descriptionTime;
    }

    public void setDescriptionTime(Date descriptionTime) {
        this.descriptionTime = descriptionTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}