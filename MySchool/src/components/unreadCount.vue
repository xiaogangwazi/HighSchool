<template>
    <div class="warp">
      <div class="content" v-if="count!=0"><span>{{count}}</span></div>
    </div>
</template>

<script>
    export default {
        name: "unreadCount",
      props:['count']
    }
</script>

<style scoped lang="stylus">
.content
  width:0.5rem
  height 0.5rem
  text-align center
  line-height 0.5rem
  background-color red
  color #FFFFFF
  border-radius 1rem

</style>
