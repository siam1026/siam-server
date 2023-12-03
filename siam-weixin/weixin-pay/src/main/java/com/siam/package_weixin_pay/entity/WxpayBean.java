package com.siam.package_weixin_pay.entity;

/**
 * 微信支付实体对象
 * 根据支付宝接口协议，其中的属性名，必须使用下划线，不能修改
 * @author Louis
 * @date Dec 12, 2018
 */
public class WxpayBean {

    /**
     * 商户订单号，必填
     *
     */
    private String out_trade_no;

    /**
     * 订单名称，必填
     */
    private String subject;

    /**
     * 付款金额，必填
     * 根据支付宝接口协议，必须使用下划线
     */
    private String total_amount;

    /**
     * 商品描述，可空
     */
    private String body;

    /**
     * 超时时间参数
     */
    private String timeout_express= "10m";

    /**
     * 产品编号
     */
    private String product_code= "FAST_INSTANT_TRADE_PAY";

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimeout_express() {
        return timeout_express;
    }

    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }
}
