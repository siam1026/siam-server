<template>
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
				<van-button type="primary" size="small" class="bottom-button" v-if="!userLocation"
				@tap="openSettingInfo" custom-style="background: #004ca0;border: 1px #004ca0;">位置授权</van-button>
			</van-empty>
			<view class="loading_box" v-if="isLoading&&tips.length==0">
				<van-loading custom-class="loading_box_class" vertical>加载中...</van-loading>
			</view>
		</scroll-view>
	</view>
	<van-popup :show="openRegionDialog" round custom-style="height: 50%;" position="bottom">
		<view class="content">
			<van-area :area-list="areaList" :value="areaValue" :columns-num="2" title="选择城市" @cancel="close"
				@change="bindRegionChange" @confirm="bindRegionComfirm" />
		</view>
	</van-popup>
</template>

<script>
	var toastService = require('../../../utils/toast.service');
	import amapFile from '../../../utils/gaode-libs/amap-wx';
	import * as Config from '../../../utils/gaode-libs/config';
	import {
		areaList
	} from '@vant/area-data';
	var lonlat;
	var city;
	var keywords;
	export default {
		data() {
			return {
				tips: [],
				isButton: false,
				customItem: '',
				region: '',
				street: '',
				location: '',
				longitude: '',
				latitude: '',
				userLocation: '',
				winHeight: '',
				areaList: areaList,
				scope: {
					userLocation: ''
				},

				i: {
					name: '',
					address: ''
				},
				openRegionDialog: false,
				areaValue: '',
				isLoading:true
			};
		},
		onShow: function(e) {
			this.getSettingInfo();
			// this.getRegeoAddress();
			// this.getAutoHeight();
		},
		methods: {
			bindInput: function(e) {
				var _this = this;
				keywords = e.detail.value;
				if (!keywords) {
					this.setData({
						tips: []
					});
					return;
				}
				var key = Config.key();
				var myAmapFun = new amapFile.AMapWX({
					key: key
				});
				toastService.showLoading();
				console.log("city=",city);
				myAmapFun.getInputtips({
					keywords: keywords,
					location: _this.longitude + ',' + _this.latitude,
					city: city,
					citylimit: true,
					output: 'JSON',
					success: function(data) {
						toastService.hideLoading();
						if (data && data.tips) {
							_this.setData({
								tips: data.tips
							});
						}
					}
				});
			},

			bindSearch: function(e) {
				keywords = e.currentTarget.dataset.keywords;
				console.log(keywords);
				this.prevPage(keywords);
			},

			prevPage(data) {
				var pages = getCurrentPages();
				var prevPage = pages[pages.length - 2]; //上一个页面
				console.log("prevPage===",prevPage);
				console.log("fullPath===",prevPage.$page.fullPath);
				//直接调用上一个页面的 setData() 方法，把数据存到上一个页面中去
				let district;
				let city;
				let regins;
				if (data.district) {
					district = data.district.split('省');
					city = district[1].split('市');
					regins = [district[0] + '省', city[0] + '市', city[1]];
				}
				console.log(prevPage.$page.fullPath.indexOf("pages/address/choose/choose"));
				console.log(prevPage.$page.fullPath.indexOf("pages/address/insert/insert"));
				if(prevPage.$page.fullPath.indexOf("pages/address/choose/choose")>=1){
					prevPage.$vm.getShopList(data.location);
				}
				
				if(prevPage.$page.fullPath.indexOf("pages/address/insert/insert")>=1){
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
						console.log(_this.areaList);
						var areaList = _this.areaList;
						var areaValue = "";
						var province = data[0].regeocodeData.addressComponent.province;
						city = data[0].regeocodeData.addressComponent.city;
						var district = data[0].regeocodeData.addressComponent.district;
						for (var key in areaList) {
							//console.log(key,areaList[key]);
							if (key == "city_list") {
								console.log(province);
								for (var key_list in areaList[key]) {
									console.log(key_list, areaList[key][key_list]);
									if (city == areaList[key][key_list]) {
										areaValue = key_list;
									}
								}
							}
						}
						_this.setData({
							region: [province, city],
							tips: data[0].regeocodeData.pois,
							areaValue: areaValue,
							isLoading:false
						});
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
				uni.getSetting({
					success(res) {
						console.log(res);
						_this.setData({
							userLocation: res.authSetting['scope.userLocation'],
							tips: []
						});
						if (res.authSetting['scope.userLocation']) {
							_this.getRegeoAddress();
							_this.getAutoHeight();
						}
					}
				});
			},

			openSettingInfo() {
				let that = this;
				uni.openSetting({
					success(res) {
						that.getSettingInfo();
						// if (res.authSetting['scope.userLocation']) {
						//   that.getRegeoAddress();
						//   that.getAutoHeight();
						// }
					}
				});
			},

			initData(location) {
				var key = Config.key();
				var myAmapFun = new amapFile.AMapWX({
					key: key
				});
				myAmapFun.getInputtips({
					location: location,
					city: city,
					citylimit: true,
					success: function(data) {
						if (data && data.tips) {
							that.setData({
								tips: data.tips
							});
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
				console.log('picker发送选择改变，携带值为', e)
				this.setData({
					region: [e.detail.values[0].name, e.detail.values[1].name],
					areaValue: e.detail.values[1].code
				});
				var value = {
					detail: {
						value: keywords
					}
				};
				city = e.detail.values[1].name;
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