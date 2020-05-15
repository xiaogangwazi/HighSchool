package com.gaoxiaobang.community.service.message.impl;

import com.gaoxiaobang.community.dao.MessageMapper;
import com.gaoxiaobang.community.entity.Message;
import com.gaoxiaobang.community.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return messageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Message record) {
        return messageMapper.insert(record);
    }

    @Override
    public List<Message> selectFocusMessage(int id) {
        return messageMapper.selectFocusMessage(id);
    }

    @Override
    public Message selectByPrimaryKey(Integer id) {
        return messageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Message> selectAll() {
        return messageMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Message record) {
        return messageMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Message> selectByTopicId(int topicid) {
        return messageMapper.selectByTopicId(topicid);
    }

    @Override
    public int addCount(int id,int count) {
        return messageMapper.addCount(id,count);
    }

    @Override
    public int addLike(Integer id,int count) {
        if(redisTemplate.opsForZSet().score("message-like",String.valueOf(id))!=null){
            redisTemplate.opsForZSet().add("message-like",String.valueOf(id),0);
        }
        redisTemplate.opsForZSet().incrementScore("message-like",String.valueOf(id),count);
        return messageMapper.addLike(id,count);
    }

    @Override
    public int addSee(int userid,int id) {
        if(redisTemplate.opsForValue().get("message:"+id+":"+userid)!=null){
            return 0;
        }
            if (redisTemplate.opsForZSet().score("message-see", String.valueOf(id)) != null) {
                redisTemplate.opsForZSet().add("message-see", String.valueOf(id), 0);
            }
            redisTemplate.opsForZSet().incrementScore("message-see", String.valueOf(id), 1);
            redisTemplate.opsForValue().set("message:"+id+":"+userid,"1");
        return messageMapper.addSee(id);
    }
}
