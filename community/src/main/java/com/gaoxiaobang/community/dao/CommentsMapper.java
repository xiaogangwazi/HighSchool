package com.gaoxiaobang.community.dao;

import com.gaoxiaobang.community.entity.Comments;
import com.gaoxiaobang.community.entity.Userinfo;

import java.util.List;

public interface CommentsMapper {
    Userinfo selectTarget(int id);
    int deleteByPrimaryKey(Integer id);

    int insert(Comments record);

    Comments selectByPrimaryKey(Integer id);

    List<Comments> selectAll();

    int updateByPrimaryKey(Comments record);
    List<Comments> selectByMessageId(int messageid);
    List<Comments> selectByReplyto(int replyto);
}