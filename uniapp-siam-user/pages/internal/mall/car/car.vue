<template>
	<view>
		<scroll-view :scroll-y="true" :style="'height:' + winHeight + 'px;'">
			<checkbox-group class="weui-slidecells" @change="checkboxChange">
				<view class="page__bd">

					<view class="weui-slidecells" v-for="(item, index) in items" :key="index">
						<van-swipe-cell :right-width="65">
							<view :class="'weui-slidecell ' + (item.disable ? 'isDisable' : '')">
								<label class="checkbox-group-label">
									<!-- <checkbox :value="index" :index="index" class="theme-color theme-border-color"
										:checked="item.checked" :disabled="item.disable" iconColor="#FFFFFF"
										style="transform:scale(0.7);" /> -->
									<checkbox :value="index" :index="index" class="theme-color theme-border-color"
										:checked="item.checked" :disabled="item.disable" iconColor="#FFFFFF" />
									<view class="commdity-item">
										<image
											:src="item.mainImage ? item.mainImage : '/static/assets/images/load-image.png'"
											mode="aspectFill" class="commodity-icon"></image>
										<view class="sell-out out-store" v-if="item.goodsStatus == 1">未上架</view>
										<view class="sell-out out-store" v-else-if="item.goodsStatus == 3">已下架</view>
										<view class="sell-out out-store" v-else-if="item.goodsStatus == 4">已售罄</view>
										<view class="commdity-types">
											<view class="commodity-name-type">
												<view class="commdity-name out_of_range one_row">{{ item.goodsName }}
												</view>
												<view class="types">{{ item.restructure }}</view>
											</view>
											<view class="commdity-money">￥{{ item.price }}</view>
										</view>
									</view>
								</label>
								<view class="stepper">
									<view class="flex_center car_reduce_add reduce-class" @tap="bindMinus"
										:data-num="index + ',' + item.number">－</view>
									<input disabled type="number" :value="item.number" class="radd-reduce-input" />
									<view @tap="bindPlus" class="flex_center car_reduce_add add-class"
										:data-num="index + ',' + item.number">＋</view>
								</view>
							</view>
							// #ifdef APP-PLUS||H5
							<template #right>
								<view style="height: 100%;" class="flex_center">
									<view class="flvan-swipe-cell__right flex_between" :data-checkboxIndex="index"
										@tap="slideButtonTap($event, { checkboxIndex: index })">
										<van-icon name="delete-o" />
									</view>
								</view>
							</template>
							// #endif
							// #ifdef MP-WEIXIN||MP-ALIPAY
							<view slot="right" class="flvan-swipe-cell__right flex_between" :data-checkboxIndex="index"
								@tap="slideButtonTap($event, { checkboxIndex: index })">
								<van-icon name="delete-o" />
							</view>
							// #endif
						</van-swipe-cell>
					</view>
				</view>
			</checkbox-group>
			<van-empty v-if="items.length <= 0" description="您的购物车有点寂寞"></van-empty>
			<view class="gotogg theme-bg" @tap="goToDrink" v-if="items.length <= 0">去逛逛</view>
			<view class="like-list-view" v-if="likeList.length > 0">
				<view class="title-like-view theme-color">
					<view class="title-view">猜你喜欢</view>
				</view>
				<view class="like-items">
					<block v-for="(item, index) in likeList" :key="index">
						<view class="like-item" v-if="index < 6" @tap="commodityDetailTap" :data-id="item.id">
							<view class="like-detail-view">
								<image :src="item.mainImage" mode="aspectFill" class="icon-like-class"></image>
								<view class="fullname-class out_of_range one_row">{{ item.name }}</view>
								<view class="engname-class out_of_range one_row">{{ item.detailmainImage }}</view>
							</view>

							<view class="like-money-view">
								<view class="like-money">
									<text>￥</text>
									<text>{{ item.price }}</text>
								</view>
								<view class="plus-view theme-bg">+</view>
							</view>
						</view>
					</block>

					<van-empty v-if="likeList.length <= 0" description="暂无数据"></van-empty>
				</view>
			</view>
		</scroll-view>
		<view class="settlement-view" v-if="items.length > 0">
			<view class="total-payable-view">
				<view class="total-payable">
					<text class="title-text">应付合计</text>
					<view class="total-payable-money-view">
						<text class="sign-icon">￥</text>
						<text class="total-payable-money">{{ totalPrice }}</text>
					</view>
				</view>
				<view class="baozhuangfei-class">
					<text :decode="true">运费 ￥0&nbsp;&nbsp;</text>
					<text class="theme-color-border full-reduction-view"
						v-if="fullPriceReductionIsHidden">{{ fullReductionRuleName }}</text>
				</view>
			</view>
			<view class="to-pay-view theme-bg" v-if="(items.length>0&&fullPriceReduction>=0&&totalPrice>0)"
				hover-class="hover-class-public" @tap="goToPay">
				去结算(￥{{ fullPriceReductionIsHidden ? fullPriceReduction : totalPrice }})
			</view>
		</view>
	</view>
