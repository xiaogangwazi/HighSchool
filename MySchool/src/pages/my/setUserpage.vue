<template>
    <div class="warp">
      <back content="设置用户信息"></back>
     <div class="content">



    </div>
    </div>
</template>

<script>
  import  ImgItem from '../../components/ImgItem'
  import back from '../../components/back'
    export default {
        name: "setUserpage",
      components:{back,ImgItem},
      data:function () {
        return{
        }
      },
      computed:{
        user:function () {
          return localStorage.getItem("user")
        }
      },
      mounted() {
          console.log(JSON.parse(localStorage.getItem("user")))
      },
      methods:{
         upload() {
    let file = document.getElementById('img').files[0];

    let formData = new FormData();
    formData.append("file",file);
    var id=JSON.parse(localStorage.getItem("user")).id

    formData.append("id",id);
    const config = {
      headers: { "Content-Type": "multipart/form-data;boundary="+new Date().getTime() }
    };

    this.axios.post(this.remote+"/user/update",formData,config)
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
         }
      }
    }
</script>

<style scoped lang="stylus">
  .warp
    width:100%
    .content
      margin-top 1.2rem
      input
        width: 99%
        height 0.8rem
        line-height 0.8rem
</style>
