<template>
<uni-shadow-root class="vant-dist-index-anchor-index"><view class="van-index-anchor-wrapper" :style="wrapperStyle">
  <view :class="'van-index-anchor '+(active ? 'van-index-anchor--active van-hairline--bottom' : '')" :style="anchorStyle">
    <slot v-if="useSlot"></slot>
    <block v-else>
      <text>{{ index }}</text>
    </block>
  </view>
</view></uni-shadow-root>
</template>

<script>

global['__wxRoute'] = 'vant/dist/index-anchor/index'
import { getRect } from '../common/utils';
import { VantComponent } from '../common/component';
import { useParent } from '../common/relation';
VantComponent({
    relation: useParent('index-bar'),
    props: {
        useSlot: Boolean,
        index: null,
    },
    data: {
        active: false,
        wrapperStyle: '',
        anchorStyle: '',
    },
    methods: {
        scrollIntoView(scrollTop) {
            getRect(this, '.van-index-anchor-wrapper').then((rect) => {
                wx.pageScrollTo({
                    duration: 0,
                    scrollTop: scrollTop + rect.top - this.parent.data.stickyOffsetTop,
                });
            });
        },
    },
});
export default global['__wxComponents']['vant/dist/index-anchor/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-index-anchor{background-color:var(--index-anchor-background-color,transparent);color:var(--index-anchor-text-color,#323233);font-size:var(--index-anchor-font-size,14px);font-weight:var(--index-anchor-font-weight,500);line-height:var(--index-anchor-line-height,32px);padding:var(--index-anchor-padding,0 16px)}.van-index-anchor--active{background-color:var(--index-anchor-active-background-color,#fff);color:var(--index-anchor-active-text-color,#07c160);left:0;right:0}
</style>