package com.gaoxiaobang.community.controller;

import com.gaoxiaobang.community.common.ResponseType;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Log
public class GlobalExceptionController {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(Exception ex){
      log.info(ex.getMessage());
    return ResponseType.responseType_500();
    }
}

