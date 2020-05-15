package com.gaoxiaobang.community.dao;

import com.gaoxiaobang.community.entity.Blackfriend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface BlackfriendMapper {
    int insert(Blackfriend record);

    List<Blackfriend> selectAll();
}