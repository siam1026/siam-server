//订单模块-订单状态
const statusText = (status) => {
    switch (status) {
        case 1:
            return '未付款';
        case 2:
            return '待处理';
        case 3:
            return '待自取';
        case 4:
            return '待配送';
        case 5:
            return '已配送';
        case 6:
            return '已完成';
        case 7:
            return '售后处理中';
        case 8:
            return '已退款';
        case 9:
            return '售后处理完成';
        case 10:
            return '已取消';
        case 11:
            return '已取消';
        default:
            return '-';
    }
};

//积分商城订单模块-订单状态
const statusMallText = (status) => {
    switch (status) {
        case 1:
            return '未付款';
        case 2:
            return '待处理';
        case 3:
            return '待自取';
        case 4:
            return '待发货';
        case 5:
            return '已发货';
        case 6:
            return '已完成';
        case 7:
            return '售后处理中';
        case 8:
            return '已退款';
        case 9:
            return '售后处理完成';
        case 10:
            return '已取消';
        case 11:
            return '已取消';
        default:
            return '-';
    }
};

//快递状态 0=快递收件(揽件) 1=在途中 2=正在派件 3=已签收 4=派送失败 5=疑难件 6=退件签收
const logisticsStatusText = (status) => {
    switch (status) {
        case 0:
            return '快递收件';
        case 1:
            return '在途中';
        case 2:
            return '正在派件';
        case 3:
            return '已签收';
        case 4:
            return '派送失败';
        case 5:
            return '疑难件';
        case 6:
            return '退件签收';
        default:
            return '-';
    }
};

//会员模块-用户会员状态信息
const typeVipText = (type) => {
    switch (type) {
        case 1:
            return '普通用户';
        case 2:
            return 'VIP会员';
        default:
            return '-';
    }
};

//会员类型信息
const statusVipText = (status) => {
    switch (status) {
        case 0:
            return '无/非会员';
        case 1:
            return '正常';
        case 2:
            return '禁用';
        case 3:
            return '已过期';
        default:
            return '-';
    }
};

// 优惠券类型
const preferentialTypeText = (status) => {
    switch (status) {
        case 1:
            return '折扣';
        case 2:
            return '满减';
        default:
            return '-';
    }
};

//积分模块-会员账单记录-积分获取和减去类型
const memberIntegralTypeText = (type) => {
    switch (type) {
        case 1:
            return '积分兑换商品';
        case 2:
            return '下单奖励积分';
        default:
            return '-';
    }
};

//打款方式/到账方式 1=微信 2=支付宝 3=银行
const withdrawalModeText = (type) => {
    switch (type) {
        case 1:
            return '微信';
        case 2:
            return '支付宝';
        case 3:
            return '银行';
        default:
            return '-';
    }
};

// 支付方式
const paymentModeText = (type) => {
    switch (type) {
        case 1:
            return '微信支付';
        case 2:
            return '余额支付';
        case 3:
            return '积分支付';
        default:
            return '-';
    }
};

// 退款账户 1=微信 2=支付宝 3=平台余额
const refundAccountText = (type) => {
    switch (type) {
        case 1:
            return '微信';
        case 2:
            return '支付宝';
        case 3:
            return '平台余额';
        case 4:
            return '平台积分';
        default:
            return '-';
    }
};

//操作类型
const operateTypeText = (type) => {
    switch (type) {
        case 1:
            return '加';
        case 2:
            return '减';
        default:
            return '-';
    }
};
//货币类型
const coinTypeText = (type) => {
    switch (type) {
        case 1:
            return '积分';
        case 2:
            return '余额';
        default:
            return '-';
    }
};

//支付状态1=待支付 2=支付成功 3=支付失败 4=交易超时自动关闭
const payStatusText = (type) => {
    switch (type) {
        case 1:
            return '待支付';
        case 2:
            return '支付成功';
        case 3:
            return '支付失败';
        case 4:
            return '交易超时自动关闭';
        default:
            return '-';
    }
};

//充值渠道 1=小程序 2=商家端 3=调度后台
const rchargeChannelText = (type) => {
    switch (type) {
        case 1:
            return '小程序';
        case 2:
            return '商家端';
        case 3:
            return '调度后台';
        default:
            return '-';
    }
};

//提现审核状态 1=平台处理中 2=到账成功 3=审核不通过
const auditStatusText = (type) => {
    switch (type) {
        case 1:
            return '平台处理中';
        case 2:
            return '到账成功';
        case 3:
            return '审核不通过';
        default:
            return '-';
    }
};

//提现货币类型 1=邀请新用户注册奖励金额
const withDrawalCoinTypeText = (type) => {
    switch (type) {
        case 1:
            return '邀请新用户注册奖励金额';
        default:
            return '-';
    }
};

//订单退款表 退款状态核对：退款状态 1=退款申请已提交 4=等待平台处理 5=平台拒绝退款，退款已关闭 7=退款成功
const orderRefundText = (type) => {
    switch (type) {
        case 1:
            return '退款申请已提交';
        case 4:
            return '等待平台处理';
        case 5:
            return '平台拒绝退款，退款已关闭';
        case 7:
            return '退款成功';
        default:
            return '-';
    }
};
export default {
    statusText: statusText,
    statusMallText: statusMallText,
    statusVipText: statusVipText,
    typeVipText: typeVipText,
    preferentialTypeText: preferentialTypeText,
    memberIntegralTypeText: memberIntegralTypeText,
    operateTypeText: operateTypeText,
    coinTypeText: coinTypeText,
    payStatusText: payStatusText,
    rchargeChannelText: rchargeChannelText,
    withdrawalModeText: withdrawalModeText,
    paymentModeText: paymentModeText,
    refundAccountText: refundAccountText,
    auditStatusText: auditStatusText,
    withDrawalCoinTypeText: withDrawalCoinTypeText,
    orderRefundText: orderRefundText,
    logisticsStatusText: logisticsStatusText
};
