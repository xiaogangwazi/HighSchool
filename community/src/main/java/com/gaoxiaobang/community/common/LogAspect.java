package com.gaoxiaobang.community.common;

import lombok.extern.java.Log;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.transaction.managed.ManagedTransaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Aspect
@Component
@Log
public class LogAspect   {
   @Around("@within(Test)&&within(com.gaoxiaobang.community.service..*)")
   public Object verifyRoleExecuteCommand(ProceedingJoinPoint pjp) throws Throwable {
       // 获取当前拦截方法的对象
      System.out.println("哈哈");

       return pjp.proceed();// 执行方法
   }







}
