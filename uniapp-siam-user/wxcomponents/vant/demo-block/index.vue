<template>
<uni-shadow-root class="vant-demo-block-index"><view :class="'custom-class demo-block van-clearfix '+(padding ? 'demo-block--padding' : '')">
  <view v-if="title" class="demo-block__title">{{ title }}</view>
  <slot></slot>
</view></uni-shadow-root>
</template>

<script>

global['__wxRoute'] = 'vant/demo-block/index'
Component({
  properties: {
    title: String,
    padding: Boolean
  },

  externalClasses: ['custom-class']
});
export default global['__wxComponents']['vant/demo-block/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';

.demo-block__title {
  margin: 0;
  font-weight: 400;
  font-size: 14px;
  color: rgba(69,90,100,.6);
  padding: 20px 15px 15px;
}

.demo-block--padding {
  padding: 0 15px;
}

.demo-block--padding .demo-block__title {
  padding-left: 0;
}
</style>