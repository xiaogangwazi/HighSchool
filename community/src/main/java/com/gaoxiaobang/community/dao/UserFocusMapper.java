package com.gaoxiaobang.community.dao;

import com.gaoxiaobang.community.entity.UserFocus;

import java.util.List;

public interface UserFocusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserFocus record);

    UserFocus selectByPrimaryKey(Integer id);

    List<UserFocus> selectAll();

    int updateByPrimaryKey(UserFocus record);
}