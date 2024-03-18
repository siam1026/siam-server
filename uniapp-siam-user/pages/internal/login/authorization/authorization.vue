<template>
    <view class="input-phone-number">
        <image src="/static/assets/logo/logo.jpg" mode="widthFix" class="brand-icon"></image>
        <view class="tip-info-text">申请获取你的公开信息（昵称、头像等）</view>
        <view class="input-button-view">
            <button class="getphonenumber theme-bg" @tap="getUserProfile">微信授权</button>
        </view>
    </view>
</template>

<script>
import {showToast} from '../../../../utils/toast.service';
//获取应用实例
const app = getApp();
var inviterId;
export default {
    data() {
        return {
            userInfo: '',
            hasUserInfo: false
        };
    }
    /**
     * 生命周期函数--监听页面加载
     */,
    onLoad: function (options) {
        if (options.inviterId) {
            inviterId = options.inviterId;
        }
        console.log('{获取用户信息页面},options.inviterId = ' + options.inviterId);
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
        getUserProfile(e) {
            // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认
            // 开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
            console.log(app.globalData.userInfo);
            if (app.globalData.userInfo) {
                uni.redirectTo({
                    url: '../choose/choose?inviterId=' + inviterId
                });
            } else {
                uni.getUserProfile({
                    desc: '用于完善会员资料',
                    // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
                    success: (res) => {
                        console.log(res);
                        if (res.userInfo) {
                            app.globalData.userInfo = res.userInfo;
                            this.setData({
                                userInfo: res.userInfo,
                                hasUserInfo: true
                            });
                            uni.redirectTo({
                                url: '../choose/choose?inviterId=' + inviterId
                            });
                        }
                    },
                    fail() {
                        app.globalData.userInfo = null;
                        uni.redirectTo({
                            url: '../choose/choose?inviterId=' + inviterId
                        });
                    }
                });
            }
        },

        bindLoginTap(e) {
            //用户信息已授权
            toastService.showLoading();
            if (this.hasUserInfo) {
                //console.log(this.data)
                toastService.hideLoading();
                uni.redirectTo({
                    url: '../choose/choose?inviterId=' + inviterId
                });
            } else {
                //用户信息未授权
                toastService.hideLoading();
                if (e.detail.userInfo) {
                    app.globalData.userInfo = e.detail.userInfo;
                    this.userInfo = e.detail.userInfo;
                    this.hasUserInfo = true;
                    uni.redirectTo({
                        url: '../choose/choose?inviterId=' + inviterId
                    });
                } else {
                    this.userInfo = null;
                    this.hasUserInfo = false;
                }
                this.setData({
                    userInfo: this.userInfo,
                    hasUserInfo: this.hasUserInfo
                });
            }
        }
    }
};
</script>
<style>
.input-phone-number {
    text-align: center;
}

.brand-icon {
    width: 258rpx;
    height: auto;
    margin-top: 188rpx;
}

.tip-info-text {
    color: #5e5e5e;
    margin-top: 26rpx;
}

.input-button-view {
    margin: 90rpx;
    text-align: left;
}

.getphonenumber {
    /* margin: 150rpx 60rpx 50rpx 60rpx; */
    font-size: 32rpx;
}

button {
    padding-left: 0px;
    padding-right: 0px;
    margin-left: 0px;
    margin-right: 0px;
}

button[plain] {
    color: black;
    border: 0px;
    font-size: 30rpx;
}
</style>
