<template>
	<view>
		<view style="height: 100%">
			<view class="position-sticky">
				<view class="swiper-content">
					<view class="swiper-tab collect-tab self-adaption">
						<view class="swiper-tab-item" :data-current="index" @tap="clickCollectTab"
							hover-class="hover-click-class" v-for="(item, index) in tabList" :key="index">
							<view :class="'swiper_table_item_view ' + (collectTab == item.modeId ? 'active' : '')"
								:data-current="index" @tap="clickCollectTab">
								{{ item.modeName }}
							</view>
						</view>
					</view>
				</view>
				<view>
					<form action="/">
						<van-search v-model="searchGoodsName" placeholder="商品名称" show-action @search="onSearch"
							@change="onFocus" @update:model-value="onFocus" background="#ededed" use-action-slot>
							// #ifdef APP-PLUS||H5
							<template #action>
								<view @click="searchBtn">搜索</view>
							</template>
							// #endif
							// #ifdef MP-WEIXIN||MP-ALIPAY
							<view slot="action" @click="searchBtn" class="van-search__action" role="button">搜索</view>
							// #endif
						</van-search>
					</form>
				</view>
				<view class="swiper-content">
					<view class="swiper-tab self-adaption">
						<view class="swiper-tab-item" :data-current="index" @tap="clickTab"
							hover-class="hover-click-class" v-for="(item, index) in modeList" :key="index">
							<view :class="'swiper_table_item_view ' + (currentTab == item.modeId ? 'active' : '')"
								:data-current="index" @tap="clickTab">
								{{ item.modeName }}
							</view>
						</view>
					</view>
					<view class="manager-checkbox" v-if="!isShowCheckbox" @tap="bindShowCheckbox">管理</view>
					<view class="manager-checkbox theme-color" v-else @tap="bindShowCheckbox">完成</view>
				</view>
			</view>
			<checkbox-group class="weui-slidecells" @change="checkboxChange">
				<view class="page__bd">
					<view class="weui-slidecells" v-for="(item, index) in collectList" :key="index">
						<van-swipe-cell :right-width="65">
							<view :class="'weui-slidecell ' + (item.disable ? 'isDisable' : '')">
								<label class="checkbox-group-label" v-if="isShowCheckbox">
									<checkbox :value="index" :index="index" class="theme-color theme-border-color"
										:checked="item.checked" :disabled="item.disable" iconColor="#FFFFFF" />
								</label>
								<view class="commdity-item" :data-id="item.goodsId" :data-shopid="item.shopId"
									@tap.stop.prevent="parseEventDynamicCode($event, collectTab == 0 ? 'bindMemberDetails' : 'bindPointsMallDetails')">
									<image
										:src="item.mainImage ? item.mainImage : '/static/assets/images/load-image.png'"
										mode="aspectFill" class="commodity-icon"></image>
									<view class="sell-out out-store" v-if="item.goodsStatus == 1">未上架</view>
									<view class="sell-out out-store" v-else-if="item.goodsStatus == 3">已下架</view>
									<view class="sell-out out-store" v-else-if="item.goodsStatus == 4">已售罄</view>
									<view class="commdity-types">
										<view class="commodity-name-type">
											<view class="commdity-name">{{ item.goodsName }}</view>
											<view class="types">{{ item.restructure }}</view>
										</view>
										<view class="commdity-money strike_through" v-if="isSale">￥{{ item.goodsPrice }}
										</view>
										<view class="commdity-money">
											￥{{ item.isSale ? item.salePrice : item.goodsPrice }}</view>
										<text class="is-goods-exists" v-if="!item.isGoodsExists">已失效</text>
									</view>
								</view>
							</view>
							// #ifdef APP-PLUS||H5
							<template #right>
								<view style="height: 100%;" class="flex_center">
									<view class="flvan-swipe-cell__right flex_between" :data-checkboxIndex="index"
										@tap="slideButtonTap($event, { goodsid: item.goodsId })">
										<van-icon name="like-o" />
									</view>
								</view>
							</template>
							// #endif
							// #ifdef MP-WEIXIN||MP-ALIPAY
							<view slot="right" class="flvan-swipe-cell__right flex_between" :data-checkboxIndex="index"
								@tap="slideButtonTap($event, { goodsid: item.goodsId })">
								<van-icon name="like-o" />
							</view>
							// #endif
						</van-swipe-cell>
					</view>
				</view>
			</checkbox-group>
		</view>
		<view v-if="isShowCheckbox" class="safe-area show-checkbox">
			<view class="checkbox-class">
				<checkbox class="all-checkbox" @tap="allChange" :checked="isAlls" iconColor="#FFFFFF">全选</checkbox>
			</view>
			<van-icon name="like-o" @tap="batchDelete" color="red" size="25"/>
		</view>
		<van-empty description="暂无收藏" v-if="collectList.length <= 0" />
	</view>
