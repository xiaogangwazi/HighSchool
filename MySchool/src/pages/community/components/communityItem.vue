<template>
    <div class="warp">
      <div class="right-item" v-for="item,index in data" :slot="index" :key="index" :id="index">
        <div class="child-item" v-for="child,i in item.value">
          <img :src="child.imgurl" class="child-img">
          <span class="child-name">{{child.name}}</span>
        </div>
      </div>
    </div>
</template>

<script>
    export default {
        name: "communityItem",
        props:['data']
    }
</script>

<style scoped lang="stylus">
  .content-right
    overflow-y scroll
    z-index 0
    position absolute
    float right
    right 0
    width 80%
    .right-item
      width 100%
      display flex
      flex-direction row
      justify-content center
      justify-items center
      flex-wrap wrap
      border 0.01rem solid #eee
      .child-item
        display flex
        flex-direction column
        margin .4rem
        .child-img
          width 1.6rem
          height 1.6rem
        .child-name
          text-align center
</style>
