package com.scxys.activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
@Controller
@EntityScan({ "com.scxys", "org.activiti" })
public class ActivitiControllerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ActivitiControllerApplication.class, args);
	}

	@RequestMapping(value = "/sortManager",method = RequestMethod.GET)
	public String sortManager(){
		return "businessProcessManager/sortManager";
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
