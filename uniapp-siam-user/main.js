
import App from './App';

// 全局mixins，用于实现setData等功能，请勿删除！';
import zpMixins from '@/uni_modules/zp-mixins/index.js';

// #ifndef VUE3
import Vue from 'vue';

Vue.use(zpMixins);

Vue.config.productionTip = false;
App.mpType = 'app';
const app = new Vue({
	...App
});
app.$mount();


// #endif
// #ifdef VUE3
import {
	createSSRApp
} from 'vue';
// #ifdef APP-PLUS||H5
// 1. 引入你需要的组件
import Vant from 'vant';
// 2. 引入组件样式
import 'vant/lib/index.css';
import '@vant/touch-emulator';

// #endif
export function createApp() {
	const app = createSSRApp(App);
	app.mixin(zpMixins);
	// #ifdef APP-PLUS||H5
	app.use(Vant);
	// #endif
	return {
		app
	};
}
// #endif