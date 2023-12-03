package com.siam.system.modular.package_order.service_impl.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.util.*;
import com.siam.system.modular.package_goods.entity.Setting;
import com.siam.system.modular.package_goods.entity.internal.*;
import com.siam.package_weixin_basic.service.WxNotifyService;
import com.siam.package_weixin_basic.service.WxPublicPlatformNotifyService;
import com.siam.system.modular.package_goods.service.*;
import com.siam.system.modular.package_goods.service.internal.*;
import com.siam.system.modular.package_user.service.MemberBillingRecordService;
import com.siam.system.modular.package_user.service.MemberService;
import com.siam.system.modular.package_user.service.MemberInviteRelationService;
import com.siam.system.modular.package_user.service.MemberTradeRecordService;
import com.siam.system.modular.package_order.controller.member.internal.PointsMallWxPayService;
import com.siam.system.modular.package_order.entity.*;
import com.siam.system.modular.package_order.entity.internal.*;
import com.siam.system.modular.package_order.mapper.internal.PointsMallOrderMapper;
import com.siam.system.modular.package_order.model.example.OrderExample;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderDetailExample;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderExample;
import com.siam.system.modular.package_order.model.param.OrderParam;
import com.siam.system.modular.package_order.model.param.internal.PointsMallOrderParam;
import com.siam.system.modular.package_order.model.result.internal.PointsMallOrderResult;
import com.siam.system.modular.package_order.model.vo.internal.PointsMallOrderVo;
import com.siam.system.modular.package_order.service.*;
import com.siam.system.modular.package_order.service.internal.*;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.entity.MemberBillingRecord;
import com.siam.system.modular.package_user.entity.MemberTradeRecord;
import com.siam.system.modular.package_user.model.example.MemberBillingRecordExample;
import com.siam.system.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Slf4j
@Service
public class PointsMallOrderServiceImpl implements PointsMallOrderService {

    public static final String USER_ORDER_TOKEN_PREFIX = "order:token:";

    @Autowired
    private PointsMallOrderMapper pointsMallOrderMapper;

    @Autowired
    private PointsMallOrderDetailService orderDetailService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private MemberBillingRecordService memberBillingRecordService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberTradeRecordService memberTradeRecordService;

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private WxNotifyService wxNotifyService;

    @Autowired
    private WxPublicPlatformNotifyService wxPublicPlatformNotifyService;

    @Autowired
    private PointsMallOrderRefundService orderRefundService;

    @Autowired
    private MemberInviteRelationService memberInviteRelationService;

    @Autowired
    private PointsMallOrderLogisticsService orderLogisticsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private VipRechargeRecordService vipRechargeRecordService;

    @Autowired
    private PointsMallOrderRefundGoodsService orderRefundGoodsService;

    @Autowired
    private PointsMallOrderRefundProcessService orderRefundProcessService;

    @Autowired
    private PointsMallWxPayService pointsMallWxPayService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PointsMallShoppingCartService shoppingCartService;

    @Autowired
    private PointsMallGoodsService goodsService;

    @Autowired
    private PointsMallGoodsSpecificationOptionService goodsSpecificationOptionService;

    @Autowired
    private PointsMallCouponsMemberRelationService couponsMemberRelationService;

    @Autowired
    private PointsMallCouponsService couponsService;

    @Autowired
    private PointsMallCouponsGoodsRelationService couponsGoodsRelationService;

    @Autowired
    private PointsMallFullReductionRuleService fullReductionRuleService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    public int countByExample(PointsMallOrderExample example){
        return pointsMallOrderMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Long id){
        pointsMallOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public String createOrderToken(PointsMallOrderParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        String token = UUID.randomUUID().toString().replace("-", "");
        redisUtils.set(USER_ORDER_TOKEN_PREFIX + loginMember.getId(), token);
        return token;
    }

    @Override
    public PointsMallOrder insert(PointsMallOrderParam param) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        //TODO(MARK) - 购物车下单、直接购买两种形式可以不拆分成两个接口
        //TODO(MARK) - 系统默认免运费
        param.setDeliveryFee(BigDecimal.ZERO);
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        Member dbMember = memberService.selectByPrimaryKey(loginMember.getId());

        //校验防重令牌是否正确 -- 保证该接口的幂等性
        /*String script = "if redis.call('get', KEYS[1]) == ARGV[1]  then  return redis.call('del', KEYS[1])  else  return 0 end";
        Long result = (Long) redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList(USER_ORDER_TOKEN_PREFIX + loginMember.getId()), param.getOrderToken());
        if(result == 0){
            throw new StoneCustomerException("请勿重复提交");
        }*/

        //校验：如果是从购物车下单的 那么需要校验购物车数据是否存在 以及购物车数据是否属于当前登录用户
        if(param.getShoppingCartIdList()!=null && !param.getShoppingCartIdList().isEmpty()){
            int count = shoppingCartService.countByIdListAndMemberId(param.getShoppingCartIdList(), loginMember.getId());
            if(count != param.getShoppingCartIdList().size()){
                throw new StoneCustomerException("购物车数据异常，请刷新页面重新下单");
            }
        }

        //根据收货地址id回写联系人信息
        DeliveryAddress dbDeliveryAddress = deliveryAddressService.selectByPrimaryKey(param.getDeliveryAddressId());
        if(dbDeliveryAddress == null){
            throw new StoneCustomerException("该收货地址不存在");
        }
        param.setContactRealname(dbDeliveryAddress.getRealname());
        param.setContactPhone(dbDeliveryAddress.getPhone());
        param.setContactProvince(dbDeliveryAddress.getProvince());
        param.setContactCity(dbDeliveryAddress.getCity());
        param.setContactArea(dbDeliveryAddress.getArea());
        param.setContactStreet(dbDeliveryAddress.getStreet());
        param.setContactSex(dbDeliveryAddress.getSex());
        param.setContactHouseNumber(dbDeliveryAddress.getHouseNumber());
        param.setContactLongitude(dbDeliveryAddress.getLongitude());
        param.setContactLatitude(dbDeliveryAddress.getLatitude());

        //1、校验前端的实付款金额是否正确
        this.validatePrice(param);

        //生成订单编号
        String orderNo = GenerateNo.getOrderNo();
        //生成订单取餐号
        Integer queueNo = getNextQueueNo();
        //生成支付截止时间(五分钟内未付款的订单会被自动关闭)
        Date paymentDeadline = DateUtilsPlus.addMinutes(new Date(), Quantity.INT_5);

        //2、添加订单记录
        PointsMallOrder insertOrder = new PointsMallOrder();
        insertOrder.setMemberId(loginMember.getId());
        insertOrder.setOrderNo(orderNo);
        insertOrder.setGoodsTotalQuantity(param.getGoodsTotalQuantity());
        insertOrder.setGoodsTotalPrice(param.getGoodsTotalPrice());
        insertOrder.setDeliveryFee(param.getDeliveryFee());
        insertOrder.setActualPrice(param.getActualPrice());
        insertOrder.setShoppingWay(Quantity.INT_2);
        insertOrder.setDeliveryAddressId(param.getDeliveryAddressId());
        insertOrder.setContactRealname(param.getContactRealname());
        insertOrder.setContactPhone(param.getContactPhone());
        insertOrder.setContactProvince(param.getContactProvince());
        insertOrder.setContactCity(param.getContactCity());
        insertOrder.setContactArea(param.getContactArea());
        insertOrder.setContactStreet(param.getContactStreet());
        insertOrder.setContactSex(param.getContactSex());
        insertOrder.setTradeId(null);
        insertOrder.setRemark(param.getRemark());
        insertOrder.setDescription(param.getDescription());
        insertOrder.setStatus(Quantity.INT_1);
        insertOrder.setOrderLogisticsId(null);
        insertOrder.setIsInvoice(false);
        insertOrder.setInvoiceId(null);
        insertOrder.setIsDeleted(false);
        insertOrder.setShopName(param.getShopName());
        insertOrder.setShopAddress(param.getShopAddress());
        insertOrder.setCancelReason(null);
        insertOrder.setPaymentDeadline(paymentDeadline);
        insertOrder.setCreateTime(new Date());
        insertOrder.setUpdateTime(new Date());
        insertOrder.setQueueNo(queueNo);
        insertOrder.setFullReductionRuleId(param.getFullReductionRuleId());
        insertOrder.setFullReductionRuleDescription(param.getFullReductionRuleDescription());
        insertOrder.setCouponsId(param.getCouponsId());
        insertOrder.setCouponsMemberRelationId(param.getCouponsMemberRelationId());
        insertOrder.setCouponsDescription(param.getCouponsDescription());
        insertOrder.setLimitedPrice(param.getLimitedPrice());
        insertOrder.setReducedPrice(param.getReducedPrice());
        insertOrder.setCouponsDiscountPrice(param.getCouponsDiscountPrice());
        insertOrder.setDeliveryWay(param.getDeliveryWay());
        insertOrder.setIsPayToMerchant(false);
        insertOrder.setBeforeReducedDeliveryFee(param.getBeforeReducedDeliveryFee());
        insertOrder.setContactHouseNumber(param.getContactHouseNumber());
        insertOrder.setContactLongitude(param.getContactLongitude());
        insertOrder.setContactLatitude(param.getContactLatitude());
        insertOrder.setFirstGoodsMainImage(param.getFirstGoodsMainImage());
        pointsMallOrderMapper.insert(insertOrder);

        //获取最新订单对象
        PointsMallOrder dbOrder = pointsMallOrderMapper.selectById(insertOrder.getId());

        //3、添加订单商品详情记录
        for(PointsMallOrderDetail orderDetail : param.getOrderDetailList()){
            // 添加订单商品详情记录
            PointsMallOrderDetail insertDetail = new PointsMallOrderDetail();
            insertDetail.setOrderId(dbOrder.getId());
            insertDetail.setGoodsId(orderDetail.getGoodsId());
            insertDetail.setGoodsName(orderDetail.getGoodsName());
            insertDetail.setMainImage(orderDetail.getMainImage());
            insertDetail.setSpecList(orderDetail.getSpecList());
            insertDetail.setPrice(orderDetail.getPrice());
            insertDetail.setNumber(orderDetail.getNumber());
            insertDetail.setSubtotal(orderDetail.getSubtotal());
            if(orderDetail.getIsUsedCoupons() != null){
                insertDetail.setIsUsedCoupons(orderDetail.getIsUsedCoupons());
                insertDetail.setCouponsDiscountPrice(orderDetail.getCouponsDiscountPrice());
                insertDetail.setAfterCouponsDiscountPrice(orderDetail.getPrice().subtract(orderDetail.getCouponsDiscountPrice()));
            }
            insertDetail.setIsDeleted(false);
            orderDetailService.insertSelective(insertDetail);

            //TODO - 减少商品库存 (规格的库存该怎么去变化)
            /*goodsService.decreaseStock(goodsId, number);*/
        }

        //4、如果是从购物车下单的 那么下单成功后需要删除购物车数据  注意用批量删除
        if(param.getShoppingCartIdList()!=null && param.getShoppingCartIdList().size()>0){
            shoppingCartService.batchDeleteByIdList(param.getShoppingCartIdList());
        }

        //5、修改是否为新用户标识
        if(dbMember.getIsNewPeople()){
            dbMember.setIsNewPeople(false);
            dbMember.setIsRemindNewPeople(false);
            memberService.updateByPrimaryKeySelective(dbMember);
        }

        //模拟程序出错
//        int i = 10 / 0;

        //6、更新优惠卷的使用状态
        if (param.getCouponsMemberRelationId() != null) {
            couponsMemberRelationService.updateCouponsUsed(param.getCouponsMemberRelationId(),true);
        }

        /*//加入MQ延时队列，检测并关闭超时未支付的订单，5分钟
        Message message = new Message("TID_COMMON_MALL", "CLOSE_OVERDUE_ORDER", JSON.toJSONString(dbOrder).getBytes());
        message.setDelayTimeLevel(RocketMQConst.DELAY_TIME_LEVEL_5M);
        rocketMQTemplate.getProducer().send(message);*/

        return dbOrder;
    }

