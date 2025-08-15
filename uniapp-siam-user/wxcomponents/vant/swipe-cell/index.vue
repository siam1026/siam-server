<template>
<uni-shadow-root class="vant-swipe-cell-index"><view class="van-swipe-cell" data-key="cell" @click.stop.prevent="onClick" @touchstart="startDrag" @touchmove.stop.prevent="_$self[(catchMove ? 'noop' : '')||'_$noop']($event)" @touchmove.capture="onDrag" @touchend="endDrag" @touchcancel="endDrag">
  <view :style="wrapperStyle">
    <view v-if="leftWidth" class="van-swipe-cell__left" data-key="left" @click.stop.prevent="onClick">
      <slot name="left"></slot>
    </view>
    <slot></slot>
    <view v-if="rightWidth" class="van-swipe-cell__right" data-key="right" @click.stop.prevent="onClick">
      <slot name="right"></slot>
    </view>
  </view>
</view></uni-shadow-root>
</template>

<script>

global['__wxRoute'] = 'vant/swipe-cell/index'
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var component_1 = require("../common/component");
var touch_1 = require("../mixins/touch");
var utils_1 = require("../common/utils");
var THRESHOLD = 0.3;
var ARRAY = [];
component_1.VantComponent({
    props: {
        disabled: Boolean,
        leftWidth: {
            type: Number,
            value: 0,
            observer: function (leftWidth) {
                if (leftWidth === void 0) { leftWidth = 0; }
                if (this.offset > 0) {
                    this.swipeMove(leftWidth);
                }
            }
        },
        rightWidth: {
            type: Number,
            value: 0,
            observer: function (rightWidth) {
                if (rightWidth === void 0) { rightWidth = 0; }
                if (this.offset < 0) {
                    this.swipeMove(-rightWidth);
                }
            }
        },
        asyncClose: Boolean,
        name: {
            type: [Number, String],
            value: ''
        }
    },
    mixins: [touch_1.touch],
    data: {
        catchMove: false
    },
    created: function () {
        this.offset = 0;
        ARRAY.push(this);
    },
    destroyed: function () {
        var _this = this;
        ARRAY = ARRAY.filter(function (item) { return item !== _this; });
    },
    methods: {
        open: function (position) {
            var _a = this.data, leftWidth = _a.leftWidth, rightWidth = _a.rightWidth;
            var offset = position === 'left' ? leftWidth : -rightWidth;
            this.swipeMove(offset);
            this.$emit('open', {
                position: position,
                name: this.data.name
            });
        },
        close: function () {
            this.swipeMove(0);
        },
        swipeMove: function (offset) {
            if (offset === void 0) { offset = 0; }
            this.offset = utils_1.range(offset, -this.data.rightWidth, this.data.leftWidth);
            var transform = "translate3d(" + this.offset + "px, 0, 0)";
            var transition = this.dragging
                ? 'none'
                : 'transform .6s cubic-bezier(0.18, 0.89, 0.32, 1)';
            this.setData({
                wrapperStyle: "\n        -webkit-transform: " + transform + ";\n        -webkit-transition: " + transition + ";\n        transform: " + transform + ";\n        transition: " + transition + ";\n      "
            });
        },
        swipeLeaveTransition: function () {
            var _a = this.data, leftWidth = _a.leftWidth, rightWidth = _a.rightWidth;
            var offset = this.offset;
            if (rightWidth > 0 && -offset > rightWidth * THRESHOLD) {
                this.open('right');
            }
            else if (leftWidth > 0 && offset > leftWidth * THRESHOLD) {
                this.open('left');
            }
            else {
                this.swipeMove(0);
            }
            this.setData({ catchMove: false });
        },
        startDrag: function (event) {
            if (this.data.disabled) {
                return;
            }
            this.startOffset = this.offset;
            this.touchStart(event);
        },
        noop: function () { },
        onDrag: function (event) {
            var _this = this;
            if (this.data.disabled) {
                return;
            }
            this.touchMove(event);
            if (this.direction !== 'horizontal') {
                return;
            }
            this.dragging = true;
            ARRAY.filter(function (item) { return item !== _this; }).forEach(function (item) { return item.close(); });
            this.setData({ catchMove: true });
            this.swipeMove(this.startOffset + this.deltaX);
        },
        endDrag: function () {
            if (this.data.disabled) {
                return;
            }
            this.dragging = false;
            this.swipeLeaveTransition();
        },
        onClick: function (event) {
            var _a = event.currentTarget.dataset.key, position = _a === void 0 ? 'outside' : _a;
            this.$emit('click', position);
            if (!this.offset) {
                return;
            }
            if (this.data.asyncClose) {
                this.$emit('close', {
                    position: position,
                    instance: this,
                    name: this.data.name
                });
            }
            else {
                this.swipeMove(0);
            }
        }
    }
});
export default global['__wxComponents']['vant/swipe-cell/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-swipe-cell{position:relative;overflow:hidden}.van-swipe-cell__left,.van-swipe-cell__right{position:absolute;top:0;height:100%}.van-swipe-cell__left{left:0;-webkit-transform:translate3d(-100%,0,0);transform:translate3d(-100%,0,0)}.van-swipe-cell__right{right:0;-webkit-transform:translate3d(100%,0,0);transform:translate3d(100%,0,0)}
</style>