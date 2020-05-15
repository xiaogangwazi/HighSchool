<template>
    <div class="warp">
      <div class="content">
      <span v-if="more==true">加载中....</span>
      <span v-else>到底了</span>
      </div>
    </div>
</template>

<script>
    export default {
        name: "load-more",
      props:['more']
    }
</script>

<style scoped lang="stylus">
.warp
  width: 100%
  .content
    position fixed
    bottom 1rem
    left 0rem
    span
      text-align center
      font-size 0.24rem
      font-weight lighter

</style>
