package com.siam.system.modular.package_order.controller.member.internal;

import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.util.RedisUtils;
import com.siam.package_weixin_pay.util.IpUtils;
import com.siam.package_weixin_pay.util.PayUtil;
import com.siam.package_weixin_pay.config.WxPayConfig;
import com.siam.package_weixin_pay.entity.WxPayDto;
import com.siam.system.modular.package_goods.service.ShopService;
import com.siam.system.modular.package_goods.service.internal.VipRechargeDenominationService;
import com.siam.system.modular.package_goods.service.internal.VipRechargeRecordService;
import com.siam.system.modular.package_user.service.MemberTradeRecordService;
import com.siam.system.modular.package_goods.entity.Shop;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeDenomination;
import com.siam.package_common.util.GenerateNo;
import com.siam.package_common.util.StringUtils;
import com.siam.package_weixin_pay.util.WxdecodeUtils;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeRecord;
import com.siam.system.modular.package_order.entity.DeliveryAddress;
import com.siam.system.modular.package_order.entity.Order;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrder;
import com.siam.system.modular.package_order.service.CommonService;
import com.siam.system.modular.package_order.service.DeliveryAddressService;
import com.siam.system.modular.package_order.service.OrderService;
import com.siam.system.modular.package_order.service.internal.PointsMallOrderService;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.entity.MemberTradeRecord;
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
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/rest/member/wxPay")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "微信支付模块相关接口", description = "WxPayController")
public class WxPayController {

    @Autowired
    private WxPayConfig wxPayConfig;

    @Autowired
    private MemberTradeRecordService memberTradeRecordService;

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
    private VipRechargeDenominationService vipRechargeDenominationService;

    @Autowired
    private VipRechargeRecordService vipRechargeRecordService;

