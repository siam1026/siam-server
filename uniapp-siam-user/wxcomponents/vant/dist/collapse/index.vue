<template>
<uni-shadow-root class="vant-dist-collapse-index"><view :class="'custom-class van-collapse '+(border ? 'van-hairline--top-bottom' : '')">
  <slot></slot>
</view></uni-shadow-root>
</template>

<script>

global['__wxRoute'] = 'vant/dist/collapse/index'
import { VantComponent } from '../common/component';
import { useChildren } from '../common/relation';
VantComponent({
    relation: useChildren('collapse-item'),
    props: {
        value: {
            type: null,
            observer: 'updateExpanded',
        },
        accordion: {
            type: Boolean,
            observer: 'updateExpanded',
        },
        border: {
            type: Boolean,
            value: true,
        },
    },
    methods: {
        updateExpanded() {
            this.children.forEach((child) => {
                child.updateExpanded();
            });
        },
        switch(name, expanded) {
            const { accordion, value } = this.data;
            const changeItem = name;
            if (!accordion) {
                name = expanded
                    ? (value || []).concat(name)
                    : (value || []).filter((activeName) => activeName !== name);
            }
            else {
                name = expanded ? name : '';
            }
            if (expanded) {
                this.$emit('open', changeItem);
            }
            else {
                this.$emit('close', changeItem);
            }
            this.$emit('change', name);
            this.$emit('input', name);
        },
    },
});
export default global['__wxComponents']['vant/dist/collapse/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';
</style>