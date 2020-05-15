<template>
    <div class="warp">
      <comment-user-item :user="comment.comments.user" :time="comment.comments.time"></comment-user-item>
      <div class="content">{{comment.comments.content}}</div>
      <div class="bottom">
        <span class="iconfont" @click="addcomment()">&#xe8bd;{{comment.count}}</span>
        <span class="iconfont">&#xe8c0;{{comment.count}}</span>
      </div>
      <div class="child-comment" v-for="item ,index  in comment.list" :key="index">
        <comment-user-item :user="item.user" :time="item.time"></comment-user-item>
        <div class="content">回复 {{item.target.nickname}}:{{item.content}}</div>
      </div>
    </div>
</template>

<script>
  import commentUserItem from './commentUser-item'
    export default {
        name: "comment-item",
      props:['comment'],
      components:{commentUserItem},
      methods:{
        addcomment:function () {
          this.$emit('addcomments', this.comment.comments)
        }
      }

    }
</script>

<style scoped lang="stylus">
  .warp
    width 100%
    .content
      width 100%
      padding-left  0.1rem
      font-weight bolder
      padding-left  1rem
    .child-comment
      font-size 0.2rem
      margin-left 1rem
    .bottom
      text-align right
      margin-right 0.5rem
      .iconfont
        font-size 0.24rem
        margin-right 0.2rem
</style>
