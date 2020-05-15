package com.gaoxiaobang.community.service.topic.impl;

import com.gaoxiaobang.community.dao.TopicMapper;
import com.gaoxiaobang.community.entity.Topic;
import com.gaoxiaobang.community.service.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicMapper topicMapper;

    @Override
    public int setCount(int id, int add) {
        return topicMapper.setCount(id,add);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return topicMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Topic record) {
        return topicMapper.insert(record);
    }

    @Override
    public Topic selectByPrimaryKey(Integer id) {
        return topicMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Topic> selectAll() {
        return topicMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Topic record) {
        return topicMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Topic> selectListByCommunityId(int communityid) {
        return topicMapper.selectListByCommunityId(communityid);
    }
}
