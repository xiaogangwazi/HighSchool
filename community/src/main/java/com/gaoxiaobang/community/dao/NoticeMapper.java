package com.gaoxiaobang.community.dao;

import com.gaoxiaobang.community.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface NoticeMapper {
    int updateStatue(@Param("id") int id, @Param("statue") int statue);
    int deleteByPrimaryKey(Integer id);
    List<Notice> selectNoticeList(int targetid);
    int insert(Notice record);

    Notice selectByPrimaryKey(Integer id);

    List<Notice> selectAll();

    int updateByPrimaryKey(Notice record);
    int getUnReadNoticeCount(int targetid);
}