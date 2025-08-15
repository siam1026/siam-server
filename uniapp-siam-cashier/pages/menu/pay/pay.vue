<template>
	<view class="safe-area">
		<view class="shopping-commodity-details">
			<view v-if="shopInfo">
				{{ shopInfo.shop.name }}
			</view>
			<view class="commodity-name-price-detail" v-for="(item, index) in shoppingCartList" :key="index">
				<view class="commodity-name-types">
					<view class="commodity-name">{{ item.goodsName }}</view>
					<view class="commodity-types">{{ item.restructure }}</view>
				</view>

				<view class="commodity-totalnum-price">
					<view class="commodity-totalnum">x{{ item.number }}</view>
					<view class="commodity-price">￥{{ item.totalPrice }}</view>
				</view>
			</view>
			<view class="commodity-name-price-detail">
				<view class="commodity-name-types">
					<view class="commodity-name">包装费</view>
					<view class="commodity-types commodity-delivery-tip theme-color">商品包装费</view>
				</view>
				<view class="commodity-totalnum-price">
					<view></view>
					<view class="commodity-price">￥{{ packingCharges }}</view>
				</view>
			</view>
			<view class="commodity-name-price-detail" v-if="fullPriceReductionIsHidden">
				<view class="commodity-name-types">
					<view class="commodity-name">满减</view>
				</view>
				<view class="commodity-totalnum-price">
					<view></view>
					<view class="commodity-price full-reduction-view theme-color-border">
						{{ fullReductionRuleName }}
					</view>
				</view>
			</view>
			<view class="view-line"></view>
			<view class="total-money-view">
				<text class="money-icon commodity-price">总计：</text>
				<view :class="fullPriceReductionIsHidden? 'fullPriceReductionClass' : ''">
					<text class="commodity-price">￥{{ actualPrice }}</text>
				</view>
				<text class="commodity-price" v-if="fullPriceReductionIsHidden" :decode="true">
					&nbsp;&nbsp;&nbsp;￥{{ fullPriceReduction }}
				</text>
			</view>
		</view>
		<view class="ask-for-remarks-view">
			<view class="remarks-view">
				<view class="remarks-title-input-num">
					<view class="remarks-title">特殊备注要求</view>
					<view class="remarks-input-num">{{ inputLength }}/30</view>
				</view>
				<textarea class="textarea-remarks" @input="remarksInput" maxlength="30" placeholder="输入其他特殊备注要求（可不填）"
					placeholder-class="textarea-placeholder"></textarea>
			</view>
		</view>
		<view class="safe-area go-pay-view">
			<view class="go-pay-money">
				<view class="more-pay">
					<view>还需支付</view>
				</view>
				<view class="total-money">
					￥{{ !fullPriceReductionIsHidden?actualPrice:fullPriceReduction }}
				</view>
			</view>
			<view class="flex_center go-pay theme-bg" hover-class="hover-class-public" @tap="orderInsert">
				立即下单
			</view>
		</view>
		<van-dialog use-slot :show="showPayPwdInput" :showConfirmButton="false" :showCancelButton="false" z-index='1'>
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
		<van-overlay :show="isVipDialogShow" z-index="1">
			<view class="flex_column_center content_box" style="margin-top: 50px;">
				<image
					:src="'https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/business/vip_recharge_guide.png?v=' + timestamp"
					mode="widthFix" class="now-order-image" @tap="goToRecharge"></image>
				<van-icon name="clear" @tap="close" style="font-size: 40px;color: wheat;" />
			</view>
		</van-overlay>
		// #ifdef APP-PLUS||H5
		<van-number-keyboard :show="payFocus" @blur="payFocus = false" @input="inputPwd" @delete="deletePwd" />
		// #endif
	</view>
</template>

