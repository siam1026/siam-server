<template>
<uni-shadow-root class="dist-vant-stepper-index"><view :class="(utils.bem('stepper', [theme]))+' custom-class'">
  <view v-if="showMinus" data-type="minus" :style="computed.buttonStyle({ buttonSize })" :class="'minus-class '+(utils.bem('stepper__minus', { disabled: disabled || disableMinus || currentValue <= min }))" hover-class="van-stepper__minus--hover" hover-stay-time="70" @click="onTap" @touchstart="onTouchStart" @touchend="onTouchEnd">
    <slot name="minus"></slot>
  </view>
  <input :type="integer ? 'number' : 'digit'" :class="'input-class '+(utils.bem('stepper__input', { disabled: disabled || disableInput }))" :style="computed.inputStyle({ buttonSize, inputWidth })" :value="currentValue" :focus="focus" :disabled="disabled || disableInput" :always-embed="alwaysEmbed" @input="onInput" @focus="onFocus" @blur="onBlur"></input>
  <view v-if="showPlus" data-type="plus" :style="computed.buttonStyle({ buttonSize })" :class="'plus-class '+(utils.bem('stepper__plus', { disabled: disabled || disablePlus || currentValue >= max }))" hover-class="van-stepper__plus--hover" hover-stay-time="70" @click="onTap" @touchstart="onTouchStart" @touchend="onTouchEnd">
    <slot name="plus"></slot>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs><wxs src="./index.wxs" module="computed"></wxs>
<script>

