<template>
<uni-shadow-root class="vant-calendar-components-header-index"><view class="van-calendar__header">
  <block v-if="showTitle">
    <view class="van-calendar__header-title"><slot name="title"></slot></view>
    <view class="van-calendar__header-title">{{ title }}</view>
  </block>

  <view v-if="showSubtitle" class="van-calendar__header-subtitle">
    {{ subtitle }}
  </view>

  <view class="van-calendar__weekdays">
    <view v-for="(item,index) in (weekdays)" :key="item.index" class="van-calendar__weekday">
      {{ item }}
    </view>
  </view>
</view></uni-shadow-root>
</template>

<script>

global['__wxRoute'] = 'vant/calendar/components/header/index'
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var component_1 = require("../../../common/component");
component_1.VantComponent({
    props: {
        title: {
            type: String,
            value: '日期选择'
        },
        subtitle: String,
        showTitle: Boolean,
        showSubtitle: Boolean
    },
    data: {
        weekdays: ['日', '一', '二', '三', '四', '五', '六']
    },
    methods: {}
});
export default global['__wxComponents']['vant/calendar/components/header/index']
</script>
<style platform="mp-weixin">
@import '../../../common/index.css';.van-calendar__header{-webkit-flex-shrink:0;flex-shrink:0;box-shadow:0 2px 10px rgba(125,126,128,.16);box-shadow:var(--calendar-header-box-shadow,0 2px 10px rgba(125,126,128,.16))}.van-calendar__header-subtitle,.van-calendar__header-title{text-align:center;height:44px;height:var(--calendar-header-title-height,44px);font-weight:500;font-weight:var(--font-weight-bold,500);line-height:44px;line-height:var(--calendar-header-title-height,44px)}.van-calendar__header-title+.van-calendar__header-title,.van-calendar__header-title:empty{display:none}.van-calendar__header-title:empty+.van-calendar__header-title{display:block!important}.van-calendar__weekdays{display:-webkit-flex;display:flex}.van-calendar__weekday{-webkit-flex:1;flex:1;text-align:center;font-size:12px;font-size:var(--calendar-weekdays-font-size,12px);line-height:30px;line-height:var(--calendar-weekdays-height,30px)}
</style>