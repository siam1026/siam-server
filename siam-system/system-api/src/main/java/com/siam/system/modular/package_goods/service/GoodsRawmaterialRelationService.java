package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.GoodsRawmaterialRelation;
import com.siam.system.modular.package_goods.model.example.GoodsRawmaterialRelationExample;
import com.siam.system.modular.package_order.entity.OrderDetail;

import java.util.List;

/**
 *  jianyang
 */
public interface GoodsRawmaterialRelationService {

    int countByExample(GoodsRawmaterialRelationExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(GoodsRawmaterialRelation goodsRawmaterialRelation);

    GoodsRawmaterialRelation selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(GoodsRawmaterialRelation goodsRawmaterialRelation);

    Page getListByPage(int pageNo, int pageSize, GoodsRawmaterialRelation goodsRawmaterialRelation);

    void deleteByGoodsId(int goodsId);

    Page findGoodsRawmaterialRelation(int pageNo, int pageSize, String goodsName);

    Page selectByGoodsId(int pageNo, int pageSize, int goodsId);

    List<GoodsRawmaterialRelation> selectByExample(GoodsRawmaterialRelationExample example);

    void updateRawmaterialConsumedQuantityByOrderDetailList(List<OrderDetail> orderDetailList);

}