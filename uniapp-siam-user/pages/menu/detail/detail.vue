<template>
	<view>
		<scroll-view :scroll-y="true" :style="'height:' + (winHeight-10) + 'px'">
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
						<text class="name">{{ data.name }}</text>
						<text :decode="true" class="price">￥{{ data.price }}</text>
					</view>

					<view class="collect-in">
						<view class="flex_column">
							<van-icon name="like-o" :class="'iconfont iconshoucang1 ' + (isCollect ? 'is-collect' : '')"
								@tap="parseEventDynamicCode($event, isCollect ? 'bindDeleteCollect' : 'bindInsertCollect')"
								:data-goodsid="data.id" />
							<text :class="'shoucang-text ' + (isCollect ? 'is-collect' : 'not-collect')">收藏</text>
						</view>
					</view>
				</view>
				<view class="commdity-name-type-view commdity-name-specList">
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
								<radio :value="index" :checked="item.checked" :disabled="!item.stock" class="radio" />

								{{ item.name }}
							</label>
						</radio-group>
					</view>
				</view>
			</view>
			<view class="commodity-detail-view">
				<view class="detail-title">
					<view>
						商品详情
					</view>
					<view
						v-if="!shopInfo.isOutofDeliveryRange && shopInfo.isOperatingOfShop && shopInfo.shop.isOperating">
						<view class="stepper">
							<block v-if="data.number>0">
								<view class="flex_center car_reduce_add reduce-class" @tap="bindMinus"
									:data-cartId="data.cartId" :data-number="data.number">－</view>
								<input disabled type="number" :value="data.number" class="radd-reduce-input" />
								<view class="flex_center car_reduce_add add-class" :data-goodsId="data.id"
									@tap="parseEventDynamicCode($event, data.goodsStatus != 4 ? 'openSpecifications' : '')">
									＋
								</view>
							</block>
							<block v-else>
								<view class="insert-view theme-bg" :data-goodsId="data.id"
									@tap="parseEventDynamicCode($event, data.goodsStatus != 4 ? 'openSpecifications' : '')">
									＋
								</view>
							</block>
						</view>

					</view>
				</view>
				<view class="commodity-detail">
					{{ data.detail }}
				</view>
			</view>
			<navigator class="navigator-class" :url="'../../mine/share/index/index?inviterId=' + userInfo.id">
				<view class="invite-wrapper">
					<image
						:src="'https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/business/share-invite/share_mine.png?v=' + timestamp"
						mode="aspectFill" class="invite-image"></image>
				</view>
			</navigator>
		</scroll-view>
		<view class="shopping-cart-detail">
			<view class="content-fullReductionRuleName">
				{{
                    !shopInfo.isOutofDeliveryRange
                        ? shopInfo.isOperatingOfShop && shopInfo.shop.isOperating
                            ? fullPriceReductionIsHidden
                                ? fullReductionRuleName
                                : '暂无优惠'
                            : '商家已休息，暂不接单'
                        : '超出配送范围'
                }}
			</view>
			<view v-if="!specificationsDialog"
				:class="'safe-area shopping-cart-content ' + (shopInfo.isOutofDeliveryRange || !shopInfo.isOperatingOfShop || !shopInfo.shop.isOperating ? 'isOutofDeliveryRange' : '')">
				<view class="shopping-cart-left"
					@tap="parseEventDynamicCode($event, shopInfo.isOutofDeliveryRange || !shopInfo.isOperatingOfShop ? '' : 'openShoppingCart')">
					<view>
						<van-icon name="cart"
							:class="'iconfont icongouwuche1 highlight ' + (shoppingCartList.length <= 0 ? 'theme-other-bg' : 'theme-bg')" />
						<view class="num" v-if="totalNum > 0">{{ totalNum }}</view>
					</view>
					<view>
						<view class="shopping-cart-totalPrice">
							<view :class="fullPriceReductionIsHidden ? 'fullPriceReductionClass' : 'totalPrice'"
								v-if="shoppingCartList.length > 0">￥{{ totalPrice }}</view>
							<view class="full-price-reduction"
								v-if="fullPriceReductionIsHidden && shoppingCartList.length > 0">
								￥{{ fullPriceReduction }}</view>
							<view class="not-full-price-reduction" v-if="shoppingCartList.length <= 0">暂未选购商品</view>
						</view>
						<!-- <view class="shopping-cart-desc">另需配送费￥{{deliveryPrice}}元</view> -->
					</view>
				</view>
				<view :class="(isStartDeliveryPrice ? 'theme-bg' : 'shopping-cart-bg') + ' shopping-cart-right'" @tap="
                        parseEventDynamicCode(
                            $event,
                            shoppingCartList.length <= 0 || !isStartDeliveryPrice || shopInfo.isOutofDeliveryRange || !shopInfo.isOperatingOfShop ? '' : 'goToPay'
                        )
                    ">
					{{ isStartDeliveryPrice ? '去结算' : '差 ￥' + priceDifference + ' 起送' }}
				</view>
			</view>
			<van-action-sheet :show="shoppingCartDialog" @close="closeShoppingCart" @cancel="closeShoppingCart"
				title="已选商品" z-index="0">
				<view class="content">
					<scroll-view style="height: 55vh" scroll-y>
						<view class="shoppingCart-item" v-for="(item, index) in shoppingCartList" :key="index">
							<view class="goodsName-restructure-view">
								<view class="goodsName out_of_range one_row">{{ item.goodsName }}</view>
								<view class="restructure out_of_range one_row">{{ item.restructure }}</view>
							</view>

							<view class="goodsPrice-number-view">
								<view class="goodsPrice">￥{{ item.price }}</view>
								<view class="stepper">
									<view class="flex_center car_reduce_add reduce-class" @tap="bindMinus"
										:data-cartId="item.id" :data-number="item.number">－</view>
									<input disabled type="number" :value="item.number" class="add-reduce-input" />
									<view @tap="bindPlus" class="flex_center car_reduce_add add-class"
										:data-num="index + ',' + item.number">＋</view>
								</view>
							</view>
						</view>
						<view class="goodsName-packingCharges" :style="'padding-bottom:'+cartDetailHeight+'px;'">
							<text class="goodsName">包装费</text>
							<text class="goodsPrice">￥{{ packingCharges }}</text>
							<text></text>
						</view>
					</scroll-view>
				</view>
			</van-action-sheet>
			<van-action-sheet :show="specificationsDialog" @close="closeSpecificationsDialog"
				@cancel="closeSpecificationsDialog" title="选择规格">
				<view class="content">
					<view class="goods-info-view">
						<image :src="data.mainImage" mode="aspectFill" class="commodity-image"></image>
						<view>
							<view class="goods-info-name">{{ data.name }}</view>
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

										{{ item.name }}
									</label>
								</radio-group>
							</view>
							<view class="loading_box" v-if="specLoading&&specList.length == 0">
								<van-loading custom-class="loading_box_class" vertical>加载中...</van-loading>
							</view>
							<van-empty v-if="!specLoading&&specList.length == 0" description="暂无数据"></van-empty>
						</view>
					</scroll-view>
					<view slot="footer" class="position-sticky-bottom">
						<view class="good-choice-btn theme-bg" @tap="insertShoppingCart">我选好了</view>
					</view>
				</view>
			</van-action-sheet>
		</view>
	</view>
