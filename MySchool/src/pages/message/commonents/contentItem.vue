<template>
    <div class="warp" @click="tochatPage()">
      <div class="content">
      <div class="left">
      <img :src="data.target.headurl">
      </div>
      <div class="center">
        <span class="name">{{data.target.nickname}}</span>
        <span class="message">{{data.list[index].content}}</span>
      </div>
      <div class="right">
        <span class="time">{{time}}</span>
        <div class="count">
        <span class="unreadCount" v-show="unreadCount!=0">{{unreadCount}}</span>
        </div>
      </div>
      </div>
    </div>
</template>

<script>
    export default {
        name: "contentItem",
      props:['data'],
      computed:{
          time:function () {
            var da= this.data.list[0].time
            const date= new Date(da)
            date.setDate(date.getDate())
            return this.timeUtil.formatDate(date)
          },
          index:function () {
             const index=this.data.list.length-1
            return  index
          },
        unreadCount:function () {
         return this.data.unreadCount
        }
      },
      methods:{
        tochatPage:function () {
          this.$emit('tochatPage')
        }
      }
    }
</script>

<style scoped lang="stylus">
  .warp
    border-radius 5%
    width: 100%
    .content
      width 100%
      display flex
      flex-direction row
      .left
        flex-grow 0.4
        height 1.8rem
        line-height 1.8rem
        text-align center
        img
          width: 1.2rem
          height 1.2rem
          margin 0.3rem
          border-radius 50%
      .center
        text-align left
        padding 0.1rem
        height 1.8rem
        flex-grow 7
        display flex
        flex-direction column
        .name
          font-weight bolder
          margin-bottom 0.1rem
        .message
          font-size 0.06rem
      .right
        flex-grow 2
        height 1.8rem
        padding-right 0.2rem
        line-height 1rem
        text-align right
        font-size:0.06rem
        font-weight lighter
        display flex
        flex-direction column
        .count
          height 0.9rem
          line-height 0.45rem
          padding-right 1rem
          .unreadCount
            background-color #ff2514
            color #FFFFFF
            border-radius 50%
            padding 0.05rem 0.1rem 0.05rem 0.1rem


</style>
