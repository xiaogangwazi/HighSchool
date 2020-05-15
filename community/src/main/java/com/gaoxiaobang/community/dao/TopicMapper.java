package com.gaoxiaobang.community.dao;

import com.gaoxiaobang.community.entity.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface TopicMapper {
    int setCount(@Param("id") int id,@Param("add") int add);
    int deleteByPrimaryKey(Integer id);

    int insert(Topic record);

    Topic selectByPrimaryKey(Integer id);

    List<Topic> selectAll();

    int updateByPrimaryKey(Topic record);
    List<Topic> selectListByCommunityId(int communityid);
}