package com.gaoxiaobang.community.service.userinfo.impl;

import com.gaoxiaobang.community.common.Test;
import com.gaoxiaobang.community.dao.UserinfoMapper;
import com.gaoxiaobang.community.entity.Userinfo;
import com.gaoxiaobang.community.service.userinfo.UserInfoService;
import com.jcraft.jsch.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Test
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserinfoMapper userinfoMapper;

    @Override
    public int addFriendsCount(int id, int count) {
       return userinfoMapper.addFriendsCount(id,count);

    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int addScore(int id, int score) {
        return userinfoMapper.addScore(id,score);
    }

    @Override
    public int insert(Userinfo record) {
        return userinfoMapper.insert(record);
    }

    @Override
    public Userinfo selectByPrimaryKey(Integer id) {
        return userinfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Userinfo> selectAll() {
        return null;
    }

    @Override
    public List<Userinfo> selectFocusList(int userid) {
        return userinfoMapper.selectFocusList(userid);
    }

    @Override
    public List<UserInfo> selectFocusedList(int targetid) {
        return userinfoMapper.selectFocusedList(targetid);
    }

    @Override
    public int updateByPrimaryKey(Userinfo record) {
        return userinfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByHeaderUrlPrimaryKey(int id, String headurl) {
        return userinfoMapper.updateByHeaderUrlPrimaryKey(id,headurl);
    }

    @Override
    public Userinfo selectByEmail(String email) {
        return userinfoMapper.selectByEmail(email);
    }

    @Override
    public Userinfo selectByNickname(String nickname) {
        return userinfoMapper.selectByNickname(nickname);
    }

    @Override
    public int updateStatueByPrimaryKey(Userinfo userinfo) {
        return userinfoMapper.updateStatueByPrimaryKey(userinfo);

    }
}
