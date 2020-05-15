package com.gaoxiaobang.community;

import com.gaoxiaobang.community.dao.MessageMapper;
import com.gaoxiaobang.community.dao.elasticsearch.MessageRepo;
import com.gaoxiaobang.community.entity.Message;
import com.gaoxiaobang.community.service.elasticsearch.MessageSearch;
import com.gaoxiaobang.community.service.message.MessageService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = CommunityApplication.class )
public class ElasticsearchTest {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    MessageRepo messagePostRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private MessageSearch messageSearch;
    @Autowired
    private MessageRepo messageRepo;

    @Test
    public void testSave(){
        List<Message> messages = messageMapper.selectAll();
        messagePostRepository.saveAll(messages);
    }
    @Test
    public void testsearch(){

        Page<Message> messagePage = messageSearch.search("篮球", 0, 10);
        for(Message message:messagePage.getContent()){
            System.out.println(message.toString());
        }
        System.out.println("执行结束");
    }

}
