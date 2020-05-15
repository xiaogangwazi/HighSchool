package com.gaoxiaobang.community.controller;

import com.gaoxiaobang.community.common.ResponseType;
import com.gaoxiaobang.community.entity.Focustopic;
import com.gaoxiaobang.community.entity.Topic;
import com.gaoxiaobang.community.service.focus.FocusTopicService;
import com.gaoxiaobang.community.service.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private FocusTopicService focusTopicService;

    /**
     * 获取主题的详情，包括用户是否关注了改主题
     * @param topicid
     * @param userid
     * @return
     */
    @ResponseBody
    @RequestMapping("/get")
    public Object get(@RequestParam("topicid") int topicid,@RequestParam("userid") int userid){
        Topic topic = topicService.selectByPrimaryKey(topicid);
        ResponseType responseType = ResponseType.responseType_200();
        if(topic!=null) {
            int i1 = focusTopicService.selectIfLikeTopic(topicid, userid);
            HashMap<String, Object> hashMap = new HashMap<>(2);
            hashMap.put("topic", topic);
            hashMap.put("focus",i1);
            responseType.setResult(hashMap);
        }
        return responseType;
    }

    /**
     * 关注和取消关注
     * focus表示是㕑操作还是取消关注操作 0表示取消关注 1表示关注
     * 首次查看是否存在记录，存在的话直接修改statue，
     * @param topicid
     * @param userid
     * @param focus
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateFocus")
    public Object focus(@RequestParam("topicid") int topicid,@RequestParam("userid") int userid,@RequestParam("focus") int focus){
        int i1 = focusTopicService.selectIfHas(topicid,userid);
        if(i1!=0){
            focusTopicService.updateFocus(topicid,userid,focus);
        }else{
            Topic topic = topicService.selectByPrimaryKey(topicid);
            Focustopic focustopic = new Focustopic();
            if(topic!=null) {
                focustopic.setTopicid(topicid);
                focustopic.setUserid(userid);
                focustopic.setStatue((byte) 1);
                focustopic.setTopicname(topic.getName());
                focusTopicService.insert(focustopic);
            }
        }
        ResponseType responseType = ResponseType.responseType_200();
        return responseType;
    }



}
