<template>
    <div class="warp">
      <back content="好友列表"></back>
      <div class="content" v-if="getFriendList.length!=0">
        <div class="user" v-for="item,index in getFriendList" :key="index" @click="tochatPage(item)">
          <user :user="item.userinfo" v-if="userid==item.userid"></user>
          <user :user="item.user" v-else></user>
        </div>
      </div>
    </div>
</template>

<script>
  import user from '../../components/user'
  import {mapGetters} from 'vuex'
  import Back from "../../components/back";
    export default {
        name: "friendList",
      computed:{
        ...mapGetters(['getFriendList','getChatList']),
        userid:function () {
          return JSON.parse(localStorage.getItem("user")).id
        }
      },
      components:{
        Back,
        user
      },
        mounted() {
          const that =this
          const userid= JSON.parse(localStorage.getItem("user")).id
            that.axios.post(that.remote+"/friend/getFriendList?userid="+userid).then(resolve=>{
                  that.$store.dispatch('setFriendList',resolve.data.result.friendList)
            }).catch(error=>{
              console.log(error)
            })

        },
      methods:{
        tochatPage:function (item) {
          console.log(item)
          var userid=item.userid
          var targetid= item.targetid
          var index =-1
          var ui=JSON.parse(localStorage.getItem("user")).id
          var target = ui==userid?item.target:item.user
          var ci=userid>targetid?targetid+"_"+userid:userid+"_"+targetid
          for(var i=0;i<this.getChatList.length;i++){
              if(this.getChatList[i].list[0].conversationid==ci){
                    index=i
                break
              }
          }
          if(index!=-1){
            this.$router.push({path:'/message/chatpage',query:{chatIndex:index}})
          }else{
              var chat={
                target:target,
                list:[],
                unreadCount:0
              }
              this.$store.dispatch('addChat',chat)
            this.$router.push({path:'/message/chatpage',query:{chatIndex:this.getChatList.length-1}})
          }
        }
      }
    }
</script>

<style scoped lang="stylus">
.warp
  width: 100%
  .content
    margin-top 1rem
</style>
