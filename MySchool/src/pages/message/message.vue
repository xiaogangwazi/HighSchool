<template>
    <div class="wrap">
      <div class="header">
        <div class="title "><span>消息</span></div>
        <div class="ico "><span class="iconfont" @click="toUserListPage()">&#xe722;</span></div>
        <div  class="ico " @click="toSearchUserPage"><span class="iconfont">&#xe742;</span></div>
      </div>
      <div class="content">
      <div class="content-item" v-for="item,index in getChatList" :key="index">
        <content-item v-bind:data="item" :data="item" @tochatPage="tochatPage(index)"></content-item>
      </div>
      </div>
    </div>
</template>

<script>
  import  contentItem from './commonents/contentItem'
  import {mapGetters} from 'vuex'
  import  qs from 'qs'
    export default {
        name: "message",
      data:function () {
        return{
        }
      },
      components:{
          contentItem
      },

      methods:{
        toUserListPage:function(){
          this.$router.push({path:'/message/friendList'})
        },
        tochatPage:function (index) {
          this.$router.push({path:'/message/chatpage',query:{chatIndex:index}})
        },
        toSearchUserPage:function () {
        this.$router.push("/message/searchUserPage")
        }
      },
      computed:{
        ...mapGetters(['getChatList'])
      },
      mounted() {

      }
    }
</script>

<style scoped lang="stylus">
  .wrap
    width 100%
    .header
      width 100%
      height 1rem
      line-height 1rem
      border-bottom 0.01rem solid #eee
      display: flex
      flex-direction row
      justify-content center
      .title
        flex-grow 8
        text-align center
        margin-left 20%
      .ico
        flex-grow 1
        height 1rem
        line-height 1rem
        text-align center
      .iconfont
        font-size:0.6rem



</style>
