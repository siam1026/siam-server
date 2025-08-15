<template>
<uni-shadow-root class="vant-dist-calendar-components-month-index"><view class="van-calendar__month" :style="computed.getMonthStyle(visible, date, rowHeight)">
  <view v-if="showMonthTitle" class="van-calendar__month-title">
    {{ computed.formatMonthTitle(date) }}
  </view>

  <view v-if="visible" class="van-calendar__days">
    <view v-if="showMark" class="van-calendar__month-mark">
      {{ computed.getMark(date) }}
    </view>

    <view v-for="(item,index) in (days)" :key="item.index" :style="computed.getDayStyle(item.type, index, date, rowHeight, color, firstDayOfWeek)" :class="(utils.bem('calendar__day', [item.type]))+' '+(item.className)" :data-index="index" @click="onClick">
      <view v-if="item.type === 'selected'" class="van-calendar__selected-day" :style="'width: '+(rowHeight)+'px; height: '+(rowHeight)+'px; background: '+(color)">
        <view v-if="item.topInfo" class="van-calendar__top-info">{{ item.topInfo }}</view>
        {{ item.text }}
        <view v-if="item.bottomInfo" class="van-calendar__bottom-info">
          {{ item.bottomInfo }}
        </view>
      </view>

      <view v-else>
        <view v-if="item.topInfo" class="van-calendar__top-info">{{ item.topInfo }}</view>
        {{ item.text }}
        <view v-if="item.bottomInfo" class="van-calendar__bottom-info">
          {{ item.bottomInfo }}
        </view>
      </view>
    </view>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="./index.wxs" module="computed"></wxs><wxs src="../../../wxs/utils.wxs" module="utils"></wxs>
<script>

