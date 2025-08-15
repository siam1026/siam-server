<template>
	<view>
		<view class="top-detail">
			<view class="current-tips" @tap.stop.prevent="customerServiceWechat">联系客服可领取运费险，获取客服微信请戳这里</view>
		</view>
		<view class="top-detail">
			<view class="top-shop-name">
				<text :decode="true">{{ order.description }}</text>
			</view>
			<view class="top-shop-name">
				<view style="width: 20%;">
					<checkbox :checked="isSelectAll" @tap="bindSelectAll" style="width: 100%;;" iconColor="#FFFFFF">全选</checkbox>
					
				</view>
				<view>
					<text>退款金额</text>
					<text>￥{{ order.actualPrice }}</text>
				</view>
			</view>
			<view class="commdiy-lists">
				<checkbox-group @change="checkboxChange">
					<view class="order-detail-list" v-for="(item, index) in orderDetailList" :key="index">
						<label>
							<checkbox :checked="item.checked" :value="index" :index="index" iconColor="#FFFFFF">
							</checkbox>
						</label>

						<image :src="item.mainImage" mode="scaleToFill" class="order-mainImage"></image>

						<view class="order-detail">
							<view class="order-goodsName-number">
								<view class="order-goodsName out_of_range one_row">{{ item.goodsName }}</view>
								<text class="order-number" v-if="item.oldNumber > 1">x{{ item.oldNumber }}</text>
								<text class="order-number">￥{{ item.subtotal }}</text>
							</view>
							<!-- <text>x{{item.number}}</text> -->
							<view class="item-stepper">
								<view class="order-specListAnalysis out_of_range one_row">{{ item.specListAnalysis }}
								</view>
								<view class="stepper" v-if="item.oldNumber > 1">
									<block>
										<text class="reduce-class" @tap="bindMinus" :data-goodsId="item.goodsId"
											:data-number="item.number">－</text>
										<input disabled type="number" :value="item.number" class="reduce-class" />
										<text @tap="bindPlus" class="add-class" :data-goodsId="item.goodsId"
											:data-index="index" :data-number="item.number"
											:data-oldNumber="item.oldNumber">
											＋
										</text>
									</block>
								</view>
							</view>
						</view>
					</view>
				</checkbox-group>
			</view>
			<view class="order-delivery-fee" v-if="order.reducedPrice > 0">
				<text>满减费</text>
				<text>-￥{{ order.reducedPrice }}</text>
			</view>
			<view class="order-delivery-fee">
				<text>配送费</text>
				<text>￥{{ order.deliveryFee?order.deliveryFee:0 }}</text>
			</view>
		</view>
		<view class="refund-reason">
			<view class="refund-reason-top">
				<text>退款原因</text>
				<view class="refund-reason-top-right">
					<view @tap="isAllowApplyRefund">
						<text v-if="!notSelected" class="theme-color">（必选）</text>
						<text v-if="!notSelected" class="not-select">请选择</text>
						<text v-if="notSelected"
							class="theme-color">{{ cancelOrderNoReasonList[oldCancelOrderNoReason].name }}</text>
					</view>
					<van-icon name="arrow" />
				</view>
			</view>
			<view class="textarea-uploader">
				<textarea placeholder="补充详细退款原因，有利于商家更快的帮您处理" @input="bindRefundReasonDescription"
					placeholder-class="textarea-placeholder" :disabled="textareaDisabled"></textarea>
				<view class="flex_between">
					<text></text>
					<text>{{ uploaderImages.length }}/3</text>
				</view>
				<view class="uploader-images-view">
					<view class="uploaderItems" v-for="(img, index) in uploaderImages" :key="index">
						<image :src="img" :data-index="index" :data-imgs="uploaderImages" @tap="viewImage"
							class="uploader-imgs-item" mode="aspectFill"></image>

						<text class="iconfont icon55 uploader-close-img" @tap="closeImage" :data-index="index"></text>
					</view>
					<text class="iconfont iconshangchuantupian1" @tap="uploadImage"
						v-if="uploaderImages.length <= 2"></text>
				</view>
			</view>
		</view>

		<view class="apply-refund">
			<view class="top_tips">
				<text class="refund-type">({{ refundJson.name }})</text>
			</view>
			<view class="apply-refund-bottom">
				<view class="refund-price">
					<text>退款金额</text>
					<text>￥{{ order.actualPrice }}</text>
				</view>
				<view class="refund-submit theme-bg" @tap="submitApplyRefund">
					<view>提交</view>
				</view>
			</view>
		</view>

		<van-action-sheet :show="choiceReasonApplyDialog" title="选择取消原因" @close="close" @cancel="close" z-index="1">
			<view class="content_box">
				<scroll-view style="height: 55vh" scroll-y>
					<radio-group class="choiceReason-radio-group" @change="radioChange">
						<label
							:class="'choiceReason-lable ' + (cancelOrderNoReasonList.length - 1 != index ? 'choiceReason-border' : '')"
							v-for="(item, index) in cancelOrderNoReasonList" :key="index">
							{{ item.name }}

							<radio :value="index" :checked="item.checked"></radio>
						</label>
					</radio-group>
				</scroll-view>
				<view class="content_box_footer">
					<view class="good-choice-btn theme-bg" @tap="cancelOrderNoReasonApply">确定</view>
				</view>
			</view>

		</van-action-sheet>
		<van-action-sheet :show="customerServiceWechatDialog" title="客服二维码" @close="close" @cancel="close">
			<view class="content_box">
				<scroll-view style="height: 35vh" scroll-y>
					<view class="wechat-code-view">
						<image :src="current.customerServiceWechatQrcode" :show-menu-by-longpress="true"
							mode="aspectFit" class="customerServiceWechatQrcode" @tap="viewQrCodeImage"></image>
						<view class="customerServiceWechat">客服微信号：{{ current.customerServiceWechat }}</view>
					</view>
				</scroll-view>
			</view>
		</van-action-sheet>
	</view>
