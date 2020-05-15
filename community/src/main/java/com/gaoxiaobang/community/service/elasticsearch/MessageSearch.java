package com.gaoxiaobang.community.service.elasticsearch;

import com.gaoxiaobang.community.dao.elasticsearch.MessageRepo;
import com.gaoxiaobang.community.entity.Message;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Repository
public class MessageSearch {
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    RedisTemplate redisTemplate;

    public Page<Message> queryByUuser_Schoolname(String content,int page,int size){
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(QueryBuilders.multiMatchQuery(content, "schoolname"))
                .withSort(SortBuilders.fieldSort("publictime"))
                .withSort(SortBuilders.fieldSort("see"))
                .withSort(SortBuilders.fieldSort("like"))
                .withPageable(PageRequest.of(page, size));
            nativeSearchQueryBuilder.withHighlightFields(new HighlightBuilder.Field("schoolname").preTags("<em style:'color:red'>").postTags("</em>"));
        NativeSearchQuery build = nativeSearchQueryBuilder.build();
        return messageRepo.search(build);
    }
    public Page<Message> search(String content,int page,int size){
            redisTemplate.opsForZSet().incrementScore("searchkey",content,1);
        SearchQuery searchQuery = generateSearchQuery(content,page,size,"title","content","topicname");
       return messageRepo.search(searchQuery);
    }
    public Set<String> searchSet(int size){

        Set searchkey = redisTemplate.opsForZSet().reverseRangeByScore("searchkey", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, size);
        return searchkey;
    }
    public SearchQuery generateSearchQuery(String content,int page,int size,String... fieldNames){
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(QueryBuilders.multiMatchQuery(content, fieldNames))
                .withSort(SortBuilders.fieldSort("see"))
                .withSort(SortBuilders.fieldSort("like"))
                .withSort(SortBuilders.fieldSort("publictime"))
                .withPageable(PageRequest.of(page, size));
               for(String f:fieldNames){
                   nativeSearchQueryBuilder.withHighlightFields(new HighlightBuilder.Field(f).preTags("<em style:'color:red'>").postTags("</em>"));
               }
               return nativeSearchQueryBuilder.build();
    }
    public void add(Message message){
            messageRepo.save(message);
    }
    public void delete(Message message){
        messageRepo.delete(message);
    }
}
