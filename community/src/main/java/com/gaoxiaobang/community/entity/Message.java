package com.gaoxiaobang.community.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@Document(indexName = "message",type ="_doc",shards = 5,replicas = 3)
public class Message implements Serializable {
    @Field(type = FieldType.Integer)
    private int see;
    @Field(type = FieldType.Integer)
    private int like;
    @Field(type = FieldType.Integer)
    private  int commentsCount;
    @Field(type = FieldType.Date)
    private Date publictime;
    private List<Comments> commentsList;

    private Userinfo user;
    @Id()
    private Integer id;
    @Field(type = FieldType.Integer)
    private Integer publicid;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String title;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String content;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String topicname;
    @Field(type = FieldType.Integer)
    private Integer topicid;
    @Field(type = FieldType.Integer)
    private Byte type;
    @Field(type = FieldType.Text)
    private String photo;
    @Field(type = FieldType.Integer)
    private Byte statue;

    private static final long serialVersionUID = 1L;


    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Date getPublictime() {
        return publictime;
    }

    public void setPublictime(Date publictime) {
        this.publictime = publictime;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public Userinfo getUser() {
        return user;
    }

    public void setUser(Userinfo user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPublicid() {
        return publicid;
    }

    public void setPublicid(Integer publicid) {
        this.publicid = publicid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }

    public Integer getTopicid() {
        return topicid;
    }

    public void setTopicid(Integer topicid) {
        this.topicid = topicid;
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
        this.photo = photo;
    }

    public Byte getStatue() {
        return statue;
    }

    public void setStatue(Byte statue) {
        this.statue = statue;
    }

    public int getSee() {
        return see;
    }

    public void setSee(int see) {
        this.see = see;
    }

    @Override
    public String toString() {
        return "Message{" +
                "see=" + see +
                ", like=" + like +
                ", commentsCount=" + commentsCount +
                ", publictime=" + publictime +
                ", commentsList=" + commentsList +
                ", user=" + user +
                ", id=" + id +
                ", publicid=" + publicid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", topicname='" + topicname + '\'' +
                ", topicid=" + topicid +
                ", type=" + type +
                ", photo='" + photo + '\'' +
                ", statue=" + statue +
                '}';
    }
}