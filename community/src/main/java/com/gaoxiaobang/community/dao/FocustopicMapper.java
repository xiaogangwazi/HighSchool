package com.gaoxiaobang.community.dao;

import com.gaoxiaobang.community.entity.Focustopic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface FocustopicMapper {
    Focustopic selectIfHas(@Param("topicid") int topicid,@Param("userid") int userid);
    int selectIfLikeTopic(@Param("topicid") int topicid,@Param("userid") int userid);
    int deleteByPrimaryKey(Integer id);
    int insert(Focustopic record);

    Focustopic selectByPrimaryKey(Integer id);

    List<Focustopic> selectAll();

    int updateByPrimaryKey(Focustopic record);
    int updateFocus(@Param("topicid")int topicid,@Param("userid") int userid,@Param("focus") int focus);
}