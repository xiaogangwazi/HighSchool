package com.gaoxiaobang.community.dao;

import com.gaoxiaobang.community.entity.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface FriendMapper {
    int updateStatue(@Param("id") int id,@Param("statue") int statue);
    int deleteByPrimaryKey(Integer friendId);

    int insert(Friend record);

    Friend selectByPrimaryKey(Integer friendId);

    List<Friend> selectAll();

    int updateByPrimaryKey(Friend record);
    List<Friend> getFriendList(int userid);
    int deleteFriend(@Param("userid") int userid,@Param("targetid") int targetid);
    Friend selectByUserIdAndTargetId(@Param("userid") int userid,@Param("targetid") int targetid);
}