package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.MerchantRecommendGoods;
import com.siam.system.modular.package_goods.model.example.MerchantRecommendGoodsExample;

import java.util.List;
import java.util.Map;

public interface MerchantRecommendGoodsService {

    int insert(MerchantRecommendGoods record);

    int insertSelective(MerchantRecommendGoods record);

    int deleteByExample(MerchantRecommendGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MerchantRecommendGoods record);

    List<MerchantRecommendGoods> selectByExample(MerchantRecommendGoodsExample example);

    MerchantRecommendGoods selectByPrimaryKey(Integer id);

    int countByExample(MerchantRecommendGoodsExample example);

    Page<MerchantRecommendGoods> getListByPage(int pageNo, int pageSize, MerchantRecommendGoods merchantRecommendGoods);

    Page<Map<String, Object>> getListByPageJoinGoods(MerchantRecommendGoods merchantRecommendGoods);
}