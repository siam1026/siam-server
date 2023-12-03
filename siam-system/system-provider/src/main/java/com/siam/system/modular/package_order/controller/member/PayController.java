package com.siam.system.modular.package_order.controller.member;

import com.github.binarywang.wxpay.service.WxPayService;
import com.siam.package_weixin_pay.config.WxpayConfigTemp;
import com.siam.package_common.entity.AlipayConfig;
import com.siam.system.modular.package_order.service.OrderDetailService;
import com.siam.system.modular.package_order.service.OrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/rest/pay")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "支付模块相关接口", description = "PayController")
public class PayController {
    private Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private OrderService orderService;

//    @Autowired
//    private MemberTradeRecordService memberTradeRecordService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private WxpayConfigTemp wxpayConfigTemp;

    /*@Autowired*/
    private WxPayService wxPayService; //依赖第三方库提供的

//    @Autowired
//    private GoodsService goodsService;

//    @Autowired
//    private GoodsSpecificationService goodsSpecificationService;

    @Autowired
    private OrderDetailService orderDetailService;

//    @Autowired
//    private GoodsRawmaterialRelationService rawmaterialRelationService;

//    @Autowired
//    private SettingService settingService;

//    @Autowired
//    private MemberBillingRecordService memberBillingRecordService;
//
//    @Autowired
//    private MemberService memberService;

    /*@ApiOperation(value = "支付宝支付")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "out_trade_no", value = "商户订单号", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "subject", value = "订单名称", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "total_amount", value = "付款金额", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "body", value = "商品描述", required = false, paramType = "query", dataType = "string"),
    })
    @PostMapping(value = "/alipay")
    public BasicResult alipay(AlipayBean alipayBean){
        BasicData basicResult = new BasicData();
        try {
            // 获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getPrivateKey(), alipayConfig.getFormat(), alipayConfig.getCharset(), alipayConfig.getPublicKey(), alipayConfig.getSignType());

            // 设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(alipayConfig.getReturnUrl()); //页面跳转同步通知页面路径
            alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl()); //服务器异步通知页面路径
            alipayRequest.setBizContent(JsonUtils.toJson(alipayBean)); //封装参数

            // 请求支付宝进行付款，并获取支付结果
            String result = alipayClient.pageExecute(alipayRequest).getBody();

            basicResult.setData(result);
            basicResult.setSuccess(true);
            basicResult.setCode(BasicResultCode.SUCCESS);
            basicResult.setMessage("发起支付宝支付成功");

        } catch (AlipayApiException e) {
            //e.printStackTrace();
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("发起微信支付失败");
        }
        return basicResult;
    }

    @ApiOperation(value = "支付宝支付回调")
    @PostMapping(value = "/alipayCallback")
    public BasicResult alipayCallback(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        BasicResult basicResult = new BasicResult();

        // 获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        log.debug("\n=================== params ===================\n" + params);

        // 调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
        *//*boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.alipay_public_key, alipayConfig.charset, alipayConfig.sign_type); //调用SDK验证签名*//*
        // 比对支付信息是否与订单信息相匹配
        *//*  实际验证过程建议商户务必添加以下校验：
            1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
            2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
            3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
            4、验证app_id是否为该商户本身。
        *//*

        if(signVerified) {//验证成功
            String out_trade_no = request.getParameter("out_trade_no");//商户订单号

            String trade_no = request.getParameter("trade_no");//支付宝交易号

            String trade_status = request.getParameter("trade_status");//交易状态

            if (trade_status.equals("TRADE_SUCCESS")){
                Order dbOrder = orderService.selectByOrderNo(out_trade_no);

                // 添加memberTrade记录
                *//*MemberTradeRecord insertMemberTradeRecord = new MemberTradeRecord();
                insertMemberTradeRecord.setMemberId(dbOrder.getMemberId());
                insertMemberTradeRecord.setTradeNo(trade_no);
                insertMemberTradeRecord.setAmount(dbOrder.getActualPrice());
                insertMemberTradeRecord.setPayType(Quantity.INT_2);
                insertMemberTradeRecord.setTradeStatus(Quantity.INT_1);
                insertMemberTradeRecord.setType(Quantity.INT_0);
                insertMemberTradeRecord.setRemark(null);
                insertMemberTradeRecord.setCreateTime(new Date());
                memberTradeRecordService.insertSelective(insertMemberTradeRecord);*//*

                // 更新order记录
                *//*Order updateOrder = new Order();
                updateOrder.setId(dbOrder.getId());
                updateOrder.setTradeId(insertMemberTradeRecord.getId());
                updateOrder.setUpdateTime(new Date());
                orderService.updateByPrimaryKeySelective(updateOrder);*//*

                // 支付成功，将订单信息存入消息队列，用于减少库存
                *//*String json = GsonUtils.toJson(dbOrder);
                String msgId = UUID.randomUUID().toString();
                // 将 msgId 和 message 绑定
                Message message = MessageBuilder.withBody(json.getBytes())
                                                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                                                .setCorrelationId(msgId).build();
                // 将 msgId 和 CorrelationData 绑定
                CorrelationData correlationData = new CorrelationData(msgId);
                rabbitTemplate.convertAndSend(ExchangeConfig.EXCHANGE_01, RabbitMqConfig.ROUTING_KEY_1, message, correlationData);

                log.debug("\n=================== 消费者 =====================");
                log.debug("\n" + message);
                log.debug("\n" + new String(message.getBody()));

                // 订单支付成功，减少库存（未支付，那库存是否变化，我觉得网站上库存是偏小的）
                Order order = GsonUtils.toBean(new String(message.getBody()), Order.class);
                List<OrderDetail> list = orderDetailService.selectByOrderId(order.getId());
                for(OrderDetail orderDetail : list){
                    // 修改订单状态为--已付款
                    Order updateOrder = new Order();
                    updateOrder.setId(order.getId());
                    updateOrder.setOrderStatus(Quantity.INT_1);
                    orderService.updateByPrimaryKeySelective(updateOrder);

                    // 减少商品库存
                    Integer number = orderDetail.getNumber();
                    String goodsId = orderDetail.getGoodsId();
                    goodsService.decreaseStock(Integer.valueOf(goodsId), number);

                    // 减少商品规格组合库存
                    String goodsSpecificationId = orderDetail.getGoodsSpecificationId();
                    goodsSpecificationService.decreaseStock(Integer.valueOf(goodsSpecificationId), number);
                }*//*

            }

            log.debug("\nsuccess");

        }else {//验证失败
            log.debug("\nfail");
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("支付宝回调成功");
        return basicResult;
    }*/