    @Override
    public PointsMallOrder insertByMQ(PointsMallOrderParam param, String transId) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return null;
    }

    /**
     * 校验前端的实付款金额是否正确
     *
     * @author 暹罗
     */
    public void validatePrice(PointsMallOrderParam param){
        //订单总金额以后端计算为准，前端传递过来的总金额需要进行比对-防止前端计算错误
        //最终应付价格
        BigDecimal finalPrice;

        //1、计算商品总金额
        BigDecimal goodsTotalPrice = BigDecimal.ZERO;
        //商品总数量
        int goodsTotalQuantity = 0;
        for (PointsMallOrderDetail orderDetail : param.getOrderDetailList()) {
            PointsMallGoods dbGoods = goodsService.selectByPrimaryKey(orderDetail.getGoodsId());
            if (dbGoods == null){
                throw new StoneCustomerException("订单商品数据异常，请稍后重试");
            }
            //判断商品状态是否正确
            if(!dbGoods.getStatus().equals(PointsMallGoods.STATUS_ON_SHELF)) {
                String errorMsg = "";
                if(dbGoods.getStatus().equals(PointsMallGoods.STATUS_WAIT_ON_SHELF)){
                    errorMsg = dbGoods.getName() + "还未上架，请重新选择";
                }else if(dbGoods.getStatus().equals(PointsMallGoods.STATUS_OFF_SHELF)){
                    errorMsg = dbGoods.getName() + "已下架，请重新选择";
                }else if(dbGoods.getStatus().equals(PointsMallGoods.STATUS_SELL_OUT)){
                    errorMsg = dbGoods.getName() + "已售罄，请重新选择";
                }
                throw new StoneCustomerException(errorMsg);
            }
            //计算单品对应规格的价格
            BigDecimal specOptionPrice = BigDecimal.ZERO;
            List<String> nameList = new ArrayList<>();
            Map<String, Object> map = GsonUtils.toMap(orderDetail.getSpecList());
            for(String key : map.keySet()){
                nameList.add((String) map.get(key));
            }
            //正常情况下nameList不能为空，为空也要做特殊处理
            if(!nameList.isEmpty()){
                specOptionPrice = goodsSpecificationOptionService.selectSumPriceByGoodsIdAndName(orderDetail.getGoodsId(), nameList);
            }
            //计算单品的总价
            BigDecimal price = dbGoods.getPrice().add(specOptionPrice);
            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(orderDetail.getNumber()));
            //订单总金额累加
            goodsTotalPrice = goodsTotalPrice.add(subtotal);
            goodsTotalQuantity = goodsTotalQuantity + orderDetail.getNumber();

            //填充订单主图
            if(StringUtils.isBlank(param.getFirstGoodsMainImage())){
                param.setFirstGoodsMainImage(dbGoods.getMainImage());
            }
            //填充订单商品详情信息
            orderDetail.setPrice(price);
            orderDetail.setSubtotal(subtotal);
            orderDetail.setGoodsName(dbGoods.getName());
            orderDetail.setMainImage(dbGoods.getMainImage());
        }

        //计算未使用优惠前的最终价格(商品总价)
        finalPrice = goodsTotalPrice;
        log.debug("\n\n》》》 商品总价 = " + goodsTotalPrice);
        log.debug("\n\n》》》 商品总数量 = " + goodsTotalQuantity);
        log.debug("\n\n》》》 未使用优惠前的最终价格 = " + finalPrice);

        //2、判断优惠卷是否满足使用条件
        Integer couponsMemberRelationId = param.getCouponsMemberRelationId();
        //使用优惠券时优惠的金额
        BigDecimal subtractPrice = BigDecimal.ZERO;
        if (couponsMemberRelationId != null) {
            //查询出使用的优惠券信息
            PointsMallCouponsMemberRelation dbPointsMallCouponsMemberRelation = couponsMemberRelationService.selectPointsMallCouponsMemberRelationByPrimaryKey(couponsMemberRelationId);
            Map couponsMap = couponsService.selectCouponsAndGoodsByPrimaryKey(dbPointsMallCouponsMemberRelation.getCouponsId());
            PointsMallCoupons dbPointsMallCoupons = (PointsMallCoupons) couponsMap.get("coupons");
            if(PointsMallCoupons.TYPE_DISCOUNT.equals(dbPointsMallCoupons.getPreferentialType())){
                //折扣优惠券

                //下单商品是否满足此优惠券使用条件的标识
                Boolean isMeetCouponsOfGoods = false;
                //下单商品中的最高价格
                BigDecimal subtractNum = BigDecimal.ZERO;
                //优惠券选择优惠的商品
                PointsMallOrderDetail couponsDiscountOrderDetail = null;

                //查询出优惠券关联的所有商品
                List<Integer> goodsIdList = couponsGoodsRelationService.getGoodsIdByCouponsId(dbPointsMallCouponsMemberRelation.getCouponsId());
                for (PointsMallOrderDetail orderDetail : param.getOrderDetailList()) {
                    //判断商品是否能够使用此优惠卷 且 是否应用于最高价格的商品(不包括包装费)
                    if (goodsIdList.contains(orderDetail.getGoodsId())) {
                        isMeetCouponsOfGoods = true;

                        //将当前价格 与 最高价格进行比较
                        if(orderDetail.getPrice().compareTo(subtractNum) == 1){
                            subtractNum = orderDetail.getPrice();
                            couponsDiscountOrderDetail = orderDetail;
                        }
                    }
                }
                //TODO(MARK) - 关于优惠券是否应用于最高价格的商品，这个如果前端判断错误，后端不单独给与提醒 -- 统一用订单实付款计算错误提醒即可
                if(!isMeetCouponsOfGoods){
                    throw new StoneCustomerException("所选优惠券不满足使用条件，请刷新重试");
                }

                log.debug("\n\n》》》 优惠券选择优惠的商品价格：" + subtractNum.toString());
                log.debug("\n\n》》》 优惠券折扣额度：" + dbPointsMallCoupons.getDiscountAmount().toString());

                //计算使用优惠券时优惠的金额
                subtractPrice = subtractNum.multiply((BigDecimal.ONE.subtract(((PointsMallCoupons) couponsMap.get("coupons")).getDiscountAmount()))).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
                finalPrice = finalPrice.subtract(subtractPrice);
                //标识订单商品详情
                couponsDiscountOrderDetail.setIsUsedCoupons(true);
                couponsDiscountOrderDetail.setCouponsDiscountPrice(subtractPrice);

            }else if(PointsMallCoupons.TYPE_FULL_REDUCTION.equals(((PointsMallCoupons)couponsMap.get("coupons")).getPreferentialType())){
                //TODO-满减优惠券的使用逻辑待定，初步探讨是满减规则或满减优惠券两者选其一，像奶茶这种产品下单金额比较小，所以估计用不着满减优惠券；
                //优惠券为满减卷类型
                //这里要依据使用满减规则后的最终价格来进行判断
                /*if(finalPrice.compareTo((BigDecimal) couponsMap.get("limitedPrice"))>=0){
                    subtractPrice = (BigDecimal) couponsMap.get("reducedPrice");
                    finalPrice = finalPrice.subtract(subtractPrice);
                }else{
                    basicResult.setSuccess(false);
                    basicResult.setCode(BasicResultCode.ERR);
                    basicResult.setMessage("优惠卷使用错误");
                    return basicResult;
                }*/
            }
            param.setCouponsId(dbPointsMallCouponsMemberRelation.getCouponsId());
            param.setCouponsMemberRelationId(couponsMemberRelationId);
            param.setCouponsDescription(((PointsMallCoupons)couponsMap.get("coupons")).getName() + "-优惠" + subtractPrice + "元");
            log.debug("\n\n》》》 使用优惠券时优惠的金额：" + subtractPrice.toString());
        }

        //3、判断满减规则是否满足使用条件
        //TODO-后端不判断订单应使用哪个满减规则，只对前端传递的满减规则进行是否满足使用条件判断
        if (param.getFullReductionRuleId() != null) {
            PointsMallFullReductionRule dbFullReductionRule = fullReductionRuleService.selectByPrimaryKey(param.getFullReductionRuleId());
            if(finalPrice.compareTo(dbFullReductionRule.getLimitedPrice()) >= 0){
                finalPrice = finalPrice.subtract(dbFullReductionRule.getReducedPrice());
                param.setFullReductionRuleId(dbFullReductionRule.getId());
                param.setFullReductionRuleDescription(dbFullReductionRule.getName());
                param.setLimitedPrice(dbFullReductionRule.getLimitedPrice());
                param.setReducedPrice(dbFullReductionRule.getReducedPrice());
                log.debug("\n\n》》》 使用满减规则优惠金额：" + dbFullReductionRule.getReducedPrice());
                log.debug("\n\n》》》 使用满减规则后的最终价格：" + finalPrice);
            }else{
                throw new StoneCustomerException("满减规则不满足使用条件，请刷新页面重试");
            }
        }

        //判断前端的最终价格和后端的最终价格是否一致
        //如果最终价格计算出来是负数，则要手动赋值为0
        finalPrice = (finalPrice.compareTo(BigDecimal.ZERO)==-1) ? BigDecimal.ZERO : finalPrice.setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
        log.debug("\n\n》》》 前端计算的实付款：" + param.getActualPrice().toString());
        log.debug("\n\n》》》 后端计算的实付款：" + finalPrice);
        if(param.getActualPrice().compareTo(finalPrice) != 0){
            throw new StoneCustomerException("订单实付款计算错误，请刷新页面重试");
        }

        //订单描述信息
        String description = param.getOrderDetailList().get(0).getGoodsName();
        description += (goodsTotalQuantity > 1) ? ("&nbsp;&nbsp;等" + goodsTotalQuantity + "件") : "";
        //填充订单信息
        param.setDescription(description);
        param.setGoodsTotalQuantity(goodsTotalQuantity);
        param.setGoodsTotalPrice(goodsTotalPrice);
        param.setActualPrice(finalPrice);
        param.setCouponsDiscountPrice(subtractPrice);
    }

    public List<PointsMallOrder> selectByExample(PointsMallOrderExample example){
        return pointsMallOrderMapper.selectByExample(example);
    }

    public PointsMallOrder selectByPrimaryKey(Long id){
        return pointsMallOrderMapper.selectById(id);
    }

    public void updateByExampleSelective(PointsMallOrder record, PointsMallOrderExample example){
        pointsMallOrderMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(PointsMallOrder record){
        pointsMallOrderMapper.updateByPrimaryKeySelective(record);
    }

    public Page<PointsMallOrderResult> getListByPageWithAsc(PointsMallOrderParam param) {
        Page<PointsMallOrderResult> page = pointsMallOrderMapper.getListByPageWithAsc(new Page(param.getPageNo(), param.getPageSize()), param);

        //查询出这些订单对应的订单商品详情信息
        List<Long> orderIdList = new ArrayList<>();
        page.getRecords().forEach(obj -> {
            orderIdList.add(obj.getId());
        });
        if (!orderIdList.isEmpty()){
            PointsMallOrderDetailExample detailExample = new PointsMallOrderDetailExample();
            detailExample.createCriteria().andPointsMallOrderIdIn(orderIdList);
            List<PointsMallOrderDetail> detailList = orderDetailService.selectByExample(detailExample);

            //组装返回数据
            Map<Long, List> filterMap = new LinkedHashMap<>();
            detailList.forEach(detailObj -> {
                if(!filterMap.containsKey(detailObj.getOrderId())){
                    filterMap.put(detailObj.getOrderId(), new ArrayList<>());
                }
                filterMap.get(detailObj.getOrderId()).add(detailObj);
            });

            page.getRecords().forEach(obj -> {
                obj.setOrderDetailList(filterMap.get(obj.getId()));
            });
        }

        return page;
    }

    public Page<Map<String, Object>> getListByPageWithDesc(PointsMallOrderParam param) {

        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //只查询当前登录用户的订单信息
        param.setMemberId(loginMember.getId());
        param.setIsDeleted(false);

        Page<Map<String, Object>> page = pointsMallOrderMapper.getListByPageWithDesc(new Page(param.getPageNo(), param.getPageSize()), param);

        //如果订单已评价 或 订单已完成超过14天 或 订单非已完成状态，则不允许评价
        page.getRecords().forEach(map -> {
            /*if(((long) map.get("isAllowAppraise")) == 1L){
                int status = (int) map.get("status");
                Date createTime = (Date) map.get("createTime");
                if (status!=Quantity.INT_6 || DateUtils.diffDays(new Date(), createTime)>14){
                    map.put("isAllowAppraise", 0L);
                }
            }*/

            //如果可以无责取消订单，则无需显示申请退款按钮
            if(((long) map.get("isAllowApplyRefund")) == 1L){
                map.put("isAllowApplyAfterSales", 0L);
            }
        });

        //订单未发货时可以申请退款

        //订单已发货，距离支付时间未超过20天可以申请售后

        return page;
    }

    @Override
    public PointsMallOrder selectByOrderNo(String orderNo) {
        return pointsMallOrderMapper.selectByPointsMallOrderNo(orderNo);
    }

    @Override
    public void closeOverdueOrder(Long id) {
        //五分钟内未付款的订单会被自动关闭
        PointsMallOrder dbOrder = pointsMallOrderMapper.selectById(id);
        if(dbOrder!=null && dbOrder.getStatus().equals(PointsMallOrder.STATUS_OF_WAIT_PAYMENT)){
            //修改订单状态为已取消(未支付)
            PointsMallOrder updateOrder = new PointsMallOrder();
            updateOrder.setId(id);
            updateOrder.setStatus(PointsMallOrder.STATUS_OF_CANCLED_NOT_PAY);
            updateOrder.setCancelReason(PointsMallOrder.CANCEL_REASON_OF_OVERDUE);
            updateOrder.setUpdateTime(new Date());
            pointsMallOrderMapper.updateById(updateOrder);

            //退回使用的优惠卷
            Integer couponsMemberRelationId = dbOrder.getCouponsMemberRelationId();
            if (couponsMemberRelationId != null) {
                couponsMemberRelationService.updateCouponsUsed(couponsMemberRelationId, false);
            }
        }
    }

    @Override
    public void updateByFinishOverdueOrder() {
        //积分商城订单支付超过7天(7*24个小时) 且 订单处于已发货状态，则将订单修改为已完成
        Date overdueTime = DateUtilsPlus.addDays(new Date(), Quantity.INT_7);
        pointsMallOrderMapper.updateFinish(overdueTime, new Date(), Quantity.INT_6);
    }

    @Override
    public void printRceceipts(Long id) {

        PointsMallOrder order= pointsMallOrderMapper.selectById(id);
        //订单联系电话
        String contactPhone="";
        //订单联系人姓名
        String contactRealname="";
        //性别
        Integer contactSex=null;
        if(order.getContactPhone()!=null&&order.getContactPhone().length()>0){
            contactRealname=order.getContactRealname().substring(0,1);
        }
        if(order.getContactPhone()!=null&&order.getContactPhone().length()>0){

            if(order.getContactSex()!=null&&order.getContactSex()!=0){
                if(order.getContactSex()==1){
                    contactRealname=order.getContactPhone().substring(0,4)+"(先生)";
                }
                if(order.getContactSex()==2){
                    contactRealname=order.getContactPhone().substring(0,4)+"(女生)";
                }

            }

        }
        //购买数量
        Integer  number=null;
        //商品详情
        String  specList="";
        //查询订单信息
        List<PointsMallOrderDetail> orderDetails = orderDetailService.selectByPointsMallOrderId(id);
       /* for(PointsMallOrderDetail PointsMallOrderDetail:orderDetails){
            number=PointsMallOrderDetail.getNumber();
            specList=PointsMallOrderDetail.getSpecList();
            PrintUtils
        }*/
      //  PrintUtils.print(orderDetails);
    }

    @Override
    public int batchUpdateIsPrintedTrue(List<Long> idList) {
        return pointsMallOrderMapper.batchUpdateIsPrintedTrue(idList);
    }

    @Override
    public Integer getNextQueueNo() {
        Integer maxQueueNo = pointsMallOrderMapper.findMaxQueueNo();
        Integer queueNo = maxQueueNo==null?1: maxQueueNo+ 1;
        return queueNo;
    }

    public Page<PointsMallOrderResult> getListByTodayOrderWithAsc(PointsMallOrderParam param) {
        Page<PointsMallOrderResult> page = pointsMallOrderMapper.getListByTodayPointsMallOrderWithAsc(new Page(param.getPageNo(), param.getPageSize()), param);
        return page;
    }

    @Override
    public void paymentNotify(String outTradeNo) {
        PointsMallOrder dbPointsMallOrder = pointsMallOrderMapper.selectByPointsMallOrderNo(outTradeNo);
        if(dbPointsMallOrder == null){
            log.error("该商户单号不存在，回调逻辑处理失败");
            return;
        }

        //订单分配到待发货
        int status = Quantity.INT_4;
        //更新订单的状态
        PointsMallOrder updatePointsMallOrder = new PointsMallOrder();
        updatePointsMallOrder.setId(dbPointsMallOrder.getId());
        updatePointsMallOrder.setStatus(status);
        updatePointsMallOrder.setPaymentSuccessTime(new Date());
        updatePointsMallOrder.setUpdateTime(new Date());
        pointsMallOrderMapper.updateByPrimaryKeySelective(updatePointsMallOrder);

        List<PointsMallOrderDetail> orderDetailList = orderDetailService.selectByPointsMallOrderId(dbPointsMallOrder.getId());

        // 获取下单积分量
        Setting setting=settingService.selectCurrent();

        Member dbMember = memberService.selectByPrimaryKey(dbPointsMallOrder.getMemberId());

        //商品月销量和总销量修改
        for (PointsMallOrderDetail orderDetail : orderDetailList) {
            Integer num = orderDetail.getNumber();
            Integer goodsId = orderDetail.getGoodsId();
            goodsService.updateSales(goodsId, num);
        }

        //TODO-是否需要插入平台抽佣账单记录


        //发送新订单通知-微信公众号消息
        //如果商品明细内容过长，则需要分多次发送公众号消息
        //字数：38*4=152 -> 112
        String goodsDescription = "";
        boolean isFirstSend = true; //是否为第一次发送

        for (PointsMallOrderDetail orderDetail : orderDetailList) {
            //处理商品规格
            String specText = "";
            Map<String, Object> map = GsonUtils.toMap(orderDetail.getSpecList());
            for(String key : map.keySet()){
                if(specText.isEmpty()){
                    specText += (String) map.get(key);
                }else{
                    specText += "/" + (String) map.get(key);
                }
            }

            String str = orderDetail.getGoodsName() + "  " + specText + "  *  " + orderDetail.getNumber() + "件\r\n";
            if(goodsDescription.length() + str.length() > 112){
                //内容超过112个字数，需要重新拼接商品明细内容，将消息发送掉
                goodsDescription = goodsDescription.substring(0, goodsDescription.length()-2);
                //给商家发送微信公众号消息
                String title = isFirstSend
                        ? "积分商城-新订单服务通知 - 订单编号" + dbPointsMallOrder.getOrderNo()
                        : "订单商品明细服务通知 - 订单编号" + dbPointsMallOrder.getOrderNo();
                if(isFirstSend){
                    String addressStr = dbPointsMallOrder.getContactProvince() + dbPointsMallOrder.getContactCity() + dbPointsMallOrder.getContactArea() + dbPointsMallOrder.getContactStreet() + dbPointsMallOrder.getContactHouseNumber();
                    String deliveryAddress = ("订单编号" + dbPointsMallOrder.getOrderNo() + " - " + addressStr);
                    String contacts = dbPointsMallOrder.getContactRealname().concat(" - ").concat(dbPointsMallOrder.getContactPhone());
                    String amountDescription = "已付款".concat(" - ").concat(dbPointsMallOrder.getActualPrice()+"元");
                    String remark = StringUtils.isNotBlank(dbPointsMallOrder.getRemark()) ? dbPointsMallOrder.getRemark() : "无";
                    wxPublicPlatformNotifyService.sendNewOrderMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), deliveryAddress, contacts, amountDescription, remark);
                }else{
                    String addressStr = "";
                    String deliveryAddress = "";
                    String contacts = "";
                    String amountDescription = "";
                    String remark = "";
                    wxPublicPlatformNotifyService.sendNewOrderMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), deliveryAddress, contacts, amountDescription, remark);
                }
                goodsDescription = "";
                isFirstSend = false;
            }
            goodsDescription += str;

            //当前为列表末尾位置，需要将消息发送掉
            if(orderDetailList.indexOf(orderDetail) == orderDetailList.size()-1){
                //内容超过112个字数，需要重新拼接商品明细内容，将消息发送掉
                goodsDescription = goodsDescription.substring(0, goodsDescription.length()-2);
                //给商家发送微信公众号消息
                String title = isFirstSend
                        ? "积分商城-新订单服务通知 - 订单编号" + dbPointsMallOrder.getOrderNo()
                        : "订单商品明细服务通知 - 订单编号" + dbPointsMallOrder.getOrderNo();
                if(isFirstSend){
                    String addressStr = dbPointsMallOrder.getContactProvince() + dbPointsMallOrder.getContactCity() + dbPointsMallOrder.getContactArea() + dbPointsMallOrder.getContactStreet() + dbPointsMallOrder.getContactHouseNumber();
                    String deliveryAddress = ("订单编号" + dbPointsMallOrder.getOrderNo() + " - " + addressStr);
                    String contacts = dbPointsMallOrder.getContactRealname().concat(" - ").concat(dbPointsMallOrder.getContactPhone());
                    String amountDescription = "已付款".concat(" - ").concat(dbPointsMallOrder.getActualPrice()+"元");
                    String remark = StringUtils.isNotBlank(dbPointsMallOrder.getRemark()) ? dbPointsMallOrder.getRemark() : "无";
                    wxPublicPlatformNotifyService.sendNewOrderMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), deliveryAddress, contacts, amountDescription, remark);
                }else{
                    String addressStr = "";
                    String deliveryAddress = "";
                    String contacts = "";
                    String amountDescription = "";
                    String remark = "";
                    wxPublicPlatformNotifyService.sendNewOrderMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), deliveryAddress, contacts, amountDescription, remark);
                }
                goodsDescription = "";
                isFirstSend = false;
            }
        }

        //平台邀请佣金
        BigDecimal inviteeConsumeCommissionPercent = setting.getPointsMallInviteeConsumeCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal totalCommissionAmount = dbPointsMallOrder.getActualPrice().multiply(inviteeConsumeCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
        BigDecimal inviterCommissionPercent, commissionAmount;
        String message;
        //给下单用户的邀请人发放佣金
        Map<String, Integer> inviterMap = memberInviteRelationService.selectInviter(dbPointsMallOrder.getMemberId());
        if(inviterMap.containsKey("secondLevelInviter")){
            //有2个上级邀请人
            //发放下单用户佣金奖励
            inviterCommissionPercent = setting.getPointsMallCasethreeOwnCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            message = "下单用户佣金奖励，来自" + dbMember.getMobile()+"-"+dbMember.getUsername();
            giveInviterReward(dbMember.getId(), commissionAmount, MemberBillingRecord.TYPE_OWN_COMMISSION, message, dbPointsMallOrder.getId());

            //发放一级邀请人佣金奖励
            inviterCommissionPercent = setting.getPointsMallCasethreeFirstLevelInviterCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            message = "一级邀请人佣金奖励，来自" + dbMember.getMobile()+"-"+dbMember.getUsername();
            giveInviterReward(inviterMap.get("firstLevelInviter"), commissionAmount, MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION, message, dbPointsMallOrder.getId());

            //发放二级邀请人佣金奖励
            inviterCommissionPercent = setting.getPointsMallCasethreeSecondLevelInviterCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            message = "二级邀请人佣金，来自" + dbMember.getMobile()+"-"+dbMember.getUsername();
            giveInviterReward(inviterMap.get("secondLevelInviter"), commissionAmount, MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION, message, dbPointsMallOrder.getId());

        }else if(inviterMap.containsKey("firstLevelInviter")){
            //有1个上级邀请人时
            //发放下单用户佣金奖励
            inviterCommissionPercent = setting.getPointsMallCasetwoOwnCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            message = "下单用户佣金奖励，来自" + dbMember.getMobile()+"-"+dbMember.getUsername();
            giveInviterReward(dbMember.getId(), commissionAmount, MemberBillingRecord.TYPE_OWN_COMMISSION, message, dbPointsMallOrder.getId());

            //发放一级邀请人佣金奖励
            inviterCommissionPercent = setting.getPointsMallCasetwoFirstLevelInviterCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            message = "一级邀请人佣金奖励，来自" + dbMember.getMobile()+"-"+dbMember.getUsername();
            giveInviterReward(inviterMap.get("firstLevelInviter"), commissionAmount, MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION, message, dbPointsMallOrder.getId());

        }else if(inviterMap.isEmpty()){
            //无上级邀请人时
            //发放下单用户佣金奖励
            inviterCommissionPercent = setting.getPointsMallCaseoneOwnCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            message = "下单用户佣金奖励，来自" + dbMember.getMobile()+"-"+dbMember.getUsername();
            giveInviterReward(dbMember.getId(), commissionAmount, MemberBillingRecord.TYPE_OWN_COMMISSION, message, dbPointsMallOrder.getId());
        }
    }

    @Override
    public void updateByChangeToDeliveryPayNotify(String changeToDeliveryOutTradeNo) {

    }

    /**
     * 给邀请人发放佣金
     *
     * @return
     */
    public void giveInviterReward(Integer inviterId, BigDecimal commissionAmount, Integer type, String message, Long orderId){
        //规则：如果邀请人不是会员，则不给与佣金奖励；如果邀请人在近30天内无消费，则不给予佣金奖励，不对其它邀请人造成影响；
        //1、开通会员后的第一笔订单无需判断近30天是否有消费这个条件，直接给与佣金奖励
        //2、近30天是否有消费这个条件 是指用户下过单(无论什么状态)--只要支付成功过的都算
        //3、近30天是否有消费这个条件 是指外卖系统、积分商城、(会员充值) 任意有一笔消费即可
        //4、近30天是否有消费这个条件 需要排除当前支付的订单
        Member dbMember = memberService.selectByPrimaryKey(inviterId);
        if(!dbMember.getType().equals(Quantity.INT_2)){
            log.info("\n\nid为" + inviterId + "的邀请人不是会员，不给予佣金奖励");
            return;
        }

        //判断当前订单是否为开通会员后的第一笔订单，如果是，则直接给与佣金奖励
        //查询当前用户最近一笔支付成功的会员充值记录
        VipRechargeRecord vipRechargeRecord = vipRechargeRecordService.selectLastestPaid(dbMember.getId());
        List excludeStatusList = new ArrayList();
        excludeStatusList.add(1);
        excludeStatusList.add(10);
        //查询外卖系统的符合订单数
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andCreateTimeGreaterThan(vipRechargeRecord.getCreateTime()).andStatusNotIn(excludeStatusList);
        int count = orderService.countByExample(orderExample);
        //查询积分商城的符合订单数
        PointsMallOrderExample pointsMallOrderExample = new PointsMallOrderExample();
        pointsMallOrderExample.createCriteria().andIdNotEqualTo(orderId).andCreateTimeGreaterThan(vipRechargeRecord.getCreateTime()).andStatusNotIn(excludeStatusList);
        Integer count_pointsMall = pointsMallOrderMapper.countByExample(pointsMallOrderExample);
        int total_count = count + count_pointsMall;
        if(total_count > 0){
            //不是第一笔订单，进行近30天是否有消费的条件判断
            //近30天外卖系统支付成功的订单数
            OrderParam orderParam = new OrderParam();
            orderParam.setMemberId(inviterId);
            orderParam.setStartTime(DateUtilsExtend.getFrontDay(DateUtilsExtend.getDayEnd(), 30));
            orderParam.setEndTime(DateUtilsExtend.getDayEnd());
            count = this.orderService.selectCountPaid(orderParam);
            //近30天积分商城支付成功的订单数
            PointsMallOrderParam pointsMallOrderParam = new PointsMallOrderParam();
            pointsMallOrderParam.setMemberId(inviterId);
            pointsMallOrderParam.setExcludeOrderId(orderId);
            count_pointsMall = this.pointsMallOrderMapper.selectCountPaid(pointsMallOrderParam, DateUtilsExtend.getFrontDay(DateUtilsExtend.getDayEnd(), 30), DateUtilsExtend.getDayEnd());
            count_pointsMall = count_pointsMall!=null ? count_pointsMall : 0;

            total_count = count + count_pointsMall;
            if(total_count == 0){
                //TODO-可以建立一张表把这些信息存下来的
                log.info("\n\nid为" + inviterId + "的邀请人在近30天内无消费，不给予佣金奖励");
                return;
            }
        }

        //增加用户的邀请新用户注册奖励金额
        BigDecimal updateUnreceivedInviteRewardAmount = dbMember.getUnreceivedInviteRewardAmount().add(commissionAmount).setScale(2, BigDecimal.ROUND_HALF_UP);

        Member updateMember = new Member();
        updateMember.setId(dbMember.getId());
        updateMember.setUnreceivedInviteRewardAmount(updateUnreceivedInviteRewardAmount);
        updateMember.setUpdateTime(new Date());
        memberService.updateByPrimaryKeySelective(updateMember);
        dbMember = memberService.selectByPrimaryKey(inviterId);

        //增加用户账单记录
        MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
        memberBillingRecord.setMemberId(inviterId);
        memberBillingRecord.setType(type);
        memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
        memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_UNRECEIVED_INVITE_REWARD_AMOUNT);
        memberBillingRecord.setNumber(commissionAmount);
        memberBillingRecord.setMessage(message);
        memberBillingRecord.setPointsMallOrderId(orderId);
        memberBillingRecord.setCreateTime(new Date());
        memberBillingRecordService.insertSelective(memberBillingRecord);
    }

    @Override
    public Map countOrder(PointsMallOrderParam order) {
        return pointsMallOrderMapper.countPointsMallOrder(order);
    }

    @Override
    public int selectLatelyMonthlySalesByShopId(Date startTime, Date endTime, Integer shopId){
        Integer integer = pointsMallOrderMapper.selectLatelyMonthlySalesByShopId(startTime, endTime, shopId);
        return integer!=null ? integer : 0;
    }

    @Override
    public BigDecimal selectSumMerchantIncome(PointsMallOrderParam order,Date startTime, Date endTime) {
        return pointsMallOrderMapper.selectSumMerchantIncome(order, startTime, endTime);
    }

    @Override
    public BigDecimal selectSumActualPrice(PointsMallOrderParam order,Date startTime, Date endTime) {
        return pointsMallOrderMapper.selectSumActualPrice(order, startTime, endTime);
    }

    @Override
    public int selectCountCompleted(PointsMallOrderParam order,Date startTime, Date endTime) {
        Integer integer = pointsMallOrderMapper.selectCountCompleted(order, startTime, endTime);
        return integer!=null ? integer : 0;
    }

    @Override
    public boolean getIsAllowApplyRefund(PointsMallOrderParam order) {
        return pointsMallOrderMapper.getIsAllowApplyRefund(order);
    }

    @Override
    public boolean getIsAllowApplyAfterSales(PointsMallOrderParam order) {
        return pointsMallOrderMapper.getIsAllowApplyAfterSales(order);
    }

    @Override
    public boolean getIsShowLogistics(PointsMallOrderParam order) {
        return pointsMallOrderMapper.getIsShowLogistics(order);
    }

    @Override
    public Page<Map<String, Object>> getAfterSalesListByPageWithAsc(PointsMallOrderParam param) {
        Page<Map<String, Object>> page = pointsMallOrderMapper.getAfterSalesListByPageWithAsc(new Page(param.getPageNo(), param.getPageSize()), param);

        //查询出这些订单对应的订单商品详情信息
        List<Long> orderIdList = new ArrayList<>();
        page.getRecords().forEach(obj -> {
            orderIdList.add((long) obj.get("id"));
        });
        if (!orderIdList.isEmpty()){
            PointsMallOrderDetailExample detailExample = new PointsMallOrderDetailExample();
            detailExample.createCriteria().andPointsMallOrderIdIn(orderIdList);
            List<PointsMallOrderDetail> detailList = orderDetailService.selectByExample(detailExample);

            //组装返回数据
            Map<Long, List> filterMap = new LinkedHashMap<>();
            detailList.forEach(detailObj -> {
                if(!filterMap.containsKey(detailObj.getOrderId())){
                    filterMap.put(detailObj.getOrderId(), new ArrayList<>());
                }
                filterMap.get(detailObj.getOrderId()).add(detailObj);
            });

            page.getRecords().forEach(obj -> {
                obj.put("orderDetailList", filterMap.get(obj.get("id")));
            });
        }

        return page;
    }

    @Override
    public void updatePayOrderFrozenBalanceOfMerchant() {

    }

    @Override
    public void remindOvertimeOrder() {

    }

    @Override
    public List<Map<String, Object>> selectStatisticOrder(Integer shopId) {
        return pointsMallOrderMapper.selectStatisticPointsMallOrder(shopId);
    }

    @Override
    public String selectStartDatePointsMallOrder(Integer shopId) {
        return pointsMallOrderMapper.selectStartDatePointsMallOrder(shopId);
    }

    @Override
    public int selectCountPayers(PointsMallOrderParam order,Date startTime, Date endTime) {
        return pointsMallOrderMapper.selectCountPayers(order, startTime, endTime);
    }

    @Override
    public int selectCountOrderPeoples(PointsMallOrderParam order,Date startTime, Date endTime) {
        return pointsMallOrderMapper.selectCountPointsMallOrderPeoples(order, startTime, endTime);
    }

    @Override
    public void updateRefundStatus(String out_trade_no){
        //微信退款服务成功后回调，修改相关业务表的退款状态,订单状态等
        PointsMallOrder dbPointsMallOrder = this.selectByOrderNo(out_trade_no);

        //添加退款记录
        PointsMallOrderRefund dbPointsMallOrderRefund = orderRefundService.selectByPointsMallOrderId(dbPointsMallOrder.getId());
        PointsMallOrderRefund updatePointsMallOrderRefund = new PointsMallOrderRefund();
        updatePointsMallOrderRefund.setId(dbPointsMallOrderRefund.getId());
        updatePointsMallOrderRefund.setStatus(Quantity.INT_7);
        updatePointsMallOrderRefund.setUpdateTime(new Date());
        orderRefundService.updateByPrimaryKeySelective(updatePointsMallOrderRefund);

        //添加退款流程
        PointsMallOrderRefundProcess orderRefundProcess = new PointsMallOrderRefundProcess();
        orderRefundProcess.setOrderRefundId(dbPointsMallOrderRefund.getId());
        orderRefundProcess.setName("退款成功");
        orderRefundProcess.setCreateTime(new Date());
        orderRefundProcessService.insertSelective(orderRefundProcess);

        //TODO-对订单退款金额进行划分
        if(dbPointsMallOrderRefund.getRefundWay()==Quantity.INT_1 && dbPointsMallOrderRefund.getIsRefundDeliveryFee()){
            //退还发货费 / 全额退款
        }else{
            //不退还发货费 / 部分退款
            //减少商家-用户下单冻结资金
        }
    }

    @Override
    public void syncOrderLogisticsInfo() {
        //同步积分商城-订单物流信息
        //查询订单状态处于(5=已发货 6=已完成 7=售后处理中) 且 未签收的订单
        List<PointsMallOrderResult> list = pointsMallOrderMapper.selectByNeedSyncOrderLogisticsInfo();
        list.forEach(order -> {
            //排除没有填写快递单号的
            if(StringUtils.isBlank(order.getLogisticsNo())){
                //TODO-做提醒
                return;
            }
            orderLogisticsService.updateOrderLogisticsInfo(order.getId(), order.getLogisticsNo());
        });
    }

    @Override
    public int selectCountPaid(PointsMallOrderParam order,Date startTime, Date endTime) {
        Integer integer = this.pointsMallOrderMapper.selectCountPaid(order, startTime, endTime);
        return integer!=null ? integer : 0;
    }

    @Override
    public void batchUpdateIsPrintedTrue(PointsMallOrderParam param) {

        if(StringUtils.isEmpty(param.getIdListStr())){
            throw new StoneCustomerException("订单id不能为空");
        }

        List<Integer> idList = GsonUtils.toList(param.getIdListStr(), Integer.class);
        if(idList!=null && idList.size()>0){
            orderService.batchUpdateIsPrintedTrue(idList);
        }

    }

    @Override
    public Map selectAllTabWaitHandleNum(PointsMallOrderParam param) {

        Map dataMap = new HashMap();

        //自取订单-待制作订单
        PointsMallOrderExample example = new PointsMallOrderExample();
        example.createCriteria().andShoppingWayEqualTo(Quantity.INT_1).andStatusEqualTo(Quantity.INT_2);
        int waitHandleNum = pointsMallOrderMapper.countByExample(example);
        dataMap.put("waitHandleNum", waitHandleNum);

        //自取订单-待自取订单
        example = new PointsMallOrderExample();
        example.createCriteria().andShoppingWayEqualTo(Quantity.INT_1).andStatusEqualTo(Quantity.INT_3);
        int waitPickUpNum = pointsMallOrderMapper.countByExample(example);
        dataMap.put("waitPickUpNum", waitPickUpNum);

        //外卖订单-待配送订单
        example = new PointsMallOrderExample();
        example.createCriteria().andShoppingWayEqualTo(Quantity.INT_2).andStatusEqualTo(Quantity.INT_4);
        int waitDeliveryNum = pointsMallOrderMapper.countByExample(example);
        dataMap.put("waitDeliveryNum", waitDeliveryNum);

        //外卖订单-已配送订单
        example = new PointsMallOrderExample();
        example.createCriteria().andShoppingWayEqualTo(Quantity.INT_2).andStatusEqualTo(Quantity.INT_5);
        int deliveredNum = pointsMallOrderMapper.countByExample(example);
        dataMap.put("deliveredNum", deliveredNum);
        return dataMap;
    }

    @Override
    public List<PointsMallOrder> waitPrintPointsMallOrderList(PointsMallOrderParam param) {
        //排除掉状态为1=未付款、10=已取消的订单
        List<Integer> excludedStatusList = new ArrayList<>();
        excludedStatusList.add(Quantity.INT_1);
        excludedStatusList.add(Quantity.INT_10);
        PointsMallOrderExample example = new PointsMallOrderExample();
        example.createCriteria().andStatusNotIn(excludedStatusList).andIsPrintedEqualTo(false);
        List<PointsMallOrder> orders = pointsMallOrderMapper.selectByExample(example);
        return orders;
    }

    @Override
    public void auditAfterSalesOrder(PointsMallOrderParam param) {
        //TODO(MARK)-此处有三种退款类型需要处理
        if(param.getStatus() == Quantity.INT_2 && StringUtils.isBlank(param.getOpinion())){
            throw new StoneCustomerException("审核不通过时，审核意见不能为空");
        }

        PointsMallOrder dbPointsMallOrder = pointsMallOrderMapper.selectById(param.getId());
        if(dbPointsMallOrder == null) throw new StoneCustomerException("该订单不存在");

        PointsMallOrderRefund dbPointsMallOrderRefund = orderRefundService.selectByPointsMallOrderId(param.getId());
        if(dbPointsMallOrderRefund == null) throw new StoneCustomerException("该订单无退款记录");
        if(dbPointsMallOrderRefund.getStatus()!=Quantity.INT_4){
            throw new StoneCustomerException("该售后订单已被处理过，不允许操作");
        }

        //获取该订单的对应用户
        Member orderMember = memberService.selectByPrimaryKey(dbPointsMallOrder.getMemberId());

        if(param.getStatus() == Quantity.INT_1){
            //审核通过

            //修改订单记录状态-售后处理完成
            PointsMallOrder updatePointsMallOrder = new PointsMallOrder();
            updatePointsMallOrder.setId(param.getId());
            updatePointsMallOrder.setStatus(Quantity.INT_9);
            pointsMallOrderMapper.updateByPrimaryKeySelective(updatePointsMallOrder);

            //进行订单自动退款操作
            boolean isRefundSuccess = false;
            if(dbPointsMallOrder.getPaymentMode().equals(Quantity.INT_1)){
                //微信支付
                isRefundSuccess = pointsMallWxPayService.refund(dbPointsMallOrder.getOrderNo(), dbPointsMallOrder.getActualPrice(), dbPointsMallOrderRefund.getRefundAmount());

            }else if(dbPointsMallOrder.getPaymentMode().equals(Quantity.INT_3)){
                //余额支付
                //增加用户的余额
                Member updateMember = new Member();
                updateMember.setId(orderMember.getId());
                updateMember.setPoints(orderMember.getPoints().add(dbPointsMallOrderRefund.getRefundAmount()));
                updateMember.setTotalConsumePoints(orderMember.getTotalConsumePoints().subtract(dbPointsMallOrderRefund.getRefundAmount()));
                memberService.updateByPrimaryKeySelective(updateMember);
                orderMember = memberService.selectByPrimaryKey(dbPointsMallOrder.getMemberId());
                //添加账单记录
                MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
                memberBillingRecord.setMemberId(orderMember.getId());
                memberBillingRecord.setType(dbPointsMallOrderRefund.getType()==Quantity.INT_1 ? MemberBillingRecord.TYPE_POINTSMALL_ORDER_UNDELIVERED_REFUND_RETURN_POINTS : MemberBillingRecord.TYPE_POINTSMALL_ORDER_DELIVERED_REFUND_RETURN_POINTS);
                memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
                memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_POINTS);
                memberBillingRecord.setNumber(dbPointsMallOrderRefund.getRefundAmount());
                memberBillingRecord.setMessage(dbPointsMallOrderRefund.getType()==Quantity.INT_1 ? "未发货订单申请退款-积分退回" : "已发货订单申请退款-积分退回");
                memberBillingRecord.setCreateTime(new Date());
                memberBillingRecordService.insertSelective(memberBillingRecord);

                //退款成功的操作
                orderService.updateRefundStatus(dbPointsMallOrder.getOrderNo());

                isRefundSuccess = true;
            }

            if(!isRefundSuccess){
                throw new StoneCustomerException("退款失败，请联系管理员");
            }

            //5）只有在退了使用优惠券的商品时，优惠券才会被退回
            if(dbPointsMallOrderRefund.getIsUsedCoupons()){
                //退回优惠卷
                Integer couponsMemberRelationId = dbPointsMallOrder.getCouponsMemberRelationId();
                if (couponsMemberRelationId != null) {
                    couponsMemberRelationService.updateCouponsUsed(couponsMemberRelationId,false);
                }
            }

            //修改退款状态 -- 退款成功
            PointsMallOrderRefund updatePointsMallOrderRefund = new PointsMallOrderRefund();
            updatePointsMallOrderRefund.setId(dbPointsMallOrderRefund.getId());
            updatePointsMallOrderRefund.setStatus(Quantity.INT_7);
            orderRefundService.updateByPrimaryKeySelective(updatePointsMallOrderRefund);

            //添加退款流程 -- 等待平台处理
            PointsMallOrderRefundProcess orderRefundProcess = new PointsMallOrderRefundProcess();
            orderRefundProcess.setOrderRefundId(dbPointsMallOrderRefund.getId());
            orderRefundProcess.setName("等待平台处理");
            orderRefundProcess.setCreateTime(new Date());
            orderRefundProcessService.insertSelective(orderRefundProcess);

            //添加退款流程 -- 退款成功
            PointsMallOrderRefundProcess orderRefundProcess_second = new PointsMallOrderRefundProcess();
            orderRefundProcess_second.setOrderRefundId(dbPointsMallOrderRefund.getId());
            orderRefundProcess_second.setName("退款成功");
            orderRefundProcess_second.setCreateTime(DateUtilsPlus.addSeconds(new Date(), 5));
            orderRefundProcessService.insertSelective(orderRefundProcess_second);

            //发送服务通知
            wxNotifyService.sendOrderRefundSuccessMessage(orderMember.getOpenId(), dbPointsMallOrder.getShopName(), dbPointsMallOrder.getOrderNo(), dbPointsMallOrder.getDescription(), dbPointsMallOrderRefund.getRefundAmount(), new Date());

            //退回下单佣金奖励
            List typeList = new ArrayList();
            typeList.add(MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION);
            typeList.add(MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION);
            typeList.add(MemberBillingRecord.TYPE_OWN_COMMISSION);
            MemberBillingRecordExample example = new MemberBillingRecordExample();
            example.createCriteria().andTypeIn(typeList)
                    .andOperateTypeEqualTo(MemberBillingRecord.OPERATE_TYPE_ADD)
                    .andCoinTypeEqualTo(MemberBillingRecord.COIN_TYPE_UNRECEIVED_INVITE_REWARD_AMOUNT)
                    .andPointsMallOrderIdEqualTo(dbPointsMallOrder.getId());
            List<MemberBillingRecord> list = memberBillingRecordService.selectByExample(example);
            if(!list.isEmpty()){
                for (MemberBillingRecord dbMemberBillingRecord : list) {
                    Integer type = null;
                    String message = "";
                    if(dbMemberBillingRecord.getType().equals(MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION)){
                        type = MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION_RETURN;
                        message = "订单退款-一级邀请人佣金奖励退回";
                    }else if(dbMemberBillingRecord.getType().equals(MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION)){
                        type = MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION_RETURN;
                        message = "订单退款-二级邀请人佣金奖励退回";
                    }else if(dbMemberBillingRecord.getType().equals(MemberBillingRecord.TYPE_OWN_COMMISSION)){
                        type = MemberBillingRecord.TYPE_OWN_COMMISSION_RETURN;
                        message = "订单退款-下单用户佣金奖励退回";
                    }

                    Member dbMember = memberService.selectByPrimaryKey(dbMemberBillingRecord.getMemberId());
                    //增加用户的邀请新用户注册奖励金额
                    BigDecimal updateUnreceivedInviteRewardAmount = dbMember.getUnreceivedInviteRewardAmount().subtract(dbMemberBillingRecord.getNumber()).setScale(2, BigDecimal.ROUND_HALF_UP);

                    Member updateMember = new Member();
                    updateMember.setId(dbMember.getId());
                    updateMember.setUnreceivedInviteRewardAmount(updateUnreceivedInviteRewardAmount);
                    updateMember.setUpdateTime(new Date());
                    memberService.updateByPrimaryKeySelective(updateMember);
                    dbMember = memberService.selectByPrimaryKey(dbMemberBillingRecord.getMemberId());

                    //增加用户账单记录
                    MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
                    memberBillingRecord.setMemberId(dbMember.getId());
                    memberBillingRecord.setType(type);
                    memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_SUB);
                    memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_UNRECEIVED_INVITE_REWARD_AMOUNT);
                    memberBillingRecord.setNumber(dbMemberBillingRecord.getNumber());
                    memberBillingRecord.setMessage(message);
                    memberBillingRecord.setPointsMallOrderId(dbPointsMallOrder.getId());
                    memberBillingRecord.setCreateTime(new Date());
                    memberBillingRecordService.insertSelective(memberBillingRecord);

                    //之前的账单记录标注已退回
                    MemberBillingRecord updateMemberBillingRecord = new MemberBillingRecord();
                    updateMemberBillingRecord.setId(dbMemberBillingRecord.getId());
                    updateMemberBillingRecord.setIsReturn(true);
                    memberBillingRecordService.updateByPrimaryKeySelective(updateMemberBillingRecord);
                }
            }
        }else if(param.getStatus() == Quantity.INT_2){
            //审核不通过

            //修改订单记录状态-售后处理完成
            PointsMallOrder updatePointsMallOrder = new PointsMallOrder();
            updatePointsMallOrder.setId(param.getId());
            updatePointsMallOrder.setStatus(Quantity.INT_9);
            pointsMallOrderMapper.updateByPrimaryKeySelective(updatePointsMallOrder);

            //修改退款状态 -- 平台拒绝退款，退款已关闭
            PointsMallOrderRefund updatePointsMallOrderRefund = new PointsMallOrderRefund();
            updatePointsMallOrderRefund.setId(dbPointsMallOrderRefund.getId());
            updatePointsMallOrderRefund.setStatus(Quantity.INT_5);
            orderRefundService.updateByPrimaryKeySelective(updatePointsMallOrderRefund);

            //添加退款流程 -- 等待平台处理
            PointsMallOrderRefundProcess orderRefundProcess = new PointsMallOrderRefundProcess();
            orderRefundProcess.setOrderRefundId(dbPointsMallOrderRefund.getId());
            orderRefundProcess.setName("等待平台处理");
            orderRefundProcess.setCreateTime(new Date());
            orderRefundProcessService.insertSelective(orderRefundProcess);

            //添加退款流程 -- 平台拒绝退款，退款已关闭
            PointsMallOrderRefundProcess orderRefundProcess_second = new PointsMallOrderRefundProcess();
            orderRefundProcess_second.setOrderRefundId(dbPointsMallOrderRefund.getId());
            orderRefundProcess_second.setName("平台拒绝退款，退款已关闭");
            orderRefundProcess_second.setDescription(param.getOpinion());
            orderRefundProcess_second.setCreateTime(DateUtilsPlus.addSeconds(new Date(), 5));
            orderRefundProcessService.insertSelective(orderRefundProcess_second);
        }

    }

    @Override
    public Map statistic(PointsMallOrderParam param) throws ParseException {

        Map resultMap = new HashMap();

        //只统计状态为[6=已完成 7=售后处理中 9=售后处理完成]的订单数据

        //日期、订单数量、订单金额

        //1、查询开始日期(系统/订单服务第一次上线日期) - 今天
        /*String startDate = "2020-05-10";*/
        String startDate = pointsMallOrderMapper.selectStartDatePointsMallOrder(null);
        if(startDate == null){
            startDate = DateUtilsPlus.formatDate(new Date(), "YYYY-MM-dd");
        }
        String endDate = DateUtilsPlus.formatDate(new Date(), "YYYY-MM-dd");
        List<String> betweenDays = DateUtilsPlus.getBetweenDays(startDate, endDate);

        //2、构造日期列表
        Map<String, Map> filterMap = new HashMap();
        List<Map<String, Object>> resultList = new ArrayList();
        for (String dateStr : betweenDays) {
            Map<String, Object> map = new HashMap<>();
            map.put("date", dateStr); //前端的变量名为date
            map.put("orderCount", 0);
            map.put("orderAmount", 0);
            resultList.add(map);
            //记录日期对应的集合元素，便于后续赋值
            filterMap.put(dateStr, map);
        }

        //3、查询每一个日期对应的 订单数量、订单金额
        List<Map<String, Object>> statisticList = orderService.selectStatisticOrder(null);
        statisticList.forEach(statisticMap -> {
            if(filterMap.containsKey(statisticMap.get("orderDate"))){
                //修改订单数据
                Map map = filterMap.get(statisticMap.get("orderDate"));
                map.put("orderCount", statisticMap.get("orderCount"));
                map.put("orderAmount", statisticMap.get("orderAmount"));
            }
        });

        resultMap.put("resultList", resultList);


        //订单表筛选条件-所有商家
        PointsMallOrderParam order = new PointsMallOrderParam();

        //本月订单总数、上月订单总数、同比情况
        int thisMonthCountPaid = pointsMallOrderMapper.selectCountCompleted(order, DateUtilsExtend.getBeginDayOfMonth(), DateUtilsExtend.getEndDayOfMonth());
        int lastMonthCountPaid = pointsMallOrderMapper.selectCountCompleted(order, DateUtilsExtend.getBeginDayOfLastMonth(), DateUtilsExtend.getEndDayOfLastMonth());

        //本周订单总数、上周订单总数、同比情况
        int thisWeekCountPaid = pointsMallOrderMapper.selectCountCompleted(order, DateUtilsExtend.getBeginDayOfWeek(), DateUtilsExtend.getEndDayOfWeek());
        int lastWeekCountPaid = pointsMallOrderMapper.selectCountCompleted(order, DateUtilsExtend.getBeginDayOfLastWeek(), DateUtilsExtend.getEndDayOfLastWeek());

        //本月销售总额、上月销售总额、同比情况
        BigDecimal thisMonthSumActualPrice = pointsMallOrderMapper.selectSumActualPrice(order, DateUtilsExtend.getBeginDayOfMonth(), DateUtilsExtend.getEndDayOfMonth());
        BigDecimal lastMonthSumActualPrice = pointsMallOrderMapper.selectSumActualPrice(order, DateUtilsExtend.getBeginDayOfLastMonth(), DateUtilsExtend.getEndDayOfLastMonth());

        //本周销售总额、上周销售总额、同比情况
        BigDecimal thisWeekSumActualPrice = pointsMallOrderMapper.selectSumActualPrice(order, DateUtilsExtend.getBeginDayOfWeek(), DateUtilsExtend.getEndDayOfWeek());
        BigDecimal lastWeekSumActualPrice = pointsMallOrderMapper.selectSumActualPrice(order, DateUtilsExtend.getBeginDayOfLastWeek(), DateUtilsExtend.getEndDayOfLastWeek());

        resultMap.put("thisMonthCountPaid", thisMonthCountPaid);
        resultMap.put("lastMonthCountPaid", lastMonthCountPaid);
        resultMap.put("thisWeekCountPaid", thisWeekCountPaid);
        resultMap.put("lastWeekCountPaid", lastWeekCountPaid);
        resultMap.put("thisMonthSumActualPrice", thisMonthSumActualPrice);
        resultMap.put("lastMonthSumActualPrice", lastMonthSumActualPrice);
        resultMap.put("thisWeekSumActualPrice", thisWeekSumActualPrice);
        resultMap.put("lastWeekSumActualPrice", lastWeekSumActualPrice);
        return resultMap;
    }

    @Override
    public void deliver(PointsMallOrderParam param) {

        PointsMallOrder dbOrder = pointsMallOrderMapper.selectById(param.getId());
        if(dbOrder == null) throw new StoneCustomerException("该订单不存在");

        if(dbOrder.getStatus() != Quantity.INT_4){
            throw new StoneCustomerException("该订单状态非待发货，不允许操作");
        }

        //修改订单状态为5=已发货 且 保存快递单号
        PointsMallOrder updateOrder = new PointsMallOrder();
        updateOrder.setId(param.getId());
        updateOrder.setStatus(Quantity.INT_5);
        updateOrder.setLogisticsNo(param.getLogisticsNo());
        pointsMallOrderMapper.updateByPrimaryKeySelective(updateOrder);

        //系统默认插入的两条物流状态描述
        PointsMallOrderLogistics logistics = new PointsMallOrderLogistics();
        logistics.setOrderId(param.getId());
        logistics.setDescription(PointsMallOrderLogistics.DESCRIPTION_OF_FIRST_COMMIT_ORDER);
        logistics.setDescriptionTime(dbOrder.getPaymentSuccessTime());
        logistics.setCreateTime(new Date());
        orderLogisticsService.insertSelective(logistics);

        logistics = new PointsMallOrderLogistics();
        logistics.setOrderId(param.getId());
        logistics.setDescription(PointsMallOrderLogistics.DESCRIPTION_OF_SECOND_DELIVERED);
        logistics.setDescriptionTime(new Date());
        logistics.setCreateTime(new Date());
        orderLogisticsService.insertSelective(logistics);

        //同步积分商城-订单物流信息
        Boolean isSuccess = orderLogisticsService.updateOrderLogisticsInfo(param.getId(), param.getLogisticsNo());
        if(!isSuccess){
            throw new StoneCustomerException("物流跟踪信息同步失败，请检查快递单号是否填写正确！");
        }

    }

    @Override
    public void updateLogisticsNo(PointsMallOrderParam param) {
        PointsMallOrder dbOrder = pointsMallOrderMapper.selectById(param.getId());
        if(dbOrder == null) throw new StoneCustomerException("该订单不存在");

        if(dbOrder.getStatus()!=Quantity.INT_5 && dbOrder.getStatus()!=Quantity.INT_7){
            throw new StoneCustomerException("该订单所处状态不允许修改快递单号");
        }

        //修改快递单号
        PointsMallOrder updateOrder = new PointsMallOrder();
        updateOrder.setId(param.getId());
        updateOrder.setLogisticsNo(param.getLogisticsNo());
        pointsMallOrderMapper.updateByPrimaryKeySelective(updateOrder);

        //同步积分商城-订单物流信息
        Boolean isSuccess = orderLogisticsService.updateOrderLogisticsInfo(param.getId(), param.getLogisticsNo());
        if(!isSuccess){
            throw new StoneCustomerException("物流跟踪信息同步失败，请检查快递单号是否填写正确！");
        }

    }

    @Override
    public void updateByAdmin(PointsMallOrderParam param) {
        PointsMallOrder dbOrder = pointsMallOrderMapper.selectById(param.getId());
        if(dbOrder == null) throw new StoneCustomerException("该订单不存在");

        //只准修改发货源、发货源订单编号、平台备注
        PointsMallOrder updateOrder = new PointsMallOrder();
        updateOrder.setId(param.getId());
        updateOrder.setGoodsSource(param.getGoodsSource());
        updateOrder.setGoodsSourceOrderNo(param.getGoodsSourceOrderNo());
        updateOrder.setPlatformRemark(param.getPlatformRemark());
        pointsMallOrderMapper.updateByPrimaryKeySelective(updateOrder);
    }

    @Override
    public PointsMallOrderVo selectById(PointsMallOrderParam param) {
        PointsMallOrderVo vo = new PointsMallOrderVo();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        PointsMallOrder dbPointsMallOrder = pointsMallOrderMapper.selectById(param.getId());
        if(dbPointsMallOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }
        if(!dbPointsMallOrder.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("该订单不是你的，不允许查看");
        }

        //查询订单对应的订单商品详情数据
        List<PointsMallOrderDetail> orderDetailList = orderDetailService.selectByPointsMallOrderId(param.getId());

        //如果订单已评价 或 订单已完成超过14天 或 订单非已完成状态，则不允许评价
        Map<String, Object> dbPointsMallOrderMap = BeanUtils.beanToMap(dbPointsMallOrder);
        /*Appraise appraise = new Appraise();
        appraise.setOrderId(dbPointsMallOrder.getId());
        appraise.setMemberId(dbPointsMallOrder.getMemberId());
        boolean isAllowAppraise = appraiseService.getIsAllowAppraise(appraise);
        if(isAllowAppraise){
            if (dbPointsMallOrder.getStatus()!=Quantity.INT_6 || DateUtils.diffDays(new Date(), dbPointsMallOrder.getCreateTime())>14){
                isAllowAppraise = false;
            }
        }
        dbPointsMallOrderMap.put("isAllowAppraise", isAllowAppraise);*/

        //订单未发货时可以申请退款
        PointsMallOrderParam orderParam = new PointsMallOrderParam();
        orderParam.setId(dbPointsMallOrder.getId());
        boolean isAllowApplyRefund = pointsMallOrderMapper.getIsAllowApplyRefund(orderParam);
        dbPointsMallOrderMap.put("isAllowApplyRefund", isAllowApplyRefund);

        //订单已发货，距离支付时间未超过20天可以申请售后
        boolean isAllowApplyAfterSales = pointsMallOrderMapper.getIsAllowApplyAfterSales(orderParam);
        dbPointsMallOrderMap.put("isAllowApplyAfterSales", isAllowApplyAfterSales);

        //如果可以无责取消订单，则无需显示申请退款按钮
        if(isAllowApplyRefund){
            dbPointsMallOrderMap.put("isAllowApplyAfterSales", false);
        }

        //查询订单的退款进度
        PointsMallOrderRefund orderRefund = orderRefundService.selectByPointsMallOrderId(dbPointsMallOrder.getId());
        if(orderRefund != null){
            dbPointsMallOrderMap.put("isRefundOrder", true);
            dbPointsMallOrderMap.put("refundStatus", orderRefund.getStatus());
        }else{
            dbPointsMallOrderMap.put("isRefundOrder", false);
        }

        //查看物流按钮，只有状态处于已发货、已完成时才能显示
        boolean isShowLogistics = pointsMallOrderMapper.getIsShowLogistics(orderParam);
        dbPointsMallOrderMap.put("isShowLogistics", isShowLogistics);

        //返回商家电话
        /*Shop shop = shopService.selectByPrimaryKey(dbPointsMallOrder.getShopId());
        dbPointsMallOrderMap.put("shopContactPhone", shop.getContactPhone());*/

        vo.setOrder(dbPointsMallOrderMap);
        vo.setOrderDetailList(orderDetailList);
        return vo;
    }

    @Override
    public void cancelOrder(PointsMallOrderParam param) {

        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        PointsMallOrder dbPointsMallOrder = pointsMallOrderMapper.selectById(param.getId());
        if(dbPointsMallOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }
        if(!dbPointsMallOrder.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("该订单不是你的，不允许取消");
        }
        if(dbPointsMallOrder.getStatus() != Quantity.INT_1){
            throw new StoneCustomerException("该订单状态非未付款，不能取消");
        }

        //修改订单记录状态
        PointsMallOrder updatePointsMallOrder = new PointsMallOrder();
        updatePointsMallOrder.setId(dbPointsMallOrder.getId());
        updatePointsMallOrder.setStatus(Quantity.INT_10);
        pointsMallOrderMapper.updateByPrimaryKeySelective(updatePointsMallOrder);

        //查询订单对应的订单商品详情数据
        List<PointsMallOrderDetail> orderDetailList = orderDetailService.selectByPointsMallOrderId(param.getId());

        //恢复订单对应的商品库存 (对应规格的库存怎么修改)
        for(PointsMallOrderDetail orderDetail : orderDetailList){
            int goodsId = orderDetail.getGoodsId();
            int number = orderDetail.getNumber();
            //增加商品库存
//            goodsService.increaseStock(goodsId, number);
        }

        //退回优惠卷
        Integer couponsMemberRelationId = dbPointsMallOrder.getCouponsMemberRelationId();
        if (couponsMemberRelationId != null) {
            couponsMemberRelationService.updateCouponsUsed(couponsMemberRelationId,false);
        }

    }

    @Override
    public void applyRefundUndelivered(PointsMallOrderParam param) {

        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //手动将JSON字符串转化为对象
        List<PointsMallOrderRefundGoods> orderRefundGoodsList = StringUtils.isNotBlank(param.getOrderRefundGoodsListStr()) ? GsonUtils.toList(param.getOrderRefundGoodsListStr(), PointsMallOrderRefundGoods.class) : null;
        log.debug("\n\nparam.getOrderRefundGoodsListStr() : " + param.getOrderRefundGoodsListStr());
        if(orderRefundGoodsList != null){
            for (PointsMallOrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
                log.debug("\n\norderDetailId : " + orderRefundGoods.getOrderDetailId() + " -- number : " + orderRefundGoods.getNumber());
            }
        }

        PointsMallOrder dbPointsMallOrder = pointsMallOrderMapper.selectById(param.getId());
        if(dbPointsMallOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }
        if(!dbPointsMallOrder.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("该订单不是你的，不允许取消");
        }

        //订单未发货时可以申请退款
        PointsMallOrderParam orderParam = new PointsMallOrderParam();
        orderParam.setId(dbPointsMallOrder.getId());
        boolean isAllowApplyRefund = pointsMallOrderMapper.getIsAllowApplyRefund(orderParam);
        if(!isAllowApplyRefund){
            throw new StoneCustomerException("该订单不允许申请退款");
        }

        if(orderRefundGoodsList==null || orderRefundGoodsList.isEmpty()){
            throw new StoneCustomerException("订单退款-商品参数丢失");
        }

        //TODO-部分退款/全额退款不退包装费？发货费肯定是不退的
        //TODO-全额退款是否退还发货费？ -- 暂时退还
        //TODO-没有全额退款 和 部分退款的说法
        //TODO-目前一律不退还包装费与发货费，商品本身的价格
        //TODO-还要考虑用在该订单上的满减规则、优惠券
        //校验退款金额是否正确
        BigDecimal goodsAmount = BigDecimal.ZERO;

        Boolean isUsedPointsMallCoupons = false;

        /*BigDecimal packingCharges = BigDecimal.ZERO; //包装费*/

        Boolean isAllAmountRefund = false;

        int goodsTotalQuantity = 0;

        for (PointsMallOrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
            PointsMallOrderDetail orderDetail = orderDetailService.selectByPrimaryKey(orderRefundGoods.getOrderDetailId());
            if(orderDetail == null){
                throw new StoneCustomerException("数据异常，请稍后重试");
            }

            log.debug("\n\norderRefundGoods : " + orderRefundGoods + " -- number : " + orderRefundGoods.getNumber());
            log.debug("\n\norderDetail : " + orderDetail + " -- number : " + orderDetail.getNumber());


            if(orderDetail.getIsUsedCoupons()){
                isUsedPointsMallCoupons = true;
            }

            //校验退款数量是否正确
            if(orderRefundGoods.getNumber().compareTo(0)<=0 || orderRefundGoods.getNumber().compareTo(orderDetail.getNumber()) > 0){
                throw new StoneCustomerException(orderDetail.getGoodsName() + "的退款数量错误");
            }
            //累加退还金额
            BigDecimal subtotal = orderDetail.getPrice().multiply(BigDecimal.valueOf(orderRefundGoods.getNumber())).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
            goodsAmount = goodsAmount.add(subtotal);

            //累计包装费
            /*BigDecimal totalPackingCharges = orderDetail.getPackingCharges().multiply(BigDecimal.valueOf(orderRefundGoods.getNumber())).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
            packingCharges = packingCharges.add(totalPackingCharges);*/

            //累计退款商品总数量
            goodsTotalQuantity += orderRefundGoods.getNumber();

            //回写一些商品的基本信息，便于后续添加功能
            orderRefundGoods.setGoodsId(orderDetail.getGoodsId());
            orderRefundGoods.setGoodsName(orderDetail.getGoodsName());
            orderRefundGoods.setMainImage(orderDetail.getMainImage());
            orderRefundGoods.setSpecList(orderDetail.getSpecList());
            orderRefundGoods.setPrice(orderDetail.getPrice());
            orderRefundGoods.setSubtotal(subtotal);
        }

        if(goodsTotalQuantity == dbPointsMallOrder.getGoodsTotalQuantity()){
            isAllAmountRefund = true;
        }

        log.debug("\n\n退款商品总金额 : " + goodsAmount);
        /*log.debug("\n\n退款商品总包装费 : " + packingCharges);*/

        /*BigDecimal refundAmount = goodsAmount.add(packingCharges);*/
        BigDecimal refundAmount = goodsAmount;


        //1）优惠券 只退 用了该优惠券的商品，如果使用了优惠券的商品购买了3件，那么退第1件的时候，就按照使用了优惠券的商品计算
        if(isUsedPointsMallCoupons){
            refundAmount = refundAmount.subtract(dbPointsMallOrder.getCouponsDiscountPrice()).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
        }
        log.debug("\n\n减去优惠券的折扣后为 : " + refundAmount);

        //2）退款金额+包装费-优惠券的优惠金额 凑足了满减，给他退减去的金额
        /*Boolean isUsedPointsMallFullReductionRule = false;
        if(dbPointsMallOrder.getFullReductionRuleId() !=null ){
            if(refundAmount.compareTo(dbPointsMallOrder.getLimitedPrice()) >= 0){
                refundAmount = refundAmount.subtract(dbPointsMallOrder.getReducedPrice()).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
                isUsedPointsMallFullReductionRule = true;
                log.debug("\n\n减去满减后为 : " + refundAmount);
            }else{
                log.debug("\n\n退款商品金额不满足满减");
            }
        }else{
            log.debug("\n\n该订单未使用满减");
        }*/

        //TODO(MARK)：用商品总价格+总包装费来判断是否满足满减规则；如果不满足，在计算商品分摊金额的时候只算商品本身价格，不算包装费；
        //2）如果商品总金额不满足满减条件，则需要对商品进行满减优惠分摊金额计算；否则无需计算分摊金额，直接用商品总金额减去满减优惠就行了；
        Boolean isUsedPointsMallFullReductionRule = false;
        if(dbPointsMallOrder.getFullReductionRuleId() != null){
            if(refundAmount.compareTo(dbPointsMallOrder.getLimitedPrice()) >= 0){
                //满足满减条件
                refundAmount = refundAmount.subtract(dbPointsMallOrder.getReducedPrice()).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
                isUsedPointsMallFullReductionRule = true;
                log.debug("\n\n退款商品金额满足满减");
                log.debug("\n\n减去满减后为 : " + refundAmount);
            }else{
                BigDecimal fullReductionRatio = dbPointsMallOrder.getReducedPrice().divide(dbPointsMallOrder.getLimitedPrice(), Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
                for (PointsMallOrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
                    BigDecimal shareDiscount = orderRefundGoods.getSubtotal().multiply(fullReductionRatio).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
                    log.debug("\n\n"+ orderRefundGoods.getGoodsName() +"商品分摊到的优惠是 : " + shareDiscount);
                    refundAmount = refundAmount.subtract(shareDiscount).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
                }
                log.debug("\n\n退款商品金额不满足满减条件");
                log.debug("\n\n减去满减后为 : " + refundAmount);
            }
        }else{
            log.debug("\n\n该订单未使用满减");
        }

        //3）退款商品的包装费

        //TODO(MARK)-积分商城订单免运费
        //4）只有全部商品 才能退发货费
        Boolean isRefundDeliveryFee = false;
        if(isAllAmountRefund){
            refundAmount = refundAmount.add(dbPointsMallOrder.getDeliveryFee());
            isRefundDeliveryFee = true;
        }

        log.debug("\n\n前端传递退款金额 : " + param.getOrderRefund().getRefundAmount());
        log.debug("\n\n后端核算退款金额 : " + refundAmount);
        if(refundAmount.compareTo(param.getOrderRefund().getRefundAmount()) != 0){
            throw new StoneCustomerException("退款金额计算错误，请稍后重试");
        }

        //判断退款金额不能 高于 实际支付金额
        if(refundAmount.compareTo(dbPointsMallOrder.getActualPrice()) > 0){
            log.error("\n\n退款金额 高于 实际支付金额");
            throw new StoneCustomerException("退款金额计算错误，请稍后重试");
        }
        //判断是全额退款/部分退款
        if(refundAmount.compareTo(dbPointsMallOrder.getActualPrice()) == 0){
            param.getOrderRefund().setRefundWay(Quantity.INT_1);
        }else{
            param.getOrderRefund().setRefundWay(Quantity.INT_2);
        }

        //修改订单记录状态
        PointsMallOrder updatePointsMallOrder = new PointsMallOrder();
        updatePointsMallOrder.setId(dbPointsMallOrder.getId());
        updatePointsMallOrder.setStatus(Quantity.INT_7);
        pointsMallOrderMapper.updateByPrimaryKeySelective(updatePointsMallOrder);

        //查询订单对应的订单商品详情数据
        List<PointsMallOrderDetail> orderDetailList = orderDetailService.selectByPointsMallOrderId(param.getId());

        //恢复订单对应的商品库存 (对应规格的库存怎么修改)
        for(PointsMallOrderDetail orderDetail : orderDetailList){
            int goodsId = orderDetail.getGoodsId();
            int number = orderDetail.getNumber();
            //增加商品库存
//            goodsService.increaseStock(goodsId, number);
        }

        //查找该订单对应的交易记录
        MemberTradeRecord memberTradeRecord = memberTradeRecordService.selectByPrimaryKey(dbPointsMallOrder.getTradeId());
        int refundAccount = memberTradeRecord.getPaymentMode() == Quantity.INT_1
                ? Quantity.INT_1 :
                (memberTradeRecord.getPaymentMode() == Quantity.INT_2 ? Quantity.INT_2
                        : (memberTradeRecord.getPaymentMode() == Quantity.INT_3 ? Quantity.INT_3
                        : (memberTradeRecord.getPaymentMode() == Quantity.INT_4 ? Quantity.INT_4 : Quantity.INT_0)));

        //添加退款记录
        PointsMallOrderRefund insertPointsMallOrderRefund = new PointsMallOrderRefund();
        insertPointsMallOrderRefund.setOrderId(dbPointsMallOrder.getId());
        insertPointsMallOrderRefund.setType(Quantity.INT_1);
        insertPointsMallOrderRefund.setRefundWay(param.getOrderRefund().getRefundWay());
        insertPointsMallOrderRefund.setRefundReason(param.getOrderRefund().getRefundReason());
        insertPointsMallOrderRefund.setRefundReasonDescription(param.getOrderRefund().getRefundReasonDescription());
        insertPointsMallOrderRefund.setEvidenceImages(param.getOrderRefund().getEvidenceImages());
        insertPointsMallOrderRefund.setRefundAmount(param.getOrderRefund().getRefundAmount());
        insertPointsMallOrderRefund.setRefundAccount(refundAccount);
        insertPointsMallOrderRefund.setStatus(Quantity.INT_4); //跳过第一步-退款申请已提交，直接到等待平台处理
        insertPointsMallOrderRefund.setGoodsTotalQuantity(goodsTotalQuantity);
        insertPointsMallOrderRefund.setGoodsTotalPrice(goodsAmount);
        /*insertPointsMallOrderRefund.setPackingCharges(packingCharges);*/
        /*insertPointsMallOrderRefund.setIsRefundDeliveryFee(isRefundDeliveryFee);*/
        insertPointsMallOrderRefund.setDeliveryFee(dbPointsMallOrder.getDeliveryFee());
        insertPointsMallOrderRefund.setIsUsedCoupons(isUsedPointsMallCoupons);
        insertPointsMallOrderRefund.setIsUsedFullReductionRule(isUsedPointsMallFullReductionRule);
        insertPointsMallOrderRefund.setCreateTime(new Date());
        insertPointsMallOrderRefund.setUpdateTime(new Date());
        orderRefundService.insertSelective(insertPointsMallOrderRefund);

        //添加订单部分退款-商品详情信息
        orderRefundGoodsList.forEach(orderRefundGoods -> {
            orderRefundGoods.setOrderRefundId(insertPointsMallOrderRefund.getId());
            orderRefundGoodsService.insertSelective(orderRefundGoods);
        });

        //添加退款流程
        String refundReasonText = getRefundReasonText(param.getOrderRefund().getRefundReason());
        if(StringUtils.isNotBlank(param.getOrderRefund().getRefundReasonDescription())){
            refundReasonText += " -- " + param.getOrderRefund().getRefundReasonDescription();
        }
        PointsMallOrderRefundProcess orderRefundProcess = new PointsMallOrderRefundProcess();
        orderRefundProcess.setOrderRefundId(insertPointsMallOrderRefund.getId());
        orderRefundProcess.setName("退款申请已提交");
        orderRefundProcess.setDescription("退款原因: ".concat(refundReasonText));
        orderRefundProcess.setCreateTime(new Date());
        orderRefundProcessService.insertSelective(orderRefundProcess);

        PointsMallOrderRefundProcess orderRefundProcess_second = new PointsMallOrderRefundProcess();
        orderRefundProcess_second.setOrderRefundId(insertPointsMallOrderRefund.getId());
        orderRefundProcess_second.setName("等待平台处理");
        orderRefundProcess_second.setDescription("");
        orderRefundProcess_second.setCreateTime(DateUtilsPlus.addSeconds(new Date(), 5));
        orderRefundProcessService.insertSelective(orderRefundProcess_second);


        //发送订单退款提醒
        //如果商品明细内容过长，则需要分多次发送公众号消息
        //字数：38*4=152 -> 112
        String goodsDescription = "";
        boolean isFirstSend = true; //是否为第一次发送

        for (PointsMallOrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
            //处理商品规格
            String specText = "";
            Map<String, Object> map = GsonUtils.toMap(orderRefundGoods.getSpecList());
            for(String key : map.keySet()){
                if(specText.isEmpty()){
                    specText += (String) map.get(key);
                }else{
                    specText += "/" + (String) map.get(key);
                }
            }

            String str = orderRefundGoods.getGoodsName() + "  " + specText + "  *  " + orderRefundGoods.getNumber() + "件\r\n";
            if(goodsDescription.length() + str.length() > 112){
                //内容超过112个字数，需要重新拼接商品明细内容，将消息发送掉
                goodsDescription = goodsDescription.substring(0, goodsDescription.length()-2);
                //给商家发送微信公众号消息
                String refundWayText = insertPointsMallOrderRefund.getRefundWay() == Quantity.INT_1 ? "全额退款" : "部分退款";
                String title = isFirstSend
                        ? "尊敬的商家，您有一个积分商城订单已被用户申请售后-仅退款，请及时处理 - 订单编号" + dbPointsMallOrder.getOrderNo()
                        : "订单退款商品明细服务通知 - 订单编号" + dbPointsMallOrder.getOrderNo();
                if(isFirstSend){
                    String addressStr = dbPointsMallOrder.getContactProvince() + dbPointsMallOrder.getContactCity() + dbPointsMallOrder.getContactArea() + dbPointsMallOrder.getContactStreet() + dbPointsMallOrder.getContactHouseNumber();
                    String orderDescription = ("订单编号" + dbPointsMallOrder.getOrderNo() + " - " + addressStr);
                    String contacts = dbPointsMallOrder.getContactRealname().concat(" - ").concat(dbPointsMallOrder.getContactPhone());
                    String cancelReason = "取消原因：" + getRefundReasonText(insertPointsMallOrderRefund.getRefundReason());
                    if(StringUtils.isNotBlank(insertPointsMallOrderRefund.getRefundReasonDescription())){
                        cancelReason = cancelReason + "-" + insertPointsMallOrderRefund.getRefundReasonDescription();
                    }
                    String remark = refundWayText + "，订单金额："+dbPointsMallOrder.getActualPrice()+"，退款金额："+insertPointsMallOrderRefund.getRefundAmount();
                    wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
                }else{
                    String addressStr = "";
                    String orderDescription = "";
                    String contacts = "";
                    String cancelReason = "";
                    String remark = "";
                    wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
                }
                goodsDescription = "";
                isFirstSend = false;
            }
            goodsDescription += str;

            //当前为列表末尾位置，需要将消息发送掉
            if(orderRefundGoodsList.indexOf(orderRefundGoods) == orderRefundGoodsList.size()-1){
                //内容超过112个字数，需要重新拼接商品明细内容，将消息发送掉
                goodsDescription = goodsDescription.substring(0, goodsDescription.length()-2);
                //给商家发送微信公众号消息
                String refundWayText = insertPointsMallOrderRefund.getRefundWay() == Quantity.INT_1 ? "全额退款" : "部分退款";
                String title = isFirstSend
                        ? "尊敬的商家，您有一个积分商城订单已被用户申请售后-仅退款，请及时处理 - 订单编号" + dbPointsMallOrder.getOrderNo()
                        : "订单退款商品明细服务通知 - 订单编号" + dbPointsMallOrder.getOrderNo();
                if(isFirstSend){
                    String addressStr = dbPointsMallOrder.getContactProvince() + dbPointsMallOrder.getContactCity() + dbPointsMallOrder.getContactArea() + dbPointsMallOrder.getContactStreet() + dbPointsMallOrder.getContactHouseNumber();
                    String orderDescription = ("订单编号" + dbPointsMallOrder.getOrderNo() + " - " + addressStr);
                    String contacts = dbPointsMallOrder.getContactRealname().concat(" - ").concat(dbPointsMallOrder.getContactPhone());
                    String cancelReason = "取消原因：" + getRefundReasonText(insertPointsMallOrderRefund.getRefundReason());
                    if(StringUtils.isNotBlank(insertPointsMallOrderRefund.getRefundReasonDescription())){
                        cancelReason = cancelReason + "-" + insertPointsMallOrderRefund.getRefundReasonDescription();
                    }
                    String remark = refundWayText + "，订单金额："+dbPointsMallOrder.getActualPrice()+"，退款金额："+insertPointsMallOrderRefund.getRefundAmount();
                    wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
                }else{
                    String addressStr = "";
                    String orderDescription = "";
                    String contacts = "";
                    String cancelReason = "";
                    String remark = "";
                    wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
                }
                goodsDescription = "";
                isFirstSend = false;
            }
        }

        //TODO(MARK)-如果平台48小时内未处理，则自动进行退款
        //进行订单自动退款操作
        boolean isRefundSuccess = false;
        if(dbPointsMallOrder.getPaymentMode().equals(Quantity.INT_1)){
            //微信支付
            isRefundSuccess = pointsMallWxPayService.refund(dbPointsMallOrder.getOrderNo(), dbPointsMallOrder.getActualPrice(), insertPointsMallOrderRefund.getRefundAmount());

        }else if(dbPointsMallOrder.getPaymentMode().equals(Quantity.INT_2)){
            //余额支付
            Member dbMember = memberService.selectByPrimaryKey(loginMember.getId());
            //增加用户的余额
            Member updateMember = new Member();
            updateMember.setId(dbMember.getId());
            updateMember.setBalance(dbMember.getBalance().add(insertPointsMallOrderRefund.getRefundAmount()));
            updateMember.setTotalConsumeBalance(dbMember.getTotalConsumeBalance().subtract(insertPointsMallOrderRefund.getRefundAmount()));
            memberService.updateByPrimaryKeySelective(updateMember);
            //添加账单记录
            MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
            memberBillingRecord.setMemberId(dbMember.getId());
            memberBillingRecord.setType(MemberBillingRecord.TYPE_ORDER_CANCEL_RETURN_BALANCE);
            memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
            memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_BALANCE);
            memberBillingRecord.setNumber(insertPointsMallOrderRefund.getRefundAmount());
            memberBillingRecord.setMessage("一分钟内取消订单-余额退回");
            memberBillingRecord.setCreateTime(new Date());
            memberBillingRecordService.insertSelective(memberBillingRecord);

            //退款成功的操作
            updateRefundStatus(dbPointsMallOrder.getOrderNo());

            isRefundSuccess = true;
        }
        if(isRefundSuccess){
            PointsMallOrderRefund updatePointsMallOrderRefund = new PointsMallOrderRefund();
            updatePointsMallOrderRefund.setId(insertPointsMallOrderRefund.getId());
            updatePointsMallOrderRefund.setStatus(Quantity.INT_7);
            updatePointsMallOrderRefund.setUpdateTime(new Date());
            orderRefundService.updateByPrimaryKeySelective(updatePointsMallOrderRefund);

            //添加退款流程
            orderRefundProcess_second = new PointsMallOrderRefundProcess();
            orderRefundProcess_second.setOrderRefundId(insertPointsMallOrderRefund.getId());
            orderRefundProcess_second.setName("退款成功");
            orderRefundProcess_second.setCreateTime(new Date());
            orderRefundProcessService.insertSelective(orderRefundProcess_second);
        }

    }

    @Override
    public void onlyRefund(PointsMallOrderParam param) {

        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //手动将JSON字符串转化为对象
        List<PointsMallOrderRefundGoods> orderRefundGoodsList = StringUtils.isNotBlank(param.getOrderRefundGoodsListStr()) ? GsonUtils.toList(param.getOrderRefundGoodsListStr(), PointsMallOrderRefundGoods.class) : null;
        log.debug("\n\nparam.getOrderRefundGoodsListStr() : " + param.getOrderRefundGoodsListStr());
        if(orderRefundGoodsList != null){
            for (PointsMallOrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
                log.debug("\n\norderDetailId : " + orderRefundGoods.getOrderDetailId() + " -- number : " + orderRefundGoods.getNumber());
            }
        }

        PointsMallOrder dbPointsMallOrder = pointsMallOrderMapper.selectById(param.getOrderRefund().getOrderId());
        if(dbPointsMallOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }
        if(!dbPointsMallOrder.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("该订单不是你的，不允许取消");
        }

        //订单已发货，距离支付时间未超过20天可以申请售后
        PointsMallOrderParam orderParam = new PointsMallOrderParam();
        orderParam.setId(dbPointsMallOrder.getId());
        boolean isAllowApplyRefund = pointsMallOrderMapper.getIsAllowApplyAfterSales(orderParam);
        if(!isAllowApplyRefund){
            throw new StoneCustomerException("该订单不允许申请退款");
        }

        if(orderRefundGoodsList==null || orderRefundGoodsList.isEmpty()){
            throw new StoneCustomerException("订单退款-商品参数丢失");
        }

        //TODO-部分退款/全额退款不退包装费？发货费肯定是不退的
        //TODO-全额退款是否退还发货费？ -- 暂时退还
        //TODO-没有全额退款 和 部分退款的说法
        //TODO-目前一律不退还包装费与发货费，商品本身的价格
        //TODO-还要考虑用在该订单上的满减规则、优惠券
        //校验退款金额是否正确
        BigDecimal goodsAmount = BigDecimal.ZERO;

        Boolean isUsedPointsMallCoupons = false;

        /*BigDecimal packingCharges = BigDecimal.ZERO; //包装费*/

        Boolean isAllAmountRefund = false;

        int goodsTotalQuantity = 0;

        for (PointsMallOrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
            PointsMallOrderDetail orderDetail = orderDetailService.selectByPrimaryKey(orderRefundGoods.getOrderDetailId());
            if(orderDetail == null){
                throw new StoneCustomerException("数据异常，请稍后重试");
            }

            log.debug("\n\norderRefundGoods : " + orderRefundGoods + " -- number : " + orderRefundGoods.getNumber());
            log.debug("\n\norderDetail : " + orderDetail + " -- number : " + orderDetail.getNumber());


            if(orderDetail.getIsUsedCoupons()){
                isUsedPointsMallCoupons = true;
            }

            //校验退款数量是否正确
            if(orderRefundGoods.getNumber().compareTo(0)<=0 || orderRefundGoods.getNumber().compareTo(orderDetail.getNumber()) > 0){
                throw new StoneCustomerException(orderDetail.getGoodsName() + "的退款数量错误");
            }
            //累加退还金额
            BigDecimal subtotal = orderDetail.getPrice().multiply(BigDecimal.valueOf(orderRefundGoods.getNumber())).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
            goodsAmount = goodsAmount.add(subtotal);

            //累计包装费
            /*BigDecimal totalPackingCharges = orderDetail.getPackingCharges().multiply(BigDecimal.valueOf(orderRefundGoods.getNumber())).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
            packingCharges = packingCharges.add(totalPackingCharges);*/

            //累计退款商品总数量
            goodsTotalQuantity += orderRefundGoods.getNumber();

            //回写一些商品的基本信息，便于后续添加功能
            orderRefundGoods.setGoodsId(orderDetail.getGoodsId());
            orderRefundGoods.setGoodsName(orderDetail.getGoodsName());
            orderRefundGoods.setMainImage(orderDetail.getMainImage());
            orderRefundGoods.setSpecList(orderDetail.getSpecList());
            orderRefundGoods.setPrice(orderDetail.getPrice());
            orderRefundGoods.setSubtotal(subtotal);
        }

        if(goodsTotalQuantity == dbPointsMallOrder.getGoodsTotalQuantity()){
            isAllAmountRefund = true;
        }

        log.debug("\n\n退款商品总金额 : " + goodsAmount);
        /*log.debug("\n\n退款商品总包装费 : " + packingCharges);*/

        /*BigDecimal refundAmount = goodsAmount.add(packingCharges);*/
        BigDecimal refundAmount = goodsAmount;


        //1）优惠券 只退 用了该优惠券的商品，如果使用了优惠券的商品购买了3件，那么退第1件的时候，就按照使用了优惠券的商品计算
        if(isUsedPointsMallCoupons){
            refundAmount = refundAmount.subtract(dbPointsMallOrder.getCouponsDiscountPrice()).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
        }
        log.debug("\n\n减去优惠券的折扣后为 : " + refundAmount);

        //2）退款金额+包装费-优惠券的优惠金额 凑足了满减，给他退减去的金额
        /*Boolean isUsedPointsMallFullReductionRule = false;
        if(dbPointsMallOrder.getFullReductionRuleId() !=null ){
            if(refundAmount.compareTo(dbPointsMallOrder.getLimitedPrice()) >= 0){
                refundAmount = refundAmount.subtract(dbPointsMallOrder.getReducedPrice()).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
                isUsedPointsMallFullReductionRule = true;
                log.debug("\n\n减去满减后为 : " + refundAmount);
            }else{
                log.debug("\n\n退款商品金额不满足满减");
            }
        }else{
            log.debug("\n\n该订单未使用满减");
        }*/

        //TODO(MARK)：用商品总价格+总包装费来判断是否满足满减规则；如果不满足，在计算商品分摊金额的时候只算商品本身价格，不算包装费；
        //2）如果商品总金额不满足满减条件，则需要对商品进行满减优惠分摊金额计算；否则无需计算分摊金额，直接用商品总金额减去满减优惠就行了；
        Boolean isUsedPointsMallFullReductionRule = false;
        if(dbPointsMallOrder.getFullReductionRuleId() != null){
            if(refundAmount.compareTo(dbPointsMallOrder.getLimitedPrice()) >= 0){
                //满足满减条件
                refundAmount = refundAmount.subtract(dbPointsMallOrder.getReducedPrice()).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
                isUsedPointsMallFullReductionRule = true;
                log.debug("\n\n退款商品金额满足满减");
                log.debug("\n\n减去满减后为 : " + refundAmount);
            }else{
                BigDecimal fullReductionRatio = dbPointsMallOrder.getReducedPrice().divide(dbPointsMallOrder.getLimitedPrice(), Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
                for (PointsMallOrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
                    BigDecimal shareDiscount = orderRefundGoods.getSubtotal().multiply(fullReductionRatio).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
                    log.debug("\n\n"+ orderRefundGoods.getGoodsName() +"商品分摊到的优惠是 : " + shareDiscount);
                    refundAmount = refundAmount.subtract(shareDiscount).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
                }
                log.debug("\n\n退款商品金额不满足满减条件");
                log.debug("\n\n减去满减后为 : " + refundAmount);
            }
        }else{
            log.debug("\n\n该订单未使用满减");
        }

        //3）退款商品的包装费

        //TODO(MARK)-积分商城订单免运费
        //4）只有全部商品 才能退发货费
        Boolean isRefundDeliveryFee = false;
        if(isAllAmountRefund){
            refundAmount = refundAmount.add(dbPointsMallOrder.getDeliveryFee());
            isRefundDeliveryFee = true;
        }

        log.debug("\n\n前端传递退款金额 : " + param.getOrderRefund().getRefundAmount());
        log.debug("\n\n后端核算退款金额 : " + refundAmount);
        if(refundAmount.compareTo(param.getOrderRefund().getRefundAmount()) != 0){
            throw new StoneCustomerException("退款金额计算错误，请稍后重试");
        }

        //判断退款金额不能 高于 实际支付金额
        if(refundAmount.compareTo(dbPointsMallOrder.getActualPrice()) > 0){
            log.error("\n\n退款金额 高于 实际支付金额");
            throw new StoneCustomerException("退款金额计算错误，请稍后重试");
        }
        //判断是全额退款/部分退款
        if(refundAmount.compareTo(dbPointsMallOrder.getActualPrice()) == 0){
            param.getOrderRefund().setRefundWay(Quantity.INT_1);
        }else{
            param.getOrderRefund().setRefundWay(Quantity.INT_2);
        }

        //修改订单记录状态
        PointsMallOrder updatePointsMallOrder = new PointsMallOrder();
        updatePointsMallOrder.setId(dbPointsMallOrder.getId());
        updatePointsMallOrder.setStatus(Quantity.INT_7);
        pointsMallOrderMapper.updateByPrimaryKeySelective(updatePointsMallOrder);

        //查询订单对应的订单商品详情数据
        List<PointsMallOrderDetail> orderDetailList = orderDetailService.selectByPointsMallOrderId(param.getId());

        //恢复订单对应的商品库存 (对应规格的库存怎么修改)
        for(PointsMallOrderDetail orderDetail : orderDetailList){
            int goodsId = orderDetail.getGoodsId();
            int number = orderDetail.getNumber();
            //增加商品库存
//            goodsService.increaseStock(goodsId, number);
        }

        //查找该订单对应的交易记录
        MemberTradeRecord memberTradeRecord = memberTradeRecordService.selectByPrimaryKey(dbPointsMallOrder.getTradeId());
        int refundAccount = memberTradeRecord.getPaymentMode() == Quantity.INT_1
                ? Quantity.INT_1 :
                (memberTradeRecord.getPaymentMode() == Quantity.INT_2 ? Quantity.INT_2
                        : (memberTradeRecord.getPaymentMode() == Quantity.INT_3 ? Quantity.INT_3
                        : (memberTradeRecord.getPaymentMode() == Quantity.INT_4 ? Quantity.INT_4 : Quantity.INT_0)));

        //添加退款记录
        PointsMallOrderRefund insertPointsMallOrderRefund = new PointsMallOrderRefund();
        insertPointsMallOrderRefund.setOrderId(dbPointsMallOrder.getId());
        insertPointsMallOrderRefund.setType(Quantity.INT_2);
        insertPointsMallOrderRefund.setRefundWay(param.getOrderRefund().getRefundWay());
        insertPointsMallOrderRefund.setRefundReason(param.getOrderRefund().getRefundReason());
        insertPointsMallOrderRefund.setRefundReasonDescription(param.getOrderRefund().getRefundReasonDescription());
        insertPointsMallOrderRefund.setEvidenceImages(param.getOrderRefund().getEvidenceImages());
        insertPointsMallOrderRefund.setRefundAmount(param.getOrderRefund().getRefundAmount());
        insertPointsMallOrderRefund.setRefundAccount(refundAccount);
        insertPointsMallOrderRefund.setStatus(Quantity.INT_4); //跳过第一步-退款申请已提交，直接到等待平台处理
        insertPointsMallOrderRefund.setGoodsTotalQuantity(goodsTotalQuantity);
        insertPointsMallOrderRefund.setGoodsTotalPrice(goodsAmount);
        /*insertPointsMallOrderRefund.setPackingCharges(packingCharges);*/
        /*insertPointsMallOrderRefund.setIsRefundDeliveryFee(isRefundDeliveryFee);*/
        insertPointsMallOrderRefund.setDeliveryFee(dbPointsMallOrder.getDeliveryFee());
        insertPointsMallOrderRefund.setIsUsedCoupons(isUsedPointsMallCoupons);
        insertPointsMallOrderRefund.setIsUsedFullReductionRule(isUsedPointsMallFullReductionRule);
        insertPointsMallOrderRefund.setCreateTime(new Date());
        insertPointsMallOrderRefund.setUpdateTime(new Date());
        orderRefundService.insertSelective(insertPointsMallOrderRefund);

        //添加订单部分退款-商品详情信息
        orderRefundGoodsList.forEach(orderRefundGoods -> {
            orderRefundGoods.setOrderRefundId(insertPointsMallOrderRefund.getId());
            orderRefundGoodsService.insertSelective(orderRefundGoods);
        });

        //添加退款流程
        String refundReasonText = getRefundReasonText(param.getOrderRefund().getRefundReason());
        if(StringUtils.isNotBlank(param.getOrderRefund().getRefundReasonDescription())){
            refundReasonText += " -- " + param.getOrderRefund().getRefundReasonDescription();
        }
        PointsMallOrderRefundProcess orderRefundProcess = new PointsMallOrderRefundProcess();
        orderRefundProcess.setOrderRefundId(insertPointsMallOrderRefund.getId());
        orderRefundProcess.setName("退款申请已提交");
        orderRefundProcess.setDescription("退款原因: ".concat(refundReasonText));
        orderRefundProcess.setCreateTime(new Date());
        orderRefundProcessService.insertSelective(orderRefundProcess);

        PointsMallOrderRefundProcess orderRefundProcess_second = new PointsMallOrderRefundProcess();
        orderRefundProcess_second.setOrderRefundId(insertPointsMallOrderRefund.getId());
        orderRefundProcess_second.setName("等待平台处理");
        orderRefundProcess_second.setDescription("");
        orderRefundProcess_second.setCreateTime(DateUtilsPlus.addSeconds(new Date(), 5));
        orderRefundProcessService.insertSelective(orderRefundProcess_second);


        //发送订单退款提醒
        //如果商品明细内容过长，则需要分多次发送公众号消息
        //字数：38*4=152 -> 112
        String goodsDescription = "";
        boolean isFirstSend = true; //是否为第一次发送

        for (PointsMallOrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
            //处理商品规格
            String specText = "";
            Map<String, Object> map = GsonUtils.toMap(orderRefundGoods.getSpecList());
            for(String key : map.keySet()){
                if(specText.isEmpty()){
                    specText += (String) map.get(key);
                }else{
                    specText += "/" + (String) map.get(key);
                }
            }

            String str = orderRefundGoods.getGoodsName() + "  " + specText + "  *  " + orderRefundGoods.getNumber() + "件\r\n";
            if(goodsDescription.length() + str.length() > 112){
                //内容超过112个字数，需要重新拼接商品明细内容，将消息发送掉
                goodsDescription = goodsDescription.substring(0, goodsDescription.length()-2);
                //给商家发送微信公众号消息
                String refundWayText = insertPointsMallOrderRefund.getRefundWay() == Quantity.INT_1 ? "全额退款" : "部分退款";
                String title = isFirstSend
                        ? "尊敬的商家，您有一个积分商城订单已被用户申请售后-仅退款，请及时处理 - 订单编号" + dbPointsMallOrder.getOrderNo()
                        : "订单退款商品明细服务通知 - 订单编号" + dbPointsMallOrder.getOrderNo();
                if(isFirstSend){
                    String addressStr = dbPointsMallOrder.getContactProvince() + dbPointsMallOrder.getContactCity() + dbPointsMallOrder.getContactArea() + dbPointsMallOrder.getContactStreet() + dbPointsMallOrder.getContactHouseNumber();
                    String orderDescription = ("订单编号" + dbPointsMallOrder.getOrderNo() + " - " + addressStr);
                    String contacts = dbPointsMallOrder.getContactRealname().concat(" - ").concat(dbPointsMallOrder.getContactPhone());
                    String cancelReason = "取消原因：" + getRefundReasonText(insertPointsMallOrderRefund.getRefundReason());
                    if(StringUtils.isNotBlank(insertPointsMallOrderRefund.getRefundReasonDescription())){
                        cancelReason = cancelReason + "-" + insertPointsMallOrderRefund.getRefundReasonDescription();
                    }
                    String remark = refundWayText + "，订单金额："+dbPointsMallOrder.getActualPrice()+"，退款金额："+insertPointsMallOrderRefund.getRefundAmount();
                    wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
                }else{
                    String addressStr = "";
                    String orderDescription = "";
                    String contacts = "";
                    String cancelReason = "";
                    String remark = "";
                    wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
                }
                goodsDescription = "";
                isFirstSend = false;
            }
            goodsDescription += str;

            //当前为列表末尾位置，需要将消息发送掉
            if(orderRefundGoodsList.indexOf(orderRefundGoods) == orderRefundGoodsList.size()-1){
                //内容超过112个字数，需要重新拼接商品明细内容，将消息发送掉
                goodsDescription = goodsDescription.substring(0, goodsDescription.length()-2);
                //给商家发送微信公众号消息
                String refundWayText = insertPointsMallOrderRefund.getRefundWay() == Quantity.INT_1 ? "全额退款" : "部分退款";
                String title = isFirstSend
                        ? "尊敬的商家，您有一个积分商城订单已被用户申请售后-仅退款，请及时处理 - 订单编号" + dbPointsMallOrder.getOrderNo()
                        : "订单退款商品明细服务通知 - 订单编号" + dbPointsMallOrder.getOrderNo();
                if(isFirstSend){
                    String addressStr = dbPointsMallOrder.getContactProvince() + dbPointsMallOrder.getContactCity() + dbPointsMallOrder.getContactArea() + dbPointsMallOrder.getContactStreet() + dbPointsMallOrder.getContactHouseNumber();
                    String orderDescription = ("订单编号" + dbPointsMallOrder.getOrderNo() + " - " + addressStr);
                    String contacts = dbPointsMallOrder.getContactRealname().concat(" - ").concat(dbPointsMallOrder.getContactPhone());
                    String cancelReason = "取消原因：" + getRefundReasonText(insertPointsMallOrderRefund.getRefundReason());
                    if(StringUtils.isNotBlank(insertPointsMallOrderRefund.getRefundReasonDescription())){
                        cancelReason = cancelReason + "-" + insertPointsMallOrderRefund.getRefundReasonDescription();
                    }
                    String remark = refundWayText + "，订单金额："+dbPointsMallOrder.getActualPrice()+"，退款金额："+insertPointsMallOrderRefund.getRefundAmount();
                    wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
                }else{
                    String addressStr = "";
                    String orderDescription = "";
                    String contacts = "";
                    String cancelReason = "";
                    String remark = "";
                    wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
                }
                goodsDescription = "";
                isFirstSend = false;
            }
        }

    }

    @Override
    public void returnGoodsWithRefund(PointsMallOrderParam param) {

        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //手动将JSON字符串转化为对象
        List<PointsMallOrderRefundGoods> orderRefundGoodsList = StringUtils.isNotBlank(param.getOrderRefundGoodsListStr()) ? GsonUtils.toList(param.getOrderRefundGoodsListStr(), PointsMallOrderRefundGoods.class) : null;
        log.debug("\n\nparam.getOrderRefundGoodsListStr() : " + param.getOrderRefundGoodsListStr());
        if(orderRefundGoodsList != null){
            for (PointsMallOrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
                log.debug("\n\norderDetailId : " + orderRefundGoods.getOrderDetailId() + " -- number : " + orderRefundGoods.getNumber());
            }
        }

        PointsMallOrder dbPointsMallOrder = pointsMallOrderMapper.selectById(param.getOrderRefund().getOrderId());
        if(dbPointsMallOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }
        if(!dbPointsMallOrder.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("该订单不是你的，不允许取消");
        }

        //订单已发货，距离支付时间未超过20天可以申请售后
        PointsMallOrderParam orderParam = new PointsMallOrderParam();
        orderParam.setId(dbPointsMallOrder.getId());
        boolean isAllowApplyRefund = pointsMallOrderMapper.getIsAllowApplyAfterSales(orderParam);
        if(!isAllowApplyRefund){
            throw new StoneCustomerException("该订单不允许申请退款");
        }

        if(orderRefundGoodsList==null || orderRefundGoodsList.isEmpty()){
            throw new StoneCustomerException("订单退款-商品参数丢失");
        }

        //TODO-部分退款/全额退款不退包装费？发货费肯定是不退的
        //TODO-全额退款是否退还发货费？ -- 暂时退还
        //TODO-没有全额退款 和 部分退款的说法
        //TODO-目前一律不退还包装费与发货费，商品本身的价格
        //TODO-还要考虑用在该订单上的满减规则、优惠券
        //校验退款金额是否正确
        BigDecimal goodsAmount = BigDecimal.ZERO;

        Boolean isUsedPointsMallCoupons = false;

        /*BigDecimal packingCharges = BigDecimal.ZERO; //包装费*/

        Boolean isAllAmountRefund = false;

        int goodsTotalQuantity = 0;

        for (PointsMallOrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
            PointsMallOrderDetail orderDetail = orderDetailService.selectByPrimaryKey(orderRefundGoods.getOrderDetailId());
            if(orderDetail == null){
                throw new StoneCustomerException("数据异常，请稍后重试");
            }

            log.debug("\n\norderRefundGoods : " + orderRefundGoods + " -- number : " + orderRefundGoods.getNumber());
            log.debug("\n\norderDetail : " + orderDetail + " -- number : " + orderDetail.getNumber());


            if(orderDetail.getIsUsedCoupons()){
                isUsedPointsMallCoupons = true;
            }

            //校验退款数量是否正确
            if(orderRefundGoods.getNumber().compareTo(0)<=0 || orderRefundGoods.getNumber().compareTo(orderDetail.getNumber()) > 0){
                throw new StoneCustomerException(orderDetail.getGoodsName() + "的退款数量错误");
            }
            //累加退还金额
            BigDecimal subtotal = orderDetail.getPrice().multiply(BigDecimal.valueOf(orderRefundGoods.getNumber())).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
            goodsAmount = goodsAmount.add(subtotal);

            //累计包装费
            /*BigDecimal totalPackingCharges = orderDetail.getPackingCharges().multiply(BigDecimal.valueOf(orderRefundGoods.getNumber())).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
            packingCharges = packingCharges.add(totalPackingCharges);*/

            //累计退款商品总数量
            goodsTotalQuantity += orderRefundGoods.getNumber();

            //回写一些商品的基本信息，便于后续添加功能
            orderRefundGoods.setGoodsId(orderDetail.getGoodsId());
            orderRefundGoods.setGoodsName(orderDetail.getGoodsName());
            orderRefundGoods.setMainImage(orderDetail.getMainImage());
            orderRefundGoods.setSpecList(orderDetail.getSpecList());
            orderRefundGoods.setPrice(orderDetail.getPrice());
            orderRefundGoods.setSubtotal(subtotal);
        }

        if(goodsTotalQuantity == dbPointsMallOrder.getGoodsTotalQuantity()){
            isAllAmountRefund = true;
        }

        log.debug("\n\n退款商品总金额 : " + goodsAmount);
        /*log.debug("\n\n退款商品总包装费 : " + packingCharges);*/

        /*BigDecimal refundAmount = goodsAmount.add(packingCharges);*/
        BigDecimal refundAmount = goodsAmount;


        //1）优惠券 只退 用了该优惠券的商品，如果使用了优惠券的商品购买了3件，那么退第1件的时候，就按照使用了优惠券的商品计算
        if(isUsedPointsMallCoupons){
            refundAmount = refundAmount.subtract(dbPointsMallOrder.getCouponsDiscountPrice()).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
        }
        log.debug("\n\n减去优惠券的折扣后为 : " + refundAmount);

        //2）退款金额+包装费-优惠券的优惠金额 凑足了满减，给他退减去的金额
        /*Boolean isUsedPointsMallFullReductionRule = false;
        if(dbPointsMallOrder.getFullReductionRuleId() !=null ){
            if(refundAmount.compareTo(dbPointsMallOrder.getLimitedPrice()) >= 0){
                refundAmount = refundAmount.subtract(dbPointsMallOrder.getReducedPrice()).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
                isUsedPointsMallFullReductionRule = true;
                log.debug("\n\n减去满减后为 : " + refundAmount);
            }else{
                log.debug("\n\n退款商品金额不满足满减");
            }
        }else{
            log.debug("\n\n该订单未使用满减");
        }*/

        //TODO(MARK)：用商品总价格+总包装费来判断是否满足满减规则；如果不满足，在计算商品分摊金额的时候只算商品本身价格，不算包装费；
        //2）如果商品总金额不满足满减条件，则需要对商品进行满减优惠分摊金额计算；否则无需计算分摊金额，直接用商品总金额减去满减优惠就行了；
        Boolean isUsedPointsMallFullReductionRule = false;
        if(dbPointsMallOrder.getFullReductionRuleId() != null){
            if(refundAmount.compareTo(dbPointsMallOrder.getLimitedPrice()) >= 0){
                //满足满减条件
                refundAmount = refundAmount.subtract(dbPointsMallOrder.getReducedPrice()).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
                isUsedPointsMallFullReductionRule = true;
                log.debug("\n\n退款商品金额满足满减");
                log.debug("\n\n减去满减后为 : " + refundAmount);
            }else{
                BigDecimal fullReductionRatio = dbPointsMallOrder.getReducedPrice().divide(dbPointsMallOrder.getLimitedPrice(), Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
                for (PointsMallOrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
                    BigDecimal shareDiscount = orderRefundGoods.getSubtotal().multiply(fullReductionRatio).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
                    log.debug("\n\n"+ orderRefundGoods.getGoodsName() +"商品分摊到的优惠是 : " + shareDiscount);
                    refundAmount = refundAmount.subtract(shareDiscount).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
                }
                log.debug("\n\n退款商品金额不满足满减条件");
                log.debug("\n\n减去满减后为 : " + refundAmount);
            }
        }else{
            log.debug("\n\n该订单未使用满减");
        }

        //3）退款商品的包装费

        //TODO(MARK)-积分商城订单免运费
        //4）只有全部商品 才能退发货费
        Boolean isRefundDeliveryFee = false;
        if(isAllAmountRefund){
            refundAmount = refundAmount.add(dbPointsMallOrder.getDeliveryFee());
            isRefundDeliveryFee = true;
        }

        log.debug("\n\n前端传递退款金额 : " + param.getOrderRefund().getRefundAmount());
        log.debug("\n\n后端核算退款金额 : " + refundAmount);
        if(refundAmount.compareTo(param.getOrderRefund().getRefundAmount()) != 0){
            throw new StoneCustomerException("退款金额计算错误，请稍后重试");
        }

        //判断退款金额不能 高于 实际支付金额
        if(refundAmount.compareTo(dbPointsMallOrder.getActualPrice()) > 0){
            log.error("\n\n退款金额 高于 实际支付金额");
            throw new StoneCustomerException("退款金额计算错误，请稍后重试");
        }
        //判断是全额退款/部分退款
        if(refundAmount.compareTo(dbPointsMallOrder.getActualPrice()) == 0){
            param.getOrderRefund().setRefundWay(Quantity.INT_1);
        }else{
            param.getOrderRefund().setRefundWay(Quantity.INT_2);
        }

        //修改订单记录状态
        PointsMallOrder updatePointsMallOrder = new PointsMallOrder();
        updatePointsMallOrder.setId(dbPointsMallOrder.getId());
        updatePointsMallOrder.setStatus(Quantity.INT_7);
        pointsMallOrderMapper.updateByPrimaryKeySelective(updatePointsMallOrder);

        //查询订单对应的订单商品详情数据
        List<PointsMallOrderDetail> orderDetailList = orderDetailService.selectByPointsMallOrderId(param.getId());

        //恢复订单对应的商品库存 (对应规格的库存怎么修改)
        for(PointsMallOrderDetail orderDetail : orderDetailList){
            int goodsId = orderDetail.getGoodsId();
            int number = orderDetail.getNumber();
            //增加商品库存
//            goodsService.increaseStock(goodsId, number);
        }

        //查找该订单对应的交易记录
        MemberTradeRecord memberTradeRecord = memberTradeRecordService.selectByPrimaryKey(dbPointsMallOrder.getTradeId());
        int refundAccount = memberTradeRecord.getPaymentMode() == Quantity.INT_1
                ? Quantity.INT_1 :
                (memberTradeRecord.getPaymentMode() == Quantity.INT_2 ? Quantity.INT_2
                        : (memberTradeRecord.getPaymentMode() == Quantity.INT_3 ? Quantity.INT_3
                        : (memberTradeRecord.getPaymentMode() == Quantity.INT_4 ? Quantity.INT_4 : Quantity.INT_0)));

        //添加退款记录
        PointsMallOrderRefund insertPointsMallOrderRefund = new PointsMallOrderRefund();
        insertPointsMallOrderRefund.setOrderId(dbPointsMallOrder.getId());
        insertPointsMallOrderRefund.setType(Quantity.INT_3);
        insertPointsMallOrderRefund.setRefundWay(param.getOrderRefund().getRefundWay());
        insertPointsMallOrderRefund.setRefundReason(param.getOrderRefund().getRefundReason());
        insertPointsMallOrderRefund.setRefundReasonDescription(param.getOrderRefund().getRefundReasonDescription());
        insertPointsMallOrderRefund.setEvidenceImages(param.getOrderRefund().getEvidenceImages());
        insertPointsMallOrderRefund.setRefundAmount(param.getOrderRefund().getRefundAmount());
        insertPointsMallOrderRefund.setRefundAccount(refundAccount);
        insertPointsMallOrderRefund.setStatus(Quantity.INT_4); //跳过第一步-退款申请已提交，直接到等待平台处理
        insertPointsMallOrderRefund.setGoodsTotalQuantity(goodsTotalQuantity);
        insertPointsMallOrderRefund.setGoodsTotalPrice(goodsAmount);
        /*insertPointsMallOrderRefund.setPackingCharges(packingCharges);*/
        /*insertPointsMallOrderRefund.setIsRefundDeliveryFee(isRefundDeliveryFee);*/
        insertPointsMallOrderRefund.setDeliveryFee(dbPointsMallOrder.getDeliveryFee());
        insertPointsMallOrderRefund.setIsUsedCoupons(isUsedPointsMallCoupons);
        insertPointsMallOrderRefund.setIsUsedFullReductionRule(isUsedPointsMallFullReductionRule);
        insertPointsMallOrderRefund.setCreateTime(new Date());
        insertPointsMallOrderRefund.setUpdateTime(new Date());
        orderRefundService.insertSelective(insertPointsMallOrderRefund);

        //添加订单部分退款-商品详情信息
        orderRefundGoodsList.forEach(orderRefundGoods -> {
            orderRefundGoods.setOrderRefundId(insertPointsMallOrderRefund.getId());
            orderRefundGoodsService.insertSelective(orderRefundGoods);
        });

        //添加退款流程
        String refundReasonText = getRefundReasonText(param.getOrderRefund().getRefundReason());
        if(StringUtils.isNotBlank(param.getOrderRefund().getRefundReasonDescription())){
            refundReasonText += " -- " + param.getOrderRefund().getRefundReasonDescription();
        }
        PointsMallOrderRefundProcess orderRefundProcess = new PointsMallOrderRefundProcess();
        orderRefundProcess.setOrderRefundId(insertPointsMallOrderRefund.getId());
        orderRefundProcess.setName("退款申请已提交");
        orderRefundProcess.setDescription("退款原因: ".concat(refundReasonText));
        orderRefundProcess.setCreateTime(new Date());
        orderRefundProcessService.insertSelective(orderRefundProcess);

        PointsMallOrderRefundProcess orderRefundProcess_second = new PointsMallOrderRefundProcess();
        orderRefundProcess_second.setOrderRefundId(insertPointsMallOrderRefund.getId());
        orderRefundProcess_second.setName("等待平台处理");
        orderRefundProcess_second.setDescription("");
        orderRefundProcess_second.setCreateTime(DateUtilsPlus.addSeconds(new Date(), 5));
        orderRefundProcessService.insertSelective(orderRefundProcess_second);


        //发送订单退款提醒
        //如果商品明细内容过长，则需要分多次发送公众号消息
        //字数：38*4=152 -> 112
        String goodsDescription = "";
        boolean isFirstSend = true; //是否为第一次发送

        for (PointsMallOrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
            //处理商品规格
            String specText = "";
            Map<String, Object> map = GsonUtils.toMap(orderRefundGoods.getSpecList());
            for(String key : map.keySet()){
                if(specText.isEmpty()){
                    specText += (String) map.get(key);
                }else{
                    specText += "/" + (String) map.get(key);
                }
            }

            String str = orderRefundGoods.getGoodsName() + "  " + specText + "  *  " + orderRefundGoods.getNumber() + "件\r\n";
            if(goodsDescription.length() + str.length() > 112){
                //内容超过112个字数，需要重新拼接商品明细内容，将消息发送掉
                goodsDescription = goodsDescription.substring(0, goodsDescription.length()-2);
                //给商家发送微信公众号消息
                String refundWayText = insertPointsMallOrderRefund.getRefundWay() == Quantity.INT_1 ? "全额退款" : "部分退款";
                String title = isFirstSend
                        ? "尊敬的商家，您有一个积分商城订单已被用户申请售后-退货退款，请及时处理 - 订单编号" + dbPointsMallOrder.getOrderNo()
                        : "订单退款商品明细服务通知 - 订单编号" + dbPointsMallOrder.getOrderNo();
                if(isFirstSend){
                    String addressStr = dbPointsMallOrder.getContactProvince() + dbPointsMallOrder.getContactCity() + dbPointsMallOrder.getContactArea() + dbPointsMallOrder.getContactStreet() + dbPointsMallOrder.getContactHouseNumber();
                    String orderDescription = ("订单编号" + dbPointsMallOrder.getOrderNo() + " - " + addressStr);
                    String contacts = dbPointsMallOrder.getContactRealname().concat(" - ").concat(dbPointsMallOrder.getContactPhone());
                    String cancelReason = "取消原因：" + getRefundReasonText(insertPointsMallOrderRefund.getRefundReason());
                    if(StringUtils.isNotBlank(insertPointsMallOrderRefund.getRefundReasonDescription())){
                        cancelReason = cancelReason + "-" + insertPointsMallOrderRefund.getRefundReasonDescription();
                    }
                    String remark = refundWayText + "，订单金额："+dbPointsMallOrder.getActualPrice()+"，退款金额："+insertPointsMallOrderRefund.getRefundAmount();
                    wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
                }else{
                    String addressStr = "";
                    String orderDescription = "";
                    String contacts = "";
                    String cancelReason = "";
                    String remark = "";
                    wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
                }
                goodsDescription = "";
                isFirstSend = false;
            }
            goodsDescription += str;

            //当前为列表末尾位置，需要将消息发送掉
            if(orderRefundGoodsList.indexOf(orderRefundGoods) == orderRefundGoodsList.size()-1){
                //内容超过112个字数，需要重新拼接商品明细内容，将消息发送掉
                goodsDescription = goodsDescription.substring(0, goodsDescription.length()-2);
                //给商家发送微信公众号消息
                String refundWayText = insertPointsMallOrderRefund.getRefundWay() == Quantity.INT_1 ? "全额退款" : "部分退款";
                String title = isFirstSend
                        ? "尊敬的商家，您有一个积分商城订单已被用户申请售后-退货退款，请及时处理 - 订单编号" + dbPointsMallOrder.getOrderNo()
                        : "订单退款商品明细服务通知 - 订单编号" + dbPointsMallOrder.getOrderNo();
                if(isFirstSend){
                    String addressStr = dbPointsMallOrder.getContactProvince() + dbPointsMallOrder.getContactCity() + dbPointsMallOrder.getContactArea() + dbPointsMallOrder.getContactStreet() + dbPointsMallOrder.getContactHouseNumber();
                    String orderDescription = ("订单编号" + dbPointsMallOrder.getOrderNo() + " - " + addressStr);
                    String contacts = dbPointsMallOrder.getContactRealname().concat(" - ").concat(dbPointsMallOrder.getContactPhone());
                    String cancelReason = "取消原因：" + getRefundReasonText(insertPointsMallOrderRefund.getRefundReason());
                    if(StringUtils.isNotBlank(insertPointsMallOrderRefund.getRefundReasonDescription())){
                        cancelReason = cancelReason + "-" + insertPointsMallOrderRefund.getRefundReasonDescription();
                    }
                    String remark = refundWayText + "，订单金额："+dbPointsMallOrder.getActualPrice()+"，退款金额："+insertPointsMallOrderRefund.getRefundAmount();
                    wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
                }else{
                    String addressStr = "";
                    String orderDescription = "";
                    String contacts = "";
                    String cancelReason = "";
                    String remark = "";
                    wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(WxPublicPlatformNotifyService.tkOpenId_mall, title, goodsDescription, dbPointsMallOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
                }
                goodsDescription = "";
                isFirstSend = false;
            }
        }

    }

    @Override
    public void confirmReceipt(PointsMallOrderParam param) {

        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        PointsMallOrder dbPointsMallOrder = pointsMallOrderMapper.selectById(param.getId());
        if(dbPointsMallOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }

        if(!dbPointsMallOrder.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("该订单不是你的，不允许修改");
        }

        // 当订单状态处于已签收，才可以执行确认收货操作
        if(dbPointsMallOrder.getStatus() != Quantity.INT_3){
            throw new StoneCustomerException("该订单状态不正确，不允许修改");
        }

        // 更新PointsMallOrder数据
        PointsMallOrder updatePointsMallOrder = new PointsMallOrder();
        updatePointsMallOrder.setId(dbPointsMallOrder.getId());
        updatePointsMallOrder.setStatus(Quantity.INT_8);
        pointsMallOrderMapper.updateByPrimaryKeySelective(updatePointsMallOrder);

    }

    @Override
    public void delete(PointsMallOrderParam param) {

        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        PointsMallOrder dbPointsMallOrder = pointsMallOrderMapper.selectById(param.getId());
        if(dbPointsMallOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }

        if(!dbPointsMallOrder.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("该订单不是你的，不允许删除");
        }

        // 更新PointsMallOrder数据
        PointsMallOrder updatePointsMallOrder = new PointsMallOrder();
        updatePointsMallOrder.setId(dbPointsMallOrder.getId());
        updatePointsMallOrder.setIsDeleted(true);
        pointsMallOrderMapper.updateByPrimaryKeySelective(updatePointsMallOrder);

    }

    @Override
    public Map selectRefundProcess(PointsMallOrderParam param) {

        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        Map resultMap = new HashMap();

        PointsMallOrder dbPointsMallOrder = pointsMallOrderMapper.selectById(param.getId());
        if(dbPointsMallOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }
        if(!dbPointsMallOrder.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("该订单不是你的，不允许查看");
        }
        if(dbPointsMallOrder.getStatus()!=7 && dbPointsMallOrder.getStatus()!=9 && dbPointsMallOrder.getStatus()!=11){
            throw new StoneCustomerException("该订单无退款信息");
        }

        //查询订单退款信息
        PointsMallOrderRefund orderRefund = orderRefundService.selectByPointsMallOrderId(param.getId());

        //查询订单退款-商品详情数据
        List<PointsMallOrderRefundGoods> orderRefundGoodsList = orderRefundGoodsService.selectByPointsMallOrderRefundId(orderRefund.getId());

        //查询订单退款流程信息
        List<PointsMallOrderRefundProcess> orderRefundProcessList = orderRefundProcessService.selectByPointsMallOrderRefundId(orderRefund.getId());

        resultMap.put("orderRefund", orderRefund);
        resultMap.put("orderRefundGoodsList", orderRefundGoodsList);
        resultMap.put("orderRefundProcessList", orderRefundProcessList);
        return resultMap;
    }

    @Override
    public Map selectTabNumber(PointsMallOrderParam param) {

        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        List<Integer> statusList;
        PointsMallOrderExample mallOrderExample;

        //待付款 状态处于1=未付款
        statusList = new ArrayList<>();
        statusList.add(1);
        mallOrderExample = new PointsMallOrderExample();
        mallOrderExample.createCriteria().andStatusIn(statusList).andMemberIdEqualTo(loginMember.getId()).andIsDeletedEqualTo(false);
        int waitPaymentNumber = pointsMallOrderMapper.countByExample(mallOrderExample);

        //待发货 状态处于4=待发货(已处理)
        statusList = new ArrayList<>();
        statusList.add(4);
        mallOrderExample = new PointsMallOrderExample();
        mallOrderExample.createCriteria().andStatusIn(statusList).andMemberIdEqualTo(loginMember.getId()).andIsDeletedEqualTo(false);
        int waitDeliveredNumber = pointsMallOrderMapper.countByExample(mallOrderExample);

        //待收货 状态处于5=已发货
        statusList = new ArrayList<>();
        statusList.add(5);
        mallOrderExample = new PointsMallOrderExample();
        mallOrderExample.createCriteria().andStatusIn(statusList).andMemberIdEqualTo(loginMember.getId()).andIsDeletedEqualTo(false);
        int waitReceivedNumber = pointsMallOrderMapper.countByExample(mallOrderExample);

        //退款/售后 状态处于7=售后处理中 8=已退款(废弃选项)
        statusList = new ArrayList<>();
        statusList.add(7);
        statusList.add(8);
        mallOrderExample = new PointsMallOrderExample();
        mallOrderExample.createCriteria().andStatusIn(statusList).andMemberIdEqualTo(loginMember.getId()).andIsDeletedEqualTo(false);
        int afterSalesNumber = pointsMallOrderMapper.countByExample(mallOrderExample);

        Map map = new HashMap();
        map.put("waitPaymentNumber", waitPaymentNumber);
        map.put("waitDeliveredNumber", waitDeliveredNumber);
        map.put("waitReceivedNumber", waitReceivedNumber);
        map.put("afterSalesNumber", afterSalesNumber);
        return map;
    }

    @Override
    public String selectCommissionReward(PointsMallOrderParam param) {

        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        String text = null;
        BigDecimal commissionAmount = BigDecimal.ZERO;

        Setting setting=settingService.selectCurrent();
        //平台邀请佣金
        BigDecimal inviteeConsumeCommissionPercent = setting.getPointsMallInviteeConsumeCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal totalCommissionAmount = param.getActualPrice().multiply(inviteeConsumeCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
        BigDecimal inviterCommissionPercent;
        String message;
        //给下单用户的邀请人发放佣金
        Map<String, Integer> inviterMap = memberInviteRelationService.selectInviter(loginMember.getId());
        if(inviterMap.containsKey("secondLevelInviter")){
            //有2个上级邀请人
            //发放下单用户佣金奖励
            inviterCommissionPercent = setting.getPointsMallCasethreeOwnCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);

//            //发放一级邀请人佣金奖励
//            inviterCommissionPercent = setting.getPointsMallCasethreeFirstLevelInviterCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
//            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
//            message = "一级邀请人佣金奖励，来自" + dbMember.getMobile()+"-"+dbMember.getUsername();
//            giveInviterReward(inviterMap.get("firstLevelInviter"), commissionAmount, MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION, message);
//
//            //发放二级邀请人佣金奖励
//            inviterCommissionPercent = setting.getPointsMallCasethreeSecondLevelInviterCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
//            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
//            message = "二级邀请人佣金，来自" + dbMember.getMobile()+"-"+dbMember.getUsername();
//            giveInviterReward(inviterMap.get("secondLevelInviter"), commissionAmount, MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION, message);

        }else if(inviterMap.containsKey("firstLevelInviter")){
            //有1个上级邀请人时
            //发放下单用户佣金奖励
            inviterCommissionPercent = setting.getPointsMallCasetwoOwnCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);

//            //发放一级邀请人佣金奖励
//            inviterCommissionPercent = setting.getPointsMallCasetwoFirstLevelInviterCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
//            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
//            message = "一级邀请人佣金奖励，来自" + dbMember.getMobile()+"-"+dbMember.getUsername();
//            giveInviterReward(inviterMap.get("firstLevelInviter"), commissionAmount, MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION, message);

        }else if(inviterMap.isEmpty()){
            //无上级邀请人时
            //发放下单用户佣金奖励
            inviterCommissionPercent = setting.getPointsMallCaseoneOwnCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
        }

        //规则：如果邀请人不是会员，则不给与佣金奖励；如果邀请人在近30天内无消费，则不给予佣金奖励，不对其它邀请人造成影响；
        //1、开通会员后的第一笔订单无需判断近30天是否有消费这个条件，直接给与佣金奖励
        //2、近30天是否有消费这个条件 是指用户下过单(无论什么状态)--只要支付成功过的都算
        //3、近30天是否有消费这个条件 是指外卖系统、积分商城、(会员充值) 任意有一笔消费即可
        //4、近30天是否有消费这个条件 需要排除当前支付的订单
        Member dbMember = memberService.selectByPrimaryKey(loginMember.getId());
        if(!dbMember.getType().equals(Quantity.INT_2)){
            text = "您现在不是会员，充值会员后，您可得到"+ commissionAmount +"元返利";
        }else{
            //判断当前订单是否为开通会员后的第一笔订单，如果是，则直接给与佣金奖励
            //查询当前用户最近一笔支付成功的会员充值记录
            VipRechargeRecord vipRechargeRecord = vipRechargeRecordService.selectLastestPaid(dbMember.getId());
            List excludeStatusList = new ArrayList();
            excludeStatusList.add(1);
            excludeStatusList.add(10);
            //查询外卖系统的符合订单数
            OrderExample orderExample = new OrderExample();
            orderExample.createCriteria().andCreateTimeGreaterThan(vipRechargeRecord.getCreateTime()).andStatusNotIn(excludeStatusList);
            int count = orderService.countByExample(orderExample);
            //查询积分商城的符合订单数
            PointsMallOrderExample pointsMallOrderExample = new PointsMallOrderExample();
            pointsMallOrderExample.createCriteria().andCreateTimeGreaterThan(vipRechargeRecord.getCreateTime()).andStatusNotIn(excludeStatusList);
            int count_pointsMall = pointsMallOrderMapper.countByExample(pointsMallOrderExample);
            int total_count = count + count_pointsMall;
            if(total_count > 0){
                //不是第一笔订单，进行近30天是否有消费的条件判断
                //近30天外卖系统支付成功的订单数
                OrderParam orderParam = new OrderParam();
                orderParam.setMemberId(loginMember.getId());
                orderParam.setStartTime(DateUtilsExtend.getFrontDay(DateUtilsExtend.getDayEnd(), 30));
                orderParam.setEndTime(DateUtilsExtend.getDayEnd());
                count = this.orderService.selectCountPaid(orderParam);
                //近30天积分商城支付成功的订单数
                PointsMallOrderParam pointsMallOrder = new PointsMallOrderParam();
                pointsMallOrder.setMemberId(loginMember.getId());
                count_pointsMall = this.pointsMallOrderMapper.selectCountPaid(pointsMallOrder, DateUtilsExtend.getFrontDay(DateUtilsExtend.getDayEnd(), 30), DateUtilsExtend.getDayEnd());

                total_count = count + count_pointsMall;
                if(total_count == 0){
                    //TODO-可以建立一张表把这些信息存下来的
                    /*text = "您在近30天内无消费，该笔订单完成后再次消费，您可得到"+ commissionAmount +"元返利";*/
                }else{
                    text = "该笔订单完成后，您可得到"+ commissionAmount +"元返利";
                }
            }else{
                text = "该笔订单完成后，您可得到"+ commissionAmount +"元返利";
            }
        }
        return text;
    }

    public String getRefundReasonText(int refundReason){
        String refundReasonText = "";
        switch (refundReason){
            case 1:
                refundReasonText = "订单信息拍错（规格/尺码/颜色等）";
                break;
            case 2:
                refundReasonText = "我不想要了";
                break;
            case 3:
                refundReasonText = "地址/电话信息填写错误";
                break;
            case 4:
                refundReasonText = "没用/少用优惠";
                break;
            case 5:
                refundReasonText = "缺货";
                break;
            case 31:
                refundReasonText = "缺货";
                break;
            case 32:
                refundReasonText = "协商一致退款";
                break;
            case 33:
                refundReasonText = "未按约定时间发货";
                break;
            case 34:
                refundReasonText = "排错/多拍/不想要";
                break;
            case 35:
                refundReasonText = "其他";
                break;
            case 61:
                refundReasonText = "效果不好/不喜欢";
                break;
            case 62:
                refundReasonText = "材质不符";
                break;
            case 63:
                refundReasonText = "尺寸不符";
                break;
            case 64:
                refundReasonText = "外观破损";
                break;
            case 65:
                refundReasonText = "颜色/款式图案与描述不符";
                break;
            case 66:
                refundReasonText = "物流问题";
                break;
            case 67:
                refundReasonText = "和预期不符";
                break;
            case 68:
                refundReasonText = "服务承诺/态度";
                break;
            case 69:
                refundReasonText = "质量问题";
                break;
            case 70:
                refundReasonText = "其他";
                break;
        }
        return refundReasonText;
    }
}