package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.ShoppingCart;
import com.siam.system.modular.package_goods.mapper.ShoppingCartMapper;
import com.siam.system.modular.package_goods.model.example.ShoppingCartExample;
import com.siam.system.modular.package_goods.service.ShoppingCartService;
import com.siam.system.modular.package_goods.entity.ShoppingCart;
import com.siam.system.modular.package_goods.model.example.ShoppingCartExample;
import com.siam.system.modular.package_goods.mapper.ShoppingCartMapper;
import com.siam.system.modular.package_goods.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    public int countByExample(ShoppingCartExample example){
        return shoppingCartMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        shoppingCartMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(ShoppingCart record){
        shoppingCartMapper.insertSelective(record);
    }

    public List<ShoppingCart> selectByExample(ShoppingCartExample example){
        return shoppingCartMapper.selectByExample(example);
    }

    public ShoppingCart selectByPrimaryKey(Integer id){
        return shoppingCartMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(ShoppingCart record, ShoppingCartExample example){
        shoppingCartMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(ShoppingCart record){
        shoppingCartMapper.updateByPrimaryKeySelective(record);
    }

    public Page getListByPage(int pageNo, int pageSize, ShoppingCart shoppingCart) {
        Page<Map<String, Object>> page = shoppingCartMapper.getListByPage(new Page(pageNo, pageSize), shoppingCart);
        return page;
    }

    public Page<Map<String, Object>> getListByPageJoinGoods(int pageNo, int pageSize, ShoppingCart shoppingCart) {
        Page<Map<String, Object>> page = shoppingCartMapper.getListByPageJoinGoods(new Page(pageNo, pageSize), shoppingCart);
        return page;
    }

    @Override
    public int countByIdListAndMemberId(List<Integer> idList, Integer memberId) {
        return shoppingCartMapper.countByIdListAndMemberId(idList, memberId);
    }

    @Override
    public int batchDeleteByIdList(List<Integer> idList) {
        return shoppingCartMapper.batchDeleteByIdList(idList);
    }

    @Override
    public void updateIsGoodsExistsTo0ByGoodsId(int goodsId) {
        shoppingCartMapper.updateIsGoodsExistsTo0ByGoodsId(goodsId);
    }

    @Override
    public int selectCountGoodsNumber(Integer shopId, Date startTime, Date endTime) {
        return shoppingCartMapper.selectCountGoodsNumber(shopId, startTime, endTime);
    }
}