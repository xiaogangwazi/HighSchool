package com.gaoxiaobang.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableCaching()
@MapperScan(basePackages = "com.gaoxiaobang.community.dao")
public class CommunityApplication {
	@PostConstruct
	public void init(){
		//解决netty启动冲突的问题
		System.setProperty("es.set.netty.runtime.available.processors","false");
	}
	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}

}
