package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.GoodsAccessories;

/**
 *  暹罗
 */
public interface GoodsAccessoriesService {

    void deleteByPrimaryKey(Integer id);

    void insertSelective(GoodsAccessories goodsAccessories);

    GoodsAccessories selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(GoodsAccessories goodsAccessories);

    Page getListByPage(int pageNo, int pageSize, GoodsAccessories goodsAccessories);

    GoodsAccessories selectByName(String name);

}
