package com.scxys.activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * Created by pengyingzhi on 2017/3/22.
 */
@SpringBootApplication
@Controller
@EntityScan({"com.scxys.bean","com.scxys.activiti.bean"})//创建实体对应的数据库
public class ActivitiControllerApplication {
    public static void main(String[] args){
        SpringApplication.run(ActivitiControllerApplication.class, args);
    }
    
    @RequestMapping(value="/index",method=RequestMethod.GET)
    public String index(){
    	System.out.println("----------");
    	return "login";
    }
}
