package com.siam.system.modular.package_user.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.entity.MemberBillingRecord;
import com.siam.system.modular.package_user.mapper.MemberBillingRecordMapper;
import com.siam.system.modular.package_user.model.dto.MemberBillingRecordDto;
import com.siam.system.modular.package_user.model.example.MemberBillingRecordExample;
import com.siam.system.modular.package_user.model.param.MemberBillingRecordParam;
import com.siam.system.modular.package_user.service.MemberBillingRecordService;
import com.siam.system.modular.package_user.service.MemberService;
import com.siam.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MemberBillingRecordServiceImpl implements MemberBillingRecordService {

    @Autowired
    private MemberBillingRecordMapper memberBillingRecordMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        memberBillingRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(MemberBillingRecord memberBillingRecord) {
        memberBillingRecordMapper.insertSelective(memberBillingRecord);
    }

    @Override
    public MemberBillingRecord selectByPrimaryKey(MemberBillingRecordParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        MemberBillingRecord memberBillingRecord = memberBillingRecordMapper.selectByPrimaryKey(param.getId());
        if(!memberBillingRecord.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("500, 该账单不是你的，不允许查看");
        }
        return memberBillingRecord;
    }

    @Override
    public void updateByPrimaryKeySelective(MemberBillingRecord memberBillingRecord) {
        memberBillingRecordMapper.updateByPrimaryKeySelective(memberBillingRecord);
    }

    @Override
    public List<MemberBillingRecord> selectByExample(MemberBillingRecordExample example) {
        return memberBillingRecordMapper.selectByExample(example);
    }

    @Override
    public Page getListByPage(MemberBillingRecordParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        param.setMemberId(loginMember.getId());
        Page<Map<String, Object>> page = memberBillingRecordMapper.getListByPage(new Page(param.getPageNo(), param.getPageSize()), param);
        return page;
    }

    @Override
    public BigDecimal selectSumNumber(MemberBillingRecordDto memberBillingRecordDto, Date startTime, Date endTime) {
        return memberBillingRecordMapper.selectSumNumber(memberBillingRecordDto, startTime, endTime);
    }

    @Override
    public void settledReward() {
        //TODO(MARK)-需要对外卖订单、商城订单的状态进行判断，只有订单为已完成才能发放奖励 -- 有些订单处于售后处理中，平台很久没处理，就会有bug
        //对未到账的积分、佣金奖励进行结算
        //结算时间：外卖系统-下单7天后，积分商城-下单25天后
        //外卖系统-可结算奖励
        List<MemberBillingRecord> list = memberBillingRecordMapper.getListBySettledReward(new MemberBillingRecord());
        list.forEach(dbMemberBillingRecord -> {
            if(dbMemberBillingRecord.getCoinType().equals(MemberBillingRecord.COIN_TYPE_UNRECEIVED_POINTS)){
                //获取用户当前积分数
                Member dbMember = memberService.selectByPrimaryKey(dbMemberBillingRecord.getMemberId());
                BigDecimal pointsNum = dbMember.getPoints().add(dbMemberBillingRecord.getNumber());
                BigDecimal unreceivedPointsNum = dbMember.getUnreceivedPoints().subtract(dbMemberBillingRecord.getNumber());
                //修改用户的积分数
                Member member = new Member();
                member.setId(dbMemberBillingRecord.getMemberId());
                member.setPoints(pointsNum);
                member.setTotalPoints(dbMember.getTotalPoints().add(dbMemberBillingRecord.getNumber()));
                member.setUnreceivedPoints(unreceivedPointsNum);
                memberService.updateByPrimaryKeySelective(member);
                dbMember = memberService.selectByPrimaryKey(dbMemberBillingRecord.getMemberId());

                //添加正式的账单记录
                MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
                memberBillingRecord.setMemberId(dbMemberBillingRecord.getMemberId());
                memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_POINTS);
                memberBillingRecord.setType(dbMemberBillingRecord.getType());
                memberBillingRecord.setOperateType(dbMemberBillingRecord.getOperateType());
                memberBillingRecord.setNumber(dbMemberBillingRecord.getNumber());
                memberBillingRecord.setMessage(dbMemberBillingRecord.getMessage());
                memberBillingRecord.setOrderId(dbMemberBillingRecord.getOrderId());
                memberBillingRecord.setCreateTime(new Date());
                this.insertSelective(memberBillingRecord);

                //修改状态为已结算
                MemberBillingRecord updateMemberBillingRecord = new MemberBillingRecord();
                updateMemberBillingRecord.setId(dbMemberBillingRecord.getId());
                updateMemberBillingRecord.setIsSettled(true);
                this.updateByPrimaryKeySelective(updateMemberBillingRecord);

            }else if(dbMemberBillingRecord.getCoinType().equals(MemberBillingRecord.COIN_TYPE_UNRECEIVED_INVITE_REWARD_AMOUNT)){
                //获取用户当前佣金
                Member dbMember = memberService.selectByPrimaryKey(dbMemberBillingRecord.getMemberId());
                BigDecimal inviteRewardAmountNum = dbMember.getInviteRewardAmount().add(dbMemberBillingRecord.getNumber());
                BigDecimal unreceivedInviteRewardAmountNum = dbMember.getUnreceivedInviteRewardAmount().subtract(dbMemberBillingRecord.getNumber());
                //修改用户的佣金
                Member member = new Member();
                member.setId(dbMemberBillingRecord.getMemberId());
                member.setInviteRewardAmount(inviteRewardAmountNum);
                member.setUnreceivedInviteRewardAmount(unreceivedInviteRewardAmountNum);
                memberService.updateByPrimaryKeySelective(member);
                dbMember = memberService.selectByPrimaryKey(dbMemberBillingRecord.getMemberId());

                //添加正式的账单记录
                MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
                memberBillingRecord.setMemberId(dbMemberBillingRecord.getMemberId());
                memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_INVITE_REWARD_AMOUNT);
                memberBillingRecord.setType(dbMemberBillingRecord.getType());
                memberBillingRecord.setOperateType(dbMemberBillingRecord.getOperateType());
                memberBillingRecord.setNumber(dbMemberBillingRecord.getNumber());
                memberBillingRecord.setMessage(dbMemberBillingRecord.getMessage());
                memberBillingRecord.setOrderId(dbMemberBillingRecord.getOrderId());
                memberBillingRecord.setCreateTime(new Date());
                this.insertSelective(memberBillingRecord);

                //修改状态为已结算
                MemberBillingRecord updateMemberBillingRecord = new MemberBillingRecord();
                updateMemberBillingRecord.setId(dbMemberBillingRecord.getId());
                updateMemberBillingRecord.setIsSettled(true);
                this.updateByPrimaryKeySelective(updateMemberBillingRecord);
            }
        });

        //积分商城-可结算奖励
        List<MemberBillingRecord> pointsMallList = memberBillingRecordMapper.getListBySettledRewardPointsMall(new MemberBillingRecord());
        pointsMallList.forEach(dbMemberBillingRecord -> {
            if(dbMemberBillingRecord.getCoinType().equals(MemberBillingRecord.COIN_TYPE_UNRECEIVED_INVITE_REWARD_AMOUNT)){
                //获取用户当前佣金
                Member dbMember = memberService.selectByPrimaryKey(dbMemberBillingRecord.getMemberId());
                BigDecimal inviteRewardAmountNum = dbMember.getInviteRewardAmount().add(dbMemberBillingRecord.getNumber());
                BigDecimal unreceivedInviteRewardAmountNum = dbMember.getUnreceivedInviteRewardAmount().subtract(dbMemberBillingRecord.getNumber());
                //修改用户的佣金
                Member member = new Member();
                member.setId(dbMemberBillingRecord.getMemberId());
                member.setInviteRewardAmount(inviteRewardAmountNum);
                member.setUnreceivedInviteRewardAmount(unreceivedInviteRewardAmountNum);
                memberService.updateByPrimaryKeySelective(member);
                dbMember = memberService.selectByPrimaryKey(dbMemberBillingRecord.getMemberId());

                //添加正式的账单记录
                MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
                memberBillingRecord.setMemberId(dbMemberBillingRecord.getMemberId());
                memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_INVITE_REWARD_AMOUNT);
                memberBillingRecord.setType(dbMemberBillingRecord.getType());
                memberBillingRecord.setOperateType(dbMemberBillingRecord.getOperateType());
                memberBillingRecord.setNumber(dbMemberBillingRecord.getNumber());
                memberBillingRecord.setMessage(dbMemberBillingRecord.getMessage());
                memberBillingRecord.setPointsMallOrderId(dbMemberBillingRecord.getPointsMallOrderId());
                memberBillingRecord.setCreateTime(new Date());
                this.insertSelective(memberBillingRecord);

                //修改状态为已结算
                MemberBillingRecord updateMemberBillingRecord = new MemberBillingRecord();
                updateMemberBillingRecord.setId(dbMemberBillingRecord.getId());
                updateMemberBillingRecord.setIsSettled(true);
                this.updateByPrimaryKeySelective(updateMemberBillingRecord);
            }
        });
    }
}