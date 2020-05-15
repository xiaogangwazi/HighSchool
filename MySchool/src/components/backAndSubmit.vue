<template>
    <div class="warp">
      <div class="header">
        <span class="iconfont ico" @click="back">&#xe744;</span>
        <span class="nickname">{{content}}</span>
        <span @click="submit" class="submit" v-if="title!=null">{{title}}</span>
        <span @click="submit" class="submit" v-else>提交</span>
      </div>
    </div>
</template>

<script>
    export default {
        name: "back",
      props:['content','title'],
      methods:{

          back:function () {
            this.$router.go(-1)
          },
        submit:function () {
          this.$emit('tosubmit')
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
      justify-content space-between
      .ico
        padding-left 0.2rem
        font-size 0.36rem
      .submit
        margin-right  0.2rem



</style>
