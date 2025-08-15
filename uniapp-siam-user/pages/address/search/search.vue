<template>
	<view>
		<view class="section" v-if="userLocation">
			<view class="picker flex_center" @tap="bindOpenRegionDialog">
				<view>{{ region[1] ? region[1] : '定位中...' }}</view>
				<van-icon name="arrow" v-if="region[1]" />
			</view>
			<input @input="bindInput" :placeholder="'请输入地址名称	' + scope.userLocation" :focus="true" />
		</view>
		<view>
			<scroll-view :scroll-y="true" :style="'height:' + (winHeight-20) + 'px;'">
				<view @tap="bindSearch" :data-keywords="i" class="text_box" v-for="(i, index) in tips" :key="index">
					<view class="address-name">
						{{ i.name }}
					</view>

					<view class="address-address">
						{{ i.address }}
					</view>
				</view>
				<van-empty v-if="!isLoading&&tips.length == 0" description="没有搜索到地址">
					<van-button type="primary" size="small" color="#004ca0" class="bottom-button" v-if="!userLocation"
						@tap="openSettingInfo">位置授权</van-button>
				</van-empty>
				<view class="loading_box" v-if="isLoading&&tips.length==0">
					<van-loading custom-class="loading_box_class" vertical>加载中...</van-loading>
				</view>
			</scroll-view>
		</view>
		<van-action-sheet :show="openRegionDialog" @close="close" @cancel="close" title="选择城市" z-index="2">
			<view class="content">
				<van-area :area-list="areaList" :value="areaValue" :columns-num="2" @cancel="close"
					@change="bindRegionChange" @confirm="bindRegionComfirm" />
			</view>
		</van-action-sheet>
		<!-- <van-popup :show="openRegionDialog" round custom-style="height: 50%;" position="bottom">
			
		</van-popup> -->
	</view>

</template>

