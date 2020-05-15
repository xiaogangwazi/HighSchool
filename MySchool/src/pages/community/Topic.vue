<template>
  <div class="warp">
    <back></back>
    <div class="background">
      <div class="background-img" :style="'background-image: url('+this.$store.getters.getTopic.topic.backgroundUrl+')'"></div>
      <div class="background-content">
        <div class="back-left">
          <span class="name">#{{topic.topic.name}}</span>
          <span class="describe">{{topic.topic.describe}}</span>
        </div>
        <div class="back-right">
          <span class="iconfont focus" v-if="topicAndFocus.focus===0||focusMap.get(key)" @click="focus(1)">&#xe61d;关注</span>
          <span class="iconfont focused" v-else @click="focus(0)">&#xe61d;已关注</span>
        </div>
      </div>
    </div>
    <div class="content">
      <div v-for="item,index in topic.topic.messageList" :key="item.id">
        <message-item :message="item"></message-item>
      </div>
    </div>
    <publish @toPublishPage="toPublishPage"></publish>
  </div>
</template>

<script>
  import messageItem from '../../components/messageItem'
  import  back from '../../components/back'
  import  CommentInput from '../../components/commentInput'
  import {mapGetters} from 'vuex'
  import  qs from 'qs'
  import publish from '../../components/publish'
  export default {
    name: "Topic",
    components:{back,messageItem,publish,CommentInput},
    data:function () {
      return{
        topicAndFocus:{
          topic:null,
          focus:0
        },
      }
    },
    computed:{
      ...mapGetters({topic:'getTopic',focusMap:'getFocusMap',topicid:'getTopicId',getshowmyinput:'getshowmyinput'}),
    },
    methods:{
      toPublishPage:function(){
        this.$router.push('/publishPage')
      },
      key:function () {
        return this.topic.topic.id+":"+JSON.parse(localStorage.getItem('user')).id
      },
      focus:function (f) {
        const  that= this
        const userid= JSON.parse(localStorage.getItem("user")).id
        const form = new FormData()
        const config = {
          headers: { "Content-Type": "multipart/form-data;boundary="+new Date().getTime() }
        };
        form.append("topicid",that.topic.topic.id)
        form.append("userid",userid)
        form.append("focus",1)
          if(f===1) {
            that.axios.post(that.remote+"/topic/updateFocus",form,config).then(resolve=>{
                  if(resolve.data.code===200){
                    that.$message({
                      type: 'success',
                      message: '关注成功!'
                    });
                    that.$store.dispatch('addFocus',that.key())
                    that.topicAndFocus.focus=1

                  }
            }).catch(error=>{
              console.log(error)
            })
          }else{
            form.set("focus",0)
            that.$confirm('确定取消关注，在关注页将不再显示其相关内容, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning',
              center: true
            }).then(() => {
              that.axios.post(that.remote+"/topic/updateFocus",form,config).then(resolve=>{
                  if (resolve.data.code===200){
                    that.$message({
                      type: 'success',
                      message: '取消关注成功!'
                    });
                    that.$store.dispatch('deleteFocus',that.key())
                    that.topicAndFocus.focus=0
                  } else{
                    that.$message({
                      type: 'fail',
                      message: '取消关注失败，请重试！'
                    });
                  }
              }).catch(error=>{
                console.log(error)
                that.$message({
                  type: 'fail',
                  message: '取消关注失败，请重试!'
                });
              })

            })

          }
      }
    },
    mounted() {
      const id= this.topicid
      const userid= JSON.parse(localStorage.getItem("user")).id
      const  that = this
      const url = this.remote+"/topic/get"
      this.axios.get(url+"?"+qs.stringify({topicid:id,userid:userid})).then(resolve=>{
        if(resolve.data.result!=null) {
          console.log(resolve.data.result)
          that.$store.dispatch('updateTopic', resolve.data.result)
          that.topicAndFocus=resolve.data.result
        }
      }).catch(error=>{
        console.log(error)
      })
    }
  }
</script>

<style scoped lang="stylus">
  .focused
    font-size 0.36rem
    border 0.02rem solid #fff
    padding 0.2rem
    border-radius 0.5rem
    color #FFFFFF
    font-weight lighter
    text-align center
  .warp
    width: 100%
    .background
      margin-top 1rem
      width: 100%
      height 5rem
      .background-img
        opacity: 0.8;
        z-index: -1
        width: 100%
        height 5rem
        position absolute
      .background-content
        z-index: 1
        display flex
        flex-direction row
        padding-top  1rem
        height 4rem
        .back-left
          flex-grow 9
          display flex
          flex-direction column
          height 4rem
          line-height 1rem
          margin-left 0.2rem
          color #FFFFFF
          .describe
            font-size 0.2rem
        .back-right
          flex-grow 2
          text-align center
          margin-right 0.1rem
          .focus
            font-size 0.36rem
            background-color red
            padding 0.2rem
            border-radius 0.5rem
            color #FFFFFF
            font-weight lighter
            text-align center
    .content
      z-index -2
      margin-top 0.4rem
      width: 100%
      height auto
      position absolute
      margin-bottom 1rem


</style>
