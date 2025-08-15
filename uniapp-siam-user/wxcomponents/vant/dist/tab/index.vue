<template>
<uni-shadow-root class="vant-dist-tab-index"><view :class="'custom-class '+(utils.bem('tab__pane', { active, inactive: !active }))" :style="shouldShow ? '' : 'display: none;'">
  <slot v-if="shouldRender"></slot>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>

global['__wxRoute'] = 'vant/dist/tab/index'
import { useParent } from '../common/relation';
import { VantComponent } from '../common/component';
VantComponent({
    relation: useParent('tabs'),
    props: {
        dot: {
            type: Boolean,
            observer: 'update',
        },
        info: {
            type: null,
            observer: 'update',
        },
        title: {
            type: String,
            observer: 'update',
        },
        disabled: {
            type: Boolean,
            observer: 'update',
        },
        titleStyle: {
            type: String,
            observer: 'update',
        },
        name: {
            type: null,
            value: '',
        },
    },
    data: {
        active: false,
    },
    methods: {
        getComputedName() {
            if (this.data.name !== '') {
                return this.data.name;
            }
            return this.index;
        },
        updateRender(active, parent) {
            const { data: parentData } = parent;
            this.inited = this.inited || active;
            this.setData({
                active,
                shouldRender: this.inited || !parentData.lazyRender,
                shouldShow: active || parentData.animated,
            });
        },
        update() {
            if (this.parent) {
                this.parent.updateTabs();
            }
        },
    },
});
export default global['__wxComponents']['vant/dist/tab/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.vant-dist-tab-index{box-sizing:border-box;flex-shrink:0;width:100%}.van-tab__pane{-webkit-overflow-scrolling:touch;box-sizing:border-box;overflow-y:auto}.van-tab__pane--active{height:auto}.van-tab__pane--inactive{height:0;overflow:visible}
</style>