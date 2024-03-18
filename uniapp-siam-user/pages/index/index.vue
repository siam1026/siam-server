<template>
	<view class="page">
		<swiper :indicator-dots="indicatorDots" class="carousel-swiper" :autoplay="autoplay" :interval="interval"
			:duration="duration" :indicator-active-color="afterColor" style="margin: 0;border-radius: 0;">
			<block v-for="(item, index) in carouselUrls" :key="index">
				<swiper-item class="carousel-swiper-item">
					<image :src="item.imagePath" class="carousel-image" mode="aspectFill"
						:data-imageLinkUrl="item.imageLinkUrl" @tap="carouseCommodityDetailTap" />
				</swiper-item>
			</block>
		</swiper>
		<view class="self_out_items">
			<view class="self_out_item" hover-class="hover-class-public" @tap="businessTap" data-index="0">
				<view class="self_out_items_view">
					<image src="https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/system/selfPickup.jpg"
						class="self_out_image" mode="widthFix"></image>
					<view class="self_out_item_text">
						<view class="self_out_item_title">
							<view class="self_out_title">自提</view>
							<view class="self_out_desc">下单免排队</view>
						</view>
					</view>

				</view>
			</view>
			<view class="self_out_item" hover-class="hover-class-public" @tap="businessTap" data-index="1">
				<image src="https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/system/delivery.jpg"
					class="self_out_image" mode="widthFix"></image>
				<view class="self_out_item_text">
					<view class="self_out_item_title">
						<view class="self_out_title">雪王外送</view>
						<view class="self_out_desc">甜蜜送到家</view>
					</view>

				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import GlobalConfig from '../../utils/global-config';
	import https from '../../utils/http';
	import authService from '../../utils/auth';
	import show from'../../utils/toast.service';
	import amapFile from '../../utils/gaode-libs/amap-wx';
	import * as Config from '../../utils/gaode-libs/config';
	//获取应用实例
	const app = getApp();
	export default {
		data() {
			return {
				indicatorDots: false,
				autoplay: true,
				interval: 5000,
				duration: 1000,
				//beforeColor: "white",//指示点颜色,
				afterColor: '#f1a142',
				//当前选中的指示点颜色
				previousmargin: '60px',
				//前边距
				nextmargin: '60px',
				//后边距
				opacity: 0.4,
				extClass: 'weui-dialog-ext-index',
				scrollTop: false,
				noDataTip: '../../assets/common/no-data.png',
				shopList: [],
				isActivityDialog: false,
				statusBarHeight: '',
				dialogShow: false,
				maskClosable: false,
				regeoInfo: {
					name: '',
					address: ''
				},
				dialogvisible: false,
				recommendGoodsList: '',
				carouselUrls: '',
				shopIndex: 0,
				shopAdditionalVo: {
					promotionList: []
				},
				rule: {
					name: ''
				},
				reducedDeliveryPrice: 0,
				isLoading: true
			};
		},
		onLoad: function() {
			this.setData({
				statusBarHeight: app.globalData.systemInfoSync.statusBarHeight * 2
			});
			this.getCarouselList();
			this.getRegeoInit();
		},
		onShow: function() {
			//判断是否显示新用户弹窗

		},
		onPullDownRefresh() {
			this.getCarouselList();
			this.getRegeo();
			setTimeout((outtime) => {
				// 隐藏导航栏加载框
				uni.hideNavigationBarLoading();
				// 停止下拉动作
				uni.stopPullDownRefresh();
				clearTimeout(outtime);
			}, 1000);
		},
		onHide() {
			this.setData({
				dialogShow: false,
				maskClosable: true
			});
		},
		methods: {
			getRegeo() {
				var self = this;
				var key = Config.key();
				var myAmapFun = new amapFile.AMapWX({
					key: key
				});
				myAmapFun.getRegeo({
					success: function(getRegeo) {
						console.log(getRegeo);
						if (!app.globalData.deliveryAndSelfTaking.regeoInfo) {
							self.setData({
								regeoInfo: getRegeo[0].regeocodeData.pois[0]
							});
							app.globalData.deliveryAndSelfTaking.location = getRegeo[0].longitude + ',' +
								getRegeo[0].latitude;
							app.globalData.deliveryAndSelfTaking.regeoInfo = getRegeo[0].regeocodeData.pois[0];
						} else {
							app.globalData.deliveryAndSelfTaking.location = app.globalData
								.deliveryAndSelfTaking.location;
							self.setData({
								regeoInfo: app.globalData.deliveryAndSelfTaking.regeoInfo
							});
						}
					},
					fail: function(info) {
						// wx.showModal({title:info.errMsg})
					}
				});
			},

			getRegeoInit() {
				var self = this;
				if (!app.globalData.deliveryAndSelfTaking.regeoInfo) {
					var addressInfo = {
						name: '麓谷小镇',
						address: '岳麓大道尖山路口北300米',
						location: '112.885538,28.232363'
					};
					self.setData({
						regeoInfo: addressInfo
					});
					app.globalData.deliveryAndSelfTaking.location = addressInfo.location;
					app.globalData.deliveryAndSelfTaking.regeoInfo = addressInfo;
				} else {
					app.globalData.deliveryAndSelfTaking.location = app.globalData.deliveryAndSelfTaking.location;
					self.setData({
						regeoInfo: app.globalData.deliveryAndSelfTaking.regeoInfo
					});
				}
			},

			shoppingAddressTap() {
				uni.navigateTo({
					url: `../address/replace/replace?jump_page=index`
				});
			},

			showDialog: function() {
				this.setData({
					dialogvisible: true
				});
			},

			gotoShop(e) {
				app.globalData.isRemindNewPeople().then((result) => {
					if (result) {
						this.setData({
							dialogShow: false,
							maskClosable: true
						});
					}
					uni.switchTab({
						url: '../menu/index/index'
					});
				});
			},

			businessTap(e) {
				console.log(e)
				var index = e.currentTarget.dataset.index;
				console.log("tiaozhuan", index);
				app.globalData.deliveryAndSelfTaking.selfOutActiveIndex = index;
				app.globalData.deliveryAndSelfTaking.ifIndexSwitchTab = true;
				app.globalData.deliveryAndSelfTaking.ifChooseBack = false;
				app.globalData.deliveryAndSelfTaking.ifChoosePayBack = false;
				uni.switchTab({
					url: `/pages/menu/index/index`
				});
			},

			searchBusinessTap(e) {
				console.log(this);
				uni.navigateTo({
					url: '../menu/search/search?location=' + app.globalData.deliveryAndSelfTaking.location
				});
			},

			commodityDetailTap(e) {
				uni.navigateTo({
					url: '../menu/detail/detail?id=' + e.currentTarget.dataset.id + '&shopId=' + e.currentTarget
						.dataset.shopid
				});
			},

			carouseCommodityDetailTap(e) {
				let _this = this;
				authService.checkIsLogin().then((result) => {
					console.log(result);
					if (result) {
						_this.getUserInfo(e);
						return;
					}
					app.globalData.checkIsAuth('scope.userInfo');
				});
			},

			getUserInfo: function(e) {
				https.request('/rest/member/getLoginMemberInfo', {}).then((result) => {
					if (result.success) {
						uni.navigateTo({
							url: e.currentTarget.dataset.imagelinkurl
						});
					} else {
						app.globalData.checkIsAuth('scope.userInfo');
					}
				});
			},
			bindInDevelopment() {
				app.globalData.bindInDevelopment();
			},
			getShoppingCartList() {
				this.shopList.forEach((item, index) => {
					https.request('/rest/member/shoppingCart/list', {
						shopId: item.id,
						pageNo: -1,
						pageSize: 20
					}).then((result) => {
						show.hideLoading();
						if (result.success && result.data) {
							var number = 0;
							result.data.records.forEach((cart, index) => {
								number = number + cart.number;
							});
							item.shopCartNums = number;
							this.setData({
								shopList: this.shopList
							});
						}
					});
				});
			},
			getCarouselList() {
				https.request('/rest/advertisement/list', {
					type: 1,
					pageNo: -1,
					pageSize: 20
				}).then((result) => {
					if (result.success) {
						result.data.records.forEach(function(item, index) {
							item.imagePath = GlobalConfig.ossUrl + item.imagePath;
						});
						this.setData({
							carouselUrls: result.data.records
						});
					}
				});
			},

			openConfirm() {
				this.setData({
					dialogShow: true,
					maskClosable: false
				});
			},

			close() {
				app.globalData.isRemindNewPeople().then((result) => {
					if (result) {
						this.setData({
							dialogShow: false,
							maskClosable: true
						});
					}
				});
			},

			isPromotionTap(e) {
				let shopIndex = e.currentTarget.dataset.shopindex;
				console.log(shopIndex);
				this.setData({
					isActivityDialog: true,
					shopIndex: shopIndex
				});
			}
		}
	};
