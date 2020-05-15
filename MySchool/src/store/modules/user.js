const state ={
 user:null

}
const getters={

  getUser: (state)=>{
    return state.user
  }
}
const mutations={
    setUser:function (state,user) {
      state.user=user
    }
}
const actions={
  updateUser:function(context,user){
    return new Promise(resolve => {
      console.log("更新用户信息")
      context.commit('setUser',user)
    })
  }

}
const user={
  state,
  getters,
  mutations,
  actions
}
export  default  user
