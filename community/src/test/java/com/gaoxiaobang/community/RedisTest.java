package com.gaoxiaobang.community;

import com.gaoxiaobang.community.common.util.CommunityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Test
    public void test(){
        String s = CommunityUtil.generatorUUID()+1;
        String s1 = CommunityUtil.md5(s);
        HashMap<String,Object>  hashMap = new HashMap<>();
        hashMap.put("login",true);
        redisTemplate.opsForValue().set(s1,"true",3600);

        Object token = redisTemplate.opsForValue().get(s1);

    }
}