<script>
	import toastService from '../../../utils/toast.service';
	import amapFile from '../../../utils/gaode-libs/amap-wx';
	import * as Config from '../../../utils/gaode-libs/config';
	import {
		areaList
	} from '@vant/area-data';
	var lonlat;
	var city = "北京市";
	var keywords;
	let app = null;
	export default {
		data() {
			return {
				tips: [],
				isButton: false,
				customItem: '',
				region: ['北京市', '北京市'],
				street: '',
				location: '',
				longitude: '',
				latitude: '',
				userLocation: false,
				winHeight: '',
				areaList: [],
				scope: {
					userLocation: ''
				},
				i: {
					name: '',
					address: ''
				},
				openRegionDialog: false,
				areaValue: '',
				isLoading: true,
				amapPlugin: null,
			};
		},
		onShow: function(e) {
			app = getApp();
			this.areaList = areaList;
			this.getSettingInfo();
			// this.getRegeoAddress();
			// this.getAutoHeight();
		},
		methods: {
			bindInput: function(e) {
				var _this = this;
				keywords = e.detail.value;
				if (!keywords) {
					this.tips = [];
					return;
				}
				var key = Config.key();
				var myAmapFun = new amapFile.AMapWX({
					key: key
				});
				toastService.showLoading();
				console.log("city=", city);
				myAmapFun.getInputtips({
					keywords: keywords,
					location: _this.longitude + ',' + _this.latitude,
					city: city,
					citylimit: true,
					output: 'JSON',
					success: function(data) {
						toastService.hideLoading();
						if (data && data.tips) {
							_this.tips = data.tips;
						}
					}
				});
			},

			bindSearch: function(e) {
				keywords = e.currentTarget.dataset.keywords;
				console.log(keywords);
				if (!keywords.location) {
					toastService.showToast("当前地址不可用");
					return
				}
				this.prevPage(keywords);
			},

			prevPage(data) {
				var pages = getCurrentPages();
				var prevPage = pages[pages.length - 2]; //上一个页面
				console.log("data===", data);
				console.log("prevPage===", prevPage);
				console.log("fullPath===", prevPage.$page.fullPath);
				//直接调用上一个页面的 setData() 方法，把数据存到上一个页面中去
				let district;
				let city;
				let regins;
				if (data.district) {
					if (data.district.indexOf("省") > 0) {
						console.log("有省市区");
						district = data.district.split('省');
						city = district[1].split('市');
					} else {
						console.log("没省");
						district = ''
						city = data.district.split('市');
					}

					regins = [district ? district[0] + '省' : '', city[0] + '市', city[1]];
				}
				console.log(regins);
				console.log(prevPage.$page.fullPath.indexOf("pages/address/choose/choose"));
				console.log(prevPage.$page.fullPath.indexOf("pages/address/insert/insert"));
				if (prevPage.$page.fullPath.indexOf("pages/address/choose/choose") >= 1) {
					prevPage.$vm.getShopList(data.location);
				}

				if (prevPage.$page.fullPath.indexOf("pages/address/insert/insert") >= 1 ||
					prevPage.$page.fullPath.indexOf("pages/address/edit/edit") >= 1) {
					prevPage.$vm.region = data.district ? regins : this.region;
					prevPage.$vm.street = data.name;
					prevPage.$vm.location = data.location;
					prevPage.$vm.longitude = data.location.split(',')[0];
					prevPage.$vm.latitude = data.location.split(',')[1];
				}
				uni.navigateBack(1);
			},

			getRegeoAddress() {
				var _this = this;
				var key = Config.key();
				var myAmapFun = new amapFile.AMapWX({
					key: key
				});
				myAmapFun.getRegeo({
					success: function(data) {
						console.log("myAmapFun.getRegeo====", data);
						//console.log(_this.areaList);
						var areaList = _this.areaList;
						var areaValue = "";
						var province = data[0].regeocodeData.addressComponent.province;
						city = data[0].regeocodeData.addressComponent.city;
						var district = data[0].regeocodeData.addressComponent.district;
						for (var key in areaList) {
							//console.log(key,areaList[key]);
							if (key == "city_list") {
								//console.log(province);
								for (var key_list in areaList[key]) {
									//console.log(key_list, areaList[key][key_list]);
									if (city == areaList[key][key_list]) {
										areaValue = key_list;
									}
								}
							}
						}
						province = province.length > 0 ? province : '北京市';
						city = city.length > 0 ? city : '北京市';
						console.log(province, city);
						_this.region = [province, city];
						_this.tips = data[0].regeocodeData.pois;
						_this.areaValue = areaValue;
						_this.isLoading = false;
						_this.initData(data[0].regeocodeData.addressComponent.streetNumber.location);
					},
					fail: function(info) {
						//toastService.showLoading();
						//toastService.showToast(info.errMsg)
						// wx.showModal({title:info.errMsg})
					}
				});
			},

			getSettingInfo() {
				let _this = this;
				setTimeout(() => {
					_this.isLoading = false;
				}, 2000);
				// #ifdef H5
				console.log(1111111111);
				app.globalData.getH5Location().then(result => {
					console.log("app.globalData.getAppLocation(),", result);
					const longs = result.longitude.toString();
					const lat = result.latitude.toString();
					if (longs !== '' && lat !== '') {
						_this.userLocation = true;

						_this.getRegeoAddress();
						_this.getAutoHeight();
					}
				});
				// #endif

				// #ifdef APP-PLUS
				app.globalData.getAppLocation().then(result => {
					console.log("app.globalData.getAppLocation(),", result);
					const longs = result.longitude.toString();
					const lat = result.latitude.toString();
					if (longs !== '' && lat !== '') {
						_this.userLocation = true;
						_this.getRegeoAddress();
						_this.getAutoHeight();
					}
				});
				// #endif

				// #ifdef MP-WEIXIN||MP-ALIPAY
				uni.getSetting({
					success(res) {
						console.log(res);
						_this.userLocation = res.authSetting['scope.userLocation'];
						_this.tips = [];
						if (res.authSetting['scope.userLocation']) {
							_this.getRegeoAddress();
							_this.getAutoHeight();
						} else {
							//未授权位置信息则打开设置项
							uni.openSetting({
								success(res) {
									console.log(res.authSetting)
									// res.authSetting = {
									//   "scope.userInfo": true,
									//   "scope.userLocation": true
									// }
								}
							})
						}
					}
				});
				// #endif

			},

			openSettingInfo() {
				this.getSettingInfo();
			},

			initData(location) {
				console.log("location=", location, "city=", city, "keywords=", keywords);
				var _this = this;
				var key = Config.key();
				var myAmapFun = new amapFile.AMapWX({
					key: key
				});
				myAmapFun.getInputtips({
					keywords: keywords,
					output: 'JSON',
					location: location,
					city: city,
					citylimit: true,
					success: function(data) {
						console.log("initData", data);
						if (data && data.tips) {
							_this.tips = data.tips;
						}
					}
				});
			},
			bindRegionChange: function(e) {
				console.log('picker发送选择改变，携带值为', e)
				// this.setData({
				// 	regionChangeValue: [e.detail.values[0],e.detail.values[1]]
				// });
			},
			bindRegionComfirm: function(e) {
				console.log('picker发送选择改变，携带值为', e);
				// #ifdef APP-PLUS||H5
				this.region = [e.selectedOptions[0].text, e.selectedOptions[1].text];
				this.areaValue = e.selectedOptions[1].value;
				city = e.selectedOptions[1].name;

				// #endif

				// #ifdef MP-WEIXIN||MP-ALIPAY
				this.region = [e.detail.values[0].name, e.detail.values[1].name];
				this.areaValue = e.detail.values[1].code;
				city = e.detail.values[1].name;
				// #endif

				var value = {
					detail: {
						value: keywords
					}
				};

				this.bindInput(value);
				this.close();
			},

			getAutoHeight() {
				//获取用户手机系统信息
				var winHeight = 0;
				let height = 0;
				var _this = this;
				uni.getSystemInfo({
					success: function(res) {
						winHeight = res.windowHeight; //获取高度
						//获取class为settlement-view并减去这个高度，自适应scrollview的高度
						setTimeout(function timeout() {
							uni.createSelectorQuery()
								.in(_this)
								.selectAll('.section')
								.boundingClientRect(function(rects) {
									console.log(".section", rects)
									rects.forEach(function(rect, index) {
										height = height + rect.height;
									});
									if (rects.length > 0) {
										_this.setData({
											winHeight: winHeight - rects[0].height
										});
									}

								}).exec();
						}, 1000);
					}
				});
			},
			bindOpenRegionDialog() {
				console.log("==============================")
				this.setData({
					openRegionDialog: true
				})
			},
			close() {
				this.setData({
					openRegionDialog: false
				})
			}
		}
	};
</script>
<style>
	page {
		background: #f5f5f5;
	}

	.section {
		background: white;
		padding: 10rpx 20rpx;
		display: flex;
		align-items: center;
		border-bottom: 0.5rpx solid #c3c3c3;
	}

	.section input {
		width: 80%;
		margin: 5rpx 10rpx;
		border: 1px solid #c3c3c3;
		height: 30px;
		border-radius: 3px;
		padding: 0 5px;
	}

	.text_box {
		margin: 25rpx 20rpx;
		padding: 22rpx 20rpx;
		background: white;
		border-radius: 15rpx;
	}

	picker {
		width: 30%;
	}

	.picker {
		font-size: 28rpx;
		width: 20%;
	}

	.address-name {
		font-size: 28rpx;
		font-weight: bold;
	}

	.address-address {
		font-size: 24rpx;
		color: #c3c3c3;
	}
</style>