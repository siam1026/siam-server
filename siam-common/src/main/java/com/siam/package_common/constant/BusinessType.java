package com.siam.package_common.constant;

/**
 * 业务常量类
 **/
public class BusinessType {

    //超级管理员的主键id
    public static final int ADMIN_ID = 1;

    //TODO-验证码需要对小程序端、商家端、调度后台进行区分
    //1、验证码发送记录表
    // 注册
    public static final String SMS_LOG_TYPE_REGISTER = "register";
    // 登录
    public static final String SMS_LOG_TYPE_LOGIN = "login";
    // 验证手机号
    public static final String SMS_LOG_TYPE_VERIFICATION = "verification";
    // 找回密码
    public static final String SMS_LOG_TYPE_FINDPWD = "findpwd";

    //2、登录方式标识
    // 用户登录方式
    public static final String MEMBER_TOKEN_TYPE_WAP = "wap";
    // 管理员登录方式
    public static final String ADMIN_TOKEN_TYPE_WAP = "wap";
    // 商家账号登录方式
    public static final String MERCHANT_TOKEN_TYPE_WAP = "wap";

    //3、系统默认属性
    //外送订单-默认接单门店
    public static final String DEFAULT_SHOP_NAME = "辰泰科技园区店";

    //4、百度地图属性
    //百度地图API出行方案-驾车
    public static final String BAIDU_TRAVELING_PLAN_DRIVING = "driving";
    //百度地图API出行方案-骑行
    public static final String BAIDU_TRAVELING_PLAN_RIDING = "riding";
    //百度地图API出行方案-步行
    public static final String BAIDU_TRAVELING_PLAN_WALKING = "walking";

    //5、系统默认优惠券标识
    //系统默认优惠券ID-新人3折卷
    public static final Integer NEW_PEOPLE_COUPONS_ID = Quantity.INT_1;
    //系统默认优惠券ID-推荐新人3折卷
    public static final Integer INVITE_NEW_PEOPLE_COUPONS_ID = Quantity.INT_2;

    //6、WebSocket传递给前端的标识
    //新订单来了 -- 未开启本地打印
    public static final String NEW_ORDER = "newOrder";
    //新订单来了 -- 开启了本地打印
    public static final String NEW_ORDER_WITH_PRINT = "newOrderWithPrint";
    //订单10分钟内未点击配送/制作完成，播放超时提醒
    public static final String ORDER_OVERTIME = "orderOvertime";
    //有订单申请退款
    public static final String ORDER_APPLY_REFUND = "orderApplyRefund";
    //自取订单改成配送 -- 未开启本地打印
    public static final String ORDER_CHANGE_TO_DELIVERY = "orderChangeToDelivery";
    //自取订单改成配送 -- 开启了本地打印
    public static final String ORDER_CHANGE_TO_DELIVERY_WITH_PRINT = "orderChangeToDeliveryWithPrint";
    //本地打印 -- 未开启语音提醒
    public static final String NEW_ORDER_PRINT = "newOrderPrint";

    //oss文件路径前缀
    public static final String OSS_PREFIX = "https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/";

    //系统/平台邮件地址
    public static final String SYSTEM_EMAIL_ADDRESS = "siam1026@163.com";
}