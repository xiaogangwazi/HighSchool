import  axios from 'axios'
const  apiUtil= {

  getNotice1 :function() {
    console.log("更新")
    const that = this
    return new Promise(resolve => {
      const userid = JSON.parse(localStorage.getItem("user")).id
      that.axios.get(that.remote + "/notice/getNotice?userid=" + userid).then(resolve => {
        var list = JSON.parse(JSON.stringify(resolve.data.result.noticeList))
        that.$store.dispatch('setNotice', list)
        that.$store.dispatch('setUnreadCount', resolve.data.result.unreadCount)
      }).catch(error => {
        console.log(error)
      })
    })

  }
}
export default apiUtil;

