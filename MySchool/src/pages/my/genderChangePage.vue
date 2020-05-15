<template>
  <div class="warp">
    <backAndSubmit content="昵称" @tosubmit="submitgender"></backAndSubmit>
    <div class="content">
      <select v-model="gender">
        <option v-for="(item,index) of g" :value="item.code" :key="index">{{item.value}}</option>
      </select>
    </div>
  </div>
</template>

<script>
  import  backAndSubmit from '../../components/backAndSubmit'
  import  qs from 'qs'
    export default {
        name: "genderChangePage",
      data:function(){
        return{
          gender:null,
          selected:'selected',
          g:[{value:'男',code:1},{value:'女',code:0}]
        }
      },
      components:{
        backAndSubmit
      },
      mounted() {
          const gender= this.$route.query.gender
          this.gender=gender
      },
      methods:{
        submitgender:function () {
          const that =this
          var user = JSON.parse(localStorage.getItem("user"))
          console.log(user.gender)
          if (this.gender !== user.gender) {
            user.gender = this.gender
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
    select
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
      background-color #fff
</style>
