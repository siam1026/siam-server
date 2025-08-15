<template>
<uni-shadow-root class="vant-goods-action-button-index"><van-button :id="id" :lang="lang" :type="type" :color="color" :plain="plain" :loading="loading" :disabled="disabled" :open-type="openType" :class="utils.bem('goods-action-button', [type, { first: isFirst, last: isLast, plain: plain }])" custom-class="van-goods-action-button__inner" :business-id="businessId" :session-from="sessionFrom" :app-parameter="appParameter" :send-message-img="sendMessageImg" :send-message-path="sendMessagePath" :show-message-card="showMessageCard" :send-message-title="sendMessageTitle" @click="onClick" @error="bindError" @contact="bindContact" @opensetting="bindOpenSetting" @getuserinfo="bindGetUserInfo" @getphonenumber="bindGetPhoneNumber" @launchapp="bindLaunchApp">
  {{ text }}
  <slot></slot>
</van-button></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>
import VanButton from '../button/index.vue'
global['__wxVueOptions'] = {components:{'van-button': VanButton}}

global['__wxRoute'] = 'vant/goods-action-button/index'
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var component_1 = require("../common/component");
var link_1 = require("../mixins/link");
var button_1 = require("../mixins/button");
var open_type_1 = require("../mixins/open-type");
component_1.VantComponent({
    mixins: [link_1.link, button_1.button, open_type_1.openType],
    relation: {
        type: 'ancestor',
        name: 'goods-action',
        current: 'goods-action-button',
    },
    props: {
        text: String,
        color: String,
        loading: Boolean,
        disabled: Boolean,
        plain: Boolean,
        type: {
            type: String,
            value: 'danger'
        }
    },
    mounted: function () {
        this.updateStyle();
    },
    methods: {
        onClick: function (event) {
            this.$emit('click', event.detail);
            this.jumpLink();
        },
        updateStyle: function () {
            var _a = this.parent.children, children = _a === void 0 ? [] : _a;
            var length = children.length;
            var index = children.indexOf(this);
            this.setData({
                isFirst: index === 0,
                isLast: index === length - 1
            });
        }
    }
});
export default global['__wxComponents']['vant/goods-action-button/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.vant-goods-action-button-index{-webkit-flex:1;flex:1}.van-goods-action-button{--button-warning-background-color:linear-gradient(90deg,#ffd01e,#ff8917);--button-warning-background-color:var(--goods-action-button-warning-color,linear-gradient(90deg,#ffd01e,#ff8917));--button-danger-background-color:linear-gradient(90deg,#ff6034,#ee0a24);--button-danger-background-color:var(--goods-action-button-danger-color,linear-gradient(90deg,#ff6034,#ee0a24));--button-default-height:40px;--button-default-height:var(--goods-action-button-height,40px);--button-line-height:20px;--button-line-height:var(--goods-action-button-line-height,20px);--button-plain-background-color:#fff;--button-plain-background-color:var(--goods-action-button-plain-color,#fff);display:block;--button-border-width:0}.van-goods-action-button--first{margin-left:5px;--button-border-radius:20px 0 0 20px;--button-border-radius:var(--goods-action-button-border-radius,20px) 0 0 var(--goods-action-button-border-radius,20px)}.van-goods-action-button--last{margin-right:5px;--button-border-radius:0 20px 20px 0;--button-border-radius:0 var(--goods-action-button-border-radius,20px) var(--goods-action-button-border-radius,20px) 0}.van-goods-action-button--first.van-goods-action-button--last{--button-border-radius:20px;--button-border-radius:var(--goods-action-button-border-radius,20px)}.van-goods-action-button--plain{--button-border-width:1px}.van-goods-action-button__inner{width:100%;font-weight:500!important;font-weight:var(--font-weight-bold,500)!important}@media (max-width:321px){.van-goods-action-button{font-size:13px}}
</style>