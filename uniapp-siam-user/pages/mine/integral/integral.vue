<template>
    <view>
        <view class="balance-views theme-bg">
            <view class="balance-text">当前积分</view>
            <view class="balance-view">
                {{ userInfo.points }}
            </view>
            <view @tap="bindUnreceivedPoints" v-if="userInfo.unreceivedPoints > 0">未到账积分：{{ userInfo.unreceivedPoints }} 元</view>
            <view class="balance-detail">
                <view class="balance-detail-info">
                    <view class="balance-quota">{{ userInfo.totalPoints }}</view>
                    <view class="balance-title">累计积分</view>
                </view>
                <view class="balance-detail-info">
                    <view class="balance-quota">{{ userInfo.totalConsumePoints }}</view>
                    <view class="balance-title">累计消费</view>
                </view>
                <view class="balance-detail-info">
                    <view class="balance-quota">{{ userInfo.totayGainPoints }}</view>
                    <view class="balance-title">今日获得</view>
                </view>
            </view>
        </view>
        <swiper :current="currentTab" class="swiper-box" duration="300" :style="'height:' + (winHeight - 10) + 'px'" @change="bindSlideChange">
            <swiper-item class="swiper-items">
                <scroll-view @scrolltoupper="onPullDownRefresh" upper-threshold="-50" @scrolltolower="onReachBottom" lower-threshold="0" scroll-y class="scroll-views">
                    <view class="integral-items" @tap="openSpecifications" :data-id="item.id" v-for="(item, index) in integralList" :key="index">
                        <view class="integral-item">
                            <view class="integral-item-name">
                                <text>{{ item.message }}</text>
                            </view>
                            <view class="integral-item-time">
                                <text>{{ item.createTime }}</text>
                            </view>
                        </view>

                        <view :class="'integral-item-number ' + (item.operateType == 1 ? 'theme-color' : 'color-integral')">
                            {{ item.operateType == 1 ? '+' : '-' }}{{ item.number }}
                        </view>
                    </view>
					<van-empty v-if="integralList.length <= 0" description="暂无记录">
					</van-empty>
                </scroll-view>
            </swiper-item>
        </swiper>
		<van-action-sheet :show="specificationsDialog" @close="close" title="账单详情">
            <view class="content_box">
                <view class="order-info-view">
                    <view class="order-title">账单说明</view>
                    <view class="order-info">{{ recordInfo.message }}</view>
                    <view class="order-title">账单金额</view>
                    <view class="order-info">{{ recordInfo.operateType == 1 ? '+' : '-' }}{{ recordInfo.number }}元</view>
                    <view class="order-title">支付时间</view>
                    <view class="order-info">{{ recordInfo.createTime }}</view>
                    <view class="order-title" v-if="recordInfo.serviceFee">服务费</view>
                    <view class="order-info" v-if="recordInfo.serviceFee">{{ recordInfo.serviceFee }}元</view>
                </view>
            </view>
        </van-action-sheet>
    </view>
</template>

