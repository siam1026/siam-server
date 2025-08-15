<template>
	<view class="input-phone-number">
		<image :src="'/static/assets/logo/logo.jpg?v=' + timestamp" mode="widthFix" class="brand-icon"></image>
		<view class="input-button-view">
			<input placeholder="请输入手机号" type="number" @input="phoneKey" :focus="true" />
			<view class="view-line"></view>
			<view class="input-button-code">
				<input placeholder="请输入验证码" type="number" @input="codeKey" />
				<button
					:class="'get-verification-code ' + (!disabledCode ? 'code-disabled-true theme-color-border' : 'code-disabled-false')"
					open-type="getUserInfo" @getuserinfo="parseEventDynamicCode($event, !disabledCode ? 'send' : '')"
					style="width: 30%">
					{{ time }}
				</button>
			</view>
			<view class="view-line"></view>
			<!-- <button :disabled="disabled" class="confirm-btn theme-bg"
				@tap="parseEventDynamicCode($event, disabled ? '' : 'loginTap')" hover-class="hover-class-public">
				确定
			</button> -->
			<van-button type="primary" :disabled="disabled" class="confirm-btn theme-bg" color="#004ca0"
			@tap="parseEventDynamicCode($event, disabled ? '' : 'loginTap')" hover-class="hover-class-public" block>确定</van-button>
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
	var interval = null;
	//获取应用实例
	let app = null;
	export default {
		data() {
			return {
				time: '获取验证码',
				currentTime: 60,
				disabled: true,
				disabledCode: true,
				inviterId: '',
				userInfo: '',
				hasUserInfo: false,
				timestamp: '',
				phone: '',
				code: ''
			};
		}
		/**
		 * 生命周期函数--监听页面加载
		 */
		,
		onLoad: function(options) {
			app = getApp();
			//判断是否有邀请者id传递过来
			if (options.inviterId) {
				this.inviterId = options.inviterId;
			}
			console.log('options.inviterId = ' + options.inviterId);
			if (app.globalData.userInfo) {
				this.userInfo = app.globalData.userInfo;
				this.hasUserInfo = true;
			} else if (this.canIUse) {
				// 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
				// 所以此处加入 callback 以防止这种情况
				app.globalData.userInfoReadyCallback = (res) => {
					this.userInfo = res.userInfo;
					this.hasUserInfo = true;
				};
			} else {
				// 在没有 open-type=getUserInfo 版本的兼容处理
				// wx.getUserInfo({
				//   success: res => {
				//     app.globalData.userInfo = res.userInfo
				//     this.setData({
				//       userInfo: res.userInfo,
				//       hasUserInfo: true
				//     })
				//   }
				// })
			}
		},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {
			this.getTimestamp();
		},
		/**
		 * 生命周期函数--监听页面隐藏
		 */
		onHide: function() {
			clearTimeout(interval);
		},
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
			getTimestamp() {
				var timestamp = dateHelper.getTimestamp();
				console.log(timestamp);
				this.setData({
					timestamp: timestamp
				});
			},

			phoneKey(e) {
				//判断电话号码的el表达式
				let phone = e.detail.value;
				let isMobile = utilHelper.verifyPhone(phone);
				var data = this;
				data.disabledCode = true; //设置“获取验证码”按钮为disable
				//如果通过电话号码的el表达式并且停顿时间等于60秒设置可以重新获取
				if (isMobile && this.currentTime == 60) {
					data.disabledCode = false;
				}
				//判断如果验证码输入框不为空并且验证码的长度等于6并且“验证码”按钮不为真则设置可以登录，否则不能登录
				data.disabled = data.code && data.code.length == 6 && !data.disabledCode ? false : true;
				this.setData({
					phone: phone,
					disabled: data.disabled,
					disabledCode: data.disabledCode
				});
			},

			codeKey(e) {
				let key = e.detail.value;
				var data = this;
				data.disabled = true;
				//判断验证码输入框的长度等于6则设置登录按钮可以点击
				if (key.length == 6) {
					data.disabled = false;
				}
				this.setData({
					code: key,
					disabled: data.disabled
				});
			},

			send(e) {
				console.log(e);
				if (!e.detail.userInfo) {
					return;
				}
				//this.onLoad({options:{inviterId:this.data.inviterId}});
				let phone = this.phone;
				let isMobile = utilHelper.verifyPhone(phone);
				//电话号码输入有误，弹出模态框提示
				if (!isMobile) {
					toastService.showToast('请输入正确的电话号码');
					return;
				}
				toastService.showLoading('正在发送...', true);
				https.request('/rest/smsLog/sendMobileCode', {
					mobile: phone,
					type: 'login'
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

			loginTap() {
				console.log(this.phone, this.code);
				// 发送 res.code 到后台换取 openId, sessionKey, unionId
				toastService.showLoading('正在登录...', true);
				let phone = this.phone;
				var mobile = /^[1][3,4,5,7,8][0-9]{9}$/;
				var isMobile = mobile.exec(phone);
				//输入有误的话，弹出模态框提示
				if (!isMobile) {
					toastService.showToast('请输入正确的电话号码');
					return;
				}
				var _this = this;
				// #ifdef APP-PLUS||H5
				this.verificationLogin(this.code);
				// #endif
				// #ifdef MP-WEIXIN||MP-ALIPAY
				uni.login({
					success: (res) => {
						if (!this.userInfo) {
							uni.getUserInfo({
								success: (userRes) => {
									_this.setData({
										userInfo: userRes.userInfo
									});
									_this.verificationLogin(res.code);
								}
							});
						} else {
							this.verificationLogin(res.code);
						}
					}
				});
				// #endif

			},

			verificationLogin(code, userInfo) {
				https.request('/rest/member/verification/login', {
					mobile: this.phone,
					mobileCode: this.code,
					headImg: this.userInfo.avatarUrl,
					username: this.userInfo.nickName,
					sex: this.userInfo.gender,
					code: code,
					inviterId: this.inviterId ? this.inviterId : ''
				}).then((result) => {
					if (result.success) {
						authService.setToken(result.data.token);
						authService.setOpenId(result.data.openId);
						authService.setPhoneNumber(this.phone);
						app.globalData.getUserInfo();
						this.setData({
							disabled: true
						});
						toastService.hideLoading();
						toastService.showSuccess('登录成功');
						let timeout = setTimeout(() => {
							//如果是邀请链接直接跳转到首页，如果是进入用户后退一页
							if (this.inviterId) {
								uni.switchTab({
									url: '../../../mine/index/index'
								});
							} else {
								uni.navigateBack(2);
							}
							clearTimeout(timeout);
						}, 1000);
					}
				});
			}
		}
	};
</script>
<style>
	.input-phone-number {
		text-align: center;
	}

	.brand-icon {
		width: 208rpx;
		height: auto;
		margin-top: 88rpx;
	}

	.input-button-view {
		margin: 90rpx;
		text-align: left;
	}

	.input-button-code {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-top: 30rpx;
	}

	.input-button-code input {
		width: 70%;
	}

	.get-verification-code {
		width: 25%;
		font-size: 22rpx;
		border-radius: 50rpx;
		padding: 10rpx;
		/* height: 30rpx; */
		line-height: 30rpx;
		text-align: center;
	}

	/* .code-disabled-false{
  border: 0.5rpx solid #acacac;
  color: #acacac;
} */

	.code-disabled-false {
		background: #f5f5f5;
		color: #808080;
		border: 1rpx solid #808080;
	}

	input {
		height: 98rpx;
	}

	.confirm-btn {
		margin-top: 50rpx;
		color: white;
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