package com.siam.system.modular.package_goods.service_impl.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.service.AliyunSms;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCoupons;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCouponsMemberRelation;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallCouponsMapper;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallCouponsMemberRelationMapper;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallGoodsMapper;
import com.siam.system.modular.package_goods.service.internal.PointsMallCouponsMemberRelationService;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallCouponsMapper;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallCouponsMemberRelationMapper;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallGoodsMapper;
import com.siam.system.modular.package_goods.service.internal.PointsMallCouponsMemberRelationService;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCoupons;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCouponsMemberRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PointsMallCouponsMemberRelationServiceImpl implements PointsMallCouponsMemberRelationService {

    @Autowired
    private PointsMallCouponsMemberRelationMapper couponsMemberRelationMapper;

    @Autowired
    private PointsMallCouponsMapper couponsMapper;

    @Autowired
    private PointsMallGoodsMapper goodsMapper;

    @Autowired
    private AliyunSms aliyunSms;

//    @Autowired
//    private MemberMapper memberMapper;

    @Override
    public void insertSelective(PointsMallCouponsMemberRelation couponsMemberRelation) {
        //通过优惠卷id查出优惠卷信息
        PointsMallCoupons coupons=couponsMapper.selectByPrimaryKey(couponsMemberRelation.getCouponsId());
        //将优惠卷名称，和截止时间附上
        couponsMemberRelation.setCouponsName(coupons.getName());
        couponsMemberRelation.setIsUsed(false);
        couponsMemberRelation.setIsExpired(false);
        couponsMemberRelation.setIsValid(true);
        couponsMemberRelation.setCreateTime(new Date());
        Integer validType=coupons.getValidType();
        if(PointsMallCoupons.VALID_TYPE_DAYS_NUM.equals(validType)){
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
        }else if(PointsMallCoupons.VALID_TYPE_TIME_QUANTUM.equals(validType)){
            couponsMemberRelation.setStartTime(coupons.getValidStartTime());
            couponsMemberRelation.setEndTime(coupons.getValidEndTime());
        }
        //保存
        couponsMemberRelationMapper.insertSelective(couponsMemberRelation);
    }

    @Override
    public PointsMallCouponsMemberRelation selectPointsMallCouponsMemberRelationByPrimaryKey(Integer id) {
        PointsMallCouponsMemberRelation couponsMemberRelation = couponsMemberRelationMapper.selectByPrimaryKey(id);
        return couponsMemberRelation;
    }

    @Override
    public void updateCouponsUsed(Integer id, Boolean isUsed) {
        PointsMallCouponsMemberRelation couponsMemberRelation = couponsMemberRelationMapper.selectByPrimaryKey(id);
        couponsMemberRelation.setIsUsed(isUsed);
        couponsMemberRelation.setUpdateTime(new Date());
        couponsMemberRelationMapper.updateByPrimaryKeySelective(couponsMemberRelation);
    }

    @Override
    public Page<Map<String, Object>> getListByPage(int pageNo, int pageSize, PointsMallCouponsMemberRelation couponsMemberRelation) {
        Page<Map<String, Object>> page = couponsMemberRelationMapper.getListByPage(new Page(pageNo, pageSize), couponsMemberRelation);

        List<Map<String, Object>> result = new ArrayList();
        for (Map  couponsMemberRelationMap : page.getRecords()) {
            Map map = new HashMap(2);
            map.put("couponsMemberRelationMap", couponsMemberRelationMap);
            map.put("goodsList",goodsMapper.getListByPointsMallCouponsId((Integer) couponsMemberRelationMap.get("couponsId")));
            /*map.put("shopList",shopMapper.getListByPointsMallCouponsId((Integer) couponsMemberRelationMap.get("couponsId")));*/
            result.add(map);
        }

        page.setRecords(result);
        return page;
    }

    @Override
    public void updateOverdue() {
        Date date = new Date();
        couponsMemberRelationMapper.updateOverduePointsMallCoupons(date);
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
//            /*aliyunSms.sendPointsMallCouponsOverdueReminderMessage(member.getMobile());*/
//        }
    }

    @Override
    public Boolean hasPointsMallCouponsByMemberId(Integer memberId) {
        Integer count=couponsMemberRelationMapper.getCountsByMemberId(memberId);
        return (count != null && count > 0) ? true : false;
    }

}
