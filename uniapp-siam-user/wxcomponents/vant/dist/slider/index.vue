<template>
<uni-shadow-root class="vant-dist-slider-index"><view :class="'custom-class '+(utils.bem('slider', { disabled, vertical }))" :style="wrapperStyle" @click="onClick">
  <view :class="utils.bem('slider__bar')" :style="(barStyle)+'; '+(style({ backgroundColor: activeColor }))">
    <view v-if="range" :class="utils.bem('slider__button-wrapper-left')" :data-index="0" @touchstart="onTouchStart" @touchmove.stop.prevent="onTouchMove" @touchend="onTouchEnd" @touchcancel="onTouchEnd">
      <slot v-if="useButtonSlot" name="left-button"></slot>
      <view v-else :class="utils.bem('slider__button')"></view>
    </view>
    <view v-if="range" :class="utils.bem('slider__button-wrapper-right')" :data-index="1" @touchstart="onTouchStart" @touchmove.stop.prevent="onTouchMove" @touchend="onTouchEnd" @touchcancel="onTouchEnd">
      <slot v-if="useButtonSlot" name="right-button"></slot>
      <view v-else :class="utils.bem('slider__button')"></view>
    </view>

    <view v-if="(!range)" :class="utils.bem('slider__button-wrapper')" @touchstart="onTouchStart" @touchmove.stop.prevent="onTouchMove" @touchend="onTouchEnd" @touchcancel="onTouchEnd">
      <slot v-if="useButtonSlot" name="button"></slot>
      <view v-else :class="utils.bem('slider__button')"></view>
    </view>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs><wxs src="../wxs/style.wxs" module="style"></wxs>
<script>

