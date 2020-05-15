package com.gaoxiaobang.community.service.notice.impl;

import com.gaoxiaobang.community.dao.NoticeMapper;
import com.gaoxiaobang.community.entity.Notice;
import com.gaoxiaobang.community.service.notice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeService;

    @Override
    public int getUnReadNoticeCount(int targetid) {
        return noticeService.getUnReadNoticeCount(targetid);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return noticeService.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Notice record) {
        return noticeService.insert(record);
    }

    @Override
    public Notice selectByPrimaryKey(Integer id) {
        return noticeService.selectByPrimaryKey(id);
    }

    @Override
    public List<Notice> selectAll() {
        return noticeService.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Notice record) {
        return noticeService.updateByPrimaryKey(record);
    }

    @Override
    public List<Notice> selectNoticeList(int targetid) {
        return noticeService.selectNoticeList(targetid);
    }

    @Override
    public int updateStatue(int id, int statue) {
        return noticeService.updateStatue(id,statue);
    }
}
