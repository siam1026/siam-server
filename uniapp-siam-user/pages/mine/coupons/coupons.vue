<template>
	<view>
		<view class="swiper-content position-sticky">
			<view class="swiper-tab self-adaption">
				<view class="swiper-tab-item" :data-current="index" @tap="clickTab" hover-class="hover-click-class"
					v-for="(item, index) in tabList" :key="index">
					<view :class="'swiper_table_item_view ' + (currentTab == item.modeId ? 'active' : '')"
						:data-current="index" @tap="clickTab">
						{{ item.modeName }}
					</view>
				</view>
			</view>
		</view>
		<swiper :current="currentTab" class="swiper-box" duration="300" @change="bindSlideChange"
			:style="'height:' + winHeight + 'px;'">
			<swiper-item class="swiper-items">
				<scroll-view @scrolltoupper="onPullDownRefresh" upper-threshold="-50"
					:style="'height:' + winHeight + 'px;'" @scrolltolower="onReachBottom" lower-threshold="0" scroll-y
					class="scroll-views" v-if="couponsList.length > 0">
					<view class="coupins-items">
						<radio-group>
							<view
								:class="'coupins-item ' + (item.couponsMemberRelationMap.isRelation ? '' : 'isRelation')"
								v-for="(item, index) in couponsList" :key="index">
								<view class="item-top">
									<view>
										<view class="couponsName">
											{{ item.couponsMemberRelationMap.couponsName }}-{{ item.couponsMemberRelationMap.preferentialTypeText }}
										</view>
										<view class="endTime">有效期至{{ item.couponsMemberRelationMap.endTime }}</view>
									</view>
									<view class="theme-color">
										<view class="couponsNum">
											{{
                                                item.couponsMemberRelationMap.preferentialType == 1
                                                    ? item.couponsMemberRelationMap.discountAmount * 10 + '折'
                                                    : '满' + item.couponsMemberRelationMap.limitedPrice + '减' + item.couponsMemberRelationMap.reducedPrice
                                            }}
										</view>
										<view
											v-if="item.couponsMemberRelationMap.afterDiscountPrice >= 0 && item.couponsMemberRelationMap.isRelation"
											class="afterDiscountPrice">
											优惠{{ item.couponsMemberRelationMap.afterDiscountPrice }}元
										</view>
									</view>
								</view>

								<view class="view-line"></view>

								<view class="bottom-view">
									<view class="usage-rule" @tap="onClick" :data-index="index">
										使用规则
										<text class="iconfont iconweibiaoti35"
											v-if="item.couponsMemberRelationMap.isShow"></text>
										<text class="iconfont iconhtbArrowright02"
											v-if="!item.couponsMemberRelationMap.isShow"></text>
									</view>
									<view :class="
                                            'immediate-use ' +
                                            (!item.couponsMemberRelationMap.isExpired && !item.couponsMemberRelationMap.isUsed && item.couponsMemberRelationMap.isRelation
                                                ? 'theme-color-border'
                                                : 'out-of-commission') +
                                            ' '
                                        " @tap="
                                            parseEventDynamicCode(
                                                $event,
                                                !item.couponsMemberRelationMap.isExpired && !item.couponsMemberRelationMap.isUsed && item.couponsMemberRelationMap.isRelation
                                                    ? 'onImmediateUse'
                                                    : ''
                                            )
                                        " v-if="!item.couponsMemberRelationMap.checked" :data-index="index">
										立即使用
									</view>
									<label class="checkbox-group-label" v-else>
										<checkbox :value="index" :data-index="index" @tap="onRadioChange"
											class="theme-color theme-border-color"
											:checked="item.couponsMemberRelationMap.checked" />
									</label>
								</view>

								<view v-if="item.couponsMemberRelationMap.isShow" class="usage-rule">
									{{ item.couponsMemberRelationMap.description }}
								</view>
							</view>
						</radio-group>
					</view>
				</scroll-view>
				<van-empty :description="'暂无' + tabList[0].modeName" v-if="couponsList.length <= 0" />
			</swiper-item>
			<swiper-item class="swiper-items">
				<scroll-view @scrolltoupper="onPullDownRefresh" upper-threshold="-50"
					:style="'height:' + winHeight + 'px;'" @scrolltolower="onReachBottom" lower-threshold="0" scroll-y
					class="scroll-views" v-if="couponsPointsList.length > 0">
					<view class="coupins-items">
						<radio-group>
							<view
								:class="'coupins-item ' + (item.couponsMemberRelationMap.isRelation ? '' : 'isRelation')"
								v-for="(item, index) in couponsPointsList" :key="index">
								<view class="item-top">
									<view>
										<view class="couponsName">
											{{ item.couponsMemberRelationMap.couponsName }}-{{ item.couponsMemberRelationMap.preferentialTypeText }}
										</view>
										<view class="endTime">有效期至{{ item.couponsMemberRelationMap.endTime }}</view>
									</view>
									<view class="theme-color">
										<view class="couponsNum">
											{{
                                                item.couponsMemberRelationMap.preferentialType == 1
                                                    ? item.couponsMemberRelationMap.discountAmount * 10 + '折'
                                                    : '满' + item.couponsMemberRelationMap.limitedPrice + '减' + item.couponsMemberRelationMap.reducedPrice
                                            }}
										</view>
										<view
											v-if="item.couponsMemberRelationMap.afterDiscountPrice >= 0 && item.couponsMemberRelationMap.isRelation"
											class="afterDiscountPrice">
											优惠{{ item.couponsMemberRelationMap.afterDiscountPrice }}元
										</view>
									</view>
								</view>

								<view class="view-line"></view>

								<view class="bottom-view">
									<view class="usage-rule" @tap="onPointsClick" :data-index="index">
										使用规则
										<text class="iconfont iconweibiaoti35"
											v-if="item.couponsMemberRelationMap.isShow"></text>
										<text class="iconfont iconhtbArrowright02"
											v-if="!item.couponsMemberRelationMap.isShow"></text>
									</view>
									<view :class="
                                            'immediate-use ' +
                                            (!item.couponsMemberRelationMap.isExpired && !item.couponsMemberRelationMap.isUsed && item.couponsMemberRelationMap.isRelation
                                                ? 'theme-color-border'
                                                : 'out-of-commission') +
                                            ' '
                                        " @tap="
                                            parseEventDynamicCode(
                                                $event,
                                                !item.couponsMemberRelationMap.isExpired && !item.couponsMemberRelationMap.isUsed && item.couponsMemberRelationMap.isRelation
                                                    ? 'onImmediatePointsUse'
                                                    : ''
                                            )
                                        " v-if="!item.couponsMemberRelationMap.checked" :data-index="index">
										立即使用
									</view>
									<label class="checkbox-group-label" v-else>
										<checkbox :value="index" :data-index="index" @tap="onPointsRadioChange"
											class="theme-color theme-border-color"
											:checked="item.couponsMemberRelationMap.checked" />
									</label>
								</view>

								<view v-if="item.couponsMemberRelationMap.isShow" class="usage-rule">
									{{ item.couponsMemberRelationMap.description }}
								</view>
							</view>
						</radio-group>
					</view>
				</scroll-view>
				<van-empty :description="'暂无' + tabList[1].modeName" v-if="couponsPointsList.length <= 0" />
			</swiper-item>
		</swiper>
	</view>
