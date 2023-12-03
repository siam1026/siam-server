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
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WxPublicPlatformNotifyService {

    public static final String jpOpenId = "ocYTLtxbT_Hlidv4MjZRSVhgqa8I";

    public static final String tkOpenId = "ocYTLtzWLOUUwczetzDFoAXaOWNM";

    public static final String tkOpenId_mall = "ocYTLt4kCynv9cGA-kjIdTwyoDHU";

    /**
     * 给商家发送新订单通知
     */
    public void sendNewOrderMessageForMerchant(String openId, String title, String goodsName, Date createTime, String deliveryAddress, String contacts, String amountDescription, String remark){
        String appid = "wx2e1a8193d3ed12fe"; //小程序appid
        String access_token = this.getAccessToken();

        Map data = new HashMap();
        //新订单服务通知
        Map subMap = new HashMap();
        subMap.put("value", title);
        data.put("first", subMap);

        subMap = new HashMap();
        subMap.put("value", goodsName);
        data.put("keyword1", subMap);

        subMap = new HashMap();
        subMap.put("value", DateUtilsPlus.formatDate(createTime, "yyyy-MM-dd HH:mm:ss"));
        data.put("keyword2", subMap);

        subMap = new HashMap();
        subMap.put("value", deliveryAddress);
        data.put("keyword3", subMap);

        subMap = new HashMap();
        subMap.put("value", contacts);
        data.put("keyword4", subMap);

        //已付款
        subMap = new HashMap();
        subMap.put("value", amountDescription);
        data.put("keyword5", subMap);

        //请您尽快处理哦！
        subMap = new HashMap();
        subMap.put("value", remark);
        /*subMap.put("value", "加辣加辣加辣加辣加辣加辣加辣加辣加辣加" +
                "辣加辣加辣加辣加辣加辣加辣加辣加辣加辣加辣加辣" +
                "加辣加辣加辣加辣加辣加辣加辣加辣加辣加辣加辣加" +
                "辣加辣加辣加辣");*/
        data.put("remark", subMap);

        Map paramsMap = new HashMap();
        paramsMap.put("access_token", access_token);
        paramsMap.put("touser", openId);
        paramsMap.put("template_id", "vSk8PeGfK1nioxngvd-NFjSoPek7EM0zO-4yMQUsBKk");
        paramsMap.put("appid", appid);
        paramsMap.put("data", data);

        try {
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token);
            httpPost.setEntity(new StringEntity(GsonUtils.toJson(paramsMap), HTTP.UTF_8));
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
            Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
            if((double) map.get("errcode") != 0){
                log.debug("\n\n微信公众号-发送消息失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给商家发送订单取消通知
     */
    public void sendOrderCancelMessageForMerchant(String openId, String title, String orderDescription, String orderAmount, String refundAmount, Date cancelTime, String contacts, String remark){
        String appid = "wx2e1a8193d3ed12fe"; //小程序appid
        String access_token = this.getAccessToken();

        Map data = new HashMap();
        //新订单服务通知
        Map subMap = new HashMap();
        subMap.put("value", title);
        data.put("first", subMap);

        subMap = new HashMap();
        subMap.put("value", orderDescription);
        data.put("keyword1", subMap);

        subMap = new HashMap();
        subMap.put("value", orderAmount);
        data.put("keyword2", subMap);

        subMap = new HashMap();
        subMap.put("value", refundAmount);
        data.put("keyword3", subMap);

        subMap = new HashMap();
        subMap.put("value", DateUtilsPlus.formatDate(cancelTime, "yyyy-MM-dd HH:mm:ss"));
        data.put("keyword4", subMap);

        //已付款
        subMap = new HashMap();
        subMap.put("value", contacts);
        data.put("keyword5", subMap);

        //请您尽快处理哦！
        subMap = new HashMap();
        subMap.put("value", remark);
        data.put("remark", subMap);

        Map paramsMap = new HashMap();
        paramsMap.put("access_token", access_token);
        paramsMap.put("touser", openId);
        paramsMap.put("template_id", "xoj8oiAQH2xgUdnKsDGSlfjb089CUVR0P4adun-Bkl0");
        paramsMap.put("appid", appid);
        paramsMap.put("data", data);

        try {
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token);
            httpPost.setEntity(new StringEntity(GsonUtils.toJson(paramsMap), HTTP.UTF_8));
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
            Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
            if((double) map.get("errcode") != 0){
                log.debug("\n\n微信公众号-发送消息失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给商家发送订单退款提醒
     */
    public void sendOrderRefundMessageForMerchant(String openId, String title, String goodsName, Date createTime, String orderDescription, String contacts, String refundReason, String remark){
        String appid = "wx2e1a8193d3ed12fe"; //小程序appid
        String access_token = this.getAccessToken();

        Map data = new HashMap();
        //新订单服务通知
        Map subMap = new HashMap();
        subMap.put("value", title);
        data.put("first", subMap);

        subMap = new HashMap();
        subMap.put("value", goodsName);
        data.put("keyword1", subMap);

        subMap = new HashMap();
        subMap.put("value", DateUtilsPlus.formatDate(createTime, "yyyy-MM-dd HH:mm:ss"));
        data.put("keyword2", subMap);

        subMap = new HashMap();
        subMap.put("value", orderDescription);
        data.put("keyword3", subMap);

        subMap = new HashMap();
        subMap.put("value", contacts);
        data.put("keyword4", subMap);

        //已付款
        subMap = new HashMap();
        subMap.put("value", refundReason);
        data.put("keyword5", subMap);

        //请您尽快处理哦！
        subMap = new HashMap();
        subMap.put("value", remark);
        data.put("remark", subMap);

        Map paramsMap = new HashMap();
        paramsMap.put("access_token", access_token);
        paramsMap.put("touser", openId);
        paramsMap.put("template_id", "FHEvM5ktRd-2YsWHCwQj498lK9kwBKA9AUqobb_pw9k");
        paramsMap.put("appid", appid);
        paramsMap.put("data", data);

        try {
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token);
            httpPost.setEntity(new StringEntity(GsonUtils.toJson(paramsMap), HTTP.UTF_8));
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
            Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
            if((double) map.get("errcode") != 0){
                log.debug("\n\n微信公众号-发送消息失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给管理员发送程序错误提醒/设备故障提醒
     */
    public void sendFatalErrorMessage(String openId, String title, String functionLocation, String errorCode, Date createTime, String remark){
        String appid = "wx2e1a8193d3ed12fe"; //小程序appid
        String access_token = this.getAccessToken();

        Map data = new HashMap();
        //新订单服务通知
        Map subMap = new HashMap();
        subMap.put("value", title);
        data.put("first", subMap);

        subMap = new HashMap();
        subMap.put("value", functionLocation);
        data.put("keyword1", subMap);

        subMap = new HashMap();
        subMap.put("value", errorCode);
        data.put("keyword2", subMap);

        subMap = new HashMap();
        subMap.put("value", DateUtilsPlus.formatDate(createTime, "yyyy-MM-dd HH:mm:ss"));
        data.put("keyword3", subMap);

        //请您尽快处理哦！
        subMap = new HashMap();
        subMap.put("value", remark);
        data.put("remark", subMap);

        Map paramsMap = new HashMap();
        paramsMap.put("access_token", access_token);
        paramsMap.put("touser", openId);
        paramsMap.put("template_id", "sU2nDXMiZl1Jjm_5rLRqzD0OCWPVlSMNjN3ixbZU9rE");
        paramsMap.put("appid", appid);
        paramsMap.put("data", data);

        try {
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token);
            httpPost.setEntity(new StringEntity(GsonUtils.toJson(paramsMap), HTTP.UTF_8));
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
            Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
            if((double) map.get("errcode") != 0){
                log.debug("\n\n微信公众号-发送消息失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取access_token
     */
    private String getAccessToken() {
        String appid = "wxd28950054b3c01ff";
        String secret = "d0054fc133b5dffae28050e33a5e1873";
        //获取access_token
        String access_token = "";
        try {
            HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret);
            HttpResponse httpResponseGetToken = new DefaultHttpClient().execute(httpGet);
            Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponseGetToken.getEntity(), StandardCharsets.UTF_8));
            if(map.get("access_token") == null){
                log.debug("\n\n微信公众号-获取access_token失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
            }
            access_token = (String) map.get("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_token;
    }
}