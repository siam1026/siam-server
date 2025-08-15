package com.siam.system.modular.package_goods.service_impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.util.DateUtilsExtend;
import com.siam.package_common.util.DateUtilsPlus;
import com.siam.system.modular.package_goods.entity.Shop;
import com.siam.system.modular.package_order.entity.Order;
import com.siam.system.modular.package_order.model.example.OrderRefundExample;
import com.siam.system.modular.package_order.service.OrderService;
import com.siam.system.modular.package_order.service.OrderRefundService;
import com.siam.system.modular.package_user.model.example.MemberExample;
import com.siam.system.modular.package_user.service.MemberService;
import com.siam.system.modular.package_user.service.MemberTradeRecordService;
import com.siam.system.modular.package_user.service.MerchantWithdrawRecordService;
import com.siam.system.modular.package_goods.model.example.GoodsExample;
import com.siam.system.modular.package_goods.model.example.ShopChangeRecordExample;
import com.siam.system.modular.package_goods.model.param.StatisticsParam;
import com.siam.system.modular.package_goods.service.*;
import com.siam.system.modular.package_order.entity.OrderRefund;
import com.siam.system.modular.package_order.model.param.OrderParam;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.system.modular.package_user.entity.MemberTradeRecord;
import com.siam.system.modular.package_user.entity.Merchant;
import com.siam.system.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberTradeRecordService memberTradeRecordService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopChangeRecordService shopChangeRecordService;

    @Autowired
    private SystemUsageRecordService systemUsageRecordService;

    @Autowired
    private OrderRefundService orderRefundService;

    @Autowired
    private MerchantWithdrawRecordService merchantWithdrawRecordService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private GoodsService goodsService;

    @Override
    public Map todayStatisticByAdmin(StatisticsParam param) {
        Map resultMap = new HashMap();

        //订单表筛选条件-所有商家
        OrderParam order = new OrderParam();

        //今日支付订单、今日支付金额、待完成订单、待处理退款
        order.setStartTime(DateUtilsExtend.getDayBegin());
        order.setEndTime(DateUtilsExtend.getDayEnd());
        int dayCountPaid = orderService.selectCountCompleted(order);
        BigDecimal daySumActualPrice = orderService.selectSumActualPrice(order);
        int unCompletedNum = orderService.selectCountUnCompleted(order);
        int waitHandleRefundNum = orderService.selectCountWaitHandle(order);

        //总帐余额 = 累计进账 - 累计出账
        //今日进账 = 所有微信支付入口汇总的金额；退款金额不做计算；余额支付、积分支付这种不算
        //今日出账 = 佣金提现 + 商户提现 + 退款金额之类的所有由微信支出的汇总；余额和积分的支付也算
        MemberTradeRecord memberTradeRecord = new MemberTradeRecord();
        memberTradeRecord.setStartTime(DateUtilsExtend.getDayBegin());
        memberTradeRecord.setEndTime(DateUtilsExtend.getDayEnd());
        BigDecimal daySumIncome = memberTradeRecordService.selectSumIncome(memberTradeRecord, memberTradeRecord.getStartTime(), memberTradeRecord.getEndTime());
        BigDecimal daySumExpense = memberTradeRecordService.selectSumExpense(memberTradeRecord, memberTradeRecord.getStartTime(), memberTradeRecord.getEndTime());
        //退款金额--微信/支付宝
        OrderRefund orderRefund = new OrderRefund();
        orderRefund.setStartTime(DateUtilsExtend.getDayBegin());
        orderRefund.setEndTime(DateUtilsExtend.getDayEnd());
        BigDecimal daySumRefundAmount = orderRefundService.selectSumRefundAmount(orderRefund, orderRefund.getStartTime(), orderRefund.getEndTime());
        daySumExpense = daySumExpense.add(daySumRefundAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
        //退款金额--余额/积分
        BigDecimal daySumRefundAmountByPlatformCoin = orderRefundService.selectSumRefundAmountByPlatformCoin(orderRefund, orderRefund.getStartTime(), orderRefund.getEndTime());
        daySumExpense = daySumExpense.subtract(daySumRefundAmountByPlatformCoin).setScale(2, BigDecimal.ROUND_HALF_UP);

        memberTradeRecord.setStartTime(new Date("1970/1/1 00:00:00"));
        memberTradeRecord.setEndTime(new Date("2100/1/1 00:00:00"));
        BigDecimal totalSumIncome = memberTradeRecordService.selectSumIncome(memberTradeRecord, memberTradeRecord.getStartTime(), memberTradeRecord.getEndTime());
        BigDecimal totalSumExpense = memberTradeRecordService.selectSumExpense(memberTradeRecord, memberTradeRecord.getStartTime(), memberTradeRecord.getEndTime());
        //退款金额--微信/支付宝
        orderRefund.setStartTime(new Date("1970/1/1 00:00:00"));
        orderRefund.setEndTime(new Date("2100/1/1 00:00:00"));
        BigDecimal totalSumRefundAmount = orderRefundService.selectSumRefundAmount(orderRefund, orderRefund.getStartTime(), orderRefund.getEndTime());
        totalSumExpense = totalSumExpense.add(totalSumRefundAmount);
        //退款金额--余额/积分
        BigDecimal totalSumRefundAmountByPlatformCoin = orderRefundService.selectSumRefundAmountByPlatformCoin(orderRefund, orderRefund.getStartTime(), orderRefund.getEndTime());
        totalSumExpense = totalSumExpense.subtract(totalSumRefundAmountByPlatformCoin).setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal balance = totalSumIncome.subtract(totalSumExpense).setScale(2, BigDecimal.ROUND_HALF_UP);

        //商品支付金额、配送费金额、退款金额、配送费退款金额 -- 针对于所有店铺、现金/余额都算
        Map<String, Object> orderSumField = orderService.selectSumField(order);
        Map<String, Object> orderRefundSumField = orderRefundService.selectSumField(new OrderRefund());
        BigDecimal totalActualPrice = (BigDecimal) orderSumField.get("totalActualPrice");
        BigDecimal totalDeliveryFee = (BigDecimal) orderSumField.get("totalDeliveryFee");
        BigDecimal totalRefundAmount = (BigDecimal) orderRefundSumField.get("totalRefundAmount");
        BigDecimal totalRefundDeliveryFee = (BigDecimal) orderRefundSumField.get("totalRefundDeliveryFee");

        //待处理开店申请、待处理提现申请、待处理变更资料申请、待处理退款申请
        LambdaQueryWrapper<Shop> shopLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shopLambdaQueryWrapper.eq(Shop::getAuditStatus, Quantity.INT_1);
        int handleShopCount = shopService.count(shopLambdaQueryWrapper);

        int handleMerchantWithdrawCount = (int) merchantWithdrawRecordService.countByAuditStatus(Quantity.INT_1).getData();

        ShopChangeRecordExample shopChangeExample = new ShopChangeRecordExample();
        shopChangeExample.createCriteria().andAuditStatusEqualTo(Quantity.INT_1);
        int handleShopChangeCount = shopChangeRecordService.countByExample(shopChangeExample);

        OrderRefundExample orderRefundCount = new OrderRefundExample();
        orderRefundCount.createCriteria().andStatusEqualTo(Quantity.INT_4);
        int handleOrderRefundCount = orderRefundService.countByExample(orderRefundCount);

        //商家总览：已下架、已上架、全部商家
        shopLambdaQueryWrapper = new LambdaQueryWrapper<Shop>();
        shopLambdaQueryWrapper.eq(Shop::getStatus, Quantity.INT_3);
        int underShelfShopCount = shopService.count(shopLambdaQueryWrapper);

        shopLambdaQueryWrapper = new LambdaQueryWrapper<Shop>();
        shopLambdaQueryWrapper.eq(Shop::getStatus, Quantity.INT_2);
        int onShelfShopCount = shopService.count(shopLambdaQueryWrapper);

        /*List filterList = new ArrayList<>();
        filterList.add(Quantity.INT_1);
        filterList.add(Quantity.INT_2);
        filterList.add(Quantity.INT_3);*/
        shopLambdaQueryWrapper = new LambdaQueryWrapper<Shop>();
        shopLambdaQueryWrapper.eq(Shop::getAuditStatus, Quantity.INT_2);
        int allShopCount = shopService.count(shopLambdaQueryWrapper);

        //用户总览：今日新增、昨日新增、本月新增、会员总数
        int dayMemberCount = memberService.selectCountRegister(DateUtilsExtend.getDayBegin(), DateUtilsExtend.getDayEnd());
        int yesterdayMemberCount = memberService.selectCountRegister(DateUtilsExtend.getBeginDayOfYesterday(), DateUtilsExtend.getEndDayOfYesterDay());
        int thisMonthMemberCount = memberService.selectCountRegister(DateUtilsExtend.getBeginDayOfMonth(), DateUtilsExtend.getEndDayOfMonth());
        int allMemberCount = memberService.countByExample(new MemberExample());

        resultMap.put("dayCountPaid", dayCountPaid);
        resultMap.put("daySumActualPrice", daySumActualPrice);
        resultMap.put("handleShopCount", handleShopCount);
        resultMap.put("handleMerchantWithdrawCount", handleMerchantWithdrawCount);
        resultMap.put("handleShopChangeCount", handleShopChangeCount);
        resultMap.put("handleOrderRefundCount", handleOrderRefundCount);
        resultMap.put("underShelfShopCount", underShelfShopCount);
        resultMap.put("onShelfShopCount", onShelfShopCount);
        resultMap.put("allShopCount", allShopCount);
        resultMap.put("dayMemberCount", dayMemberCount);
        resultMap.put("yesterdayMemberCount", yesterdayMemberCount);
        resultMap.put("thisMonthMemberCount", thisMonthMemberCount);
        resultMap.put("allMemberCount", allMemberCount);
        resultMap.put("totalActualPrice", totalActualPrice);
        resultMap.put("totalDeliveryFee", totalDeliveryFee);
        resultMap.put("totalRefundAmount", totalRefundAmount);
        resultMap.put("totalRefundDeliveryFee", totalRefundDeliveryFee);
        resultMap.put("unCompletedNum", unCompletedNum);
        resultMap.put("waitHandleRefundNum", waitHandleRefundNum);
        resultMap.put("balance", balance);
        resultMap.put("daySumIncome", daySumIncome);
        resultMap.put("daySumExpense", daySumExpense);

//        //今日结束时间
//        Calendar today_endCalendar = Calendar.getInstance();
//        today_endCalendar.setTime(new Date());
//        today_endCalendar.set(Calendar.HOUR_OF_DAY, 23);
//        today_endCalendar.set(Calendar.MINUTE, 59);
//        today_endCalendar.set(Calendar.SECOND, 59);
//        today_endCalendar.set(Calendar.MILLISECOND, 999);
//        Date today_endTime = today_endCalendar.getTime();
//
//        //昨日结束时间
//        Calendar yesterday_endCalendar = Calendar.getInstance();
//        yesterday_endCalendar.setTime(DateUtils.subtractDays(new Date(), 1));
//        yesterday_endCalendar.set(Calendar.HOUR_OF_DAY, 23);
//        yesterday_endCalendar.set(Calendar.MINUTE, 59);
//        yesterday_endCalendar.set(Calendar.SECOND, 59);
//        yesterday_endCalendar.set(Calendar.MILLISECOND, 999);
//        Date yesterday_endTime = yesterday_endCalendar.getTime();
//
//        //系统开始使用时间
//        Calendar system_startCalendar = Calendar.getInstance();
//        system_startCalendar.setTime(new Date("1970/1/1 00:00:00"));
//        Date system_startTime = system_startCalendar.getTime();
//
//        //订单表筛选条件-所有商家
//        Order order = new Order();
//
//        //今日支付金额(商家实际到手金额)
//        BigDecimal todaySumMerchantIncome = orderService.selectSumMerchantIncome(order, system_startTime, today_endTime);
//        //昨日支付金额(商家实际到手金额)
//        BigDecimal yesterdaySumMerchantIncome = orderService.selectSumMerchantIncome(order, system_startTime, yesterday_endTime);
//
//        //今日支付订单数量
//        int todayCountPaid = orderService.selectCountCompleted(order, system_startTime, today_endTime);
//        //昨日支付订单数量
//        int yesterdayCountPaid = orderService.selectCountCompleted(order, system_startTime, yesterday_endTime);
//
//        //今日截止总共普通注册人数
//        int todayCountGeneralRegister = memberService.selectCountGeneralRegister(system_startTime, today_endTime);
//        //昨日截止总共普通注册人数
//        int yesterdayCountGeneralRegister = memberService.selectCountGeneralRegister(system_startTime, yesterday_endTime);
//
//        //今日截止总共邀请注册人数
//        int todayCountInviteRegister = memberService.selectCountInviteRegister(system_startTime, today_endTime);
//        //昨日截止总共邀请注册人数
//        int yesterdayCountInviteRegister = memberService.selectCountInviteRegister(system_startTime, yesterday_endTime);
//
//        resultMap.put("todaySumMerchantIncome", todaySumMerchantIncome);
//        resultMap.put("yesterdaySumMerchantIncome", yesterdaySumMerchantIncome);
//        resultMap.put("todayCountPaid", todayCountPaid);
//        resultMap.put("yesterdayCountPaid", yesterdayCountPaid);
//        resultMap.put("todayCountGeneralRegister", todayCountGeneralRegister);
//        resultMap.put("yesterdayCountGeneralRegister", yesterdayCountGeneralRegister);
//        resultMap.put("todayCountInviteRegister", todayCountInviteRegister);
//        resultMap.put("yesterdayCountInviteRegister", yesterdayCountInviteRegister);

        return resultMap;
    }

    @Override
    public Map todayStatisticByMerchant(StatisticsParam param) {
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());
        Map resultMap = new HashMap();

        //订单表筛选条件-当前登录商家
        OrderParam order = new OrderParam();
        order.setShopId(loginMerchant.getShopId());
        order.setStartTime(DateUtilsExtend.getDayBegin());
        order.setEndTime(DateUtilsExtend.getDayEnd());
        //今日支付订单、今日实际收入、今日进店人数、今日加购商品
        log.debug("\n\n》》》 StatisticsServiceImpl.todayStatisticByMerchant - order = " + JSON.toJSONString(order));
        int dayCountPaid = orderService.selectCountCompleted(order);
        BigDecimal daySumMerchantIncome = orderService.selectSumMerchantIncome(order);
        int todayCountIntoShop = systemUsageRecordService.selectCountIntoShop(loginMerchant.getShopId(), DateUtilsExtend.getDayBegin(), DateUtilsExtend.getDayEnd());
        int todayCountShoppingCartGoodsNumber = shoppingCartService.selectCountGoodsNumber(loginMerchant.getShopId(), DateUtilsExtend.getDayBegin(), DateUtilsExtend.getDayEnd());

        //待制作订单(自取)、待配送订单(外卖)、已完成订单、待处理退款申请
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(Order::getShopId, loginMerchant.getShopId());
        orderLambdaQueryWrapper.eq(Order::getStatus, Quantity.INT_2);
        int waitHandleOrderCount = orderService.count(orderLambdaQueryWrapper);

        orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(Order::getShopId, loginMerchant.getShopId());
        orderLambdaQueryWrapper.eq(Order::getStatus, Quantity.INT_4);
        int waitDeliverOrderCount = orderService.count(orderLambdaQueryWrapper);

        orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(Order::getShopId, loginMerchant.getShopId());
        orderLambdaQueryWrapper.eq(Order::getStatus, Quantity.INT_6);
        int completedOrderCount = orderService.count(orderLambdaQueryWrapper);

        orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(Order::getShopId, loginMerchant.getShopId());
        orderLambdaQueryWrapper.eq(Order::getStatus, Quantity.INT_7);
        int handleOrderRefundCount = orderService.count(orderLambdaQueryWrapper);

        //商品总览：已下架、已上架、库存售罄、全部商品
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andShopIdEqualTo(loginMerchant.getShopId()).andStatusEqualTo(Quantity.INT_3);
        int underShelfGoodsCount = goodsService.countByExample(goodsExample);

        goodsExample = new GoodsExample();
        goodsExample.createCriteria().andShopIdEqualTo(loginMerchant.getShopId()).andStatusEqualTo(Quantity.INT_2);
        int onShelfGoodsCount = goodsService.countByExample(goodsExample);

        goodsExample = new GoodsExample();
        goodsExample.createCriteria().andShopIdEqualTo(loginMerchant.getShopId()).andStatusEqualTo(Quantity.INT_4);
        int sellOutGoodsCount = goodsService.countByExample(goodsExample);

        goodsExample = new GoodsExample();
        goodsExample.createCriteria().andShopIdEqualTo(loginMerchant.getShopId());
        int allGoodsCount = goodsService.countByExample(goodsExample);

        //指数总览：客单价(成交金额/成交订单数)、下单转化率(下单人数/访问人数)、下单-支付转化率(支付人数/下单人数)、支付转化率(支付人数/访问人数)
        Date startDate = new Date("1970/1/1 00:00:00");
        BigDecimal perCustomerTransaction;
        order.setStartTime(startDate);
        order.setEndTime(DateUtilsExtend.getDayEnd());
        BigDecimal sumActualPrice = orderService.selectSumActualPrice(order);
        int countPaid = orderService.selectCountCompleted(order);
        if(countPaid == 0){
            perCustomerTransaction = BigDecimal.valueOf(-1);
        }else{
            perCustomerTransaction = sumActualPrice.divide(BigDecimal.valueOf(countPaid), 2, BigDecimal.ROUND_HALF_UP);
        }

        BigDecimal orderConversionRate;
        order.setStartTime(startDate);
        order.setEndTime(DateUtilsExtend.getDayEnd());
        int countOrderPeoples = orderService.selectCountOrderPeoples(order);
        int countIntoShop = systemUsageRecordService.selectCountIntoShop(loginMerchant.getShopId(), startDate, DateUtilsExtend.getDayEnd());
        if(countIntoShop == 0){
            orderConversionRate = BigDecimal.valueOf(-1);
        }else{
            orderConversionRate = BigDecimal.valueOf(countOrderPeoples).divide(BigDecimal.valueOf(countIntoShop), 2, BigDecimal.ROUND_HALF_UP);
        }

        BigDecimal orderPaymentConversionRate;
        order.setStartTime(startDate);
        order.setEndTime(DateUtilsExtend.getDayEnd());
        int countPayers = orderService.selectCountPayers(order);
        if(countOrderPeoples == 0){
            orderPaymentConversionRate = BigDecimal.valueOf(-1);
        }else{
            orderPaymentConversionRate = BigDecimal.valueOf(countPayers).divide(BigDecimal.valueOf(countOrderPeoples), 2, BigDecimal.ROUND_HALF_UP);
        }

        BigDecimal paymentConversionRate;
        if(countIntoShop == 0){
            paymentConversionRate = BigDecimal.valueOf(-1);
        }else{
            paymentConversionRate = BigDecimal.valueOf(countPayers).divide(BigDecimal.valueOf(countIntoShop), 2, BigDecimal.ROUND_HALF_UP);
        }

        //对百分比做限制，必须控制在0~1之间
        if(orderConversionRate.compareTo(BigDecimal.ONE) > 0){
            orderConversionRate = BigDecimal.ONE;
        }else if(orderConversionRate.compareTo(BigDecimal.ZERO) < 0){
            orderConversionRate = BigDecimal.ZERO;
        }
        if(orderPaymentConversionRate.compareTo(BigDecimal.ONE) > 0){
            orderPaymentConversionRate = BigDecimal.ONE;
        }else if(orderPaymentConversionRate.compareTo(BigDecimal.ZERO) < 0){
            orderPaymentConversionRate = BigDecimal.ZERO;
        }
        if(paymentConversionRate.compareTo(BigDecimal.ONE) > 0){
            paymentConversionRate = BigDecimal.ONE;
        }else if(paymentConversionRate.compareTo(BigDecimal.ZERO) < 0){
            paymentConversionRate = BigDecimal.ZERO;
        }

        resultMap.put("dayCountPaid", dayCountPaid);
        resultMap.put("daySumMerchantIncome", daySumMerchantIncome);
        resultMap.put("todayCountIntoShop", todayCountIntoShop);
        resultMap.put("todayCountShoppingCartGoodsNumber", todayCountShoppingCartGoodsNumber);
        resultMap.put("waitHandleOrderCount", waitHandleOrderCount);
        resultMap.put("waitDeliverOrderCount", waitDeliverOrderCount);
        resultMap.put("completedOrderCount", completedOrderCount);
        resultMap.put("handleOrderRefundCount", handleOrderRefundCount);
        resultMap.put("underShelfGoodsCount", underShelfGoodsCount);
        resultMap.put("onShelfGoodsCount", onShelfGoodsCount);
        resultMap.put("sellOutGoodsCount", sellOutGoodsCount);
        resultMap.put("allGoodsCount", allGoodsCount);
        resultMap.put("perCustomerTransaction", perCustomerTransaction);
        resultMap.put("orderConversionRate", orderConversionRate);
        resultMap.put("orderPaymentConversionRate", orderPaymentConversionRate);
        resultMap.put("paymentConversionRate", paymentConversionRate);

        //今日开始时间
        Calendar today_startCalendar = Calendar.getInstance();
        today_startCalendar.setTime(new Date());
        today_startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        today_startCalendar.set(Calendar.MINUTE, 0);
        today_startCalendar.set(Calendar.SECOND, 0);
        today_startCalendar.set(Calendar.MILLISECOND, 0);
        Date today_startTime = today_startCalendar.getTime();
        //今日结束时间
        Calendar today_endCalendar = Calendar.getInstance();
        today_endCalendar.setTime(new Date());
        today_endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        today_endCalendar.set(Calendar.MINUTE, 59);
        today_endCalendar.set(Calendar.SECOND, 59);
        today_endCalendar.set(Calendar.MILLISECOND, 999);
        Date today_endTime = today_endCalendar.getTime();

        //昨日结束时间
        Calendar yesterday_endCalendar = Calendar.getInstance();
        yesterday_endCalendar.setTime(DateUtilsPlus.subtractDays(new Date(), 1));
        yesterday_endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        yesterday_endCalendar.set(Calendar.MINUTE, 59);
        yesterday_endCalendar.set(Calendar.SECOND, 59);
        yesterday_endCalendar.set(Calendar.MILLISECOND, 999);
        Date yesterday_endTime = yesterday_endCalendar.getTime();
        //昨日开始时间
        Calendar yesterday_startCalendar = Calendar.getInstance();
        yesterday_startCalendar.setTime(DateUtilsPlus.subtractDays(yesterday_endTime, 1));
        yesterday_startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        yesterday_startCalendar.set(Calendar.MINUTE, 0);
        yesterday_startCalendar.set(Calendar.SECOND, 0);
        yesterday_startCalendar.set(Calendar.MILLISECOND, 0);
        Date yesterday_startTime = yesterday_startCalendar.getTime();

        //订单表筛选条件-当前登录商家
        order = new OrderParam();
        order.setShopId(loginMerchant.getShopId());

        //今日进店人数
        todayCountIntoShop = systemUsageRecordService.selectCountIntoShop(loginMerchant.getShopId(), today_startTime, today_endTime);
        //昨日进店人数
        int yesterdayCountIntoShop = systemUsageRecordService.selectCountIntoShop(loginMerchant.getShopId(), yesterday_startTime, yesterday_endTime);

        //今日支付金额(商家实际到手金额)
        order.setStartTime(today_startTime);
        order.setEndTime(today_endTime);
        BigDecimal todaySumMerchantIncome = orderService.selectSumMerchantIncome(order);
        //昨日支付金额(商家实际到手金额)
        order.setStartTime(yesterday_startTime);
        order.setEndTime(yesterday_endTime);
        BigDecimal yesterdaySumMerchantIncome = orderService.selectSumMerchantIncome(order);

        //今日支付订单数量
        order.setStartTime(today_startTime);
        order.setEndTime(today_endTime);
        int todayCountPaid = orderService.selectCountCompleted(order);
        //昨日支付订单数量
        order.setStartTime(yesterday_startTime);
        order.setEndTime(yesterday_endTime);
        int yesterdayCountPaid = orderService.selectCountCompleted(order);

        //今日加购商品数量
        int todayCountGoodsNumber = shoppingCartService.selectCountGoodsNumber(loginMerchant.getShopId(), today_startTime, today_endTime);
        //昨日加购商品数量
        int yesterdayCountGoodsNumber = shoppingCartService.selectCountGoodsNumber(loginMerchant.getShopId(), yesterday_startTime, yesterday_endTime);

        resultMap.put("todayCountIntoShop", todayCountIntoShop);
        resultMap.put("yesterdayCountIntoShop", yesterdayCountIntoShop);
        resultMap.put("todaySumMerchantIncome", todaySumMerchantIncome);
        resultMap.put("yesterdaySumMerchantIncome", yesterdaySumMerchantIncome);
        resultMap.put("todayCountPaid", todayCountPaid);
        resultMap.put("yesterdayCountPaid", yesterdayCountPaid);
        resultMap.put("todayCountGoodsNumber", todayCountGoodsNumber);
        resultMap.put("yesterdayCountGoodsNumber", yesterdayCountGoodsNumber);

        return resultMap;
    }
}