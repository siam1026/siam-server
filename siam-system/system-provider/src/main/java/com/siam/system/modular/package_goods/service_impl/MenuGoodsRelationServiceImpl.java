package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.MenuGoodsRelation;
import com.siam.system.modular.package_goods.mapper.MenuGoodsRelationMapper;
import com.siam.system.modular.package_goods.model.example.MenuGoodsRelationExample;
import com.siam.system.modular.package_goods.service.MenuGoodsRelationService;
import com.siam.system.modular.package_goods.entity.MenuGoodsRelation;
import com.siam.system.modular.package_goods.model.example.MenuGoodsRelationExample;
import com.siam.system.modular.package_goods.mapper.MenuGoodsRelationMapper;
import com.siam.system.modular.package_goods.service.MenuGoodsRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MenuGoodsRelationServiceImpl implements MenuGoodsRelationService {

    @Autowired
    private MenuGoodsRelationMapper menuGoodsRelationMapper;

    @Override
    public int countByExample(MenuGoodsRelationExample example){
        return menuGoodsRelationMapper.countByExample(example);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        menuGoodsRelationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(MenuGoodsRelation menuGoodsRelation) {
        menuGoodsRelationMapper.insertSelective(menuGoodsRelation);
    }

    @Override
    public MenuGoodsRelation selectByPrimaryKey(Integer id) {
        return menuGoodsRelationMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(MenuGoodsRelation menuGoodsRelation) {
        menuGoodsRelationMapper.updateByPrimaryKeySelective(menuGoodsRelation);
    }

    @Override
    public Page getListByPage(int pageNo, int pageSize, MenuGoodsRelation menuGoodsRelation) {
        Page<Map<String, Object>> page = menuGoodsRelationMapper.getListByPage(new Page(pageNo, pageSize), menuGoodsRelation);
        return page;
    }

    @Override
    public List<MenuGoodsRelation> selectByExample(MenuGoodsRelationExample example){
        return menuGoodsRelationMapper.selectByExample(example);
    }

    @Override
    public void deleteByGoodsId(int goodsId) {
        menuGoodsRelationMapper.deleteByGoodsId(goodsId);
    }
}