<template>
<uni-shadow-root class="vant-dist-tabbar-index"><view :class="(border ? 'van-hairline--top-bottom' : '')+' '+(utils.bem('tabbar', { fixed, safe: safeAreaInsetBottom }))+' custom-class'" :style="zIndex ? 'z-index: ' + zIndex : ''">
  <slot></slot>
</view>

<view v-if="fixed && placeholder" :style="'height: '+(height)+'px;'"></view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>

global['__wxRoute'] = 'vant/dist/tabbar/index'
import { VantComponent } from '../common/component';
import { useChildren } from '../common/relation';
import { getRect } from '../common/utils';
VantComponent({
    relation: useChildren('tabbar-item', function () {
        this.updateChildren();
    }),
    props: {
        active: {
            type: null,
            observer: 'updateChildren',
        },
        activeColor: {
            type: String,
            observer: 'updateChildren',
        },
        inactiveColor: {
            type: String,
            observer: 'updateChildren',
        },
        fixed: {
            type: Boolean,
            value: true,
            observer: 'setHeight',
        },
        placeholder: {
            type: Boolean,
            observer: 'setHeight',
        },
        border: {
            type: Boolean,
            value: true,
        },
        zIndex: {
            type: Number,
            value: 1,
        },
        safeAreaInsetBottom: {
            type: Boolean,
            value: true,
        },
    },
    data: {
        height: 50,
    },
    methods: {
        updateChildren() {
            const { children } = this;
            if (!Array.isArray(children) || !children.length) {
                return;
            }
            children.forEach((child) => child.updateFromParent());
        },
        setHeight() {
            if (!this.data.fixed || !this.data.placeholder) {
                return;
            }
            wx.nextTick(() => {
                getRect(this, '.van-tabbar').then((res) => {
                    this.setData({ height: res.height });
                });
            });
        },
    },
});
export default global['__wxComponents']['vant/dist/tabbar/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-tabbar{background-color:var(--tabbar-background-color,#fff);box-sizing:initial;display:flex;height:var(--tabbar-height,50px);width:100%}.van-tabbar--fixed{bottom:0;left:0;position:fixed}.van-tabbar--safe{padding-bottom:env(safe-area-inset-bottom)}
</style>