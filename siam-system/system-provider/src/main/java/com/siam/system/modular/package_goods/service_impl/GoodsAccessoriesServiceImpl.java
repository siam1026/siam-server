package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.GoodsAccessories;
import com.siam.system.modular.package_goods.mapper.GoodsAccessoriesMapper;
import com.siam.system.modular.package_goods.service.GoodsAccessoriesService;
import com.siam.system.modular.package_goods.entity.GoodsAccessories;
import com.siam.system.modular.package_goods.mapper.GoodsAccessoriesMapper;
import com.siam.system.modular.package_goods.service.GoodsAccessoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GoodsAccessoriesServiceImpl implements GoodsAccessoriesService {

    @Autowired
    private GoodsAccessoriesMapper goodsAccessoriesMapper;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        goodsAccessoriesMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(GoodsAccessories goodsAccessories) {
        goodsAccessoriesMapper.insertSelective(goodsAccessories);
    }

    @Override
    public GoodsAccessories selectByPrimaryKey(Integer id) {
        return goodsAccessoriesMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(GoodsAccessories goodsAccessories) {
        goodsAccessoriesMapper.updateByPrimaryKeySelective(goodsAccessories);
    }

    @Override
    public Page getListByPage(int pageNo, int pageSize, GoodsAccessories goodsAccessories) {
        Page<Map<String, Object>> page = goodsAccessoriesMapper.getListByPage(new Page(pageNo, pageSize), goodsAccessories);
        return page;
    }

    @Override
    public GoodsAccessories selectByName(String name) {
        return goodsAccessoriesMapper.selectByName(name);
    }
}
