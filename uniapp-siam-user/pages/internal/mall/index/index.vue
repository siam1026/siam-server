<template>
	<view>
		<view class="top-carousel-swiper" v-if="carouselUrls.length > 0">
			<swiper :indicator-dots="indicatorDots" class="carousel-swiper" :autoplay="autoplay" :interval="interval"
				:duration="duration" :indicator-active-color="afterColor">
				<block v-for="(item, index) in carouselUrls" :key="index">
					<swiper-item class="carousel-swiper-item">
						<image :src="item.imagePath" class="carousel-image" mode="aspectFill"
							:data-imageLinkUrl="item.imageLinkUrl" @tap="carouseCommodityDetailTap" />
					</swiper-item>
				</block>
			</swiper>
		</view>
		<view class="menu-items">
			<view class="menu-item" @tap="bindToMenu" :data-shopid="item.shipId" hover-class="hover-class-public"
				v-for="(item, index) in classifyList" :key="index">
				<image mode="aspectFit" class="menu-image" :src="item.iconUrl" v-if="item.icon"></image>

				<view class="menu-title out_of_range one_row">{{ item.name }}</view>
			</view>
		</view>

		<view class="like-items">
			<view class="like-item" hover-class="hover-class-public" @tap="commodityDetailTap"
				:data-shopid="item.shopId" :data-id="item.id" v-for="(item, index) in recommendGoodsList" :key="index">
				<image :src="item.mainImage" mode="scaleToFill" class="icon-like-class"></image>

				<view class="fullname-class">
					<text class="name-text out_of_range two_row">{{ item.name }}</text>
				</view>

				<view class="like-money-view">
					<view class="like-money">
						<text>￥</text>
						<text>
							{{ item.price }}
							<text class="score-money" :decode="true">&nbsp;&nbsp;(可用积分等额兑换)</text>
						</text>
					</view>
				</view>

				<view class="like-money-view">
					<text class="latelyMonthlySales out_of_range one_row">已售：{{ item.latelyMonthlySales }}</text>
					<view class="insert-car" @tap.stop.prevent="openSpecifications" :data-index="index">
						<van-icon name="plus" class="icongouwuche1 theme-bg"/>
					</view>
				</view>
			</view>
		</view>
		<van-divider contentPosition="center" custom-style="padding: 20px;" v-if="isMore">没有更多啦</van-divider>
		<van-action-sheet :show="specificationsDialog" @close="close" title="选择规格">
			<view class="content_box">
				<view class="goods-info-view">
					<image :src="goods.mainImage" mode="aspectFill" class="commodity-image"></image>
					<view>
						<view class="goods-info-name out_of_range one_row">{{ goods.name }}</view>
						<view class="goods-info-specListString">已选：{{ specListString }}</view>
						<view class="goods-info-price">￥{{ priceAfter }}</view>
					</view>
				</view>
				<scroll-view scroll-y style="height: 52vh">
					<view class="commdity-name-type-view">
						<view class="commdity-type-item" v-if="specList" v-for="(item, key) in specList" :key="key">
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
						<van-empty v-if="specList.length <= 0" description="暂无规格"></van-empty>
					</view>
				</scroll-view>

				<view slot="footer">
					<view class="good-choice-btn theme-bg" @tap="insertShoppingCart" :data-goodsid="goods.id">我选好了
					</view>
				</view>
			</view>
		</van-action-sheet>
	</view>
</template>

