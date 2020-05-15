package com.gaoxiaobang.community.kafka;

import com.gaoxiaobang.community.service.uploadHead.UploadHeadService;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class UploadHeadExecute extends  DefaultExecute{
   @Autowired
   private  UploadHeadService uploadHeadService;
    private String fileDir;
    private int userId;
    public UploadHeadExecute(String fileDir,int userId){
        this.fileDir=fileDir;
        this.userId=userId;
    }
    public UploadHeadExecute(){

    }
    @Override
    public void execute0() throws Exception {
        try {
            uploadHeadService.upload(fileDir, userId);
        }catch (Exception e){
            throw  e;
        }
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
