package com.siam.system.modular.package_goods.service_impl.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.service.MemberBillingRecordService;
import com.siam.system.modular.package_user.service.MemberService;
import com.siam.system.modular.package_goods.entity.Coupons;
import com.siam.system.modular.package_goods.entity.CouponsMemberRelation;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeDenomination;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeDenominationCouponsRelation;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeRecord;
import com.siam.system.modular.package_goods.mapper.internal.VipRechargeRecordMapper;
import com.siam.system.modular.package_goods.model.example.internal.VipRechargeDenominationCouponsRelationExample;
import com.siam.system.modular.package_goods.service.CouponsMemberRelationService;
import com.siam.system.modular.package_goods.service.CouponsService;
import com.siam.system.modular.package_goods.service.internal.VipRechargeDenominationCouponsRelationService;
import com.siam.system.modular.package_goods.service.internal.VipRechargeDenominationService;
import com.siam.system.modular.package_goods.service.internal.VipRechargeRecordService;
import com.siam.system.modular.package_goods.entity.*;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeDenomination;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeDenominationCouponsRelation;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeRecord;
import com.siam.system.modular.package_goods.mapper.internal.VipRechargeRecordMapper;
import com.siam.system.modular.package_goods.service.*;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.util.GsonUtils;
import com.siam.system.modular.package_goods.model.example.internal.VipRechargeDenominationCouponsRelationExample;
import com.siam.system.modular.package_goods.service.internal.VipRechargeDenominationCouponsRelationService;
import com.siam.system.modular.package_goods.service.internal.VipRechargeDenominationService;
import com.siam.system.modular.package_goods.service.internal.VipRechargeRecordService;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.entity.MemberBillingRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class VipRechargeRecordServiceImpl implements VipRechargeRecordService {

    @Autowired
    private VipRechargeRecordMapper vipRechargeRecordMapper;

    @Autowired
    private VipRechargeDenominationService vipRechargeDenominationService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberBillingRecordService memberBillingRecordService;

    @Autowired
    private VipRechargeDenominationCouponsRelationService vipRechargeDenominationCouponsRelationService;

    @Autowired
    private CouponsService couponsService;

    @Autowired
    private CouponsMemberRelationService couponsMemberRelationService;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        vipRechargeRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(VipRechargeRecord vipRechargeRecord) {
        vipRechargeRecordMapper.insertSelective(vipRechargeRecord);
    }

    @Override
    public VipRechargeRecord selectByPrimaryKey(Integer id) {
        return vipRechargeRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(VipRechargeRecord vipRechargeRecord) {
        vipRechargeRecordMapper.updateByPrimaryKeySelective(vipRechargeRecord);
    }

    @Override
    public Page getListByPage(int pageNo, int pageSize, VipRechargeRecord vipRechargeRecord) {
        Page<Map<String, Object>> page = vipRechargeRecordMapper.getListByPage(new Page(pageNo, pageSize), vipRechargeRecord);
        return page;
    }

    @Override
    public Page getListByPageJoinMember(int pageNo, int pageSize, VipRechargeRecord vipRechargeRecord) {
        Page<Map<String, Object>> page = vipRechargeRecordMapper.getListByPageJoinMember(new Page(pageNo, pageSize), vipRechargeRecord);
        return page;
    }

    @Override
    public Map<String, Object> statisticalAmount(VipRechargeRecord vipRechargeRecord) {
        BigDecimal rechargeSuccessfulAmount = vipRechargeRecordMapper.statisticalAmountByRechargeSuccessful(vipRechargeRecord);

        Map<String, Object> map = new HashMap<>();
        map.put("rechargeSuccessfulAmount", rechargeSuccessfulAmount);
        return map;
    }

    @Override
    public void updateByPayNotify(String outTradeNo) {
        VipRechargeRecord dbVipRechargeRecord = vipRechargeRecordMapper.selectByOrderNo(outTradeNo);
        if(dbVipRechargeRecord == null){
            throw new StoneCustomerException("该商户单号不存在，回调逻辑处理失败");
        }
        VipRechargeDenomination dbVipRechargeDenomination = vipRechargeDenominationService.selectByPrimaryKey(dbVipRechargeRecord.getDenominationId());
        if(dbVipRechargeDenomination == null){
            throw new StoneCustomerException("该会员充值面额不存在");
        }
        Member dbMember = memberService.selectByPrimaryKey(dbVipRechargeRecord.getMemberId());
        if(dbMember == null){
            throw new StoneCustomerException("该充值用户不存在");
        }

        //如果该用户为普通用户，则将其用户类型改为VIP会员
        if(dbMember.getType().equals(Quantity.INT_1)){
            Member updateMember = new Member();
            updateMember.setId(dbMember.getId());
            updateMember.setType(Quantity.INT_2);
            memberService.updateByPrimaryKeySelective(updateMember);
            dbMember = memberService.selectByPrimaryKey(dbVipRechargeRecord.getMemberId());
        }

        //按照充值面额的原价金额同等发放到充值用户的余额中
        Member updateMember = new Member();
        updateMember.setId(dbMember.getId());
        updateMember.setBalance(dbMember.getBalance().add(dbVipRechargeDenomination.getPrice()));
        updateMember.setTotalBalance(dbMember.getTotalBalance().add(dbVipRechargeDenomination.getPrice()));
        memberService.updateByPrimaryKeySelective(updateMember);
        dbMember = memberService.selectByPrimaryKey(dbVipRechargeRecord.getMemberId());
        //添加账单记录
        MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
        memberBillingRecord.setMemberId(dbMember.getId());
        memberBillingRecord.setType(MemberBillingRecord.TYPE_VIP_RECHARGE_PRICE_SWITCH_TO_BALANCE);
        memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
        memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_BALANCE);
        memberBillingRecord.setNumber(dbVipRechargeDenomination.getPrice());
        memberBillingRecord.setMessage("会员充值原价金额同等转入余额");
        memberBillingRecord.setCreateTime(new Date());
        memberBillingRecordService.insertSelective(memberBillingRecord);

        //赠送余额
        if(dbVipRechargeDenomination.getIsGiveBalance()){
            updateMember = new Member();
            updateMember.setId(dbMember.getId());
            updateMember.setBalance(dbMember.getBalance().add(dbVipRechargeDenomination.getGiveBalance()));
            updateMember.setTotalBalance(dbMember.getTotalBalance().add(dbVipRechargeDenomination.getGiveBalance()));
            memberService.updateByPrimaryKeySelective(updateMember);
            dbMember = memberService.selectByPrimaryKey(dbVipRechargeRecord.getMemberId());

            //添加账单记录
            memberBillingRecord = new MemberBillingRecord();
            memberBillingRecord.setMemberId(dbMember.getId());
            memberBillingRecord.setType(MemberBillingRecord.TYPE_VIP_RECHARGE_REWARD_BALANCE);
            memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
            memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_BALANCE);
            memberBillingRecord.setNumber(dbVipRechargeDenomination.getGiveBalance());
            memberBillingRecord.setMessage("会员充值奖励余额");
            memberBillingRecord.setCreateTime(new Date());
            memberBillingRecordService.insertSelective(memberBillingRecord);
        }

        //赠送优惠券
        String denominationGiveCouponsDescription = "", denominationGiveCouponsJson = "";
        if(dbVipRechargeDenomination.getIsGiveCoupons()){
            VipRechargeDenominationCouponsRelationExample example = new VipRechargeDenominationCouponsRelationExample();
            example.createCriteria().andVipRechargeDenominationIdEqualTo(dbVipRechargeDenomination.getId());
            List<VipRechargeDenominationCouponsRelation> denominationCouponsRelationList = vipRechargeDenominationCouponsRelationService.selectByExample(example);
            for (VipRechargeDenominationCouponsRelation denominationCouponsRelation : denominationCouponsRelationList) {
                //获取优惠卷
                Coupons coupons=couponsService.selectByPrimaryKey(denominationCouponsRelation.getCouponsId());
                if(coupons == null) throw new StoneCustomerException("该优惠券不存在");

                //获取开始和结束时间
                Date startTime = new Date();
                Date endTime = null;
                Integer validType=coupons.getValidType();
                if(Coupons.VALID_TYPE_DAYS_NUM.equals(validType)){
                    Calendar calendar = Calendar. getInstance();
                    calendar.setTime( new Date());
                    calendar.set(Calendar. HOUR_OF_DAY, 0);
                    calendar.set(Calendar. MINUTE, 0);
                    calendar.set(Calendar. SECOND, 0);
                    calendar.set(Calendar. MILLISECOND, 0);
                    calendar.add(Calendar. DAY_OF_MONTH, coupons.getValidDays());
                    endTime = calendar.getTime();
                }else if(Coupons.VALID_TYPE_TIME_QUANTUM.equals(validType)){
                    startTime=coupons.getValidStartTime();
                    endTime=coupons.getValidEndTime();
                }

                //给用户派发优惠卷
                for (int i = 0; i < denominationCouponsRelation.getGiveQuantity(); i++){
                    CouponsMemberRelation couponsMemberRelation = new CouponsMemberRelation();
                    couponsMemberRelation.setCouponsId(denominationCouponsRelation.getCouponsId());
//                    couponsMemberRelation.setMemberId(dbMember.getId());
                    couponsMemberRelation.setCouponsName(coupons.getName());
                    couponsMemberRelation.setIsUsed(false);
                    couponsMemberRelation.setIsExpired(false);
                    couponsMemberRelation.setIsValid(true);
                    couponsMemberRelation.setStartTime(startTime);
                    couponsMemberRelation.setEndTime(endTime);
                    couponsMemberRelation.setCreateTime(new Date());
                    couponsMemberRelationService.insertSelective(couponsMemberRelation);
                }
                denominationGiveCouponsDescription = denominationGiveCouponsDescription.isEmpty() ? (denominationCouponsRelation.getGiveQuantity() + "张" + coupons.getName()) : (denominationGiveCouponsDescription + ", " + denominationCouponsRelation.getGiveQuantity() + "张" + coupons.getName());
            }
            denominationGiveCouponsJson = GsonUtils.toJson(denominationCouponsRelationList);
        }

        //更新会员充值记录的状态
        VipRechargeRecord updateVipRechargeRecord = new VipRechargeRecord();
        updateVipRechargeRecord.setId(dbVipRechargeRecord.getId());
        updateVipRechargeRecord.setDenominationName(dbVipRechargeDenomination.getName());
        updateVipRechargeRecord.setDenominationPrice(dbVipRechargeDenomination.getPrice());
        updateVipRechargeRecord.setDenominationIsSale(dbVipRechargeDenomination.getIsSale());
        updateVipRechargeRecord.setDenominationSalePrice(dbVipRechargeDenomination.getSalePrice());
        updateVipRechargeRecord.setDenominationIsGiveBalance(dbVipRechargeDenomination.getIsGiveBalance());
        updateVipRechargeRecord.setDenominationGiveBalance(dbVipRechargeDenomination.getGiveBalance());
        updateVipRechargeRecord.setDenominationIsGiveCoupons(dbVipRechargeDenomination.getIsGiveCoupons());
        updateVipRechargeRecord.setDenominationGiveCouponsDescription(denominationGiveCouponsDescription);
        updateVipRechargeRecord.setDenominationGiveCouponsJson(denominationGiveCouponsJson);
        updateVipRechargeRecord.setStatus(Quantity.INT_2);
        vipRechargeRecordMapper.updateByPrimaryKeySelective(updateVipRechargeRecord);
    }

    @Override
    public VipRechargeRecord selectLastestPaid(Integer memberId) {
        return vipRechargeRecordMapper.selectLastestPaid(memberId);
    }
}