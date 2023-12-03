package com.siam.package_weixin_basic.service;

import com.siam.package_common.util.DateUtilsPlus;
import com.siam.package_common.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WxNotifyService {

    String miniprogram_state = "formal";

//    /**
//     * 订单支付提醒/付款成功通知
//     */
//    public void sendOrderPaidMessage(String openId, String orderId, String goodsName, BigDecimal amount, Date paymentTime, String remark){
//        String access_token = this.getAccessToken();
//
//        Map data = new HashMap();
//
//        Map subMap = new HashMap();
//        subMap.put("value", orderId);
//        data.put("character_string5", subMap);
//
//        subMap = new HashMap();
//        subMap.put("value", goodsName);
//        data.put("thing4", subMap);
//
//        subMap = new HashMap();
//        subMap.put("value", amount);
//        data.put("amount1", subMap);
//
//        subMap = new HashMap();
//        subMap.put("value", DateUtils.formatDate(paymentTime, "yyyy-MM-dd HH:mm:ss"));
//        data.put("date2", subMap);
//
//        subMap = new HashMap();
//        subMap.put("value", remark);
//        data.put("thing3", subMap);
//
//        Map paramsMap = new HashMap();
//        paramsMap.put("access_token", access_token);
//        paramsMap.put("touser", openId);
//        paramsMap.put("template_id", "N63hQksq6Rp3XlrBySkligk-pSvvpJ5fCovwUkwHVH4");
//        paramsMap.put("data", data);
//        paramsMap.put("miniprogram_state", miniprogram_state);
//
//        try {
//            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + access_token);
//            httpPost.setEntity(new StringEntity(GsonUtils.toJson(paramsMap), HTTP.UTF_8));
//            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
//            Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
//            if((double) map.get("errcode") != 0){
//                log.debug("\n\n付款成功通知发送失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 自取订单制作完成后通知用户及时领取/自取消息提醒
     */
    public void sendPickUpOrderCompleteMessage(String openId, String queueNo, String shopName, String shopAddress, String goodsName){
        String access_token = this.getAccessToken();

        Map data = new HashMap();

        Map subMap = new HashMap();
        subMap.put("value", queueNo);
        data.put("character_string5", subMap);

        subMap = new HashMap();
        subMap.put("value", shopName);
        data.put("thing1", subMap);

        subMap = new HashMap();
        subMap.put("value", shopAddress);
        data.put("thing2", subMap);

        subMap = new HashMap();
        subMap.put("value", goodsName);
        data.put("thing3", subMap);

        subMap = new HashMap();
        subMap.put("value", "为了不影响饮品口感，请及时提取和饮用。");
        data.put("thing8", subMap);

        Map paramsMap = new HashMap();
        paramsMap.put("access_token", access_token);
        paramsMap.put("touser", openId);
        paramsMap.put("template_id", "eF29ugG7ZKKKHCDH2Nk48Q2JCH1qKDLBMX2LnAzCz-w");
        paramsMap.put("data", data);
        paramsMap.put("miniprogram_state", miniprogram_state);

        try {
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + access_token);
            httpPost.setEntity(new StringEntity(GsonUtils.toJson(paramsMap), HTTP.UTF_8));
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
            Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
            if((double) map.get("errcode") != 0){
                log.debug("\n\n自取消息提醒发送失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 外卖订单已配送通知用户等待领取/订单配送通知
     */
    public void sendTakeOutOrderDeliveryMessage(String openId, String queueNo, String shopName, String deliveryAddress, String goodsName){
        String access_token = this.getAccessToken();

        Map data = new HashMap();

        Map subMap = new HashMap();
        subMap.put("value", queueNo);
        data.put("character_string14", subMap);

        subMap = new HashMap();
        subMap.put("value", shopName);
        data.put("thing1", subMap);

        subMap = new HashMap();
        subMap.put("value", deliveryAddress);
        data.put("thing9", subMap);

        subMap = new HashMap();
        subMap.put("value", goodsName);
        data.put("thing6", subMap);

        //此处限制20个字符
        subMap = new HashMap();
        subMap.put("value", "请您保持电话畅通，提前下楼就位取餐！");
        data.put("thing5", subMap);

        Map paramsMap = new HashMap();
        paramsMap.put("access_token", access_token);
        paramsMap.put("touser", openId);
        paramsMap.put("template_id", "YUNE7UPxeqqZZpVFzLWz51fKq3i045SzVwOJKZ_pEiw");
        paramsMap.put("data", data);
        paramsMap.put("miniprogram_state", miniprogram_state);

        try {
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + access_token);
            httpPost.setEntity(new StringEntity(GsonUtils.toJson(paramsMap), HTTP.UTF_8));
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
            Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
            if((double) map.get("errcode") != 0){
                log.debug("\n\n订单开始配送通知发送失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 订单已完成提醒/订单完成通知
//     */
//    public void sendOrderCompletedMessage(String openId, String shopName, String orderId, BigDecimal amount, Date createTime){
//        String access_token = this.getAccessToken();
//
//        Map data = new HashMap();
//
//        Map subMap = new HashMap();
//        subMap.put("value", shopName);
//        data.put("thing1", subMap);
//
//        subMap = new HashMap();
//        subMap.put("value", orderId);
//        data.put("character_string2", subMap);
//
//        subMap = new HashMap();
//        subMap.put("value", amount);
//        data.put("amount3", subMap);
//
//        subMap = new HashMap();
//        subMap.put("value", DateUtils.formatDate(createTime, "yyyy-MM-dd HH:mm:ss"));
//        data.put("time4", subMap);
//
//        subMap = new HashMap();
//        subMap.put("value", "您的订单已完成");
//        data.put("thing5", subMap);
//
//        Map paramsMap = new HashMap();
//        paramsMap.put("access_token", access_token);
//        paramsMap.put("touser", openId);
//        paramsMap.put("template_id", "E_fMNRGeNtciUn9qYBIMrqB37uDKDAeOsat2GQQO7F4");
//        paramsMap.put("data", data);
//        paramsMap.put("miniprogram_state", miniprogram_state);
//
//        try {
//            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + access_token);
//            httpPost.setEntity(new StringEntity(GsonUtils.toJson(paramsMap), HTTP.UTF_8));
//            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
//            Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
//            if((double) map.get("errcode") != 0){
//                log.debug("\n\n订单完成通知发送失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 退款成功通知
     */
    public void sendOrderRefundSuccessMessage(String openId, String shopName, String orderNo, String goodsName, BigDecimal amount, Date refundTime){
        String access_token = this.getAccessToken();

        Map data = new HashMap();

        Map subMap = new HashMap();
        subMap.put("value", shopName);
        data.put("thing8", subMap);

        subMap = new HashMap();
        subMap.put("value", orderNo);
        data.put("character_string1", subMap);

        subMap = new HashMap();
        subMap.put("value", goodsName);
        data.put("thing3", subMap);

        subMap = new HashMap();
        subMap.put("value", amount);
        data.put("amount5", subMap);

        subMap = new HashMap();
        subMap.put("value", DateUtilsPlus.formatDate(refundTime, "yyyy-MM-dd HH:mm:ss"));
        data.put("date2", subMap);

        Map paramsMap = new HashMap();
        paramsMap.put("access_token", access_token);
        paramsMap.put("touser", openId);
        paramsMap.put("template_id", "iclsEo9mCyiVNDT4lZjb3NI6_H6cgZaC3e7IiI2XnxY");
        paramsMap.put("data", data);
        paramsMap.put("miniprogram_state", miniprogram_state);

        try {
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + access_token);
            httpPost.setEntity(new StringEntity(GsonUtils.toJson(paramsMap), HTTP.UTF_8));
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
            Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
            if((double) map.get("errcode") != 0){
                log.debug("\n\n退款成功通知发送失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取access_token
     */
    private String getAccessToken() {
        String appid = "wx2e1a8193d3ed12fe";
        String secret = "2774e3a86dc30fbf1ac63d81b56f2291";
        //获取access_token
        String access_token = "";
        try {
            HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret);
            HttpResponse httpResponseGetToken = new DefaultHttpClient().execute(httpGet);
            Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponseGetToken.getEntity(), StandardCharsets.UTF_8));
            if(map.get("access_token") == null){
                log.debug("\n\n服务通知-获取access_token失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
            }
            access_token = (String) map.get("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_token;
    }
}