<template>
	<view>
		<scroll-view @scrolltoupper="onPullDownRefresh" upper-threshold="-100" :style="'height:' + winHeight + 'px'"
			@scrolltolower="onReachBottom" lower-threshold="20" :scroll-x="false" :scroll-y="true" class="scroll-views">
			<view class="delivery-address-list" v-if="addressList.length > 0">
				<view class="my-address">我的地址</view>
				<view class="delivery-address-item" v-for="(item, index) in addressList" :key="index">
					<view class="address-detail-info">
						<view class="address-name out_of_range one_row">
							{{ item.province }}{{ item.city }}{{ item.area }} {{ item.street }}</view>
						<view class="personal-info">
							<view class="default-address" v-if="item.isDefault">默认地址</view>
							<!-- <view class="address-tag theme-bg">家</view> -->
							<view class="phone-number">{{ item.phone }}</view>
							<view class="username-class">{{ item.realname }}</view>
						</view>
					</view>
					<van-icon name="edit" @tap="editAddressTap" :data-data="item" :data-key="index" />
				</view>
			</view>
			<van-empty description="您还没有地址信息" v-if="addressList.length <= 0">
				<van-button type="primary" size="small" class="bottom-button" @tap="goToDrink"
					custom-style="background: #004ca0;border: 1px #004ca0;">去喝一杯</van-button>
			</van-empty>
		</scroll-view>
		<navigator url="../insert/insert" class="insert-address-view theme-color self-adaption">+新增地址</navigator>
	</view>
</template>

<script>
	import GlobalConfig from '../../../utils/global-config';
	import https from '../../../utils/http';
	import authService from '../../../utils/auth';
	var toastService = require('../../../utils/toast.service');
	//获取应用实例
	const app = getApp();
	var pageNo = -1;
	var prevList = [];
	export default {
		data() {
			return {
				list: [],
				isUpdate: false,

				//是否修改
				isInsert: false,

				isDelete: false,
				winHeight: '',
				addressList: '',
				refreshKey: ''
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			pageNo = 1;
			this.getDeliveryAddressList();
		},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {
			this.selfAdaption();
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
		onPullDownRefresh: function() {
			//pageNo = 1;
			// 显示顶部刷新图标
			uni.showNavigationBarLoading();
			this.list = [];
			// 设置数组元素
			this.getDeliveryAddressList();
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
			selfAdaption() {
				var that = this;
				let winHeight = 0;
				let height = 0;
				uni.getSystemInfo({
					success: function(res) {
						//获取到用户的手机信息
						winHeight = res.windowHeight;
						// 获取需要减去的dom结构的高度信息
						setTimeout(function timeout() {
							uni.createSelectorQuery()
								.in(_this)
								.selectAll('.self-adaption')
								.boundingClientRect(function(rects) {
									console.log(".self-adaption", rects)
									rects.forEach(function(rect, index) {
										height = height + rect.height;
									});
									if (rects.length > 0) {
										_this.setData({
											winHeight: winHeight - height
										});
									}

								}).exec();
						}, 1000);
					}
				});
			},

			getDeliveryAddressList() {
				toastService.showLoading();
				https
					.request('/rest/member/deliveryAddress/list', {
						pageNo: -1,
						pageSize: 10
					})
					.then((result) => {
						toastService.hideLoading();
						if (result.success) {
							this.setData({
								addressList: result.data.records
							});
						}
					});
			},

			editAddressTap(e) {
				uni.navigateTo({
					url: '../edit/edit?detail=' + JSON.stringify(e.currentTarget.dataset.data)
				});
				this.setData({
					refreshKey: e.currentTarget.dataset.key
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
		background-color: #f7f7f7;
	}

	.scroll-views {
		height: 100%;
	}

	::-webkit-scrollbar {
		width: 0;
		height: 0;
		color: transparent;
	}

	.delivery-address-list {
		background: #fff;
		margin: 20rpx;
		border-radius: 15rpx;
		margin-bottom: 20rpx;
	}

	.my-address {
		font-size: 28rpx;
		padding: 20rpx 0 0 20rpx;
	}

	.address-name {
		font-size: 28rpx;
	}

	.delivery-address-item {
		border-bottom: 1rpx solid #ededed;
		padding: 20rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
		background: #fff;
	}

	.address-detail-info {
		width: 85%;
	}

	.personal-info {
		display: flex;
		align-items: center;
		justify-content: flex-start;
		font-size: 24rpx;
	}

	.default-address {
		padding: 5rpx;
		background: #9f9f9b;
		color: white;
		border-radius: 10rpx;
		margin-right: 8rpx;
		font-size: 20rpx;
		padding: 0 10rpx;
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
	}

	.store-name {
		font-size: 36rpx;
		line-height: 60rpx;
	}

	.business-hours,
	.store-address {
		font-size: 24rpx;
		display: flex;
		align-items: center;
		width: 75%;
	}

	.business-hours view,
	.store-address view {
		color: #9f9f9b;
		width: 100%;
	}

	.distance-class {
		font-size: 24rpx;
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
		font-size: 30rpx;
		width: 100%;
		background: #fff;
		z-index: 6;
		box-shadow: -2px 0px 5px 0.5px rgba(0, 0, 0, 0.1);
	}

	.view-line {
		height: 20rpx;
		background: #f5f5f5;
		border: none;
	}

	.iconbianji-copy {
		font-size: 50rpx;
	}
</style>