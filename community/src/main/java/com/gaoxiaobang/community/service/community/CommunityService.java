package com.gaoxiaobang.community.service.community;

import com.gaoxiaobang.community.entity.Community;

import java.util.List;

public interface CommunityService {
    int deleteByPrimaryKey(Integer id);

    int insert(Community record);

    Community selectByPrimaryKey(Integer id);

    List<Community> selectAll();

    int updateByPrimaryKey(Community record);
    List<Community> selectTopicList();
}
