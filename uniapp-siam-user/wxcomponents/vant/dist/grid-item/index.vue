<template>
<uni-shadow-root class="vant-dist-grid-item-index"><view :class="'custom-class '+(utils.bem('grid-item', { square }))" :style="computed.wrapperStyle({ square, gutter, columnNum, index })" @click="onClick">
  <view :class="'content-class '+(utils.bem('grid-item__content', [direction, { center, square, reverse, clickable, surround: border && gutter }]))+' '+(border ? 'van-hairline--surround' : '')" :style="computed.contentStyle({ square, gutter })">
    <block v-if="useSlot">
      <slot></slot>
    </block>
    <block v-else>
      <view class="van-grid-item__icon icon-class">
        <van-icon v-if="icon" :name="icon" :color="iconColor" :class-prefix="iconPrefix" :dot="dot" :info="badge || info" :size="iconSize"></van-icon>
        <slot v-else name="icon"></slot>
      </view>
      <view class="van-grid-item__text text-class">
        <text v-if="text">{{ text }}</text>
        <slot v-else name="text"></slot>
      </view>
    </block>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs><wxs src="./index.wxs" module="computed"></wxs>
<script>
import VanIcon from '../icon/index.vue'
global['__wxVueOptions'] = {components:{'van-icon': VanIcon}}

global['__wxRoute'] = 'vant/dist/grid-item/index'
import { VantComponent } from '../common/component';
import { useParent } from '../common/relation';
import { link } from '../mixins/link';
VantComponent({
    relation: useParent('grid'),
    classes: ['content-class', 'icon-class', 'text-class'],
    mixins: [link],
    props: {
        icon: String,
        iconColor: String,
        iconPrefix: {
            type: String,
            value: 'van-icon',
        },
        dot: Boolean,
        info: null,
        badge: null,
        text: String,
        useSlot: Boolean,
    },
    data: {
        viewStyle: '',
    },
    mounted() {
        this.updateStyle();
    },
    methods: {
        updateStyle() {
            if (!this.parent) {
                return;
            }
            const { data, children } = this.parent;
            const { columnNum, border, square, gutter, clickable, center, direction, reverse, iconSize, } = data;
            this.setData({
                center,
                border,
                square,
                gutter,
                clickable,
                direction,
                reverse,
                iconSize,
                index: children.indexOf(this),
                columnNum,
            });
        },
        onClick() {
            this.$emit('click');
            this.jumpLink();
        },
    },
});
export default global['__wxComponents']['vant/dist/grid-item/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-grid-item{box-sizing:border-box;float:left;position:relative}.van-grid-item--square{height:0}.van-grid-item__content{background-color:var(--grid-item-content-background-color,#fff);box-sizing:border-box;display:flex;flex-direction:column;height:100%;padding:var(--grid-item-content-padding,16px 8px)}.van-grid-item__content:after{border-width:0 1px 1px 0;z-index:1}.van-grid-item__content--surround:after{border-width:1px}.van-grid-item__content--center{align-items:center;justify-content:center}.van-grid-item__content--square{left:0;position:absolute;right:0;top:0}.van-grid-item__content--horizontal{flex-direction:row}.van-grid-item__content--horizontal .van-grid-item__text{margin:0 0 0 8px}.van-grid-item__content--reverse{flex-direction:column-reverse}.van-grid-item__content--reverse .van-grid-item__text{margin:0 0 8px}.van-grid-item__content--horizontal.van-grid-item__content--reverse{flex-direction:row-reverse}.van-grid-item__content--horizontal.van-grid-item__content--reverse .van-grid-item__text{margin:0 8px 0 0}.van-grid-item__content--clickable:active{background-color:var(--grid-item-content-active-color,#f2f3f5)}.van-grid-item__icon{align-items:center;display:flex;font-size:var(--grid-item-icon-size,26px);height:var(--grid-item-icon-size,26px)}.van-grid-item__text{word-wrap:break-word;color:var(--grid-item-text-color,#646566);font-size:var(--grid-item-text-font-size,12px)}.van-grid-item__icon+.van-grid-item__text{margin-top:8px}
</style>