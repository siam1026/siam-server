package com.siam.system.modular.package_goods.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeDenominationCouponsRelation;
import com.siam.system.modular.package_goods.model.example.internal.VipRechargeDenominationCouponsRelationExample;

import java.util.List;

public interface VipRechargeDenominationCouponsRelationService {

    void insertSelective(VipRechargeDenominationCouponsRelation record);

    void insertSelective(Integer vipRechargeDenominationId, List<VipRechargeDenominationCouponsRelation> denominationCouponsRelationList);

    Page<VipRechargeDenominationCouponsRelation> getListByPage(int pageNo, int pageSize, VipRechargeDenominationCouponsRelation vipRechargeDenominationCouponsRelation);

    void deleteByVipRechargeDenominationId(int vipRechargeDenominationId);

    List<VipRechargeDenominationCouponsRelation> selectByExample(VipRechargeDenominationCouponsRelationExample example);

}