<script>
	import GlobalConfig from '../../../../utils/global-config';
	import https from '../../../../utils/http';
	import toastService from '../../../../utils/toast.service';
	import authService from '../../../../utils/auth';
	const app = getApp();
	var pageNo = 1;
	var pageSize = 10;
	export default {
		data() {
			return {
				indicatorDots: true,
				autoplay: true,
				interval: 5000,
				duration: 1000,

				//beforeColor: "white",//指示点颜色,
				afterColor: '#f1a142',

				//当前选中的指示点颜色
				opacity: 0.4,

				recommendGoodsList: [],
				specList: [],
				isMore: false,
				carouselUrls: '',
				classifyList: '',
				priceAfter: '',
				specListString: '',
				specificationsDialog: false,

				goods: {
					mainImage: '',
					name: '',
					id: ''
				},

				specLis: ''
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			pageNo = 1;
			this.getSystemUsageRecord();
			this.getRecommendGoodsList();
		},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {
			//pageNo=1;
			app.globalData.getShoppingCarNumber();
			this.getClassifyList();
			//this.getRecommendGoodsList();
			this.getCarouselList();
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
			pageNo = 1;
			this.getRecommendGoodsList();
		},
		/**
		 * 页面上拉触底事件的处理函数
		 */
		onReachBottom: function() {
			console.log('1111111111');
			toastService.showLoading('正在加载...');
			var that = this;
			pageNo = pageNo + 1;
			https
				.request('/rest/pointsMall/goods/recommendGoodsList', {
					pageNo: pageNo,
					pageSize: pageSize
				})
				.then((result) => {
					toastService.hideLoading();
					if (result.success) {
						console.log(result.data);
						if (result.data.records.length <= 0) {
							pageNo = pageNo - 1;
							that.setData({
								isMore: result.data.hasNextPage ? false : true
							});
							//toastService.showToast("没有更多啦~");
							return;
						}
						result.data.records.forEach(function(item, index) {
							item.mainImage = GlobalConfig.ossUrl + item.mainImage;
							that.recommendGoodsList.push(item);
						});
						console.log(this.recommendGoodsList);
						this.setData({
							recommendGoodsList: this.recommendGoodsList,
							isMore: result.data.hasNextPage ? false : true
						});
					}
				});
		},
		/**
		 * 用户点击右上角分享
		 */
		onShareAppMessage: function() {},
		methods: {
			carouseCommodityDetailTap(e) {
				let _this = this;
				authService.checkIsLogin().then((result) => {
					console.log(result);
					if (result) {
						_this.getUserInfo(e);
						return;
					}
					app.globalData.checkIsAuth('scope.userInfo');
				});
			},

			getUserInfo: function(e) {
				https.request('/rest/member/getLoginMemberInfo', {}).then((result) => {
					if (result.success) {
						uni.navigateTo({
							url: e.currentTarget.dataset.imagelinkurl
						});
					} else {
						app.globalData.checkIsAuth('scope.userInfo');
					}
				});
			},

			getCarouselList() {
				https
					.request('/rest/advertisement/list', {
						type: 3,
						pageNo: -1,
						pageSize: 20
					})
					.then((result) => {
						if (result.success) {
							result.data.records.forEach(function(item, index) {
								item.imagePath = GlobalConfig.ossUrl + item.imagePath;
							});
							this.setData({
								carouselUrls: result.data.records
							});
						}
					});
			},

			getClassifyList() {
				https
					.request('/rest/pointsMall/menu/list', {
						pageNo: 1,
						pageSize: 8
					})
					.then((result) => {
						if (result.success) {
							result.data.records.forEach(function(item, index) {
								item.iconUrl = GlobalConfig.ossUrl + item.icon;
							});
							this.setData({
								classifyList: result.data.records
							});
						}
					});
			},

			getRecommendGoodsList() {
				toastService.showLoading();
				https
					.request('/rest/pointsMall/goods/recommendGoodsList', {
						pageNo: pageNo,
						pageSize: pageSize
					})
					.then((result) => {
						toastService.hideLoading();
						uni.stopPullDownRefresh();
						if (result.success) {
							result.data.records.forEach(function(item, index) {
								item.mainImage = GlobalConfig.ossUrl + item.mainImage;
							});
							this.recommendGoodsList = [];
							this.setData({
								recommendGoodsList: result.data.records,
								isMore: result.data.hasNextPage ? false : true
							});
						}
					});
			},

			commodityDetailTap(e) {
				console.log(e.currentTarget.dataset.id);
				uni.navigateTo({
					url: '../detail/detail?id=' + e.currentTarget.dataset.id
				});
			},

			bindToMenu(e) {
				uni.navigateTo({
					url: '../menu/menu'
				});
			},

			getSystemUsageRecord() {
				https
					.request('/rest/systemUsageRecord/insert', {
						type: 'intoPointsMall'
					})
					.then((result) => {
						if (result.success) {}
					});
			},

			openSpecifications(e) {
				this.selectByGoodsId(e.currentTarget.dataset.index);
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
				for (var i in checkValue) {
					specList[firstIndex][checkValue[i]].checked = true;
					//console.log(specList[firstIndex][checkValue[i]])
				}

				let price = this.goods.price;
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
				this.setData({
					specList: specList,
					priceAfter: price,
					specListString: specListString
				});
			},

			insertShoppingCart(e) {
				authService.checkIsLogin().then((result) => {
					toastService.showLoading();
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
							goodsId: e.currentTarget.dataset.goodsid,
							specList: JSON.stringify(goodsSpecs)
						};
						toastService.hideLoading();
						https.request('/rest/member/pointsMall/shoppingCart/insert', data, authService.getToken())
							.then((result) => {
								if (result.success) {
									toastService.showToast('加入购物车成功');
									this.setData({
										specificationsDialog: false
									});
									app.globalData.getShoppingCarNumber();
								}
							});
						return;
					}
					toastService.hideLoading();
					app.globalData.checkIsAuth('scope.userInfo');
				});
			},
			setTabBarBadge(num) {
			    app.globalData.setTabBarBadge(num);
			},

			selectByGoodsId(index) {
				let goods = this.recommendGoodsList[index];
				https.request('/rest/pointsMall/goodsSpecificationOption/selectByGoodsId', {
					goodsId: goods.id
				}).then((result) => {
					if (result.success) {
						//重新设置商品的规格等数据的格式
						//let goodsSpecs = {};
						let specList = result.data;
						let price = goods.price;
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
						console.log(goods);
						console.log(specList);
						this.setData({
							specListString: specListString ? specListString : '暂无规格',
							specList: JSON.stringify(specList) == '{}' ? [] : specList,
							goods: goods,
							specificationsDialog: true,
							priceAfter: price
						});
					}
				});
			},
			close() {
				this.setData({
					specificationsDialog: false
				})
			}
		}
	};
