<template>
<uni-shadow-root class="vant-collapse-item-index"><view :class="'van-collapse-item custom-class '+(index !== 0 ? 'van-hairline--top' : '')">
  <van-cell :title="title" title-class="title-class" :icon="icon" :value="value" :label="label" :is-link="isLink" :clickable="clickable" :border="border && expanded" :class="utils.bem('collapse-item__title', { disabled, expanded })" right-icon-class="van-cell__right-icon" custom-class="van-cell" hover-class="van-cell--hover" @click="onClick">
    <slot name="title" slot="title"></slot>
    <slot name="icon" slot="icon"></slot>
    <slot name="value"></slot>
    <slot name="right-icon" slot="right-icon"></slot>
  </van-cell>
  <view :class="utils.bem('collapse-item__wrapper', { transition })" :style="'height: '+(contentHeight)+';'" @transitionend="onTransitionEnd">
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

global['__wxRoute'] = 'vant/collapse-item/index'
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var component_1 = require("../common/component");
var nextTick = function () { return new Promise(function (resolve) { return setTimeout(resolve, 20); }); };
component_1.VantComponent({
    classes: ['title-class', 'content-class'],
    relation: {
        name: 'collapse',
        type: 'ancestor',
        current: 'collapse-item',
    },
    props: {
        name: null,
        title: null,
        value: null,
        icon: String,
        label: String,
        disabled: Boolean,
        clickable: Boolean,
        border: {
            type: Boolean,
            value: true
        },
        isLink: {
            type: Boolean,
            value: true
        }
    },
    data: {
        contentHeight: 0,
        expanded: false,
        transition: false
    },
    mounted: function () {
        var _this = this;
        this.updateExpanded()
            .then(nextTick)
            .then(function () {
            var data = { transition: true };
            if (_this.data.expanded) {
                data.contentHeight = 'auto';
            }
            _this.setData(data);
        });
    },
    methods: {
        updateExpanded: function () {
            if (!this.parent) {
                return Promise.resolve();
            }
            var _a = this.parent.data, value = _a.value, accordion = _a.accordion;
            var _b = this.parent.children, children = _b === void 0 ? [] : _b;
            var name = this.data.name;
            var index = children.indexOf(this);
            var currentName = name == null ? index : name;
            var expanded = accordion
                ? value === currentName
                : (value || []).some(function (name) { return name === currentName; });
            var stack = [];
            if (expanded !== this.data.expanded) {
                stack.push(this.updateStyle(expanded));
            }
            stack.push(this.set({ index: index, expanded: expanded }));
            return Promise.all(stack);
        },
        updateStyle: function (expanded) {
            var _this = this;
            return this.getRect('.van-collapse-item__content')
                .then(function (rect) { return rect.height; })
                .then(function (height) {
                if (expanded) {
                    return _this.set({
                        contentHeight: height ? height + "px" : 'auto'
                    });
                }
                return _this.set({ contentHeight: height + "px" })
                    .then(nextTick)
                    .then(function () { return _this.set({ contentHeight: 0 }); });
            });
        },
        onClick: function () {
            if (this.data.disabled) {
                return;
            }
            var _a = this.data, name = _a.name, expanded = _a.expanded;
            var index = this.parent.children.indexOf(this);
            var currentName = name == null ? index : name;
            this.parent.switch(currentName, !expanded);
        },
        onTransitionEnd: function () {
            if (this.data.expanded) {
                this.setData({
                    contentHeight: 'auto'
                });
            }
        }
    }
});
export default global['__wxComponents']['vant/collapse-item/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-collapse-item__title .van-cell__right-icon{-webkit-transform:rotate(90deg);transform:rotate(90deg);transition:-webkit-transform .3s;transition:transform .3s;transition:transform .3s,-webkit-transform .3s;transition:-webkit-transform var(--collapse-item-transition-duration,.3s);transition:transform var(--collapse-item-transition-duration,.3s);transition:transform var(--collapse-item-transition-duration,.3s),-webkit-transform var(--collapse-item-transition-duration,.3s)}.van-collapse-item__title--expanded .van-cell__right-icon{-webkit-transform:rotate(-90deg);transform:rotate(-90deg)}.van-collapse-item__title--disabled .van-cell,.van-collapse-item__title--disabled .van-cell__right-icon{color:#c8c9cc!important;color:var(--collapse-item-title-disabled-color,#c8c9cc)!important}.van-collapse-item__title--disabled .van-cell--hover{background-color:#fff!important;background-color:var(--white,#fff)!important}.van-collapse-item__wrapper{overflow:hidden}.van-collapse-item__wrapper--transition{transition:height .3s ease-in-out}.van-collapse-item__content{padding:15px;padding:var(--collapse-item-content-padding,15px);color:#969799;color:var(--collapse-item-content-text-color,#969799);font-size:13px;font-size:var(--collapse-item-content-font-size,13px);line-height:1.5;line-height:var(--collapse-item-content-line-height,1.5);background-color:#fff;background-color:var(--collapse-item-content-background-color,#fff)}
</style>