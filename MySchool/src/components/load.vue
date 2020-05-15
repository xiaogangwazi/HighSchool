<template>
    <div class="warp">
      <div class="con" v-if="showState">
        <div class="item" v-if="finish">
          <span>加载完成！</span>
        </div>
        <div class="item" v-else>
          <span>加载中...</span>
        </div>
      </div>
    </div>
</template>

<script>
    export default {
        name: "load",
      data:function () {
          return {
            showState: true,
            loading: false,
            finish: false,
            domHeight:0,
            container:null
          }
      },
      mounted() {//绑定高度
          this.container=this.$el
          this.domHeight=this.$el.clientHeight
          this.switchBottom()
          this.bindSrcoll()
      },
      methods: {
        switchBottom() {
          this.$nextTick(() => {
            // 判断容器内容是否 大于 自身内容可视区域高度
            if (this.$el.scrollHeight > this.domHeight) {
              // 如果大于，则显示加载状态，至于是加载完成还是没有可以继续加载，我们不用关心，如果继续上滑有数据会执行方法的
              this.showState = true
            } else {
              // 如果页面不足一屏且还有下一页数据，继续执行加载更多方法
              if (!this.finish) {
                // 不足一屏，还有数据，现在加载状态
                this.showState = true
                // 执行父组件请求数据方法
                setTimeout(() => {
                  this.$emit('loadMore')
                }, 1000)
              } else {
                // 没有数据不显示底部加载状态
                this.showState = false
              }
            }
          })
        },
        // 视图数据更新，重新调用
        updated() {
          this.switchBottom()
        },
// 如果有用到keep-alive，组件激活时调用
        activated() {
          this.switchBottom()
        },
        scrollPage() {
          if (!this.$el) {
            return
          }
          // 获取内容向上滚动了多少距离
          var domScrollTop = this.container.scrollTop
          // 当内容滚动到距离底部<50时,且没有加载完成&&没有正在加载中
          // 内容距离底部多少距离 = 内容总高度-滚动高度-当前可视高度
          if (this.$el.scrollHeight - domScrollTop - this.domHeight < 50 && !this.loading && !this.finish) {
            // 设置为正在加载中
            this.loading = true
            // 0.5秒后执行父组件接口方法
            setTimeout(() => {
              this.$emit('loadMore')
            }, 500)
          }
        },
      bindSrcoll() {
        this.unScroll()
        if (this.scrollContainer) {
          this.container.addEventListener('scroll', this.scrollPage)
        }
      },
      unScroll() {
        if (this.scrollContainer) {
          this.container.removeEventListener('scroll', this.scrollPage)
        }
      }
    },
      // 页面销毁，移除滚动监听
      beforeDestroy () {
        this.unScroll()
      }
    }
</script>

<style scoped>

</style>