</template>

<script>
	import GlobalConfig from '../../../utils/global-config';
	import https from '../../../utils/http';
	import authService from '../../../utils/auth';
	import toastService from '../../../utils/toast.service';
	import utilHelper from '../../../utils/util';
	import dateHelper from '../../../utils/date-helper'; //获取应用实例
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
				shopId: '',
				timestamp: '',
				specList: [],
				priceAfter: '',
				fullPriceReduction: '',
				fullPriceReductionIsHidden: '',
				fullReductionRuleName: '',
				winHeight: 0,
				cartDetailHeight: 0,
				goodsId: '',
				shopInfo: {
					isOutofDeliveryRange: '',
					isOperatingOfShop: '',

					shop: {
						isOperating: ''
					}
				},
				isItNear: '',
				data: {
					name: '',
					price: '',
					shopId: '',
					id: '',
					isShopCart: '',
					cartId: '',
					number: 0,
					detail: '',
					mainImage: ''
				},
				totalNum: '',
				isStartDeliveryPrice: '',
				packingCharges: '',
				fullReductionRuleList: '',
				specListString: '',
				collectInfo: '',
				isCollect: '',
				specLoading: false
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			app = getApp();
			this.userInfo = app.globalData.loginUserInfo;
			this.getShopInfo(options.shopId);
			this.getCommodityDetails(options.id);
			this.getGoodsCollect(options.id);
			console.log("options=", options)
			this.shopId = options.shopId;
			this.userInfo = app.globalData.loginUserInfo;
			this.initShopInfo = JSON.parse(options.initShopInfo);
			this.options = options;
		},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {
			this.autoHeigth();
		},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {
			if (this.shopId) {
				this.getShopInfo(this.shopId);
			}
			// if(this.options){
			// 	this.getShopInfo(this.options.id);
			// }

			this.getTimestamp();
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
				specList[firstIndex][checkValue].checked = true;
				// for (var i in checkValue) {
				// 	specList[firstIndex][checkValue[i]].checked = true;
				// 	//console.log(specList[firstIndex][checkValue[i]])
				// }

				let price = this.data.price;
				for (let key in specList) {
					for (let keyof in specList[key]) {
						//console.log(specList[key][keyof].price)
						if (specList[key][keyof].checked) {
							price = price + specList[key][keyof].price;
						}
					}
				}
				// let goodsSpecs = {};
				// for(let i=0;i<specList.length;i++){
				//   for (let j = 0; j < specList[i].length;j++){
				//     //拼接查询规格等的json数据格式，查询商品规格等对应的价格
				//     if (specList[i][j].checked) {
				//       goodsSpecs[specList[i].spec] = specList[i][j].name;
				//     }
				//   }
				// }
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
							fullPriceReduction = totalPrice + this.data.packingCharges - this.fullReductionRuleList[i]
								.reducedPrice;
							fullReductionRuleName = this.fullReductionRuleList[i].name;
							fullPriceReductionIsHidden = true;
						}
					}
				}
				this.setData({
					specList: JSON.stringify(specList) == '{}' ? [] : specList,
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
					toastService.hideLoading();
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
				var _this = this;
				authService.checkIsLogin().then((result) => {
					toastService.showLoading();
					if (result) {
						let goodsSpecs = {};
						let specList = _this.specList;
						for (let key in specList) {
							for (let keyof in specList[key]) {
								//拼接查询规格等的json数据格式，查询商品规格等对应的价格
								if (specList[key][keyof].checked) {
									goodsSpecs[key] = specList[key][keyof].name;
								}
							}
						}
						let data = {
							goodsId: _this.goodsId,
							specList: JSON.stringify(goodsSpecs),
							shopId: _this.shopInfo.shop.id
						};
						toastService.hideLoading();
						https.request('/rest/member/shoppingCart/insert', data).then((
							result) => {
							if (result.success) {
								_this.specificationsDialog = false;
								_this.getShoppingCartList();
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
						//判断店铺是否打烊
						let startTime = this.shopInfo.shop.startTime;
						let endTime = this.shopInfo.shop.endTime;
						let isOperating = this.shopInfo.shop.isOperating;
						app.globalData.getIsBusiness(startTime, endTime, isOperating).then((result) => {
							if (!result) {
								return;
							}
							self.toPay();
						});
						return;
					}
					app.globalData.checkIsAuth('scope.userInfo');
				});
			},

			toPay() {
				var list = this.shoppingCartList;
				var orderDetail = {};
				orderDetail.actualPrice = this.totalPrice;
				orderDetail.fullPriceReduction = this.totalPrice;
				orderDetail.reducedPrice = 0;
				orderDetail.shopId = this.shopInfo.shop.id;
				orderDetail.initShopInfo = this.initShopInfo;
				orderDetail.selfOutActiveIndex = this.selfOutActiveIndex;
				orderDetail.orderDetailList = [];
				orderDetail.packingCharges = 0;
				for (var key in list) {
					orderDetail.packingCharges = orderDetail.packingCharges + list[key].packingCharges
					orderDetail.orderDetailList.push({
						goodsId: list[key].goodsId,
						specList: list[key].specList,
						number: list[key].number,
						goodsName: list[key].goodsName,
						restructure: list[key].restructure,
						price: list[key].price,
						id: list[key].id,
						packingCharges: list[key].packingCharges,
						totalPrice: list[key].price * list[key].number
					});
				}
				app.globalData.deliveryAndSelfTaking.payType = 'car';
				app.globalData.deliveryAndSelfTaking.orderDetail = orderDetail;
				uni.navigateTo({
					url: '../pay/pay'
				});
			},

			autoHeigth() {
				var _this = this;
				//获取用户手机系统信息
				uni.getSystemInfo({
					success: function(res) {
						let winHeight = res.windowHeight; //获取高度
						var height = 0;
						//获取class为settlement-view并减去这个高度，自适应scrollview的高度
						setTimeout(function timeout() {
							uni.createSelectorQuery()
								.in(_this)
								.selectAll('.shopping-cart-detail')
								.boundingClientRect(function(rects) {
									console.log(".shopping-cart-detail", rects)
									rects.forEach(function(rect, index) {
										height = height + rect.height;
									});
									if (rects.length > 0) {
										_this.setData({
											winHeight: winHeight - (height),
											cartDetailHeight: height
										});
									}

								}).exec();
						}, 1000);
					}
				});
			},

			closeShoppingCart: function() {
				this.shoppingCartDialog = false;
			},
			closeSpecificationsDialog: function() {
				this.specificationsDialog = false;
			},
			openShoppingCart() {
				authService.checkIsLogin().then((result) => {
					if (result) {
						if (this.shoppingCartList.length > 0) {
							this.shoppingCartDialog = this.shoppingCartDialog ? false : true;
						}
						return;
					}
					app.globalData.checkIsAuth('scope.userInfo');
				});
			},

			openSpecifications(e) {
				this.specificationsDialog = true;
				this.goodsId = e.currentTarget.dataset.goodsid;
				this.specLoading = true;
				this.selectByGoodsId(e.currentTarget.dataset.goodsid);
			},

			getShopInfo(shopId) {
				https.request('/rest/shop/detail', {
					id: shopId,
					position: app.globalData.deliveryAndSelfTaking.location
				}).then((result) => {
					if (result.success && result.data) {
						this.getFullReductionRule(result.data);
					}
				});
			},

			//获取满减规则
			getFullReductionRule(shopInfo) {
				console.log(shopInfo);
				let fullPriceReduction = 0;
				let fullReductionRuleName = '';
				let fullPriceReductionIsHidden = false;
				let limitedPrice = 0;
				for (let i = 0; i < shopInfo.fullReductionRuleList.length; i++) {
					if (totalPrice + this.packingCharges >= shopInfo.fullReductionRuleList[i].limitedPrice) {
						if (shopInfo.fullReductionRuleList[i].limitedPrice > limitedPrice) {
							limitedPrice = shopInfo.fullReductionRuleList[i].limitedPrice;
							fullPriceReduction = totalPrice - shopInfo.fullReductionRuleList[i].reducedPrice;
							fullReductionRuleName = shopInfo.fullReductionRuleList[i].name;
							fullPriceReductionIsHidden = true;
						}
					}
				}
				var shopWithinImgs = shopInfo.shop.shopWithinImg.split(',');
				for (let i = 0; i < shopWithinImgs.length; i++) {
					shopWithinImgs[i] = GlobalConfig.ossUrl + shopWithinImgs[i];
				}
				shopInfo.shop.shopLogoImg = GlobalConfig.ossUrl + shopInfo.shop.shopLogoImg;
				shopInfo.shop.shopWithinImgs = shopWithinImgs;
				//shopInfo.shop.isOperating = false;
				//shopInfo.shop.isOutofDeliveryRange = true;
				app.globalData.getIsItNear(shopInfo.shop.startTime, shopInfo.shop.endTime);
				//isOutofDeliveryRange true为超出范围
				//isOperatingOfShop false为打烊
				this.setData({
					shopInfo: shopInfo,
					fullPriceReduction: utilHelper.toFixed(fullPriceReduction, 2),
					fullReductionRuleName: fullReductionRuleName,
					fullPriceReductionIsHidden: fullPriceReductionIsHidden,
					isItNear: app.globalData.deliveryAndSelfTaking.isItNear
				});
				this.getShoppingCartList();
			},

			getShoppingCartList() {
				var _this = this;
				https.request('/rest/member/shoppingCart/list', {
					shopId: this.shopInfo.shop.id,
					pageNo: -1,
					pageSize: 20
				}).then((result) => {
					if (result.success && result.data) {
						var packingCharges = 0;
						var totalNum = 0;
						var totalPrice = 0;
						var number = 0;
						this.data.isShopCart = false;
						this.data.number = 0;
						result.data.records.forEach((result, index) => {
							let specList = '';
							for (var key in JSON.parse(result.specList)) {
								specList = (specList ? specList + '/' : specList) + JSON.parse(result
									.specList)[key];
							}
							result.restructure = specList;
							totalNum = totalNum + result.number;
							result.goodsPrices = utilHelper.toFixed(Number(result.goodsPrice) * result
								.number, 2);
							totalPrice += result.price * result.number; //初始化被选中的商品的总金额
							result.disable = result.goodsStatus == 1 || result.goodsStatus == 3 || result
								.goodsStatus == 4 ? true : false;
							packingCharges =
								result.goodsStatus == 1 || result.goodsStatus == 3 || result.goodsStatus ==
								4 ?
								packingCharges :
								packingCharges + result.packingCharges * result.number;
							if (this.data.id == result.goodsId) {
								number = number + result.number;
								this.data.isShopCart = true;
								this.data.cartId = result.id;
								this.data.number = number;
							}
						});
						setTimeout(() => {
							console.log("number=", _this.data.number);
							totalPrice = utilHelper.toFixed(totalPrice, 2);
							var isStartDeliveryPrice = false;
							var priceDifference = 0;
							_this.shopInfo.shop.startDeliveryPrice = _this.shopInfo.shop
								.startDeliveryPrice ? _this
								.shopInfo.shop.startDeliveryPrice : 0;
							if (totalPrice + packingCharges >= _this.shopInfo.shop.startDeliveryPrice) {
								isStartDeliveryPrice = true;
							}
							console.log('获取当前店铺的起送费====', _this.shopInfo.shop.startDeliveryPrice);
							priceDifference = _this.shopInfo.shop.startDeliveryPrice - (totalPrice +
								packingCharges);
							_this.data = _this.data;
							_this.totalNum = totalNum;
							_this.priceDifference = utilHelper.toFixed(priceDifference, 2);
							_this.isStartDeliveryPrice = isStartDeliveryPrice;
							_this.shoppingCartList = result.data.records;
							_this.packingCharges = packingCharges;
							_this.totalPrice = utilHelper.toFixed(totalPrice + packingCharges, 2);
							_this.getShopCartFullReductionRule();
						}, 500);
					}
				});
			},

			//获取满减规则
			getShopCartFullReductionRule() {
				https.request('/rest/fullReductionRule/list', {
					pageNo: -1,
					pageSize: 1,
					shopId: this.shopInfo.shop.id
				}).then((result) => {
					if (result.success) {
						let fullPriceReduction = 0;
						let fullReductionRuleName = '';
						let fullPriceReductionIsHidden = false;
						let limitedPrice = 0;
						for (let i = 0; i < result.data.records.length; i++) {
							if (this.totalPrice >= result.data.records[i].limitedPrice) {
								if (result.data.records[i].limitedPrice > limitedPrice) {
									limitedPrice = result.data.records[i].limitedPrice;
									fullPriceReduction = this.totalPrice - result.data.records[i].reducedPrice;
									fullReductionRuleName = result.data.records[i].name;
									fullPriceReductionIsHidden = true;
								}
							}
						}
						this.setData({
							fullPriceReduction: utilHelper.toFixed(fullPriceReduction, 2),
							fullReductionRuleName: fullReductionRuleName,
							fullPriceReductionIsHidden: fullPriceReductionIsHidden,
							fullReductionRuleList: result.data.records
						});
					}
				});
			},

			getCommodityDetails(id) {
				https.request('/rest/goods/selectById', {
					id: id,
					position: app.globalData.deliveryAndSelfTaking.location
				}).then((result) => {
					if (result.success && result.data) {
						//获取商品的详细图片，转换以轮播图的数据格式
						let carouselUrls = result.data.subImages.split(',');
						for (let i = 0; i < carouselUrls.length; i++) {
							this.carouselUrls.push(GlobalConfig.ossUrl + carouselUrls[i]);
						}
						result.data.mainImage = GlobalConfig.ossUrl + result.data.mainImage;
						this.data = result.data;
						this.carouselUrls = this.carouselUrls;
						this.priceAfter = result.data.price;
						this.selectByGoodsId(id);
					}
				});
			},

			selectByGoodsId(goodsId) {
				https.request('/rest/goodsSpecificationOption/selectByGoodsId', {
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
						this.specListString = specListString;
						this.specList = JSON.stringify(specList) == '{}' ? [] : specList;
						this.specLoading = false;
					}
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

			getGoodsCollect(goodsId) {
				https.request('/rest/member/goodsCollect/selectByGoodsId', {
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
				https.request('/rest/member/goodsCollect/insert', {
					goodsId: e.currentTarget.dataset.goodsid
				}).then((result) => {
					if (result.success) {
						this.isCollect = true;
						toastService.showSuccess('收藏成功');
					}
				});
			},

			bindDeleteCollect(e) {
				https.request('/rest/member/goodsCollect/delete', {
					goodsId: e.currentTarget.dataset.goodsid
				}).then((result) => {
					if (result.success) {
						this.isCollect = false;
						toastService.showSuccess('取消成功');
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
		background: #f5f5f5;
		padding-bottom: 160rpx;
	}

	.carousel-swiper {
		margin: 20rpx;
		height: 31vh;
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
		margin-top: 20rpx;
		padding: 20rpx;
		margin: 20rpx;
		border-radius: 20rpx;
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
	}

	.shopping-detail-view {
		position: fixed;
		bottom: 0;
		width: 100%;
	}

	.buy-shopping-cart-view {
		width: 100%;
		display: flex;
		height: 100rpx;
	}

	.immediate-purchase {
		width: 50%;
		font-size: 30rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		background: #f5f5f5;
	}

	.add-to-cart {
		width: 50%;
		font-size: 30rpx;
		color: #fff;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.commodity-shopping-view {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 10rpx 20rpx;
		background: white;
		box-shadow: -2px 0px 5px 0.5px rgba(0, 0, 0, 0.1);
	}

	.commodity-name-price-view {
		display: flex;
		flex-direction: column;
	}

	.commodity-name-price {
		display: flex;
		align-items: center;
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

	.money-icon-total-price {
		display: flex;
		align-items: center;
		font-size: 28rpx;
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

	.money-icon-fullPrice {
		font-weight: bold;
	}

	.full-reduction-view {
		font-size: 20rpx;
		font-weight: bold;
		padding: 0 10rpx;
		margin: 0 10rpx;
		border-radius: 10rpx;
	}

	.fullPriceReductionClass {
		color: gainsboro;
		display: flex;
		align-items: center;
		text-decoration: line-through;
	}

	/* shopcart 样式 */
	.content-fullReductionRuleName {
		width: 100%;
		background: #fffadc;
		font-size: 24rpx;
		text-align: center;
		position: sticky;
		top: 0;
		z-index: 1;
		opacity: 0.7;
		visibility: visible;
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
		z-index: 1;
		width: 100%;
	}

	.highlight {
		position: relative;
		top: -10px;
		width: 80rpx;
		line-height: 80rpx;
		text-align: center;
		height: 80rpx;
		margin-left: 20rpx;
		margin-right: 20rpx;
		border-radius: 50%;
		border: 10rpx solid #444444;
	}

	.shopping-cart-content {
		width: 100%;
		display: flex;
		align-items: center;
		background: white;
		z-index: 1;
		height: 54px;
	}

	.shopping-cart-left {
		width: 70%;
		height: 100%;
		display: flex;
		align-items: center;
		background: #505052;
		z-index: 1;
	}

	.shopping-cart-right {
		width: 30%;
		height: 100%;
		text-align: center;
		color: white;
		z-index: 1;
		font-size: 30rpx;
		font-weight: bold;
		display: flex;
		align-items: center;
		justify-content: center;
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
		top: 5px;
		left: 40px;
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
		border-radius: 20rpx;
		margin: 0 20rpx;
	}

	.commdity-name-specList {
		margin: 0;
		padding: 20rpx 0;
	}

	.commdity-name {
		font-size: 32rpx;
		font-weight: bold;
		line-height: 50rpx;
		width: 70%;
		align-items: flex-start;
	}

	.commdity-name .name {
		font-size: 30rpx;
	}

	.commdity-name .price {
		font-size: 32rpx;
		font-weight: bold;
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

	.manjiantop {
		position: absolute;
		top: 0;
	}

	.closeImages {
		position: relative;
		left: 91%;
		top: 4%;
		z-index: 999;
	}

	.close-image-class {
		width: 50rpx;
		height: auto;
	}

	.specifications-scroll-view {
		height: 274px;
	}

	.go-to-shop {
		padding: 0 20rpx;
		border-radius: 10rpx;
		font-size: 28rpx;
		margin: 0;
		margin-left: 20rpx;
	}

	.stepper-view {
		margin: 20rpx 0 10rpx 0;
	}

	.clearNull {
		font-weight: 700;
		font-size: 15px;
		color: #80858a;
	}

	.specifications-dialog {
		margin-bottom: 0rpx;
	}

	.good-choice-view {
		padding: 20rpx;
		border-top: 1prx #808080 solid;
	}

	.good-choice-btn {
		width: 100%;
		text-align: center;
		padding: 20rpx 0;
		border-radius: 15rpx;
		font-size: 28rpx;
		font-weight: bold;
	}

	.shoppingCart-screen-dialog {
		height: 55%;
	}

	.shoppingCart-scroll-view {
		height: 350px;
	}

	.shoppingCart-item {
		display: flex;
		align-items: center;
		justify-content: space-between;
		/* width: 100%; */
		padding: 20rpx 20rpx;
		border-bottom: 1rpx solid #f5f5f5;
	}

	.goodsName-restructure-view {
		width: 50%;
	}

	.goodsName-packingCharges {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 20rpx 20rpx 60rpx 20rpx;
	}

	.goodsName {
		font-size: 28rpx;
	}

	.restructure {
		font-size: 22rpx;
		color: #808080;
	}

	.goodsPrice-number-view {
		width: 50%;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.goodsPrice {
		font-size: 30rpx;
		font-weight: bold;
		color: #f01414;
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

	/* 商家栏样式 */
	.swiper-bussiness {
		text-align: left;
	}

	.swiper-tab-bussiness {
		text-align: left;
	}

	.swiper-bussiness-item {
		background: white;
		margin-bottom: 20rpx;
		padding: 20rpx;
	}

	.swiper-bussiness-info {
		font-size: 28rpx;
		color: #717171;
	}

	.swiper-bussiness-title {
		font-size: 30rpx;
		font-weight: bold;
	}

	.swiper-bussiness-row {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 15rpx 0;
	}

	.swiper-bussiness-row-left {
		width: 20%;
		font-size: 26rpx;
		color: black;
		font-weight: bold;
	}

	.contact-bussiness-text {
		color: #2e87cd;
	}

	.carousel-swiper-item {
		border-radius: 15rpx;
		height: 200rpx;
	}

	.carousel-image {
		width: 100%;
		height: 100%;
		border-radius: 20rpx;
	}

	.is-end-item {
		padding-bottom: 50px;
	}

	.theme-other-bg {
		background: #353535;
		color: #5f5e63;
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
		margin: 0 20rpx;
		border-radius: 20rpx;
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
	}

	.collect-in {
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
		font-size: 26rpx;
	}

	.not-collect {
		color: #c3c3c3;
	}

	.score-money {
		font-size: 30rpx;
	}

	.content {
		padding: 0 16px 16px 16px;
	}
</style>