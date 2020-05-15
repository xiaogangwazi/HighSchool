package com.gaoxiaobang.community.service.mail.impl;

import com.gaoxiaobang.community.entity.Mail;
import com.gaoxiaobang.community.service.mail.MailService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.FileTypeMap;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Log
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${server.address}")
    private String address;
    @Value("${server.port}")
    private Integer port;
    private String text= "Click on me to complete registration";
    @Override
    public void sendHtmlMail(Mail mail) throws  Exception{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

            if(!StringUtils.isBlank(mail.getFrom())) {
                helper.setFrom(mail.getFrom());
            }else {
                helper.setFrom(username);
            }
            String msg= "感谢注册高校帮,";
            String link= "<a href=\"http://"+address+":"+port+"/register?id="+mail.getUserId()+"\""+">"+text+"</a>";
            helper.setTo(mail.getTo());
            helper.setText(msg+link,true);
            helper.setSubject(mail.getSubject());
            helper.setSentDate(mail.getSentDate());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
           log.warning("邮件发送失败"+e.getMessage());
           throw e;
        }

    }
}
