package com.gaoxiaobang.community.dao;

import com.gaoxiaobang.community.entity.Chat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ChatMapper {
    int setStepList(List<Integer> list);
    int setStep(@Param("id")int id,@Param("step")int step);
    int deleteByPrimaryKey(Integer id);

    int insert(Chat record);

    Chat selectByPrimaryKey(Integer id);

    List<Chat> selectAll();

    int updateByPrimaryKey(Chat record);
    List<String> getChatList(int userid);
    List<Chat> selectChatConversation(String conversationid);
    int getUnreadCount(@Param("conversationid") String conversationid,@Param("fromid") int fromid,@Param("toid") int toid);
}