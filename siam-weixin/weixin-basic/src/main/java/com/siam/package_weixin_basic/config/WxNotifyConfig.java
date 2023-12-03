package com.siam.package_weixin_basic.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(value = "wxlogin")
public class WxNotifyConfig {

    private String appId;//AppID(小程序ID)
    private String secret;//AppSecret(小程序密钥)

}
