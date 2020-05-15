package com.gaoxiaobang.community.service.chat.impl;

import com.gaoxiaobang.community.dao.ChatMapper;
import com.gaoxiaobang.community.entity.Chat;
import com.gaoxiaobang.community.service.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatMapper chatMapper;

    @Override
    public int setRead(int userid, int id, int count) {

        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return chatMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Chat record) {
        return chatMapper.insert(record);
    }

    @Override
    public Chat selectByPrimaryKey(Integer id) {
        return chatMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Chat> selectAll() {
        return chatMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Chat record) {
        return chatMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<String> getChatList(int userid) {
        return chatMapper.getChatList(userid);
    }

    @Override
    public List<Chat> selectChatConversation(String conversationid) {
        return chatMapper.selectChatConversation(conversationid);
    }

    @Override
    public int getUnreadCount(String conversationid, int fromid, int toid) {
        return chatMapper.getUnreadCount(conversationid,fromid,toid);
    }

    @Override
    public int setStep(int id, int step) {
        return chatMapper.setStep(id,step);
    }

    @Override
    public int setStepList(List<Integer> ids) {
        return chatMapper.setStepList(ids);
    }
}
