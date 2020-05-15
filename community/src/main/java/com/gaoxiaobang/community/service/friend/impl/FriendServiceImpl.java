package com.gaoxiaobang.community.service.friend.impl;

import com.gaoxiaobang.community.dao.FriendMapper;
import com.gaoxiaobang.community.entity.Friend;
import com.gaoxiaobang.community.service.friend.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendMapper friendMapper;
    @Override
    public int deleteByPrimaryKey(Integer friendId) {
        return friendMapper.deleteByPrimaryKey(friendId);
    }

    @Override
    public int insert(Friend record) {
        return friendMapper.insert(record);
    }

    @Override
    public Friend selectByPrimaryKey(Integer friendId) {
        return friendMapper.selectByPrimaryKey(friendId);
    }

    @Override
    public List<Friend> selectAll() {
        return friendMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Friend record) {
        return friendMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Friend> getFriendList(int userid) {
        return friendMapper.getFriendList(userid);
    }

    @Override
    public int deleteFriend(int userid, int targetid) {
        return friendMapper.deleteFriend(userid,targetid);
    }

    @Override
    public int updateStatue(int id, int statue) {
        return friendMapper.updateStatue(id,statue);
    }

    @Override
    public Friend selectByUserIdAndTargetId(int userid, int targetid) {
        return friendMapper.selectByUserIdAndTargetId(userid,targetid);
    }
}
