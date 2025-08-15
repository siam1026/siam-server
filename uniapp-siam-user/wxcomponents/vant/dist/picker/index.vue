<template>
<uni-shadow-root class="vant-dist-picker-index"><view class="van-picker custom-class">
  <include v-if="toolbarPosition === 'top'" src="./toolbar.wxml"></include>

  <view v-if="loading" class="van-picker__loading">
    <loading color="#1989fa"></loading>
  </view>

  <view class="van-picker__columns" :style="computed.columnsStyle({ itemHeight, visibleItemCount })" @touchmove.stop.prevent="noop">
    <picker-column v-for="(item,index) in (computed.columns(columns))" :key="item.index" class="van-picker__column" :data-index="index" custom-class="column-class" :value-key="valueKey" :initial-options="item.values" :default-index="item.defaultIndex || defaultIndex" :item-height="itemHeight" :visible-item-count="visibleItemCount" active-class="active-class" @change="onChange"></picker-column>
    <view class="van-picker__mask" :style="computed.maskStyle({ itemHeight, visibleItemCount })"></view>
    <view class="van-picker__frame van-hairline--top-bottom" :style="computed.frameStyle({ itemHeight })"></view>
  </view>

  <include v-if="toolbarPosition === 'bottom'" src="./toolbar.wxml"></include>
</view></uni-shadow-root>
</template>
<wxs src="./index.wxs" module="computed"></wxs>
<script>
import PickerColumn from '../picker-column/index.vue'
import Loading from '../loading/index.vue'
global['__wxVueOptions'] = {components:{'picker-column': PickerColumn,'loading': Loading}}

global['__wxRoute'] = 'vant/dist/picker/index'
import { VantComponent } from '../common/component';
import { pickerProps } from './shared';
VantComponent({
    classes: ['active-class', 'toolbar-class', 'column-class'],
    props: Object.assign(Object.assign({}, pickerProps), { valueKey: {
            type: String,
            value: 'text',
        }, toolbarPosition: {
            type: String,
            value: 'top',
        }, defaultIndex: {
            type: Number,
            value: 0,
        }, columns: {
            type: Array,
            value: [],
            observer(columns = []) {
                this.simple = columns.length && !columns[0].values;
                if (Array.isArray(this.children) && this.children.length) {
                    this.setColumns().catch(() => { });
                }
            },
        } }),
    beforeCreate() {
        Object.defineProperty(this, 'children', {
            get: () => this.selectAllComponents('.van-picker__column') || [],
        });
    },
    methods: {
        noop() { },
        setColumns() {
            const { data } = this;
            const columns = this.simple ? [{ values: data.columns }] : data.columns;
            const stack = columns.map((column, index) => this.setColumnValues(index, column.values));
            return Promise.all(stack);
        },
        emit(event) {
            const { type } = event.currentTarget.dataset;
            if (this.simple) {
                this.$emit(type, {
                    value: this.getColumnValue(0),
                    index: this.getColumnIndex(0),
                });
            }
            else {
                this.$emit(type, {
                    value: this.getValues(),
                    index: this.getIndexes(),
                });
            }
        },
        onChange(event) {
            if (this.simple) {
                this.$emit('change', {
                    picker: this,
                    value: this.getColumnValue(0),
                    index: this.getColumnIndex(0),
                });
            }
            else {
                this.$emit('change', {
                    picker: this,
                    value: this.getValues(),
                    index: event.currentTarget.dataset.index,
                });
            }
        },
        // get column instance by index
        getColumn(index) {
            return this.children[index];
        },
        // get column value by index
        getColumnValue(index) {
            const column = this.getColumn(index);
            return column && column.getValue();
        },
        // set column value by index
        setColumnValue(index, value) {
            const column = this.getColumn(index);
            if (column == null) {
                return Promise.reject(new Error('setColumnValue: 对应列不存在'));
            }
            return column.setValue(value);
        },
        // get column option index by column index
        getColumnIndex(columnIndex) {
            return (this.getColumn(columnIndex) || {}).data.currentIndex;
        },
        // set column option index by column index
        setColumnIndex(columnIndex, optionIndex) {
            const column = this.getColumn(columnIndex);
            if (column == null) {
                return Promise.reject(new Error('setColumnIndex: 对应列不存在'));
            }
            return column.setIndex(optionIndex);
        },
        // get options of column by index
        getColumnValues(index) {
            return (this.children[index] || {}).data.options;
        },
        // set options of column by index
        setColumnValues(index, options, needReset = true) {
            const column = this.children[index];
            if (column == null) {
                return Promise.reject(new Error('setColumnValues: 对应列不存在'));
            }
            const isSame = JSON.stringify(column.data.options) === JSON.stringify(options);
            if (isSame) {
                return Promise.resolve();
            }
            return column.set({ options }).then(() => {
                if (needReset) {
                    column.setIndex(0);
                }
            });
        },
        // get values of all columns
        getValues() {
            return this.children.map((child) => child.getValue());
        },
        // set values of all columns
        setValues(values) {
            const stack = values.map((value, index) => this.setColumnValue(index, value));
            return Promise.all(stack);
        },
        // get indexes of all columns
        getIndexes() {
            return this.children.map((child) => child.data.currentIndex);
        },
        // set indexes of all columns
        setIndexes(indexes) {
            const stack = indexes.map((optionIndex, columnIndex) => this.setColumnIndex(columnIndex, optionIndex));
            return Promise.all(stack);
        },
    },
});
export default global['__wxComponents']['vant/dist/picker/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-picker{-webkit-text-size-adjust:100%;background-color:var(--picker-background-color,#fff);overflow:hidden;position:relative;-webkit-user-select:none;user-select:none}.van-picker__toolbar{display:flex;height:var(--picker-toolbar-height,44px);justify-content:space-between;line-height:var(--picker-toolbar-height,44px)}.van-picker__cancel,.van-picker__confirm{font-size:var(--picker-action-font-size,14px);padding:var(--picker-action-padding,0 16px)}.van-picker__cancel--hover,.van-picker__confirm--hover{opacity:.7}.van-picker__confirm{color:var(--picker-confirm-action-color,#576b95)}.van-picker__cancel{color:var(--picker-cancel-action-color,#969799)}.van-picker__title{font-size:var(--picker-option-font-size,16px);font-weight:var(--font-weight-bold,500);max-width:50%;text-align:center}.van-picker__columns{display:flex;position:relative}.van-picker__column{flex:1 1;width:0}.van-picker__loading{align-items:center;background-color:var(--picker-loading-mask-color,hsla(0,0%,100%,.9));bottom:0;display:flex;justify-content:center;left:0;position:absolute;right:0;top:0;z-index:4}.van-picker__mask{-webkit-backface-visibility:hidden;backface-visibility:hidden;background-image:linear-gradient(180deg,hsla(0,0%,100%,.9),hsla(0,0%,100%,.4)),linear-gradient(0deg,hsla(0,0%,100%,.9),hsla(0,0%,100%,.4));background-position:top,bottom;background-repeat:no-repeat;height:100%;left:0;top:0;width:100%;z-index:2}.van-picker__frame,.van-picker__mask{pointer-events:none;position:absolute}.van-picker__frame{left:16px;right:16px;top:50%;transform:translateY(-50%);z-index:1}
</style>