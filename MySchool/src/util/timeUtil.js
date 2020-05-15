Date.prototype.Format = function (fmt) {
  var o = {
    "M+": this.getMonth() + 1,                 //月份
    "d+": this.getDate(),                    //日
    "h+": this.getHours(),                   //小时
    "m+": this.getMinutes(),                 //分
    "s+": this.getSeconds(),                 //秒
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
    "S": this.getMilliseconds()             //毫秒
  };
  if (/(y+)/.test(fmt))
    fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt))
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}

const timeUtil= {
  formatTimeToStr: function (times, pattern) {
    var d = new Date(times).Format("yyyy-MM-dd hh:mm:ss");
    if (pattern) {
      d = new Date(times).Format(pattern);
    }
    return d.toLocaleString();
  },
  formatDate:function(times) {
    var curr = new Date()
    var time = new Date(times)
    var now = {
      "y+": curr.getFullYear(),                //年份
      "M+": curr.getMonth() + 1,                 //月份
      "d+": curr.getDate(),                    //日
      "h+": curr.getHours(),                   //小时
      "m+": curr.getMinutes(),                 //分
      "s+": curr.getSeconds(),                 //秒
      "q+": Math.floor((curr.getMonth() + 3) / 3), //季度
      "S": curr.getMilliseconds(),            //毫秒
      "w+": curr.getDay(),             //星期
      "t+": curr.getTime()                       //时间
    };
    var _time = {
      "y+": time.getFullYear(),                //年份
      "M+": time.getMonth() + 1,                 //月份
      "d+": time.getDate(),                    //日
      "h+": time.getHours(),                   //小时
      "m+": time.getMinutes(),                 //分
      "s+": time.getSeconds(),                 //秒
      "q+": Math.floor((time.getMonth() + 3) / 3), //季度
      "S": time.getMilliseconds(),          //毫秒
      "w+": time.getDay(),                      //星期
      "t+": time.getTime()                       //时间
    };
    var week = ["一", "二", "三", "四", "五", "六", "天"]
    var result = ''
    if (now["y+"] - _time["y+"] != 0) {//非今年，格式化年份
      if (now["y+"] - _time["y+"] == 1) {
        result = result + '去年'
      } else if (now["y+"] - _time["y+"] == 2) {
        result = result + '前年'
      } else {
        result = result + _time["y+"] + '-'+_time["M+"] + '-' + _time["d+"]
      }
      result = result + ' ' + _time["M+"] + '-' + _time["d+"]
    } else {//今年
      var one = new Date()
      var tow = new Date()
      var thisweekbegin = new Date()
      var thisweekend = new Date()
      thisweekbegin.setDate(one.getDate() - thisweekbegin.getDay())
      thisweekend.setDate(one.getDate() - thisweekend.getDay() + 7)
      one.setDate(one.getDate() - 1)
      tow.setDate(tow.getDate() - 2)
      if (_time["y+"] == one.getFullYear() && _time["M+"] == one.getMonth() + 1 && _time["d+"] == one.getDate()) {//昨天
        result = result + '昨天 '
      } else if (_time["y+"] == tow.getFullYear() && _time["M+"] == tow.getMonth() + 1 && _time["d+"] == tow.getDate()) {//前天
        result = result + '前天'
      }else if (_time["y+"] == now["y+"] && _time["M+"] == now["M+"] && _time["d+"] == now["d+"]){

      }  else if (thisweekend.getTime() >= _time["t+"]&&thisweekbegin.getTime()<=_time["t+"]) {//同一星期
        result = result + '星期' + week[_time["w+"] - 1]
      }else {
        result = result + ' ' + _time["M+"] + '-' + _time["d+"]
      }
    }
    if(_time["h+"]>=0&&_time["h+"]<=4){
      result=result+'凌晨 '
    }else if(_time["h+"]>=5&&_time["h+"]<=6){
      result=result+'清晨 '
    }else if(_time["h+"]>=7&&_time["h+"]<=8){
      result=result+'早晨 '
    }else if(_time["h+"]>=9&&_time["h+"]<=11){
      result=result+'上午 '
    }else if(_time["h+"]==12){
      result=result+'中午 '
    }else if(_time["h+"]>=13&&_time["h+"]<=16){
      result=result+'下午 '
    }else if(_time["h+"]>=17&&_time["h+"]<=18){
      result=result+'黄昏 '
    }else if(_time["h+"]>=19&&_time["h+"]<=20){
      result=result+'傍晚 '
    }else if(_time["h+"]>=21&&_time["h+"]<=24){
      result=result+'晚上 '
    }

    result = result + _time["h+"] + ':' + _time["m+"] + ':' + _time["s+"]
    return result
  }
}
export default timeUtil