    /*@ApiOperation(value = "微信支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "out_trade_no", value = "商户订单号", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "subject", value = "订单名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "total_amount", value = "付款金额", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "body", value = "商品描述", required = false, paramType = "query", dataType = "string"),
    })
    @PostMapping(value = "/wxpay")
    public BasicResult wxpay(WxpayBean wxpayBean, HttpServletRequest request, WxPayUnifiedOrderRequest wxreq){
        BasicData basicResult = new BasicData();
        try {
            String ip = request.getHeader("x-forwarded-for");
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                // 多次反向代理后会有多个ip值，第一个ip才是真实ip
                if( ip.indexOf(",")!=-1 ){
                    ip = ip.split(",")[0];
                }
            }

            // 微信支付的单位是分，例如你支付金额是9.9元（保留两位小数），那你微信支付的时候支付金额是9.9元*100 = 990分，最后支付金额是整数。
            Integer totalFee = (int)(Double.valueOf(wxpayBean.getTotal_amount()) * 100);

            // 填充微信支付请求必要信息
            wxreq.setSpbillCreateIp(ip);
            wxreq.setBody(wxpayBean.getBody());
            wxreq.setOutTradeNo(wxpayBean.getOut_trade_no());
            wxreq.setTotalFee(totalFee);
            wxreq.setAppid(wxpayConfig.getAppId());
            wxreq.setMchId(wxpayConfig.getMchId());
            wxreq.setNotifyUrl(wxpayConfig.getNotifyUrl());
            wxreq.setTradeType(wxpayConfig.getTradeType());

            // 发起请求，获取地址，然后根据地址生成二维码
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxreq);

            basicResult.setData(wxPayUnifiedOrderResult);
            basicResult.setSuccess(true);
            basicResult.setCode(BasicResultCode.SUCCESS);
            basicResult.setMessage("发起微信支付成功");

        } catch (WxPayException e) {
            //e.printStackTrace();
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("发起微信支付失败");
        }
        return basicResult;
    }


    @ApiOperation(value = "微信支付回调")
    @PostMapping(value = "/wxpayCallback")
    public BasicResult wxpayCallback(String xmlData,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, AlipayApiException, WxPayException {
        BasicResult basicResult = new BasicResult();

        WxPayOrderNotifyResult orderNotifyResult = wxPayService.parseOrderNotifyResult(xmlData);
        String noticeStr = null;
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

            if (orderNotifyResult.getResultCode().equals("SUCCESS")) {//支付成功，商户处理后同步返回给微信参数
                *//**
                 * 处理你的业务，修改订单状态等
                 *//*
                // 通知微信已经收到消息，不要再给我发消息了，否则微信会8连击调用本接口
                noticeStr = setXML("SUCCESS", "支付成功");
                outputStream.write(noticeStr.getBytes());
                outputStream.flush();
                outputStream.close();
            } else {//支付失败，记录流水失败
                noticeStr = setXML("FAIL", "支付失败");
                outputStream.write(noticeStr.getBytes());
                outputStream.flush();
                outputStream.close();
                log.debug("\nfail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.debug("\n=================== noticeStr ===================\n" + noticeStr);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("支付宝回调成功");
        return basicResult;
    }

    public String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }

    @ApiOperation(value = "微信支付_2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "out_trade_no", value = "商户订单号", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "subject", value = "订单名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "total_amount", value = "付款金额", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "body", value = "商品描述", required = false, paramType = "query", dataType = "string"),
    })
    @PostMapping(value = "/wxpay_2")
    public BasicResult wxpay_2(WxpayBean wxpayBean, HttpServletRequest request, WxPayUnifiedOrderRequest wxreq){
        BasicData basicResult = new BasicData();
        basicResult.setData("http://www.baidu.com");
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("发起微信支付成功");
        return basicResult;
    }*/

