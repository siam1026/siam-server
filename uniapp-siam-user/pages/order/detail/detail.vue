<template>
	<view>
		<view class="status-to-greet">
			<view class="unpaid-class" v-if="order.status == 1">
				<view class="comfirm-tip">请在五分钟内完成支付，过时订单将会自动取消！</view>
				<view class="pay-cancel-button">
					<view class="cancel-button" hover-class="hover-class-public" @tap="cancelOrder">取消订单</view>
					<view class="pay-button theme-border theme-color" hover-class="hover-class-public"
						@tap="openConfirm">立即支付</view>
				</view>
			</view>
			<view class="to-greet-view" v-else-if="order.status == 2">
				{{ order.shoppingWay == 1 ? '取餐号:' : '配送号:' }}
				<text class="order-queueNo">{{ order.queueNo }}</text>
			</view>
			<view class="to-greet-view" v-else-if="order.status == 3">
				取餐号:
				<text class="order-queueNo">{{ order.queueNo }}</text>
			</view>
			<view class="to-greet-view" v-else-if="order.status == 4 || order.status == 5">
				配送号:
				<text class="order-queueNo">{{ order.queueNo }}</text>
			</view>
			<view class="to-greet-view" v-else>期待您再次光临。</view>
			<view class="status-view">{{ order.statusText }}</view>

			<view class="caozuo-buttons">
				<button size="mini" class="quxiao-shenqing-button pinglun-btn" hover-class="hover-class-public"
					@tap="isAllowCancelNoReason" v-if="order.isAllowCancelNoReason" style="font-size: 28rpx">
					取消订单
				</button>
				<button size="mini" class="quxiao-shenqing-button pinglun-btn" hover-class="hover-class-public"
					@tap="applyRefund" v-if="order.isAllowApplyRefund" style="font-size: 28rpx">
					申请退款
				</button>
				<button v-if="order.isAllowAppraise" size="mini" class="pinglun-btn theme-bg" :data-id="order.id"
					:data-shopId="order.shopId" @tap="evaluateTip" style="font-size: 28rpx">
					评论
				</button>
				<button size="mini" class="pinglun-btn theme-bg" @tap="contactBussinessTap"
					style="font-size: 28rpx">联系商家</button>
			</view>
		</view>
		<view class="refund-process-view" @tap="bindRefundProcess" v-if="order.isRefundOrder">
			<view class="refund-process-detail">
				<text>退款进度</text>
				<view class="refund-process-right">
					<text class="theme-color">{{ order.refundStatusText }}</text>
					<van-icon name="arrow" color="#004ca0" />
				</view>
			</view>
		</view>

		<view class="order-details-view">
			<view class="order-number-time">
				<text class="order-number-text">{{ order.shoppingWay == 1 ? '自取' : '外卖' }}订单：{{ order.orderNo }}</text>
				<text class="time-text">{{ order.createTime }}</text>
			</view>
			<view class="view-line"></view>
			<view class="commodity-details-view" v-for="(item, index) in orderDetailList" :key="index">
				<view class="commodity-name-specs">
					<view class="commodity-name out_of_range one_row">{{ item.goodsName }}</view>
					<view class="commodity-specs">{{ item.specListAnalysis }}</view>
				</view>

				<view class="commodity-number-money">
					<text class="commodity-number">x{{ item.number }}</text>
					<text class="commodity-money">￥{{ item.price }}</text>
				</view>
			</view>
			<view class="distribution-fee-view">
				<text>配送费</text>
				<text>￥{{ order.deliveryFee }}</text>
			</view>
			<view class="distribution-fee-view">
				<text>包装费</text>
				<text>￥{{ order.packingCharges }}</text>
			</view>
			<view class="distribution-fee-view" v-if="order.fullReductionRuleId">
				<text>使用满减</text>
				<text>{{ order.fullReductionRuleDescription }}</text>
			</view>
			<view class="distribution-fee-view" v-if="order.couponsId">
				<text>使用优惠券</text>
				<text>{{ order.couponsDescription }}</text>
			</view>
			<view class="view-line"></view>
			<view class="actual-payment-view">
				<text class="actual-payment-number" :decode="true">{{ order.description }}商品</text>
				<view class="actual-payment-view">
					<text class="actual-payment-title">实付</text>
					<text class="actual-payment-money">￥{{ order.actualPrice }}</text>
				</view>
			</view>
		</view>
		<view class="order-time-view">
			<text class="receiving-address-title">支付方式</text>
			<text class="receiving-address">{{ order.paymentModeText }}</text>
		</view>
		<view class="receiving-address-view">
			<text class="receiving-address-title">{{ order.shoppingWay == 1 ? '自取' : '配送' }}地址</text>
			<view class="receiving-address-details" v-if="order.shoppingWay == 1">
				<view class="receiving-address out_of_range">【{{ order.shopName }}】{{ order.shopAddress }}
				</view>
			</view>
			<view class="receiving-address-details" v-else>
				<view class="receiving-address out_of_range">
					{{ order.contactProvince }}{{ order.contactCity }}{{ order.contactArea }}{{ order.contactStreet }}
				</view>
				<view class="username-phone">{{ order.contactRealname }}{{ order.contactSex == 0 ? '先生' : '女士' }}
					{{ order.contactPhone }}
				</view>
			</view>
		</view>
		<van-action-sheet :show="choiceReasonDialog" title="选择取消原因" @close="close" @cancel="close" z-index="1">
			<view class="content_box">
				<scroll-view style="height: 55vh" scroll-y>
					<radio-group class="choiceReason-radio-group" @change="radioChange">
						<label
							:class="'choiceReason-lable ' + (refundReasonRapidly.length - 1 != index ? 'choiceReason-border' : '')"
							v-for="(item, index) in refundReasonRapidly" :key="index">
							{{ item.name }}

							<radio :value="item.value"></radio>
						</label>
					</radio-group>
				</scroll-view>
				<view class="content_box_footer">
					<view class="good-choice-btn theme-bg" @tap="cancelOrderNoReasonFun">确定</view>
				</view>
			</view>

		</van-action-sheet>
		<van-action-sheet :show="dialogShow" :actions="paymentModes" @select="goToPay" cancel-text="取消" :title="title"
			@cancel="closeDialog" @close="closeDialog"></van-action-sheet>
		<van-dialog use-slot :show="showPayPwdInput" :showConfirmButton="false" :showCancelButton="false" z-index='2'>
			<view class="flex_between content_box">
				<view></view>
				<view>输入支付密码</view>
				<van-icon name="cross" @tap="balancePayFail" />
			</view>
			// #ifdef APP-PLUS||H5
			<!-- 密码输入框 -->
			<view class="content_box" style="padding: 0 0;">
				<view class="password_dialog_tip" style="padding: 0 16px;"><text>使用会员卡余额支付需要验证身份，验证通过后才可进行支付。</text></view>
				<van-password-input :value="pwdVal" :focused="payFocus" @focus="payFocus = true" />
				<view class="theme-color password_dialog_forget_pwd" @tap.stop.prevent="forgetThePassword">忘记密码</view>
			</view>
			// #endif
			// #ifdef MP-WEIXIN||MP-ALIPAY
			<view class="content_box" style="padding: 0 16px;">
				<view class="password_dialog_tip"><text>使用会员卡余额支付需要验证身份，验证通过后才可进行支付。</text></view>
				<view class="password_dialog_row" @tap="getFocus">
					<view class="password_dialog_item_input" v-for="(item, i) in 6" :key="i">
						<text v-if="pwdVal.length > i"></text>
					</view>
				</view>
				<view class="theme-color password_dialog_forget_pwd" @tap.stop.prevent="forgetThePassword">忘记密码</view>
				<input class="password_dialog_input_control" password type="number" :focus="payFocus"
					:hold-keyboard="true" :value="pwdVal" @input="inputPwd" maxlength="6"
					:adjust-position="adjustPosition" cursor-spacing="100" :auto-focus="payFocus" inputmode="numeric" />
			</view>
			// #endif
		</van-dialog>
		<van-number-keyboard :show="payFocus" @blur="payFocus = false" @input="inputPwd" @delete="deletePwd" />
	</view>
