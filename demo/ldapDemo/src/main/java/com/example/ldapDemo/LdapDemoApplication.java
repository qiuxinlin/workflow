package com.example.ldapDemo;

import com.example.ldapDemo.domain.Person;
import com.example.ldapDemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by pengyingzhi on 2017/4/19.
 */
@SpringBootApplication
public class LdapDemoApplication {

    public static void main(String[] args){
        SpringApplication.run(LdapDemoApplication.class,args);
    }

}
