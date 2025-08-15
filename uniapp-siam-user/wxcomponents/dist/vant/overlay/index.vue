<template>
<uni-shadow-root class="dist-vant-overlay-index"><root-portal v-if="rootPortal">
  <include src="./overlay.wxml"></include>
</root-portal>

<include v-else src="./overlay.wxml"></include></uni-shadow-root>
</template>

<script>

const __wxTemplateComponentProps = {}
import __wxTemplateComponent0 from './overlay.vue'

import VanTransition from '../transition/index.vue'
global['__wxVueOptions'] = {components:{'van-transition': VanTransition,}}

global['__wxRoute'] = 'dist/vant/overlay/index'
import { VantComponent } from '../common/component';
VantComponent({
    props: {
        show: Boolean,
        customStyle: String,
        duration: {
            type: null,
            value: 300,
        },
        zIndex: {
            type: Number,
            value: 1,
        },
        lockScroll: {
            type: Boolean,
            value: true,
        },
        rootPortal: {
            type: Boolean,
            value: false,
        },
    },
    methods: {
        onClick() {
            this.$emit('click');
        },
        // for prevent touchmove
        noop() { },
    },
});
export default global['__wxComponents']['dist/vant/overlay/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-overlay{background-color:var(--overlay-background-color,rgba(0,0,0,.7));height:100%;left:0;position:fixed;top:0;width:100%}
</style>