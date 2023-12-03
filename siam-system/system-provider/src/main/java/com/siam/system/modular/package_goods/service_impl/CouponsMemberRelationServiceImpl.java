package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.service.AliyunSms;
import com.siam.system.modular.package_goods.entity.Coupons;
import com.siam.system.modular.package_goods.entity.CouponsMemberRelation;
import com.siam.system.modular.package_goods.mapper.CouponsMapper;
import com.siam.system.modular.package_goods.mapper.CouponsMemberRelationMapper;
import com.siam.system.modular.package_goods.mapper.GoodsMapper;
import com.siam.system.modular.package_goods.mapper.ShopMapper;
import com.siam.system.modular.package_goods.service.CouponsMemberRelationService;
import com.siam.system.modular.package_goods.entity.Coupons;
import com.siam.system.modular.package_goods.entity.CouponsMemberRelation;
import com.siam.system.modular.package_goods.mapper.CouponsMapper;
import com.siam.system.modular.package_goods.mapper.CouponsMemberRelationMapper;
import com.siam.system.modular.package_goods.mapper.GoodsMapper;
import com.siam.system.modular.package_goods.mapper.ShopMapper;
import com.siam.system.modular.package_goods.service.CouponsMemberRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CouponsMemberRelationServiceImpl implements CouponsMemberRelationService {

    @Autowired
    private CouponsMemberRelationMapper couponsMemberRelationMapper;

    @Autowired
    private CouponsMapper couponsMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private AliyunSms aliyunSms;

//    @Autowired
//    private MemberMapper memberMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public void insertSelective(CouponsMemberRelation couponsMemberRelation) {
        //通过优惠卷id查出优惠卷信息
        Coupons coupons=couponsMapper.selectByPrimaryKey(couponsMemberRelation.getCouponsId());
        //将优惠卷名称，和截止时间附上
        couponsMemberRelation.setCouponsName(coupons.getName());
        couponsMemberRelation.setIsUsed(false);
        couponsMemberRelation.setIsExpired(false);
        couponsMemberRelation.setIsValid(true);
        couponsMemberRelation.setCreateTime(new Date());
        Integer validType=coupons.getValidType();
        if(Coupons.VALID_TYPE_DAYS_NUM.equals(validType)){
            couponsMemberRelation.setStartTime(new Date());
            Calendar calendar = Calendar. getInstance();
            calendar.setTime( new Date());
            calendar.set(Calendar. HOUR_OF_DAY, 0);
            calendar.set(Calendar. MINUTE, 0);
            calendar.set(Calendar. SECOND, 0);
            calendar.set(Calendar. MILLISECOND, 0);
            calendar.add(Calendar. DAY_OF_MONTH, coupons.getValidDays());
            Date endTime = calendar.getTime();
            couponsMemberRelation.setEndTime(endTime);
        }else if(Coupons.VALID_TYPE_TIME_QUANTUM.equals(validType)){
            couponsMemberRelation.setStartTime(coupons.getValidStartTime());
            couponsMemberRelation.setEndTime(coupons.getValidEndTime());
        }
        //保存
        couponsMemberRelationMapper.insertSelective(couponsMemberRelation);
    }

    @Override
    public CouponsMemberRelation selectCouponsMemberRelationByPrimaryKey(Integer id) {
        CouponsMemberRelation couponsMemberRelation = couponsMemberRelationMapper.selectByPrimaryKey(id);
        return couponsMemberRelation;
    }

    @Override
    public void updateCouponsUsed(Integer id,Boolean isUsed) {
        CouponsMemberRelation couponsMemberRelation = couponsMemberRelationMapper.selectByPrimaryKey(id);
        couponsMemberRelation.setIsUsed(isUsed);
        couponsMemberRelation.setUpdateTime(new Date());
        couponsMemberRelationMapper.updateByPrimaryKeySelective(couponsMemberRelation);
    }

    @Override
    public Page<Map<String, Object>> getListByPage(int pageNo, int pageSize, CouponsMemberRelation couponsMemberRelation) {
        Page<Map<String, Object>> page = couponsMemberRelationMapper.getListByPage(new Page(pageNo, pageSize), couponsMemberRelation);

        List<Map<String, Object>> result = new ArrayList();
        for (Map  couponsMemberRelationMap : page.getRecords()) {
            Map map = new HashMap(2);
            map.put("couponsMemberRelationMap", couponsMemberRelationMap);
            map.put("goodsList",goodsMapper.getListByCouponsId((Integer) couponsMemberRelationMap.get("couponsId")));
            map.put("shopList",shopMapper.getListByCouponsId((Integer) couponsMemberRelationMap.get("couponsId")));
            result.add(map);
        }

        page.setRecords(result);

        return page;
    }

    @Override
    public void updateOverdue() {
        Date date = new Date();
        couponsMemberRelationMapper.updateOverdueCoupons(date);
    }

    @Override
    public Integer getCountsByMemberId(Integer memberId) {
        return couponsMemberRelationMapper.getCountsByMemberId(memberId);
    }

    @Override
    public void overdueSMSReminder() {
//        //获取有优惠卷即将过期的用户id
//        Date currentTime = new Date();
//        Date overdueTime=DateUtils.addDays(currentTime, 2);
//        List<Member> memberList = memberMapper.selectHasOverdueCoupons(overdueTime);
//
//        //发送短信
//        for (Member member : memberList) {
//            //发送短信
//            /*aliyunSms.sendCouponsOverdueReminderMessage(member.getMobile());*/
//        }
    }

    @Override
    public Boolean hasCouponsByMemberId(Integer memberId) {
        Integer count=couponsMemberRelationMapper.getCountsByMemberId(memberId);
        return (count != null && count > 0) ? true : false;
    }

}
