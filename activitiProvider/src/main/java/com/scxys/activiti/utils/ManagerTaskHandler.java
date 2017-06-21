package com.scxys.activiti.utils;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.scxys.service.EmployeeService;

/**
 * 员工经理任务分配
 *
 */
public class ManagerTaskHandler implements TaskListener {
	
	private static final long serialVersionUID = 1L;
	@Reference(version="1.0.0")
	EmployeeService employeeService;
	@Override
	public void notify(DelegateTask delegateTask) {
		
		System.out.println("-----------------进入监听器");
		//设置个人任务的办理人
		delegateTask.setAssignee(GloableVariable.nextAssigneeEm.getName());
		
	}

}
