<template>
<uni-shadow-root class="vant-image-index"><view :style="viewStyle" :class="'custom-class '+(utils.bem('image', { round }))" @click="onClick">
  <image v-if="(!error)" :src="src" :mode="mode" :lazy-load="lazyLoad" class="image-class van-image__img" :show-menu-by-longpress="showMenuByLongpress" @load="onImageLoad" @error="onImageError"></image>

  <view v-if="loading && showLoading" class="loading-class van-image__loading">
    <slot v-if="useLoadingSlot" name="loading"></slot>
    <van-icon v-else name="photo-o" size="22"></van-icon>
  </view>
  <view v-if="error && showError" class="error-class van-image__error">
    <slot v-if="useErrorSlot" name="error"></slot>
    <van-icon v-else name="warning-o" size="22"></van-icon>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>
import VanIcon from '../icon/index.vue'
import VanLoading from '../loading/index.vue'
global['__wxVueOptions'] = {components:{'van-icon': VanIcon,'van-loading': VanLoading}}

global['__wxRoute'] = 'vant/image/index'
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var utils_1 = require("../common/utils");
var component_1 = require("../common/component");
var button_1 = require("../mixins/button");
var open_type_1 = require("../mixins/open-type");
var FIT_MODE_MAP = {
    none: 'center',
    fill: 'scaleToFill',
    cover: 'aspectFill',
    contain: 'aspectFit',
    widthFix: 'widthFix',
    heightFix: 'heightFix'
};
component_1.VantComponent({
    mixins: [button_1.button, open_type_1.openType],
    classes: ['custom-class', 'loading-class', 'error-class', 'image-class'],
    props: {
        src: {
            type: String,
            observer: function () {
                this.setData({
                    error: false,
                    loading: true
                });
            }
        },
        round: Boolean,
        width: {
            type: null,
            observer: 'setStyle'
        },
        height: {
            type: null,
            observer: 'setStyle'
        },
        radius: null,
        lazyLoad: Boolean,
        useErrorSlot: Boolean,
        useLoadingSlot: Boolean,
        showMenuByLongpress: Boolean,
        fit: {
            type: String,
            value: 'fill',
            observer: 'setMode'
        },
        showError: {
            type: Boolean,
            value: true
        },
        showLoading: {
            type: Boolean,
            value: true
        }
    },
    data: {
        error: false,
        loading: true,
        viewStyle: '',
    },
    mounted: function () {
        this.setMode();
        this.setStyle();
    },
    methods: {
        setMode: function () {
            this.setData({
                mode: FIT_MODE_MAP[this.data.fit],
            });
        },
        setStyle: function () {
            var _a = this.data, width = _a.width, height = _a.height, radius = _a.radius;
            var style = '';
            if (utils_1.isDef(width)) {
                style += "width: " + utils_1.addUnit(width) + ";";
            }
            if (utils_1.isDef(height)) {
                style += "height: " + utils_1.addUnit(height) + ";";
            }
            if (utils_1.isDef(radius)) {
                style += 'overflow: hidden;';
                style += "border-radius: " + utils_1.addUnit(radius) + ";";
            }
            this.setData({ viewStyle: style });
        },
        onImageLoad: function (event) {
            this.setData({
                loading: false
            });
            this.$emit('load', event.detail);
        },
        onImageError: function (event) {
            this.setData({
                loading: false,
                error: true
            });
            this.$emit('error', event.detail);
        },
        onClick: function (event) {
            this.$emit('click', event.detail);
        }
    }
});
export default global['__wxComponents']['vant/image/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-image{position:relative;display:inline-block}.van-image--round{overflow:hidden;border-radius:50%}.van-image--round .van-image__img{border-radius:inherit}.van-image__error,.van-image__img,.van-image__loading{display:block;width:100%;height:100%}.van-image__error,.van-image__loading{position:absolute;top:0;left:0;display:-webkit-flex;display:flex;-webkit-flex-direction:column;flex-direction:column;-webkit-align-items:center;align-items:center;-webkit-justify-content:center;justify-content:center;color:#969799;color:var(--image-placeholder-text-color,#969799);font-size:14px;font-size:var(--image-placeholder-font-size,14px);background-color:#f7f8fa;background-color:var(--image-placeholder-background-color,#f7f8fa)}
</style>