<template>
	<view>
		<view class="top-class">
			<image :src="shopInfo.shop.shopLogoImg" class="shopLogoImg-class" mode="aspectFill"></image>
			<text class="shopInfo-name">{{ shopInfo.shop.name }}</text>
		</view>
		<view class="business-evaluate-view">
			<view class="business-evaluate-title">商家评价</view>
			<!-- <multiple-rate :rate="rate" :disabled="disabled" @change="handleTap"></multiple-rate> -->
			<view class="textarea-view" v-if="isChoiceRate">
				<textarea @input="bindTextarea" :placeholder="contentPlaceholder"></textarea>
				<text class="textarea-tip">谢谢您的评价</text>
			</view>
		</view>
		<view class="uploader-class">
			<text class="images-num">{{ uploaderImages.length }}/3</text>
			<view class="uploaderImages">
				<view class="uploaderItems" v-for="(img, index) in uploaderImages" :key="index">
					<image :src="img" :data-index="index" :data-imgs="uploaderImages" @tap="viewImage"
						class="uploader-imgs-item" mode="aspectFill"></image>

					<text class="iconfont icon55 uploader-close-img" @tap="closeImage" :data-index="index"></text>

					<!-- <image src="../../assets/common/close-gary.png" data-index="{{index}}" bindtap="closeImage" class="uploader-close-img" mode="aspectFill"></image> -->
				</view>
				<text class="iconfont iconshangchuantupian1" @tap="uploadImage"></text>
				<!-- <image src="../../assets/common/tupian.png" wx:if="{{uploaderImages.length < 3}}" bindtap="uploadImage" class="uploader-class-img" mode="aspectFill"></image> -->
			</view>
		</view>
		<view class="submit-evaluate theme-bg" @tap="submitEvaluate">提交评价</view>
	</view>
</template>

