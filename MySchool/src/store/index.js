import  Vue from 'vue'
import  Vuex from 'vuex'
import  app from './modules/app'
import  my from './modules/my'
import getters from './getter'
import actions from './actions'
import mutations from './mutations'
import user from './modules/user'
import  community from './modules/community'
import chat from './modules/chat'
import  message from './modules/message'
import friend from "./modules/friend";
Vue.use(Vuex)
const store = new Vuex.Store({
  state:{

  },
  getters:getters,
  mutations:mutations,
  actions:actions,
  modules:{
    app,
    my,
    user,
    community,
    chat,
    message,
    friend
  }
})


export  default  store

