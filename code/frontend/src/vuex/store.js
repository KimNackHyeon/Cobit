import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import actions from './actions'
import mutations from './mutations'

const state = {
  StageDetail: {},
  StageNum: 0,
  StageType: 0,
  InStageNum: '',
  InStageStar: '',
  IsLastStage: false,
}
console.log(state);

Vue.use(Vuex)

export default new Vuex.Store({
  state:{
    kakaoUserInfo:{
      id:null,
      email:null,
      nickname:null,
      star:null,
      // profile_image_url:null
    },
  },
  getters,
  mutations,
  actions
})

