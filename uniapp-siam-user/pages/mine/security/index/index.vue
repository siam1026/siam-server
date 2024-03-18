<template>
    <view>
        <view class="items-class">
            <text class="text-class">当前账号</text>
            <text class="account-text">{{ userInfo.mobile }}</text>
        </view>
        <view class="view-line"></view>
        <view class="items-class" @tap="bindVerify">
            <text class="text-class">重置支付密码</text>
            <text class="iconfont iconhtbArrowright02"></text>
        </view>
    </view>
</template>

<script>
import https from '../../../../utils/http';
import authService from '../../../../utils/auth';
var toastService = require('../../../../utils/toast.service');
var utilHelper = require('../../../../utils/util');
var dateHelper = require('../../../../utils/date-helper');
var systemStatus = require('../../../../utils/system-status');
export default {
    data() {
        return {
            userInfo: {
                mobile: ''
            }
        };
    }
    /**
     * 生命周期函数--监听页面加载
     */,
    onLoad: function (options) {
        this.getUserInfo();
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
        getUserInfo: function (e) {
            https.request('/rest/member/getLoginMemberInfo', {}).then((result) => {
                if (result.success) {
                    result.data.typeVipText = systemStatus.typeVipText(result.data.vipType);
                    result.data.statusVipText = systemStatus.statusVipText(result.data.vipStatus);
                    result.data.vipStartTime = dateHelper.formatDate(result.data.vipStartTime);
                    result.data.vipEndTime = dateHelper.formatDate(result.data.vipEndTime);
                    this.setData({
                        userInfo: result.data
                    });
                }
            });
        },

        bindVerify(e) {
            uni.navigateTo({
                url: '../verify/verify'
            });
        }
    }
};
</script>
<style>
page {
    background: #f5f5f5;
    width: 100%;
    margin: 0;
}

.items-class {
    padding: 20rpx;
    background: white;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 30rpx;
    font-weight: bold;
}

.text-class {
    font-size: 28rpx;
}

.account-text {
    font-size: 30rpx;
    color: #555555;
}
</style>
