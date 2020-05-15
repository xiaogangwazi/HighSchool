package com.gaoxiaobang.community.dao;

import com.gaoxiaobang.community.entity.Userinfo;
import com.jcraft.jsch.UserInfo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface UserinfoMapper {
    int addFriendsCount(@Param("id")int id,@Param("count") int count);
    int addScore(@Param("id") int id,@Param("score") int score);
    List<UserInfo> selectFocusedList(int targetid);
    List<Userinfo> selectFocusList(int userid);
    int updateByHeaderUrlPrimaryKey(@Param("id") int id, @Param("headurl") String headurl);
    int deleteByPrimaryKey(Integer id);
    int updateStatueByPrimaryKey(Userinfo userinfo);
    int insert(Userinfo record);

    Userinfo selectByPrimaryKey(Integer id);
    Userinfo selectByEmail(String email);
    Userinfo selectByNickname(@Param("nickName") String nickName);

    List<Userinfo> selectAll();

    int updateByPrimaryKey(Userinfo record);
}