package com.gaoxiaobang.community.entity;

import java.io.Serializable;

public class Friend implements Serializable {
    private int id;
    private Userinfo friend;
    private Userinfo user;
    private Integer targetid;
    private Integer userid;

    private Integer statue;

    private static final long serialVersionUID = 1L;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Userinfo getUserinfo() {
        return friend;
    }

    public void setUserinfo(Userinfo userinfo) {
        this.friend = userinfo;
    }

    public Integer getTargetid() {
        return targetid;
    }

    public void setTargetid(Integer targetid) {
        this.targetid = targetid;
    }

    public Integer getUserId() {
        return userid;
    }

    public void setUserId(Integer userId) {
        this.userid = userId;
    }

    public Integer getStatue() {
        return statue;
    }

    public void setStatue(Integer statue) {
        this.statue = statue;
    }


    public Userinfo getUser() {
        return user;
    }

    public void setUser(Userinfo user) {
        this.user = user;
    }
}