</template>

<script>
	import https from '../../../utils/http';
	import toastService from '../../../utils/toast.service';
	import utilHelper from '../../../utils/util';
	import dateHelper from '../../../utils/date-helper';
	import systemStatus from '../../../utils/system-status';
	let app = null;
	var pageNo = -1;
	var pageSize = 20;
	export default {
		data() {
			return {
				couponsList: [],
				currentTab: 0,
				tabList: [{
						modeId: 0,
						modeName: '外卖券'
					},
					{
						modeId: 1,
						modeName: '商城券'
					}
				],
				prevData: {
					fullPriceReduction: '',
					couponsIsHidden: false
				},
				winHeight: '',
				couponsPointsList: '',
				afterDiscount: '',
				data: {
					couponsIsHidden: false
				},
				feeData: '',
				modeName: ''
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			console.log(options);
			app = getApp();
			//可以直接取上一个页面的数据
			var prevData = options.prevData;
			console.log(prevData);
			if (prevData) {
				prevData = JSON.parse(prevData);
				console.log(prevData);
				prevData.checked = true;
				this.prevData = prevData;
				console.log(prevData.type);
				if (prevData.type == 2) {
					this.getCouponsMemberPointsMallRelation();
				} else {
					this.getCouponsMemberRelation();
				}
				this.currentTab = prevData.type - 1;
			} else {
				this.getCouponsMemberRelation();
				this.getCouponsMemberPointsMallRelation();
			}
			
		},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {
			this.getHeight();
		},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {},
		/**
		 * 生命周期函数--监听页面隐藏
		 */
		onHide: function() {
			//this.getFullReductionRule();
		},
		/**
		 * 生命周期函数--监听页面卸载
		 */
		onUnload: function() {
			//this.getFullReductionRule();
		},
		/**
		 * 页面相关事件处理函数--监听用户下拉动作
		 */
		onPullDownRefresh: function() {},
		/**
		 * 页面上拉触底事件的处理函数
		 */
		onReachBottom: function() {
			// if(this.data.isLastPage){
			//   toastService.showToast("没有更多啦~");
			//   return;
			// }
			// pageNo = pageNo + 1;
			// this.getCouponsMemberRelation();
		},
		/**
		 * 用户点击右上角分享
		 */
		onShareAppMessage: function() {},
		methods: {
			getHeight() {
				//获取用户手机系统信息
				var that = this;
				uni.getSystemInfo({
					success: function(res) {
						let winHeight = res.windowHeight; //获取高度
						//获取class为settlement-view并减去这个高度，自适应scrollview的高度
						if (that.prevData) {
							that.setData({
								winHeight: winHeight
							});
						} else {
							const query = uni.createSelectorQuery();
							query.select('.swiper-content').boundingClientRect();
							query.selectViewport().scrollOffset();
							query.exec(function(res) {
								console.log(winHeight - res[0].height);
								that.setData({
									winHeight: winHeight - res[0].height
								});
							});
						}
					}
				});
			},

			// 滑动切换tab
			bindSlideChange: function(e) {
				console.log(e);
				// if (e.detail.current == 1) {
				//   if (this.data.mallList.length <= 0) {
				//     this.getMallOrderList();
				//   }
				// }
				this.setData({
					currentTab: e.detail.current
				});
			},

			//点击切换
			clickTab: function(e) {
				//console.log(e.target.dataset.current)
				if (this.currentTab === e.target.dataset.current) {
					return false;
				} else {
					// 显示加载图标
					this.setData({
						currentTab: e.target.dataset.current
					});
				}
			},

			getCouponsMemberRelation() {
				toastService.showLoading();
				https.request('/rest/member/couponsMemberRelation/list', {
					pageNo: pageNo,
					pageSize: pageSize,
					isUsed: 0,
					isExpired: 0,
					isValid: 0
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						result.data.records.forEach((res) => {
							res.couponsMemberRelationMap.preferentialTypeText = systemStatus
								.preferentialTypeText(res.couponsMemberRelationMap.preferentialType);
							res.couponsMemberRelationMap.startTime = dateHelper.formatISODate(res
								.couponsMemberRelationMap.startTime, 'YYYY-MM-DD');
							//res.couponsMemberRelationMap.isRelation = true;
							res.couponsMemberRelationMap.endTime = dateHelper.formatISODate(res
								.couponsMemberRelationMap.endTime, 'YYYY-MM-DD');
							res.couponsMemberRelationMap.isShow = false; //设置每个优惠券的使用规则为隐藏
							//console.log(this.data)
							console.log(this.prevData);
							if (this.prevData) {
								if (res.couponsMemberRelationMap.id == this.prevData.id) {
									res.couponsMemberRelationMap.checked = true;
								}
								//判断优惠券是否过期和是否已经使用
								//if (!res.couponsMemberRelationMap.isExpired && !res.couponsMemberRelationMap.isUsed) {
								//console.log(this.data)
								//判断是否已经满减，如果已经满减就取满减之后的字段值，反之取总额
								//if (this.data.data.actualPrice && !this.data.data.fullPriceReduction) {
								//遍历当前订单的商品
								let afterDiscountPrice = 0;
								res.couponsMemberRelationMap.afterDiscountPrice = 0;
								//判断当前优惠券是折扣还是满减券,1等于折扣,2等于满减
								if (res.couponsMemberRelationMap.preferentialType == 1) {
									res.couponsMemberRelationMap.isRelation = false;
									if (res.shopList.length <= 0) {
										return;
									}
									res.shopList.forEach((shop, shopIndex) => {
										if (shop.id == this.prevData.shopId) {
											this.prevData.orderDetailList.forEach((order) => {
												//source  优惠券发放来源 1=商家中心 2=调度中心
												//如果是商家中心发放的优惠券，则需要判断关联商品
												//调度中心发放的优惠券，则无需判断关联商品，所有商品皆可使用
												if (res.couponsMemberRelationMap.source ==
													1) {
													//遍历当前优惠券绑定的优惠商品
													res.goodsList.forEach((goods) => {
														//console.log(goods)
														if (order.goodsId == goods
															.id) {
															//console.log(res.couponsMemberRelationMap)
															res.couponsMemberRelationMap
																.isRelation = true;
														}
													});
												}
												let orderPrice = order.price;
												let orderNumber = order.number;
												let unitPrice = utilHelper.toFixed(
													orderPrice - orderPrice * res
													.couponsMemberRelationMap
													.discountAmount, 2);
												//判断当前订单的商品是否等于优惠券绑定的优惠商品
												//等于则进行优惠
												//判断优惠券优惠的最大金额
												if (orderPrice >= afterDiscountPrice) {
													//console.log(afterDiscountPrice)
													afterDiscountPrice = orderPrice;
													//获取当前的商品折扣后的优惠价格
													res.couponsMemberRelationMap
														.afterDiscountPrice =
														res.couponsMemberRelationMap
														.discountAmount > 0 ? utilHelper
														.toFixed(unitPrice, 2) : unitPrice;
												}
												res.couponsMemberRelationMap.isRelation =
													true;
											});
											return;
										}
									});
								}
								//判断如果是优惠券满减的话这就进行优惠券的总价满减
								//console.log(res)
								if (res.couponsMemberRelationMap.preferentialType == 2) {
									if (this.prevData.fullPriceReductionAfter) {
										if (this.prevData.fullPriceReductionAfter >= res
											.couponsMemberRelationMap.limitedPrice) {
											//console.log(res.couponsMemberRelationMap.limitedPrice)
											res.couponsMemberRelationMap.afterDiscountPrice = this.prevData
												.fullPriceReductionAfter - res.couponsMemberRelationMap
												.reducedPrice;
										}
										return;
									}
									if (this.prevData.actualPrice >= res.couponsMemberRelationMap
										.limitedPrice) {
										res.couponsMemberRelationMap.afterDiscountPrice = this.prevData
											.actualPrice - res.couponsMemberRelationMap.reducedPrice;
									}
								}
								return;
							}
							console.log('从我的进入...');
							res.couponsMemberRelationMap.isRelation = true;
							//}
						});

						this.setData({
							couponsList: result.data.records
						});
					}
				});
			},

			getCouponsMemberPointsMallRelation() {
				toastService.showLoading();
				https.request('/rest/member/pointsMall/couponsMemberRelation/list', {
					pageNo: pageNo,
					pageSize: pageSize,
					isUsed: 0,
					isExpired: 0,
					isValid: 0
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						result.data.records.forEach((res) => {
							res.couponsMemberRelationMap.preferentialTypeText = systemStatus
								.preferentialTypeText(res.couponsMemberRelationMap.preferentialType);
							res.couponsMemberRelationMap.startTime = dateHelper.formatISODate(res
								.couponsMemberRelationMap.startTime, 'YYYY-MM-DD');
							//res.couponsMemberRelationMap.isRelation = true;
							res.couponsMemberRelationMap.endTime = dateHelper.formatISODate(res
								.couponsMemberRelationMap.endTime, 'YYYY-MM-DD');
							res.couponsMemberRelationMap.isShow = false; //设置每个优惠券的使用规则为隐藏
							//console.log(this.data)
							console.log(this.prevData);
							if (this.prevData) {
								if (res.couponsMemberRelationMap.id == this.prevData.id) {
									res.couponsMemberRelationMap.checked = true;
								}
								//判断优惠券是否过期和是否已经使用
								//if (!res.couponsMemberRelationMap.isExpired && !res.couponsMemberRelationMap.isUsed) {
								//console.log(this.data)
								//判断是否已经满减，如果已经满减就取满减之后的字段值，反之取总额
								//if (this.data.data.actualPrice && !this.data.data.fullPriceReduction) {
								//遍历当前订单的商品
								let afterDiscountPrice = 0;
								res.couponsMemberRelationMap.afterDiscountPrice = 0;
								//判断当前优惠券是折扣还是满减券,1等于折扣,2等于满减
								if (res.couponsMemberRelationMap.preferentialType == 1) {
									res.couponsMemberRelationMap.isRelation = false;
									if (this.prevData) {
										this.prevData.orderDetailList.forEach((order) => {
											//source  优惠券发放来源 1=商家中心 2=调度中心
											//如果是商家中心发放的优惠券，则需要判断关联商品
											//调度中心发放的优惠券，则无需判断关联商品，所有商品皆可使用
											if (res.couponsMemberRelationMap.source == 1) {
												//遍历当前优惠券绑定的优惠商品
												res.goodsList.forEach((goods) => {
													//console.log(goods)
													if (order.goodsId == goods.id) {
														//console.log(res.couponsMemberRelationMap)
														res.couponsMemberRelationMap
															.isRelation = true;
													}
												});
											}
											let orderPrice = order.price;
											let orderNumber = order.number;
											let unitPrice = utilHelper.toFixed(orderPrice -
												orderPrice * res.couponsMemberRelationMap
												.discountAmount, 2);
											//判断当前订单的商品是否等于优惠券绑定的优惠商品
											//等于则进行优惠
											//判断优惠券优惠的最大金额
											if (orderPrice >= afterDiscountPrice) {
												//console.log(afterDiscountPrice)
												afterDiscountPrice = orderPrice;
												//获取当前的商品折扣后的优惠价格
												res.couponsMemberRelationMap.afterDiscountPrice =
													res.couponsMemberRelationMap.discountAmount >
													0 ? utilHelper.toFixed(unitPrice, 2) :
													unitPrice;
											}
											res.couponsMemberRelationMap.isRelation = true;
										});
									}
								}
								//判断如果是优惠券满减的话这就进行优惠券的总价满减
								//console.log(res)
								if (res.couponsMemberRelationMap.preferentialType == 2) {
									if (this.prevData.fullPriceReductionAfter) {
										if (this.prevData.fullPriceReductionAfter >= res
											.couponsMemberRelationMap.limitedPrice) {
											//console.log(res.couponsMemberRelationMap.limitedPrice)
											res.couponsMemberRelationMap.afterDiscountPrice = this.prevData
												.fullPriceReductionAfter - res.couponsMemberRelationMap
												.reducedPrice;
										}
										return;
									}
									if (this.prevData.actualPrice >= res.couponsMemberRelationMap
										.limitedPrice) {
										res.couponsMemberRelationMap.afterDiscountPrice = this.prevData
											.actualPrice - res.couponsMemberRelationMap.reducedPrice;
									}
								}
								return;
							}
							console.log('从我的进入...');
							res.couponsMemberRelationMap.isRelation = true;
							//}
						});

						this.setData({
							couponsPointsList: result.data.records
						});
					}
				});
			},

			onClick(e) {
				//设置当前点击的使用规则为显示
				this.couponsList[e.currentTarget.dataset.index].couponsMemberRelationMap.isShow = this.couponsList[e
						.currentTarget.dataset.index].couponsMemberRelationMap.isShow ?
					false :
					true;
				this.setData({
					couponsList: this.couponsList
				});
			},

			onPointsClick(e) {
				//设置当前点击的使用规则为显示
				this.couponsPointsList[e.currentTarget.dataset.index].couponsMemberRelationMap.isShow = this
					.couponsPointsList[e.currentTarget.dataset.index].couponsMemberRelationMap
					.isShow ?
					false :
					true;
				this.setData({
					couponsPointsList: this.couponsPointsList
				});
			},

			onImmediateUse(e) {
				if (this.prevData) {
					//直接调用上一个页面的 setData() 方法，把数据存到上一个页面中去
					//console.log(this.data)
					let afterDiscounts = this.prevData;
					//console.log(afterDiscounts)
					//console.log(afterDiscounts.fullPriceReductionAfter - afterDiscounts.price)
					afterDiscounts.id = this.couponsList[e.currentTarget.dataset.index].couponsMemberRelationMap.id;
					//console.log(this.data.couponsList[e.currentTarget.dataset.index].couponsMemberRelationMap.afterDiscountPrice)
					afterDiscounts.price = this.couponsList[e.currentTarget.dataset.index].couponsMemberRelationMap
						.afterDiscountPrice;
					afterDiscounts.couponsName = this.couponsList[e.currentTarget.dataset.index].couponsMemberRelationMap
						.couponsName;
					afterDiscounts.preferentialTypeText = systemStatus.preferentialTypeText(this.couponsList[e
						.currentTarget.dataset.index].preferentialType);
					console.log(this.prevData);
					// prevPage.setData({
					//   afterDiscount: afterDiscounts,
					//   "data.fullPriceReduction": utilHelper.toFixed((this.data.prevData.actualPrice - afterDiscounts.price), 2),
					//   "data.couponsIsHidden": true
					// })
					this.setData({
						afterDiscount: afterDiscounts,
						'prevData.fullPriceReduction': utilHelper.toFixed(this.prevData.actualPrice -
							afterDiscounts.price, 2),
						'prevData.couponsIsHidden': true
					});
					this.getFullReductionRule();
					//console.log(prevPage.data)
					//wx.navigateBack(1);
					return;
				}
				let couponIndex = e.currentTarget.dataset.index;
				console.log(this.couponsList[couponIndex]);
				if (this.couponsList[couponIndex].shopList.length == 1) {
					uni.navigateTo({
						url: '../../menu/index/index?id=' + this.couponsList[couponIndex].shopList[0].id
					});
				} else if (this.couponsList[couponIndex].shopList.length > 1) {
					uni.switchTab({
						url: '/pages/index/index'
					});
				} else {
					uni.switchTab({
						url: '/pages/index/index'
					});
				}
			},

			onImmediatePointsUse(e) {
				if (this.prevData) {
					//直接调用上一个页面的 setData() 方法，把数据存到上一个页面中去
					//console.log(this.data)
					let afterDiscounts = this.prevData;
					//console.log(afterDiscounts)
					//console.log(afterDiscounts.fullPriceReductionAfter - afterDiscounts.price)
					afterDiscounts.id = this.couponsPointsList[e.currentTarget.dataset.index].couponsMemberRelationMap.id;
					//console.log(this.data.couponsList[e.currentTarget.dataset.index].couponsMemberRelationMap.afterDiscountPrice)
					afterDiscounts.price = this.couponsPointsList[e.currentTarget.dataset.index].couponsMemberRelationMap
						.afterDiscountPrice;
					afterDiscounts.couponsName = this.couponsPointsList[e.currentTarget.dataset.index]
						.couponsMemberRelationMap.couponsName;
					afterDiscounts.preferentialTypeText = systemStatus.preferentialTypeText(this.couponsPointsList[e
						.currentTarget.dataset.index].preferentialType);
					console.log(this.prevData);
					// prevPage.setData({
					//   afterDiscount: afterDiscounts,
					//   "data.fullPriceReduction": utilHelper.toFixed((this.data.prevData.actualPrice - afterDiscounts.price), 2),
					//   "data.couponsIsHidden": true
					// })
					this.setData({
						afterDiscount: afterDiscounts,
						'prevData.fullPriceReduction': utilHelper.toFixed(this.prevData.actualPrice -
							afterDiscounts.price, 2),
						'prevData.couponsIsHidden': true
					});
					this.getPointsFullReductionRule();
					//console.log(prevPage.data)
					//wx.navigateBack(1);
					return;
				}
				let couponIndex = e.currentTarget.dataset.index;
				console.log(this.couponsPointsList[couponIndex]);
				uni.switchTab({
					url: '../../mall/index/index'
				});
			},

			//获取满减规则
			getFullReductionRule() {
				toastService.showLoading();
				https.request('/rest/fullReductionRule/list', {
					pageNo: -1,
					pageSize: 1,
					shopId: this.prevData.shopId
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						var pages = getCurrentPages();
						var prevPage = pages[pages.length - 2]; //上一个页面
						//获取配送费，配送费不作为满减条件
						let feeData = app.globalData.deliveryAndSelfTaking.reducedDeliveryTotalPrice ? app
							.globalData.deliveryAndSelfTaking.reducedDeliveryTotalPrice : 0;
						let actualPrice = this.prevData.actualPrice;
						let fullPriceReductionBefore = actualPrice - feeData;
						let reducedDeliveryPrice = 0;
						console.log('总商品价格===》' + this.prevData.actualPrice);
						this.prevData.actualPrice = this.prevData.actualPrice ? utilHelper.toFixed(Number(this
							.prevData.actualPrice), 2) : 0;
						this.prevData.fullPriceReductionAfter = this.prevData.actualPrice;
						this.prevData.discountPrice = 0;
						this.prevData.fullPriceReductionBefore = fullPriceReductionBefore;
						this.prevData.fullPriceReductionIsHidden = this.prevData.fullPriceReductionIsHidden;
						this.prevData.limitedPrice = 0;
						if (prevPage.data.reducedDeliveryPrice >= feeData) {
							reducedDeliveryPrice = 0;
						} else {
							reducedDeliveryPrice = feeData - prevPage.data.reducedDeliveryPrice;
						}
						console.log('fullPriceReductionBefore===>' + fullPriceReductionBefore);
						console.log('获取商家配送费====' + prevPage.data.reducedDeliveryPrice);
						console.log('获取用户地址配送费====' + feeData);
						console.log('获取商家支付后的配送费====' + reducedDeliveryPrice);
						console.log('获取价格====' + this.prevData.fullPriceReductionBefore);
						console.log('获取优惠券信息===>');
						console.log(this.afterDiscount);
						fullPriceReductionBefore -= this.afterDiscount.price;
						this.prevData.fullPriceReduction = fullPriceReductionBefore;
						console.log('获取优惠券后的金额===》' + fullPriceReductionBefore);
						for (let i = 0; i < result.data.records.length; i++) {
							//总价格减去配送费大于满减金额则进行满减操作
							if (Number(fullPriceReductionBefore) >= result.data.records[i].limitedPrice) {
								//判断当前满减价格limitedPrice和上一个满减价格对比，如果大于就进行认证
								if (result.data.records[i].limitedPrice > this.prevData.limitedPrice) {
									this.prevData.limitedPrice = result.data.records[i].limitedPrice;
									console.log(result.data.records[i].reducedPrice);
									this.prevData.fullPriceReduction = utilHelper.toFixed(
										fullPriceReductionBefore - result.data.records[i].reducedPrice, 2);
									this.prevData.fullReductionRuleName = result.data.records[i].name;
									this.prevData.fullReductionRuleId = result.data.records[i].id;
									this.prevData.fullPriceReductionIsHidden = true;
									this.prevData.fullPriceReductionAfter = utilHelper.toFixed(this.prevData
										.fullPriceReduction - result.data.records[i].reducedPrice, 2);
								}
							}
						}
						console.log('获取满减后的金额===》' + this.prevData.fullPriceReduction);
						if (app.globalData.deliveryAndSelfTaking.currentTab == 0) {
							this.prevData.fullPriceReduction = utilHelper.toFixed(this.prevData
								.fullPriceReduction + reducedDeliveryPrice, 2);
						}
						app.globalData.deliveryAndSelfTaking.isThereADiscount = false;
						if (feeData != reducedDeliveryPrice) {
							app.globalData.deliveryAndSelfTaking.isThereADiscount = true;
						}
						console.log('获取价格====' + this.prevData.fullPriceReductionBefore);
						prevPage.setData({
							data: this.prevData,
							afterDiscount: this.afterDiscount
						});
						uni.navigateBack(1);
					}
				});
			},

			//获取满减规则
			getPointsFullReductionRule() {
				toastService.showLoading();
				https.request('/rest/pointsMall/fullReductionRule/list', {
					pageNo: -1,
					pageSize: 1
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						var pages = getCurrentPages();
						var prevPage = pages[pages.length - 2]; //上一个页面

						let actualPrice = this.prevData.actualPrice;
						let fullPriceReductionBefore = actualPrice;
						console.log('总商品价格===》' + this.prevData.actualPrice);
						this.prevData.actualPrice = this.prevData.actualPrice ? utilHelper.toFixed(Number(this
							.prevData.actualPrice), 2) : 0;
						this.prevData.fullPriceReductionAfter = this.prevData.actualPrice;
						this.prevData.discountPrice = 0;
						this.prevData.fullPriceReductionBefore = fullPriceReductionBefore;
						this.prevData.fullPriceReductionIsHidden = this.prevData.fullPriceReductionIsHidden;
						this.prevData.limitedPrice = 0;
						fullPriceReductionBefore -= this.afterDiscount.price;
						this.prevData.fullPriceReduction = fullPriceReductionBefore;
						console.log('获取优惠券后的金额===》' + fullPriceReductionBefore);
						for (let i = 0; i < result.data.records.length; i++) {
							//总价格减去配送费大于满减金额则进行满减操作
							if (Number(fullPriceReductionBefore) >= result.data.records[i].limitedPrice) {
								//判断当前满减价格limitedPrice和上一个满减价格对比，如果大于就进行认证
								if (result.data.records[i].limitedPrice > this.prevData.limitedPrice) {
									this.prevData.limitedPrice = result.data.records[i].limitedPrice;
									console.log(result.data.records[i].reducedPrice);
									this.prevData.fullPriceReduction = utilHelper.toFixed(
										fullPriceReductionBefore - result.data.records[i].reducedPrice, 2);
									this.prevData.fullReductionRuleName = result.data.records[i].name;
									this.prevData.fullReductionRuleId = result.data.records[i].id;
									this.prevData.fullPriceReductionIsHidden = true;
									this.prevData.fullPriceReductionAfter = utilHelper.toFixed(this.prevData
										.fullPriceReduction - result.data.records[i].reducedPrice, 2);
								}
							}
						}
						console.log('获取价格====' + this.prevData.fullPriceReductionBefore);
						prevPage.setData({
							data: this.prevData,
							afterDiscount: this.afterDiscount
						});
						uni.navigateBack(1);
					}
				});
			},

			getFullReductionRule_() {
				toastService.showLoading();
				https.request('/rest/fullReductionRule/list', {
					pageNo: -1,
					pageSize: 1,
					shopId: this.prevData.shopId
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						var pages = getCurrentPages();
						var prevPage = pages[pages.length - 2]; //上一个页面
						//获取配送费，配送费不作为满减条件
						let feeData = app.globalData.deliveryAndSelfTaking.reducedDeliveryTotalPrice ? app
							.globalData.deliveryAndSelfTaking.reducedDeliveryTotalPrice : 0;
						let reducedDeliveryPrice = 0;
						console.log('商品总价----》' + this.prevData.actualPrice);
						if (prevPage.data.reducedDeliveryPrice >= feeData) {
							reducedDeliveryPrice = 0;
							console.log('用户需支付的配送费为====>' + reducedDeliveryPrice);
						} else {
							reducedDeliveryPrice = feeData - prevPage.data.reducedDeliveryPrice;
							console.log('用户需支付的配送费为====>' + reducedDeliveryPrice);
						}
						this.prevData.actualPrice = this.prevData.actualPrice ? utilHelper.toFixed(Number(this
							.prevData.actualPrice), 2) : 0;
						this.prevData.fullPriceReductionAfter = this.prevData.actualPrice;
						this.prevData.discountPrice = 0;
						this.prevData.fullPriceReductionBefore = this.prevData.actualPrice > 0 ? this.prevData
							.actualPrice - feeData : 0;
						this.prevData.fullPriceReductionIsHidden = this.prevData.fullPriceReductionIsHidden;
						this.prevData.limitedPrice = 0;
						console.log('获取商家配送费====' + prevPage.data.reducedDeliveryPrice);
						console.log('获取用户地址配送费====' + feeData);
						console.log('获取商家支付后的配送费====' + reducedDeliveryPrice);
						console.log('获取价格====' + this.prevData.fullPriceReductionBefore);
						this.prevData.fullPriceReduction = this.prevData.fullPriceReductionBefore;
						for (let i = 0; i < result.data.records.length; i++) {
							//总价格减去配送费大于满减金额则进行满减操作
							if (Number(this.prevData.fullPriceReductionBefore) >= result.data.records[i]
								.limitedPrice) {
								//判断当前满减价格limitedPrice和上一个满减价格对比，如果大于就进行认证
								if (result.data.records[i].limitedPrice > this.prevData.limitedPrice) {
									this.prevData.limitedPrice = result.data.records[i].limitedPrice;
									this.prevData.fullPriceReduction = utilHelper.toFixed(this.prevData
										.fullPriceReductionBefore - result.data.records[i].reducedPrice, 2);
									this.prevData.fullReductionRuleName = result.data.records[i].name;
									this.prevData.fullReductionRuleId = result.data.records[i].id;
									this.prevData.fullPriceReductionIsHidden = true;
									this.prevData.fullPriceReductionAfter = utilHelper.toFixed(this.prevData
										.fullPriceReductionBefore - result.data.records[i].reducedPrice, 2);
									//console.log(this.data.prevData.fullPriceReduction)
								}
							}
						}

						console.log('获取最终配送金额=====' + reducedDeliveryPrice);
						console.log('获取最终优惠金额=====' + this.prevData.fullPriceReduction);
						this.prevData.fullPriceReduction = this.prevData.fullPriceReduction;
						if (app.globalData.deliveryAndSelfTaking.currentTab == 0) {
							this.prevData.fullPriceReduction = this.prevData.fullPriceReduction +
								reducedDeliveryPrice;
						}
						app.globalData.deliveryAndSelfTaking.isThereADiscount = false;
						if (reducedDeliveryPrice != feeData) {
							app.globalData.deliveryAndSelfTaking.isThereADiscount = true;
						}
						console.log('获取最终价格====' + this.prevData.fullPriceReduction);
						prevPage.setData({
							feeData: reducedDeliveryPrice,
							data: this.prevData,
							'data.couponsIsHidden': false
						});
					}
				});
			},

			getPonintsFullReductionRule_() {
				toastService.showLoading();
				https.request('/rest/pointsMall/fullReductionRule/list', {
					pageNo: -1,
					pageSize: 1
				}).then((result) => {
					toastService.hideLoading();
					if (result.success) {
						var pages = getCurrentPages();
						var prevPage = pages[pages.length - 2]; //上一个页面
						//获取配送费，配送费不作为满减条件
						let feeData = app.globalData.deliveryAndSelfTaking.reducedDeliveryTotalPrice ? app
							.globalData.deliveryAndSelfTaking.reducedDeliveryTotalPrice : 0;
						console.log('商品总价----》' + this.prevData.actualPrice);
						this.prevData.actualPrice = this.prevData.actualPrice ? utilHelper.toFixed(Number(this
							.prevData.actualPrice), 2) : 0;
						this.prevData.fullPriceReductionAfter = this.prevData.actualPrice;
						this.prevData.discountPrice = 0;
						this.prevData.fullPriceReductionBefore = this.prevData.actualPrice > 0 ? this.prevData
							.actualPrice - feeData : 0;
						this.prevData.fullPriceReductionIsHidden = this.prevData.fullPriceReductionIsHidden;
						this.prevData.limitedPrice = 0;
						this.prevData.fullPriceReduction = this.prevData.fullPriceReductionBefore;
						for (let i = 0; i < result.data.records.length; i++) {
							//总价格减去配送费大于满减金额则进行满减操作
							if (Number(this.prevData.fullPriceReductionBefore) >= result.data.records[i]
								.limitedPrice) {
								//判断当前满减价格limitedPrice和上一个满减价格对比，如果大于就进行认证
								if (result.data.records[i].limitedPrice > this.prevData.limitedPrice) {
									this.prevData.limitedPrice = result.data.records[i].limitedPrice;
									this.prevData.fullPriceReduction = utilHelper.toFixed(this.prevData
										.fullPriceReductionBefore - result.data.records[i].reducedPrice, 2);
									this.prevData.fullReductionRuleName = result.data.records[i].name;
									this.prevData.fullReductionRuleId = result.data.records[i].id;
									this.prevData.fullPriceReductionIsHidden = true;
									this.prevData.fullPriceReductionAfter = utilHelper.toFixed(this.prevData
										.fullPriceReductionBefore - result.data.records[i].reducedPrice, 2);
									//console.log(this.data.prevData.fullPriceReduction)
								}
							}
						}

						console.log('获取最终价格====' + this.prevData.fullPriceReduction);
						prevPage.setData({
							data: this.prevData,
							'data.couponsIsHidden': false
						});
					}
				});
			},

			onRadioChange(e) {
				var pages = getCurrentPages();
				var prevPage = pages[pages.length - 2]; //上一个页面
				//直接调用上一个页面的 setData() 方法，把数据存到上一个页面中去
				let afterDiscounts = this.prevData;
				// console.log(afterDiscounts)
				afterDiscounts.id = null;
				afterDiscounts.price = 0;
				afterDiscounts.couponsName = null;
				afterDiscounts.couponsDescription = null;
				afterDiscounts.preferentialType = null;
				prevPage.setData({
					afterDiscount: afterDiscounts
				});
				this.getFullReductionRule_();
				this.couponsList[e.currentTarget.dataset.index].couponsMemberRelationMap.checked = false;
				this.setData({
					couponsList: this.couponsList
				});
			},

			onPointsRadioChange(e) {
				var pages = getCurrentPages();
				var prevPage = pages[pages.length - 2]; //上一个页面
				//直接调用上一个页面的 setData() 方法，把数据存到上一个页面中去
				let afterDiscounts = this.prevData;
				// console.log(afterDiscounts)
				afterDiscounts.id = null;
				afterDiscounts.price = 0;
				afterDiscounts.couponsName = null;
				afterDiscounts.couponsDescription = null;
				afterDiscounts.preferentialType = null;
				prevPage.setData({
					afterDiscount: afterDiscounts
				});
				this.getPonintsFullReductionRule_();
				this.couponsPointsList[e.currentTarget.dataset.index].couponsMemberRelationMap.checked = false;
				this.setData({
					couponsPointsList: this.couponsPointsList
				});
			},

			onPullDownRefresh() {
				console.log('占位：函数 onPullDownRefresh 未声明');
			},

			onReachBottom() {
				console.log('占位：函数 onReachBottom 未声明');
			}
		}
	};
