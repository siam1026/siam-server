package com.siam.system.modular.package_user.controller.member;

import com.google.gson.Gson;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_weixin_basic.config.WxCode;
import com.siam.package_weixin_basic.config.WxEncrypted;
import com.siam.package_weixin_basic.config.WxSession;
import com.siam.package_weixin_pay.util.WxdecodeUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @CalssName WxLoginController
 * @Description TODO
 * @Author WP
 * @Date 2019/11/18 10:44
 **/
@Slf4j
@RestController
@RequestMapping(value = "/rest/wxLogin")
@Transactional(rollbackFor = Exception.class)
@Component
@ConfigurationProperties(value = "wxlogin")
public class WxLoginController {
    private static String appId;//AppID(小程序ID)
    private static String secret;//AppSecret(小程序密钥)

    /**
     * @Description 通过appId、secret和code获取微信提供的openid、session_key和unionid
     * @Author WP
     * @Date 2019/11/19 13:49
     * @Param [appId, secret, code]
     * @Return com.siam.package_common.config.WxSession
     **/
    public static WxSession getSessionKeyFromWxByCode(String code) {
        //appid:小程序 appId;secret:小程序 appSecret;js_code:登录时获取的 code;grant_type:授权类型，此处只需填写 authorization_code
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + secret
                + "&js_code=" + code + "&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        // 进行网络请求,访问url接口
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if (responseEntity != null) {
            //获取到openid:用户唯一标识,session_key:会话密钥,unionid:用户在开放平台的唯一标识符,在满足 UnionID 下发条件的情况下会返回
            String sessionData = responseEntity.getBody();
            log.debug("-------------------------" + sessionData);
            Gson gson = new Gson();
            // 解析从微信服务器获得的openid和session_key;
            WxSession wsSession = gson.fromJson(sessionData, WxSession.class);
            log.debug(wsSession.getSession_key());
            return wsSession;
        }
        return null;
    }

    /**
     * @Description code：小程序的wx.login返回的code,用户点击一键登录按钮时返回的iv和encryptedData
     * 把小程序传过来的用户加密信息转成用户对象
     * @Author WP
     * @Date 2019/11/19 13:51
     * @Param [code, iv, encryptedData]
     * @Return com.siam.package_common.config.WxEncrypted
     **/
    public WxEncrypted getWxUserInfo(String code, String iv, String encryptedData) throws Exception {
        WxSession wsSession = getSessionKeyFromWxByCode(code);
        log.debug(encryptedData);
        //通过session_key和encryptedData解密微信用户信息
        String str = WxdecodeUtils.decryptData(encryptedData, wsSession.getSession_key(), iv);
        log.debug(str + "------");
        if (str != null) {
            Gson gson = new Gson();
            //解析从微信服务器获得的手机号码及其他;
            WxEncrypted wxEncrypted = gson.fromJson(str, WxEncrypted.class);
            wxEncrypted.setOpenid(wsSession.getOpenid());
            log.debug(wxEncrypted.getPurePhoneNumber());
            return wxEncrypted;
        }
        return null;
    }

    @ApiOperation(value = "获取微信手机号")
    @PostMapping(value = "/getWxPhone")
    public BasicResult getWxPhone(WxCode wxCode) throws Exception {
        BasicData basicResult = new BasicData();
        /*
         1. 得用wxCode从wx服务器拿到session_key
         2. 利用session_key解密wxEncryptedData,得到手机号码
        */
        WxEncrypted wxEncrypted = this.getWxUserInfo(wxCode.getWxCode(), wxCode.getWxIV(), wxCode.getEncryptedData());

        if (wxEncrypted == null) {
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("操作失败");
            return basicResult;
        }
        basicResult.setData(wxEncrypted);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("操作成功");
        return basicResult;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
