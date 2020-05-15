package com.gaoxiaobang.community.controller;

import com.gaoxiaobang.community.common.ResponseType;
import com.gaoxiaobang.community.entity.Comments;
import com.gaoxiaobang.community.entity.CommentsVo;
import com.gaoxiaobang.community.service.comments.CommentsService;
import com.gaoxiaobang.community.service.message.MessageService;
import com.gaoxiaobang.community.service.userinfo.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller()
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 根据messageid获取评论列表详情
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object getComments(int id){
        List<Comments> comments = commentsService.selectByMessageId(id);
        HashMap<String,Object> hashMap = new HashMap<>();
        List<CommentsVo> list = new ArrayList<>();
       for(Comments comments1:comments){
           CommentsVo commentsVo = new CommentsVo();
           commentsVo.setComments(comments1);
           List<Comments> comments2 = commentsService.selectByReplyto(comments1.getId());
               commentsVo.setList(comments2);
           commentsVo.setCount();
           list.add(commentsVo);
       }
       hashMap.put("list",list);
        ResponseType responseType = ResponseType.responseType_200();
        responseType.setResult(hashMap);
        return responseType;

    }

    /**
     * 添加评论，可以是给主题添加评论，给评论添加评论
     * @param comments
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addComments(Comments comments){
        if(comments!=null) {
            comments.setTime(new Date());
            comments.setStatue((byte)0);
            int insert = commentsService.insert(comments);
            if (insert > 0) {
                messageService.addCount(comments.getMessageid(), 1);
                userInfoService.addScore(comments.getUserid(), 100);
            }
        }
        return ResponseType.responseType_200();
    }

    /**
     *删除评论
     * @param messageid
     * @param conmmentid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object addComments(int messageid,int conmmentid){
        int insert = commentsService.deleteByPrimaryKey(conmmentid);
        if (insert>0){
            messageService.addCount(messageid,-1);
        }
        return ResponseType.responseType_200();
    }



}
