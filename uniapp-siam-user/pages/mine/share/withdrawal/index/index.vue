<template>
	<view>
		<view class="modular-class">
			<view class="list-class" @tap="openDialog">
				<text class="mode-title">提现至</text>
				<view class="choose-mode">
					<van-icon name="wechat-pay" />
					<text class="mode-name">微信零钱</text>
					<!-- <van-icon name="arrow" /> -->
				</view>
			</view>
		</view>

		<view class="modular-class bottom-class">
			<view class="mode-title">
				<view>
					<text :decode="true">提现金额&nbsp;</text>
					<van-icon name="question-o" @tap="selectCurrentSetting"/>
				</view>
				<view class="txjl-view" @tap="bindWithdrawRecord">
					<text class="theme-color">提现记录</text>
					<van-icon name="arrow" />
				</view>
			</view>
			<view class="input-class">
				<text class="money-title">￥</text>
				<input class="money-input" type="digit" placeholder="  " placeholder-class="placeholder-class"
					:adjust-position="adjustPosition" @input="bindInputMoney" :value="inputMoney" />
			</view>
			<view class="view-line"></view>
			<view class="tips-btn-class" v-if="!isTip || inputMoney <= userInfo.inviteRewardAmount">
				<text class="tip-class">可提现奖励金额￥{{ userInfo.inviteRewardAmount }}，</text>
				<text class="theme-color" @tap="bindAllWithDrawal">全部提现</text>
			</view>
			<view class="tips-btn-class" v-if="isTip && inputMoney > userInfo.inviteRewardAmount">
				<text class="errortip-class">输入金额超过奖励金额</text>
			</view>
			<button style="width: 100%"
				:type="inputMoney && inputMoney > 0 && inputMoney <= userInfo.inviteRewardAmount && isTip ? 'primary' : 'default'"
				@tap="parseEventDynamicCode($event, inputMoney && inputMoney > 0 && inputMoney <= userInfo.inviteRewardAmount && isTip ? 'bindWithDrawalPWd' : '')">
				提现
			</button>
		</view>
		<van-dialog use-slot :show="showPayPwdInput" :showConfirmButton="false" :showCancelButton="false" z-index='2'>
			<view class="flex_between content_box">
				<view></view>
				<view>输入支付密码</view>
				<van-icon name="cross" @tap="balancePayFail" />
			</view>
			
			// #ifdef APP-PLUS||H5
			<!-- 密码输入框 -->
			<view class="content_box" style="padding: 0 0;">
				<view class="password_dialog_tip" style="padding: 0 16px;"><text>使用会员卡余额支付需要验证身份，验证通过后才可进行支付。</text>
				</view>
				<van-password-input :value="pwdVal" :focused="payFocus" @focus="payFocus = true" />
				<view class="theme-color password_dialog_forget_pwd" @tap.stop.prevent="forgetThePassword">忘记密码</view>
			</view>
			// #endif
			// #ifdef MP-WEIXIN||MP-ALIPAY
			<view class="content_box" style="padding: 0 16px;">
				<view class="password_dialog_tip"><text>使用会员卡余额支付需要验证身份，验证通过后才可进行支付。</text></view>
				<view class="password_dialog_row" @tap.stop.prevent="getFocus">
					<view class="password_dialog_item_input" v-for="(item, i) in 6" :key="i">
						<text v-if="pwdVal.length > i"></text>
					</view>
				</view>
				<view class="theme-color password_dialog_forget_pwd" @tap.stop.prevent="forgetThePassword">忘记密码</view>
				<input class="password_dialog_input_control" password type="number" :focus="payFocus"
					:hold-keyboard="true" :value="pwdVal" @input="inputPwd" maxlength="6"
					:adjust-position="adjustPosition" :always-embed="holdKeyboard" :auto-focus="payFocus"
					inputmode="numeric" />
			</view>
			// #endif
		</van-dialog>
		// #ifdef APP-PLUS||H5
		<van-number-keyboard :show="payFocus" @blur="payFocus = false" @input="inputPwd" @delete="deletePwd" />
		// #endif
		<van-action-sheet :show="serverFeeDialog" @close="close" @cancel="close" title="收费提示">
			<view class="content_box">
				<view v-if="!isSatisfyFee" class="isSatisfyFee">余额不满足以支付服务费，实际到账金额如下</view>
				<view class="memberWithdrawFee-desc">
					<text>{{ !isSatisfyFee ? '到账' : '提现' }}金额</text>
					<text :class="'memberWithdrawFee-value ' + (!isSatisfyFee ? 'withdrawFeeAfter-satisfyFee' : '')">
						{{ !isSatisfyFee ? memberWithdrawFeeAfter : inputMoney }}元
					</text>
				</view>
				<view class="memberWithdrawFee-desc">
					<text>本次服务费</text>
					<text class="memberWithdrawFee-value">{{ satisfyFee }}元</text>
				</view>
				<view class="good-choice-btn theme-bg" @tap="bindContinueWithDrawalPWd">继续提现</view>
			</view>

		</van-action-sheet>
		<van-action-sheet :show="selectCurrentDialog" @close="close" @cancel="close" title="奖励规则">
			<view class="content_box">
				<view class="order-info-view">
					<scroll-view style="height: 55vh" scroll-y>
						<text class="order-title">{{ current.commissionRule }}</text>
					</scroll-view>
				</view>
			</view>
		</van-action-sheet>
	</view>