<script>
	import https from '../../../utils/http';
	import authService from '../../../utils/auth';
	import toastService from '../../../utils/toast.service';
	import systemStatus from '../../../utils/system-status';
	import dateHelper from '../../../utils/date-helper';
	import utilHelper from '../../../utils/util';
	let app = null;
	var wxNotifyTemplates = [];
	export default {
		data() {
			return {
				time: '10:00',
				isChoose: false,
				//是否选择派送方式
				isFirstShop: false,
				//是否选择的是门店
				isFirstAddress: false,
				inputLength: 0,
				deliveryData: {},
				remarks: '',
				selfOutActiveIndex: 0,
				currentTab: 0,
				reducedDeliveryPrice: 0,
				title: '请选择支付方式',
				paymentModeIndex: 1,
				showPayPwdInput: false,
				//是否展示密码输入层
				pwdVal: '',
				//输入的密码
				payFocus: false,
				//文本框焦点
				adjustPosition: false,
				holdKeyboard: true,
				isVipDialogShow: false,
				payType: '',
				initData: '',
				isJs: false,
				timestamp: '',
				userInfo: '',
				dialogShow: false,
				shopAddress: {
					shop: {
						name: '',
						province: '',
						city: '',
						area: '',
						street: ''
					}
				},
				afterDiscount: {
					couponsName: false,
					price: ''
				},
				tipReward: '',
				isPayJson: '',
				balanceId: '',
				balanceOrderNo: '',
				balanceActualPrice: '',
				balanceOpenId: '',
				phoneNumber: 0,
				radioIndex: 0,
				maskClosable: '',
				i: '',
				buttons: '',
				extClass: '',
				initShopInfo: {
					id: ''
				},
				shopInfo: {
					shop: {
						name: ''
					}
				},
				actualPrice: 0,
				fullPriceReduction: 0,
				orderDetailList: [],
				packingCharges: 0,
				fullPriceReductionIsHidden: false,
				fullReductionRuleName: '',
				initShopInfo: {},
				storgeKey: ''
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			app = getApp();
			this.isLoading = true;
			this.shopId = options.shopId;
			if (options.storgeKey) {
				this.storgeKey = options.storgeKey;
			}
			if (options.shopId) {
				console.log("query=", 2);
				this.getShoppingCartList(options.shopId);
			}

			this.$nextTick(() => {
				setTimeout(() => {
					this.getShopInfo(options.shopId);
				}, 500);
			})
		},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {
			console.log('onReady');

		},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {

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
			getTimestamp() {
				var timestamp = dateHelper.getTimestamp();
				console.log(timestamp);
				this.timestamp = timestamp;
			},
			remarksInput(e) {
				this.inputLength = e.detail.value.length;
				this.remarks = e.detail.value;
			},
			getShopInfo(shopId) {
				https.request('/rest/shop/detail', {
					id: shopId
				}).then((result) => {
					if (result.success) {
						this.shopInfo = result.data;
						this.initShopInfo = result.data;
					}
				});
			},
			selectCommissionReward(data) {
				console.log(this);
				https.request('/rest/member/order/selectCommissionReward', {
					actualPrice: !data.fullPriceReductionIsHidden && !data.couponsIsHidden ? data
						.actualPrice : data.fullPriceReduction
				}).then((result) => {
					if (result.success) {
						this.tipReward = result.data;
					}
				});
			},
			orderInsert() {
				var data = {};
				var list = this.shoppingCartList;
				var orderDetailList = [];
				for (var key in list) {
					orderDetailList.push({
						goodsId: list[key].goodsId,
						specList: list[key].specList,
						number: list[key].number
					});
				}
				data.packingCharges = this.packingCharges;
				data.fullReductionRuleId = this.fullReductionRuleId;
				data.deliveryFee = 0;
				data.orderDetailListStr = JSON.stringify(orderDetailList);
				data.actualPrice = this.fullPriceReductionIsHidden ? this.fullPriceReduction : this.actualPrice;
				data.remark = this.remarks;
				data.shopId = this.shopId;
				if (this.fullReductionRuleId) {
					data.fullReductionRuleId = this.fullReductionRuleId;
				}
				var _this = this;
				toastService.showLoading();
				https.request('/rest/merchant/order/insert', data).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						toastService.showSuccess('下单成功', true, 1000);
						let timeout = setTimeout(() => {
							uni.navigateTo({
								url: `../index/index`
							});
							authService.setWxStorage(this.storgeKey, []);
							clearTimeout(timeout);
						}, 1000);
					}
				});
			},
			getShoppingCartList(shopId) {
				var _this = this;
				authService.getWxStorage(this.storgeKey).then((result) => {
					console.log(this.storgeKey, result);
					if (result) {
						var packingCharges = 0;
						var totalNum = 0;
						var totalPrice = 0;
						result.forEach((result, index) => {
							let specList = '';
							for (var key in JSON.parse(result.specList)) {
								specList = (specList ? specList + '/' : specList) + JSON.parse(result
									.specList)[key];
							}
							result.restructure = specList;
							totalNum = totalNum + result.number;
							result.goodsPrices = utilHelper.toFixed(Number(result.goodsPrice) * result
								.number, 2);
							totalPrice += (result.price + result.specPrice) * result
							.number; //初始化被选中的商品的总金额
							result.disable = result.goodsStatus == 1 || result.goodsStatus == 3 || result
								.goodsStatus == 4 ? true : false;
							packingCharges =
								result.goodsStatus == 1 || result.goodsStatus == 3 || result.goodsStatus ==
								4 ?
								packingCharges :
								packingCharges + result.packingCharges * result.number;
						});

						totalPrice = utilHelper.toFixed(totalPrice, 2);
						this.totalNum = totalNum;
						this.shoppingCartList = result;
						this.packingCharges = packingCharges;
						this.totalPrice = utilHelper.toFixed(totalPrice + packingCharges, 2);
						this.actualPrice = this.totalPrice;
						this.getShopCartFullReductionRule();
					}
				});
			},
			//获取满减规则
			getShopCartFullReductionRule() {
				https.request('/rest/fullReductionRule/list', {
					pageNo: -1,
					pageSize: 1,
					shopId: this.shopId
				}).then(result => {
					if (result.success) {
						let fullPriceReduction = 0,
							fullReductionRuleName = "",
							fullReductionRuleId = "",
							fullPriceReductionIsHidden = false,
							limitedPrice = 0;
						for (let i = 0; i < result.data.records.length; i++) {
							if (this.totalPrice >= result.data.records[i].limitedPrice) {
								if (result.data.records[i].limitedPrice > limitedPrice) {
									limitedPrice = result.data.records[i].limitedPrice;
									fullPriceReduction = this.totalPrice - result.data.records[i]
										.reducedPrice;
									fullReductionRuleName = result.data.records[i].name;
									fullReductionRuleId = result.data.records[i].id;
									fullPriceReductionIsHidden = true;
								}
							}
						}
						this.fullPriceReduction = utilHelper.toFixed(fullPriceReduction, 2);
						this.fullReductionRuleName = fullReductionRuleName;
						this.fullReductionRuleId = fullReductionRuleId;
						this.fullPriceReductionIsHidden = fullPriceReductionIsHidden;
					}
				})
			},
		}
	};
