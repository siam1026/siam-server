<template>
<uni-shadow-root class="vant-dist-dialog-index"><van-popup :show="show" :z-index="zIndex" :overlay="overlay" :transition="transition" :custom-class="'van-dialog van-dialog--'+(theme)+(className)+' custom-class'" :custom-style="'width: '+(utils.addUnit(width))+';'+(customStyle)" :overlay-style="overlayStyle" :close-on-click-overlay="closeOnClickOverlay" @close="onClickOverlay">
  <view v-if="title || useTitleSlot" :class="utils.bem('dialog__header', { isolated: !(message || useSlot) })">
    <slot v-if="useTitleSlot" name="title"></slot>
    <block v-else-if="title">{{ title }}</block>
  </view>

  <slot v-if="useSlot"></slot>
  <view v-else-if="message" :class="utils.bem('dialog__message', [theme, messageAlign, { hasTitle: title }])">
    <text class="van-dialog__message-text">{{ message }}</text>
  </view>

  <van-goods-action v-if="theme === 'round-button'" custom-class="van-dialog__footer--round-button">
    <van-goods-action-button v-if="showCancelButton" size="large" :loading="loading.cancel" class="van-dialog__button van-hairline--right" custom-class="van-dialog__cancel cancle-button-class" :custom-style="'color: '+(cancelButtonColor)" @click="onCancel">
      {{ cancelButtonText }}
    </van-goods-action-button>
    <van-goods-action-button v-if="showConfirmButton" size="large" class="van-dialog__button" :loading="loading.confirm" custom-class="van-dialog__confirm confirm-button-class" :custom-style="'color: '+(confirmButtonColor)" :button-id="confirmButtonId" :open-type="confirmButtonOpenType" :lang="lang" :business-id="businessId" :session-from="sessionFrom" :send-message-title="sendMessageTitle" :send-message-path="sendMessagePath" :send-message-img="sendMessageImg" :show-message-card="showMessageCard" :app-parameter="appParameter" @agreeprivacyauthorization="onAgreePrivacyAuthorization" @getRealTimePhoneNumber="onGetRealTimePhoneNumber" @click="onConfirm" @getuserinfo="onGetUserInfo" @contact="onContact" @getphonenumber="onGetPhoneNumber" @error="onError" @launchapp="onLaunchApp" @opensetting="onOpenSetting">
      {{ confirmButtonText }}
    </van-goods-action-button>
  </van-goods-action>

  <view v-else-if="showCancelButton || showConfirmButton" class="van-hairline--top van-dialog__footer">
    <block v-if="showCancelButton">
      <slot v-if="useCancelButtonSlot" name="cancel-button"></slot>

      <van-button v-else size="large" :loading="loading.cancel" class="van-dialog__button van-hairline--right" custom-class="van-dialog__cancel cancle-button-class" :custom-style="'color: '+(cancelButtonColor)" @click="onCancel">
        {{ cancelButtonText }}
      </van-button>
    </block>

    <block v-if="showConfirmButton">
      <slot v-if="useConfirmButtonSlot" name="confirm-button"></slot>

      <van-button v-else size="large" class="van-dialog__button" :loading="loading.confirm" custom-class="van-dialog__confirm confirm-button-class" :custom-style="'color: '+(confirmButtonColor)" :button-id="confirmButtonId" :open-type="confirmButtonOpenType" :lang="lang" :business-id="businessId" :session-from="sessionFrom" :send-message-title="sendMessageTitle" :send-message-path="sendMessagePath" :send-message-img="sendMessageImg" :show-message-card="showMessageCard" :app-parameter="appParameter" @agreeprivacyauthorization="onAgreePrivacyAuthorization" @getRealTimePhoneNumber="onGetRealTimePhoneNumber" @click="onConfirm" @getuserinfo="onGetUserInfo" @contact="onContact" @getphonenumber="onGetPhoneNumber" @error="onError" @launchapp="onLaunchApp" @opensetting="onOpenSetting">
        {{ confirmButtonText }}
      </van-button>
    </block>
  </view>
</van-popup></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>
import VanPopup from '../popup/index.vue'
import VanButton from '../button/index.vue'
import VanGoodsAction from '../goods-action/index.vue'
import VanGoodsActionButton from '../goods-action-button/index.vue'
global['__wxVueOptions'] = {components:{'van-popup': VanPopup,'van-button': VanButton,'van-goods-action': VanGoodsAction,'van-goods-action-button': VanGoodsActionButton}}

