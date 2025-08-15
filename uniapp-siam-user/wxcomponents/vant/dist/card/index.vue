<template>
<uni-shadow-root class="vant-dist-card-index"><view class="custom-class van-card">
  <view :class="utils.bem('card__header', { center: centered })">
    <view class="van-card__thumb" @click="onClickThumb">
      <image v-if="thumb" :src="thumb" :mode="thumbMode" :lazy-load="lazyLoad" class="van-card__img thumb-class"></image>
      <slot v-else name="thumb"></slot>
      <van-tag v-if="tag" mark type="danger" custom-class="van-card__tag">
        {{ tag }}
      </van-tag>
      <slot v-else name="tag"></slot>
    </view>

    <view :class="'van-card__content '+(utils.bem('card__content', { center: centered }))">
      <view>
        <view v-if="title" class="van-card__title title-class">{{ title }}</view>
        <slot v-else name="title"></slot>

        <view v-if="desc" class="van-card__desc desc-class">{{ desc }}</view>
        <slot v-else name="desc"></slot>

        <slot name="tags"></slot>
      </view>

      <view class="van-card__bottom">
        <slot name="price-top"></slot>
        <view v-if="price || price === 0" class="van-card__price price-class">
          <text>{{ currency }}</text>
          <text class="van-card__price-integer">{{ integerStr }}</text>
          <text class="van-card__price-decimal">{{ decimalStr }}</text>
        </view>
        <slot v-else name="price"></slot>
        <view v-if="originPrice || originPrice === 0" class="van-card__origin-price origin-price-class">{{ currency }} {{ originPrice }}</view>
        <slot v-else name="origin-price"></slot>
        <view v-if="num" class="van-card__num num-class">x {{ num }}</view>
        <slot v-else name="num"></slot>
        <slot name="bottom"></slot>
      </view>
    </view>
  </view>

  <view class="van-card__footer">
    <slot name="footer"></slot>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>
import VanTag from '../tag/index.vue'
global['__wxVueOptions'] = {components:{'van-tag': VanTag}}

global['__wxRoute'] = 'vant/dist/card/index'
import { link } from '../mixins/link';
import { VantComponent } from '../common/component';
VantComponent({
    classes: [
        'num-class',
        'desc-class',
        'thumb-class',
        'title-class',
        'price-class',
        'origin-price-class',
    ],
    mixins: [link],
    props: {
        tag: String,
        num: String,
        desc: String,
        thumb: String,
        title: String,
        price: {
            type: String,
            observer: 'updatePrice',
        },
        centered: Boolean,
        lazyLoad: Boolean,
        thumbLink: String,
        originPrice: String,
        thumbMode: {
            type: String,
            value: 'aspectFit',
        },
        currency: {
            type: String,
            value: '¥',
        },
    },
    methods: {
        updatePrice() {
            const { price } = this.data;
            const priceArr = price.toString().split('.');
            this.setData({
                integerStr: priceArr[0],
                decimalStr: priceArr[1] ? `.${priceArr[1]}` : '',
            });
        },
        onClickThumb() {
            this.jumpLink('thumbLink');
        },
    },
});
export default global['__wxComponents']['vant/dist/card/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-card{background-color:var(--card-background-color,#fafafa);box-sizing:border-box;color:var(--card-text-color,#323233);font-size:var(--card-font-size,12px);padding:var(--card-padding,8px 16px);position:relative}.van-card__header{display:flex}.van-card__header--center{align-items:center;justify-content:center}.van-card__thumb{flex:none;height:var(--card-thumb-size,88px);margin-right:var(--padding-xs,8px);position:relative;width:var(--card-thumb-size,88px)}.van-card__thumb:empty{display:none}.van-card__img{border-radius:8px;height:100%;width:100%}.van-card__content{display:flex;flex:1;flex-direction:column;justify-content:space-between;min-height:var(--card-thumb-size,88px);min-width:0;position:relative}.van-card__content--center{justify-content:center}.van-card__desc,.van-card__title{word-wrap:break-word}.van-card__title{font-weight:700;line-height:var(--card-title-line-height,16px)}.van-card__desc{color:var(--card-desc-color,#646566);line-height:var(--card-desc-line-height,20px)}.van-card__bottom{line-height:20px}.van-card__price{color:var(--card-price-color,#ee0a24);display:inline-block;font-size:var(--card-price-font-size,12px);font-weight:700}.van-card__price-integer{font-size:var(--card-price-integer-font-size,16px)}.van-card__price-decimal,.van-card__price-integer{font-family:var(--card-price-font-family,Avenir-Heavy,PingFang SC,Helvetica Neue,Arial,sans-serif)}.van-card__origin-price{color:var(--card-origin-price-color,#646566);display:inline-block;font-size:var(--card-origin-price-font-size,10px);margin-left:5px;text-decoration:line-through}.van-card__num{float:right}.van-card__tag{left:0;position:absolute!important;top:2px}.van-card__footer{flex:none;text-align:right;width:100%}
</style>