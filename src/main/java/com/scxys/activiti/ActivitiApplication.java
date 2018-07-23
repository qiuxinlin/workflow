package com.scxys.activiti;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import com.scxys.activiti.interceptor.JsonpCallbackFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
@Controller
@EntityScan({"org.activiti","com.scxys"})
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "businessProcessManager/html/process-list";
    }
    /**
     * @Author: qiuxinlin
     * @Dercription: 集成Diagram Viewer追踪流程图
     * @Date: 2017/7/13
     */
    @Bean
    public FilterRegistrationBean indexFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new JsonpCallbackFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }
}
