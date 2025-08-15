<template>
    <view>
        <view class="refund-process-top">
            <view class="refund-process-title">退款流程</view>
            <view class="view-line"></view>
            <view class="refund-process-details">
                <view
                    :class="'refund-process-item ' + (orderRefundProcessList.length - 1 != index ? 'refund-process-item-margin' : '')"
                    v-for="(item, index) in orderRefundProcessList"
                    :key="index"
                >
                    <view :class="'seria-number ' + (index == 0 ? 'theme-bg' : 'detail-out out-status')">{{ orderRefundProcessList.length - index }}</view>

                    <view class="detail">
                        <view class="detail-top">
                            <text :class="'detail-status ' + (index == 0 ? 'theme-color' : 'detail-out')">{{ item.name }}</text>
                            <text class="detail-time">{{ item.createTime }}</text>
                        </view>
                        <view class="detail-tip" v-if="item.description">{{ item.description }}</view>
                    </view>
                </view>
            </view>
        </view>
        <view class="refund-process-top">
            <view class="refund-process-title">退款信息</view>
            <view class="view-line"></view>
            <view class="order-detail-lists">
                <view class="order-detail-list" v-for="(item, index) in orderRefundGoodsList" :key="index">
                    <image :src="item.mainImage" mode="scaleToFill" class="order-mainImage"></image>

                    <view class="order-detail">
                        <view class="order-goods-name">
                            <view class="out_of_range one_row">{{ item.goodsName }}</view>
                            <view class="order-price">￥{{ item.price }}</view>
                        </view>
                        <view class="order-specListAnalysis">
                            <view class="out_of_range one_row">{{ item.specListAnalysis }}</view>
                            <view>x{{ item.number }}</view>
                        </view>
                    </view>
                </view>
            </view>
            <view class="refund-process-column">
                <text class="title">配送费</text>
                <text class="pirce">￥{{ orderRefund.deliveryFee }}</text>
            </view>
            <view class="view-line"></view>
            <view class="refund-process-column">
                <text class="title">包装费</text>
                <text class="pirce">￥{{ orderRefund.packingCharges }}</text>
            </view>
            <view class="view-line"></view>
            <view class="refund-process-column">
                <text class="title">退款金额</text>
                <view class="refund-process-column-right">
                    <text class="refund-process-column-tip" :decode="true">实退&nbsp;</text>
                    <text class="pirce">￥{{ orderRefund.refundAmount }}</text>
                </view>
            </view>
            <view class="view-line"></view>
            <view class="refund-process-column">
                <text class="title">退单原因</text>
                <text class="refund-process-column-tip">
                    {{ orderRefund.refundReasonText }}{{ orderRefund.refundReasonDescription ? '-' + orderRefund.refundReasonDescription : '' }}
                </text>
            </view>
            <view class="view-line"></view>
            <view class="refund-process-column">
                <text class="title">退款账户</text>
                <text class="refund-process-column-tip">{{ orderRefund.refundAccountText }}</text>
            </view>
        </view>
    </view>
</template>

