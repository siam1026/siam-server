<template>
<uni-shadow-root class="vant-stepper-index"><view class="van-stepper custom-class">
  <view v-if="showMinus" data-type="minus" :style="'width: '+(utils.addUnit(buttonSize))+'; height: '+(utils.addUnit(buttonSize))" :class="'minus-class '+(utils.bem('stepper__minus', { disabled: disabled || disableMinus || currentValue <= min }))" hover-class="van-stepper__minus--hover" hover-stay-time="70" @click="onTap" @touchstart="onTouchStart" @touchend="onTouchEnd"></view>
  <input :type="integer ? 'number' : 'digit'" :class="'input-class '+(utils.bem('stepper__input', { disabled: disabled || disableInput }))" :style="'width: '+(utils.addUnit(inputWidth))+'; height: '+(utils.addUnit(buttonSize))" :value="currentValue" :focus="focus" :disabled="disabled || disableInput" @input="onInput" @focus="onFocus" @blur="onBlur"></input>
  <view v-if="showPlus" data-type="plus" :style="'width: '+(utils.addUnit(buttonSize))+'; height: '+(utils.addUnit(buttonSize))" :class="'plus-class '+(utils.bem('stepper__plus', { disabled: disabled || disablePlus || currentValue >= max }))" hover-class="van-stepper__plus--hover" hover-stay-time="70" @click="onTap" @touchstart="onTouchStart" @touchend="onTouchEnd"></view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>

