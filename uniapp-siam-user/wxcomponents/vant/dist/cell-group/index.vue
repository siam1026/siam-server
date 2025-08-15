<template>
<uni-shadow-root class="vant-dist-cell-group-index"><view v-if="title" :class="utils.bem('cell-group__title', { inset })">
  {{ title }}
</view>
<view :class="'custom-class '+(utils.bem('cell-group', { inset }))+' '+(border ? 'van-hairline--top-bottom' : '')">
  <slot></slot>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>

global['__wxRoute'] = 'vant/dist/cell-group/index'
import { VantComponent } from '../common/component';
VantComponent({
    props: {
        title: String,
        border: {
            type: Boolean,
            value: true,
        },
        inset: Boolean,
    },
});
export default global['__wxComponents']['vant/dist/cell-group/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-cell-group--inset{border-radius:var(--cell-group-inset-border-radius,8px);margin:var(--cell-group-inset-padding,0 16px);overflow:hidden}.van-cell-group__title{color:var(--cell-group-title-color,#969799);font-size:var(--cell-group-title-font-size,14px);line-height:var(--cell-group-title-line-height,16px);padding:var(--cell-group-title-padding,16px 16px 8px)}.van-cell-group__title--inset{padding:var(--cell-group-inset-title-padding,16px 16px 8px 32px)}
</style>