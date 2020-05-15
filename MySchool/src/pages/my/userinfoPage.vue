<template>
    <div class="warp">
      <back content="基本信息"></back>
      <input type="file" hidden="hidden" id="headurl" accept="image/*" @change="filechange($event)">
      <div class="content">
        <div class="item" @click="changHeadurl">
          <span>头像</span>
          <el-upload
            :http-request="updateHeadUrl"
            class="avatar-uploader"
            :action=action
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="userinfo.headurl" :src="headUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <div class="item" @click="toNicknameChangePage">
          <span>昵称</span>
          <span class="iconfont">{{userinfo.nickname}}&#xe743;</span>
        </div>
        <div class="item"  @click="toGenderChangePage">
          <span>性别</span>
          <span class="iconfont" v-if="userinfo.gender===0">女&#xe743;</span>
          <span class="iconfont" v-else>男&#xe743;</span>

        </div>
        <div class="item" @click="toSchoolnameChangePage">
          <span>学校</span>
          <span class="iconfont">{{userinfo.schoolname}}&#xe743;</span>
        </div>
        <div class="item">
          <span>邮箱</span>
          <span class="iconfont">{{userinfo.email}}&#xe743;</span>
        </div>
        <div class="item">
          <span>积分</span>
          <span class="iconfont">{{userinfo.score}}</span>
        </div>


      </div>
      <div class="reset" @click="reset">
        <span>退出登录</span>
      </div>
    </div>
</template>

<script>
  import Back from '../../components/back'
    export default {
        name: "userinfoPage",
      components:{
          Back
      },
      data:function(){
        return{
          action:this.remote+"/user/updateHeadUrl",
          user:null
        }
      },
      computed:{

          userinfo:function () {
            this.user= JSON.parse(localStorage.getItem("user"))
            return JSON.parse(localStorage.getItem("user"))
          },
        headUrl:function() {
          return this.userinfo.headurl+'?t='+Math.random()
        },
      },
      methods:{
          reset:function(){
            localStorage.setItem("islogin","false")
            localStorage.removeItem("token")
            this.$router.push("/")
          },
        updateHeadUrl:function(content){
         const head = new FormData()
          const userid= JSON.parse(localStorage.getItem("user")).id
          head.append("file",content.file)
          head.append("id",userid)
          this.axios.post(this.remote+"/user/updateHeadUrl",head).then(resolve=>{
            console.log(resolve.data)
          }).catch(error=>{
            console.log(error)
          })
        },
        toNicknameChangePage:function(){
          const nickname= this.userinfo.nickname
          this.$router.push({path:'nicknameChangePage',query:{nickname:nickname}})
        },
        toSchoolnameChangePage:function(){
          const schoolname= this.userinfo.schoolname
          this.$router.push({path:'schoolnameChangePage',query:{nickname:schoolname}})
        },
        toGenderChangePage:function(){
          const gender= this.userinfo.gender
          this.$router.push({path:'genderChangePage',query:{gender:gender}})
        },
        filechange:function (event) {
        },
        changHeadurl(){

        },
        handleAvatarSuccess(res, file) {
          console.log(res+":"+file.raw)
          const formdate= new FormData()
          formdate.set("head",file)
          this.imageUrl = URL.createObjectURL(file.raw);
        },
        beforeAvatarUpload(file) {
          const isJPG = file.type === 'image/jpeg'||file.type === 'image/png';
          const isLt2M = file.size / 1024 / 1024 < 2;

          if (!isJPG) {
            this.$message.error('上传头像图片只能是 JPG 格式!');
          }
          if (!isLt2M) {
            this.$message.error('上传头像图片大小不能超过 2MB!');
          }
          return isJPG && isLt2M;
        }
      },
      mounted() {

          const name = JSON.parse(localStorage.getItem("user")).nickname
        this.axios.get(this.remote+"/user/get?name="+name).then(resolve=>{
              if(resolve.data.user!=null) {
                localStorage.setItem("user", resolve.data.user)
              }
        })
      }
    }
</script>

<style scoped lang="stylus">
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 1rem;
    height: 1rem;
    border-radius 1rem
    display: block;
  }
  .warp
    width: 100%
    .content
      margin-top 1rem
      width: 100%
      display flex
      flex-direction column
      .item
        padding-left 0.2rem
        padding-right 0.2rem
        height:1rem
        line-height 1rem
        border-bottom 0.02rem solid #eee
        display: flex
        flex-direction row
        justify-content space-between
        .headurl
          width: 1rem
          border-radius 1rem
          height 1rem
    .reset
      width:auto
      height 1rem
      background-color #E4E4E4
      line-height 1rem
      text-align center
      border-radius 1rem
      margin-top:0.2rem

</style>
