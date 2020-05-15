package com.gaoxiaobang.community.service.uploadHead.impl;

import com.gaoxiaobang.community.common.util.GitUtil;
import com.gaoxiaobang.community.entity.Userinfo;
import com.gaoxiaobang.community.service.uploadHead.UploadHeadService;
import com.gaoxiaobang.community.service.userinfo.UserInfoService;
import lombok.extern.java.Log;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Log
@Service
public class UploadServiceImpl implements UploadHeadService {
    @Value("${git.localDir}")
    private String dir;
    @Value("${gitpage.location}")
    private String gitpageLocation;
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CredentialsProvider credentialsProvider;
    @Override
    public void upload(String  filedir, int userid) throws Exception{
        Git open = null;
        try {
            open = Git.open(new File(dir));
            GitUtil.commit(open,new Date().toString()+":提交用户"+userid+"的头像",credentialsProvider);
            GitUtil.push(open,"master",credentialsProvider);
            log.info("头像上传成功！");
           } catch (Exception e) {
            log.warning("上传失败："+e);
            throw e;
        }

    }
}
