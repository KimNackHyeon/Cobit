import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import Store from '@/vuex/store.js'

const store = new Vuex.Store({
  modules: {
    Store: Store,
  },
})

export default store