</template>

<script>
	import GlobalConfig from '../../../../utils/global-config';
	import https from '../../../../utils/http';
	import authService from '../../../../utils/auth';
	import toastService from '../../../../utils/toast.service';
	let app = null;
	var winHeight = 0;
	var totalPrice = 0;
	var totalNum = 0;
	export default {
		data() {
			return {
				slideButtons: [{
					src: '/static/assets/common/icon-del.svg' // icon的路径
				}],
				items: [],
				minusStatus: 'disable',
				disabled: '',
				totalPrice: '',
				winHeight: '',
				fullPriceReduction: '',
				packingCharges: '',
				fullReductionRuleName: '',
				fullPriceReductionIsHidden: '',
				likeList: '',
				imageTip: ''
			};
		},
		onLoad: function(option) {

		},
		onShow: function() {
			app = getApp();
			toastService.showLoading();
			authService.checkIsLogin().then((result) => {
				this.getLoveIt();
				if (result) {
					this.getShoppingCartList();
					return;
				}
				toastService.hideLoading();
				this.setData({
					items: []
				});
				this.initData();
				app.globalData.checkIsAuth('scope.userInfo');
			});
			console.log(this);
		},
		methods: {
			initData() {
				const items = this.items;
				totalNum = 0;
				totalPrice = 0;
				for (let i = 0; i < items.length; i++) {
					items[i].checked = true;
					if (items[i].disable) {
						items[i].checked = false;
					}
					if (items[i].checked) {
						totalPrice += items[i].price * items[i].number; //初始化被选中的商品的总金额
					}

					totalNum = totalNum + items[i].number;
				}
				this.setTabBarBadge(totalNum);
				this.setData({
					items: items,
					totalPrice: totalPrice
				});
				this.getFullReductionRule();
				this.getAutoHeight();
			},

			getAutoHeight() {
				//获取用户手机系统信息
				var _this = this;
				uni.getSystemInfo({
					success: function(res) {
						var winHeight = res.windowHeight; //获取高度
						var height = 0;
						//console.log(winHeight)
						//获取class为settlement-view并减去这个高度，自适应scrollview的高度
						setTimeout(function timeout() {
							uni.createSelectorQuery()
								.in(_this)
								.selectAll('.settlement-view')
								.boundingClientRect(function(rects) {
									console.log(".settlement-view", rects)
									rects.forEach(function(rect, index) {
										height = height + rect.height;
									});
									if (rects.length > 0) {
										winHeight = winHeight - (height);
									}
									_this.setData({
										winHeight: winHeight
									});
								}).exec();
						}, 500);
					}
				});
			},

			slideButtonTap(e, _dataset) {
				/* ---处理dataset begin--- */
				this.handleDataset(e, _dataset);
				/* ---处理dataset end--- */
				//console.log('slide button tap', e)
				var that = this;
				let items = that.items;
				let cheakedBoxIndex = e.currentTarget.dataset.checkboxindex; //商品下标
				let number = items[cheakedBoxIndex].number; //该商品的数量
				let id = items[cheakedBoxIndex].id; //该商品的id
				if (items[cheakedBoxIndex].disable) {
					that.updateNumber(id, number, 0, function callback() {
						items.splice(cheakedBoxIndex, 1); //删除商品
						that.setData({
							items: items
						});
						totalNum -= number;
						that.setTabBarBadge(totalNum);
					});
					return;
				}
				toastService.showModal(
					null,
					'确定不要这个了吗？',
					function confirm() {
						that.updateNumber(id, number, 0, function callback() {
							totalPrice = totalPrice - items[cheakedBoxIndex].price * number; //总价格减去当前商品数量总和
							items.splice(cheakedBoxIndex, 1); //删除商品
							that.setData({
								items: items,
								totalPrice: totalPrice
							});
							that.getAutoHeight();
							that.getFullReductionRule();
							console.log(number);
							totalNum -= number;
							console.log(totalNum);
							that.setTabBarBadge(totalNum);
						});
					},
					null
				);
			},

			checkboxChange(e) {
				//console.log('checkbox发生change事件，携带value值为：', e)
				const items = this.items; //获取该用户的所有商品列表
				const values = e.detail.value; //获取点击选中或取消选中的下标值

				//遍历分类信息给第一级分类为false，提交的时候对应各级分类
				for (var j in items) {
					items[j].checked = false;
				}
				totalPrice = 0;
				let totalNum = 0;
				let fullPriceReduction = 0;
				let packingCharges = 0;
				for (var i in values) {
					items[values[i]].checked = true; //给选中的第二级分类的checked设置为true
					//console.log(items[values[i]].price + "------------" + items[values[i]].number)
					totalPrice = totalPrice + items[values[i]].price * items[values[i]].number;
					fullPriceReduction = fullPriceReduction + items[values[i]].price * items[values[i]].number;
					totalNum = totalNum + items[values[i]].number;
					packingCharges = packingCharges + items[values[i]].packingCharges * items[values[i]].number;
				}
				this.setTabBarBadge(totalNum); //设置角标
				this.setData({
					items,
					totalPrice: totalPrice,
					fullPriceReduction: fullPriceReduction,
					packingCharges: packingCharges
				});
				this.getFullReductionRule();
			},

			//事件处理函数
			/*点击减号*/
			bindMinus: function(e) {
				var that = this;
				let numList = e.currentTarget.dataset.num.split(','); //获取购物车的列表下表和商品数量
				let items = this.items; //获取购物车列表信息
				//点击减号后则当前商品的数量-1,如果这个商品不能被操作则return
				if (items[numList[0]].disable) {
					return;
				}
				if (!items[numList[0]].checked) {
					toastService.showToast('未选择商品');
					return;
				}

				//点击减号后,当前商品的数量小于1,就进行删除该商品
				// items[numList[0]].number = Number(numList[1]) - 1;
				let number = items[numList[0]].number - 1;
				//重新计算被选中的商品的总金额
				totalPrice = 0;
				let packingCharges = items[numList[0]].packingCharges; //获取该点击的商品的包装费
				for (let i = 0; i < items.length; i++) {
					if (items[i].checked) {
						//如果被选中则进行总额计算（已经计算出的价格+商品价格*商品的数量)
						if (numList[0] == i) {
							//console.log(number)
							totalPrice = totalPrice + items[i].price * number;
						} else {
							//console.log(items[i].price * items[i].number)
							totalPrice = totalPrice + items[i].price * items[i].number;
						}
					}
				}
				if (numList[1] == 1) {
					toastService.showModal(null, '确定不要这个了吗？', function confirm() {
						that.updateNumber(items[numList[0]].id, 1, 0, function callback() {
							items.splice(Number(numList[0]), 1); //列表减一
							totalNum--;
							that.setData({
								items: items,
								totalPrice: totalPrice,
								packingCharges: that.packingCharges - packingCharges
							});
							that.setTabBarBadge(totalNum);
							that.getFullReductionRule();
							that.getAutoHeight();
						});
					});
					return;
				}
				//点击减号后,当前商品的数量小于1,就进行删除该商品
				items[numList[0]].number = Number(numList[1]) - 1;
				this.updateNumber(items[numList[0]].id, 1, 0, function callback() {
					totalNum--;
					that.setTabBarBadge(totalNum);
					that.setData({
						items: items,
						totalPrice: totalPrice,
						packingCharges: that.packingCharges - packingCharges //所有商品包装费减去当前点击的商品的包装费
					});

					that.getFullReductionRule();
				});
			},

			/*点击加号*/
			bindPlus: function(e) {
				var that = this;
				let numList = e.currentTarget.dataset.num.split(',');
				let items = this.items;
				totalPrice = 0;
				if (items[numList[0]].disable) {
					return;
				}
				if (!items[numList[0]].checked) {
					toastService.showToast('未选择商品');
					return;
				}
				items[numList[0]].number = Number(numList[1]) + 1; //当前商品的数量+1
				let packingCharges = items[numList[0]].packingCharges; //获取该点击的商品的包装费;
				this.updateNumber(items[numList[0]].id, 1, 1, function callback() {
					//重新计算被选中的商品的总金额
					for (let i = 0; i < items.length; i++) {
						if (items[i].checked) {
							//如果被选中则进行总额计算（已经计算出的价格+商品价格*商品的数量)
							totalPrice = totalPrice + items[i].price * items[i].number;
						}
					}
					totalNum++;
					that.setTabBarBadge(totalNum);
					that.setData({
						items: items,
						totalPrice: totalPrice,
						packingCharges: that.packingCharges + packingCharges
					});
					that.getFullReductionRule();
				});
			},

			setTabBarBadge(num) {
				app.globalData.setTabBarBadge(num);
			},

			goToPay() {
				//判断店铺是否打烊
				var list = this.items;
				var orderDetail = {};
				orderDetail.actualPrice = this.totalPrice;
				orderDetail.packingCharges = this.packingCharges;
				orderDetail.orderDetailList = [];
				var isTap = false;
				for (var key in list) {
					if (list[key].checked) {
						isTap = true;
						orderDetail.orderDetailList.push({
							goodsId: list[key].goodsId,
							specList: list[key].specList,
							number: list[key].number,
							goodsName: list[key].goodsName,
							restructure: list[key].restructure,
							price: list[key].price,
							id: list[key].id,
							packingCharges: list[key].packingCharges
						});
					}
				}
				if (isTap) {
					uni.navigateTo({
						url: '../pay/pay?orderDetail=' + JSON.stringify(orderDetail) + '&payType=car'
					});
					return;
				}
				this.goToTap();
			},

			goToDrink() {
				uni.switchTab({
					url: '../../mall/index/index'
				});
			},

			commodityDetailTap(e) {
				uni.navigateTo({
					url: '../detail/detail?id=' + e.currentTarget.dataset.id
				});
			},

			getShoppingCartList() {
				https
					.request('/rest/member/pointsMall/shoppingCart/list', {
						pageNo: -1,
						pageSize: 20
					})
					.then((result) => {
						if (result.success) {
							var packingCharges = 0;
							result.data.records.forEach((result) => {
								if (result.mainImage) {
									result.mainImage = GlobalConfig.ossUrl + result.mainImage;
								}
								let specList = '';
								for (var key in JSON.parse(result.specList)) {
									specList = (specList ? specList + '/' : specList) + JSON.parse(result
										.specList)[key];
								}
								console.log(result);
								result.restructure = specList;
								result.disable = result.goodsStatus == 1 || result.goodsStatus == 3 || result
									.goodsStatus == 4 ? true : false;
								packingCharges =
									result.goodsStatus == 1 || result.goodsStatus == 3 || result.goodsStatus ==
									4 ?
									packingCharges :
									packingCharges + result.packingCharges * result.number;
							});
							//console.log(result.data.records)
							this.setData({
								items: result.data.records,
								packingCharges: packingCharges
							});
							//if (result.data.records.length > 0) {
							this.initData();
							//}
						}
					});
			},

			//获取满减规则
			getFullReductionRule() {
				toastService.showLoading('正在加载...', true);
				https
					.request('/rest/pointsMall/fullReductionRule/list', {
						pageNo: -1,
						pageSize: 1
					})
					.then((result) => {
						toastService.hideLoading();
						if (result.success) {
							let fullPriceReduction = 0;
							let fullReductionRuleName = '';
							let fullPriceReductionIsHidden = false;
							let limitedPrice = 0;
							for (let i = 0; i < result.data.records.length; i++) {
								if (totalPrice + this.packingCharges >= result.data.records[i].limitedPrice) {
									if (result.data.records[i].limitedPrice > limitedPrice) {
										limitedPrice = result.data.records[i].limitedPrice;
										fullPriceReduction = totalPrice - result.data.records[i].reducedPrice;
										fullReductionRuleName = result.data.records[i].name;
										fullPriceReductionIsHidden = true;
									}
								}
							}
							this.setData({
								fullPriceReduction: fullPriceReduction,
								fullReductionRuleName: fullReductionRuleName,
								fullPriceReductionIsHidden: fullPriceReductionIsHidden
							});
						}
					});
			},

			getLoveIt() {
				toastService.showLoading();
				https.request('/rest/pointsMall/goods/guessLikeGoodsList', {}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						//console.log(result.data)
						if (result.success) {
							result.data.forEach(function(item, index) {
								item.mainImage = GlobalConfig.ossUrl + item.mainImage;
							});
							this.setData({
								likeList: result.data
							});
						}
					}
				});
			},

			updateNumber(id, number, type, callbak) {
				https
					.request('/rest/member/pointsMall/shoppingCart/updateNumber', {
						id: id,
						number: number,
						type: type
					})
					.then((result) => {
						if (result.success) {
							callbak();
						}
					});
			},

			goToTap() {
				toastService.showModal(null, '至少购买一件商品', null, null, false);
			}
		}
	};
