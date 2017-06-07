package com.scxys.activiti;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.scxys.activiti.interceptor.TestInterceptor;

/**
 * Created by pengyingzhi on 2017/3/20.
 */
@SpringBootApplication
@EntityScan({"com.scxys.bean","org.activiti"})
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }
}
