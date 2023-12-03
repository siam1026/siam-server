package com.siam.system.modular.package_goods.controller.admin.internal;

import com.siam.package_common.service.AliyunSms;
import com.siam.system.modular.package_goods.service.internal.PointsMallCouponsMemberRelationService;
import com.siam.system.modular.package_goods.service.internal.PointsMallCouponsService;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.BusinessType;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCoupons;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCouponsMemberRelation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping(value = "/rest/admin/pointsMall/couponsMemberRelation")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "管理端优惠卷用关系接口", description = "AdminPointsMallCouponsMemberRelationController")
public class AdminPointsMallCouponsMemberRelationController {

    @Autowired
    private PointsMallCouponsMemberRelationService pointsMallCouponsMemberRelationService;

//    @Autowired
//    private MemberService memberService;

    @Autowired
    private PointsMallCouponsService pointsMallCouponsService;

    @Autowired
    private AliyunSms aliyunSms;


    @ApiOperation(value = "赠送优惠卷")
    @PostMapping(value = "/giveCoupons")
    public BasicResult giveCoupons(@RequestBody @Validated(value = {}) PointsMallCouponsMemberRelation param) {
        BasicResult basicResult = new BasicResult();

        if (param.getCouponsId().equals(BusinessType.NEW_PEOPLE_COUPONS_ID) || param.getCouponsId().equals(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID)) {
            throw new StoneCustomerException("新人优惠卷和推荐新人优惠卷为系统默认优惠卷，不允许群发");
        }

        //获取优惠卷
        PointsMallCoupons coupons= pointsMallCouponsService.selectByPrimaryKey(param.getCouponsId());

        //获取开始和结束时间
        Date startTime = new Date();
        Date endTime = startTime;
        Integer validType=coupons.getValidType();
        if(PointsMallCoupons.VALID_TYPE_DAYS_NUM.equals(validType)){
            Calendar calendar = Calendar. getInstance();
            calendar.setTime( new Date());
            calendar.set(Calendar. HOUR_OF_DAY, 0);
            calendar.set(Calendar. MINUTE, 0);
            calendar.set(Calendar. SECOND, 0);
            calendar.set(Calendar. MILLISECOND, 0);
            calendar.add(Calendar. DAY_OF_MONTH, coupons.getValidDays());
            endTime = calendar.getTime();
        }else if(PointsMallCoupons.VALID_TYPE_TIME_QUANTUM.equals(validType)){
            startTime=coupons.getValidStartTime();
            endTime=coupons.getValidEndTime();
        }

//        //查询出所有的用户
//        MemberExample queryMemberExample = new MemberExample();
//        //List<Member> memberList = memberService.selectByExample(queryMemberExample);
//        List<Member> memberList = memberService.selectAllMemberNoneCouponsByPointsMall();
//
//        //给所有用户派发优惠卷
//        for (Member member : memberList) {
//            PointsMallCouponsMemberRelation couponsMemberRelation = new PointsMallCouponsMemberRelation();
//            couponsMemberRelation.setCouponsId(couponsId);
//            couponsMemberRelation.setMemberId(member.getId());
//            couponsMemberRelation.setCouponsName(coupons.getName());
//            couponsMemberRelation.setIsUsed(false);
//            couponsMemberRelation.setIsExpired(false);
//            couponsMemberRelation.setIsValid(true);
//            couponsMemberRelation.setStartTime(startTime);
//            couponsMemberRelation.setEndTime(endTime);
//            couponsMemberRelation.setCreateTime(new Date());
//            pointsMallCouponsMemberRelationService.insertSelective(couponsMemberRelation);
//
//            //发送短信
//            /*aliyunSms.sendPointsMallCouponsDistributeReminderMessage(member.getMobile(), coupons.getName());*/
//        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("操作成功");
        return basicResult;
    }

    @ApiOperation(value = "赠送优惠卷（单发）")
    @PostMapping(value = "/giveSingleCoupons")
    public BasicResult giveSingleCoupons(@RequestBody @Validated(value = {}) PointsMallCouponsMemberRelation param) {
        BasicResult basicResult = new BasicResult();

        if (param.getCouponsId().equals(BusinessType.NEW_PEOPLE_COUPONS_ID) || param.getCouponsId().equals(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID)) {
            throw new StoneCustomerException("新人优惠卷和推荐新人优惠卷为系统默认优惠卷，不允许群发");
        }

        //获取优惠卷
        PointsMallCoupons coupons= pointsMallCouponsService.selectByPrimaryKey(param.getCouponsId());

        //获取开始和结束时间
        Date startTime = new Date();
        Date endTime = startTime;
        Integer validType=coupons.getValidType();
        if(PointsMallCoupons.VALID_TYPE_DAYS_NUM.equals(validType)){
            Calendar calendar = Calendar. getInstance();
            calendar.setTime( new Date());
            calendar.set(Calendar. HOUR_OF_DAY, 0);
            calendar.set(Calendar. MINUTE, 0);
            calendar.set(Calendar. SECOND, 0);
            calendar.set(Calendar. MILLISECOND, 0);
            calendar.add(Calendar. DAY_OF_MONTH, coupons.getValidDays());
            endTime = calendar.getTime();
        }else if(PointsMallCoupons.VALID_TYPE_TIME_QUANTUM.equals(validType)){
            startTime=coupons.getValidStartTime();
            endTime=coupons.getValidEndTime();
        }

        //判断是否有可用优惠卷
        if (pointsMallCouponsMemberRelationService.hasPointsMallCouponsByMemberId(param.getMemberId())) {
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("操作失败，用户存在未使用的优惠卷");
            return basicResult;
        }

//        //构建优惠卷关系，发送消息
//        Member member=memberService.selectByPrimaryKey(memberId);
//        PointsMallCouponsMemberRelation couponsMemberRelation = new PointsMallCouponsMemberRelation();
//        couponsMemberRelation.setCouponsId(couponsId);
//        couponsMemberRelation.setMemberId(member.getId());
//        couponsMemberRelation.setCouponsName(coupons.getName());
//        couponsMemberRelation.setIsUsed(false);
//        couponsMemberRelation.setIsExpired(false);
//        couponsMemberRelation.setIsValid(true);
//        couponsMemberRelation.setStartTime(startTime);
//        couponsMemberRelation.setEndTime(endTime);
//        couponsMemberRelation.setCreateTime(new Date());
//        pointsMallCouponsMemberRelationService.insertSelective(couponsMemberRelation);

        //发送短信
        /*aliyunSms.sendPointsMallCouponsDistributeReminderMessage(member.getMobile(), coupons.getName());*/

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("操作成功");
        return basicResult;

    }

}
