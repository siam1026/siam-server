<template>
<uni-shadow-root class="vant-dist-share-sheet-options"><view :class="utils.bem('share-sheet__options', { border: showBorder })">
  <view v-for="(item,index) in (options)" :key="item.index" class="van-share-sheet__option" :data-index="index" @click="onSelect">
    <button class="van-share-sheet__button" :open-type="item.openType">
      <image :src="computed.getIconURL(item.icon)" class="van-share-sheet__icon"></image> 
      <view v-if="item.name" class="van-share-sheet__name">{{ item.name }}</view>
      <view v-if="item.description" class="van-share-sheet__option-description">
        {{ item.description }}
      </view>
    </button>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs><wxs src="./options.wxs" module="computed"></wxs>
<script>

global['__wxRoute'] = 'vant/dist/share-sheet/options'
import { VantComponent } from '../common/component';
VantComponent({
    props: {
        options: Array,
        showBorder: Boolean,
    },
    methods: {
        onSelect(event) {
            const { index } = event.currentTarget.dataset;
            const option = this.data.options[index];
            this.$emit('select', Object.assign(Object.assign({}, option), { index }));
        },
    },
});
export default global['__wxComponents']['vant/dist/share-sheet/options']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-share-sheet__options{-webkit-overflow-scrolling:touch;display:flex;overflow-x:auto;overflow-y:visible;padding:16px 0 16px 8px;position:relative}.van-share-sheet__options--border:before{border-top:1px solid #ebedf0;box-sizing:border-box;content:" ";left:16px;pointer-events:none;position:absolute;right:0;top:0;transform:scaleY(.5);transform-origin:center}.van-share-sheet__options::-webkit-scrollbar{height:0}.van-share-sheet__option{align-items:center;display:flex;flex-direction:column;-webkit-user-select:none;user-select:none}.van-share-sheet__option:active{opacity:.7}.van-share-sheet__button{background-color:initial;border:0;height:auto;line-height:inherit;padding:0}.van-share-sheet__button:after{border:0}.van-share-sheet__icon{height:48px;margin:0 16px;width:48px}.van-share-sheet__name{color:#646566;font-size:12px;margin-top:8px;padding:0 4px}.van-share-sheet__option-description{color:#c8c9cc;font-size:12px;padding:0 4px}
</style>