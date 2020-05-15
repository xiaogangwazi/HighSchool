package com.gaoxiaobang.community.dao;

import com.gaoxiaobang.community.entity.UserPassword;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserPasswordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPassword record);

    UserPassword selectByPrimaryKey(Integer id);

    UserPassword selectByUserId(Integer userId);


    List<UserPassword> selectAll();

    int updateByPrimaryKey(UserPassword record);
}