package com.siam.system.modular.package_goods.model.vo;

import lombok.Data;

/**
 * 折扣优惠类(包含店铺满减、新人XX折这种活动这类的)
 */
@Data
public class PromotionVo {

    //折扣优惠类型 1=店铺满减
    private Integer type;

    //折扣优惠名称
    private String name;

}