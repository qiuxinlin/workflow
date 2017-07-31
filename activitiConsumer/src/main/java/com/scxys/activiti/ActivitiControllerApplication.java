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

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		System.out.println("----------");
		return "login";
	}

	@RequestMapping(value = "/bpm", method = RequestMethod.GET)
	public String bpm() {
		return "bpm/html/actFlowclassify";
	}

	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public String process() {
		return "bpm/html/actProcess";
	}

	@RequestMapping(value = "/userGroup", method = RequestMethod.GET)
	public String userGroup() {
		return "bpm/html/userGroup";
	}

	@RequestMapping(value = "/taskDelegate", method = RequestMethod.GET)
	public String taskDelegate() {
		return "bpm/html/task-entrust";
	}

	@RequestMapping(value = "/monitor", method = RequestMethod.GET)
	public String monitor() {
		return "bpm/html/monitor";
	}

	@RequestMapping(value = "/runDetail", method = RequestMethod.GET)
	public String runDetail() {
		return "bpm/html/run-detail";
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
