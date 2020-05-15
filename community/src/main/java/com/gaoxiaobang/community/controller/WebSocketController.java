package com.gaoxiaobang.community.controller;

import com.gaoxiaobang.community.common.ResponseType;
import com.gaoxiaobang.community.common.util.JSONUtil;
import com.gaoxiaobang.community.entity.Chat;
import com.gaoxiaobang.community.service.chat.ChatService;
import com.gaoxiaobang.community.websocket.WebSocket;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
@Log
@Controller
public class WebSocketController {
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private ChatService chatService;

    /**
     * 当前端用户启动应用时调用该接口，用户可获取未读私信信息
     * @param userid
     */
    @GetMapping("/getchatList/{userid}")
    public void getChatList(@PathParam("userid") Integer userid){
        if(userid!=null) {
            List<String> chatList = chatService.getChatList(userid);
            List<HashMap<String,Object>> lists = new ArrayList<>();
            if(chatList!=null){
                ListIterator<String> stringListIterator = chatList.listIterator();
                while (stringListIterator.hasNext()){
                    HashMap<String ,Object> hashMap = new HashMap<>();
                    List<Chat> chats = chatService.selectChatConversation(stringListIterator.next());
                    if(chats!=null) {
                        hashMap.put("list", chats);
                        Chat chat = chats.get(0);
                        int unread = 0;
                        if (chat.getToid() == userid) {
                            unread = chatService.getUnreadCount(chat.getConversationid(), chat.getFromid(), chat.getToid());
                        }

                        hashMap.put("unreadCount", unread);
                        lists.add(hashMap);
                    }
                }
            }
            ResponseType responseType = ResponseType.responseType_200();
            HashMap<String,Object > hashMap = new HashMap<>();
            hashMap.put("chatList",lists);
            responseType.setResult(hashMap);
            String s = JSONUtil.toJsonString(responseType);
            try {
                webSocket.sendOneMessage(userid,s);
            } catch (Exception e) {
                log.info("推送失败："+e);

            }
        }
    }

    @GetMapping("/sendOneWebSocket/{userName}")
    public String sendOneWebSocket(@PathVariable("userid") Integer userName) {
        String text=userName+" 你好！ 这是websocket单人发送！";
        try {
            webSocket.sendOneMessage(userName,text);
        } catch (Exception e) {
            log.info("推送失败："+e);
        }
        return text;
    }
}