global['__wxRoute'] = 'vant/dist/slider/index'
import { VantComponent } from '../common/component';
import { touch } from '../mixins/touch';
import { canIUseModel } from '../common/version';
import { getRect, addUnit, nextTick, addNumber, clamp } from '../common/utils';
const DRAG_STATUS = {
    START: 'start',
    MOVING: 'moving',
    END: 'end',
};
VantComponent({
    mixins: [touch],
    props: {
        range: Boolean,
        disabled: Boolean,
        useButtonSlot: Boolean,
        activeColor: String,
        inactiveColor: String,
        max: {
            type: Number,
            value: 100,
        },
        min: {
            type: Number,
            value: 0,
        },
        step: {
            type: Number,
            value: 1,
        },
        value: {
            type: null,
            value: 0,
            observer(val) {
                if (val !== this.value) {
                    this.updateValue(val);
                }
            },
        },
        vertical: Boolean,
        barHeight: null,
    },
    created() {
        this.updateValue(this.data.value);
    },
    methods: {
        onTouchStart(event) {
            if (this.data.disabled)
                return;
            const { index } = event.currentTarget.dataset;
            if (typeof index === 'number') {
                this.buttonIndex = index;
            }
            this.touchStart(event);
            this.startValue = this.format(this.value);
            this.newValue = this.value;
            if (this.isRange(this.newValue)) {
                this.startValue = this.newValue.map((val) => this.format(val));
            }
            else {
                this.startValue = this.format(this.newValue);
            }
            this.dragStatus = DRAG_STATUS.START;
        },
        onTouchMove(event) {
            if (this.data.disabled)
                return;
            if (this.dragStatus === DRAG_STATUS.START) {
                this.$emit('drag-start');
            }
            this.touchMove(event);
            this.dragStatus = DRAG_STATUS.MOVING;
            getRect(this, '.van-slider').then((rect) => {
                const { vertical } = this.data;
                const delta = vertical ? this.deltaY : this.deltaX;
                const total = vertical ? rect.height : rect.width;
                const diff = (delta / total) * this.getRange();
                if (this.isRange(this.startValue)) {
                    this.newValue[this.buttonIndex] =
                        this.startValue[this.buttonIndex] + diff;
                }
                else {
                    this.newValue = this.startValue + diff;
                }
                this.updateValue(this.newValue, false, true);
            });
        },
        onTouchEnd() {
            if (this.data.disabled)
                return;
            if (this.dragStatus === DRAG_STATUS.MOVING) {
                this.dragStatus = DRAG_STATUS.END;
                nextTick(() => {
                    this.updateValue(this.newValue, true);
                    this.$emit('drag-end');
                });
            }
        },
        onClick(event) {
            if (this.data.disabled)
                return;
            const { min } = this.data;
            getRect(this, '.van-slider').then((rect) => {
                const { vertical } = this.data;
                const touch = event.touches[0];
                const delta = vertical
                    ? touch.clientY - rect.top
                    : touch.clientX - rect.left;
                const total = vertical ? rect.height : rect.width;
                const value = Number(min) + (delta / total) * this.getRange();
                if (this.isRange(this.value)) {
                    const [left, right] = this.value;
                    const middle = (left + right) / 2;
                    if (value <= middle) {
                        this.updateValue([value, right], true);
                    }
                    else {
                        this.updateValue([left, value], true);
                    }
                }
                else {
                    this.updateValue(value, true);
                }
            });
        },
        isRange(val) {
            const { range } = this.data;
            return range && Array.isArray(val);
        },
        handleOverlap(value) {
            if (value[0] > value[1]) {
                return value.slice(0).reverse();
            }
            return value;
        },
        updateValue(value, end, drag) {
            if (this.isRange(value)) {
                value = this.handleOverlap(value).map((val) => this.format(val));
            }
            else {
                value = this.format(value);
            }
            this.value = value;
            const { vertical } = this.data;
            const mainAxis = vertical ? 'height' : 'width';
            this.setData({
                wrapperStyle: `
          background: ${this.data.inactiveColor || ''};
          ${vertical ? 'width' : 'height'}: ${addUnit(this.data.barHeight) || ''};
        `,
                barStyle: `
          ${mainAxis}: ${this.calcMainAxis()};
          left: ${vertical ? 0 : this.calcOffset()};
          top: ${vertical ? this.calcOffset() : 0};
          ${drag ? 'transition: none;' : ''}
        `,
            });
            if (drag) {
                this.$emit('drag', { value });
            }
            if (end) {
                this.$emit('change', value);
            }
            if ((drag || end) && canIUseModel()) {
                this.setData({ value });
            }
        },
        getScope() {
            return Number(this.data.max) - Number(this.data.min);
        },
        getRange() {
            const { max, min } = this.data;
            return max - min;
        },
        getOffsetWidth(current, min) {
            const scope = this.getScope();
            // 避免最小值小于最小step时出现负数情况
            return `${Math.max(((current - min) * 100) / scope, 0)}%`;
        },
        // 计算选中条的长度百分比
        calcMainAxis() {
            const { value } = this;
            const { min } = this.data;
            if (this.isRange(value)) {
                return this.getOffsetWidth(value[1], value[0]);
            }
            return this.getOffsetWidth(value, Number(min));
        },
        // 计算选中条的开始位置的偏移量
        calcOffset() {
            const { value } = this;
            const { min } = this.data;
            const scope = this.getScope();
            if (this.isRange(value)) {
                return `${((value[0] - Number(min)) * 100) / scope}%`;
            }
            return '0%';
        },
        format(value) {
            const min = +this.data.min;
            const max = +this.data.max;
            const step = +this.data.step;
            value = clamp(value, min, max);
            const diff = Math.round((value - min) / step) * step;
            return addNumber(min, diff);
        },
    },
});
export default global['__wxComponents']['vant/dist/slider/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-slider{background-color:var(--slider-inactive-background-color,#ebedf0);border-radius:999px;height:var(--slider-bar-height,2px);position:relative}.van-slider:before{bottom:calc(var(--padding-xs, 8px)*-1);content:"";left:0;position:absolute;right:0;top:calc(var(--padding-xs, 8px)*-1)}.van-slider__bar{background-color:var(--slider-active-background-color,#1989fa);border-radius:inherit;height:100%;position:relative;transition:all .2s;width:100%}.van-slider__button{background-color:var(--slider-button-background-color,#fff);border-radius:var(--slider-button-border-radius,50%);box-shadow:var(--slider-button-box-shadow,0 1px 2px rgba(0,0,0,.5));height:var(--slider-button-height,24px);width:var(--slider-button-width,24px)}.van-slider__button-wrapper,.van-slider__button-wrapper-right{position:absolute;right:0;top:50%;transform:translate3d(50%,-50%,0)}.van-slider__button-wrapper-left{left:0;position:absolute;top:50%;transform:translate3d(-50%,-50%,0)}.van-slider--disabled{opacity:var(--slider-disabled-opacity,.5)}.van-slider--vertical{display:inline-block;height:100%;width:var(--slider-bar-height,2px)}.van-slider--vertical .van-slider__button-wrapper,.van-slider--vertical .van-slider__button-wrapper-right{bottom:0;right:50%;top:auto;transform:translate3d(50%,50%,0)}.van-slider--vertical .van-slider__button-wrapper-left{left:auto;right:50%;top:0;transform:translate3d(50%,-50%,0)}.van-slider--vertical:before{bottom:0;left:-8px;right:-8px;top:0}
</style>