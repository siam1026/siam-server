<template>
<uni-shadow-root class="vant-dist-notify-index"><van-transition name="slide-down" :show="show" custom-class="van-notify__container" :custom-style="computed.rootStyle({ zIndex, top })" @click.native="onTap">
  <view :class="'van-notify van-notify--'+(type)" :style="computed.notifyStyle({ background, color })">
    <view v-if="safeAreaInsetTop" :style="'height: '+(statusBarHeight)+'px'"></view>
    <text>{{ message }}</text>
  </view>
</van-transition></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs><wxs src="./index.wxs" module="computed"></wxs>
<script>
import VanTransition from '../transition/index.vue'
global['__wxVueOptions'] = {components:{'van-transition': VanTransition}}

global['__wxRoute'] = 'vant/dist/notify/index'
import { VantComponent } from '../common/component';
import { WHITE } from '../common/color';
import { getSystemInfoSync } from '../common/utils';
VantComponent({
    props: {
        message: String,
        background: String,
        type: {
            type: String,
            value: 'danger',
        },
        color: {
            type: String,
            value: WHITE,
        },
        duration: {
            type: Number,
            value: 3000,
        },
        zIndex: {
            type: Number,
            value: 110,
        },
        safeAreaInsetTop: {
            type: Boolean,
            value: false,
        },
        top: null,
    },
    data: {
        show: false,
        onOpened: null,
        onClose: null,
        onClick: null,
    },
    created() {
        const { statusBarHeight } = getSystemInfoSync();
        this.setData({ statusBarHeight });
    },
    methods: {
        showNotify() {
            const { duration, onOpened } = this.data;
            clearTimeout(this.timer);
            this.setData({ show: true });
            wx.nextTick(onOpened);
            if (duration > 0 && duration !== Infinity) {
                this.timer = setTimeout(() => {
                    this.hide();
                }, duration);
            }
        },
        hide() {
            const { onClose } = this.data;
            clearTimeout(this.timer);
            this.setData({ show: false });
            wx.nextTick(onClose);
        },
        onTap(event) {
            const { onClick } = this.data;
            if (onClick) {
                onClick(event.detail);
            }
        },
    },
});
export default global['__wxComponents']['vant/dist/notify/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-notify{word-wrap:break-word;font-size:var(--notify-font-size,14px);line-height:var(--notify-line-height,20px);padding:var(--notify-padding,6px 15px);text-align:center}.van-notify__container{box-sizing:border-box;left:0;position:fixed;top:0;width:100%}.van-notify--primary{background-color:var(--notify-primary-background-color,#1989fa)}.van-notify--success{background-color:var(--notify-success-background-color,#07c160)}.van-notify--danger{background-color:var(--notify-danger-background-color,#ee0a24)}.van-notify--warning{background-color:var(--notify-warning-background-color,#ff976a)}
</style>