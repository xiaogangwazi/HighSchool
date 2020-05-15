<template>
    <div class="warp">
      <div class="header">
        <span class="iconfont ico" @click="back">&#xe744;</span>
        <span class="nickname">{{content}}</span>
      </div>
    </div>
</template>

<script>
    export default {
        name: "back",
      props:['content'],
      methods:{

          back:function () {
            this.$router.go(-1)
          }

      }
    }
</script>

<style scoped lang="stylus">
  .warp
    width 100%
    .header
      position fixed
      top: 0
      width 100%
      height 1rem
      line-height 1rem
      background-color #eee
      display flex
      .ico
        flex-grow 5
        padding-left 0.2rem
        font-size 0.36rem
      .nickname
        flex-grow 6
        text-align left


</style>
