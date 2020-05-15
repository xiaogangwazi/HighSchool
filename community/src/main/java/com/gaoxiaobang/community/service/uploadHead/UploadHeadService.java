package com.gaoxiaobang.community.service.uploadHead;

import com.gaoxiaobang.community.entity.Userinfo;

import java.io.File;

public interface UploadHeadService {
    void upload(String filedir, int userid) throws Exception;
}
