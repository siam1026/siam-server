<template>
<uni-shadow-root class="vant-dist-picker-column-index"><view class="van-picker-column custom-class" :style="computed.rootStyle({ itemHeight, visibleItemCount })" @touchstart="onTouchStart" @touchmove.stop.prevent="onTouchMove" @touchend="onTouchEnd" @touchcancel="onTouchEnd">
  <view :style="computed.wrapperStyle({ offset, itemHeight, visibleItemCount, duration })">
    <view v-for="(option,index) in (options)" :key="option.index" :data-index="index" :style="'height: '+(itemHeight)+'px'" :class="'van-ellipsis '+(utils.bem('picker-column__item', { disabled: option && option.disabled, selected: index === currentIndex }))+' '+(index === currentIndex ? 'active-class' : '')" @click="onClickItem">{{ computed.optionText(option, valueKey) }}</view>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs><wxs src="./index.wxs" module="computed"></wxs>
<script>

global['__wxRoute'] = 'vant/dist/picker-column/index'
import { VantComponent } from '../common/component';
import { range } from '../common/utils';
import { isObj } from '../common/validator';
const DEFAULT_DURATION = 200;
VantComponent({
    classes: ['active-class'],
    props: {
        valueKey: String,
        className: String,
        itemHeight: Number,
        visibleItemCount: Number,
        initialOptions: {
            type: Array,
            value: [],
        },
        defaultIndex: {
            type: Number,
            value: 0,
            observer(value) {
                this.setIndex(value);
            },
        },
    },
    data: {
        startY: 0,
        offset: 0,
        duration: 0,
        startOffset: 0,
        options: [],
        currentIndex: 0,
    },
    created() {
        const { defaultIndex, initialOptions } = this.data;
        this.set({
            currentIndex: defaultIndex,
            options: initialOptions,
        }).then(() => {
            this.setIndex(defaultIndex);
        });
    },
    methods: {
        getCount() {
            return this.data.options.length;
        },
        onTouchStart(event) {
            this.setData({
                startY: event.touches[0].clientY,
                startOffset: this.data.offset,
                duration: 0,
            });
        },
        onTouchMove(event) {
            const { data } = this;
            const deltaY = event.touches[0].clientY - data.startY;
            this.setData({
                offset: range(data.startOffset + deltaY, -(this.getCount() * data.itemHeight), data.itemHeight),
            });
        },
        onTouchEnd() {
            const { data } = this;
            if (data.offset !== data.startOffset) {
                this.setData({ duration: DEFAULT_DURATION });
                const index = range(Math.round(-data.offset / data.itemHeight), 0, this.getCount() - 1);
                this.setIndex(index, true);
            }
        },
        onClickItem(event) {
            const { index } = event.currentTarget.dataset;
            this.setIndex(index, true);
        },
        adjustIndex(index) {
            const { data } = this;
            const count = this.getCount();
            index = range(index, 0, count);
            for (let i = index; i < count; i++) {
                if (!this.isDisabled(data.options[i]))
                    return i;
            }
            for (let i = index - 1; i >= 0; i--) {
                if (!this.isDisabled(data.options[i]))
                    return i;
            }
        },
        isDisabled(option) {
            return isObj(option) && option.disabled;
        },
        getOptionText(option) {
            const { data } = this;
            return isObj(option) && data.valueKey in option
                ? option[data.valueKey]
                : option;
        },
        setIndex(index, userAction) {
            const { data } = this;
            index = this.adjustIndex(index) || 0;
            const offset = -index * data.itemHeight;
            if (index !== data.currentIndex) {
                return this.set({ offset, currentIndex: index }).then(() => {
                    userAction && this.$emit('change', index);
                });
            }
            return this.set({ offset });
        },
        setValue(value) {
            const { options } = this.data;
            for (let i = 0; i < options.length; i++) {
                if (this.getOptionText(options[i]) === value) {
                    return this.setIndex(i);
                }
            }
            return Promise.resolve();
        },
        getValue() {
            const { data } = this;
            return data.options[data.currentIndex];
        },
    },
});
export default global['__wxComponents']['vant/dist/picker-column/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-picker-column{color:var(--picker-option-text-color,#000);font-size:var(--picker-option-font-size,16px);overflow:hidden;text-align:center}.van-picker-column__item{padding:0 5px}.van-picker-column__item--selected{color:var(--picker-option-selected-text-color,#323233);font-weight:var(--font-weight-bold,500)}.van-picker-column__item--disabled{opacity:var(--picker-option-disabled-opacity,.3)}
</style>