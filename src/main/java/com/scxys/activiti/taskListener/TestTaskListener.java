package com.scxys.activiti.taskListener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: qiuxinlin
 * @Dercription:
 * @Date: 16:20 2018/7/26
 */
@Configuration
public class TestTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("测试任务监听器");
    }
}
