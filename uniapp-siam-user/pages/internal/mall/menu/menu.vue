<template>
    <view>
        <view v-if="menuList.length > 0" class="mp-vtabs-view">
            <mp-vtabs
                :vtabs="menuList"
                :activeTab="activeTab"
                tabBarClass="mp-vtabs-class"
                @tabclick="onTabCLick"
                @change="onChange"
                tabBarLineColor="#004ca0"
                tabBarActiveTextColor="#004ca0"
            >
                <block v-for="(menu, menuIndex) in menuList" :key="menuIndex">
                    <mp-vtabs-content :tabIndex="menuIndex">
                        <view :class="'vtabs-content-item ' + (menuList.length - 1 == menuIndex ? 'is-end-item' : '')">
                            <view class="commodity-type position-sticky">
                                <view class="categoryName-view out_of_range one_row">{{ menu.name }}</view>
                            </view>
                            <block v-for="(chil, goodsIndex) in menu.goodsList" :key="goodsIndex">
                                <view :class="'commodity-item ' + (chil.goodsStatus == 4 ? 'isEnd' : '')" :hover-class="chil.goodsStatus == 4 ? '' : 'hover-class-public'">
                                    <view
                                        class="commodity-name-view"
                                        @tap="parseEventDynamicCode($event, chil.goodsStatus == 4 ? '' : 'commodityDetailTap')"
                                        :data-id="chil.goodsId"
                                    >
                                        <image :src="chil.mainImage ? chil.mainImage : '/static/assets/images/load-image.png'" mode="aspectFill" class="commodity-image"></image>
                                        <view class="sell-out" v-if="chil.goodsStatus == 4">售罄</view>
                                        <view class="commodity-name-english-view">
                                            <view class="commodity-name out_of_range two_row">{{ chil.goodsName }}</view>
                                            <view class="commodity-english"><text></text></view>
                                            <view class="money-view">￥{{ chil.goodsPrice }}</view>
                                        </view>
                                    </view>
                                </view>

                                <view class="money-insert-view">
                                    <!-- <view class="insert-view theme-bg">＋</view> -->
                                    <view class="stepper" v-if="chil.number > 0">
                                        <block>
                                            <text class="reduce-class" @tap="bindMinus" :data-cartId="chil.cartId" :data-number="chil.number">－</text>
                                            <input disabled type="number" :value="chil.number" class="reduce-class" />
                                            <text
                                                class="add-class"
                                                :data-goodsId="chil.goodsId"
                                                @tap.stop.prevent="parseEventDynamicCode($event, chil.goodsStatus != 4 ? 'openSpecifications' : '')"
                                            >
                                                ＋
                                            </text>
                                        </block>
                                    </view>
                                    <block v-else>
                                        <view
                                            :class="'insert-view theme-bg ' + (chil.goodsStatus == 4 ? 'isEnd' : '')"
                                            :data-goodsId="chil.goodsId"
                                            @tap="parseEventDynamicCode($event, chil.goodsStatus != 4 ? 'openSpecifications' : '')"
                                        >
                                            ＋
                                        </view>
                                    </block>
                                </view>
                            </block>
                        </view>
                    </mp-vtabs-content>
                </block>
            </mp-vtabs>
        </view>
        <van-action-sheet :show="specificationsDialog" title="选择规格" @close="close">
            <view class="content_box">
                <view class="goods-info-view">
                    <image :src="goodsInfo.mainImage" mode="aspectFill" class="commodity-image"></image>
                    <view>
                        <view class="goods-info-name out_of_range one_row">{{ goodsInfo.name }}</view>
                        <view class="goods-info-specListString">已选：{{ specListString }}</view>
                        <view class="goods-info-price">￥{{ priceAfter }}</view>
                    </view>
                </view>
                <scroll-view scroll-y style="height: 56vh">
                    <view class="commdity-name-type-view">
                        <view class="commdity-name">{{ data.name }}</view>
                        <view class="commdity-engname">{{ data.name }}</view>
                        <view class="commdity-type-item" v-for="(item, key) in specList" :key="key">
                            <view class="commdity-type-name">{{ key }}</view>

                            <radio-group class="radio-group" @change="radioChange" :data-firstIndex="key">
                                <label
                                    :class="
                                        'group-label theme-border ' +
                                        (!item.stock ? 'disabled-group-label' : '') +
                                        ' ' +
                                        (item.checked ? 'active theme-bg' : 'theme-color-border') +
                                        ' out_of_range one_row'
                                    "
                                    v-for="(item, index) in item"
                                    :key="index"
                                >
                                    <radio :value="index" :checked="item.checked" :disabled="!item.stock" class="radio" />

                                    {{ item.name }}
                                </label>
                            </radio-group>
                        </view>
						<van-empty v-if="specList.length <= 0" description="暂无规格"></van-empty>
                    </view>
                </scroll-view>
                <view slot="footer">
                    <view class="good-choice-btn theme-bg" @tap="insertShoppingCart">我选好了</view>
                </view>
            </view>
        </van-action-sheet>
    </view>
