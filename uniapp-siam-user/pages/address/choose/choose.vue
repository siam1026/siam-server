<template>
	<view>
		<view class="swiper-tab self-adaption" v-if="pageType != 'orderDetail'">
			<view class="swiper-tab-item" :data-current="index" @tap="clickTab" hover-class="hover-click-class"
				v-for="(item, index) in modeList" :key="index">
				<view :class="'swiper_table_item_view ' + (selfOutActiveIndex == item.modeId ? 'active' : '')"
					:data-current="index" @tap="clickTab">
					{{ item.modeName }}
				</view>
			</view>
		</view>
		<swiper :current="selfOutActiveIndex" class="swiper-box" duration="300"
			:style="'height:' + (winHeight - 5) + 'px'" @change="bindSlideChange">
			<swiper-item class="swiper-items" v-if="pageType != 'orderDetail'">
				<scroll-view @scrolltoupper="onPullDownRefresh" upper-threshold="-50" @scrolltolower="onReachBottom"
					lower-threshold="0" scroll-y class="scroll-views">
					<view class="section" @tap="getRegeoAddress">
						<input :placeholder="'搜索门店' + scope.userLocation" disabled="disabled" />
					</view>
					<view class="self-taking-list">
						<view :class="'self-taking-item '+(shopId == item.id?'active-bg':'')" @tap="confirmShopChoice"
							hover-class="hover-class-public" v-for="(item, index) in shopList" :key="index"
							:data-index="index" :data-id="item.id">
							<view class="flex_between store-name ">
								<view class="flex_between">
									<view class="out_of_range one_row">{{ item.name }}</view>
									<view class="theme-bg active-address" v-if="shopId == item.id">当前选择
									</view>
								</view>
								<view></view>
							</view>

							<view class="store-address-details">
								<view class="store-address">
									<van-icon name="location-o" />
									<view class="out_of_range one_row">
										距您{{item.shopAdditionalVo.deliveryDistanceText}}|{{ item.province }}{{ item.city }}{{ item.area }}{{ item.street }}
									</view>
								</view>
							</view>
							<view class="business-hours-distance">
								<view class="business-hours">
									<van-icon name="clock-o" />
									<view>
										{{ item.startTime }}-{{ item.endTime }}
									</view>
								</view>
							</view>
						</view>
					</view>
					<van-empty v-if="!isLoading&&shopList.length <= 0" description="该区域外送功能即将开通">
					</van-empty>
					<view class="loading_box" v-if="isLoading&&shopList.length==0">
						<van-loading custom-class="loading_box_class" vertical>加载中...</van-loading>
					</view>
				</scroll-view>
			</swiper-item>
			<swiper-item class="swiper-items" v-if="selfOutActiveIndex == 1">
				<scroll-view @scrolltoupper="onPullDownRefresh" upper-threshold="-50" v-if="addressList.length > 0"
					@scrolltolower="onReachBottom" lower-threshold="0" :scroll-x="false" :scroll-y="true"
					class="scroll-views">
					<view class="delivery-address-list">
						<view class="my-address">我的收货地址</view>
						<view class="delivery-address-item" hover-class="hover-class-public"
							v-for="(item, index) in addressList" :key="index">
							<view class="address-items"
								@tap="parseEventDynamicCode($event, pageType == 'orderDetail' ? 'confirmChoiceDelivery' : 'confirmChoice')"
								:data-index="index">
								<view class="flex_between address-name">
									<view class="flex_between address-name-view">
										<view class="address-name-text out_of_range two_row"
											:style="'width:'+(addressNameWidth>260?260:addressNameWidth)+'px;'">
											{{ item.province }}{{ item.city }}{{ item.area }}{{ item.street }}
										</view>
										<view class="theme-bg active-address" v-if="chooseId == item.id">
											当前选择</view>
										<view v-else></view>
									</view>
									<view style="text-align: end;">
										<van-icon name="edit" @tap="editAddressTap" :data-data="item"
											:data-key="index" />
									</view>
								</view>
								<view class="personal-info">
									<view class="default-address" v-if="item.isDefault">默认地址</view>
									<!-- <view class="address-tag theme-bg">家</view> -->
									<view class="phone-number">{{ item.phone }}</view>
									<view class="username-class">{{ item.realname }}</view>
								</view>
							</view>
						</view>
					</view>
				</scroll-view>
				<view class="loading_box" v-if="isLoading&&addressList.length==0">
					<van-loading custom-class="loading_box_class" vertical>加载中...</van-loading>
				</view>
				<van-empty v-if="addressList.length <= 0&&!isLoading" description="您还没有地址信息">
					<van-button round type="primary" size="small" color="#004ca0" class="bottom-button" v-if="addressList.length > 0"
						@bindTap="goToDrink">去喝一杯</van-button>
				</van-empty>
			</swiper-item>

		</swiper>

		<navigator url="../insert/insert" class="insert-address-view theme-color safe-area"
			v-if="selfOutActiveIndex == 1">+新增地址</navigator>

		<van-popup :show="openRegionDialog" round custom-style="height: 50%;" position="bottom">
			<view class="content">
				<van-area :area-list="areaList" :value="areaValue" :columns-num="2" title="选择城市" @cancel="close"
					@change="bindRegionChange" @confirm="bindRegionComfirm" />
			</view>
		</van-popup>
	</view>

