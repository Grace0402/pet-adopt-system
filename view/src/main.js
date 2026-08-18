import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import 'element-ui/lib/theme-chalk/index.css';
import './assets/css/style.scss'
import './assets/css/code-highlight.scss'
import './assets/css/wang-editor.scss'
import './assets/css/elementui-cover.scss'
import request from '@/utils/request'
import md5 from 'js-md5';

Vue.config.productionTip = false;
Vue.prototype.$md5 = md5; // md5加密
Vue.prototype.$axios = request;

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");
