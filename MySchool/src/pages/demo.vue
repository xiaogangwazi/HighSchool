<template>
  <div class="container">
    <div class="wrapper">
      <div class="section" style="height:500px;width:100%" v-for="(item, index) in list" :key="index" :style="{'height':index==0?'1000px':'500px'}">
        <div style="width:100%;height:100%;font-size:30px;text-align:center;font-weight:bold;color:#fff;" :style="{'background':item.backgroundcolor}">{{item.name}}</div>
      </div>
    </div>
    <div id="nac" style="height:500px;"></div>
    <nav style="position:fixed;right:30px;top:300px;">
      <a class="nav1" v-for="(item, index) in navList" :key="index" @click="jump(index)" :class="index==0?'current':''">{{item}}</a>
    </nav>
  </div>
</template>
<script>
  export default {
    name:'demo',
    data:function(){
      return {
        scroll: '',
        list: [{
          name: "第一条",
          backgroundcolor: "#90B2A3"
        }, {
          name: "第二条",
          backgroundcolor: "#A593B2"
        }, {
          name: "第三条",
          backgroundcolor: "#A7B293"
        }, {
          name: "第四条",
          backgroundcolor: "#0F2798"
        }, {
          name: "第五条",
          backgroundcolor: "#0A464D"
        }],
        navList: [1, 2, 3, 4, 5]
      }
    },
    methods: {
      dataScroll: function () {
        this.scroll = document.documentElement.scrollTop || document.body.scrollTop;
      },
      jump(index) {
        let jump = document.getElementsByClassName('section');
        // 获取需要滚动的距离
        let total = jump[index].offsetTop;
        // Chrome
        document.body.scrollTop = total;
        // Firefox
        document.documentElement.scrollTop = total;
        // Safari
        window.pageYOffset = total;
        // $('html, body').animate({
        // 'scrollTop': total
        // }, 400);
      },
      loadSroll: function () {
        var self = this;
        var navs = document.getElementsByClassName("nav1");
        var sections = document.getElementsByClassName('section');
        for (var i = sections.length - 1; i >= 0; i--) {
          if (self.scroll >= sections[i].offsetTop - 100) {
            navs[i].classList.add('current')
            navs[i].classList.remove('current')
            break;
          }
        }
      }
    },
    watch: {
      scroll: function () {
        this.loadSroll()
      }
    },
    mounted() {
      window.addEventListener('scroll', this.dataScroll,true);
    }
  }
</script>

<style scoped lang="stylus">
  * {
    padding: 0;
    margin: 0;
  }
  .nav1 {
    display: block;
    width: 40px;
    height: 40px;
    text-align: center;
    line-height: 40px;
    background: #eee;
    margin: 10px 0;
  }
  .current {
    color: #fff;
    background: red;
  }
</style>