<script>
	import GlobalConfig from '../../../utils/global-config';
	import https from '../../../utils/http';
	import toastService from '../../../utils/toast.service';
	var submitImages = [];
	let app = null;
	export default {
		data() {
			return {
				rate: 0,
				disabled: false,
				isChoiceRate: false,
				value: 0,
				contentPlaceholder: '餐点味道好，包装也仔细，下次还会点',
				uploaderImages: [],
				orderId: '',
				shopId: '',
				shopInfo: {
					shop: {
						shopLogoImg: '',
						name: ''
					}
				},
				contentTextarea: '',
				choiceRate: '',
				img: ''
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			app = getApp();
			this.orderId = options.orderId;
			this.shopId = options.shopId;
			this.getOrderDetail(options.orderId);
			this.getShopInfo(options.shopId);
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
			getOrderDetail(id) {
				https.request('/rest/member/order/selectById', {
					id: id
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {}
				});
			},

			getShopInfo(shopId) {
				https.request('merchant/rest/shop/detail', {
					id: shopId,
					position: app.globalData.deliveryAndSelfTaking.location
				}).then((result) => {
					if (result.success && result.data) {
						result.data.shop.shopLogoImg = GlobalConfig.ossUrl + result.data.shop.shopLogoImg;
						this.shopInfo = result.data;
					}
				});
			},

			bindTextarea(e) {
				this.setData({
					contentTextarea: e.detail.value
				});
			},

			handleTap(e) {
				if (e.detail.value > 0) {
					this.setData({
						isChoiceRate: true,
						choiceRate: e.detail.value
					});
				}
			},

			viewImage: function(event) {
				var imgs = event.currentTarget.dataset.imgs; //获取data-src
				var index = event.currentTarget.dataset.index; //获取data-currentimg
				var src = imgs[index];
				//图片预览
				uni.previewImage({
					current: src,
					// 当前显示图片的http链接
					urls: imgs // 需要预览的图片http链接列表
				});
			},

			// uploadImage: function (e) {
			//   if (this.data.uploaderImages.length == 3) {
			//     toastService.showToast("最多只能上传三张图片");
			//     return
			//   };
			//   https.uploadFile({ url: 'util/rest/member/uploadSingleImage' }).then(result => {
			//     if (result.success) {
			//       toastService.showLoading("上传中...");
			//       let image = GlobalConfig.ossUrl + result.data;
			//       this.data.uploaderImages.push(image);
			//       submitImages.push(result.data);
			//       this.setData({
			//         uploaderImages: this.data.uploaderImages
			//       });
			//       toastService.hideLoading();
			//       toastService.showSuccess('上传成功', 1000);
			//     }
			//   })
			// },
			uploadImage: function(e) {
				uni.showActionSheet({
					itemList: ['拍照', '从手机相册选择'],
					success: (res) => {
						uni.chooseImage({
							count: res.tapIndex == 0 ? 1 : 1,
							sizeType: ['original', 'compressed'],
							// 可以指定是原图还是压缩图，默认二者都有
							sourceType: [res.tapIndex == 0 ? 'camera' : 'album'],
							success: (result) => {
								let filePath = result.tempFilePaths[0];
								this.uploadImageInner(filePath, (data) => {
									let image = GlobalConfig.ossUrl + data.data;
									this.uploaderImages.push(image);
									submitImages.push(data.data);
									this.setData({
										uploaderImages: this.uploaderImages
									});
								});
							}
						});
					}
				});
			},

			uploadImageInner: function(filePath, callback) {
				toastService.showLoading('正在上传...', true); //http://192.168.1.12:8081
				const url = GlobalConfig.baseUrl + 'util/rest/member/uploadSingleImage';
				https.uploadImage({
					url: url,
					filePath: filePath,
					success: (data) => {
						toastService.showSuccess('上传成功');
						if (callback) {
							callback(data);
						}
					},
					fail: (error) => {
						console.log('上传失败-->' + error);
						toastService.showError('上传失败');
					},
					complete: () => {
						//toastService.hideLoading();
					}
				}).then((uploadTask) => {
					uploadTask.onProgressUpdate((res) => {
						console.log('上传进度', res.progress);
						console.log('已经上传的数据长度', res.totalBytesSent);
						console.log('预期需要上传的数据总长度', res.totalBytesExpectedToSend);
					});
				});
			},

			closeImage(e) {
				let self = this;
				toastService.showModal(null, '确定要删除这张图片吗?', function confirm() {
					self.uploaderImages.splice(e.currentTarget.dataset.index, 1);
					submitImages.splice(e.currentTarget.dataset.index, 1);
					self.setData({
						uploaderImages: self.uploaderImages
					});
				});
			},

			submitEvaluate() {
				console.log('评价等级======' + this.choiceRate);
				if (!this.choiceRate) {
					toastService.showToast('请选择评价等级');
					return;
				}
				let title = '确定提交评价吗?';
				let content = this.contentTextarea;
				if (!content) {
					title = '不填写评价会默认提交文本框内的内容哦~';
					content = this.contentPlaceholder;
				}
				console.log('评价内容为=====>' + content);
				let self = this;
				toastService.showModal(null, title, function() {
					https.request('/rest/member/appraise/insert', {
						orderId: self.orderId,
						shopId: self.shopId,
						appraiseType: 1,
						content: content,
						imagesUrl: submitImages,
						level: self.choiceRate
					}).then((result) => {
						if (result.success) {
							toastService.showSuccess('提交成功', 1000);
							uni.navigateBack(1);
						}
					});
				});
			}
		}
	};
</script>
<style>
	page {
		background: #f5f5f5;
	}

	.top-class {
		padding: 20rpx;
		display: flex;
		align-items: center;
	}

	.shopLogoImg-class {
		width: 100rpx;
		height: 100rpx;
	}

	.shopInfo-name {
		margin-left: 20rpx;
		font-size: 28rpx;
	}

	.business-evaluate-view {
		margin: 0 20rpx 20rpx 20rpx;
		background: white;
		text-align: center;
		padding: 20rpx;
		border-radius: 20rpx;
	}

	.business-evaluate-title {
		font-size: 32rpx;
		font-weight: bold;
	}

	.textarea-view {
		margin-top: 40rpx;
	}

	textarea {
		padding: 20rpx;
		text-align: left;
		width: 94%;
		background: #f5f5f5;
		margin-bottom: 20rpx;
	}

	.submit-evaluate {
		width: 100%;
		padding: 20rpx 0;
		text-align: center;
		position: absolute;
		bottom: 0;
	}

	.uploader-imgs-item {
		width: 100rpx;
		height: 100rpx;
		margin-right: 20rpx;
	}

	.uploader-close-img {
		width: 30rpx;
		height: 30rpx;
		position: absolute;
		margin-left: 7%;
		margin-top: -10%;
	}

	.uploader-class-img {
		width: 100rpx;
		height: 100rpx;
	}

	.uploader-class {
		background: white;
		padding: 20rpx;
		margin: 0 20rpx;
		border-radius: 20rpx;
		display: flex;
		flex-direction: column;
	}

	.textarea-tip {
		font-size: 32rpx;
		font-weight: bold;
	}

	.uploaderImages {
		display: flex;
		align-items: center;
	}

	.uploaderItems {
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.images-num {
		text-align: end;
	}

	.iconshangchuantupian1 {
		font-size: 100rpx;
		color: #a2a2a2;
		margin-left: 20rpx;
	}
</style>