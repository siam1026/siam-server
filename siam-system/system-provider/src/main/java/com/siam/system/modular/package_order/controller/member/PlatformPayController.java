package com.siam.system.modular.package_order.controller.member;

import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_weixin_pay.config.WxPayConfig;
import com.siam.package_common.util.Base64Utils;
import com.siam.package_common.util.CommonUtils;
import com.siam.package_common.util.GenerateNo;
import com.siam.package_weixin_pay.util.WxdecodeUtils;
import com.siam.system.modular.package_goods.service.ShopService;
import com.siam.system.modular.package_user.service.MemberBillingRecordService;
import com.siam.system.modular.package_user.service.MemberService;
import com.siam.system.modular.package_user.service.MemberTradeRecordService;
import com.siam.system.modular.package_goods.entity.Shop;
import com.siam.system.modular.package_order.entity.DeliveryAddress;
import com.siam.system.modular.package_order.entity.Order;
import com.siam.system.modular.package_order.service.CommonService;
import com.siam.system.modular.package_order.service.DeliveryAddressService;
import com.siam.system.modular.package_order.service.OrderService;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.entity.MemberBillingRecord;
import com.siam.system.modular.package_user.entity.MemberTradeRecord;
import com.siam.system.modular.package_user.model.example.MemberTradeRecordExample;
import com.siam.system.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping(value = "/rest/member/platformPay")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "平台支付模块相关接口", description = "PlatformPayController")
public class PlatformPayController {

    @Autowired
    private WxPayConfig wxPayConfig;

    @Autowired
    private MemberTradeRecordService memberTradeRecordService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private WxdecodeUtils wxdecodeUtils;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberBillingRecordService memberBillingRecordService;

