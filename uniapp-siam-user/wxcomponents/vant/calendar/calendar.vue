<template>
<uni-shadow-root class="vant-calendar-calendar"><template v-if="wxTemplateName === 'calendar'">
  <view class="van-calendar">
    <header :title="title" :showTitle="showTitle" :subtitle="subtitle" :showSubtitle="showSubtitle">
      <slot name="title" slot="title"></slot>
    </header>

    <scroll-view class="van-calendar__body" scroll-y :scroll-into-view="scrollIntoView">
      <month v-for="(item,index) in (computed.getMonths(minDate, maxDate))" :key="item.index" :id="'month'+(index)" class="month" :data-date="item" :date="item" :type="type" :color="color" :minDate="minDate" :maxDate="maxDate" :showMark="showMark" :formatter="formatter" :rowHeight="rowHeight" :currentDate="currentDate" :showSubtitle="showSubtitle" :allowSameDay="allowSameDay" :showMonthTitle="index !== 0 || !showSubtitle" @click="_$self.$parent.$parent[('onClickDay')]($event)"></month>
    </scroll-view>

    <view :class="'van-calendar__footer '+(safeAreaInsetBottom ? 'van-calendar__footer--safe-area-inset-bottom' : '')">
      <slot name="footer"></slot>
    </view>

    <view :class="'van-calendar__footer '+(safeAreaInsetBottom ? 'van-calendar__footer--safe-area-inset-bottom' : '')">
      <van-button v-if="showConfirm" round block type="danger" :color="color" custom-class="van-calendar__confirm" :disabled="computed.getButtonDisabled(type, currentDate)" nativeType="text" @click="_$self.$parent.$parent[('onConfirm')]($event)">
        {{ computed.getButtonDisabled(type, currentDate) ? confirmDisabledText : confirmText }}
      </van-button>
    </view>
  </view>
</template></uni-shadow-root>
</template>
<wxs src="./index.wxs" module="computed"></wxs>
<script>

global['__wxRoute'] = 'vant/calendar/calendar'

Component({})

export default global['__wxComponents']['vant/calendar/calendar']
</script>
<style platform="mp-weixin">

</style>