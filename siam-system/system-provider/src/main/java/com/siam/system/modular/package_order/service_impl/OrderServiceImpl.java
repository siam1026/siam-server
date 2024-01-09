package com.siam.system.modular.package_order.service_impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siam.package_common.constant.BusinessType;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.mod_websocket.WebSocketService;
import com.siam.package_common.service.AliyunSms;
import com.siam.package_common.util.*;
import com.siam.system.modular.package_goods.entity.*;
import com.siam.package_weixin_basic.service.WxNotifyService;
import com.siam.package_weixin_basic.service.WxPublicPlatformNotifyService;
import com.siam.system.modular.package_goods.service.*;
import com.siam.system.modular.package_user.service.*;
import com.siam.system.modular.package_goods.model.example.ShopExample;
import com.siam.system.modular.package_order.controller.member.WxPayService;
import com.siam.system.modular.package_order.entity.*;
import com.siam.system.modular.package_order.mapper.OrderMapper;
import com.siam.system.modular.package_order.model.example.OrderExample;
import com.siam.system.modular.package_order.model.param.OrderParam;
import com.siam.system.modular.package_order.model.vo.OrderVo;
import com.siam.system.modular.package_order.model.vo.OrderVo2;
import com.siam.system.modular.package_order.service.*;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.*;
import com.siam.system.modular.package_user.model.example.MemberBillingRecordExample;
import com.siam.system.modular.package_user.model.example.MerchantExample;
import com.siam.system.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private MemberBillingRecordService memberBillingRecordService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponsMemberRelationService couponsMemberRelationService;

    @Autowired
    private MemberTradeRecordService memberTradeRecordService;

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private MerchantBillingRecordService merchantBillingRecordService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private WxNotifyService wxNotifyService;

    @Autowired
    private WxPublicPlatformNotifyService wxPublicPlatformNotifyService;

    @Autowired
    private OrderRefundService orderRefundService;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private MemberInviteRelationService memberInviteRelationService;

    @Resource(name = "rewardServiceImpl")
    private RewardService rewardService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    @Autowired
    private GoodsSpecificationOptionService goodsSpecificationOptionService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private FullReductionRuleService fullReductionRuleService;

    @Autowired
    private CouponsService couponsService;

    @Autowired
    private CouponsGoodsRelationService couponsGoodsRelationService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CouponsShopRelationService couponsShopRelationService;

    @Autowired
    private AppraiseService appraiseService;

    @Autowired
    private OrderRefundGoodsService orderRefundGoodsService;

    @Autowired
    private OrderRefundProcessService orderRefundProcessService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private AliyunSms aliyunSms;

    public int countByExample(OrderExample example){
        return orderMapper.countByExample(example);
    }

    public void delete(OrderParam param){
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        Order dbOrder = orderMapper.selectByPrimaryKey(param.getId());
        if(dbOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }
        if(!dbOrder.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("该订单不是你的，不允许删除");
        }

        //逻辑删除
        Order updateOrder = new Order();
        updateOrder.setId(dbOrder.getId());
        updateOrder.setIsDeleted(true);
        orderMapper.updateByPrimaryKeySelective(updateOrder);
    }

    public Order insert(OrderParam param) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //基础校验
        if(param.getShopId() == null){
            throw new StoneCustomerException("店铺id不能为空");
        }

        //如果是从购物车下单的 那么需要校验购物车数据是否存在 以及购物车数据是否属于当前登录用户
        if(param.getShoppingCartIdList()!=null && !param.getShoppingCartIdList().isEmpty()){
            int result = shoppingCartService.countByIdListAndMemberId(param.getShoppingCartIdList(), loginMember.getId());
            if(result != param.getShoppingCartIdList().size()){
                throw new StoneCustomerException("购物车数据异常，请刷新页面重新下单");
            }
        }

        Shop dbShop = shopService.selectByPrimaryKey(param.getShopId());
        if(dbShop == null){
            throw new StoneCustomerException("该门店信息不存在");
        }
        param.setShopName(dbShop.getName());
        param.setShopLogoImg(dbShop.getShopLogoImg());

        //回写门店地址
        String shopAddress = dbShop.getProvince() + dbShop.getCity() + dbShop.getArea() + dbShop.getStreet() + dbShop.getHouseNumber();
        param.setShopAddress(shopAddress);

        Setting setting = settingService.selectCurrent();
        if(param.getShoppingWay() == Quantity.INT_1){
            //自提订单
            //根据用户信息回写联系人姓名、联系电话、联系人性别
            param.setContactRealname(loginMember.getUsername());
            param.setContactPhone(loginMember.getMobile());
            param.setContactSex(loginMember.getSex());
            param.setDeliveryFee(BigDecimal.ZERO);

        }else if(param.getShoppingWay() == Quantity.INT_2){
            //配送订单
            //根据收货地址id回写联系人信息
            if(param.getDeliveryAddressId() == null) throw new StoneCustomerException("必须填写收货地址");
            DeliveryAddress dbDeliveryAddress = deliveryAddressService.selectByPrimaryKey(param.getDeliveryAddressId());
            if(dbDeliveryAddress == null) throw new StoneCustomerException("收货地址不存在");

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
        }

        //手动将JSON字符串转化为对象
        List<OrderDetail> orderDetailList = GsonUtils.toList(param.getOrderDetailListStr(), OrderDetail.class);

        //订单总金额以后端计算为准，前端传递过来的总金额需要进行比对-防止前端计算错误
        BigDecimal goodsTotalPrice = BigDecimal.ZERO; //商品总金额
        BigDecimal packingTotalPrice = BigDecimal.ZERO; //包装费
        int goodsTotalQuantity = 0; //商品总数量
        for (OrderDetail orderDetail : orderDetailList) {
            Goods dbGoods = goodsService.selectByPrimaryKey(orderDetail.getGoodsId());
            if (dbGoods == null) throw new StoneCustomerException("订单商品数据异常，请稍后重试");
            //判断商品状态是否正确
            if(dbGoods.getStatus() == Quantity.INT_2) {
                //状态为已上架，正确
            }else if(dbGoods.getStatus() == Quantity.INT_1){
                throw new StoneCustomerException(dbGoods.getName() + "还未上架，请重新选择");

            }else if(dbGoods.getStatus() == Quantity.INT_3){
                throw new StoneCustomerException(dbGoods.getName() + "已下架，请重新选择");

            }else if(dbGoods.getStatus() == Quantity.INT_4){
                throw new StoneCustomerException(dbGoods.getName() + "已售罄，请重新选择");
            }

            //商品规格选项值列表
            List<String> nameList = new ArrayList<>();
            //计算单品对应规格的价格
            Map<String, Object> map = GsonUtils.toMap(orderDetail.getSpecList());
            for(String key : map.keySet()){
                nameList.add((String) map.get(key));
            }

            //正常情况下nameList不能为空，为空也要做特殊处理
            BigDecimal specOptionPrice = BigDecimal.ZERO;
            if(nameList.size() > 0){
                specOptionPrice = goodsSpecificationOptionService.selectSumPriceByGoodsIdAndName(orderDetail.getGoodsId(), nameList);
            }

            //单品的总价
            BigDecimal goodsPrice = (dbGoods.getPrice().add(specOptionPrice)).multiply(BigDecimal.valueOf(orderDetail.getNumber()));
            //订单总金额累加
            goodsTotalPrice = goodsTotalPrice.add(goodsPrice);
            goodsTotalQuantity = goodsTotalQuantity + orderDetail.getNumber();
            packingTotalPrice = packingTotalPrice.add(dbGoods.getPackingCharges().multiply(BigDecimal.valueOf(orderDetail.getNumber())));
        }

        //计算未使用优惠前的最终价格(商品总价+包装费)
        BigDecimal finalPrice = goodsTotalPrice.add(packingTotalPrice);

        log.debug("商品总价：" + goodsTotalPrice.toString());
        log.debug("商品总数量：" + packingTotalPrice.toString());
        log.debug("包装费：" + packingTotalPrice.toString());
        log.debug("未使用优惠前的最终价格：" + finalPrice.toString());

        //判断优惠卷是否满足使用条件
        Integer couponsMemberRelationId = param.getCouponsMemberRelationId();
        BigDecimal subtractPrice = BigDecimal.ZERO; //使用优惠券时优惠的金额
        if (couponsMemberRelationId != null) {
        //查询出使用的优惠券信息
            CouponsMemberRelation dbCouponsMemberRelation = couponsMemberRelationService.selectCouponsMemberRelationByPrimaryKey(couponsMemberRelationId);
            Integer couponsId = dbCouponsMemberRelation.getCouponsId();
            Map couponsMap = couponsService.selectCouponsAndGoodsByPrimaryKey(couponsId);
            Coupons dbCoupons = (Coupons) couponsMap.get("coupons");
            if(Coupons.TYPE_DISCOUNT.equals(dbCoupons.getPreferentialType())){
                //折扣优惠券
                //如果是商家中心发放的优惠券，则需要判断关联商品
                //如果是由调度中心发放的优惠券，则无需判断关联商品，所有商品皆可使用

                Boolean isGoodsMeetCoupons = false; //下单商品是否满足此优惠券使用条件的标识
                BigDecimal subtractNum = BigDecimal.ZERO; //下单商品中的最高价格
                OrderDetail couponsDiscountOrderDetail = null; //优惠券选择优惠的商品

                //判断该优惠券能否在这家店铺使用
                List<Integer> shopIdList = couponsShopRelationService.getShopIdByCouponsId(couponsId);
                if(!shopIdList.contains(param.getShopId())){
                    throw new StoneCustomerException("当前店铺不能使用所选优惠券，请重新下单");
                }

                //查询出优惠券关联的所有商品
                List<Integer> goodsIdList = couponsGoodsRelationService.getGoodsIdByCouponsId(couponsId);
                for (OrderDetail orderDetail : orderDetailList) {
                    //获取商品详情
                    Goods goods = goodsService.selectByPrimaryKey(orderDetail.getGoodsId());
                    //判断商品是否能够使用此优惠卷 且 是否应用于最高价格的商品(不包括包装费)
                    if (goodsIdList.contains(goods.getId()) || dbCoupons.getSource() == Quantity.INT_2) {
                        //标记此优惠券满足使用条件
                        isGoodsMeetCoupons = true;

                        //这一步先计算出商品单价+规格单价的价格
                        //规格
                        String specList=orderDetail.getSpecList();
                        List<String> nameList = new ArrayList();
                        Map<String, Object> map = GsonUtils.toMap(specList);
                        for(String key : map.keySet()){
                            nameList.add((String) map.get(key));
                        }
                        //正常情况下nameList不能为空，为空也要做特殊处理
                        BigDecimal specOptionPrice = BigDecimal.ZERO;
                        if(nameList.size() > 0){
                            specOptionPrice = goodsSpecificationOptionService.selectSumPriceByGoodsIdAndName(orderDetail.getGoodsId(), nameList);
                        }
                        //计算出商品单价+规格单价的价格
                        BigDecimal price = goods.getPrice().add(specOptionPrice);

                        //然后将当前价格 与 最高价格进行比较
                        if(price.compareTo(subtractNum) == 1){
                            subtractNum = price;
                            couponsDiscountOrderDetail = orderDetail;
                        }
                    }
                }
                //关于优惠券是否应用于最高价格的商品，这个如果前端判断错误，后端不单独给与提醒--统一用订单实付款计算错误提醒即可
                if(!isGoodsMeetCoupons){
                    throw new StoneCustomerException("所选优惠券不满足使用条件，请重新下单");
                }

                log.debug("优惠券选择优惠的商品价格：" + subtractNum.toString());
                log.debug("优惠券折扣额度：" + dbCoupons.getDiscountAmount().toString());

                //计算使用优惠券时优惠的金额
                subtractPrice= subtractNum.multiply((BigDecimal.ONE.subtract(dbCoupons.getDiscountAmount()))).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
                finalPrice = finalPrice.subtract(subtractPrice);

                //标识订单商品详情
                couponsDiscountOrderDetail.setIsUsedCoupons(true);
                couponsDiscountOrderDetail.setCouponsDiscountPrice(subtractPrice);

            }else if(Coupons.TYPE_FULL_REDUCTION.equals(dbCoupons.getPreferentialType())){
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
            param.setCouponsId(couponsId);
            param.setCouponsMemberRelationId(couponsMemberRelationId);
            param.setCouponsDescription((dbCoupons).getName() + "-优惠" + subtractPrice + "元");
            log.debug("使用优惠券时优惠的金额：" + subtractPrice.toString());
        }

        //TODO-后端不判断订单应使用哪个满减规则，只对前端传递的满减规则进行是否满足使用条件判断
        //判断满减规则是否满足使用条件
        Integer fullReductionRuleId = param.getFullReductionRuleId();
        if (fullReductionRuleId != null) {
            FullReductionRule dbFullReductionRule = fullReductionRuleService.selectByPrimaryKey(fullReductionRuleId);
            if(finalPrice.compareTo(dbFullReductionRule.getLimitedPrice()) >= 0){
                finalPrice = finalPrice.subtract(dbFullReductionRule.getReducedPrice());
                param.setFullReductionRuleId(dbFullReductionRule.getId());
                param.setFullReductionRuleDescription(dbFullReductionRule.getName());
                param.setLimitedPrice(dbFullReductionRule.getLimitedPrice());
                param.setReducedPrice(dbFullReductionRule.getReducedPrice());
                log.debug("使用满减规则优惠金额：" + dbFullReductionRule.getReducedPrice());
                log.debug("使用满减规则后的最终价格：" + finalPrice);
            }else{
                throw new StoneCustomerException("满减规则不满足使用条件，请稍后重试");
            }
        }

        BigDecimal merchantDeliveryFee = BigDecimal.ZERO; //商家承担配送费
        //配送费判断
        if(param.getShoppingWay() == Quantity.INT_2){
            Integer addressId = param.getDeliveryAddressId();
            DeliveryAddress deliveryAddress = deliveryAddressService.selectByPrimaryKey(addressId);
            /*String addressStr = deliveryAddress.getProvince() + deliveryAddress.getCity() + deliveryAddress.getArea() + deliveryAddress.getStreet();*/
            BigDecimal deliveryFee = commonService.selectDeliveryFee(deliveryAddress.getLongitude(), deliveryAddress.getLatitude(), param.getShopId());
            param.setBeforeReducedDeliveryFee(deliveryFee);
            //判断商家立减配送费
            if(dbShop.getReducedDeliveryPrice().compareTo(BigDecimal.ZERO) > 0){
                if((dbShop.getReducedDeliveryPrice().compareTo(deliveryFee) >= 0)){
                    merchantDeliveryFee = deliveryFee;
                    deliveryFee = BigDecimal.ZERO;
                }else{
                    merchantDeliveryFee = dbShop.getReducedDeliveryPrice();
                    deliveryFee = deliveryFee.subtract(dbShop.getReducedDeliveryPrice());
                }
            }
            if(deliveryFee.compareTo(param.getDeliveryFee()) != 0){
                throw new StoneCustomerException("配送费计算错误，请稍后重试");
            }
            finalPrice = finalPrice.add(deliveryFee);
            log.debug("配送费：" + deliveryFee);
        }else{
            param.setDeliveryFee(BigDecimal.ZERO);
        }

        //判断前端的最终价格和后端的最终价格是否一致
        //如果最终价格计算出来是负数，则要手动赋值为0
        finalPrice = (finalPrice.compareTo(BigDecimal.ZERO)==-1) ? BigDecimal.ZERO : finalPrice.setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
        log.debug("前端计算的实付款：" + param.getActualPrice().toString());
        log.debug("后端计算的实付款：" + finalPrice);
        if(param.getActualPrice().compareTo(finalPrice) != 0){
            throw new StoneCustomerException("订单实付款计算错误，请稍后重试");
        }

        //计算平台抽取费用等属性
        BigDecimal platformExtractRatio, platformExtractPrice, platformDeliveryFee, platformIncome, courierIncome, merchantIncome;
        if(param.getShoppingWay() == Quantity.INT_2){
            //配送
            platformExtractRatio = setting.getOrderSystemExtractionRatio().divide(BigDecimal.valueOf(100), Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            platformExtractPrice = finalPrice.multiply(platformExtractRatio).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
            platformDeliveryFee = platformExtractPrice;
            platformIncome = platformExtractPrice.subtract(platformDeliveryFee);
            courierIncome = platformDeliveryFee.add(merchantDeliveryFee).add(param.getDeliveryFee());
            merchantIncome = finalPrice.subtract(platformExtractPrice).subtract(merchantDeliveryFee).subtract(param.getDeliveryFee());
        }else{
            //自取
            platformExtractRatio = setting.getOrderSystemExtractionRatio().divide(BigDecimal.valueOf(100), Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            platformExtractPrice = finalPrice.multiply(platformExtractRatio).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
            platformDeliveryFee = BigDecimal.ZERO;
            platformIncome = BigDecimal.ZERO;
            courierIncome = BigDecimal.ZERO;
            merchantIncome = finalPrice.subtract(platformExtractPrice).add(platformExtractPrice);
        }

        // 获取订单编号
        int i = 0;
        String orderNo = GenerateNo.getOrderNo();
        while (true){
            if(i == 99){
                throw new StoneCustomerException("无法生成订单编号");
            }
            log.debug("\n获取订单编号...");
            OrderExample orderExample = new OrderExample();
            orderExample.createCriteria().andOrderNoEqualTo(orderNo);
            int result = orderMapper.countByExample(orderExample);
            if(result > 0){
                orderNo = GenerateNo.getOrderNo();
            }else{
                break;
            }
            i++;
        }

        //订单描述信息
        String description = orderDetailList.get(0).getGoodsName();
        description += (goodsTotalQuantity > 1) ? ("&nbsp;&nbsp;等" + goodsTotalQuantity + "件") : "";

        //订单取餐号
        Integer queueNo = this.getNextQueueNo();

        //支付截止时间(五分钟内未付款的订单会被自动关闭)
        Date paymentDeadline = DateUtilsPlus.addMinutes(new Date(), Quantity.INT_5);

        //TODO-配送方式默认为自配送
        if(param.getShoppingWay() == Quantity.INT_2){
            param.setDeliveryWay(Quantity.INT_1);
        }

        //添加订单记录
        Order insertOrder = new Order();
        insertOrder.setMemberId(loginMember.getId());
        insertOrder.setOrderNo(orderNo);
        insertOrder.setGoodsTotalQuantity(goodsTotalQuantity);
        insertOrder.setGoodsTotalPrice(goodsTotalPrice);
        insertOrder.setPackingCharges(packingTotalPrice);
        insertOrder.setDeliveryFee(param.getDeliveryFee());
        insertOrder.setActualPrice(finalPrice);
        insertOrder.setShoppingWay(param.getShoppingWay());
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
        insertOrder.setDescription(description);
        insertOrder.setStatus(Quantity.INT_1);
        insertOrder.setOrderLogisticsId(null);
        insertOrder.setIsInvoice(false);
        insertOrder.setInvoiceId(null);
        insertOrder.setIsDeleted(false);
        insertOrder.setShopId(param.getShopId());
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
        insertOrder.setPlatformExtractRatio(platformExtractRatio);
        insertOrder.setPlatformExtractPrice(platformExtractPrice);
        insertOrder.setPlatformDeliveryFee(platformDeliveryFee);
        insertOrder.setPlatformIncome(platformIncome);
        insertOrder.setMerchantDeliveryFee(merchantDeliveryFee);
        insertOrder.setCourierIncome(courierIncome);
        insertOrder.setMerchantIncome(merchantIncome);
        insertOrder.setLimitedPrice(param.getLimitedPrice());
        insertOrder.setReducedPrice(param.getReducedPrice());
        insertOrder.setCouponsDiscountPrice(subtractPrice);
        insertOrder.setDeliveryWay(param.getDeliveryWay());
        insertOrder.setIsPayToMerchant(false);
        insertOrder.setBeforeReducedDeliveryFee(param.getBeforeReducedDeliveryFee());
        insertOrder.setContactHouseNumber(param.getContactHouseNumber());
        insertOrder.setContactLongitude(param.getContactLongitude());
        insertOrder.setContactLatitude(param.getContactLatitude());
        insertOrder.setShopLogoImg(param.getShopLogoImg());
        orderMapper.insertSelective(insertOrder);

        Order dbOrder = orderMapper.selectByPrimaryKey(insertOrder.getId());

        // 添加订单商品详情记录
        for(OrderDetail orderDetail : orderDetailList){
            int goodsId = orderDetail.getGoodsId();
            String goodsSpecification = orderDetail.getSpecList();
            int number = orderDetail.getNumber();
            // 获取商品信息
            Goods dbGoods = goodsService.selectByPrimaryKey(Integer.valueOf(goodsId));
            //商品规格选项值列表
            List<String> nameList = new ArrayList<>();
            //计算单品对应规格的价格
            Map<String, Object> map = GsonUtils.toMap(orderDetail.getSpecList());
            for(String key : map.keySet()){
                nameList.add((String) map.get(key));
            }

            //正常情况下nameList不能为空，为空也要做特殊处理
            BigDecimal specOptionPrice = BigDecimal.ZERO;
            if(nameList.size() > 0){
                specOptionPrice = goodsSpecificationOptionService.selectSumPriceByGoodsIdAndName(orderDetail.getGoodsId(), nameList);
            }

            //单品的总价
            BigDecimal price = dbGoods.getPrice().add(specOptionPrice);
            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(orderDetail.getNumber()));
            // 添加订单商品详情记录
            OrderDetail insertOrderDetail = new OrderDetail();
            insertOrderDetail.setOrderId(dbOrder.getId());
            insertOrderDetail.setGoodsId(goodsId);
            insertOrderDetail.setGoodsName(dbGoods.getName());
            insertOrderDetail.setMainImage(dbGoods.getMainImage());
            insertOrderDetail.setSpecList(goodsSpecification);
            insertOrderDetail.setPrice(price);
            insertOrderDetail.setNumber(number);
            insertOrderDetail.setSubtotal(subtotal);
            insertOrderDetail.setPackingCharges(dbGoods.getPackingCharges());
            if(orderDetail.getIsUsedCoupons() != null){
                insertOrderDetail.setIsUsedCoupons(orderDetail.getIsUsedCoupons());
                insertOrderDetail.setCouponsDiscountPrice(orderDetail.getCouponsDiscountPrice());
                insertOrderDetail.setAfterCouponsDiscountPrice(dbGoods.getPrice().subtract(orderDetail.getCouponsDiscountPrice()));
            }
            insertOrderDetail.setIsDeleted(false);
            orderDetailService.insertSelective(insertOrderDetail);

            // 减少商品库存 (规格的库存该怎么去变化)
            /*goodsService.decreaseStock(goodsId, number);*/
        }

        //如果是从购物车下单的 那么下单成功后需要删除购物车数据  注意用批量删除
        if(param.getShoppingCartIdList()!=null && !param.getShoppingCartIdList().isEmpty()){
            shoppingCartService.batchDeleteByIdList(param.getShoppingCartIdList());
        }

        //修改新用户
        Member dbMember = new Member();
        if(loginMember.getIsNewPeople()){
            Member updateMember = new Member();
            updateMember.setId(loginMember.getId());
            updateMember.setIsNewPeople(false);
            updateMember.setIsRemindNewPeople(false);
            memberService.updateByPrimaryKeySelective(updateMember);
            dbMember = memberService.selectByPrimaryKey(loginMember.getId());
        }

        //使用优惠卷
        if (couponsMemberRelationId != null) {
            couponsMemberRelationService.updateCouponsUsed(couponsMemberRelationId,true);
        }

        /*//加入MQ延时队列，检测并关闭超时未支付的订单，5分钟
        Message message = new Message("TID_COMMON", "CLOSE_OVERDUE_ORDER", JSON.toJSONString(dbOrder).getBytes());
        message.setDelayTimeLevel(RocketMQConst.DELAY_TIME_LEVEL_5M);
        rocketMQTemplate.getProducer().send(message);*/

        return dbOrder;
    }

    @Override
    public void cancelOrder(OrderParam param) {
        BasicResult basicResult = new BasicResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        Order dbOrder = orderMapper.selectByPrimaryKey(param.getId());
        if(dbOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }
        if(!dbOrder.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("该订单不是你的，不允许取消");
        }
        if(dbOrder.getStatus() != Quantity.INT_1){
            throw new StoneCustomerException("该订单状态非未付款，不能取消");
        }

        //修改订单记录状态
        Order updateOrder = new Order();
        updateOrder.setId(dbOrder.getId());
        updateOrder.setStatus(Quantity.INT_10);
        orderMapper.updateByPrimaryKeySelective(updateOrder);

        //查询订单对应的订单商品详情数据
        List<OrderDetail> orderDetailList = orderDetailService.selectByOrderId(param.getId());

        //恢复订单对应的商品库存 (对应规格的库存怎么修改)
        for(OrderDetail orderDetail : orderDetailList){
            int goodsId = orderDetail.getGoodsId();
            int number = orderDetail.getNumber();
            //增加商品库存
            /*goodsService.increaseStock(goodsId, number);*/
        }

        //退回优惠卷
        Integer couponsMemberRelationId = dbOrder.getCouponsMemberRelationId();
        if (couponsMemberRelationId != null) {
            couponsMemberRelationService.updateCouponsUsed(couponsMemberRelationId,false);
        }
    }

    @Override
    public void cancelOrderNoReason(OrderParam param) throws IOException {
        BasicResult basicResult = new BasicResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        Order dbOrder = orderMapper.selectByPrimaryKey(param.getId());
        if (dbOrder == null) {
            throw new StoneCustomerException("该订单不存在");
        }
        if (!dbOrder.getMemberId().equals(loginMember.getId())) {
            throw new StoneCustomerException("该订单不是你的，不允许取消");
        }

        //订单支付后1分钟内 & 订单状态处于待处理时(自取订单是待处理，配送订单是待配送)，用户可以无责取消订单；
        Order orderParam = new Order();
        orderParam.setId(dbOrder.getId());
        boolean isAllowCancelNoReason = orderMapper.getIsAllowCancelNoReason(param);
        if (!isAllowCancelNoReason) {
            throw new StoneCustomerException("该订单不允许取消");
        }

        //修改订单记录状态
        Order updateOrder = new Order();
        updateOrder.setId(dbOrder.getId());
        updateOrder.setStatus(Quantity.INT_11);
        orderMapper.updateByPrimaryKeySelective(updateOrder);

        /*//查询订单对应的订单商品详情数据
        List<OrderDetail> orderDetailList = orderDetailService.selectByOrderId(param.getId());

        //恢复订单对应的商品库存 (对应规格的库存怎么修改)
        for(OrderDetail orderDetail : orderDetailList){
            int goodsId = orderDetail.getGoodsId();
            int number = orderDetail.getNumber();
            //增加商品库存
//            goodsService.increaseStock(goodsId, number);
        }*/

        //退回优惠卷
        Integer couponsMemberRelationId = dbOrder.getCouponsMemberRelationId();
        if (couponsMemberRelationId != null) {
            couponsMemberRelationService.updateCouponsUsed(couponsMemberRelationId, false);
        }

        //查找该订单对应的交易记录
        MemberTradeRecord memberTradeRecord = memberTradeRecordService.selectByPrimaryKey(dbOrder.getTradeId());
        int refundAccount = memberTradeRecord.getPaymentMode() == Quantity.INT_1
                ? Quantity.INT_1 :
                (memberTradeRecord.getPaymentMode() == Quantity.INT_2 ? Quantity.INT_2
                        : (memberTradeRecord.getPaymentMode() == Quantity.INT_3 ? Quantity.INT_3 : Quantity.INT_0));

        //添加退款记录
        OrderRefund insertOrderRefund = new OrderRefund();
        insertOrderRefund.setOrderId(dbOrder.getId());
        insertOrderRefund.setType(Quantity.INT_1);
        insertOrderRefund.setRefundWay(Quantity.INT_1);
        insertOrderRefund.setRefundReason(param.getOrderRefund().getRefundReason());
        insertOrderRefund.setRefundReasonDescription(null);
        insertOrderRefund.setEvidenceImages(null);
        insertOrderRefund.setRefundAmount(dbOrder.getActualPrice());
        insertOrderRefund.setRefundAccount(refundAccount);
        insertOrderRefund.setStatus(Quantity.INT_1); //跳过第一步-退款申请已提交，直接到等待商家处理
        insertOrderRefund.setGoodsTotalQuantity(dbOrder.getGoodsTotalQuantity());
        insertOrderRefund.setGoodsTotalPrice(dbOrder.getGoodsTotalPrice());
        insertOrderRefund.setPackingCharges(dbOrder.getPackingCharges());
        insertOrderRefund.setIsRefundDeliveryFee(true);
        insertOrderRefund.setDeliveryFee(dbOrder.getDeliveryFee());
        insertOrderRefund.setIsUsedCoupons(dbOrder.getCouponsId() != null ? true : false);
        insertOrderRefund.setIsUsedFullReductionRule(dbOrder.getFullReductionRuleId() != null ? true : false);
        insertOrderRefund.setCreateTime(new Date());
        insertOrderRefund.setUpdateTime(new Date());
        orderRefundService.insertSelective(insertOrderRefund);

        //查询订单对应的订单商品详情数据
        List<OrderDetail> orderDetailList = orderDetailService.selectByOrderId(dbOrder.getId());
        //添加订单部分退款-商品详情信息
        OrderRefundGoods orderRefundGoods = new OrderRefundGoods();
        orderDetailList.forEach(orderDetail -> {
            //计算退还金额
            BigDecimal subtotal = orderDetail.getPrice().multiply(BigDecimal.valueOf(orderDetail.getNumber())).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            orderRefundGoods.setId(null);
            orderRefundGoods.setOrderRefundId(insertOrderRefund.getId());
            orderRefundGoods.setOrderDetailId(orderDetail.getId());
            orderRefundGoods.setGoodsId(orderDetail.getGoodsId());
            orderRefundGoods.setGoodsName(orderDetail.getGoodsName());
            orderRefundGoods.setMainImage(orderDetail.getMainImage());
            orderRefundGoods.setSpecList(orderDetail.getSpecList());
            orderRefundGoods.setPrice(orderDetail.getPrice());
            orderRefundGoods.setNumber(orderDetail.getNumber());
            orderRefundGoods.setSubtotal(subtotal);
            orderRefundGoodsService.insertSelective(orderRefundGoods);
        });

        //添加退款流程
        String refundReasonText = this.getRefundReasonText(param.getOrderRefund().getRefundReason());
        OrderRefundProcess orderRefundProcess = new OrderRefundProcess();
        orderRefundProcess.setOrderRefundId(insertOrderRefund.getId());
        orderRefundProcess.setName("退款申请已提交");
        orderRefundProcess.setDescription("退款原因: ".concat(refundReasonText));
        orderRefundProcess.setCreateTime(new Date());
        orderRefundProcessService.insertSelective(orderRefundProcess);

        //进行订单自动退款操作
        boolean isRefundSuccess = false;
        if (dbOrder.getPaymentMode().equals(Quantity.INT_1)) {
            //微信支付
            isRefundSuccess = wxPayService.refund(dbOrder.getOrderNo(), dbOrder.getActualPrice(), insertOrderRefund.getRefundAmount());

        } else if (dbOrder.getPaymentMode().equals(Quantity.INT_2)) {
            //余额支付
            Member dbMember = memberService.selectByPrimaryKey(loginMember.getId());
            //增加用户的余额
            Member updateMember = new Member();
            updateMember.setId(dbMember.getId());
            updateMember.setBalance(dbMember.getBalance().add(insertOrderRefund.getRefundAmount()));
            updateMember.setTotalConsumeBalance(dbMember.getTotalConsumeBalance().subtract(insertOrderRefund.getRefundAmount()));
            memberService.updateByPrimaryKeySelective(updateMember);
            dbMember = memberService.selectByPrimaryKey(loginMember.getId());
            //添加账单记录
            MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
            memberBillingRecord.setMemberId(dbMember.getId());
            memberBillingRecord.setType(MemberBillingRecord.TYPE_ORDER_CANCEL_RETURN_BALANCE);
            memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
            memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_BALANCE);
            memberBillingRecord.setNumber(insertOrderRefund.getRefundAmount());
            memberBillingRecord.setMessage("一分钟内取消订单-余额退回");
            memberBillingRecord.setCreateTime(new Date());
            memberBillingRecordService.insertSelective(memberBillingRecord);

            //退款成功的操作
            this.updateRefundStatus(dbOrder.getOrderNo());

            isRefundSuccess = true;
        }
        if (isRefundSuccess) {
            OrderRefund updateOrderRefund = new OrderRefund();
            updateOrderRefund.setId(insertOrderRefund.getId());
            updateOrderRefund.setStatus(Quantity.INT_7);
            updateOrderRefund.setUpdateTime(new Date());
            orderRefundService.updateByPrimaryKeySelective(updateOrderRefund);

            //添加退款流程
            OrderRefundProcess orderRefundProcess_second = new OrderRefundProcess();
            orderRefundProcess_second.setOrderRefundId(insertOrderRefund.getId());
            orderRefundProcess_second.setName("退款成功");
            orderRefundProcess_second.setCreateTime(new Date());
            orderRefundProcessService.insertSelective(orderRefundProcess_second);

            //退回下单奖励积分
            MemberBillingRecordExample example = new MemberBillingRecordExample();
            example.createCriteria().andTypeEqualTo(MemberBillingRecord.TYPE_ORDER_REWARD_POINTS)
                    .andOperateTypeEqualTo(MemberBillingRecord.OPERATE_TYPE_ADD)
                    .andCoinTypeEqualTo(MemberBillingRecord.COIN_TYPE_UNRECEIVED_POINTS)
                    .andOrderIdEqualTo(dbOrder.getId());
            List<MemberBillingRecord> list = memberBillingRecordService.selectByExample(example);
            if (!list.isEmpty()) {
                MemberBillingRecord dbMemberBillingRecord = list.get(0);
                Member dbMember = memberService.selectByPrimaryKey(dbOrder.getMemberId());
                //获取用户当前积分数 -- 未到账积分
                BigDecimal unreceivedPointsNum = dbMember.getUnreceivedPoints().subtract(dbMemberBillingRecord.getNumber());
                //修改用户的积分数
                Member member = new Member();
                member.setId(dbOrder.getMemberId());
                member.setUnreceivedPoints(unreceivedPointsNum);
                memberService.updateByPrimaryKeySelective(member);
                dbMember = memberService.selectByPrimaryKey(dbOrder.getMemberId());

                MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
                memberBillingRecord.setMemberId(dbOrder.getMemberId());
                memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_UNRECEIVED_POINTS);
                memberBillingRecord.setType(MemberBillingRecord.TYPE_ORDER_REWARD_POINTS_RETURN);
                memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_SUB);
                memberBillingRecord.setNumber(dbMemberBillingRecord.getNumber());
                memberBillingRecord.setMessage("订单退款-下单奖励积分退回");
                memberBillingRecord.setOrderId(dbOrder.getId());
                memberBillingRecord.setCreateTime(new Date());
                memberBillingRecordService.insertSelective(memberBillingRecord);

                //之前的账单记录标注已退回
                MemberBillingRecord updateMemberBillingRecord = new MemberBillingRecord();
                updateMemberBillingRecord.setId(dbMemberBillingRecord.getId());
                updateMemberBillingRecord.setIsReturn(true);
                memberBillingRecordService.updateByPrimaryKeySelective(updateMemberBillingRecord);
            }

            //退回下单佣金奖励
            List typeList = new ArrayList();
            typeList.add(MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION);
            typeList.add(MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION);
            typeList.add(MemberBillingRecord.TYPE_OWN_COMMISSION);
            example = new MemberBillingRecordExample();
            example.createCriteria().andTypeIn(typeList)
                    .andOperateTypeEqualTo(MemberBillingRecord.OPERATE_TYPE_ADD)
                    .andCoinTypeEqualTo(MemberBillingRecord.COIN_TYPE_UNRECEIVED_INVITE_REWARD_AMOUNT)
                    .andOrderIdEqualTo(dbOrder.getId());
            list = memberBillingRecordService.selectByExample(example);
            if (!list.isEmpty()) {
                for (MemberBillingRecord dbMemberBillingRecord : list) {
                    Integer type = null;
                    String message = "";
                    if (dbMemberBillingRecord.getType().equals(MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION)) {
                        type = MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION_RETURN;
                        message = "订单退款-一级邀请人佣金奖励退回";
                    } else if (dbMemberBillingRecord.getType().equals(MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION)) {
                        type = MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION_RETURN;
                        message = "订单退款-二级邀请人佣金奖励退回";
                    } else if (dbMemberBillingRecord.getType().equals(MemberBillingRecord.TYPE_OWN_COMMISSION)) {
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
                    memberBillingRecord.setOrderId(dbOrder.getId());
                    memberBillingRecord.setCreateTime(new Date());
                    memberBillingRecordService.insertSelective(memberBillingRecord);

                    //之前的账单记录标注已退回
                    MemberBillingRecord updateMemberBillingRecord = new MemberBillingRecord();
                    updateMemberBillingRecord.setId(dbMemberBillingRecord.getId());
                    updateMemberBillingRecord.setIsReturn(true);
                    memberBillingRecordService.updateByPrimaryKeySelective(updateMemberBillingRecord);
                }
                }
            }

        //获取该订单对应的商家信息
        Shop dbShop = shopService.selectByPrimaryKey(dbOrder.getShopId());
        Merchant dbMerchant = merchantService.selectByPrimaryKey(dbShop.getMerchantId());
        Member bindMember = memberService.selectByPrimaryKey(dbMerchant.getMemberId());
        if (bindMember != null) {
            //发送微信公众号消息通知商家
            String title = "尊敬的商家，您有一个订单已被用户取消，为避免损失，请不要继续备货哦 - 取餐号" + dbOrder.getQueueNo();
            String addressStr = dbOrder.getContactProvince() + dbOrder.getContactCity() + dbOrder.getContactArea() + dbOrder.getContactStreet() + dbOrder.getContactHouseNumber();
            String orderDescription = dbOrder.getShoppingWay() == Quantity.INT_1
                    ? ("自取订单 - 取餐号" + dbOrder.getQueueNo())
                    : ("配送订单 - 取餐号" + dbOrder.getQueueNo() + " - " + addressStr);
            String contacts = dbOrder.getContactRealname().concat(" - ").concat(dbOrder.getContactPhone());
            String remark = "取消原因：" + getRefundReasonText(insertOrderRefund.getRefundReason());
            wxPublicPlatformNotifyService.sendOrderCancelMessageForMerchant(bindMember.getWxPublicPlatformOpenId(), title, orderDescription, dbOrder.getActualPrice() + "元", insertOrderRefund.getRefundAmount() + "元", insertOrderRefund.getCreateTime(), contacts, remark);
        } else {
            log.debug(dbOrder.getShopName() + "还未绑定小程序账号，发送订单取消通知失败");
        }

        //商家端中心进行语音提醒
        Boolean isOpenOrderAudio = dbShop.getIsOpenOrderAudio();
        if (isOpenOrderAudio) {
            webSocketService.pushMessage(dbMerchant.getMobile(), BusinessType.ORDER_APPLY_REFUND);
        }
    }


    @Override
    public void applyRefund(OrderParam param) throws IOException {
        BasicResult basicResult = new BasicResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //手动将JSON字符串转化为对象
        List<OrderRefundGoods> orderRefundGoodsList = org.apache.commons.lang3.StringUtils.isNotBlank(param.getOrderRefundGoodsListStr()) ? GsonUtils.toList(param.getOrderRefundGoodsListStr(), OrderRefundGoods.class) : null;
        log.debug("\n\norderRefundGoodsListStr : " + param.getOrderRefundGoodsListStr());
        if(orderRefundGoodsList != null){
            for (OrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
                log.debug("\n\norderDetailId : " + orderRefundGoods.getOrderDetailId() + " -- number : " + orderRefundGoods.getNumber());
            }
        }

        Order dbOrder = orderMapper.selectByPrimaryKey(param.getId());
        if(dbOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }
        if(!dbOrder.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("该订单不是你的，不允许取消");
        }

        //订单支付后24小时(1天)内 & 订单状态不等于(1=未付款 7=售后处理中 8=已退款(废弃选项) 9=售后处理完成 10=已取消(未支付) 11=已取消(已支付))时，用户可以申请退款；如果可以无责取消订单，则无需显示申请退款按钮；
        OrderParam orderParam = new OrderParam();
        orderParam.setId(dbOrder.getId());
        boolean isAllowApplyRefund = orderMapper.getIsAllowApplyRefund(orderParam);
        if(!isAllowApplyRefund){
            throw new StoneCustomerException("该订单不允许申请退款");
        }

        if(orderRefundGoodsList==null || orderRefundGoodsList.isEmpty()){
            throw new StoneCustomerException("订单退款-商品参数丢失");
        }

        //TODO-部分退款/全额退款不退包装费？配送费肯定是不退的
        //TODO-全额退款是否退还配送费？ -- 暂时退还
        //TODO-没有全额退款 和 部分退款的说法
        //TODO-目前一律不退还包装费与配送费，商品本身的价格
        //TODO-还要考虑用在该订单上的满减规则、优惠券
        //校验退款金额是否正确
        BigDecimal goodsAmount = BigDecimal.ZERO;

        Boolean isUsedCoupons = false;

        BigDecimal packingCharges = BigDecimal.ZERO; //包装费

        Boolean isAllAmountRefund = false;

        int goodsTotalQuantity = 0;

        for (OrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
            OrderDetail orderDetail = orderDetailService.selectByPrimaryKey(orderRefundGoods.getOrderDetailId());
            if(orderDetail == null){
                throw new StoneCustomerException("数据异常，请稍后重试");
            }

            log.debug("\n\norderRefundGoods : " + orderRefundGoods + " -- number : " + orderRefundGoods.getNumber());
            log.debug("\n\norderDetail : " + orderDetail + " -- number : " + orderDetail.getNumber());


            if(orderDetail.getIsUsedCoupons()){
                isUsedCoupons = true;
            }

            //校验退款数量是否正确
            if(orderRefundGoods.getNumber().compareTo(0)<=0 || orderRefundGoods.getNumber().compareTo(orderDetail.getNumber()) > 0){
                throw new StoneCustomerException(orderDetail.getGoodsName() + "的退款数量错误");
            }
            //累加退还金额
            BigDecimal subtotal = orderDetail.getPrice().multiply(BigDecimal.valueOf(orderRefundGoods.getNumber())).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
            goodsAmount = goodsAmount.add(subtotal);

            //累计包装费
            BigDecimal totalPackingCharges = orderDetail.getPackingCharges().multiply(BigDecimal.valueOf(orderRefundGoods.getNumber())).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
            packingCharges = packingCharges.add(totalPackingCharges);

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

        if(goodsTotalQuantity == dbOrder.getGoodsTotalQuantity()){
            isAllAmountRefund = true;
        }

        log.debug("\n\n退款商品总金额 : " + goodsAmount);
        log.debug("\n\n退款商品总包装费 : " + packingCharges);

        BigDecimal refundAmount = goodsAmount.add(packingCharges);


        //1）优惠券 只退 用了该优惠券的商品，如果使用了优惠券的商品购买了3件，那么退第1件的时候，就按照使用了优惠券的商品计算
        if(isUsedCoupons){
            refundAmount = refundAmount.subtract(dbOrder.getCouponsDiscountPrice()).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
        }
        log.debug("\n\n减去优惠券的折扣后为 : " + refundAmount);

        //2）退款金额+包装费-优惠券的优惠金额 凑足了满减，给他退减去的金额
        /*Boolean isUsedFullReductionRule = false;
        if(dbOrder.getFullReductionRuleId() !=null ){
            if(refundAmount.compareTo(dbOrder.getLimitedPrice()) >= 0){
                refundAmount = refundAmount.subtract(dbOrder.getReducedPrice()).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
                isUsedFullReductionRule = true;
                log.debug("\n\n减去满减后为 : " + refundAmount);
            }else{
                log.debug("\n\n退款商品金额不满足满减");
            }
        }else{
            log.debug("\n\n该订单未使用满减");
        }*/

        //TODO(MARK)：用商品总价格+总包装费来判断是否满足满减规则；如果不满足，在计算商品分摊金额的时候只算商品本身价格，不算包装费；
        //2）如果商品总金额不满足满减条件，则需要对商品进行满减优惠分摊金额计算；否则无需计算分摊金额，直接用商品总金额减去满减优惠就行了；
        Boolean isUsedFullReductionRule = false;
        if(dbOrder.getFullReductionRuleId() != null){
            if(refundAmount.compareTo(dbOrder.getLimitedPrice()) >= 0){
                //满足满减条件
                refundAmount = refundAmount.subtract(dbOrder.getReducedPrice()).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
                isUsedFullReductionRule = true;
                log.debug("\n\n退款商品金额满足满减");
                log.debug("\n\n减去满减后为 : " + refundAmount);
            }else{
                BigDecimal fullReductionRatio = dbOrder.getReducedPrice().divide(dbOrder.getLimitedPrice(), Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
                for (OrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
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

        //4）只有全部商品 才能退配送费
        Boolean isRefundDeliveryFee = false;
        if(isAllAmountRefund){
            refundAmount = refundAmount.add(dbOrder.getDeliveryFee());
            isRefundDeliveryFee = true;
        }

        log.debug("\n\n前端传递退款金额 : " + param.getOrderRefund().getRefundAmount());
        log.debug("\n\n后端核算退款金额 : " + refundAmount);
        if(refundAmount.compareTo(param.getOrderRefund().getRefundAmount()) != 0){
            throw new StoneCustomerException("退款金额计算错误，请稍后重试");
        }

        //判断退款金额不能 高于 实际支付金额
        if(refundAmount.compareTo(dbOrder.getActualPrice()) > 0){
            log.error("\n\n退款金额 高于 实际支付金额");
            throw new StoneCustomerException("退款金额计算错误，请稍后重试");
        }
        //判断是全额退款/部分退款
        if(refundAmount.compareTo(dbOrder.getActualPrice()) == 0){
            param.getOrderRefund().setRefundWay(Quantity.INT_1);
        }else{
            param.getOrderRefund().setRefundWay(Quantity.INT_2);
        }

        //修改订单记录状态
        Order updateOrder = new Order();
        updateOrder.setId(dbOrder.getId());
        updateOrder.setStatus(Quantity.INT_7);
        orderMapper.updateByPrimaryKeySelective(updateOrder);

//        //查询订单对应的订单商品详情数据
//        List<OrderDetail> orderDetailList = orderDetailService.selectByOrderId(id);
//
//        //恢复订单对应的商品库存 (对应规格的库存怎么修改)
//        for(OrderDetail orderDetail : orderDetailList){
//            int goodsId = orderDetail.getGoodsId();
//            int number = orderDetail.getNumber();
//            //增加商品库存
//            goodsService.increaseStock(goodsId, number);
//        }

        //查找该订单对应的交易记录
        MemberTradeRecord memberTradeRecord = memberTradeRecordService.selectByPrimaryKey(dbOrder.getTradeId());
        int refundAccount = memberTradeRecord.getPaymentMode() == Quantity.INT_1
                ? Quantity.INT_1 :
                (memberTradeRecord.getPaymentMode() == Quantity.INT_2 ? Quantity.INT_2
                        : (memberTradeRecord.getPaymentMode() == Quantity.INT_3 ? Quantity.INT_3 : Quantity.INT_0));

        //添加退款记录
        OrderRefund insertOrderRefund = new OrderRefund();
        insertOrderRefund.setOrderId(dbOrder.getId());
        insertOrderRefund.setType(Quantity.INT_2);
        insertOrderRefund.setRefundWay(param.getOrderRefund().getRefundWay());
        insertOrderRefund.setRefundReason(param.getOrderRefund().getRefundReason());
        insertOrderRefund.setRefundReasonDescription(param.getOrderRefund().getRefundReasonDescription());
        insertOrderRefund.setEvidenceImages(param.getOrderRefund().getEvidenceImages());
        insertOrderRefund.setRefundAmount(param.getOrderRefund().getRefundAmount());
        insertOrderRefund.setRefundAccount(refundAccount);
        insertOrderRefund.setStatus(Quantity.INT_2); //跳过第一步-退款申请已提交，直接到等待商家处理
        insertOrderRefund.setGoodsTotalQuantity(goodsTotalQuantity);
        insertOrderRefund.setGoodsTotalPrice(goodsAmount);
        insertOrderRefund.setPackingCharges(packingCharges);
        insertOrderRefund.setIsRefundDeliveryFee(isRefundDeliveryFee);
        insertOrderRefund.setDeliveryFee(dbOrder.getDeliveryFee());
        insertOrderRefund.setIsUsedCoupons(isUsedCoupons);
        insertOrderRefund.setIsUsedFullReductionRule(isUsedFullReductionRule);
        insertOrderRefund.setCreateTime(new Date());
        insertOrderRefund.setUpdateTime(new Date());
        orderRefundService.insertSelective(insertOrderRefund);

        //添加订单部分退款-商品详情信息
        orderRefundGoodsList.forEach(orderRefundGoods -> {
            orderRefundGoods.setOrderRefundId(insertOrderRefund.getId());
            orderRefundGoodsService.insertSelective(orderRefundGoods);
        });

        //添加退款流程
        String refundReasonText = getRefundReasonText(param.getOrderRefund().getRefundReason());
        if(StringUtils.isNotBlank(param.getOrderRefund().getRefundReasonDescription())){
            refundReasonText += " -- " + param.getOrderRefund().getRefundReasonDescription();
        }
        OrderRefundProcess orderRefundProcess = new OrderRefundProcess();
        orderRefundProcess.setOrderRefundId(insertOrderRefund.getId());
        orderRefundProcess.setName("退款申请已提交");
        orderRefundProcess.setDescription("退款原因: ".concat(refundReasonText));
        orderRefundProcess.setCreateTime(new Date());
        orderRefundProcessService.insertSelective(orderRefundProcess);

        OrderRefundProcess orderRefundProcess_second = new OrderRefundProcess();
        orderRefundProcess_second.setOrderRefundId(insertOrderRefund.getId());
        orderRefundProcess_second.setName("等待商家处理");
        orderRefundProcess_second.setDescription("");
        orderRefundProcess_second.setCreateTime(DateUtilsPlus.addSeconds(new Date(), 5));
        orderRefundProcessService.insertSelective(orderRefundProcess_second);

        //获取该订单对应的商家信息
        Shop dbShop = shopService.selectByPrimaryKey(dbOrder.getShopId());
        Merchant dbMerchant = merchantService.selectByPrimaryKey(dbShop.getMerchantId());
        Member bindMember = memberService.selectByPrimaryKey(dbMerchant.getMemberId());
        if(bindMember != null){
            //如果商品明细内容过长，则需要分多次发送公众号消息
            //字数：38*4=152 -> 112
            String goodsDescription = "";
            boolean isFirstSend = true; //是否为第一次发送

            for (OrderRefundGoods orderRefundGoods : orderRefundGoodsList) {
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
                    String refundWayText = insertOrderRefund.getRefundWay() == Quantity.INT_1 ? "全额退款" : "部分退款";
                    String title = isFirstSend
                            ? "尊敬的商家，您有一个订单已被用户申请退款，请及时处理 - 取餐号" + dbOrder.getQueueNo()
                            : "订单退款商品明细服务通知 - 取餐号" + dbOrder.getQueueNo();
                    if(isFirstSend){
                        String addressStr = dbOrder.getContactProvince() + dbOrder.getContactCity() + dbOrder.getContactArea() + dbOrder.getContactStreet() + dbOrder.getContactHouseNumber();
                        String orderDescription = dbOrder.getShoppingWay() == Quantity.INT_1
                                ? ("自取订单 - 取餐号" + dbOrder.getQueueNo())
                                : ("配送订单 - 取餐号" + dbOrder.getQueueNo() + " - " + addressStr);
                        String contacts = dbOrder.getContactRealname().concat(" - ").concat(dbOrder.getContactPhone());
                        String cancelReason = "取消原因：" + getRefundReasonText(insertOrderRefund.getRefundReason());
                        if(StringUtils.isNotBlank(insertOrderRefund.getRefundReasonDescription())){
                            cancelReason = cancelReason + "-" + insertOrderRefund.getRefundReasonDescription();
                        }
                        String remark = refundWayText + "，订单金额："+dbOrder.getActualPrice()+"，退款金额："+insertOrderRefund.getRefundAmount();
                        wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(bindMember.getWxPublicPlatformOpenId(), title, goodsDescription, dbOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
                    }else{
                        String addressStr = "";
                        String orderDescription = "";
                        String contacts = "";
                        String cancelReason = "";
                        String remark = "";
                        wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(bindMember.getWxPublicPlatformOpenId(), title, goodsDescription, dbOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
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
                    String refundWayText = insertOrderRefund.getRefundWay() == Quantity.INT_1 ? "全额退款" : "部分退款";
                    String title = isFirstSend
                            ? "尊敬的商家，您有一个订单已被用户申请退款，请及时处理 - 取餐号" + dbOrder.getQueueNo()
                            : "订单退款商品明细服务通知 - 取餐号" + dbOrder.getQueueNo();
                    if(isFirstSend){
                        String addressStr = dbOrder.getContactProvince() + dbOrder.getContactCity() + dbOrder.getContactArea() + dbOrder.getContactStreet() + dbOrder.getContactHouseNumber();
                        String orderDescription = dbOrder.getShoppingWay() == Quantity.INT_1
                                ? ("自取订单 - 取餐号" + dbOrder.getQueueNo())
                                : ("配送订单 - 取餐号" + dbOrder.getQueueNo() + " - " + addressStr);
                        String contacts = dbOrder.getContactRealname().concat(" - ").concat(dbOrder.getContactPhone());
                        String cancelReason = "取消原因：" + getRefundReasonText(insertOrderRefund.getRefundReason());
                        if(StringUtils.isNotBlank(insertOrderRefund.getRefundReasonDescription())){
                            cancelReason = cancelReason + "-" + insertOrderRefund.getRefundReasonDescription();
                        }
                        String remark = refundWayText + "，订单金额："+dbOrder.getActualPrice()+"，退款金额："+insertOrderRefund.getRefundAmount();
                        wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(bindMember.getWxPublicPlatformOpenId(), title, goodsDescription, dbOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
                    }else{
                        String addressStr = "";
                        String orderDescription = "";
                        String contacts = "";
                        String cancelReason = "";
                        String remark = "";
                        wxPublicPlatformNotifyService.sendOrderRefundMessageForMerchant(bindMember.getWxPublicPlatformOpenId(), title, goodsDescription, dbOrder.getCreateTime(), orderDescription, contacts, cancelReason, remark);
                    }
                    goodsDescription = "";
                    isFirstSend = false;
                }
            }
        }else{
            log.debug(dbOrder.getShopName() + "还未绑定小程序账号，发送订单退款提醒失败");
        }

        //商家端中心进行语音提醒
        Boolean isOpenOrderAudio = dbShop.getIsOpenOrderAudio();
        if(isOpenOrderAudio){
            webSocketService.pushMessage(dbMerchant.getMobile(), BusinessType.ORDER_APPLY_REFUND);
        }
    }

    @Override
    public void confirmReceipt(OrderParam param) {
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        Order dbOrder = orderMapper.selectByPrimaryKey(param.getId());
        if(dbOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }
        if(!dbOrder.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("该订单不是你的，不允许修改");
        }
        // 当订单状态处于已签收，才可以执行确认收货操作
        if(dbOrder.getStatus() != Quantity.INT_3){
            throw new StoneCustomerException("该订单状态不正确，不允许修改");
        }

        // 更新Order数据
        Order updateOrder = new Order();
        updateOrder.setId(dbOrder.getId());
        updateOrder.setStatus(Quantity.INT_8);
        orderMapper.updateByPrimaryKeySelective(updateOrder);
    }

    public List<Order> selectByExample(OrderExample example){
        return orderMapper.selectByExample(example);
    }

    public Order selectByPrimaryKey(Integer id){
        return orderMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(Order record, OrderExample example){
        orderMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(Order record){
        orderMapper.updateByPrimaryKeySelective(record);
    }

    public Page<Order> getListByPageWithAsc(OrderParam param) {
        Page<Order> page = orderMapper.getListByPageWithAsc(new Page<>(param.getPageNo(), param.getPageSize()), param);
        return page;
    }

    public Page<Map<String, Object>> getListByPageWithDesc(OrderParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //只查询当前登录用户的订单信息
        param.setMemberId(loginMember.getId());
        param.setIsDeleted(false);

        Page<Map<String, Object>> page = orderMapper.getListByPageWithDesc(new Page<>(param.getPageNo(), param.getPageSize()), param);


        //如果订单已评价 或 订单已完成超过14天 或 订单非已完成状态，则不允许评价
        page.getRecords().forEach(map -> {
            if(((long) map.get("isAllowAppraise")) == 1L){
                int status = (int) map.get("status");
                Date createTime = (Date) map.get("createTime");
                if (status!=Quantity.INT_6 || DateUtilsPlus.diffDays(new Date(), createTime)>14){
                    map.put("isAllowAppraise", 0L);
                }
            }

            //如果可以无责取消订单，则无需显示申请退款按钮
            if(((long) map.get("isAllowCancelNoReason")) == 1L){
                map.put("isAllowApplyRefund", 0L);
            }
        });

        //订单支付后1分钟内 & 订单状态处于待处理时(自取订单是待处理，配送订单是待配送)，用户可以无责取消订单；

        //订单支付后24小时(1天)内 & 订单状态不等于(1=未付款 7=售后处理中 8=已退款(废弃选项) 9=售后处理完成 10=已取消(未支付) 11=已取消(已支付))时，用户可以申请退款；如果可以无责取消订单，则无需显示申请退款按钮；


        return page;
    }

    @Override
    public Order selectByOrderNo(String orderNo) {
        return orderMapper.selectByOrderNo(orderNo);
    }

    @Override
    public void closeOverdueOrder(Integer id) {
        //五分钟内未付款的订单会被自动关闭
        Order dbOrder = orderMapper.selectById(id);
        if(dbOrder!=null && dbOrder.getStatus().equals(Order.STATUS_OF_WAIT_PAYMENT)){
            //修改订单状态为已取消(未支付)
            Order updateOrder = new Order();
            updateOrder.setId(id);
            updateOrder.setStatus(Order.STATUS_OF_CANCLED_NOT_PAY);
            updateOrder.setCancelReason(Order.CANCEL_REASON_OF_OVERDUE);
            updateOrder.setUpdateTime(new Date());
            orderMapper.updateById(updateOrder);

            //退回使用的优惠卷
            Integer couponsMemberRelationId = dbOrder.getCouponsMemberRelationId();
            if (couponsMemberRelationId != null) {
                couponsMemberRelationService.updateCouponsUsed(couponsMemberRelationId, false);
            }
        }
    }

    @Override
    public void autoCompletedOrder(Integer id) {
        //订单支付1个小时后，如果订单未取消/未申请退款，自动将状态更改为已完成
        Date overdueTime = DateUtilsPlus.addMinutes(new Date(), Quantity.INT_MINUS_60);
        orderMapper.updateFinish(overdueTime, new Date(), Quantity.INT_6);
    }

    @Override
    public void printRceceipts(Integer id) {

        Order order=orderMapper.selectByPrimaryKey(id);
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
        List<OrderDetail> orderDetails = orderDetailService.selectByOrderId(id);
       /* for(OrderDetail OrderDetail:orderDetails){
            number=OrderDetail.getNumber();
            specList=OrderDetail.getSpecList();
            PrintUtils
        }*/
      //  PrintUtils.print(orderDetails);
    }

    @Override
    public int batchUpdateIsPrintedTrue(List<Integer> idList) {
        return orderMapper.batchUpdateIsPrintedTrue(idList);
    }

    @Override
    public Integer getNextQueueNo() {
        Integer maxQueueNo = orderMapper.findMaxQueueNo();
        Integer queueNo = maxQueueNo==null?1: maxQueueNo+ 1;
        return queueNo;
    }

    public Page<Order> getListByTodayOrderWithAsc(OrderParam param) {
        Page<Order> page = orderMapper.getListByTodayOrderWithAsc(new Page<>(param.getPageNo(), param.getPageSize()), param);
        return page;
    }

    @Override
    public void paymentNotify(String outTradeNo) throws IOException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Order dbOrder = orderMapper.selectByOrderNo(outTradeNo);
        if(dbOrder == null){
            log.error("该商户单号不存在，回调逻辑处理失败");
            return;
        }

        //自取订单分配到待处理，外卖订单分配到待配送
        int status = 0;
        if(dbOrder.getShoppingWay() == Quantity.INT_1){
            status = Quantity.INT_2;
        }else{
            status = Quantity.INT_4;
        }
        //更新订单的状态
        Order updateOrder = new Order();
        updateOrder.setId(dbOrder.getId());
        updateOrder.setStatus(status);
        updateOrder.setPaymentSuccessTime(new Date());
        updateOrder.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKeySelective(updateOrder);

        //减去使用的原料
        List<OrderDetail> orderDetailList = orderDetailService.selectByOrderId(dbOrder.getId());
//        rawmaterialRelationService.updateRawmaterialConsumedQuantityByOrderDetailList(orderDetailList);

        // 获取下单积分量
        Setting setting= settingService.selectCurrent();
        BigDecimal settingNum = setting.getPurchaseRewardPoints();
        settingNum = settingNum == null ? BigDecimal.ZERO : settingNum;

        //计算奖励积分数
        Integer goodsNum = dbOrder.getGoodsTotalQuantity();
        goodsNum = goodsNum == null ? 0 : goodsNum;
        BigDecimal givePoints = settingNum.multiply(BigDecimal.valueOf(goodsNum).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP));

        Member dbMember = memberService.selectByPrimaryKey(dbOrder.getMemberId());
        //生成积分账单 -- 未到账积分
        if(givePoints.compareTo(BigDecimal.ZERO) > 0){
            //获取用户当前积分数 -- 未到账积分
            BigDecimal unreceivedPointsNum = dbMember.getUnreceivedPoints().add(givePoints);
            //修改用户的积分数
            Member member = new Member();
            member.setId(dbOrder.getMemberId());
            member.setUnreceivedPoints(unreceivedPointsNum);
            memberService.updateByPrimaryKeySelective(member);
            dbMember = memberService.selectByPrimaryKey(dbOrder.getMemberId());

            MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
            memberBillingRecord.setMemberId(dbOrder.getMemberId());
            memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_UNRECEIVED_POINTS);
            memberBillingRecord.setType(MemberBillingRecord.TYPE_ORDER_REWARD_POINTS);
            memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
            memberBillingRecord.setNumber(givePoints);
            memberBillingRecord.setMessage("下单奖励积分");
            memberBillingRecord.setOrderId(dbOrder.getId());
            memberBillingRecord.setCreateTime(new Date());
            memberBillingRecordService.insertSelective(memberBillingRecord);
        }

        //商品月销量和总销量修改
        for (OrderDetail orderDetail : orderDetailList) {
            Integer num = orderDetail.getNumber();
            Integer goodsId = orderDetail.getGoodsId();
            goodsService.updateSales(goodsId, num);
        }

        Shop dbShop = shopService.selectByPrimaryKey(dbOrder.getShopId());

        //增加商家-用户下单冻结资金
        Merchant dbMerchant = merchantService.selectByPrimaryKey(dbShop.getMerchantId());
        if(dbMerchant == null){
            throw new StoneCustomerException("该商家信息不存在");
        }
        BigDecimal updateOrderFrozenBalance = dbMerchant.getOrderFrozenBalance().add(dbOrder.getMerchantIncome()).setScale(2, BigDecimal.ROUND_HALF_UP);
        Merchant updateMerchant = new Merchant();
        updateMerchant.setId(dbMerchant.getId());
        updateMerchant.setOrderFrozenBalance(updateOrderFrozenBalance);
        updateMerchant.setUpdateTime(new Date());
        merchantService.updateByPrimaryKeySelective(updateMerchant);

        //增加商家账单记录
        MerchantBillingRecord merchantBillingRecord = new MerchantBillingRecord();
        merchantBillingRecord.setMerchantId(dbShop.getMerchantId());
        merchantBillingRecord.setType(Quantity.INT_1);
        merchantBillingRecord.setOperateType(Quantity.INT_1);
        merchantBillingRecord.setCoinType(Quantity.INT_1);
        merchantBillingRecord.setNumber(dbOrder.getMerchantIncome());
        merchantBillingRecord.setMessage("订单收入 -- 订单号" + dbOrder.getOrderNo());
        merchantBillingRecord.setCreateTime(new Date());
        merchantBillingRecordService.insertSelective(merchantBillingRecord);

        //TODO-插入骑手配送费账单记录，由于目前是商家自配送，所以配送费要打到商家余额中
        if(dbOrder.getShoppingWay()==Quantity.INT_2 && dbOrder.getDeliveryWay()==Quantity.INT_1){
            //增加商家-用户下单冻结资金
            dbMerchant = merchantService.selectByPrimaryKey(dbShop.getMerchantId());
            if(dbMerchant == null){
                throw new StoneCustomerException("该商家信息不存在");
            }
            updateOrderFrozenBalance = dbMerchant.getOrderFrozenBalance().add(dbOrder.getCourierIncome()).setScale(2, BigDecimal.ROUND_HALF_UP);
            updateMerchant = new Merchant();
            updateMerchant.setId(dbMerchant.getId());
            updateMerchant.setOrderFrozenBalance(updateOrderFrozenBalance);
            updateMerchant.setUpdateTime(new Date());
            merchantService.updateByPrimaryKeySelective(updateMerchant);

            //增加商家账单记录
            merchantBillingRecord = new MerchantBillingRecord();
            merchantBillingRecord.setMerchantId(dbShop.getMerchantId());
            merchantBillingRecord.setType(Quantity.INT_6);
            merchantBillingRecord.setOperateType(Quantity.INT_1);
            merchantBillingRecord.setCoinType(Quantity.INT_1);
            merchantBillingRecord.setNumber(dbOrder.getCourierIncome());
            merchantBillingRecord.setMessage("商家自配送-配送费收入 -- 订单号" + dbOrder.getOrderNo());
            merchantBillingRecord.setCreateTime(new Date());
            merchantBillingRecordService.insertSelective(merchantBillingRecord);
        }else{

        }

        //TODO-是否需要插入平台抽佣账单记录

      //获取该订单对应的商家信息
        Merchant merchant = merchantService.selectByPrimaryKey(dbShop.getMerchantId());
        Member bindMember = memberService.selectByPrimaryKey(merchant.getMemberId());
        if(bindMember != null){
            //如果商品明细内容过长，则需要分多次发送公众号消息
            //字数：38*4=152 -> 112
            String goodsDescription = "";
            boolean isFirstSend = true; //是否为第一次发送

            for (OrderDetail orderDetail : orderDetailList) {
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
                            ? "新订单服务通知 - 取餐号" + dbOrder.getQueueNo()
                            : "订单商品明细服务通知 - 取餐号" + dbOrder.getQueueNo();
                    if(isFirstSend){
                        String addressStr = dbOrder.getContactProvince() + dbOrder.getContactCity() + dbOrder.getContactArea() + dbOrder.getContactStreet() + dbOrder.getContactHouseNumber();
                        String deliveryAddress = dbOrder.getShoppingWay() == Quantity.INT_1
                                ? ("自取订单 - 取餐号" + dbOrder.getQueueNo())
                                : ("配送订单 - 取餐号" + dbOrder.getQueueNo() + " - " + addressStr);
                        String contacts = dbOrder.getContactRealname().concat(" - ").concat(dbOrder.getContactPhone());
                        String amountDescription = "已付款".concat(" - ").concat(dbOrder.getActualPrice()+"元");
                        String remark = StringUtils.isNotBlank(dbOrder.getRemark()) ? dbOrder.getRemark() : "无";
                        wxPublicPlatformNotifyService.sendNewOrderMessageForMerchant(bindMember.getWxPublicPlatformOpenId(), title, goodsDescription, dbOrder.getCreateTime(), deliveryAddress, contacts, amountDescription, remark);
                    }else{
                        String addressStr = "";
                        String deliveryAddress = "";
                        String contacts = "";
                        String amountDescription = "";
                        String remark = "";
                        wxPublicPlatformNotifyService.sendNewOrderMessageForMerchant(bindMember.getWxPublicPlatformOpenId(), title, goodsDescription, dbOrder.getCreateTime(), deliveryAddress, contacts, amountDescription, remark);
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
                            ? "新订单服务通知 - 取餐号" + dbOrder.getQueueNo()
                            : "订单商品明细服务通知 - 取餐号" + dbOrder.getQueueNo();
                    if(isFirstSend){
                        String addressStr = dbOrder.getContactProvince() + dbOrder.getContactCity() + dbOrder.getContactArea() + dbOrder.getContactStreet() + dbOrder.getContactHouseNumber();
                        String deliveryAddress = dbOrder.getShoppingWay() == Quantity.INT_1
                                ? ("自取订单 - 取餐号" + dbOrder.getQueueNo())
                                : ("配送订单 - 取餐号" + dbOrder.getQueueNo() + " - " + addressStr);
                        String contacts = dbOrder.getContactRealname().concat(" - ").concat(dbOrder.getContactPhone());
                        String amountDescription = "已付款".concat(" - ").concat(dbOrder.getActualPrice()+"元");
                        String remark = StringUtils.isNotBlank(dbOrder.getRemark()) ? dbOrder.getRemark() : "无";
                        wxPublicPlatformNotifyService.sendNewOrderMessageForMerchant(bindMember.getWxPublicPlatformOpenId(), title, goodsDescription, dbOrder.getCreateTime(), deliveryAddress, contacts, amountDescription, remark);
                    }else{
                        String addressStr = "";
                        String deliveryAddress = "";
                        String contacts = "";
                        String amountDescription = "";
                        String remark = "";
                        wxPublicPlatformNotifyService.sendNewOrderMessageForMerchant(bindMember.getWxPublicPlatformOpenId(), title, goodsDescription, dbOrder.getCreateTime(), deliveryAddress, contacts, amountDescription, remark);
                    }
                    goodsDescription = "";
                    isFirstSend = false;
                }
            }
        }else{
            log.debug(dbOrder.getShopName() + "还未绑定小程序账号，发送新订单通知失败");
        }

        //商家端中心进行语音提醒、订单打印
        Boolean isOpenOrderAudio = dbShop.getIsOpenOrderAudio();
        Boolean isOpenLocalPrint = dbShop.getIsOpenLocalPrint();
        if(isOpenOrderAudio && isOpenLocalPrint){
            //双开
            webSocketService.pushMessage(dbMerchant.getMobile(), BusinessType.NEW_ORDER_WITH_PRINT);
        }else if(isOpenOrderAudio && !isOpenLocalPrint){
            //语音开了，本地打印没开
            webSocketService.pushMessage(dbMerchant.getMobile(), BusinessType.NEW_ORDER);
        }else if(!isOpenOrderAudio && isOpenLocalPrint){
            //语音没开，本地打印开了
            webSocketService.pushMessage(dbMerchant.getMobile(), BusinessType.NEW_ORDER_PRINT);
        }else{
            //两个都没开
        }

        //平台邀请佣金
        BigDecimal inviteeConsumeCommissionPercent = setting.getInviteeConsumeCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal totalCommissionAmount = dbOrder.getActualPrice().multiply(inviteeConsumeCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
        BigDecimal inviterCommissionPercent, commissionAmount;
        String message;
        //给下单用户的邀请人发放佣金
        Map<String, Integer> inviterMap = memberInviteRelationService.selectInviter(dbOrder.getMemberId());
        if(inviterMap.containsKey("secondLevelInviter")){
            //有2个上级邀请人
            //发放下单用户佣金奖励
            inviterCommissionPercent = setting.getCasethreeOwnCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            message = "下单用户佣金奖励，来自" + dbMember.getMobile()+"-"+dbMember.getUsername();
            rewardService.giveInviterReward(dbMember.getId(), commissionAmount, MemberBillingRecord.TYPE_OWN_COMMISSION, message, dbOrder.getId());

            //发放一级邀请人佣金奖励
            inviterCommissionPercent = setting.getCasethreeFirstLevelInviterCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            message = "一级邀请人佣金奖励，来自" + dbMember.getMobile()+"-"+dbMember.getUsername();
            rewardService.giveInviterReward(inviterMap.get("firstLevelInviter"), commissionAmount, MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION, message, dbOrder.getId());

            //发放二级邀请人佣金奖励
            inviterCommissionPercent = setting.getCasethreeSecondLevelInviterCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            message = "二级邀请人佣金，来自" + dbMember.getMobile()+"-"+dbMember.getUsername();
            rewardService.giveInviterReward(inviterMap.get("secondLevelInviter"), commissionAmount, MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION, message, dbOrder.getId());

        }else if(inviterMap.containsKey("firstLevelInviter")){
            //有1个上级邀请人时
            //发放下单用户佣金奖励
            inviterCommissionPercent = setting.getCasetwoOwnCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            message = "下单用户佣金奖励，来自" + dbMember.getMobile()+"-"+dbMember.getUsername();
            rewardService.giveInviterReward(dbMember.getId(), commissionAmount, MemberBillingRecord.TYPE_OWN_COMMISSION, message, dbOrder.getId());

            //发放一级邀请人佣金奖励
            inviterCommissionPercent = setting.getCasetwoFirstLevelInviterCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            message = "一级邀请人佣金奖励，来自" + dbMember.getMobile()+"-"+dbMember.getUsername();
            rewardService.giveInviterReward(inviterMap.get("firstLevelInviter"), commissionAmount, MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION, message, dbOrder.getId());

        }else if(inviterMap.isEmpty()){
            //无上级邀请人时
            //发放下单用户佣金奖励
            inviterCommissionPercent = setting.getCaseoneOwnCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            message = "下单用户佣金奖励，来自" + dbMember.getMobile()+"-"+dbMember.getUsername();
            rewardService.giveInviterReward(dbMember.getId(), commissionAmount, MemberBillingRecord.TYPE_OWN_COMMISSION, message, dbOrder.getId());
        }

        /*//加入MQ延时队列，订单支付超过1个小时 且 订单未取消/未申请退款，则将订单修改为已完成
        Message message01 = new Message("TID_COMMON", "AUTO_COMPLETED_ORDER", JSON.toJSONString(dbOrder).getBytes());
        message01.setDelayTimeLevel(RocketMQConst.DELAY_TIME_LEVEL_1H);
        rocketMQTemplate.getProducer().send(message01);*/

        /*//加入MQ延时队列，订单支付超过10分钟，状态还是处于待处理、待配送，则给与商家中心PC端订单即将超时语音提醒
        Message message02 = new Message("TID_COMMON", "REMIND_OVERTIME_ORDER", JSON.toJSONString(dbOrder).getBytes());
        message02.setDelayTimeLevel(RocketMQConst.DELAY_TIME_LEVEL_10M);
        rocketMQTemplate.getProducer().send(message02);*/
    }

    @Override
    public void paymentNotifyOfChangeToDelivery(String changeToDeliveryOutTradeNo) {
        Order dbOrder = orderMapper.selectByChangeToDeliveryOutTradeNo(changeToDeliveryOutTradeNo);
        if(dbOrder == null){
            log.error("该自取订单改为配送的商户单号不存在，回调逻辑处理失败");
            return;
        }

        MemberTradeRecord dbMemberTradeRecord = memberTradeRecordService.selectByPrimaryKey(dbOrder.getChangeToDeliveryTradeId());
        if(dbMemberTradeRecord == null){
            log.error("用户交易记录不存在，回调逻辑处理失败");
            return;
        }

        Setting setting = settingService.selectCurrent();

        //修改订单信息：配送信息、订单基本信息、是否由自取订单改为配送->是
        //接单门店不能进行变动(是否需要考虑某些门店不支持配送的情况)，小票需要重新打印(显示自取订单改为配送标识)
        Order updateOrder = new Order();
        updateOrder.setId(dbOrder.getId());

        //填写配送费
        updateOrder.setDeliveryFee(dbMemberTradeRecord.getAmount());

        //修改实付款
        BigDecimal finalPrice = dbOrder.getActualPrice().add(dbMemberTradeRecord.getAmount());
        updateOrder.setActualPrice(finalPrice);

        //修改购物方式->配送
        updateOrder.setShoppingWay(Quantity.INT_2);

        //填写配送信息
        DeliveryAddress dbDeliveryAddress = deliveryAddressService.selectByPrimaryKey(dbOrder.getDeliveryAddressId());
        if(dbDeliveryAddress == null){
            log.error("收货地址不存在，回调逻辑处理失败");
            return;
        }

        //计算平台抽取费用等属性
        BigDecimal platformExtractRatio = setting.getOrderSystemExtractionRatio().divide(BigDecimal.valueOf(100), Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
//        BigDecimal platformExtractRatio = BigDecimal.ZERO.divide(BigDecimal.valueOf(100), Quantity.INT_2, BigDecimal.ROUND_HALF_UP);

        BigDecimal platformExtractPrice = finalPrice.multiply(platformExtractRatio).setScale(Quantity.INT_2,BigDecimal.ROUND_HALF_UP);
        BigDecimal platformDeliveryFee = platformExtractPrice;
        BigDecimal platformIncome = platformExtractPrice.subtract(platformDeliveryFee);
        BigDecimal courierIncome = platformDeliveryFee.add(dbOrder.getMerchantDeliveryFee()).add(updateOrder.getDeliveryFee());
        BigDecimal merchantIncome = finalPrice.subtract(platformExtractPrice).subtract(dbOrder.getMerchantDeliveryFee()).subtract(dbOrder.getDeliveryFee());

        updateOrder.setDeliveryAddressId(dbOrder.getDeliveryAddressId());
        updateOrder.setContactRealname(dbDeliveryAddress.getRealname());
        updateOrder.setContactPhone(dbDeliveryAddress.getPhone());
        updateOrder.setContactProvince(dbDeliveryAddress.getProvince());
        updateOrder.setContactCity(dbDeliveryAddress.getCity());
        updateOrder.setContactArea(dbDeliveryAddress.getArea());
        updateOrder.setContactStreet(dbDeliveryAddress.getStreet());
        updateOrder.setContactSex(dbDeliveryAddress.getSex());
        updateOrder.setPlatformExtractRatio(platformExtractRatio);
        updateOrder.setPlatformExtractPrice(platformExtractPrice);
        updateOrder.setPlatformDeliveryFee(platformDeliveryFee);
        updateOrder.setPlatformIncome(platformIncome);
        updateOrder.setCourierIncome(courierIncome);
        updateOrder.setMerchantIncome(merchantIncome);

        //不管原订单状态如何，现在订单状态统一改为4=待配送(已处理)
        updateOrder.setStatus(Quantity.INT_4);

        //修改是否已打印->否
        updateOrder.setIsPrinted(false);

        //是否由自取订单改为配送->是
        updateOrder.setIsChangeToDelivery(true);

        updateOrder.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKeySelective(updateOrder);

        Shop dbShop = shopService.selectByPrimaryKey(dbOrder.getShopId());

        if(dbOrder.getMerchantIncome().compareTo(updateOrder.getMerchantIncome()) > 0){
            //当前商家收入 低于 之前商家收入
            //减少商家的用户下单冻结资金
            Merchant dbMerchant = merchantService.selectByPrimaryKey(dbShop.getMerchantId());
            if(dbMerchant == null){
                throw new StoneCustomerException("该商家信息不存在");
            }
            BigDecimal number = dbOrder.getMerchantIncome().subtract(updateOrder.getMerchantIncome());
            BigDecimal updateOrderFrozenBalance = dbMerchant.getOrderFrozenBalance().subtract(number).setScale(2, BigDecimal.ROUND_HALF_UP);
            Merchant updateMerchant = new Merchant();
            updateMerchant.setId(dbMerchant.getId());
            updateMerchant.setOrderFrozenBalance(updateOrderFrozenBalance);
            updateMerchant.setUpdateTime(new Date());
            merchantService.updateByPrimaryKeySelective(updateMerchant);

            //增加商家账单记录
            MerchantBillingRecord merchantBillingRecord = new MerchantBillingRecord();
            merchantBillingRecord.setMerchantId(dbShop.getMerchantId());
            merchantBillingRecord.setType(Quantity.INT_4);
            merchantBillingRecord.setOperateType(Quantity.INT_2);
            merchantBillingRecord.setCoinType(Quantity.INT_1);
            merchantBillingRecord.setNumber(number);
            merchantBillingRecord.setMessage("自取订单改为配送，收入减少 -- 订单号" + dbOrder.getOrderNo());
            merchantBillingRecord.setCreateTime(new Date());
            merchantBillingRecordService.insertSelective(merchantBillingRecord);


        }else if(dbOrder.getMerchantIncome().compareTo(updateOrder.getMerchantIncome()) < 0){
            //当前商家收入 高于 之前商家收入
            //增加商家的用户下单冻结资金
            Merchant dbMerchant = merchantService.selectByPrimaryKey(dbShop.getMerchantId());
            if(dbMerchant == null){
                throw new StoneCustomerException("该商家信息不存在");
            }
            BigDecimal number = updateOrder.getMerchantIncome().subtract(dbOrder.getMerchantIncome());
            BigDecimal updateOrderFrozenBalance = dbMerchant.getOrderFrozenBalance().add(number).setScale(2, BigDecimal.ROUND_HALF_UP);
            Merchant updateMerchant = new Merchant();
            updateMerchant.setId(dbMerchant.getId());
            updateMerchant.setOrderFrozenBalance(updateOrderFrozenBalance);
            updateMerchant.setUpdateTime(new Date());
            merchantService.updateByPrimaryKeySelective(updateMerchant);

            //增加商家账单记录
            MerchantBillingRecord merchantBillingRecord = new MerchantBillingRecord();
            merchantBillingRecord.setMerchantId(dbShop.getMerchantId());
            merchantBillingRecord.setType(Quantity.INT_5);
            merchantBillingRecord.setOperateType(Quantity.INT_1);
            merchantBillingRecord.setCoinType(Quantity.INT_1);
            merchantBillingRecord.setNumber(number);
            merchantBillingRecord.setMessage("自取订单改为配送，收入增加 -- 订单号" + dbOrder.getOrderNo());
            merchantBillingRecord.setCreateTime(new Date());
            merchantBillingRecordService.insertSelective(merchantBillingRecord);
        }
    }

    @Override
    public Map countOrder(OrderParam order) {
        return orderMapper.countOrder(order);
    }

    @Override
    public int selectLatelyMonthlySalesByShopId(Date startTime, Date endTime, Integer shopId){
        Integer integer = orderMapper.selectLatelyMonthlySalesByShopId(startTime, endTime, shopId);
        return integer!=null ? integer : 0;
    }

    @Override
    public BigDecimal selectSumMerchantIncome(OrderParam param) {
        return orderMapper.selectSumMerchantIncome(param);
    }

    @Override
    public BigDecimal selectSumActualPrice(OrderParam param) {
        return orderMapper.selectSumActualPrice(param);
    }

    @Override
    public int selectCountCompleted(OrderParam param) {
        Integer integer = orderMapper.selectCountCompleted(param);
        return integer!=null ? integer : 0;
    }

    @Override
    public int selectCountPaid(OrderParam param) {
        Integer integer = orderMapper.selectCountPaid(param);
        return integer!=null ? integer : 0;
    }

    @Override
    public boolean getIsAllowCancelNoReason(OrderParam order) {
        return orderMapper.getIsAllowCancelNoReason(order);
    }

    @Override
    public boolean getIsAllowApplyRefund(OrderParam order) {
        return orderMapper.getIsAllowApplyRefund(order);
    }

    @Override
    public Page<Order> getAfterSalesListByPageWithAsc(OrderParam param) {
        Page<Order> page = orderMapper.getAfterSalesListByPageWithAsc(new Page<>(param.getPageNo(), param.getPageSize()), param);
        return page;
    }

    @Override
    public void updatePayOrderFrozenBalanceOfMerchant() {
        //检测状态处于已完成、售后处理完成 且 订单支付超过24小时(1天)的订单
        List<Order> orderList = orderMapper.selectByNeedPayOrderFrozenBalanceOfMerchant();
        orderList.forEach(order -> {
            BigDecimal amount = BigDecimal.ZERO;
            if(order.getStatus() == Quantity.INT_6){
                amount = order.getActualPrice();
            }else if(order.getStatus() == Quantity.INT_9){
                OrderRefund orderRefund = orderRefundService.selectByOrderId(order.getId());
                //如果退款订单是部分退款，才需要发放
                if(orderRefund.getRefundWay() == Quantity.INT_2){
                    amount = order.getActualPrice().subtract(orderRefund.getRefundAmount());
                }
            }

            Shop dbShop = shopService.selectByPrimaryKey(order.getShopId());
            Merchant dbMerchant = merchantService.selectByPrimaryKey(dbShop.getMerchantId());

            //增加商家的余额、可提现余额，减少商家的用户下单冻结资金
            BigDecimal updateBalance = dbMerchant.getBalance().add(amount).setScale(2, BigDecimal.ROUND_HALF_UP);;
            BigDecimal updateWithdrawableBalance = dbMerchant.getWithdrawableBalance().add(amount).setScale(2, BigDecimal.ROUND_HALF_UP);;
            BigDecimal updateOrderFrozenBalance = dbMerchant.getOrderFrozenBalance().subtract(amount).setScale(2, BigDecimal.ROUND_HALF_UP);;
            Merchant updateMerchant = new Merchant();
            updateMerchant.setId(dbMerchant.getId());
            updateMerchant.setBalance(updateBalance);
            updateMerchant.setWithdrawableBalance(updateWithdrawableBalance);
            updateMerchant.setOrderFrozenBalance(updateOrderFrozenBalance);
            updateMerchant.setUpdateTime(new Date());
            merchantService.updateByPrimaryKeySelective(updateMerchant);

            //修改订单的用户下单资金是否已转入商家余额状态
            Order updateOrder = new Order();
            updateOrder.setId(order.getId());
            updateOrder.setIsPayToMerchant(true);
            orderMapper.updateByPrimaryKeySelective(updateOrder);
        });
    }

    @Override
    public void remindOvertimeOrder(Integer id) throws IOException {
        //查询订单支付超过10分钟，状态还是处于待处理、待配送的订单
        List<Integer> shopIdList = orderMapper.selectShopIdByOvertimeOrder();
        if(shopIdList==null || shopIdList.isEmpty()){
            return;
        }

        ShopExample shopExample = new ShopExample();
        shopExample.createCriteria().andIdIn(shopIdList);
        List<Shop> shopList = shopService.selectByExample(shopExample);

        MerchantExample merchantExample = new MerchantExample();
        merchantExample.createCriteria().andShopIdIn(shopIdList);
        List<Merchant> merchantList = merchantService.selectByExample(merchantExample);

        Map<Integer, Shop> filterMap = new HashMap<>();
        for (Merchant merchant : merchantList) {
            for (Shop shop : shopList) {
                if(shop.getMerchantId().equals(merchant.getId())){
                    filterMap.put(merchant.getId(), shop);
                    break;
                }
            }
        }

        for (Merchant merchant : merchantList) {
            //商家端中心进行语音提醒
            Shop shop = filterMap.get(merchant.getId());
            Boolean isOpenOrderAudio = shop.getIsOpenOrderAudio();
            if(isOpenOrderAudio){
                webSocketService.pushMessage(merchant.getMobile(), BusinessType.ORDER_OVERTIME);
            }
        }
    }

    @Override
    public List<Map<String, Object>> selectStatisticOrder(Integer shopId) {
        return orderMapper.selectStatisticOrder(shopId);
    }

    @Override
    public String selectStartDateOrder(Integer shopId) {
        return orderMapper.selectStartDateOrder(shopId);
    }

    @Override
    public int selectCountPayers(OrderParam param) {
        return orderMapper.selectCountPayers(param);
    }

    @Override
    public int selectCountOrderPeoples(OrderParam param) {
        return orderMapper.selectCountOrderPeoples(param);
    }

    @Override
    public void updateRefundStatus(String out_trade_no){
        //微信退款服务成功后回调，修改相关业务表的退款状态,订单状态等
        Order dbOrder = this.selectByOrderNo(out_trade_no);

        //添加退款记录
        OrderRefund dbOrderRefund = orderRefundService.selectByOrderId(dbOrder.getId());
        /*OrderRefund updateOrderRefund = new OrderRefund();
        updateOrderRefund.setId(dbOrderRefund.getId());
        updateOrderRefund.setStatus(Quantity.INT_7);
        updateOrderRefund.setUpdateTime(new Date());
        orderRefundService.updateByPrimaryKeySelective(updateOrderRefund);*/

        //添加退款流程
        /*OrderRefundProcess orderRefundProcess = new OrderRefundProcess();
        orderRefundProcess.setOrderRefundId(dbOrderRefund.getId());
        orderRefundProcess.setName("退款成功");
        orderRefundProcess.setCreateTime(new Date());
        orderRefundProcessService.insertSelective(orderRefundProcess);*/

        //TODO-对订单退款金额进行划分
        if(dbOrderRefund.getRefundWay()==Quantity.INT_1 && dbOrderRefund.getIsRefundDeliveryFee()){
            //退还配送费 / 全额退款
            //减少商家-用户下单冻结资金
            Shop dbShop = shopService.selectByPrimaryKey(dbOrder.getShopId());
            Merchant dbMerchant = merchantService.selectByPrimaryKey(dbShop.getMerchantId());
            if(dbMerchant == null){
                throw new StoneCustomerException("该商家信息不存在");
            }
            BigDecimal updateOrderFrozenBalance = dbMerchant.getOrderFrozenBalance().subtract(dbOrder.getMerchantIncome()).setScale(2, BigDecimal.ROUND_HALF_UP);
            Merchant updateMerchant = new Merchant();
            updateMerchant.setId(dbMerchant.getId());
            updateMerchant.setOrderFrozenBalance(updateOrderFrozenBalance);
            updateMerchant.setUpdateTime(new Date());
            merchantService.updateByPrimaryKeySelective(updateMerchant);

            //增加商家账单记录
            MerchantBillingRecord merchantBillingRecord = new MerchantBillingRecord();
            merchantBillingRecord.setMerchantId(dbShop.getMerchantId());
            merchantBillingRecord.setType(dbOrderRefund.getType() == 1 ? Quantity.INT_7 : Quantity.INT_9);
            merchantBillingRecord.setOperateType(Quantity.INT_2);
            merchantBillingRecord.setCoinType(Quantity.INT_1);
            merchantBillingRecord.setNumber(dbOrder.getMerchantIncome());
            merchantBillingRecord.setMessage((dbOrderRefund.getType() == 1 ? "用户一分钟内取消订单" : "用户申请退款") + " -- 订单号" + dbOrder.getOrderNo());
            merchantBillingRecord.setCreateTime(new Date());
            merchantBillingRecordService.insertSelective(merchantBillingRecord);

            //减少商家-用户下单冻结资金 配送费退回
            dbMerchant = merchantService.selectByPrimaryKey(dbShop.getMerchantId());
            if(dbMerchant == null){
                throw new StoneCustomerException("该商家信息不存在");
            }
            updateOrderFrozenBalance = dbMerchant.getOrderFrozenBalance().subtract(dbOrder.getCourierIncome()).setScale(2, BigDecimal.ROUND_HALF_UP);
            updateMerchant = new Merchant();
            updateMerchant.setId(dbMerchant.getId());
            updateMerchant.setOrderFrozenBalance(updateOrderFrozenBalance);
            updateMerchant.setUpdateTime(new Date());
            merchantService.updateByPrimaryKeySelective(updateMerchant);

            //增加商家账单记录
            merchantBillingRecord = new MerchantBillingRecord();
            merchantBillingRecord.setMerchantId(dbShop.getMerchantId());
            merchantBillingRecord.setType(dbOrderRefund.getType() == 1 ? Quantity.INT_8 : Quantity.INT_10);
            merchantBillingRecord.setOperateType(Quantity.INT_2);
            merchantBillingRecord.setCoinType(Quantity.INT_1);
            merchantBillingRecord.setNumber(dbOrder.getCourierIncome());
            merchantBillingRecord.setMessage((dbOrderRefund.getType() == 1 ? "用户一分钟内取消订单-配送费退回" : "用户申请退款-配送费退回") + " -- 订单号" + dbOrder.getOrderNo());
            merchantBillingRecord.setCreateTime(new Date());
            merchantBillingRecordService.insertSelective(merchantBillingRecord);

        }else{
            //不退还配送费 / 部分退款
            //减少商家-用户下单冻结资金
            Shop dbShop = shopService.selectByPrimaryKey(dbOrder.getShopId());
            Merchant dbMerchant = merchantService.selectByPrimaryKey(dbShop.getMerchantId());
            if(dbMerchant == null){
                throw new StoneCustomerException("该商家信息不存在");
            }
            BigDecimal updateOrderFrozenBalance = dbMerchant.getOrderFrozenBalance().subtract(dbOrderRefund.getRefundAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
            Merchant updateMerchant = new Merchant();
            updateMerchant.setId(dbMerchant.getId());
            updateMerchant.setOrderFrozenBalance(updateOrderFrozenBalance);
            updateMerchant.setUpdateTime(new Date());
            merchantService.updateByPrimaryKeySelective(updateMerchant);

            //增加商家账单记录
            MerchantBillingRecord merchantBillingRecord = new MerchantBillingRecord();
            merchantBillingRecord.setMerchantId(dbShop.getMerchantId());
            merchantBillingRecord.setType(dbOrderRefund.getType() == 1 ? Quantity.INT_7 : Quantity.INT_9);
            merchantBillingRecord.setOperateType(Quantity.INT_2);
            merchantBillingRecord.setCoinType(Quantity.INT_1);
            merchantBillingRecord.setNumber(dbOrderRefund.getRefundAmount());
            merchantBillingRecord.setMessage((dbOrderRefund.getType() == 1 ? "用户一分钟内取消订单" : "用户申请退款") + " -- 订单号" + dbOrder.getOrderNo());
            merchantBillingRecord.setCreateTime(new Date());
            merchantBillingRecordService.insertSelective(merchantBillingRecord);
        }
    }

    @Override
    public Map<String, Object> selectSumField(OrderParam order) {
        return this.orderMapper.selectSumField(order);
    }

    @Override
    public int selectCountUnCompleted(OrderParam order) {
        Integer integer = this.orderMapper.selectCountUnCompleted(order);
        return integer!=null ? integer : 0;
    }

    @Override
    public int selectCountWaitHandle(OrderParam order) {
        Integer integer = this.orderMapper.selectCountWaitHandle(order);
        return integer!=null ? integer : 0;
    }

    @Override
    public OrderVo selectById(OrderParam param) {
        OrderVo orderVo = new OrderVo();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        Order dbOrder = orderMapper.selectByPrimaryKey(param.getId());
        if(dbOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }
        if(!dbOrder.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("该订单不是你的，不允许查看");
        }

        //查询订单对应的订单商品详情数据
        List<OrderDetail> orderDetailList = orderDetailService.selectByOrderId(param.getId());

        //如果订单已评价 或 订单已完成超过14天 或 订单非已完成状态，则不允许评价
        Map<String, Object> dbOrderMap = BeanUtils.beanToMap(dbOrder);
        Appraise appraise = new Appraise();
        appraise.setOrderId(dbOrder.getId());
        appraise.setMemberId(dbOrder.getMemberId());
        boolean isAllowAppraise = appraiseService.getIsAllowAppraise(appraise);
        if(isAllowAppraise){
            if (dbOrder.getStatus()!=Quantity.INT_6 || DateUtilsPlus.diffDays(new Date(), dbOrder.getCreateTime())>14){
                isAllowAppraise = false;
            }
        }
        dbOrderMap.put("isAllowAppraise", isAllowAppraise);

        //订单支付后1分钟内 & 订单状态处于待处理时(自取订单是待处理，配送订单是待配送)，用户可以无责取消订单；
        OrderParam orderParam = new OrderParam();
        orderParam.setId(dbOrder.getId());
        boolean isAllowCancelNoReason = orderMapper.getIsAllowCancelNoReason(orderParam);
        dbOrderMap.put("isAllowCancelNoReason", isAllowCancelNoReason);

        //订单支付后24小时(1天)内 & 订单状态不等于(1=未付款 7=售后处理中 8=已退款(废弃选项) 9=售后处理完成 10=已取消(未支付) 11=已取消(已支付))时，用户可以申请退款；如果可以无责取消订单，则无需显示申请退款按钮；
        boolean isAllowApplyRefund = orderMapper.getIsAllowApplyRefund(orderParam);
        dbOrderMap.put("isAllowApplyRefund", isAllowApplyRefund);

        //如果可以无责取消订单，则无需显示申请退款按钮
        if(isAllowCancelNoReason){
            dbOrderMap.put("isAllowApplyRefund", false);
        }

        //查询订单的退款进度
        OrderRefund orderRefund = orderRefundService.selectByOrderId(dbOrder.getId());
        if(orderRefund != null){
            dbOrderMap.put("isRefundOrder", true);
            dbOrderMap.put("refundStatus", orderRefund.getStatus());
        }else{
            dbOrderMap.put("isRefundOrder", false);
        }

        //返回商家电话
        Shop shop = shopService.selectByPrimaryKey(dbOrder.getShopId());
        dbOrderMap.put("shopContactPhone", shop.getContactPhone());

        orderVo.setOrder(dbOrderMap);
        orderVo.setOrderDetailList(orderDetailList);

        return orderVo;
    }

    @Override
    public OrderVo2 selectRefundProcess(OrderParam param) {
        OrderVo2 vo2 = new OrderVo2();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        Map resultMap = new HashMap();

        Order dbOrder = orderMapper.selectByPrimaryKey(param.getId());
        if(dbOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }
        if(!dbOrder.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("该订单不是你的，不允许查看");
        }
        if(dbOrder.getStatus()!=7 && dbOrder.getStatus()!=9 && dbOrder.getStatus()!=11){
            throw new StoneCustomerException("该订单无退款信息");
        }

        //查询订单退款信息
        OrderRefund orderRefund = orderRefundService.selectByOrderId(param.getId());

        //查询订单退款-商品详情数据
        List<OrderRefundGoods> orderRefundGoodsList = orderRefundGoodsService.selectByOrderRefundId(orderRefund.getId());

        //查询订单退款流程信息
        List<OrderRefundProcess> orderRefundProcessList = orderRefundProcessService.selectByOrderRefundId(orderRefund.getId());

        vo2.setOrderRefund(orderRefund);
        vo2.setOrderRefundGoodsList(orderRefundGoodsList);
        vo2.setOrderRefundProcessList(orderRefundProcessList);

        return vo2;
    }

    @Override
    public Map selectTabNumber(OrderParam param) {
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        List<Integer> statusList;
        OrderExample orderExample;

        //待付款 状态处于1=未付款
        statusList = new ArrayList<>();
        statusList.add(1);
        orderExample = new OrderExample();
        orderExample.createCriteria().andStatusIn(statusList).andMemberIdEqualTo(loginMember.getId()).andIsDeletedEqualTo(false);
        int waitPaymentNumber = orderMapper.countByExample(orderExample);

        //待收货 4=待配送(已处理) 5=已配送
        statusList = new ArrayList<>();
        statusList.add(4);
        statusList.add(5);
        orderExample = new OrderExample();
        orderExample.createCriteria().andStatusIn(statusList).andMemberIdEqualTo(loginMember.getId()).andIsDeletedEqualTo(false);
        int waitReceivedNumber = orderMapper.countByExample(orderExample);

        //待自提 2=待处理 3=待自取(已处理)
        statusList = new ArrayList<>();
        statusList.add(2);
        statusList.add(3);
        orderExample = new OrderExample();
        orderExample.createCriteria().andStatusIn(statusList).andMemberIdEqualTo(loginMember.getId()).andIsDeletedEqualTo(false);
        int waitPickedUp = orderMapper.countByExample(orderExample);

        //退款/售后 状态处于7=售后处理中 8=已退款(废弃选项)
        statusList = new ArrayList<>();
        statusList.add(7);
        statusList.add(8);
        orderExample = new OrderExample();
        orderExample.createCriteria().andStatusIn(statusList).andMemberIdEqualTo(loginMember.getId()).andIsDeletedEqualTo(false);
        int afterSalesNumber = orderMapper.countByExample(orderExample);

        Map map = new HashMap();
        map.put("waitPaymentNumber", waitPaymentNumber);
        map.put("waitReceivedNumber", waitReceivedNumber);
        map.put("waitPickedUp", waitPickedUp);
        map.put("afterSalesNumber", afterSalesNumber);
        return map;
    }

    @Override
    public void batchUpdateIsPrintedTrue(OrderParam param) {
        if(StringUtils.isEmpty(param.getIdListStr())){
            throw new StoneCustomerException("订单id不能为空");
        }
        List<Integer> idList = GsonUtils.toList(param.getIdListStr(), Integer.class);
        if(idList!=null && idList.size()>0){
            orderMapper.batchUpdateIsPrintedTrue(idList);
        }
    }

    @Override
    public Map selectAllTabWaitHandleNum(OrderParam param) {
        Map dataMap = new HashMap();

        //自取订单-待制作订单
        OrderExample example = new OrderExample();
        example.createCriteria().andShoppingWayEqualTo(Quantity.INT_1).andStatusEqualTo(Quantity.INT_2);
        int waitHandleNum = orderMapper.countByExample(example);
        dataMap.put("waitHandleNum", waitHandleNum);

        //自取订单-待自取订单
        example = new OrderExample();
        example.createCriteria().andShoppingWayEqualTo(Quantity.INT_1).andStatusEqualTo(Quantity.INT_3);
        int waitPickUpNum = orderMapper.countByExample(example);
        dataMap.put("waitPickUpNum", waitPickUpNum);

        //外卖订单-待配送订单
        example = new OrderExample();
        example.createCriteria().andShoppingWayEqualTo(Quantity.INT_2).andStatusEqualTo(Quantity.INT_4);
        int waitDeliveryNum = orderMapper.countByExample(example);
        dataMap.put("waitDeliveryNum", waitDeliveryNum);

        //外卖订单-已配送订单
        example = new OrderExample();
        example.createCriteria().andShoppingWayEqualTo(Quantity.INT_2).andStatusEqualTo(Quantity.INT_5);
        int deliveredNum = orderMapper.countByExample(example);
        dataMap.put("deliveredNum", deliveredNum);
        return dataMap;
    }

    @Override
    public List<Order> waitPrintOrderList(OrderParam param) {
        //排除掉状态为1=未付款、10=已取消的订单
        List<Integer> excludedStatusList = new ArrayList<>();
        excludedStatusList.add(Quantity.INT_1);
        excludedStatusList.add(Quantity.INT_10);
        OrderExample example = new OrderExample();
        example.createCriteria().andStatusNotIn(excludedStatusList).andIsPrintedEqualTo(false);
        List<Order> orders = orderMapper.selectByExample(example);
        return orders;
    }

    @Override
    public void auditAfterSalesOrder(OrderParam param) {
        if(param.getStatus() == Quantity.INT_2 && org.apache.commons.lang3.StringUtils.isBlank(param.getOpinion())){
            throw new StoneCustomerException("审核不通过时，审核意见不能为空");
        }

        Order dbOrder = orderMapper.selectByPrimaryKey(param.getId());
        if(dbOrder == null) throw new StoneCustomerException("该订单不存在");

        OrderRefund dbOrderRefund = orderRefundService.selectByOrderId(param.getId());
        if(dbOrderRefund == null) throw new StoneCustomerException("该订单无退款记录");
        if(dbOrderRefund.getStatus()!=Quantity.INT_4 && dbOrderRefund.getStatus()!=Quantity.INT_6){
            throw new StoneCustomerException("该售后订单已被处理过/等待商家处理，不允许操作");
        }

        //获取该订单的对应用户
        Member orderMember = memberService.selectByPrimaryKey(dbOrder.getMemberId());

        if(param.getStatus() == Quantity.INT_1){
            //审核通过

            //修改订单记录状态-售后处理完成
            Order updateOrder = new Order();
            updateOrder.setId(param.getId());
            updateOrder.setStatus(Quantity.INT_9);
            orderMapper.updateByPrimaryKeySelective(updateOrder);

            //进行订单自动退款操作
            boolean isRefundSuccess = false;
            if(dbOrder.getPaymentMode().equals(Quantity.INT_1)){
                //微信支付
                isRefundSuccess = wxPayService.refund(dbOrder.getOrderNo(), dbOrder.getActualPrice(), dbOrderRefund.getRefundAmount());

            }else if(dbOrder.getPaymentMode().equals(Quantity.INT_2)){
                //余额支付
                //增加用户的余额
                Member updateMember = new Member();
                updateMember.setId(orderMember.getId());
                updateMember.setBalance(orderMember.getBalance().add(dbOrderRefund.getRefundAmount()));
                updateMember.setTotalConsumeBalance(orderMember.getTotalConsumeBalance().subtract(dbOrderRefund.getRefundAmount()));
                memberService.updateByPrimaryKeySelective(updateMember);
                orderMember = memberService.selectByPrimaryKey(dbOrder.getMemberId());
                //添加账单记录
                MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
                memberBillingRecord.setMemberId(orderMember.getId());
                memberBillingRecord.setType(MemberBillingRecord.TYPE_ORDER_REFUND_RETURN_BALANCE);
                memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
                memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_BALANCE);
                memberBillingRecord.setNumber(dbOrderRefund.getRefundAmount());
                memberBillingRecord.setMessage("用户申请退款-余额退回");
                memberBillingRecord.setCreateTime(new Date());
                memberBillingRecordService.insertSelective(memberBillingRecord);

                //退款成功的操作
                updateRefundStatus(dbOrder.getOrderNo());

                isRefundSuccess = true;
            }
            if(!isRefundSuccess){
                throw new StoneCustomerException("退款失败，请联系管理员");
            }

            //5）只有在退了使用优惠券的商品时，优惠券才会被退回
            if(dbOrderRefund.getIsUsedCoupons()){
                //退回优惠卷
                Integer couponsMemberRelationId = dbOrder.getCouponsMemberRelationId();
                if (couponsMemberRelationId != null) {
                    couponsMemberRelationService.updateCouponsUsed(couponsMemberRelationId,false);
                }
            }

            //修改退款状态 -- 退款成功
            OrderRefund updateOrderRefund = new OrderRefund();
            updateOrderRefund.setId(dbOrderRefund.getId());
            updateOrderRefund.setStatus(Quantity.INT_7);
            orderRefundService.updateByPrimaryKeySelective(updateOrderRefund);

            //添加退款流程 -- 等待平台处理
            OrderRefundProcess orderRefundProcess = new OrderRefundProcess();
            orderRefundProcess.setOrderRefundId(dbOrderRefund.getId());
            orderRefundProcess.setName("等待平台处理");
            orderRefundProcess.setCreateTime(new Date());
            orderRefundProcessService.insertSelective(orderRefundProcess);

            //添加退款流程 -- 退款成功
            OrderRefundProcess orderRefundProcess_second = new OrderRefundProcess();
            orderRefundProcess_second.setOrderRefundId(dbOrderRefund.getId());
            orderRefundProcess_second.setName("退款成功");
            orderRefundProcess_second.setCreateTime(DateUtilsPlus.addSeconds(new Date(), 5));
            orderRefundProcessService.insertSelective(orderRefundProcess_second);

            //发送服务通知
            wxNotifyService.sendOrderRefundSuccessMessage(orderMember.getOpenId(), dbOrder.getShopName(), dbOrder.getOrderNo(), dbOrder.getDescription(), dbOrderRefund.getRefundAmount(), new Date());

            //退回下单奖励积分
            MemberBillingRecordExample example = new MemberBillingRecordExample();
            example.createCriteria().andTypeEqualTo(MemberBillingRecord.TYPE_ORDER_REWARD_POINTS)
                    .andOperateTypeEqualTo(MemberBillingRecord.OPERATE_TYPE_ADD)
                    .andCoinTypeEqualTo(MemberBillingRecord.COIN_TYPE_UNRECEIVED_POINTS)
                    .andOrderIdEqualTo(dbOrder.getId());
            List<MemberBillingRecord> list = memberBillingRecordService.selectByExample(example);
            if(!list.isEmpty()){
                MemberBillingRecord dbMemberBillingRecord = list.get(0);
                Member dbMember = memberService.selectByPrimaryKey(dbOrder.getMemberId());
                //获取用户当前积分数 -- 未到账积分
                BigDecimal unreceivedPointsNum = dbMember.getUnreceivedPoints().subtract(dbMemberBillingRecord.getNumber());
                //修改用户的积分数
                Member member = new Member();
                member.setId(dbOrder.getMemberId());
                member.setUnreceivedPoints(unreceivedPointsNum);
                memberService.updateByPrimaryKeySelective(member);
                dbMember = memberService.selectByPrimaryKey(dbOrder.getMemberId());

                MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
                memberBillingRecord.setMemberId(dbOrder.getMemberId());
                memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_UNRECEIVED_POINTS);
                memberBillingRecord.setType(MemberBillingRecord.TYPE_ORDER_REWARD_POINTS_RETURN);
                memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_SUB);
                memberBillingRecord.setNumber(dbMemberBillingRecord.getNumber());
                memberBillingRecord.setMessage("订单退款-下单奖励积分退回");
                memberBillingRecord.setOrderId(dbOrder.getId());
                memberBillingRecord.setCreateTime(new Date());
                memberBillingRecordService.insertSelective(memberBillingRecord);

                //之前的账单记录标注已退回
                MemberBillingRecord updateMemberBillingRecord = new MemberBillingRecord();
                updateMemberBillingRecord.setId(dbMemberBillingRecord.getId());
                updateMemberBillingRecord.setIsReturn(true);
                memberBillingRecordService.updateByPrimaryKeySelective(updateMemberBillingRecord);
            }

            //退回下单佣金奖励
            List typeList = new ArrayList();
            typeList.add(MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION);
            typeList.add(MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION);
            typeList.add(MemberBillingRecord.TYPE_OWN_COMMISSION);
            example = new MemberBillingRecordExample();
            example.createCriteria().andTypeIn(typeList)
                    .andOperateTypeEqualTo(MemberBillingRecord.OPERATE_TYPE_ADD)
                    .andCoinTypeEqualTo(MemberBillingRecord.COIN_TYPE_UNRECEIVED_INVITE_REWARD_AMOUNT)
                    .andOrderIdEqualTo(dbOrder.getId());
            list = memberBillingRecordService.selectByExample(example);
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
                    memberBillingRecord.setOrderId(dbOrder.getId());
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
            Order updateOrder = new Order();
            updateOrder.setId(param.getId());
            updateOrder.setStatus(Quantity.INT_9);
            orderMapper.updateByPrimaryKeySelective(updateOrder);

            //修改退款状态 -- 平台拒绝退款，退款已关闭
            OrderRefund updateOrderRefund = new OrderRefund();
            updateOrderRefund.setId(dbOrderRefund.getId());
            updateOrderRefund.setStatus(Quantity.INT_5);
            orderRefundService.updateByPrimaryKeySelective(updateOrderRefund);

            //添加退款流程 -- 等待平台处理
            OrderRefundProcess orderRefundProcess = new OrderRefundProcess();
            orderRefundProcess.setOrderRefundId(dbOrderRefund.getId());
            orderRefundProcess.setName("等待平台处理");
            orderRefundProcess.setCreateTime(new Date());
            orderRefundProcessService.insertSelective(orderRefundProcess);

            //添加退款流程 -- 平台拒绝退款，退款已关闭
            OrderRefundProcess orderRefundProcess_second = new OrderRefundProcess();
            orderRefundProcess_second.setOrderRefundId(dbOrderRefund.getId());
            orderRefundProcess_second.setName("平台拒绝退款，退款已关闭");
            orderRefundProcess_second.setDescription(param.getOpinion());
            orderRefundProcess_second.setCreateTime(DateUtilsPlus.addSeconds(new Date(), 5));
            orderRefundProcessService.insertSelective(orderRefundProcess_second);
        }

    }

    @Override
    public Map statistic(OrderParam param) throws ParseException {
        Map resultMap = new HashMap();

        //只统计状态为[6=已完成 7=售后处理中 9=售后处理完成]的订单数据

        //日期、订单数量、订单金额

        //1、查询开始日期(系统/订单服务第一次上线日期) - 今天
        /*String startDate = "2020-05-10";*/
        String startDate = orderMapper.selectStartDateOrder(null);
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
        List<Map<String, Object>> statisticList = orderMapper.selectStatisticOrder(null);
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
        OrderParam orderParam = new OrderParam();

        //本月订单总数、上月订单总数、同比情况
        orderParam.setStartTime(DateUtilsExtend.getBeginDayOfMonth());
        orderParam.setEndTime(DateUtilsExtend.getEndDayOfMonth());
        int thisMonthCountPaid = orderMapper.selectCountCompleted(orderParam);

        orderParam.setStartTime(DateUtilsExtend.getBeginDayOfLastMonth());
        orderParam.setEndTime(DateUtilsExtend.getEndDayOfLastMonth());
        int lastMonthCountPaid = orderMapper.selectCountCompleted(orderParam);

        //本周订单总数、上周订单总数、同比情况
        orderParam.setStartTime(DateUtilsExtend.getBeginDayOfWeek());
        orderParam.setEndTime(DateUtilsExtend.getEndDayOfWeek());
        int thisWeekCountPaid = orderMapper.selectCountCompleted(orderParam);
        orderParam.setStartTime(DateUtilsExtend.getBeginDayOfLastWeek());
        orderParam.setEndTime(DateUtilsExtend.getEndDayOfLastWeek());
        int lastWeekCountPaid = orderMapper.selectCountCompleted(orderParam);

        //本月销售总额、上月销售总额、同比情况
        orderParam.setStartTime(DateUtilsExtend.getBeginDayOfMonth());
        orderParam.setEndTime(DateUtilsExtend.getEndDayOfMonth());
        BigDecimal thisMonthSumActualPrice = orderMapper.selectSumActualPrice(orderParam);
        orderParam.setStartTime(DateUtilsExtend.getBeginDayOfLastMonth());
        orderParam.setEndTime(DateUtilsExtend.getEndDayOfLastMonth());
        BigDecimal lastMonthSumActualPrice = orderMapper.selectSumActualPrice(orderParam);

        //本周销售总额、上周销售总额、同比情况
        orderParam.setStartTime(DateUtilsExtend.getBeginDayOfWeek());
        orderParam.setEndTime(DateUtilsExtend.getEndDayOfWeek());
        BigDecimal thisWeekSumActualPrice = orderMapper.selectSumActualPrice(orderParam);
        orderParam.setStartTime(DateUtilsExtend.getBeginDayOfLastWeek());
        orderParam.setEndTime(DateUtilsExtend.getEndDayOfLastWeek());
        BigDecimal lastWeekSumActualPrice = orderMapper.selectSumActualPrice(orderParam);

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

    public String getRefundReasonText(int refundReason){
        String refundReasonText = "";
        switch (refundReason){
            case 1:
                refundReasonText = "信息填写错误";
                break;
            case 2:
                refundReasonText = "送达时间选错了";
                break;
            case 3:
                refundReasonText = "买错了/买少了";
                break;
            case 4:
                refundReasonText = "商家缺货";
                break;
            case 5:
                refundReasonText = "商家联系我取消";
                break;
            case 6:
                refundReasonText = "配送太慢";
                break;
            case 7:
                refundReasonText = "骑手联系我取消";
                break;
            case 8:
                refundReasonText = "我不想要了";
                break;
            case 9:
                refundReasonText = "商家通知我卖完了";
                break;
            case 10:
                refundReasonText = "商家沟通态度差";
                break;
            case 11:
                refundReasonText = "骑手沟通态度差";
                break;
            case 12:
                refundReasonText = "送太慢了，等太久了";
                break;
            case 13:
                refundReasonText = "商品撒漏/包装破损";
                break;
            case 14:
                refundReasonText = "商家少送商品";
                break;
            case 15:
                refundReasonText = "商家送错商品";
                break;
            case 16:
                refundReasonText = "口味不佳/个人感受不好";
                break;
            case 17:
                refundReasonText = "餐品内有异物";
                break;
            case 18:
                refundReasonText = "食用后引起身体不适";
                break;
            case 19:
                refundReasonText = "商品变质/有异味";
                break;
            case 20:
                refundReasonText = "其他";
                break;
        }
        return refundReasonText;
    }
}