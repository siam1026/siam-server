<template>
<uni-shadow-root class="vant-dist-popup-popup"><view v-if="inited" :class="'custom-class '+(classes)+' '+(utils.bem('popup', [position, { round, safe: safeAreaInsetBottom, safeTop: safeAreaInsetTop, safeTabBar: safeAreaTabBar }]))" :style="computed.popupStyle({ zIndex, currentDuration, display, customStyle })" @transitionend="onTransitionEnd">
  <slot></slot>
  <van-icon v-if="closeable" :name="closeIcon" :class="'close-icon-class van-popup__close-icon van-popup__close-icon--'+(closeIconPosition)" @click.native="onClickCloseIcon"></van-icon>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs><wxs src="./index.wxs" module="computed"></wxs>
<script>

global['__wxRoute'] = 'vant/dist/popup/popup'

Component({})

export default global['__wxComponents']['vant/dist/popup/popup']
</script>
<style platform="mp-weixin">

</style>