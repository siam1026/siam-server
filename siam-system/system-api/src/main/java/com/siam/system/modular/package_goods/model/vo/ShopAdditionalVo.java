package com.siam.system.modular.package_goods.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ShopAdditionalVo {

    //服务评级
    /*private BigDecimal serviceRating;*/

    //月售数量
    private Integer latelyMonthlySales;

    //起送价格
    /*private BigDecimal startDeliveryPrice;*/

    //配送费
    private BigDecimal deliveryFee;

    //配送时长
    private String deliveryDurationText;

    //距离公里数
    private String deliveryDistanceText;

    //优惠活动列表
    private List<PromotionVo> promotionList;

}