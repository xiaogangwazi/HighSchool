<template>
    <div class="warp" @click="toMessageDetail">
      <div class="content-item">
        <div class="head">
          <span>{{message.user.nickname}}</span>
          <span>{{time}}</span>
        </div>
        <div class="title">
          <span>{{message.title}}</span>
          <span class="topicname">#{{message.topicname}}</span>
        </div>

        <div class="button">
          <span class="iconfont">&#xe604; {{message.commentsCount}}</span>
          <span class="iconfont">&#xe8be;</span>
        </div>
      </div>
    </div>

</template>

<script>
  import  qs from 'qs'
    export default {
        name: "messageItem",
      props:['message'],
      methods:{
          toMessageDetail(){

            this.$router.push({path:'/message/detail',query:{messageid:this.message.id}})

          }
      },
      computed:{
        time:function () {
          return this.timeUtil.formatDate(this.message.publictime)
        }
      }
    }
</script>

<style scoped lang="stylus">

.warp
  width: 100%
  .content-item
    border-bottom 0.01rem solid #eee
    padding  0.2rem
    display flex
    flex-direction column
    height 1.6rem
    .head
      height 0.3rem
      line-height 0.3rem
      span
        margin-right 0.5rem
        font-size 0.24rem
        font-weight lighter
    .title
      height 1rem
      line-height 1rem
      span
        font-weight bolder
        font-size 0.36rem
      .topicname
        font-size 0.2rem
        color #aaa
    .button
      span
        font-size 0.2rem
        margin-right  0.8rem


</style>
