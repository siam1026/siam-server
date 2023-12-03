package com.siam.system.modular.package_goods.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallGoodsSpecificationOptionDto;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoodsSpecificationOption;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallGoodsSpecificationOptionExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PointsMallGoodsSpecificationOptionService {
    int countByExample(PointsMallGoodsSpecificationOptionExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(PointsMallGoodsSpecificationOption record);

    List<PointsMallGoodsSpecificationOption> selectByExample(PointsMallGoodsSpecificationOptionExample example);

    PointsMallGoodsSpecificationOption selectByPrimaryKey(Integer id);

    void updateByExampleSelective(PointsMallGoodsSpecificationOption record, PointsMallGoodsSpecificationOptionExample example);

    void updateByPrimaryKeySelective(PointsMallGoodsSpecificationOption record);

    Page<PointsMallGoodsSpecificationOption> getListByPage(int pageNo, int pageSize, PointsMallGoodsSpecificationOption goodsSpecificationOption);

    Page<Map<String, Object>> getListByPageJoinPointsMallGoods(int pageNo, int pageSize, PointsMallGoodsSpecificationOptionDto goodsSpecificationOptionDto);

    int selectMaxSortNumberByPointsMallGoodsSpecificationId(Integer specificationId);

    BigDecimal selectSumPriceByGoodsIdAndName(Integer goodsId, List<String> nameList);

//    /**
//     * 修改商品辅料时，级联修改商品规格选项的价格、库存
//     * @param goodsAccessories
//     */
//    void updateByPointsMallGoodsAccessories(PointsMallGoodsAccessories goodsAccessories);

    void deleteByPointsMallGoodsId(int goodsId);
}