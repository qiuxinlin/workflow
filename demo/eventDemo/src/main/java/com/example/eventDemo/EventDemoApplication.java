package com.example.eventDemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by pengyingzhi on 2017/3/27.
 */
@SpringBootApplication
public class EventDemoApplication {
    public static void main(String[] args){
        SpringApplication.run(EventDemoApplication.class, args);
    }
}
