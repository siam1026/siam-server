<template>
<uni-shadow-root class="dist-vant-overlay-overlay"><van-transition :show="show" custom-class="van-overlay custom-class" :custom-style="'z-index: '+(zIndex)+'; '+(customStyle)" :duration="duration" @click.native="onClick" @touchmove.native.stop.prevent="_$self[(lockScroll ? 'noop' : '')||'_$noop']($event)">
  <slot></slot>
</van-transition></uni-shadow-root>
</template>

<script>

global['__wxRoute'] = 'dist/vant/overlay/overlay'

Component({})

export default global['__wxComponents']['dist/vant/overlay/overlay']
</script>
<style platform="mp-weixin">

</style>