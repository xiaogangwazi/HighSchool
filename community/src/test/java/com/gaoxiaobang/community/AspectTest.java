package com.gaoxiaobang.community;

import com.gaoxiaobang.community.service.userinfo.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = CommunityApplication.class )
public class AspectTest {
    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void test(){
       userInfoService.selectFocusedList(22);
    }
}
