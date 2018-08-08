package com.scxys.activiti.taskListener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @Author: qiuxinlin
 * @Dercription:
 * @Date: 17:01 2018/8/6
 */
public class TestCreateTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String name=delegateTask.getName();
        String description=delegateTask.getDescription();
        String assignee=delegateTask.getAssignee();
        String processInstanceId=delegateTask.getProcessInstanceId();
        System.out.println("name:"+name+"description:"+description+"assignee:"+assignee+"processInstanceId:"+processInstanceId);

        delegateTask.setAssignee("张三");
    }
}
