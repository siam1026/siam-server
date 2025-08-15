<template>
<uni-shadow-root class="dist-vant-row-index"><view class="van-row custom-class" :style="computed.rootStyle({ gutter })">
  <slot></slot>
</view></uni-shadow-root>
</template>
<wxs src="./index.wxs" module="computed"></wxs>
<script>

global['__wxRoute'] = 'dist/vant/row/index'
import { VantComponent } from '../common/component';
import { useChildren } from '../common/relation';
VantComponent({
    relation: useChildren('col', function (target) {
        const { gutter } = this.data;
        if (gutter) {
            target.setData({ gutter });
        }
    }),
    props: {
        gutter: {
            type: Number,
            observer: 'setGutter',
        },
    },
    methods: {
        setGutter() {
            this.children.forEach((col) => {
                col.setData(this.data);
            });
        },
    },
});
export default global['__wxComponents']['dist/vant/row/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-row:after{clear:both;content:"";display:table}
</style>