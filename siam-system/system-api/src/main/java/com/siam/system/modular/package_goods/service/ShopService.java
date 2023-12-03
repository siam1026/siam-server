package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.Shop;
import com.siam.system.modular.package_goods.entity.Shop;
import com.siam.system.modular.package_goods.model.example.ShopExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ShopService {

    int insert(Shop record);

    int insertSelective(Shop record);

    int deleteByExample(ShopExample example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shop record);

    List<Shop> selectByExample(ShopExample example);

    Shop selectByPrimaryKey(Integer id);

    int countByExample(ShopExample example);

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