</template>

<script>
import GlobalConfig from '../../../../utils/global-config';
import https from '../../../../utils/http';
import authService from '../../../../utils/auth';
import {Base64} from 'js-base64';
var toastService = require('../../../../utils/toast.service');
var systemStatus = require('../../../../utils/system-status');
var dateHelper = require('../../../../utils/date-helper');
var utilHelper = require('../../../../utils/util');
var menuList = [];
var app = getApp();
export default {
    data() {
        return {
            specificationsDialog: false,
            menuList: '',
            goodsId: '',

            goodsInfo: {
                mainImage: '',
                name: ''
            },

            priceAfter: '',
            specListString: '',
            specList: '',
            shoppingCartDialog: false,
            activeTab: '',
            menuIndex: 0,

            menu: {
                name: '',
                goodsList: []
            },

            goodsIndex: 0,

            chil: {
                goodsStatus: 0,
                goodsId: '',
                mainImage: false,
                goodsName: '',
                goodsPrice: '',
                number: 0,
                cartId: ''
            },

            data: {
                name: ''
            }
        };
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.getMenuList();
    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {},
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
        getMenuList() {
            toastService.showLoading();
            var self = this;
            https.request('/rest/pointsMall/menu/listWithGoods', {}).then((result) => {
                toastService.hideLoading();
                if (result.success && result.data) {
                    //console.log(result.data)
                    result.data.forEach((aitem, index) => {
                        aitem.goodsList.forEach((bitem) => {
                            bitem.isShopCart = false;
                            bitem.mainImage = GlobalConfig.ossUrl + bitem.mainImage;
                        });
                    });
                    menuList = result.data;
                    this.setData({
                        menuList: menuList
                    });
                }
            });
        },

        openSpecifications(e) {
            toastService.showLoading();
            this.setData({
                specificationsDialog: true,
                goodsId: e.currentTarget.dataset.goodsid
            });
            this.getCommodityDetails(e.currentTarget.dataset.goodsid);
        },

        getCommodityDetails(id) {
            https
                .request('/rest/pointsMall/goods/selectById', {
                    id: id,
                    pageNo: -1,
                    pageSize: 20
                })
                .then((result) => {
                    toastService.hideLoading();
                    if (result.success && result.data) {
                        //获取商品的详细图片，转换以轮播图的数据格式
                        //console.log(result.data)
                        result.data.mainImage = GlobalConfig.ossUrl + result.data.mainImage;
                        this.setData({
                            goodsInfo: result.data,
                            priceAfter: result.data.price
                        });
                        this.selectByGoodsId(id);
                    }
                });
        },

        commodityDetailTap(e) {
            console.log(e.currentTarget.dataset.id);
            uni.navigateTo({
                url: '../detail/detail?id=' + e.currentTarget.dataset.id
            });
        },

        selectByGoodsId(goodsId) {
            console.log(this);
            https
                .request('/rest/pointsMall/goodsSpecificationOption/selectByGoodsId', {
                    goodsId: goodsId
                })
                .then((result) => {
                    if (result.success && result.data) {
                        //重新设置商品的规格等数据的格式
                        //let goodsSpecs = {};
                        let specList = result.data;
                        let price = 0;
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
                                    specListString = (specListString ? specListString + '/' : specListString) + specList[key][keyof].name;
                                    isChecked = false;
                                }
                            }
                        }
                        this.setData({
                            specListString: specListString ? specListString : '暂无规格',
                            specList: JSON.stringify(specList) == '{}' ? [] : specList
                        });
                    }
                });
        },

        //事件处理函数
        /*点击减号*/
        bindMinus: function (e) {
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
                totalNum--;
                that.getShoppingCartList();
            });
        },

        /*点击加号*/
        bindPlus: function (e) {
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

            let price = this.goodsInfo.price;
            let specListString = '';
            for (let key in specList) {
                for (let keyof in specList[key]) {
                    //console.log(specList[key][keyof].price)
                    if (specList[key][keyof].checked) {
                        price = price + specList[key][keyof].price;
                        specListString = (specListString ? specListString + '/' : specListString) + specList[key][keyof].name;
                    }
                }
            }
            // let totalPrice = price * this.data.commodityNum;
            // let fullPriceReduction = 0,
            //    fullReductionRuleName = "",
            //    fullPriceReductionIsHidden = false,
            //    limitedPrice = 0;
            // //console.log(this.data.fullReductionRuleList)
            // for (let i = 0; i < this.data.shopInfo.fullReductionRuleList.length; i++) {
            //    //console.log(this.data.fullReductionRuleList[i].limitedPrice)
            //    if (totalPrice >= this.data.shopInfo.fullReductionRuleList[i].limitedPrice) {
            //       if (this.data.shopInfo.fullReductionRuleList[i].limitedPrice > limitedPrice) {
            //          limitedPrice = this.data.shopInfo.fullReductionRuleList[i].limitedPrice;
            //          fullPriceReduction = (totalPrice + this.data.data.packingCharges) - this.data.shopInfo.fullReductionRuleList[i].reducedPrice;
            //          fullReductionRuleName = this.data.shopInfo.fullReductionRuleList[i].name;
            //          fullPriceReductionIsHidden = true;
            //       }
            //    }
            // }

            // for (var key in specList) {
            //   console.log(specList[key])
            //   specListString = (specListString ? specListString + "/" : specListString) + specList[key];
            // }
            //console.log(specList)
            this.setData({
                specList: specList,
                specListString: specListString,
                priceAfter: price
                // totalPrice: utilHelper.toFixed(totalPrice, 2),
                // fullPriceReduction: utilHelper.toFixed(fullPriceReduction, 2),
                // fullPriceReductionIsHidden: fullPriceReductionIsHidden,
                // fullReductionRuleName: fullReductionRuleName
            });
        },

        insertShoppingCart(e) {
            // if(!goodsSpecs){
            //   toastService.showToast("请选择规格");
            //   return
            // }
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
                        goodsId: this.goodsInfo.id,
                        specList: JSON.stringify(goodsSpecs)
                    };
                    https.request('/rest/member/pointsMall/shoppingCart/insert', data).then((result) => {
                        if (result.success) {
                            toastService.showToast('加入购物车成功');
                            this.setData({
                                specificationsDialog: false
                            });
                        }
                    });
                    return;
                }
                app.globalData.checkIsAuth('scope.userInfo');
            });
        },

        onTabCLick() {
            console.log('占位：函数 onTabCLick 未声明');
        },

        onChange() {
            console.log('占位：函数 onChange 未声明');
        },
		close(){
			this.setData({
				specificationsDialog:false
			})
		}
    }
};
</script>
<style>
page {
    background-color: #fff;
}

