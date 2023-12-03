package com.siam.system.modular.package_goods.service_impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.siam.package_weixin_basic.config.WxNotifyConfig;
import com.siam.package_weixin_basic.config.WxNotifyMessage;
import com.siam.package_weixin_basic.config.WxNotifyTemplate;
import com.siam.package_common.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class PreviousWxNotifyServiceImpl {

    //取餐模板id
    private static final String TAKE_TEMPLATE_ID="B3TjXWXB9iMCVB0-vUhAMognjYw4qWHqdTUYcsvb18c";

    @Autowired
    private WxNotifyConfig wxNotifyConfig;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;



    /**
     *
     * @param openid 用户openid
     * @return
     */
    public String pushOneUser(String openid) {
        //订阅消息内容组装
        WxNotifyMessage wxNotifyMessage = new WxNotifyMessage();
        wxNotifyMessage.setThing2(new WxNotifyMessage.Thing2("1号点"));
        wxNotifyMessage.setThing5(new WxNotifyMessage.Thing5("奶茶"));
        wxNotifyMessage.setThing11(new WxNotifyMessage.Thing11("提示"));
        wxNotifyMessage.setCharacter_string4(new WxNotifyMessage.CharacterString4("1"));

        //组装接口所需对象
        WxNotifyTemplate wxNotifyTemplate = new WxNotifyTemplate();
        wxNotifyTemplate.setData(wxNotifyMessage);//这里的订阅消息对象 不需要额外转json
        wxNotifyTemplate.setTouser(openid);
        wxNotifyTemplate.setTemplate_id(TAKE_TEMPLATE_ID);

        //对象转map
        String jsonStr = JSON.toJSONString(wxNotifyTemplate);

        //获取accessToken
        String access_token = getAccess_token();

        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="+access_token;

        String result = HttpUtil.sendPost(url, jsonStr);
        System.out.println("发送返回的内容：" + result);
        return result;
    }

    /*
     * 获取access_token
     * appid和appsecret到小程序后台获取，当然也可以让小程序开发人员给你传过来
     * */
    public String getAccess_token() {
        //获取access_token
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                "&appid=" + wxNotifyConfig.getAppId() + "&secret=" + wxNotifyConfig.getSecret();
        if(restTemplate==null){
            restTemplate = new RestTemplate();
        }
        String json = restTemplate.getForObject(url, String.class);
        JSONObject myJson = JSONObject.parseObject(json);
        return myJson.get("access_token").toString();
    }

    public static void main(String[] args) {

    }
}

