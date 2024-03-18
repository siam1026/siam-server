<template>
	<!-- <mp-navigation-bar loading="{{loading}}" show="{{show}}" animated="{{animated}}" color="{{color}}" background="{{background}}" 
title="全民参与 · 邀请好友" back="{{true}}" ext-class="{{extClass}}"></mp-navigation-bar> -->
	<view class="share-top">
		<!-- <view class="share-text-top" style="padding-top:{{statusBarHeight}}rpx;">全民参与·邀请好友</view> -->
		<view class="wodejl-view" @tap="bindReward">我的奖励>></view>
		<view>
			<image src="/static/assets/share-invite/2.png" class="top-img" mode="aspectFit"></image>
		</view>
		<view class="top-tis">获得好友下单现金返利</view>
		<view class="top-coupon">
			<view class="hdgz-view" @tap="bindActivityRules">活动规则>></view>

			<view class="">
				<image src="/static/assets/share-invite/3.png" class="top-coupon-img" mode="aspectFit"></image>
			</view>

			<view class="top-send" @tap.stop.prevent="goodShareDialogFun">
				<image src="/static/assets/share-invite/4.png" class="top-send-img" mode="widthFix"></image>
			</view>
		</view>
		<view class="top-list">
			<image src="/static/assets/share-invite/5.png" class="top-list-img" mode="aspectFit"></image>
			<view class="invite-relation-number">{{ inviteRelationList.length }}</view>
			<view class="top-null-view" v-if="inviteRelationList.length <= 0">
				<image src="/static/assets/share-invite/6.png" class="top-null-img" mode="aspectFit"></image>
			</view>

			<scroll-view class="list-wrapper" v-else scroll-y style="height: 180px">
				<view class="invite-relation-wrapper" :data-id="item.id" v-for="(item, index) in inviteRelationList"
					:key="index">
					<view class="user-info-wrapper">
						<image :src="item.headImg" class="headImg"></image>
						<view class="username out_of_range one_row">{{ item.username }}</view>
					</view>

					<view class="description">+1</view>
				</view>
			</scroll-view>
		</view>
		<van-action-sheet :show="activityRulesDialog" @close="close" title="活动规则">
			<view class="content_box">
				<scroll-view style="height: 55vh" scroll-y>
					<view class="activity-rule">
						<text>{{ inviteFriendsActivityRule }}</text>
					</view>
				</scroll-view>
			</view>
		</van-action-sheet>
		<van-action-sheet :show="goodShareDialog" @close="close" title="好物分享">
			<view class="content_box">
				<view class="wechat-images-view flex_between">
					<button open-type="share" class="flex_column"
						style="margin: 0rpx; background: none !important; color: none !important">
						<image src="/static/assets/share-invite/wx.png" mode="widthFix" class="invite-wechat-image">
						</image>
						<text class="share-text">微信</text>
					</button>

					<button class="flex_column" @tap="createGoodImgDialogFun"
						style="margin: 0rpx; background: none !important; color: none !important">
						<image src="/static/assets/share-invite/pyq.png" mode="widthFix" class="invite-tupian-image">
						</image>
						<text class="share-text">朋友圈</text>
					</button>
				</view>
			</view>
			<view slot="footer"></view>
		</van-action-sheet>
		<!-- <van-action-sheet :show="createGoodImgDialog" title="朋友圈分享美图">
			<view class="content_box">
				<scroll-view style="height: 100vh" scroll-y>
					<view class="carousel-show">
						<swiper :indicator-dots="indicatorDots" class="carousel-swiper" :autoplay="autoplay"
							:interval="interval" :duration="duration" :indicator-active-color="afterColor"
							@change="bindSlideChange">
							<block v-for="(item, index) in advertisementList" :key="index">
								<swiper-item class="carousel-swiper-item">
									<image :src="item.mainImageUrl" class="carousel-image" mode="aspectFit"
										:data-index="index" :data-imageLinkUrl="item.imageLinkUrl" @tap="viewImage" />
								</swiper-item>
							</block>
						</swiper>
					</view>
				</scroll-view>
			</view>
			<view slot="footer">
				<view class="halfScreenDialog-btn flex_center theme-bg" @tap="saveDialogShowFun">保存</view>
			</view>
		</van-action-sheet>
		<van-dialog title="保存图片" :show="saveDialogShow" :buttons="buttons" :mask-closable="maskClosable"
			@touchmove.native.stop.prevent="trueFun" @buttontap="createGoodImgSave" extClass="extClassSaveGoodImg">
			<scroll-view style="height: 40vh" scroll-y>
				<image :src="advertisementList[saveIndex].mainImageUrl" class="carousel-image" mode="aspectFill" />
			</scroll-view>
		</van-dialog> -->
	</view>
