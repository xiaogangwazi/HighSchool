package com.gaoxiaobang.community.service.comments.impl;

import com.gaoxiaobang.community.dao.CommentsMapper;
import com.gaoxiaobang.community.entity.Comments;
import com.gaoxiaobang.community.service.comments.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsMapper commentsMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return commentsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Comments record) {
        return commentsMapper.insert(record);
    }

    @Override
    public Comments selectByPrimaryKey(Integer id) {
        return commentsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Comments> selectAll() {
        return commentsMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Comments record) {
        return commentsMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Comments> selectByMessageId(int messageid) {
        return commentsMapper.selectByMessageId(messageid);
    }

    @Override
    public List<Comments> selectByReplyto(int replyto) {
        return commentsMapper.selectByReplyto(replyto);
    }
}
