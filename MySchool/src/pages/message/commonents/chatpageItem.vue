<template>
  <div class="warp">
    <div class="content">
        <div class="time">
          <span>{{time}}</span>
        </div>
        <div class="left" v-if="item.fromid===fromid">
          <img class="left-img" :src="item.fromuser.headurl+sub">
          <div class="left-content">{{item.content}}</div>
        </div>
        <div class="right" v-else>
          <img class="right-img" :src="item.fromuser.headurl+sub">
          <div class="right-content">{{item.content}}</div>
        </div>
      </div>
    </div>
</template>

<script>
  export default {
    name: "chatpageItem",
    props:['item','fromid'],
    computed:{
      time:function () {
        return this.timeUtil.formatDate(this.item.time)
      },
      sub:function () {
        return '?t='+Math.random()
      }

    }
  }
</script>

<style scoped lang="stylus">
    .content
      height auto
      width:100%
    .time
      font-size 0.1rem
      font-weight lighter
      text-align center
    .left,.right
      display flex
      padding:0.2rem
    .left
      flex-direction row
    .right
      flex-direction row-reverse
    .left-img,.right-img
      width 1rem
      height 1rem
      border-radius 50%
      margin 0 0.2rem 0 0.2rem
    .left-content,.right-content
      background-color #78DDF8;
      height auto
      border-radius 20%
      padding 0.2rem
      display block
      .left-content
        position absolute
        left: 1rem
      .right-content
        position absolute
        right 1rem



</style>
