<template>
    <view>
        <view class="search-input-views position-sticky theme-bg" @tap="searchBusinessTap">
            <view class="search-input-view">
                <text class="iconfont iconsousuo-copy"></text>
                <!-- <image src="../../assets/common/search.png" mode="widthFix" class="search-image-class"></image> -->
                <input
                    class="search-input"
                    placeholder-class="placeholder-search-class"
                    confirm-type="search"
                    placeholder="搜索商家、商品名称"
                    @input="searchInputFun"
                    :focus="true"
                    :value="searchValue"
                    @confirm="searchTap"
                />
            </view>
        </view>
        <scroll-view class="scroll-views" :style="'height:' + bussinessHeight + 'px'" scroll-y scroll-top="scrollTop">
            <view v-if="!searchInput">
                <!-- <view class="history-hot-search">
			<view class="history-hot-search-title">
				<view>搜索历史</view>
				<image src="../../assets/common/delete.png" mode="widthFix" class="delete-image-class"></image>
			</view>
			<view class="history-hot-items">
				<radio-group class="radio-group">
					<label class="group-label history-hot-item" bindtap="radioSearchTap" data-name="瑞幸">
						<radio class="radio"></radio>
						瑞幸
					</label>
				</radio-group>
			</view>
		</view> -->
                <!-- <view class="history-hot-search">
			<view class="history-hot-search-title">
				<view>热门搜索</view>
				<view></view>
			</view>
			<view class="history-hot-items">
				<radio-group class="radio-group">
					<label class="group-label history-hot-item">
						<radio class="radio"></radio>
						瑞幸
					</label>
				</radio-group>
			</view>
		</view> -->
            </view>

            <view v-else>
                <view class="search-value" @tap="searchTap" v-if="searchTip">
                    <text class="iconfont iconsousuo-copy"></text>
                    <!-- <image src="../../assets/common/search.png" mode="widthFix" class="search-value-image-class"></image> -->
                    <view class="search-value-class">查找“{{ searchValue }}”</view>
                </view>
				<van-empty v-if="shopList.length <= 0" description="定位地址暂无商家">
				</van-empty>
                <view class="business-items margin-common" v-if="shopList.length > 0">
                    <view class="business-item" v-for="(item, shopIndex) in shopList" :key="shopIndex">
                        <view class="image-detail">
                            <view class="main-image-num" style="width: 25%">
                                <image :src="item.shopLogoImg" mode="widthFix" class="business-image"></image>
                                <view class="num" v-if="item.shopCartNums > 0">{{ item.shopCartNums }}</view>
                            </view>
                            <view class="business-info">
                                <view @tap="businessTap" :data-id="item.id">
                                    <view class="business-info-flex business-name">
                                        <text class="out_of_range one_row">{{ item.name }}</text>
                                    </view>
                                    <view class="business-info-flex">
                                        <view class="business-sale">
                                            <text class="business-evaluate">★ {{ item.serviceRating }}</text>
                                            <text class="business-right">月售：{{ item.shopAdditionalVo.latelyMonthlySales }}</text>
                                        </view>
                                    </view>
                                    <view class="business-info-flex">
                                        <view class="business-sale">
                                            <text>起送￥{{ item.startDeliveryPrice }}</text>
                                            <text :decode="true">&nbsp;&nbsp;配送&nbsp;</text>
                                            <text v-if="item.isfeeData">{{ item.feeData }}</text>
                                            <text :class="'business-right ' + (item.isfeeData ? 'strike_through' : '')">￥{{ item.shopAdditionalVo.deliveryFee }}</text>
                                        </view>
                                        <text>{{ item.shopAdditionalVo.deliveryDurationText }}{{ item.shopAdditionalVo.deliveryDistanceText }}</text>
                                    </view>
                                </view>
                                <view class="business-discount-info business-info-flex" @tap="isPromotionTap" :data-shopIndex="shopIndex">
                                    <view class="business-discount out_of_range one_row">
                                        <view
                                            class="theme-color-border business-discount-list"
                                            v-if="index < 4 && item.shopAdditionalVo.promotionList.length > 0"
                                            v-for="(rule, index) in item.shopAdditionalVo.promotionList"
                                            :key="index"
                                        >
                                            {{ rule.name }}
                                        </view>
                                        <view v-if="item.isfeeData" class="theme-color">配送费立减{{ item.shopAdditionalVo.deliveryFee - item.feeData }}元</view>
                                    </view>
                                    <text class="iconfont iconweibiaoti35-copy"></text>
                                </view>
                            </view>
                        </view>

                        <view class="goods-list-view">
                            <view
                                class="goods-item"
                                v-if="index <= 2"
                                @tap="commodityDetailTap"
                                :data-shopid="item.shopId"
                                :data-id="item.id"
                                v-for="(item, index) in item.goodsList"
                                :key="index"
                            >
                                <image :src="item.mainImage" class="goods-image" mode="widthFix"></image>

                                <view class="goods-name out_of_range one_row">{{ item.name }}</view>

                                <view class="goods-price">￥{{ item.price }}</view>
                            </view>
                        </view>
                    </view>
                </view>
            </view>
        </scroll-view>
        <mp-halfScreenDialog :show="isActivityDialog" extClass="extClassSpecifications">
            <view slot="title">优惠活动</view>
            <view slot="desc">
                <scroll-view style="height: 30vh" scroll-y>
                    <view>
                        <view v-if="shopList[shopIndex].shopAdditionalVo.promotionList.length">
                            <text class="dialog-title">优惠：</text>
                            <view class="business-discount-info business-info-flex">
                                <view class="business-discount out_of_range one_row">
                                    <view
                                        class="theme-color-border business-discount-list"
                                        v-for="(rule, index) in shopList[shopIndex].shopAdditionalVo.promotionList"
                                        :key="index"
                                    >
                                        {{ rule.name }}
                                    </view>
                                </view>
                            </view>
                        </view>
                        <view class="reduced-delivery-price" v-if="shopList[shopIndex].reducedDeliveryPrice > 0">
                            <text class="dialog-title">配送费：</text>
                            配送费立减{{ shopList[shopIndex].reducedDeliveryPrice }}元
                        </view>
                    </view>
                </scroll-view>
            </view>
        </mp-halfScreenDialog>
    </view>
