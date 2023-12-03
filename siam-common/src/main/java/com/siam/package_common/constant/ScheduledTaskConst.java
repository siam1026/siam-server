package com.siam.package_common.constant;

/**
 * 结果码常量类
 **/
public class ScheduledTaskConst {

    //检测并关闭超时未支付的订单
    public static final String CLOSE_OVERDUE_ORDER = "closeOverdueOrder";

    //检测逾期优惠卷
    public static final String CHECK_OVERDUE_COUPONS = "checkOverdueCoupons";

    //检测是否清空所有商品月销量
    public static final String MONTHLY_SALES_RESET = "MonthlySalesReset";

    //检测并完成超时未确认的订单
    public static final String FINISH_OVERDUE_ORDER = "finishOverdueOrder";

    //检测并将新用户的"是否需要弹出新人引导提示"字段值改成是
    public static final String UPDATE_ISREMINDNEWPEOPLE_OF_MEMBER="updateIsRemindNewPeopleOfMember";

    //修改用户的是否需要请求授权服务通知状态
    public static final String UPDATE_ISREQUESTWXNOTIFY_OF_MEMBER="updateIsRequestWxNotifyOfMember";

    //检测给商家发放用户下单资金
    public static final String UPDATE_PAYORDERFROZENBALANCE_OF_MERCHANT="updatePayOrderFrozenBalanceOfMerchant";

    //同步积分商城-订单物流信息
    public static final String SYNC_POINTS_MALL_ORDER_LOGISTICS_INFO="syncPointsMallOrderLogisticsInfo";

    //优惠卷即将过期提醒
    public static final String COUPONS_OVERDUE_SMS_REMINDER="couponsOverdueSMSReminder";

    //处理综合事件
    public static final String HANDLE_GENERAL_EVENT="handleGeneralEvent";
}