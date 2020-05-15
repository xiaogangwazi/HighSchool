package com.gaoxiaobang.community.dao.elasticsearch;

import com.gaoxiaobang.community.common.Page;
import com.gaoxiaobang.community.entity.Message;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends  ElasticsearchRepository<Message,Integer>{
    Message queryMessageById(int id);
    int deleteById(int id);
}
