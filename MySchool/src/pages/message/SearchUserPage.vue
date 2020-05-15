<template>
  <div class="wrap">
    <back content="查找用户"></back>
    <div class="content">
      <input name="content" class="myinput" ref="content">
      <button class="mybutton" @click="send()"  type="primary"
              v-loading.fullscreen.lock="fullscreenLoading">搜索</button>
    </div>
    <div class="result">
    <div v-if="user!=null" >
      <user-item :user="user" @addUser="addUser"></user-item>
    </div>
    </div>
  </div>
</template>

<script>
  import  back from '../../components/back'
  import UserItem from "./commonents/userItem";
  import  qs from 'qs'
  export default {
    name: "SearchUserPage",
    components:{UserItem, back},
    data:function(){
      return{
        user:null,
        fullscreenLoading:false
      }
    },
    methods:{
      addUser:function(id){
          if(id!=null){
             var  userid=JSON.parse(localStorage.getItem("user")).id
            var formDate = new FormData()
            formDate.set("userid",userid)
            formDate.set("targetid",id)
            const that = this
            this.axios.post(this.remote+"/friend/add",formDate).then(resolve=>{
              that.$message({
                showClose: true,
                message: '申请成功！',
                type: 'success'
              });
            }).catch(error=>{
              console.log(error)
            })
          }
      },
      send:function () {
        const that = this

        var input=this.$refs.content.value
        if(input===''){
          return
        }
        that.fullscreenLoading=true
        this.axios.get(
          that.remote+'/user/get',
          {params:{name:input}}

        ).then(resolve=>{
          console.log(resolve.data.result.user)
          if(resolve.data.result.user!=null){
              that.user=resolve.data.result.user
          }else{
            that.$message('该用户名/邮箱用户为空')
          }
        }).catch(e=>{
          this.$message({
            showClose: true,
            message: '获取用户失败，请重试',
            type: 'error'
          })
          console.log(e)
        }).finally(()=>{
          that.fullscreenLoading=false
        })

      }
    }
  }
</script>

<style scoped lang="stylus">
  .wrap
    background-color #fff
    width: 100%
    margin-top 1.2rem
    line-height 1rem
    .header
      width:100%
      height 1rem
      background-color #fff
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
    .result
      margin-top 0.5rem



</style>
