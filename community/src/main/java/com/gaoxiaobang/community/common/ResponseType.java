package com.gaoxiaobang.community.common;

import java.io.Serializable;

public class ResponseType implements Serializable {
    private int code;
    private String Msg;
    private Object result;
    public ResponseType(int code,String msg){
        this.code=code;
        this.Msg=msg;
    }
    public static ResponseType responseType_404(){
        return new ResponseType(404,"fail");
    }
    public static ResponseType responseType_500(){
        return new ResponseType(500,"fail");
    }
    public static ResponseType responseType_200(){
        return new ResponseType(200,"success");
    }
    public static ResponseType responseType_1001(){//用户不存在
        return new ResponseType(1001,"user not exist");
    }
    public static ResponseType responseType_1002(){//密码错误
        return new ResponseType(1002,"password not correct");
    }
    public static ResponseType responseType_1003(){//邮箱格式错误
        return new ResponseType(1003,"Mailbox format error");
    }
    public static ResponseType responseType_1004(){//y用户名为空
        return new ResponseType(1004,"Username is empty");
    }
    public static ResponseType responseType_1007(){//邮箱为空
        return new ResponseType(1007,"Mail is empty");
    }
    public static ResponseType responseType_1005(){//用户名已经存在
        return new ResponseType(1005,"username already exist");
    }
    public static ResponseType responseType_1006(){//邮箱已存在
        return new ResponseType(1006,"Mailbox already exist");
    }
    public static ResponseType responseType_1008(){
        return new ResponseType(1008,"chat message send fail.");
    }
    public static ResponseType responseType_1009(){
        return new ResponseType(1009,"current user has look for this message before.");
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }


}
