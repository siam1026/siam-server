package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.siam.system.modular.package_goods.entity.Shop;

import java.math.BigDecimal;
import java.util.Map;

public interface ShopService extends IService<Shop> {

    Page<Shop> getListByPage(int pageNo, int pageSize, Shop shop);

    Page<Map<String, Object>> getMapListByPageJoinMerchant(int pageNo, int pageSize, Shop shop);

    Page<Map<String, Object>> getMapListByPage(int pageNo, int pageSize, Shop shop);

    Shop selectByName(String name);

    Shop selectByMerchantId(Integer merchantId);

    /**
     * 计算配送费
     *
     * @param shop 进行配送的店铺
     * @param deliveryDistance 距离公里数
     * @return
     * @author 暹罗
     */
    BigDecimal selectDeliveryFee(Shop shop, BigDecimal deliveryDistance);

    Page<Shop> selectByDistance(int pageNo, int pageSize, BigDecimal positionLongitude, BigDecimal positionLatitude, BigDecimal deliveryDistanceLimit);

    Page<Map<String, Object>> selectMapByDistance(int pageNo, int pageSize, BigDecimal positionLongitude, BigDecimal positionLatitude, BigDecimal deliveryDistanceLimit);
}