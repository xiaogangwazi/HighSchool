package com.gaoxiaobang.community.service.chat;

import com.gaoxiaobang.community.entity.Chat;

import java.util.List;

public interface ChatService {
    int setRead(int userid,int id,int count);
    int deleteByPrimaryKey(Integer id);

    int insert(Chat record);

    Chat selectByPrimaryKey(Integer id);

    List<Chat> selectAll();

    int updateByPrimaryKey(Chat record);
    List<String> getChatList(int userid);
    List<Chat> selectChatConversation(String conversationid);
    int getUnreadCount(String conversationid,int fromid,int toid);
    int setStep(int id,int step);
    int setStepList(List<Integer> ids);
}
