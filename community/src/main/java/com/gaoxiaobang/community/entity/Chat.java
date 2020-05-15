package com.gaoxiaobang.community.entity;

import java.io.Serializable;
import java.util.Date;

public class Chat implements Serializable {
    private int step;
    private Userinfo fromuser;
    private Userinfo touser;
    private Integer id;

    private Integer fromid;

    private Integer toid;

    private String conversationid;

    private String content;

    private Byte type;

    private String photo;

    private Date time;

    private Byte statue;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromid() {
        return fromid;
    }

    public void setFromid(Integer fromid) {
        this.fromid = fromid;
    }

    public Integer getToid() {
        return toid;
    }

    public void setToid(Integer toid) {
        this.toid = toid;
    }

    public String getConversationid() {
        return conversationid;
    }

    public void setConversationid(String conversationid) {
        this.conversationid = conversationid == null ? null : conversationid.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
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
        sb.append(", fromid=").append(fromid);
        sb.append(", toid=").append(toid);
        sb.append(", conversationid=").append(conversationid);
        sb.append(", content=").append(content);
        sb.append(", type=").append(type);
        sb.append(", photo=").append(photo);
        sb.append(", time=").append(time);
        sb.append(", statue=").append(statue);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public Userinfo getFromuser() {
        return fromuser;
    }

    public void setFromuser(Userinfo fromuser) {
        this.fromuser = fromuser;
    }

    public Userinfo getTouser() {
        return touser;
    }

    public void setTouser(Userinfo touser) {
        this.touser = touser;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}