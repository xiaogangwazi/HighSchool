package com.gaoxiaobang.community.websocket;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
@Log
@ServerEndpoint("/websocket/{userid}")
@Service
public class WebSocket {
    private Session session;
    private int userid;
    private  static CopyOnWriteArraySet<WebSocket> websockets= new CopyOnWriteArraySet<>();
    private  static Map<Integer,Session>  sessionpool=new ConcurrentHashMap<>();
    @OnOpen
    public void onOpen(Session session , @PathParam("userid") int userid){
                    this.session=session;
                    this.userid=userid;
                    websockets.add(this);
                    sessionpool.put(userid,session);
                    log.info("用户id="+userid+"上线"+"当前在线用户:"+sessionpool.size());
    }
    @OnClose
    public void onClose(){
                websockets.remove(this);
                sessionpool.remove(userid);
                log.info("用户"+userid+"下线，当前在线用户:"+sessionpool.size());
    }
    @OnMessage
    public void onMessage(String message){
                System.out.println(message);
    }
    @OnError
    public void onError(Session session,Throwable throwable){
                log.warning("websocket出错了："+throwable.getMessage());
    }

    public void sendAllMessage(String message){
        for(WebSocket w:websockets){
            try {
                w.session.getAsyncRemote().sendText(message);
            }catch (Exception e){
                log.warning(e.getMessage());
            }
        }
    }
    public void sendOneMessage(Integer userid,String message) throws Exception{
        Session session = sessionpool.get(userid);
        if(session==null){
            throw new Exception("用户不存在或者不在线");
        }
        try {
                session.getAsyncRemote().sendText(message);
            }catch (Exception e){
                log.warning(e.getMessage());
                throw  e;
            }

    }
    public void pushMessage(String message){
        this.session.getAsyncRemote().sendText(message);
    }

}