    /*@ApiOperation(value = "订单支付(过渡接口)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "out_trade_no", value = "商户订单号", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "subject", value = "订单名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "total_amount", value = "付款金额", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "body", value = "商品描述", required = false, paramType = "query", dataType = "string"),
    })
    @PostMapping(value = "/orderPay")
    public BasicResult orderPay(WxpayBean wxpayBean, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        Order dbOrder = orderService.selectByOrderNo(wxpayBean.getOut_trade_no());
        if(dbOrder == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该商户订单号不存在，支付失败");
            return basicResult;
        }

        //添加用户交易记录
        MemberTradeRecord insertMemberTradeRecord = new MemberTradeRecord();
        insertMemberTradeRecord.setMemberId(dbOrder.getMemberId());
        insertMemberTradeRecord.setTradeNo(GenerateNo.getUUID());
        insertMemberTradeRecord.setAmount(new BigDecimal(wxpayBean.getTotal_amount()));
        insertMemberTradeRecord.setPayType(Quantity.INT_1);
        insertMemberTradeRecord.setTradeStatus(Quantity.INT_1);
        insertMemberTradeRecord.setType(Quantity.INT_0);
        insertMemberTradeRecord.setRemark(null);
        insertMemberTradeRecord.setCreateTime(new Date());
        memberTradeRecordService.insertSelective(insertMemberTradeRecord);

        //订单状态不经过待处理流程，直接分流到待自取 或者 待配送
        int status = 0;
        if(dbOrder.getShoppingWay() == Quantity.INT_1){
            status = Quantity.INT_3;
        }else{
            status = Quantity.INT_4;
        }
        //更新订单的状态
        Order updateOrder = new Order();
        updateOrder.setId(dbOrder.getId());
        updateOrder.setTradeId(insertMemberTradeRecord.getId());
        updateOrder.setStatus(status);
        updateOrder.setUpdateTime(new Date());
        orderService.updateByPrimaryKeySelective(updateOrder);

        //减去使用的原料
        List<OrderDetail> orderDetailList = orderDetailService.selectByOrderId(dbOrder.getId());
        rawmaterialRelationService.updateRawmaterialConsumedQuantityByOrderDetailList(orderDetailList);

        // 获取下单积分量
        Setting setting=settingService.selectCurrent();
        Integer settingNum = setting.getPurchaseRewardPoints();
        settingNum = settingNum == null ? 0 : settingNum;

        //获取用户当前积分数
        Integer currentPointsNum = memberService.selectByPrimaryKey(dbOrder.getMemberId()).getPoints();
        currentPointsNum = currentPointsNum == null ? 0 : currentPointsNum;

        //获取最后的积分数
        Integer goodsNum = dbOrder.getGoodsTotalQuantity();
        goodsNum = goodsNum == null ? 0 : goodsNum;
        Integer pointsNnm = currentPointsNum+(goodsNum * settingNum);

        //生成积分账单
        MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
        memberBillingRecord.setMemberId(dbOrder.getMemberId());
        memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_POINTS);
        memberBillingRecord.setType(MemberBillingRecord.TYPE_ORDER_REWARD_POINTS);
        memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
        memberBillingRecord.setNumber(BigDecimal.valueOf(settingNum*goodsNum));
        memberBillingRecord.setCreateTime(new Date());
        memberBillingRecord.setMessage("下单奖励积分");
        memberBillingRecordService.insertSelective(memberBillingRecord);


        //修改用户的积分数
        Member member = new Member();
        member.setId(dbOrder.getMemberId());
        member.setPoints(pointsNnm);
        memberService.updateByPrimaryKeySelective(member);

        //商品月销量和总销量修改
        for (OrderDetail orderDetail : orderDetailList) {
            Integer num = orderDetail.getNumber();
            Integer goodsId = orderDetail.getGoodsId();
            goodsService.updateSales(goodsId, num);
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("支付成功");
        return basicResult;
    }*/
}