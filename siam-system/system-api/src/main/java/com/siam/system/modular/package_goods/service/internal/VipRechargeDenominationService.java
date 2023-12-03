package com.siam.system.modular.package_goods.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeDenomination;

/**
 *  jianyang
 */
public interface VipRechargeDenominationService {

    void deleteByPrimaryKey(Integer id);

    void insertSelective(VipRechargeDenomination vipRechargeDenomination);

    VipRechargeDenomination selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(VipRechargeDenomination vipRechargeDenomination);

    Page getListByPage(int pageNo, int pageSize, VipRechargeDenomination vipRechargeDenomination);

}
