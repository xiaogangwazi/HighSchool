package com.gaoxiaobang.community.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import sun.security.provider.MD2;
import sun.security.provider.MD5;

import java.util.UUID;

public class CommunityUtil {
    public static  String generatorUUID4(){
        return UUID.randomUUID().toString().substring(0,4).replaceAll("-","");
    }
    public static  String generatorUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String md5(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        String s = DigestUtils.md5DigestAsHex(key.getBytes());
        return s;

    }
    public static boolean valitationEmail(String email){
        String regp= "^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\\.)+(com|cn|net|org)$";
        return email.matches(regp);
    }


}
