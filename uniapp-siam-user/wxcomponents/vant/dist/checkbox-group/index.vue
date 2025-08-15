<template>
<uni-shadow-root class="vant-dist-checkbox-group-index"><view :class="utils.bem('checkbox-group', [{ horizontal: direction === 'horizontal' }])">
  <slot></slot>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>

global['__wxRoute'] = 'vant/dist/checkbox-group/index'
import { useChildren } from '../common/relation';
import { VantComponent } from '../common/component';
VantComponent({
    field: true,
    relation: useChildren('checkbox', function (target) {
        this.updateChild(target);
    }),
    props: {
        max: Number,
        value: {
            type: Array,
            observer: 'updateChildren',
        },
        disabled: {
            type: Boolean,
            observer: 'updateChildren',
        },
        direction: {
            type: String,
            value: 'vertical',
        },
    },
    methods: {
        updateChildren() {
            this.children.forEach((child) => this.updateChild(child));
        },
        updateChild(child) {
            const { value, disabled, direction } = this.data;
            child.setData({
                value: value.indexOf(child.data.name) !== -1,
                parentDisabled: disabled,
                direction,
            });
        },
    },
});
export default global['__wxComponents']['vant/dist/checkbox-group/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-checkbox-group--horizontal{display:flex;flex-wrap:wrap}
</style>