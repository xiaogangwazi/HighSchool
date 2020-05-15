package com.gaoxiaobang.community.service.community.impl;

import com.gaoxiaobang.community.common.util.JSONUtil;
import com.gaoxiaobang.community.config.redis.MyRedisCacheWriterCustomer;
import com.gaoxiaobang.community.dao.CommunityMapper;
import com.gaoxiaobang.community.entity.Community;
import com.gaoxiaobang.community.service.community.CommunityService;
import javafx.util.Duration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public int deleteByPrimaryKey(Integer id) {

        int count= communityMapper.deleteByPrimaryKey(id);
        List<Community> communities = communityMapper.selectTopicList();
        redisTemplate.opsForValue().set("communityList",JSONUtil.toJsonString(communities),60*60*24*30, TimeUnit.SECONDS);
        return count;
    }

    @Override
    public int insert(Community record) {
        int count=communityMapper.insert(record);
        List<Community> communities = communityMapper.selectTopicList();
        redisTemplate.opsForValue().set("communityList",JSONUtil.toJsonString(communities),60*60*24*30, TimeUnit.SECONDS);

        return count;
    }

    @Override
    public Community selectByPrimaryKey(Integer id) {

        return communityMapper.selectByPrimaryKey(id);
    }


    @Override
    public List<Community> selectAll() {
        return communityMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Community record) {
        int count = communityMapper.updateByPrimaryKey(record);
        List<Community> communities = communityMapper.selectTopicList();
        redisTemplate.opsForValue().set("communityList",JSONUtil.toJsonString(communities),60*60*24*30, TimeUnit.SECONDS);
        return count;
    }


    @Override
    public List<Community> selectTopicList() {
        String communityList = (String) redisTemplate.opsForValue().get("communityList");
        if(!StringUtils.isBlank(communityList)){
            List<Community> communities = JSONUtil.ParseList(communityList, Community.class);
            return communities;
        }else {
            List<Community> communities = communityMapper.selectTopicList();
            redisTemplate.opsForValue().set("communityList",JSONUtil.toJsonString(communities),60*60*24*30, TimeUnit.SECONDS);
            return  communities;
        }
    }
}
