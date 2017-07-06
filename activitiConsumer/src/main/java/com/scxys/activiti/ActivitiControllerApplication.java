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

/**
 * Created by pengyingzhi on 2017/3/22.
 */
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
		System.out.println("----------");
		return "bpm/html/actFlowclassify";
	}

	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public String process() {
		return "bpm/html/actProcess";
	}

	@Bean
	public FilterRegistrationBean indexFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new JsonpCallbackFilter());
		registration.addUrlPatterns("/*");
		return registration;
	}
}
