<template>
	<view>
		<view class="items-class">
			<text class="text-class">{{ userInfo.mobile }}</text>
			<text class="account-text theme-color" @tap="send">{{ time }}</text>
		</view>
		<view class="view-line"></view>
		<view class="items-class">
			<input type="number" placeholder="请输入短信验证码" maxlength="6" @input="codeKey" />
		</view>
		<view :class="'next-button ' + (disabled ? 'theme-bg' : 'isSend')"
			@tap="parseEventDynamicCode($event, disabled ? 'bindReset' : '')">下一步</view>
	</view>
</template>

<script>
	import https from '../../../../utils/http';
	import authService from '../../../../utils/auth';
	var toastService = require('../../../../utils/toast.service');
	var utilHelper = require('../../../../utils/util');
	var dateHelper = require('../../../../utils/date-helper');
	var systemStatus = require('../../../../utils/system-status');
	var interval = null;
	export default {
		data() {
			return {
				disabled: false,
				time: '获取验证码',
				currentTime: 60,

				userInfo: {
					mobile: ''
				},

				code: '',
				disabledCode: false
			};
		}
		/**
		 * 生命周期函数--监听页面加载
		 */
		,
		onLoad: function(options) {
			this.getUserInfo();
		},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {},
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

			codeKey(e) {
				let key = e.detail.value;
				var data = this;
				data.disabled = false;
				//判断验证码输入框的长度等于6则设置登录按钮可以点击
				if (key.length == 6) {
					data.disabled = true;
				}
				this.setData({
					code: key,
					disabled: data.disabled
				});
			},

			send(e) {
				if (!this.userInfo.mobile) {
					return;
				}
				//this.onLoad({options:{inviterId:this.data.inviterId}});
				let phone = this.userInfo.mobile;
				let isMobile = utils.verifyPhone(phone);
				//电话号码输入有误，弹出模态框提示
				if (!isMobile) {
					toastService.showToast('请输入正确的电话号码');
					return;
				}
				toastService.showLoading('正在发送...', true);
				https.request('/rest/smsLog/sendMobileCode', {
					mobile: phone,
					type: 'findpwd'
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						toastService.showToast(result.message);
						//设置验证码按钮为不可点击状态，因为要等60秒之后才能进行点击
						this.setData({
							disabledCode: true
						});
						this.getCode();
					}
				});
			},

			getCode: function(options) {
				var that = this;
				var currentTime = this.currentTime;
				//设置定时器计算发送验证码之后60秒可以重新发送，一秒请求一次
				interval = setInterval(function() {
					// if (currentTime == 60) {
					//   toastService.hideLoading();
					// }
					currentTime--;
					that.setData({
						time: currentTime + 's',
						currentTime: currentTime
					});
					if (currentTime <= 0) {
						clearInterval(interval); //当60秒过去之后，清除定时器
						that.setData({
							time: '重新获取',
							currentTime: 60,
							disabledCode: false
						});
					}
				}, 1000);
			},

			bindNext(e) {
				uni.redirectTo({
					url: '../verify/verify'
				});
			},

			bindReset(e) {
				// 发送 res.code 到后台换取 openId, sessionKey, unionId
				toastService.showLoading('验证中');
				let phone = this.userInfo.mobile;
				var mobile = /^[1][3,4,5,7,8][0-9]{9}$/;
				var isMobile = mobile.exec(phone);
				//输入有误的话，弹出模态框提示
				if (!isMobile) {
					toastService.showToast('请输入正确的电话号码');
					return;
				}
				https.request('/rest/member/forgetPaymentPassword/step1', {
					mobile: phone,
					mobileCode: this.code
				}).then((result) => {
					if (result.success) {
						this.setData({
							disabled: false
						});
						toastService.hideLoading();
						uni.redirectTo({
							url: '../reset/reset'
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
		/* color: #6cc5ef; */
	}

	input {
		width: 100%;
	}

	.next-button {
		text-align: center;
		margin: 20rpx;
		margin-top: 100px;
		padding: 15rpx;
		border-radius: 15rpx;
		font-size: 30rpx;
	}

	.isSend {
		background-color: #cccccc;
		color: white;
	}
</style>