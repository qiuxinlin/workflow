package com.example.eventDemo;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by pengyingzhi on 2017/3/27.
 */
@Component
public class DemoEventListener implements ApplicationListener<DemoEvent> {
    @Override
    public void onApplicationEvent(DemoEvent demoEvent) {
        String msg = demoEvent.getMsg();
        System.out.println("DemoEventListener:"+msg);
    }
}
