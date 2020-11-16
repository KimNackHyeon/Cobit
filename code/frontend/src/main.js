import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './vuex/store';
import vuetify from './plugins/vuetify';
import VueCookies from "vue-cookies";
import VueSpeech from 'vue-speech'

Vue.config.productionTip = false
Vue.use(VueSpeech)
Vue.use(VueCookies);

// window.Kakao.init("a90caea0345c3be6cd63ed25b9c44977");
window.Kakao.init("c18a3434c1cd9382531a7976be0c0418");

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
