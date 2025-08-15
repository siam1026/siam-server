<template>
<uni-shadow-root class="vant-dist-overlay-overlay"><van-transition :show="show" custom-class="van-overlay custom-class" :custom-style="'z-index: '+(zIndex)+'; '+(customStyle)" :duration="duration" @click.native="onClick" @touchmove.native.stop.prevent="_$self[(lockScroll ? 'noop' : '')||'_$noop']($event)">
  <slot></slot>
</van-transition></uni-shadow-root>
</template>

<script>

global['__wxRoute'] = 'vant/dist/overlay/overlay'

Component({})

export default global['__wxComponents']['vant/dist/overlay/overlay']
</script>
<style platform="mp-weixin">

</style>