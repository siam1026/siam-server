<template>
<uni-shadow-root class="vant-dist-skeleton-index"><view v-if="loading" :class="'custom-class '+(utils.bem('skeleton', [{animate}]))">
  <view v-if="avatar" :class="'avatar-class '+(utils.bem('skeleton__avatar', [avatarShape]))" :style="'width:' + avatarSize + ';height:' + avatarSize"></view>
  <view :class="utils.bem('skeleton__content')">
    <view v-if="title" :class="'title-class '+(utils.bem('skeleton__title'))" :style="'width:' + titleWidth"></view>
    <view v-for="(item,index) in (rowArray)" :key="item.index" :class="'row-class '+(utils.bem('skeleton__row'))" :style="'width:' + (isArray ? rowWidth[index] : rowWidth)"></view>
  </view>
</view>
<view v-else :class="utils.bem('skeleton__content')">
  <slot></slot>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>

global['__wxVueOptions'] = {components:{}}

global['__wxRoute'] = 'vant/dist/skeleton/index'
import { VantComponent } from '../common/component';
VantComponent({
    classes: ['avatar-class', 'title-class', 'row-class'],
    props: {
        row: {
            type: Number,
            value: 0,
            observer(value) {
                this.setData({ rowArray: Array.from({ length: value }) });
            },
        },
        title: Boolean,
        avatar: Boolean,
        loading: {
            type: Boolean,
            value: true,
        },
        animate: {
            type: Boolean,
            value: true,
        },
        avatarSize: {
            type: String,
            value: '32px',
        },
        avatarShape: {
            type: String,
            value: 'round',
        },
        titleWidth: {
            type: String,
            value: '40%',
        },
        rowWidth: {
            type: null,
            value: '100%',
            observer(val) {
                this.setData({ isArray: val instanceof Array });
            },
        },
    },
    data: {
        isArray: false,
        rowArray: [],
    },
});
export default global['__wxComponents']['vant/dist/skeleton/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-skeleton{box-sizing:border-box;display:flex;padding:var(--skeleton-padding,0 16px);width:100%}.van-skeleton__avatar{background-color:var(--skeleton-avatar-background-color,#f2f3f5);flex-shrink:0;margin-right:var(--padding-md,16px)}.van-skeleton__avatar--round{border-radius:100%}.van-skeleton__content{flex:1}.van-skeleton__avatar+.van-skeleton__content{padding-top:var(--padding-xs,8px)}.van-skeleton__row,.van-skeleton__title{background-color:var(--skeleton-row-background-color,#f2f3f5);height:var(--skeleton-row-height,16px)}.van-skeleton__title{margin:0}.van-skeleton__row:not(:first-child){margin-top:var(--skeleton-row-margin-top,12px)}.van-skeleton__title+.van-skeleton__row{margin-top:20px}.van-skeleton--animate{animation:van-skeleton-blink 1.2s ease-in-out infinite}@keyframes van-skeleton-blink{50%{opacity:.6}}
</style>