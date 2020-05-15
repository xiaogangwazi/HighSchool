package com.gaoxiaobang.community.service.websocket;

import java.io.Serializable;
import java.util.HashMap;

public class PushDate implements Serializable {
    private String type;
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    private int userid;
    private HashMap<String,Object> data= new HashMap<>(5);

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public PushDate addDate(String key,Object date){
        this.data.put(key,date);
        return this;
    }
    public PushDate deleteDate(String key){
        this.data.remove(key);
        return this;
    }
}
