<template>
  <div class="warp">

    <div class="bottom-item" v-on:click="tocommunitypage()">
      <span v-bind:class="{active: getactives[0]}" class="iconfont " ref="community" >&#xe6bd;</span>
    </div>

    <div class="bottom-item" v-on:click="tohomepage()">
      <span v-bind:class="{active: getactives[1]}" class="iconfont" ref="home">&#xe642;</span>
    </div>

    <div class="bottom-item search" v-on:click="tosearchpage()">
      <span v-bind:class="{active: getactives[2]}" class="iconfont " ref="search" >&#xe62a;</span>
    </div>

    <div class="bottom-item" v-on:click="tomessagepage()" >

      <span v-bind:class="{active: getactives[3]}" class="iconfont" ref="message">&#xe8bd;</span>
    </div>


    <div class="bottom-item" v-on:click="tomypage()">
      <span v-bind:class="{active: getactives[4]}"  class="iconfont" ref="my">&#xe8bb;</span>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  export default {
    name: "homeBottom",
    computed: {
      ...mapGetters(['getactives','getlast','getChatList']),
    },
    methods:{
      tocommunitypage(){
        this.$store.commit('tocommunitypage',this)
      },
      tohomepage(){
        this.$store.commit('tohomepage',this)
      },
      tomessagepage(){
        this.$store.commit('tomessagepage',this)
      },
      tomypage(){
        this.$store.commit('tomypage',this)
      },
      tosearchpage() {
        this.$store.commit('tosearchpage', this)
      }
  }
  }
</script>

<style scoped lang="stylus">
  .warp
    background-color #fff
    border-top: .01rem solid #eee
    width 100%
    display: flex
    flex-direction row
    height: 1rem
    .bottom-item
      line-height 1rem
      width 20%
      height:1rem
      text-align: center
      .iconfont
        font-size:0.8rem


  .active
    color red




</style>
