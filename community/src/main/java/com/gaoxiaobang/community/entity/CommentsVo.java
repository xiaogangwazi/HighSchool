package com.gaoxiaobang.community.entity;

import java.io.Serializable;
import java.util.List;

public class CommentsVo  implements Serializable {
    private Comments comments;
    private List<Comments> list;
    private int count;

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public List<Comments> getList() {
        return list;
    }

    public void setList(List<Comments> list) {
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }

    public void setCount() {
        this.count = list.size();
    }
}
