<template>
	<view>
		<view class="position-sticky" id="top_tab_list">
			<view class="swiper-content">
				<view class="swiper-tab self-adaption">
					<view class="swiper-tab-item" :data-current="index" @tap="clickTab" hover-class="hover-click-class"
						v-for="(item, index) in tabList" :key="index">
						<view :class="'swiper_table_item_view ' + (currentTab == item.modeId ? 'active' : '')"
							:data-current="index" @tap="clickTab">
							{{ item.modeName }}
						</view>
					</view>
				</view>
			</view>
			<view>
				<van-search @search="searchFun" @cancel="searchInputClear" placeholder="请输入联系人手机号/订单号"></van-search>
			</view>
			<scroll-view class="swiper-content" v-if="currentTab == 0" :scroll-x="true">
				<view class="swiper-tab self-adaption">
					<view class="swiper-tab-item two-tab" :data-current="index" @tap="clickOrderTab"
						hover-class="hover-click-class" v-for="(item, index) in shopOrderTabList" :key="index">
						<view :class="'swiper_table_item_view ' + (currentOrderTab == item.modeId ? 'active' : '')"
							style="position: relative; margin-right: 10px">
							<text class="out_of_range one_row">{{ item.modeName }}</text>
							<view class="badge_position" v-if="item.number > 0">
								// #ifdef APP-PLUS||H5
								<van-badge :content="item.number > 0?item.number:''" />
								// #endif
								// #ifdef MP-WEIXIN||MP-ALIPAY
								<text class="badge_num">{{item.number > 0?item.number:''}}</text>
								// #endif

							</view>

						</view>
					</view>
				</view>
			</scroll-view>
			<scroll-view class="swiper-content" v-else :scroll-x="true">
				<view class="swiper-tab self-adaption">
					<view class="swiper-tab-item two-tab" :data-current="index" @tap="clickOrderTab"
						hover-class="hover-click-class" v-for="(item, index) in pointsOrderTabList" :key="index">
						<view :class="'swiper_table_item_view ' + (currentOrderTab == item.modeId ? 'active' : '')"
							style="position: relative; margin-right: 10px">
							<text class="out_of_range one_row" :decode="true">{{ item.modeName }}</text>
							<view class="badge_position" v-if="item.number > 0">
								// #ifdef APP-PLUS||H5
								<van-badge :content="item.number > 0?item.number:''" />
								// #endif
								// #ifdef MP-WEIXIN||MP-ALIPAY
								<text class="badge_num">{{item.number > 0?item.number:''}}</text>
								// #endif
							</view>
						</view>
					</view>
				</view>
			</scroll-view>
		</view>
		<!-- <swiper :current="currentOrderTab" class="swiper-box" @change="bindSlideChange"
			:style="'height:' + winHeight + 'px;'" v-if="currentTab == 0">
			<swiper-item class="swiper-items" v-for="(item, index) in shopOrderTabList" :key="index"
				:style="'height:' + winHeight + 'px;'"> -->
		<block v-if="currentTab == 0">
			<scroll-view @scrolltoupper="onPullDownRefresh" upper-threshold="-50" :style="'height:' + winHeight + 'px;'"
				@scrolltolower="onReachBottom" lower-threshold="50" scroll-y class="scroll-views"
				v-if="list.length > 0">
				<view class="order-list-view" v-for="(item, index1) in list" :key="index1">
					<view class="order-item flex_between" hover-class="hover-class-public" @tap="orderDetailsTap"
						:data-id="item.id" :data-index="index">
						<image :src="item.shopLogoImg" class="commodity-image" mode="aspectFill"></image>
						<view class="right-item">
							<view class="address-status-info">
								<view class="address-view">
									<!-- <view class="order-mode theme-color-border">{{item.shoppingWay==1?"自取":"外卖"}}</view> -->
									<view class="address-info out_of_range one_row">
										{{ item.shopName }}
									</view>
								</view>
								<view class="status-view">{{ item.paymentModeText }}</view>
							</view>
							<view class="name-num-money">
								<view class="name-num flex_between">
									<text class="name-view out_of_range one_row"
										:decode="true">{{ item.description }}</text>
									<text class="num-view">x{{ item.goodsTotalQuantity }}</text>
								</view>
								<view class="money-view">￥{{ item.actualPrice }}</view>
							</view>
							<view class="tiem-view">
								{{ item.createTime }}
								<text class="status-text theme-color">{{ item.statusText }}</text>
							</view>
						</view>
					</view>

					<view class="tiem-view" v-if="item.isAllowAppraise">
						<text></text>
						<button class="evaluate-btn theme-color theme-border" size="mini" :data-id="item.id"
							:data-shopId="item.shopId" @tap.stop.prevent="evaluateTip"
							style="font-size: 22rpx; margin-top: 0">
							评价
						</button>
					</view>
				</view>
				<view class="loading_list_box" v-if="isLoading&&!isEndList">
					<van-loading />
				</view>
				<van-divider contentPosition="center" style="padding-bottom: 20px;"
					v-if="!isLoading&&isEndList">没有更多啦</van-divider>
			</scroll-view>
			<van-empty :description="'暂无'+shopOrderTabList[currentOrderTab].modeName+'订单'" v-if="list.length <= 0">
				<van-button type="primary" size="small" color="#004ca0" class="bottom-button" @tap="goToDrink">
					{{currentTab==0?'去喝一杯':'去逛逛'}}
				</van-button>
			</van-empty>
		</block>

		<!-- </swiper-item>
		</swiper> -->
		<!-- <swiper :current="currentOrderTab" class="swiper-box" @change="bindSlideChange"
			:style="'height:' + winHeight + 'px;'" v-if="currentTab == 1">
			<swiper-item class="swiper-items" v-for="(item, index) in pointsOrderTabList" :key="index"
				:style="'height:' + winHeight + 'px;'"> -->
		<block v-if="currentTab == 1">
			<scroll-view @scrolltoupper="onPullDownRefresh" upper-threshold="-50" :style="'height:' + winHeight + 'px;'"
				@scrolltolower="onReachBottom" lower-threshold="0" scroll-y class="scroll-views"
				v-if="mallList.length > 0">
				<view class="order-list-view" v-for="(item, index1) in mallList" :key="index1">
					<view class="order-item flex_between" hover-class="hover-class-public" @tap="orderMallDetailsTap"
						:data-id="item.id" :data-index="index">
						<image :src="item.firstGoodsMainImage" class="commodity-image" mode="aspectFill"></image>
						<view class="right-item">
							<view class="address-status-info">
								<view class="address-view">
									<view class="address-info out_of_range one_row">
										{{ item.contactCity }}{{ item.contactArea }}{{ item.contactStreet }}
									</view>
									<view class="address-info out_of_range one_row">
										{{ item.shopName }}
									</view>
								</view>
								<view class="status-view">{{ item.paymentModeText }}</view>
							</view>
							<view class="name-num-money">
								<view class="name-num flex_between">
									<text class="name-view out_of_range one_row"
										:decode="true">{{ item.description }}</text>
									<text class="num-view">x{{ item.goodsTotalQuantity }}</text>
								</view>
								<view class="money-view">￥{{ item.actualPrice }}</view>
							</view>
							<view class="tiem-view">
								{{ item.createTime }}
								<text class="status-text theme-color">{{ item.statusText }}</text>
							</view>
						</view>
					</view>

					<view class="tiem-view" v-if="item.isAllowAppraise">
						<text></text>
						<button class="evaluate-btn theme-color theme-border" size="mini" :data-id="item.id"
							:data-shopId="item.shopId" @tap.stop.prevent="evaluateTip"
							style="font-size: 22rpx; margin-top: 0">
							评价
						</button>
					</view>
				</view>
				<view class="loading_list_box" v-if="isMallLoading&&!isEndMallList">
					<van-loading />
				</view>
				<van-divider contentPosition="center" style="padding-bottom: 20px;"
					v-if="!isMallLoading&&isEndMallList">没有更多啦</van-divider>
			</scroll-view>
			<van-empty :description="'暂无'+shopOrderTabList[currentOrderTab].modeName+'订单'" v-if="mallList.length <= 0">
				<van-button type="primary" size="small" color="#004ca0" class="bottom-button" @tap="goToDrink">
					{{currentTab==0?'去喝一杯':'去逛逛'}}
				</van-button>
			</van-empty>
		</block>

		<!-- </swiper-item>
		</swiper> -->
	</view>
