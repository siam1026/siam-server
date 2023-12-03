package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.CouponsShopRelation;

import java.util.List;

public interface CouponsShopRelationService {

    void insertSelective(CouponsShopRelation record);

    void insertSelective(Integer couponsId, List<Integer> shopIdList);

    Page<CouponsShopRelation> getListByPage(int pageNo, int pageSize, CouponsShopRelation couponsShopRelation);

    void deleteByShopId(int shopId);

    List<Integer> getShopIdByCouponsId(Integer couponsId);
}