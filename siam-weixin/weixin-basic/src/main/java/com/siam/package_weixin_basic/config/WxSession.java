package com.siam.package_weixin_basic.config;

/**
 * @CalssName WeChatSession
 * @Description TODO
 * @Author WP
 * @Date 2019/11/18 9:57
 **/
public class WxSession {
    private String openid;

    private String session_key;

    private String unionid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}