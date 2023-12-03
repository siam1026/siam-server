package com.siam.system.modular.package_order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TravelingDistanceVo {

    //距离文本(XX米/公里)
    private String distanceText;

    //距离公里值
    private BigDecimal distanceValue;

    //驾车时长文本(XX分钟/小时/天)
    private String durationText;

    //驾车时长秒值
    private BigDecimal durationValue;

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

}