package com.gaoxiaobang.community.common.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;

import java.util.List;

import static sun.plugin2.util.PojoUtil.toJson;

/**
 * json序列化反序列化工具类
 * 利用jackson将对象序列化
 * 利用fastjson反序列化json
 */
@Log
public class JSONUtil {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJsonString(Object object){
        String s=null;
        try {
             s = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.warning("序列化出错"+e.getMessage());
        }
        return s;
    }
    public static <T>T Parse(String json,Class<T> tClass){
        return JSON.parseObject(json,tClass);
    }
    public static <T>List<T> ParseList(String json,Class<T> tClass){
       return  JSON.parseArray(json,tClass);
    }


}

