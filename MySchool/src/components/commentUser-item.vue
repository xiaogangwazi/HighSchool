<template>
  <div class="warp">
    <div class="user">
      <div class="user-left">
        <img :src="user.headurl">
      </div>
      <div class="user-right">
        <span class="right-name">{{user.nickname}}<span class="poster" v-if="poster!=''">{{poster}}</span></span>

        <span class="right-time">{{publictime}}</span>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
    export default {
        name: "commentUser-item",
      props:['user','time'],
      computed:{
          ...mapGetters(['getMessage']),
          publictime:function () {
            return this.timeUtil.formatDate(this.time)
          },
          poster:function () {
              return this.getMessage.publicid===this.user.id?"楼主":""
          },

      },
    }
</script>

<style scoped lang="stylus">
  .poster
    color #fff
    margin 0.1rem
    padding 0.04rem
    background-color red
    border-radius 1rem
    font-size:12px
  .warp
    width: 100%
    .user
      padding 0.2rem
      width: 100%
      height:1rem
      line-height 1rem
      display flex
      flex-direction row
      .user-left
        width:1rem
        height 1rem
        line-height 1rem
        text-align center
        img
          width:0.6rem
          height 0.6rem
          line-height 1rem
          border-radius 1rem
      .user-right
        height 1rem
        display flex
        flex-direction column
        .right-name,.right-time
          height 0.5rem
          line-height 0.5rem
          font-size 0.08rem
          font-weight:lighter



</style>