.mp-vtabs-view {
    background-color: #fff;
}

.content-class {
    /* margin-top: 6%; */
    width: 100%;
    height: 100%;
}

/* 地址定位 */
.store-full-name-view {
    box-shadow: -2px 0.5px 5px 0.5px rgba(0, 0, 0, 0.1);
    padding: 15rpx 20rpx;
    margin: 15rpx 20rpx;
    border-radius: 10rpx;
    /* display: flex;
   justify-content: space-between;
   align-items: center; */
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

.radio-group {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
    padding: 5rpx;
    border-radius: 50rpx;
    /* height: 66rpx; */
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
    height: 244rpx;
}

.slide-image {
    width: 100%;
    height: 244rpx;
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
    width: 100%;
    /* background: white; */
    border-radius: 10rpx;
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
    /* margin-top: 25rpx; */
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
    border-radius: 5rpx;
    background-color: white;
}

.commodity-image {
    width: 170rpx;
    height: 166rpx;
    border-radius: 8rpx;
    margin-right: 10rpx;
}

.commodity-name-english-view {
    width: 62%;
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
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    line-height: 45rpx;
}

.shangjiatuijianjia {
    margin-right: 35rpx;
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
    z-index: 9999;
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
    margin-right: 20rpx;
    border-radius: 50%;
    border: 10rpx solid #444444;
}

.shopping-cart-content {
    width: 100%;
    display: flex;
    align-items: center;
    background: #535257;
    z-index: 9999;
    height: 54px;
}

.shopping-cart-left {
    width: 70%;
    height: 100%;
    display: flex;
    align-items: center;
    background: #505052;
    z-index: 9999;
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
    padding: 10rpx 10rpx 10rpx 0;
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

.vtabs-content-item {
    height: 100%;
}

.mp-vtabs-class scroll-view {
    height: 100vh;
}

.weui-vtabs-content__wrp scroll-view {
    height: 100vh;
}

.weui-vtabs-bar__scrollview .weui-vtabs-bar__content {
    padding-bottom: 50px;
    background: #eeeeee;
}

.weui-vtabs-content__scrollview .weui-vtabs-content {
    padding-bottom: 50px;
}

.is-end-item {
    padding-bottom: 50px;
    background-color: white;
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
</style>
