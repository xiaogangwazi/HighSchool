package com.gaoxiaobang.community.config;

import com.gaoxiaobang.community.common.PagerInterceptor1;
import com.gaoxiaobang.community.common.util.GitUtil;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class Config extends WebMvcConfigurerAdapter {
    @Value("${git.username}")
    public  String username;
    @Value("${git.password}")
    private String password;
    @Value("${git.repository}")
    private String repository;
    @Value("${git.localDir}")
    private String dir;

    /**
     *
     * 注册分页拦截器
     * @return
     */
    @Autowired
    private LoginHandlerInterceptor loginHandlerInterceptor;
    @Bean
    public Interceptor pager(){
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/application.properties");
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PagerInterceptor1 interceptor1 = new PagerInterceptor1();
        interceptor1.setProperties(properties);
        return interceptor1;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("注册拦截器");
        registry.addInterceptor(loginHandlerInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    /**
     * 配置编程式事务对象，设置事务级别和传播行为
     * @return
     */
    @Bean
    public TransactionTemplate transactionTemplate(){
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(new DataSourceTransactionManager());
        transactionTemplate.setIsolationLevel(TransactionIsolationLevel.REPEATABLE_READ.getLevel());
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return transactionTemplate;
    }


    /**
     *
     * @return
     */
    @Bean("credentialsProvider")
    public CredentialsProvider createCredentialsProvider(){
      return   GitUtil.createCredential(username,password);
    }


    @Bean
    public CommonsMultipartResolver initCommonsMultipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(104857600);
        resolver.setMaxInMemorySize(4096);
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }


}
