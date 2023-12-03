package com.siam.package_weixin_pay.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.siam.package_weixin_pay.entity.ResultEntity;
import com.siam.package_weixin_pay.entity.TransfersDto;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class WechatpayUtil
{
    private static final Log LOG = LogFactory.getLog(WechatpayUtil.class);
    
    private static final String TRANS_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    // 微信商户appkey
    private static String APP_KEY = "";

    // 微信商户证书路径
    private static String CERT_PATH = "";
    
    /**
     * @param model
     *            微信接口请求参数DTO对象
     * @return ResultEntity 返回结构体
     */
    public static ResultEntity doTransfers(String appkey, String certPath, TransfersDto model)
    {
        // appkey和certPath可以直接写在常量, 这里使用参数是为了方便理解
        APP_KEY = appkey;
        CERT_PATH = certPath;
        try
        {
            // 1.计算参数签名
            String paramStr = WechatpayUtil.createLinkString(model);
            String mysign = paramStr + "&key=" + APP_KEY;
            String sign = DigestUtils.md5Hex(mysign).toUpperCase();

            // 2.封装请求参数
            StringBuilder reqXmlStr = new StringBuilder();
            reqXmlStr.append("<xml>");
            /*reqXmlStr.append("<mchid>" + model.getMchid() + "</mchid>");
            reqXmlStr.append("<mch_appid>" + model.getMch_appid() + "</mch_appid>");*/
            reqXmlStr.append("<nonce_str>" + model.getNonce_str() + "</nonce_str>");
            reqXmlStr.append("<check_name>" + model.getCheck_name() + "</check_name>");
            reqXmlStr.append("<re_user_name>" + model.getRe_user_name() + "</re_user_name>");
            reqXmlStr.append("<openid>" + model.getOpenid() + "</openid>");
            reqXmlStr.append("<amount>" + model.getAmount() + "</amount>");
            reqXmlStr.append("<desc>" + model.getDesc() + "</desc>");
            reqXmlStr.append("<sign>" + sign + "</sign>");
            reqXmlStr.append("<partner_trade_no>" + model.getPartner_trade_no() + "</partner_trade_no>");
            reqXmlStr.append("<spbill_create_ip>" + model.getSpbill_create_ip() + "</spbill_create_ip>");
            reqXmlStr.append("</xml>");

            LOG.info("request xml = " + reqXmlStr);
            // 3.加载证书请求接口
            String result = HttpRequestHandler.httpsRequest(TRANS_URL, reqXmlStr.toString(),
                model, CERT_PATH);
            LOG.info(("response xml = " + result));
            if(result.contains("CDATA[FAIL]")){
                return new ResultEntity(false, "调用微信接口失败, 具体信息请查看访问日志");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResultEntity(false, e.getMessage());
        }
        return new ResultEntity(true);
    }
    
    private static String createLinkString(TransfersDto model)
    {
        // 微信签名规则 https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=4_3
        Map<String, Object> paramMap = new HashMap<String, Object>();
        
        // 订单号默认用商户号+时间戳+4位随机数+可以根据自己的规则进行调整
        /*model.setAppkey(APP_KEY);
        model.setNonce_str(WechatpayUtil.getNonce_str());
        model.setPartner_trade_no(model.getMchid()
                                  + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                                  + (int)((Math.random() * 9 + 1) * 1000));
        
        paramMap.put("mch_appid", model.getMch_appid());
        paramMap.put("mchid", model.getMchid());*/
        paramMap.put("openid", model.getOpenid());
        paramMap.put("amount", model.getAmount());
        paramMap.put("check_name", model.getCheck_name());
        paramMap.put("re_user_name", model.getRe_user_name());
        paramMap.put("desc", model.getDesc());
        paramMap.put("partner_trade_no", model.getPartner_trade_no());
        paramMap.put("nonce_str", model.getNonce_str());
        paramMap.put("spbill_create_ip", model.getSpbill_create_ip());
        
        List<String> keys = new ArrayList(paramMap.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++ )
        {
            String key = keys.get(i);
            Object value = (Object)paramMap.get(key);
            if (i == keys.size() - 1)
            {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            }
            else
            {
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

}
