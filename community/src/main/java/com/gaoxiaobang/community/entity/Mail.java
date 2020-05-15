package com.gaoxiaobang.community.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
public class Mail implements Serializable {
    private int userId;
    private String subject;
    @Value("${spring.mail.username}")
    private String from;
    private String to;
    private String text;
    private Date sentDate;
    private String cc;
    private String scc;
    private String status;
    private String error;
    @JsonIgnore
    private MultipartFile[] multipartFiles;


}
