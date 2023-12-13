package com.siam.system.modular.package_order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.entity.DeliveryAddress;
import com.siam.system.modular.package_order.model.example.DeliveryAddressExample;

import java.util.List;

public interface DeliveryAddressService {
    int countByExample(DeliveryAddressExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(DeliveryAddress record);

    List<DeliveryAddress> selectByExample(DeliveryAddressExample example);

    DeliveryAddress selectByPrimaryKey(Integer id);

    void updateByExampleSelective(DeliveryAddress record, DeliveryAddressExample example);

    void updateByPrimaryKeySelective(DeliveryAddress record);

    Page getListByPage(int pageNo, int pageSize, DeliveryAddress deliveryAddress);

    void updateIsDefaultExclusion(Integer id, Integer memberId);
}