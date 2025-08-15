<template>
<uni-shadow-root class="vant-dist-action-sheet-index"><van-popup :show="show" position="bottom" :round="round" :z-index="zIndex" :overlay="overlay" custom-class="van-action-sheet custom-class" :safe-area-inset-bottom="safeAreaInsetBottom" :close-on-click-overlay="closeOnClickOverlay" @close="onClickOverlay">
  <view v-if="title" class="van-action-sheet__header">
    {{ title }}
    <van-icon name="cross" custom-class="van-action-sheet__close" @click="onClose"></van-icon>
  </view>
  <view v-if="description" class="van-action-sheet__description van-hairline--bottom">
    {{ description }}
  </view>
  <view v-if="actions && actions.length" class="list-class">
    
    <button v-for="(item,index) in (actions)" :key="item.index" :open-type="item.disabled || item.loading || (canIUseGetUserProfile && item.openType === 'getUserInfo') ? '' : item.openType" :style="item.color ? 'color: ' + item.color : ''" :class="(utils.bem('action-sheet__item', { disabled: item.disabled || item.loading }))+' '+(item.className || '')" hover-class="van-action-sheet__item--hover" :data-index="index" @click="_$self[(item.disabled || item.loading ? '' : 'onSelect')||'_$noop']($event)" @getuserinfo="onGetUserInfo" @contact="onContact" @getphonenumber="onGetPhoneNumber" @error="onError" @launchapp="onLaunchApp" @opensetting="onOpenSetting" :lang="lang" :session-from="sessionFrom" :send-message-title="sendMessageTitle" :send-message-path="sendMessagePath" :send-message-img="sendMessageImg" :show-message-card="showMessageCard" :app-parameter="appParameter">
      <block v-if="(!item.loading)">
        {{ item.name }}
        <view v-if="item.subname" class="van-action-sheet__subname">{{ item.subname }}</view>
      </block>
      <van-loading v-else custom-class="van-action-sheet__loading" size="22px"></van-loading>
    </button>
  </view>
  <slot></slot>
  <block v-if="cancelText">
    <view class="van-action-sheet__gap"></view>
    <view class="van-action-sheet__cancel" hover-class="van-action-sheet__cancel--hover" hover-stay-time="70" @click="onCancel">
      {{ cancelText }}
    </view>
  </block>
</van-popup></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>
import VanIcon from '../icon/index.vue'
import VanPopup from '../popup/index.vue'
import VanLoading from '../loading/index.vue'
global['__wxVueOptions'] = {components:{'van-icon': VanIcon,'van-popup': VanPopup,'van-loading': VanLoading}}

global['__wxRoute'] = 'vant/dist/action-sheet/index'
import { VantComponent } from '../common/component';
import { button } from '../mixins/button';
VantComponent({
    classes: ['list-class'],
    mixins: [button],
    props: {
        show: Boolean,
        title: String,
        cancelText: String,
        description: String,
        round: {
            type: Boolean,
            value: true,
        },
        zIndex: {
            type: Number,
            value: 100,
        },
        actions: {
            type: Array,
            value: [],
        },
        overlay: {
            type: Boolean,
            value: true,
        },
        closeOnClickOverlay: {
            type: Boolean,
            value: true,
        },
        closeOnClickAction: {
            type: Boolean,
            value: true,
        },
        safeAreaInsetBottom: {
            type: Boolean,
            value: true,
        },
    },
    methods: {
        onSelect(event) {
            const { index } = event.currentTarget.dataset;
            const { actions, closeOnClickAction, canIUseGetUserProfile } = this.data;
            const item = actions[index];
            if (item) {
                this.$emit('select', item);
                if (closeOnClickAction) {
                    this.onClose();
                }
                if (item.openType === 'getUserInfo' && canIUseGetUserProfile) {
                    wx.getUserProfile({
                        desc: item.getUserProfileDesc || '  ',
                        complete: (userProfile) => {
                            this.$emit('getuserinfo', userProfile);
                        },
                    });
                }
            }
        },
        onCancel() {
            this.$emit('cancel');
        },
        onClose() {
            this.$emit('close');
        },
        onClickOverlay() {
            this.$emit('click-overlay');
            this.onClose();
        },
    },
});
export default global['__wxComponents']['vant/dist/action-sheet/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-action-sheet{color:var(--action-sheet-item-text-color,#323233);max-height:var(--action-sheet-max-height,90%)!important}.van-action-sheet__cancel,.van-action-sheet__item{background-color:var(--action-sheet-item-background,#fff);font-size:var(--action-sheet-item-font-size,16px);line-height:var(--action-sheet-item-line-height,22px);padding:14px 16px;text-align:center}.van-action-sheet__cancel--hover,.van-action-sheet__item--hover{background-color:#f2f3f5}.van-action-sheet__cancel:after,.van-action-sheet__item:after{border-width:0}.van-action-sheet__cancel{color:var(--action-sheet-cancel-text-color,#646566)}.van-action-sheet__gap{background-color:var(--action-sheet-cancel-padding-color,#f7f8fa);display:block;height:var(--action-sheet-cancel-padding-top,8px)}.van-action-sheet__item--disabled{color:var(--action-sheet-item-disabled-text-color,#c8c9cc)}.van-action-sheet__item--disabled.van-action-sheet__item--hover{background-color:var(--action-sheet-item-background,#fff)}.van-action-sheet__subname{color:var(--action-sheet-subname-color,#969799);font-size:var(--action-sheet-subname-font-size,12px);line-height:var(--action-sheet-subname-line-height,20px);margin-top:var(--padding-xs,8px)}.van-action-sheet__header{font-size:var(--action-sheet-header-font-size,16px);font-weight:var(--font-weight-bold,500);line-height:var(--action-sheet-header-height,48px);text-align:center}.van-action-sheet__description{color:var(--action-sheet-description-color,#969799);font-size:var(--action-sheet-description-font-size,14px);line-height:var(--action-sheet-description-line-height,20px);padding:20px var(--padding-md,16px);text-align:center}.van-action-sheet__close{color:var(--action-sheet-close-icon-color,#c8c9cc);font-size:var(--action-sheet-close-icon-size,22px)!important;line-height:inherit!important;padding:var(--action-sheet-close-icon-padding,0 16px);position:absolute!important;right:0;top:0}.van-action-sheet__loading{display:flex!important}
</style>