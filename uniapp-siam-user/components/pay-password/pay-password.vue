<template>
    <!-- 密码输入框 -->
    <view v-if="showPayPwdInput">
        <view class="bg_layer"></view>
        <view class="password_dialog_main">
            <view class="input_title">
                <view class="input_back" @tap.stop.prevent="hidePayLayer"><text></text></view>
                <text>输入支付密码</text>
            </view>
            <view class="password_dialog_tip"><text>使用会员卡余额支付需要验证身份，验证通过后才可进行支付。</text></view>
            <view class="password_dialog_row" @tap.stop.prevent="getFocus">
                <view class="password_dialog_item_input" v-for="(item, i) in 6" :key="i">
                    <text v-if="pwdValClone.length > i"></text>
                </view>
            </view>
            <view class="password_dialog_forget_pwd" @tap.stop.prevent="hidePayLayer">忘记密码</view>
            <input class="password_dialog_input_control" password type="number" :focus="payFocusClone" @input="inputPwd" maxlength="6" />
        </view>
    </view>
</template>

<script>
// component.js
export default {
    data() {
        return {
            from: 'component',
            showPayPwdInput: false,
            i: '',
            payFocusClone: false,
            pwdValClone: ''
        };
    },
    props: {
        isShow: {
            type: Boolean,
            default: false
        },
        pwdVal: {
            type: Number,
            default: ''
        },
        payFocus: {
            type: Boolean,
            default: false
        }
    },
    mounted() {
        // 处理小程序 ready 生命周期
        this.$nextTick(() => this.ready());
    },
    methods: {
        ready() {},

        /**
         * 显示支付密码输入层
         */
        showInputLayer: function () {
            this.setData({
                showPayPwdInput: true,
                payFocusClone: true
            });
        },

        /**
         * 隐藏支付密码输入层
         */
        hidePayLayer: function () {
            var val = this.pwdVal;
            this.setData(
                {
                    showPayPwdInput: false,
                    payFocusClone: false,
                    pwdValClone: ''
                },
                function () {
                    uni.showToast({
                        title: val
                    });
                }
            );
        },

        /**
         * 获取焦点
         */
        getFocus: function () {
            this.setData({
                payFocusClone: true
            });
        },

        /**
         * 输入密码监听
         */
        inputPwd: function (e) {
            this.setData({
                pwdValClone: e.detail.value
            });
            if (e.detail.value.length >= 6) {
                this.hidePayLayer();
            }
        }
    },
    created: function () {},
    watch: {
        payFocus: {
            handler: function (newVal, oldVal) {
                this.payFocusClone = newVal;
            },

            immediate: true
        },

        pwdVal: {
            handler: function (newVal, oldVal) {
                this.pwdValClone = newVal;
            },

            immediate: true
        }
    }
};
</script>
<style>
.btn_pay {
    margin: 100rpx auto;
    width: 600rpx;
    height: 100rpx;
    line-height: 100rpx;
    border-radius: 100rpx;
    background-color: #d3a95a;
    color: #fff;
    font-size: 36rpx;
    text-align: center;
}
/* 支付密码css start */
.bg_layer {
    position: fixed;
    left: 0;
    top: 0;
    bottom: 0;
    right: 0;
    background-color: rgba(0, 0, 0, 0.6);
    z-index: 9998;
}
.password_dialog_main {
    position: fixed;
    left: 0;
    bottom: 500rpx;
    width: 100%;
    height: 394rpx;
    background-color: #fff;
    z-index: 9999;
}
.input_title {
    width: 100%;
    height: 90rpx;
    line-height: 90rpx;
    text-align: center;
    font-size: 32rpx;
    border-bottom: 1rpx solid #e2e2e2;
}
.input_back {
    position: absolute;
    left: 0;
    top: 0;
    width: 80rpx;
    height: 90rpx;
    display: flex;
    justify-content: center;
    align-items: center;
}
.input_back text {
    width: 20rpx;
    height: 20rpx;
    background-color: white;
    border: 1rpx solid #aaa;
    border-width: 5rpx 0 0 5rpx;
    transform: rotate(-45deg);
}

.password_dialog_tip {
    margin: 30rpx;
    font-size: 24rpx;
    color: #888;
}

/* 密码掩码模拟 */
.password_dialog_row {
    width: 690rpx;
    margin: 0 auto;
    height: 98rpx;
    position: relative;
    display: flex;
    align-items: center;
    border: 1rpx solid #e2e2e2;
    border-radius: 20rpx;
}
.password_dialog_row .password_dialog_item_input {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    border-right: 1rpx solid #e2e2e2;
    position: relative;
}
.password_dialog_item_input:nth-last-of-type(1) {
    border-right: 0;
}
.password_dialog_item_input text {
    width: 30rpx;
    height: 30rpx;
    border-radius: 30rpx;
    background-color: #555;
}

.password_dialog_forget_pwd {
    float: right;
    margin: 30rpx;
    width: 100rpx;
    text-align: right;
    font-size: 24rpx;
    color: #ff7800;
}

/* 文本输入框位置: 设置到左边隐藏 */
.password_dialog_input_control {
    position: relative;
    left: -300rpx;
    bottom: 0;
    width: 100rpx;
    height: 100rpx;
}
</style>
