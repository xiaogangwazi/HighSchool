package com.gaoxiaobang.community;

import com.gaoxiaobang.community.entity.Mail;
import com.gaoxiaobang.community.service.mail.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = CommunityApplication.class )
public class MailTest {
    @Autowired
    private MailService mailService;

    @Test
    public void testmail(){

    }



}