<script>
import https from '../../../../utils/http';
import authService from '../../../../utils/auth';
import GlabalConfig from '../../../../utils/global-config';
import toastService from '../../../../utils/toast.service';
import systemStatus from '../../../../utils/system-status';
import dateHelper from '../../../../utils/date-helper';
import utilHelper from '../../../../utils/util';
export default {
    data() {
        return {
            refundReasonApply: [
                {
                    value: 1,
                    name: '信息填写错误'
                },
                {
                    value: 2,
                    name: '送达时间选错了'
                },
                {
                    value: 3,
                    name: '买错了/买少了'
                },
                {
                    value: 4,
                    name: '商家缺货'
                },
                {
                    value: 5,
                    name: '商家联系我取消'
                },
                {
                    value: 6,
                    name: '配送太慢'
                },
                {
                    value: 7,
                    name: '骑手联系我取消'
                },
                {
                    value: 8,
                    name: '我不想要了'
                },
                {
                    value: 9,
                    name: '商家通知我卖完了',
                    checked: false
                },
                {
                    value: 10,
                    name: '商家沟通态度差',
                    checked: false
                },
                {
                    value: 11,
                    name: '骑手沟通态度差',
                    checked: false
                },
                {
                    value: 12,
                    name: '送太慢了，等太久了',
                    checked: false
                },
                {
                    value: 13,
                    name: '商品撒漏/包装破损',
                    checked: false
                },
                {
                    value: 14,
                    name: '商家少送商品',
                    checked: false
                },
                {
                    value: 15,
                    name: '商家送错商品',
                    checked: false
                },
                {
                    value: 16,
                    name: '口味不佳/个人感受不好',
                    checked: false
                },
                {
                    value: 17,
                    name: '餐品内有异物',
                    checked: false
                },
                {
                    value: 18,
                    name: '食用后引起身体不适',
                    checked: false
                },
                {
                    value: 19,
                    name: '商品变质/有异味',
                    checked: false
                },
                {
                    value: 20,
                    name: '其他',
                    checked: false
                }
            ],

            orderRefund: {
                deliveryFee: '',
                packingCharges: '',
                refundAmount: '',
                refundReasonText: '',
                refundReasonDescription: false,
                refundAccountText: ''
            },

            orderRefundGoodsList: '',
            orderRefundProcessList: ''
        };
    }
    /**
     * 生命周期函数--监听页面加载
     */,
    onLoad: function (options) {
        this.getSelectRefundProcess(options.orderId);
    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {},
    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {},
    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {},
    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {},
    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {},
    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {},
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {},
    methods: {
        getSelectRefundProcess(orderId) {
            toastService.showLoading();
            https.request('/rest/member/order/selectRefundProcess', {
                    id: orderId
                }).then((result) => {
                    toastService.hideLoading();
                    if (result.success) {
                        result.data.orderRefundGoodsList.forEach((item, index) => {
                            let specListAnalysis = '';
                            for (var key in JSON.parse(item.specList)) {
                                specListAnalysis = (specListAnalysis ? specListAnalysis + '/' : specListAnalysis) + JSON.parse(item.specList)[key];
                            }
                            item.specListAnalysis = specListAnalysis;
                            item.mainImage = GlabalConfig.ossUrl + item.mainImage;
                        });
                        result.data.orderRefundProcessList.forEach((item, index) => {
                            item.createTime = dateHelper.fmtDate(item.createTime);
                        });
                        this.refundReasonApply.forEach((item, index) => {
                            if (result.data.orderRefund.refundReason == item.value) {
                                result.data.orderRefund.refundReasonText = item.name;
                            }
                        });
                        result.data.orderRefund.refundAccountText = systemStatus.refundAccountText(result.data.orderRefund.refundAccount);
                        this.setData({
                            orderRefund: result.data.orderRefund,
                            orderRefundGoodsList: result.data.orderRefundGoodsList,
                            orderRefundProcessList: result.data.orderRefundProcessList
                        });
                    }
                });
        }
    }
};
</script>
<style>
page {
    background: #f5f5f5;
    padding-bottom: 20rpx;
}

.refund-process-top {
    background-color: white;
    margin: 20rpx;
    border-radius: 15rpx;
}

.refund-process-title {
    padding: 20rpx;
    font-size: 30rpx;
}

.refund-process-details {
    padding: 20rpx;
}

.refund-process-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.refund-process-item-margin {
    margin-bottom: 20rpx;
}

.refund-process-item .seria-number {
    border-radius: 50%;
    width: 40rpx;
    height: 40rpx;
    font-size: 30rpx;
    display: flex;
    align-items: center;
    justify-content: center;
}

.out-status {
    background: #f5f5f5;
}

.refund-process-item .detail {
    width: 90%;
}

.detail-top {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.detail-status {
    font-size: 30rpx;
}

.detail-out {
    color: #888888;
}

.detail-time {
    color: #888888;
    font-size: 26rpx;
}

.detail-tip {
    font-size: 28rpx;
    color: #a4a4a4;
}

.order-detail-lists {
    padding: 0 20rpx;
}

.order-detail-list {
    border-bottom: 1rpx solid #f5f5f5;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 10px 0;
}

.order-mainImage {
    width: 20%;
    height: 140rpx;
    border-radius: 10rpx;
}

.order-detail {
    width: 78%;
}

.order-goods-name {
    font-size: 30rpx;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.order-specListAnalysis {
    margin-top: 30rpx;
    font-size: 28rpx;
    color: #767676;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.order-price {
    font-size: 30rpx;
    font-weight: bold;
}

.refund-process-column {
    padding: 20rpx;
    font-size: 30rpx;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.column-one {
    padding-top: 0;
}

.refund-process-column .title {
    color: black;
    font-size: 30rpx;
}

.refund-process-column .price {
    font-weight: bold;
}

.refund-process-column-right {
    display: flex;
    align-items: center;
}

.refund-process-column-tip {
    color: #767676;
    font-size: 30rpx;
}
</style>
