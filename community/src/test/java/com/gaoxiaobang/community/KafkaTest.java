package com.gaoxiaobang.community;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = CommunityApplication.class )
public class KafkaTest {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void test(){
       kafkaTemplate.send("test","hello world");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
@Component
class Consumer{
    @KafkaListener(topics = "test")
    public void consumer(ConsumerRecord consumerRecord){
        System.out.println(consumerRecord.topic()+":"+consumerRecord.value());
    }
}
