<template>
<uni-shadow-root class="vant-dist-radio-group-index"><view :class="utils.bem('radio-group', [direction])">
  <slot></slot>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>

global['__wxRoute'] = 'vant/dist/radio-group/index'
import { VantComponent } from '../common/component';
import { useChildren } from '../common/relation';
VantComponent({
    field: true,
    relation: useChildren('radio'),
    props: {
        value: {
            type: null,
            observer: 'updateChildren',
        },
        direction: String,
        disabled: {
            type: Boolean,
            observer: 'updateChildren',
        },
    },
    methods: {
        updateChildren() {
            this.children.forEach((child) => child.updateFromParent());
        },
    },
});
export default global['__wxComponents']['vant/dist/radio-group/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-radio-group--horizontal{display:flex;flex-wrap:wrap}
</style>