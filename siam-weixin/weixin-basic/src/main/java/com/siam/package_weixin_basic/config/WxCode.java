package com.siam.package_weixin_basic.config;

/**
 * @CalssName WeChatCode
 * @Description TODO
 * @Author WP
 * @Date 2019/11/18 10:11
 **/
public class WxCode {
    private String wxCode;

    private String wxIV;

    private String encryptedData;

    public String getWxCode() {
        return wxCode;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode;
    }

    public String getWxIV() {
        return wxIV;
    }

    public void setWxIV(String wxIV) {
        this.wxIV = wxIV;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }
}
