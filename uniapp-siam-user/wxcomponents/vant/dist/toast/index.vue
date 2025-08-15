<template>
<uni-shadow-root class="vant-dist-toast-index"><van-overlay v-if="mask || forbidClick" :show="show" :z-index="zIndex" :custom-style="mask ? '' : 'background-color: transparent;'"></van-overlay>
<van-transition :show="show" :custom-style="'z-index: '+(zIndex)" custom-class="van-toast__container">
  <view :class="'van-toast van-toast--'+((type === 'text' || type === 'html') ? 'text' : 'icon')+' van-toast--'+(position)" @touchmove.stop.prevent="noop">
    
    <text v-if="type === 'text'">{{ message }}</text>

    
    <rich-text v-else-if="type === 'html'" :nodes="message"></rich-text>

    
    <block v-else>
      <van-loading v-if="type === 'loading'" color="white" :type="loadingType" custom-class="van-toast__loading"></van-loading>
      <van-icon v-else class="van-toast__icon" :name="type"></van-icon>
      <text v-if="message" class="van-toast__text">{{ message }}</text>
    </block>

    <slot></slot>
  </view>
</van-transition></uni-shadow-root>
</template>

<script>
import VanIcon from '../icon/index.vue'
import VanLoading from '../loading/index.vue'
import VanOverlay from '../overlay/index.vue'
import VanTransition from '../transition/index.vue'
global['__wxVueOptions'] = {components:{'van-icon': VanIcon,'van-loading': VanLoading,'van-overlay': VanOverlay,'van-transition': VanTransition}}

global['__wxRoute'] = 'vant/dist/toast/index'
import { VantComponent } from '../common/component';
VantComponent({
    props: {
        show: Boolean,
        mask: Boolean,
        message: String,
        forbidClick: Boolean,
        zIndex: {
            type: Number,
            value: 1000,
        },
        type: {
            type: String,
            value: 'text',
        },
        loadingType: {
            type: String,
            value: 'circular',
        },
        position: {
            type: String,
            value: 'middle',
        },
    },
    methods: {
        // for prevent touchmove
        noop() { },
    },
});
export default global['__wxComponents']['vant/dist/toast/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-toast{word-wrap:break-word;align-items:center;background-color:var(--toast-background-color,rgba(0,0,0,.7));border-radius:var(--toast-border-radius,8px);box-sizing:initial;color:var(--toast-text-color,#fff);display:flex;flex-direction:column;font-size:var(--toast-font-size,14px);justify-content:center;line-height:var(--toast-line-height,20px);white-space:pre-wrap}.van-toast__container{left:50%;max-width:var(--toast-max-width,70%);position:fixed;top:50%;transform:translate(-50%,-50%);width:-webkit-fit-content;width:fit-content}.van-toast--text{min-width:var(--toast-text-min-width,96px);padding:var(--toast-text-padding,8px 12px)}.van-toast--icon{min-height:var(--toast-default-min-height,88px);padding:var(--toast-default-padding,16px);width:var(--toast-default-width,88px)}.van-toast--icon .van-toast__icon{font-size:var(--toast-icon-size,36px)}.van-toast--icon .van-toast__text{padding-top:8px}.van-toast__loading{margin:10px 0}.van-toast--top{transform:translateY(-30vh)}.van-toast--bottom{transform:translateY(30vh)}
</style>