<template>
    <div class="warp">
      <back :content="chat.target.nickname"></back>
      <div class="content1" id="val" ref="chatContent">
     <div class="content-item" v-for="item,index in chat.list" :key="index">
       <chatpage-item v-bind:item="item":item="item" :fromid="chat.target.id"></chatpage-item>
     </div>
      </div>
      <div>
      <my-input class="myinput" @send="send"></my-input>
      </div>
    </div>
</template>

<script>
  import chatpageItem from './commonents/chatpageItem'
  import myInput from '../../components/myInput'
  import  {mapGetters} from 'vuex'
  import back from '../../components/back'
  import  qs from 'qs'
    export default {
      name: "chatpage",
      components:{
          chatpageItem,
          myInput,
          back
      },
      data:function () {
        return{
          chat:null,
          index:0,
          change:0,
          offset:0
        }
      },
      methods:{
        scrollMessage:function(){

        },
        send:function (data) {
          var userid = JSON.parse(localStorage.getItem("user")).id
          var targetid = this.chat.target.id
          const pa = {
            userid:userid,
            targetid:targetid,
            content:data
          }
          const that=this
         this.axios.post(this.remote+"/chat/send",qs.stringify(pa)).then(resolve=>{
          if (resolve.data.code===200){
            const chat = resolve.data.result.chat
            console.log(chat)
            console.log(typeof that.getChatList)
            const data ={
              index:that.index,
              chat:chat
            }
            that.$store.dispatch('addChat',data)
           that.handleScroll()
          }
         }).catch(error=>{
           console.log(error)
         })
        },
        back:function () {
          this.$router.go(-1)
        }
      },
      computed:{
         ...mapGetters(['getshowmyinput','getChatList'])

      },
      created:function(){
        this.$store.dispatch('hiddenbottombarAndshowmyinput')
      },
      beforeDestroy:function(){
        this.$store.dispatch('hiddenmyinputAndshowbottombar')
      },

      mounted() {
       
        const index = this.$route.query.chatIndex
        this.index = index
        var count = this.getChatList[index].unreadCount
        var length = this.getChatList[index].list.length
        this.chat = this.getChatList[index]
        console.log(this.chat.unreadCount)
        if (count != 0) {
          var list = []
          var chatlist = this.getChatList[index].list
          for (var i = 0; i < count; i++) {
            list.push(chatlist[length - 1].id)
            length--;
          }
          const that = this
          this.axios.delete(this.remote + "/chat/read?list=" + list).then(resolve => {
                that.getChatList[index].unreadCount=0
          }).catch(error => {
            console.log(error)
          })
        }
      }
    }
</script>

<style scoped lang="stylus">
  .myinput
    position fixed
    bottom 0rem
    z-index 2
  .warp
    width: 100%
    .content1
      overflow auto
      margin-top 1rem
      margin-bottom 1rem
      .content-item
        width:100%
        .left,.right
          display flex
          padding:0.2rem
        .left
          flex-direction row
        .right
          flex-direction row-reverse
        .left-img,.right-img
          width 1rem
          height 1rem
          border-radius 50%
        .left-content,.right-content
          background-color #78DDF8;
          width auto
          height auto
          border-radius 10%
          padding 0.2rem



</style>
