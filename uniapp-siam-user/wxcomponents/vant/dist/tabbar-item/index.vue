<template>
<uni-shadow-root class="vant-dist-tabbar-item-index"><view :class="(utils.bem('tabbar-item', { active }))+' custom-class'" :style="'color: '+(active ? activeColor : inactiveColor)" @click="onClick">
  <view class="van-tabbar-item__icon">
    <van-icon v-if="icon" :name="icon" :class-prefix="iconPrefix" custom-class="van-tabbar-item__icon__inner"></van-icon>
    <block v-else>
      <slot v-if="active" name="icon-active"></slot>
      <slot v-else name="icon"></slot>
    </block>
    <van-info :dot="dot" :info="info" custom-class="van-tabbar-item__info"></van-info>
  </view>
  <view class="van-tabbar-item__text">
    <slot></slot>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>
import VanIcon from '../icon/index.vue'
import VanInfo from '../info/index.vue'
global['__wxVueOptions'] = {components:{'van-icon': VanIcon,'van-info': VanInfo}}

global['__wxRoute'] = 'vant/dist/tabbar-item/index'
import { VantComponent } from '../common/component';
import { useParent } from '../common/relation';
VantComponent({
    props: {
        info: null,
        name: null,
        icon: String,
        dot: Boolean,
        url: {
            type: String,
            value: '',
        },
        linkType: {
            type: String,
            value: 'redirectTo',
        },
        iconPrefix: {
            type: String,
            value: 'van-icon',
        },
    },
    relation: useParent('tabbar'),
    data: {
        active: false,
        activeColor: '',
        inactiveColor: '',
    },
    methods: {
        onClick() {
            const { parent } = this;
            if (parent) {
                const index = parent.children.indexOf(this);
                const active = this.data.name || index;
                if (active !== this.data.active) {
                    parent.$emit('change', active);
                }
            }
            const { url, linkType } = this.data;
            if (url && wx[linkType]) {
                return wx[linkType]({ url });
            }
            this.$emit('click');
        },
        updateFromParent() {
            const { parent } = this;
            if (!parent) {
                return;
            }
            const index = parent.children.indexOf(this);
            const parentData = parent.data;
            const { data } = this;
            const active = (data.name || index) === parentData.active;
            const patch = {};
            if (active !== data.active) {
                patch.active = active;
            }
            if (parentData.activeColor !== data.activeColor) {
                patch.activeColor = parentData.activeColor;
            }
            if (parentData.inactiveColor !== data.inactiveColor) {
                patch.inactiveColor = parentData.inactiveColor;
            }
            if (Object.keys(patch).length > 0) {
                this.setData(patch);
            }
        },
    },
});
export default global['__wxComponents']['vant/dist/tabbar-item/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.vant-dist-tabbar-item-index{flex:1}.van-tabbar-item{align-items:center;color:var(--tabbar-item-text-color,#646566);display:flex;flex-direction:column;font-size:var(--tabbar-item-font-size,12px);height:100%;justify-content:center;line-height:var(--tabbar-item-line-height,1)}.van-tabbar-item__icon{font-size:var(--tabbar-item-icon-size,22px);margin-bottom:var(--tabbar-item-margin-bottom,4px);position:relative}.van-tabbar-item__icon__inner{display:block;min-width:1em}.van-tabbar-item--active{color:var(--tabbar-item-active-color,#1989fa)}.van-tabbar-item__info{margin-top:2px}
</style>