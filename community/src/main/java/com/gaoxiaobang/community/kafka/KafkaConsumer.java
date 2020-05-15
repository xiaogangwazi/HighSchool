package com.gaoxiaobang.community.kafka;

import com.gaoxiaobang.community.common.util.JSONUtil;
import com.gaoxiaobang.community.service.comments.CommentsService;
import com.gaoxiaobang.community.service.message.MessageService;
import com.gaoxiaobang.community.service.uploadHead.UploadHeadService;
import lombok.extern.java.Log;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Service;

@Service
@Log
public class KafkaConsumer {
    @Autowired
    private UploadHeadService uploadHeadService;

    @KafkaListener(topics = {"message","comment","like"})//message表示回复了你的帖子，comment表示回复了你的评论，like表示点赞
    public void messageListener(ConsumerRecord<?,?> consumerRecord){
        if (consumerRecord.value()==null){
            log.warning("主题内容为空");

            return;
        }

    }

    /**
     * 处理邮件发送事件
     * @param consumerRecord
     */
    @KafkaListener(topics =EventType.MAIL)
    public void MailSend(ConsumerRecord consumerRecord){
        if(consumerRecord.value()==null){
            log.warning("主题内容为空！");
            return;
        }
        String key = (String) consumerRecord.topic();
        if(!key.equals(EventType.MAIL)){
            log.warning("主题错误！");
            return;
        }
        MailExecute mailExecute =  JSONUtil.Parse((String)consumerRecord.value(),MailExecute.class);
            mailExecute.execute();
    }
    @KafkaListener(topics ={EventType.UPLAOD},properties = {"max.poll.interval.ms:30000"})
    public void upload(ConsumerRecord consumerRecord){
        log.info("接收到消息["+consumerRecord.topic()+"]:"+consumerRecord.value());
        if(consumerRecord.value()==null){
            log.warning("主题内容为空！");
            return;
        }
        String key = (String) consumerRecord.topic();
        if(!key.equals(EventType.UPLAOD)){
            log.warning("主题错误！");
            return;
        }
      String val= (String) consumerRecord.value();
        Execute execute = DefaultExecute.getExecute(val);
        if(execute!=null) {
            execute.execute();
        }
    }




}