global['__wxRoute'] = 'vant/dist/calendar/components/month/index'
import { VantComponent } from '../../../common/component';
import { getMonthEndDay, compareDay, getPrevDay, getNextDay, } from '../../utils';
VantComponent({
    props: {
        date: {
            type: null,
            observer: 'setDays',
        },
        type: {
            type: String,
            observer: 'setDays',
        },
        color: String,
        minDate: {
            type: null,
            observer: 'setDays',
        },
        maxDate: {
            type: null,
            observer: 'setDays',
        },
        showMark: Boolean,
        rowHeight: null,
        formatter: {
            type: null,
            observer: 'setDays',
        },
        currentDate: {
            type: null,
            observer: 'setDays',
        },
        firstDayOfWeek: {
            type: Number,
            observer: 'setDays',
        },
        allowSameDay: Boolean,
        showSubtitle: Boolean,
        showMonthTitle: Boolean,
    },
    data: {
        visible: true,
        days: [],
    },
    methods: {
        onClick(event) {
            const { index } = event.currentTarget.dataset;
            const item = this.data.days[index];
            if (item.type !== 'disabled') {
                this.$emit('click', item);
            }
        },
        setDays() {
            const days = [];
            const startDate = new Date(this.data.date);
            const year = startDate.getFullYear();
            const month = startDate.getMonth();
            const totalDay = getMonthEndDay(startDate.getFullYear(), startDate.getMonth() + 1);
            for (let day = 1; day <= totalDay; day++) {
                const date = new Date(year, month, day);
                const type = this.getDayType(date);
                let config = {
                    date,
                    type,
                    text: day,
                    bottomInfo: this.getBottomInfo(type),
                };
                if (this.data.formatter) {
                    config = this.data.formatter(config);
                }
                days.push(config);
            }
            this.setData({ days });
        },
        getMultipleDayType(day) {
            const { currentDate } = this.data;
            if (!Array.isArray(currentDate)) {
                return '';
            }
            const isSelected = (date) => currentDate.some((item) => compareDay(item, date) === 0);
            if (isSelected(day)) {
                const prevDay = getPrevDay(day);
                const nextDay = getNextDay(day);
                const prevSelected = isSelected(prevDay);
                const nextSelected = isSelected(nextDay);
                if (prevSelected && nextSelected) {
                    return 'multiple-middle';
                }
                if (prevSelected) {
                    return 'end';
                }
                return nextSelected ? 'start' : 'multiple-selected';
            }
            return '';
        },
        getRangeDayType(day) {
            const { currentDate, allowSameDay } = this.data;
            if (!Array.isArray(currentDate)) {
                return '';
            }
            const [startDay, endDay] = currentDate;
            if (!startDay) {
                return '';
            }
            const compareToStart = compareDay(day, startDay);
            if (!endDay) {
                return compareToStart === 0 ? 'start' : '';
            }
            const compareToEnd = compareDay(day, endDay);
            if (compareToStart === 0 && compareToEnd === 0 && allowSameDay) {
                return 'start-end';
            }
            if (compareToStart === 0) {
                return 'start';
            }
            if (compareToEnd === 0) {
                return 'end';
            }
            if (compareToStart > 0 && compareToEnd < 0) {
                return 'middle';
            }
            return '';
        },
        getDayType(day) {
            const { type, minDate, maxDate, currentDate } = this.data;
            if (compareDay(day, minDate) < 0 || compareDay(day, maxDate) > 0) {
                return 'disabled';
            }
            if (type === 'single') {
                return compareDay(day, currentDate) === 0 ? 'selected' : '';
            }
            if (type === 'multiple') {
                return this.getMultipleDayType(day);
            }
            /* istanbul ignore else */
            if (type === 'range') {
                return this.getRangeDayType(day);
            }
            return '';
        },
        getBottomInfo(type) {
            if (this.data.type === 'range') {
                if (type === 'start') {
                    return '开始';
                }
                if (type === 'end') {
                    return '结束';
                }
                if (type === 'start-end') {
                    return '开始/结束';
                }
            }
        },
    },
});
export default global['__wxComponents']['vant/dist/calendar/components/month/index']
</script>
<style platform="mp-weixin">
@import '../../../common/index.css';.van-calendar{background-color:var(--calendar-background-color,#fff);display:flex;flex-direction:column;height:100%}.van-calendar__month-title{font-size:var(--calendar-month-title-font-size,14px);font-weight:var(--font-weight-bold,500);height:var(--calendar-header-title-height,44px);line-height:var(--calendar-header-title-height,44px);text-align:center}.van-calendar__days{display:flex;flex-wrap:wrap;position:relative;-webkit-user-select:none;user-select:none}.van-calendar__month-mark{color:var(--calendar-month-mark-color,rgba(242,243,245,.8));font-size:var(--calendar-month-mark-font-size,160px);left:50%;pointer-events:none;position:absolute;top:50%;transform:translate(-50%,-50%);z-index:0}.van-calendar__day,.van-calendar__selected-day{align-items:center;display:flex;justify-content:center;text-align:center}.van-calendar__day{font-size:var(--calendar-day-font-size,16px);height:var(--calendar-day-height,64px);position:relative;width:14.285%}.van-calendar__day--end,.van-calendar__day--multiple-middle,.van-calendar__day--multiple-selected,.van-calendar__day--start,.van-calendar__day--start-end{background-color:var(--calendar-range-edge-background-color,#ee0a24);color:var(--calendar-range-edge-color,#fff)}.van-calendar__day--start{border-radius:4px 0 0 4px}.van-calendar__day--end{border-radius:0 4px 4px 0}.van-calendar__day--multiple-selected,.van-calendar__day--start-end{border-radius:4px}.van-calendar__day--middle{color:var(--calendar-range-middle-color,#ee0a24)}.van-calendar__day--middle:after{background-color:currentColor;bottom:0;content:"";left:0;opacity:var(--calendar-range-middle-background-opacity,.1);position:absolute;right:0;top:0}.van-calendar__day--disabled{color:var(--calendar-day-disabled-color,#c8c9cc);cursor:default}.van-calendar__bottom-info,.van-calendar__top-info{font-size:var(--calendar-info-font-size,10px);left:0;line-height:var(--calendar-info-line-height,14px);position:absolute;right:0}@media (max-width:350px){.van-calendar__bottom-info,.van-calendar__top-info{font-size:9px}}.van-calendar__top-info{top:6px}.van-calendar__bottom-info{bottom:6px}.van-calendar__selected-day{background-color:var(--calendar-selected-day-background-color,#ee0a24);border-radius:4px;color:var(--calendar-selected-day-color,#fff);height:var(--calendar-selected-day-size,54px);width:var(--calendar-selected-day-size,54px)}
</style>