<template>
<uni-shadow-root class="vant-dist-collapse-item-index"><view :class="'van-collapse-item custom-class '+(index !== 0 ? 'van-hairline--top' : '')">
  <van-cell :size="size" :title="title" title-class="title-class" :icon="icon" :value="value" :label="label" :is-link="isLink" :clickable="clickable" :border="border && expanded" :class="utils.bem('collapse-item__title', { disabled, expanded })" right-icon-class="van-cell__right-icon" custom-class="van-cell" hover-class="van-cell--hover" @click="onClick">
    <slot name="title" slot="title"></slot>
    <slot name="icon" slot="icon"></slot>
    <slot name="value"></slot>
    <slot name="right-icon" slot="right-icon"></slot>
  </van-cell>
  <view :class="utils.bem('collapse-item__wrapper')" style="height: 0;" :animation="animation">
    <view class="van-collapse-item__content content-class">
      <slot></slot>
    </view>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>
import VanCell from '../cell/index.vue'
global['__wxVueOptions'] = {components:{'van-cell': VanCell}}

global['__wxRoute'] = 'vant/dist/collapse-item/index'
import { VantComponent } from '../common/component';
import { useParent } from '../common/relation';
import { setContentAnimate } from './animate';
VantComponent({
    classes: ['title-class', 'content-class'],
    relation: useParent('collapse'),
    props: {
        size: String,
        name: null,
        title: null,
        value: null,
        icon: String,
        label: String,
        disabled: Boolean,
        clickable: Boolean,
        border: {
            type: Boolean,
            value: true,
        },
        isLink: {
            type: Boolean,
            value: true,
        },
    },
    data: {
        expanded: false,
    },
    mounted() {
        this.updateExpanded();
        this.mounted = true;
    },
    methods: {
        updateExpanded() {
            if (!this.parent) {
                return;
            }
            const { value, accordion } = this.parent.data;
            const { children = [] } = this.parent;
            const { name } = this.data;
            const index = children.indexOf(this);
            const currentName = name == null ? index : name;
            const expanded = accordion
                ? value === currentName
                : (value || []).some((name) => name === currentName);
            if (expanded !== this.data.expanded) {
                setContentAnimate(this, expanded, this.mounted);
            }
            this.setData({ index, expanded });
        },
        onClick() {
            if (this.data.disabled) {
                return;
            }
            const { name, expanded } = this.data;
            const index = this.parent.children.indexOf(this);
            const currentName = name == null ? index : name;
            this.parent.switch(currentName, !expanded);
        },
    },
});
export default global['__wxComponents']['vant/dist/collapse-item/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-collapse-item__title .van-cell__right-icon{transform:rotate(90deg);transition:transform var(--collapse-item-transition-duration,.3s)}.van-collapse-item__title--expanded .van-cell__right-icon{transform:rotate(-90deg)}.van-collapse-item__title--disabled .van-cell,.van-collapse-item__title--disabled .van-cell__right-icon{color:var(--collapse-item-title-disabled-color,#c8c9cc)!important}.van-collapse-item__title--disabled .van-cell--hover{background-color:#fff!important}.van-collapse-item__wrapper{overflow:hidden}.van-collapse-item__content{background-color:var(--collapse-item-content-background-color,#fff);color:var(--collapse-item-content-text-color,#969799);font-size:var(--collapse-item-content-font-size,13px);line-height:var(--collapse-item-content-line-height,1.5);padding:var(--collapse-item-content-padding,15px)}
</style>