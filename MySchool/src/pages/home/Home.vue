<template>
  <div class="warp">
    <div class="header">
    <div class="home-header">
      <span class="recommend" v-on:click="change(0)" v-bind:class="{active:title[0]}">推荐</span>
      <span class="campus" v-on:click="change(1)" v-bind:class="{active: title[1]}">校内</span>
    </div>
      <div v-show="title[0]==true">

      </div>
      <div v-show="title[1]==true">
        <div v-if="getSameSchool.length!=0">
          <div v-for="item,index in getSameSchool" :key="index">
            <message-item :message="item"></message-item>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import  HomeBottom from '../../components/homeBottom'
  import {mapGetters} from 'vuex'
  import  messageItem from '../../components/messageItem'
    export default {
        name: "Home",
      data:function(){
        return{
            title:[true,false],
            last:0
        }
      },
      components:{
          HomeBottom,messageItem
      },
      computed:{
        ...mapGetters(['getSameSchool','getRecommended'])
      },
      methods:{
          updateSchool:function(){
            const that= this
            const  userid = JSON.parse(localStorage.getItem("user")).id
            this.axios.get(this.remote+"/message/getSameSchool?userid="+userid).then(resolve=>{
              if(resolve.data.result!=null){
                  that.$store.dispatch('updateSameSchool',resolve.data.result.list)
              }
            })
          },
        updateRecommended:function(){
          const that= this
          const  userid = JSON.parse(localStorage.getItem("user")).id
          this.axios.get(this.remote+"/message/getRecommended?userid="+userid).then(resolve=>{
            if(resolve.data.result!=null){
              that.$store.dispatch('updateRecommended',resolve.data.result.list)
            }
          })
        },
          change:function (curr) {
            if(curr==0){
              this.title.splice(0,1,true)
              this.title.splice(1,1,false)
            }else{
              this.title.splice(0,1,false)
              this.title.splice(1,1,true)
            }
          }
      },
      mounted() {
          this.updateRecommended()
        this.updateSchool()
      }
    }
</script>

<style scoped lang="stylus">
    .warp
      width: 100%
      .header
        text-align center
        width auto
        height:1rem
        line-height 1rem
        border-bottom .01rem solid #eee
    .active
      font-weight bolder


</style>
