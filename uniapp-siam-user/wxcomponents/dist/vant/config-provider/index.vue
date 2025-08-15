<template>
<uni-shadow-root class="dist-vant-config-provider-index"><view class="van-config-provider" :style="computed.mapThemeVarsToCSSVars(themeVars)">
  <slot></slot>
</view></uni-shadow-root>
</template>
<wxs src="./index.wxs" module="computed"></wxs>
<script>

global['__wxRoute'] = 'dist/vant/config-provider/index'
import { VantComponent } from '../common/component';
VantComponent({
    props: {
        themeVars: {
            type: Object,
            value: {},
        },
    },
});
export default global['__wxComponents']['dist/vant/config-provider/index']
</script>
<style platform="mp-weixin">

</style>