</script>
<style>
	page {
		width: 100%;
	}

	.banner {
		border-radius: 15rpx;
		/* margin: 20rpx; */
		background: white;
		padding-bottom: 0rpx;
	}

	.location-address {
		display: flex;
		align-items: center;
		padding: 20rpx;
		font-weight: bold;
		font-size: 34rpx;
	}

	.search-input-views {
		padding: 20rpx;
		background: white;
	}

	.iconsousuo-copy {
		color: #c3c3c3;
	}

	.search-input-view {
		width: 100%;
		display: flex;
		align-items: center;
		justify-content: center;
		background: #f5f5f5;
		border-radius: 30rpx;
		padding: 15rpx 0;
	}

	.search-image-class {
		width: 44rpx;
		height: auto;
	}

	.search-input {
		color: #c3c3c3;
		font-size: 28rpx;
		margin-left: 10rpx;
	}

	.place-image {
		width: 10%;
		height: auto;
		padding-right: 20rpx;
	}

	.menu-swiper {
		height: 400rpx;
		position: sticky;
		top: 0;
	}

	.carousel_img {
		border-radius: 15rpx;
	}

	.index-nav-view-first {
		display: flex;
		flex-direction: column;
		margin: 30rpx 20rpx;
	}

	.index-nav-view-second {
		display: flex;
		justify-content: space-between;
		margin-bottom: 20rpx;
	}

	.index-nav-view-third {
		width: 100%;
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 20rpx;
	}

	.index-nav-view-third image {
		width: 70%;
		height: auto;
		margin-bottom: 14rpx;
		box-shadow: -2px 0px 5px 0.5px rgba(0, 0, 0, 0), 0px -2px 5px 1px rgba(0, 0, 0, 0.1), 2px 0px 5px 1px rgba(0, 0, 0, 0), 0px 2px 5px 1px rgba(0, 0, 0, 0.1);
		border-radius: 60rpx;
	}

	.index-nav-view-third text {
		font-size: 24rpx;
		color: rgb(0, 0, 0);
	}

	.carousel-swiper {
		margin: 20rpx;
		height: 558rpx;
	}

	.carousel-swiper-item {
		height: 100%;
	}

	.carousel-image {
		width: 100%;
		height: 100%;
	}

	.recommend-business-title {
		font-size: 34rpx;
		font-weight: bold;
		margin: 20rpx;
	}

	.business-item {
		display: flex;

		/* align-items: center; */
	}

	.main-image-num {
		position: relative;
		width: 100%;
	}

	.business-image {
		width: 100%;
		height: auto;
	}

	.business-info {
		width: 75%;
		margin-left: 15rpx;
		padding: 10rpx 0 20rpx 0;
		border-bottom: 1rpx solid #f5f5f5;
	}

	.business-info-flex {
		display: flex;
		align-items: center;
		justify-content: space-between;
		font-size: 24rpx;
		color: #717171;
		margin-top: 10rpx;
	}

	.business-sale {
		margin: 5rpx 0;
	}

	.business-fsize-color {
		font-size: 24rpx;
		color: #717171;
	}

	.business-name {
		font-size: 34rpx;
		font-weight: bold;
		color: black;
	}

	.business-evaluate {
		color: #ff6500;
	}

	.business-right {
		margin-left: 15rpx;
	}

	.business-discount-list {
		padding: 1rpx 6rpx;
		font-size: 20rpx;
		border-radius: 10rpx;
		margin-right: 10rpx;
	}

	.business-discount {
		width: 85%;
		padding-bottom: 1rpx;
		/* margin-bottom: 10rpx; */
		display: flex;
		align-items: center;
	}

	.settlement-view {
		position: fixed;
		z-index: 999;
		background: white;
		width: 94.8%;
		top: 0;
	}

	.radio-group {
		width: 100%;
		display: flex;
		justify-content: flex-start;
		align-items: center;
		flex-wrap: wrap;
		/* padding: 10rpx 10rpx 10rpx 0; */
		background: #fff;
		border-radius: 50rpx;
	}

	.group-label {
		margin-right: 5rpx;
		margin-bottom: 5rpx;
		font-size: 20rpx;
		border-radius: 10rpx;
		text-align: center;
		padding: 2rpx 5rpx;
		display: inline-block；;
	}

	.disabled-group-label {
		background: #f5f5f5;
		color: #808080;
		border: none;
	}

	.radio {
		display: none;
	}

	/* 商品推荐 */
	.like-items {
		display: grid;
		grid-template-columns: 1fr 1fr;
		grid-column-gap: 10px;
		padding: 0 20rpx 10rpx 20rpx;
	}

	.like-item {
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
		background: #f5f5f5;
		border-radius: 15rpx;
		margin-bottom: 20rpx;
		border: 1rpx #f5f5f5 solid;
	}

	.icon-like-class {
		width: 100%;
		height: 350rpx;
		border-radius: 15rpx 15rpx 0 0;
	}

	.item-two {
		margin: 0 3.5%;
	}

	.like-detail-view {
		width: 100%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
	}

	.fullname-class {
		margin-top: 11rpx;
		font-size: 26rpx;
		width: 90%;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.name-text {
		width: 65%;
	}

	.latelyMonthlySales {
		width: 35%;
	}

	.num {
		position: absolute;
		top: -6px;
		right: -10px;
		width: 24px;
		height: 24px;
		line-height: 24px;
		text-align: center;
		border-radius: 16px;
		font-size: 9px;
		font-weight: 700;
		color: #fff;
		background: rgb(240, 20, 20);
		box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.4);
	}

	.go-to-shop {
		padding: 0 20rpx;
		border-radius: 10rpx;
	}

	.engname-class {
		font-size: 24rpx;
		color: #ccc;
		width: 90%;
	}

	.like-money-view {
		width: 90%;
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 15rpx 0 10rpx 0;
	}

	.like-money {
		font-size: 26rpx;
		font-weight: bold;
	}

	.new-commodity-button {
		font-size: 26rpx;
	}

	.reduced-delivery-price {
		font-size: 30rpx;
		margin-top: 20rpx;
	}

	.self_out_items {
		display: grid;
		grid-template-columns: 1fr 1fr;
		grid-column-gap: 10px;
		padding: 20rpx;
		height: 175px;
	}

	.self_out_items_view {
		position: absolute;
		width: 100%;
	}

	.self_out_item {
		height: 175px;
		position: relative;
		background: #f6f6f6;
		border-radius: 20rpx;
	}

	.self_out_item_text {
		position: relative;
	}

	.self_out_image {
		position: absolute;
		width: 100%;
	}

	.self_out_item_title {
		margin-top: 32px;
		margin-left: 20rpx;
	}

	.self_out_title {
		font-size: 32rpx;
	}

	.self_out_desc {
		margin-top: 10rpx;
		font-size: 24rpx;
		color: #646566;
	}
</style>