global['__wxRoute'] = 'vant/dist/dialog/index'
import { VantComponent } from '../common/component';
import { button } from '../mixins/button';
import { GRAY, RED } from '../common/color';
import { toPromise } from '../common/utils';
VantComponent({
    mixins: [button],
    classes: ['cancle-button-class', 'confirm-button-class'],
    props: {
        show: {
            type: Boolean,
            observer(show) {
                !show && this.stopLoading();
            },
        },
        title: String,
        message: String,
        theme: {
            type: String,
            value: 'default',
        },
        confirmButtonId: String,
        className: String,
        customStyle: String,
        asyncClose: Boolean,
        messageAlign: String,
        beforeClose: null,
        overlayStyle: String,
        useSlot: Boolean,
        useTitleSlot: Boolean,
        useConfirmButtonSlot: Boolean,
        useCancelButtonSlot: Boolean,
        showCancelButton: Boolean,
        closeOnClickOverlay: Boolean,
        confirmButtonOpenType: String,
        width: null,
        zIndex: {
            type: Number,
            value: 2000,
        },
        confirmButtonText: {
            type: String,
            value: '确认',
        },
        cancelButtonText: {
            type: String,
            value: '取消',
        },
        confirmButtonColor: {
            type: String,
            value: RED,
        },
        cancelButtonColor: {
            type: String,
            value: GRAY,
        },
        showConfirmButton: {
            type: Boolean,
            value: true,
        },
        overlay: {
            type: Boolean,
            value: true,
        },
        transition: {
            type: String,
            value: 'scale',
        },
    },
    data: {
        loading: {
            confirm: false,
            cancel: false,
        },
        callback: (() => { }),
    },
    methods: {
        onConfirm() {
            this.handleAction('confirm');
        },
        onCancel() {
            this.handleAction('cancel');
        },
        onClickOverlay() {
            this.close('overlay');
        },
        close(action) {
            this.setData({ show: false });
            wx.nextTick(() => {
                this.$emit('close', action);
                const { callback } = this.data;
                if (callback) {
                    callback(action, this);
                }
            });
        },
        stopLoading() {
            this.setData({
                loading: {
                    confirm: false,
                    cancel: false,
                },
            });
        },
        handleAction(action) {
            this.$emit(action, { dialog: this });
            const { asyncClose, beforeClose } = this.data;
            if (!asyncClose && !beforeClose) {
                this.close(action);
                return;
            }
            this.setData({
                [`loading.${action}`]: true,
            });
            if (beforeClose) {
                toPromise(beforeClose(action)).then((value) => {
                    if (value) {
                        this.close(action);
                    }
                    else {
                        this.stopLoading();
                    }
                });
            }
        },
    },
});
export default global['__wxComponents']['vant/dist/dialog/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-dialog{background-color:var(--dialog-background-color,#fff);border-radius:var(--dialog-border-radius,16px);font-size:var(--dialog-font-size,16px);overflow:hidden;top:45%!important;width:var(--dialog-width,320px)}@media (max-width:321px){.van-dialog{width:var(--dialog-small-screen-width,90%)}}.van-dialog__header{font-weight:var(--dialog-header-font-weight,500);line-height:var(--dialog-header-line-height,24px);padding-top:var(--dialog-header-padding-top,24px);text-align:center}.van-dialog__header--isolated{padding:var(--dialog-header-isolated-padding,24px 0)}.van-dialog__message{-webkit-overflow-scrolling:touch;font-size:var(--dialog-message-font-size,14px);line-height:var(--dialog-message-line-height,20px);max-height:var(--dialog-message-max-height,60vh);overflow-y:auto;padding:var(--dialog-message-padding,24px);text-align:center}.van-dialog__message-text{word-wrap:break-word}.van-dialog__message--hasTitle{color:var(--dialog-has-title-message-text-color,#646566);padding-top:var(--dialog-has-title-message-padding-top,8px)}.van-dialog__message--round-button{color:#323233;padding-bottom:16px}.van-dialog__message--left{text-align:left}.van-dialog__message--right{text-align:right}.van-dialog__message--justify{text-align:justify}.van-dialog__footer{display:flex}.van-dialog__footer--round-button{padding:8px 24px 16px!important;position:relative!important}.van-dialog__button{flex:1}.van-dialog__cancel,.van-dialog__confirm{border:0!important}.van-dialog-bounce-enter{opacity:0;transform:translate3d(-50%,-50%,0) scale(.7)}.van-dialog-bounce-leave-active{opacity:0;transform:translate3d(-50%,-50%,0) scale(.9)}
</style>