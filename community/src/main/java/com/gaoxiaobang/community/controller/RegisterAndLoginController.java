package com.gaoxiaobang.community.controller;

import com.gaoxiaobang.community.common.util.CommunityUtil;
import com.gaoxiaobang.community.common.RedisKeys;
import com.gaoxiaobang.community.common.ResponseType;
import com.gaoxiaobang.community.entity.Mail;
import com.gaoxiaobang.community.entity.UserPassword;
import com.gaoxiaobang.community.entity.Userinfo;
import com.gaoxiaobang.community.kafka.EventType;
import com.gaoxiaobang.community.kafka.KafkaProductor;
import com.gaoxiaobang.community.kafka.MailExecute;
import com.gaoxiaobang.community.requestEntity.LoginEntity;
import com.gaoxiaobang.community.requestEntity.RegisterEntity;
import com.gaoxiaobang.community.service.UserPassword.UserPasswordService;
import com.gaoxiaobang.community.service.mail.MailService;
import com.gaoxiaobang.community.service.userinfo.UserInfoService;
import com.jcraft.jsch.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;

@Controller
public class RegisterAndLoginController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserPasswordService userPasswordService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private KafkaProductor kafkaProductor;

    /**
     * 功能：注册
     * 步骤：
     * 1.验证邮箱格式
     * 2.验证邮箱和用户名是否存在
     *3.添加用户信息，用户表中注册字段statue为0未注册。
     * 4.立即返回客户注册成功结果，提醒用户查看邮件。
     * 5.将邮件发送事件提交到kafka中异步执行，当发送邮件成功之后在redis中存储forregister:id=键表示注册信息，暂时不设置键的失效时间。
     *
     * @param request
     * @param registerEntity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Object register(HttpServletRequest request, RegisterEntity registerEntity){
        Userinfo userinfo2 = userInfoService.selectByNickname(registerEntity.getName());

        if(userinfo2!=null){
            return ResponseType.responseType_1005();
        }
        Userinfo userinfo1 = userInfoService.selectByEmail(registerEntity.getEmail());

        if(userinfo1!=null){
            return ResponseType.responseType_1006();
        }
        Userinfo userinfo = new Userinfo();
        userinfo.setNickname(registerEntity.getName());
        userinfo.setEmail(registerEntity.getEmail());
        String salt = CommunityUtil.generatorUUID4();
        userinfo.setScore(100);
        userinfo.setCreateTime(new Date());
        userInfoService.insert(userinfo);
        UserPassword userPassword= new UserPassword();
        userPassword.setUserid(userinfo.getId());
        userPassword.setSalt(salt);
        userPassword.setPassword(CommunityUtil.md5(registerEntity.getPassword()+salt));
        userPasswordService.insert(userPassword);

        Mail mail = new Mail();
        mail.setTo(userinfo.getEmail());
        mail.setSubject("高校帮注册");
        mail.setSentDate(new Date());
        MailExecute mailExecute = new MailExecute(mail, EventType.MAIL);
        mailExecute.onSuccess((r)->{
            System.out.println("注册邮件已发送");
            Userinfo userInfo = (Userinfo)r;
            redisTemplate.opsForValue().append(RedisKeys.REGISTER_KEY+userInfo.getId(),"0");
        });
        mailExecute.onError(error->{
            System.out.println("邮件发送错误："+error);
        });
        kafkaProductor.mailSend(mailExecute,EventType.MAIL);
        ResponseType responseType = new ResponseType(200,"ok");
        return responseType;
    }

    /**
     * 功能：完成注册验证
     * 详情：客户通过访问邮箱中的链接完成注册，链接中带有参数id表示用户id
     * 步骤：
     * 1.获取redis中的注册信息，将注册string的value设置为"1"表示完成注册
     * 2.将用户表中的statue字段设置为1
     * 3,返回注册成功页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String registerConfirm(@RequestParam("id") Integer id){
        String value = (String) redisTemplate.opsForValue().get(RedisKeys.REGISTER_KEY + id);
        if(value!=null&&value=="0") {
            Userinfo userinfo = new Userinfo();
            userinfo.setStatue((byte) 1);
            userinfo.setId(id);
            userInfoService.updateStatueByPrimaryKey(userinfo);
            redisTemplate.opsForValue().set(RedisKeys.REGISTER_KEY+id,"1");
        }
        return "registerSuccess.html";
    }

    /**
     * 功能：登陆
     * 步骤：验证用户邮箱格式，验证密码
     * 返回登陆token，userinfo 并且存入reids中
     * @param loginEntity
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(LoginEntity loginEntity, HttpServletResponse response) {
            if (!StringUtils.isBlank(loginEntity.getLoginType())) {
                if (loginEntity.getLoginType().equals("2")) {//邮箱登陆
                    if(!StringUtils.isBlank(loginEntity.getName())) {
                        boolean b = CommunityUtil.valitationEmail(loginEntity.getName());
                        if (b == false) {

                            return ResponseType.responseType_1003();
                        } else {
                            Userinfo userinfo = userInfoService.selectByEmail(loginEntity.getName());

                            if (userinfo != null) {
                                UserPassword userPassword = userPasswordService.selectByUserId(userinfo.getId());
                                if (userPassword != null && CommunityUtil.md5( loginEntity.getPassword()+userPassword.getSalt()).equals(userPassword.getPassword())) {
                                    String s = CommunityUtil.generatorUUID();
                                    String s1 = CommunityUtil.md5(userinfo.getId() + userinfo.getNickname() + s);
                                    redisTemplate.opsForValue().set( s1, "true",3600 * 30 * 24);//缓存一个月
                                    HashMap<String, Object> result = new HashMap<>();
                                    result.put("token", s1);
                                    result.put("user",userinfo);
                                    ResponseType responseType = ResponseType.responseType_200();
                                    responseType.setResult(result);
                                    return responseType;
                                } else {
                                    return ResponseType.responseType_1001();
                                }
                            } else {
                                return ResponseType.responseType_1002();
                            }
                        }
                    }else {
                        return ResponseType.responseType_1007();
                    }
                } else {//用户名登陆
                    if (!StringUtils.isBlank(loginEntity.getName())) {
                        Userinfo userinfo = userInfoService.selectByNickname(loginEntity.getName());
                        if (userinfo != null) {
                            UserPassword userPassword = userPasswordService.selectByUserId(userinfo.getId());
                            if (userPassword != null && CommunityUtil.md5( loginEntity.getPassword()+userPassword.getSalt()).equals(userPassword.getPassword())) {
                                String s = CommunityUtil.generatorUUID();
                                String s1 = CommunityUtil.md5(userinfo.getId() + userinfo.getNickname() + s);
                                try {
                                    redisTemplate.opsForValue().set(s1, "true", 3600 * 30 * 24);//缓存一个月
                                }catch (Exception e){
                                    System.out.println("出错了"+e);
                                }
                                HashMap<String, Object> result = new HashMap<>();
                                   result.put("token", s1);
                                    result.put("user", userinfo);
                                ResponseType responseType = ResponseType.responseType_200();
                                responseType.setResult(result);
                                return responseType;
                            } else {
                                return ResponseType.responseType_1001();
                            }
                        } else {
                            return ResponseType.responseType_1001();
                        }
                    } else {
                        return ResponseType.responseType_1004();
                    }
                }
            }
        return  new ResponseType(500,"fail");
    }




}
