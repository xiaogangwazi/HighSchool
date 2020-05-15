package com.gaoxiaobang.community.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginHandlerInterceptor implements HandlerInterceptor {
        @Autowired
        private RedisTemplate redisTemplate;
        @Value("${login.interceptor.PathPatterns}")
        private String paths;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(request.getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        String pathInfo = request.getRequestURI();
        if(pathInfo.endsWith(".html")){
            return true;
        }
        for(String s:paths.split(",")){
            if(pathInfo.matches(s)){
                return true;
            }
        }
        String authorization = request.getHeader("Authorization");
        if(!StringUtils.isBlank(authorization)){
             if(!StringUtils.isBlank((String)redisTemplate.opsForValue().get(authorization))){
                 return true;
            }else {
                 response.setStatus(-1);
                 response.setStatus(-1,"token failure!");
                 return false;
             }

        }
        return false;
    }
}