</template>

<script>
	import https from '../../../../utils/http';
	import authService from '../../../../utils/auth';
	import GlobalConfig from '../../../../utils/global-config';
	var toastService = require('../../../../utils/toast.service');
	var utilHelper = require('../../../../utils/util');
	var dateHelper = require('../../../../utils/date-helper');
	//获取应用实例
	const app = getApp();
	// var pageNo = -1, pageSize = 20, prevList = [];
	export default {
		data() {
			return {
				list: [],
				activityRulesDialog: false,
				loading: false,
				color: '#000',
				background: 'rgba(0,0,0,0)',
				show: true,
				animated: false,
				extClass: 'weui-navigation-bar-custom',
				goodShareDialog: false,
				createGoodImgDialog: false,
				indicatorDots: true,
				autoplay: true,
				interval: 10000,
				duration: 1000,

				//beforeColor: "white",//指示点颜色,
				afterColor: '#f1a142',

				//当前选中的指示点颜色
				opacity: 0.4,

				saveDialogShow: false,

				buttons: [{
						text: '取消'
					},
					{
						text: '上一张'
					},
					{
						text: '下一张'
					},
					{
						text: '保存'
					}
				],

				saveIndex: 0,
				canvasHidden: false,
				inviterId: '',
				statusBarHeight: '',
				userInfo: '',
				inviteRelationList: '',
				total: '',
				advertisementList: '',
				viewImages: '',
				inviteFriendsActivityRule: '',
				qrCode: '',
				winHeight: '',
				winWidth: '',
				shareImgSrc: '',
				timestamp: '',
				maskClosable: '',
				mainImageUrl: ''
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			console.log(options.inviterId);
			this.setData({
				inviterId: options.inviterId
			});
			this.setData({
				statusBarHeight: app.globalData.systemInfoSync.statusBarHeight * 2
			});
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
					this.getDeliveryAddressList();
					this.getUserInfo();
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
		onPullDownRefresh: function() {},
		/**
		 * 页面上拉触底事件的处理函数
		 */
		onReachBottom: function() {},
		/**
		 * 用户点击右上角分享
		 */
		onShareAppMessage: function(options) {
			// console.log(123);
			// var memberId = this.data.data.id;
			var inviterId = this.userInfo.id;
			console.log(inviterId);
			var timestamp = dateHelper.getTimestamp();
			console.log(
				`https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/bussiness/share-invite/share_wx.png?v=${timestamp}`
				);
			var shareObj = {
				title: '你的好友送了你一张3折优惠券，快去领取吧～',
				// path: '/pages/insert-share-invite/insert-share-invite?inviterId=' + inviterId,
				path: '/pages/login/choose/choose?inviterId=' + inviterId,
				//自定义图片路径，可以是本地文件路径、代码包文件路径或者网络图片路径，支持PNG及JPG，不传入 imageUrl 则使用默认截图。显示图片长宽比是 5:4
				imageUrl: `https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/bussiness/share-invite/share_wx.png?v=${timestamp}`
			};
			// 默认是当前页面，必须是以‘/’开头的完整路径};
			// 来自页面内的按钮的转发
			// if (options.from == 'button') {
			//   shareObj = {
			//     //默认是小程序的名称(可以写slogan等)
			//     title: "周二了，要不要来杯陨石拿铁？我请你~",
			//     imageUrl: '/assets/logo/logo.jpg',
			//     success: function (res) {
			//       if (res.errMsg == 'shareAppMessage:ok') {

			//       }
			//     },
			//     fail: function () {
			//       if (res.errMsg == 'shareAppMessage:fail cancel') {
			//         //用户取消转发

			//       } else if (res.errMsg == 'shareAppMessage:fail') {
			//         //转发失败，其中 detail message 为详细失败信息

			//       }
			//     }
			//   };
			//   shareObj.path = '/pages/invite-friends/invite-friends?memberId=' + this.data.data.id;
			// }
			return shareObj;
		},
		methods: {
			getUserInfo: function(e) {
				https.request('/rest/member/getLoginMemberInfo', {}).then((result) => {
					if (result.success) {
						this.setData({
							userInfo: result.data
						});
					}
				});
			},

			bindActivityRules(e) {
				console.log(e)
				this.setData({
					activityRulesDialog: true
				});
				this.getInviteFriendsActivityRule();
			},

			getDeliveryAddressList() {
				toastService.showLoading();
				https.request('/rest/memberInviteRelation/getMemberListByInviterId', {
					pageNo: -1,
					pageSize: 10
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						this.setData({
							inviteRelationList: result.data.records,
							total: result.data.total
						});
					}
				});
			},

			getAdvertisementList() {
				toastService.showLoading();
				https.request('/rest/member/advertisement/list', {
					type: 4,
					pageNo: -1,
					pageSize: 20
				}).then((result) => {
					if (result.success) {
						var viewImages = [];
						result.data.records.forEach(function(item, index) {
							console.log(item);
							item.mainImageUrl = GlobalConfig.ossUrl + item.imagePath;
							viewImages.push(item.mainImageUrl);
						});
						this.setData({
							advertisementList: result.data.records,
							viewImages: viewImages
						});
					}
					toastService.hideLoading();
				});
			},

			getInviteFriendsActivityRule() {
				toastService.showLoading();
				https.request('/rest/setting/selectCurrent', {}).then((result) => {
					if (result.success) {
						console.log(result);
						let inviteFriendsActivityRule = result.data.inviteFriendsActivityRule.replace('↵', '\n');
						this.setData({
							inviteFriendsActivityRule: inviteFriendsActivityRule
						});
					}
					toastService.hideLoading();
				});
			},

			getAccessToken(e) {
				var appid = 'wx2e1a8193d3ed12fe'; //填写微信小程序appid
				var secret = '2774e3a86dc30fbf1ac63d81b56f2291'; //填写微信小程序secret
				var _this = this;
				uni.request({
					url: 'https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=' + appid +
						'&secret=' + secret,
					header: {
						'content-type': 'application/json'
					},
					success: function(res) {
						console.log(res);
						_this.getCode(res.data.access_token);
					}
				});
			},

			getCode(ACCESS_TOKEN) {
				console.log(ACCESS_TOKEN);
				var _this = this;
				uni.request({
					url: 'https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=' + ACCESS_TOKEN,
					data: {
						scene: 'id=18'
					},
					method: 'POST',
					responseType: 'arraybuffer',
					//这个是转化成base64需要加的
					success: function(res) {
						console.log(res);
						var array = uni.base64ToArrayBuffer(res.data);
						var base64 = uni.arrayBufferToBase64(res.data);
						console.log(base64);
						_this.setData({
							qrCode: 'data:image/jpeg;base64,' + base64
						});
					}
				});
			},

			bindReward(e) {
				uni.navigateTo({
					url: '../reward/reward'
				});
			},

			goodShareDialogFun() {
				this.setData({
					goodShareDialog: true
				});
			},
			close() {
				this.setData({
					goodShareDialog: false,
					activityRulesDialog:false
				});
			},

			createGoodImgDialogFun() {
				this.getAdvertisementList();
				this.setData({
					goodShareDialog: false,
					createGoodImgDialog: true
				});
			},

			// 滑动切换tab
			bindSlideChange: function(e) {
				console.log(e);
				this.setData({
					saveIndex: e.detail.current
				});
			},

			saveDialogShowFun() {
				var _this = this;
				toastService.showLoading('保存中...');
				uni.getImageInfo({
					src: _this.advertisementList[_this.saveIndex].mainImageUrl,
					success: function(res) {
						var path = res.path;
						uni.saveImageToPhotosAlbum({
							filePath: path,
							success(res) {
								toastService.hideLoading();
								toastService.showSuccess('保存相册成功');
								_this.saveImageToPhotosAlbum();
								console.log(res);
							},
							fail(res) {
								toastService.hideLoading();
								toastService.showError('保存相册失败');
								console.log(res);
							}
						});
					}
				});
			},

			createGoodImgSave(e) {
				console.log(e);
				if (e.detail.index == 0) {
					console.log('点击了', e.detail.item.text);
					this.setData({
						saveDialogShow: false,
						saveIndex: 0
					});
				}
				if (e.detail.index == 1) {
					console.log('点击了', e.detail.item.text);
					this.setData({
						saveIndex: this.saveIndex == 0 ? this.advertisementList.length - 1 : this.saveIndex - 1
					});
				}
				if (e.detail.index == 2) {
					console.log('点击了', e.detail.item.text);
					this.setData({
						saveIndex: this.saveIndex == this.advertisementList.length - 1 ? 0 : this.saveIndex + 1
					});
				}
				if (e.detail.index == 3) {
					console.log('点击了', e.detail.item.text);
					uni.getImageInfo({
						src: this.advertisementList[this.saveIndex].mainImageUrl,
						success: function(res) {
							var path = res.path;
							uni.saveImageToPhotosAlbum({
								filePath: path,
								success(res) {
									toastService.showToast('保存相册成功');
									console.log(res);
								},
								fail(res) {
									toastService.showToast('保存相册失败');
									console.log(res);
								}
							});
						}
					});
				}
			},

			viewImage: function(e) {
				var index = e.currentTarget.dataset.index; //获取data-currentimg
				var src = this.viewImages[index];

				//图片预览
				uni.previewImage({
					current: src,
					// 当前显示图片的http链接
					urls: this.viewImages // 需要预览的图片http链接列表
				});
			},

			getHeight() {
				//获取用户手机系统信息
				var that = this;
				uni.getSystemInfo({
					success: function(res) {
						let winHeight = res.windowHeight; //获取高度
						that.setData({
							winHeight: winHeight,
							winWidth: res.windowWidth
						});
					}
				});
			},

			//保存至相册
			saveImageToPhotosAlbum: function() {
				console.log('保存中');
				this.getHeight();
				var that = this;
				//2. canvas绘制文字和图片
				const ctx = uni.createCanvasContext('share');
				//这里很重要，主要就是布局
				console.log(this.advertisementList[this.saveIndex].mainImageUrl);
				const mainImageUrl = this.advertisementList[this.saveIndex].mainImageUrl;
				uni.getImageInfo({
					src: mainImageUrl,
					success(res) {
						console.log(res);
						ctx.drawImage(res.path, 0, 0, res.width / 3, res.height / 3);
						ctx.drawImage(that.qrCode, that.winWidth - 140, res.height / 3, 100, 100);
						ctx.fillText('姓名：江鹏', 10, res.height / 3 + 40);
						ctx.fillText('人物匹配：猛男', 10, res.height / 3 + 60);
						ctx.fillText('近期预言：给我涨，给我红，冲冲冲！', 10, res.height / 3 + 80);
						ctx.setFontSize(13);
						ctx.setFillStyle('#5e7436');
						ctx.stroke();
						ctx.draw(false, function() {
							// 3. canvas画布转成图片
							uni.canvasToTempFilePath({
								x: 0,
								y: 0,
								width: that.winWidth,
								height: that.winHeight,
								destWidth: that.winWidth,
								destHeight: that.winHeight,
								canvasId: 'share',
								success: function(res) {
									console.log(res);
									that.setData({
										shareImgSrc: res.tempFilePath,
										canvasHidden: false
									});
									if (!res.tempFilePath) {
										uni.showModal({
											title: '提示',
											content: '图片绘制中，请稍后重试',
											showCancel: false
										});
									}
									//4. 当用户点击分享到朋友圈时，将图片保存到相册
									uni.saveImageToPhotosAlbum({
										filePath: res.tempFilePath,
										success(res) {
											console.log(res);
											uni.showModal({
												title: '图片保存成功',
												content: '图片成功保存到相册了，去发圈噻~',
												showCancel: false,
												confirmText: '好哒',
												confirmColor: '#72B9C3',
												success: function(res) {
													if (res.confirm) {
														console.log(
															'用户点击确定'
															);
													}
													that.setData({
														canvasHidden: true
													});
												}
											});
										}
									});
								},
								fail: function(res) {
									console.log(res);
								}
							});
						});
					}
				});
			},

			getTimestamp() {
				var timestamp = dateHelper.getTimestamp();
				console.log(timestamp);
				this.setData({
					timestamp: timestamp
				});
			},

			trueFun() {
				console.log('占位：函数 true 未声明');
			}
		}
	};
</script>
<style>
	page {
		background: #feefd0;
	}

	.share-top {
		text-align: center;
	}

	.share-text-top {
		font-size: 40rpx;
		font-weight: bold;
		padding-bottom: 20rpx;
		background: white;
	}

	.wodejl-view {
		position: fixed;
		margin-top: 6%;
		right: 0;
		background: linear-gradient(to right, #ff5c57, #fe4646);
		padding: 10rpx 20rpx;
		border-radius: 40rpx 0 0 40rpx;
		color: white;
		font-size: 30rpx;
	}

	/* 关于背景图的样式 */
	.top-img {
		height: 159rpx;
		width: 100%;
		margin-top: 130rpx;
	}

	.top-coupon {
		/* margin-top: -9%; */
		width: 100%;
		text-align: center;
	}

	.hdgz-view {
		color: #929392;
		font-size: 28rpx;
		z-index: 9999999999;
	}

	.top-list {
		width: 100%;
		text-align: center;
		z-index: 9999;
		margin-top: 5%;
	}

	.top-coupon-img {
		height: 740rpx;
		width: 100%;
	}

	.top-tis {
		margin-top: 50%;
		position: absolute;
		text-align: center;
		width: 100%;
		font-size: 32rpx;
		color: #6e6e6e;
	}

	.top-send {
		margin-top: -37%;
	}

	button {
		background: inherit;
	}

	button:after {
		border: none;
	}

	.top-send-img {
		width: 46%;
	}

	.top-list-img {
		width: 82%;
		height: 740rpx;
	}

	.top-null-view {
		margin-top: -10%;
		z-index: 99999;
	}

	.top-null-img {
		width: 100%;
	}

	.invite-relation-number {
		margin-top: -65.5%;
		z-index: 99999;
		margin-left: 12%;
		color: white;
	}

	/* 邀请记录区块 */
	.invite-all-wrapper {
		margin-bottom: 130px;
	}

	.title {
		text-align: center;
		font-size: 12px;
		margin-top: 15px;
		/* color: white; */
		font-weight: bold;
	}

	.invite-relation-wrapper {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin: 20rpx;
	}

	/* .invite-relation-wrapper:first-child{
  padding-top: 16rpx;
}
.invite-relation-wrapper:last-child{
  padding-bottom: 16rpx;
} */
	.list-wrapper {
		z-index: 99999;
		color: white;
		margin: 0 15%;
		width: 70%;
		/* padding: 16rpx 26rpx;   */
	}

	.user-info-wrapper {
		display: flex;
		justify-content: flex-start;
		justify-content: space-between;
		align-items: center;
		width: 80%;
	}

	.headImg {
		width: 38px;
		height: 38px;
		border-radius: 50%;
	}

	.username {
		margin-left: 10px;
		width: 70%;
	}

	.data-emtpy-description {
		text-align: center;
		font-size: 12px;
		margin-top: 15px;
		margin-bottom: 15px;
		/* color: white; */
		font-weight: bold;
	}

	.number-text {
		/* 金麒麟色 */
		color: #daa520;
		font-weight: bold;
	}

	/* 底部悬浮区块 */
	.bottom-wrapper {
		height: 120px;
		align-items: center;
		justify-content: center;
		display: flex;
		flex-direction: column;
		background: white;
		/* Start 底部悬浮控制 */
		position: fixed;
		bottom: 0px;
		width: 100%;
		/* End 底部悬浮控制 */
	}

	.wxchat-icon {
		width: 50px;
		height: 50px;
	}

	.wxchat-icon-description {
		font-size: 13px;
		font-weight: bold;
	}

	.share-button {
		background-color: unset;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.content-class {
		position: absolute;
		top: 0;
		/* margin-top: 6%; */
		width: 100%;
	}

	/* 自定义弹出框的最大高度为100%，并设置他的左右上交的border-ric为0 */
	.weui-show .weui-half-screen-dialog.extClassShoppingCart {
		max-height: 100vh;
		padding: 0 20rpx;
		position: fixed;
		bottom: 0;
	}

	.weui-half-screen-dialog.extClassShoppingCart .weui-half-screen-dialog__ft {
		padding: 20rpx 0;
		position: sticky;
		bottom: 0;
	}

	.weui-show .weui-half-screen-dialog.extClassShoppingCart .weui-half-screen-dialog__hd {
		padding: 0 20rpx;
	}

	/* 自定义弹出框的最大高度为100%，并设置他的左右上交的border-ric为0 */
	.weui-show .weui-half-screen-dialog.extClassCreateGoodImg {
		max-height: 100vh;
		padding: 0 20rpx;
		position: fixed;
		bottom: 0;
	}

	.weui-half-screen-dialog.extClassCreateGoodImg .weui-half-screen-dialog__ft {
		padding: 20rpx 0;
		position: sticky;
		bottom: 0;
	}

	.weui-show .weui-half-screen-dialog.extClassCreateGoodImg .weui-half-screen-dialog__hd {
		padding: 0 20rpx;
	}

	.weui-half-screen-dialog.extClassCreateGoodImg {
		border-radius: 0;
	}

	.activity-rule {
		text-align: left;
		margin-bottom: 10rpx;
	}

	.activity-rule text {
		font-size: 28rpx;
	}

	.invite-wechat-image {
		width: 120rpx;
		height: auto;
	}

	.invite-tupian-image {
		width: 140rpx;
		height: auto;
	}

	.share-text {
		font-size: 30rpx;
	}

	.carousel-swiper {
		height: 80%;
	}

	.carousel-show {
		height: 100%;
		padding-bottom: 5%;
	}

	.carousel-swiper-item {
		border-radius: 15rpx;
		height: 100%;
	}

	.carousel-image {
		width: 100%;
		height: 100%;
	}

	.weui-mask {
		z-index: 5000;
	}

	.weui-mask,
	.weui-mask_transparent {
		z-index: 5000;
	}

	.qrcodeImage {
		width: 100%;
	}

	.wx-swiper-dots {
		position: relative;
		top: 0;
		text-align: center;
	}

	.wx-swiper-dots.wx-swiper-dots-horizontal {
		margin-top: 15rpx;
	}

	.halfScreenDialog-btn {
		height: 56rpx;
	}

	button[type='default'] {
		background-color: inherit;
	}
</style>