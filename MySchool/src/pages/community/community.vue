<template>
<div class="warp" v-loading="loading"
     element-loading-text="加载中"
     element-loading-spinner="el-icon-loading"
     element-loading-background="rgba(0, 0, 0, 0.8)"
     style="width: 100%">
  <div class="header">
    <span class="header-item" :class="{check:check[0]}" @click="tocommunityfocus()">关注</span>
    <span class="header-item" :class="{check:check[1]}">话题</span>
  </div>
  <div class="content">
    <div class="content-left">
      <div  class="left-item " v-for="item ,index in list" :name="index" :class="{active:curr==index}">
        <span @click="to(index)">{{item.name}}</span>
      </div>
    </div>

    <div class="content-right">
      <div class="right-item" v-for="item,index in list" :slot="index" :key="index" :id="index" :ref="index">
        <template v-if="item.topicList.length!=0">
        <div class="child-item" v-for="child,i in item.topicList" @click="toTopicDetail(child.id)" :id="child.id">
          <img :src="child.backgroundUrl" class="child-img">
          <span class="child-name">{{child.name}}</span>
        </div>
        </template>
        <template v-else>
          <div >
            <img  style="width:3rem;height: 2rem;margin: 0.8rem" src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584563013256&di=027cccd4192829619fd843d69f696c0a&imgtype=0&src=http%3A%2F%2Ft8.baidu.com%2Fit%2Fu%3D3571592872%2C3353494284%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1200%26h%3D1290">
          </div>
        </template>
      </div>

    </div>
  </div>
</div>
</template>

<script>
    import {mapGetters,mapState,mapActions} from 'vuex'

    export default {
        name: "community",
      data:function () {
        return{
          check:[false,true],
          last:0,
          curr:0,
          loading:true
        }
      },
      computed:{
        ...mapGetters({list:'getCommunityList'}),
        size:function () {
          return this.data.length
        },
        type:function () {
          return localStorage.getItem("user").type
        }
      },

      mounted() {
        window.addEventListener("scroll", this.handleScroll, true);
        const that = this
       this.axios.get(this.remote+"/community/list").then(resolve=>{
         if(resolve.data.code===200){
           that.$store.dispatch('updateCommunityList',resolve.data.result.communityList)
           that.loading=false
         }
       }).catch(error=>{
         console.log(error)
       }).finally(()=>{
         that.loading=false
       })
      },

      methods: {
        tocommunityfocus(){
          this.$router.push('/community/focus')
        },
        toTopicDetail(id){
          this.$store.dispatch('updateTopicId',id)
          this.$router.push('/topic/detail')

        },
        handleScroll(el) {
         const item = document.getElementsByClassName('right-item')
          for (var index =0;index<item.length;index++){
            if(item[index].getBoundingClientRect().top>0){
              document.getElementsByName(index)[0].classList.add('active')
              if(index!=this.curr)
              document.getElementsByName(this.last)[0].classList.remove('active')
              document.getElementById(index).classList.add('b')
              if(index!=this.curr)
              document.getElementById(this.last).classList.remove('b')
              this.last=this.curr
              this.curr=index
              console.log(index)
              break;
            }
          }
        },
        to:function (index) {

          if (this.curr != index) {
            console.log(index)
            document.getElementById(index).scrollIntoView(true)
            document.getElementsByName(index)[0].classList.add('active')
            document.getElementsByName(this.last)[0].classList.remove('active')
            document.getElementById(index).classList.add('b')
            document.getElementById(this.last).classList.remove('b')
            this.last = this.curr
            this.curr=index
          }
        }
      }
    }
</script>

<style scoped lang="stylus">
  .check
    font-weight bolder
    font-size 0.46rem
  .b
    border: 0.1rem solid red
  .active
    background-color #fff
  .warp
    width: 100%
    .header
      width 100%
      background-color #fff
      z-index 2
      position fixed
      top: 0
      border-bottom .01rem solid #eee
      .header-item
        margin .2rem .4rem
        height 1rem
        line-height 1rem
        text-align center
    .content
      width 100%
      margin-top 1rem
      display flex
      flex-direction row
      .content-left
        position: fixed
        background-color #EfEfEf
        width 20%
        left 0
        .left-item
          height 1.2rem
          line-height 1.2rem
          text-align center
      .content-right
        z-index 0
        position absolute
        float right
        right 0
        width 80%
        .right-item
          width 100%
          display flex
          flex-direction row
          justify-content center
          justify-items center
          flex-wrap wrap
          border 0.01rem solid #eee
          .child-item
            display flex
            flex-direction column
            margin .4rem
            .child-img
              width 1.6rem
              height 1.6rem
              border-radius 30%
            .child-name
              text-align center
              font-size 0.1rem
</style>