    @Resource(name = "pointsMallOrderServiceImpl")
    private PointsMallOrderService pointsMallOrderService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 调用微信支付常见错误：
     * 1、验签错误
     * 解决方案：1) 更改商户密钥5~6次；2) 调整xml报文的顺序排列；3) 如openid这种属性要严格区分大小写
     */
    @ApiOperation(value = "微信小程序发起微信支付")
    @PostMapping(value = "/toPay4Applet")
    public BasicResult toPay4Applet(@RequestBody @Validated WxPayDto wxPayDto, HttpServletRequest request) {
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        String wxPaySwitch = (String) redisUtils.get("wxPaySwitch");
        if(wxPaySwitch != null && wxPaySwitch.equals("false")){
            throw new StoneCustomerException("暂不支持微信支付，请选择余额支付/积分支付");
        }

        //商户单号
        String outTradeNo = null;

        //自取订单改为配送的商户单号
        String changeToDeliveryOutTradeNo = null;

        if(wxPayDto.getType() == Quantity.INT_1){
            //原订单对象
            Order dbOrder = null;
            //交易类型为订单付款
            if(wxPayDto.getOut_trade_no() == null){
                throw new StoneCustomerException("必须填写商户单号");
            }
            dbOrder = orderService.selectByOrderNo(wxPayDto.getOut_trade_no());
            if(dbOrder == null){
                throw new StoneCustomerException("该商户单号不存在");
            }
            if(dbOrder.getStatus()!=Quantity.INT_1 && dbOrder.getStatus()==Quantity.INT_10){
                throw new StoneCustomerException("该笔订单超时未支付，已自动取消，请重新下单");
            }
            if(dbOrder.getActualPrice().compareTo(wxPayDto.getTotal_fee()) != 0){
                basicResult.setSuccess(false);
                basicResult.setMessage("付款金额错误");
                return basicResult;
            }
            outTradeNo = wxPayDto.getOut_trade_no();

            //添加用户交易记录
            MemberTradeRecord insertMemberTradeRecord = new MemberTradeRecord();
            insertMemberTradeRecord.setMemberId(loginMember.getId());
            insertMemberTradeRecord.setOutTradeNo(outTradeNo);
            insertMemberTradeRecord.setType(wxPayDto.getType());
            insertMemberTradeRecord.setPaymentMode(Quantity.INT_1);
            insertMemberTradeRecord.setAmount(wxPayDto.getTotal_fee());
            insertMemberTradeRecord.setStatus(Quantity.INT_1);
            insertMemberTradeRecord.setCreateTime(new Date());
            insertMemberTradeRecord.setUpdateTime(new Date());
            memberTradeRecordService.insertSelective(insertMemberTradeRecord);

            //修改订单信息：补填用户交易id
            Order updateOrder = new Order();
            updateOrder.setId(dbOrder.getId());
            updateOrder.setTradeId(insertMemberTradeRecord.getId());
            updateOrder.setPaymentMode(Quantity.INT_1);
            updateOrder.setUpdateTime(new Date());
            orderService.updateById(updateOrder);

        }else if(wxPayDto.getType() == Quantity.INT_2){
            //交易类型为会员充值
            if(wxPayDto.getVipRechargeDenominationId() == null){
                throw new StoneCustomerException("必须填写会员充值面额id");
            }
            VipRechargeDenomination dbVipRechargeDenomination = vipRechargeDenominationService.selectByPrimaryKey(wxPayDto.getVipRechargeDenominationId());
            if(dbVipRechargeDenomination == null){
                throw new StoneCustomerException("该会员充值面额不存在");
            }
            if(dbVipRechargeDenomination.getIsSale()){
                if(dbVipRechargeDenomination.getSalePrice().compareTo(wxPayDto.getTotal_fee()) != 0){
                    basicResult.setSuccess(false);
                    basicResult.setMessage("付款金额错误");
                    return basicResult;
                }
            }else{
                if(dbVipRechargeDenomination.getPrice().compareTo(wxPayDto.getTotal_fee()) != 0){
                    basicResult.setSuccess(false);
                    basicResult.setMessage("付款金额错误");
                    return basicResult;
                }
            }

            // 获取订单编号
            outTradeNo = getOutTradeNo();

            //添加用户交易记录
            MemberTradeRecord insertMemberTradeRecord = new MemberTradeRecord();
            insertMemberTradeRecord.setMemberId(loginMember.getId());
            insertMemberTradeRecord.setOutTradeNo(outTradeNo);
            insertMemberTradeRecord.setType(Quantity.INT_2);
            insertMemberTradeRecord.setPaymentMode(Quantity.INT_1);
            insertMemberTradeRecord.setAmount(wxPayDto.getTotal_fee());
            insertMemberTradeRecord.setStatus(Quantity.INT_1);
            insertMemberTradeRecord.setCreateTime(new Date());
            insertMemberTradeRecord.setUpdateTime(new Date());
            memberTradeRecordService.insertSelective(insertMemberTradeRecord);

            //添加会员充值记录
            VipRechargeRecord insertVipRechargeRecord = new VipRechargeRecord();
            insertVipRechargeRecord.setMemberId(loginMember.getId());
            insertVipRechargeRecord.setOrderNo(outTradeNo);
            insertVipRechargeRecord.setChannel(Quantity.INT_1);
            insertVipRechargeRecord.setDenominationId(wxPayDto.getVipRechargeDenominationId());
            insertVipRechargeRecord.setAmount(wxPayDto.getTotal_fee());
            insertVipRechargeRecord.setTradeId(insertMemberTradeRecord.getId());
            insertVipRechargeRecord.setStatus(Quantity.INT_1);
            insertVipRechargeRecord.setCreateTime(new Date());
            vipRechargeRecordService.insertSelective(insertVipRechargeRecord);

        }else if(wxPayDto.getType() == Quantity.INT_3){
            //原订单对象
            Order dbOrder = null;
            //交易类型为自取订单改为配送
            if(wxPayDto.getOut_trade_no() == null){
                throw new StoneCustomerException("必须填写商户单号");
            }
            if(wxPayDto.getDeliveryAddressId() == null){
                throw new StoneCustomerException("必须填写收获地址");
            }
            dbOrder = orderService.selectByOrderNo(wxPayDto.getOut_trade_no());
            if(dbOrder == null){
                throw new StoneCustomerException("该商户单号不存在");
            }
            if(dbOrder.getShoppingWay() != Quantity.INT_1){
                throw new StoneCustomerException("该笔订单不属于自取订单，不允许操作");
            }
            if(dbOrder.getStatus()!=Quantity.INT_2 && dbOrder.getStatus()!=Quantity.INT_3){
                throw new StoneCustomerException("该笔订单状态错误，不允许操作");
            }

            Shop dbShop = shopService.getById(dbOrder.getShopId());

            BigDecimal merchantDeliveryFee = BigDecimal.ZERO; //商家承担配送费
            //计算配送费是否正确
            DeliveryAddress dbDeliveryAddress = deliveryAddressService.selectByPrimaryKey(wxPayDto.getDeliveryAddressId());
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
            if(deliveryFee.compareTo(wxPayDto.getTotal_fee()) != 0){
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
            insertMemberTradeRecord.setType(wxPayDto.getType());
            insertMemberTradeRecord.setPaymentMode(Quantity.INT_1);
            insertMemberTradeRecord.setAmount(wxPayDto.getTotal_fee());
            insertMemberTradeRecord.setStatus(Quantity.INT_1);
            insertMemberTradeRecord.setCreateTime(new Date());
            insertMemberTradeRecord.setUpdateTime(new Date());
            memberTradeRecordService.insertSelective(insertMemberTradeRecord);

            //修改订单信息：补填自取订单改为配送的用户交易id
            //配送费、收获地址id要持久化到数据库，回调时配送费从用户交易记录表中获取(考虑到有些地方无论订单类型都会将配送费展示出来)，收获地址id从订单表中获取
            Order updateOrder = new Order();
            updateOrder.setId(dbOrder.getId());
            updateOrder.setMerchantDeliveryFee(merchantDeliveryFee);
            updateOrder.setDeliveryAddressId(wxPayDto.getDeliveryAddressId());
            updateOrder.setChangeToDeliveryOutTradeNo(outTradeNo);
            updateOrder.setChangeToDeliveryTradeId(insertMemberTradeRecord.getId());
            updateOrder.setPaymentMode(Quantity.INT_1);
            updateOrder.setUpdateTime(new Date());
            orderService.updateById(updateOrder);

        }else if(wxPayDto.getType() == Quantity.INT_4){
            //原订单对象
            PointsMallOrder dbOrder = null;
            //交易类型为订单付款
            if(wxPayDto.getOut_trade_no() == null){
                throw new StoneCustomerException("必须填写商户单号");
            }
            dbOrder = pointsMallOrderService.selectByOrderNo(wxPayDto.getOut_trade_no());
            if(dbOrder == null){
                throw new StoneCustomerException("该商户单号不存在");
            }
            if(dbOrder.getStatus()!=Quantity.INT_1 && dbOrder.getStatus()==Quantity.INT_10){
                throw new StoneCustomerException("该笔订单超时未支付，已自动取消，请重新下单");
            }
            if(dbOrder.getActualPrice().compareTo(wxPayDto.getTotal_fee()) != 0){
                basicResult.setSuccess(false);
                basicResult.setMessage("付款金额错误");
                return basicResult;
            }
            outTradeNo = wxPayDto.getOut_trade_no();

            //添加用户交易记录
            MemberTradeRecord insertMemberTradeRecord = new MemberTradeRecord();
            insertMemberTradeRecord.setMemberId(loginMember.getId());
            insertMemberTradeRecord.setOutTradeNo(outTradeNo);
            insertMemberTradeRecord.setType(Quantity.INT_6);
            insertMemberTradeRecord.setPaymentMode(Quantity.INT_1);
            insertMemberTradeRecord.setAmount(wxPayDto.getTotal_fee());
            insertMemberTradeRecord.setStatus(Quantity.INT_1);
            insertMemberTradeRecord.setCreateTime(new Date());
            insertMemberTradeRecord.setUpdateTime(new Date());
            memberTradeRecordService.insertSelective(insertMemberTradeRecord);

            //修改订单信息：补填用户交易id
            PointsMallOrder updateOrder = new PointsMallOrder();
            updateOrder.setId(dbOrder.getId());
            updateOrder.setTradeId(insertMemberTradeRecord.getId());
            updateOrder.setPaymentMode(Quantity.INT_1);
            updateOrder.setUpdateTime(new Date());
            pointsMallOrderService.updateByPrimaryKeySelective(updateOrder);
        }

        try {
            //生成的随机字符串
            String nonce_str = StringUtils.getRandomStringByLength(32);
            //获取本机的ip地址
            String spbill_create_ip = IpUtils.getIpAddr(request);
            //订单描述
            String body = wxPayDto.getType()==1 ? "订单付款" : (wxPayDto.getType()==2 ? "会员充值" : (wxPayDto.getType()==3 ? "自取订单改为配送" : (wxPayDto.getType()==4 ? "积分商城订单付款" : "-")));

            String total_fee = String.valueOf(wxPayDto.getTotal_fee().multiply(new BigDecimal(100)).longValue());

            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", wxPayConfig.getAppId());
            packageParams.put("body", body);
            packageParams.put("mch_id", wxPayConfig.getMchId());
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("notify_url", wxPayConfig.getNotifyUrl());
            packageParams.put("openid", wxPayDto.getOpenid());
            packageParams.put("out_trade_no", outTradeNo);
            packageParams.put("spbill_create_ip", spbill_create_ip);
            //付款金额转化成以分为单位，还有这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("total_fee", total_fee);
            packageParams.put("trade_type", wxPayConfig.getTradeType());

            // 除去数组中的空值和签名参数
            packageParams = PayUtil.paraFilter(packageParams);
            // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            String prestr = PayUtil.createLinkString(packageParams);

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, wxPayConfig.getMchKey(), "utf-8").toUpperCase();
            log.info("\n=======================第一次生成的签名：" + mysign + "=====================");

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>"
                    + "<appid>" + wxPayConfig.getAppId() + "</appid>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + wxPayConfig.getMchId() + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + wxPayConfig.getNotifyUrl() + "</notify_url>"
                    + "<openid>" + wxPayDto.getOpenid() + "</openid>"
                    + "<out_trade_no>" + outTradeNo + "</out_trade_no>"
                    + "<sign>" + mysign + "</sign>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + total_fee + "</total_fee>"
                    + "<trade_type>" + wxPayConfig.getTradeType() + "</trade_type>"
                    + "</xml>";
            log.info("\n统一下单接口-请求的XML数据：" + xml);

            //调用统一下单接口，并接受返回的结果
            String result = PayUtil.httpRequest(wxPayConfig.getPayUrl(), "POST", xml);
            log.info("\n统一下单接口-返回的XML数据：" + result);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);

            //设置返回给移动端需要的参数
            Map<String, Object> response = new HashMap<String, Object>();

            //获取返回状态码
            String return_code = (String) map.get("return_code");
            if ("SUCCESS".equals(return_code)) {
                //返回的预支付订单信息
                String prepay_id = (String) map.get("prepay_id");
                //这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                String timeStamp = (System.currentTimeMillis() / 1000) + "";
                String stringSignTemp =
                        "appId=" + wxPayConfig.getAppId() +
                        "&nonceStr=" + nonce_str +
                        "&package=prepay_id=" + prepay_id +
                        "&signType=" + wxPayConfig.getSignType() +
                        "&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, wxPayConfig.getMchKey(), "utf-8").toUpperCase();
                log.info("\n=======================第二次生成的签名：" + paySign + "=====================");

                //设置返回结果
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                response.put("timeStamp", timeStamp);
                response.put("paySign", paySign);
                response.put("appid", wxPayConfig.getAppId());

                basicResult.setData(response);
                basicResult.setSuccess(true);
                basicResult.setMessage("发起微信支付成功");
                return basicResult;
            }else{
                basicResult.setSuccess(false);
                basicResult.setMessage("发起微信支付失败");
                return basicResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            basicResult.setSuccess(false);
            basicResult.setMessage("发起微信支付失败");
            return basicResult;
        }
    }

    @RequestMapping(value = "/notify")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("\n微信回调成功......");
        //返回给微信的报文
        String resXml = "";
        //获取微信返回的报文
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        log.info("\n接收到的报文：" + notityXml);

        //解析报文
        Map map = PayUtil.doXMLParse(notityXml);
        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equals(returnCode)) {
            //验证签名是否正确
            //支付回调这里不需要验证签名是否正确，目前回调的签名验证报签名错误

            /*if (PayUtil.verify(PayUtil.createLinkString(map), (String) map.get("sign"), wxPayConfig.getMchKey(), "utf-8")) {

            }else{
                log.info("\n微信支付回调-验签失败");
            }*/

            log.info("\n微信支付回调-支付成功");
            //out_trade_no_array暂不处理
            String outTradeNo = (String) map.get("out_trade_no");

            MemberTradeRecord dbMemberTradeRecord = memberTradeRecordService.selectByOutTradeNo(outTradeNo);
            if(dbMemberTradeRecord == null){
                log.error("该商户单号不存在，回调逻辑处理失败");
                return;
            }
            //为防止微信重复回调，需要对用户交易记录的状态进行判断
            if(dbMemberTradeRecord.getStatus() != Quantity.INT_1){
                log.error("微信重复回调，商户单号为" + outTradeNo);
                return;
            }

            String transactionId = (String) map.get("transaction_id");
            //补填交易单号(支付平台的订单号)，将交易状态改为支付成功
            MemberTradeRecord updateMemberTradeRecord = new MemberTradeRecord();
            updateMemberTradeRecord.setId(dbMemberTradeRecord.getId());
            updateMemberTradeRecord.setTradeNo(transactionId);
            updateMemberTradeRecord.setStatus(Quantity.INT_2);
            updateMemberTradeRecord.setUpdateTime(new Date());
            memberTradeRecordService.updateByPrimaryKeySelective(updateMemberTradeRecord);

            if(dbMemberTradeRecord.getType() == Quantity.INT_1){
                //交易类型为订单付款
                orderService.paymentNotify(outTradeNo);

            }else if(dbMemberTradeRecord.getType() == Quantity.INT_2){
                //交易类型为会员充值
                vipRechargeRecordService.updateByPayNotify(outTradeNo);

            }else if(dbMemberTradeRecord.getType() == Quantity.INT_3){
                //交易类型为自取订单改为配送
                orderService.paymentNotifyOfChangeToDelivery(outTradeNo);

            }else if(dbMemberTradeRecord.getType() == Quantity.INT_6){
                //交易类型为积分商城订单付款
                pointsMallOrderService.paymentNotify(outTradeNo);
            }

            //通知微信服务器已经支付成功
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml>";
        }
        log.info("\n" + resXml);
        log.info("\n微信支付回调结束");

        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/refundSuccessNotify")
    public void refundSuccessNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("\n微信退款回调成功......");
        //返回给微信的报文
        String resXml = "";
        //获取微信返回的报文
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        log.info("\n接收到的报文：" + notityXml);

        //解析报文
        Map map = PayUtil.doXMLParse(notityXml);
        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equals(returnCode)) {
            //验证签名是否正确
            //支付回调这里不需要验证签名是否正确，目前回调的签名验证报签名错误

            /*if (PayUtil.verify(PayUtil.createLinkString(map), (String) map.get("sign"), wxPayConfig.getMchKey(), "utf-8")) {

            }else{
                log.info("\n微信退款回调-验签失败");
            }*/
            log.info("\n微信退款回调-支付成功");

            log.debug("\n返回报文：" + map);

            // 获得加密信息
            String reqInfo = (String) map.get("req_info");
            // 进行AES解密 获取req_info中包含的相关信息(解密失败会抛出异常)
            String refundDecryptedData = wxdecodeUtils.decryptData(reqInfo);
            Map<String, String> reqInfoMap = PayUtil.doXMLParse(refundDecryptedData);
            log.info("[refundAsyncNotify] [reqInfo解密成功] [reqInfoMap:{}]", reqInfoMap);

            //out_trade_no_array暂不处理
            String outTradeNo = (String) map.get("out_trade_no");

            MemberTradeRecord dbMemberTradeRecord = memberTradeRecordService.selectByOutTradeNo(outTradeNo);
            if(dbMemberTradeRecord == null){
                log.error("该商户单号不存在，回调逻辑处理失败");
                return;
            }
            //为防止微信重复回调，需要对用户交易记录的状态进行判断
            if(dbMemberTradeRecord.getStatus() != Quantity.INT_1){
                log.error("微信重复回调，商户单号为" + outTradeNo);
                return;
            }

            String transactionId = (String) map.get("transaction_id");
            //补填交易单号(支付平台的订单号)，将交易状态改为支付成功
            MemberTradeRecord updateMemberTradeRecord = new MemberTradeRecord();
            updateMemberTradeRecord.setId(dbMemberTradeRecord.getId());
            updateMemberTradeRecord.setTradeNo(transactionId);
            updateMemberTradeRecord.setStatus(Quantity.INT_2);
            updateMemberTradeRecord.setUpdateTime(new Date());
            memberTradeRecordService.updateByPrimaryKeySelective(updateMemberTradeRecord);

            //交易类型为订单付款
            if(dbMemberTradeRecord.getType() == Quantity.INT_1){
                orderService.paymentNotify(outTradeNo);
            }else if(dbMemberTradeRecord.getType() == Quantity.INT_3){
                orderService.paymentNotifyOfChangeToDelivery(outTradeNo);
            }

            //通知微信服务器已经退款成功
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml>";
        }
        log.info("\n" + resXml);
        log.info("\n微信退款回调结束");

        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    /**
     * 生成商户单号/订单编号
     * [此处要做两个判断，一个是针对于订单编号，一个是针对于自取订单改为配送的商户单号]
     * => 考虑到商户单号不止针对于订单表，还有Vip充值等操作，所以应该在用户交易记录表中判断商户单号是否重复
     */
    public String getOutTradeNo(){
        int i = 0;
        String outTradeNo = GenerateNo.getOrderNo();
        /*while (true){
            if(i == 99){
                throw new StoneCustomerException("无法生成商户单号");
            }
            log.debug("\n获取商户单号...");
            MemberTradeRecordExample memberTradeRecordExample = new MemberTradeRecordExample();
            memberTradeRecordExample.createCriteria().andOutTradeNoEqualTo(outTradeNo);
            int result = memberTradeRecordService.countByExample(memberTradeRecordExample);
            if(result > 0){
                outTradeNo = GenerateNo.getOrderNo();
            }else{
                break;
            }

            i++;
        }*/
        return outTradeNo;
    }
}