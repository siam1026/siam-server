package com.siam.package_weixin_pay.entity;

import java.beans.Transient;
import java.util.HashMap;
import java.util.Map;


public class TransfersDto
{
    // 与商户号关联应用(如微信公众号/小程序)的APPID
    /*private String mch_appid;*/

    // 微信支付分配的商户号
    /*private String mchid;*/
    
    // 商户名称, 如'XXX服务号'
    /*private String mch_name;*/

    // 商户订单号，需保持唯一性(只能是字母或者数字，不能包含有其他字符)
    private String partner_trade_no;

    // 商户appid下，某用户的openid
    private String openid;

    // NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名
    /*private String check_name = "NO_CHECK";*/
    private String check_name = "FORCE_CHECK";

    private String re_user_name;

    // 企业付款金额，单位为元
    private int amount;
    
    // 随机字符串，不长于32位
    private String nonce_str;

    // 企业付款备注
    private String desc;
    
    /*private String appkey;*/

    // 发起者IP地址+该IP可传用户端或者服务端的IP。
    private String spbill_create_ip = "127.0.0.1";

    /*public String getMch_appid()
    {
        return mch_appid;
    }

    public void setMch_appid(String mch_appid)
    {
        this.mch_appid = mch_appid;
    }

    public String getMchid()
    {
        return mchid;
    }*/

    /**
     * 与商户号关联应用(如微信公众号/小程序)的APPID
     */
    /*public void setMchid(String mchid)
    {
        this.mchid = mchid;
    }*/
    
    public String getPartner_trade_no()
    {
        return partner_trade_no;
    }

    public void setPartner_trade_no(String partner_trade_no)
    {
        this.partner_trade_no = partner_trade_no;
    }

    public String getOpenid()
    {
        return openid;
    }

    public void setOpenid(String openid)
    {
        this.openid = openid;
    }
    
    public String getCheck_name()
    {
        return check_name;
    }

    public int getAmount()
    {
        return amount;
    }

    /**
     * 为了方便理解+这里接收的单位是元+会自动转换为分传给微信支付后台
     */
    public void setAmount(double amount)
    {
        this.amount = (int)(amount * 100);
    }
    
    public String getNonce_str()
    {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str)
    {
        this.nonce_str = nonce_str;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    /*public String getAppkey()
    {
        return appkey;
    }

    public void setAppkey(String appkey)
    {
        this.appkey = appkey;
    }*/

    public String getSpbill_create_ip()
    {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip)
    {
        this.spbill_create_ip = spbill_create_ip;
    }
    
    /*public String getMch_name()
    {
        return mch_name;
    }

    public void setMch_name(String mch_name)
    {
        this.mch_name = mch_name;
    }*/

    public String getRe_user_name() {
        return re_user_name;
    }

    public void setRe_user_name(String re_user_name) {
        this.re_user_name = re_user_name;
    }

    @Transient
    public Map<String, String> map(){
        Map<String, String> map = new HashMap<String, String>();
        /*map.put("mch_appid", this.mch_appid);
        map.put("mchid", this.mchid);
        map.put("mch_name", this.mch_name);*/
        map.put("openid", this.openid);
        map.put("amount", String.valueOf(this.amount));
        map.put("desc", this.desc);
        /*map.put("appkey", this.appkey);*/
        map.put("nonce_str", this.nonce_str);
        map.put("partner_trade_no", this.partner_trade_no);
        map.put("spbill_create_ip", this.spbill_create_ip);
        return map;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        /*sb.append("[mch_appid]" + this.mch_appid);
        sb.append(",[mchid]" + this.mchid);*/
        sb.append(",[openid]" + this.openid);
        sb.append(",[amount]" + this.amount);
        sb.append(",[desc]" + this.desc);
        sb.append(",[partner_trade_no]" + this.partner_trade_no);
        sb.append(",[nonce_str]" + this.nonce_str);
        sb.append(",[spbill_create_ip]" + this.spbill_create_ip);
        sb.append(",[check_name]" + this.check_name);
        return sb.toString();
    }
}
