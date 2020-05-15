package com.gaoxiaobang.community.service.friend;

import com.gaoxiaobang.community.entity.Friend;

import java.util.List;

public interface FriendService {
    int deleteByPrimaryKey(Integer friendId);

    int insert(Friend record);

    Friend selectByPrimaryKey(Integer friendId);

    List<Friend> selectAll();

    int updateByPrimaryKey(Friend record);
    List<Friend> getFriendList(int userid);
    int deleteFriend(int userid,int targetid);
    int updateStatue(int id,int statue);
    Friend selectByUserIdAndTargetId(int userid,int targetid);
}