global['__wxRoute'] = 'vant/stepper/index'
"use strict";
var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
Object.defineProperty(exports, "__esModule", { value: true });
var component_1 = require("../common/component");
var utils_1 = require("../common/utils");
var LONG_PRESS_START_TIME = 600;
var LONG_PRESS_INTERVAL = 200;
// add num and avoid float number
function add(num1, num2) {
    var cardinal = Math.pow(10, 10);
    return Math.round((num1 + num2) * cardinal) / cardinal;
}
function equal(value1, value2) {
    return String(value1) === String(value2);
}
component_1.VantComponent({
    field: true,
    classes: ['input-class', 'plus-class', 'minus-class'],
    props: {
        value: {
            type: null,
            observer: function (value) {
                if (!equal(value, this.data.currentValue)) {
                    this.setData({ currentValue: this.format(value) });
                }
            }
        },
        integer: {
            type: Boolean,
            observer: 'check'
        },
        disabled: Boolean,
        inputWidth: null,
        buttonSize: null,
        asyncChange: Boolean,
        disableInput: Boolean,
        decimalLength: {
            type: Number,
            value: null,
            observer: 'check'
        },
        min: {
            type: null,
            value: 1,
            observer: 'check'
        },
        max: {
            type: null,
            value: Number.MAX_SAFE_INTEGER,
            observer: 'check'
        },
        step: {
            type: null,
            value: 1
        },
        showPlus: {
            type: Boolean,
            value: true
        },
        showMinus: {
            type: Boolean,
            value: true
        },
        disablePlus: Boolean,
        disableMinus: Boolean,
        longPress: {
            type: Boolean,
            value: true
        }
    },
    data: {
        currentValue: ''
    },
    created: function () {
        this.setData({
            currentValue: this.format(this.data.value)
        });
    },
    methods: {
        check: function () {
            var val = this.format(this.data.currentValue);
            if (!equal(val, this.data.currentValue)) {
                this.setData({ currentValue: val });
            }
        },
        isDisabled: function (type) {
            if (type === 'plus') {
                return (this.data.disabled ||
                    this.data.disablePlus ||
                    this.data.currentValue >= this.data.max);
            }
            return (this.data.disabled ||
                this.data.disableMinus ||
                this.data.currentValue <= this.data.min);
        },
        onFocus: function (event) {
            this.$emit('focus', event.detail);
        },
        onBlur: function (event) {
            var value = this.format(event.detail.value);
            this.emitChange(value);
            this.$emit('blur', __assign(__assign({}, event.detail), { value: value }));
        },
        // filter illegal characters
        filter: function (value) {
            value = String(value).replace(/[^0-9.-]/g, '');
            if (this.data.integer && value.indexOf('.') !== -1) {
                value = value.split('.')[0];
            }
            return value;
        },
        // limit value range
        format: function (value) {
            value = this.filter(value);
            // format range
            value = value === '' ? 0 : +value;
            value = Math.max(Math.min(this.data.max, value), this.data.min);
            // format decimal
            if (utils_1.isDef(this.data.decimalLength)) {
                value = value.toFixed(this.data.decimalLength);
            }
            return value;
        },
        onInput: function (event) {
            var _a = (event.detail || {}).value, value = _a === void 0 ? '' : _a;
            // allow input to be empty
            if (value === '') {
                return;
            }
            var formatted = this.filter(value);
            // limit max decimal length
            if (utils_1.isDef(this.data.decimalLength) && formatted.indexOf('.') !== -1) {
                var pair = formatted.split('.');
                formatted = pair[0] + "." + pair[1].slice(0, this.data.decimalLength);
            }
            this.emitChange(formatted);
        },
        emitChange: function (value) {
            if (!this.data.asyncChange) {
                this.setData({ currentValue: value });
            }
            this.$emit('change', value);
        },
        onChange: function () {
            var type = this.type;
            if (this.isDisabled(type)) {
                this.$emit('overlimit', type);
                return;
            }
            var diff = type === 'minus' ? -this.data.step : +this.data.step;
            var value = this.format(add(+this.data.currentValue, diff));
            this.emitChange(value);
            this.$emit(type);
        },
        longPressStep: function () {
            var _this = this;
            this.longPressTimer = setTimeout(function () {
                _this.onChange();
                _this.longPressStep();
            }, LONG_PRESS_INTERVAL);
        },
        onTap: function (event) {
            var type = event.currentTarget.dataset.type;
            this.type = type;
            this.onChange();
        },
        onTouchStart: function (event) {
            var _this = this;
            if (!this.data.longPress) {
                return;
            }
            clearTimeout(this.longPressTimer);
            var type = event.currentTarget.dataset.type;
            this.type = type;
            this.isLongPress = false;
            this.longPressTimer = setTimeout(function () {
                _this.isLongPress = true;
                _this.onChange();
                _this.longPressStep();
            }, LONG_PRESS_START_TIME);
        },
        onTouchEnd: function () {
            if (!this.data.longPress) {
                return;
            }
            clearTimeout(this.longPressTimer);
        }
    }
});
export default global['__wxComponents']['vant/stepper/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-stepper{font-size:0}.van-stepper__minus,.van-stepper__plus{position:relative;display:inline-block;box-sizing:border-box;margin:1px;vertical-align:middle;border:0;background-color:#f2f3f5;background-color:var(--stepper-background-color,#f2f3f5);color:#323233;color:var(--stepper-button-icon-color,#323233);width:28px;width:var(--stepper-input-height,28px);height:28px;height:var(--stepper-input-height,28px);padding:4px;padding:var(--padding-base,4px)}.van-stepper__minus:before,.van-stepper__plus:before{width:9px;height:1px}.van-stepper__minus:after,.van-stepper__plus:after{width:1px;height:9px}.van-stepper__minus:after,.van-stepper__minus:before,.van-stepper__plus:after,.van-stepper__plus:before{position:absolute;top:0;right:0;bottom:0;left:0;margin:auto;background-color:currentColor;content:""}.van-stepper__minus--hover,.van-stepper__plus--hover{background-color:#e8e8e8;background-color:var(--stepper-active-color,#e8e8e8)}.van-stepper__minus--disabled,.van-stepper__plus--disabled{color:#c8c9cc;color:var(--stepper-button-disabled-icon-color,#c8c9cc)}.van-stepper__minus--disabled,.van-stepper__minus--disabled.van-stepper__minus--hover,.van-stepper__minus--disabled.van-stepper__plus--hover,.van-stepper__plus--disabled,.van-stepper__plus--disabled.van-stepper__minus--hover,.van-stepper__plus--disabled.van-stepper__plus--hover{background-color:#f7f8fa;background-color:var(--stepper-button-disabled-color,#f7f8fa)}.van-stepper__minus{border-radius:4px 0 0 4px;border-radius:var(--stepper-border-radius,4px) 0 0 var(--stepper-border-radius,4px)}.van-stepper__minus:after{display:none}.van-stepper__plus{border-radius:0 4px 4px 0;border-radius:0 var(--stepper-border-radius,4px) var(--stepper-border-radius,4px) 0}.van-stepper__input{display:inline-block;box-sizing:border-box;min-height:0;margin:1px;padding:1px;text-align:center;vertical-align:middle;border:0;border-width:1px 0;border-radius:0;-webkit-appearance:none;font-size:14px;font-size:var(--stepper-input-font-size,14px);color:#323233;color:var(--stepper-input-text-color,#323233);background-color:#f2f3f5;background-color:var(--stepper-background-color,#f2f3f5);width:32px;width:var(--stepper-input-width,32px);height:28px;height:var(--stepper-input-height,28px)}.van-stepper__input--disabled{color:#c8c9cc;color:var(--stepper-input-disabled-text-color,#c8c9cc);background-color:#f2f3f5;background-color:var(--stepper-input-disabled-background-color,#f2f3f5)}
</style>