</template>

<script>
	import https from '../../../utils/http';
	import authService from '../../../utils/auth';
	import {
		Base64
	} from 'js-base64';
	import toastService from '../../../utils/toast.service';
	import utilHelper from '../../../utils/util';
	import dateHelper from '../../../utils/date-helper';
	import systemStatus from '../../../utils/system-status';

	var id = '';
	let app = null;
	export default {
		data() {
			return {
				refundReasonRapidly: [{
						value: 1,
						name: '信息填写错误'
					},
					{
						value: 2,
						name: '送达时间选错了'
					},
					{
						value: 3,
						name: '买错了/买少了'
					},
					{
						value: 4,
						name: '商家缺货'
					},
					{
						value: 5,
						name: '商家联系我取消'
					},
					{
						value: 6,
						name: '配送太慢'
					},
					{
						value: 7,
						name: '骑手联系我取消'
					},
					{
						value: 8,
						name: '我不想要了'
					}
				],

				refundStatusTexts: [{
						value: 1,
						name: '退款申请已提交'
					},
					{
						value: 2,
						name: '等待商家处理'
					},
					{
						value: 3,
						name: '商家拒绝退款'
					},
					{
						value: 4,
						name: '等待平台处理'
					},
					{
						value: 5,
						name: '平台拒绝退款，退款已关闭'
					},
					{
						value: 6,
						name: '退款已关闭'
					},
					{
						value: 7,
						name: '退款成功'
					}
				],
				choiceReasonDialog: false,
				choiceReasonApplyDialog: false,
				buttons: [{
						text: '取消'
					},
					{
						text: '确定'
					}
				],
				showPayPwdInput: false,
				//是否展示密码输入层
				pwdVal: '',
				//输入的密码
				payFocus: false,
				//文本框焦点
				adjustPosition: false,
				isForgetThePassword: false,
				userInfo: '',
				dialogShow: false,
				maskClosable: false,
				title: '',
				paymentModes: [],
				paymentMode: '',
				paymentModeIndex: '',
				balanceId: '',
				balanceOpenId: '',
				cancelOrderNoReason: '',
				order: {
					status: 0,
					queueNo: '',
					statusText: '',
					isAllowCancelNoReason: '',
					isAllowApplyRefund: '',
					isAllowAppraise: '',
					id: '',
					shopId: '',
					isRefundOrder: '',
					refundStatusText: '',
					shoppingWay: 0,
					orderNo: '',
					createTime: '',
					deliveryFee: '',
					packingCharges: '',
					fullReductionRuleId: '',
					fullReductionRuleDescription: '',
					couponsId: '',
					couponsDescription: '',
					description: '',
					actualPrice: '',
					paymentModeText: '',
					shopName: '',
					shopAddress: '',
					contactProvince: '',
					contactCity: '',
					contactArea: '',
					contactStreet: '',
					contactRealname: '',
					contactSex: 0,
					contactPhone: ''
				},
				orderDetailList: '',
				shopInfo: '',
				i: ''
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			app = getApp();
			id = options.id;
			this.getOrderDetail(id);
		},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {
			if (this.isForgetThePassword || this.showPayPwdInput) {
				console.log(this.paymentModes);
				console.log(this.paymentModeIndex);
				if (this.paymentModes[this.paymentModeIndex].value == 2) {
					this.showPwdLayer();
					this.setData({
						isForgetThePassword: false
					});
				}
			}
			this.getLoginMemberInfo();
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
			getLoginMemberInfo: function(e) {
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

			deliveryAddressChange(e) {
				let deliveryData = {
					deliveryAddress: 0
				};
				let self = this;
				authService.checkIsLogin().then((result) => {
					if (result) {
						//判断店铺是否打烊
						let startTime = this.shopInfo.shop.startTime;
						let endTime = this.shopInfo.shop.endTime;
						let isOperating = this.shopInfo.shop.isOperating;
						app.globalData.getIsBusiness(startTime, endTime, isOperating).then((result) => {
							if (!result) {
								return;
							}
							console.log(this);
							uni.navigateTo({
								url: '../../address/choose/choose?radioIndex=0&pageType=orderDetail&orderNo=' +
									this.order.orderNo +
									'&orderId=' +
									this.order.id +
									'&deliveryData=' +
									JSON.stringify(deliveryData) +
									'&shopId=' +
									this.order.shopId +
									'&reducedDeliveryPrice=' +
									this.shopInfo.shop.reducedDeliveryPrice
							});
						});
						return;
					}
					app.globalData.checkIsAuth('scope.userInfo');
				});
			},

			openConfirm() {
				let paymentModes = [{
						value: 1,
						name: '微信支付',
						icon: 'wechat-pay',
						show: true
					},
					{
						value: 2,
						name: '平台余额',
						icon: 'balance-pay',
						show: true
					}
				];
				console.log('余额=', this.userInfo.balance);
				console.log('需支付金额=', this.order.actualPrice);
				if (this.order.actualPrice > this.userInfo.balance) {
					paymentModes[1].subname = '余额不足';
					paymentModes[1].isBindTap = true;
				}
				if (!this.userInfo.paymentPassword) {
					paymentModes[1].subname = '未设置支付密码,去设置';
					paymentModes[1].isBindTap = false;
				}
				this.dialogShow = true;
				this.maskClosable = false;
				this.title = '请选择支付方式';
				this.paymentModes = paymentModes;
			},

			closeDialog: function() {
				this.setData({
					dialogShow: false
				});
			},

			btnClick(e) {
				console.log(e);
				this.closeDialog();
			},

			paymentModeChange(e) {
				console.log(e);
				console.log(this.paymentModes[e.detail.value].name);
				this.setData({
					paymentMode: this.paymentModes[e.detail.value].mode
				});
			},

			goToPay(e) {
				console.log(e)
				var _this = this;
				authService.getOpenId().then((openId) => {
					console.log(openId);
					_this.closeDialog();
					// if (!openId) {
					//   toastService.showToast("登录用户错误，请重新登录");
					//   return
					// }
					console.log(_this.order);
					var paymentModeIndex = 1;
					// #ifdef APP-PLUS||H5
					paymentModeIndex = e.value;
					// #endif
					// #ifdef MP-WEIXIN||MP-ALIPAY
					paymentModeIndex = e.detail.value;
					// #endif
					_this.setData({
						paymentModeIndex: paymentModeIndex
					});
					if (paymentModeIndex == 1) {
						console.log('微信支付');
						toastService.showToast('暂不支持微信支付，请选择余额支付/积分支付');
						return;
						//toastService.showLoading();
						//that.weChatPay(id, openId);
					}

					if (paymentModeIndex == 2) {
						console.log('余额支付');
						if (!_this.userInfo.paymentPassword) {
							uni.navigateTo({
								url: '../../mine/security/index/index'
							});
							return;
						}
						_this.setData({
							balanceId: id,
							balanceOpenId: openId
						});
						_this.$nextTick(() => {
							_this.showPayPwdInput = true;
							_this.getFocus();
						});
					}
				}, null);
			},

			close(e) {
				console.log(e);
			},

			weChatPay(id, openId) {
				var that = this;
				let pages = getCurrentPages();
				let prevPage = pages[pages.length - 2]; //上一个页面
				https.request('/rest/member/wxPay/toPay4Applet', {
					openid: openId,
					type: 1,
					out_trade_no: this.order.orderNo,
					total_fee: this.order.actualPrice
				}).then((result) => {
					if (result.success) {
						toastService.hideLoading();
						uni.requestPayment({
							appId: result.data.appid,
							timeStamp: result.data.timeStamp,
							nonceStr: result.data.nonceStr,
							package: result.data.package,
							signType: 'MD5',
							paySign: result.data.paySign,
							success(res) {
								toastService.showSuccess('支付成功', true);
								let timeout = setTimeout(() => {
									that.getOrderDetail(id);
									prevPage.getOrderDetail(id);
									clearTimeout(timeout);
								}, 1000);
							},
							fail(res) {
								toastService.showError('支付失败', true);
								let timeout = setTimeout(() => {
									that.getOrderDetail(id);
									clearTimeout(timeout);
								}, 1000);
							}
						});
					}
				});
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
					this.balancePay();
				}
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

			balancePay() {
				let pages = getCurrentPages();
				let prevPage = pages[pages.length - 2]; //上一个页面
				var password = Base64.encode(this.pwdVal);
				this.setData({
					pwdVal: '',
					payFocus: true
				});
				https.request('/rest/member/platformPay/byBalance', {
					openid: this.balanceOpenId,
					type: 1,
					paymentMode: 2,
					paymentPassword: password,
					out_trade_no: this.order.orderNo,
					total_fee: this.order.actualPrice
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						toastService.showSuccess('支付成功', true);
						this.hidePwdLayer();
						let timeout = setTimeout(() => {
							this.getOrderDetail(this.balanceId);
							prevPage.getOrderDetail(this.balanceId);
							clearTimeout(timeout);
						}, 1000);
					}
				});
			},

			bindtouchend(e) {
				console.log('触摸结束');
				this.showPwdLayer();
			},

			balancePayFail() {
				this.hidePwdLayer();
				toastService.showError('支付失败', true);
				// let timeout = setTimeout(() => {

				//   this.getOrderDetail(this.data.balanceId);
				//   clearTimeout(timeout);
				// }, 1000);
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
				this.setData({
					isForgetThePassword: true
				});
				uni.navigateTo({
					url: '../../mine/security/index/index'
				});
			},

			cancelOrder() {
				let that = this;
				toastService.showModal(
					null,
					'确定取消这个订单吗?',
					function() {
						https.request('/rest/member/order/cancelOrder', {
							id: id
						}).then((result) => {
							if (result.success) {
								toastService.showSuccess('取消成功', true);
								// var pages = getCurrentPages();
								// var prevPage = pages[pages.length - 2]; //上一个页面
								//直接调用上一个页面的 setData() 方法，把数据存到上一个页面中去
								// prevPage.setData({
								//   isOperation: true
								// })
								let timeout = setTimeout(() => {
									//wx.navigateBack(1);
									that.getOrderDetail(id);
									clearTimeout(timeout);
								}, 1500);
							}
						});
					},
					null
				);
			},
			close() {
				this.choiceReasonDialog = false;
			},
			isAllowCancelNoReason() {
				this.choiceReasonDialog = true;
			},

			applyRefund() {
				// this.setData({
				//   choiceReasonApplyDialog: this.data.choiceReasonApplyDialog ? false : true
				// });
				uni.navigateTo({
					url: '../refund/apply/apply?orderId=' + this.order.id
				});
			},

			radioChange(e) {
				console.log(e);
				this.setData({
					cancelOrderNoReason: e.detail.value
				});
			},

			cancelOrderNoReasonFun() {
				toastService.showLoading();
				this.choiceReasonDialog = false;
				https.request('/rest/member/order/cancelOrderNoReason', {
					id: this.order.id,
					orderRefund: {
						refundReason: this.cancelOrderNoReason
					}
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						toastService.showSuccess('取消成功');
						this.getOrderDetail(this.order.id);
					}
				});
			},

			cancelOrderNoReasonApply() {
				toastService.showLoading();
				var orderRefundGoodsListStr = [];
				this.orderDetailList((item, index) => {
					orderRefundGoodsListStr.push({
						orderDetailId: item.id,
						number: item.number
					});
				});
				https.request('/rest/member/order/applyRefund', {
					id: this.order.id,
					orderRefundGoodsListStr: orderRefundGoodsListStr,
					orderRefund: {
						refundReason: this.cancelOrderNoReason
					}
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						toastService.showSuccess('操作成功');
						this.getOrderDetail(this.order.id);
					}
				});
			},

			bindRefundProcess() {
				uni.navigateTo({
					url: '../refund/progress/progress?orderId=' + this.order.id
				});
			},

			getOrderDetail(id) {
				https.request('/rest/member/order/selectById', {
					id: id
				}).then((result) => {
					if (result.success) {
						const status = result.data.order.status;
						const createTime = result.data.order.createTime;
						result.data.order.statusText = systemStatus.statusText(status);
						result.data.order.createTime = dateHelper.fmtDate(createTime);
						result.data.order.paymentModeText = systemStatus.paymentModeText(result.data.order
							.paymentMode);
						//解析订单商品的规格
						result.data.orderDetailList.forEach((orderDetailList) => {
							let specListAnalysis = '';
							for (var key in JSON.parse(orderDetailList.specList)) {
								specListAnalysis = (specListAnalysis ? specListAnalysis + '/' :
									specListAnalysis) + JSON.parse(orderDetailList.specList)[key];
							}
							orderDetailList.specListAnalysis = specListAnalysis;
							//console.log(orderDetailList)
						});

						for (let i in this.refundStatusTexts) {
							if (result.data.order.refundStatus == this.refundStatusTexts[i].value) {
								result.data.order.refundStatusText = this.refundStatusTexts[i].name;
							}
						}
						this.order = result.data.order;
						this.orderDetailList = result.data.orderDetailList;
						this.getShopInfo(result.data.order.shopId);
					}
				});
			},

			contactBussinessTap() {
				uni.makePhoneCall({
					phoneNumber: this.order.shopContactPhone
				});
			},

			evaluateTip(e) {
				id = e.currentTarget.dataset.id;
				let shopId = e.currentTarget.dataset.shopid;
				uni.navigateTo({
					url: '../evaluate/evaluate?orderId=' + id + '&shopId=' + shopId
				});
			},

			getShopInfo(shopId) {
				https.request('/rest/shop/detail', {
					id: shopId,
					position: app.globalData.deliveryAndSelfTaking.location
				}).then((result) => {
					if (result.success && result.data) {
						this.shopInfo = result.data;
					}
				});
			}
		}
	};
