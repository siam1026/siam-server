<template>
<uni-shadow-root class="vant-dist-tag-index"><view :class="'custom-class '+(utils.bem('tag', [type, size, { mark, plain, round }]))" :style="computed.rootStyle({ plain, color, textColor })">
  <slot></slot>
  <van-icon v-if="closeable" name="cross" custom-class="van-tag__close" @click="onClose"></van-icon>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs><wxs src="./index.wxs" module="computed"></wxs>
<script>
import VanIcon from '../icon/index.vue'
global['__wxVueOptions'] = {components:{'van-icon': VanIcon}}

global['__wxRoute'] = 'vant/dist/tag/index'
import { VantComponent } from '../common/component';
VantComponent({
    props: {
        size: String,
        mark: Boolean,
        color: String,
        plain: Boolean,
        round: Boolean,
        textColor: String,
        type: {
            type: String,
            value: 'default',
        },
        closeable: Boolean,
    },
    methods: {
        onClose() {
            this.$emit('close');
        },
    },
});
export default global['__wxComponents']['vant/dist/tag/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-tag{align-items:center;border-radius:var(--tag-border-radius,2px);color:var(--tag-text-color,#fff);display:inline-flex;font-size:var(--tag-font-size,12px);line-height:var(--tag-line-height,16px);padding:var(--tag-padding,0 4px);position:relative}.van-tag--default{background-color:var(--tag-default-color,#969799)}.van-tag--default.van-tag--plain{color:var(--tag-default-color,#969799)}.van-tag--danger{background-color:var(--tag-danger-color,#ee0a24)}.van-tag--danger.van-tag--plain{color:var(--tag-danger-color,#ee0a24)}.van-tag--primary{background-color:var(--tag-primary-color,#1989fa)}.van-tag--primary.van-tag--plain{color:var(--tag-primary-color,#1989fa)}.van-tag--success{background-color:var(--tag-success-color,#07c160)}.van-tag--success.van-tag--plain{color:var(--tag-success-color,#07c160)}.van-tag--warning{background-color:var(--tag-warning-color,#ff976a)}.van-tag--warning.van-tag--plain{color:var(--tag-warning-color,#ff976a)}.van-tag--plain{background-color:var(--tag-plain-background-color,#fff)}.van-tag--plain:before{border:1px solid;border-radius:inherit;bottom:0;content:"";left:0;pointer-events:none;position:absolute;right:0;top:0}.van-tag--medium{padding:var(--tag-medium-padding,2px 6px)}.van-tag--large{border-radius:var(--tag-large-border-radius,4px);font-size:var(--tag-large-font-size,14px);padding:var(--tag-large-padding,4px 8px)}.van-tag--mark{border-radius:0 var(--tag-round-border-radius,var(--tag-round-border-radius,999px)) var(--tag-round-border-radius,var(--tag-round-border-radius,999px)) 0}.van-tag--mark:after{content:"";display:block;width:2px}.van-tag--round{border-radius:var(--tag-round-border-radius,999px)}.van-tag__close{margin-left:2px;min-width:1em}
</style>