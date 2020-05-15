// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import  './js/iconfont'
import App from './App'
import router from './router'
import  'lib-flexible/flexible'
import 'mint-ui/lib/style.css'  // 引入css
import   './assets/style/reset.css'
import   './assets/style/border.css'
import  fastClick from 'fastclick'
import './assets/style/iconfont.css'
import timeUtil from './util/timeUtil'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import store from './store'
import axios from 'axios'
import apiUtil  from './util/apiUtil'
import {button,Loading} from 'element-ui'
import properties from './properties'
import  qs from 'qs'
Vue.use(ElementUI)
Vue.config.productionTip = false
Vue.prototype.ws=properties.ws
Vue.prototype.timeUtil=timeUtil
Vue.prototype.apiUtil=apiUtil
Vue.prototype.remote=properties.remote
Vue.prototype.axios=axios
Vue.config.devtools=true
fastClick.attach(document.body)
/* eslint-disable no-new */
//定义拦截器，获取本地token传递给后台
var instance= axios.create({
  baseURL:properties.remote,
  timeout:1000,
  headers:{"Content-Type":"multipart/form-data",'Access-Control-Allow-Origin':"*"}
})
Vue.prototype.instance=instance
axios.defaults.headers['Content-Type']='application/x-www-form-urlencoded'
axios.interceptors.request.use(
  config=>{
    let token = localStorage.getItem("token")
    let islogin= localStorage.getItem("islogin")
    config.headers['Access-Control-Allow-Origin']='*'
    if(islogin==='true'&&token!=null){
      console.log("token:"+token+"islogin:"+islogin)
      config.headers['authorization']=token;
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

//定义拦截器，拦截响应 如果token过期了删除本地token
axios.interceptors.response.use(
  response=>{
    let{data} = response
    if(data.code==='-1'){
      console.log('token过期了，需要重新登录')
      localStorage.removeItem('token')
      localStorage.removeItem('islogin')

    }else{
      return Promise.resolve(response)
    }
  },
  error => {
    console.log(error)
    return Promise.reject(error)
  }
)
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
