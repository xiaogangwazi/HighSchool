package com.gaoxiaobang.community.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Comments implements Serializable {
    private List<Comments> commentList;
    private Userinfo user;
    private int replyto;
    private Integer id;
    private Integer messageid;

    private Integer userid;
    private Userinfo target;
    private String content;
    @JSONField(format="yyyy-MM-dd HH:mm:ss.SSS")
    private Date time;

    private Byte statue;
    private Byte type;
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMessageid() {
        return messageid;
    }

    public void setMessageid(Integer messageid) {
        this.messageid = messageid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Byte getStatue() {
        return statue;
    }

    public void setStatue(Byte statue) {
        this.statue = statue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", messageid=").append(messageid);
        sb.append(", userid=").append(userid);
        sb.append(", content=").append(content);
        sb.append(", time=").append(time);
        sb.append(", statue=").append(statue);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public int getReplyto() {
        return replyto;
    }

    public void setReplyto(int replyto) {
        this.replyto = replyto;
    }

    public Userinfo getUser() {
        return user;
    }

    public void setUser(Userinfo user) {
        this.user = user;
    }

    public List<Comments> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comments> commentList) {
        this.commentList = commentList;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Userinfo getTarget() {
        return target;
    }

    public void setTarget(Userinfo target) {
        this.target = target;
    }
}