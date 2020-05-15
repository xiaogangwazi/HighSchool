<template>
    <div class="warp">
    <back content="关注"></back>
      <div class="content">
        <div v-for="item,index in getFocusMessage" :key="item.id">
          <message-item :message="item"></message-item>
        </div>
      </div>
    </div>
</template>

<script>
  import  publish from '../../components/publish'
  import Back from "../../components/back";
  import  {mapGetters} from 'vuex'
  import messageItem from '../../components/messageItem'
    export default {
        name: "communityfocus",
      components:{
        Back,
        publish,
        messageItem
      },
      computed:{
        ...mapGetters(['getFocusMessage'])
      },
      mounted() {
              const that = this
          this.axios.get(this.remote+"/message/getFocus?id="+JSON.parse(localStorage.getItem("user")).id).then(resolve=>{
            console.log(resolve.data.result.focusList)
            that.$store.dispatch('updateFocusMessage',resolve.data.result.focusList)
          }).catch(error=>{
            console.log(error)
          })
      }
    }
</script>

<style scoped lang="stylus">
.warp
  width: 100%
  .content
    z-index -2
    margin-top 1rem
    width: 100%
    height auto
    position absolute
    margin-bottom 1rem
</style>
