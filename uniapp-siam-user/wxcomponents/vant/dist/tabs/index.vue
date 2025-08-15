<template>
<uni-shadow-root class="vant-dist-tabs-index"><view :class="'custom-class '+(utils.bem('tabs'))">
  <van-sticky :disabled="(!sticky)" :z-index="zIndex" :offset-top="offsetTop" :container="container" @scroll="onTouchScroll">
    <view :class="(utils.bem('tabs--') + type)+' '+(utils.bem('tabs__wrap', { scrollable }))+' '+(type === 'line' && border ? 'van-hairline--top-bottom' : '')+' wrap-class'">
      <slot name="nav-left"></slot>

      <scroll-view :scroll-x="scrollable" :scroll-with-animation="scrollWithAnimation" :scroll-left="scrollLeft" :class="utils.bem('tabs__scroll', [type])" :style="color ? 'border-color: ' + color : ''">
        <view :class="(utils.bem('tabs__nav', [type, { complete: !ellipsis }]))+' nav-class'" :style="computed.navStyle(color, type)">
          <view v-if="type === 'line'" class="van-tabs__line" :style="computed.lineStyle({ color, lineOffsetLeft, lineHeight, skipTransition, duration, lineWidth, inited })"></view>
          <view v-for="(item,index) in (tabs)" :key="item.index" :data-index="index" :class="(computed.tabClass(index === currentIndex, ellipsis))+' '+(utils.bem('tab', { active: index === currentIndex, disabled: item.disabled, complete: !ellipsis }))" :style="computed.tabStyle({ active: index === currentIndex, ellipsis, color, type, disabled: item.disabled, titleActiveColor, titleInactiveColor, swipeThreshold, scrollable })" @click="onTap">
            <view :class="ellipsis ? 'van-ellipsis' : ''" :style="item.titleStyle">
              {{ item.title }}
              <van-info v-if="item.info !== null || item.dot" :info="item.info" :dot="item.dot" custom-class="van-tab__title__info"></van-info>
            </view>
          </view>
        </view>
      </scroll-view>

      <slot name="nav-right"></slot>
    </view>
  </van-sticky>

  <view class="van-tabs__content" @touchstart="onTouchStart" @touchmove="onTouchMove" @touchend="onTouchEnd" @touchcancel="onTouchEnd">
    <view :class="(utils.bem('tabs__track', [{ animated }]))+' van-tabs__track'" :style="computed.trackStyle({ duration, currentIndex, animated })">
      <slot></slot>
    </view>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs><wxs src="./index.wxs" module="computed"></wxs>
<script>
import VanInfo from '../info/index.vue'
import VanSticky from '../sticky/index.vue'
global['__wxVueOptions'] = {components:{'van-info': VanInfo,'van-sticky': VanSticky}}

