<template>
<uni-shadow-root class="vant-row-index"><view class="custom-class van-row" :style="viewStyle">
  <slot></slot>
</view></uni-shadow-root>
</template>

<script>

global['__wxRoute'] = 'vant/row/index'
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var component_1 = require("../common/component");
component_1.VantComponent({
    relation: {
        name: 'col',
        type: 'descendant',
        current: 'row',
        linked: function (target) {
            if (this.data.gutter) {
                target.setGutter(this.data.gutter);
            }
        }
    },
    props: {
        gutter: {
            type: Number,
            observer: 'setGutter'
        }
    },
    data: {
        viewStyle: '',
    },
    mounted: function () {
        if (this.data.gutter) {
            this.setGutter();
        }
    },
    methods: {
        setGutter: function () {
            var _this = this;
            var gutter = this.data.gutter;
            var margin = "-" + Number(gutter) / 2 + "px";
            var viewStyle = gutter
                ? "margin-right: " + margin + "; margin-left: " + margin + ";"
                : '';
            this.setData({ viewStyle: viewStyle });
            this.getRelationNodes('../col/index').forEach(function (col) {
                col.setGutter(_this.data.gutter);
            });
        }
    }
});
export default global['__wxComponents']['vant/row/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-row:after{display:table;clear:both;content:""}
</style>