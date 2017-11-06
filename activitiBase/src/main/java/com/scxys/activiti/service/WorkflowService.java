package com.scxys.activiti.service;

/**
 * @author 作者: 
 * @version 创建时间:2017年3月22日 下午1:55:09 
 * @description 说明:
 */
public interface WorkflowService { 
	/**
	 * @Author: qiuxinlin
	 * @Dercription: 任务委托
	 * @Date: 2017/8/29
	 */
	void delegateTasks(String afterDate,String beforeDate,String assignee,String delegateUser);
	/**
	 * @Author: qiuxinlin
	 * @Dercription: 部署流程
	 * @Date: 2017/8/29
	 */
	void deployment(String name,String diagramData,String svgData);
}
 