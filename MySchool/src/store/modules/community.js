const state ={
 communityList:[],
  topic:null,
  topicid:null,
  message:null,
  comments:null,
  focusMap:new Map(),//保存我关注的列表，key是id,
  focusMessage:[]

}
const getters={
  getFocusMessage:function (state) {
    return state.focusMessage
  },
  getCommunityList:function (state) {
    return state.communityList
  },
  getTopic:function (state) {
    return state.topic
  },
  getMessage:function (state) {
    return state.message
  },
  getComments:function (state) {
    return state.comments
  },
  getFocusMap:function (state) {
    return state.focusMap
  },
  getTopicId:function (state) {
    return state.topicid
  },
}
const mutations={
      setFocusMessage:function (state,focusMessage) {
        state.focusMessage=focusMessage
      },
      setTopicId:function (state,id) {
        state.topicid=id
      },
      setCommunityList:function (state,list) {
            state.communityList=list
      },
      setTopic:function (state,topic) {
        state.topic=topic
      },
      setMessage:function (state,message) {
        state.message=message
      },
      setComments:function (state,comments) {
         state.comments=comments
      },
      setLike:function(state,like){
        state.message.like=like
      },
      addFocusMap(state,key){
        state.focusMap.set(key,true)

      },
      deleteFocusMap(state,key){
        state.focusMap.delete(key)
      }
}
const actions={
      updateFocusMessage:function(context,focusMessage){
        return new Promise(resolve=>{
          context.commit('setFocusMessage',focusMessage)
        })
      },
      updateTopicId:function(context,id){
        return new Promise(resolve=>{
          context.commit('setTopicId',id)
        })
      },
      updateCommunityList:function (context,list) {
        return new Promise(resolve => {
          context.commit('setCommunityList',list)
        })
      },
      updateTopic:function (context,topic) {
        return new Promise(resolve => {
          context.commit('setTopic',topic)
        })
      },
      updateMessage:function (context,message) {
        return new Promise(resolve => {
          context.commit('setMessage',message)
        })
      },
      updateComments:function (context,list) {
        return new Promise(resolve => {
          context.commit('setComments',list)
        })
      },
      addFocus:function(context,key){
        return new Promise(resolve => {
          context.commit('addFocusMap',key)
        })
      },
      deleteFocus:function(context,key){
        return new Promise(resolve => {
          context.commit('deleteFocusMap',key)
        })
      },
  setLike:function(context,like){
    return new Promise(resolve => {
      context.commit('setLike',like)
    })
  }

}
const community={
  state,
  getters,
  mutations,
  actions
}
export  default  community
