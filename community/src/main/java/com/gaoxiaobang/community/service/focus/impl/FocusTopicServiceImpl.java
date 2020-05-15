package com.gaoxiaobang.community.service.focus.impl;

import com.gaoxiaobang.community.dao.FocustopicMapper;
import com.gaoxiaobang.community.entity.Focustopic;
import com.gaoxiaobang.community.service.focus.FocusTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FocusTopicServiceImpl  implements FocusTopicService {
    @Autowired
    private FocustopicMapper focustopicMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return focustopicMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Focustopic record) {
        int insert = focustopicMapper.insert(record);
        redisTemplate.opsForValue().set(record.getUserid()+":"+record.getTopicid(),record.getId()+"");
        return insert;
    }

    @Override
    public Focustopic selectByPrimaryKey(Integer id) {
        return focustopicMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Focustopic> selectAll() {
        return focustopicMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Focustopic record) {
        return focustopicMapper.updateByPrimaryKey(record);
    }



    @Override
    public int selectIfLikeTopic(int topicid, int userid) {
        String o = (String)redisTemplate.opsForValue().get(userid + ":" + topicid);
        if(o!=null){
            return Integer.valueOf(o);
        }
        return focustopicMapper.selectIfLikeTopic(topicid,userid);
    }

    @Override
    public int selectIfHas(int topicid, int userid) {
        String o = (String)redisTemplate.opsForValue().get(userid + ":" + topicid);
        if(o!=null){
            return Integer.valueOf(o);
        }
        Focustopic focustopic = focustopicMapper.selectIfHas(topicid, userid);
        if(focustopic!=null){
            return focustopic.getId();
        }
        return 0;
    }


    @Override
    public int updateFocus(int topicid, int userid, int focus) {
        return focustopicMapper.updateFocus(topicid,userid,focus);
    }
}
