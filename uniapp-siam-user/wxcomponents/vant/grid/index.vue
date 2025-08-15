<template>
<uni-shadow-root class="vant-grid-index"><view :class="'van-grid custom-class '+(border && !gutter ? 'van-hairline--top' : '')" :style="viewStyle">
  <slot></slot>
</view></uni-shadow-root>
</template>

<script>

global['__wxRoute'] = 'vant/grid/index'
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var component_1 = require("../common/component");
var utils_1 = require("../common/utils");
component_1.VantComponent({
    relation: {
        name: 'grid-item',
        type: 'descendant',
        current: 'grid',
    },
    props: {
        square: {
            type: Boolean,
            observer: 'updateChildren'
        },
        gutter: {
            type: [Number, String],
            value: 0,
            observer: 'updateChildren'
        },
        clickable: {
            type: Boolean,
            observer: 'updateChildren'
        },
        columnNum: {
            type: Number,
            value: 4,
            observer: 'updateChildren'
        },
        center: {
            type: Boolean,
            value: true,
            observer: 'updateChildren'
        },
        border: {
            type: Boolean,
            value: true,
            observer: 'updateChildren'
        }
    },
    data: {
        viewStyle: '',
    },
    created: function () {
        var gutter = this.data.gutter;
        if (gutter) {
            this.setData({
                viewStyle: "padding-left: " + utils_1.addUnit(gutter)
            });
        }
    },
    methods: {
        updateChildren: function () {
            this.children.forEach(function (child) {
                child.updateStyle();
            });
        }
    }
});
export default global['__wxComponents']['vant/grid/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-grid{position:relative;box-sizing:border-box;overflow:hidden}
</style>