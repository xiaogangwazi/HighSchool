package com.gaoxiaobang.community.service.userinfo;

import com.gaoxiaobang.community.entity.Userinfo;
import com.jcraft.jsch.UserInfo;

import java.util.List;

public interface UserInfoService {
    int addFriendsCount(int id,int count);
    int deleteByPrimaryKey(Integer id);
    int addScore(int id,int score);

    int insert(Userinfo record);

    Userinfo selectByPrimaryKey(Integer id);

    List<Userinfo> selectAll();
    List<Userinfo> selectFocusList(int userid);
    List<UserInfo> selectFocusedList(int targetid);
    int updateByPrimaryKey(Userinfo record);
    int updateByHeaderUrlPrimaryKey(int id,String headurl);
    Userinfo selectByEmail(String email);

    Userinfo selectByNickname(String nickname);
    int updateStatueByPrimaryKey(Userinfo userinfo);

}