</script>
<style>
	page {
		background: #f5f5f5;
	}

	.swiper-content {
		border-radius: 15rpx;
		background-color: white;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.swiper-tab-item {
		width: 50%;
	}

	.swiper-box {
		/* height: 400rpx; */
		border-radius: 0 0 15rpx 15rpx;
	}

	.swiper-tab {
		width: 100%;
		box-shadow: -2px 0px 5px 0.5px rgba(0, 0, 0, 0.1);
		text-align: center;
		height: 88rpx;
		line-height: 88rpx;
		display: flex;
		flex-flow: row;
		justify-content: space-between;
		background: #fff;
		z-index: 1;
	}

	.swiper-tab-item {
		width: 50%;
	}

	.swiper-box {
		display: block;
		width: 100%;
		height: 100%;
		overflow: hidden;
	}

	.swiper-items {
		height: 100%;
	}

	.scroll-views {
		height: 100%;
	}

	::-webkit-scrollbar {
		width: 0;
		height: 0;
		color: transparent;
	}

	.isRelation {
		opacity: 0.4;
	}

	.coupins-item {
		background: white;
		border-radius: 10rpx;
		padding: 20rpx;
		margin: 20rpx;
	}

	.item-top {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.couponsName {
		font-size: 30rpx;
		font-weight: bold;
	}

	.endTime {
		color: #7882c7;
		font-size: 28rpx;
		line-height: 66rpx;
		height: 66rpx;
	}

	.bottom-view {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding-top: 20rpx;
	}

	.usage-rule {
		font-size: 28rpx;
		color: #a9a9a9;
	}

	.immediate-use {
		padding: 10rpx 20rpx;
		border-radius: 50rpx;
		font-size: 28rpx;
	}

	.afterDiscountPrice {
		color: red;
		font-size: 24rpx;
		text-align: right;
	}

	.out-of-commission {
		color: #a9a9a9;
		border: 1rpx solid #a9a9a9;
	}

	.couponsNum {
		text-align: right;
	}
</style>