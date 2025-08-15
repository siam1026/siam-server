<template>
<uni-shadow-root class="vant-dropdown-menu-index"><view class="van-dropdown-menu van-dropdown-menu--top-bottom">
  <view v-for="(item,index) in (itemListData)" :key="item.index" :data-index="index" :class="utils.bem('dropdown-menu__item', { disabled: item.disabled })" @click="onTitleTap">
    <view :class="(item.titleClass)+' '+(utils.bem('dropdown-menu__title', { active: item.showPopup, down: item.showPopup === (direction === 'down') }))" :style="item.showPopup ? 'color:' + activeColor : ''">
      <view class="van-ellipsis">
        {{ computed.displayTitle(item) }}
      </view>
    </view>
  </view>

  <slot></slot>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs><wxs src="./index.wxs" module="computed"></wxs>
<script>

global['__wxRoute'] = 'vant/dropdown-menu/index'
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var component_1 = require("../common/component");
var utils_1 = require("../common/utils");
var ARRAY = [];
component_1.VantComponent({
    field: true,
    relation: {
        name: 'dropdown-item',
        type: 'descendant',
        current: 'dropdown-menu',
        linked: function () {
            this.updateItemListData();
        },
        unlinked: function () {
            this.updateItemListData();
        }
    },
    props: {
        activeColor: {
            type: String,
            observer: 'updateChildrenData'
        },
        overlay: {
            type: Boolean,
            value: true,
            observer: 'updateChildrenData'
        },
        zIndex: {
            type: Number,
            value: 10
        },
        duration: {
            type: Number,
            value: 200,
            observer: 'updateChildrenData'
        },
        direction: {
            type: String,
            value: 'down',
            observer: 'updateChildrenData'
        },
        closeOnClickOverlay: {
            type: Boolean,
            value: true,
            observer: 'updateChildrenData'
        },
        closeOnClickOutside: {
            type: Boolean,
            value: true
        }
    },
    data: {
        itemListData: []
    },
    beforeCreate: function () {
        var windowHeight = wx.getSystemInfoSync().windowHeight;
        this.windowHeight = windowHeight;
        ARRAY.push(this);
    },
    destroyed: function () {
        var _this = this;
        ARRAY = ARRAY.filter(function (item) { return item !== _this; });
    },
    methods: {
        updateItemListData: function () {
            this.setData({
                itemListData: this.children.map(function (child) { return child.data; })
            });
        },
        updateChildrenData: function () {
            this.children.forEach(function (child) {
                child.updateDataFromParent();
            });
        },
        toggleItem: function (active) {
            this.children.forEach(function (item, index) {
                var showPopup = item.data.showPopup;
                if (index === active) {
                    item.toggle();
                }
                else if (showPopup) {
                    item.toggle(false, { immediate: true });
                }
            });
        },
        close: function () {
            this.children.forEach(function (child) {
                child.toggle(false, { immediate: true });
            });
        },
        getChildWrapperStyle: function () {
            var _this = this;
            var _a = this.data, zIndex = _a.zIndex, direction = _a.direction;
            return this.getRect('.van-dropdown-menu').then(function (rect) {
                var _a = rect.top, top = _a === void 0 ? 0 : _a, _b = rect.bottom, bottom = _b === void 0 ? 0 : _b;
                var offset = direction === 'down' ? bottom : _this.windowHeight - top;
                var wrapperStyle = "z-index: " + zIndex + ";";
                if (direction === 'down') {
                    wrapperStyle += "top: " + utils_1.addUnit(offset) + ";";
                }
                else {
                    wrapperStyle += "bottom: " + utils_1.addUnit(offset) + ";";
                }
                return wrapperStyle;
            });
        },
        onTitleTap: function (event) {
            var _this = this;
            var index = event.currentTarget.dataset.index;
            var child = this.children[index];
            if (!child.data.disabled) {
                ARRAY.forEach(function (menuItem) {
                    if (menuItem &&
                        menuItem.data.closeOnClickOutside &&
                        menuItem !== _this) {
                        menuItem.close();
                    }
                });
                this.toggleItem(index);
            }
        }
    }
});
export default global['__wxComponents']['vant/dropdown-menu/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-dropdown-menu{display:-webkit-flex;display:flex;-webkit-user-select:none;user-select:none;height:50px;height:var(--dropdown-menu-height,50px);background-color:#fff;background-color:var(--dropdown-menu-background-color,#fff)}.van-dropdown-menu__item{display:-webkit-flex;display:flex;-webkit-flex:1;flex:1;-webkit-align-items:center;align-items:center;-webkit-justify-content:center;justify-content:center;min-width:0}.van-dropdown-menu__item:active{opacity:.7}.van-dropdown-menu__item--disabled:active{opacity:1}.van-dropdown-menu__item--disabled .van-dropdown-menu__title{color:#969799;color:var(--dropdown-menu-title-disabled-text-color,#969799)}.van-dropdown-menu__title{position:relative;box-sizing:border-box;max-width:100%;padding:0 8px;padding:var(--dropdown-menu-title-padding,0 8px);color:#323233;color:var(--dropdown-menu-title-text-color,#323233);font-size:15px;font-size:var(--dropdown-menu-title-font-size,15px);line-height:18px;line-height:var(--dropdown-menu-title-line-height,18px)}.van-dropdown-menu__title:after{position:absolute;top:50%;right:-4px;margin-top:-5px;border-color:transparent transparent currentcolor currentcolor;border-style:solid;border-width:3px;-webkit-transform:rotate(-45deg);transform:rotate(-45deg);opacity:.8;content:""}.van-dropdown-menu__title--active{color:#1989fa;color:var(--dropdown-menu-title-active-text-color,#1989fa)}.van-dropdown-menu__title--down:after{margin-top:-1px;-webkit-transform:rotate(135deg);transform:rotate(135deg)}
</style>