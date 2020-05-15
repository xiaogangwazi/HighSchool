package com.gaoxiaobang.community.service.focus;

import com.gaoxiaobang.community.entity.Focustopic;

import java.util.List;

public interface FocusTopicService {
    int deleteByPrimaryKey(Integer id);

    int insert(Focustopic record);

    Focustopic selectByPrimaryKey(Integer id);

    List<Focustopic> selectAll();

    int updateByPrimaryKey(Focustopic record);
    int selectIfLikeTopic(int topicid, int userid);
    int selectIfHas(int topicid,int userid);
    int updateFocus(int topicid,int userid,int focus);
}