</script>
<style>
	page {
		background-color: #f7f7f7;
		padding-top: 10rpx;
	}

	/* .weui-slidecells {
  margin-top: 10rpx;
} */

	.weui-slidecell {
		display: flex;
		justify-content: space-between;
		margin: 10rpx 20rpx 10rpx 20rpx;
		background-color: #fff;
		border-radius: 8px;
		padding: 20rpx 15rpx;
		line-height: 1.4;
		font-size: 17px;
		color: rgba(0, 0, 0, 0.9);
	}

	.is-first {
		margin-top: 20rpx;
	}

	.commdity-item {
		display: flex;
		align-items: center;
		justify-content: flex-start;
		width: 100%;
	}

	.commodity-icon {
		width: 25%;
		height: 108rpx;
		margin-right: 20rpx;
		border-radius: 10rpx;
	}

	.commdity-types {
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		width: 72%;
	}

	.commodity-name-type {
		margin-bottom: 5rpx;
	}

	.commodity-image {
		width: 190rpx;
		height: auto;
		border-radius: 5rpx;
	}

	.checkbox-group-label {
		display: flex;
		align-items: center;
		width: 75%;
	}

	.commdity-name {
		font-size: 26rpx;
		font-weight: bold;
	}

	.types {
		font-size: 22rpx;
		color: #858585;
	}

	.commdity-money-add-subtract {
		width: 50%;
		display: flex;
		flex-direction: column;
	}

	.commdity-money {
		font-size: 26rpx;
		font-weight: bold;
		display: flex;
		align-items: center;
	}

	/*主容器*/
	.stepper {
		margin-left: 20rpx;
	}

	/*加号和减号*/
	.stepper text {
		width: 40rpx;
		height: 40rpx;
		line-height: 40rpx;
		font-size: 28rpx;
	}

	/*数值*/
	.stepper input {
		width: 30px;
	}

	/* 底部去支付样式 */
	.settlement-view {
		position: fixed;
		bottom: 0;
		width: 100%;
		display: flex;
		justify-content: space-between;
		z-index: 66;
		box-shadow: -2px 0px 5px 0.5px rgba(0, 0, 0, 0.1);
		background: white;
	}

	.total-payable-view {
		width: 65%;
		background: #fff;
		padding-left: 30rpx;
		display: flex;
		flex-direction: column;
	}

	.total-payable {
		display: flex;
		align-items: center;
		padding: 10rpx 0;
	}

	.baozhuangfei-class {
		font-size: 24rpx;
		display: flex;
		align-items: center;
		margin-bottom: 10rpx;
	}

	.total-payable-money-view {
		margin-left: 20rpx;
	}

	.to-pay-view {
		/* width: 25%; */
		font-size: 28rpx;
		color: white;
		display: flex;
		align-items: center;
		justify-content: center;
		padding: 0 10rpx;
	}

	.title-text {
		font-size: 28rpx;
		color: #858585;
	}

	.sign-icon {
		font-size: 32rpx;
		font-weight: bold;
	}

	.total-payable-money {
		font-size: 34rpx;
		font-weight: bold;
	}

	/* 猜你喜欢样式 */

	.like-list-view {
		padding: 20rpx;
	}

	.title-like-view {
		display: flex;
		align-items: center;
		justify-content: center;
		margin-bottom: 20rpx;
	}

	.title-view {
		font-size: 30rpx;
		font-weight: bold;
	}

	.icon-font-view {
		display: flex;
		align-items: center;
		font-size: 28rpx;
	}

	.icon-change-class {
		width: 36rpx;
		height: auto;
		margin-right: 10rpx;
	}

	.icon-like-class {
		width: 100%;
		height: 100%;
		border-radius: 15rpx 15rpx 0 0;
	}

	.like-items {
		width: 100%;
		display: grid;
		grid-template-columns: 1fr 1fr 1fr;
		grid-row-gap: 10px;
		grid-column-gap: 10px;
	}

	.like-item {
		height: 25vh;
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
		background: white;
		border-radius: 15rpx;
		margin-bottom: 15rpx;
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

	.plus-view {
		font-size: 28rpx;
		width: 38rpx;
		height: 38rpx;
		line-height: 38rpx;
		text-align: center;
		border-radius: 50%;
		color: white;
	}

	.out-store {
		left: 13.8%;
		font-size: 22rpx;
		height: 80rpx;
		line-height: 80rpx;
		width: 80rpx;
	}

	.full-reduction-view {
		font-size: 20rpx;
		font-weight: bold;
		padding: 0 10rpx;
		margin: 0 5rpx;
		border-radius: 10rpx;
	}

	.gotogg {
		text-align: center;
		margin: 0 35% 15% 35%;
		border-radius: 50rpx;
		padding: 10rpx 0;
		font-size: 30rpx;
	}

	.flvan-swipe-cell__right {
		color: red;
		width: 100%;
		height: 100rpx;
		line-height: 100rpx;
		font-size: 48rpx;
		width: 100rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		background-color: white;
		border-radius: 50%;
	}

	.van-swipe-cell__right {
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.settlement-view {
		bottom: calc(var(--window-bottom));
	}
</style>