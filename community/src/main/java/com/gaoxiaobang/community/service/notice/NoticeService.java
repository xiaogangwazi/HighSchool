package com.gaoxiaobang.community.service.notice;

import com.gaoxiaobang.community.entity.Notice;

import java.util.List;

public interface NoticeService {
    int getUnReadNoticeCount(int targetid);
    int deleteByPrimaryKey(Integer id);

    int insert(Notice record);

    Notice selectByPrimaryKey(Integer id);

    List<Notice> selectAll();

    int updateByPrimaryKey(Notice record);
    List<Notice> selectNoticeList(int targetid);
    int updateStatue(int id,int statue);
}
