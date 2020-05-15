const state ={
 friendList:[]
}
const getters={
  getFriendList: (state)=>{
    return state.friendList
  }

}
const mutations={
  setFriendList:function(state,frinedList){
    state.friendList=frinedList
  }
}
const actions={
  setFriendList:function(context,friendList){
    return new Promise(resolve => {
      context.commit('setFriendList',friendList)
    })
  },

}
const friend={
  state,
  getters,
  mutations,
  actions
}
export  default  friend
