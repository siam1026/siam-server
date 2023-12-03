package com.siam.system.modular.package_goods.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.PointsMallMenuGoodsRelation;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallMenuGoodsRelationExample;

import java.util.List;

/**
 *  jianyang
 */
public interface PointsMallMenuGoodsRelationService {

    int countByExample(PointsMallMenuGoodsRelationExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(PointsMallMenuGoodsRelation menuPointsMallGoodsRelation);

    PointsMallMenuGoodsRelation selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(PointsMallMenuGoodsRelation menuPointsMallGoodsRelation);

    Page getListByPage(int pageNo, int pageSize, PointsMallMenuGoodsRelation menuPointsMallGoodsRelation);

    List<PointsMallMenuGoodsRelation> selectByExample(PointsMallMenuGoodsRelationExample example);

    void deleteByPointsMallGoodsId(int goodsId);

}