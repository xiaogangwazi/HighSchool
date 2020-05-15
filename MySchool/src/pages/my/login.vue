<template>
    <div class="warp" >
      <img class="backgroundimg" src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585069144621&di=ff563818899962a594b142640b6a38e7&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3D02a5bc14347adab43dd01b4bbbd5b36b%2Fcae5b2119313b07e303c5f340ed7912397dd8c24.jpg">
      <div v-if="show !=0">
        <span class="iconfont" @click="back">&#xe748;</span>
      </div>
      <div class="select" v-if="show ==0">
        <div class="select-content">
        <span @click="tologin" class="select-item1">登陆</span>
        <span @click="toregister" class="select-item">注册</span>
        </div>
      </div>
      <div class="login" v-if="show ==1">
        <div class="title">
        <span >{{title}}</span>
        </div>

        <div class="login-content">
          <div class="select-type">
          <span class="login-type" @click="choose(1)" :class="{type:loginType==1}">用户名登陆</span>
          <span class="login-type" @click="choose(2)" :class="{type:loginType==2}">邮箱登陆</span>
          </div>
          <div class="login-form">
            <form>
            <input class="login-item" type="text" name="name" id="name" :placeholder="name" v-model="login.name" @blur="loginValidation">
            <input class="login-item" type="password" name="password"  placeholder="密码" v-model="login.password">
            </form>
          </div>
          <div class="button">
            <button class="submit-button" @click="loginto" ref="loginSubmit"
                    type="primary"
                    v-loading.fullscreen.lock="fullscreenLoading"
            >submit</button>
          </div>

        </div>
      </div>
      <div class="register" v-if="show ==2">
        <div class="title">
          <span >{{title}}</span>
        </div>
          <div class="register-content">
            <div class="register-form">
              <form>
              <input class="register-item" type="text" name="name"  placeholder="用户名" v-model="register.name">
              <input class="register-item" type="text" name="email"  placeholder="邮箱" v-model="register.email" @blur="registerValidation"><span class="iconfont" ref="registermsg"></span>
              <input class="register-item" type="password" name="password" placeholder="密码" v-model="register.password">
              </form>
            </div>
            <div class="button">
              <button class="submit-button" @click="registerto()"  type="primary"
                      v-loading.fullscreen.lock="fullscreenLoading">submit</button>
            </div>

          </div>
      </div>
    </div>
</template>

