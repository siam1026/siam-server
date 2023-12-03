package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.MenuGoodsRelation;
import com.siam.system.modular.package_goods.model.example.MenuGoodsRelationExample;

import java.util.List;

/**
 *  jianyang
 */
public interface MenuGoodsRelationService {

    int countByExample(MenuGoodsRelationExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(MenuGoodsRelation menuGoodsRelation);

    MenuGoodsRelation selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(MenuGoodsRelation menuGoodsRelation);

    Page getListByPage(int pageNo, int pageSize, MenuGoodsRelation menuGoodsRelation);

    List<MenuGoodsRelation> selectByExample(MenuGoodsRelationExample example);

    void deleteByGoodsId(int goodsId);

}