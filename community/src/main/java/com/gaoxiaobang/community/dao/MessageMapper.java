package com.gaoxiaobang.community.dao;

import com.gaoxiaobang.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface MessageMapper {
    List<Message> selectFocusMessage(@Param("userid") int id);
    int addSee(int id);
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    Message selectByPrimaryKey(Integer id);

    List<Message> selectAll();

    int updateByPrimaryKey(Message record);
    List<Message> selectByTopicId(int topicid);
    int addCount(@Param("id") int id,@Param("count") int count);
    int addLike(@Param("id") int id, @Param("count") int count);
}