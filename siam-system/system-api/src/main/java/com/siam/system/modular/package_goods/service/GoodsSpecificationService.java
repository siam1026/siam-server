package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.GoodsSpecification;
import com.siam.system.modular.package_goods.model.dto.GoodsSpecificationDto;
import com.siam.system.modular.package_goods.entity.GoodsSpecification;
import com.siam.system.modular.package_goods.model.example.GoodsSpecificationExample;

import java.util.List;
import java.util.Map;

public interface GoodsSpecificationService {
    int countByExample(GoodsSpecificationExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(GoodsSpecification record);

    List<GoodsSpecification> selectByExample(GoodsSpecificationExample example);

    GoodsSpecification selectByPrimaryKey(Integer id);

    void updateByExampleSelective(GoodsSpecification record, GoodsSpecificationExample example);

    void updateByPrimaryKeySelective(GoodsSpecification record);

    Page<GoodsSpecification> getListByPage(int pageNo, int pageSize, GoodsSpecification goodsSpecification);

    Page<Map<String, Object>> getListByPageJoinGoods(int pageNo, int pageSize, GoodsSpecificationDto goodsSpecificationDto);

    int selectMaxSortNumberByGoodsId(Integer goodsId);

    GoodsSpecification selectByGoodsIdAndName(Integer goodsId, String name);

    /**
     * 生成商品公共规格
     * @return
     */
    void insertPublicGoodsSpecification(int goodsId);

    void deleteByGoodsId(int goodsId);
}