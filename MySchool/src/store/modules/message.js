const state ={
  //底部bar参数

  sameSchool:[],
  recommended:[]

}
const getters= {
  getSameSchool: (state) => {
    return state.sameSchool
  },
  getRecommended: (state) => {
    return state.recommended
  }
}
  const mutations={
  updateSameSchool:(state,sameSchool)=>{
    state.sameSchool=sameSchool
  },
  updateRecommended:(state,recommended)=>{
    state.recommended=recommended
  }
}
const actions={
  updateSameSchool:function(context,sameSchool){
    return new Promise(resolve => {
      context.commit('updateSameSchool',sameSchool)
    })
  },
  updateRecommended:function(context,recommended){
    return new Promise(resolve => {
      context.commit('updateRecommended',recommended)
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
