<template>
    <div class="warp">
      <div class="user">
        <div class="user-left">
          <img :src="user.headurl">
        </div>
        <div class="user-right">
          <span class="right-name">{{user.nickname}}</span>
          <span class="right-time">{{publictime}}</span>
        </div>
      </div>
    </div>
</template>

<script>
    export default {
        name: "UserHead",
      props:['user','time'],
      computed:{
          publictime:function () {
            return this.timeUtil.formatDate(this.time)
          }
      }
    }
</script>

<style scoped lang="stylus">
  .warp
    width: 100%
    .user
      padding 0.2rem
      width: 100%
      height:1.5rem
      line-height 1.5rem
      display flex
      flex-direction row
      .user-left
        width:1.5rem
        height 1.5rem
        line-height 1.5rem
        text-align center
        img
          width:0.8rem
          height 0.8rem
          line-height 1.5rem
          border-radius 1rem
      .user-right
        height 1.1rem
        display flex
        flex-direction column
        .right-name
          height 0.5rem
          line-height 0.5rem
          font-size 0.1rem
          font-weight:lighter
        .right-time
          height 0.5rem
          line-height 0.5rem
          font-size: 0.1rem
          font-weight lighter



</style>
