<template>
	<view class="page-content">
		<view class="member-center">
			<view class="member-vip-info">
				<view class="vip-bg-view">
					<view class="flex_between">
						<view class="member-info">
							<image :src="data.headImg" mode="widthFix" class="headImg"></image>
							<view class="nickname-vipEndTime">
								<view class="nickname flex_start">
									<view class="out_of_range one_row">{{ data.username }}</view>
									<view class="vip-image-view">
										<image :src="item + '?v=' + appVersion" mode="widthFix" class="is-vip-image"
											v-for="(item, index) in isVipImages" :key="index"></image>
									</view>
								</view>
								<view class="vip-recharge flex_between">
									<view class="renew-botton" v-if="data.type == 1" @tap="bindVipRecharge">加入会员</view>
									<view class="renew-botton" v-else @tap="bindVipRecharge">去续费</view>
								</view>
								<view class="no-vipno flex_start">ID：{{ data.vipNo }}</view>
							</view>
						</view>
						<view @tap="bindReward" class="wodejl-view">佣金明细></view>
					</view>
					<view>
						<view class="mine-blocking-view">
							<view class="mine-blocking">
								<navigator class="mine-navigator" url="../../balance/index/index">
									<view class="number-tip theme-color">{{ data.balance }}</view>
									<view class="text-tip">余额</view>
								</navigator>
								<navigator class="mine-navigator" url="../../integral/integral">
									<view class="number-tip theme-color">{{ data.points }}</view>
									<view class="text-tip">我的积分</view>
								</navigator>
								<navigator class="mine-navigator" url="../../share/reward/reward">
									<view class="number-tip theme-color">{{ data.inviteRewardAmount }}</view>
									<view class="text-tip">佣金</view>
								</navigator>
								<navigator class="mine-navigator" url="../../coupons/coupons">
									<view class="number-tip theme-color">
										{{ data.couponsNumber ? data.couponsNumber : 0 }}
									</view>
									<view class="text-tip">卡券</view>
								</navigator>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="add_buttom">
			<view class="add-vip" @tap="bindVipRecharge">加入会员尊享4大权益</view>
		</view>
		<view class="vip-details-view">
			<view class="vip-detail">
				<image
					:src="'https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/business/vip_page/guarantee.png?v=' + appVersion"
					mode="aspectFill" class="invite-image"></image>
				<view class="iconfont-title">账户保障</view>
			</view>
			<view class="vip-detail">
				<image
					:src="'https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/business/vip_page/wallet.png?v=' + appVersion"
					mode="aspectFill" class="invite-image"></image>
				<view class="iconfont-title">独家礼券</view>
			</view>
			<view class="vip-detail">
				<image
					:src="'https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/business/vip_page/trophy.png?v=' + appVersion"
					mode="aspectFill" class="invite-image"></image>
				<view class="iconfont-title">下单奖励</view>
			</view>
			<view class="vip-detail">
				<image
					:src="'https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/business/vip_page/share.png?v=' + appVersion"
					mode="aspectFill" class="invite-image"></image>
				<view class="iconfont-title">邀请奖励</view>
			</view>
		</view>

		<view class="gift-view">
			<view class="view-line"></view>
			<view class="gift-view-info">
				<image
					:src="'https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/business/vip_page/recharge.png?v=' + appVersion"
					mode="widthFix" class="gift-image" @tap="bindVipRecharge"></image>
			</view>
			<view class="gift-content">
				<view>
					<view class="mrwcyqrw">每日完成邀请任务</view>
					<view class="stqdsh">送TA券的时候你还有现金奖励</view>
				</view>
				<view class="goto-finish" @tap="bindInvite">去完成</view>
			</view>
			<view class="view-line"></view>
			<view class="vip-rules">
				<text class="vip-text">{{ current.vipRule }}</text>
			</view>
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
	let app = null;
	export default {
		data() {
			return {
				isVipImages: [],
				appVersion: 1.0,
				data: {
					headImg: '',
					username: '',
					type: 0,
					vipNo: '',
					balance: '',
					points: '',
					inviteRewardAmount: '',
					couponsNumber: false
				},
				userInfo: '',
				timestamp: '',
				current: {
					vipRule: ''
				}
			};
		}
		/**
		 * 生命周期函数--监听页面加载
		 */
		,
		onLoad: function(options) {
			app = getApp();
			this.appVersion = app.globalData.appVersion;
			this.selectCurrent();
		},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {
			authService.checkIsLogin().then((result) => {
				if (result) {
					this.getUserInfo();
					return;
				}
				this.data = null;
				this.userInfo = null;
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

			selectCurrent() {
				https.request('/rest/setting/selectCurrent', {}).then((result) => {
					if (result.success) {
						result.data.vipRule = result.data.vipRule.replace('↵', '\n');
						this.setData({
							current: result.data
						});
					}
				});
			},

			bindInvite() {
				uni.navigateTo({
					url: `../../share/index/index?inviterId=${this.data.id}`
				});
			},

			getUserInfo: function(e) {
				https.request('/rest/member/getLoginMemberInfo', {}).then((result) => {
					if (result.success) {
						result.data.typeVipText = systemStatus.typeVipText(result.data.type);
						if (this.isVipImages.length <= 0) {
							if (result.data.type == 1) {
								this.isVipImages.push(
									'https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/business/mine_page/user_1.png'
								);
								this.isVipImages.push(
									'https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/business/mine_page/user_2.png'
								);
								this.isVipImages.push(
									'https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/business/mine_page/user_3.png'
								);
							} else {
								this.isVipImages.push(
									'https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/business/mine_page/vip_1.png'
								);
								this.isVipImages.push(
									'https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/business/mine_page/vip_2.png'
								);
								this.isVipImages.push(
									'https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/business/mine_page/vip_3.png'
								);
							}
						}
						result.data.isVip = result.data.type == 1 ? false : true;
						this.setData({
							data: result.data,
							isVipImages: this.isVipImages
						});
					}
				});
			},

			bindReward() {
				uni.navigateTo({
					url: '../../../mine/share/reward/reward'
				});
			},

			bindOrders() {
				uni.navigateTo({
					url: '../record/record'
				});
			},

			bindBalance() {
				uni.navigateTo({
					url: '../../balance/index/index'
				});
			},

			bindCoupons() {
				uni.navigateTo({
					url: '../../../mine/coupons/coupons'
				});
			},

			bindVipRecharge() {
				uni.navigateTo({
					url: '../recharge/recharge'
				});
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

	.page-content {
		background-color: white;
	}

	.member-center {
		overflow: hidden;
		color: #f0e0be;
		background: #11110f;
		padding: 20rpx 20rpx 0 20rpx;
		border-radius: 0 0 50% 50%/0 0 10% 10%;
	}

	.member-vip-info {
		display: flex;
		align-items: center;
	}

	.vip-bg-view {
		width: 100%;
		/* align-items: center; */
		background: linear-gradient(to right, #f4e5c2, #e9a8a6);
		color: #6f4e20;
		height: 360rpx;
		padding: 20rpx;
		border-top-left-radius: 15rpx;
		border-top-right-radius: 15rpx;
	}

	.headImg {
		width: 96rpx;
		height: auto;
		border-radius: 50%;
	}

	.member-info {
		/* width: 70%; */
		display: flex;
		/* align-items: center; */
	}

	.nickname-vipEndTime {
		margin-left: 20rpx;
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

	.nickname {
		font-size: 30rpx;
		font-weight: bold;
		height: 50rpx;
		line-height: 50rpx;
	}

	.vipEndTime {
		font-size: 22rpx;
	}

	.renew-botton {
		border: 1rpx solid #6f4e20;
		padding: 0rpx 10rpx;
		border-radius: 50rpx;
		font-size: 24rpx;
		background-color: #f4e5c2;
	}

	.vip-recharge-view {
		height: 0;
		margin-top: 25rpx;
	}

	.vip-image-view {
		display: flex;
		align-items: center;
		margin-left: 15rpx;
	}

	.is-vip-image {
		width: 36rpx;
		height: auto;
		margin-right: 10rpx;
	}

	.vipImg {
		width: 280rpx;
		height: auto;
	}

	.vip-details-view {
		display: grid;
		grid-template-columns: 1fr 1fr 1fr 1fr;
		grid-row-gap: 10px;
		grid-column-gap: 10px;
		background-color: white;
		margin: 20rpx;
	}

	.vip-detail {
		display: flex;
		align-items: center;
		flex-direction: column;
		color: #11110f;
		font-size: 30rpx;
	}

	.vip-detailImg {
		width: 90rpx;
		height: auto;
	}

	.iconfont.iconyue,
	.icondingdan-,
	.iconyouhuiquan {
		font-size: 65rpx;
		color: #f0dcab;
	}

	.iconfont-title {
		font-size: 26rpx;
	}

	.iconhuiyuan1 {
		font-size: 220rpx;
		color: #f0deaf;
	}

	.no-vipno {
		height: 86rpx;
		font-weight: bold;
		font-size: 28rpx;
	}

	.wodejl-view {
		position: absolute;
		right: 3%;
		opacity: 0.6;
		background: white;
		padding: 10rpx 20rpx;
		border-radius: 40rpx 0 0 40rpx;
		color: black;
		font-size: 28rpx;
		font-weight: bold;
	}

	/* 个人余额和钱包等样式 */
	.mine-blocking-view {
		padding: 0 20rpx 20rpx 20rpx;
		text-align: center;
	}

	.mine-blocking {
		display: flex;
		align-items: center;
		justify-content: space-between;
		text-align: center;
		border-radius: 10rpx;
	}

	.mine-navigator {
		padding: 20rpx 0;
		width: 24%;
		border-radius: 10rpx;
	}

	.number-tip {
		font-size: 38rpx;
	}

	.text-tip {
		font-size: 26rpx;
	}

	.invite-image {
		width: 150rpx;
		height: 150rpx;
	}

	.add_buttom {
		display: flex;
		align-items: center;
		justify-content: center;
		position: relative;
		margin-top: -6%;
	}

	.add-vip {
		font-size: 30rpx;
		text-align: center;
		padding: 15rpx;
		background-color: #f5e9cd;
		border-radius: 90rpx;
		width: 40%;
		color: #5a2e16;
		border: 5rpx solid;
		border-color: #e9a8a6;
	}

	.gift-view {
		margin: 20rpx;
	}

	.gift-view-info {
		/* width: 100%; */
	}

	.gift-image {
		margin-top: 20rpx;
		width: 100%;
		height: auto;
	}

	.gift-content {
		margin-bottom: 20rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.mrwcyqrw {
		font-size: 32rpx;
		color: #eea025;
		font-weight: bold;
	}

	.stqdsh {
		font-size: 28rpx;
		color: #999999;
	}

	.goto-finish {
		border: 1rpx solid #eba9a9;
		padding: 5rpx 20rpx;
		border-radius: 50rpx;
		color: #df5353;
		font-weight: bold;
		font-size: 28rpx;
	}

	.vip-rules {
		margin: 20rpx 0;
		padding-bottom: 80rpx;
	}

	.vip-text {
		font-size: 30rpx;
	}
</style>