package com.gaoxiaobang.community.service.websocket;

import com.gaoxiaobang.community.entity.Message;

public enum PushDateType {
    FRIEND_ADD(1,"friend_add"),
    MESSAGE(2,"message");
      PushDateType(int code,String name){
        this.code=code;
        this.name=name;
    }
    private int code;
      private String name;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
