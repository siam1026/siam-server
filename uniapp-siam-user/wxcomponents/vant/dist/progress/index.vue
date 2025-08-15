<template>
<uni-shadow-root class="vant-dist-progress-index"><view class="van-progress custom-class" :style="computed.rootStyle({ strokeWidth, trackColor })">
  <view class="van-progress__portion" :style="computed.portionStyle({ percentage, inactive, color })">
    <view v-if="showPivot && computed.pivotText(pivotText, percentage)" :style="computed.pivotStyle({ textColor, pivotColor, inactive, color, right })" class="van-progress__pivot">
      {{ computed.pivotText(pivotText, percentage) }}
    </view>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs><wxs src="./index.wxs" module="computed"></wxs>
<script>

global['__wxRoute'] = 'vant/dist/progress/index'
import { VantComponent } from '../common/component';
import { BLUE } from '../common/color';
import { getRect } from '../common/utils';
VantComponent({
    props: {
        inactive: Boolean,
        percentage: {
            type: Number,
            observer: 'setLeft',
        },
        pivotText: String,
        pivotColor: String,
        trackColor: String,
        showPivot: {
            type: Boolean,
            value: true,
        },
        color: {
            type: String,
            value: BLUE,
        },
        textColor: {
            type: String,
            value: '#fff',
        },
        strokeWidth: {
            type: null,
            value: 4,
        },
    },
    data: {
        right: 0,
    },
    mounted() {
        this.setLeft();
    },
    methods: {
        setLeft() {
            Promise.all([
                getRect(this, '.van-progress'),
                getRect(this, '.van-progress__pivot'),
            ]).then(([portion, pivot]) => {
                if (portion && pivot) {
                    this.setData({
                        right: (pivot.width * (this.data.percentage - 100)) / 100,
                    });
                }
            });
        },
    },
});
export default global['__wxComponents']['vant/dist/progress/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-progress{background:var(--progress-background-color,#ebedf0);border-radius:var(--progress-height,4px);height:var(--progress-height,4px);position:relative}.van-progress__portion{background:var(--progress-color,#1989fa);border-radius:inherit;height:100%;left:0;position:absolute}.van-progress__pivot{background-color:var(--progress-pivot-background-color,#1989fa);border-radius:1em;box-sizing:border-box;color:var(--progress-pivot-text-color,#fff);font-size:var(--progress-pivot-font-size,10px);line-height:var(--progress-pivot-line-height,1.6);min-width:3.6em;padding:var(--progress-pivot-padding,0 5px);position:absolute;text-align:center;top:50%;transform:translateY(-50%);word-break:keep-all}
</style>