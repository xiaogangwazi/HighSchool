package com.gaoxiaobang.community.service.websocket;

import com.gaoxiaobang.community.common.util.JSONUtil;
import com.gaoxiaobang.community.entity.Chat;
import com.gaoxiaobang.community.websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * webSocketService类
 * 为用户实现消息的推动：
 * 1.用户私信的推送服务
 * 2.用户通知消息的推送：比如用户点赞，评论回复，通知等
 *
 */
@Service
public class WebSocketService {
    @Autowired
    private WebSocket webSocket;


    /**
     *
     * @param targetId//推送用户id
     * @param pushDate//推送数据实体
     * @throws Exception
     */
    public void pushMessage(int targetId,PushDate pushDate)  throws Exception{
        try {
            webSocket.sendOneMessage(targetId, JSONUtil.toJsonString(pushDate));
        }catch (Exception e){
            throw  e;
        }
    }

}
