package com.example.casClientDemo;

import net.unicon.cas.client.configuration.EnableCasClient;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by pengyingzhi on 2017/4/13.
 */
@SpringBootApplication
@EnableCasClient
@RestController
public class CasClientDemoApplication {
    public static void main(String[] args){
        SpringApplication.run(CasClientDemoApplication.class,args);
    }

    @RequestMapping("/")
    public String hello(HttpServletRequest request){
            AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        return "hello "+principal.getName() +"\n"+principal.getAttributes().get("name")+"\n"+principal.getAttributes().get("id");
    }

}
