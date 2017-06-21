package com.scxys.activiti;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.scxys.activiti.interceptor.JsonpCallbackFilter;

/**
 * Created by pengyingzhi on 2017/3/20.
 */
@SpringBootApplication
@EntityScan({"com.scxys.bean","org.activiti"})
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }
    
    @Bean
    public FilterRegistrationBean indexFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new JsonpCallbackFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }
}
