<template>
    <div class="warp">
      <div class="item" v-if="item.type==1">
        <span class="left">{{item.message}}</span>
        <span class="time">{{time}}</span>
        <span class="right" v-if="item.statue==0">
        <span class="act agree" @click="agree()">同意</span>
        <span class="act reject" @click="reject()">拒接</span>
        </span>
        <span v-else>
          <span class="right">
            <span class="">已处理</span>
          </span>
        </span>
      </div>
      <div v-else>

      </div>
    </div>
</template>

<script>
    export default {
        name: "notice-item",
      props:['item'],

      computed:{
          time:function () {
            return this.timeUtil.formatDate(this.item.time)
          }
      },
      methods:{
          read:function(){
            this.$store.dispatch('addUnreadCount',-1)
          },
          agree:function () {
              const that = this
              this.item.statue=1
            console.log(this.item.url)
              this.axios.get(this.item.url+"&&act=1&&noticeId="+this.item.id).then(resolve=>{
                that.$message({
                  type: 'success',
                  message: '处理成功!'
                });
                that.read()
              }).catch(error=>{
                console.log(error)
              })
          },
          reject:function () {
            const that = this
            this.item.statue=1

            this.axios.get(this.item.url+"&&act=-1&&noticeId="+this.item.id).then(resolve=>{
                  that.read()
            }).catch(error=>{
              console.log(error)
            })
        }
      }
    }
</script>

<style scoped lang="stylus">
.warp
  width:100%
  .item

    width:100%
    height 1rem
    line-height 1rem
    border-bottom 0.02rem solid #eee
    .act
      border-radius 1rem
      color #FFFFFF
      padding 0.1rem
     .agree
      background-color blue
     .reject
      background-color red
    .left
      margin-left 0.2rem
    .right
      font-size 0.24rem
      font-weight lighter
      float right
      margin-right 0.2rem
    .time
      font-size 0.24rem
      font-weight lighter
      margin-left 0.2rem
</style>
