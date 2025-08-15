<template>
<uni-shadow-root class="vant-dist-sidebar-index"><view class="van-sidebar custom-class">
  <slot></slot>
</view></uni-shadow-root>
</template>

<script>

global['__wxRoute'] = 'vant/dist/sidebar/index'
import { VantComponent } from '../common/component';
import { useChildren } from '../common/relation';
VantComponent({
    relation: useChildren('sidebar-item', function () {
        this.setActive(this.data.activeKey);
    }),
    props: {
        activeKey: {
            type: Number,
            value: 0,
            observer: 'setActive',
        },
    },
    beforeCreate() {
        this.currentActive = -1;
    },
    methods: {
        setActive(activeKey) {
            const { children, currentActive } = this;
            if (!children.length) {
                return Promise.resolve();
            }
            this.currentActive = activeKey;
            const stack = [];
            if (currentActive !== activeKey && children[currentActive]) {
                stack.push(children[currentActive].setActive(false));
            }
            if (children[activeKey]) {
                stack.push(children[activeKey].setActive(true));
            }
            return Promise.all(stack);
        },
    },
});
export default global['__wxComponents']['vant/dist/sidebar/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-sidebar{width:var(--sidebar-width,80px)}
</style>