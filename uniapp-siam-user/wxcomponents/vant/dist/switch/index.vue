<template>
<uni-shadow-root class="vant-dist-switch-index"><view :class="(utils.bem('switch', { on: checked === activeValue, disabled }))+' custom-class'" :style="computed.rootStyle({ size, checked, activeColor, inactiveColor, activeValue })" @click="onClick">
  <view class="van-switch__node node-class">
    <van-loading v-if="loading" :color="computed.loadingColor({ checked, activeColor, inactiveColor, activeValue })" custom-class="van-switch__loading"></van-loading>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs><wxs src="./index.wxs" module="computed"></wxs>
<script>
import VanLoading from '../loading/index.vue'
global['__wxVueOptions'] = {components:{'van-loading': VanLoading}}

global['__wxRoute'] = 'vant/dist/switch/index'
import { VantComponent } from '../common/component';
VantComponent({
    field: true,
    classes: ['node-class'],
    props: {
        checked: null,
        loading: Boolean,
        disabled: Boolean,
        activeColor: String,
        inactiveColor: String,
        size: {
            type: String,
            value: '30',
        },
        activeValue: {
            type: null,
            value: true,
        },
        inactiveValue: {
            type: null,
            value: false,
        },
    },
    methods: {
        onClick() {
            const { activeValue, inactiveValue, disabled, loading } = this.data;
            if (disabled || loading) {
                return;
            }
            const checked = this.data.checked === activeValue;
            const value = checked ? inactiveValue : activeValue;
            this.$emit('input', value);
            this.$emit('change', value);
        },
    },
});
export default global['__wxComponents']['vant/dist/switch/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-switch{background-color:var(--switch-background-color,#fff);border:var(--switch-border,1px solid rgba(0,0,0,.1));border-radius:var(--switch-node-size,1em);box-sizing:initial;display:inline-block;height:var(--switch-height,1em);position:relative;transition:background-color var(--switch-transition-duration,.3s);width:var(--switch-width,2em)}.van-switch__node{background-color:var(--switch-node-background-color,#fff);border-radius:100%;box-shadow:var(--switch-node-box-shadow,0 3px 1px 0 rgba(0,0,0,.05),0 2px 2px 0 rgba(0,0,0,.1),0 3px 3px 0 rgba(0,0,0,.05));height:var(--switch-node-size,1em);left:0;position:absolute;top:0;transition:var(--switch-transition-duration,.3s) cubic-bezier(.3,1.05,.4,1.05);width:var(--switch-node-size,1em);z-index:var(--switch-node-z-index,1)}.van-switch__loading{height:50%;left:25%;position:absolute!important;top:25%;width:50%}.van-switch--on{background-color:var(--switch-on-background-color,#1989fa)}.van-switch--on .van-switch__node{transform:translateX(calc(var(--switch-width, 2em) - var(--switch-node-size, 1em)))}.van-switch--disabled{opacity:var(--switch-disabled-opacity,.4)}
</style>