<template>
    <div class="warp"  v-loading="loading"
         element-loading-text="加载中"
         element-loading-spinner="el-icon-loading"
         element-loading-background="rgba(0, 0, 0, 0.8)"
         style="width: 100%">
      <back :content="'#'+message.topicname"></back>
      <div class="title">
        <h2>{{message.title}}</h2>
      </div>
      <UserHead :user="message.user" :time="message.publictime"/>
        <div class="content">
          <span>{{message.content}}</span>
        </div>
        <div class="like">
          <span class="iconfont" @click="addcommentstoMessage">&#xe8bd;{{message.commentsCount}}</span>
        <span class="iconfont" v-if="ilike==null||ilike==='0'" @click="addlike">&#xe8c0; {{message.like}}</span>
          <span class="iconfont like" v-else @click="adddislike">&#xe656; {{message.like}}</span>
          <span class="iconfont see">&#xe8bf;{{message.see}}</span>
        </div>
        <div class="comments">
          <div class="comment-item" v-for="item,index in comments" :key="item.id">
          <comment-item :comment="item" @addcomments="addcomments"></comment-item>
          </div>
        </div>
      <comment-input @send="send" :place="place"></comment-input>

      </div>
</template>

<script>
  import UserHead from '../../components/UserHead'
  import  commentItem from '../../components/comment-item'
  import  commentInput from '../../components/commentInput'
  import back from '../../components/back'
  import {mapGetters} from 'vuex'
  import  qs from 'qs'
    export default {
        name: "messageDetail",
      components:{back,UserHead,commentItem,commentInput},
      data:function(){
        return {
          loading:true,
          target:null,
          place:"",
          replyto:0,//0表示回复给帖子，1表示回复给评论
          replyComment:null,
          ilike:'0'
        }
      },
      computed:{
          ...mapGetters({message:'getMessage',comments:'getComments'}),
        userid:function(){
          return JSON.parse(localStorage.getItem("user")).id
        },
      },
      created:function(){
        this.$store.dispatch('hiddenbottombarAndshowmyinput')
      },
      beforeDestroy:function(){
        this.$store.dispatch('hiddenmyinputAndshowbottombar')
      },
      methods:{
        addcommentstoMessage:function(){
          this.place="#回复给帖子"
          this.replyto=0
        },
        addcomments:function(data){
          console.log(this.ilike)
               this.place="#回复给"+data.user.nickname
                this.replyComment=data
                this.replyto=1
        },
          send:function(data){
            var  da=null
            if(this.replyto==0) {
              da = {
                messageid: this.message.id,
                userid: JSON.parse(localStorage.getItem("user")).id,
                replyto: 0,
                content: data,
                type: 1,
              }
            }else{
              da={
                messageid: this.message.id,
                userid: JSON.parse(localStorage.getItem("user")).id,
                replyto: this.replyComment.id,
                content: data,
                type: 0,
              }
            }
            const that = this
            this.axios.post(this.remote+"/comments/add",qs.stringify(da)).then(resolve=>{
              that.$message({
                type: 'success',
                message: '评论成功!'
              });
              that.updateMessage(that.message.id)
            }).catch(error=>{
              console.log(error)
              that.$message({
                type: 'error',
                message: '评论失败，请稍后再试！'
              });
            })

          },
        addlike:function () {
          if(this.ilike==null||this.ilike==='0'){
            const that = this
            const pa= {
              id:this.message.id,
              count:1
            }
            this.axios.post(this.remote+"/message/addlike",qs.stringify(pa)).then(resolve=>{
              const userid= JSON.parse(localStorage.getItem("user")).id
              that.$store.dispatch('setLike',that.message.like+1)
              localStorage.setItem("messageLike:"+that.message.id+":"+userid,"1")
              that.ilike= localStorage.getItem("messageLike:"+that.message.id+":"+userid)
              console.log("点赞成功")
            }).catch(error=>{
              console.log(error)
            })
            }
          console.log(this.ilike)
        } ,
        adddislike:function () {
          console.log("dislike")
          if(this.ilike==='1'){
            const da= {
              id:this.message.id,
              count:-1
            }
            const that = this
            this.axios.post(this.remote+"/message/addlike",qs.stringify(da)).then(resolve=>{
              const userid= JSON.parse(localStorage.getItem("user")).id
              that.$store.dispatch('setLike',that.message.like-1)
              localStorage.setItem("messageLike:"+that.message.id+":"+userid,'0')
              that.ilike= localStorage.getItem("messageLike:"+that.message.id+":"+userid)
              console.log("取消点赞成功")
            }).catch(error=>{
              console.log(error)
            })
          }
          console.log(this.ilike)
        },
        updateMessage:function (id) {
          const that = this
          const  da={
            userid:JSON.parse(localStorage.getItem("user")).id,
            messageid:id
          }
          this.axios.post(this.remote+"/message/see",qs.stringify(da)).then(resolve=>{
            that.axios.get(this.remote+"/message/get?id="+id).then(resolve=>{
              if(resolve.data.result!=null){
                console.log(resolve.data.result)
                that.$store.dispatch('updateMessage',resolve.data.result.message)
              }
            }).then(resolve=>{
              that.axios.get(that.remote+"/comments/list?id="+id).then(resolve=>{
                if(resolve.data.result!=null) {
                  that.$store.dispatch('updateComments', resolve.data.result.list)
                }
                that.loading=false
              })
            }).catch(error=>{
              console.log(error)
            }).finally(()=>{
              that.loading=false
            })
          }).catch(error=>{
            console.log(error)
          })

        }
      },
      mounted() {
        const that = this
        var id = this.$route.query.messageid
        const like=localStorage.getItem("messageLike:"+id+":"+this.userid)
        this.ilike=like
        console.log(this.ilike)
        this.updateMessage(id)

      }
    }
</script>

<style scoped lang="stylus">
    .like
      color red
    .iconfont
      color: #333
    .warp
      width: 100%
      .title
        padding 0.1rem
        margin-top 1rem
      .content
        padding: 0.1rem
        font-weight bold
        font-size:0.34rem
        line-height 0.36rem
       .like
        text-align right
        height 0.6rem
        line-height 0.6rem
        span
          margin-right 0.5rem
      .comments
        margin-bottom 1rem
        width: 100%
        height auto
        position absolute
        border-top:0.5rem solid #eee

</style>
