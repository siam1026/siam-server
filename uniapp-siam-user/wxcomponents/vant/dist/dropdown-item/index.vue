<template>
<uni-shadow-root class="vant-dist-dropdown-item-index"><view v-if="showWrapper" :class="(utils.bem('dropdown-item', direction))+' custom-class'" :style="wrapperStyle">
  <van-popup :show="showPopup" :custom-style="'position: absolute;'+(popupStyle)" overlay-style="position: absolute;" :overlay="overlay" :position="direction === 'down' ? 'top' : 'bottom'" :duration="transition ? duration : 0" :safe-area-tab-bar="safeAreaTabBar" :close-on-click-overlay="closeOnClickOverlay" :rootPortal="rootPortal" @enter="onOpen" @leave="onClose" @close="toggle" @after-enter="onOpened" @after-leave="onClosed">
    <van-cell v-for="(item,index) in (options)" :key="item.value" :data-option="item" :class="utils.bem('dropdown-item__option', { active: item.value === value } )" clickable :icon="item.icon" @click.native="onOptionTap">
      <view slot="title" class="van-dropdown-item__title item-title-class" :style="item.value === value  ? 'color:' + activeColor : ''">
        {{ item.text }}
      </view>
      <van-icon v-if="item.value === value" name="success" class="van-dropdown-item__icon" :color="activeColor"></van-icon>
    </van-cell>

    <slot></slot>
  </van-popup>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>
import VanPopup from '../popup/index.vue'
import VanCell from '../cell/index.vue'
import VanIcon from '../icon/index.vue'
global['__wxVueOptions'] = {components:{'van-popup': VanPopup,'van-cell': VanCell,'van-icon': VanIcon}}

global['__wxRoute'] = 'vant/dist/dropdown-item/index'
import { useParent } from '../common/relation';
import { VantComponent } from '../common/component';
VantComponent({
    classes: ['item-title-class'],
    field: true,
    relation: useParent('dropdown-menu', function () {
        this.updateDataFromParent();
    }),
    props: {
        value: {
            type: null,
            observer: 'rerender',
        },
        title: {
            type: String,
            observer: 'rerender',
        },
        disabled: Boolean,
        titleClass: {
            type: String,
            observer: 'rerender',
        },
        options: {
            type: Array,
            value: [],
            observer: 'rerender',
        },
        popupStyle: String,
        useBeforeToggle: {
            type: Boolean,
            value: false,
        },
        rootPortal: {
            type: Boolean,
            value: false,
        },
    },
    data: {
        transition: true,
        showPopup: false,
        showWrapper: false,
        displayTitle: '',
        safeAreaTabBar: false,
    },
    methods: {
        rerender() {
            wx.nextTick(() => {
                var _a;
                (_a = this.parent) === null || _a === void 0 ? void 0 : _a.updateItemListData();
            });
        },
        updateDataFromParent() {
            if (this.parent) {
                const { overlay, duration, activeColor, closeOnClickOverlay, direction, safeAreaTabBar, } = this.parent.data;
                this.setData({
                    overlay,
                    duration,
                    activeColor,
                    closeOnClickOverlay,
                    direction,
                    safeAreaTabBar,
                });
            }
        },
        onOpen() {
            this.$emit('open');
        },
        onOpened() {
            this.$emit('opened');
        },
        onClose() {
            this.$emit('close');
        },
        onClosed() {
            this.$emit('closed');
            this.setData({ showWrapper: false });
        },
        onOptionTap(event) {
            const { option } = event.currentTarget.dataset;
            const { value } = option;
            const shouldEmitChange = this.data.value !== value;
            this.setData({ showPopup: false, value });
            this.$emit('close');
            this.rerender();
            if (shouldEmitChange) {
                this.$emit('change', value);
            }
        },
        toggle(show, options = {}) {
            const { showPopup } = this.data;
            if (typeof show !== 'boolean') {
                show = !showPopup;
            }
            if (show === showPopup) {
                return;
            }
            this.onBeforeToggle(show).then((status) => {
                var _a;
                if (!status) {
                    return;
                }
                this.setData({
                    transition: !options.immediate,
                    showPopup: show,
                });
                if (show) {
                    (_a = this.parent) === null || _a === void 0 ? void 0 : _a.getChildWrapperStyle().then((wrapperStyle) => {
                        this.setData({ wrapperStyle, showWrapper: true });
                        this.rerender();
                    });
                }
                else {
                    this.rerender();
                }
            });
        },
        onBeforeToggle(status) {
            const { useBeforeToggle } = this.data;
            if (!useBeforeToggle) {
                return Promise.resolve(true);
            }
            return new Promise((resolve) => {
                this.$emit('before-toggle', {
                    status,
                    callback: (value) => resolve(value),
                });
            });
        },
    },
});
export default global['__wxComponents']['vant/dist/dropdown-item/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-dropdown-item{left:0;overflow:hidden;position:fixed;right:0}.van-dropdown-item__option{text-align:left}.van-dropdown-item__option--active .van-dropdown-item__icon,.van-dropdown-item__option--active .van-dropdown-item__title{color:var(--dropdown-menu-option-active-color,#ee0a24)}.van-dropdown-item--up{top:0}.van-dropdown-item--down{bottom:0}.van-dropdown-item__icon{display:block;line-height:inherit}
</style>