global['__wxRoute'] = 'vant/dist/tabs/index'
import { VantComponent } from '../common/component';
import { touch } from '../mixins/touch';
import { getAllRect, getRect, groupSetData, nextTick, requestAnimationFrame, } from '../common/utils';
import { isDef } from '../common/validator';
import { useChildren } from '../common/relation';
VantComponent({
    mixins: [touch],
    classes: [
        'nav-class',
        'tab-class',
        'tab-active-class',
        'line-class',
        'wrap-class',
    ],
    relation: useChildren('tab', function () {
        this.updateTabs();
    }),
    props: {
        sticky: Boolean,
        border: Boolean,
        swipeable: Boolean,
        titleActiveColor: String,
        titleInactiveColor: String,
        color: String,
        animated: {
            type: Boolean,
            observer() {
                this.children.forEach((child, index) => child.updateRender(index === this.data.currentIndex, this));
            },
        },
        lineWidth: {
            type: null,
            value: 40,
            observer: 'resize',
        },
        lineHeight: {
            type: null,
            value: -1,
        },
        active: {
            type: null,
            value: 0,
            observer(name) {
                if (name !== this.getCurrentName()) {
                    this.setCurrentIndexByName(name);
                }
            },
        },
        type: {
            type: String,
            value: 'line',
        },
        ellipsis: {
            type: Boolean,
            value: true,
        },
        duration: {
            type: Number,
            value: 0.3,
        },
        zIndex: {
            type: Number,
            value: 1,
        },
        swipeThreshold: {
            type: Number,
            value: 5,
            observer(value) {
                this.setData({
                    scrollable: this.children.length > value || !this.data.ellipsis,
                });
            },
        },
        offsetTop: {
            type: Number,
            value: 0,
        },
        lazyRender: {
            type: Boolean,
            value: true,
        },
        useBeforeChange: {
            type: Boolean,
            value: false,
        },
    },
    data: {
        tabs: [],
        scrollLeft: 0,
        scrollable: false,
        currentIndex: 0,
        container: null,
        skipTransition: true,
        scrollWithAnimation: false,
        lineOffsetLeft: 0,
        inited: false,
    },
    mounted() {
        requestAnimationFrame(() => {
            this.swiping = true;
            this.setData({
                container: () => this.createSelectorQuery().select('.van-tabs'),
            });
            this.resize();
            this.scrollIntoView();
        });
    },
    methods: {
        updateTabs() {
            const { children = [], data } = this;
            this.setData({
                tabs: children.map((child) => child.data),
                scrollable: this.children.length > data.swipeThreshold || !data.ellipsis,
            });
            this.setCurrentIndexByName(data.active || this.getCurrentName());
        },
        trigger(eventName, child) {
            const { currentIndex } = this.data;
            const data = this.getChildData(currentIndex, child);
            if (!isDef(data)) {
                return;
            }
            this.$emit(eventName, data);
        },
        onTap(event) {
            const { index } = event.currentTarget.dataset;
            const child = this.children[index];
            if (child.data.disabled) {
                this.trigger('disabled', child);
                return;
            }
            this.onBeforeChange(index).then(() => {
                this.setCurrentIndex(index);
                nextTick(() => {
                    this.trigger('click');
                });
            });
        },
        // correct the index of active tab
        setCurrentIndexByName(name) {
            const { children = [] } = this;
            const matched = children.filter((child) => child.getComputedName() === name);
            if (matched.length) {
                this.setCurrentIndex(matched[0].index);
            }
        },
        setCurrentIndex(currentIndex) {
            const { data, children = [] } = this;
            if (!isDef(currentIndex) ||
                currentIndex >= children.length ||
                currentIndex < 0) {
                return;
            }
            groupSetData(this, () => {
                children.forEach((item, index) => {
                    const active = index === currentIndex;
                    if (active !== item.data.active || !item.inited) {
                        item.updateRender(active, this);
                    }
                });
            });
            if (currentIndex === data.currentIndex) {
                if (!data.inited) {
                    this.resize();
                }
                return;
            }
            const shouldEmitChange = data.currentIndex !== null;
            this.setData({ currentIndex });
            requestAnimationFrame(() => {
                this.resize();
                this.scrollIntoView();
            });
            nextTick(() => {
                this.trigger('input');
                if (shouldEmitChange) {
                    this.trigger('change');
                }
            });
        },
        getCurrentName() {
            const activeTab = this.children[this.data.currentIndex];
            if (activeTab) {
                return activeTab.getComputedName();
            }
        },
        resize() {
            if (this.data.type !== 'line') {
                return;
            }
            const { currentIndex, ellipsis, skipTransition } = this.data;
            Promise.all([
                getAllRect(this, '.van-tab'),
                getRect(this, '.van-tabs__line'),
            ]).then(([rects = [], lineRect]) => {
                const rect = rects[currentIndex];
                if (rect == null) {
                    return;
                }
                let lineOffsetLeft = rects
                    .slice(0, currentIndex)
                    .reduce((prev, curr) => prev + curr.width, 0);
                lineOffsetLeft +=
                    (rect.width - lineRect.width) / 2 + (ellipsis ? 0 : 8);
                this.setData({ lineOffsetLeft, inited: true });
                this.swiping = true;
                if (skipTransition) {
                    // waiting transition end
                    setTimeout(() => {
                        this.setData({ skipTransition: false });
                    }, this.data.duration);
                }
            });
        },
        // scroll active tab into view
        scrollIntoView() {
            const { currentIndex, scrollable, scrollWithAnimation } = this.data;
            if (!scrollable) {
                return;
            }
            Promise.all([
                getAllRect(this, '.van-tab'),
                getRect(this, '.van-tabs__nav'),
            ]).then(([tabRects, navRect]) => {
                const tabRect = tabRects[currentIndex];
                const offsetLeft = tabRects
                    .slice(0, currentIndex)
                    .reduce((prev, curr) => prev + curr.width, 0);
                this.setData({
                    scrollLeft: offsetLeft - (navRect.width - tabRect.width) / 2,
                });
                if (!scrollWithAnimation) {
                    nextTick(() => {
                        this.setData({ scrollWithAnimation: true });
                    });
                }
            });
        },
        onTouchScroll(event) {
            this.$emit('scroll', event.detail);
        },
        onTouchStart(event) {
            if (!this.data.swipeable)
                return;
            this.swiping = true;
            this.touchStart(event);
        },
        onTouchMove(event) {
            if (!this.data.swipeable || !this.swiping)
                return;
            this.touchMove(event);
        },
        // watch swipe touch end
        onTouchEnd() {
            if (!this.data.swipeable || !this.swiping)
                return;
            const { direction, deltaX, offsetX } = this;
            const minSwipeDistance = 50;
            if (direction === 'horizontal' && offsetX >= minSwipeDistance) {
                const index = this.getAvaiableTab(deltaX);
                if (index !== -1) {
                    this.onBeforeChange(index).then(() => this.setCurrentIndex(index));
                }
            }
            this.swiping = false;
        },
        getAvaiableTab(direction) {
            const { tabs, currentIndex } = this.data;
            const step = direction > 0 ? -1 : 1;
            for (let i = step; currentIndex + i < tabs.length && currentIndex + i >= 0; i += step) {
                const index = currentIndex + i;
                if (index >= 0 &&
                    index < tabs.length &&
                    tabs[index] &&
                    !tabs[index].disabled) {
                    return index;
                }
            }
            return -1;
        },
        onBeforeChange(index) {
            const { useBeforeChange } = this.data;
            if (!useBeforeChange) {
                return Promise.resolve();
            }
            return new Promise((resolve, reject) => {
                this.$emit('before-change', Object.assign(Object.assign({}, this.getChildData(index)), { callback: (status) => (status ? resolve() : reject()) }));
            });
        },
        getChildData(index, child) {
            const currentChild = child || this.children[index];
            if (!isDef(currentChild)) {
                return;
            }
            return {
                index: currentChild.index,
                name: currentChild.getComputedName(),
                title: currentChild.data.title,
            };
        },
    },
});
export default global['__wxComponents']['vant/dist/tabs/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-tabs{-webkit-tap-highlight-color:transparent;position:relative}.van-tabs__wrap{display:flex;overflow:hidden}.van-tabs__wrap--scrollable .van-tab{flex:0 0 22%}.van-tabs__wrap--scrollable .van-tab--complete{flex:1 0 auto!important;padding:0 12px}.van-tabs__wrap--scrollable .van-tabs__nav--complete{padding-left:8px;padding-right:8px}.van-tabs__scroll{background-color:var(--tabs-nav-background-color,#fff);overflow:auto}.van-tabs__scroll--line{box-sizing:initial;height:calc(100% + 15px)}.van-tabs__scroll--card{border:1px solid var(--tabs-default-color,#ee0a24);border-radius:2px;box-sizing:border-box;margin:0 var(--padding-md,16px);width:calc(100% - var(--padding-md, 16px)*2)}.van-tabs__scroll::-webkit-scrollbar{display:none}.van-tabs__nav{display:flex;position:relative;-webkit-user-select:none;user-select:none}.van-tabs__nav--card{box-sizing:border-box;height:var(--tabs-card-height,30px)}.van-tabs__nav--card .van-tab{border-right:1px solid var(--tabs-default-color,#ee0a24);color:var(--tabs-default-color,#ee0a24);line-height:calc(var(--tabs-card-height, 30px) - 2px)}.van-tabs__nav--card .van-tab:last-child{border-right:none}.van-tabs__nav--card .van-tab.van-tab--active{background-color:var(--tabs-default-color,#ee0a24);color:#fff}.van-tabs__nav--card .van-tab--disabled{color:var(--tab-disabled-text-color,#c8c9cc)}.van-tabs__line{background-color:var(--tabs-bottom-bar-color,#ee0a24);border-radius:var(--tabs-bottom-bar-height,3px);bottom:0;height:var(--tabs-bottom-bar-height,3px);left:0;opacity:0;position:absolute;z-index:1}.van-tabs__track{height:100%;position:relative;width:100%}.van-tabs__track--animated{display:flex;transition-property:left}.van-tabs__content{overflow:hidden}.van-tabs--line{height:var(--tabs-line-height,44px)}.van-tabs--card{height:var(--tabs-card-height,30px)}.van-tab{box-sizing:border-box;color:var(--tab-text-color,#646566);cursor:pointer;flex:1;font-size:var(--tab-font-size,14px);line-height:var(--tabs-line-height,44px);min-width:0;padding:0 5px;position:relative;text-align:center}.van-tab--active{color:var(--tab-active-text-color,#323233);font-weight:var(--font-weight-bold,500)}.van-tab--disabled{color:var(--tab-disabled-text-color,#c8c9cc)}.van-tab__title__info{position:relative!important;top:-1px!important;transform:translateX(0)!important}
</style>