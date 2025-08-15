<template>
<uni-shadow-root class="dist-vant-calendar-calendar"><view class="van-calendar">
  <header :title="title" :showTitle="showTitle" :subtitle="subtitle" :showSubtitle="showSubtitle" :firstDayOfWeek="firstDayOfWeek" @click-subtitle="onClickSubtitle">
    <slot name="title" slot="title"></slot>
  </header>

  <scroll-view class="van-calendar__body" scroll-y :scroll-into-view="scrollIntoView">
    <month v-for="(item,index) in (computed.getMonths(minDate, maxDate))" :key="item.index" :id="'month'+(index)" class="month" :data-date="item" :date="item" :type="type" :color="color" :minDate="minDate" :maxDate="maxDate" :showMark="showMark" :formatter="formatter" :rowHeight="rowHeight" :currentDate="currentDate" :showSubtitle="showSubtitle" :allowSameDay="allowSameDay" :showMonthTitle="index !== 0 || !showSubtitle" :firstDayOfWeek="firstDayOfWeek" @click="onClickDay"></month>
  </scroll-view>

  <view :class="utils.bem('calendar__footer', { safeAreaInsetBottom })">
    <slot name="footer"></slot>
  </view>

  <view :class="utils.bem('calendar__footer', { safeAreaInsetBottom })">
    <van-button v-if="showConfirm" round block type="danger" :color="color" custom-class="van-calendar__confirm" :disabled="computed.getButtonDisabled(type, currentDate, minRange)" nativeType="text" @click="onConfirm">
      {{
        computed.getButtonDisabled(type, currentDate, minRange)
          ? confirmDisabledText
          : confirmText
      }}
    </van-button>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="./index.wxs" module="computed"></wxs><wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>

global['__wxRoute'] = 'dist/vant/calendar/calendar'

Component({})

export default global['__wxComponents']['dist/vant/calendar/calendar']
</script>
<style platform="mp-weixin">

</style>