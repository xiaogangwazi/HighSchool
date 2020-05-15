package com.gaoxiaobang.community.controller;

import com.gaoxiaobang.community.common.ResponseType;
import com.gaoxiaobang.community.entity.Message;
import com.gaoxiaobang.community.entity.Userinfo;
import com.gaoxiaobang.community.service.elasticsearch.MessageSearch;
import com.gaoxiaobang.community.service.focus.FocusTopicService;
import com.gaoxiaobang.community.service.message.MessageService;
import com.gaoxiaobang.community.service.topic.TopicService;
import com.gaoxiaobang.community.service.userinfo.UserInfoService;
import com.sun.jna.IntegerType;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Log
@RestController()
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MessageService messageService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MessageSearch messageSearch;
    @Autowired
    private FocusTopicService focusTopicService;


    /**
     * 根据id获取话题的具体信息，包括评论的具体信息
     * @param id
     * @return
     */
    @RequestMapping("/get")
    public Object getList(int id){
        Message message = messageService.selectByPrimaryKey(id);
        ResponseType responseType = ResponseType.responseType_200();
        if(message!=null){
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("message",message);
            responseType.setResult(hashMap);
        }
        return responseType;
    }



   /* @RequestMapping("/recommended")
    public Object getRecommended(@RequestParam("id") int id){

    }

*/

    @RequestMapping("/getSameSchool")
    public Object getRecommended(@RequestParam("id") int id, @RequestParam("index")Integer index){
        Userinfo userinfo = userInfoService.selectByPrimaryKey(id);
        ResponseType responseType = ResponseType.responseType_200();
        if(userinfo!=null&&!StringUtils.isBlank(userinfo.getSchoolname())) {
            Page<Message> messages = messageSearch.queryByUuser_Schoolname(userinfo.getSchoolname(), index, 10);
            HashMap<String, Object> hashMap = new HashMap<>(1);
            hashMap.put("list", messages);
            responseType.setResult(hashMap);
        }
        return responseType;
    }

    /**
     * 获取关注列表
     * @param id
     * @return
     */
    @RequestMapping("/getFocus")
    public Object getFocusMessage(@RequestParam("id") Integer id){
        ResponseType responseType = ResponseType.responseType_200();
        if(id!=null){
                        List<Message> messages = messageService.selectFocusMessage(id);
                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("focusList",messages);
                        responseType.setResult(hashMap);
                    }
                    return responseType;
    }

    /**
     * 删除发表信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteMessage")
    public Object deleteMessage(int id){
        int i1 = messageService.deleteByPrimaryKey(id);
        return  ResponseType.responseType_200();
    }

    /**
     * 点赞,取消点赞
     * @return
     */
    @RequestMapping("/addlike")
    public Object addLike(@RequestParam("id") Integer id,@RequestParam("count") Integer count){
        if(id!=null&count!=null) {
            int i1 = messageService.addLike(id, count);
        }
        return  ResponseType.responseType_200();
    }

    /**
     * 发布帖子,将帖子存入数据库和ES
     * @param message
     * @return
     */
    @RequestMapping("/publish")
    public Object publish(Message message){
        try {
            if (message != null && message.getPublicid() != null && message.getTopicid() != null && message.getTopicname() != null && !StringUtils.isBlank(message.getContent()) && !StringUtils.isBlank(message.getTitle())) {
                message.setPublictime(new Date());
                message.setStatue((byte)2);
                message.setStatue((byte)2);
                messageService.insert(message);
                userInfoService.addScore(message.getPublicid(),100);
                topicService.setCount(message.getTopicid(),1);
                Message message1 = messageService.selectByPrimaryKey(message.getId());
                messageSearch.add(message1);
            }
        }catch (Exception e){
            log.warning("发布出错："+e);
            return ResponseType.responseType_500();
        }
            return ResponseType.responseType_200();
    }

    /**
     * 搜索
     * @param content
     * @return
     */
    @RequestMapping("/search")
    public Object search(@RequestParam("content") String content,@RequestParam("page") int page,@RequestParam("size") int size){
        Page<Message> search = messageSearch.search(content,page,size);
        ResponseType responseType = ResponseType.responseType_200();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("data",search);
        responseType.setResult(hashMap);
        return responseType;
    }


    @RequestMapping("/getSearchSet")
    public Object getSearchSet(){
        Set<String> strings = messageSearch.searchSet(10);
        ResponseType responseType = ResponseType.responseType_200();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("set",strings);
        responseType.setResult(hashMap);
        return responseType;
    }


    @RequestMapping("/see")
    public Object see(@RequestParam("userid") Integer userid,@RequestParam("messageid") Integer messageid){

                   int count =0;
                   if(userid!=null&&messageid!=null){
                         count=messageService.addSee(userid,messageid);
                    }
                    if(count==1) {
                        return ResponseType.responseType_200();
                    }else {
                        return ResponseType.responseType_1009();
                    }
    }

}