</template>

<script>
	import https from '../../../utils/http';
	import authService from '../../../utils/auth';
	import GlabalConfig from '../../../utils/global-config';
	import toastService from '../../../utils/toast.service';
	import utilHelper from '../../../utils/util';
	import dateHelper from '../../../utils/date-helper';
	import systemStatus from '../../../utils/system-status';
	var pageNo = 1;
	var pageSize = 10;
	var pageMallNo = 1;
	var pageMallSize = 10;
	//获取应用实例
	let app = null;
	export default {
		data() {
			return {
				currentTab: 0,
				orderTab: 1,
				winWidth: 0,
				winHeight: 0,
				shopOrderTabList: [{
						modeId: 0,
						modeType: 'all',
						modeName: '全部'
					},
					{
						modeId: 1,
						modeType: 'waitPayment',
						modeName: '待付款'
					},
					{
						modeId: 2,
						modeType: 'waitReceived',
						modeName: '待收货'
					},
					{
						modeId: 3,
						modeType: 'waitPickedUp',
						modeName: '待自提'
					},
					{
						modeId: 4,
						modeType: 'afterSales',
						modeName: '退款/售后'
					}
				],
				pointsOrderTabList: [{
						modeId: 0,
						modeType: 'all',
						modeName: '全部'
					},
					{
						modeId: 1,
						modeType: 'waitPayment',
						modeName: '待付款'
					},
					{
						modeId: 2,
						modeType: 'waitDelivered',
						modeName: '待发货'
					},
					{
						modeId: 3,
						modeType: 'waitReceived',
						modeName: '待收货'
					},
					{
						modeId: 4,
						modeType: 'afterSales',
						modeName: '退款/售后'
					}
				],
				list: [],
				mallList: [],
				tabList: [{
						modeId: 0,
						modeName: '外卖订单'
					},
					{
						modeId: 1,
						modeName: '商城订单'
					}
				],
				currentOrderTab: 0,
				keyWords: '',
				inputShowed: false,
				inputVal: '',
				i: 0,
				tabType: '',
				search: '',
				modeType: '',
				isOperation: false,
				modeName: '',
				isLoading: true,
				isMallLoading: true,
				isEndMallList: false,
				isEndList: false
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			console.log(options);
			app = getApp();
			pageNo = 1;

			this.currentTab = Number(options.currentTab);
			this.currentOrderTab = Number(options.currentOrderTab);
			this.modeType = options.modeType;
			this.selectTabNumber();
			this.selectMallTabNumber();

		},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {
			this.getHeight();
		},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {
			authService.checkIsLogin().then((result) => {
				if (result) {
					console.log(this.modeType);
					if (this.currentTab == 0) {
						console.log('如果pageNo乘以pageSize不等于pageSize====' + pageNo * pageSize);
						console.log('如果pageNo乘以pageSize不等于pageSize====' + pageSize);
						let no = pageNo;
						if (pageNo * pageSize != pageSize) {
							pageSize = no * pageSize;
							pageNo = 1;
							console.log('获取pageSize数' + pageSize);
						}
						this.getOrderList(this.modeType, '');
						if (this.isOperation) {
							this.getOrderDetail(this.modeType, '');
						}
					}
					if (this.currentTab == 1) {
						console.log('如果pageNo乘以pageSize不等于pageSize====' + pageMallNo * pageMallSize);
						console.log('如果pageNo乘以pageSize不等于pageSize====' + pageMallSize);
						let no = pageMallNo;
						if (pageMallNo * pageMallSize != pageMallSize) {
							pageMallSize = no * pageMallSize;
							pageMallNo = 1;
							console.log('获取pageSize数' + pageMallSize);
						}
						this.getMallOrderList(this.modeType, '');
						if (this.isOperation) {
							// this.getOrderDetail();
						}
					}
					return;
				}
				app.globalData.checkIsAuth('scope.userInfo');
			});
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
		onPullDownRefresh: function() {
			// 显示顶部刷新图标
			uni.showNavigationBarLoading();
			if (this.currentTab == 0) {
				pageNo = 1;
				pageSize = 10;
				this.isEndList = false;
				this.getOrderList(this.shopOrderTabList[this.currentOrderTab].modeType, this.keyWords);
			}
			if (this.currentTab == 1) {
				pageMallNo = 1;
				pageMallSize = 10;
				this.isEndMallList = false;
				this.getMallOrderList(this.pointsOrderTabList[this.currentOrderTab].modeType, this.keyWords);
			}
			// 隐藏导航栏加载框
			uni.hideNavigationBarLoading();
			// 停止下拉动作
			uni.stopPullDownRefresh();
		},
		/**
		 * 页面上拉触底事件的处理函数
		 */
		onReachBottom: function() {

		},
		/**
		 * 用户点击右上角分享
		 */
		onShareAppMessage: function() {},
		methods: {
			getHeight() {
				//获取用户手机系统信息
				var _this = this;
				let winHeight = 0;
				let height = 0;
				uni.getSystemInfo({
					success: function(res) {
						console.log(res)
						//获取到用户的手机信息
						winHeight = res.windowHeight;
						// 获取需要减去的dom结构的高度信息
						setTimeout(function timeout() {
							uni.createSelectorQuery()
								.in(_this)
								.selectAll('#top_tab_list')
								.boundingClientRect(function(rects) {
									console.log("#top_tab_list", rects)
									rects.forEach(function(rect, index) {
										height = height + rect.height;
									});
									if (rects.length > 0) {
										_this.winHeight = winHeight - height;
									}

								}).exec();
						}, 500);
					}
				});

			},

			// 滑动切换tab
			bindSlideChange: function(e) {
				console.log("bindSlideChange", e);
				this.currentOrderTab = e.detail.current;
				console.log("currentTab===", this.currentTab, "currentOrderTab===", this.currentOrderTab);
				if (this.currentTab == 0) {
					pageNo = 1;
					this.isEndList = false;
					this.isLoading = true;
					this.list = [];
					this.modeType = this.shopOrderTabList[e.detail.current].modeType;
					console.log(this.shopOrderTabList[e.detail.current].modeName);
					this.getOrderList(this.shopOrderTabList[e.detail.current].modeType, this.keyWords);
				}
				if (this.currentTab == 1) {
					pageMallNo = 1;
					this.isEndMallList = false;
					this.isMallLoading = true;
					this.mallList = [];
					this.modeType = this.pointsOrderTabList[e.detail.current].modeType;
					console.log(this.pointsOrderTabList[e.detail.current].modeName);
					this.getMallOrderList(this.pointsOrderTabList[e.detail.current].modeType, this.keyWords);
				}
			},

			//点击切换
			clickTab: function(e) {
				console.log("clickTab", e);
				console.log(e.target.dataset.current);
				if (this.currentTab === e.target.dataset.current) {
					return false;
				} else {
					this.currentTab = e.target.dataset.current;
					// 显示加载图标
					this.currentOrderTab = 0;
					if (this.currentTab == 0) {
						pageNo = 1;
						this.isEndList = false;
						this.isLoading = true;
						this.list = [];
						this.modeType = this.shopOrderTabList[this.currentOrderTab].modeType;
						this.getOrderList(this.shopOrderTabList[this.currentOrderTab].modeType, this.keyWords);
					}
					if (this.currentTab == 1) {
						pageMallNo = 1;
						this.isEndMallList = false;
						this.isMallLoading = true;
						this.mallList = [];
						console.log(this.pointsOrderTabList[this.currentOrderTab].modeType);
						this.modeType = this.pointsOrderTabList[this.currentOrderTab].modeType;
						this.getMallOrderList(this.pointsOrderTabList[this.currentOrderTab].modeType, this.keyWords);
					}
				}
			},

			//点击切换
			clickOrderTab: function(e) {
				console.log("clickOrderTab", e);

				if (this.currentOrderTab === e.currentTarget.dataset.current) {
					return false;
				} else {

					// 显示加载图标
					this.currentOrderTab = e.currentTarget.dataset.current;
					console.log("currentTab", this.currentTab, "currentOrderTab", this.currentOrderTab);
					if (this.currentTab === 0) {
						pageNo = 1;
						this.isEndList = false;
						this.isLoading = true;
						this.list = [];
						this.modeType = this.shopOrderTabList[this.currentOrderTab].modeType;
						console.log("this.modeType", this.modeType);
						this.getOrderList(this.shopOrderTabList[this.currentOrderTab].modeType, this.keyWords);
					}
					if (this.currentTab === 1) {
						pageMallNo = 1;
						this.isEndMallList = false;
						this.isMallLoading = true;
						this.mallList = [];
						this.modeType = this.pointsOrderTabList[this.currentOrderTab].modeType;
						console.log("this.modeType", this.modeType);
						this.getMallOrderList(this.pointsOrderTabList[this.currentOrderTab].modeType, this.keyWords);
					}
				}
			},

			orderDetailsTap(e) {
				var id = e.currentTarget.dataset.id;
				var index = e.currentTarget.dataset.index;
				this.clickId = id;
				this.clickIndex = index;
				uni.navigateTo({
					url: '../detail/detail?id=' + id
				});
			},

			orderMallDetailsTap(e) {
				var id = e.currentTarget.dataset.id;
				var index = e.currentTarget.dataset.index;
				this.clickId = id;
				this.clickIndex = index;
				console.log(this.modeType);
				uni.navigateTo({
					url: '../../internal/mall/order/detail/detail?id=' + id
				});
			},

			evaluateTip(e) {
				var id = e.currentTarget.dataset.id;
				let shopId = e.currentTarget.dataset.shopid;
				uni.navigateTo({
					url: '../evaluate/evaluate?orderId=' + id + '&shopId=' + shopId
				});
			},
			goToDrink() {
				uni.switchTab({
					url: '../../menu/index/index'
				});
			},
			getOrderList(tabType, keyWords) {
				https.request('/rest/member/order/list', {
					pageNo: pageNo,
					pageSize: pageSize,
					tabType: tabType,
					keyWords: keyWords
				}).then((result) => {
					if (result.success) {
						if (result.data.records.length < pageSize) {
							this.isEndList = true;
						}
						result.data.records.forEach((res) => {
							res.createTime = dateHelper.fmtDate(res.createTime);
							res.statusText = systemStatus.statusText(res.status);
							res.paymentModeText = systemStatus.paymentModeText(res.paymentMode);
							res.shopLogoImg = GlabalConfig.ossUrl + res.shopLogoImg;
						});
						this.list = result.data.records;
						this.isLoading = false;
					}
				});
			},
			getMallOrderList(tabType, keyWords) {
				https.request('/rest/member/pointsMall/order/list', {
					pageNo: pageMallNo,
					pageSize: pageMallSize,
					tabType: tabType,
					keyWords: keyWords
				}).then((result) => {
					console.log(result)
					if (result.success) {
						if (result.data.records.length < pageMallSize) {
							this.isEndMallList = true;
						}
						result.data.records.forEach((res) => {
							res.createTime = dateHelper.fmtDate(res.createTime);
							res.statusText = systemStatus.statusMallText(res.status);
							res.paymentModeText = systemStatus.paymentModeText(res.paymentMode);
							res.firstGoodsMainImage = GlabalConfig.ossUrl + res.firstGoodsMainImage;
						});
						this.mallList = result.data.records;
						this.isMallLoading = false;

					}
				});
			},

			searchFun: function(keyWords) {
				console.log(keyWords);
				return new Promise((resolve, reject) => {
					//判断大标签页
					if (this.currentTab == 0) {
						this.getOrderList(this.shopOrderTabList[this.currentOrderTab].modeType, keyWords
							.detail);
					}
					if (this.currentTab == 1) {
						this.getMallOrderList(this.pointsOrderTabList[this.currentOrderTab].modeType,
							keyWords.detail);
					}
					resolve(keyWords.detail);
					this.setData({
						keyWords: keyWords.detail
					});
				});
			},

			selectResult: function(e) {
				console.log('select result', e.detail);
			},

			searchInputClear(e) {
				console.log(e);
				if (this.currentTab == 0) {
					this.getOrderList(this.shopOrderTabList[this.currentOrderTab].modeType, '');
				}
				if (this.currentTab == 1) {
					this.getMallOrderList(this.pointsOrderTabList[this.currentOrderTab].modeType, '');
				}
				this.setData({
					keyWords: ''
				});
			},

			selectTabNumber() {
				https.request('/rest/member/order/selectTabNumber', {}).then((result) => {
					if (result.success) {
						this.shopOrderTabList.forEach((item, index) => {
							if (item.modeType != 'all') {
								for (let keyof in result.data) {
									if (keyof.indexOf(item.modeType) != -1) {
										console.log(keyof);
										console.log(result.data[keyof]);
										item.number = result.data[keyof];
										continue;
									}
								}
							}
						});
						console.log(this.shopOrderTabList);
						this.setData({
							shopOrderTabList: this.shopOrderTabList
						});
					}
				});
			},

			selectMallTabNumber() {
				https.request('/rest/member/pointsMall/order/selectTabNumber', {}).then((result) => {
					if (result.success) {
						this.pointsOrderTabList.forEach((item, index) => {
							if (item.modeType != 'all') {
								for (let keyof in result.data) {
									if (keyof.indexOf(item.modeType) != -1) {
										console.log(keyof);
										console.log(result.data[keyof]);
										item.number = result.data[keyof];
										continue;
									}
								}
							}
						});
						console.log(this.shopOrderTabList);
						this.setData({
							pointsOrderTabList: this.pointsOrderTabList
						});
					}
				});
			},

			getOrderDetail() {
				https.request('/rest/member/order/selectById', {
					id: id
				}).then((result) => {
					if (result.success) {
						const status = result.data.order.status;
						const createTime = result.data.order.createTime;
						result.data.order.statusText = systemStatus.statusText(status);
						result.data.order.createTime = dateHelper.formatDate(createTime);
						this.setData({
							['list[' + index + ']']: result.data.order,
							isOperation: false
						});
					}
				});
			},

			onPullDownRefresh() {
				console.log('占位：函数 onPullDownRefresh 未声明');
			},

			onReachBottom() {
				console.log('占位：函数 onReachBottom 未声明');
				if (this.currentTab == 0) {
					if (this.isEndList) {
						this.isEndList = true;
						return;
					}
					this.isLoading = true;
					pageNo = pageNo + 1;
					pageSize = 10;
					https.request('/rest/member/order/list', {
						pageNo: pageNo,
						pageSize: pageSize,
						tabType: this.pointsOrderTabList[this.currentOrderTab].modeType,
						keyWords: this.keyWords
					}).then((result) => {
						if (result.success) {
							if (result.data.records.length == 0) {
								this.isLoading = false;
								this.isEndList = true;
								pageNo = pageNo - 1;
							}
							if (result.data.records.length > 0) {
								result.data.records.forEach((res) => {
									res.createTime = dateHelper.formatDate(res.createTime);
									res.statusText = systemStatus.statusText(res.status);
									res.shopLogoImg = GlabalConfig.ossUrl + res.shopLogoImg;
									this.list.push(res);
								});
								this.list = this.list;
								this.isLoading = false;
							}

						}
					});
				}
				if (this.currentTab == 1) {
					if (this.isEndMallList) {
						this.isEndMallList = true;
						this.isMallLoading = false;
						return;
					}
					this.isMallLoading = true;
					pageMallNo = pageMallNo + 1;
					pageMallSize = 10;
					https.request('/rest/member/pointsMall/order/list', {
						pageNo: pageMallNo,
						pageSize: pageMallSize,
						tabType: this.pointsOrderTabList[this.currentOrderTab].modeType,
						keyWords: this.keyWords
					}).then((result) => {
						if (result.success) {
							if (result.data.records.length == 0) {
								this.isMallLoading = false;
								this.isEndMallList = true;
								pageMallNo = pageMallNo - 1;
							}
							if (result.data.records.length > 0) {
								result.data.records.forEach((res) => {
									res.createTime = dateHelper.formatDate(res.createTime);
									res.statusText = systemStatus.statusText(res.status);
									res.firstGoodsMainImage = GlabalConfig.ossUrl + res
										.firstGoodsMainImage;
									this.mallList.push(res);
								});
								this.mallList = this.mallList;
								this.isMallLoading = false;
							}

						}
					});
				}
			}
		}
	};
</script>
<style>
	page {
		background: #f5f5f5;
		width: 100%;
		margin: 0;
		padding-bottom: 0;
	}

	.swiper-content {
		border-radius: 15rpx;
		background-color: white;
		display: flex;
		align-items: center;
		justify-content: space-between;
		white-space: nowrap;
	}

	.swiper_table_item_view {
		margin-right: 10rpx;
	}

	.swiper-tab-item {
		width: 50%;
	}

	.two-tab {
		width: 20%;
	}

	.swiper-box {
		/* height: 400rpx; */
		border-radius: 0 0 15rpx 15rpx;
	}

	.swiper-tab {
		width: 100%;
		box-shadow: -2px 0px 5px 0.5px rgba(0, 0, 0, 0.1);
		text-align: center;
		height: 88rpx;
		line-height: 88rpx;
		display: flex;
		flex-flow: row;
		justify-content: space-between;
		background: #fff;
		z-index: 1;
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
	}

	::-webkit-scrollbar {
		width: 0;
		height: 0;
		color: transparent;
	}

	.order-list-view {
		margin: 0 20rpx;
		margin-bottom: 20rpx;
		background: white;
		border-radius: 10rpx;
	}

	.order-item {
		margin-top: 20rpx;
		padding: 20rpx;
		background: white;
		border-radius: 10rpx;
	}

	.address-view {
		display: flex;
		align-items: center;
		width: 70%;
	}

	.order-mode {
		width: 15%;
		text-align: center;
		height: 28rpx;
		font-size: 24rpx;
		padding: 2rpx;
		border-radius: 5rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		border-radius: 10rpx;
	}

	.address-info {
		font-size: 28rpx;
	}

	.address-status-info {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.status-view {
		font-size: 28rpx;
		color: #9f9f9f;
	}

	.name-num-money {
		display: flex;
		align-items: center;
		justify-content: space-between;
		line-height: 80rpx;
	}

	.name-num {
		width: 72%;
	}

	.name-view {
		font-size: 28rpx;
	}

	.num-view {
		margin-left: 40rpx;
		font-size: 30rpx;
	}

	.money-view {
		font-size: 32rpx;
		font-weight: bold;
	}

	.tiem-view {
		font-size: 24rpx;
		line-height: 48rpx;
		color: #9f9f9f;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.status-text {
		font-weight: bold;
	}

	.no-data-view {
		height: 100%;
		text-align: center;
	}

	.no-data-icon {
		width: 30%;
		height: auto;
	}

	.no-data-tip {
		font-size: 28rpx;
		color: #434343;
	}

	.go-to-btn {
		font-size: 28rpx;
		margin: 50rpx 38%;
		padding: 10rpx;
		border-radius: 10rpx;
	}

	.evaluate-btn {
		margin: 20rpx;
	}

	.tab-number {
		border-radius: 50%;
	}

	.commodity-image {
		width: 160rpx;
		height: 160rpx;
		border-radius: 10rpx;
	}

	.right-item {
		width: 75%;
	}

	.badge_position {
		position: absolute;
		right: -6px;
		top: -6px;
	}

	.badge_num {
		display: inline-block;
		box-sizing: border-box;
		background-color: #ee0a24;
		padding: 1px 3px;
		border-radius: 999px;
		min-width: 16px;
		line-height: 1.2;
		height: 1.2;
		text-align: center;
		font-size: 12px;
		color: #fff;
		position: absolute;
		right: -6px;
		top: 6px;
	}
</style>