<template>
    <div class="warp">
    <back content="通知"></back>
      <div class="content">
        <div v-if="getNotice.length>0" class="notice">
            <div v-for="item ,index in getNotice" :key="index" class="notice-item">
            <notice-item :item="item"></notice-item>
            </div>
        </div>
        <div v-else class="notice_else">
          <span>暂无通知</span><br>
          <button class="mybutton iconfont" @click="refresh()"  type="primary"
                  v-loading.fullscreen.lock="fullscreenLoading">&#xe650;</button>
        </div>
      </div>
    </div>
</template>

<script>
  import  back from '../../components/back'
  import {mapGetters} from 'vuex'
  import noticeItem from '../../components/notice-item'
    export default {
        name: "noticepage",
      components:{
          back,
        noticeItem
      },
      data:function(){
        return{
          fullscreenLoading:false
        }
      },
      computed:{
        ...mapGetters(['getNotice'])
      },
      methods:{
          refresh:function(){
            this.fullscreenLoading=true
              const that = this
              return new Promise(resolve => {
                const userid=JSON.parse(localStorage.getItem("user")).id
                that.axios.get(that.remote+"/notice/getNotice?userid="+userid).then(resolve=>{
                  var list= JSON.parse(JSON.stringify(resolve.data.result.noticeList))
                  that.$store.dispatch('setUnreadCount',resolve.data.result.unreadCount)
                  that.$store.dispatch('setNotice',list)
                }).catch(error=>{
                  console.log(error)
                }).finally(()=>{
                  that.fullscreenLoading=false
                })
              })
          },
        formatTime:function(t) {
          return this.timeUtil.formatDate(t)
        }
      },
      created:function(){
        this.$store.dispatch('hiddenbottombarAndshowmyinput')
      },
      beforeDestroy:function(){
        this.$store.dispatch('hiddenmyinputAndshowbottombar')
      },
    }
</script>

<style scoped lang="stylus">
  .mybutton
    box-sizing: border-box;
    height:0.8rem;
    border-radius:1rem;
    border:0.02rem solid #c8cccf;
    color:#6a6f77;
    -web-kit-appearance:none;
    -moz-appearance: none;
    outline:0;
    text-decoration:none;
.notice_else
  margin-top 8rem
  text-align center
  .iconfont
    font-size 0.4rem
.warp
  width: 100%
  .content
    margin-top 1rem
    .notice-item
      width: 100%
      height:1rem
      line-height 1rem
      border-bottom 0.02rem solid #eee
      span
        margin-left 0.2rem
</style>
