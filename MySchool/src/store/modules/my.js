const state ={
  logintype:1,//登陆形式 表示邮箱登陆 2 表示邮箱登陆
  actiontype:0 ,//操作类型，0表示选择状态 1代表登陆 2 代表注册
  notice:[],
  unreadCount:0

}
const getters={
  getNotice: (state)=>{
    return state.notice
  },
  getUnreadCount: (state)=>{
    return state.unreadCount
  },
  getlogintype: (state)=>{
    return state.logintype
  },
  getactiontype: (state)=>{
    return state.actiontype
  },

}
const mutations={
    setUnreadCount:function(state,unreadCount){
      state.unreadCount=unreadCount
    },
    addUnreadCount:function(state,count){
      state.unreadCount+=count
    },
    addNotice:function(state,notice){
      state.actiontype.push(notice)
    },
    setNotice:function(state,notice){
      state.notice=notice
    },
    setactiontype:function(state,type){
        state.actiontype=type
    },
    setlogintype:function(state,type){
      state.logintype=type
    }
}
const actions={
  addUnreadCount:function(context,count){
    return new Promise(resolve => {
      context.commit('addUnreadCount',count)
    })
  },
  setUnreadCount:function(context,count){
    return new Promise(resolve => {
      context.commit('setUnreadCount',count)
    })
  },
  addNotice:function(context,notice){
    return new Promise(resolve => {
     context.commit('addNotice',notice)
    })
  },
  setNotice:function(context,notice){
    return new Promise(resolve => {
      context.commit('setNotice',notice)
    })
  },
}
const my={
  state,
  getters,
  mutations,
  actions
}
export  default  my
