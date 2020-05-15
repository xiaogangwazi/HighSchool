package com.gaoxiaobang.community.dao;

import com.gaoxiaobang.community.entity.Community;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CommunityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Community record);

    Community selectByPrimaryKey(Integer id);

    List<Community> selectAll();

    int updateByPrimaryKey(Community record);

    List<Community> selectTopicList();
}