</template>

<script>
	import https from '../../../../../../utils/http';
	import GlobalConfig from '../../../../../../utils/global-config';
	import authService from '../../../../../../utils/auth';
	import toastService from '../../../../../../utils/toast.service';
	import utilHelper from '../../../../../../utils/util';
	import dateHelper from '../../../../../../utils/date-helper';
	import systemStatus from '../../../../../../utils/system-status';
	let app = null;
	var submitImages = [];
	var orderRefundGoodsListStr = [];
	var isCouponsDiscountPrice;
	export default {
		data() {
			return {
				uploaderImages: [],
				choiceReasonApplyDialog: false,
				customerServiceWechatDialog: false,
				notSelected: false,
				isSelectAll: true,
				textareaDisabled: false,
				refundReasonApplyList: [{
						value: 1,
						name: '订单信息拍错（规格/尺码/颜色等）',
						checked: false
					},
					{
						value: 2,
						name: '我不想要了',
						checked: false
					},
					{
						value: 3,
						name: '地址/电话信息填写错误',
						checked: false
					},
					{
						value: 4,
						name: '没用/少用优惠',
						checked: false
					},
					{
						value: 5,
						name: '缺货',
						checked: false
					}
				],
				refundOnlyList: [{
						value: 31,
						name: '缺货',
						checked: false
					},
					{
						value: 32,
						name: '协商一致退款',
						checked: false
					},
					{
						value: 33,
						name: '未按约定时间发货',
						checked: false
					},
					{
						value: 34,
						name: '拍错/多拍/不想要',
						checked: false
					},
					{
						value: 35,
						name: '其他',
						checked: false
					}
				],

				returnGoodsWithRefundList: [{
						value: 61,
						name: '效果不好/不喜欢',
						checked: false
					},
					{
						value: 62,
						name: '材质不符',
						checked: false
					},
					{
						value: 63,
						name: '尺寸不符',
						checked: false
					},
					{
						value: 64,
						name: '外观破损',
						checked: false
					},
					{
						value: 65,
						name: '颜色/款式图案与描述不符',
						checked: false
					},
					{
						value: 66,
						name: '物流问题',
						checked: false
					},
					{
						value: 67,
						name: '和预期不符',
						checked: false
					},
					{
						value: 68,
						name: '服务承诺/态度',
						checked: false
					},
					{
						value: 69,
						name: '质量问题',
						checked: false
					},
					{
						value: 70,
						name: '其他',
						checked: false
					}
				],
				cancelOrderNoReasonList: [],
				refundJson: {
					name: ''
				},
				order: {
					description: '',
					actualPrice: '',
					reducedPrice: 0,
					deliveryFee: ''
				},
				orderDetailList: '',
				oldCancelOrderNoReason: '',
				cancelOrderNoReason: '',
				refundReasonDescription: '',
				current: {
					customerServiceWechatQrcode: '',
					customerServiceWechat: ''
				},
				name: '',
				img: ''
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			var orderId = options.orderId;
			var refundType = options.type;
			console.log('退款类型0未完成退款，1只退款不退货，2退款退货', refundType);
			var refundJson = {
				type: refundType
			};
			refundJson.name = '申请退款';
			var cancelOrderNoReasonList = this.refundReasonApplyList;
			if (refundType == 1) {
				refundJson.name = '仅退款';
				cancelOrderNoReasonList = this.refundOnlyList;
			}
			if (refundType == 2) {
				refundJson.name = '退货退款';
				cancelOrderNoReasonList = this.returnGoodsWithRefundList;
			}
			console.log(cancelOrderNoReasonList)
			this.refundJson = refundJson;
			this.cancelOrderNoReasonList = cancelOrderNoReasonList;
			this.getOrderDetail(orderId);
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
		onUnload: function() {
			var pages = getCurrentPages();
			var prevPage = pages[pages.length - 2]; //上一个页面
			prevPage.$vm.getOrderDetail(this.order.id);
		},
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
			getOrderDetail(orderId) {
				toastService.showLoading();
				https.request('/rest/member/pointsMall/order/selectById', {
					id: orderId
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						const status = result.data.order.status;
						const createTime = result.data.order.createTime;
						result.data.order.statusText = systemStatus.statusText(status);
						result.data.order.createTime = dateHelper.fmtDate(createTime);
						//解析订单商品的规格
						result.data.orderDetailList.forEach((orderDetailList) => {
							let specListAnalysis = '';
							for (var key in JSON.parse(orderDetailList.specList)) {
								specListAnalysis = (specListAnalysis ? specListAnalysis + '/' :
									specListAnalysis) + JSON.parse(orderDetailList.specList)[key];
							}
							orderDetailList.specListAnalysis = specListAnalysis;
							orderDetailList.mainImage = GlobalConfig.ossUrl + orderDetailList.mainImage;
							orderDetailList.checked = true;
							orderDetailList.oldNumber = orderDetailList.number;
							//actualPrice=actualPrice+(orderDetailList.price*orderDetailList.number);
						});

						result.data.order.oldActualPrice = result.data.order.actualPrice;
						//result.data.order.actualPrice=actualPrice;
						this.setData({
							order: result.data.order,
							orderDetailList: result.data.orderDetailList
						});
					}
				});
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

			viewQrCodeImage: function(event) {
				console.log(this.current.customerServiceWechatQrcode);
				//图片预览
				uni.previewImage({
					current: this.current.customerServiceWechatQrcode,
					// 当前显示图片的http链接
					urls: [this.current.customerServiceWechatQrcode] // 需要预览的图片http链接列表
				});
			},

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
				const url = GlobalConfig.baseUrl + '/rest/member/uploadSingleImage';
				https
					.uploadImage({
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
					})
					.then((uploadTask) => {
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

			isAllowApplyRefund() {
				this.setData({
					choiceReasonApplyDialog: this.choiceReasonApplyDialog ? false : true,
					cancelOrderNoReasonList: this.cancelOrderNoReasonList
				});
			},

			customerServiceWechat() {
				this.selectCurrent();
				this.setData({
					customerServiceWechatDialog: this.customerServiceWechatDialog ? false : true
				});
			},

			cancelOrderNoReasonApply() {
				if (!this.cancelOrderNoReason) {
					toastService.showToast('请选择退款原因');
					return;
				}
				this.cancelOrderNoReasonList[this.cancelOrderNoReason].checked = true;
				this.setData({
					notSelected: true,
					choiceReasonApplyDialog: this.choiceReasonApplyDialog ? false : true,
					oldCancelOrderNoReason: this.cancelOrderNoReason,
					cancelOrderNoReasonList: this.cancelOrderNoReasonList
				});
			},

			radioChange(e) {
				this.setData({
					cancelOrderNoReason: e.detail.value
				});
			},

			bindSelectAll(e) {
				let isSelectAll = this.isSelectAll ? false : true;
				let actualPrice = 0;
				console.log('点击全选按钮框变化的值===》' + isSelectAll);
				if (isSelectAll) {
					this.orderDetailList.forEach((item, index) => {
						item.checked = true;
						item.number = item.oldNumber;
						actualPrice = this.order.oldActualPrice;
					});
				}
				if (!isSelectAll) {
					this.orderDetailList.forEach((item, index) => {
						item.checked = false;
						actualPrice = 0;
					});
				}
				this.order.actualPrice = utilHelper.toFixed(actualPrice, 2);
				console.log(this);
				this.orderDetailList = this.orderDetailList;
				this.order = this.order;
				this.isSelectAll = isSelectAll;
			},

			checkboxChange(e) {
				let checkeds = e.detail.value;
				let items = this.orderDetailList;
				let oldActualPrice = this.order.oldActualPrice;

				//遍历商品的状态都设置为未选择
				for (var order in items) {
					items[order].checked = false;
				}
				//1、获取已选择的商品总额度
				let actualPrice = 0;
				let fullActualPrice = 0; //满足满减金额
				let limitedPrice = this.order.limitedPrice;
				console.log('满足满减金额limitedPrice===>' + limitedPrice);
				//满减价格
				let reducedPrice = this.order.reducedPrice;
				console.log('满足满减价格reducedPrice===>' + reducedPrice);
				let packingCharges = 0;
				let goodsTotalQuantity = 0;
				for (var i in checkeds) {
					items[checkeds[i]].checked = true;
					//商品的价格
					let price = utilHelper.toFixed(items[checkeds[i]].price * items[checkeds[i]].number, 2);
					if (items[checkeds[i]].isUsedCoupons) {
						price = price - items[checkeds[i]].couponsDiscountPrice;
					}
					//获取当前商品的包装费并加上选择的商品的包装费
					packingCharges = utilHelper.toFixed(packingCharges + items[checkeds[i]].packingCharges * items[
						checkeds[i]].number, 2);
					//计算后的总额加上当前商品的价格
					actualPrice = utilHelper.toFixed(actualPrice + price, 2);
					//判断如果存在满减则进行商品分摊
					if (this.order.fullReductionRuleId) {
						let limitedPrice_price = utilHelper.toFixed(reducedPrice / limitedPrice, 2);
						console.log('商品价格除以满减价格===>' + limitedPrice_price);
						let sharePrice = utilHelper.toFixed(price * limitedPrice_price, 2);
						console.log(items[checkeds[i]].goodsName + '==x' + items[checkeds[i]].number + '=========>' +
							sharePrice);
						fullActualPrice = utilHelper.toFixed(utilHelper.toFixed(fullActualPrice + sharePrice, 2), 2);
					}
					goodsTotalQuantity += items[checkeds[i]].number;
				}
				actualPrice += packingCharges;
				console.log('获取已选择的商品加上包装费的额度====>' + actualPrice);
				//4、然后遍历已选择的商品，查询是否使用了优惠券，获取第三部的额度
				//如果没使用则不做任何操作
				//如果使用了优惠券则加上优惠券的价格、自动退回优惠券
				// for (let i in items) {
				//   if (items[i].isUsedCoupons&&items[i].checked) {
				//     console.log("获取优惠券金额"+items[i].couponsDiscountPrice)
				//     actualPrice -= items[i].couponsDiscountPrice;
				//   }
				// }

				//我现在部分退款，我退40元，其不满足满减规则，那么需要计算满减优惠分摊金额40*(0.4)=16元，40-16=24元，
				//那么其它三件商品是花费了56元，而三件商品的原价其实是60元，所以这三件商品其实总共分摊了满减优惠金额4元
				if (actualPrice >= limitedPrice) {
					actualPrice = actualPrice - reducedPrice;
				} else {
					actualPrice -= fullActualPrice;
				}

				//actualPrice=actualPrice-this.data.order.reducedPrice;
				console.log('获取已选择的商品总额度====>' + actualPrice);
				console.log('获取已选择的商品包装费====>' + packingCharges);
				console.log('获取已选择的满减后的额度====>' + fullActualPrice);
				console.log('获取满减之后的额度====>' + actualPrice);
				this.order.actualPrice = utilHelper.toFixed(actualPrice, 2);
				if (goodsTotalQuantity == this.order.goodsTotalQuantity) {
					this.order.actualPrice += this.order.deliveryFee;
				}
				this.setData({
					orderDetailList: items,
					order: this.order,
					isSelectAll: checkeds.length != this.orderDetailList.length ? false : true
				});
			},

			/*点击减号*/
			bindMinus: function(e) {
				toastService.showLoading();
				let goodsId = e.currentTarget.dataset.goodsid;
				let number = e.currentTarget.dataset.number;
				let that = this;
				let actualPrice = 0;
				let packingCharges = 0;
				let fullActualPrice = 0;
				let oldActualPrice = this.order.oldActualPrice;
				//满足满减金额
				let limitedPrice = this.order.limitedPrice;
				//满减价格
				let reducedPrice = this.order.reducedPrice;
				let checkedBox = 0;
				this.orderDetailList.forEach((item, index) => {
					if (item.checked) {
						if (item.goodsId == goodsId) {
							item.number--;
							checkedBox = index;
						}
						//商品的价格
						let price = utilHelper.toFixed(item.price * item.number, 2);
						if (item.isUsedCoupons) {
							price = price - item.couponsDiscountPrice;
						}
						//获取当前商品的包装费并加上选择的商品的包装费
						packingCharges = packingCharges + item.packingCharges * item.number;
						//计算后的总额加上当前商品的价格
						actualPrice = actualPrice + price;
						if (this.order.fullReductionRuleId) {
							let limitedPrice_ = utilHelper.toFixed(reducedPrice / limitedPrice, 2);
							let sharePrice = utilHelper.toFixed(price * limitedPrice_, 2);
							console.log(item.goodsName + '==x' + item.number + '=========>' + sharePrice);
							fullActualPrice = utilHelper.toFixed(fullActualPrice + sharePrice, 2);
						}
					}
				});
				console.log('获取点击的下标值===>' + checkedBox);
				console.log('获取已选商品的总额度===>' + actualPrice);
				console.log('获取已选商品的包装费===>' + packingCharges);
				console.log('获取已选商品的分摊费===>' + fullActualPrice);
				actualPrice += packingCharges;
				console.log('是否进行优惠券加减的操作====>' + that.orderDetailList[checkedBox].isUsedCoupons);
				// if (self.data.orderDetailList[checkedBox].isUsedCoupons
				//   &&self.data.orderDetailList[checkedBox].checked) {
				//     //self.data.orderDetailList[checkedBox].isUsedCoupons=false;
				//   actualPrice = actualPrice - self.data.orderDetailList[checkedBox].couponsDiscountPrice;
				// }

				if (actualPrice >= limitedPrice) {
					actualPrice = actualPrice - reducedPrice;
				} else {
					actualPrice -= fullActualPrice;
				}
				that.order.actualPrice = utilHelper.toFixed(actualPrice, 2);

				// if (actualPrice > oldActualPrice) {
				//   self.data.order.actualPrice = oldActualPrice;
				// } else {
				//   self.data.order.actualPrice = utilHelper.toFixed(actualPrice,2);
				// }
				console.log('总金额加上包装费减去分摊费 总金额===>' + actualPrice);
				if (number == 1) {
					that.orderDetailList[checkedBox].checked = false;
					that.orderDetailList[checkedBox].number = number;
					that.setData({
						order: that.order,
						orderDetailList: that.orderDetailList,
						isSelectAll: false
					});
					toastService.hideLoading();
					return;
				}
				that.orderDetailList[checkedBox].number = number - 1;
				that.setData({
					orderDetailList: that.orderDetailList,
					order: that.order
				});
				toastService.hideLoading();
			},

			/*点击加号*/
			bindPlus: function(e) {
				toastService.showLoading();
				let goodsId = e.currentTarget.dataset.goodsid;
				let number = e.currentTarget.dataset.number;
				let oldNumber = e.currentTarget.dataset.oldnumber;
				let index = e.currentTarget.dataset.index;
				console.log('当前商品的选择数量====>' + number);
				console.log('当前商品的原始数量====>' + oldNumber);
				if (oldNumber == number && this.orderDetailList[index].checked) {
					toastService.showToast('您只点了' + number + '份哦');
					return;
				}
				let actualPrice = 0;
				let packingCharges = 0;
				let fullActualPrice = 0;
				let goodsTotalQuantity = 0;
				let oldActualPrice = this.order.oldActualPrice;
				//满足满减金额
				let limitedPrice = this.order.limitedPrice;
				//满减价格
				let reducedPrice = this.order.reducedPrice;
				let checkedBox = 0;
				let that = this;
				this.orderDetailList.forEach((item, index) => {
					if (item.goodsId == goodsId) {
						if (!item.checked) {
							item.number--;
							item.checked = true;
						} else {
							number += 1;
						}
					}
					if (item.checked) {
						if (item.goodsId == goodsId) {
							item.number++;
							checkedBox = index;
						}
						//商品的价格
						let price = utilHelper.toFixed(item.price * item.number, 2);
						if (item.isUsedCoupons) {
							price = price - item.couponsDiscountPrice;
						}
						//获取当前商品的包装费并加上选择的商品的包装费
						packingCharges = packingCharges + item.packingCharges * item.number;
						//计算后的总额加上当前商品的价格
						actualPrice = utilHelper.toFixed(actualPrice + price, 2);
						if (this.order.fullReductionRuleId) {
							let limitedPrice_ = utilHelper.toFixed(reducedPrice / limitedPrice, 2);
							let sharePrice = utilHelper.toFixed(price * limitedPrice_, 2);
							console.log(item.goodsName + '==x' + item.number + '=========>' + sharePrice);
							fullActualPrice = utilHelper.toFixed(fullActualPrice + utilHelper.toFixed(
								sharePrice, 2), 2);
						}
						goodsTotalQuantity += item.number;
					}
				});
				console.log('获取点击的下标值===>' + checkedBox);
				console.log('获取已选商品的总额度===>' + actualPrice);
				console.log('获取已选商品的包装费===>' + packingCharges);
				console.log('获取已选商品的分摊费===>' + fullActualPrice);
				actualPrice += packingCharges;
				console.log('是否进行优惠券加减的操作====>' + that.orderDetailList[checkedBox].isUsedCoupons);
				// if (!self.data.orderDetailList[checkedBox].isUsedCoupons
				//   &&self.data.orderDetailList[checkedBox].checked) {
				//     console.log("进行了优惠券金额的操作")
				//   //self.data.orderDetailList[checkedBox].isUsedCoupons=true;
				//   actualPrice = actualPrice - self.data.orderDetailList[checkedBox].couponsDiscountPrice;
				// }
				if (actualPrice >= limitedPrice) {
					actualPrice = actualPrice - reducedPrice;
				} else {
					actualPrice -= fullActualPrice;
				}
				// if (actualPrice > oldActualPrice) {
				//   self.data.order.actualPrice = oldActualPrice;
				// } else {
				//   self.data.order.actualPrice = utilHelper.toFixed(actualPrice,2);
				// }
				console.log('总金额加上包装费减去分摊费 总金额===>' + actualPrice);
				that.order.actualPrice = utilHelper.toFixed(actualPrice, 2);
				//如果订单总金额等于支付金额则加上配送费
				console.log('计算选中的商品数量' + goodsTotalQuantity);
				console.log('实际的商品数量' + that.order.goodsTotalQuantity);
				if (goodsTotalQuantity == that.order.goodsTotalQuantity) {
					that.order.actualPrice += that.order.deliveryFee;
				}
				that.orderDetailList[checkedBox].number = number;
				that.setData({
					orderDetailList: that.orderDetailList,
					order: that.order,
					isSelectAll: true
				});
				toastService.hideLoading();
			},

			bindRefundReasonDescription(e) {
				this.setData({
					refundReasonDescription: e.detail.value
				});
			},

			submitApplyRefund() {
				orderRefundGoodsListStr = [];
				this.orderDetailList.forEach((i, index) => {
					if (i.checked) {
						orderRefundGoodsListStr.push({
							orderDetailId: i.id,
							number: i.number
						});
					}
				});
				if (orderRefundGoodsListStr.length <= 0) {
					toastService.showToast('请选择要退款的商品');
					return;
				}
				if (!this.notSelected || !this.oldCancelOrderNoReason) {
					toastService.showToast('请选择退款原因');
					return;
				}
				let data = {
					orderRefundGoodsListStr: JSON.stringify(orderRefundGoodsListStr),
					id: this.order.id,
					orderRefund: {
						refundReason: this.cancelOrderNoReasonList[this.oldCancelOrderNoReason].value,
						refundAmount: this.order.actualPrice
					}
				};
				if (submitImages.length > 0) {
					data.orderRefund.evidenceImages = submitImages;
				}
				if (this.refundReasonDescription) {
					data.orderRefund.refundReasonDescription = this.refundReasonDescription;
				}
				let url = '/rest/member/pointsMall/order/applyRefundUndelivered';
				if (this.refundJson.type == 1) {
					url = '/rest/member/pointsMall/order/applyAfterSalesDelivered/onlyRefund';
				}
				if (this.refundJson.type == 2) {
					url = '/rest/member/pointsMall/order/applyAfterSalesDelivered/returnGoodsWithRefund';
				}
				toastService.showModal(
					this.refundJson.name,
					'确定提交申请吗？',
					function confirm() {
						toastService.showLoading();
						https.request(url, data).then((result) => {
							toastService.hideLoading();
							if (result.success) {
								toastService.showSuccess('申请成功');
								// var pages = getCurrentPages();
								// var prevPage = pages[pages.length - 2]; //上一个页面
								// prevPage.getOrderDetail(this.data.order.id);
								uni.navigateBack(1);
							}
						});
					},
					null
				);
			},

			selectCurrent() {
				https.request('/rest/setting/selectCurrent', {}).then((result) => {
					if (result.success) {
						result.data.customerServiceWechatQrcode = GlobalConfig.ossUrl + result.data
							.customerServiceWechatQrcode;
						this.setData({
							current: result.data,
							textareaDisabled: true
						});
					}
				});
			},

			close(e) {
				console.log(e);
				this.setData({
					textareaDisabled: false,
					choiceReasonApplyDialog: false,
					customerServiceWechatDialog: false
				});
			}
		}
	};
</script>
<style>
	page {
		background: #f5f5f5;
		padding-bottom: 150rpx;
	}

	.current-tips {
		padding: 20rpx;
		font-size: 30rpx;
		border-radius: 10rpx;
		font-weight: bold;
		color: #d09650;
	}

	.top-detail {
		margin: 20rpx;
		background-color: white;
		border-radius: 10rpx;
	}

	.top-shop-name {
		font-size: 30rpx;
		padding: 20rpx;
		border-bottom: 1rpx solid #f5f5f5;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.order-mainImage {
		width: 20%;
		height: 140rpx;
		border-radius: 10rpx;
	}

	.commdiy-lists {
		padding: 20rpx;
	}

	.order-detail-list {
		display: flex;
		align-items: center;
		margin-bottom: 20rpx;
	}

	.order-detail {
		width: 68%;
		margin-left: 20rpx;
		height: 140rpx;
	}

	.order-detail view {
		height: 70rpx;
		line-height: 70rpx;
	}

	.item-stepper {
		display: flex;
		justify-content: space-between;
		/* margin-top: 40rpx; */
	}

	.order-goodsName-number {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.order-goodsName {
		font-size: 28rpx;
	}

	.order-number {
		font-size: 28rpx;
	}

	.order-specListAnalysis {
		font-size: 24rpx;
		color: #aeaeae;
	}
	
	/*主容器*/
	.stepper {
		margin-left: 20rpx;
		display: flex;
		align-items: center;
	}

	/*加号和减号*/

	.stepper text {
		width: 45rpx;
		height: 45rpx;
		line-height: 45rpx;
		font-size: 28rpx;
	}

	/*数值*/
	.stepper input {
		width: 24px;
	}

	.order-delivery-fee {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 0 20rpx 20rpx 20rpx;
		font-size: 30rpx;
	}

	.right-gray {
		width: 20rpx;
		height: auto;
		margin-left: 20rpx;
	}

	.refund-reason {
		background-color: white;
		margin: 0 20rpx;
		padding-bottom: 20rpx;
		border-radius: 10rpx;
	}

	.refund-reason-top {
		padding: 20rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
		font-size: 30rpx;
	}

	.refund-reason-top-right {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.not-select {
		color: #aeaeae;
	}

	.textarea-uploader {
		padding: 0 20rpx;
	}

	textarea {
		width: 94%;
		background: #f5f5f5;
		padding: 20rpx;
		height: 100px;
		font-size: 30rpx;
		border-radius: 10rpx;
		margin-bottom: 20rpx;
		z-index: 0;
	}

	.textarea-placeholder {
		font-size: 30rpx;
	}

	.uploader-images-view {
		display: flex;
		align-items: center;
		margin-top: 0rpx;
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

	.uploader-imgs-item {
		width: 100rpx;
		height: 100rpx;
		margin-right: 20rpx;
		border-radius: 10rpx;
	}

	.uploader-close-img {
		width: 30rpx;
		height: 30rpx;
		position: absolute;
		margin-left: 6%;
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

	.choiceReason-radio-group {
		display: flex;
		justify-content: center;
		flex-direction: column;
	}

	.choiceReason-lable {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 20rpx 0;
		font-size: 30rpx;
	}

	.choiceReason-border {
		border-bottom: 1rpx solid #f5f5f5;
	}

	.good-choice-btn {
		width: 100%;
		text-align: center;
		padding: 20rpx 0;
		border-radius: 15rpx;
		font-size: 28rpx;
		font-weight: bold;
	}

	.apply-refund {
		position: fixed;
		bottom: 0;
		margin: 0;
		width: 100%;
	}

	.apply-refund-bottom {
		border-radius: 30rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		height: 88rpx;
		line-height: 88rpx;
		width: 100%;
		margin-bottom: 20rpx;
		/* padding: 20rpx 0; */
		z-index: 999;
	}

	.refund-price {
		background-color: #000000;
		color: white;
		width: 60%;
		text-align: center;
		border-radius: 50rpx 0 0 50rpx;
	}

	.refund-submit {
		width: 30%;
		text-align: center;
		color: white;
		border-radius: 0 50rpx 50rpx 0;
	}

	::-webkit-scrollbar {
		width: 0;
		height: 0;
		color: transparent;
	}

	.iconshangchuantupian1 {
		font-size: 100rpx;
		margin-left: 20rpx;
	}

	.refund-type {
		font-size: 22rpx;
	}

	.wechat-code-view {
		height: 100%;
		text-align: center;
	}

	.customerServiceWechatQrcode {
		width: 100%;
		height: 80%;
	}

	.customerServiceWechat {
		font-size: 30rpx;
	}
</style>