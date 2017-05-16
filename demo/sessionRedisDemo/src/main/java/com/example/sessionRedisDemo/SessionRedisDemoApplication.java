package com.example.sessionRedisDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by pengyingzhi on 2017/3/22.
 */
@SpringBootApplication
public class SessionRedisDemoApplication {
    public static void main(String[] args){
        SpringApplication.run(SessionRedisDemoApplication.class, args);
    }
}
