<template>
	<view>
		<view class="swiper-content">
			<view class="swiper-current">
				<swiper :current="currentTab" class="swiper-box" duration="300" @change="bindSlideChange"
					style="height: 80vh">
					<swiper-item class="swiper-items">
						<scroll-view @scrolltoupper="onPullDownRefresh" upper-threshold="-50" style="height: 100%"
							@scrolltolower="onReachBottom" lower-threshold="0" scroll-y class="scroll-views"
							v-if="list.length > 0">
							<view class="content-recharge">
								<radio-group class="radio-group" @change="radioChange">
									<label
										:class="'group-label ' + (!item.checked ? 'disabled-group-label' : 'active theme-bg')"
										v-for="(item, index) in list" :key="index">
										<view :class="item.isSale && item.briefDescription ? '' : 'label-view'">
											<radio :value="index" :checked="item.checked" class="radio" />
											<view class="recharge-name">{{ item.name ? item.name : '' }}</view>
											<view
												:class="'theme-color recharge-price ' + (item.checked ? 'radio-active' : '')">
												{{ item.isSale ? item.salePrice : item.price }}
												<text class="recharge-text" :decode="true">元</text>
											</view>
											<view class="sale-name" v-if="item.isSale">
												{{ item.isSale ? item.price : item.price }}元
											</view>
											<view class="brief-description" v-if="item.isSale && item.briefDescription">
												{{ item.briefDescription }}
											</view>
										</view>
									</label>
								</radio-group>
								<view class="description">
									<text>{{ list[checkValue].description }}</text>
								</view>
							</view>
						</scroll-view>
						<van-empty v-if="list.length <= 0" description="暂无数据">
							<van-button type="primary" size="small" @bindTap="goToDrink">去兑换</van-button>
						</van-empty>
					</swiper-item>
				</swiper>
			</view>
		</view>
		<view class="bottom-view" v-if="list.length > 0">
			<view class="total-view">
				<view>
					合计：
					<text class="symbol-text">￥</text>
					<text
						class="total-text">{{ list[checkValue].isSale ? list[checkValue].salePrice : list[checkValue].price }}</text>
					<text class="strike_through sale-price"
						v-if="list[checkValue].isSale">￥{{ list[checkValue].price }}</text>
				</view>
				<view class="pay-button" @tap="bindPay">立即支付</view>
			</view>
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
				currentTab: 0,
				modeList: [{
						modeId: 0,
						modeName: '充值'
					},
					{
						modeId: 1,
						modeName: '查余额'
					},
					{
						modeId: 2,
						modeName: '我的记录'
					}
				],
				checkValue: 0,
				list: [],
				winHeight: '',
				userInfo: '',
				description: '',
				button: '',
				isSale: false,
				salePrice: '',
				price: ''
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			this.getVipRechargeDenominationList();
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
			getHeight() {
				//获取用户手机系统信息
				var that = this;
				uni.getSystemInfo({
					success: function(res) {
						let winHeight = res.windowHeight; //获取高度
						//获取class为settlement-view并减去这个高度，自适应scrollview的高度
						const query = uni.createSelectorQuery();
						query.select('.bottom-view').boundingClientRect();
						query.selectViewport().scrollOffset();
						query.exec(function(res) {
							console.log(winHeight - res[0].height);
							that.setData({
								winHeight: winHeight - res[0].height - 160
							});
						});
					}
				});
			},

			getUserInfo: function(e) {
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

			getVipRechargeDenominationList: function(e) {
				toastService.showLoading('正在加载...');
				https.request('/rest/vipRechargeDenomination/list', {
					pageNo: -1,
					pageSize: 20
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						console.log(result);
						result.data.records.forEach((item, index) => {
							item.checked = false;
							if (index == 0) {
								item.checked = true;
							}
							item.description = item.description.replace('↵', '\n');
						});
						this.setData({
							list: result.data.records
						});
						this.getHeight();
					}
				});
			},
			radioChange(e) {
				console.log(e);
				let checkValue = e.detail.value;
				for (let value in this.list) {
					this.list[value].checked = false;
				}
				this.list[checkValue].checked = true;
				this.setData({
					list: this.list,
					checkValue: checkValue
				});
			},
			// 滑动切换tab
			bindSlideChange: function(e) {
				this.setData({
					currentTab: e.detail.current
				});
			},

			//点击切换
			clickTab: function(e) {
				if (this.currentTab === e.target.dataset.current) {
					return false;
				} else {
					this.setData({
						currentTab: e.target.dataset.current
					});
					// this.getHeight();
				}
			},

			bindPay(e) {
				toastService.showLoading('正在加载...', true);
				var vipRechargeDenominationId = this.list[this.checkValue].id;
				var total_fee = this.list[this.checkValue].price;
				if (this.list[this.checkValue].isSale) {
					total_fee = this.list[this.checkValue].salePrice;
				}
				var that = this;
				authService.getOpenId().then((openId) => {
					if (openId) {
						https.request('/rest/member/wxPay/toPay4Applet', {
							openid: openId,
							type: 2,
							vipRechargeDenominationId: vipRechargeDenominationId,
							total_fee: total_fee
						}).then((result) => {
							toastService.hideLoading();
							if (result.success) {
								//console.log(result)
								uni.requestPayment({
									appId: result.data.appid,
									timeStamp: result.data.timeStamp,
									nonceStr: result.data.nonceStr,
									package: result.data.package,
									signType: 'MD5',
									paySign: result.data.paySign,
									success(res) {
										// that.getUserInfo();
										uni.navigateBack(1);
										toastService.showSuccess('支付成功', true);
									},
									fail(res) {
										toastService.showError('支付失败', true);
									}
								});
							}
						});
						return;
					}
					toastService.showToast('登录用户错误，请重新登录');
				});
			},

			bindRechargeRecord(e) {
				uni.navigateTo({
					url: '../record/record'
				});
			},

			bindMoneyBalance() {
				uni.navigateTo({
					url: '../../balance/index/index'
				});
			},

			bindIntegralBalance() {
				uni.navigateTo({
					url: '../../integral/integral'
				});
			},

			onPullDownRefresh() {
				console.log('占位：函数 onPullDownRefresh 未声明');
			},

			onReachBottom() {
				console.log('占位：函数 onReachBottom 未声明');
			},

			goToDrink() {
				console.log('占位：函数 goToDrink 未声明');
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

	.member-info {
		display: flex;
		background-color: white;
		margin: 20rpx;
		border-radius: 15rpx;
		padding: 20rpx 15rpx;
		/* align-items: center; */
	}

	.headImg {
		width: 96rpx;
		height: auto;
		border-radius: 50%;
	}

	.nickname-vipEndTime {
		margin-left: 20rpx;
	}

	.nickname {
		font-size: 30rpx;
		font-weight: bold;
		height: 50rpx;
		line-height: 50rpx;
	}

	.vipEndTime {
		font-size: 22rpx;
	}

	.ptyh-class {
		background: #8f8f8f;
		padding: 2rpx 10rpx;
		color: #f5f5f5;
		border-radius: 10rpx;
	}

	.cjhy-class {
		background: linear-gradient(to right, #d09650, #ba7c3f);
		padding: 2rpx 8rpx;
		color: white;
		border-radius: 10rpx;
	}

	.renew-botton {
		border: 1rpx solid #6f4e20;
		padding: 10rpx 50rpx;
		border-radius: 50rpx;
		font-size: 28rpx;
		margin-top: 160rpx;
	}

	.content-recharge {
		background-color: white;
		/* margin: 20rpx; */
		padding: 20rpx 15rpx;
	}

	.radio-group {
		display: flex;
		/* flex-flow: row wrap;
  align-content: space-around;
  align-items: center; */
		flex-wrap: wrap;
		position: sticky;
		top: 0;
		background-color: #fff;
	}

	.group-label {
		width: 31%;
		margin: 1%;
		font-size: 26rpx;
		border-radius: 18rpx;
		text-align: center;
	}

	.disabled-group-label {
		background: #f5f5f5;
		color: #808080;
		border: none;
	}

	.radio {
		display: none;
	}

	.radio-active {
		color: white;
		background-color: inherit;
	}

	.label-view {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
		height: 100%;
	}

	.recharge-name {
		padding-top: 20rpx;
	}

	.recharge-price {
		font-size: 34rpx;
		font-weight: bold;
		padding: 10rpx;
		border-radius: 0 0 15rpx 15rpx;
	}

	.recharge-text {
		font-size: 24rpx;
		font-weight: none;
	}

	.sale-name {
		text-decoration: line-through;
		margin-bottom: 20rpx;
	}

	.brief-description {
		font-size: 24rpx;
		border-top: 1rpx solid #f8ebe3;
		padding: 10rpx;
	}

	.description {
		padding: 0 10rpx;
		font-size: 28rpx;
		/* position: sticky; */
		bottom: 0;
		/* margin-top: 20rpx; */
		background-color: white;
	}

	.swiper-content {
		margin: 20rpx 20rpx 20rpx 20rpx;
		border-radius: 15rpx;
		background-color: white;
		height: 80%;
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
		border-radius: 15rpx 15rpx 0 0;
		/* border-bottom: 1rpx solid #ededed; */
	}

	.swiper-tab-item {
		width: 50%;
	}

	.swiper-box {
		/* height: 400rpx; */
		background: #fff;
		border-radius: 15rpx;
	}

	.scroll-views {
		height: 100%;
		background: #fff;
	}

	.swiper-items {
		height: 100%;
		background: #fff;
		border-radius: 15rpx;
	}

	.swiper-current {
		background: #fff;
		border-radius: 15rpx;
	}

	.my-record-swiper {
		margin: 20rpx;
		padding: 20rpx;
		display: flex;
		justify-content: space-between;
		background-color: #f9fafe;
		border-radius: 15rpx;
	}

	.balance-swiper {
		margin: 20rpx;
		padding: 20rpx;
		background-color: #f9fafe;
		border-radius: 15rpx;
		text-align: center;
	}

	.select-balance {
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin: 30rpx 50rpx;
	}

	.lijichaxun-view {
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.lijichaxun {
		width: 30%;
		border-radius: 50rpx;
		padding: 10rpx 30rpx;
	}

	.balance-button {
		padding: 0 20rpx;
	}

	.balance-tip {
		font-size: 32rpx;
		font-weight: bold;
	}

	.iconyue {
		color: #f0dcab;
	}

	.balance-value {
		font-size: 30rpx;
		font-weight: bold;
	}

	.balance-title {
		font-size: 28rpx;
	}

	.ckwdjl-view {
		font-size: 34rpx;
		font-weight: bold;
	}

	.zdjl-czjl-view {
		color: #a2a3a7;
		font-size: 28rpx;
	}

	.ljck-button {
		display: flex;
		align-items: center;
		justify-content: center;
		padding: 5rpx 30rpx;
		border-radius: 50rpx;
		margin-top: 40rpx;
		width: 60%;
	}

	.bottom-view {
		position: fixed;
		bottom: 0;
		width: 100%;
	}

	.total-view {
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin: 20rpx 30rpx;
		border-radius: 50rpx;
		padding: 20rpx;
		font-size: 30rpx;
		background-color: #2b2f3a;
		color: white;
	}

	.pay-button {
		background: linear-gradient(to right, #eeb28d, #fbdbbf);
		padding: 10rpx 30rpx;
		border-radius: 50rpx;
		color: #652800;
		font-weight: bold;
		font-size: 30rpx;
	}

	.symbol-text {
		font-size: 24rpx;
		color: #fac9ae;
	}

	.total-text {
		font-size: 32rpx;
		color: #fac9ae;
		font-weight: bold;
		margin-right: 20rpx;
	}

	.sale-price {}
</style>