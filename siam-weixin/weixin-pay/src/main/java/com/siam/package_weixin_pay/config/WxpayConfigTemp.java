package com.siam.package_weixin_pay.config;

import com.github.binarywang.wxpay.exception.WxPayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.io.*;

@Component
@ConfigurationProperties(value = "wxpay")
public class WxpayConfigTemp {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 公众号appid
    private String appId;

    // 商户号
    private String mchId;

    // 商户密钥
    private String mchKey;

    // 服务商模式下的子商户公众账号ID
    private String subAppId;

    // 服务商模式下的子商户号
    private String subMchId;

    private String keyPath;

    private String notifyUrl;

    private String tradeType;

    private String signType;

    private SSLContext sslContext;

    private boolean useSandBoxEnv = false;

    private String httpProxyHost;

    private Integer httpProxyPort;

    private String httpProxyUsername;

    private String httpProxyPassword;

    // http请求连接超时时间
    private Integer httpConnectionTimeout = 5000;

    // http请求数据读取等待时间
    private Integer httpTimeout = 10000;


    @PostConstruct
    public void init() throws WxPayException, FileNotFoundException {
        /*if (StringUtils.isBlank(this.getMchId())) {
            throw new WxPayException("请确保商户号mchId已设置");
        }

        if (StringUtils.isBlank(this.getKeyPath())) {
            throw new WxPayException("请确保证书文件地址keyPath已配置");
        }

        InputStream inputStream;
        final String prefix = "classpath:";
        String fileHasProblemMsg = "证书文件【" + keyPath + "】有问题，请核实";
        String fileNotFoundMsg = "证书文件【" + keyPath + "】不存在，请核实";
        if (this.getKeyPath().startsWith(prefix)) {
            String path = StringUtils.removeFirst(this.getKeyPath(), prefix);
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            inputStream = WxpayConfig.class.getResourceAsStream(path);
            if (inputStream == null) {
                throw new WxPayException(fileNotFoundMsg);
            }
        } else {
            try {
                File file = new File(this.getKeyPath());
                if (!file.exists()) {
                    throw new WxPayException(fileNotFoundMsg);
                }
                inputStream = new FileInputStream(file);
            } catch (IOException e) {
                throw new WxPayException(fileHasProblemMsg, e);
            }
        }

        try {
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            char[] partnerId2charArray = this.getMchId().toCharArray();
            keystore.load(inputStream, partnerId2charArray);
            sslContext = SSLContexts.custom().loadKeyMaterial(keystore, partnerId2charArray).build();
        } catch (Exception e) {
            throw new WxPayException(fileHasProblemMsg, e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }*/
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getKeyPath() {
        return keyPath;
    }

    /**
     * 设置证书
     *
     * @param keyPath apiclient_cert.p12的文件的绝对路径
     */
    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    /**
     * 交易类型
     * JSAPI--公众号支付
     * NATIVE--原生扫码支付
     * APP--app支付
     *
     * @return
     */
    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public boolean isUseSandBoxEnv() {
        return useSandBoxEnv;
    }

    public void setUseSandBoxEnv(boolean useSandBoxEnv) {
        this.useSandBoxEnv = useSandBoxEnv;
    }

    public String getHttpProxyHost() {
        return httpProxyHost;
    }

    public void setHttpProxyHost(String httpProxyHost) {
        this.httpProxyHost = httpProxyHost;
    }

    public Integer getHttpProxyPort() {
        return httpProxyPort;
    }

    public void setHttpProxyPort(Integer httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
    }

    public String getHttpProxyUsername() {
        return httpProxyUsername;
    }

    public void setHttpProxyUsername(String httpProxyUsername) {
        this.httpProxyUsername = httpProxyUsername;
    }

    public String getHttpProxyPassword() {
        return httpProxyPassword;
    }

    public void setHttpProxyPassword(String httpProxyPassword) {
        this.httpProxyPassword = httpProxyPassword;
    }

    public Integer getHttpConnectionTimeout() {
        return httpConnectionTimeout;
    }

    public void setHttpConnectionTimeout(Integer httpConnectionTimeout) {
        this.httpConnectionTimeout = httpConnectionTimeout;
    }

    public Integer getHttpTimeout() {
        return httpTimeout;
    }

    public void setHttpTimeout(Integer httpTimeout) {
        this.httpTimeout = httpTimeout;
    }
}