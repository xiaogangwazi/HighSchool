package com.gaoxiaobang.community.service.topic;

import com.gaoxiaobang.community.entity.Topic;

import java.util.List;

public interface TopicService {
    int setCount(int id,int add);
    int deleteByPrimaryKey(Integer id);

    int insert(Topic record);

    Topic selectByPrimaryKey(Integer id);

    List<Topic> selectAll();

    int updateByPrimaryKey(Topic record);

    List<Topic> selectListByCommunityId(int communityid);
}
