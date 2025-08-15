<template>
	<view>
		<view class="top-class">
			<view class="account-phone">请为账号 {{ userInfo.mobile }}</view>
			<view class="account-tip">{{ !isAgain ? '设置6位数支付密码' : '请再次输入密码' }}</view>
		</view>
		<block v-if="showPayPwdInput">
			
			// #ifdef APP-PLUS||H5
			<!-- 密码输入框 -->
			<view class="password_dialog_main">
				<view class="password_dialog_tip" style="padding: 0 16px;"><text>使用会员卡余额支付需要验证身份，验证通过后才可进行支付。</text>
				</view>
				<van-password-input :value="pwdVal" :focused="payFocus" @focus="payFocus = true" />
			</view>
			// #endif
			// #ifdef MP-WEIXIN||MP-ALIPAY
			<view class="password_dialog_main">
				<view class="password_dialog_tip"><text>使用会员卡余额支付需要验证身份，验证通过后才可进行支付。</text></view>
				<view class="password_dialog_row theme-border" @tap.stop.prevent="getFocus">
					<view class="password_dialog_item_input" v-for="(item, i) in 6" :key="i">
						<text v-if="pwdVal.length > i"></text>
					</view>
				</view>
				<input class="password_dialog_input_control" password type="number" :focus="payFocus"
					:hold-keyboard="true" :value="pwdVal" @input="inputPwd" maxlength="6"
					:adjust-position="adjustPosition" cursor-spacing="100" :auto-focus="payFocus" inputmode="numeric" />
			</view>
			// #endif
		</block>
		// #ifdef APP-PLUS||H5
		<van-number-keyboard :show="payFocus" @blur="payFocus = false" @input="inputPwd" @delete="deletePwd" />
		// #endif
	</view>
</template>

<script>
	import https from '../../../../utils/http';
	import authService from '../../../../utils/auth';
	import {
		Base64
	} from 'js-base64';
	import toastService from '../../../../utils/toast.service';
	import utilHelper from '../../../../utils/util';
	import dateHelper from '../../../../utils/date-helper';
	import systemStatus from '../../../../utils/system-status';
	//确认密码输入两次重新进入输入密码
	var number = 0;
	export default {
		data() {
			return {
				showPayPwdInput: false,
				//是否展示密码输入层
				pwdVal: '',
				//输入的密码
				payFocus: false,
				//文本框焦点
				isAgain: false,
				adjustPosition: false,
				userInfo: {
					mobile: ''
				},
				oldPassword: '',
				i: ''
			};
		}
		/**
		 * 生命周期函数--监听页面加载
		 */
		,
		onLoad: function(options) {
			
		},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {

		},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {
			this.payFocus = false;
			this.getUserInfo();
		},
		/**
		 * 生命周期函数--监听页面隐藏
		 */
		onHide: function() {},
		/**
		 * 生命周期函数--监听页面卸载
		 */
		onUnload: function() {},
		/**
		 * 页面相关事件处理函数--监听用户下拉动作
		 */
		onPullDownRefresh: function() {},
		/**
		 * 页面上拉触底事件的处理函数
		 */
		onReachBottom: function() {},
		/**
		 * 用户点击右上角分享
		 */
		onShareAppMessage: function() {},
		methods: {
			getUserInfo: function(e) {
				var _this = this;
				https.request('/rest/member/getLoginMemberInfo', {}).then((result) => {
					if (result.success) {
						result.data.typeVipText = systemStatus.typeVipText(result.data.vipType);
						result.data.statusVipText = systemStatus.statusVipText(result.data.vipStatus);
						result.data.vipStartTime = dateHelper.formatDate(result.data.vipStartTime);
						result.data.vipEndTime = dateHelper.formatDate(result.data.vipEndTime);
						this.userInfo = result.data;

						_this.$nextTick(() => {
							_this.showPayPwdInput = true;
							_this.payFocus = false;
							_this.getFocus();
						});
					}
				});
			},

			/**
			 * 显示支付密码输入层
			 */
			showInputLayer: function() {
				this.setData({
					showPayPwdInput: true,
					payFocus: true
				});
			},

			/**
			 * 隐藏支付密码输入层
			 */
			hidePayLayer: function() {
				var val = this.pwdVal;
				this.setData({
					showPayPwdInput: false,
					payFocus: true,
					pwdVal: '',
					showPayPwdInput: true,
					oldPassword: val,
					isAgain: true
				});
			},
			onKeyboardShow(e) {
				console.log('键盘谈起了');
				this.payFocus = true;
				//this.getFocus();
			},
			/**
			 * 获取焦点
			 */
			getFocus() {
				var _this = this;
				this.payFocus = false;
				this.$nextTick(() => {
					console.log("点击获取焦点");
					// #ifdef APP-PLUS||H5
					_this.payFocus = true;
					// #endif
					// #ifdef MP-WEIXIN||MP-ALIPAY
					setTimeout(() => {
						_this.payFocus = true;
					}, 100);
					// #endif
				});
			},
			deletePwd(e) {
				if (this.pwdVal.length === 0) {
					return;
				}
				const lastIndex = this.pwdVal.length - 1;
				this.pwdVal = this.pwdVal.substring(0, lastIndex) + "";
			},
			/**
			 * 输入密码监听
			 */
			inputPwd: function(e) {
				// #ifdef APP-PLUS||H5
				this.pwdVal = this.pwdVal + e;
				// #endif
				// #ifdef MP-WEIXIN||MP-ALIPAY
				this.pwdVal = e.detail.value;
				// #endif
				if (!utilHelper.verifyNumberPassword(Number(this.pwdVal))) {
					this.payFocus = true;
					toastService.showToast('请数字密码');
					return;
				}
				if (this.pwdVal.length >= 6) {
					if (!this.isAgain) {
						this.hidePayLayer();
					} else {
						if (this.pwdVal != this.oldPassword) {
							this.atypismTip();
							number++;
							if (number == 2) {
								console.log(number);
								number = 0;
								this.isAgain = false;
								this.isAgain = this.isAgain;
								toastService.showToast('两次密码输入不一致，请重新输入6位数密码');
							} else {
								toastService.showToast('两次密码输入不一致，请重新输入');
							}
						} else {
							toastService.showLoading('正在加载...');
							this.comfirmSuccess();
						}
					}
				}
			},

			comfirmSuccess() {
				https.request('/rest/member/forgetPaymentPassword/step2', {
					paymentPassword: Base64.encode(this.oldPassword)
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						toastService.showSuccess('设置密码成功');
						let timeout = setTimeout(() => {
							uni.navigateBack(1);
							clearTimeout(timeout);
						}, 2000);
					}
				});
			},

			atypismTip: function() {
				this.setData({
					payFocus: true,
					pwdVal: ''
				});
			},

			bindNext(e) {
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

	.top-class {
		background-color: white;
		text-align: center;
		padding: 50rpx;
	}

	.account-phone {
		font-size: 30rpx;
		margin-bottom: 10rpx;
		color: #818283;
	}

	.account-tip {
		font-weight: bold;
	}

	.password-class {
		margin-top: 40rpx;
	}

	.reset-password {
		display: flex;
		align-items: center;
		margin: 20rpx;
		border-radius: 15rpx;
		background-color: white;
	}

	.input-class {
		border-right: 1rpx solid #c0c0c0;
		height: 88rpx;
		font-size: 50rpx;
		text-align: center;
	}

	.input-class-right {
		border-right: none;
	}

	.tip-class {
		text-align: center;
		font-size: 28rpx;
		color: #818283;
	}

	/* 支付密码css start */
	.password_dialog_main {
		padding: 20rpx;
	}
</style>