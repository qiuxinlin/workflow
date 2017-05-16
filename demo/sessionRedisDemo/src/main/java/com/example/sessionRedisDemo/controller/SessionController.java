package com.example.sessionRedisDemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pengyingzhi on 2017/3/28.
 */
@RestController
public class SessionController{
    @Value("${server.port}")
    String port;

    @RequestMapping("/set")
    public void setSession(HttpSession session,String name,String value){
        session.setAttribute(name,value);
    }

    @RequestMapping("/get")
    public Map getSession(HttpSession session){
        Enumeration<String> names = session.getAttributeNames();
        Map sessionMap = new HashMap();
        while (names.hasMoreElements()){
            String name = names.nextElement();
            sessionMap.put(name,session.getAttribute(name));
        }
        sessionMap.put("info",port);
        return sessionMap;

    }

}
