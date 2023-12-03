package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.GoodsAccessories;
import com.siam.system.modular.package_goods.entity.GoodsSpecificationOption;
import com.siam.system.modular.package_goods.mapper.GoodsSpecificationOptionMapper;
import com.siam.system.modular.package_goods.model.dto.GoodsSpecificationOptionDto;
import com.siam.system.modular.package_goods.model.example.GoodsSpecificationOptionExample;
import com.siam.system.modular.package_goods.service.GoodsSpecificationOptionService;
import com.siam.system.modular.package_goods.entity.GoodsAccessories;
import com.siam.system.modular.package_goods.entity.GoodsSpecificationOption;
import com.siam.system.modular.package_goods.model.example.GoodsSpecificationOptionExample;
import com.siam.system.modular.package_goods.model.dto.GoodsSpecificationOptionDto;
import com.siam.system.modular.package_goods.mapper.GoodsSpecificationOptionMapper;
import com.siam.system.modular.package_goods.service.GoodsSpecificationOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class GoodsSpecificationOptionServiceImpl implements GoodsSpecificationOptionService {

    @Autowired
    private GoodsSpecificationOptionMapper goodsSpecificationOptionMapper;

    public int countByExample(GoodsSpecificationOptionExample example){
        return goodsSpecificationOptionMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        goodsSpecificationOptionMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(GoodsSpecificationOption record){
        goodsSpecificationOptionMapper.insertSelective(record);
    }

    public List<GoodsSpecificationOption> selectByExample(GoodsSpecificationOptionExample example){
        return goodsSpecificationOptionMapper.selectByExample(example);
    }

    public GoodsSpecificationOption selectByPrimaryKey(Integer id){
        return goodsSpecificationOptionMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(GoodsSpecificationOption record, GoodsSpecificationOptionExample example){
        goodsSpecificationOptionMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(GoodsSpecificationOption record){
        goodsSpecificationOptionMapper.updateByPrimaryKeySelective(record);
    }

    public Page<GoodsSpecificationOption> getListByPage(int pageNo, int pageSize, GoodsSpecificationOption goodsSpecificationOption) {
        Page<GoodsSpecificationOption> page = goodsSpecificationOptionMapper.getListByPage(new Page(pageNo, pageSize), goodsSpecificationOption);
        return page;
    }

    @Override
    public Page<Map<String, Object>> getListByPageJoinGoods(int pageNo, int pageSize, GoodsSpecificationOptionDto goodsSpecificationOptionDto) {
        Page<Map<String, Object>> page = goodsSpecificationOptionMapper.getListByPageJoinGoods(new Page(pageNo, pageSize), goodsSpecificationOptionDto);
        return page;
    }

    @Override
    public int selectMaxSortNumberByGoodsSpecificationId(Integer specificationId) {
        return goodsSpecificationOptionMapper.selectMaxSortNumberByGoodsSpecificationId(specificationId);
    }

    @Override
    public BigDecimal selectSumPriceByGoodsIdAndName(Integer goodsId, List<String> nameList) {
        return goodsSpecificationOptionMapper.selectSumPriceByGoodsIdAndName(goodsId, nameList);
    }

    @Override
    public void updateByGoodsAccessories(GoodsAccessories goodsAccessories) {
        goodsSpecificationOptionMapper.updateByGoodsAccessories(goodsAccessories);
    }

    @Override
    public void deleteByGoodsId(int goodsId) {
        goodsSpecificationOptionMapper.deleteByGoodsId(goodsId);
    }
}