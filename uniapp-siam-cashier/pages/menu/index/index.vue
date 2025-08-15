<template>
	<view class="content-class">
		<view v-if="menuList.length > 0" id="menu_list" :class="ifScroll ? 'flex_between' : 'flex_between menu_list'"
			:style="'height:'+(winHeight-(carHeight?(carHeight):70)-5)+'px;'">
			<scroll-view :scroll-y="true" :enable-flex="true"
				:style="'height:'+(winHeight-(carHeight?(carHeight):70)-5)+'px;width:'+leftWidth+'px;'"
				:scroll-into-view="'left'+activeLeftTab" :scroll-with-animation="true" class="menu_left_scorll">
				<block v-for="(menu, menuIndex) in menuList">
					<view :id="'left'+menuIndex" v-if="menu.goodsList.length>0" @tap="leftTap" :data-index="menuIndex"
						:class="(menuIndex==activeLeftTab)?'menu_left_content theme-bg left_active ':'menu_left_content'">
						<view class="menu_left_name">
							<view class="out_of_range one_row menu_left_name_text ">{{ menu.name }}</view>
						</view>
					</view>
				</block>
			</scroll-view>
			<scroll-view :scroll-y="true" :enable-flex="true"
				:style="'height:'+(winHeight-(carHeight?(carHeight):70)-5)+'px;margin-left:5rpx;background: #f6f6f6;'"
				@scroll="mainScroll" @touchstart="mainTouch" :scroll-into-view="'into'+activeTab"
				:scroll-with-animation="true" id="scroll_right" class="menu_right_scorll">
				<view class="menu_right_scorll_view">
					<view v-for="(menu, menuIndex) in menuList" :id="'into'+menuIndex"
						:class="'vtabs-content-item ' + (menuList.length - 1 == menuIndex ? '' : '')">
						<view class="commodity-type position-sticky" v-if="menu.goodsList.length>0">
							<view class="categoryName-view">{{ menu.name }}</view>
						</view>

						<block v-for="(chil, goodsIndex) in menu.goodsList" :key="goodsIndex">
							<view :class="'commodity-item ' + (chil.goodsStatus == 4 ? 'isEnd' : '')"
								:hover-class="chil.goodsStatus == 4 ? '' : 'hover-class-public'">
								<view class="flex_between commodity-name-view" @tap="commodityDetailTap"
									:data-menuindex="menuIndex" :data-goodsindex="goodsIndex" :data-id="chil.goodsId">
									<image
										:src="chil.mainImage ? chil.mainImage_ : '/static/assets/images/load-image.png'"
										mode="aspectFill" class="commodity-image"></image>
									<view class="sell-out" v-if="chil.goodsStatus == 4">售罄</view>
									<view class="commodity-name-english-view"
										:style="rightWidth==0?'':'width:'+(rightWidth+10)+'px;'">
										<view class="commodity-name">{{ chil.goodsName }}</view>
										<view class="commodity-english"><text></text></view>
										<view class="flex_between money-view">
											<view>￥{{ chil.goodsPrice }}</view>
											<view class="flex_center money-insert-view">
												<view class="menu_cart_view">
													<view
														:class="'flex_center insert-view theme-bg ' + (chil.goodsStatus == 4 ? 'isEnd' : '')"
														:data-goodsId="chil.goodsId" @click.stop="openSpecifications"
														:data-menuindex="menuIndex" :data-goodsindex="goodsIndex">
														＋
													</view>
													<view class="menu_cart_number" v-if="chil.number>0">{{chil.number}}
													</view>
												</view>
											</view>
										</view>
									</view>
								</view>

							</view>
						</block>
						<view v-if="(menuList.length - 1) == menuIndex" class="more_box">没有更多啦~</view>
					</view>
				</view>
			</scroll-view>
			<view id="menu_scorll_mask" :class="ifScroll ? '' : 'menu_scorll_mask'"></view>
			<view class="flex_column_center shopping-cart-detail">
			</view>
		</view>
		<view class="loading_box" v-if="isLoading&&menuList.length==0">
			<van-loading custom-class="loading_box_class" vertical>加载中...</van-loading>
		</view>
		<van-empty v-if="menuList.length <= 0&&!isLoading" description="暂无商品"></van-empty>
		<view class="shopping-cart-detail">
			<view class="shopping-cart-detail" id="shopping-cart-detail">
				<view class="content-fullReductionRuleName">
					{{fullPriceReductionIsHidden? fullReductionRuleName: '暂无优惠'}}
				</view>
				<view :class="'shopping-cart-content'">
					<view class="shopping-cart-left" @tap="openShoppingCart">
						<view id="shopping-cart-left-view">
							<van-icon name="cart"
								:class="'iconfont icongouwuche1 highlight ' + (shoppingCartList.length <= 0 ? 'theme-other-bg' : 'theme-bg')" />
							<view>
								<view class="num" v-if="totalNum > 0">{{ totalNum }}</view>
							</view>
						</view>
						<view>
							<view class="shopping-cart-totalPrice">
								<view :class="fullPriceReductionIsHidden ? 'fullPriceReductionClass' : 'totalPrice'"
									v-if="shoppingCartList.length > 0">
									￥{{ totalPrice }}
								</view>
								<view class="full-price-reduction"
									v-if="fullPriceReductionIsHidden && shoppingCartList.length > 0">
									￥{{ fullPriceReduction }}</view>
								<view class="not-full-price-reduction" v-if="shoppingCartList.length <= 0">暂未选购商品</view>
							</view>
						</view>
					</view>
					<view
						:class="(shoppingCartList.length>0 ? 'theme-bg' : 'shopping-cart-bg') + ' shopping-cart-right'"
						@tap="goToPay">去结算
					</view>
				</view>
			</view>
			<van-action-sheet v-model:show="shoppingCartDialog" :show="shoppingCartDialog" title="已选商品"
				@close="closeShoppingCart" @cancel="closeShoppingCart" z-index='0'>
				<view class="content" :style="'padding-bottom:' +(carHeight)+'px;'">
					<scroll-view style="height: 55vh" scroll-y>
						<view class="shoppingCart-item" v-for="(item, index) in shoppingCartList" :key="index">
							<view class="goodsName-restructure-view">
								<view class="flex_start_center" style="width: 100%;">
									<image
										:src="item.mainImage ? item.mainImage : '/static/assets/images/load-image.png'"
										mode="aspectFill" class="commodity-image"></image>
									<view class="flex_column" :style="'width:'+(winWidth -mainImageWidth-40)+'px;'">
										<view class="flex_column">
											<view class="goodsName out_of_range one_row">{{ item.goodsName }}</view>
											<view class="restructure out_of_range one_row">{{ item.restructure }}</view>
										</view>
										<view class="flex_between goodsPrice-number-view" style="width: 100%;">
											<view class="goodsPrice">￥{{ item.price }}</view>
											<view class="stepper">
												<view class="flex_center car_reduce_add reduce-class" @tap="bindMinus"
													:data-cartid="item.id" :data-number="item.number" :data-index="index">－</view>
												<input disabled type="number" :value="item.number" class="add-reduce-input" />
												<view @tap="bindPlus" class="flex_center car_reduce_add add-class"
													:data-cartid="item.id" :data-number="item.number" :data-index="index">＋</view>
											</view>
										</view>
									</view>
								</view>
							</view>
						</view>
						<view class="goodsName-packingCharges">
							<text class="goodsName">包装费</text>
							<text class="goodsPrice">￥{{ packingCharges }}</text>
							<text></text>
						</view>
					</scroll-view>
				</view>
			</van-action-sheet>
			<van-action-sheet :show="specificationsDialog" @close="closeSpecifications" @cancel="closeSpecifications"
				title="选择规格" z-index='9'>
				<view class="content">
					<view class="goods-info-view">
						<image :src="goodsInfo.mainImage?goodsInfo.mainImage_:'/static/assets/images/load-image.png'"
							mode="aspectFill" class="commodity-image"></image>
						<view>
							<view class="goods-info-name">{{ goodsInfo.name }}</view>
							<view class="goods-info-specListString">已选：{{ specListString }}</view>
							<view class="goods-info-price">￥{{ priceAfter }}</view>
						</view>
					</view>
					<scroll-view scroll-y style="height: 50vh">
						<view class="commdity-name-type-view">
							<view class="commdity-name">{{ data.name }}</view>
							<view class="commdity-engname">{{ data.name }}</view>
							<view class="flex_start_center commdity-type-item" v-for="(item, key) in specList" :key="key">
								<view class="commdity-type-name">{{ key }}</view>
								<radio-group class="flex_start radio-group" @change="radioChange" :data-firstIndex="key">
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
							<view class="loading_box" v-if="specLoading&&specList.length==0">
								<van-loading custom-class="loading_box_class" vertical>加载中...</van-loading>
							</view>
							<van-empty v-if="!specLoading&&specList.length <= 0" description="暂无规格"></van-empty>
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
	import dateHelper from '../../../utils/date-helper';
	import storageHelper from '../../../utils/storage';

	let app = null;
	//声明全局变量
	var proListToTop = [];
	var isInitShow = true;
	var totalPrice = 0;
	var totalNum = 0;
	export default {
		data() {
			return {
				initShopInfo: {
					shopAdditionalVo: {

					}
				},
				titleActiveColor: '#004ca0',
				staticImg: '',
				currentActiveIndex: 0,
				menuList: [],
				imageTip: '../../assets/common/icon-internet-error.png',
				indicatorDots: true,
				autoplay: true,
				interval: 5000,
				duration: 1000,
				afterColor: 'white',
				currentTab: 0,
				data: {
					dialog1: false,
					dialog2: false,
					name: ''
				},
				deliveryPrice: 0,
				shoppingCartDialog: false,
				specificationsDialog: false,
				activeTab: 0,
				activeLeftTab: 0,
				rate: 5,
				disabled: true,
				priceDifference: 0,
				isStartDeliveryPrice: false,
				appraiseFocus: false,
				buttons: [],
				appraiseFocus: false,
				showConfirmBar: false,
				shoppingCartList: [],
				isShopDetail: false,
				isActivityDialog: false,
				isOutofDeliveryRangeDialog: false,
				netWorkType: false,
				dialogShow: false,
				maskClosable: false,
				proListToTop: [],
				ifScroll: false,
				shopId: '',
				screenHeight: '',
				leftHeight: '',
				bussinessHeight: '',
				carouselUrls: '',
				toView: '',
				shopInfo: {
					shop: {
						firstPoster: '',
						shopLogoImg: '',
						name: '',
						serviceRating: '',
						reducedDeliveryPrice: '',
						announcement: '',
						isOperating: '',
						shopWithinImgs: [],
						briefIntroduction: false,
						managePrimary: '',
						province: '',
						city: '',
						area: '',
						street: '',
						startTime: '',
						endTime: '',
						id: ''
					},
					promotionList: [],
					isOutofDeliveryRange: '',
					isOperatingOfShop: ''
				},
				fullPriceReduction: '',
				fullReductionRuleName: '',
				fullPriceReductionIsHidden: '',
				appraiseList: '',
				totalNum: '',
				packingCharges: '',
				totalPrice: '',
				appraise: '',
				appraiseId: '',
				userName: '',
				replyId: '',
				replyType: '',
				appraiseIndex: '',
				appraiseJson: '',
				content: '',
				appraiseHeight: '',
				goodsId: '',
				goodsInfo: {
					mainImage_: '',
					name: ''
				},
				priceAfter: '',
				specListString: '',
				specList: [],
				specLoading: false,
				pageTopView: '',
				dps: '',
				shoppingCartDetail: '',
				contentHeight: '',
				rule: {
					name: ''
				},
				goodsItem: {
					goodsStatus: 0,
					goodsId: '',
					mainImage_: '',
					goodsName: '',
					latelyMonthlySales: '',
					goodsPrice: '',
					number: 0,
					cartId: ''
				},
				menuIndex: 0,
				menu: {
					name: '',
					goodsList: []
				},
				goodsIndex: 0,
				chil: {
					goodsStatus: 0,
					goodsId: '',
					mainImage_: false,
					goodsName: '',
					goodsPrice: '',
					number: 0,
					cartId: ''
				},
				noDataTip: '',
				img: '',
				evt: '',
				selfOutItems: [{
					text: "自提",
					tap: "slefTap",
					index: 0
				}, {
					text: "外送",
					tap: "outTap",
					index: 1
				}],
				selfOutActiveIndex: 0,
				winHeight: 0,
				topHeight: 0,
				carHeight: 0,
				topArr: [],
				isMainScroll: false,
				scrollInto: '',
				topLeftNumer: 0,
				topRightNumer: 0,
				deliveryAndSelfTaking: {
					deliveryAddress: {

					}
				},
				isLoading: true,
				ifChooseBack: false,
				memberInfo: {

				},
				rightWidth: 0,
				leftWidth: 0,
				winWidth: 0,
				mainImageWidth: 0,
				cartLeftIconWidth: 0
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			console.log("onLoad=", options);
			app = getApp();
			var _this = this;
			console.log("====================================000======", app.globalData.shopId);
			authService.setWxStorage(`${app.globalData.shopId}_shoppingCartList`, []);
			// this.shopId = app.globalData.shopId;
			// this.staticImg = app.globalData.staticImg;
			// this.activeLeftTab = 0;
			// this.activeTab = 0;
			// this.topArr = [];
			// this.isMainScroll = true; //是否主菜单滑动
			// this.scrollInto = 'into0';
			// this.isLoading = true;
			// this.menuList = [];
			// if (this.shopId) {
			// 	this.getShoppingCartList(this.shopId);
			// 	this.$nextTick(() => {
			// 		setTimeout(() => {
			// 			this.getShopInfo(this.shopId);
			// 		}, 500);
			// 	})
			// }

			// this.$nextTick(() => {
			// 	setTimeout(() => {
			// 		_this.getElementTop();
			// 	}, 10);
			// 	setTimeout(() => {
			// 		_this.ifScroll = true;
			// 		_this.isLoading = false;
			// 	}, 3000);
			// });
		},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {
			console.log('onReady');
			var _this = this;
		},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {
			var _this = this;
			console.log("====================================000======");
			var options = uni.getLaunchOptionsSync().query;
			this.shopId = app.globalData.shopId;
			this.staticImg = app.globalData.staticImg;
			this.activeLeftTab = 0;
			this.activeTab = 0;
			this.topArr = [];
			this.isMainScroll = true; //是否主菜单滑动
			this.scrollInto = 'into0';
			this.isLoading = true;
			this.menuList = [];
			if (this.shopId) {
				this.getShoppingCartList(this.shopId);
				this.$nextTick(() => {
					setTimeout(() => {
						this.getShopInfo(this.shopId);
					}, 500);
				})
			}

			this.$nextTick(() => {
				setTimeout(() => {
					_this.getElementTop();
				}, 10);
				setTimeout(() => {
					_this.ifScroll = true;
					_this.isLoading = false;
				}, 3000);
			});
		},
		onResize: function() {
			console.log('onResize');
			this.activeLeftTab = 0;
			this.activeTab = 0;
			this.topArr = [];
			this.isMainScroll = true; //是否主菜单滑动
			this.scrollInto = 'into0';
			this.isLoading = true;
			this.menuList = [];
			if (this.shopId) {

				this.$nextTick(() => {
					setTimeout(() => {
						this.getShopInfo(this.shopId);
					}, 500);
				})
			}

			this.$nextTick(() => {
				setTimeout(() => {
					this.getElementTop();
				}, 10);
				setTimeout(() => {
					this.ifScroll = true;
					this.isLoading = false;
				}, 3000);
			});
		},
		/**
		 * 生命周期函数--监听页面隐藏
		 */
		onHide: function() {
			this.dialogShow = false;
			this.maskClosable = true;
			this.proListToTop = [];
		},
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
			this.getMenuList();
			// 隐藏导航栏加载框
			uni.hideNavigationBarLoading();
			// 停止下拉动作
			uni.stopPullDownRefresh();
		},
		/**
		 * 页面上拉触底事件的处理函数
		 */
		onReachBottom: function() {},
		/**
		 * 用户点击右上角分享
		 */
		onShareAppMessage: function() {},

		onPageScroll(e) {

		},
		methods: {
			selfAdaption() {
				var _this = this;
				let winHeight = 0;
				let winWidth = 0;
				let height = 0;
				uni.getSystemInfo({
					success: function(res) {
						console.log(res)
						//获取到用户的手机信息
						winHeight = res.windowHeight;
						winWidth = res.windowWidth;
						_this.leftWidth = (winWidth / 3) + 20;
						_this.winHeight = winHeight;
						_this.winWidth = winWidth;
						// 获取需要减去的dom结构的高度信息
						setTimeout(function timeout() {
							uni.createSelectorQuery()
								.in(_this)
								.selectAll('#shopping-cart-detail')
								.boundingClientRect(function(rects) {
									console.log("#shopping-cart-detail", rects)
									rects.forEach(function(rect, index) {
										height = height + rect.height;
									});
									if (rects.length > 0) {
										_this.carHeight = rects[0].height;
										_this.ifScroll = true;
									}
								}).exec();
							uni.createSelectorQuery()
								.in(_this)
								.selectAll('.menu_right_scorll_view,.commodity-image')
								.boundingClientRect(function(rects) {
									console.log(".commodity-image", rects)
									// rects.forEach(function(rect, index) {
									// 	height = height + rect.height;
									// });
									if (rects.length > 0) {
										_this.rightWidth = _this.winWidth - _this.leftWidth -
											rects[1].width;
										_this.mainImageWidth = rects[1].width;
									}
								}).exec();
							// uni.createSelectorQuery()
							// 	.in(_this)
							// 	.selectAll('#shopping-cart-left-view')
							// 	.boundingClientRect(function(rects) {
							// 		console.log("#shopping-cart-left-view", rects)
							// 		if (rects.length > 0) {
							// 			_this.cartLeftIconWidth = rects[0].width-30;
							// 		}
							// 	}).exec();
						}, 500);
					}
				});
			},
			closeShoppingCart: function() {
				this.shoppingCartDialog = false;
			},
			close: function() {
				this.shoppingCartDialog = false;
			},
			openShoppingCart() {
				var _this = this;
				if (this.shoppingCartList.length > 0) {
					this.shoppingCartDialog = this.shoppingCartDialog ? false : true;
				}
			},
			commodityDetailTap(e) {
				console.log(e)
				var menuIndex = e.currentTarget.dataset.menuindex;
				var goodsIndex = e.currentTarget.dataset.goodsindex;
				var goodsId = this.menuList[menuIndex].goodsList[goodsIndex].goodsId;
				var goodsStatus = this.menuList[menuIndex].goodsList[goodsIndex].goodsStatus;
				if (goodsStatus != 4) {
					// uni.navigateTo({
					// 	url: `../detail/detail?id=${goodsId}&shopId=${this.shopId}`
					// });
					uni.navigateTo({
						url: '../detail/detail?id=' + goodsId + '&shopId=' + this.shopId
					});
				}
			},
			scroll(e) {
				//console.log(e)
				//获取当前滑动距离顶部的距离高度
				let scrollTop = e.detail.scrollTop;
				//console.log(scrollTop)
				//获取右侧菜单的高度
				let scrollArr = proListToTop;
				//当距离顶部的高度大于等于右侧菜单的item高度
				let index = 0;
				if (scrollTop >= scrollArr[scrollArr.length - 1]) {
					return;
				} else {
					for (let i = 0; i < scrollArr.length; i++) {
						if (scrollTop >= 0 && scrollTop < scrollArr[0]) {
							index = 0;
						} else if (scrollTop >= scrollArr[i - 1] && scrollTop < scrollArr[i]) {
							index = i;
						} else if (scrollTop <= 0) {}
					}
				}
				this.setData({
					currentActiveIndex: index
				});
			},
			selectMenuTap(e) {
				var index = e.currentTarget.dataset.index;
				console.log(index);
				this.setData({
					toView: 'NAV' + index.toString(),
					currentActiveIndex: index
				});
			},
			getAllRects() {
				// 获取商品数组的位置信息
				let menuHeight = 0;
				uni.createSelectorQuery().in(uni)
					.selectAll('.commodity-item-view')
					.boundingClientRect(function(rects) {
						rects.forEach(function(rect) {
							menuHeight += rect.height;
							proListToTop.push(menuHeight);
						});
					}).exec();
			},
			getShopInfo(shopId) {
				https.request('/rest/shop/detail', {
					id: shopId
				}).then((result) => {
					if (result.success && result.data) {
						this.shopInfo = result.data;
						this.getMenuList(shopId);
					}
				});
			},
			getMenuList(shopId) {
				var _this = this;
				https.request('/rest/menu/listWithGoods', {
					shopId: shopId
				}).then((result) => {
					if (result.success && result.data) {
						var goodsList = [];
						result.data.forEach((aitem, index) => {
							if (aitem.goodsList.length > 0) {
								aitem.goodsList.forEach((bitem) => {
									bitem.isShopCart = false;
									bitem.mainImage_ = GlobalConfig.ossUrl + bitem.mainImage;
								});
								goodsList.push(aitem);
							}

						});
						console.log(goodsList)
						this.menuList = goodsList;
						this.getShoppingCartList(this.shopId);
						setTimeout(() => {
							_this.isLoading = false;
							_this.selfAdaption();
						}, 0);
					}
				});
			},
			getShoppingCartList(shopId) {
				var _this = this;
				authService.getWxStorage(`${shopId}_shoppingCartList`).then((result) => {
					console.log(`${shopId}_shoppingCartList`, result);
					if (result) {
						var packingCharges = 0;
						var totalNum = 0;
						var totalPrice = 0;
						this.menuList.forEach((menu, menuIndex) => {
							menu.goodsList.forEach((goods, goodsIndex) => {
								let number = 0;
								this.menuList[menuIndex].goodsList[goodsIndex].number = number;
								result.forEach((result, carIndex) => {
									if (goods.goodsId == result.goodsId) {
										number = number + result.number;
										this.menuList[menuIndex].goodsList[goodsIndex]
											.isShopCart = true;
										result.menuIndex = menuIndex;
										result.goodsIndex = goodsIndex;
										this.menuList[menuIndex].goodsList[goodsIndex]
											.number =
											number;
										this.menuList[menuIndex].goodsList[goodsIndex]
											.cartId =
											result.id;
									}
								});
							});
						});
						result.forEach((result, index) => {
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
						});

						totalPrice = utilHelper.toFixed(totalPrice, 2);
						this.shopInfo.shop.startDeliveryPrice = this.shopInfo.shop.startDeliveryPrice ? this
							.shopInfo.shop.startDeliveryPrice : 0;
						this.menuList = this.menuList;
						this.totalNum = totalNum;
						this.shoppingCartList = result;
						this.packingCharges = packingCharges;
						this.totalPrice = utilHelper.toFixed(totalPrice + packingCharges, 2);
						this.getShopCartFullReductionRule();
					}
				});
			},
			//获取满减规则
			getShopCartFullReductionRule() {
				https.request('/rest/fullReductionRule/list', {
					pageNo: -1,
					pageSize: 1,
					shopId: this.shopId
				}).then(result => {
					if (result.success) {
						let fullPriceReduction = 0,
							fullReductionRuleName = "",
							fullPriceReductionIsHidden = false,
							limitedPrice = 0;
						for (let i = 0; i < result.data.records.length; i++) {
							if (this.totalPrice >= result.data.records[i].limitedPrice) {
								if (result.data.records[i].limitedPrice > limitedPrice) {
									limitedPrice = result.data.records[i].limitedPrice;
									fullPriceReduction = this.totalPrice - result.data.records[i]
										.reducedPrice;
									fullReductionRuleName = result.data.records[i].name;
									fullPriceReductionIsHidden = true;
								}
							}
						}
						this.fullPriceReduction = utilHelper.toFixed(fullPriceReduction, 2);
						this.fullReductionRuleName = fullReductionRuleName;
						this.fullPriceReductionIsHidden = fullPriceReductionIsHidden;
					}
				})
			},
			openSpecifications(e) {
				console.log(e)
				var menuIndex = e.currentTarget.dataset.menuindex;
				var goodsIndex = e.currentTarget.dataset.goodsindex;
				var menuList = this.menuList;
				var goodsList = this.menuList[menuIndex].goodsList;
				var goodsId = goodsList[goodsIndex].goodsId;
				var goodsStatus = this.menuList[menuIndex].goodsList[goodsIndex].goodsStatus;
				if (goodsStatus != 4) {
					this.specificationsDialog = true;
					this.specLoading = true;
					this.goodsId = goodsId;
					this.chooseMenuIndex = menuIndex;
					this.chooseGoodsIndex = goodsIndex;
					this.getCommodityDetails(goodsId);
				}
			},

			closeSpecifications: function() {
				this.getShoppingCartList(this.shopInfo.shop.id);
				this.specificationsDialog = false;
				this.specList = [];
				this.specLoading = false;
			},

			getCommodityDetails(id) {
				https.request('/rest/goods/selectById', {
					id: id
				}).then((result) => {
					if (result.success && result.data) {
						//获取商品的详细图片，转换以轮播图的数据格式
						//console.log(result.data)
						if (result.data.mainImage) {
							result.data.mainImage_ = GlobalConfig.ossUrl + result.data.mainImage;
						}
						this.goodsInfo = result.data;
						this.priceAfter = result.data.price;
						this.selectByGoodsId(id);
					}
				});
			},
			selectByGoodsId(goodsId) {
				console.log(this);
				https.request('/rest/goodsSpecificationOption/selectByGoodsId', {
					goodsId: goodsId
				}).then((result) => {
					if (result.success && result.data) {
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
			radioChange(e) {
				//获取复选框选中的下标值
				var checkValue = e.detail.value;
				//获取第一级分类的下标值
				let firstIndex = e.currentTarget.dataset.firstindex;
				//获取所有分类信息
				let specList = this.specList;
				//遍历分类信息给第一级分类为false，提交的时候对应各级分类
				for (var j in specList[firstIndex]) {
					specList[firstIndex][j].checked = false;
				}
				//给选中的第二级分类的checked设置为true
				specList[firstIndex][checkValue].checked = true;

				let price = this.goodsInfo.price;
				let specListString = '';
				for (let key in specList) {
					for (let keyof in specList[key]) {
						console.log(specList[key][keyof].price);
						if (specList[key][keyof].checked) {
							price = price + specList[key][keyof].price;
							specListString = (specListString ? specListString + '/' : specListString) + specList[key][
								keyof
							].name;
						}
					}
				}
				let totalPrice = price * this.commodityNum;
				let fullPriceReduction = 0;
				let fullReductionRuleName = '';
				let fullPriceReductionIsHidden = false;
				let limitedPrice = 0;
				for (let i = 0; i < this.shopInfo.fullReductionRuleList.length; i++) {
					if (totalPrice >= this.shopInfo.fullReductionRuleList[i].limitedPrice) {
						if (this.shopInfo.fullReductionRuleList[i].limitedPrice > limitedPrice) {
							limitedPrice = this.shopInfo.fullReductionRuleList[i].limitedPrice;
							fullPriceReduction = totalPrice + this.data.packingCharges - this.shopInfo
								.fullReductionRuleList[i].reducedPrice;
							fullReductionRuleName = this.shopInfo.fullReductionRuleList[i].name;
							fullPriceReductionIsHidden = true;
						}
					}
				}
				console.log(specList)
				this.specList = specList;
				this.specListString = specListString;
				this.priceAfter = price;

			},
			insertShoppingCart(e) {
				var _this = this;
				toastService.showLoading();
				let goodsSpecs = {};
				let specList = _this.specList;
				let specPrice = 0;
				for (let key in specList) {
					for (let keyof in specList[key]) {
						//拼接查询规格等的json数据格式，查询商品规格等对应的价格
						if (specList[key][keyof].checked) {
							goodsSpecs[key] = specList[key][keyof].name;
							specPrice = specPrice + specList[key][keyof].price;
						}
					}
				}

				var shopId = this.shopId;
				var chooseMenuInfo = this.menuList[this.chooseMenuIndex];
				var chooseGoodsInfo = chooseMenuInfo.goodsList[this.chooseGoodsIndex];
				console.log("chooseGoodsInfo", chooseGoodsInfo)
				var goodsInfo = this.goodsInfo;
				var goodsId = goodsInfo.goodsId;
				var cartGoods = {
					"isGoodsExists": true,
					"salePrice": chooseGoodsInfo.salePrice,
					"goodsId": chooseGoodsInfo.goodsId,
					"specList": JSON.stringify(goodsSpecs),
					"packingCharges": chooseGoodsInfo.packingCharges,
					"number": 1,
					"price": goodsInfo.price,
					"goodsPrice": chooseGoodsInfo.goodsPrice,
					"specPrice": specPrice,
					"isSale": chooseGoodsInfo.isSale,
					"id": chooseGoodsInfo.goodsId,
					"shopId": chooseMenuInfo.shopId,
					"stock": chooseGoodsInfo.stock,
					"goodsName": chooseGoodsInfo.goodsName,
					"totalPrice": goodsInfo.price + specPrice,
					"mainImage": chooseGoodsInfo.mainImage ? chooseGoodsInfo.mainImage_ : ''
				}
				var shoppingCartList = [];
				authService.getWxStorage(`${shopId}_shoppingCartList`).then((result) => {
					console.log("resultresultresultresult", result);
					if (result) {
						shoppingCartList = result;
					}
					console.log("shoppingCartList===", shoppingCartList);
					if (shoppingCartList.length > 0) {
						shoppingCartList = [];
						let isOne = false;
						result.forEach((result, index_re) => {
							console.log(result.id, cartGoods.id)
							if (result.id == Number(cartGoods.id) && cartGoods.specList == result
								.specList) {
								result.number = result.number + 1;
								result.totalPrice = (result.price + result.specPrice) * result.number;
								isOne = true;
							}
							shoppingCartList.push(result);
						});
						if (!isOne) {
							shoppingCartList.push(cartGoods);
						}
					} else {
						shoppingCartList = [];
						shoppingCartList.push(cartGoods);
					}
					authService.setWxStorage(`${shopId}_shoppingCartList`, shoppingCartList);
					_this.specList = [];
					_this.specLoading = true;
					_this.specificationsDialog = false;
					_this.getShoppingCartList(_this.shopInfo.shop.id);
					toastService.hideLoading();
				}, () => {});
			},
			//事件处理函数
			/*点击减号*/
			bindMinus: function(e) {
				console.log('bindMinus', e)
				var _this = this;
				let cartId = e.currentTarget.dataset.cartid;
				let number = e.currentTarget.dataset.number;
				let index = e.currentTarget.dataset.index;
				//点击减号后,当前商品的数量小于1,就进行删除该商品
				//重新计算被选中的商品的总金额
				totalPrice = 0;
				if (number == 1) {
					toastService.showModal(null, '确定不要这个了吗？', function confirm() {

						_this.updateNumber(cartId, 1, 0, index, function callback() {
							totalNum--;
							if (_this.shoppingCartList.length == 1) {
								_this.shoppingCartDialog = false;
							}
							toastService.hideLoading();
							_this.getShoppingCartList(_this.shopId);
						});
					});
					return;
				}
				this.updateNumber(cartId, 1, 0, index, function callback() {
					totalNum--;
					_this.getShoppingCartList(_this.shopId);
				});
			},
			/*点击加号*/
			bindPlus: function(e) {
				var _this = this;
				let cartId = e.currentTarget.dataset.cartid;
				let number = e.currentTarget.dataset.number;
				let index = e.currentTarget.dataset.index;
				totalPrice = 0;
				this.updateNumber(cartId, 1, 1, index, function callback() {
					//重新计算被选中的商品的总金额
					totalNum++;
					_this.getShoppingCartList(_this.shopId);
				});
			},
			updateNumber(id, number, type, index, callbak) {
				toastService.showLoading();
				authService.getWxStorage(`${this.shopId}_shoppingCartList`).then((result) => {
					if (result) {
						var resultList = [];
						result.forEach((result, index_re) => {
							console.log(result.id, id)
							if (result.id == Number(id) && index == index_re) {
								if (type == 1) {
									result.number = result.number + 1;
								} else {
									result.number = result.number - 1;
								}
								result.totalPrice = (result.price + result.specPrice) * result.number;
							}
							if (result.number > 0) {
								resultList.push(result);
							}
						});

						authService.setWxStorage(`${this.shopId}_shoppingCartList`, resultList);
						callbak();
					}
					toastService.hideLoading();
				});
			},
			goToPay() {
				//判断店铺是否打烊
				let startTime = this.shopInfo.shop.startTime;
				let endTime = this.shopInfo.shop.endTime;
				let isOperating = this.shopInfo.shop.isOperating;
				this.toPay();
			},

			toPay() {
				var list = this.shoppingCartList;
				if (list.length == 0) {
					toastService.showToast("请选择商品后再结算");
					return
				}
				this.shoppingCartDialog = false;
				setTimeout(() => {
					uni.navigateTo({
						url: `../pay/pay?shopId=${this.shopId}&storgeKey=${this.shopId}_shoppingCartList`
					});
				}, 100);
			},
			close(e) {
				console.log(e);
				this.setData({
					specificationsDialog: false,
					shoppingCartDialog: false,
					isActivityDialog: false,
					isOutofDeliveryRangeDialog: false
				});
			},
			topViewHeight() {
				var _this = this;
				isInitShow = false;
				const query = uni.createSelectorQuery();
				let windowHeight = app.globalData.systemInfoSync.windowHeight;
				let screenHeight = app.globalData.systemInfoSync.screenHeight;
				let statusBarHeight = app.globalData.systemInfoSync.statusBarHeight * 2;
				console.log(windowHeight);
				query.selectAll('#page-top-view')
					.boundingClientRect(function(rect) {
						console.log(rect);
						_this.setData({
							pageTopView: rect[0].height * 2,
							contentHeight: screenHeight - rect[0].height * 2
						});
					})
					.exec();
				console.log(app.globalData.systemInfoSync);
			},
			//获取距离顶部的高度
			getScrollTop(selector) {
				var _this = this;
				return new Promise((resolve, reject) => {
					let query = uni.createSelectorQuery().in(_this);
					query.select(selector).boundingClientRect(data => {
						if (data && 'top' in data) {
							resolve(data.top);
						} else if (data && 'top' in data && _this.menuList.length > 0) {
							_this.getElementTop();
						}

					}).exec();
				})
			},
			/* 获取元素顶部信息 */
			async getElementTop() {
				var _this = this;
				/* Promise 对象数组 */
				let p_arr = [];

				/* 遍历数据，创建相应的 Promise 数组数据 */
				console.log("this.topHeight===", this.topHeight);
				for (let i = 0; i < this.menuList.length; i++) {
					const resu = await this.getScrollTop(`#into${i}`)
					p_arr.push(resu - this.topHeight)
				}
				console.log("p_arr====", p_arr)
				/* 主区域滚动容器的顶部距离 */
				this.getScrollTop("#scroll_right").then((res) => {
					console.log(res)
					let top = res;
					// #ifdef H5
					top += 43; //因固定提示块的需求，H5的默认标题栏是44px
					// #endif

					/* 所有节点信息返回后调用该方法 */
					Promise.all(p_arr).then((data) => {
						_this.topArr = data;
					});
				})
			},

			/* 主区域滚动监听 */
			mainScroll(e) {
				console.log(e)
				if (!this.isMainScroll) {
					return;
				}
				console.log(this.topArr);
				if (this.topArr.length > 0) {
					let top = e.detail.scrollTop;
					let index = -1;
					if (top >= this.topArr[this.topArr.length - 1]) {
						index = this.topArr.length - 1;
					} else {
						index = this.topArr.findIndex((item, index) => {
							return this.topArr[index + 1] >= top;
						});
					}
					this.activeLeftTab = (index < 0 ? 0 : index);
				} else {
					this.getElementTop();
				}

			},
			/* 主区域触摸 */
			mainTouch() {
				this.isMainScroll = true;
			},
			/* 左侧导航点击 */
			leftTap(e) {
				let index = e.currentTarget.dataset.index;
				this.isMainScroll = false;
				this.activeLeftTab = Number(index);
				this.activeTab = Number(index);
				this.scrollInto = `into${index}`;
			},
			//回到顶部
			goTop(e) { // 一键回到顶部
				var _this = this;
				this.$nextTick(() => {
					setTimeout(() => {
						_this.setData({
							topRightNumer: 0,
							topLeftNumer: 0,
							activeLeftTab: 0,
							activeTab: 0
						});
						setTimeout(() => {
							_this.getElementTop();

						}, 1000)
					}, 500);

				});
			},
		}
	};
</script>
<style>
	.position-sticky {
		background: inherit;
	}

	.content-class {
		width: 100%;
		/* height: 100%;
		overflow: hidden; */
	}

	/* 地址定位 */
	.store-full-name-view {
		box-shadow: -2px 0.5px 5px 0.5px rgba(0, 0, 0, 0.1);
		padding: 15rpx 20rpx;
		margin: 15rpx 20rpx;
		border-radius: 10rpx;
		background: #fff;
	}

	.banner-view {
		margin: 10rpx 20rpx;
		border-radius: 10rpx;
	}

	.place-image {
		width: 10%;
		height: auto;
		padding-right: 20rpx;
	}

	.business-text {
		font-size: 24rpx;
		font-weight: bold;
	}

	.right-class {
		width: 90%;
	}

	.store-full-name-place-view {
		font-size: 26rpx;
		font-weight: bold;
	}

	.distance-phone-username-veiw {
		color: #6b6b6b;
		font-size: 24rpx;
		line-height: 42rpx;
	}

	.tips-view {
		font-size: 24rpx;
		display: flex;
		align-items: center;
	}

	.mbp-view {
		color: #a1a1a1;
	}

	.bar- {
		font-size: 28rpx;
		margin: 0 10rpx;
	}

	/* 单选框样式--自取配送 */
	.radio-group-view {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 25%;
	}

	.radio-group-label-radio {
		display: none;
	}

	.radio-group-label {
		width: 46%;
		padding: 2%;
		font-size: 26rpx;
		border-radius: 50rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		/* height: 60rpx; */
	}

	.not-active {
		color: white;
	}

	/* 轮播图样式 */
	swiper {
		width: 100%;
		height: 366rpx;
	}

	.slide-image {
		width: 100%;
		height: 366rpx;
		border-radius: 10rpx;
	}

	.business-view {
		/* position: absolute;
  top: 6%; */
		text-align: center;
		width: 100%;
		margin-top: -20%;
	}

	.business-image {
		width: 160rpx;
		height: 160rpx;
		border: 2rpx solid #f5f5f5;
	}

	.business-info {
		/* margin-top: 40rpx; */
		text-align: center;
		margin-left: 20px;
		margin-right: 20px;
	}

	.business-info-name {
		font-size: 36rpx;
		font-weight: bold;
	}

	.notice-view {
		font-size: 24rpx;
		color: #9b9b9b;
	}

	.business-info-other {
		font-size: 22rpx;
	}

	.swiper-tabs-choice {
		width: 100%;
		text-align: center;
		height: 88rpx;
		line-height: 88rpx;
		display: flex;
		flex-flow: row;
		justify-content: space-between;
		background: #fff;
		z-index: 1;
		border-bottom: 6rpx solid #f5f5f5;
	}

	.swiper-tabs-choice-item {
		width: 50%;
	}

	.swiper-items {
		height: 100%;
	}

	/* 菜单品类样式 */
	.swiper-tab {
		/* width: 24%; */
		text-align: center;
		flex-flow: row;
		justify-content: space-between;
		z-index: -1;
		font-size: 34rpx;
	}

	.swiper-tab-item {
		/* width: 100%; */
		/* color:#969696; */
		display: flex;
		align-items: center;
		justify-content: center;
		padding: 10rpx 15rpx;
		font-size: 28rpx;
		height: 80rpx;
		font-weight: bold;
	}

	.swiper_table_item_view {
		display: flex;
		align-items: center;
		justify-content: center;
		/* border-bottom: 4rpx solid #fff; */
	}

	.swiper-box {
		display: block;
		width: 100%;
		height: 100%;
		overflow: hidden;
	}

	.swiper-active {
		background: white;
		transition: 0.5s;
	}

	.swiper-items {
		height: 100%;
	}

	.selectMenuTap {
		width: 40%;
	}

	.scroll-views {
		height: 100%;
		background: #f5f5f5;
		border-radius: 10rpx;
	}

	.full-reduction-view {
		display: flex;
		align-items: center;
		background: white;
		padding: 10rpx 0rpx;
		height: 34rpx;
	}

	.full-reduction-list {
		font-size: 24rpx;
		font-weight: bold;
		padding: 0 10rpx;
		margin: 0 5rpx;
		border-radius: 10rpx;
	}

	.full-reduction-text {
		color: white;
		margin-left: 20rpx;
		font-size: 28rpx;
		width: 10%;
		text-align: center;
		border-radius: 15rpx;
	}

	.business-discount-list {
		padding: 1rpx 6rpx;
		font-size: 20rpx;
		font-weight: bold;
		border-radius: 10rpx;
		margin-right: 5rpx;
	}

	.other-promotionList {
		width: 27%;
		display: flex;
		align-items: center;
	}

	.business-discount {
		width: 85%;
		padding-bottom: 1rpx;
		margin-bottom: 10rpx;
		display: flex;
		align-items: center;
	}

	.business-info-flex {
		display: flex;
		align-items: center;
		justify-content: space-between;
		font-size: 24rpx;
		color: #717171;
		margin: 10rpx 10rpx 10rpx 0;
	}

	.commodity-menu-view {
		box-shadow: -2px 0.5px 5px 0.5px rgba(0, 0, 0, 0.1);
		height: 100%;
		display: flex;
		justify-content: space-between;
		border-radius: 10rpx;
		padding-bottom: 73px;
	}

	.commodity-detail-view {
		border-radius: 10rpx;
	}

	/* 商品信息 */
	.commodity-item-view {
		display: flex;
		flex-direction: column;
		background: white;
		border-radius: 10rpx;
		padding-top: 20rpx;

	}

	.commodity-type {
		padding: 10rpx 20rpx;
		background: white;
		z-index: 1;
		border-radius: 15rpx;
		box-shadow: 0 -1px #fff;
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
		padding: 10rpx 20rpx 10rpx 20rpx;
		align-items: center;
		background-color: white;
		border-radius: 15rpx;
		position: relative;
	}

	.commodity-image {
		width: 170rpx;
		height: 166rpx;
		border-radius: 8rpx;
		margin-right: 10rpx;
	}

	.commodity-name-view {
		width: 100%;
		position: relative;
	}

	.line-view {
		background: #b0b0b0;
		width: 100%;
		height: 2rpx;
	}

	.commodity-name {
		font-size: 30rpx;
		color: #969696;
		font-weight: 600;
	}

	.commodity-english {
		font-size: 24rpx;
		color: #b0b0b0;
	}

	.money-view {
		font-size: 32rpx;
		font-weight: 600;
		margin-top: 50rpx;
	}

	.insert-view {
		border-radius: 50%;
		width: 40rpx;
		height: 40rpx;
		line-height: 40rpx;
		display: flex;
		align-items: center;
		justify-content: center;

	}

	.shangjiatuijianjia {
		margin-right: 35rpx;
	}

	.commodity-name-english-view {
		width: 55%;
	}

	#space-view {
		height: 20rpx;
		background: white;
	}

	.now-order-image {
		width: 100%;
		height: 100%;
	}

	/* shopcart 样式 */
	.content-fullReductionRuleName {
		width: 100%;
		background: #fffadc;
		font-size: 24rpx;
		text-align: center;
		position: sticky;
		top: 0;
		z-index: 0;
		opacity: 0.7;
		visibility: visible;
	}

	.shopping-cart-detail {
		position: fixed;
		bottom: 0;
		z-index: 1;
		width: 100%;
		background: white;
	}

	.highlight {
		position: relative;
		top: -10px;
		width: 80rpx;
		line-height: 80rpx;
		text-align: center;
		height: 80rpx;
		margin-left: 20rpx;
		margin-right: 40rpx;
		border-radius: 50%;
		border: 10rpx solid #444444;
	}

	.shopping-cart-content {
		width: 100%;
		display: flex;
		align-items: center;
		background: #535257;
		z-index: 9999;
		height: 100rpx;
	}

	.shopping-cart-left {
		width: 70%;
		height: 100%;
		display: flex;
		align-items: center;
		background: #505052;
		z-index: 9999;
	}

	.shopping-cart-left-view {
		position: relative;
	}

	.shopping-cart-right {
		width: 30%;
		height: 100%;
		text-align: center;
		color: white;
		z-index: 9999;
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

	.fullPriceReductionClass {
		color: gainsboro;
		text-decoration: line-through;
		margin-right: 20rpx;
	}

	.totalPrice {
		color: white;
		font-weight: bold;
	}
	.shopping-cart-totalPrice{
		.full-price-reduction {
			font-weight: bold;
			color: white;
		}
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
		top: 0rpx;
		left: 80rpx;
		width: 48rpx;
		height: 48rpx;
		line-height: 48rpx;
		text-align: center;
		border-radius: 50%;
		font-size: 24rpx;
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
		width: 255rpx;
		height: 200rpx;
		border-radius: 15rpx 15rpx 0 0;
	}

	.business-recommend-title {
		font-size: 34rpx;
		font-weight: bold;
		margin: 20rpx 20rpx 0 20rpx;
	}

	.business-recommend-scroll-view {
		margin-top: 20rpx;
		padding-bottom: 20rpx;
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
		/* justify-content: space-between; */
	}

	.business-recommend-item {
		width: 255rpx;
		height: auto;
		background: #f5f5f5;
		border-radius: 15rpx;
		margin: 0 10rpx;
	}

	.item-two {
		margin: 0 3.5%;
	}

	.recommend-sell-out {
		position: absolute;
		top: 10%;
		margin-left: 10%;
		height: 100rpx;
		line-height: 100rpx;
		width: 100rpx;
		text-align: center;
		opacity: 0.7;
		border-radius: 50%;
		font-size: 30rpx;
		background: #434343;
		color: white;
	}

	.business-recommend-detail-view {
		width: 255rpx;
		height: auto;
	}

	.fullname-class {
		margin-top: 11rpx;
		font-size: 26rpx;
	}

	.engname-class {
		font-size: 24rpx;
		color: #ccc;
		width: 90%;
	}

	.business-recommend-money-view {
		width: 100%;
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 0rpx 15rpx;
	}

	.fullname-stepper {
		margin-bottom: 10rpx;
	}

	.business-recommend-money-view .stepper {
		margin-right: 30rpx;
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
	}

	.commdity-name {
		font-size: 32rpx;
		font-weight: bold;
		line-height: 50rpx;
	}

	.commdity-engname {
		font-size: 28rpx;
		line-height: 60rpx;
	}

	.commdity-type-item {
		width: 100%;
		height: 100%;
		flex-wrap: wrap;
		padding-bottom: 10rpx;
	}

	.commdity-type-name {
		font-size: 28rpx;
		margin-right: 30rpx;
	}

	.radio-group {
		width: 80%;
		flex-wrap: wrap;
		padding: 10rpx 10rpx 10rpx 0;
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
		padding: 20rpx 20rpx 20rpx 0;
		border-bottom: 1rpx solid #f5f5f5;
	}

	.goodsName-restructure-view {
		width: 100%;
	}

	.goodsName-packingCharges {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 20rpx;
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
		margin-top: 20rpx;
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
		font-size: 26rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 15rpx 20rpx;
		background: white;
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
	}

	.is-end-item {
		background-color: white;
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
		/* border-right: 1rpx solid #f5f5f5; */
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

	.evaluate-items {
		padding: 20rpx;
		margin-bottom: 80rpx;
	}

	.evaluate-item {
		display: flex;
		justify-content: space-between;
		padding-bottom: 30rpx;
		padding-top: 20rpx;
		border-bottom: 1rpx solid #f5f5f5;
	}

	.appraise-reply-items {
		background: #f7f7f7;
		border-radius: 10rpx;
		padding: 10rpx 15rpx;
	}

	.reply-item {
		font-size: 24rpx;
		color: #4f4f4f;
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
		width: 60%;
		font-size: 28rpx;
	}

	.images-url {
		width: 100rpx;
		height: 100rpx;
		margin-right: 10rpx;
	}

	.datetime-detail {
		font-size: 24rpx;
		color: #7d7d7d;
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
		justify-content: center;
		font-size: 26rpx;
		color: #9b9b9b;
	}

	.pl-dz-display {
		display: flex;
		align-items: center;
	}

	.input-pinglun {
		width: 100%;
		padding: 20rpx;
	}

	.userinfo-text {
		font-size: 28rpx;
		text-align: end;
		font-weight: bold;
	}

	.input-appraise {
		width: 92%;
		border: 1rpx solid #8e8e8e;
		padding: 10rpx;
		border-radius: 15rpx;
		margin: 20rpx;
		text-align: start;
		height: 150rpx;
	}

	.pinglun-bottom {
		position: fixed;
		width: 100%;
		bottom: 0;
		background: white;
		border-top: 1rpx solid #f5f5f5;
	}

	.reply-button-view {
		text-align: end;
		padding: 0 20rpx 20rpx 20rpx;
	}

	.extClassShopDetail {
		z-index: 9999;
	}

	.shop-detail-dialog {
		margin-bottom: 20rpx;
	}

	.reduced-delivery-price {
		font-size: 28rpx;
		margin-top: 20rpx;
	}

	.dialog-title {
		font-size: 30rpx;
	}

	.edit-address-class {
		text-align: center;
		height: 50px;
	}

	.edit-address-btn {
		margin: 20rpx 0;
	}

	.icongouwuche1 {
		font-size: 40rpx;
	}

	.iconweibiaoti35-copy {
		font-size: 26rpx;
	}

	.weui-vtabs-bar__content {
		width: 100%;
	}

	.mp-vtabs-class-1 {
		width: 32%;
	}

	.weui-vtabs-bar__item {
		width: 100%;
	}

	#menu_list {
		background: #f6f6f6;
		padding-top: 20rpx;
	}

	.menu_list {
		position: relative;
	}

	.menu_scorll_mask {
		position: absolute;
		top: 0;
		height: 100%;
		width: 100%;
		z-index: 100;
	}


	.content {
		padding: 0 16px 16px 16px;
	}

	.van-tabs {
		display: flex;
		justify-content: space-between;
	}

	/* 左侧导航栏 */
	.van-tabs__nav {
		position: relative;
		display: block;
		background-color: #EEEEEE;
		-webkit-user-select: none;
		user-select: none;
	}

	.van-tabs__wrap--scrollable .van-tabs__nav {
		overflow-x: hidden;
		overflow-y: auto;
		-webkit-overflow-scrolling: touch;
	}

	.van-tabs__nav--line.van-tabs__nav--complete {
		padding-right: 0px;
		padding-left: 0px;
		position: fixed;
		left: 0;
	}

	.van-tabs__nav--line.van-tabs__nav--complete {
		padding-right: 0px;
		padding-left: 0px;
	}

	.van-tabs__wrap {
		height: 100%;
		width: auto;
		overflow-y: auto;
	}

	.van-tabs__line {
		display: none;
	}

	.van-tab {
		position: relative;
		height: 50px;
		width: auto;
	}

	.van-tab--active {
		color: red;
		background-color: white;
	}

	.van-tab--active::before {
		position: absolute;
		top: 50%;
		left: 0;
		width: 4px;
		height: 16px;
		background-color: #ee0a24;
		-webkit-transform: translateY(-50%);
		transform: translateY(-50%);
		content: '';
	}

	/* 右侧导航栏 */
	.van-tabs__content {
		background-color: white;
		width: calc(100% - 94px);
		height: 100%;
		overflow-y: auto;
	}

	.van-tabs--line {
		height: 90%;
		display: flex;
		flex-direction: column;
		width: 6rem;
	}

	.van-tabs__wrap {
		height: 90%;
	}

	.van-tabs--line.van-tabs__wrap.van-tabs__wrap--scrollable.wrap-class {
		height: 90%;
	}

	.van-tabs__nav {
		display: flex;
		flex-direction: column;
	}

	.van-tabs .van-tabs--line {
		height: 80vh;
	}

	.van-tabs .van-tabs__scroll {
		height: 80vh;
	}

	.public-views {
		padding: 32rpx;
	}

	.self_out_button {
		background: #ededed;
		border-radius: 50rpx;
		height: 2rem;
		line-height: 2rem;
		width: 30%;
		padding: 5rpx;
	}

	.self_button {
		font-size: 24rpx;
		width: 50%;
		text-align: center;
		padding: 0 10rpx;
	}

	.self_out_active {
		color: white;
		padding: 0 10rpx;
		border-radius: 50rpx;
	}

	.shop_info_view {
		font-size: 32rpx;
		width: 70%;
	}

	.distance_m {
		font-size: 28rpx;
		color: var(--tab-text-color, #646566);
		margin-top: 10rpx;
	}

	.menu_left_scorll {
		width: 30vh;
		background: #f6f6f6;
	}

	.menu_right_scorll {
		padding: 0 20rpx;
		background: #f6f6f6;

	}

	.menu_right_scorll_view {
		background: white;
		border-radius: 15rpx;
	}

	.vtabs-content-item {
		border-radius: 15rpx;
	}

	.menu_left_content {
		height: 80rpx;
		line-height: 80rpx;
		text-align: center;
		font-size: 26rpx;
		padding: 0 20rpx;
		box-shadow: 0 -1px #f6f6f6;
	}

	.left_active {
		border: none;
		border-radius: 0 40rpx 40rpx 0;
	}

	.no_login_out_tip {
		font-size: 26rpx;
	}

	#shopping-cart-detail {
		bottom: calc(var(--window-bottom));
	}

	.menu_cart_view {
		position: relative;
	}

	.menu_cart_number {
		position: absolute;
		top: -20rpx;
		right: -20rpx;
		background: red;
		border-radius: 50%;
		width: 35rpx;
		height: 35rpx;
		line-height: 35rpx;
		text-align: center;
		color: white;
		font-size: 20rpx;
	}
</style>