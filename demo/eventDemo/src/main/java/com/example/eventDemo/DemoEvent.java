package com.example.eventDemo;

import org.springframework.context.ApplicationEvent;

/**
 * Created by pengyingzhi on 2017/3/27.
 */
public class DemoEvent extends ApplicationEvent {
    private String msg;

    public DemoEvent(Object source,String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
