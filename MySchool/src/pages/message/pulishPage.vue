<template>
    <div class="warp">
      <back-and-submit content="发布内容" title="发布" @tosubmit="topublish"></back-and-submit>
      <div class="container">
        <input type="text" placeholder="取一个标题吧" name="title" class="title" v-model="title">
        <textarea class="content" name="content" contenteditable="true" cols="20" rows="20"  v-model="content"></textarea>
        <span class="topic1" v-if="getTopic.topic!=null">#{{getTopic.topic.name}}
        </span>
          <span class="topic2" v-else>添加主题</span>
      </div>
    </div>
</template>

<script>
  import  backAndSubmit from '../../components/backAndSubmit'
  import  {mapGetters} from 'vuex'
  import  qs from 'qs'
    export default {
        name: "pulishPage",
      data:function(){
        return {
          title: null,
          content: null
        }
      },
      components:{
          backAndSubmit
      },
      data:function(){
        return{

        }
      },
      computed:{
          ...mapGetters(['getTopic'])
      },
      created() {
        this.$store.commit('updateShowbottombar',false)
      },
      destroyed() {
        this.$store.commit('updateShowbottombar',true)
      },
      methods:{
        topublish:function () {
          const data = {
            content:this.content,
            title:this.title,
            publicid:JSON.parse(localStorage.getItem("user")).id,
            topicid:this.getTopic.topic.id,
            topicname:this.getTopic.topic.name
          }
          console.log(data)
          this.axios.post(this.remote+"/message/publish",qs.stringify(data)).then(resolve=>{
            this.$message({
              showClose: true,
              message: '发布成功！',
              type: 'success'
            });
            this.$router.back()
          }).catch(error=>{
            console.log(error)
            this.$message({
              showClose: true,
              message: '发布失败，请重试',
              type: 'error'
            });
          })
        }
      }
    }
</script>

<style scoped lang="stylus">
  .warp
    margin-top 1rem
    .container
      display flex
      flex-direction column
      .content,.title
        height 100%
        border-style:none
        overflow:hidden
        border: solid 0px;
        outline:none;
      .title
        border-bottom 0.02rem solid #eee
        height 0.8rem
      .topic2
        color #72bfdd
      .topic1
        font-weight bolder

</style>
