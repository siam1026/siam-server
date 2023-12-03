package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.FullReductionRule;
import com.siam.system.modular.package_goods.mapper.FullReductionRuleMapper;
import com.siam.system.modular.package_goods.service.FullReductionRuleService;
import com.siam.system.modular.package_goods.entity.FullReductionRule;
import com.siam.system.modular.package_goods.mapper.FullReductionRuleMapper;
import com.siam.system.modular.package_goods.service.FullReductionRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FullReductionRuleServiceImpl implements FullReductionRuleService {

    @Autowired
    private FullReductionRuleMapper fullReductionRuleMapper;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        fullReductionRuleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(FullReductionRule fullReductionRule) {
        fullReductionRule.setCreateTime(new Date());
        fullReductionRuleMapper.insertSelective(fullReductionRule);
    }

    @Override
    public FullReductionRule selectByPrimaryKey(Integer id) {
        FullReductionRule fullReductionRule = fullReductionRuleMapper.selectByPrimaryKey(id);
        return fullReductionRule;
    }

    @Override
    public void updateByPrimaryKeySelective(FullReductionRule fullReductionRule) {
        fullReductionRuleMapper.updateByPrimaryKeySelective(fullReductionRule);
    }

    @Override
    public Page<FullReductionRule> getListByPage(int pageNo, int pageSize, FullReductionRule fullReductionRule) {
        Page<FullReductionRule> page = fullReductionRuleMapper.getListByPage(new Page(pageNo, pageSize), fullReductionRule);
        return page;
    }

    @Override
    public List<FullReductionRule> selectByShopId(FullReductionRule fullReductionRule) {
        List<FullReductionRule> list = fullReductionRuleMapper.getListByPage(new Page(1, 20), fullReductionRule).getRecords();
        return list;
    }
}