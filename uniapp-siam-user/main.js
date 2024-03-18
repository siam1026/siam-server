import App from './App';

// 全局mixins，用于实现setData等功能，请勿删除！';
import zpMixins from '@/uni_modules/zp-mixins/index.js';

// #ifndef VUE3
import Vue from 'vue';

Vue.use(zpMixins);

// 导入p-f-unicom
import unicom from '@/uni_modules/p-f-unicom/index.js';
// 用于解决组件间关系(目前受制于平台及写法，仍可能存在小部分场景不生效，需手动调试修复或『替换对应组件』)
Vue.use(unicom, {
    name: 'unicom',
    idName: 'unicomId',
    groupName: 'unicomGroup'
});

Vue.config.productionTip = false;
App.mpType = 'app';
const app = new Vue({
    ...App
});
app.$mount();
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue';
export function createApp() {
    const app = createSSRApp(App);
    app.mixin(zpMixins);
    return {
        app
    };
}
// #endif
