package com.siam.system.modular.package_goods.controller.admin;

import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.BusinessType;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.service.AliyunSms;
import com.siam.system.modular.package_goods.entity.Coupons;
import com.siam.system.modular.package_goods.entity.CouponsMemberRelation;
import com.siam.system.modular.package_goods.service.CouponsMemberRelationService;
import com.siam.system.modular.package_goods.service.CouponsService;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.model.example.MemberExample;
import com.siam.system.modular.package_user.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/admin/couponsMemberRelation")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "管理端优惠卷用关系接口", description = "AdminCouponsMemberRelationController")
public class AdminCouponsMemberRelationController {

    @Autowired
    private CouponsMemberRelationService couponsMemberRelationService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponsService couponsService;

    @Autowired
    private AliyunSms aliyunSms;


    @ApiOperation(value = "赠送优惠卷")
    @PostMapping(value = "/giveCoupons")
    public BasicResult giveCoupons(@RequestBody @Validated(value = {}) CouponsMemberRelation param) {
        BasicResult basicResult = new BasicResult();

        if (param.getCouponsId().equals(BusinessType.NEW_PEOPLE_COUPONS_ID) || param.getCouponsId().equals(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID)) {
            throw new StoneCustomerException("新人优惠卷和推荐新人优惠卷为系统默认优惠卷，不允许群发");
        }

        //获取优惠卷
        Coupons coupons=couponsService.selectByPrimaryKey(param.getCouponsId());

        //获取开始和结束时间
        Date startTime = new Date();
        Date endTime = startTime;
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

        //给所有用户派发优惠卷
        List<Member> memberList = memberService.selectAllMemberNoneCoupons(param.getCouponsId());
        for (Member member : memberList) {
            CouponsMemberRelation couponsMemberRelation = new CouponsMemberRelation();
            couponsMemberRelation.setCouponsId(param.getCouponsId());
            couponsMemberRelation.setMemberId(member.getId());
            couponsMemberRelation.setCouponsName(coupons.getName());
            couponsMemberRelation.setIsUsed(false);
            couponsMemberRelation.setIsExpired(false);
            couponsMemberRelation.setIsValid(true);
            couponsMemberRelation.setStartTime(startTime);
            couponsMemberRelation.setEndTime(endTime);
            couponsMemberRelation.setCreateTime(new Date());
            couponsMemberRelationService.insertSelective(couponsMemberRelation);

            //发送短信
            /*aliyunSms.sendCouponsDistributeReminderMessage(member.getMobile(), coupons.getName());*/
        }

        return BasicResult.success();
    }

    @ApiOperation(value = "赠送优惠卷（单发）")
    @PostMapping(value = "/giveSingleCoupons")
    public BasicResult giveSingleCoupons(@RequestBody @Validated(value = {}) CouponsMemberRelation param) {
        BasicResult basicResult = new BasicResult();

        if (param.getCouponsId().equals(BusinessType.NEW_PEOPLE_COUPONS_ID) || param.getCouponsId().equals(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID)) {
            throw new StoneCustomerException("新人优惠卷和推荐新人优惠卷为系统默认优惠卷，不允许群发");
        }

        //获取优惠卷
        Coupons coupons=couponsService.selectByPrimaryKey(param.getCouponsId());

        //获取开始和结束时间
        Date startTime = new Date();
        Date endTime = startTime;
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

        //判断是否有可用优惠卷
        if (couponsMemberRelationService.hasCouponsByMemberId(param.getMemberId())) {
            throw new StoneCustomerException("操作失败，用户存在未使用的优惠卷");
        }

//        //构建优惠卷关系，发送消息
//        Member member=memberService.selectByPrimaryKey(memberId);
//        CouponsMemberRelation couponsMemberRelation = new CouponsMemberRelation();
//        couponsMemberRelation.setCouponsId(couponsId);
//        couponsMemberRelation.setMemberId(member.getId());
//        couponsMemberRelation.setCouponsName(coupons.getName());
//        couponsMemberRelation.setIsUsed(false);
//        couponsMemberRelation.setIsExpired(false);
//        couponsMemberRelation.setIsValid(true);
//        couponsMemberRelation.setStartTime(startTime);
//        couponsMemberRelation.setEndTime(endTime);
//        couponsMemberRelation.setCreateTime(new Date());
//        couponsMemberRelationService.insertSelective(couponsMemberRelation);

        //发送短信
        /*aliyunSms.sendCouponsDistributeReminderMessage(member.getMobile(), coupons.getName());*/

        return BasicResult.success();
    }
}