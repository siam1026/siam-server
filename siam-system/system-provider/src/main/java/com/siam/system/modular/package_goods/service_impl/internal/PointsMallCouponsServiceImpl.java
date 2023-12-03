package com.siam.system.modular.package_goods.service_impl.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCoupons;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoods;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallCouponsGoodsRelationMapper;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallCouponsMapper;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallCouponsMemberRelationMapper;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallGoodsMapper;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallCouponsDto;
import com.siam.system.modular.package_goods.service.internal.PointsMallCouponsService;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallCouponsGoodsRelationMapper;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallCouponsMapper;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallCouponsMemberRelationMapper;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallGoodsMapper;
import com.siam.system.modular.package_goods.service.internal.PointsMallCouponsService;
import com.siam.package_common.constant.BusinessType;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallCouponsDto;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCoupons;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PointsMallCouponsServiceImpl implements PointsMallCouponsService {

    @Autowired
    private PointsMallCouponsMapper couponsMapper;

    @Autowired
    private PointsMallCouponsGoodsRelationMapper couponsGoodsRelationMapper;

    @Autowired
    private PointsMallGoodsMapper goodsMapper;

    @Autowired
    private PointsMallCouponsMemberRelationMapper couponsMemberRelationMapper;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        if (id.equals(BusinessType.NEW_PEOPLE_COUPONS_ID) || id.equals(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID)) {
            throw new StoneCustomerException("新人优惠卷和推荐新人优惠卷为系统默认优惠卷，不允许删除");
        }

        /*Integer couponsMemberRelationCount = couponsMemberRelationMapper.getCountByPointsMallCouponsId(id);
        if (couponsMemberRelationCount != null && couponsMemberRelationCount > 0) {
            throw new StoneCustomerException("优惠卷已派发给用户，不能删除");
        }*/

        //1、删除（修改是否删除字段）对象
        //couponsMapper.deleteByPrimaryKey(id);
        PointsMallCoupons coupons = couponsMapper.selectByPrimaryKey(id);
        coupons.setIsDelete(true);
        coupons.setUpdateTime(new Date());
        couponsMapper.updateByPrimaryKeySelective(coupons);

        //2、删除关系
        couponsGoodsRelationMapper.deleteByPointsMallCouponsId(id);

        //3、删除（修改生效字段）用户关系
        couponsMemberRelationMapper.updateValidToFalseByPointsMallCouponsId(id);
    }

    @Override
    public void insertSelective(PointsMallCoupons coupons) {
        //1、插入对象
        coupons.setCreateTime(new Date());
        coupons.setIsDelete(false);
        couponsMapper.insertSelective(coupons);

        /*//2、插入关系
        for (Integer goodsId : goodsIdList) {
            PointsMallCouponsGoodsRelation couponsGoodsRelation = new PointsMallCouponsGoodsRelation();
            couponsGoodsRelation.setCouponsId(couponsId);
            couponsGoodsRelation.setGoodsId(goodsId);
            couponsGoodsRelation.setCreateTime(new Date());
            couponsGoodsRelationMapper.insertSelective(couponsGoodsRelation);
        }*/
    }

    @Override
    public Map selectCouponsAndGoodsByPrimaryKey(Integer id) {
        //1、查询对象
        PointsMallCoupons coupons = couponsMapper.selectByPrimaryKey(id);

        //2、查询对象对应的商品
        List<PointsMallGoods> goodsList = goodsMapper.getListByPointsMallCouponsId(id);

        //3、查询对象对应的店铺
        /*List<Shop> shopList = shopMapper.getListByPointsMallCouponsId(id);*/

        Map map = new HashMap();
        map.put("coupons", coupons);
        map.put("goodsList", goodsList);
        /*map.put("shopList", shopList);*/
        return map;
    }

    @Override
    public PointsMallCoupons selectByPrimaryKey(Integer id) {
        PointsMallCoupons coupons = couponsMapper.selectByPrimaryKey(id);
        return coupons;
    }

    @Override
    public void updateByPrimaryKeySelective(PointsMallCoupons coupons) {
        //TODO(MARK)-程序不抛出异常提示，只对能修改的字段做保存
        //1、系统默认优惠券无论是否派发，都只允许修改使用规则描述【积分商城没有系统默认优惠券】
        //2、其他优惠券如果已派发给用于，则只允许修改使用规则描述；如果未派发，则允许修改所有字段；
        if (coupons.getId().equals(BusinessType.NEW_PEOPLE_COUPONS_ID) || coupons.getId().equals(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID)) {
            PointsMallCoupons updateCoupons = new PointsMallCoupons();
            updateCoupons.setId(coupons.getId());
            updateCoupons.setDescription(coupons.getDescription());
            updateCoupons.setUpdateTime(new Date());
            couponsMapper.updateByPrimaryKeySelective(updateCoupons);
        }else{
            Integer couponsMemberRelationCount = couponsMemberRelationMapper.getCountByPointsMallCouponsId(coupons.getId());
            if (couponsMemberRelationCount != null && couponsMemberRelationCount > 0) {
                PointsMallCoupons updateCoupons = new PointsMallCoupons();
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
    public Page<PointsMallCoupons> getListByPage(int pageNo, int pageSize, PointsMallCoupons coupons) {
        Page<PointsMallCoupons> page = couponsMapper.getListByPage(new Page(pageNo, pageSize), coupons);

        return page;
    }

    @Override
    public Page<Map<String, Object>> getMapListByPage(int pageNo, int pageSize, PointsMallCoupons coupons) {
        Page<Map<String, Object>> page = couponsMapper.getMapListByPage(new Page(pageNo, pageSize), coupons);

        return page;
    }

    @Override
    public Page<Map<String, Object>> getListJoinPointsMallCouponsShopRelationByPage(int pageNo, int pageSize, PointsMallCouponsDto couponsDto) {
        Page<Map<String, Object>> page = couponsMapper.getListJoinPointsMallCouponsShopRelationByPage(new Page(pageNo, pageSize), couponsDto);

        return page;
    }

    @Override
    public void updatePointsMallCouponsEndTime(PointsMallCoupons coupons) {
        PointsMallCoupons currentPointsMallCoupons = couponsMapper.selectByPrimaryKey(coupons.getId());
        PointsMallCoupons updatePointsMallCoupons = new PointsMallCoupons();
        updatePointsMallCoupons.setUpdateTime(new Date());
        if(PointsMallCoupons.VALID_TYPE_TIME_QUANTUM.equals(currentPointsMallCoupons.getValidType())){
            if(coupons.getValidEndTime().before(currentPointsMallCoupons.getValidEndTime())){
                //提示时间不能缩短
                throw new StoneCustomerException("优惠卷时间不能缩短");
            }
            updatePointsMallCoupons.setId(coupons.getId());
            updatePointsMallCoupons.setValidEndTime(coupons.getValidEndTime());
            couponsMapper.updateByPrimaryKeySelective(updatePointsMallCoupons);

            //设置优惠卷关系时间
            couponsMemberRelationMapper.updateEndTime(coupons.getValidEndTime(),currentPointsMallCoupons.getId());
        }else if(PointsMallCoupons.VALID_TYPE_DAYS_NUM.equals(currentPointsMallCoupons.getValidType())){
            if(coupons.getValidDays()<currentPointsMallCoupons.getValidDays()){
                //提示时间不能缩短
                throw new StoneCustomerException("优惠卷时间不能缩短");
            }
            updatePointsMallCoupons.setId(coupons.getId());
            updatePointsMallCoupons.setValidDays(coupons.getValidDays());
            couponsMapper.updateByPrimaryKeySelective(updatePointsMallCoupons);

            //设置优惠卷关系时间
            couponsMemberRelationMapper.updaetEndTimeByDays(coupons.getValidDays(), coupons.getId());
        }


    }
}
