import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from './plugins/vuetify';
import './registerServiceWorker';
import './plugins/axios'
import './validators'
import './filters'
import { ValidationProvider, ValidationObserver } from 'vee-validate';
import RequiredIcon from "@/components/RequiredIcon";

Vue.config.productionTip = false

Vue.component('validation-provider', ValidationProvider);
Vue.component('validation-observer', ValidationObserver);
Vue.component('required-icon', RequiredIcon);

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
