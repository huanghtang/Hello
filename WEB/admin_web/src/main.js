// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import moment from 'moment'
import { VueEditor } from "vue2-editor";
import './permission' // 權限
import global_ from './components/Global'//引用文件

Vue.config.productionTip = false

Vue.use(VueEditor)
Vue.component("VueEditor", VueEditor);
Vue.prototype.$moment = moment;
Vue.prototype.GLOBAL = global_//挂载到Vue实例上面
/* eslint-disable no-new */
export default new Vue({
    el: '#app',
    router,
    store,
    template: '<App/>',
    components: { App }
})

