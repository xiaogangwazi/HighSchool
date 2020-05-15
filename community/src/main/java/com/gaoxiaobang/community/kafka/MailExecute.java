package com.gaoxiaobang.community.kafka;

import com.gaoxiaobang.community.entity.Mail;
import com.gaoxiaobang.community.service.mail.MailService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.function.Consumer;

/**
 * 邮件发送事件 需要传入发送的邮件实体对象和事件类型，可选择行的添加onError和onSuccess方法
 */
@Service
public class MailExecute extends DefaultExecute {
    @Autowired
    private MailService mailService;//邮件发送业务层实现
    private Mail mail;//邮件实体对象
    private String eventType;//事件类型
    public MailExecute(){
    }

    public MailExecute(Mail mail,String eventType){
        this.mail=mail;
        this.eventType=eventType;
    }
    @Override
    public void execute0() throws Exception {
        try {
            mailService.sendHtmlMail(mail);
        }catch (Exception e){
            throw e;
        }
    }

}
