<template>
<uni-shadow-root class="vant-dist-goods-action-index"><view :class="'custom-class '+(utils.bem('goods-action', { safe: safeAreaInsetBottom }))">
  <slot></slot>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>

global['__wxRoute'] = 'vant/dist/goods-action/index'
import { VantComponent } from '../common/component';
import { useChildren } from '../common/relation';
VantComponent({
    relation: useChildren('goods-action-button', function () {
        this.children.forEach((item) => {
            item.updateStyle();
        });
    }),
    props: {
        safeAreaInsetBottom: {
            type: Boolean,
            value: true,
        },
    },
});
export default global['__wxComponents']['vant/dist/goods-action/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-goods-action{align-items:center;background-color:var(--goods-action-background-color,#fff);bottom:0;box-sizing:initial;display:flex;height:var(--goods-action-height,50px);left:0;position:fixed;right:0}.van-goods-action--safe{padding-bottom:env(safe-area-inset-bottom)}
</style>