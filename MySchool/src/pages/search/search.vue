<template>
    <div>
      <search-header @search="search" :content="content"></search-header>
      <search-hot :hot-search="hotSearch" @searchByHot="searchByHot"></search-hot>
      <div v-if="result" class="search-result">
        <div v-for="item,index in result" :key="index">
       <message-item :message="item"></message-item>
        </div>
      </div>
      <load-more :more="more"></load-more>
    </div>
</template>

<script>
  import  messageItem from '../../components/messageItem'
  import  searchHeader from './components/searchHeader'
  import  searchHot from './components/searchHot'
  import  loadMore from '../../components/load-more'
  import qs from 'qs'
    export default {
        name: "search",
      data:function(){
          return{
            content:'',
            hotSearch:[],
            result:[],
            showhot:true,
            page:0,
            size:10,
            more:false
          }
      },
      components:{
          searchHeader,
          searchHot,
        messageItem,
        loadMore
      },
      methods:{
        searchByHot:function(content){
          if(content!=null&&content!=''){
            this.search(content)
          }
        },
          search:function (content) {
          this.content=content
          this.showhot=false
            const pa= {
              content,
              page:this.page,
              size:this.size
            }
            const that = this
              this.axios.post(this.remote+"/message/search",qs.stringify(pa)).then(resolve=>{
              if(resolve.data.result!=null) {
                that.result = resolve.data.result.data.content
              }else{
                that.$message({
                  showClose: true,
                  message: '暂无相关内容',
                  type: 'error'
                });
              }
              }).catch(e=>{
                console.log(e)
              })
          },
        scrollToBottom:function () {
          const bottom = document.getElementsByClassName('search-result').item(0).scrollTop
          const height= window.screenTop
          console.log(bottom)
          console.log(height)
        }
      },
      mounted() {
          window.addEventListener('scroll',this.scrollToBottom(),true)
          const  that = this
          this.axios.get(that.remote+"/message/getSearchSet").then(resolve=>{
            if(resolve.data.result!=null){
              console.log(resolve.data.result.set)
              that.hotSearch=resolve.data.result.set
            }
          }).catch(error=>{
            console.log(error)
          })
      }
    }
</script>

<style scoped>

</style>
