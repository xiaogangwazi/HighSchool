<template>
    <div class="warp" @click.stop="publish">
      <div class="content" >
        <span class="iconfont">&#xe631;</span>
      </div>
    </div>
</template>

<script>
  import  {mapGetters} from 'vuex'
    export default {
        name: "publish",
        data:function () {
          return{
            topicid:null
          }
        },
      computed:{
          ...mapGetters('getTopicId')
      },
      methods:{
          publish:function () {
            this.$emit('toPublishPage',this.getTopicId)
          }
      }
    }
</script>

<style scoped lang="stylus">
  .warp
    width: 1rem
    height 1rem
    position fixed
    z-index 10
    bottom 2rem
    right 1rem
    border-radius 1rem
    .content
      width: 1rem
      height 1rem
      .iconfont
        font-size 1rem
        color red


</style>
