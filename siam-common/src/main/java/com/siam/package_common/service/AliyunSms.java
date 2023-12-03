package com.siam.package_common.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.siam.package_common.util.DateUtilsPlus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Random;

@Component
@ConfigurationProperties(value = "aliyun.dysms")
public class AliyunSms {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String domain = "dysmsapi.aliyuncs.com";

    private static final String version = "2017-05-25";

    private static final String action = "SendSms";

    private String accessKeyId;

    private String secret;

    private String signName;

    private String templateCode_verificationCode;

    private String templateCode_fatalError;

    private String templateCode_pickUpOrderComplete;

    private String templateCode_takeOutOrderDelivery;

    private String templateCode_couponsDistributeReminder;

    private String templateCode_couponsOverdueReminder;

    private IAcsClient client;

    @PostConstruct
    public void init(){
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, secret);
        client = new DefaultAcsClient(profile);
    }

    /**
     * 发送手机验证码
     * @param phoneNumbers 用户手机号
     * @return
     */
    public String sendVerificationCodeMessage(String phoneNumbers) {
        String mobileCode = genMobileCode();
        CommonRequest request = new CommonRequest();
        request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction(action);
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode_verificationCode);
        //mobileCode一定要用双引号包着，否则会被作为数字处理，那么开头的0就会被去掉
        String templateParam = "{code : \"" + mobileCode + "\"}";
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            logger.info(response.getData());
        } catch (ServerException e) {
            logger.error(e.toString());
        } catch (ClientException e) {
            logger.error(e.toString());
        }
        return mobileCode;
    }

    /**
     * 程序运行时发生致命错误发送消息提醒开发人员及时处理
     * @param phoneNumbers 开发人员手机号
     * @param description 错误描述
     */
    public void sendFatalErrorMessage(String phoneNumbers, String description) {
        CommonRequest request = new CommonRequest();
        request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction(action);
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode_fatalError);

        String date = DateUtilsPlus.formatDate(new Date(), "YYYY-MM-dd HH:mm:ss");

        String templateParam = "{" +
                                    "time : '" + date + "', " +
                                    "description : '" + description + "', " +
                                "}";
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            logger.info(response.getData());
        } catch (ServerException e) {
            logger.error(e.toString());
        } catch (ClientException e) {
            logger.error(e.toString());
        }
    }

    /**
     * 自取订单制作完成后通知用户及时领取
     * @param phoneNumbers 用户手机号
     * @param queueNo 取餐号
     * @param shopName 自取店铺名称
     */
    public void sendPickUpOrderCompleteMessage(String phoneNumbers, String queueNo, String shopName) {
        CommonRequest request = new CommonRequest();
        request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction(action);
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode_pickUpOrderComplete);

        String templateParam = "{" +
                "queue_no : '" + queueNo + "', " +
                "shop_name : '" + shopName + "', " +
                "}";
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            logger.info(response.getData());
        } catch (ServerException e) {
            logger.error(e.toString());
        } catch (ClientException e) {
            logger.error(e.toString());
        }
    }

    /**
     * 外卖订单已配送通知用户等待领取
     * @param phoneNumbers 用户手机号
     */
    public void sendTakeOutOrderDeliveryMessage(String phoneNumbers) {
        CommonRequest request = new CommonRequest();
        request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction(action);
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode_takeOutOrderDelivery);

        String templateParam = "{" +
                "}";
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            logger.info(response.getData());
        } catch (ServerException e) {
            logger.error(e.toString());
        } catch (ClientException e) {
            logger.error(e.toString());
        }
    }

    /**
     * 群发优惠券后通知用户
     * @param phoneNumbers 用户手机号
     * @param couponsName 优惠券名称
     */
    public void sendCouponsDistributeReminderMessage(String phoneNumbers, String couponsName) {
        CommonRequest request = new CommonRequest();
        request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction(action);
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode_couponsDistributeReminder);

        String templateParam = "{" +
                "coupons_name : '" + couponsName + "', " +
                "}";
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            logger.info(response.getData());
        } catch (ServerException e) {
            logger.error(e.toString());
        } catch (ClientException e) {
            logger.error(e.toString());
        }
    }

    /**
     * 优惠券即将过期提醒用户/隔一段时间检测到用户还有未使用的优惠券则提醒用户
     * @param phoneNumbers 用户手机号
     * //@param couponsName 优惠券名称
     */
    public void sendCouponsOverdueReminderMessage(String phoneNumbers) {
        CommonRequest request = new CommonRequest();
        request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction(action);
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode_couponsOverdueReminder);

        String templateParam = "{}";
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            logger.info(response.getData());
        } catch (ServerException e) {
            logger.error(e.toString());
        } catch (ClientException e) {
            logger.error(e.toString());
        }
    }
    /*public void sendCouponsOverdueReminderMessage(String phoneNumbers, String couponsName) {
        CommonRequest request = new CommonRequest();
        request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction(action);
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode_couponsOverdueReminder);

        String templateParam = "{" +
                "coupons_name : '" + couponsName + "', " +
                "}";
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            logger.info(response.getData());
        } catch (ServerException e) {
            logger.error(e.toString());
        } catch (ClientException e) {
            logger.error(e.toString());
        }
    }*/

    private String genMobileCode(){
        Random r = new Random();
        StringBuilder code = new StringBuilder();
        for(int i = 0; i < 6; i++) {
            code.append(r.nextInt(10));
        }
        return code.toString();
    }

    /*private String genMobileCode(){
        Random r = new Random();
        StringBuilder code = new StringBuilder();
        int number = 0;
        for(int i = 0; i < 6; i++) {
            do {
                number = r.nextInt(10);
            }while (i==0 && number==0);
            code.append(number);
        }
        return code.toString();
    }*/

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode_verificationCode() {
        return templateCode_verificationCode;
    }

    public void setTemplateCode_verificationCode(String templateCode_verificationCode) {
        this.templateCode_verificationCode = templateCode_verificationCode;
    }

    public String getTemplateCode_fatalError() {
        return templateCode_fatalError;
    }

    public void setTemplateCode_fatalError(String templateCode_fatalError) {
        this.templateCode_fatalError = templateCode_fatalError;
    }

    public String getTemplateCode_pickUpOrderComplete() {
        return templateCode_pickUpOrderComplete;
    }

    public void setTemplateCode_pickUpOrderComplete(String templateCode_pickUpOrderComplete) {
        this.templateCode_pickUpOrderComplete = templateCode_pickUpOrderComplete;
    }

    public String getTemplateCode_takeOutOrderDelivery() {
        return templateCode_takeOutOrderDelivery;
    }

    public void setTemplateCode_takeOutOrderDelivery(String templateCode_takeOutOrderDelivery) {
        this.templateCode_takeOutOrderDelivery = templateCode_takeOutOrderDelivery;
    }

    public String getTemplateCode_couponsDistributeReminder() {
        return templateCode_couponsDistributeReminder;
    }

    public void setTemplateCode_couponsDistributeReminder(String templateCode_couponsDistributeReminder) {
        this.templateCode_couponsDistributeReminder = templateCode_couponsDistributeReminder;
    }

    public String getTemplateCode_couponsOverdueReminder() {
        return templateCode_couponsOverdueReminder;
    }

    public void setTemplateCode_couponsOverdueReminder(String templateCode_couponsOverdueReminder) {
        this.templateCode_couponsOverdueReminder = templateCode_couponsOverdueReminder;
    }
}