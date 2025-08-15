<template>
<uni-shadow-root class="vant-tabbar-item-index"><view :class="(utils.bem('tabbar-item', { active }))+' custom-class'" :style="'color: '+(active ? activeColor : inactiveColor)" @click="onClick">
  <view class="van-tabbar-item__icon">
    <van-icon v-if="icon" :name="icon" custom-class="van-tabbar-item__icon__inner"></van-icon>
    <block v-else>
      <slot v-if="active" name="icon-active"></slot>
      <slot v-else name="icon"></slot>
    </block>
    <van-info :dot="dot" :info="info" custom-class="van-tabbar-item__info"></van-info>
  </view>
  <view class="van-tabbar-item__text">
    <slot></slot>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>
import VanIcon from '../icon/index.vue'
import VanInfo from '../info/index.vue'
global['__wxVueOptions'] = {components:{'van-icon': VanIcon,'van-info': VanInfo}}

global['__wxRoute'] = 'vant/tabbar-item/index'
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var component_1 = require("../common/component");
component_1.VantComponent({
    props: {
        info: null,
        name: null,
        icon: String,
        dot: Boolean
    },
    relation: {
        name: 'tabbar',
        type: 'ancestor',
        current: 'tabbar-item',
    },
    data: {
        active: false
    },
    methods: {
        onClick: function () {
            if (this.parent) {
                this.parent.onChange(this);
            }
            this.$emit('click');
        },
        updateFromParent: function () {
            var parent = this.parent;
            if (!parent) {
                return;
            }
            var index = parent.children.indexOf(this);
            var parentData = parent.data;
            var data = this.data;
            var active = (data.name || index) === parentData.active;
            var patch = {};
            if (active !== data.active) {
                patch.active = active;
            }
            if (parentData.activeColor !== data.activeColor) {
                patch.activeColor = parentData.activeColor;
            }
            if (parentData.inactiveColor !== data.inactiveColor) {
                patch.inactiveColor = parentData.inactiveColor;
            }
            return Object.keys(patch).length > 0
                ? this.set(patch)
                : Promise.resolve();
        }
    }
});
export default global['__wxComponents']['vant/tabbar-item/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.vant-tabbar-item-index{-webkit-flex:1;flex:1}.van-tabbar-item{display:-webkit-flex;display:flex;-webkit-flex-direction:column;flex-direction:column;-webkit-align-items:center;align-items:center;-webkit-justify-content:center;justify-content:center;height:100%;color:#646566;color:var(--tabbar-item-text-color,#646566);font-size:12px;font-size:var(--tabbar-item-font-size,12px);line-height:1;line-height:var(--tabbar-item-line-height,1)}.van-tabbar-item__icon{position:relative;margin-bottom:5px;margin-bottom:var(--tabbar-item-margin-bottom,5px);font-size:18px;font-size:var(--tabbar-item-icon-size,18px)}.van-tabbar-item__icon__inner{display:block;min-width:1em}.van-tabbar-item--active{color:#1989fa;color:var(--tabbar-item-active-color,#1989fa)}.van-tabbar-item__info{margin-top:2px}
</style>