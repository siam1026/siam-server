<template>
    <view>
        <view class="page-content">
            <view class="content-view">
                <text class="content-title">订单号</text>
                <text class="content-value">{{ info.orderNo }}</text>
            </view>
            <view class="view-line"></view>
            <view class="content-view">
                <text class="content-title">商品说明</text>
                <text class="content-value">{{ info.denominationName }}</text>
            </view>
            <view class="view-line"></view>
            <view class="content-view">
                <text class="content-title">充值渠道</text>
                <text class="content-value">{{ info.channelText }}</text>
            </view>
            <view class="view-line"></view>
            <view class="content-view">
                <text class="content-title">支付状态</text>
                <text class="content-value">{{ info.statusText }}</text>
            </view>
            <view class="view-line"></view>
            <view class="content-view">
                <text class="content-title">支付金额</text>
                <view>
                    <text class="strike_through" v-if="info.denominationIsSale">￥{{ info.denominationPrice }}</text>
                    <text :decode="true" class="content-value">&nbsp;￥{{ info.denominationIsSale ? info.denominationSalePrice : info.denominationPrice }}</text>
                </view>
            </view>
            <view class="view-line"></view>
            <view class="content-view">
                <text class="content-title">支付时间</text>
                <text class="content-value">{{ info.createTime }}</text>
            </view>
        </view>

        <view class="page-content" v-if="info.denominationIsGiveBalance || info.denominationIsGiveCoupons">
            <view class="content-view" v-if="info.denominationIsGiveBalance">
                <text class="content-title">赠送余额</text>
                <text class="content-value">{{ info.denominationGiveBalance > 0 ? info.denominationGiveBalance + '元' : '无' }}</text>
            </view>
            <view class="view-line" v-if="info.denominationIsGiveCoupons"></view>
            <view class="content-view" v-if="info.denominationIsGiveCoupons">
                <text class="content-title">赠送优惠券</text>
                <text class="content-value">{{ info.denominationGiveCouponsDescription ? info.denominationGiveCouponsDescription : '无' }}</text>
            </view>
        </view>
    </view>
</template>

<script>
import https from '../../../../utils/http';
import authService from '../../../../utils/auth';
import toastService from '../../../../utils/toast.service';
import utilHelper from '../../../../utils/util';
import dateHelper from '../../../../utils/date-helper';
import systemStatus from '../../../../utils/system-status';
export default {
    data() {
        return {
            info: {
                orderNo: '',
                denominationName: '',
                channelText: '',
                statusText: '',
                denominationIsSale: '',
                denominationPrice: '',
                denominationSalePrice: '',
                createTime: '',
                denominationIsGiveBalance: '',
                denominationIsGiveCoupons: '',
                denominationGiveBalance: 0,
                denominationGiveCouponsDescription: false
            }
        };
    }
    /**
     * 生命周期函数--监听页面加载
     */,
    onLoad: function (options) {
        console.log(options);
        this.getvipRechargeRecordDetailInfo(options.id);
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
        getvipRechargeRecordDetailInfo(id) {
            toastService.showLoading('正在加载...');
            https.request('/rest/member/vipRechargeRecord/detail', {
                    id: id
                }).then((result) => {
                    toastService.hideLoading();
                    if (result.success) {
                        result.data.createTime = dateHelper.fmtDate(result.data.createTime);
                        result.data.statusText = systemStatus.payStatusText(result.data.status);
                        result.data.channelText = systemStatus.rchargeChannelText(result.data.channel);
                        this.setData({
                            info: result.data
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
    width: 100%;
    height: 100%;
    margin: 0;
}

.page-content {
    padding: 20rpx;
    margin: 20rpx;
    background-color: white;
    border-radius: 15rpx;
}

.content-view {
    margin-top: 20rpx;
    margin-bottom: 20rpx;
    background-color: white;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.content-title {
    font-size: 28rpx;
    color: #959595;
}

.content-value {
    font-size: 30rpx;
}
</style>
