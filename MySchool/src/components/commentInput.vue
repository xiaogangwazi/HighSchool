<template>
    <div class="warp">
      <div class="content">
        <input type="text" :placeholder="placehoder" ref="content">
        <button @click="send()">发布</button>
      </div>
    </div>
</template>

<script>
    export default {
        name: "commentInput",
      props:['place'],
      computed:{
        placehoder:function () {
          return this.place===""||this.place==null?"哎呦不错哦...":this.place
        }
      },
      methods: {
        send: function () {
          var input = this.$refs.content.value
          this.$refs.content.value = ""
          this.$emit('send', input)

        }
      }
    }
</script>

<style scoped lang="stylus">
    .warp
      width:100%
      position fixed
      height 0.8rem
      line-height 0.8rem
      bottom 0rem
      background-color #fff
      .content
        padding 0.1rem
        height 0.8rem
        line-height 0.8rem
        display flex
        flex-direction row
        input,button
          line-height 0.6rem
          box-sizing: border-box;
          height:0.6rem;
          border-radius:1rem;
          border:0.01rem solid #c8cccf;
          color:#6a6f77;
          -web-kit-appearance:none;
          -moz-appearance: none;
          display:block;
          outline:0;
          text-decoration:none;
          background-color #FEF
        input
          flex-grow 6
        button
          flex-grow 2
        .iconfont
          flex-grow 2
          font-size 0.36rem
          text-align center



</style>
