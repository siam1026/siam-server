package com.siam.system.modular.package_goods.model.vo;

import com.siam.system.modular.package_goods.entity.FullReductionRule;
import com.siam.system.modular.package_goods.entity.Shop;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ShopDetailVo {

    //门店信息
    private Shop shop;

    //优惠活动列表
    private List<PromotionVo> promotionList;

    //满减规则列表
    private List<FullReductionRule> fullReductionRuleList;

    //当前门店距离用户定位地址是否超出配送范围
    private Boolean isOutofDeliveryRange;

    //当前门店是否营业
    private Boolean isOperatingOfShop;

    //配送费
    private BigDecimal deliveryFee;

    //配送时长
    private String deliveryDurationText;

    //距离公里数
    private String deliveryDistanceText;
}