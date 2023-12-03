package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.Coupons;
import com.siam.system.modular.package_goods.entity.Goods;
import com.siam.system.modular.package_goods.entity.Shop;
import com.siam.system.modular.package_goods.mapper.*;
import com.siam.system.modular.package_goods.model.dto.CouponsDto;
import com.siam.system.modular.package_goods.service.CouponsService;
import com.siam.system.modular.package_goods.mapper.*;
import com.siam.package_common.constant.BusinessType;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.model.dto.CouponsDto;
import com.siam.system.modular.package_goods.service.CouponsService;
import com.siam.system.modular.package_goods.entity.Coupons;
import com.siam.system.modular.package_goods.entity.Goods;
import com.siam.system.modular.package_goods.entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CouponsServiceImpl implements CouponsService {

    @Autowired
    private CouponsMapper couponsMapper;

    @Autowired
    private CouponsGoodsRelationMapper couponsGoodsRelationMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private CouponsMemberRelationMapper couponsMemberRelationMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        if (id.equals(BusinessType.NEW_PEOPLE_COUPONS_ID) || id.equals(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID)) {
            throw new StoneCustomerException("新人优惠卷和推荐新人优惠卷为系统默认优惠卷，不允许删除");
        }

        /*Integer couponsMemberRelationCount = couponsMemberRelationMapper.getCountByCouponsId(id);
        if (couponsMemberRelationCount != null && couponsMemberRelationCount > 0) {
            throw new StoneCustomerException("优惠卷已派发给用户，不能删除");
        }*/

        //1、删除（修改是否删除字段）对象
        //couponsMapper.deleteByPrimaryKey(id);
        Coupons coupons = couponsMapper.selectByPrimaryKey(id);
        coupons.setIsDelete(true);
        coupons.setUpdateTime(new Date());
        couponsMapper.updateByPrimaryKeySelective(coupons);

        //2、删除关系
        couponsGoodsRelationMapper.deleteByCouponsId(id);

        //3、删除（修改生效字段）用户关系
        couponsMemberRelationMapper.updateValidToFalseByCouponsId(id);
    }

    @Override
    public void insertSelective(Coupons coupons) {
        //1、插入对象
        coupons.setCreateTime(new Date());
        coupons.setIsDelete(false);
        couponsMapper.insertSelective(coupons);

        /*//2、插入关系
        for (Integer goodsId : goodsIdList) {
            CouponsGoodsRelation couponsGoodsRelation = new CouponsGoodsRelation();
            couponsGoodsRelation.setCouponsId(couponsId);
            couponsGoodsRelation.setGoodsId(goodsId);
            couponsGoodsRelation.setCreateTime(new Date());
            couponsGoodsRelationMapper.insertSelective(couponsGoodsRelation);
        }*/
    }

    @Override
    public Map selectCouponsAndGoodsByPrimaryKey(Integer id) {
        //1、查询对象
        Coupons coupons = couponsMapper.selectByPrimaryKey(id);

        //2、查询对象对应的商品
        List<Goods> goodsList = goodsMapper.getListByCouponsId(id);

        //3、查询对象对应的店铺
        List<Shop> shopList = shopMapper.getListByCouponsId(id);

        Map map = new HashMap();
        map.put("coupons", coupons);
        map.put("goodsList", goodsList);
        map.put("shopList", shopList);
        return map;
    }

    @Override
    public Coupons selectByPrimaryKey(Integer id) {
        Coupons coupons = couponsMapper.selectByPrimaryKey(id);
        return coupons;
    }

    @Override
    public void updateByPrimaryKeySelective(Coupons coupons) {
        //TODO(MARK)-程序不抛出异常提示，只对能修改的字段做保存
        //1、系统默认优惠券无论是否派发，都只允许修改使用规则描述
        //2、其他优惠券如果已派发给用于，则只允许修改使用规则描述；如果未派发，则允许修改所有字段；
        if (coupons.getId().equals(BusinessType.NEW_PEOPLE_COUPONS_ID) || coupons.getId().equals(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID)) {
            Coupons updateCoupons = new Coupons();
            updateCoupons.setId(coupons.getId());
            updateCoupons.setDescription(coupons.getDescription());
            updateCoupons.setUpdateTime(new Date());
            couponsMapper.updateByPrimaryKeySelective(updateCoupons);
        }else{
            Integer couponsMemberRelationCount = couponsMemberRelationMapper.getCountByCouponsId(coupons.getId());
            if (couponsMemberRelationCount != null && couponsMemberRelationCount > 0) {
                Coupons updateCoupons = new Coupons();
                updateCoupons.setId(coupons.getId());
                updateCoupons.setDescription(coupons.getDescription());
                updateCoupons.setUpdateTime(new Date());
                couponsMapper.updateByPrimaryKeySelective(updateCoupons);
            }else{
                coupons.setUpdateTime(new Date());
                couponsMapper.updateByPrimaryKeySelective(coupons);
            }
        }
    }

    @Override
    public Page<Coupons> getListByPage(int pageNo, int pageSize, Coupons coupons) {
        Page<Coupons> page = couponsMapper.getListByPage(new Page(pageNo, pageSize), coupons);

        return page;
    }

    @Override
    public Page<Map<String, Object>> getMapListByPage(int pageNo, int pageSize, Coupons coupons) {
        Page<Map<String, Object>> page = couponsMapper.getMapListByPage(new Page(pageNo, pageSize), coupons);

        return page;
    }

    @Override
    public Page<Map<String, Object>> getListJoinCouponsShopRelationByPage(int pageNo, int pageSize, CouponsDto couponsDto) {
        Page<Map<String, Object>> page = couponsMapper.getListJoinCouponsShopRelationByPage(new Page(pageNo, pageSize), couponsDto);

        return page;
    }

    @Override
    public void updateCouponsEndTime(Coupons coupons) {
        Coupons currentCoupons = couponsMapper.selectByPrimaryKey(coupons.getId());
        Coupons updateCoupons = new Coupons();
        updateCoupons.setUpdateTime(new Date());
        if(Coupons.VALID_TYPE_TIME_QUANTUM.equals(currentCoupons.getValidType())){
            if(coupons.getValidEndTime().before(currentCoupons.getValidEndTime())){
                //提示时间不能缩短
                throw new StoneCustomerException("优惠卷时间不能缩短");
            }
            updateCoupons.setId(coupons.getId());
            updateCoupons.setValidEndTime(coupons.getValidEndTime());
            couponsMapper.updateByPrimaryKeySelective(updateCoupons);

            //设置优惠卷关系时间
            couponsMemberRelationMapper.updateEndTime(coupons.getValidEndTime(),currentCoupons.getId());
        }else if(Coupons.VALID_TYPE_DAYS_NUM.equals(currentCoupons.getValidType())){
            if(coupons.getValidDays()<currentCoupons.getValidDays()){
                //提示时间不能缩短
                throw new StoneCustomerException("优惠卷时间不能缩短");
            }
            updateCoupons.setId(coupons.getId());
            updateCoupons.setValidDays(coupons.getValidDays());
            couponsMapper.updateByPrimaryKeySelective(updateCoupons);

            //设置优惠卷关系时间
            couponsMemberRelationMapper.updaetEndTimeByDays(coupons.getValidDays(), coupons.getId());
        }


    }
}
