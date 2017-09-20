package com.scxys.activiti.bean.commonBean;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Author: qiuxinlin
 * @Dercription:别的系统登陆过来的user
 * @Date: 15:40 2017/9/19
 */
@Component
public class User implements Serializable{
    private static final long serialVersionUID = 3235224868617135856L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
