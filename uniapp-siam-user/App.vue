<script>
	import https from './utils/http.js';
	import authService from './utils/auth';
	import toastService from './utils/toast.service';
	import dateHelper from './utils/date-helper';
	//var toastService = require('./utils/toast.service');
	//var dateHelper = require('./utils/date-helper');
	//var amapFile = require('./utils/gaode-libs/amap-wx');
	//var Config = require('./utils/gaode-libs/config');
	import amapFile from './utils/gaode-libs/amap-wx';
	import * as Config from './utils/gaode-libs/config';
	// #ifdef H5
	import AMapLoader from "@amap/amap-jsapi-loader";
	// #endif
	var initRegeoInfo = {
		name: '麓谷小镇',
		address: '岳麓大道尖山路口北300米',
		location: '112.885538,28.232363',
		isAutoLocation: false
	};
	export default {
		data() {
			return {};
		},
		onLaunch: function() {
			// 展示本地存储能力
			// var logs = wx.getStorageSync('logs') || []
			// logs.unshift(Date.now())
			// wx.setStorageSync('logs', logs)
			// #ifdef MP-WEIXIN||MP-ALIPAY
			uni.login({
				success: (res) => {
					console.log(res)
					// 发送 res.code 到后台换取 openId, sessionKey, unionId
					this.globalData.code = res.code;
				}
			});
			// #endif
			// 登录

			// 获取用户信息
			// wx.getSetting({
			//   success: res => {
			//     if (res.authSetting['scope.userInfo']) {
			//       // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
			//       wx.getUserProfile({
			//         success: res => {
			//           console.log(res)
			//           // 可以将 res 发送给后台解码出 unionId
			//           this.globalData.userInfo = res.userInfo

			//           // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
			//           // 所以此处加入 callback 以防止这种情况
			//           if (this.userInfoReadyCallback) {
			//             this.userInfoReadyCallback(res);
			//           }
			//         }
			//       })
			//     }
			//   }
			// })
			this.globalData.getSystemInfo();
			this.globalData.getUserInfo();
			// this.globalData.getUserAgent().then((result)=>{
			// 	toastService.showToast(result);
			// });

			this.globalData.getShoppingCarNumber();
			// #ifdef H5
			this.globalData.getH5Location();
			// #endif

			// #ifdef APP-PLUS
			this.globalData.getAppLocation();
			// #endif

			// #ifdef MP-WEIXIN||MP-ALIPAY
			this.globalData.getRegeoLocation();
			// #endif
		},
		onHide() {},
		onShow: function() {},
		globalData: {
			userInfo: null,
			appVersion: 5.28,

			getRegeoLocation() {
				let _this = this;
				setTimeout(function timeout() {
					var key = Config.key();
					var myAmapFun = new amapFile.AMapWX({
						key: key
					});

					// myAmapFun.getRegeo({
					// 	success: function(getRegeo) {
					// 		console.log(getRegeo);
					// 		if (getRegeo) {
					// 			_this.deliveryAndSelfTaking.location = getRegeo[0].longitude + ',' + getRegeo[0].latitude;
					// 		}
					// 		https.request('/rest/member/updateLastUseAddress', {
					// 				lastUseAddress: getRegeo[0].regeocodeData.formatted_address
					// 			}).then((result) => {
					// 				if (result.success) {
					// 				}
					// 			});
					// 	},
					// 	fail: function(info) {
					// 	}
					// });
				}, 1000);
			},
			//获取经纬度
			getH5Location() {
				var _this = this;
				console.log(2222222222);
				return new Promise((fulfil, reject) => {
					uni.getLocation({
						type: 'gcj02', //返回可以用于uni.openLocation的经纬度
						geocode: true, //设置该参数为true可直接获取经纬度及城市信息
						isHighAccuracy: true, // 开启地图精准定位
						success: (res) => {
							console.log("H5位置获取=", res);
							console.log('H5端当前位置的经度：' + res.longitude);
							console.log('H5端当前位置的纬度：' + res.latitude);
							const longs = res.longitude.toString();
							const lat = res.latitude.toString();
							if (longs !== '' && lat !== '') {
								this.deliveryAndSelfTaking.location = longs + ',' + lat;
								//this.turnAdrr(longs, lat)//经纬度转地区名
							}
							fulfil(res);
						},
						fail: (err) => {
							console.log("H5位置获取失败", err);
							fulfil(err);
						}
					});
				});
			},
			getAppLocation() {
				var _this = this;
				return new Promise((fulfil, reject) => {
					uni.getLocation({
						type: 'wgs84', // 返回可以用于高德地图的坐标
						geocode: true, //设置该参数为true可直接获取经纬度及城市信息
						altitude: true,
						success: (res) => {
							console.log('APP端当前位置的经度：' + res.longitude);
							console.log('APP端当前位置的纬度：' + res.latitude);
							const longs = res.longitude.toString();
							const lat = res.latitude.toString();
							if (longs !== '' && lat !== '') {
								_this.deliveryAndSelfTaking.location = longs + ',' + lat;
								//this.turnAdrr(longs, lat)//经纬度转地区名
							}
							fulfil(res);
						},
						fail: (err) => {
							console.error('APP端获取位置失败：', err);
							fulfil(err);
						}
					});
				});
			},
			getSettingInfo() {
				var _this = this;
				uni.getSetting({
					success(res) {
						if (res.authSetting['scope.userLocation']) {
							_this.authSetting = res.authSetting;
						}
					}
				});
			},
			getSystemInfo() {
				var res = uni.getSystemInfoSync();
				this.systemInfoSync = res;
			},
			getUserAgent() {
				return new Promise((resolve, reject) => {
					let en = window.navigator.userAgent.toLowerCase();
					// 判断微信方法1、匹配en中是否含有MicroMessenger字符串
					if (en.match(/MicroMessenger/i) == 'micromessenger') {
						resolve("H5_WECHAT");
					}
					// 判断微信方法2
					// if (/MicroMessenger/.test(window.navigator.userAgent)) {
					// 	console.log('微信浏览器');
					// }
					if (en.match(/MicroMessenger/i) != 'micromessenger') {
						if (/AlipayClient/.test(window.navigator.userAgent)) {
							//判断是否是支付宝浏览器打开
							resolve("H5_ALIPAY");
						} else {
							resolve("H5_OTHER");
						}
					}
				});
			},
			getCurrentEnvironment() {
				var _this = this;
				return new Promise((resolve, reject) => {
					uni.getSystemInfo({
						success: function(res) {
							const platform = res.platform.toLowerCase();
							if (platform === 'devtools') {
								var userAgent = _this.getUserAgent();
								resolve(userAgent);
							} else if (platform === 'ios' || platform === 'android') {
								resolve('APP');
							} else if (platform === 'wechat') {
								resolve('WECHAT');
							} else {
								resolve('OTHER');
							}
						},
						fail: function(error) {
							reject(error);
						}
					})
				});
			},
			getLoginCode() {
				return new Promise((fulfil, reject) => {
					uni.login({
						success: (res) => {
							if (res.code) {
								fulfil(res.code);
							}
						}
					});
				});
			},

			weChatLogin(phoneNumber, openId, inviterId) {
				console.log('app.js -> inviterId = ' + inviterId);
				console.log(this.userInfo);
				var userInfo = this.userInfo;
				if (!userInfo) {
					uni.getUserInfo({
						success: (res) => {
							this.memberWeChatLogin(phoneNumber, openId, inviterId, res.userInfo);
						}
					});
				} else {
					this.memberWeChatLogin(phoneNumber, openId, inviterId, userInfo);
				}
			},

			memberWeChatLogin(phoneNumber, openId, inviterId, userInfo) {
				https.request('/rest/member/weChat/login', {
					mobile: phoneNumber,
					headImg: userInfo.avatarUrl,
					username: userInfo.nickName,
					sex: userInfo.gender,
					openId: openId,
					inviterId: inviterId ? inviterId : ''
				}).then((result) => {
					if (result.success) {
						authService.setToken(result.token);
						authService.setOpenId(openId);
						authService.setPhoneNumber(phoneNumber);
						toastService.showSuccess(result.message);
						this.isNewPeople = result.isNewPeople;
						this.getUserInfo();
						let timeout = setTimeout(() => {
							//如果是邀请链接直接跳转到首页，如果是进入用户后退一页
							if (inviterId) {
								uni.switchTab({
									url: '/pages/index/index'
								});
							} else {
								var pages = getCurrentPages();
								if (pages && pages.length > 0) {
									uni.navigateBack(1);
								} else {
									uni.switchTab({
										url: '/pages/index/index'
									});
								}
							}
							clearTimeout(timeout);
						}, 300);
					}
				});
			},

			getMemberInfo: function(e) {
				return new Promise((fulfil, reject) => {
					https.request('/rest/member/getLoginMemberInfo', {}).then((result) => {
						if (result.success) {
							fulfil({
								isNewPeople: result.data.isNewPeople,
								isRemindNewPeople: result.data.isRemindNewPeople
							});
						}
					});
				});
			},

			getUserInfo() {
				https.request('/rest/member/getLoginMemberInfo', {}).then((result) => {
					if (result.success) {
						//console.log(result.data);
						this.loginUserInfo = result.data;
						//console.log(this.globalData.userInfo);
					}
				});
			},

			bindInDevelopment() {
				toastService.showModal(null, '敬请期待~', null, null, false);
			},

			getIsBusiness(startTime, endTime, isOperating) {
				return new Promise((fulfil, reject) => {
					let isBusiness = dateHelper.differenceTime(startTime, endTime);
					console.log('实际后端传值isOperating======》' + isOperating);
					console.log('计算商家的关店时间isBusiness====》' + isBusiness);
					if (!isOperating || !isBusiness) {
						fulfil(false);
						toastService.showModal(
							null,
							'店铺休息中，目前不能下单',
							function confirm() {
								return;
							},
							null,
							false
						);
						return;
					}
					fulfil(true);
				});
			},

			getIsItNear(startTime, endTime) {
				let itNear = dateHelper.itNearTime(startTime, endTime);
				console.log('是否相差30分钟===》' + itNear);
				console.log('商家开门时间======》' + startTime);
				console.log('商家打烊时间====》' + endTime);
				if (itNear) {
					if (!this.deliveryAndSelfTaking.isItNear) {
						this.deliveryAndSelfTaking.isItNear = true;
						toastService.showModal(
							null,
							'店铺临近打烊或已打烊',
							function confirm() {
								return;
							},
							null,
							false
						);
					}
				}
			},

			isRemindNewPeople() {
				return new Promise((fulfil, reject) => {
					https.request('/rest/member/updateIsRemindNewPeople').then((result) => {
						console.log(result);
						if (result.success) {
							fulfil(true);
						}
						fulfil(false);
					});
				});
			},

			checkIsAuth(authSetting, params) {
				toastService.showModal(
					null,
					'当前未登录，确定去登录吗?',
					function confirm() {
						// #ifdef H5
						let url = '/pages/internal/login/code/code';
						if (params) {
							if (params.inviterId) {
								url = url + (params.inviterId ? '?inviterId=' + params.inviterId : '');
							}
						}
						uni.navigateTo({
							url: url
						});
						// #endif
						// #ifdef MP-WEIXIN||MP-ALIPAY
						uni.getSetting({
							success: (res) => {
								console.log(res);
								console.log(res.authSetting[authSetting]);
								if (res.authSetting[authSetting]) {
									let url = '/pages/internal/login/choose/choose';
									if (params) {
										if (params.inviterId) {
											url = url + (params.inviterId ? '?inviterId=' + params.inviterId : '');
										}
									}
									uni.navigateTo({
										url: url
									});
								} else {
									let url = '/pages/internal/login/authorization/authorization';
									if (params) {
										if (params.inviterId) {
											url = url + (params.inviterId ? '?inviterId=' + params.inviterId : '');
										}
									}
									uni.redirectTo({
										url: url
									});
								}
							}
						});
						// #endif
					},
					null,
					true
				);
				
			},
			beforeClose(action) {
				new Promise((resolve) => {
					setTimeout(() => {
						// action !== 'confirm'  拦截取消操作
						resolve(action === 'confirm');
					}, 1000);
				})
			},
			getDeliveryAddressList() {
				https.request('/rest/member/deliveryAddress/list', {
					pageNo: -1,
					pageSize: 10
				}).then((result) => {
					if (result.success) {
						result.data.records.forEach((item, index) => {
							if (item.isDefault) {
								this.deliveryAndSelfTaking.deliveryAddress = item;
							}
						});
					}
				});
			},

			getShoppingCarNumber() {
				authService.checkIsLogin().then((result) => {
					if (result) {
						https.request('/rest/member/pointsMall/shoppingCart/list', {
							pageNo: -1,
							pageSize: 20
						}).then((result) => {
							if (result.success) {
								const items = result.data.records;
								let totalNum = 0;
								for (let i = 0; i < items.length; i++) {
									totalNum = totalNum + items[i].number;
								}
								if (totalNum) {
									uni.setTabBarBadge({
										index: 3,
										text: String(totalNum)
									});
									return;
								}
								uni.removeTabBarBadge({
									index: 3
								});
							}
						});
						return;
					}
				});
			},
			setTabBarBadge(num) {
				if (num) {
					uni.setTabBarBadge({
						index: 3,
						text: String(num)
					});
					return;
				}
				uni.removeTabBarBadge({
					index: 3
				});
			},
			deliveryAndSelfTaking: {
				modeList: [{
						id: 0,
						radioName: '配送',
						title: '配送信息',
						tipName: '送达',
						checked: true
					},
					{
						id: 1,
						radioName: '自取',
						tipName: '可取',
						title: '自提门店',
						mode: [{
								id: 0,
								name: '店内用餐',
								checked: true
							},
							{
								id: 0,
								name: '自提带走',
								checked: false
							}
						],
						checked: false
					}
				],
				radioIndex: 0,
				feeData: 0,
				shopAddress: null,
				currentTab: 0,
				isReducedDeliveryPrice: false,
				reducedDeliveryTotalPrice: 0,
				isItNear: false,
				isThereADiscount: false,
				location: initRegeoInfo.location,
				isOutofDeliveryRange: false,
				initRegeoInfo: initRegeoInfo,
				regeoInfo: initRegeoInfo,
				ifIndexSwitchTab: false,
				ifChooseBack: false,
				ifChoosePayBack: false,
				selfOutActiveIndex: 0,
				payType: '',
				orderDetail: {
					initShopInfo: {}
				}
			},
			authSetting: '',
			systemInfoSync: '',
			isNewPeople: '',
			loginUserInfo: ''
		}
	};