<script>
import https from '../../../utils/http';
import authService from '../../../utils/auth';
var toastService = require('../../../utils/toast.service');
var utilHelper = require('../../../utils/util');
var dateHelper = require('../../../utils/date-helper');
var systemStatus = require('../../../utils/system-status');
var pageNo = 1;
var pageSize = 10;
export default {
    data() {
        return {
            loading: false,
            color: '#000',
            background: 'rgba(0,0,0,0)',
            show: true,
            animated: false,
            extClass: 'weui-navigation-bar-custom',
            currentTab: 0,
            exchangeList: [],
            button: false,
            integralList: [],
            specificationsDialog: false,
            recordInfo: {
                message: '',
                operateType: 0,
                number: '',
                createTime: '',
                serviceFee: ''
            },
            winHeight: '',
            userInfo: {
                points: '',
                unreceivedPoints: 0,
                totalPoints: '',
                totalConsumePoints: '',
                totayGainPoints: ''
            }
        };
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.getUserInfo();
    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {
        this.getMemberBillingRecord();
    },
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
    onPullDownRefresh: function () {
        pageNo = 1;
        // 显示顶部刷新图标
        uni.showNavigationBarLoading();
        //
        this.getMemberBillingRecord();
        // 隐藏导航栏加载框
        uni.hideNavigationBarLoading();
        // 停止下拉动作
        uni.stopPullDownRefresh();
    },
    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {
        pageNo = pageNo + 1;
        toastService.showLoading('正在加载...');
        https
            .request('/rest/member/billingRecord/list', {
                pageNo: pageNo,
                pageSize: pageSize,
                coinType: 1
            })
            .then((result) => {
                if (result.success) {
                    this.getHeight();
                    toastService.hideLoading();
                    if (result.data.records.length > 0) {
                        result.data.records.forEach((res) => {
                            res.createTime = dateHelper.formatISODate(res.createTime);
                            res.memberIntegralTypeText = systemStatus.memberIntegralTypeText(res.memberIntegralTypeText);
                            this.integralList.push(res);
                        });
                        this.setData({
                            integralList: this.integralList
                        });
                        return;
                    }
                    toastService.showToast('没有更多啦~');
                    pageNo = pageNo - 1;
                }
            });
    },
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {},
    methods: {
        openSpecifications(e) {
            console.log(e);
            this.setData({
                specificationsDialog: true
            });
            this.recordSelectById(e.currentTarget.dataset.id);
        },

        recordSelectById(id) {
            https
                .request('/rest/member/billingRecord/selectById', {
                    id: id
                })
                .then((result) => {
                    toastService.hideLoading();
                    if (result.success) {
                        result.data.createTime = dateHelper.fmtDate(result.data.createTime);
                        this.setData({
                            recordInfo: result.data
                        });
                    }
                });
        },

        getMemberBillingRecord(e) {
            https
                .request('/rest/member/billingRecord/list', {
                    pageNo: pageNo,
                    pageSize: pageSize,
                    coinType: 1
                })
                .then((result) => {
                    if (result.success) {
                        result.data.records.forEach((res) => {
                            res.createTime = dateHelper.fmtDate(res.createTime);
                            res.memberIntegralTypeText = systemStatus.memberIntegralTypeText(res.memberIntegralTypeText);
                        });
                        this.setData({
                            integralList: result.data.records
                        });
                        this.getHeight();
                    }
                });
        },

        getHeight() {
            //获取用户手机系统信息
            var that = this;
            uni.getSystemInfo({
                success: function (res) {
                    let winHeight = res.windowHeight; //获取高度
                    //获取class为settlement-view并减去这个高度，自适应scrollview的高度
                    const query = uni.createSelectorQuery();
                    query.select('.balance-views').boundingClientRect();
                    query.selectViewport().scrollOffset();
                    query.exec(function (res) {
                        that.setData({
                            winHeight: winHeight - res[0].height
                        });
                    });
                }
            });
        },

        getUserInfo: function (e) {
            https.request('/rest/member/getLoginMemberInfo', {}).then((result) => {
                if (result.success) {
                    result.data.typeVipText = systemStatus.typeVipText(result.data.type);
                    result.data.isVip = result.data.type == 1 ? false : true;
                    this.setData({
                        userInfo: result.data
                    });
                }
            });
        },

        // 滑动切换tab
        bindSlideChange: function (e) {
            var that = this;
            that.setData({
                currentTab: e.detail.current
            });
        },

        //点击切换
        clickTab: function (e) {
            var that = this;
            if (that.currentTab === e.target.dataset.current) {
                return false;
            } else {
                that.setData({
                    currentTab: e.target.dataset.current
                });
            }
        },

        bindUnreceivedPoints() {
            uni.navigateTo({
                url: '../unreceived/integral/list/list'
            });
        },

        onPullDownRefresh() {
            console.log('占位：函数 onPullDownRefresh 未声明');
        },

        onReachBottom() {
            console.log('占位：函数 onReachBottom 未声明');
        },
		close(){
			this.setData({
				specificationsDialog:false
			})
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

.topImg {
    width: 100%;
    height: auto;
}

.index-bg-class {
    width: 100%;
    border-radius: 0 0 35% 35%;
}

.balance-views {
    height: 360rpx;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    color: white;
}

.balance-view {
    font-size: 60rpx;
}

.balance-text {
    font-size: 28rpx;
}

.balance-detail {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 80%;
    margin-top: 20rpx;
}

.balance-detail-info {
    text-align: center;
}

.balance-quota {
    font-size: 32rpx;
}

.balance-title {
    font-size: 22rpx;
}

.swiper-tab {
    width: 100%;
    text-align: center;
    height: 88rpx;
    line-height: 88rpx;
    display: flex;
    flex-flow: row;
    justify-content: space-between;
    background: #fff;
    z-index: 1;
    border-bottom: 1rpx solid #ededed;
}

.swiper-tab-item {
    width: 50%;
}

.swiper-box {
    display: block;
    width: 100%;
    height: 100%;
    overflow: hidden;
    position: absolute;
}

.swiper-items {
    height: 100%;
}

.scroll-views {
    height: 100%;
}

.integral-items {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20rpx 26rpx;
    border-bottom: 0.5px solid #f5f5f5;
    margin: 20rpx;
    background-color: white;
    border-radius: 15rpx;
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

.order-title {
    font-size: 28rpx;
    color: rgb(141, 141, 141);
}

.order-info {
    font-size: 28rpx;
    margin-bottom: 10rpx;
}
</style>
