package com.gaoxiaobang.community;

import com.gaoxiaobang.community.common.util.GitUtil;
import lombok.extern.java.Log;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GItTest {
    @Value("${git.localDir}")
    private String dir;
    @Autowired
    private CredentialsProvider credentialsProvider;
    @Test
    public void test(){

    }
    @Test
    public void test1(){
        Git open = null;
        try {
            open = Git.open(new File(dir));
            GitUtil.commit(open,new Date().toString()+":提交用户的头像",credentialsProvider);
            GitUtil.push(open,"master",credentialsProvider);
        } catch (Exception e) {
            log.warning("上传失败："+e);
        }
    }

}
