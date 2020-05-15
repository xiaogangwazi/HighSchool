package com.gaoxiaobang.community.controller;

import com.alibaba.fastjson.JSON;
import com.gaoxiaobang.community.common.ResponseType;
import com.gaoxiaobang.community.common.util.JSONUtil;
import com.gaoxiaobang.community.entity.Chat;
import com.gaoxiaobang.community.entity.Userinfo;
import com.gaoxiaobang.community.service.chat.ChatService;
import com.gaoxiaobang.community.service.websocket.PushDate;
import com.gaoxiaobang.community.service.websocket.PushDateType;
import com.gaoxiaobang.community.service.websocket.WebSocketService;
import com.jcraft.jsch.UserInfo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/chat")
@Log
public class ChatController {
        @Autowired
        private ChatService chatService;
        @Autowired
        private WebSocketService webSocketService;
        @Autowired
         RedisTemplate redisTemplate;

    /**
     * 获取用户聊天列表，还有显示未读消息数,用于前端启动的时候加载，加载之后将状态更改为已读
     * @param userid
     * @return
     */
    @GetMapping("/getchatList")
        public Object getChatList(@RequestParam("userid") Integer userid){
            if(userid!=null) {
                List<String> chatList = chatService.getChatList(userid);
                List<HashMap<String,Object>> lists = new ArrayList<>();
                int totalUnreadCount=0;
                if(chatList!=null){

                    ListIterator<String> stringListIterator = chatList.listIterator();
                    while (stringListIterator.hasNext()){
                        HashMap<String ,Object> hashMap = new HashMap<>();
                        List<Chat> chats = chatService.selectChatConversation(stringListIterator.next());
                        if(chats!=null) {
                            hashMap.put("list", chats);
                            Chat chat = chats.get(0);
                            if(chat.getToid()==userid){
                                hashMap.put("target",chat.getFromuser());
                            }else{
                                hashMap.put("target",chat.getTouser());
                            }
                            int unread = 0;
                            if (chat.getToid() == userid) {
                                unread = chatService.getUnreadCount(chat.getConversationid(), chat.getFromid(), chat.getToid());
                            }
                            totalUnreadCount+=unread;
                            hashMap.put("unreadCount", unread);
                            lists.add(hashMap);
                            redisTemplate.opsForValue().append(chat.getConversationid()+":"+userid,JSONUtil.toJsonString(hashMap));//将用户chat列表中的数据存入redis中，key是 'conversationid:userid',值是{list，unreadCount，target}
                        }
                    }
                }
                ResponseType responseType = ResponseType.responseType_200();
                HashMap<String,Object > hashMap = new HashMap<>();
                hashMap.put("chatList",lists);
                hashMap.put("unreadCount",totalUnreadCount);
                responseType.setResult(hashMap);
                return  responseType;
            }
            return ResponseType.responseType_200();
        }

    /**
     * 私信发送类，首先尝试用websocket推送消息，假如推送失败的话将数据保存到数据库中，并且标注状态为未读，如果推送成功，将数据保存导数据库，状态为已读
     * step 0:表示已经发送，未接收；1表示已接受；未读；2表示已接受，已读
     * @param userid
     * @param targetid
     * @param content
     * @return
     */
        @RequestMapping("/send")
        public Object  send(@RequestParam("userid") Integer userid,@RequestParam("targetid") Integer targetid,@RequestParam("content") String content){
                            if(targetid==null&&targetid==null&&content==null){
                                return ResponseType.responseType_1008();
                            }
                            Chat chat = new Chat();
                            chat.setFromid(userid);
                            chat.setToid(targetid);
                            chat.setContent(content);
                            chat.setTime(new Date());
                            chat.setStatue((byte)0);
                            chat.setStep(0);
                            chat.setType((byte)1);
                            String coversationid= userid>targetid?targetid+"_"+userid:userid+"_"+targetid;
                            chat.setConversationid(coversationid);
                            chatService.insert(chat);
                            chat=chatService.selectByPrimaryKey(chat.getId());
                            try {
                                PushDate pushDate = new PushDate();
                                pushDate.setUserid(chat.getFromid());
                                pushDate.setType(PushDateType.MESSAGE.getName());
                                pushDate.addDate("chat",chat);
                                webSocketService.pushMessage(chat.getToid(),pushDate);
                                chat.setStep(2);
                                chatService.setStep(chat.getId(),2);
                            }catch (Exception e){
                                if(e instanceof NullPointerException){
                                    log.warning("私信推送失败了！,用户不存在或者已经下线。");
                                }else {
                                    log.warning("私信推送失败！"+e);
                                }
                                chat.setStep(1);
                                chatService.setStep(chat.getId(),1);
                            }
                         ResponseType responseType = ResponseType.responseType_200();
                            HashMap<String,Object> hashMap= new HashMap<>(1);
                            Chat chat1 = chatService.selectByPrimaryKey(chat.getId());
                            hashMap.put("chat",chat1);
                            responseType.setResult(hashMap);
                           return responseType;
        }
    @RequestMapping("/read")
    public Object read1(@RequestParam("list") List<Integer> ids) {
            if(ids!=null&&ids.size()!=0) {
               chatService.setStepList(ids);
            }
        return ResponseType.responseType_200();
    }
}
