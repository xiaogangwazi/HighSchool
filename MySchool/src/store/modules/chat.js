const state ={
 chatList:[]
}
const getters={
  getChatList: (state)=>{
    return state.chatList
  },

}
const mutations={
  setChatList:function(state,chatList){
    state.chatList=chatList
  },
  addChat:function (state,data) {
    state.chatList[data.index].list.push(data.chat)
  }
}
const actions={
  setChatList:function(context,chatList){
    return new Promise(resolve => {
      context.commit('setChatList',chatList)
    })
  },
  addChat:function (context,data) {
    return new Promise(resolve=>{
      context.commit('addChat',data)

    })
  }
}
const chat={
  state,
  getters,
  mutations,
  actions
}
export  default  chat