</script>
<style>
	page {
		background: #F9FAFC;
		padding-bottom: 120rpx;
	}

	.top-tips-view {
		background: white;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 32rpx;
		font-weight: bold;
		line-height: 100rpx;
	}

	.probably-time-view {
		margin-left: 20rpx;
	}

	.not-active {
		color: white;
	}

	.ask-for-delivery-detail {
		margin: 20rpx;
		padding: 20rpx;
		background: white;
		border-radius: 15rpx;
	}

	.ask-for-delivery-title {
		font-size: 24rpx;
		font-weight: bold;
		line-height: 60rpx;
	}

	.ask-for-delivery {
		display: flex;
	}

	.ask-for-delivery-address {
		width: 75%;
	}

	.ask-for-delivery-house {
		width: 100%;
		font-size: 28rpx;
		display: flex;
		align-items: center;
	}

	.dizhi-phone-icon {
		width: 5%;
		height: auto;
	}

	.right-class {
		width: 90%;
	}

	.ask-for-delivery-address-detail {
		font-size: 28rpx;
		height: 40rpx;
		line-height: 40rpx;
		color: #7f7f7f;
		display: flex;
		align-items: center;
	}

	.ask-for-delivery-view {
		width: 90%;
	}

	.shopping-commodity-details {
		margin: 20rpx;
		padding: 20rpx;
		background: white;
		border-radius: 15rpx;
	}

	.commodity-name-price-detail {
		padding: 15rpx 0;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.commodity-name-types {
		width: 70%;
	}

	.commodity-name {
		font-size: 28rpx;
		font-weight: bold;
	}

	.commodity-types {
		font-size: 24rpx;
	}

	.commodity-totalnum {
		font-size: 30rpx;
	}

	.commodity-price {
		font-size: 28rpx;
		font-weight: bold;
	}

	.commodity-totalnum-price {
		width: 30%;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.total-money-view {
		width: 100%;
		line-height: 40rpx;
		margin-top: 20rpx;
		display: flex;
		align-items: center;
		justify-content: flex-end;
	}

	.total-title {
		font-size: 28rpx;
	}

	.total-money {
		font-size: 32rpx;
		font-weight: bold;
		margin-left: 20rpx;
		width: 40%;
	}

	.icon-wechat-pay {
		width: 50rpx;
		height: auto;
		margin-right: 20rpx;
	}

	.pay-mode-view {
		display: flex;
		align-items: center;
		justify-content: space-between;
		background: white;
		margin: 20rpx;
		padding: 20rpx;
		font-size: 30rpx;
		border-radius: 15rpx;
	}

	.choose-pay-mode {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.coupon-mode {
		width: 80%;
	}

	.ask-for-remarks-view {
		background: white;
		margin: 20rpx;
		padding: 20rpx;
		border-radius: 15rpx;
	}

	.choose-ask-for {
		display: flex;
		align-items: center;
		justify-content: space-between;
		line-height: 100rpx;
		font-size: 30rpx;
	}

	.remarks-title-input-num {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.remarks-title {
		font-size: 30rpx;
		line-height: 100rpx;
	}

	.remarks-input-num {
		font-size: 28rpx;
	}

	/* .remarks-view {
  padding-bottom: 120rpx;
} */

	.textarea-remarks {
		width: 94%;
		padding: 20rpx;
		background: #f5f5f5;
		border-radius: 10rpx;
		height: 120rpx;
		font-size: 28rpx;
		z-index: 0;
	}

	.iconwechat_pay {
		color: #09bb07;
		font-size: 40rpx;
	}

	.iconyue {
		color: #f0dcab;
		font-size: 40rpx;
	}

	.go-pay-view {
		position: fixed;
		bottom: 0;
		width: 100%;
		height: 100rpx;
		line-height: 100rpx;
		display: flex;
		align-items: center;
		background: white;
		box-shadow: -2px 0px 5px 0.5px rgba(0, 0, 0, 0.1);
		font-size: 36rpx;
		z-index: 1;
		border-top: 0.5rpx solid #f5f5f5;
	}

	.go-pay-money {
		padding: 0 20rpx;
		width: 70%;
		background: white;
		display: flex;
	}

	.more-pay {
		font-size: 32rpx;
	}

	.go-pay {
		width: 30%;
		color: white;
		text-align: center;
		font-size: 32rpx;
		height: 100rpx;
		line-height: 100rpx;
	}

	cover-view {
		line-height: 100rpx;
	}

	/*radio 选项框大小  */

	radio .wx-radio-input {
		width: 35rpx;
		height: 35rpx;
		border-radius: 50%;
		border-color: #ededed;
	}

	.ask-for-label {
		display: flex;
		align-items: center;
	}

	.ask-for-radio-group {
		width: 60%;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.ra-group-label {
		display: flex;
		align-items: center;
		padding: 10rpx;
	}

	.full-reduction-view {
		font-size: 24rpx;
		font-weight: bold;
		padding: 0 10rpx;
		margin: 0 5rpx;
		border-radius: 10rpx;
	}

	.fullPriceReductionClass {
		color: gainsboro;
		text-decoration: line-through;
	}

	.pay-mode-title {
		width: 20%;
		font-size: 28rpx;
	}

	.after-discount {
		font-size: 28rpx;
		font-weight: bold;
		text-align: end;
		width: 100%;
		font-size: 28rpx;
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
	}

	.swiper-items {
		height: 100%;
	}

	.scroll-views {
		height: 100%;
		background: #fff;
	}

	.bindSlideChange {
		padding-top: 20rpx;
	}

	.space-between-class {
		width: 100%;
	}

	.bindSlideChange-class {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.currentTab1-view {
		margin-bottom: 20rpx;
	}

	.currentTab1-title {
		font-size: 26rpx;
		color: #7f7f7f;
	}

	.time-phone-view {
		border-top: 1rpx solid #f5f5f5;
		padding: 20rpx 0;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.time-phone-item {
		font-size: 24rpx;
	}

	.time-phone-content {
		width: 100%;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.time-phone-title {
		color: #7f7f7f;
	}

	.deliveryAddress-info {
		margin-bottom: 20rpx;
	}

	.dayue-time {
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin-top: 20rpx;
		font-size: 26rpx;
	}

	.dayue-time-songda-view {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.ask-for-delivery-house-view {
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin-bottom: 20rpx;
	}

	.payment-checked {
		background: linear-gradient(to right, #f2e1b5, #ebd198);
	}

	.payment-not-checked {
		background: #f0f0f0;
	}

	.radio-label-payment {
		padding: 8rpx 10rpx;
		font-size: 26rpx;
		border-radius: 18rpx;
		margin: 5rpx 0;
	}

	.pay_radio {
		display: none;
	}

	.radio-label-payment.flex_center.payment-checked .iconyue {
		color: black;
	}

	.actionItem__desc {
		font-size: 22rpx;
		color: red;
	}

	.current-tips {
		padding: 20rpx;
		font-size: 38rpx;
		border-radius: 10rpx;
		font-weight: bold;
		color: red;
	}

	.top-detail {
		margin: 20rpx;
		background-color: white;
		border-radius: 10rpx;
	}

	.weui-dialog.extClassIsVip {
		background-color: rgba(255, 255, 255, 0);
	}

	.weui-dialog.extClassIsVip .weui-dialog__bd {
		padding: 0;
	}

	.close-view-class .iconfont.icon55 {
		font-size: 62rpx;
		color: gainsboro;
	}

	.self_out_button {
		background: #ededed;
		border-radius: 50rpx;
		height: 2rem;
		line-height: 2rem;
		width: 30%;
		padding: 5rpx;
	}

	.self_button {
		font-size: 24rpx;
		width: 50%;
		text-align: center;
		padding: 0 10rpx;
	}

	.self_out_active {
		color: white;
		padding: 0 10rpx;
		border-radius: 50rpx;
	}

	.shop_info_view {
		font-size: 32rpx;
		width: 70%;
	}

	.distance_m {
		font-size: 28rpx;
		color: var(--tab-text-color, #646566);
		margin-top: 10rpx;
	}
</style>