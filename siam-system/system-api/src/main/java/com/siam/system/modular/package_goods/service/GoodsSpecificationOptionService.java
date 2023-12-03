package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.model.dto.GoodsSpecificationOptionDto;
import com.siam.system.modular.package_goods.entity.GoodsAccessories;
import com.siam.system.modular.package_goods.entity.GoodsSpecificationOption;
import com.siam.system.modular.package_goods.model.example.GoodsSpecificationOptionExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface GoodsSpecificationOptionService {
    int countByExample(GoodsSpecificationOptionExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(GoodsSpecificationOption record);

    List<GoodsSpecificationOption> selectByExample(GoodsSpecificationOptionExample example);

    GoodsSpecificationOption selectByPrimaryKey(Integer id);

    void updateByExampleSelective(GoodsSpecificationOption record, GoodsSpecificationOptionExample example);

    void updateByPrimaryKeySelective(GoodsSpecificationOption record);

    Page<GoodsSpecificationOption> getListByPage(int pageNo, int pageSize, GoodsSpecificationOption goodsSpecificationOption);

    Page<Map<String, Object>> getListByPageJoinGoods(int pageNo, int pageSize, GoodsSpecificationOptionDto goodsSpecificationOptionDto);

    int selectMaxSortNumberByGoodsSpecificationId(Integer specificationId);

    BigDecimal selectSumPriceByGoodsIdAndName(Integer goodsId, List<String> nameList);

    /**
     * 修改商品辅料时，级联修改商品规格选项的价格、库存
     * @param goodsAccessories
     */
    void updateByGoodsAccessories(GoodsAccessories goodsAccessories);

    void deleteByGoodsId(int goodsId);
}