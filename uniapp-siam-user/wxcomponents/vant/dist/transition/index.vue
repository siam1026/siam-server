<template>
<uni-shadow-root class="vant-dist-transition-index"><view v-if="inited" :class="'van-transition custom-class '+(classes)" :style="computed.rootStyle({ currentDuration, display, customStyle })" @transitionend="onTransitionEnd">
  <slot></slot>
</view></uni-shadow-root>
</template>
<wxs src="./index.wxs" module="computed"></wxs>
<script>

global['__wxRoute'] = 'vant/dist/transition/index'
import { VantComponent } from '../common/component';
import { transition } from '../mixins/transition';
VantComponent({
    classes: [
        'enter-class',
        'enter-active-class',
        'enter-to-class',
        'leave-class',
        'leave-active-class',
        'leave-to-class',
    ],
    mixins: [transition(true)],
});
export default global['__wxComponents']['vant/dist/transition/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-transition{transition-timing-function:ease}.van-fade-enter-active,.van-fade-leave-active{transition-property:opacity}.van-fade-enter,.van-fade-leave-to{opacity:0}.van-fade-down-enter-active,.van-fade-down-leave-active,.van-fade-left-enter-active,.van-fade-left-leave-active,.van-fade-right-enter-active,.van-fade-right-leave-active,.van-fade-up-enter-active,.van-fade-up-leave-active{transition-property:opacity,transform}.van-fade-up-enter,.van-fade-up-leave-to{opacity:0;transform:translate3d(0,100%,0)}.van-fade-down-enter,.van-fade-down-leave-to{opacity:0;transform:translate3d(0,-100%,0)}.van-fade-left-enter,.van-fade-left-leave-to{opacity:0;transform:translate3d(-100%,0,0)}.van-fade-right-enter,.van-fade-right-leave-to{opacity:0;transform:translate3d(100%,0,0)}.van-slide-down-enter-active,.van-slide-down-leave-active,.van-slide-left-enter-active,.van-slide-left-leave-active,.van-slide-right-enter-active,.van-slide-right-leave-active,.van-slide-up-enter-active,.van-slide-up-leave-active{transition-property:transform}.van-slide-up-enter,.van-slide-up-leave-to{transform:translate3d(0,100%,0)}.van-slide-down-enter,.van-slide-down-leave-to{transform:translate3d(0,-100%,0)}.van-slide-left-enter,.van-slide-left-leave-to{transform:translate3d(-100%,0,0)}.van-slide-right-enter,.van-slide-right-leave-to{transform:translate3d(100%,0,0)}
</style>