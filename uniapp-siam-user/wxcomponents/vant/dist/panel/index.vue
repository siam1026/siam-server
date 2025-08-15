<template>
<uni-shadow-root class="vant-dist-panel-index"><view class="van-panel van-hairline--top-bottom custom-class">
  <van-cell v-if="title || desc || status" :title="title" :label="desc" :value="status" custom-class="header-class" value-class="van-panel__header-value"></van-cell>
  <slot v-else name="header"></slot>

  <view class="van-panel__content">
    <slot></slot>
  </view>

  <view class="van-panel__footer van-hairline--top footer-class">
    <slot name="footer"></slot>
  </view>
</view></uni-shadow-root>
</template>

<script>
import VanCell from '../cell/index.vue'
global['__wxVueOptions'] = {components:{'van-cell': VanCell}}

global['__wxRoute'] = 'vant/dist/panel/index'
import { VantComponent } from '../common/component';
VantComponent({
    classes: ['header-class', 'footer-class'],
    props: {
        desc: String,
        title: String,
        status: String,
    },
});
export default global['__wxComponents']['vant/dist/panel/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-panel{background:var(--panel-background-color,#fff)}.van-panel__header-value{color:var(--panel-header-value-color,#ee0a24)}.van-panel__footer{padding:var(--panel-footer-padding,8px 16px)}.van-panel__footer:empty{display:none}
</style>