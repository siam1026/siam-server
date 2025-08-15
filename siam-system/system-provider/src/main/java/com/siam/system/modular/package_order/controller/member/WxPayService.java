package com.siam.system.modular.package_order.controller.member;

import com.siam.package_weixin_pay.util.Constants;
import com.siam.package_weixin_pay.util.PayUtil;
import com.siam.package_weixin_pay.config.WxPayConfig;
import com.siam.package_weixin_pay.entity.TransfersDto;
import com.siam.package_common.service.AliyunSms;
import com.siam.package_weixin_basic.service.WxPublicPlatformNotifyService;
import com.siam.package_common.util.CommonUtils;
import com.siam.package_common.util.GenerateNo;
import com.siam.system.modular.package_order.service.OrderRefundProcessService;
import com.siam.system.modular.package_order.service.OrderRefundService;
import com.siam.system.modular.package_order.service.OrderService;
import com.siam.package_weixin_pay.util.Constants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.security.KeyStore;
import java.util.*;

@Data
@Slf4j
@Service
public class WxPayService {

    @Value("${spring.profiles.active}")
    private String profilesActive;

    @Autowired
    private WxPayConfig wxPayConfig;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRefundService orderRefundService;

    @Autowired
    private OrderRefundProcessService orderRefundProcessService;

    @Autowired
    private AliyunSms aliyunSms;

//    @Autowired
//    private ShopService shopService;

//    @Autowired
//    private MerchantBillingRecordService merchantBillingRecordService;

//    @Autowired
//    private MerchantService merchantService;

    @Autowired
    private WxPublicPlatformNotifyService wxPublicPlatformNotifyService;

    private int socketTimeout = 10000;// 连接超时时间，默认10秒

    private int connectTimeout = 30000;// 传输超时时间，默认30秒

    private static RequestConfig requestConfig;// 请求器的配置

    private static CloseableHttpClient httpClient;// HTTP请求器

