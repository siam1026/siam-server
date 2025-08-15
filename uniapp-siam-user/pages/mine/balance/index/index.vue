<template>
	<view>
		<view class="balance-views theme-bg">
			<view class="balance-text">当前余额（元）</view>
			<view class="balance-view">
				{{ userInfo.balance }}
			</view>
			<view class="balance-detail">
				<view class="balance-detail-info">
					<view class="balance-quota">￥{{ userInfo.totalBalance }}</view>
					<view class="balance-title">累计余额</view>
				</view>
				<view class="balance-detail-info">
					<view class="balance-quota">￥{{ userInfo.totalConsumeBalance }}</view>
					<view class="balance-title">累计消费</view>
				</view>
				<view class="balance-detail-info">
					<view class="balance-quota">￥{{ userInfo.totayGainBalance }}</view>
					<view class="balance-title">今日获得</view>
				</view>
			</view>
		</view>
		<swiper :current="currentTab" class="swiper-box" duration="300" :style="'height:' + (winHeight - 10) + 'px'"
			@change="bindSlideChange">
			<swiper-item class="swiper-items">
				<scroll-view @scrolltoupper="onPullDownRefresh" upper-threshold="-50" @scrolltolower="onReachBottom"
					lower-threshold="10" scroll-y class="scroll-views">
					<view class="integral-items" @tap="openSpecifications" :data-id="item.id"
						v-for="(item, index) in list" :key="index">
						<view class="integral-item">
							<view class="integral-item-name">
								<text>{{ item.message }}</text>
							</view>
							<view class="integral-item-time">
								<text>{{ item.createTime }}</text>
							</view>
						</view>

						<view
							:class="'integral-item-number ' + (item.operateType == 1 ? 'theme-color' : 'color-integral')">
							{{ item.operateType == 1 ? '+' : '-' }}{{ item.number }}
						</view>
					</view>
					<van-empty v-if="list.length <= 0" description="暂无记录">
					</van-empty>
				</scroll-view>
			</swiper-item>
		</swiper>
		<van-action-sheet :show="specificationsDialog" title="账单详情" @close="close" @cancel="close">
			<view class="content_box">
				<view class="order-info-view">
					<view class="order-title">账单说明</view>
					<view class="order-info">{{ recordInfo.message }}</view>
					<view class="order-title">账单金额</view>
					<view class="order-info">{{ recordInfo.operateType == 1 ? '+' : '-' }}{{ recordInfo.number }}元
					</view>
					<view class="order-title">支付时间</view>
					<view class="order-info">{{ recordInfo.createTime }}</view>
					<view class="order-title" v-if="recordInfo.serviceFee">服务费</view>
					<view class="order-info" v-if="recordInfo.serviceFee">{{ recordInfo.serviceFee }}元</view>
				</view>
			</view>
		</van-action-sheet>
	</view>
</template>

<script>
	import https from '../../../../utils/http';
	import authService from '../../../../utils/auth';
	import toastService from '../../../../utils/toast.service';
	import utilHelper from '../../../../utils/util';
	import dateHelper from '../../../../utils/date-helper';
	import systemStatus from '../../../../utils/system-status';
	var pageNo = 1;
	var pageSize = 10;
	export default {
		data() {
			return {
				list: [],
				specificationsDialog: false,
				winHeight: '',
				recordInfo: {
					message: '',
					operateType: 0,
					number: '',
					createTime: '',
					serviceFee: ''
				},
				isLastPage: '',
				userInfo: {
					balance: '',
					totalBalance: '',
					totalConsumeBalance: '',
					totayGainBalance: ''
				},
				currentTab: '',
				button: ''
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			this.getHeight();
			this.getUserInfo();
			this.getMemberBillingRecordList();
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
		onReachBottom: function() {
			if (this.isLastPage) {
				toastService.showToast('没有更多啦~');
				return;
			}
			pageNo = pageNo + 1;
			this.getVipRechargeRecordList();
		},
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
						query.select('.balance-views').boundingClientRect();
						query.selectViewport().scrollOffset();
						query.exec(function(res) {
							that.setData({
								winHeight: winHeight - res[0].height
							});
						});
					}
				});
			},

			openSpecifications(e) {
				console.log(e);
				this.specificationsDialog = true;
				this.recordSelectById(e.currentTarget.dataset.id);
			},
			close(e) {
				console.log(e);
				this.specificationsDialog = false;
			},
			recordSelectById(id) {
				https.request('/rest/member/billingRecord/selectById', {
					id: id
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						result.data.createTime = dateHelper.fmtDate(result.data.createTime);
						this.setData({
							recordInfo: result.data
						});
					}
				});
			},

			getMemberBillingRecordList() {
				toastService.showLoading('正在加载...');
				https.request('/rest/member/billingRecord/list', {
					pageNo: pageNo,
					pageSize: pageSize,
					coinType: 2
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						result.data.records.forEach((item, index) => {
							item.createTime = dateHelper.fmtDate(item.createTime);
							item.statusText = systemStatus.payStatusText(item.status);
							this.list.push(item);
						});
						this.setData({
							isLastPage: result.data.isLastPage,
							list: this.list
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

			bindSlideChange() {
				console.log('占位：函数 bindSlideChange 未声明');
			},

			onPullDownRefresh() {
				console.log('占位：函数 onPullDownRefresh 未声明');
			},

			onReachBottom() {
				console.log('占位：函数 onReachBottom 未声明');
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

	.balance-views {
		height: 360rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
		color: white;
	}

	.balance-view {
		font-size: 60rpx;
	}

	.balance-text {
		font-size: 28rpx;
	}

	.balance-detail {
		display: flex;
		align-items: center;
		justify-content: space-between;
		width: 80%;
		margin-top: 20rpx;
	}

	.balance-detail-info {
		text-align: center;
	}

	.balance-quota {
		font-size: 32rpx;
	}

	.balance-title {
		font-size: 22rpx;
	}

	.integral-items {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 20rpx 26rpx;
		margin: 20rpx;
		border-radius: 15rpx;
		background-color: white;
	}

	.integral-item-name {
		font-size: 28rpx;
		color: rgb(0, 0, 0);
		margin-bottom: 10rpx;
	}

	.integral-item-time {
		font-size: 26rpx;
		color: rgb(141, 141, 141);
	}

	.integral-item-number {
		font-size: 34rpx;
	}

	.color-integral {
		color: red;
	}

	.scroll-views {
		background: #f5f5f5;
		height: 100%;
	}

	.withdrawal-button {
		/* margin-top: 60rpx; */
		font-size: 28rpx;
	}

	.order-title {
		font-size: 28rpx;
		color: rgb(141, 141, 141);
	}

	.order-info {
		font-size: 28rpx;
		margin-bottom: 10rpx;
	}

	.extClassSelectCurrent .weui-half-screen-dialog__ft.weui-half-screen-dialog__ft {
		padding: 0;
	}
</style>