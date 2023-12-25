package com.siam.system.modular.package_order.controller.member.internal;

import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.util.Base64Utils;
import com.siam.package_common.util.CommonUtils;
import com.siam.package_common.util.GenerateNo;
import com.siam.system.modular.package_user.service.MemberBillingRecordService;
import com.siam.system.modular.package_user.service.MemberService;
import com.siam.system.modular.package_user.service.MemberTradeRecordService;
import com.siam.system.modular.package_order.controller.member.PlatformPayDto;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrder;
import com.siam.system.modular.package_order.service.internal.PointsMallOrderService;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.entity.MemberBillingRecord;
import com.siam.system.modular.package_user.entity.MemberTradeRecord;
import com.siam.system.modular.package_user.model.example.MemberTradeRecordExample;
import com.siam.system.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping(value = "/rest/member/pointsMall/platformPay")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "平台支付模块相关接口", description = "PointsMallPlatformPayController")
public class PointsMallPlatformPayController {

    @Autowired
    private MemberTradeRecordService memberTradeRecordService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberBillingRecordService memberBillingRecordService;

    @Resource(name = "pointsMallOrderServiceImpl")
    private PointsMallOrderService pointsMallOrderService;

    /**
     * 和微信支付的步骤一致
     * 第一步，确认订单信息
     * 第二步，验证支付密码，验证积分是否充足
     * 第三部，支付回调
     * 现在我们的就是把这三步结合在一个接口完成，但是步骤不能乱，否则代码不能复用
     * PS：但是如果按照微信支付的步骤来，只要某个步骤失败，事务就会回滚--莫非这种就是不能在Controller上添加事务注解的原因？
     * @return
     * @author 暹罗
     */
    @ApiOperation(value = "使用平台积分支付")
    @PostMapping(value = "/byPoints")
    public BasicResult byPoints(@RequestBody @Validated PlatformPayDto platformPayDto, HttpServletRequest request) {
        //TODO-PS：但是如果按照微信支付的步骤来，只要某个步骤失败，事务就会回滚--莫非这种就是不能在Controller上添加事务注解的原因？
        //TODO-之前都没有验证过该订单是否属于当前登录用户
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        Member dbMember = memberService.selectByPrimaryKey(loginMember.getId());

        if(platformPayDto.getType()!=Quantity.INT_4){
            throw new StoneCustomerException("交易类型错误");
        }

        //商户单号
        String outTradeNo = null;

        //自取订单改为配送的商户单号
        String changeToDeliveryOutTradeNo = null;

        if(platformPayDto.getType() == Quantity.INT_4){
            //原订单对象
            PointsMallOrder dbOrder = null;

            //交易类型为订单付款
            if(platformPayDto.getOut_trade_no() == null){
                throw new StoneCustomerException("必须填写商户单号");
            }
            dbOrder = pointsMallOrderService.selectByOrderNo(platformPayDto.getOut_trade_no());
            if(dbOrder == null){
                throw new StoneCustomerException("该商户单号不存在");
            }
            if(dbOrder.getStatus()!=Quantity.INT_1 && dbOrder.getStatus()==Quantity.INT_10){
                throw new StoneCustomerException("该笔订单超时未支付，已自动取消，请重新下单");
            }
            if(dbOrder.getActualPrice().compareTo(platformPayDto.getTotal_fee()) != 0){
                basicResult.setSuccess(false);
                basicResult.setMessage("付款金额错误");
                return basicResult;
            }
            outTradeNo = platformPayDto.getOut_trade_no();

            //添加用户交易记录
            MemberTradeRecord insertMemberTradeRecord = new MemberTradeRecord();
            insertMemberTradeRecord.setMemberId(loginMember.getId());
            insertMemberTradeRecord.setOutTradeNo(outTradeNo);
            insertMemberTradeRecord.setType(Quantity.INT_6);
            insertMemberTradeRecord.setPaymentMode(Quantity.INT_4);
            insertMemberTradeRecord.setAmount(platformPayDto.getTotal_fee());
            insertMemberTradeRecord.setStatus(Quantity.INT_1);
            insertMemberTradeRecord.setCreateTime(new Date());
            insertMemberTradeRecord.setUpdateTime(new Date());
            memberTradeRecordService.insertSelective(insertMemberTradeRecord);

            //修改订单信息：补填用户交易id
            PointsMallOrder updateOrder = new PointsMallOrder();
            updateOrder.setId(dbOrder.getId());
            updateOrder.setTradeId(insertMemberTradeRecord.getId());
            updateOrder.setPaymentMode(Quantity.INT_3);
            updateOrder.setUpdateTime(new Date());
            pointsMallOrderService.updateByPrimaryKeySelective(updateOrder);
        }



        //判断支付密码是否正确
        String paymentPassword = Base64Utils.decode(platformPayDto.getPaymentPassword());
        paymentPassword = CommonUtils.genMd5Password(paymentPassword, dbMember.getPaymentPasswordSalt());
        if(!paymentPassword.equals(dbMember.getPaymentPassword())){
            throw new StoneCustomerException("支付密码输入错误，请重新输入");
        }

        //校验积分是否充足
        if(dbMember.getPoints().compareTo(platformPayDto.getTotal_fee()) < 0){
            throw new StoneCustomerException("您的积分不足，请选择其它支付方式");
        }
        //减少用户的积分
        Member updateMember = new Member();
        updateMember.setId(dbMember.getId());
        updateMember.setPoints(dbMember.getPoints().subtract(platformPayDto.getTotal_fee()));
        updateMember.setTotalConsumePoints(dbMember.getTotalConsumePoints().add(platformPayDto.getTotal_fee()));
        memberService.updateByPrimaryKeySelective(updateMember);
        dbMember = memberService.selectByPrimaryKey(loginMember.getId());
        //添加账单记录
        MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
        memberBillingRecord.setMemberId(dbMember.getId());
        memberBillingRecord.setType(MemberBillingRecord.TYPE_POINTSMALL_ORDER_PAYMENT_BY_POINTS);
        memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_SUB);
        memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_POINTS);
        memberBillingRecord.setNumber(platformPayDto.getTotal_fee());
        memberBillingRecord.setMessage("积分商城订单使用积分支付");
        memberBillingRecord.setCreateTime(new Date());
        memberBillingRecordService.insertSelective(memberBillingRecord);

        MemberTradeRecord dbMemberTradeRecord = memberTradeRecordService.selectByOutTradeNo(outTradeNo);
        if(dbMemberTradeRecord == null){
            throw new StoneCustomerException("该商户单号不存在，回调逻辑处理失败");
        }
        //为防止微信重复回调，需要对用户交易记录的状态进行判断
        if(dbMemberTradeRecord.getStatus() != Quantity.INT_1){
            throw new StoneCustomerException("微信重复回调，商户单号为" + outTradeNo);
        }

        //补填交易单号(支付平台的订单号)，将交易状态改为支付成功
        MemberTradeRecord updateMemberTradeRecord = new MemberTradeRecord();
        updateMemberTradeRecord.setId(dbMemberTradeRecord.getId());
        updateMemberTradeRecord.setStatus(Quantity.INT_2);
        updateMemberTradeRecord.setUpdateTime(new Date());
        memberTradeRecordService.updateByPrimaryKeySelective(updateMemberTradeRecord);

        if(dbMemberTradeRecord.getType() == Quantity.INT_6){
            //交易类型为积分商城订单付款
            pointsMallOrderService.paymentNotify(outTradeNo);
        }

        basicResult.setSuccess(true);
        basicResult.setMessage("支付成功");
        return basicResult;
    }

    /**
     * 生成商户单号/订单编号
     * [此处要做两个判断，一个是针对于订单编号，一个是针对于自取订单改为配送的商户单号]
     * => 考虑到商户单号不止针对于订单表，还有Vip充值等操作，所以应该在用户交易记录表中判断商户单号是否重复
     */
    public String getOutTradeNo(){
        int i = 0;
        String outTradeNo = GenerateNo.getOrderNo();
        while (true){
            if(i == 99){
                throw new StoneCustomerException("无法生成商户单号");
            }
            log.debug("\n获取商户单号...");
            MemberTradeRecordExample memberTradeRecordExample = new MemberTradeRecordExample();
            memberTradeRecordExample.createCriteria().andOutTradeNoEqualTo(outTradeNo);
//            int result = memberTradeRecordService.countByExample(memberTradeRecordExample);
//            if(result > 0){
//                outTradeNo = GenerateNo.getOrderNo();
//            }else{
//                break;
//            }

            i++;
        }
//        return outTradeNo;
    }
}