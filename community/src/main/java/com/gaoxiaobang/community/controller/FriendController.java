package com.gaoxiaobang.community.controller;

import com.gaoxiaobang.community.common.ResponseType;
import com.gaoxiaobang.community.entity.Friend;
import com.gaoxiaobang.community.entity.Notice;
import com.gaoxiaobang.community.entity.Userinfo;
import com.gaoxiaobang.community.service.friend.FriendService;
import com.gaoxiaobang.community.service.notice.NoticeService;
import com.gaoxiaobang.community.service.userinfo.UserInfoService;
import com.gaoxiaobang.community.service.websocket.AddFriendNotice;
import com.gaoxiaobang.community.service.websocket.PushDate;
import com.gaoxiaobang.community.service.websocket.PushDateType;
import com.gaoxiaobang.community.service.websocket.WebSocketService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/friend")
@Log
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private NoticeService noticeService;
    @Value("${server.address}")
    private String address;
    @Value("${server.port}")
    private int port;
    @RequestMapping("/add")
    @ResponseBody
    public Object addFriend(@RequestParam("userid")Integer userid,@RequestParam("targetid") Integer targetid){
        if(userid!=null&&targetid!=null) {

            Userinfo userinfo = userInfoService.selectByPrimaryKey(userid);
            if (userinfo!=null) {
                Friend friend1 = friendService.selectByUserIdAndTargetId(userid, targetid);
                Friend friend2 = friendService.selectByUserIdAndTargetId(targetid, userid);
                if(friend1!=null||friend2!=null){
                    return ResponseType.responseType_200();
                }
                Friend friend = new Friend();
                friend.setUserId(userid);
                friend.setTargetid(targetid);
                friend.setStatue(0);
                friendService.insert(friend);
                userInfoService.addFriendsCount(userid,1);
                PushDate pushDate = new PushDate();
                pushDate.setType(PushDateType.FRIEND_ADD.getName());
                pushDate.setUserid(targetid);
                Notice notice = new Notice();
                notice.setTargetid(targetid);
                notice.setTime(new Date());
                notice.setType((byte)1);
                notice.setStatue((byte)0);
                notice.setMessage("用户" + userinfo.getNickname() + "请求加为好友");
                notice.setUrl("http://"+address+":"+port+"/friend/response?id="+friend.getId());
                noticeService.insert(notice);
                AddFriendNotice addFriendNotice = new AddFriendNotice();
                addFriendNotice.setFromuser(userinfo);
                addFriendNotice.setNotice(notice);
                pushDate.addDate("notice",addFriendNotice);
                try {
                    webSocketService.pushMessage(targetid, pushDate);
                } catch (Exception e) {
                    log.info("推送添加好友失败"+e.getMessage());
                }
            }
        }
        return ResponseType.responseType_200();
    }

    /**
     * 获得好用列表
     * @param userid
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFriendList")
    public Object getFriendList(@RequestParam("userid")Integer userid){
        if(userid!=null) {
            List<Friend> friendList = friendService.getFriendList(userid);
            ResponseType responseType = ResponseType.responseType_200();
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("friendList",friendList);
            responseType.setResult(hashMap);
            return responseType;
        }
        return ResponseType.responseType_200();
    }

    /**
     * 删除好友方法
     * @param userid
     * @param targetid
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object deleteFriend(@RequestParam("userid") Integer userid,@RequestParam("targetid")  Integer targetid){
        if(userid!=null&&targetid!=null) {
            friendService.deleteFriend(userid, targetid);
        }
        return  ResponseType.responseType_200();
    }

    /**
     * 处理好友请求
     * 1.同意好友申请将好友状态改为1
     *2.拒绝好友申请的话将好友信息状态修改为2，表示拒绝，即废号有关系
     * 3.将通知状态更改为1，表示已处理
     * @param id
     * @param act
     * @param noticeId
     * @return
     */
    @RequestMapping("/response")
    @ResponseBody
    public Object response(@RequestParam("id") Integer id ,@RequestParam("act") Integer act,@RequestParam("noticeId") Integer noticeId){
            if(id!=null&&act!=null){
                noticeService.updateStatue(noticeId,1);
                if(act==1){//添加好友操作
                    friendService.updateStatue(id,1);
                }else {
                    friendService.updateStatue(id,2);
                }
            }
            return  ResponseType.responseType_200();
    }







}
