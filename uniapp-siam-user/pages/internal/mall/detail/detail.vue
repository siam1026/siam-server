<template>
	<view>
		<view class="wodejl-view" @tap="bindShare">分享商品>></view>

		<swiper :indicator-dots="indicatorDots" :autoplay="autoplay" class="carousel-swiper" :interval="interval"
			:duration="duration" :indicator-active-color="afterColor">
			<block v-for="(item, index) in carouselUrls" :key="index">
				<swiper-item class="carousel-image">
					<image :src="item" class="carousel-image" mode="scaleToFill" @tap="uploadImage" />
				</swiper-item>
			</block>
		</swiper>
		<view class="commdity-name-type-view">
			<view class="display_flex_between">
				<view class="flex_column commdity-name">
					<text class="name out_of_range two_row">{{ data.name }}</text>
					<text :decode="true" class="price">
						￥{{ priceAfter }}
						<text class="score-money" :decode="true">&nbsp;&nbsp;(可用积分等额兑换)</text>
					</text>
				</view>
				<view class="flex_column collect-in">
					<van-icon name="like-o" :class="'iconfont iconshoucang1 ' + (isCollect ? 'is-collect' : '')"
						@tap="parseEventDynamicCode($event, isCollect ? 'bindDeleteCollect' : 'bindInsertCollect')"
						:data-goodsid="data.id" />
					<text :class="'shoucang-text ' + (isCollect ? 'is-collect' : 'not-collect')">收藏</text>
				</view>
			</view>
		</view>
		<view class="commodity-detail-view">
			<view class="guige-yunfei guige-view">
				<text class="commodity-detail">{{ specListString ? '已选择' : '' }}规格</text>
				<view class="specifications-line flex_end" @tap="openSpecifications" data-type="car">
					<view :class="specListString ? 'guige-specListString theme-color' : 'goods-info-specListString'">
						{{ specListString ? specListString : '未选择商品规格' }}
					</view>
					<van-icon name="arrow" />
				</view>
			</view>
		</view>
		<view class="commodity-detail-view">
			<view class="guige-yunfei">
				<text class="commodity-detail">月销</text>
				<text class="goods-info-specListString guige-specListString"
					:decode="true">{{ data.latelyMonthlySales }}&nbsp;件</text>
			</view>
		</view>
		<view class="commodity-detail-view">
			<view class="guige-yunfei">
				<text class="commodity-detail">运费</text>
				<text class="goods-info-specListString guige-specListString">免运费</text>
			</view>
		</view>

		<view class="commodity-detail-view">
			<view class="detail-title">
				<view>商品详情</view>
			</view>
			<view class="commodity-detail">
				<text>{{ data.detail ? data.detail : '暂无详情' }}</text>
			</view>
		</view>
		<view class="commdity-image-list">
			<image mode="widthFix" :src="item" class="detail-images" v-for="(item, index) in detailImages" :key="index">
			</image>
		</view>
		<text style="padding-bottom: 100rpx"></text>
		<view class="shopping-cart-detail">
			<van-action-sheet :show="shoppingCartDialog" @close="close" @cancel="close" title="已选商品">
				<view class="content_box">
					<scroll-view style="height: 55vh" scroll-y>
						<view class="shoppingCart-item" v-for="(item, index) in shoppingCartList" :key="index">
							<view class="goodsName-restructure-view">
								<view class="goodsName out_of_range one_row">{{ item.goodsName }}</view>
								<view class="restructure out_of_range one_row">{{ item.restructure }}</view>
							</view>

							<view class="goodsPrice-number-view">
								<view class="goodsPrice">￥{{ item.price }}</view>
								<view class="stepper">
									<text class="reduce-class" @tap="bindMinus" :data-cartId="item.id"
										:data-number="item.number">－</text>
									<input disabled type="number" :value="item.number" class="reduce-class" />
									<text @tap="bindPlus" class="add-class"
										:data-num="index + ',' + item.number">＋</text>
								</view>
							</view>
						</view>
					</scroll-view>
				</view>
			</van-action-sheet>
			<van-action-sheet :show="specificationsDialog" @close="close" @cancel="close" title="选择规格">
				<view class="content_box">
					<view class="goods-info-view">
						<image :src="data.mainImage" mode="aspectFill" class="commodity-image"></image>
						<view>
							<view class="goods-info-name out_of_range one_row">{{ data.name }}</view>
							<view class="goods-info-specListString">已选：{{ specListString }}</view>
							<view class="goods-info-price">￥{{ priceAfter }}</view>
						</view>
					</view>
					<scroll-view scroll-y style="height: 50vh">
						<view class="commdity-name-type-view">
							<!-- <view class="commdity-name">{{data.name}}</view> -->
							<view class="commdity-type-item" v-for="(item, key) in specList" :key="key">
								<view class="commdity-type-name">{{ key }}</view>

								<radio-group class="radio-group" @change="radioChange" :data-firstIndex="key">
									<label :class="
                                            'group-label theme-border ' +
                                            (!item.stock ? 'disabled-group-label' : '') +
                                            ' ' +
                                            (item.checked ? 'active theme-bg' : 'theme-color-border') +
                                            ' out_of_range one_row'
                                        " v-for="(item, index) in item" :key="index">
										<radio :value="index" :checked="item.checked" :disabled="!item.stock"
											class="radio" />

										<view class="out_of_range one_row">{{ item.name }}</view>
									</label>
								</radio-group>
							</view>
							<van-empty v-if="specList.length <= 0" description="暂无规格"></van-empty>
						</view>
					</scroll-view>
					<view slot="footer">
						<view class="good-choice-btn theme-bg" @tap="chooseSpecifications">我选好了</view>
					</view>
				</view>
			</van-action-sheet>
		</view>
		<view class="safe-area shopping-detail-view">
			<view class="top_tips">
				<view v-if="fullPriceReductionIsHidden">满减：{{ fullReductionRuleName }}</view>
			</view>
			<view class="buy-shopping-cart-view">
				<view class="cz-button">
					<view class="immediate-purchase theme-bg" @tap="openSpecifications" data-type="pay"
						hover-class="hover-class-public">
						立即购买(￥{{ fullPriceReductionIsHidden ? fullPriceReduction : data.price }})
					</view>
					<view class="add-to-cart theme-color-border" hover-class="hover-class-public"
						@tap="openSpecifications" data-type="car">加入购物车</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import GlobalConfig from '../../../../utils/global-config';
	import https from '../../../../utils/http';
	import authService from '../../../../utils/auth';
	import toastService from '../../../../utils/toast.service';
	import utilHelper from '../../../../utils/util';
	import dateHelper from '../../../../utils/date-helper';
	//获取应用实例
	let app = null;
	var totalPrice = 0;
	var totalNum = 0;
	export default {
		data() {
			return {
				carouselUrls: [],
				indicatorDots: true,
				autoplay: true,
				interval: 5000,
				duration: 1000,
				//beforeColor: "white",//指示点颜色,
				afterColor: 'white',
				//当前选中的指示点颜色
				commodityNum: 1,
				totalPrice: 0,
				shoppingCartDialog: false,
				specificationsDialog: false,
				userInfo: {},
				priceDifference: 0,
				shoppingCartList: [],
				chooseType: 'init',
				specList: '',
				specListString: '',
				priceAfter: '',
				fullPriceReduction: '',
				fullPriceReductionIsHidden: '',
				fullReductionRuleName: '',
				winHeight: '',
				fullReductionRuleList: '',
				data: {
					name: '',
					id: '',
					latelyMonthlySales: '',
					detail: false,
					mainImage: '',
					price: ''
				},
				detailImages: '',
				goodsId: '',
				repData: '',
				collectInfo: '',
				isCollect: ''
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			app = getApp();
			this.userInfo = app.globalData.loginUserInfo;
			this.getCommodityDetails(options.id);
			this.getGoodsCollect(options.id);
			this.autoHeigth();
			//console.log(app.globalData.loginUserInfo)
			this.setData({
				userInfo: app.globalData.loginUserInfo
			});

			// this.chooseSpecifications();
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
			radioChange(e) {
				//获取复选框选中的下标值
				var checkValue = e.detail.value;
				//获取第一级分类的下标值
				let firstIndex = e.currentTarget.dataset.firstindex;
				//获取所有分类信息
				let specList = this.specList;
				//console.log(specList)
				//遍历分类信息给第一级分类为false，提交的时候对应各级分类
				for (var j in specList[firstIndex]) {
					specList[firstIndex][j].checked = false;
				}
				//给选中的第二级分类的checked设置为true
				// for (var i in checkValue) {
				//     specList[firstIndex][checkValue[i]].checked = true;
				//     //console.log(specList[firstIndex][checkValue[i]])
				// }
				specList[firstIndex][checkValue].checked = true;

				let price = this.data.price;
				let specListString = '';
				for (let key in specList) {
					for (let keyof in specList[key]) {
						//console.log(specList[key][keyof].price)
						if (specList[key][keyof].checked) {
							price = price + specList[key][keyof].price;
							specListString = (specListString ? specListString + '/' : specListString) + specList[key][
								keyof
							].name;
						}
					}
				}
				console.log(specListString);
				let totalPrice = price * this.commodityNum;
				let fullPriceReduction = 0;
				let fullReductionRuleName = '';
				let fullPriceReductionIsHidden = false;
				let limitedPrice = 0;
				for (let i = 0; i < this.fullReductionRuleList.length; i++) {
					//console.log(this.data.fullReductionRuleList[i].limitedPrice)
					if (totalPrice >= this.fullReductionRuleList[i].limitedPrice) {
						if (this.fullReductionRuleList[i].limitedPrice > limitedPrice) {
							limitedPrice = this.fullReductionRuleList[i].limitedPrice;
							fullPriceReduction = totalPrice + 0 - this.fullReductionRuleList[i].reducedPrice;
							fullReductionRuleName = this.fullReductionRuleList[i].name;
							fullPriceReductionIsHidden = true;
						}
					}
				}
				this.setData({
					specList: specList,
					specListString: specListString,
					priceAfter: price,
					totalPrice: utilHelper.toFixed(totalPrice, 2),
					fullPriceReduction: utilHelper.toFixed(fullPriceReduction, 2),
					fullPriceReductionIsHidden: fullPriceReductionIsHidden,
					fullReductionRuleName: fullReductionRuleName
				});
			},

			//事件处理函数
			/*点击减号*/
			bindMinus: function(e) {
				toastService.showLoading();
				var that = this;
				let cartId = e.currentTarget.dataset.cartid;
				let number = e.currentTarget.dataset.number;
				//点击减号后,当前商品的数量小于1,就进行删除该商品
				//重新计算被选中的商品的总金额
				totalPrice = 0;
				if (number == 1) {
					toastService.hideLoading();
					toastService.showModal(null, '确定不要这个了吗？', function confirm() {
						toastService.showLoading();
						that.updateNumber(cartId, 1, 0, function callback() {
							totalNum--;
							if (that.shoppingCartList.length == 1) {
								that.setData({
									shoppingCartDialog: false
								});
							}
							that.getShoppingCartList();
						});
					});
					return;
				}
				this.updateNumber(cartId, 1, 0, function callback() {
					totalNum--;
					that.getShoppingCartList();
				});
			},

			/*点击加号*/
			bindPlus: function(e) {
				toastService.showLoading();
				var that = this;
				let numList = e.currentTarget.dataset.num.split(',');
				let items = this.shoppingCartList;
				totalPrice = 0;
				items[numList[0]].number = Number(numList[1]) + 1; //当前商品的数量+1
				if (items[numList[0]].disable) {
					return;
				}
				this.updateNumber(items[numList[0]].id, 1, 1, function callback() {
					//重新计算被选中的商品的总金额
					totalNum++;
					that.getShoppingCartList();
				});
			},
			updateNumber(id, number, type, callbak) {
				https.request('/rest/member/shoppingCart/updateNumber', {
					id: id,
					number: number,
					type: type
				}).then((result) => {
					if (result.success) {
						callbak();
					}
				});
			},

			insertShoppingCart(e) {
				// if(!goodsSpecs){
				//   toastService.showToast("请选择规格");
				//   return
				// }
				toastService.showLoading();
				authService.checkIsLogin().then((result) => {
					toastService.hideLoading();
					if (result) {
						let goodsSpecs = {};
						let specList = this.specList;
						for (let key in specList) {
							for (let keyof in specList[key]) {
								//拼接查询规格等的json数据格式，查询商品规格等对应的价格
								if (specList[key][keyof].checked) {
									goodsSpecs[key] = specList[key][keyof].name;
								}
							}
						}
						//console.log(goodsSpecs)
						let data = {
							goodsId: this.data.id,
							specList: JSON.stringify(goodsSpecs)
						};
						https.request('/rest/member/pointsMall/shoppingCart/insert', data, authService.getToken())
							.then((result) => {
								if (result.success) {
									toastService.showToast('加入购物车成功');
									this.setData({
										specificationsDialog: false
									});
								}
							});
						return;
					}
					app.globalData.checkIsAuth('scope.userInfo');
				});
			},

			goToPay() {
				let self = this;
				authService.checkIsLogin().then((result) => {
					if (result) {
						self.toPay();
						return;
					}
					app.globalData.checkIsAuth('scope.userInfo');
				});
			},

			toPay() {
				var data = this.data;
				var specList = this.specList;
				var orderDetail = {};
				var specLists = {};
				var restructure = '';
				//console.log(specList)
				if (data.status == 4) {
					toastService.showToast('商品已售罄');
					return;
				}
				//拼接数据
				for (let key in specList) {
					for (let keyof in specList[key]) {
						//拼接查询规格等的json数据格式，查询商品规格等对应的价格
						if (specList[key][keyof].checked) {
							specLists[key] = specList[key][keyof].name;
							restructure = (restructure ? restructure + '/' : restructure) + specList[key][keyof].name;
						}
					}
				}
				//设置和在购物车提交时的数据格式一样
				orderDetail.actualPrice = this.totalPrice;
				orderDetail.packingCharges = 0;
				orderDetail.orderDetailList = [];
				orderDetail.orderDetailList.push({
					goodsId: data.id,
					specList: JSON.stringify(specLists),
					number: this.commodityNum,
					goodsName: data.name,
					restructure: restructure,
					price: this.priceAfter,
					packingCharges: 0
				});
				console.log(orderDetail);
				uni.navigateTo({
					url: '../pay/pay?payType=' + this.chooseType + '&orderDetail=' + JSON.stringify(orderDetail)
				});
			},

			autoHeigth() {
				var that = this;
				//获取用户手机系统信息
				uni.getSystemInfo({
					success: function(res) {
						let winHeight = res.windowHeight; //获取高度
						//获取class为settlement-view并减去这个高度，自适应scrollview的高度
						const query = uni.createSelectorQuery();
						query.select('.shopping-cart-detail').boundingClientRect();
						query.selectViewport().scrollOffset();
						query.exec(function(res) {
							that.setData({
								winHeight: winHeight - (res[0].height + 5)
							});
						});
					}
				});
			},

			closeShoppingCart: function() {
				this.setData({
					shoppingCartDialog: false
				});
			},

			openShoppingCart() {
				authService.checkIsLogin().then((result) => {
					if (result) {
						if (this.shoppingCartList.length > 0) {
							this.setData({
								shoppingCartDialog: this.shoppingCartDialog ? false : true
							});
						}
						return;
					}
					app.globalData.checkIsAuth('scope.userInfo');
				});
			},

			//获取满减规则
			getFullReductionRule(commodityDetails) {
				https.request('/rest/pointsMall/fullReductionRule/list', {
					pageNo: -1,
					pageSize: 1
				}).then((result) => {
					if (result.success) {
						let fullPriceReduction = 0;
						let fullReductionRuleName = '';
						let fullPriceReductionIsHidden = false;
						let limitedPrice = 0;
						let totalPrice = commodityDetails.price + 0;
						for (let i = 0; i < result.data.records.length; i++) {
							if (totalPrice >= result.data.records[i].limitedPrice) {
								if (result.data.records[i].limitedPrice > limitedPrice) {
									limitedPrice = result.data.records[i].limitedPrice;
									fullPriceReduction = totalPrice - result.data.records[i].reducedPrice;
									fullReductionRuleName = result.data.records[i].name;
									fullPriceReductionIsHidden = true;
								}
							}
						}
						this.setData({
							fullPriceReduction: utilHelper.toFixed(fullPriceReduction, 2),
							fullReductionRuleName: fullReductionRuleName,
							fullPriceReductionIsHidden: fullPriceReductionIsHidden,
							fullReductionRuleList: result.data.records,
							totalPrice: commodityDetails.price
						});
					}
				});
			},

			getCommodityDetails(id) {
				https.request('/rest/pointsMall/goods/selectById', {
					id: id
				}).then((result) => {
					if (result.success && result.data) {
						//获取商品的详细图片，转换以轮播图的数据格式
						let carouselUrls = result.data.subImages.split(',');
						this.carouselUrls = [];
						for (let i = 0; i < carouselUrls.length; i++) {
							this.carouselUrls.push(GlobalConfig.ossUrl + carouselUrls[i]);
						}
						this.detailImages = [];
						if (result.data.detailImages) {
							let detailImages = result.data.detailImages.split(',');
							for (let i = 0; i < detailImages.length; i++) {
								this.detailImages.push(GlobalConfig.ossUrl + detailImages[i]);
							}
							result.data.mainImage = GlobalConfig.ossUrl + result.data.mainImage;
						}
						this.setData({
							data: result.data,
							carouselUrls: this.carouselUrls,
							detailImages: this.detailImages,
							priceAfter: result.data.price
						});
						this.selectByGoodsId(id);
						this.getFullReductionRule(result.data);
						this.chooseSpecifications();
					}
				});
			},
			close() {
				this.setData({
					specificationsDialog: false,
					shoppingCartDialog: false
				});
			},
			openSpecifications(e) {
				console.log(e);
				this.setData({
					specificationsDialog: true,
					goodsId: this.data.id,
					chooseType: e.currentTarget.dataset.type
				});
				this.selectByGoodsId(this.data.id);
			},

			selectByGoodsId(goodsId) {
				https.request('/rest/pointsMall/goodsSpecificationOption/selectByGoodsId', {
					goodsId: goodsId
				}).then((result) => {
					if (result.success && result.data) {
						// if(!result.data){
						//   return;
						// }
						//重新设置商品的规格等数据的格式
						//let goodsSpecs = {};
						let specList = result.data;
						let price = this.data.price;
						let specListString = '';
						for (let key in specList) {
							let isChecked = true;
							for (let keyof in specList[key]) {
								//拼接查询规格等的json数据格式，查询商品规格等对应的价格
								specList[key][keyof].checked = false;
								//设置每个规格的第一个选项为选中，当库存为0时则选中下一个
								if (specList[key][keyof].stock == 1 && isChecked) {
									specList[key][keyof].checked = true;
									//选中的规格价钱在商品价钱的基础上累加
									price = price + specList[key][keyof].price;
									specListString = (specListString ? specListString + '/' : specListString) +
										specList[key][keyof].name;
									isChecked = false;
								}
							}
						}
						this.setData({
							specListString: specListString ? specListString : '暂无规格',
							specList: JSON.stringify(specList) == '{}' ? [] : specList,
							priceAfter: price
						});
					}
				});
			},

			chooseSpecifications(e) {
				toastService.showLoading();
				var _this = this;
				authService.checkIsLogin().then((result) => {
					toastService.hideLoading();
					if (result) {
						let goodsSpecs = {};
						let specList = _this.specList;
						console.log(_this);
						let price = _this.data.price;
						for (let key in specList) {
							for (let keyof in specList[key]) {
								//拼接查询规格等的json数据格式，查询商品规格等对应的价格
								if (specList[key][keyof].checked) {
									goodsSpecs[key] = specList[key][keyof].name;
									//选中的规格价钱在商品价钱的基础上累加
									price = price + specList[key][keyof].price;
								}
							}
						}
						//console.log(goodsSpecs)

						_this.setData({
							repData: JSON.stringify(goodsSpecs),
							specificationsDialog: false
						});
						console.log(_this.chooseType);
						if (this.chooseType == 'pay') {
							_this.goToPay();
						} else if (_this.chooseType == 'car') {
							_this.insertShoppingCart();
						} else {}
						toastService.hideLoading();
						return;
					}
					this.setData({
						specificationsDialog: false
					});
					app.globalData.checkIsAuth('scope.userInfo');
				});
			},

			insertStore() {
				authService.checkIsLogin().then((result) => {
					if (result) {
						let goodsSpecs = {};
						let specList = this.specList;
						for (let key in specList) {
							for (let keyof in specList[key]) {
								//拼接查询规格等的json数据格式，查询商品规格等对应的价格
								if (specList[key][keyof].checked) {
									goodsSpecs[key] = specList[key][keyof].name;
								}
							}
						}
						//console.log(goodsSpecs)
						let data = {
							goodsId: this.data.id,
							specList: JSON.stringify(goodsSpecs),
							number: this.commodityNum
						};
						https.request('/rest/member/shoppingCart/insert', data, authService.getToken()).then((
							result) => {
							if (result.success) {
								toastService.showSuccess('加入成功');
								//wx.navigateBack(1)
							}
						});

						return;
					}
					app.globalData.checkIsAuth('scope.userInfo');
				});
			},

			businessTap(e) {
				uni.navigateTo({
					url: '../../menu/index/index?id=' + e.currentTarget.dataset.id
				});
			},

			getGoodsCollect(goodsId) {
				https.request('/rest/member/pointsMall/goodsCollect/selectByGoodsId', {
						goodsId: goodsId
					}).then((result) => {
						if (result.success) {
							this.setData({
								collectInfo: result.data,
								isCollect: result.data ? true : false
							});
						}
					});
			},

			bindInsertCollect(e) {
				authService.checkIsLogin().then((result) => {
					if (result) {
						https.request('/rest/member/pointsMall/goodsCollect/insert', {
							goodsId: e.currentTarget.dataset.goodsid
						}).then((result) => {
							if (result.success) {
								this.isCollect = true;
								toastService.showSuccess('收藏成功');
							}
						});
						return;
					}
					app.globalData.checkIsAuth('scope.userInfo');
				});
			},

			bindDeleteCollect(e) {
				authService.checkIsLogin().then((result) => {
					if (result) {
						https.request('/rest/member/pointsMall/goodsCollect/delete', {
							goodsId: e.currentTarget.dataset.goodsid
						}).then((result) => {
							if (result.success) {
								this.isCollect = false;
								toastService.showSuccess('取消成功');
							}
						});
						return;
					}
					app.globalData.checkIsAuth('scope.userInfo');
				});
			},

			bindShare(e) {
				authService.checkIsLogin().then((result) => {
					if (result) {
						uni.navigateTo({
							url: '../../../mine/share/index/index?inviterId=' + this.userInfo.id
						});
					} else {
						app.globalData.checkIsAuth('scope.userInfo');
					}
				});
			},

			uploadImage() {
				console.log('占位：函数 uploadImage 未声明');
			}
		}
	};
</script>
<style>
	page {
		height: 100%;
		background: #f5f5f5;
		padding-bottom: 130px;
	}

	.carousel-swiper {
		margin: 20rpx;
		height: 40vh;
	}

	.carousel-swiper-item {
		border-radius: 15rpx;
		height: 100%;
	}

	.carousel-image {
		width: 100%;
		height: 100%;
		border-radius: 15rpx;
	}

	.commdity-xuanze {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.commodity-detail-view {
		background: white;
		margin: 0rpx 20rpx;
		margin-top: 20rpx;
		padding: 20rpx;
		border-radius: 15rpx;
	}

	.detail-title {
		font-size: 30rpx;
		line-height: 80rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.commodity-detail {
		font-size: 26rpx;
		color: #919191;
		word-wrap: break-word;
	}

	.specifications-line {
		width: 80%;
	}

	.shopping-detail-view {
		position: fixed;
		bottom: 0;
		width: 100%;
		background-color: white;
		box-shadow: -2px 0px 5px 0.5px rgba(0, 0, 0, 0.2);
	}

	.buy-shopping-cart-view {
		display: flex;
		justify-items: center;
		justify-content: space-between;
		height: 100rpx;
		margin: 20rpx;
	}

	.immediate-purchase {
		width: 50%;
		font-size: 30rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		margin-right: 20rpx;
		padding: 20rpx;
		border-radius: 40rpx;
	}

	.add-to-cart {
		width: 50%;
		font-size: 30rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		padding: 20rpx;
		border-radius: 40rpx;
	}

	.gwc-iconfont {
		display: flex;
		align-items: center;
		justify-content: center;
		border-radius: 50%;
	}

	.gwc-iconfont .iconfont {
		border-radius: 50%;
		width: 70rpx;
		height: 70rpx;
		line-height: 70rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.cz-button {
		width: 100%;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	/*主容器*/
	.stepper {
		width: auto;
		height: auto;
	}

	/*加号和减号*/
	.stepper text {
		width: 48rpx;
		height: 48rpx;
		line-height: 48rpx;
	}

	/*数值*/
	.stepper input {
		width: 40px;
	}

	.money-icon {
		font-size: 30rpx;
		margin-left: 30rpx;
	}

	.baozhuangfei-class {
		font-size: 24rpx;
		line-height: 40rpx;
		display: flex;
		align-items: center;
	}

	/* hover-class样式，点击态 */
	.hover-class-public {
		opacity: 0.9;
	}

	.insert-view {
		border-radius: 50%;
		width: 38rpx;
		height: 38rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		color: white;
	}

	.shopping-cart-detail {
		position: fixed;
		bottom: 0;
		z-index: 9999;
		width: 100%;
	}

	.shopping-cart-content {
		width: 100%;
		display: flex;
		align-items: center;
		background: #535257;
		z-index: 9999;
		height: 54px;
	}

	.shopping-cart-bg {
		background: #535257;
	}

	.shopping-cart-desc {
		font-size: 24rpx;
		color: #8c8c8e;
	}

	.shopping-cart-totalPrice {
		display: flex;
		align-items: center;
	}

	.fullPriceReductionClass {
		color: gainsboro;
		text-decoration: line-through;
		margin-right: 20rpx;
	}

	.totalPrice {
		color: white;
		font-weight: bold;
	}

	.full-price-reduction {
		color: white;
		font-weight: bold;
	}

	.not-full-price-reduction {
		font-size: 28rpx;
		color: #8c8c8e;
	}

	.fullPriceReductionIsHidden {
		font-size: 28rpx;
	}

	.content-manjian {
		position: relative;
		top: 0;
		background: #fffadc;
		font-size: 24rpx;
		font-weight: bold;
		text-align: center;
		height: 73px;
		z-index: 9999;
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

	.business-recommend-scroll-view {
		height: 269rpx;
		white-space: nowrap;
	}

	.icon-business-recommend-class {
		width: 100%;
		height: 200rpx;
		border-radius: 15rpx 15rpx 0 0;
	}

	.business-recommend-title {
		font-size: 34rpx;
		font-weight: bold;
		margin: 20rpx 20rpx 0 20rpx;
	}

	.business-recommend-scroll-view {
		margin: 20rpx 0rpx;
		background: #fff;
		border-radius: 50rpx;
		height: 100%;
		display: flex;
		align-items: center;
	}

	.business-recommend-scroll-view scroll-view {
		display: block;
		width: 100%;
	}

	.business-recommend-items {
		width: 100%;
		display: flex;
		justify-content: space-between;
	}

	.business-recommend-item {
		width: 32%;
		height: auto;
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
		background: #f5f5f5;
		border-radius: 15rpx;
		margin: 0 10rpx;
	}

	.item-two {
		margin: 0 3.5%;
	}

	.business-recommend-detail-view {
		width: 100%;
		height: auto;
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
	}

	.fullname-class {
		margin-top: 11rpx;
		font-size: 26rpx;
		width: 70%;
	}

	.engname-class {
		font-size: 24rpx;
		color: #ccc;
		width: 90%;
	}

	.business-recommend-money-view {
		width: 90%;
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 15rpx 0 10rpx 0;
	}

	.business-recommend-money {
		font-size: 26rpx;
		font-weight: bold;
	}

	.plus-view {
		font-size: 28rpx;
		width: 38rpx;
		height: 38rpx;
		line-height: 38rpx;
		text-align: center;
		border-radius: 50%;
		color: white;
	}

	.settlement-view {
		position: fixed;
		z-index: 999;
		background: white;
		top: 0;
		border-bottom: 6rpx solid #f5f5f5;
	}

	.goods-info-view {
		display: flex;
		padding-bottom: 20rpx;
		padding-left: 20rpx;
	}

	.goods-info-name {
		font-size: 30rpx;
		font-weight: bold;
	}

	.goods-info-specListString {
		color: #6b6b6b;
		font-size: 24rpx;
	}

	.goods-info-price {
		font-weight: bold;
		height: 88rpx;
		line-height: 88rpx;
		font-size: 32rpx;
		color: #e0583b;
	}

	.specifications-scroll-view {
		height: 274px;
	}

	.clearNull {
		font-weight: 700;
		font-size: 15px;
		color: #80858a;
	}

	.specifications-dialog {
		margin-bottom: 0rpx;
	}

	.commdity-name-type-view {
		padding: 20rpx;
		background: #fff;
		margin: 0 20rpx;
		border-radius: 15rpx;
	}

	.commdity-name {
		font-size: 32rpx;
		font-weight: bold;
		line-height: 50rpx;
		width: 85%;
		align-items: flex-start;
	}

	.commdity-name .name {
		font-size: 30rpx;
	}

	.commdity-name .price {
		font-size: 32rpx;
		font-weight: bold;
	}

	.collect-in {
		width: 15%;
	}

	.commdity-engname {
		font-size: 28rpx;
		line-height: 60rpx;
	}

	.commdity-type-item {
		width: 100%;
		height: 100%;
		display: flex;
		justify-content: left;
		align-items: center;
		flex-wrap: wrap;
		padding-bottom: 10rpx;
	}

	.commdity-type-name {
		font-size: 28rpx;
		margin-right: 30rpx;
	}

	.radio-group {
		width: 80%;
		display: flex;
		justify-content: flex-start;
		align-items: center;
		flex-wrap: wrap;
		padding: 10rpx;
		background: #fff;
		border-radius: 50rpx;
	}

	.group-label {
		width: 28%;
		padding: 12rpx 1rpx;
		margin: 5rpx 5rpx;
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

	/* 商品信息 */
	.commodity-item-view {
		display: flex;
		flex-direction: column;
		/* margin-bottom: 10rpx; */
		background: white;
		border-radius: 10rpx;
		padding-top: 20rpx;
	}

	.commodity-type {
		/* line-height: 64rpx; */
		padding: 10rpx 20rpx;
	}

	.categoryName-view {
		/* width: 35%; */
		font-size: 28rpx;
	}

	.font-white {
		color: white;
	}

	.commodity-item {
		display: flex;
		padding: 20rpx 20rpx 0rpx 20rpx;
		align-items: center;
		border-radius: 5rpx;
	}

	.commodity-image {
		width: 170rpx;
		height: 152rpx;
		border-radius: 8rpx;
		margin-right: 10rpx;
	}

	.commodity-name-view {
		width: 100%;
		display: flex;
		align-items: center;
	}

	.line-view {
		background: #b0b0b0;
		width: 100%;
		height: 2rpx;
	}

	.commodity-name {
		font-size: 28rpx;
		color: #969696;
		font-weight: bold;
	}

	.commodity-english {
		font-size: 24rpx;
		color: #b0b0b0;
	}

	.money-view {
		font-size: 30rpx;
		font-weight: bold;
		margin-top: 30rpx;
	}

	.insert-view {
		border-radius: 50%;
		width: 45rpx;
		height: 45rpx;
		line-height: 45rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		color: white;
	}

	.money-insert-view {
		/* width: 100%; */
		position: absolute;
		/* margin-left: 85%;
   margin-top: 10%; */
		margin-top: -10%;
		right: 5%;
	}

	#space-view {
		height: 20rpx;
		background: white;
	}

	.now-order-image {
		width: 100%;
		height: 100%;
	}

	.is-end-item {
		padding-bottom: 50px;
	}

	.theme-other-bg {
		background: #353535;
		color: #5f5e63;
	}

	.weui-vtabs-bar__item .weui-vtabs-bar__title {
		white-space: normal;
		text-align: center;
	}

	.evaluate-business-info {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.evaluate-info-left {
		width: 80%;
		margin: 20rpx 0rpx;
		padding: 0 50rpx;
		border-right: 1rpx solid #f5f5f5;
	}

	.evaluate-info-right {
		width: 20%;
		padding: 20rpx;
		text-align: center;
	}

	.business-evaluate {
		display: flex;
		justify-content: space-between;
	}

	.evaluate-total-score {
		color: #f56427;
		font-size: 40rpx;
		margin-right: 25rpx;
	}

	.evaluate-total-star {
		color: #4f4f4f;
		font-size: 24rpx;
	}

	.evaluate-total-num {
		color: #4f4f4f;
		font-size: 35rpx;
	}

	.view-line {
		height: 20rpx;
		background: #f3f3f3;
	}

	.evaluate-items {
		padding: 20rpx;
	}

	.evaluate-item {
		display: flex;
		justify-content: space-between;
		padding-bottom: 30rpx;
		border-bottom: 1rpx solid #f5f5f5;
	}

	.evaluate-item-detail {
		width: 90%;
	}

	.evaluate-user-image {
		width: 8%;
		height: 8%;
		border-radius: 50%;
	}

	.evaluate-itemu-username-time {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.username-detail {
		font-size: 28rpx;
	}

	.images-url {
		width: 100rpx;
		height: 100rpx;
	}

	.datetime-detail {
		font-size: 24rpx;
		color: #7d7d7d;
	}

	.appraise-class-item {
		width: 30rpx;
		height: 30rpx;
	}

	.pl-dz-class {
		display: flex;
		align-items: center;
		justify-content: space-between;
		text-align: end;
	}

	.appraise-class-pl-dz {
		margin-left: 10rpx;
	}

	.pl-dz-view {
		display: flex;
		align-items: center;
		font-size: 26rpx;
		color: #9b9b9b;
	}

	.navigator-class {
		padding: 15rpx;
		background: white;
	}

	.invite-wrapper {
		padding: 10rpx;
		height: 200rpx;
	}

	.invite-image {
		width: 100%;
		height: 100%;
	}

	.view-line {
		height: 20rpx;
	}

	.isOutofDeliveryRange {
		opacity: 0.4;
	}

	.icongouwuche1 {
		font-size: 40rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.good-choice-btn {
		width: 100%;
		text-align: center;
		padding: 20rpx 0;
		border-radius: 15rpx;
		font-size: 28rpx;
		font-weight: bold;
	}

	.guige-yunfei {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.iconshoucang1 {
		color: #c3c3c3;
		font-size: 45rpx;
	}

	.is-collect {
		color: #fc7a7c;
	}

	.shoucang-text {
		font-size: 28rpx;
	}

	.not-collect {
		color: #c3c3c3;
	}

	.commdity-image-list {
		padding: 20rpx;
		border-radius: 15rpx;
		padding-bottom: 30%;
		display: flex;
		flex-direction: column;
	}

	.detail-images {
		width: 100%;
		height: auto;
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
		z-index: 1;
	}

	.guige-view {
		padding: 20rpx 0;
	}

	.guige-specListString {
		font-size: 32rpx;
		font-weight: bold;
	}

	.score-money {
		font-size: 30rpx;
	}

	.content_box {
		padding-top: 0;
	}
</style>