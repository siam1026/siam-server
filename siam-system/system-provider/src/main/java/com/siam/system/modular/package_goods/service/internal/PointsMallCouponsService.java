package com.siam.system.modular.package_goods.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallCouponsDto;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCoupons;

import java.util.Map;

public interface PointsMallCouponsService {

    void deleteByPrimaryKey(Integer id);

    void insertSelective(PointsMallCoupons coupons);

    Map selectCouponsAndGoodsByPrimaryKey(Integer id);

    PointsMallCoupons selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(PointsMallCoupons coupons);

    Page<PointsMallCoupons> getListByPage(int pageNo, int pageSize, PointsMallCoupons coupons);

    Page<Map<String, Object>> getMapListByPage(int pageNo, int pageSize, PointsMallCoupons coupons);

    Page<Map<String, Object>> getListJoinPointsMallCouponsShopRelationByPage(int pageNo, int pageSize, PointsMallCouponsDto couponsDto);

    void updatePointsMallCouponsEndTime(PointsMallCoupons coupons);

}
