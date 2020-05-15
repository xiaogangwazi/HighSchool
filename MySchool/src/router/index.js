import Vue from 'vue'
import Router from 'vue-router'
import home from '../pages/home/Home'
import  my from '../pages/my/my'
import  search from '../pages/search/search'
import  message from '../pages/message/message'
import  community from  '../pages/community/community'
import  communityfocus from  '../pages/community/communityfocus'
import  chatPage from  '../pages/message/chatpage'
import  login from '../pages/my/login'
import searchUserPage from '../pages/message/SearchUserPage'
import  setUserpage from '../pages/my/setUserpage'
import  Topic from '../pages/community/Topic'
import messageDetail from "../pages/community/messageDetail";
import  userinfoPage from '../pages/my/userinfoPage'
import nicknameChangePage from "../pages/my/nicknameChangePage"
import  publishPage from '../pages/message/pulishPage'
import genderChangePage from "../pages/my/genderChangePage";
import schoolnameChangePage from "../pages/my/schoolnameChangePage";
import noticepage from "../pages/my/noticepage";
import friendList from "../pages/message/friendList";

Vue.use(Router)

const router=  new Router({
  routes: [
    {
      path: '/message/chatpage',
      name: 'chatPage',
      component: chatPage
    },
    {
      path: '/community/focus',
      name: 'communityfocus',
      component: communityfocus
    },
    {
      path:'/user/noticepage',
      name:'noticepage',
      component:noticepage
    },
    {
      path: '/home',
      name: 'home',
      component: home
    },
    {
      path: '/my',
      name: 'my',
      component: my
    },
    {
      path: '/search',
      name: 'search',
      component: search
    },
    {
      path:"/message",
      name:"message",
      component:message
    },
    {
      path:"/",
      name:"home",
      component:home
    },
    {
      path:"/community",
      name:"community",
      component:community
    },
    {
      path:"/login",
      name:"login",
      component:login
    },
    {
      path:"/message/searchUserPage",
      name:"searchUserPage",
      component:searchUserPage
    },
    {
      path:"/user/setUserpage",
      name:"setUserpage",
      component:setUserpage
    },
    {
      path:"/topic/detail",
      name:"Topic",
      component:Topic
    },
    {
      path:"/message/detail",
      name:"messageDetail",
      component:messageDetail
    },
    {
      path:"/user/userinfoPage",
      name:"userinfoPage",
      component:userinfoPage
    },
    {
      path:"/user/nicknameChangePage",
      name:"nicknameChangePage",
      component:nicknameChangePage
    },
    {
      path:"/publishPage",
      name:"publishPage",
      component:publishPage
    },
    {
      path:"/user/genderChangePage",
      name:"genderChangePage",
      component:genderChangePage
    },
    {
      path:"/user/schoolnameChangePage",
      name:"schoolnameChangePage",
      component:schoolnameChangePage
    },
    {
      path:"/message/friendList",
      name:"friendList",
      component:friendList
    }


  ]
})
router.beforeEach((to,from,next)=>{
  var islogin = localStorage.getItem('islogin')
  console.log("islogin:"+islogin+"token:"+localStorage.getItem("token"))
  if (islogin&&islogin =='true') {
    if (to.name === 'login') {
      next({path: '/home'})
    }
    next()

  } else if (to.name=== 'login'){
    next()
  } else {
    console.log('请先登录/注册')
    next('/login')
  }
})
export  default  router
