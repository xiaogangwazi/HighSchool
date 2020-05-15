<template>
    <div class="warp">
      <div class="content" v-if="hotSearch.length != 0">
          <h2>热搜</h2>
          <div class="hot">
            <div v-for="item ,index in hotSearch" class="hot-item">
              <span class="item-span" @click="searchByHot(item)"><span class="iconfont" v-if="index<5" style="color: red">&#xe8be;</span>{{item}}</span>
            </div>
          </div>
      </div>
    </div>
</template>

<script>
    export default {
        name: "searchHot",
        props:['hotSearch'],
      methods:{
        searchByHot:function (content) {
            if(content!=null&&content!=''){
              this.$emit('searchByHot',content)
            }
        }
      }
    }
</script>

<style scoped lang="stylus">
    .warp
      width:100%
      .hot
        margin 0.24rem
        width 90%
        display flex
        flex-direction row
        flex-wrap wrap
        .hot-item
          margin 0.06rem
          border-radius 38%
          height 0.48rem
          line-height 0.48m
          margin-right 0.48rem
          background-color #ccc
          .item-span
            padding-left 0.36rem
            padding-right 0.36rem

</style>