    /**
     * 企业付款到零钱
     */
    public boolean payToBalance(TransfersDto model){
        //只有正式环境才能执行
        if(!this.getProfilesActive().equals("prod")){
            return true;
        }
        try{
            // 1.计算参数签名
            String paramStr = createLinkString(model);
            String mysign = paramStr + "&key=" + wxPayConfig.getMchKey();
            String sign = DigestUtils.md5Hex(mysign).toUpperCase();

            // 2.封装请求参数
            StringBuilder reqXmlStr = new StringBuilder();
            reqXmlStr.append("<xml>");
            reqXmlStr.append("<mchid>" + wxPayConfig.getMchId() + "</mchid>");
            reqXmlStr.append("<mch_appid>" + wxPayConfig.getAppId() + "</mch_appid>");
            reqXmlStr.append("<nonce_str>" + model.getNonce_str() + "</nonce_str>");
            reqXmlStr.append("<check_name>" + model.getCheck_name() + "</check_name>");
            reqXmlStr.append("<re_user_name>" + model.getRe_user_name() + "</re_user_name>");
            reqXmlStr.append("<openid>" + model.getOpenid() + "</openid>");
            reqXmlStr.append("<amount>" + model.getAmount() + "</amount>");
            reqXmlStr.append("<desc>" + model.getDesc() + "</desc>");
            reqXmlStr.append("<sign>" + sign + "</sign>");
            reqXmlStr.append("<partner_trade_no>" + model.getPartner_trade_no() + "</partner_trade_no>");
            /*reqXmlStr.append("<spbill_create_ip>" + model.getSpbill_create_ip() + "</spbill_create_ip>");*/
            reqXmlStr.append("</xml>");

            log.info("request xml = " + reqXmlStr);

            // 3.加载证书请求接口
            String result = httpsRequest(wxPayConfig.getPayToBalanceUrl(), reqXmlStr.toString());
            log.info(("response xml = " + result));

            Map notifyMap = PayUtil.doXMLParse(result);
            if ("SUCCESS".equals(notifyMap.get("return_code"))) {
                if("SUCCESS".equals(notifyMap.get("result_code"))) {
                    //付款成功的操作
                }else{
                    String errorMsg = "";
                    String err_code = (String) notifyMap.get("err_code");
                    String err_code_des = (String) notifyMap.get("err_code_des");
                    errorMsg = model.getPartner_trade_no()+"订单企业付款到零钱失败，err_code : "+ err_code + "，err_code_des : " + err_code_des;
                    log.error(errorMsg);

                    //微信公众号消息通知管理员
                    wxPublicPlatformNotifyService.sendFatalErrorMessage(WxPublicPlatformNotifyService.jpOpenId, errorMsg, CommonUtils.getFunctionLocation(), "无", new Date(), "请管理员及时处理");

                    return false;
                }
            }else{
                String return_msg = (String) notifyMap.get("return_msg");
                String errorMsg = model.getPartner_trade_no()+"订单企业付款到零钱失败，return_msg : " + return_msg;
                log.error(errorMsg);

                //微信公众号消息通知管理员
                wxPublicPlatformNotifyService.sendFatalErrorMessage(WxPublicPlatformNotifyService.jpOpenId, errorMsg, CommonUtils.getFunctionLocation(), "无", new Date(), "请管理员及时处理");

                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error(e.toString(), e);

            String errorMsg = model.getPartner_trade_no()+"订单企业付款到零钱失败，catch Exception : " + e.toString();
            //微信公众号消息通知管理员
            wxPublicPlatformNotifyService.sendFatalErrorMessage(WxPublicPlatformNotifyService.jpOpenId, errorMsg, CommonUtils.getFunctionLocation(), "无", new Date(), "请管理员及时处理");

            return false;
        }
        return true;
    }

    private String createLinkString(TransfersDto model){
        // 微信签名规则 https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=4_3
        Map<String, Object> paramMap = new HashMap<String, Object>();

        // 订单号默认用商户号+时间戳+4位随机数+可以根据自己的规则进行调整
        /*model.setAppkey(wxPayConfig.getMchKey());*/
        model.setNonce_str(getNonce_str());
        /*model.setPartner_trade_no(model.getMchid()
                + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + (int)((Math.random() * 9 + 1) * 1000));*/

        paramMap.put("mch_appid", wxPayConfig.getAppId());
        paramMap.put("mchid", wxPayConfig.getMchId());
        paramMap.put("openid", model.getOpenid());
        paramMap.put("amount", model.getAmount());
        paramMap.put("check_name", model.getCheck_name());
        paramMap.put("re_user_name", model.getRe_user_name());
        paramMap.put("desc", model.getDesc());
        paramMap.put("partner_trade_no", model.getPartner_trade_no());
        paramMap.put("nonce_str", model.getNonce_str());
        /*paramMap.put("spbill_create_ip", model.getSpbill_create_ip());*/

        List<String> keys = new ArrayList(paramMap.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++ ){
            String key = keys.get(i);
            Object value = (Object)paramMap.get(key);
            if (i == keys.size() - 1){// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            }
            else{
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    private static String getNonce_str()
    {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++ )
        {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    //具体的调用微信的退款接口
    public boolean refund(String out_trade_no, BigDecimal total_fee, BigDecimal refund_fee){
        //只有正式环境才能执行
        if(!this.getProfilesActive().equals("prod")){
            return true;
        }
        String code = Constants.CODE_SUCCESS;//状态码
        String msg = Constants.REFUND_SUCCESS;//提示信息
        Map <String,String> data = new HashMap<String,String>();

        String total_fee_text = String.valueOf(total_fee.multiply(new BigDecimal(100)).longValue());
        String refund_fee_text = String.valueOf(refund_fee.multiply(new BigDecimal(100)).longValue());

        try {
            //退款到用户微信
            String nonce_str = getRandomStringByLength(32);
            /*data.put("userId", String.valueOf("00000000000000000"));*/
            data.put("appid", wxPayConfig.getAppId());
            data.put("mch_id", wxPayConfig.getMchId());
            data.put("nonce_str", nonce_str);
            data.put("sign_type", "MD5");
            data.put("out_trade_no", out_trade_no);//商户订单号
            data.put("out_refund_no", GenerateNo.getUUID());//商户退款单号
            /*Math.round(userecord.getMoney() * 100);*/
            data.put("total_fee", total_fee_text);//支付金额，微信支付提交的金额是不能带小数点的，且是以分为单位,这边需要转成字符串类型，否则后面的签名会失败
            data.put("refund_fee", refund_fee_text);//退款总金额,订单总金额,单位为分,只能为整数
            /*data.put("notify_url", wxPayConfig.getRefundSuccessNotifyUrl());//退款成功后的回调地址*/
            String preStr = PayUtil.createLinkString(data); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mySign = PayUtil.sign(preStr, wxPayConfig.getMchKey(), "utf-8").toUpperCase();
            data.put("sign", mySign);

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xmlStr = postData(wxPayConfig.getRefundUrl(), PayUtil.GetMapToXML(data)); //支付结果通知的xml格式数据
            System.out.println(xmlStr);
            Map notifyMap = PayUtil.doXMLParse(xmlStr);
            if ("SUCCESS".equals(notifyMap.get("return_code"))) {
                if("SUCCESS".equals(notifyMap.get("result_code"))) {
                    //退款成功的操作
                    orderService.updateRefundStatus(out_trade_no);

                    /*//退款成功的操作
                    String prepay_id = (String) notifyMap.get("prepay_id");//返回的预付单信息
                    System.out.println(prepay_id);
                    Long timeStamp = System.currentTimeMillis() / 1000;
                    //拼接签名需要的参数
                    String stringSignTemp = "appId=" + wxPayConfig.getAppId() + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
                    //签名算法生成签名
                    String paySign = PayUtil.sign(stringSignTemp, wxPayConfig.getMchKey(), "utf-8").toUpperCase();
                    data.put("package", "prepay_id=" + prepay_id);
                    data.put("timeStamp", String.valueOf(timeStamp));
                    data.put("paySign", paySign);*/
                }else{
                    String errorMsg = "";
                    String err_code = (String) notifyMap.get("err_code");
                    String err_code_des = (String) notifyMap.get("err_code_des");
                    errorMsg = out_trade_no+"订单退款失败，err_code : "+ err_code + "，err_code_des : " + err_code_des;
                    log.error(errorMsg);

                    //微信公众号消息通知管理员
                    wxPublicPlatformNotifyService.sendFatalErrorMessage(WxPublicPlatformNotifyService.jpOpenId, errorMsg, CommonUtils.getFunctionLocation(), "无", new Date(), "请管理员及时处理");

                    return false;
                }
            }else{
                String return_msg = (String) notifyMap.get("return_msg");
                String errorMsg = out_trade_no+"订单退款失败，return_msg : " + return_msg;
                log.error(errorMsg);

                //微信公众号消息通知管理员
                wxPublicPlatformNotifyService.sendFatalErrorMessage(WxPublicPlatformNotifyService.jpOpenId, errorMsg, CommonUtils.getFunctionLocation(), "无", new Date(), "请管理员及时处理");

                return false;
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString(), e);

            String errorMsg = out_trade_no+"订单退款失败，catch Exception : " + e.toString();
            //微信公众号消息通知管理员
            wxPublicPlatformNotifyService.sendFatalErrorMessage(WxPublicPlatformNotifyService.jpOpenId, errorMsg, CommonUtils.getFunctionLocation(), "无", new Date(), "请管理员及时处理");

            return false;
        }
        /*Map <String,Object> jsonResult = new HashMap<String,Object>();
        jsonResult.put("code",code);
        jsonResult.put("msg",msg);
        jsonResult.put("data",data);
        System.out.println(jsonResult);*/
        return true;
    }

    /**
     * 加载证书
     *
     */
    private void initCert() throws Exception {
        // 证书密码，默认为商户ID
        String key = wxPayConfig.getMchId();
        // 商户证书的路径
        /*String path = Constants.CERT_PATH;*/

        // 指定读取证书格式为PKCS12
        KeyStore keyStore = KeyStore.getInstance("pkcs12");

        // 读取本机存放的PKCS12证书文件
        /*FileInputStream instream = new FileInputStream(new File(path));*/
        ClassPathResource classPathResource = new ClassPathResource("wx_cert/apiclient_cert.p12");
        InputStream instream = classPathResource.getInputStream();

        try {
            // 指定PKCS12的密码(商户ID)
            keyStore.load(instream, key.toCharArray());
        } finally {
            instream.close();
        }

        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, key.toCharArray()).build();

        // 指定TLS版本
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, null, null,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        // 设置httpclient的SSLSocketFactory
        httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }


    /**
     * 通过Https往API post xml数据
     * @param url  API地址
     * @param xmlObj   要提交的XML数据对象
     * @return
     */
    public  String postData(String url, String xmlObj) {
        // 加载证书
        try {
            initCert();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(xmlObj, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        // 根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .build();
        // 设置请求器的配置
        httpPost.setConfig(requestConfig);
        try {
            HttpResponse response = null;
            try {
                response = httpClient.execute(httpPost);
            }  catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity entity = response.getEntity();
            try {
                result = EntityUtils.toString(entity, "UTF-8");
            }  catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            httpPost.abort();
        }
        return result;
    }

    private  String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 通过Https往API post xml数据
     *
     * @param url
     *            API地址
     * @param xmlObj
     *            要提交的XML数据对象
     * @return
     * @throws IOException
     */
    public String httpsRequest(String url, String xmlObj) throws Exception {
        // 加载证书
        initCert();

        String result = null;

        HttpPost httpPost = new HttpPost(url);

        // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(xmlObj, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        // 设置请求器的配置
        httpPost.setConfig(requestConfig);

        try{
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        }catch (ConnectionPoolTimeoutException e){

        }catch (ConnectTimeoutException e){

        }catch (SocketTimeoutException e){

        }catch (Exception e){

        }finally{
            httpPost.abort();
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        /*FileInputStream instream = new FileInputStream(new File("src/main/resources/wx_cert/apiclient_cert.p12"));*/

        ClassPathResource classPathResource = new ClassPathResource("wx_cert/apiclient_cert.p12");
        InputStream inputStream = classPathResource.getInputStream();
        System.out.println(inputStream);
    }
}