package com.gaoxiaobang.community.service.mail;

import com.gaoxiaobang.community.entity.Mail;
import org.springframework.beans.factory.annotation.Value;

public interface MailService {
     void sendHtmlMail(Mail mail) throws  Exception;

}
