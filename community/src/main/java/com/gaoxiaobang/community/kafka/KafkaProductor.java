package com.gaoxiaobang.community.kafka;

import com.gaoxiaobang.community.common.util.JSONUtil;
import com.gaoxiaobang.community.entity.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.mail.event.MailEvent;

@Service
public class KafkaProductor {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void mailSend(MailExecute mailExecute ,String eventType){
        kafkaTemplate.send(eventType,mailExecute.getMail().getTo());
    }
    public void uplaod(UploadHeadExecute uploadHeadExecute,String eventType){
        kafkaTemplate.send(eventType,uploadHeadExecute.getFileDir());
    }

}
