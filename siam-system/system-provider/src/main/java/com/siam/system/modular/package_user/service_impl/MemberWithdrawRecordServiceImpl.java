package com.siam.system.modular.package_user.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_weixin_pay.entity.TransfersDto;
import com.siam.package_common.util.Base64Utils;
import com.siam.package_common.util.CommonUtils;
import com.siam.package_common.util.GenerateNo;
import com.siam.system.modular.package_goods.service.SettingService;
import com.siam.system.modular.package_order.controller.member.WxPayService;
import com.siam.system.modular.package_goods.entity.Setting;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.entity.MemberBillingRecord;
import com.siam.system.modular.package_user.entity.MemberTradeRecord;
import com.siam.system.modular.package_user.entity.MemberWithdrawRecord;
import com.siam.system.modular.package_user.mapper.MemberWithdrawRecordMapper;
import com.siam.system.modular.package_user.model.example.MemberWithdrawRecordExample;
import com.siam.system.modular.package_user.model.param.MemberWithdrawRecordParam;
import com.siam.system.modular.package_user.service.*;
import com.siam.system.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MemberWithdrawRecordServiceImpl extends ServiceImpl<MemberWithdrawRecordMapper, MemberWithdrawRecord> implements MemberWithdrawRecordService {

    @Autowired
    private MemberWithdrawRecordMapper memberWithdrawRecordMapper;

//    @Autowired
//    private SettingService settingService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberTradeRecordService memberTradeRecordService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private MemberBillingRecordService memberBillingRecordService;

    @Autowired
    private MemberSessionManager memberSessionManager;

//    @Autowired
//    private SettingService settingService;

    @Override
    public int countByExample(MemberWithdrawRecordExample example) {
        return memberWithdrawRecordMapper.countByExample(example);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        memberWithdrawRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(MemberWithdrawRecordParam param) {
        //TODO-这里如果真实姓名未初始化时，还会输入一个真实姓名
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        Member dbMember = memberService.selectByPrimaryKey(loginMember.getId());

        if(StringUtils.isBlank(dbMember.getRealName())){
            throw new StoneCustomerException("您的真实姓名还未填写");
        }

        //判断密码是否匹配
        String paymentPassword = Base64Utils.decode(param.getPaymentPassword());
        paymentPassword = CommonUtils.genMd5Password(paymentPassword, dbMember.getPaymentPasswordSalt());
        if(!paymentPassword.equals(dbMember.getPaymentPassword())){
            throw new StoneCustomerException(500, "支付密码输入错误，请重新输入");
        }

        if(param.getWithdrawAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new StoneCustomerException("提现金额必须大于0");
        }

        Setting setting = settingService.selectCurrent();
        if(dbMember.getInviteRewardAmount().compareTo(setting.getMemberWithdrawMeetAmount()) < 0){
            throw new StoneCustomerException("奖励金累计到(≥)" + setting.getMemberWithdrawMeetAmount() + "元才可以提现");
        }

        //自动计算平台手续费
        BigDecimal memberWithdrawFee = setting.getMemberWithdrawFee().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal platformFee = param.getWithdrawAmount().multiply(memberWithdrawFee).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal totalAmount = param.getWithdrawAmount().add(platformFee).setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal actualAmount = param.getWithdrawAmount();
        if(totalAmount.compareTo(dbMember.getInviteRewardAmount()) > 0){
            actualAmount = param.getWithdrawAmount().subtract(platformFee).setScale(2, BigDecimal.ROUND_HALF_UP);
            totalAmount = param.getWithdrawAmount();
        }

        log.debug("\n\nmemberWithdrawFee = " + memberWithdrawFee);
        log.debug("\n\nmemberWithdrawRecord.getWithdrawAmount() = " + param.getWithdrawAmount());
        log.debug("\n\nplatformFee = " + platformFee);
        log.debug("\n\nactualAmount = " + actualAmount);
        log.debug("\n\ntotalAmount = " + totalAmount);
        //判断余额是否充足
        if(totalAmount.compareTo(dbMember.getInviteRewardAmount()) > 0){
            throw new StoneCustomerException("奖励金不足，请重新填写提现金额");
        }

        // 获取订单编号
        int i = 0;
        String orderNo = GenerateNo.getOrderNo();
        while (true){
            if(i == 99){
                throw new StoneCustomerException("无法生成订单编号");
            }
            log.debug("\n获取订单编号...");
            MemberWithdrawRecordExample memberWithdrawRecordExample = new MemberWithdrawRecordExample();
            memberWithdrawRecordExample.createCriteria().andOrderNoEqualTo(orderNo);
            int result = this.countByExample(memberWithdrawRecordExample);
            if(result > 0){
                orderNo = GenerateNo.getOrderNo();
            }else{
                break;
            }
            i++;
        }

        param.setMemberId(loginMember.getId());
        param.setOrderNo(orderNo);
        param.setCoinType(Quantity.INT_1);
        param.setPlatformFee(platformFee);
        param.setActualAmount(actualAmount);
        param.setCreateTime(new Date());
        param.setUpdateTime(new Date());
        this.save(param);

        //减少用户的邀请新用户注册奖励金额
        BigDecimal updateWithdrawableBalance = dbMember.getInviteRewardAmount().subtract(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP);

        Member updateMember = new Member();
        updateMember.setId(dbMember.getId());
        updateMember.setInviteRewardAmount(updateWithdrawableBalance);
        updateMember.setTotalWithdrawInviteRewardAmount(dbMember.getTotalWithdrawInviteRewardAmount().add(totalAmount));
        updateMember.setUpdateTime(new Date());
        memberService.updateByPrimaryKeySelective(updateMember);
        dbMember = memberService.selectByPrimaryKey(loginMember.getId());

        //TODO-增加用户账单记录
        MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
        memberBillingRecord.setMemberId(dbMember.getId());
        memberBillingRecord.setType(MemberBillingRecord.TYPE_INVITE_REWARD_AMOUNT_WITHDRAW);
        memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_SUB);
        memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_INVITE_REWARD_AMOUNT);
        memberBillingRecord.setNumber(actualAmount);
        memberBillingRecord.setServiceFee(platformFee);
        /*memberBillingRecord.setServiceFee(memberWithdrawRecord.getPlatformFee());*/
        memberBillingRecord.setMessage("用户提现 -- 订单号" + param.getOrderNo());
        memberBillingRecord.setCreateTime(new Date());
        memberBillingRecordService.insertSelective(memberBillingRecord);
    }

    @Override
    public MemberWithdrawRecord selectByPrimaryKey(Integer id) {
        return memberWithdrawRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(MemberWithdrawRecord memberWithdrawRecord) {
        memberWithdrawRecordMapper.updateByPrimaryKeySelective(memberWithdrawRecord);
    }

    @Override
    public Page getListByPage(MemberWithdrawRecordParam param) {
        //获取当前登录用户绑定的门店编号
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        param.setMemberId(loginMember.getId());
        Page<Map<String, Object>> page = memberWithdrawRecordMapper.getListByPage(new Page(param.getPageNo(), param.getPageSize()), param);
        return page;
    }

    @Override
    public Page getListByPageJoinMember(MemberWithdrawRecordParam param) {
        Page<Map<String, Object>> page = memberWithdrawRecordMapper.getListByPageJoinMember(new Page(param.getPageNo(), param.getPageSize()), param);
        return page;
    }

    @Override
    public Map<String, Object> statisticalAmount(MemberWithdrawRecordParam param) {
        BigDecimal withdrawalSuccessfulAmount = memberWithdrawRecordMapper.statisticalAmountByWithdrawalSuccessful(param);

        Map<String, Object> map = new HashMap<>();
        map.put("withdrawalSuccessfulAmount", withdrawalSuccessfulAmount);
        return map;
    }

    @Override
    public void autoPayment() {
        //查询所有符合条件的用户提现记录，进行自动打款
        Setting setting = settingService.selectCurrent();
        List<MemberWithdrawRecord> memberWithdrawRecordList = memberWithdrawRecordMapper.selectByNeedAutoPayment(setting.getMemberWithdrawAuditThreshold());
        memberWithdrawRecordList.forEach(memberWithdrawRecord -> {
            Member dbMember = memberService.selectByPrimaryKey(memberWithdrawRecord.getMemberId());

            //企业付款到零钱
            String orderNo = memberWithdrawRecord.getOrderNo();
            TransfersDto transfersDto = new TransfersDto();
            transfersDto.setOpenid(dbMember.getOpenId());
            transfersDto.setAmount(memberWithdrawRecord.getWithdrawAmount().doubleValue());
            transfersDto.setRe_user_name(dbMember.getRealName());
            transfersDto.setPartner_trade_no(orderNo);
            transfersDto.setDesc("暹罗外卖-用户提现到账");
            boolean isPaySuccess = wxPayService.payToBalance(transfersDto);
            if(!isPaySuccess){
                throw new StoneCustomerException("打款失败，请联系管理员");
            }

            //修改用户申请信息
            MemberWithdrawRecord updateMemberWithdrawRecord = new MemberWithdrawRecord();
            updateMemberWithdrawRecord.setId(memberWithdrawRecord.getId());
            updateMemberWithdrawRecord.setAuditStatus(Quantity.INT_2);
            updateMemberWithdrawRecord.setAuditTime(new Date());
            updateMemberWithdrawRecord.setUpdateTime(new Date());
            this.updateByPrimaryKeySelective(updateMemberWithdrawRecord);

            //添加用户交易记录
            MemberTradeRecord insertMemberTradeRecord = new MemberTradeRecord();
            insertMemberTradeRecord.setMemberId(dbMember.getId());
            insertMemberTradeRecord.setOutTradeNo(orderNo);
            insertMemberTradeRecord.setType(Quantity.INT_4);
            insertMemberTradeRecord.setPaymentMode(Quantity.INT_1);
            insertMemberTradeRecord.setAmount(memberWithdrawRecord.getWithdrawAmount());
            insertMemberTradeRecord.setStatus(Quantity.INT_2);
            insertMemberTradeRecord.setCreateTime(new Date());
            insertMemberTradeRecord.setUpdateTime(new Date());
            memberTradeRecordService.insertSelective(insertMemberTradeRecord);
        });
    }

    @Override
    public void auditApplyWithdraw(MemberWithdrawRecordParam param) {
        BasicResult basicResult = new BasicResult();

        if(param.getStatus() == Quantity.INT_2 && StringUtils.isBlank(param.getOpinion())){
            throw new StoneCustomerException("审核不通过时，审核意见不能为空");
        }

        MemberWithdrawRecord dbMemberWithdrawRecord = this.selectByPrimaryKey(param.getId());
        if(dbMemberWithdrawRecord.getAuditStatus() != Quantity.INT_1){
            throw new StoneCustomerException("该提现记录的审核状态错误");
        }

        Member dbMember = memberService.selectByPrimaryKey(dbMemberWithdrawRecord.getMemberId());

        //TODO-进行提现操作
        if(param.getStatus() == Quantity.INT_1){
            //企业付款到零钱
            String orderNo = dbMemberWithdrawRecord.getOrderNo();
            TransfersDto transfersDto = new TransfersDto();
            transfersDto.setOpenid(dbMember.getOpenId());
            transfersDto.setAmount(dbMemberWithdrawRecord.getWithdrawAmount().doubleValue());
            transfersDto.setRe_user_name(dbMember.getRealName());
            transfersDto.setPartner_trade_no(orderNo);
            transfersDto.setDesc("暹罗外卖-用户提现到账");
            boolean isPaySuccess = wxPayService.payToBalance(transfersDto);
            if(!isPaySuccess){
                throw new StoneCustomerException("打款失败，请联系管理员");
            }

            //修改用户申请信息
            MemberWithdrawRecord updateMemberWithdrawRecord = new MemberWithdrawRecord();
            updateMemberWithdrawRecord.setId(param.getId());
            updateMemberWithdrawRecord.setAuditStatus(Quantity.INT_2);
            updateMemberWithdrawRecord.setAuditTime(new Date());
            updateMemberWithdrawRecord.setUpdateTime(new Date());
            this.updateByPrimaryKeySelective(updateMemberWithdrawRecord);

            //添加用户交易记录
            MemberTradeRecord insertMemberTradeRecord = new MemberTradeRecord();
            insertMemberTradeRecord.setMemberId(dbMember.getId());
            insertMemberTradeRecord.setOutTradeNo(orderNo);
            insertMemberTradeRecord.setType(Quantity.INT_4);
            insertMemberTradeRecord.setPaymentMode(Quantity.INT_1);
            insertMemberTradeRecord.setAmount(dbMemberWithdrawRecord.getWithdrawAmount());
            insertMemberTradeRecord.setStatus(Quantity.INT_2);
            insertMemberTradeRecord.setCreateTime(new Date());
            insertMemberTradeRecord.setUpdateTime(new Date());
            memberTradeRecordService.insertSelective(insertMemberTradeRecord);

            //TODO-发消息通知用户
        }else if(param.getStatus() == Quantity.INT_2){
            //审核不通过
            //修改用户申请信息
            MemberWithdrawRecord updateMemberWithdrawRecord = new MemberWithdrawRecord();
            updateMemberWithdrawRecord.setId(param.getId());
            updateMemberWithdrawRecord.setAuditStatus(Quantity.INT_3);
            updateMemberWithdrawRecord.setAuditReason(param.getOpinion());
            updateMemberWithdrawRecord.setAuditTime(new Date());
            updateMemberWithdrawRecord.setUpdateTime(new Date());
            this.updateByPrimaryKeySelective(updateMemberWithdrawRecord);

            //退回用户提现扣除的金额
            BigDecimal totalAmount = dbMemberWithdrawRecord.getActualAmount().add(dbMemberWithdrawRecord.getPlatformFee()).setScale(2, BigDecimal.ROUND_HALF_UP);

            //增加用户的邀请新用户注册奖励金额
            BigDecimal updateInviteRewardAmount = dbMember.getInviteRewardAmount().add(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP);

            Member updateMember = new Member();
            updateMember.setId(dbMember.getId());
            updateMember.setInviteRewardAmount(updateInviteRewardAmount);
            updateMember.setTotalWithdrawInviteRewardAmount(dbMember.getTotalWithdrawInviteRewardAmount().subtract(totalAmount));
            updateMember.setUpdateTime(new Date());
            memberService.updateByPrimaryKeySelective(updateMember);
            dbMember = memberService.selectByPrimaryKey(dbMemberWithdrawRecord.getMemberId());

            //增加用户账单记录
            MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
            memberBillingRecord.setMemberId(dbMemberWithdrawRecord.getMemberId());
            memberBillingRecord.setType(MemberBillingRecord.TYPE_INVITE_REWARD_AMOUNT_WITHDRAW_FAIL_RETURN);
            memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
            memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_INVITE_REWARD_AMOUNT);
            memberBillingRecord.setNumber(totalAmount);
            memberBillingRecord.setMessage("用户提现失败退回 -- 订单号" + dbMemberWithdrawRecord.getOrderNo());
            memberBillingRecord.setCreateTime(new Date());
            memberBillingRecordService.insertSelective(memberBillingRecord);

            //TODO-发消息通知用户
        }
    }
}