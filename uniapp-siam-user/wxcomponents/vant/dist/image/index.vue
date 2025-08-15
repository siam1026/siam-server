<template>
<uni-shadow-root class="vant-dist-image-index"><view :style="computed.rootStyle({ width, height, radius })" :class="'custom-class '+(utils.bem('image', { round }))" @click="onClick">
  <image v-if="(!error)" :src="src" :mode="computed.mode(fit)" :lazy-load="lazyLoad" :webp="webp" class="image-class van-image__img" :show-menu-by-longpress="showMenuByLongpress" @load="onImageLoad" @error="onImageError"></image>

  <view v-if="loading && showLoading" class="loading-class van-image__loading">
    <slot v-if="useLoadingSlot" name="loading"></slot>
    <van-icon v-else name="photo" custom-class="van-image__loading-icon"></van-icon>
  </view>
  <view v-if="error && showError" class="error-class van-image__error">
    <slot v-if="useErrorSlot" name="error"></slot>
    <van-icon v-else name="photo-fail" custom-class="van-image__error-icon"></van-icon>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs><wxs src="./index.wxs" module="computed"></wxs>
<script>
import VanIcon from '../icon/index.vue'
import VanLoading from '../loading/index.vue'
global['__wxVueOptions'] = {components:{'van-icon': VanIcon,'van-loading': VanLoading}}

global['__wxRoute'] = 'vant/dist/image/index'
import { VantComponent } from '../common/component';
import { button } from '../mixins/button';
VantComponent({
    mixins: [button],
    classes: ['custom-class', 'loading-class', 'error-class', 'image-class'],
    props: {
        src: {
            type: String,
            observer() {
                this.setData({
                    error: false,
                    loading: true,
                });
            },
        },
        round: Boolean,
        width: null,
        height: null,
        radius: null,
        lazyLoad: Boolean,
        useErrorSlot: Boolean,
        useLoadingSlot: Boolean,
        showMenuByLongpress: Boolean,
        fit: {
            type: String,
            value: 'fill',
        },
        webp: {
            type: Boolean,
            value: false,
        },
        showError: {
            type: Boolean,
            value: true,
        },
        showLoading: {
            type: Boolean,
            value: true,
        },
    },
    data: {
        error: false,
        loading: true,
        viewStyle: '',
    },
    methods: {
        onImageLoad(event) {
            this.setData({
                loading: false,
            });
            this.$emit('load', event.detail);
        },
        onImageError(event) {
            this.setData({
                loading: false,
                error: true,
            });
            this.$emit('error', event.detail);
        },
        onClick(event) {
            this.$emit('click', event.detail);
        },
    },
});
export default global['__wxComponents']['vant/dist/image/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-image{display:inline-block;position:relative}.van-image--round{border-radius:50%;overflow:hidden}.van-image--round .van-image__img{border-radius:inherit}.van-image__error,.van-image__img,.van-image__loading{display:block;height:100%;width:100%}.van-image__error,.van-image__loading{align-items:center;background-color:var(--image-placeholder-background-color,#f7f8fa);color:var(--image-placeholder-text-color,#969799);display:flex;flex-direction:column;font-size:var(--image-placeholder-font-size,14px);justify-content:center;left:0;position:absolute;top:0}.van-image__loading-icon{color:var(--image-loading-icon-color,#dcdee0);font-size:var(--image-loading-icon-size,32px)!important}.van-image__error-icon{color:var(--image-error-icon-color,#dcdee0);font-size:var(--image-error-icon-size,32px)!important}
</style>