</template>

<script>
	import https from '../../../../../utils/http';
	import toastService from '../../../../../utils/toast.service';
	import utilHelper from '../../../../../utils/util';
	import dateHelper from '../../../../../utils/date-helper';
	import {
		Base64
	} from 'js-base64';
	export default {
		data() {
			return {
				isTip: false,
				showDialog: false,
				showPayPwdInput: false,

				//是否展示密码输入层
				pwdVal: '',

				//输入的密码
				payFocus: false,

				//文本框焦点
				adjustPosition: false,

				holdKeyboard: true,
				serverFeeDialog: false,
				isSatisfy: true,
				selectCurrentDialog: false,

				current: {
					commissionRule: ''
				},

				userInfo: {
					inviteRewardAmount: 0
				},

				inputMoney: '',
				satisfyFee: '',
				isSatisfyFee: '',
				memberWithdrawFeeAfter: '',
				maskClosable: '',
				i: ''
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {
			console.log(this.showPayPwdInput);
			if (this.showPayPwdInput) {
				this.setData({
					payFocus: true,
					pwdVal: ''
				});
			}
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
			selectCurrentSetting() {
				if (this.current) {
					this.setData({
						selectCurrentDialog: true
					});
					return;
				}
				https.request('/rest/setting/selectCurrent', {}).then((result) => {
					if (result.success) {
						result.data.commissionRule = result.data.commissionRule.replace('↵', '\n');
						this.setData({
							current: result.data,
							selectCurrentDialog: true
						});
					}
				});
			},

			getUserInfo: function(e) {
				https.request('/rest/member/getLoginMemberInfo', {}).then((result) => {
					if (result.success) {
						this.setData({
							userInfo: result.data
						});
					}
				});
			},

			bindInputMoney(e) {
				// console.log(e)
				let value = e.detail.value;
				if (!utilHelper.verifyNumber(value)) {
					this.setData({
						inputMoney: ''
					});
					return;
				}
				if (value.length >= 2) {
					if (value == 0 || value == 0) {
						if (value[1] == 0) {
							this.setData({
								inputMoney: this.userInfo.inviteRewardAmount
							});
							return;
						}
					}
				}
				this.isTip = false;
				// console.log(this.data.inputMoney)
				var valueSpilt = value.split('.');
				if (valueSpilt.length > 2) {
					value = this.inputMoney;
				} else {
					if (valueSpilt[1]) {
						if (valueSpilt[1].length > 2) {
							value = this.inputMoney;
						}
					}
					if (value > this.userInfo.inviteRewardAmount) {
						this.isTip = true;
					}
				}
				this.isTip = value >= 0 ? true : false;
				this.setData({
					inputMoney: value,
					isTip: this.isTip
				});
			},

			bindAllWithDrawal(e) {
				this.setData({
					inputMoney: this.userInfo.inviteRewardAmount,
					isTip: this.userInfo.inviteRewardAmount > 0 ? true : false
				});
			},

			bindWithDrawal() {
				if (!this.inputMoney && !this.isTip) {
					toastService.showToast('输入金额超过奖励金额');
					return;
				}
				var self = this;
				uni.checkIsSupportSoterAuthentication({
					success(authentication) {
						console.log('获取本机支持的 SOTER 生物认证方式', authentication);
						// res.supportMode = [] 不具备任何被SOTER支持的生物识别方式
						// res.supportMode = ['fingerPrint'] 只支持指纹识别
						// res.supportMode = ['fingerPrint', 'facial'] 支持指纹识别和人脸识别
						let supportMode = '';
						let key = 0;
						for (let key in authentication.supportMode) {
							if (authentication.supportMode[key] == 'fingerPrint') {
								supportMode = '请使用指纹解锁';
							}
							if (authentication.supportMode[key] == 'facial') {
								supportMode = '请使用面部解锁';
							}
						}
						console.log('SOTER支持的生物识别方式={},{}', supportMode, authentication.supportMode[key]);
						console.log('SOTER支持的生物识别方式=', authentication.supportMode[key]);
						uni.checkIsSoterEnrolledInDevice({
							checkAuthMode: authentication.supportMode[key],
							success(device) {
								console.log('获取设备内是否录入如指纹等生物信息的接口', device);
								if (device.isEnrolled) {
									console.log('开始 SOTER 生物认证');
									uni.startSoterAuthentication({
										requestAuthModes: authentication.supportMode,
										challenge: self.inputMoney,
										authContent: supportMode,
										success(res) {
											console.log('SOTER 生物认证成功', res);
											https
												.request(
													'/rest/member/memberWithdrawRecord/inviteRewardAmount/insert', {
														withdrawAmount: self.inputMoney,
														paymentMode: 1
													})
												.then((result) => {
													if (result.success) {
														toastService.showSuccess(result
															.message);
														setTimeout((time) => {
															uni.navigateBack(1);
															clearTimeout(time);
														}, 2000);
													}
												});
										}
									});
								}
							}
						});
					}
				});
			},

			bindWithDrawalPWd(e) {
				if (this.inputMoney < 1) {
					toastService.showToast('奖励金累计到(≥)1.00元才可以提现');
					return;
				}
				this.selectCurrent();
			},

			bindContinueWithDrawalPWd() {
				var _this = this;
				this.setData({
					serverFeeDialog: false
				});
				if (!this.userInfo.realName) {
					toastService.showModal(
						null,
						'请先确认真实姓名后操作',
						function confirm() {
							uni.navigateTo({
								url: '../../../../mine/userinfo/userinfo'
							});
						},
						null
					);
					return;
				}
				if (!this.inputMoney) {
					toastService.showToast('输入金额为空');
					return;
				}
				if (this.inputMoney > this.userInfo.inviteRewardAmount) {
					toastService.showToast('输入金额为空输入金额超过奖励金额');
					return;
				}
				if (!this.userInfo.paymentPassword) {
					toastService.showModal(
						'',
						'请先设置平台支付密码!',
						function comfirm() {
							uni.navigateTo({
								url: '../../../../mine/security/index/index'
							});
						},
						function() {
							toastService.showToast('设置平台支付密码之后提现');
						}
					);
					return;
				}
				toastService.showLoading();
				_this.$nextTick(() => {
					_this.showPayPwdInput = true;
					_this.getFocus();
				});

				toastService.hideLoading();
			},
			deletePwd(e) {
				if (this.pwdVal.length === 0) {
					return;
				}
				const lastIndex = this.pwdVal.length - 1;
				this.pwdVal = this.pwdVal.substring(0, lastIndex) + "";
			},
			inputPwd: function(e) {
				// #ifdef APP-PLUS||H5
				this.pwdVal = this.pwdVal + e;
				// #endif
				// #ifdef MP-WEIXIN||MP-ALIPAY
				this.pwdVal = e.detail.value;
				// #endif
				if (this.pwdVal.length >= 6) {
					toastService.showLoading('正在加载...');
					this.withDrawal();
				}
			},

			withDrawal() {
				var password = Base64.encode(this.pwdVal);
				this.pwdVal = '';
				this.payFocus = true;
				https.request('/rest/member/memberWithdrawRecord/inviteRewardAmount/insert', {
					withdrawAmount: this.inputMoney,
					paymentMode: 1,
					paymentPassword: password
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						this.hidePwdLayer();
						toastService.showSuccess(result.message);
						setTimeout((time) => {
							uni.navigateBack(1);
							clearTimeout(time);
						}, 2000);
					}
				});
			},
			selectCurrent() {
				toastService.showLoading('正在加载');
				https.request('/rest/setting/selectCurrent', {}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						// console.log(this.data.inputMoney)
						let satisfyFee = utilHelper.toFixed(Number(this.inputMoney) * (result.data
							.memberWithdrawFee / 100));
						let memberWithdrawFeeAfter = this.memberWithdrawFeeAfter + this.inputMoney;
						let isSatisfyFee = true;
						if (this.userInfo.inviteRewardAmount - satisfyFee < this.inputMoney) {
							isSatisfyFee = false;
						}
						this.setData({
							satisfyFee: satisfyFee,
							isSatisfyFee: isSatisfyFee,
							memberWithdrawFeeAfter: utilHelper.toFixed(this.inputMoney - satisfyFee),
							serverFeeDialog: true
						});
					}
				});
			},

			/**
			 * 获取焦点
			 */
			getFocus: function() {
				var _this = this;
				this.payFocus = false;
				//this.showPayPwdInput = true;
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
				
				})
			},

			bindtouchend(e) {
				console.log('触摸结束');
				this.setData({
					payFocus: true
				});
			},

			balancePayFail() {
				toastService.showError('提现失败', true);
				this.hidePwdLayer();
			},

			hidePwdLayer() {
				this.setData({
					showPayPwdInput: false,
					payFocus: false,
					pwdVal: ''
				});
			},

			showPwdLayer() {
				this.setData({
					showPayPwdInput: true,
					payFocus: true,
					pwdVal: ''
				});
			},

			/**
			 * 隐藏支付密码输入层
			 */
			forgetThePassword: function() {
				uni.navigateTo({
					url: '../../../../mine/security/index/index'
				});
			},

			bindWithdrawRecord() {
				uni.navigateTo({
					url: '../detail/detail'
				});
			},

			openDialog() {
				console.log('占位：函数 openDialog 未声明');
			},
			close() {
				this.setData({
					serverFeeDialog: false,
					selectCurrentDialog: false
				})
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

	.modular-class {
		margin: 20rpx;
		background-color: white;
		padding: 20rpx 40rpx;
		font-size: 30rpx;
		border-radius: 15rpx;
	}

	.list-class {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.bottom-class {
		padding-bottom: 40rpx;
	}

	.mode-title {
		font-weight: bold;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.txjl-view {
		font-weight: normal;
		font-size: 30rpx;
	}

	.txjl-view .iconfont {
		color: #9293a2;
	}

	.choose-mode {
		color: #5c5c5c;
	}

	.mode-name {
		margin: 0 10rpx;
	}

	.iconfont.iconwechat_pay {
		font-size: 35rpx;
		color: #09bb07;
	}

	.input-class {
		display: flex;
		align-items: center;
		padding-top: 20rpx;
	}

	.money-title {
		font-size: 56rpx;
	}

	.placeholder-class {
		font-size: 78rpx;
	}

	.money-input {
		height: 100%;
		font-size: 78rpx;
		margin-left: 10rpx;
		padding: 10rpx 0 10rpx 0;
		display: flex;
		justify-content: center;
	}

	.tips-btn-class {
		height: 108rpx;
		line-height: 108rpx;
		font-size: 28rpx;
	}

	.tip-button {
		color: #7d839d;
	}

	.errortip-class {
		color: red;
	}

	.good-choice-btn {
		width: 100%;
		text-align: center;
		padding: 20rpx 0;
		border-radius: 15rpx;
		font-size: 28rpx;
		font-weight: bold;
		position: relative;
		bottom: 0;
		margin-top: 20px;
	}

	.extClassSpecifications .weui-half-screen-dialog__ft.weui-half-screen-dialog__ft {
		padding-left: 0;
		padding-right: 0;
	}

	.extClassSelectCurrent .weui-half-screen-dialog__ft.weui-half-screen-dialog__ft {
		padding: 0;
	}

	.memberWithdrawFee-desc {
		display: flex;
		align-items: center;
		justify-content: space-between;
		color: #787878;
		margin-top: 20rpx;
		font-size: 30rpx;
	}

	.memberWithdrawFee-value {
		color: black;
		font-weight: bold;
		display: flex;
		align-items: center;
	}

	.isSatisfyFee {
		font-size: 28rpx;
		padding: 20rpx 0;
	}

	.withdrawFeeAfter-satisfyFee {
		color: #e6575e;
	}

	.order-title {
		font-size: 28rpx;
		color: rgb(141, 141, 141);
	}
</style>