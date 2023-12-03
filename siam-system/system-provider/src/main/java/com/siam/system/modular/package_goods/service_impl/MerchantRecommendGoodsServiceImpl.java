package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.MerchantRecommendGoods;
import com.siam.system.modular.package_goods.mapper.MerchantRecommendGoodsMapper;
import com.siam.system.modular.package_goods.model.example.MerchantRecommendGoodsExample;
import com.siam.system.modular.package_goods.service.MerchantRecommendGoodsService;
import com.siam.system.modular.package_goods.entity.MerchantRecommendGoods;
import com.siam.system.modular.package_goods.mapper.MerchantRecommendGoodsMapper;
import com.siam.system.modular.package_goods.model.example.MerchantRecommendGoodsExample;
import com.siam.system.modular.package_goods.service.MerchantRecommendGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MerchantRecommendGoodsServiceImpl implements MerchantRecommendGoodsService {

    @Autowired
    private MerchantRecommendGoodsMapper merchantRecommendGoodsMapper;

    @Override
    public int insert(MerchantRecommendGoods record) {
        return merchantRecommendGoodsMapper.insert(record);
    }

    @Override
    public int insertSelective(MerchantRecommendGoods record) {
        return merchantRecommendGoodsMapper.insertSelective(record);
    }

    @Override
    public int deleteByExample(MerchantRecommendGoodsExample example) {
        return merchantRecommendGoodsMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return merchantRecommendGoodsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MerchantRecommendGoods record) {
        return merchantRecommendGoodsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<MerchantRecommendGoods> selectByExample(MerchantRecommendGoodsExample example) {
        return merchantRecommendGoodsMapper.selectByExample(example);
    }

    @Override
    public MerchantRecommendGoods selectByPrimaryKey(Integer id) {
        return merchantRecommendGoodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int countByExample(MerchantRecommendGoodsExample example) {
        return merchantRecommendGoodsMapper.countByExample(example);
    }

    @Override
    public Page<MerchantRecommendGoods> getListByPage(int pageNo, int pageSize, MerchantRecommendGoods merchantRecommendGoods) {
        Page<MerchantRecommendGoods> page = merchantRecommendGoodsMapper.getListByPage(new Page(pageNo, pageSize), merchantRecommendGoods);
        return page;
    }

    @Override
    public Page<Map<String, Object>> getListByPageJoinGoods(MerchantRecommendGoods merchantRecommendGoods) {
        Page<Map<String, Object>> page = merchantRecommendGoodsMapper.getListByPageJoinGoods(new Page(merchantRecommendGoods.getPageNo(), merchantRecommendGoods.getPageSize()), merchantRecommendGoods);
        return page;
    }
}