package com.gaoxiaobang.community.controller;

import com.gaoxiaobang.community.common.ResponseType;
import com.gaoxiaobang.community.entity.Community;
import com.gaoxiaobang.community.entity.Topic;
import com.gaoxiaobang.community.service.community.CommunityService;
import com.gaoxiaobang.community.service.topic.TopicService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller()
@Log
public class CommunityController {
    @Autowired
    private CommunityService communityService;
    @Autowired
    private TopicService topicService;

    /**
     * 获取社区列表
     * @return
     */
    @ResponseBody
    @RequestMapping("/community/list")
    public Object getCommunityList(){
        List<Community> communities = communityService.selectTopicList();
        ResponseType responseType = ResponseType.responseType_200();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("communityList",communities);
        responseType.setResult(hashMap);
        return responseType;

    }

    /**
     * 根据主题id获得主题信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/community/topic")
    public Object getTopicContents(int id){
        Topic topic = topicService.selectByPrimaryKey(id);
        ResponseType responseType = ResponseType.responseType_200();
        if(topic!=null){
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("topic",topic);
           responseType.setResult(hashMap);
        }
        return responseType;
    }



}