</template>

<script>
	import https from '../../../utils/http';
	import GlobalConfig from '../../../utils/global-config';
	import authService from '../../../utils/auth';
	import amapFile from '../../../utils/gaode-libs/amap-wx';
	import * as Config from '../../../utils/gaode-libs/config';
	import toastService from '../../../utils/toast.service';
	import utilHelper from '../../../utils/util';
	import dateHelper from '../../../utils/date-helper';
	import {
		areaList
	} from '@vant/area-data';

	//获取应用实例
	let app = null;
	var heightList = [];
	export default {
		data() {
			return {
				addressList: [],
				shopAddressList: [],
				modeList: [{
						modeName: '门店自提',
						modeId: 0
					},
					{
						modeName: '送货上门',
						modeId: 1
					}
				],
				isInsert: false,
				reducedDeliveryPrice: '',
				selfOutActiveIndex: '',
				shopId: '',
				pageType: '',
				chooseId: '',
				orderNo: '',
				orderId: '',
				isOperation: false,
				deliveryAndSelfTaking: '',
				shopAddress: {
					name: '',
					isBusiness: false,
					isBusinessText: '',
					province: '',
					city: '',
					area: '',
					street: ''
				},
				data: '',
				oldAddressId: '',
				time: '',
				isPreviousPage: false,
				isJs: false,
				refreshKey: '',
				winHeight: '',
				shopList: [],
				isLoading: true,
				openRegionDialog: false,
				userLocation: '',
				tips: [],
				areaList: areaList,
				region: '',
				areaValue: '',
				scope: {
					userLocation: ''
				},
				keywords: ''
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			//获取应用实例
			app = getApp();
			console.log(options);
			var location = app.globalData.deliveryAndSelfTaking.location;
			var selfOutActiveIndex = app.globalData.deliveryAndSelfTaking.selfOutActiveIndex;
			var chooseIndex = app.globalData.deliveryAndSelfTaking.chooseIndex;
			var actvieIndex = app.globalData.deliveryAndSelfTaking.actvieIndex;
			var pageType = app.globalData.deliveryAndSelfTaking.pageType;
			var chooseId = app.globalData.deliveryAndSelfTaking.chooseId;
			var shopId = app.globalData.deliveryAndSelfTaking.shopId;
			this.selfOutActiveIndex = chooseIndex;
			this.chooseIndex = actvieIndex;
			this.pageType = pageType;
			this.chooseId = chooseId;
			this.shopId = shopId;
			this.location = location;
			this.getDeliveryAddressList();
			this.getShopList(location);
		},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {
			//自适应手机高度
			this.selfAdaption();
		},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {
			this.getSettingInfo();
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
		onReachBottom: function() {
			// // 显示加载图标
			// toastService.showLoading('正在加载...');
			// if (this.data.currentTab == 1) {
			//   // 页数+1
			//   pageNo = pageNo + 1;
			//   this.getDeliveryAddressList();
			//   return
			// }
			// shopPageNo = shopPageNo + 1;
			// this.getShopAddressList();
		},
		/**
		 * 用户点击右上角分享
		 */
		onShareAppMessage: function() {},
		methods: {
			getShopList(location) {
				if (location) {
					var _this = this;
					return new Promise((fulfill, reject) => {
						https.request('/rest/shop/list', {
							pageNo: -1,
							pageSize: 5,
							position: location
						}).then((result) => {
							if (result.success) {
								result.data.records.forEach((item, index) => {
									item.shopLogoImg = GlobalConfig.ossUrl + item.shopLogoImg;
									item.isfeeData = false;
									if (item.reducedDeliveryPrice > 0) {
										if (item.reducedDeliveryPrice >= item.shopAdditionalVo
											.deliveryFee) {
											item.feeData = 0;
											item.isfeeData = true;
										} else {
											item.feeData = item.shopAdditionalVo.deliveryFee - item
												.reducedDeliveryPrice;
											item.isfeeData = true;
										}
									}
								});
								this.shopList = result.data.records;
								this.isLoading = false;
								fulfill(result.data);
								authService.checkIsLogin().then((result) => {
									if (!result) {
										return;
									}
								});
							}
						});
					});
				}
			},
			// 滑动切换tab
			bindSlideChange: function(e) {
				var that = this;
				that.setData({
					selfOutActiveIndex: e.detail.current
				});
				//自适应手机高度
				that.selfAdaption();
			},

			//点击切换
			clickTab: function(e) {
				var that = this;
				if (that.selfOutActiveIndex === e.target.dataset.current) {
					return false;
				} else {
					that.setData({
						selfOutActiveIndex: e.target.dataset.current
					});
					//自适应手机高度
					that.selfAdaption();
				}
			},

			getHasShopDelivery(deliveryAddressId) {
				return new Promise((fulfil, reject) => {
					https.request('/common/selectHasShopDelivery', {
						deliveryAddressId: deliveryAddressId
					}).then((result) => {
						if (result.success) {
							fulfil(result.data);
						}
					});
				});
			},

			// 订单详情我要配送方法
			confirmChoiceDelivery(e) {
				var key = e.currentTarget.dataset.index;
				//直接调用上一个页面的 setData() 方法，把数据存到上一个页面中去
				var shopAddress = this.addressList[key];

				https.request('/rest/common/selectDeliveryFeee', {
					deliveryAddressId: shopAddress.id,
					shopId: this.shopId
				}).then((result) => {
					if (result.success) {
						var that = this;
						var pages = getCurrentPages();
						var prevPage = pages[pages.length - 2]; //上一个页面
						var prevToPage = pages[pages.length - 3]; //上一个页面
						var payPrice = result.data - Number(this.reducedDeliveryPrice);
						if (payPrice > 0) {
							toastService.showModal('', '确认支付' + payPrice + '元配送费吗？', function() {
								authService.getOpenId().then((openId) => {
									if (openId) {
										toastService.showLoading();
										https.request('/rest/member/wxPay/toPay4Applet', {
											openid: openId,
											type: 3,
											out_trade_no: that.orderNo,
											total_fee: payPrice,
											deliveryAddressId: shopAddress.id,
											shopId: that.shopId
										}).then((result) => {
											if (result.success) {
												uni.requestPayment({
													appId: result.data.appid,
													timeStamp: result.data
														.timeStamp,
													nonceStr: result.data.nonceStr,
													package: result.data.package,
													signType: 'MD5',
													paySign: result.data.paySign,
													success(res) {
														toastService.showSuccess(
															'支付成功', true);
														let timeout = setTimeout(
														() => {
																prevPage
																	.getOrderDetail(
																		that
																		.orderId
																		);
																prevToPage
																	.setData({
																		isOperation: true
																	});
																clearTimeout(
																	timeout
																	);
																uni.navigateBack(
																	1);
															}, 1000);
													},
													fail(res) {
														toastService.showError(
															'支付失败', true);
													}
												});
											}
										});
										return;
									}
									toastService.showToast('登录用户错误，请重新登录');
								});
							}, null);
						}
					}
				});
				// })
			},
			//选择用户地址
			confirmChoice(e) {
				console.log("选择用户地址",e);
				var key = e.currentTarget.dataset.index;
				//直接调用上一个页面的 setData() 方法，把数据存到上一个页面中去
				var shopAddress = this.addressList[key];
				//直接调用上一个页面的 setData() 方法，把数据存到上一个页面中去
				for (let index in app.globalData.deliveryAndSelfTaking.modeList) {
					app.globalData.deliveryAndSelfTaking.modeList[index].checked = false;
					if (index == this.selfOutActiveIndex) {
						app.globalData.deliveryAndSelfTaking.modeList[index].checked = true;
					}
				}
				var _this = this;
				let pages = getCurrentPages();
				let prevPage = pages[pages.length - 2]; //上一个页面
				console.log(prevPage);
				var location = shopAddress.longitude + "," + shopAddress.latitude;
				toastService.showLoading("加载中...");
				//如果重复选择地址则不进行任何操作
				if (_this.chooseId == shopAddress.id) {
					return;
				}
				this.confirmChoiceShopList(location).then(result => {
					console.log("查询用户地址配送费=", result);
					var shopInfo = result.records[0];
					var addressid = shopAddress.id;
					var shopId = shopInfo.id;
					this.confirmSelectDeliveryFee(addressid, shopId).then(feeData => {
						feeData = app.globalData.deliveryAndSelfTaking.reducedDeliveryTotalPrice ? app
							.globalData
							.deliveryAndSelfTaking.reducedDeliveryTotalPrice : 0;
						toastService.hideLoading();
						
						app.globalData.deliveryAndSelfTaking.selfOutActiveIndex = 1;
						app.globalData.deliveryAndSelfTaking.deliveryAddress = shopAddress;
						app.globalData.deliveryAndSelfTaking.ifIndexSwitchTab = true;
						app.globalData.deliveryAndSelfTaking.ifChooseBack = true;
						app.globalData.deliveryAndSelfTaking.reducedDeliveryTotalPrice = feeData;

						prevPage.$vm.deliveryAndSelfTaking = app.globalData.deliveryAndSelfTaking;
						prevPage.$vm.selfOutActiveIndex = 1;
						prevPage.$vm.activeTab = 0;
						prevPage.$vm.activeLeftTab = 0;
						prevPage.$vm.topArr = [];
						prevPage.$vm.menuList = [];
						prevPage.$vm.isMainScroll = false;
						prevPage.$vm.scrollInto = 'into0';
						prevPage.$vm.isLoading = true;
						
						uni.navigateBack(1);
						_this.setData({
							itemId: _this.addressList[key].id
						});
						if (prevPage.$page.fullPath.indexOf('pages/menu/pay/pay') < 0) {
							prevPage.$vm.getShopList();
						}
						if (prevPage.$page.fullPath.indexOf('pages/menu/pay/pay') > 0) {
							app.globalData.deliveryAndSelfTaking.ifChoosePayBack = true;
						}
					})

				});
			},
			confirmSelectDeliveryFee(addressid, shopId) {
				var _this = this;
				return new Promise((fulfill, reject) => {
					https.request('/rest/common/selectDeliveryFee', {
						deliveryAddressId: addressid,
						shopId: shopId
					}).then((result) => {
						console.log(result);
						if (result.success) {
							fulfill(result.data);
						}
					});
				});
			},
			confirmChoiceShopList(location) {
				if (location) {
					var _this = this;
					return new Promise((fulfill, reject) => {
						https.request('/rest/shop/list', {
							pageNo: -1,
							pageSize: 5,
							position: location
						}).then((result) => {
							if (result.success) {
								if (result.data.records.length == 0) {
									toastService.showModal('', '该区域外送功能即将开通',
										function() {})
									return
								};
								fulfill(result.data);
							}
						});
					});
				}
			},
			editAddressTap(e) {
				uni.navigateTo({
					url: '../edit/edit?detail=' + JSON.stringify(e.currentTarget.dataset.data)
				});
				this.setData({
					refreshKey: e.currentTarget.dataset.key
				});
			},

			selfAdaption() {
				var _this = this;
				let winHeight = 0;
				let winWidth = 0;
				let height = 0;
				uni.getSystemInfo({
					success: function(res) {
						console.log(res)
						//获取到用户的手机信息
						winHeight = res.windowHeight;
						// 获取需要减去的dom结构的高度信息
						uni.createSelectorQuery()
							.in(_this)
							.selectAll('.self-adaption')
							.boundingClientRect(function(rects) {
								console.log(rects)
								rects.forEach(function(rect, index) {
									height = height + rect.height;
								});
								if (rects.length > 0) {
									_this.setData({
										winHeight: winHeight - height
									});
								}

							})
							.exec();
						var width = 0;
						setTimeout(function timeout() {
							uni.createSelectorQuery()
								.in(_this)
								.selectAll('.address-name-text')
								.boundingClientRect(function(rects) {
									console.log(".address-name-text", rects);
									var addressNameWidth = 0;
									rects.forEach(function(rect, index) {
										if (addressNameWidth < rect.width) {
											addressNameWidth = rect.width;
										}
									});
									if (rects.length > 0) {
										_this.setData({
											"addressNameWidth": addressNameWidth
										});
										console.log(addressNameWidth);
									}

								}).exec();
						}, 500);
					}
				});
			},

			getShopAddressList() {
				https.request('/rest/shop/detail', {
					id: this.shopId,
					position: app.globalData.deliveryAndSelfTaking.location
				}).then((result) => {
					if (result.success) {
						this.setData({
							shopAddress: result.data
						});
					}
				});
			},

			getDeliveryAddressList() {
				https.request('/rest/member/deliveryAddress/list', {
					pageNo: -1,
					pageSize: 10
				}).then((result) => {
					if (result.success) {
						this.addressList = result.data.records;
						this.isLoading = false;
					}
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
			},

			confirmShopChoice(e) {
				console.log('占位：函数 confirmShopChoice 未声明', e);
				toastService.showLoading();
				var _this = this;
				var index = e.currentTarget.dataset.index;
				let pages = getCurrentPages();
				let prevPage = pages[pages.length - 2]; //上一个页面
				console.log(prevPage);
				prevPage.$vm.getShopInfo(this.shopList[index]);

				prevPage.$vm.selfOutActiveIndex = 0;
				prevPage.$vm.deliveryAndSelfTaking = {};
				prevPage.$vm.activeTab = 0;
				prevPage.$vm.activeLeftTab = 0;
				prevPage.$vm.topArr = [];
				prevPage.$vm.menuList = [];
				prevPage.$vm.isMainScroll = false;
				prevPage.$vm.scrollInto = 'into0';
				prevPage.$vm.isLoading = true;
				//清空配送地址信息
				app.globalData.deliveryAndSelfTaking.radioIndex = _this.selfOutActiveIndex;
				app.globalData.deliveryAndSelfTaking.selfOutActiveIndex = _this.selfOutActiveIndex;
				app.globalData.deliveryAndSelfTaking.deliveryAddress = null;
				app.globalData.deliveryAndSelfTaking.reducedDeliveryTotalPrice = 0;
				app.globalData.deliveryAndSelfTaking.isReducedDeliveryPrice = 0;
				app.globalData.deliveryAndSelfTaking.feeData = 0;
				app.globalData.deliveryAndSelfTaking.ifIndexSwitchTab = true;
				app.globalData.deliveryAndSelfTaking.ifChooseBack = true;
				if (this.pageType == 'pay') {
					app.globalData.deliveryAndSelfTaking.ifChoosePayBack = true;
				}
				toastService.hideLoading();
				app.globalData.deliveryAndSelfTaking.orderDetail.initShopInfo = this.shopList[index];
				uni.navigateBack(1);
				//toastService.showLoading();

				// this.$nextTick(() => {
				// 	setTimeout(() => {
				// 		console.log("_this.pageType_this.pageType===>", _this.pageType)
				// 		if (_this.pageType == 1) {
				// 			prevPage.$vm.getElementTop();
				// 		}
				// 		toastService.hideLoading();
				// 		uni.navigateBack(1);
				// 	}, 500);
				// });
			},
			getSettingInfo() {
				let _this = this;
				uni.getSetting({
					success(res) {
						console.log(res);
						_this.setData({
							userLocation: res.authSetting['scope.userLocation'],
							tips: []
						});
					}
				});
			},
			openSettingInfo() {
				let _this = this;
				uni.openSetting({
					success(res) {
						_this.getSettingInfo();
					}
				});
			},
			getRegeoAddress(e) {
				uni.navigateTo({
					url: '../../address/search/search'
				});
			},
		}
	};
</script>
<style>
	page {
		background: #fff;
		width: 100%;
		margin: 0;
		padding-bottom: 0;
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
		width: 100%;
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

	::-webkit-scrollbar {
		width: 0;
		height: 0;
		color: transparent;
	}

	.delivery-address-list {
		height: 100%;
	}

	.my-address {
		font-size: 28rpx;
		padding: 20rpx;
	}

	.delivery-address-choice {
		margin-right: 20rpx;
		font-size: 30rpx;
	}

	.address-name {
		width: 100%;
		font-size: 30rpx;
		margin: 5px 0 5px 0;
		align-items: inherit;
	}

	.address-name-view {
		align-items: flex-start;
		justify-content: flex-start;
	}

	.address-name-text {
		width: 80%;
	}

	.address-items {
		width: 100%;
	}

	.delivery-address-item {
		border-bottom: 1rpx solid #ededed;
		padding: 10rpx 20rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.personal-info {
		display: flex;
		align-items: center;
		justify-content: flex-start;
		font-size: 24rpx;
	}

	.default-address {
		padding: 5rpx 10rpx;
		background: #9f9f9b;
		color: white;
		border-radius: 10rpx;
		margin-right: 8rpx;
	}

	.active-address {
		padding: 5rpx 10rpx;
		color: white;
		border-radius: 15rpx 15rpx 15rpx 0;
		font-size: 22rpx;
		margin-left: 15rpx;
	}

	.address-tag {
		padding: 5rpx 8rpx;
		color: white;
		border-radius: 10rpx;
		margin-right: 8rpx;
	}

	.phone-number,
	.username-class {
		color: #9f9f9b;
		margin-right: 8rpx;
	}

	.self-taking-list {
		border-bottom: 1rpx solid #ededed;
	}

	.self-taking-item {
		padding: 20rpx 20rpx 10rpx 20rpx;
	}

	.business-hours-distance,
	.store-address-details {
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin: 10rpx 0;
		font-size: 28rpx;
	}

	.store-name {
		font-size: 32rpx;
	}

	.business-hours,
	.store-address {
		font-size: 24rpx;
		display: flex;
		align-items: center;
	}

	.business-hours view,
	.store-address view {
		color: #9f9f9b;
		width: 100%;
	}

	.distance-class {
		font-size: 24rpx;
	}

	.view-details {
		font-size: 24rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.icon-detail {
		width: 14rpx;
		height: auto;
		margin-left: 10rpx;
	}

	.insert-address-view {
		position: fixed;
		bottom: 0;
		height: 100rpx;
		line-height: 100rpx;
		text-align: center;
		font-size: 32rpx;
		width: 100%;
		background: #fff;
		z-index: 6;
		box-shadow: -2px 0px 5px 0.5px rgba(0, 0, 0, 0.1);
	}

	.edit-icon {
		width: 7%;
		height: auto;
	}

	.iconbianji-copy {
		font-size: 50rpx;
	}

	.active-bg {
		background-color: #ededed;
	}

	.section {
		background: white;
		padding: 10rpx 20rpx;
		display: flex;
		align-items: center;
		border-bottom: 0.5rpx solid #ededed;
	}

	.section input {
		width: 100%;
		margin: 5rpx 10rpx;
		border: 1px solid #c3c3c3;
		height: 30px;
		border-radius: 3px;
		padding: 0 5px;
	}

	picker {
		width: 30%;
	}

	.picker {
		font-size: 28rpx;
		width: 20%;
	}
</style>