</script>
<style>
	page {
		background: #f5f5f5;
		padding-bottom: 20rpx;
	}

	.status-to-greet {
		background: white;
		text-align: center;
		padding: 20rpx;
		display: flex;
		align-items: center;
		flex-direction: column;
		margin: 20rpx;
		border-radius: 15rpx;
	}

	.status-view {
		font-size: 40rpx;
		line-height: 88rpx;
		font-weight: bold;
	}

	.caozuo-buttons {
		display: flex;
		align-items: center;
		/* justify-content: center; */
		width: 100%;
		margin: 20rpx;
		flex-wrap: wrap;
	}

	.caozuo-buttons .cancel-button {
		width: 199rpx;
		margin: 0;
		margin-right: 10rpx;
	}

	.quxiao-shenqing-button {
		border: 1rpx #898989 solid;
	}

	.pinglun-btn {
		margin: 0;
		margin-right: 10rpx;
		margin-bottom: 10rpx;
	}

	.appraise-button-view {
		text-align: center;
		background: white;
	}

	.unpaid-class {
		display: flex;
		align-items: center;
		flex-direction: column;
		justify-content: center;
	}

	.comfirm-tip {
		font-size: 24rpx;
	}

	.pay-cancel-button {
		display: flex;
		justify-content: space-between;
		align-items: center;
		line-height: 60rpx;
		font-size: 26rpx;
		width: 100%;
		margin: 30rpx;
	}

	.cancel-button,
	.pay-button {
		width: 45%;
		display: flex;
		justify-content: center;
		align-items: center;
		border-radius: 15rpx;
	}

	.cancel-button {
		border: 1rpx #898989 solid;
		margin: 20rpx;
		border-radius: 15rpx;
	}

	.refund-process-view {
		margin: 20rpx;
		border-radius: 15rpx;
		background-color: white;
		padding: 20rpx;
	}

	.refund-process-detail {
		display: flex;
		align-items: center;
		justify-content: space-between;
		font-size: 30rpx;
	}

	.refund-process-right {
		display: flex;
		align-items: center;
	}

	.wenxin-tip {
		color: #979797;
		font-size: 26rpx;
	}

	.right-gray {
		width: 20rpx;
		height: auto;
		margin-left: 20rpx;
	}

	.to-greet-view {
		font-size: 38rpx;
	}

	.order-queueNo {
		font-size: 50rpx;
		font-weight: bold;
	}

	.order-details-view {
		margin: 20rpx;
		border-radius: 15rpx;
		background: white;
		padding: 0 20rpx;
	}

	.order-number-time {
		line-height: 88rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
		font-size: 28rpx;
	}

	.order-number-text {
		font-size: 24rpx;
	}

	.time-text {
		color: #898989;
		font-size: 24rpx;
	}

	.commodity-details-view {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-top: 20rpx;
		margin-bottom: 10rpx;
	}

	.commodity-name-specs {
		width: 75%;
		padding-right: 20rpx;
	}

	.commodity-name {
		font-size: 30rpx;
		line-height: 58rpx;
		font-weight: bold;
	}

	.commodity-specs {
		font-size: 24rpx;
	}

	.commodity-number-money {
		width: 25%;
		display: flex;
		justify-content: space-between;
	}

	.commodity-number {
		font-size: 28rpx;
	}

	.commodity-money {
		font-size: 30rpx;
		font-weight: bold;
	}

	.distribution-fee-view {
		display: flex;
		align-items: center;
		justify-content: space-between;
		/* line-height: 88rpx; */
		padding: 10rpx 0;
		font-size: 28rpx;
	}

	.actual-payment-view {
		display: flex;
		justify-content: space-between;
		align-items: center;
		line-height: 88rpx;
	}

	.actual-payment-number {
		font-size: 26rpx;
	}

	.actual-payment-title {
		font-size: 32rpx;
		margin-right: 10rpx;
	}

	.actual-payment-money {
		font-size: 32rpx;
		font-weight: bold;
	}

	.order-time-view {
		margin: 20rpx;
		border-radius: 15rpx;
		background: white;
		color: #898989;
		line-height: 78rpx;
		padding: 0 20rpx;
		font-size: 28rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.receiving-address-view {
		margin: 20rpx;
		border-radius: 15rpx;
		background: white;
		display: flex;
		justify-content: space-between;
		padding: 40rpx 20rpx;
	}

	.receiving-address-title {
		width: 30%;
		font-size: 28rpx;
		color: #898989;
	}

	.receiving-address-details {
		width: 80%;
		font-size: 28rpx;
	}

	.receiving-address {
		text-align: end;
	}

	.username-phone {
		text-align: end;
	}

	.fullPriceReductionText {
		position: relative;
		left: -47%;
		font-size: 30rpx;
		font-weight: bold;
		top: 0;
	}

	.fullPriceReductionClass {
		color: gainsboro;
		display: flex;
		align-items: center;
	}

	.choiceReason-radio-group {
		display: flex;
		justify-content: center;
		flex-direction: column;
	}

	.choiceReason-lable {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 20rpx 0;
		font-size: 30rpx;
	}

	.choiceReason-border {
		border-bottom: 1rpx solid #f5f5f5;
	}

	.good-choice-btn {
		width: 100%;
		text-align: center;
		padding: 20rpx 0;
		border-radius: 15rpx;
		font-size: 28rpx;
		font-weight: bold;
	}

	::-webkit-scrollbar {
		width: 0;
		height: 0;
		color: transparent;
	}

	.radio-group-label {
		/* width: 100%; */
		display: flex;
		align-items: center;
		justify-content: space-between;
		height: 88rpx;
		padding: 0 20rpx;
		font-weight: bold;
	}

	.iconwechat_pay {
		color: #09bb07;
		font-size: 40rpx;
	}

	.iconyue {
		color: #f0dcab;
		font-size: 40rpx;
	}
</style>