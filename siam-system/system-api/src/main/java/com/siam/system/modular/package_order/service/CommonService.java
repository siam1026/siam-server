package com.siam.system.modular.package_order.service;

import com.siam.system.modular.package_order.entity.TravelingDistanceVo;

import java.math.BigDecimal;

public interface CommonService {

    /**
     * 计算订单配送费
     */
    BigDecimal selectDeliveryFee(BigDecimal lngA, BigDecimal latA, Integer shopId);

    /**
     * 计算订单配送费
     */
    BigDecimal selectDeliveryFee(String addressStr, Integer shopId);

    /**
     * 计算两地的距离 / 用户当前定位与平台各店铺的距离
     */
    TravelingDistanceVo selectTravelingDistance(BigDecimal lngA, BigDecimal latA, BigDecimal lngB, BigDecimal latB);

    /**
     * 查询门店是否营业/店铺是否打烊
     *
     * @param shopId
     */
    Boolean selectIsOperatingOfShop(Integer shopId);

}