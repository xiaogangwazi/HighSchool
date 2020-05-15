package com.gaoxiaobang.community.service.websocket;

import com.gaoxiaobang.community.entity.Notice;
import com.gaoxiaobang.community.entity.Userinfo;

public class AddFriendNotice  {
    private Notice notice;
    private Userinfo fromuser;

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public Userinfo getFromuser() {
        return fromuser;
    }

    public void setFromuser(Userinfo fromuser) {
        this.fromuser = fromuser;
    }
}