global['__wxRoute'] = 'dist/vant/stepper/index'
import { VantComponent } from '../common/component';
import { isDef } from '../common/validator';
const LONG_PRESS_START_TIME = 600;
const LONG_PRESS_INTERVAL = 200;
// add num and avoid float number
function add(num1, num2) {
    const cardinal = Math.pow(10, 10);
    return Math.round((num1 + num2) * cardinal) / cardinal;
}
function equal(value1, value2) {
    return String(value1) === String(value2);
}
VantComponent({
    field: true,
    classes: ['input-class', 'plus-class', 'minus-class'],
    props: {
        value: {
            type: null,
        },
        integer: {
            type: Boolean,
            observer: 'check',
        },
        disabled: Boolean,
        inputWidth: String,
        buttonSize: String,
        asyncChange: Boolean,
        disableInput: Boolean,
        decimalLength: {
            type: Number,
            value: null,
            observer: 'check',
        },
        min: {
            type: null,
            value: 1,
            observer: 'check',
        },
        max: {
            type: null,
            value: Number.MAX_SAFE_INTEGER,
            observer: 'check',
        },
        step: {
            type: null,
            value: 1,
        },
        showPlus: {
            type: Boolean,
            value: true,
        },
        showMinus: {
            type: Boolean,
            value: true,
        },
        disablePlus: Boolean,
        disableMinus: Boolean,
        longPress: {
            type: Boolean,
            value: true,
        },
        theme: String,
        alwaysEmbed: Boolean,
    },
    data: {
        currentValue: '',
    },
    watch: {
        value() {
            this.observeValue();
        },
    },
    created() {
        this.setData({
            currentValue: this.format(this.data.value),
        });
    },
    methods: {
        observeValue() {
            const { value } = this.data;
            this.setData({ currentValue: this.format(value) });
        },
        check() {
            const val = this.format(this.data.currentValue);
            if (!equal(val, this.data.currentValue)) {
                this.setData({ currentValue: val });
            }
        },
        isDisabled(type) {
            const { disabled, disablePlus, disableMinus, currentValue, max, min } = this.data;
            if (type === 'plus') {
                return disabled || disablePlus || +currentValue >= +max;
            }
            return disabled || disableMinus || +currentValue <= +min;
        },
        onFocus(event) {
            this.$emit('focus', event.detail);
        },
        onBlur(event) {
            const value = this.format(event.detail.value);
            this.setData({ currentValue: value });
            this.emitChange(value);
            this.$emit('blur', Object.assign(Object.assign({}, event.detail), { value }));
        },
        // filter illegal characters
        filter(value) {
            value = String(value).replace(/[^0-9.-]/g, '');
            if (this.data.integer && value.indexOf('.') !== -1) {
                value = value.split('.')[0];
            }
            return value;
        },
        // limit value range
        format(value) {
            value = this.filter(value);
            // format range
            value = value === '' ? 0 : +value;
            value = Math.max(Math.min(this.data.max, value), this.data.min);
            // format decimal
            if (isDef(this.data.decimalLength)) {
                value = value.toFixed(this.data.decimalLength);
            }
            return value;
        },
        onInput(event) {
            const { value = '' } = event.detail || {};
            // allow input to be empty
            if (value === '') {
                return;
            }
            let formatted = this.filter(value);
            // limit max decimal length
            if (isDef(this.data.decimalLength) && formatted.indexOf('.') !== -1) {
                const pair = formatted.split('.');
                formatted = `${pair[0]}.${pair[1].slice(0, this.data.decimalLength)}`;
            }
            this.emitChange(formatted);
        },
        emitChange(value) {
            if (!this.data.asyncChange) {
                this.setData({ currentValue: value });
            }
            this.$emit('change', value);
        },
        onChange() {
            const { type } = this;
            if (this.isDisabled(type)) {
                this.$emit('overlimit', type);
                return;
            }
            const diff = type === 'minus' ? -this.data.step : +this.data.step;
            const value = this.format(add(+this.data.currentValue, diff));
            this.emitChange(value);
            this.$emit(type);
        },
        longPressStep() {
            this.longPressTimer = setTimeout(() => {
                this.onChange();
                this.longPressStep();
            }, LONG_PRESS_INTERVAL);
        },
        onTap(event) {
            const { type } = event.currentTarget.dataset;
            this.type = type;
            this.onChange();
        },
        onTouchStart(event) {
            if (!this.data.longPress) {
                return;
            }
            clearTimeout(this.longPressTimer);
            const { type } = event.currentTarget.dataset;
            this.type = type;
            this.isLongPress = false;
            this.longPressTimer = setTimeout(() => {
                this.isLongPress = true;
                this.onChange();
                this.longPressStep();
            }, LONG_PRESS_START_TIME);
        },
        onTouchEnd() {
            if (!this.data.longPress) {
                return;
            }
            clearTimeout(this.longPressTimer);
        },
    },
});
export default global['__wxComponents']['dist/vant/stepper/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-stepper{font-size:0}.van-stepper__minus,.van-stepper__plus{background-color:var(--stepper-background-color,#f2f3f5);border:0;box-sizing:border-box;color:var(--stepper-button-icon-color,#323233);display:inline-block;height:var(--stepper-input-height,28px);margin:1px;padding:var(--padding-base,4px);position:relative;vertical-align:middle;width:var(--stepper-input-height,28px)}.van-stepper__minus:before,.van-stepper__plus:before{height:1px;width:9px}.van-stepper__minus:after,.van-stepper__plus:after{height:9px;width:1px}.van-stepper__minus:empty.van-stepper__minus:after,.van-stepper__minus:empty.van-stepper__minus:before,.van-stepper__minus:empty.van-stepper__plus:after,.van-stepper__minus:empty.van-stepper__plus:before,.van-stepper__plus:empty.van-stepper__minus:after,.van-stepper__plus:empty.van-stepper__minus:before,.van-stepper__plus:empty.van-stepper__plus:after,.van-stepper__plus:empty.van-stepper__plus:before{background-color:currentColor;bottom:0;content:"";left:0;margin:auto;position:absolute;right:0;top:0}.van-stepper__minus--hover,.van-stepper__plus--hover{background-color:var(--stepper-active-color,#e8e8e8)}.van-stepper__minus--disabled,.van-stepper__plus--disabled{color:var(--stepper-button-disabled-icon-color,#c8c9cc)}.van-stepper__minus--disabled,.van-stepper__minus--disabled.van-stepper__minus--hover,.van-stepper__minus--disabled.van-stepper__plus--hover,.van-stepper__plus--disabled,.van-stepper__plus--disabled.van-stepper__minus--hover,.van-stepper__plus--disabled.van-stepper__plus--hover{background-color:var(--stepper-button-disabled-color,#f7f8fa)}.van-stepper__minus{border-radius:var(--stepper-border-radius,var(--stepper-border-radius,4px)) 0 0 var(--stepper-border-radius,var(--stepper-border-radius,4px))}.van-stepper__minus:after{display:none}.van-stepper__plus{border-radius:0 var(--stepper-border-radius,var(--stepper-border-radius,4px)) var(--stepper-border-radius,var(--stepper-border-radius,4px)) 0}.van-stepper--round .van-stepper__input{background-color:initial!important}.van-stepper--round .van-stepper__minus,.van-stepper--round .van-stepper__plus{border-radius:100%}.van-stepper--round .van-stepper__minus:active,.van-stepper--round .van-stepper__plus:active{opacity:.7}.van-stepper--round .van-stepper__minus--disabled,.van-stepper--round .van-stepper__minus--disabled:active,.van-stepper--round .van-stepper__plus--disabled,.van-stepper--round .van-stepper__plus--disabled:active{opacity:.3}.van-stepper--round .van-stepper__plus{background-color:#ee0a24;color:#fff}.van-stepper--round .van-stepper__minus{background-color:#fff;border:1px solid #ee0a24;color:#ee0a24}.van-stepper__input{-webkit-appearance:none;background-color:var(--stepper-background-color,#f2f3f5);border:0;border-radius:0;border-width:1px 0;box-sizing:border-box;color:var(--stepper-input-text-color,#323233);display:inline-block;font-size:var(--stepper-input-font-size,14px);height:var(--stepper-input-height,28px);margin:1px;min-height:0;padding:1px;text-align:center;vertical-align:middle;width:var(--stepper-input-width,32px)}.van-stepper__input--disabled{background-color:var(--stepper-input-disabled-background-color,#f2f3f5);color:var(--stepper-input-disabled-text-color,#c8c9cc)}
</style>