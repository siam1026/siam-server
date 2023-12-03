package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.constant.BusinessType;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.entity.CouponsGoodsRelation;
import com.siam.system.modular.package_goods.mapper.CouponsGoodsRelationMapper;
import com.siam.system.modular.package_goods.mapper.CouponsMemberRelationMapper;
import com.siam.system.modular.package_goods.service.CouponsGoodsRelationService;
import com.siam.system.modular.package_goods.entity.CouponsGoodsRelation;
import com.siam.system.modular.package_goods.mapper.CouponsGoodsRelationMapper;
import com.siam.system.modular.package_goods.service.CouponsGoodsRelationService;
import com.siam.system.modular.package_goods.mapper.CouponsMemberRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponsGoodsRelationServiceImpl implements CouponsGoodsRelationService {

    @Autowired
    private CouponsGoodsRelationMapper couponsGoodsRelationMapper;

    @Autowired
    private CouponsMemberRelationMapper couponsMemberRelationMapper;

    @Override
    public void insertSelective(CouponsGoodsRelation record) {
        record.setCreateTime(new Date());
        couponsGoodsRelationMapper.insertSelective(record);
    }

    @Override
    public void insertSelective(Integer couponsId, List<Integer> goodsIdList) {
        if (couponsId.equals(BusinessType.NEW_PEOPLE_COUPONS_ID) || couponsId.equals(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID)) {
            throw new StoneCustomerException("新人优惠卷和推荐新人优惠卷为系统默认优惠卷，不允许操作");
        }

        /*Integer couponsMemberRelationCount = couponsMemberRelationMapper.getCountByCouponsId(couponsId);
        if (couponsMemberRelationCount != null && couponsMemberRelationCount > 0) {
            throw new StoneCustomerException("优惠卷已派发给用户，不能进行该操作");
        }*/

        //1、删除所有关系
        couponsGoodsRelationMapper.deleteByCouponsId(couponsId);

        //2、创建关系
        for (Integer goodsId : goodsIdList) {
            CouponsGoodsRelation couponsGoodsRelation = new CouponsGoodsRelation();
            couponsGoodsRelation.setCouponsId(couponsId);
            couponsGoodsRelation.setGoodsId(goodsId);
            couponsGoodsRelation.setCreateTime(new Date());
            couponsGoodsRelationMapper.insertSelective(couponsGoodsRelation);
        }
    }

    @Override
    public Page<CouponsGoodsRelation> getListByPage(int pageNo, int pageSize, CouponsGoodsRelation couponsGoodsRelation) {
        Page<CouponsGoodsRelation> page = couponsGoodsRelationMapper.getListByPage(new Page(pageNo, pageSize), couponsGoodsRelation);

        return page;
    }

    @Override
    public List<Integer> getGoodsIdByCouponsId(Integer couponsId) {
        List<Integer> list = couponsGoodsRelationMapper.getGoodsIdByCouponsId(couponsId);
        return list;
    }

    @Override
    public void deleteByGoodsId(int goodsId) {
        couponsGoodsRelationMapper.deleteByGoodsId(goodsId);
    }
}
