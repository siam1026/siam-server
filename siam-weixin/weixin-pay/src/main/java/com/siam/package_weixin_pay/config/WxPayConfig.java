package com.siam.package_weixin_pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(value = "wxpay")
public class WxPayConfig {

    private String appId;

    private String mchId;

    private String mchKey;

    private String notifyUrl;

    private String signType;

    private String tradeType;

    private String payUrl;

    private String refundUrl;

    private String refundSuccessNotifyUrl;

    private String payToBalanceUrl;
}