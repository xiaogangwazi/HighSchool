package com.gaoxiaobang.community.service.comments;

import com.gaoxiaobang.community.entity.Comments;

import java.util.List;

public interface CommentsService {
    int deleteByPrimaryKey(Integer id);

    int insert(Comments record);

    Comments selectByPrimaryKey(Integer id);

    List<Comments> selectAll();

    int updateByPrimaryKey(Comments record);
    List<Comments> selectByMessageId(int messageid);
    List<Comments> selectByReplyto(int replyto);

}
