package com.siam.system.modular.package_user.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.entity.MemberInviteRelation;
import com.siam.system.modular.package_user.mapper.MemberInviteRelationMapper;
import com.siam.system.modular.package_user.mapper.MemberMapper;
import com.siam.system.modular.package_user.model.example.MemberInviteRelationExample;
import com.siam.system.modular.package_user.model.param.MemberInviteRelationParam;
import com.siam.system.modular.package_user.service.MemberBillingRecordService;
import com.siam.system.modular.package_user.service.MemberInviteRelationService;
import com.siam.system.modular.package_user.service.MemberService;
import com.siam.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MemberInviteRelationServiceImpl implements MemberInviteRelationService {

    @Autowired
    private MemberInviteRelationMapper memberInviteRelationMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberService memberService;

//    @Autowired
//    private SettingService settingService;

    @Autowired
    private MemberBillingRecordService memberBillingRecordService;

//    @Autowired
//    private CouponsService couponsService;
//
//    @Autowired
//    private CouponsMemberRelationService couponsMemberRelationService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    @Override
    public void insertSelective(MemberInviteRelation memberInviteRelation) {
        memberInviteRelation.setCreateTime(new Date());
        memberInviteRelationMapper.insertSelective(memberInviteRelation);
    }

    @Override
    public Page<Member> getMemberListByInviterId(MemberInviteRelationParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        Page<Member> page = memberMapper.getMemberListByInviterId(new Page(param.getPageNo(), param.getPageSize()), loginMember.getId());
        return page;
    }

    @Override
    public Map<String, Integer> selectInviter(Integer memberId) {
        Map<String, Integer> map = new HashMap<>();
        //查询一级邀请人
        MemberInviteRelationExample example = new MemberInviteRelationExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        List<MemberInviteRelation> memberInviteRelationList = this.memberInviteRelationMapper.selectByExample(example);
        if(memberInviteRelationList!=null && !memberInviteRelationList.isEmpty()){
            map.put("firstLevelInviter", memberInviteRelationList.get(0).getInviterId());

            //查询二级邀请人
            example = new MemberInviteRelationExample();
            example.createCriteria().andMemberIdEqualTo(memberInviteRelationList.get(0).getInviterId());
            memberInviteRelationList = this.memberInviteRelationMapper.selectByExample(example);
            if(memberInviteRelationList!=null && !memberInviteRelationList.isEmpty()){
                map.put("secondLevelInviter", memberInviteRelationList.get(0).getInviterId());

//                //查询三级邀请人
//                example = new MemberInviteRelationExample();
//                example.createCriteria().andMemberIdEqualTo(memberInviteRelationList.get(0).getInviterId());
//                memberInviteRelationList = this.memberInviteRelationMapper.selectByExample(example);
//                if(memberInviteRelationList!=null && !memberInviteRelationList.isEmpty()){
//                    map.put("thirdLevelInviter", memberInviteRelationList.get(0).getInviterId());
//                }
            }
        }
        return map;
    }



//    @Override
//    public void insertSelective(String mobile, Integer inviterId) {
//        //查看用户是否已存在
//        Member member=memberMapper.selectByMobile(mobile);
//        if (member != null && member.getId() != null) {
//            throw new StoneCustomerException("您已经是暹罗的用户了，快去喝一杯吧");
//        }
//
//        //注册用户
//
//        //用户未操作，进行自动注册操作
//        //获取会员卡号
//        String vipNo = memberService.generateVipNo();
//
//        //获取积分设置
//        Setting setting = settingService.selectCurrent();
//        BigDecimal registrationRewardPoints = setting.getRegistrationRewardPoints();
//        registrationRewardPoints = registrationRewardPoints == null ? BigDecimal.ZERO : registrationRewardPoints;
//
//        // 添加member记录
//        Member insertMember = new Member();
//        insertMember.setUsername(mobile);
//        insertMember.setMobile(mobile);
//        insertMember.setNickname(mobile);
//        insertMember.setBalance(BigDecimal.ZERO);
//        insertMember.setLoginCount(Quantity.INT_1);
//        insertMember.setInviteCode(null);
//        insertMember.setIsDisabled(false);
//        insertMember.setHeadImg(null);
//        insertMember.setRoles(null);
//        insertMember.setSex(Quantity.INT_1);
//        insertMember.setEmail(null);
//        insertMember.setIsDisabled(false);
//        insertMember.setIsDeleted(false);
//        insertMember.setOpenId(null);
//        insertMember.setIsBindWx(false);
//        insertMember.setPoints(registrationRewardPoints);
//        insertMember.setVipStatus(Quantity.INT_0);
//        insertMember.setVipType(Quantity.INT_0);
//        insertMember.setVipStartTime(null);
//        insertMember.setVipEndTime(null);
//        insertMember.setType(Quantity.INT_1);
//        insertMember.setVipNo(vipNo);
//        insertMember.setIsNewPeople(true);
//        insertMember.setIsRemindNewPeople(true);
//        insertMember.setCreateTime(new Date());
//        insertMember.setUpdateTime(new Date());
//        insertMember.setLastLoginTime(new Date());
//        memberService.insertSelective(insertMember);
//
//        //生成积分账单
//        if (registrationRewardPoints.compareTo(BigDecimal.ZERO) > 0) {
//            MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
//            memberBillingRecord.setMemberId(insertMember.getId());
//            memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_POINTS);
//            memberBillingRecord.setType(MemberBillingRecord.TYPE_REGISTER_REWARD_POINTS);
//            memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
//            memberBillingRecord.setNumber(registrationRewardPoints);
//            memberBillingRecord.setCreateTime(new Date());
//            memberBillingRecord.setMessage("新用户注册赠送积分");
//            memberBillingRecordService.insertSelective(memberBillingRecord);
//        }
//
//        //赠送系统默认优惠券-新人3折卷
//        Coupons dbCoupons = couponsService.selectByPrimaryKey(BusinessType.NEW_PEOPLE_COUPONS_ID);
//        if (dbCoupons == null) {
//            throw new StoneCustomerException("系统默认优惠券-新人3折卷不存在");
//        }
//        CouponsMemberRelation couponsMemberRelation = new CouponsMemberRelation();
//        couponsMemberRelation.setCouponsId(BusinessType.NEW_PEOPLE_COUPONS_ID);
//        couponsMemberRelation.setMemberId(insertMember.getId());
//        couponsMemberRelationService.insertSelective(couponsMemberRelation);
//
//        //从数据库中获取注册好的用户信息
//        Member dbMember = memberService.selectByMobile(mobile);
//
//        // 生成token
//        String token = GenerateNo.getUUID();
//
//        //添加membertoken记录
//        MemberToken memberToken = new MemberToken();
//        memberToken.setMemberId(dbMember.getId());
//        memberToken.setUsername(dbMember.getUsername());
//        memberToken.setToken(token);
//        memberToken.setType(BusinessType.MEMBER_TOKEN_TYPE_WAP);
//        memberToken.setCreateTime(new Date());
//        memberTokenService.insertSelective(memberToken);
//
//        //创建用户关系
//        MemberInviteRelation memberInviteRelation = new MemberInviteRelation();
//        memberInviteRelation.setInviterId(inviterId);
//        memberInviteRelation.setMemberId(dbMember.getId());
//        memberInviteRelation.setCreateTime(new Date());
//        memberInviteRelationMapper.insertSelective(memberInviteRelation);
//
//        //发送邀请卷
//        Coupons inviteCoupons = couponsService.selectByPrimaryKey(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID);
//        if (inviteCoupons == null) {
//            throw new StoneCustomerException("系统默认优惠券-邀请新人卷不存在");
//        }
//        CouponsMemberRelation inviteCouponsMemberRelation = new CouponsMemberRelation();
//        inviteCouponsMemberRelation.setCouponsId(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID);
//        inviteCouponsMemberRelation.setMemberId(inviterId);
//        couponsMemberRelationService.insertSelective(inviteCouponsMemberRelation);
//
//    }
}