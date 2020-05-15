<template>
    <div class="warp">
      <div class="content">
        <div class="left">
          <img :src="user.headurl">
        </div>
        <div class=" center">
          <div class="top">{{user.nickname}}</div>
          <div class="bottom">
            <span class="iconfont" v-if="user.gender===0" >&#xe607;</span>
            <span class="iconfont" v-else>&#xe609;</span>
          </div>
        </div>
        <div class="right">
          <span class="add-button" @click="addUser">添加好友</span>
        </div>
      </div>
    </div>
</template>

<script>
    export default {
        name: "userItem",
      props:['user'],
      methods:{
          addUser:function () {
            this.$emit('addUser',this.user.id)
          }
      }
    }
</script>

<style scoped lang="stylus">
  .warp
    width: 100%
    .content
      padding 0.2rem
      width: 100%
      height 1rem
      display flex
      flex-direction row
      .left
        flex-grow 1
        img
          height 1rem
          width: 1rem
          border-radius 1rem
      .center
        flex-grow 8
        display flex
        flex-direction column
        align-items self-start
        .top ,.bottom
          height 0.5rem
          line-height 0.5rem
      .right
        margin-right 0.4rem
        flex-grow 1
        height 0.6rem
        line-height 0.6rem
        background-color orange
        padding 0.05rem
        border-radius 0.5rem
        text-align center

</style>
