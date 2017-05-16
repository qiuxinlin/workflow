package com.example.eventDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pengyingzhi on 2017/3/27.
 */
@RestController
public class DemoEventController {
    @Autowired
    DemoEventPublisher demoEventPublisher;
    @RequestMapping("/publish")
    public void publish(String msg){
        demoEventPublisher.publish(msg);
    }
}
