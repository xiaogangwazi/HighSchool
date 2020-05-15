package com.gaoxiaobang.community.controller;

import com.gaoxiaobang.community.common.ResponseType;
import com.gaoxiaobang.community.entity.Notice;
import com.gaoxiaobang.community.service.notice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @RequestMapping("/getNotice")
    public Object getNotice(@RequestParam("userid")Integer userid){
        ResponseType responseType = ResponseType.responseType_200();
        if(userid!=null){
            List<Notice> notices = noticeService.selectNoticeList(userid);
           int count= noticeService.getUnReadNoticeCount(userid);
            HashMap<String,Object> hashMap = new HashMap<>(1);
            hashMap.put("noticeList",notices);
            hashMap.put("unreadCount",count);
            responseType.setResult(hashMap);
        }
        return responseType;
    }
}
