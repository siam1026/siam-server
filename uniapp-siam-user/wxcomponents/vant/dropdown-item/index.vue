<template>
<uni-shadow-root class="vant-dropdown-item-index"><view v-if="showWrapper" :class="utils.bem('dropdown-item', direction)" :style="wrapperStyle">
  <van-popup :show="showPopup" :custom-style="'position: absolute;'+(popupStyle)" overlay-style="position: absolute;" :overlay="overlay" :position="direction === 'down' ? 'top' : 'bottom'" :duration="transition ? duration : 0" :close-on-click-overlay="closeOnClickOverlay" @enter="onOpen" @leave="onClose" @close="toggle" @after-enter="onOpened" @after-leave="onClosed">
    <van-cell v-for="(item,index) in (options)" :key="item.value" :data-option="item" :class="utils.bem('dropdown-item__option', { active: item.value === value } )" clickable :icon="item.icon" @click.native="onOptionTap">
      <view slot="title" class="van-dropdown-item__title" :style="item.value === value  ? 'color:' + activeColor : ''">
        {{ item.text }}
      </view>
      <van-icon v-if="item.value === value" name="success" class="van-dropdown-item__icon" :color="activeColor"></van-icon>
    </van-cell>

    <slot></slot>
  </van-popup>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>
import VanPopup from '../popup/index.vue'
import VanCell from '../cell/index.vue'
import VanIcon from '../icon/index.vue'
global['__wxVueOptions'] = {components:{'van-popup': VanPopup,'van-cell': VanCell,'van-icon': VanIcon}}

global['__wxRoute'] = 'vant/dropdown-item/index'
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var component_1 = require("../common/component");
component_1.VantComponent({
    field: true,
    relation: {
        name: 'dropdown-menu',
        type: 'ancestor',
        current: 'dropdown-item',
        linked: function () {
            this.updateDataFromParent();
        }
    },
    props: {
        value: {
            type: null,
            observer: 'rerender'
        },
        title: {
            type: String,
            observer: 'rerender'
        },
        disabled: Boolean,
        titleClass: {
            type: String,
            observer: 'rerender'
        },
        options: {
            type: Array,
            value: [],
            observer: 'rerender'
        },
        popupStyle: String
    },
    data: {
        transition: true,
        showPopup: false,
        showWrapper: false,
        displayTitle: ''
    },
    methods: {
        rerender: function () {
            var _this = this;
            wx.nextTick(function () {
                _this.parent && _this.parent.updateItemListData();
            });
        },
        updateDataFromParent: function () {
            if (this.parent) {
                var _a = this.parent.data, overlay = _a.overlay, duration = _a.duration, activeColor = _a.activeColor, closeOnClickOverlay = _a.closeOnClickOverlay, direction = _a.direction;
                this.setData({
                    overlay: overlay,
                    duration: duration,
                    activeColor: activeColor,
                    closeOnClickOverlay: closeOnClickOverlay,
                    direction: direction
                });
            }
        },
        onOpen: function () {
            this.$emit('open');
        },
        onOpened: function () {
            this.$emit('opened');
        },
        onClose: function () {
            this.$emit('close');
        },
        onClosed: function () {
            this.$emit('closed');
            this.setData({ showWrapper: false });
        },
        onOptionTap: function (event) {
            var option = event.currentTarget.dataset.option;
            var value = option.value;
            var shouldEmitChange = this.data.value !== value;
            this.setData({ showPopup: false, value: value });
            this.$emit('close');
            this.rerender();
            if (shouldEmitChange) {
                this.$emit('change', value);
            }
        },
        toggle: function (show, options) {
            var _this = this;
            if (options === void 0) { options = {}; }
            var showPopup = this.data.showPopup;
            if (typeof show !== 'boolean') {
                show = !showPopup;
            }
            if (show === showPopup) {
                return;
            }
            this.setData({
                transition: !options.immediate,
                showPopup: show,
            });
            if (show) {
                this.parent.getChildWrapperStyle().then(function (wrapperStyle) {
                    _this.setData({ wrapperStyle: wrapperStyle, showWrapper: true });
                    _this.rerender();
                });
            }
            else {
                this.rerender();
            }
        }
    }
});
export default global['__wxComponents']['vant/dropdown-item/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-dropdown-item{position:fixed;right:0;left:0;overflow:hidden}.van-dropdown-item__option{text-align:left}.van-dropdown-item__option--active .van-dropdown-item__icon,.van-dropdown-item__option--active .van-dropdown-item__title{color:#1989fa;color:var(--dropdown-menu-option-active-color,#1989fa)}.van-dropdown-item--up{top:0}.van-dropdown-item--down{bottom:0}.van-dropdown-item__icon{display:block;line-height:inherit}
</style>