</script>
<style>
	page {
		width: 100%;
		background-color: #f5f5f5;
	}

	.top-carousel-swiper {
		width: 100%;
		border-radius: 10rpx;
	}

	.carousel-swiper {
		margin: 20rpx 20rpx 0 20rpx;
		height: 358rpx;
	}

	.carousel-swiper-item {
		border-radius: 15rpx;
		height: 100%;
	}

	.carousel-image {
		width: 100%;
		height: 100%;
	}

	.menu-items {
		/* display: flex;
   justify-content: flex-start;
   flex-flow: row wrap;
   align-content: space-around; */
		padding: 20rpx;
		background-color: #f5f5f5;
		display: grid;
		grid-template-columns: 1fr 1fr 1fr 1fr;
		grid-row-gap: 10px;
		grid-column-gap: 10px;
		/* margin-top: 15rpx; */
	}

	.menu-item {
		text-align: center;
		margin: 1%;
		border-radius: 15rpx;
		background-color: white;
		padding: 10rpx;
	}

	.menu-item-first {
		margin-bottom: 10rpx;
	}

	.menu-image {
		width: 100rpx;
		height: 100rpx;
		border-radius: 50%;
		border: 1rpx #ededed solid;
	}

	.menu-title {
		font-size: 26rpx;
	}

	/* 商品推荐 */
	.recommend-business-title {
		margin-bottom: 20rpx;
		font-weight: bold;
		font-size: 30rpx;
	}

	.like-items {
		display: grid;
		grid-template-columns: 1fr 1fr;
		grid-column-gap: 10px;
		padding: 0 20rpx 10rpx 20rpx;
		padding: 0 20rpx;
		background-color: #f5f5f5;
	}

	.like-item {
		display: flex;
		/* justify-content: center; */
		align-items: center;
		flex-direction: column;
		background: white;
		border-radius: 15rpx;
		margin-bottom: 20rpx;
		padding-bottom: 10rpx;
	}

	.icon-like-class {
		width: 100%;
		height: 315rpx;
		border-radius: 15rpx 15rpx 0 0;
	}

	.item-two {
		margin: 0 3.5%;
	}

	.like-detail-view {
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
		width: 90%;
	}

	.latelyMonthlySales {
		width: 75%;
		font-size: 26rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.go-to-shop {
		padding: 0 20rpx;
		border-radius: 10rpx;
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
		padding: 5rpx 0;
	}

	.like-money {
		font-size: 26rpx;
		font-weight: bold;
	}

	.insert-car {
		position: absolute;
		margin-top: -0.5%;
		margin-left: 36%;
	}

	.icongouwuche1 {
		width: 40rpx;
		height: 40rpx;
		font-size: 24rpx;
		border-radius: 50%;
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
		padding: 1%;
		margin: 1%;
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

	.commodity-image {
		width: 170rpx;
		height: 152rpx;
		border-radius: 8rpx;
		margin-right: 10rpx;
	}

	.good-choice-btn {
		width: 100%;
		text-align: center;
		padding: 20rpx 0;
		border-radius: 15rpx;
		font-size: 28rpx;
		font-weight: bold;
	}

	.commdity-name-type-view {
		padding: 20rpx;
		background: #fff;
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
</style>