</template>

<script>
import GlobalConfig from '../../../utils/global-config';
const show = require('../../../utils/toast.service.js');
import https from '../../../utils/http';
//获取应用实例
const app = getApp();
export default {
    data() {
        return {
            searchInput: false,
            searchTip: false,
            isActivityDialog: false,
            location: '',
            bussinessHeight: '',
            searchValue: '',
            shopList: [],
            shopIndex: '',

            rule: {
                name: ''
            },

            shopAdditionalVo: {
                promotionList: []
            },

            reducedDeliveryPrice: 0
        };
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        console.log(options);
        this.setData({
            location: options.location
        });
    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {
        this.getScrolls();
    },
    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {},
    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {},
    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {},
    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {},
    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {},
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {},
    methods: {
        getScrolls() {
            var that = this;
            const query = uni.createSelectorQuery();
            let windowHeight = app.globalData.systemInfoSync.windowHeight;
            query
                .selectAll('.search-input-views')
                .boundingClientRect(function (rect) {
                    let height = rect[0].height;
                    that.setData({
                        bussinessHeight: windowHeight
                    });
                })
                .exec();
        },

        searchInputFun(e) {
            console.log(e);
            let value = e.detail.value;
            this.searchInput = false;
            if (value && value != this.searchValue) {
                this.searchInput = true;
                this.searchTip = true;
            }
            this.setData({
                searchInput: this.searchInput,
                searchValue: value,
                searchTip: this.searchTip,
                shopList: []
            });
        },

        searchTap() {
            show.showLoading('搜索中...');
            this.getShopList(this.searchValue);
        },

        businessTap(e) {
            uni.navigateTo({
                url: '../../menu/index/index?id=' + e.currentTarget.dataset.id
            });
        },

        radioSearchTap(e) {
            this.setData({
                searchValue: e.currentTarget.dataset.name
            });
            // let searchValue = {
            //   detail: {
            //     value: e.currentTarget.dataset.name
            //   }
            // }
            // this.searchInput(searchValue)
            this.getShopList(e.currentTarget.dataset.name);
        },

        getShopList(name) {
            https
                .request('merchant/rest/shop/search', {
                    pageNo: -1,
                    pageSize: 5,
                    keywords: name,
                    position: app.globalData.deliveryAndSelfTaking.location
                })
                .then((result) => {
                    show.hideLoading();
                    if (result.success) {
                        result.data.records.forEach((item, index) => {
                            item.shopLogoImg = GlobalConfig.ossUrl + item.shopLogoImg;
                            item.isfeeData = false;
                            if (item.reducedDeliveryPrice > 0) {
                                if (item.reducedDeliveryPrice >= item.shopAdditionalVo.deliveryFee) {
                                    item.feeData = 0;
                                    item.isfeeData = true;
                                } else {
                                    item.feeData = item.shopAdditionalVo.deliveryFee - item.reducedDeliveryPrice;
                                    item.isfeeData = true;
                                }
                            }
                            item.goodsList.forEach((goods, goodsIndex) => {
                                goods.mainImage = GlobalConfig.ossUrl + goods.mainImage;
                            });
                        });
                        this.setData({
                            searchTip: result.data.records ? false : true,
                            searchInput: result.data.records ? true : false
                        });
                        this.getShoppingCartList(result.data.records);
                    }
                });
        },

        getShoppingCartList(shopList) {
            shopList.forEach((item, index) => {
                https
                    .request('/rest/member/shoppingCart/list', {
                        shopId: item.id,
                        pageNo: -1,
                        pageSize: 20
                    })
                    .then((result) => {
                        show.hideLoading();
                        if (result.success && result.data) {
                            var number = 0;
                            result.data.records.forEach((cart, index) => {
                                number = number + cart.number;
                            });
                            item.shopCartNums = number;
                            this.setData({
                                shopList: shopList
                            });
                        }
                    });
            });
        },

        commodityDetailTap(e) {
            uni.navigateTo({
                url: '../../menu/detail/detail?id=' + e.currentTarget.dataset.id + '&shopId=' + e.currentTarget.dataset.shopid
            });
        },

        isPromotionTap(e) {
            let shopIndex = e.currentTarget.dataset.shopindex;
            console.log(shopIndex);
            this.setData({
                isActivityDialog: true,
                shopIndex: shopIndex
            });
        },

        searchBusinessTap() {
            console.log('占位：函数 searchBusinessTap 未声明');
        }
    }
};
</script>
<style>
page {
    background: #f5f5f5;
}

.search-input-views {
    padding: 20rpx;
}

.iconsousuo-copy {
    color: #c3c3c3;
}

.search-input-view {
    display: flex;
    align-items: center;
    background: #f5f5f5;
    border-radius: 30rpx;
    padding: 5rpx 15rpx;
}

.search-image-class {
    width: 40rpx;
    height: auto;
}

.placeholder-search-class {
    color: #c3c3c3;
    font-size: 25rpx;
}

.search-input {
    color: black;
    font-size: 28rpx;
    margin: 5rpx 0 5rpx 15rpx;
    margin-left: 15rpx;
    width: 100%;
}

.history-hot-search {
    font-size: 28rpx;
    color: #a5a5a5;
}

.history-hot-search-title {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 20rpx;
}

.delete-image-class {
    width: 30rpx;
    height: auto;
}

.history-hot-items {
    padding: 5rpx 20rpx;
}

.history-hot-item {
    border: 1rpx solid #e2e2e2;
    padding: 10rpx 20rpx;
    border-radius: 10rpx;
    margin-right: 15rpx;
    word-break: break-all;
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

.search-value {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 20rpx;
}

.search-value-image-class {
    width: 6%;
    height: auto;
}

.search-value-class {
    width: 90%;
    border-bottom: 1rpx solid #e2e2e2;
    font-size: 28rpx;
    padding: 20rpx 20rpx 20rpx 0;
}

.business-items {
    margin-top: 20rpx;
}

.main-image-num {
    position: relative;
    width: 100%;
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

.business-item {
    /* display: flex; */
    margin-bottom: 20rpx;
    background-color: white;
    padding: 20rpx;
    border-radius: 15rpx;
    /* align-items: center; */
}

.image-detail {
    display: flex;
    justify-content: space-between;
}

.business-image {
    width: 25%;
}

.business-info {
    width: 75%;
    margin-left: 15rpx;
    padding: 10rpx 0 20rpx 0;
    border-bottom: 1rpx solid #f5f5f5;
}

.business-info-flex {
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 24rpx;
    color: #717171;
}

.business-sale {
    margin: 5rpx 0;
}

.business-fsize-color {
    font-size: 24rpx;
    color: #717171;
}

.business-name {
    font-size: 34rpx;
    font-weight: bold;
    color: black;
}

.business-evaluate {
    color: #ff6500;
}

.business-right {
    margin-left: 15rpx;
}

.business-image {
    width: 100%;
    height: auto;
    border: 1rpx solid #f5f5f5;
}

.business-discount-list {
    padding: 1rpx 6rpx;
    font-size: 20rpx;
    border-radius: 10rpx;
    margin-right: 5rpx;
}

.business-discount {
    width: 90%;
    padding-bottom: 1rpx;
    display: flex;
    align-items: center;
}

.goods-list-view {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    margin-top: 10rpx;
}

.goods-item {
    width: 33%;
    margin: 0 20rpx;
}

.goods-image {
    width: 100%;
    height: 62px;
}

.goods-name {
    font-size: 26rpx;
}

.goods-price {
    font-size: 26rpx;
    color: #ff6500;
}

.reduced-delivery-price {
    font-size: 30rpx;
    margin-top: 20rpx;
}

.dialog-title {
    font-size: 30rpx;
}

/* 自定义弹出框的最大高度为100%，并设置他的左右上交的border-ric为0 */
.weui-show .weui-half-screen-dialog.extClassShoppingCart {
    max-height: 100vh;
    padding: 0 20rpx;
    position: fixed;
    bottom: 0;
    padding-bottom: 12%;
}

.weui-half-screen-dialog.extClassShoppingCart .weui-half-screen-dialog__ft {
    padding: 20rpx 0;
    position: sticky;
    bottom: 0;
}

.weui-show .weui-half-screen-dialog.extClassShoppingCart .weui-half-screen-dialog__hd {
    padding: 0 20rpx;
}

/* 选择商品规格弹窗 */
.weui-show .weui-half-screen-dialog.extClassSpecifications {
    z-index: 9999999;
}

/* 自定义弹窗样式 */
.weui-show .weui-half-screen-dialog.extClassSpecifications {
    padding: 0 20rpx;
}

.weui-half-screen-dialog.extClassSpecifications .weui-half-screen-dialog__ft {
    padding: 20rpx 0 0 0;
    position: sticky;
    bottom: 0;
}

/* 自定义弹出框的最大高度为100%，并设置他的左右上交的border-ric为0 */
.weui-show .weui-half-screen-dialog.extClassSpecifications {
    max-height: 90vh;
    /* border-radius: 0%; */
}

.weui-show .weui-half-screen-dialog.extClassSpecifications .weui-half-screen-dialog__hd {
    padding: 0 20rpx;
}

.iconweibiaoti35-copy {
    font-size: 26rpx;
}
</style>
