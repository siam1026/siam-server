<template>
<uni-shadow-root class="dist-vant-goods-action-button-index"><van-button :id="id" :button-id="buttonId" :lang="lang" :type="type" :size="size" :color="color" :plain="plain" :loading="loading" :disabled="disabled" :open-type="openType" :class="utils.bem('goods-action-button', [type, { first: isFirst, last: isLast, plain: plain }])" custom-class="van-goods-action-button__inner custom-class" :business-id="businessId" :session-from="sessionFrom" :app-parameter="appParameter" :send-message-img="sendMessageImg" :send-message-path="sendMessagePath" :show-message-card="showMessageCard" :send-message-title="sendMessageTitle" @click="onClick" @error="onError" @contact="onContact" @opensetting="onOpenSetting" @getuserinfo="onGetUserInfo" @agreeprivacyauthorization="onAgreePrivacyAuthorization" @getRealTimePhoneNumber="onGetRealTimePhoneNumber" @getphonenumber="onGetPhoneNumber" @launchapp="onLaunchApp">
  {{ text }}
  <slot></slot>
</van-button></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>
import VanButton from '../button/index.vue'
global['__wxVueOptions'] = {components:{'van-button': VanButton}}

global['__wxRoute'] = 'dist/vant/goods-action-button/index'
import { VantComponent } from '../common/component';
import { useParent } from '../common/relation';
import { button } from '../mixins/button';
import { link } from '../mixins/link';
VantComponent({
    mixins: [link, button],
    relation: useParent('goods-action'),
    props: {
        text: String,
        color: String,
        size: {
            type: String,
            value: 'normal',
        },
        loading: Boolean,
        disabled: Boolean,
        plain: Boolean,
        type: {
            type: String,
            value: 'danger',
        },
    },
    methods: {
        onClick(event) {
            this.$emit('click', event.detail);
            this.jumpLink();
        },
        updateStyle() {
            if (this.parent == null) {
                return;
            }
            const { index } = this;
            const { children = [] } = this.parent;
            this.setData({
                isFirst: index === 0,
                isLast: index === children.length - 1,
            });
        },
    },
});
export default global['__wxComponents']['dist/vant/goods-action-button/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.dist-vant-goods-action-button-index{flex:1}.van-goods-action-button{--button-warning-background-color:var(--goods-action-button-warning-color,linear-gradient(to right,#ffd01e,#ff8917));--button-danger-background-color:var(--goods-action-button-danger-color,linear-gradient(to right,#ff6034,#ee0a24));--button-default-height:var(--goods-action-button-height,40px);--button-line-height:var(--goods-action-button-line-height,20px);--button-plain-background-color:var(--goods-action-button-plain-color,#fff);--button-border-width:0;display:block}.van-goods-action-button--first{--button-border-radius:999px 0 0 var(--goods-action-button-border-radius,999px);margin-left:5px}.van-goods-action-button--last{--button-border-radius:0 999px var(--goods-action-button-border-radius,999px) 0;margin-right:5px}.van-goods-action-button--first.van-goods-action-button--last{--button-border-radius:var(--goods-action-button-border-radius,999px)}.van-goods-action-button--plain{--button-border-width:1px}.van-goods-action-button__inner{font-weight:var(--font-weight-bold,500)!important;width:100%}@media (max-width:321px){.van-goods-action-button{font-size:13px}}
</style>