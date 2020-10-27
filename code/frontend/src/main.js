import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './vuex/store';
import vuetify from './plugins/vuetify';

Vue.config.productionTip = false

window.Kakao.init("a90caea0345c3be6cd63ed25b9c44977");

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
