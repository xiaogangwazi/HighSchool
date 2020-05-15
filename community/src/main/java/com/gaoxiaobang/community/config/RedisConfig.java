package com.gaoxiaobang.community.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaoxiaobang.community.config.redis.MyRedisCacheWriterCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

@Configuration

public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 配置CacheManager,设置RedisCacheWriter
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter myRedisCacheWriterCustomer = new MyRedisCacheWriterCustomer(redisConnectionFactory);

        return new RedisCacheManager(myRedisCacheWriterCustomer,getRedisCacheConfiguration());
    }

    /**
     * 配置redis configuration，
     * @return
     */
    @Bean
    public RedisCacheConfiguration getRedisCacheConfiguration(){
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()));
        redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
        return redisCacheConfiguration;
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        System.out.println("注册redisTemplate");
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
       objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }
   /* @Bean("myKeyGenerator")
    public KeyGenerator myKeyGenerator(){
        return new KeyGenerator() {
            private String prex="xgwz";
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(prex).append("=>");
                    stringBuffer.append(o.getClass().getName()).append(".").append(method.getName()).append("(");
                    for(Object o1:objects){
                        stringBuffer.append(o1.getClass().getName()).append(":"+o1).append(",");
                    }
                    stringBuffer.replace(stringBuffer.length()-1,stringBuffer.length(),")");
                    System.out.println("生成key："+stringBuffer.toString());
                return DigestUtils.sha1DigestAsHex(stringBuffer.toString());
            }
        };
    }*/


}
