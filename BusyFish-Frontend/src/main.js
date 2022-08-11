// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue'
// import BootstrapVue from "bootstrap-vue"
import App from './views/App'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import AdminNavBar from "./views/admin/AdminNavBar";
import UserNavBar from "./views/user/UserNavBar";
import axios from "axios";

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)
Vue.config.productionTip = false
Vue.component('AdminNavBar',AdminNavBar)
Vue.component('UserNavBar', UserNavBar)
axios.defaults.withCredentials=true

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
