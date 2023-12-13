package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.FullReductionRule;

import java.util.List;

public interface FullReductionRuleService {

    void deleteByPrimaryKey(Integer id);

    void insertSelective(FullReductionRule fullReductionRule);

    FullReductionRule selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(FullReductionRule fullReductionRule);

    Page<FullReductionRule> getListByPage(int pageNo, int pageSize, FullReductionRule fullReductionRule);

    List<FullReductionRule> selectByShopId(FullReductionRule fullReductionRule);
}
