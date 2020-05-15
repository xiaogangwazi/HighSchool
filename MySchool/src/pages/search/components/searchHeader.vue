<template>
    <div class="warp">
      <div class="header-left"> my</div>
      <div class="header-center">
        <input type="text" v-model="content">
      </div>
      <div class="header-right">
        <span class="iconfont">&#xe64d;</span>
        <span @click="publish">搜索</span>
      </div>
    </div>
</template>

<script>
    export default {
        name: "searchHeader",
      props:['content'],
      data:function () {
        return{
        }
      },
      methods:{
          publish:function () {
            if(this.content!=null&&this.content!=''){
              console.log(this.content)
              this.$emit('search',this.content)
            }
          }
      }
    }
</script>

<style scoped lang="stylus">
  .warp
    margin-top .4rem
    display flex
    flex-direction row
    width: 100%
    height: 1rem
    line-height 1rem
    align-items center
    .header-left
      text-align center
      width:15%
    .header-center
      padding-left 0.1rem
      margin-top:0.12rem
      margin-bottom:0.12rem
      height:0.64rem
      line-height 0.64rem
      width:70%
      input
        box-sizing: border-box;
        height:0.64rem;
        border-radius:1rem;
        border:0.02rem solid #c8cccf;
        color:#6a6f77;
        -web-kit-appearance:none;
        -moz-appearance: none;
        display:block;
        outline:0;
        text-decoration:none;
        width 100%;
    .header-right
      text-align center
      width:15%



</style>
