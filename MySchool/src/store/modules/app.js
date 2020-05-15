const state ={
  //底部bar参数
  showbottombar:true,
  showmyinput:false,
  actives:[false,true,false,false,false],
  last:1,
  websocket:null

}
const getters={
    getBweSocket:(state)=>{
       return state.websocket
    },
    getshowbottombar: (state)=>{
      return state.showbottombar
    },
  getshowmyinput: (state)=>{
    return state.showmyinput
  },
  getlast: (state)=>{
    return state.last
  },
  getactives: (state)=>{
    return state.actives
  },
}
const mutations={
  setWebSocket:function(state,websocket){
    this.state.websocket=websocket
  },
  tocommunitypage: function (state,vm) {

    state.actives.splice(0,1,true)
    if(state.last !== 0){
      state.actives.splice(state.last,1,false)
    }
    state.last=0
    if (vm.$router.currentRoute.name !== 'community') {
      vm.$router.push("/community")
    }
  },

  tomypage: function (state,vm) {
    state.actives.splice(4,1,true)
    if(state.last !== 4){
      state.actives.splice(state.last,1,false)
    }
    state.last=4
    if (vm.$router.currentRoute.name !== 'my') {
      vm.$router.push("/my")
    }
  },
  tosearchpage: function(state,vm){

    state.actives.splice(2,1,true)
    if(state.last !== 2){
      state.actives.splice(state.last,1,false)
    }
    state.last=2
    if(vm.$router.currentRoute.name !== 'search') {
      vm.$router.push("/search")
    }
  },
  tohomepage:function(state,vm){

    state.actives.splice(1,1,true)
    if(state.last !== 1){
      state.actives.splice(state.last,1,false)
    }
    state.last=1
    if(vm.$router.currentRoute.name !== 'home') {
      vm.$router.push("/home")
    }
  },
  tomessagepage:function(state,vm) {
    state.actives.splice(3, 1, true)
    if (state.last !== 3) {
      state.actives.splice(state.last, 1, false)
    }
    state.last = 3
    if (vm.$router.currentRoute.name !== 'message') {
      vm.$router.push("/message")
    }
  },
    updateShowbottombar:(state,sta)=>{
      console.log('更改bar')
      state.showbottombar=sta
    },
    updateShowmyinput:(state,sta)=>{
      console.log('更改input')
    state.showmyinput=sta
  }
}
const actions={
    hiddenbottombarAndshowmyinput:function(context){
       return new Promise(resolve => {
         context.commit('updateShowbottombar',false)
         context.commit('updateShowmyinput',true)
       })
    },
  hiddenmyinputAndshowbottombar:function(context){
    return new Promise(resolve => {
      context.commit('updateShowmyinput',false)
      context.commit('updateShowbottombar',true)
    })
  },
  hiddenBottombar:function (context) {
    return new Promise(resolve => {
      context.commit('updateShowbottombar',false)
    })
  },
  upBottombar:function (context) {
    return new Promise(resolve => {
      context.commit('updateShowbottombar',true)
    })
  }


}
const app={
  state,
  getters,
  mutations,
  actions
}
export  default  app
