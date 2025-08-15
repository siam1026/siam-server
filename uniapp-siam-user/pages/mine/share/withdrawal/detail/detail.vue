<template>
    <view class="swiper-items">
        <view class="integral-items" v-for="(item, index) in list" :key="index">
            <view class="integral-item">
                <view class="integral-item-name">
                    <text>{{ item.coinTypeText }}</text>
                </view>
                <view class="integral-item-time">
                    <text>{{ item.createTime }}</text>
                </view>
            </view>

            <view :class="'integral-item-number ' + (item.operateType == 1 ? 'theme-color' : 'color-integral')">
                {{ item.operateType == 1 ? '+' : '-' }}{{ item.withdrawAmount }}
            </view>
        </view>
		<van-empty description="暂无提现记录" v-if="list.length <= 0"/>
    </view>
</template>

<script>
import https from '../../../../../utils/http';
import authService from '../../../../../utils/auth';
import toastService from '../../../../../utils/toast.service';
import utilHelper from '../../../../../utils/util';
import dateHelper from '../../../../../utils/date-helper';
import systemStatus from '../../../../../utils/system-status';
var pageNo = 1;
var pageSize = 10;
export default {
    data() {
        return {
            list: [],
            isLastPage: '',
            userInfo: '',
            button: ''
        };
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.getUserInfo();
        pageNo = 1;
        this.setData({
            list: []
        });
        this.getMemberWithdrawRecordList();
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
    onReachBottom: function () {
        if (this.isLastPage) {
            toastService.showToast('没有更多啦~');
            return;
        }
        pageNo = pageNo + 1;
        this.getVipRechargeRecordList();
    },
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {},
    methods: {
        getMemberWithdrawRecordList() {
            toastService.showLoading('正在加载...');
            https
                .request('/rest/member/memberWithdrawRecord/list', {
                    pageNo: pageNo,
                    pageSize: pageSize
                })
                .then((result) => {
                    toastService.hideLoading();
                    if (result.success) {
                        result.data.records.forEach((item, index) => {
                            item.createTime = dateHelper.fmtDate(item.createTime);
                            item.coinTypeText = systemStatus.withDrawalCoinTypeText(item.coinType);
                            this.list.push(item);
                        });
                        this.setData({
                            isLastPage: result.data.isLastPage,
                            list: this.list
                        });
                    }
                });
        },

        getUserInfo: function (e) {
            https.request('/rest/member/getLoginMemberInfo', {}).then((result) => {
                if (result.success) {
                    this.setData({
                        userInfo: result.data
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

.integral-items {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20rpx 26rpx;
    margin: 20rpx;
    border-radius: 15rpx;
    background-color: white;
}

.integral-item-name {
    font-size: 28rpx;
    color: rgb(0, 0, 0);
    margin-bottom: 10rpx;
}

.integral-item-time {
    font-size: 26rpx;
    color: rgb(141, 141, 141);
}

.integral-item-number {
    font-size: 34rpx;
}

.color-integral {
    color: red;
}

.scroll-views {
    background: #f5f5f5;
    height: 100%;
}

.withdrawal-button {
    margin-top: 60rpx;
    font-size: 28rpx;
}
</style>
