<template>
<uni-shadow-root class="vant-dist-popup-index"><van-overlay v-if="overlay" :show="show" :z-index="zIndex" :custom-style="overlayStyle" :duration="duration" @click="onClickOverlay" :lock-scroll="lockScroll" :root-portal="rootPortal"></van-overlay>

<root-portal v-if="rootPortal">
  <include src="./popup.wxml"></include>
</root-portal>

 <include v-else src="./popup.wxml"></include></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs><wxs src="./index.wxs" module="computed"></wxs>
<script>

const __wxTemplateComponentProps = {}
import __wxTemplateComponent0 from './popup.vue'

import VanIcon from '../icon/index.vue'
import VanOverlay from '../overlay/index.vue'
global['__wxVueOptions'] = {components:{'van-icon': VanIcon,'van-overlay': VanOverlay,}}

global['__wxRoute'] = 'vant/dist/popup/index'
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
        'close-icon-class',
    ],
    mixins: [transition(false)],
    props: {
        round: Boolean,
        closeable: Boolean,
        customStyle: String,
        overlayStyle: String,
        transition: {
            type: String,
            observer: 'observeClass',
        },
        zIndex: {
            type: Number,
            value: 100,
        },
        overlay: {
            type: Boolean,
            value: true,
        },
        closeIcon: {
            type: String,
            value: 'cross',
        },
        closeIconPosition: {
            type: String,
            value: 'top-right',
        },
        closeOnClickOverlay: {
            type: Boolean,
            value: true,
        },
        position: {
            type: String,
            value: 'center',
            observer: 'observeClass',
        },
        safeAreaInsetBottom: {
            type: Boolean,
            value: true,
        },
        safeAreaInsetTop: {
            type: Boolean,
            value: false,
        },
        safeAreaTabBar: {
            type: Boolean,
            value: false,
        },
        lockScroll: {
            type: Boolean,
            value: true,
        },
        rootPortal: {
            type: Boolean,
            value: false,
        },
    },
    created() {
        this.observeClass();
    },
    methods: {
        onClickCloseIcon() {
            this.$emit('close');
        },
        onClickOverlay() {
            this.$emit('click-overlay');
            if (this.data.closeOnClickOverlay) {
                this.$emit('close');
            }
        },
        observeClass() {
            const { transition, position, duration } = this.data;
            const updateData = {
                name: transition || position,
            };
            if (transition === 'none') {
                updateData.duration = 0;
                this.originDuration = duration;
            }
            else if (this.originDuration != null) {
                updateData.duration = this.originDuration;
            }
            this.setData(updateData);
        },
    },
});
export default global['__wxComponents']['vant/dist/popup/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-popup{-webkit-overflow-scrolling:touch;animation:ease both;background-color:var(--popup-background-color,#fff);box-sizing:border-box;max-height:100%;overflow-y:auto;position:fixed;transition-timing-function:ease}.van-popup--center{left:50%;top:50%;transform:translate3d(-50%,-50%,0)}.van-popup--center.van-popup--round{border-radius:var(--popup-round-border-radius,16px)}.van-popup--top{left:0;top:0;width:100%}.van-popup--top.van-popup--round{border-radius:0 0 var(--popup-round-border-radius,var(--popup-round-border-radius,16px)) var(--popup-round-border-radius,var(--popup-round-border-radius,16px))}.van-popup--right{right:0;top:50%;transform:translate3d(0,-50%,0)}.van-popup--right.van-popup--round{border-radius:var(--popup-round-border-radius,var(--popup-round-border-radius,16px)) 0 0 var(--popup-round-border-radius,var(--popup-round-border-radius,16px))}.van-popup--bottom{bottom:0;left:0;width:100%}.van-popup--bottom.van-popup--round{border-radius:var(--popup-round-border-radius,var(--popup-round-border-radius,16px)) var(--popup-round-border-radius,var(--popup-round-border-radius,16px)) 0 0}.van-popup--left{left:0;top:50%;transform:translate3d(0,-50%,0)}.van-popup--left.van-popup--round{border-radius:0 var(--popup-round-border-radius,var(--popup-round-border-radius,16px)) var(--popup-round-border-radius,var(--popup-round-border-radius,16px)) 0}.van-popup--bottom.van-popup--safe{padding-bottom:env(safe-area-inset-bottom)}.van-popup--bottom.van-popup--safeTabBar,.van-popup--top.van-popup--safeTabBar{bottom:var(--tabbar-height,50px)}.van-popup--safeTop{padding-top:env(safe-area-inset-top)}.van-popup__close-icon{color:var(--popup-close-icon-color,#969799);font-size:var(--popup-close-icon-size,18px);position:absolute;z-index:var(--popup-close-icon-z-index,1)}.van-popup__close-icon--top-left{left:var(--popup-close-icon-margin,16px);top:var(--popup-close-icon-margin,16px)}.van-popup__close-icon--top-right{right:var(--popup-close-icon-margin,16px);top:var(--popup-close-icon-margin,16px)}.van-popup__close-icon--bottom-left{bottom:var(--popup-close-icon-margin,16px);left:var(--popup-close-icon-margin,16px)}.van-popup__close-icon--bottom-right{bottom:var(--popup-close-icon-margin,16px);right:var(--popup-close-icon-margin,16px)}.van-popup__close-icon:active{opacity:.6}.van-scale-enter-active,.van-scale-leave-active{transition-property:opacity,transform}.van-scale-enter,.van-scale-leave-to{opacity:0;transform:translate3d(-50%,-50%,0) scale(.7)}.van-fade-enter-active,.van-fade-leave-active{transition-property:opacity}.van-fade-enter,.van-fade-leave-to{opacity:0}.van-center-enter-active,.van-center-leave-active{transition-property:opacity}.van-center-enter,.van-center-leave-to{opacity:0}.van-bottom-enter-active,.van-bottom-leave-active,.van-left-enter-active,.van-left-leave-active,.van-right-enter-active,.van-right-leave-active,.van-top-enter-active,.van-top-leave-active{transition-property:transform}.van-bottom-enter,.van-bottom-leave-to{transform:translate3d(0,100%,0)}.van-top-enter,.van-top-leave-to{transform:translate3d(0,-100%,0)}.van-left-enter,.van-left-leave-to{transform:translate3d(-100%,-50%,0)}.van-right-enter,.van-right-leave-to{transform:translate3d(100%,-50%,0)}
</style>