<template>
    <div class="wrap">
      <div class="content">
        <input name="content" class="myinput" ref="content">
        <button class="mybutton" @click="send()">发送</button>
      </div>
    </div>
</template>

<script>
    export default {
        name: "myInput",
        methods:{
          send:function () {
            var input=this.$refs.content.value
            this.$refs.content.value=""
            this.$emit('send',input)

          }
        }
    }
</script>

<style scoped lang="stylus">
    .wrap
      background-color #fff
      width: 100%
      padding 0.14rem 0 0.14rem 0
      line-height 1rem
      .content
        height 0.8rem
        width 100%
        background-color #FFFFFF
        display flex
        flex-direction row
        justify-content center
        justify-items center
        .myinput,.mybutton
          box-sizing: border-box;
          height:0.8rem;
          border-radius:1rem;
          border:0.02rem solid #c8cccf;
          color:#6a6f77;
          -web-kit-appearance:none;
          -moz-appearance: none;
          display:block;
          outline:0;
          text-decoration:none;
        .myinput
          flex-grow 8
          font-size 0.36rem
        .mybutton
          flex-grow 2




</style>