<script>
  import Back from "../../components/back";
  import { MessageBox } from 'mint-ui';
  import {mapActions} from 'vuex'
  import  qs from 'qs'
    export default {
      name: "login",
      components: {Back},
      data:function(){
          return{
            fullscreenLoading:false,
            loading:false,
            loginType:0,
            show:0,
            name:'用户名',
            title:'登陆',
            login:{
              name:'',
              password:'',
              loginType:0,
            },
            register:{
              email:'',
              name:'',
              password:''
            }
          }
      },

      methods:{
        ...mapActions(['updateUser']),

        loginValidation:function () {
          console.log('验证登陆邮箱')
          var loginemail= this.login.name
          if(loginemail ===''){
            this.$message({
              showClose: true,
              message: '请输入邮箱',
              type: 'error'
            });
            return false
          }
          if(this.loginType==2){
            var myReg=/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/;
            if(myReg.test(loginemail)){
             return true;
            }else{
              this.$message({
                showClose: true,
                message: '邮箱格式错误',
                type: 'error'
              });
              return false;
            }
          }
        },


        registerValidation(){
          var registeremail= this.register.email
          var myReg=/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/;
          if(registeremail ===''){
            this.$message({
              showClose: true,
              message: '请输入邮箱',
              type: 'error'
            });
            return false
          }
          if(myReg.test(registeremail)){
           return true;
          }else{
            this.$message({
              showClose: true,
              message: '邮箱格式错误',
              type: 'error'
            });
           return false;
          }

        },
        registerto(){
              this.fullscreenLoading=true
          const that = this
          console.log(this.register)

          this.axios.post(this.remote+'/register',qs.stringify(this.register)).then(function (response) {
            console.log(response.data.code)
            that.fullscreenLoading=false
            if(response.data.code===200){
              that.$message({
                showClose: true,
                message: '注册成功，请查看邮箱完成注册！',
                type: 'success'
              })
                that.show=2
            }else if(response.data.code===1005){
                that.$message({
                  showClose: true,
                  message: '用户名已存在',
                  type: 'error'
                })
              }else if(response.data.code===1006){
                that.$message({
                  showClose: true,
                  message: '邮箱已存在',
                  type: 'error'
                })
              }else if(response.data.code===1004) {
              that.$message({
                showClose: true,
                message: '用户名不能为空',
                type: 'error',
              })
            }else if(response.data.code===1007){
              that.$message({
                showClose: true,
                message: '邮箱不能为空',
                type: 'error',
              })
            }else {
                that.$message({
                  showClose: true,
                  message: '注册失败，请重试',
                  type: 'error',
                })
              }
          }).catch(error=>{
            console.log(error)
            that.fullscreenLoading=false
            that.$message({
              showClose: true,
              message: '注册异常，请稍后再试！',
              type: 'error'
            })
          })
        },

        back(){
          this.show=0
        },
        choose(index){
          this.loginType=index
          if(index==1){
            this.name='用户名'
          }else{
            this.name='邮箱'
          }
        },
        loginto(){
            this.fullscreenLoading=true
            if(this.login.password==''){
              this.$message({
                showClose: true,
                message: '密码不能为空',
                type: 'error'
              });
              return;
            }
            console.log('开始登陆')
          this.login.loginType=this.loginType
          const that = this
            this.axios.post(this.remote+'/login',qs.stringify(this.login)).then(function (response) {
               if (response.data.code===200){
                 that.$message({
                   showClose:true,
                   message:'登陆成功',
                   type:'success'
                 })
                  if (response.data.result.token!=null){
                    that.$store.dispatch('updateUser',response.data.result.user).catch(e=>{
                      console.log("更新用户信息失败"+e)
                    })
                    console.log("获得vuex中的用户信息"+that.$store.getters.getuser)
                    localStorage.setItem("user",JSON.stringify(response.data.result.user))
                    localStorage.setItem("token",response.data.result.token);
                    localStorage.setItem("islogin","true")
                    console.log("获取登陆token，存入本地")
                    console.log("获取用户信息"+response.data.result.user)
                  }
                 that.$router.go("/home")
               }else if(response.data.code===1004){
                 that.$message({
                   showClose: true,
                   message: '用户名不能为空',
                   type: 'error'
                 })
               }else if(response.data.code===1007) {
                 that.$message({
                   showClose: true,
                   message: '邮箱不能为空',
                   type: 'error',
                 })
               }else if(response.data.code===1003) {
                 that.$message({
                   showClose: true,
                   message: '邮箱格式错误',
                   type: 'error',
                 })
               }else if(response.data.code===1001){
                   that.$message({
                     showClose: true,
                     message: '用户不存在',
                     type: 'error',
                   })
               }else if(response.data.code===1002){
                 that.$message({
                   showClose: true,
                   message: '密码错误',
                   type: 'error',
                 })
               }else {
                 that.$message({
                   showClose: true,
                   message: '登陆失败，请重试',
                   type: 'error',
                 })
               }
              that.fullscreenLoading=false
            }).catch(error=>{
              that.$message({
                showClose: true,
                message: '登陆失败，请重试',
                type: 'error',
              })
              console.log(error)
              that.fullscreenLoading=false
          })


        },
          tologin(){
            this.show=1
            this.name='用户名'
            this.title='登陆'

        },
          toregister(){
            this.show=2
            this.name='注册'
            this.title='注册'
          }

      },
      created() {
          this.$store.commit('updateShowbottombar',false)
      },
      destroyed() {
        this.$store.commit('updateShowbottombar',true)
      }
    }


</script>

<style scoped lang="stylus">
  .title
    text-align center
    height 1rem
    line-height 1rem
    margin-bottom 1rem
    font-size 0.4rem
    font-weight lighter
  .type
    font-size: 0.4rem
    font-weight bolder
  .warp
    width: 100%
    .iconfont
      font-size 0.48rem
    .backgroundimg
      width: 100%
      height 100%
      position fixed
      z-index: -1
      opacity 0.5
    .select
      .select-content
        width: 100%
        height 3rem
        position fixed
        top: 50%
        display flex
        flex-direction column
        .select-item1,.select-item
          margin 0.5rem
          background-color #fff
          opacity 0.5
          height 1rem
          line-height 1rem
          border-radius 1rem
          text-align center
          font-weight bolder
          font-size 0.36rem
    .login,.register
      position fixed
      width: 100%
      top:30%
      .login-content,.register-content
        width: 100%
        .select-type
          width:100%
          display flex
          flex-direction row
          .login-type
            line-height 1rem
            height 1rem
            text-align center
            flex-grow 1
        .login-form,.register-form
          display flex
          flex-direction column
          .loginmsg
            font-size 0.3rem
          .login-item,.register-item
            margin: 0.2rem
            border-radius 1rem
            width: 90%
            height 1rem
            outline-color: invert;
            outline-style: none;
            outline-width: 0;
            border: 0.02rem solid #eee;
            font-weight lighter
            background-color transparent
            padding-left  0.2rem
            text-shadow: none;
            -webkit-appearance: none;
            -webkit-user-select: text;
            outline-color: transparent;
            box-shadow: none
        .button
          display: flex
          flex-direction row
          margin-top 0.5rem
          text-align center
          .submit-button
            width 90%
            height 1rem
            margin-left 0.2rem
            line-height 1rem
            border: 0.01rem solid #fff;
            border-radius  1rem
            background-color: #6da9ff;
            opacity 0.5
            outline: none;




</style>
