<template>

  <div class="warp">
    <div class="back">
    </div>
    <div class="header" >
      <div class="header-top">
        <span class="iconfont top-ioc">&#xe627;</span>
        <span class="iconfont top-ioc" @click="tosetUserpage()">&#xe60c;</span>
      </div>
      <div class="userInfo">
        <span class="head" ><img :src="headUrl"></span>
        <span class="name">{{userinfo.nickname}}</span>
        <span class="name score">{{range}}</span>
        <span v-if="userinfo.gender === 0" class="iconfont gender" style="color: #d4237a">&#xe607;</span>
        <span v-else class="iconfont gender" style="color: #1296db">&#xe609;</span>
        <span class="iconfont ico" @click="toUserinfoPage">&#xe743;</span>
      </div>
      <div class="focus">
        <span class="focus-item focus-item1">关注{{userinfo.focus}}</span>
        <span class="focus-item">关注者{{userinfo.noticer}}</span>
      </div>
      <div class="header-center">
        <div class="center-item" @click="toNoticePage">
          <span class="iconfont one">&#xe62e;</span>
          <span class="tow">叮铃</span>
          <span v-if="getUnreadCount!=0" class="count">{{getUnreadCount}}</span>
          <span class="iconfont three">&#xe743;</span>
        </div>
        <div class="center-item">
          <span class="iconfont one">&#xe605;</span>
          <span class="tow">我的收藏</span>
          <span class="iconfont three">&#xe743;</span>
        </div>
        <div class="center-item">
          <span class="iconfont one">&#xe61f;</span>
          <span class="tow">设置</span>
          <span class="iconfont three">&#xe743;</span>
        </div>
        <div class="center-item">
          <span class="iconfont one">&#xe635;</span>
          <span class="tow">帮助与反馈</span>
          <span class="iconfont three">&#xe743;</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  export default {
    name: "my",
    data:function () {
      return {
        user:null
      }
    },
    computed:{
      ...mapGetters(['getUnreadCount']),
      range:function () {
        const score= this.userinfo.score
        if(score<=500){
          return "新生代"
        }else if(score<2000){
          return "初出茅庐"
        }else if (score<10000){
          return "校园大使"
        }else {
          return "校园担当"
        }
      },
      userinfo:function(){
      return JSON.parse(localStorage.getItem("user"))
      },

      headUrl:function() {
        return this.userinfo.headurl+'?t='+Math.random()
  }
    },

    methods: {
      toNoticePage: function () {
        this.$router.push("/user/noticepage")
      },
      tosetUserpage: function () {
        this.$router.push("/user/setUserpage")
      },
      toUserinfoPage: function () {
        this.$router.push("/user/userinfoPage")
      },
      getNotice1: function () {
        console.log("更新")
        const that = this
        return new Promise(resolve => {
          const userid = JSON.parse(localStorage.getItem("user")).id
          that.axios.get(that.remote + "/notice/getNotice?userid=" + userid).then(resolve => {
            var list = JSON.parse(JSON.stringify(resolve.data.result.noticeList))
            that.$store.dispatch('setNotice', list)
            that.$store.dispatch('setUnreadCount', resolve.data.result.unreadCount)
          }).catch(error => {
            console.log(error)
          })
        })

      }
  },

    mounted() {
      this.getNotice1()

    }
  }
</script>

<style scoped lang="stylus">
  .count
    background-color red
    color: #FFFFFF
    border-radius 50%
    text-align center
    height 0.36rem
    line-height 0.36rem
    font-size 0.36rem
    width:0.36rem
    margin-top 0.24rem
    padding 0.04rem
    font-weight lighter

  .warp
    width: 100%
    background-repeat no-repeat
    background-size 100% 100%
    position absolute
    .back
      position absolute
      opacity 0.5
      z-index 0
      width 100%
      height 20rem
    .header
      position absolute
      width: 100%
      height 20rem
      z-index 1
      .header-top
        height 1rem
        line-height 1rem
        display flex
        justify-content space-between
        margin .4rem .4rem
        .top-ioc
          font-size: 0.6rem
          justify-items center
      .userInfo
        border-bottom 0.01rem solid #eee
        padding  0.2rem 0
        width: 100%
        height: 2rem
        display: flex
        flex-direction row
        line-height 2rem
        justify-content center
        justify-items center
        .head,.name,.ico,.gender
          line-height 1rem
          height 1rem
          font-size 0.48rem
        .head
          flex-grow 2
          text-align center
          img
            width:1rem
            height 1rem
            text-align center
            line-height 1rem
            border-radius 50%
            text-align center
        .name
            flex-grow:5
            font-size 0.36rem
            font-weight bolder
            text-align left
         .score
            height 0.8rem
            line-height 0.8rem
            text-align center
            background-color #ffa751
            border-radius 1rem
        .ico
            flex-grow 1
        .gender
            flex-grow 1
            text-align left
      .focus
        width: 100%
        height: 1rem
        line-height 1rem
        display flex
        flex-direction row
        text-align center
        border-bottom 0.01rem solid #eee
        .focus-item
          width: 50%
          line-height 1rem
          height:1rem
          font-weight bolder
          color #fff
        .focus-item1
          border-right 0.01rem solid #eee
      .header-center
        margin-top 1rem
        width 100%
        height 4rem
        display flex
        flex-direction column
        .center-item
          width: 100%
          height 1rem
          line-height 1rem
          display flex
          font-size 0.48rem
          flex-direction row
          .one
            width: 10%
            text-align center
          .tow
            text-align left
            width: 80%
          .three
            width: 10%



</style>
