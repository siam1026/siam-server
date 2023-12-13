package com.siam.system.modular.package_goods.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCouponsGoodsRelation;

import java.util.List;

public interface PointsMallCouponsGoodsRelationService {

    void insertSelective(PointsMallCouponsGoodsRelation record);

    void insertSelective(Integer couponsId, List<Integer> goodsIdList);

    Page<PointsMallCouponsGoodsRelation> getListByPage(int pageNo, int pageSize, PointsMallCouponsGoodsRelation couponsGoodsRelation);

    List<Integer> getGoodsIdByCouponsId(Integer couponsId);

    void deleteByPointsMallGoodsId(int goodsId);

}
