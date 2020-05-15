<template>
  <div id="app">
    <router-view/>
    <div v-if="getshowbottombar">
    <home-bottom class="home-bottom"></home-bottom>
    </div>
  </div>
</template>

<script>

  import homeBottom from './components/homeBottom'
  import {mapGetters} from 'vuex'
export default {
  name: 'App',
  components:{
    homeBottom
  },
  data:function(){
    return {
      websocket: null
    }
  },
  created(){
    const that = this
    that.initWebsocket()
    that.getChatList1()
    that.getNotice1()
  },
  mounted(){
    const userinfo=JSON.parse(localStorage.getItem("user"))
    console.log(userinfo)
    this.$store.dispatch('updateUser',userinfo)
  },
  methods:{
    getNotice1:function(){
      const that = this
      return new Promise(resolve => {
        const userid=JSON.parse(localStorage.getItem("user")).id
        that.axios.get(that.remote+"/notice/getNotice?userid="+userid).then(resolve=>{
          var list= JSON.parse(JSON.stringify(resolve.data.result.noticeList))
          that.$store.dispatch('setNotice',list)
          that.$store.dispatch('setUnreadCount',resolve.data.result.unreadCount)
        }).catch(error=>{
          console.log(error)
        })
      })

    },
    getChatList1:function(){
      const that = this
      return new Promise(resolve => {
        const userid=JSON.parse(localStorage.getItem("user")).id
        that.axios.get(that.remote+"/chat/getchatList?userid="+userid).then(resolve=>{
          var list= JSON.parse(JSON.stringify(resolve.data.result.chatList))
          that.$store.dispatch('setChatList',list)
        }).catch(error=>{
          console.log(error)
        })
      })

    },
    initWebsocket:function () {
      const that = this
      return new Promise(resolve => {
        if(localStorage.getItem("islogin")==='true'){
          const userid=JSON.parse(localStorage.getItem("user")).id
          that.websocket=new WebSocket(that.ws+"/websocket/"+userid)
          that.websocket.onopen=that.websocketOnOpen
          that.websocket.onerror=that.websocketOnError
          that.websocket.onmessage= that.websocketOnMessage
          that.websocket.onclose=that.websocketOnClose
          that.$store.commit('setWebSocket',that.websocket)
        }
      })

    },
    websocketOnOpen:function () {
        console.log("连接服务器成功！")
      this.websocket.send("你好服务器,这是来自客户端的消息")
    },
    websocketOnError:function (e) {
        console.log("websocket出错："+e.data)
    },
    websocketOnMessage:function (e) {
    console.log("接收到消息："+e.data)
      const data= JSON.parse(e.data)
      if(data.type=='message'){//私信消息体
        const length=this.getChatList.length
        for(var i=0;i<length;i++){
            if(this.getChatList[i].target.id==data.userid){
              this.getChatList[i].unreadCount+=1
              this.getChatList.unreadCount+=1
              this.getChatList[i].list.push(data.data.chat)
            }
        }
      }else if(data.type=='friend_add'){//添加好友申请
            console.log(data)
            this.$store.dispatch('addNotice',data.data.notice.notice);
      }
    },
    websocketOnClose:function (e) {
          console.log("关闭连接："+e.code)
    }
  },
  computed:{
    ...mapGetters(['getshowbottombar','getChatList'])

  },
  beforeRouteEnter(to,from,next){
    console.log("从"+from.path+"到"+to.path)
    if(to.path.match('/home')||to.path.match('/community')||to.path.match('/my'||to.path==='/')||to.path.match('/search'||to.path==='/message')){
      this.$store.dispatch('upBottombar')
    }else{
      this.$store.dispatch('hiddenBottombar')
    }
    next()
  }
}
</script>

<style lang="stylus" scoped>
  .home-bottom
    position fixed
    bottom 0rem
    z-index 2
  .icon {
    width: 0.3em;
    height: 0.3em;
    vertical-align: -0.15em;
    fill: currentColor;
    overflow: hidden;
  }
</style>