    /**
     * 和微信支付的步骤一致
     * 第一步，确认订单信息
     * 第二步，验证支付密码，验证余额是否充足
     * 第三部，支付回调
     * 现在我们的就是把这三步结合在一个接口完成，但是步骤不能乱，否则代码不能复用
     * PS：但是如果按照微信支付的步骤来，只要某个步骤失败，事务就会回滚--莫非这种就是不能在Controller上添加事务注解的原因？
     * @return
     * @author 暹罗
     */
    @ApiOperation(value = "使用平台余额支付")
    @PostMapping(value = "/byBalance")
    public BasicResult byBalance(@RequestBody @Validated PlatformPayDto platformPayDto, HttpServletRequest request) throws InterruptedException, RemotingException, MQClientException, MQBrokerException, IOException {
        //TODO-PS：但是如果按照微信支付的步骤来，只要某个步骤失败，事务就会回滚--莫非这种就是不能在Controller上添加事务注解的原因？
        //TODO-之前都没有验证过该订单是否属于当前登录用户
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        Member dbMember = memberService.selectByPrimaryKey(loginMember.getId());

        if(platformPayDto.getType()!=Quantity.INT_1 && platformPayDto.getType()!=Quantity.INT_3){
            throw new StoneCustomerException("交易类型错误");
        }

        //商户单号
        String outTradeNo = null;

        //原订单对象
        Order dbOrder = null;

        //自取订单改为配送的商户单号
        String changeToDeliveryOutTradeNo = null;

        if(platformPayDto.getType() == Quantity.INT_1){
            //交易类型为订单付款
            if(platformPayDto.getOut_trade_no() == null){
                throw new StoneCustomerException("必须填写商户单号");
            }
            dbOrder = orderService.selectByOrderNo(platformPayDto.getOut_trade_no());
            if(dbOrder == null){
                throw new StoneCustomerException("该商户单号不存在");
            }
            if(dbOrder.getStatus()!=Quantity.INT_1 && dbOrder.getStatus()==Quantity.INT_10){
                throw new StoneCustomerException("该笔订单超时未支付，已自动取消，请重新下单");
            }
            //TODO-修改金额为一分钱，用于测试(已恢复正常)
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
            insertMemberTradeRecord.setType(platformPayDto.getType());
            insertMemberTradeRecord.setPaymentMode(Quantity.INT_3);
            insertMemberTradeRecord.setAmount(platformPayDto.getTotal_fee());
            insertMemberTradeRecord.setStatus(Quantity.INT_1);
            insertMemberTradeRecord.setCreateTime(new Date());
            insertMemberTradeRecord.setUpdateTime(new Date());
            memberTradeRecordService.insertSelective(insertMemberTradeRecord);

            //修改订单信息：补填用户交易id
            Order updateOrder = new Order();
            updateOrder.setId(dbOrder.getId());
            updateOrder.setTradeId(insertMemberTradeRecord.getId());
            updateOrder.setPaymentMode(Quantity.INT_2);
            updateOrder.setUpdateTime(new Date());
            orderService.updateByPrimaryKeySelective(updateOrder);

        }else if(platformPayDto.getType() == Quantity.INT_3){
            //交易类型为自取订单改为配送
            if(platformPayDto.getOut_trade_no() == null){
                throw new StoneCustomerException("必须填写商户单号");
            }
            if(platformPayDto.getDeliveryAddressId() == null){
                throw new StoneCustomerException("必须填写收获地址");
            }
            dbOrder = orderService.selectByOrderNo(platformPayDto.getOut_trade_no());
            if(dbOrder == null){
                throw new StoneCustomerException("该商户单号不存在");
            }
            if(dbOrder.getShoppingWay() != Quantity.INT_1){
                throw new StoneCustomerException("该笔订单不属于自取订单，不允许操作");
            }
            if(dbOrder.getStatus()!=Quantity.INT_2 && dbOrder.getStatus()!=Quantity.INT_3){
                throw new StoneCustomerException("该笔订单状态错误，不允许操作");
            }

            Shop dbShop = shopService.selectByPrimaryKey(dbOrder.getShopId());

            BigDecimal merchantDeliveryFee = BigDecimal.ZERO; //商家承担配送费
            //计算配送费是否正确
            DeliveryAddress dbDeliveryAddress = deliveryAddressService.selectByPrimaryKey(platformPayDto.getDeliveryAddressId());
            if(dbDeliveryAddress == null) throw new StoneCustomerException("收货地址不存在");
            /*String addressStr = dbDeliveryAddress.getProvince() + dbDeliveryAddress.getCity() + dbDeliveryAddress.getArea() + dbDeliveryAddress.getStreet();*/
            BigDecimal deliveryFee = commonService.selectDeliveryFee(dbDeliveryAddress.getLongitude(), dbDeliveryAddress.getLatitude(), dbOrder.getShopId());
            //判断商家立减配送费
            if(dbShop.getReducedDeliveryPrice().compareTo(BigDecimal.ZERO) > 0){
                if((dbShop.getReducedDeliveryPrice().compareTo(deliveryFee) >= 0)){
                    deliveryFee = BigDecimal.ZERO;
                    merchantDeliveryFee = deliveryFee;
                }else{
                    deliveryFee = deliveryFee.subtract(dbShop.getReducedDeliveryPrice());
                    merchantDeliveryFee = dbShop.getReducedDeliveryPrice();
                }
            }
            if(deliveryFee.compareTo(platformPayDto.getTotal_fee()) != 0){
                throw new StoneCustomerException("配送费计算错误，请稍后重试");
            }
            log.debug("配送费：" + deliveryFee);

            //如果自取订单改为配送的商户单号已存在，则无需重新生成
            changeToDeliveryOutTradeNo = dbOrder.getChangeToDeliveryOutTradeNo()==null ? getOutTradeNo() : dbOrder.getChangeToDeliveryOutTradeNo();
            outTradeNo = changeToDeliveryOutTradeNo;

            //添加用户交易记录
            MemberTradeRecord insertMemberTradeRecord = new MemberTradeRecord();
            insertMemberTradeRecord.setMemberId(loginMember.getId());
            insertMemberTradeRecord.setOutTradeNo(outTradeNo);
            insertMemberTradeRecord.setType(platformPayDto.getType());
            insertMemberTradeRecord.setPaymentMode(Quantity.INT_3);
            insertMemberTradeRecord.setAmount(platformPayDto.getTotal_fee());
            insertMemberTradeRecord.setStatus(Quantity.INT_1);
            insertMemberTradeRecord.setCreateTime(new Date());
            insertMemberTradeRecord.setUpdateTime(new Date());
            memberTradeRecordService.insertSelective(insertMemberTradeRecord);

            //修改订单信息：补填自取订单改为配送的用户交易id
            //配送费、收获地址id要持久化到数据库，回调时配送费从用户交易记录表中获取(考虑到有些地方无论订单类型都会将配送费展示出来)，收获地址id从订单表中获取
            Order updateOrder = new Order();
            updateOrder.setId(dbOrder.getId());
            updateOrder.setMerchantDeliveryFee(merchantDeliveryFee);
            updateOrder.setDeliveryAddressId(platformPayDto.getDeliveryAddressId());
            updateOrder.setChangeToDeliveryOutTradeNo(outTradeNo);
            updateOrder.setChangeToDeliveryTradeId(insertMemberTradeRecord.getId());
            updateOrder.setPaymentMode(Quantity.INT_2);
            updateOrder.setUpdateTime(new Date());
            orderService.updateByPrimaryKeySelective(updateOrder);
        }

        //判断支付密码是否正确
        String paymentPassword = Base64Utils.decode(platformPayDto.getPaymentPassword());
        paymentPassword = CommonUtils.genMd5Password(paymentPassword, dbMember.getPaymentPasswordSalt());
        if(!paymentPassword.equals(dbMember.getPaymentPassword())){
            throw new StoneCustomerException("支付密码输入错误，请重新输入");
        }

        //校验余额是否充足
        if(dbMember.getBalance().compareTo(platformPayDto.getTotal_fee()) < 0){
            throw new StoneCustomerException("您的余额不足，请选择其它支付方式");
        }
        //减少用户的余额
        Member updateMember = new Member();
        updateMember.setId(dbMember.getId());
        updateMember.setBalance(dbMember.getBalance().subtract(platformPayDto.getTotal_fee()));
        updateMember.setTotalConsumeBalance(dbMember.getTotalConsumeBalance().add(platformPayDto.getTotal_fee()));
        memberService.updateByPrimaryKeySelective(updateMember);
        dbMember = memberService.selectByPrimaryKey(loginMember.getId());
        //添加账单记录
        MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
        memberBillingRecord.setMemberId(dbMember.getId());
        memberBillingRecord.setType(MemberBillingRecord.TYPE_ORDER_PAYMENT_BY_BALANCE);
        memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_SUB);
        memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_BALANCE);
        memberBillingRecord.setNumber(platformPayDto.getTotal_fee());
        memberBillingRecord.setMessage("订单使用余额支付");
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

        if(dbMemberTradeRecord.getType() == Quantity.INT_1){
            //交易类型为订单付款
            orderService.paymentNotify(outTradeNo);

        }else if(dbMemberTradeRecord.getType() == Quantity.INT_3){
            //交易类型为自取订单改为配送
            orderService.paymentNotifyOfChangeToDelivery(outTradeNo);
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