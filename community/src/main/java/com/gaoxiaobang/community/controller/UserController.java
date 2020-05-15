package com.gaoxiaobang.community.controller;

import com.gaoxiaobang.community.common.util.CommunityUtil;
import com.gaoxiaobang.community.common.ResponseType;
import com.gaoxiaobang.community.common.util.GitUtil;
import com.gaoxiaobang.community.entity.UserFocus;
import com.gaoxiaobang.community.entity.Userinfo;
import com.gaoxiaobang.community.kafka.EventType;
import com.gaoxiaobang.community.kafka.KafkaProductor;
import com.gaoxiaobang.community.kafka.UploadHeadExecute;
import com.gaoxiaobang.community.service.userinfo.UserInfoService;
import com.jcraft.jsch.UserInfo;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Log
@Controller
public class UserController {
    @Autowired
    private UploadHeadExecute uploadHeadExecute;
    @Autowired
    private UserInfoService userInfoService;
    @Value("${git.localDir}")
    private String dir;
    @Value("${gitpage.location}")
    private String gitpageLocation;
    @Autowired
    private KafkaProductor kafkaProductor;


    /**
     * 查询用户
     * @param name 用户名或者是邮箱号
     * @return
     */
    @ResponseBody
    @RequestMapping("/user/get")
    public Object getUser(@RequestParam("name") String name){
            if(StringUtils.isBlank(name)){
                return ResponseType.responseType_200();
            }else {
                Userinfo userinfo = null;
                if(CommunityUtil.valitationEmail(name)){
                    userinfo = userInfoService.selectByEmail(name);
                }else {
                     userinfo = userInfoService.selectByNickname(name);

                }

                ResponseType responseType = ResponseType.responseType_200();
                try {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("user", userinfo);
                    responseType.setResult(hashMap);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                return  responseType;
            }
    }

    /**
     * 修改用户头像,将图片上出提交消息队列并且立即返回执行成功
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/user/updateHeadUrl")
    public Object updateUserHeadUrl( @RequestParam("file") MultipartFile file,@RequestParam("id") String id){
        if(file!=null){
            int uid=Integer.valueOf(id);
            int i1 = file.getOriginalFilename().lastIndexOf(".");
            final String defaultDir= ".jpg";
            String substring = file.getOriginalFilename().substring(i1);
            File file1 = new File(dir+"\\"+id+substring);
            if(file1.exists()){
                file1.delete();
            }
            try {
                file.transferTo(new File(dir+"\\"+id+defaultDir));
            } catch (IOException e) {
               log.warning("头像图片存储错误！"+e.getMessage());
            }
            uploadHeadExecute.setFileDir(id+defaultDir);
            uploadHeadExecute.setUserId(Integer.valueOf(id));

                uploadHeadExecute.onSuccess((s)->{
                  userInfoService.updateByHeaderUrlPrimaryKey(uid,gitpageLocation+id+defaultDir);
              });
              uploadHeadExecute.onError((e)->{
                  log.warning("上传失败："+e);
              });
              uploadHeadExecute.addExecute(id+substring,uploadHeadExecute);
              kafkaProductor.uplaod(uploadHeadExecute, EventType.UPLAOD);
                ResponseType responseType = ResponseType.responseType_200();
                HashMap<String,Object> hashMap = new HashMap<>();
                Userinfo userinfo = userInfoService.selectByPrimaryKey(uid);
                hashMap.put("user",userinfo);
                responseType.setResult(hashMap);
                return responseType;
        }
        return ResponseType.responseType_200();
    }

    /**
     * 修改用户信息
     * @param userinfo
     * @return
     */
    @RequestMapping("/user/update")
    @ResponseBody
    public Object updateUserinfo(Userinfo userinfo){
        try {
            if (userinfo != null && userinfo.getId() != null) {
                userInfoService.updateByPrimaryKey(userinfo);
            }
            return ResponseType.responseType_200();
        }catch (Exception e){
            log.warning("修改用户信息失败"+e.getMessage());
        }
        return ResponseType.responseType_500();
    }

    /**
     * 获取我关注的用户列表
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/user/focuslist")
    public Object getfoucusList(@RequestParam("id") String id){
                if(!StringUtils.isBlank(id)){
                    List<Userinfo> userFoci = userInfoService.selectFocusList(Integer.valueOf(id));
                    ResponseType responseType = ResponseType.responseType_200();
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("list",userFoci);
                    responseType.setResult(hashMap);
                    return responseType;
                }
                return ResponseType.responseType_200();
    }

    /**
     * 获取关注我的用户列表
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/user/focusedlist")
    public Object getfoucuedList(@RequestParam("id") String id){
        if(!StringUtils.isBlank(id)){
            List<UserInfo> userInfos = userInfoService.selectFocusedList(Integer.valueOf(id));

            ResponseType responseType = ResponseType.responseType_200();
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("list",userInfos);
            responseType.setResult(hashMap);
            return responseType;
        }
        return ResponseType.responseType_200();
    }








}