</template>

<script>
	import GlobalConfig from '../../../../utils/global-config';
	import https from '../../../../utils/http';
	import authService from '../../../../utils/auth';
	import CustomPage from '../../../../base/CustomPage';
	import toastService from '../../../../utils/toast.service';
	import utilHelper from '../../../../utils/util';
	import dateHelper from '../../../../utils/date-helper';
	import systemStatus from '../../../../utils/system-status';
	export default {
		data() {
			return {
				inputShowed: false,
				inputVal: '',
				i: 0,
				currentTab: 0,
				modeList: [{
						modeId: 0,
						modeName: '全部'
					},
					{
						modeId: 1,
						modeName: '已买过'
					}
				],
				tabList: [{
						modeId: 0,
						modeName: '外卖收藏'
					},
					{
						modeId: 1,
						modeName: '商城收藏'
					}
				],
				collectTab: 0,
				checkValue: 0,
				isShowCheckbox: false,
				collectList: [],
				slideButtons: [{
					src: '/static/assets/common/icon-del.svg' // icon的路径
				}],
				isAlls: false,
				searchGoodsName: '',
				search: '',
				goodsIdList: [],
				isSale: false,
				noDataTip: ''
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			this.getMemberCollect();
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
			// 滑动切换tab
			bindSlideChange: function(e) {
				this.setData({
					currentTab: e.detail.current
				});
				// this.getHeight();
			},

			//点击切换
			clickTab: function(e) {
				if (this.currentTab === e.target.dataset.current) {
					return false;
				} else {
					if (this.collectTab == 0) {
						if (e.target.dataset.current == 0) {
							this.getMemberCollect(false, this.searchGoodsName);
						} else {
							this.getMemberCollect(true, this.searchGoodsName);
						}
					}
					if (this.collectTab == 1) {
						if (e.target.dataset.current == 0) {
							this.getPointsMallCollect(false, this.searchGoodsName);
						} else {
							this.getPointsMallCollect(true, this.searchGoodsName);
						}
					}
					this.currentTab = e.target.dataset.current;
				}
			},

			clickCollectTab: function(e) {
				if (this.collectTab === e.target.dataset.current) {
					return false;
				} else {
					if (e.target.dataset.current == 0) {
						this.getMemberCollect(false, this.searchGoodsName);
					} else {
						this.getPointsMallCollect(false, this.searchGoodsName);
					}
					this.collectTab = e.target.dataset.current;
					this.currentTab = 0;
					this.isAlls = false;
				}
			},

			onSearch: function(e) {
				console.log(e);
				var value = this.searchGoodsName;

				//判断大标签页
				if (this.collectTab == 0) {
					//判断小标签页
					if (this.currentTab == 0) {
						this.getMemberCollect(false, value);
					}
					if (this.currentTab == 1) {
						this.getMemberCollect(true, value);
					}
				}
				if (this.collectTab == 1) {
					//判断小标签页
					if (this.currentTab == 0) {
						this.getPointsMallCollect(false, value);
					}
					if (this.currentTab == 1) {
						this.getPointsMallCollect(true, value);
					}
				}
				this.searchGoodsName = value;
			},
			onFocus(e) {
				console.log('select result', e);

				// #ifdef H5
				if (!e.type) {
					this.searchGoodsName = e;
				}
				// #endif
				// #ifdef MP-WEIXIN||MP-ALIPAY
				this.searchGoodsName = e.detail;
				// #endif
			},
			searchBtn(e) {
				console.log('searchBtn', this.searchGoodsName);
				var value = this.searchGoodsName;
				//判断大标签页
				if (this.collectTab == 0) {
					//判断小标签页
					if (this.currentTab == 0) {
						this.getMemberCollect(false, value);
					}
					if (this.currentTab == 1) {
						this.getMemberCollect(true, value);
					}
				}
				if (this.collectTab == 1) {
					//判断小标签页
					if (this.currentTab == 0) {
						this.getPointsMallCollect(false, value);
					}
					if (this.currentTab == 1) {
						this.getPointsMallCollect(true, value);
					}
				}
			},
			searchInputClear(e) {
				console.log(e);
				if (this.collectTab == 0) {
					//判断小标签页
					if (this.currentTab == 0) {
						this.getMemberCollect(false, '');
					}
					if (this.currentTab == 1) {
						this.getMemberCollect(true, '');
					}
				}
				if (this.collectTab == 1) {
					//判断小标签页
					if (this.currentTab == 0) {
						this.getPointsMallCollect(false, '');
					}
					if (this.currentTab == 1) {
						this.getPointsMallCollect(true, '');
					}
				}
			},

			getPointsMallCollect(isBuy, goodsName) {
				let data = {
					pageNo: -1,
					pageSize: 20
				};
				if (isBuy) {
					data.isBuy = true;
				}
				if (goodsName) {
					data.goodsName = goodsName;
				}
				this.collectList = [];
				https.request('/rest/member/pointsMall/goodsCollect/list', data).then((result) => {
					if (result.success) {
						result.data.records.forEach((item, index) => {
							item.mainImage = GlobalConfig.ossUrl + item.mainImage;
							this.collectList.push(item);
						});
						this.setData({
							collectList: this.collectList
						});
					}
				});
			},

			getMemberCollect(isBuy, goodsName) {
				let data = {
					pageNo: -1,
					pageSize: 20,
					isBuy: isBuy ? isBuy : false,
					goodsName: goodsName ? goodsName : ''
				};
				// if (isBuy) {
				// 	data.isBuy = isBuy;
				// }
				// if (goodsName) {
				// 	data.goodsName = goodsName;
				// }
				this.collectList = [];
				https.request('/rest/member/goodsCollect/list', data).then((result) => {
					if (result.success) {
						result.data.records.forEach((item, index) => {
							item.mainImage = GlobalConfig.ossUrl + item.mainImage;
							this.collectList.push(item);
						});
						this.setData({
							collectList: this.collectList
						});
					}
				});
			},

			bindShowCheckbox(e) {
				this.setData({
					isShowCheckbox: this.isShowCheckbox ? false : true
				});
			},

			bindPointsMallDetails(e) {
				uni.navigateTo({
					url: '../../../internal/mall/detail/detail?id=' + e.currentTarget.dataset.id
				});
			},

			bindMemberDetails(e) {
				uni.navigateTo({
					url: '../../../menu/detail/detail?id=' + e.currentTarget.dataset.id + '&shopId=' + e
						.currentTarget.dataset.shopid
				});
			},

			checkboxChange(e) {
				console.log(e);
				var values = e.detail.value;
				var deleteIds = [];
				for (var key in values) {
					deleteIds.push(this.collectList[key].goodsId);
				}
				this.setData({
					goodsIdList: deleteIds,
					isAlls: values.length != this.collectList.length ? false : true
				});
			},

			allChange(e) {
				console.log(e);
				var deleteIds = [];
				this.collectList.forEach((item, index) => {
					if (!this.isAlls) {
						item.checked = true;
						deleteIds.push(item.goodsId);
					} else {
						item.checked = false;
					}
				});
				console.log(deleteIds);
				this.setData({
					collectList: this.collectList,
					goodsIdList: deleteIds,
					isAlls: this.isAlls ? false : true
				});
			},

			slideButtonTap(e, _dataset) {
				console.log("点击取消收藏按钮=", _dataset, e)
				var deleteIds = [];
				let goodsid = _dataset.goodsid; //商品下标
				deleteIds.push(goodsid);
				this.goodsIdList = deleteIds;
				this.batchDelete();
			},

			batchDelete() {
				console.log("this.goodsIdList", this.goodsIdList);
				if (this.goodsIdList.length <= 0) {
					toastService.showToast('请选择要取消的收藏');
					return;
				}
				var _this = this;
				toastService.showModal(null, '确定要取消这' + this.goodsIdList.length + '条收藏吗?',
					function confirm() {
						let url = '/rest/member/pointsMall/goodsCollect/batchDelete';
						if (_this.collectTab == 0) {
							url = '/rest/member/goodsCollect/batchDelete';
						}
						https.request(url, {
							goodsIdList: _this.goodsIdList
						}).then((result) => {
							if (result.success) {
								if (_this.isAlls) {
									_this.isShowCheckbox = false;
									_this.isAlls = false;
								}
								toastService.showSuccess("取消成功");
								//判断大标签页
								if (_this.collectTab == 0) {
									//判断小标签页
									if (_this.currentTab == 0) {
										_this.getMemberCollect();
									}
									if (_this.currentTab == 1) {
										_this.getMemberCollect(true);
									}
								}
								if (_this.collectTab == 1) {
									//判断小标签页
									if (_this.currentTab == 0) {
										_this.getPointsMallCollect();
									}
									if (_this.currentTab == 1) {
										_this.getPointsMallCollect(true);
									}
								}
							}
						});
					}, null, true);
			}
		}
	};
</script>
<style>
	page {
		padding-bottom: 120rpx;
		background: #f5f5f5;
	}

	.searchbar-result {
		margin-top: 0;
		font-size: 14px;
	}

	.searchbar-result:before {
		display: none;
	}

	.swiper-content {
		margin: 0 20rpx 20rpx 20rpx;
		border-radius: 15rpx;
		background-color: white;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.swiper-tab {
		width: 40%;
		text-align: center;
		height: 88rpx;
		line-height: 88rpx;
		display: flex;
		flex-flow: row;
		justify-content: space-between;
		background: #fff;
		z-index: 1;
		border-radius: 15rpx 15rpx 0 0;
		/* border-bottom: 1rpx solid #ededed; */
	}

	.collect-tab {
		width: 100%;
	}

	.swiper-tab-item {
		width: 50%;
	}

	.swiper-box {
		/* height: 400rpx; */
		border-radius: 0 0 15rpx 15rpx;
	}

	.manager-checkbox {
		margin-right: 20rpx;
		font-size: 28rpx;
	}

	.show-checkbox {
		position: fixed;
		bottom: 0;
		z-index: 9999;
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 20rpx;
		font-size: 35rpx;
		width: 95%;
		background-color: white;
	}

	.checkbox-class {
		width: 60%;
	}

	.all-checkbox {
		font-size: 30rpx;
		width: 60%;
	}

	.iconshanchuguan {
		font-size: 40rpx;
		color: red;
	}

	.weui-slidecell {
		display: flex;
		justify-content: space-between;
		background-color: #fff;
		border-radius: 8px;
		padding: 20rpx;
		margin: 10rpx 20rpx;
		line-height: 1.4;
		font-size: 17px;
		color: rgba(0, 0, 0, 0.9);
	}

	.is-first {
		margin-top: 20rpx;
	}

	.commdity-item {
		display: flex;
		justify-content: flex-start;
		width: 100%;
		height: 188rpx;
	}

	.commodity-icon {
		width: 38%;
		height: 100%;
		margin-right: 20rpx;
		border-radius: 10rpx;
	}

	.commdity-types {
		flex-direction: column;
		justify-content: space-between;
		width: 60%;
	}

	.commodity-name-type {
		margin-bottom: 5rpx;
	}

	.commodity-image {
		width: 190rpx;
		height: auto;
		border-radius: 5rpx;
	}

	.weui-slideview_icon .weui-slideview__btn__wrp:first-child {
		display: flex;
		align-items: center;
	}

	.weui-slideview_icon .weui-slideview__btn {
		background-color: red;
	}

	.checkbox-group-label {
		display: flex;
		align-items: center;
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

	.is-goods-exists {
		background-color: #d1d1d1;
		color: white;
		font-size: 24rpx;
		border-radius: 20rpx;
		padding: 2rpx 10rpx;
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

	.van-search__action {
		padding: 0 10rpx;
	}
</style>