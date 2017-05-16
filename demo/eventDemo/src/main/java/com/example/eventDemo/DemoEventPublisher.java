package com.example.eventDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by pengyingzhi on 2017/3/27.
 */
@Component
public class DemoEventPublisher {
    @Autowired
    ApplicationContext applicationContext;
    public void publish(String msg){
        applicationContext.publishEvent(new DemoEvent(this,msg));
    }
}
