package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.CouponsShopRelation;
import com.siam.system.modular.package_goods.mapper.CouponsMemberRelationMapper;
import com.siam.system.modular.package_goods.mapper.CouponsShopRelationMapper;
import com.siam.system.modular.package_goods.service.CouponsShopRelationService;
import com.siam.system.modular.package_goods.mapper.CouponsShopRelationMapper;
import com.siam.system.modular.package_goods.mapper.CouponsMemberRelationMapper;
import com.siam.system.modular.package_goods.service.CouponsShopRelationService;
import com.siam.package_common.constant.BusinessType;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.entity.CouponsShopRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponsShopRelationServiceImpl implements CouponsShopRelationService {

    @Autowired
    private CouponsShopRelationMapper couponsShopRelationMapper;

    @Autowired
    private CouponsMemberRelationMapper couponsMemberRelationMapper;

    @Override
    public void insertSelective(CouponsShopRelation record) {
        couponsShopRelationMapper.insertSelective(record);
    }

    @Override
    public void insertSelective(Integer couponsId, List<Integer> shopIdList) {
        if (couponsId.equals(BusinessType.NEW_PEOPLE_COUPONS_ID) || couponsId.equals(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID)) {
            throw new StoneCustomerException("新人优惠卷和推荐新人优惠卷为系统默认优惠卷，不允许操作");
        }

        /*Integer couponsMemberRelationCount = couponsMemberRelationMapper.getCountByCouponsId(couponsId);
        if (couponsMemberRelationCount != null && couponsMemberRelationCount > 0) {
            throw new StoneCustomerException("优惠卷已派发给用户，不能进行该操作");
        }*/

        //1、删除所有关系
        couponsShopRelationMapper.deleteByCouponsId(couponsId);

        //2、创建关系
        for (Integer shopId : shopIdList) {
            CouponsShopRelation couponsShopRelation = new CouponsShopRelation();
            couponsShopRelation.setCouponsId(couponsId);
            couponsShopRelation.setShopId(shopId);
            couponsShopRelation.setCreateTime(new Date());
            couponsShopRelationMapper.insertSelective(couponsShopRelation);
        }
    }

    @Override
    public Page<CouponsShopRelation> getListByPage(int pageNo, int pageSize, CouponsShopRelation couponsShopRelation) {
        Page<CouponsShopRelation> page = couponsShopRelationMapper.getListByPage(new Page(pageNo, pageSize), couponsShopRelation);

        return page;
    }

    @Override
    public void deleteByShopId(int shopId) {
        couponsShopRelationMapper.deleteByShopId(shopId);
    }

    @Override
    public List<Integer> getShopIdByCouponsId(Integer couponsId) {
        List<Integer> list = couponsShopRelationMapper.getShopIdByCouponsId(couponsId);
        return list;
    }
}