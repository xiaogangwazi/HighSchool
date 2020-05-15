<template>
  <div class="warp">
    <backAndSubmit content="昵称" @tosubmit="submitschoolname"></backAndSubmit>
    <div class="content">
      <input type="text" id="schoolname" v-model="schoolname"/>
    </div>
  </div>
</template>

<script>
  import  backAndSubmit from '../../components/backAndSubmit'
  import  qs from 'qs'
    export default {
        name: "schoolnameChangePage",
      data:function(){
        return{
          schoolname:null
        }
      },
      components:{
        backAndSubmit
      },
      mounted() {
          const schoolname= this.$route.query.schoolname
          this.schoolname=schoolname
      },
      methods:{
        submitschoolname:function () {
          const that =this
          var user = JSON.parse(localStorage.getItem("user"))
          console.log(user.schoolname)
          if (this.schoolname !== user.schoolname) {
            user.schoolname = this.schoolname
            this.axios.post(this.remote + "/user/update", qs.stringify(user)).then(resolve => {
              that.$message({
                showClose: true,
                message: '修改成功！',
                type: 'success'
              });
              localStorage.setItem("user",JSON.stringify(user))
              that.$router.back()
            }).catch(error => {
              console.log(error)
              that.$message({
                showClose: true,
                message: '修改失败，请重试',
                type: 'error'
              });
            })
          }
        }
      }

    }
</script>

<style scoped lang="stylus">
 .warp
   width: 100%
  .content
    margin-top 1rem
    width: 100%
    input
      box-sizing: border-box;
      height:0.8rem;
      border:0.02rem solid #c8cccf;
      color:#6a6f77;
      -web-kit-appearance:none;
      -moz-appearance: none;
      display:block;
      outline:0;
      text-decoration:none;
      width:100%
</style>
