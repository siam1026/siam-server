package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.siam.system.modular.package_goods.entity.Advertisement;
import com.siam.system.modular.package_goods.model.param.AdvertisementParam;

/**
 *  jianyang
 */
public interface AdvertisementService extends IService<Advertisement> {

    void deleteByPrimaryKey(AdvertisementParam param);

    void insertSelective(AdvertisementParam param);

    Advertisement selectByPrimaryKey(AdvertisementParam param);

    void updateByPrimaryKeySelective(AdvertisementParam param);

    Page getListByPage(AdvertisementParam param);

}