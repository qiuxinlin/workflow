package com.scxys.activiti.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.scxys.activiti.utils.SessionContext;
import com.scxys.bean.Employee;
import com.scxys.service.EmployeeService;

/** 
* @author 作者: qiuxinlin
* @version 创建时间:2017年3月24日 下午3:28:36 
* @description 说明:
*/
@Controller
@RequestMapping("/employ/")
public class EmployController {
	@Reference(version="1.0.0")
	EmployeeService employeeService;
	
	@RequestMapping(value="login/",method = RequestMethod.GET)
	public String login(Model model,HttpSession httpSession,String name){
		Employee employee=employeeService.findByName(name);
		System.out.println(employee.getName());
		//将查询的对象（惟一）放置到Session中
		SessionContext.setUser(employee,httpSession);
		return "main";
	}
	/**
	 * 标题
	 * @return
	 */
	@RequestMapping("top/")
	public String top(Model model,HttpSession httpSession) {
		Employee employee=(Employee)httpSession.getAttribute("globle_user");
		model.addAttribute("globle_user",employee);
		return "top";
	}
	
	/**
	 * 左侧菜单
	 * @return
	 */
	@RequestMapping("left/")
	public String left() {
		return "left";
	}
	
	/**
	 * 主页显示
	 * @return
	 */
	@RequestMapping("welcome/")
	public String welcome() {
		return "welcome";
	}
	
	/**退出系统*/
	@RequestMapping("logout/")
	public String logout(HttpSession httpSession){
		//清空Session
		SessionContext.setUser(null,httpSession);
		return "login";
	}
}
 