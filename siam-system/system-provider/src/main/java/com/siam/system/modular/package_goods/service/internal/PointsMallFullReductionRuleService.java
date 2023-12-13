package com.siam.system.modular.package_goods.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.PointsMallFullReductionRule;

import java.util.List;

public interface PointsMallFullReductionRuleService {

    void deleteByPrimaryKey(Integer id);

    void insertSelective(PointsMallFullReductionRule fullReductionRule);

    PointsMallFullReductionRule selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(PointsMallFullReductionRule fullReductionRule);

    Page<PointsMallFullReductionRule> getListByPage(int pageNo, int pageSize, PointsMallFullReductionRule fullReductionRule);

    List<PointsMallFullReductionRule> selectByShopId(PointsMallFullReductionRule fullReductionRule);
}
