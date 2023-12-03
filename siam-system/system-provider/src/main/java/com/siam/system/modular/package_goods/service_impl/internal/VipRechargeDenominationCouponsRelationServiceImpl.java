package com.siam.system.modular.package_goods.service_impl.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeDenominationCouponsRelation;
import com.siam.system.modular.package_goods.mapper.CouponsMemberRelationMapper;
import com.siam.system.modular.package_goods.mapper.internal.VipRechargeDenominationCouponsRelationMapper;
import com.siam.system.modular.package_goods.model.example.internal.VipRechargeDenominationCouponsRelationExample;
import com.siam.system.modular.package_goods.service.internal.VipRechargeDenominationCouponsRelationService;
import com.siam.system.modular.package_goods.mapper.internal.VipRechargeDenominationCouponsRelationMapper;
import com.siam.system.modular.package_goods.mapper.CouponsMemberRelationMapper;
import com.siam.system.modular.package_goods.service.internal.VipRechargeDenominationCouponsRelationService;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeDenominationCouponsRelation;
import com.siam.system.modular.package_goods.model.example.internal.VipRechargeDenominationCouponsRelationExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VipRechargeDenominationCouponsRelationServiceImpl implements VipRechargeDenominationCouponsRelationService {

    @Autowired
    private VipRechargeDenominationCouponsRelationMapper vipRechargeDenominationCouponsRelationMapper;

    @Autowired
    private CouponsMemberRelationMapper couponsMemberRelationMapper;

    @Override
    public void insertSelective(VipRechargeDenominationCouponsRelation record) {
        record.setCreateTime(new Date());
        vipRechargeDenominationCouponsRelationMapper.insertSelective(record);
    }

    @Override
    public void insertSelective(Integer vipRechargeDenominationId, List<VipRechargeDenominationCouponsRelation> denominationCouponsRelationList) {
        //1、删除所有关系
        vipRechargeDenominationCouponsRelationMapper.deleteByVipRechargeDenominationId(vipRechargeDenominationId);

        //2、创建关系
        for (VipRechargeDenominationCouponsRelation denominationCouponsRelation : denominationCouponsRelationList) {
            VipRechargeDenominationCouponsRelation vipRechargeDenominationCouponsRelation = new VipRechargeDenominationCouponsRelation();
            vipRechargeDenominationCouponsRelation.setVipRechargeDenominationId(vipRechargeDenominationId);
            vipRechargeDenominationCouponsRelation.setCouponsId(denominationCouponsRelation.getCouponsId());
            vipRechargeDenominationCouponsRelation.setGiveQuantity(denominationCouponsRelation.getGiveQuantity());
            vipRechargeDenominationCouponsRelation.setCreateTime(new Date());
            vipRechargeDenominationCouponsRelationMapper.insertSelective(vipRechargeDenominationCouponsRelation);
        }
    }

    @Override
    public Page<VipRechargeDenominationCouponsRelation> getListByPage(int pageNo, int pageSize, VipRechargeDenominationCouponsRelation vipRechargeDenominationCouponsRelation) {
        Page<VipRechargeDenominationCouponsRelation> page = vipRechargeDenominationCouponsRelationMapper.getListByPage(new Page(pageNo, pageSize), vipRechargeDenominationCouponsRelation);

        return page;
    }

    @Override
    public void deleteByVipRechargeDenominationId(int vipRechargeDenominationId) {
        vipRechargeDenominationCouponsRelationMapper.deleteByVipRechargeDenominationId(vipRechargeDenominationId);
    }

    @Override
    public List<VipRechargeDenominationCouponsRelation> selectByExample(VipRechargeDenominationCouponsRelationExample example) {
        return vipRechargeDenominationCouponsRelationMapper.selectByExample(example);
    }
}
