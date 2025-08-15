<template>
<uni-shadow-root class="vant-dist-radio-index"><view :class="(utils.bem('radio', [direction]))+' custom-class'">
  <view v-if="labelPosition === 'left'" :class="(utils.bem('radio__label', [labelPosition, { disabled: disabled || parentDisabled }]))+' label-class'" @click="onClickLabel">
    <slot></slot>
  </view>
  <view class="van-radio__icon-wrap" :style="'font-size: '+(utils.addUnit(iconSize))" @click="onChange">
    <slot v-if="useIconSlot" name="icon"></slot>
    <van-icon v-else name="success" :class="utils.bem('radio__icon', [shape, { disabled: disabled || parentDisabled, checked: value === name }])" :style="computed.iconStyle({ iconSize, checkedColor, disabled, parentDisabled, value, name })" custom-class="icon-class" :custom-style="computed.iconCustomStyle({ iconSize })"></van-icon>
  </view>
  <view v-if="labelPosition === 'right'" :class="'label-class '+(utils.bem('radio__label', [labelPosition, { disabled: disabled || parentDisabled }]))" @click="onClickLabel">
    <slot></slot>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs><wxs src="./index.wxs" module="computed"></wxs>
<script>
import VanIcon from '../icon/index.vue'
global['__wxVueOptions'] = {components:{'van-icon': VanIcon}}

global['__wxRoute'] = 'vant/dist/radio/index'
import { canIUseModel } from '../common/version';
import { VantComponent } from '../common/component';
import { useParent } from '../common/relation';
VantComponent({
    field: true,
    relation: useParent('radio-group', function () {
        this.updateFromParent();
    }),
    classes: ['icon-class', 'label-class'],
    props: {
        name: null,
        value: null,
        disabled: Boolean,
        useIconSlot: Boolean,
        checkedColor: String,
        labelPosition: {
            type: String,
            value: 'right',
        },
        labelDisabled: Boolean,
        shape: {
            type: String,
            value: 'round',
        },
        iconSize: {
            type: null,
            value: 20,
        },
    },
    data: {
        direction: '',
        parentDisabled: false,
    },
    methods: {
        updateFromParent() {
            if (!this.parent) {
                return;
            }
            const { value, disabled: parentDisabled, direction } = this.parent.data;
            this.setData({
                value,
                direction,
                parentDisabled,
            });
        },
        emitChange(value) {
            const instance = this.parent || this;
            instance.$emit('input', value);
            instance.$emit('change', value);
            if (canIUseModel()) {
                instance.setData({ value });
            }
        },
        onChange() {
            if (!this.data.disabled && !this.data.parentDisabled) {
                this.emitChange(this.data.name);
            }
        },
        onClickLabel() {
            const { disabled, parentDisabled, labelDisabled, name } = this.data;
            if (!(disabled || parentDisabled) && !labelDisabled) {
                this.emitChange(name);
            }
        },
    },
});
export default global['__wxComponents']['vant/dist/radio/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-radio{align-items:center;display:flex;overflow:hidden;-webkit-user-select:none;user-select:none}.van-radio__icon-wrap{flex:none}.van-radio--horizontal{margin-right:var(--padding-sm,12px)}.van-radio__icon{align-items:center;border:1px solid var(--radio-border-color,#c8c9cc);box-sizing:border-box;color:transparent;display:flex;font-size:var(--radio-size,20px);height:1em;justify-content:center;text-align:center;transition-duration:var(--radio-transition-duration,.2s);transition-property:color,border-color,background-color;width:1em}.van-radio__icon--round{border-radius:100%}.van-radio__icon--checked{background-color:var(--radio-checked-icon-color,#1989fa);border-color:var(--radio-checked-icon-color,#1989fa);color:#fff}.van-radio__icon--disabled{background-color:var(--radio-disabled-background-color,#ebedf0);border-color:var(--radio-disabled-icon-color,#c8c9cc)}.van-radio__icon--disabled.van-radio__icon--checked{color:var(--radio-disabled-icon-color,#c8c9cc)}.van-radio__label{word-wrap:break-word;color:var(--radio-label-color,#323233);line-height:var(--radio-size,20px);padding-left:var(--radio-label-margin,10px)}.van-radio__label--left{float:left;margin:0 var(--radio-label-margin,10px) 0 0}.van-radio__label--disabled{color:var(--radio-disabled-label-color,#c8c9cc)}.van-radio__label:empty{margin:0}
</style>