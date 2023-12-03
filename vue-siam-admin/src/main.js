import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './common/common.css'
import VueRouter from 'vue-router'
import store from './vuex/store'
import Vuex from 'vuex'
import myPrint from './components/internal/print'
import orderPrint from './components/internal/orderPrint'
import VCharts from 'v-charts'

// import VueQuillEditor from 'vue-quill-editor'
// import {ImageDrop} from 'quill-image-drop-module'

// import ImageResize from 'quill-image-resize-module'

// import 'quill/dist/quill.core.css'
// import 'quill/dist/quill.snow.css'
// import 'quill/dist/quill.bubble.css'

// Quill.register('modules/imageDrop', ImageDrop)
// Quill.register('modules/imageResize', ImageResize)


import http from './utils/http';
import utils from './utils/common';
Vue.prototype.$http = http;
Vue.prototype.$utils = utils;
import routes from './routes'
import VueCropper from 'vue-cropper'

Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vuex)
Vue.use(myPrint)
Vue.use(orderPrint)
Vue.use(VCharts)
Vue.use(VueCropper)
// Vue.use(VueQuillEditor)


const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  let user = JSON.parse(sessionStorage.getItem('user'));
  if (to.path == '/login' || to.path == '/QuickLogin' || to.path == '/setPassword') {
    next()
  }else if (!user && to.path != '/login') {
    sessionStorage.removeItem('user');
    next({ path: '/login' })
  } else if(to.path == '/') {
    next({path: '/statisticGraph'})
  } else {
    next()
  }
})


new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
}).$mount('#app')