</script>
<style>
	.margin-common {
		margin: 0rpx 20rpx;
	}

	/* 右侧箭头公共样式 */
	.right-white-class {
		width: 18rpx;
		height: auto;
		margin-left: 20rpx;
	}

	/* 分割线 */
	.view-line {
		border: 1rpx solid #f8f8f8;
	}

	.position-sticky {
		position: sticky;
		top: 0;
		z-index: 99;
		background: white;
	}

	.position-sticky-bottom {
		position: sticky;
		bottom: 0;
		z-index: 99;
		background: white;
	}


	/* 超出范围以...显示 */
	.out_of_range {
		overflow: hidden;
		text-overflow: 'string';
		display: -webkit-box;
		-webkit-box-orient: vertical;
		align-content: center;
		word-break: break-all;
	}

	/* 不显示"..."省略号，可以以符号表示 */
	.white_space {
		white-space: nowrap;
	}

	.one_row {
		-webkit-line-clamp: 1;
	}

	.two_row {
		-webkit-line-clamp: 2;
	}

	/* 公共图标样式 */
	.icon-class {
		width: 5%;
		height: auto;
		margin-right: 10rpx;
	}

	.textarea-placeholder {
		font-size: 28rpx;
	}

	.strike_through {
		color: gainsboro;
		text-decoration: line-through;
	}

	.input-placeholder {
		font-size: 28rpx;
	}

	/* hover-class样式，点击态;opacity:透明程度*/
	.hover-class-public {
		opacity: 0.7;
		background: #f5f5f5;
		transition: 0.5s;
	}

	/* 不能操作的item */
	.isDisable {
		opacity: 0.5;
		transition: 0.5s;
	}

	.isEnd {
		opacity: 0.4;
	}

	/* 售罄样式 */
	.sell-out {
		position: absolute;
		left: 10%;
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

	.menu-position {
		position: relative;
		width: 100%;
		top: 0;
		background: white;
		z-index: 9999;
		color: #434343;
	}

	/* 活动tab的公共样式 */
	.swiper-tab-item {
		font-size: 28rpx;
		color: #434343;
		display: flex;
		justify-content: center;
	}

	.swiper_table_item_view {
		display: flex;
		align-items: center;
		justify-content: center;
		border-bottom: 4rpx solid #fff;
	}

	/* 主题颜色 */
	.active {
		color: #004ca0;
		border-bottom: 4rpx solid #004ca0;
		/* transition: 0.5s; */
	}

	.active_ {
		color: #004ca0;
		border-bottom: 4rpx solid white;
		/* transition: 0.5s; */
	}

	.group-active {
		color: white;
	}

	.theme-bg {
		background: #004ca0;
		color: white;
		border: none;
	}

	.theme-color {
		color: #004ca0;
	}

	.theme-border {
		border: 0.5rpx solid #004ca0;
	}

	/* 是字体颜色加边框的宽度和实心颜色 */
	.theme-color-border {
		color: #004ca0;
		border: 0.5rpx solid #004ca0;
	}

	/* 只是边框颜色 */
	.theme-border-color {
		border-color: #004ca0;
	}

	/* 选择地址radio公共样式 */
	.radio-active {
		background: #004ca0;
		background: white;
		color: #004ca0;
		transition: 0.5s;
	}

	/* 去除选项框的radio样式 */
	.none-radio {
		display: none;
	}

	/*checkbox 选项框大小  */
	radio-group .wx-radio-input {
		border-radius: 50%;
		border-color: #ededed;
	}

	/*radio选中后样式  */
	radio-group .wx-radio-input.wx-radio-input-checked {
		border-color: 1rpx solid #004ca0;
		border-radius: 50%;
		background: #004ca0 !important;
	}

	/* checkBox复选框公共样式 */
	checkbox .wx-checkbox-input {
		border-radius: 50%;
		border-color: #ededed;
	}

	/*checkbox选中后样式  */
	checkbox .wx-checkbox-input.wx-checkbox-input-checked {
		border-radius: 50%;
		color: #ffffff;
		border-color: 1rpx solid #004ca0;
		background-color: #004ca0;
	}

	checkbox .wx-checkbox-input.wx-checkbox-input-checked::before {
		border-radius: 50%;
		color: #ffffff;
		border-color: 1rpx solid #004ca0;
		background-color: #004ca0;
	}

	radio .wx-radio-input {
		border-radius: 50%;
		border-color: #ededed;
	}

	radio .wx-radio-input.wx-radio-input-checked {
		color: #ffffff;
		border-radius: 50%;
		border: 1rpx solid #004ca0 !important;
		background: #004ca0 !important;
	}

	radio .wx-radio-input.wx-radio-input-checked::before {
		color: #ffffff;
		border-radius: 50%;
		border: 1rpx solid #004ca0 !important;
		background: #004ca0 !important;
	}

	radio {
		width: 40rpx;
		height: 40rpx;
		margin-right: 15rpx;
	}

	/* 加减联动器 */
	.stepper {
		width: auto;
		height: auto;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.add-reduce-input {
		color: #004ca0;
	}

	.reduce-class {
		border: 1rpx solid #004ca0;
	}

	/*加号和减号*/
	.stepper text {
		text-align: center;
		border: 1rpx solid #004ca0;
		border-radius: 50%;
	}

	/* 加号样式 */
	.add-class {
		background: #004ca0;
		color: white;
		border: 1rpx solid #004ca0;
	}

	.car_reduce_add {
		border-radius: 50%;
		width: 35rpx;
		height: 35rpx;
	}

	.radd-reduce-input {
		width: 24rpx;
	}

	/* 减号样式 */
	.reduce-class {
		color: #004ca0;
	}

	/*数值*/
	.stepper input {
		width: 30px;
		float: left;
		text-align: center;
		font-size: 12px;
		z-index: 0;
	}

	.top-bg-class {
		width: 100%;
	}

	.isNotData {
		background: #b8b8b8;
		color: white;
		padding: 2rpx 10rpx;
		border-radius: 5rpx;
		font-size: 22rpx;
	}

	/*  */
	.weui-navigation-bar-custom {
		width: 100%;
		overflow: hidden;
		position: fixed;
		top: 0;
		left: 0;
		z-index: 10;
	}

	.display_flex_between {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	/* 支付密码css start */
	.password_dialog_tip {
		margin: 30rpx 0;
		font-size: 24rpx;
		color: #888;
	}

	/* 密码掩码模拟 */
	.password_dialog_row {
		margin: 0 auto;
		height: 98rpx;
		position: relative;
		border-radius: 15rpx;
		display: flex;
		align-items: center;
		border: 1rpx solid #e2e2e2;
	}

	.password_dialog_row.theme-border {
		border: 1rpx solid #004ca0;
	}

	.password_dialog_row .password_dialog_item_input {
		flex: 1;
		display: flex;
		align-items: center;
		justify-content: center;
		height: 100%;
		border-right: 1rpx solid #e2e2e2;
		position: relative;
	}

	.password_dialog_item_input:nth-last-of-type(1) {
		border-right: 0;
	}

	.password_dialog_item_input text {
		width: 30rpx;
		height: 30rpx;
		border-radius: 30rpx;
		background-color: #555;
	}

	.password_dialog_forget_pwd {
		float: right;
		margin: 30rpx;
		width: 100rpx;
		text-align: right;
		font-size: 24rpx;
	}

	/* 文本输入框位置: 设置到左边隐藏 */
	.password_dialog_input_control {
		position: relative;
		left: -300rpx;
		bottom: 0;
		width: 100rpx;
		height: 100rpx;
	}

	.top_tips {
		width: 100%;
		background: #fffadc;
		font-size: 24rpx;
		text-align: center;
		position: sticky;
		top: 0;
		z-index: -1;
		opacity: 0.7;
		visibility: visible;
	}

	.flex_center {
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.flex_between_normal {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.flex_start {
		display: flex;
		align-items: center;
		justify-content: flex-start;
	}

	.flex_between {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.flex_end {
		display: flex;
		align-items: center;
		justify-content: flex-end;
	}

	.flex_column {
		display: flex;
		align-items: center;
		flex-direction: column;
	}

	.halfScreenDialog-btn {
		width: 100%;
		text-align: center;
		padding: 20rpx 0;
		border-radius: 15rpx;
		font-size: 28rpx;
		font-weight: bold;
	}

	.list-ismore {
		text-align: center;
		margin: 20rpx 0;
		padding: 20rpx 0;
		color: #888;
		font-size: 28rpx;
	}

	.content_box {
		padding: 16px;
	}

	.content_box_footer {
		margin: 10px;
	}

	.safe-area {
		padding-bottom: calc(constant(safe-area-inset-bottom));
		padding-bottom: calc(env(safe-area-inset-bottom));
		background: #F9FAFC;
	}

	.loading_box {
		width: 100%;
		margin-top: 30%;
		text-align: center;
	}

	.loading_list_box {
		width: 100%;
		margin: 10rpx 0;
		text-align: center;
	}

	.loading_box_class {
		color: #004ca0;
	}

	.more_box {
		padding: 20px 0;
		background: #f6f6f6;
		font-size: 26rpx;
		color: #969696;
		text-align: center;
	}

	.divider_box {}

	/* #ifdef APP-PLUS||H5 */
	uni-checkbox .uni-checkbox-input {
		border-radius: 50% !important;
		color: #ffffff !important;
	}

	uni-checkbox .uni-checkbox-input.uni-checkbox-input-checked {
		border: 2rpx solid #004ca0 !important;
		background: #004ca0;
		border-color: #004ca0;
	}

	uni-checkbox .uni-checkbox-input svg {
		background: #004ca0;
		border-radius: 50%;
		height: 100%;
		border: 5px solid #004ca0;
		color: #ffffff !important;
	}

	uni-checkbox .uni-checkbox-input.uni-checkbox-input-checked::before {
		width: 40rpx;
		height: 40rpx;
		line-height: 40rpx;
		text-align: center;
		font-size: 18rpx;
		color: #fff;
		background: transparent;
		transform: translate(-70%, -50%) scale(1);
		-webkit-transform: translate(-70%, -50%) scale(1);
	}

	/* vant搜索框不兼容H5和app问题 */
	.van-field__control[type=search]::-webkit-search-cancel-button {
		-webkit-appearance: none !important;
	}

	/* 加载提示问题 */
	.uni-toast {
		height: 8em;
		max-height: 8em;
	}

	.uni-toast__content {
		margin-top: 5px;
	}

	.uni-toast__icon {
		margin-top: 30px;
	}

	/* #endif */
	// #ifdef MP-WEIXIN||MP-ALIPAY
	/*checkbox 选项框大小  */
	checkbox .wx-checkbox-input {
		width: 40rpx;
		height: 40rpx;
	}

	/*checkbox选中后样式  */
	checkbox .wx-checkbox-input.wx-checkbox-input-checked {
		width: 40rpx;
		height: 40rpx;
	}

	checkbox {
		width: 40rpx;
		height: 40rpx;
		margin-right: 15rpx;
	}


	// #endif
</style>