package com.gaoxiaobang.community.service.message;

import com.gaoxiaobang.community.common.Page;
import com.gaoxiaobang.community.entity.Message;

import java.util.List;

public interface MessageService {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    List<Message> selectFocusMessage(int id);
    Message selectByPrimaryKey(Integer id);

    List<Message> selectAll();

    int updateByPrimaryKey(Message record);
    List<Message> selectByTopicId(int topicid);
    int addCount(int id,int count);
    int addLike(Integer id, int count);
    int addSee(int userid,int id);
}
