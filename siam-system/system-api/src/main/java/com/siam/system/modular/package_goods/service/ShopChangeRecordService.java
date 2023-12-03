package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.ShopChangeRecord;
import com.siam.system.modular.package_goods.model.example.ShopChangeRecordExample;

/**
 *  jianyang
 */
public interface ShopChangeRecordService {

    int countByExample(ShopChangeRecordExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(ShopChangeRecord shopChangeRecord);

    ShopChangeRecord selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(ShopChangeRecord shopChangeRecord);

    Page getListByPage(int pageNo, int pageSize, ShopChangeRecord shopChangeRecord);

    Page getListByPageJoinShop(int pageNo, int pageSize, ShopChangeRecord shopChangeRecord);

    ShopChangeRecord selectLastestByShopId(Integer shopId);
}
