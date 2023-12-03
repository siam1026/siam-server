package com.siam.system.modular.package_user.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_weixin_pay.entity.TransfersDto;
import com.siam.package_common.util.GenerateNo;
import com.siam.system.modular.package_goods.service.SettingService;
import com.siam.system.modular.package_order.controller.member.WxPayService;
import com.siam.system.modular.package_goods.entity.Setting;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.system.modular.package_user.entity.*;
import com.siam.system.modular.package_user.mapper.MerchantWithdrawRecordMapper;
import com.siam.system.modular.package_user.model.example.MerchantWithdrawRecordExample;
import com.siam.system.modular.package_user.model.param.MerchantWithdrawRecordParam;
import com.siam.system.modular.package_user.service.*;
import com.siam.system.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MerchantWithdrawRecordServiceImpl extends ServiceImpl<MerchantWithdrawRecordMapper, MerchantWithdrawRecord> implements MerchantWithdrawRecordService {

    @Autowired
    private MerchantWithdrawRecordMapper merchantWithdrawRecordMapper;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberTradeRecordService memberTradeRecordService;

    @Autowired
    private MerchantBillingRecordService merchantBillingRecordService;

    @Autowired
    private MerchantSessionManager merchantSessionManager;

//    @Autowired
//    private MerchantService merchantService;

//    @Autowired
//    private SettingService settingService;

    @Override
    public int countByExample(MerchantWithdrawRecordExample example) {
        return merchantWithdrawRecordMapper.countByExample(example);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        merchantWithdrawRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(MerchantWithdrawRecordParam param) {
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());
        Merchant dbMerchant = merchantService.selectByPrimaryKey(loginMerchant.getId());

        if(param.getWithdrawAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new StoneCustomerException("提现金额必须大于0");
        }

        //自动计算平台手续费
        Setting setting = settingService.selectCurrent();
        BigDecimal merchantWithdrawFee = setting.getMerchantWithdrawFee().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal platformFee = param.getWithdrawAmount().multiply(merchantWithdrawFee).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal totalAmount = param.getWithdrawAmount().add(platformFee).setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal actualAmount = param.getWithdrawAmount();
        if(totalAmount.compareTo(dbMerchant.getWithdrawableBalance()) > 0){
            actualAmount = param.getWithdrawAmount().subtract(platformFee).setScale(2, BigDecimal.ROUND_HALF_UP);
            totalAmount = param.getWithdrawAmount();
        }

        //判断余额是否充足
        if(totalAmount.compareTo(dbMerchant.getWithdrawableBalance()) > 0){
            throw new StoneCustomerException("余额不足，请重新填写提现金额");
        }

        // 获取订单编号
        int i = 0;
        String orderNo = GenerateNo.getOrderNo();
        while (true){
            if(i == 99){
                throw new StoneCustomerException("无法生成订单编号");
            }
            log.debug("\n获取订单编号...");
            MerchantWithdrawRecordExample merchantWithdrawRecordExample = new MerchantWithdrawRecordExample();
            merchantWithdrawRecordExample.createCriteria().andOrderNoEqualTo(orderNo);
            int result = this.countByExample(merchantWithdrawRecordExample);
            if(result > 0){
                orderNo = GenerateNo.getOrderNo();
            }else{
                break;
            }
            i++;
        }

        param.setMerchantId(dbMerchant.getId());
        param.setOrderNo(orderNo);
        param.setPlatformFee(platformFee);
        param.setActualAmount(actualAmount);
        param.setCreateTime(new Date());
        param.setUpdateTime(new Date());
        this.save(param);

        //减少商家的余额
        BigDecimal updateBalance = dbMerchant.getBalance().subtract(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP);;

        BigDecimal updateWithdrawableBalance = dbMerchant.getWithdrawableBalance().subtract(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP);;

        Merchant updateMerchant = new Merchant();
        updateMerchant.setId(dbMerchant.getId());
        updateMerchant.setBalance(updateBalance);
        updateMerchant.setWithdrawableBalance(updateWithdrawableBalance);
        updateMerchant.setUpdateTime(new Date());
        merchantService.updateByPrimaryKeySelective(updateMerchant);

        //增加商家账单记录
        MerchantBillingRecord merchantBillingRecord = new MerchantBillingRecord();
        merchantBillingRecord.setMerchantId(dbMerchant.getId());
        merchantBillingRecord.setType(Quantity.INT_2);
        merchantBillingRecord.setOperateType(Quantity.INT_2);
        merchantBillingRecord.setCoinType(Quantity.INT_1);
        merchantBillingRecord.setNumber(actualAmount);
        merchantBillingRecord.setServiceFee(param.getPlatformFee());
        merchantBillingRecord.setMessage("商家提现 -- 订单号" + param.getOrderNo());
        merchantBillingRecord.setCreateTime(new Date());
        merchantBillingRecordService.insertSelective(merchantBillingRecord);
    }

    @Override
    public MerchantWithdrawRecord selectByPrimaryKey(Integer id) {
        return merchantWithdrawRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(MerchantWithdrawRecord merchantWithdrawRecord) {
        merchantWithdrawRecordMapper.updateByPrimaryKeySelective(merchantWithdrawRecord);
    }

    @Override
    public Page getListByPage(MerchantWithdrawRecordParam param) {
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());
        param.setMerchantId(loginMerchant.getId());
        Page<Map<String, Object>> page = merchantWithdrawRecordMapper.getListByPage(new Page(param.getPageNo(), param.getPageSize()), param);
        return page;
    }

    @Override
    public Page getListByPageJoinShop(MerchantWithdrawRecordParam param) {
        Page<Map<String, Object>> page = merchantWithdrawRecordMapper.getListByPageJoinShop(new Page(param.getPageNo(), param.getPageSize()), param);
        return page;
    }

    @Override
    public Map<String, Object> statisticalAmount(MerchantWithdrawRecordParam param) {
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());
        if(loginMerchant != null){
            param.setMerchantId(loginMerchant.getId());
        }

        BigDecimal withdrawalSuccessfulAmount = merchantWithdrawRecordMapper.statisticalAmountByWithdrawalSuccessful(param);

        Map<String, Object> map = new HashMap<>();
        map.put("withdrawalSuccessfulAmount", withdrawalSuccessfulAmount);
        return map;
    }

    @Override
    public void auditApplyWithdraw(MerchantWithdrawRecordParam param) {
        BasicResult basicResult = new BasicResult();

        if(param.getStatus() == Quantity.INT_2 && StringUtils.isBlank(param.getOpinion())){
            throw new StoneCustomerException("审核不通过时，审核意见不能为空");
        }

        MerchantWithdrawRecord dbMerchantWithdrawRecord = this.selectByPrimaryKey(param.getId());
        if(dbMerchantWithdrawRecord.getAuditStatus() != Quantity.INT_1){
            throw new StoneCustomerException("该提现记录的审核状态错误");
        }

        Merchant dbMerchant = merchantService.selectByPrimaryKey(dbMerchantWithdrawRecord.getMerchantId());

        //TODO-进行提现操作
        if(param.getStatus() == Quantity.INT_1){
            //审核通过
            Member bindMember = memberService.selectByPrimaryKey(dbMerchant.getMemberId());

            //企业付款到零钱
            String orderNo = dbMerchantWithdrawRecord.getOrderNo();
            TransfersDto transfersDto = new TransfersDto();
            transfersDto.setOpenid(bindMember.getOpenId());
            transfersDto.setAmount(dbMerchantWithdrawRecord.getWithdrawAmount().doubleValue());
            transfersDto.setRe_user_name(dbMerchant.getRealName());
            transfersDto.setPartner_trade_no(orderNo);
            transfersDto.setDesc("暹罗外卖-商家提现到账");
            boolean isPaySuccess = wxPayService.payToBalance(transfersDto);
            if(!isPaySuccess){
                throw new StoneCustomerException("打款失败，请联系管理员");
            }

            //修改商家申请信息
            MerchantWithdrawRecord updateMerchantWithdrawRecord = new MerchantWithdrawRecord();
            updateMerchantWithdrawRecord.setId(param.getId());
            updateMerchantWithdrawRecord.setAuditStatus(Quantity.INT_2);
            updateMerchantWithdrawRecord.setAuditTime(new Date());
            updateMerchantWithdrawRecord.setUpdateTime(new Date());
            this.updateByPrimaryKeySelective(updateMerchantWithdrawRecord);

            //添加商家交易记录
            MemberTradeRecord insertMemberTradeRecord = new MemberTradeRecord();
            insertMemberTradeRecord.setMerchantId(dbMerchant.getId());
            insertMemberTradeRecord.setOutTradeNo(orderNo);
            insertMemberTradeRecord.setType(Quantity.INT_4);
            insertMemberTradeRecord.setPaymentMode(Quantity.INT_1);
            insertMemberTradeRecord.setAmount(dbMerchantWithdrawRecord.getWithdrawAmount());
            insertMemberTradeRecord.setStatus(Quantity.INT_2);
            insertMemberTradeRecord.setCreateTime(new Date());
            insertMemberTradeRecord.setUpdateTime(new Date());
            memberTradeRecordService.insertSelective(insertMemberTradeRecord);

            //TODO-发消息通知用户
        }else if(param.getStatus() == Quantity.INT_2){
            //审核不通过
            //修改商家申请信息
            MerchantWithdrawRecord updateMerchantWithdrawRecord = new MerchantWithdrawRecord();
            updateMerchantWithdrawRecord.setId(param.getId());
            updateMerchantWithdrawRecord.setAuditStatus(Quantity.INT_3);
            updateMerchantWithdrawRecord.setAuditReason(param.getOpinion());
            updateMerchantWithdrawRecord.setAuditTime(new Date());
            updateMerchantWithdrawRecord.setUpdateTime(new Date());
            this.updateByPrimaryKeySelective(updateMerchantWithdrawRecord);

            //退回商家提现扣除的金额
            BigDecimal totalAmount = dbMerchantWithdrawRecord.getActualAmount().add(dbMerchantWithdrawRecord.getPlatformFee()).setScale(2, BigDecimal.ROUND_HALF_UP);

            //增加商家的余额
            BigDecimal updateBalance = dbMerchant.getBalance().add(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP);;

            BigDecimal updateWithdrawableBalance = dbMerchant.getWithdrawableBalance().add(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP);;

            Merchant updateMerchant = new Merchant();
            updateMerchant.setId(dbMerchant.getId());
            updateMerchant.setBalance(updateBalance);
            updateMerchant.setWithdrawableBalance(updateWithdrawableBalance);
            updateMerchant.setUpdateTime(new Date());
            merchantService.updateByPrimaryKeySelective(updateMerchant);

            //增加商家账单记录
            MerchantBillingRecord merchantBillingRecord = new MerchantBillingRecord();
            merchantBillingRecord.setMerchantId(dbMerchantWithdrawRecord.getMerchantId());
            merchantBillingRecord.setType(Quantity.INT_3);
            merchantBillingRecord.setOperateType(Quantity.INT_1);
            merchantBillingRecord.setCoinType(Quantity.INT_1);
            merchantBillingRecord.setNumber(totalAmount);
            merchantBillingRecord.setMessage("商家提现失败退回 -- 订单号" + dbMerchantWithdrawRecord.getOrderNo());
            merchantBillingRecord.setCreateTime(new Date());
            merchantBillingRecordService.insertSelective(merchantBillingRecord);

            //TODO-发消息通知用户
        }
    }

    @Override
    public BasicResult countByAuditStatus(int auditStatus) {
        MerchantWithdrawRecordExample merchantWithdrawExample = new MerchantWithdrawRecordExample();
        merchantWithdrawExample.createCriteria().andAuditStatusEqualTo(auditStatus);
        return BasicResult.success(this.countByExample(merchantWithdrawExample));
    }
}