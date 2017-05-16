package com.scxys.activiti.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.annotation.SessionScope;

import com.alibaba.dubbo.config.annotation.Reference;
import com.scxys.activiti.utils.SessionContext;
import com.scxys.bean.Employee;
import com.scxys.bean.LeaveBill;
import com.scxys.service.LeaveBillService;

@Controller
@RequestMapping("/leaveBillController/")
public class LeaveBillController {

	@Reference(version = "1.0.0")
	LeaveBillService leaveBillService;
	
	/**
	 * 请假管理首页显示
	 */
	@RequestMapping("home")
	public String home(Model model){
		//1：查询所有的请假信息（对应a_leavebill），返回List<LeaveBill>
		List<LeaveBill> list = leaveBillService.findLeaveBillList(); 
		model.addAttribute("list", list);
		return "leaveBill/list";
	}
	
	/**
	 * 添加请假申请
	 * @return
	 */
	@RequestMapping("input")
	public String input(Model model,LeaveBill leaveBill){
		//1：获取请假单ID
		Long id = leaveBill.getId();
		//修改
		if(id!=null){
			//2：使用请假单ID，查询请假单信息，
			LeaveBill bill = leaveBillService.findLeaveBillById(id);
			//3：将请假单信息放置到栈顶，页面使用struts2的标签，支持表单回显
			//ValueContext.putValueStack(bill);
			model.addAttribute("bill", bill);
			return "leaveBill/input";
		}
		//新增
		return "leaveBill/input";
	}
	
	/**
	 * 保存/更新，请假申请
	 * 
	 */
	@RequestMapping("save")
	public String save(LeaveBill leaveBill,HttpSession httpSession) {
		//执行保存
		Employee employee=SessionContext.get(httpSession);
		System.out.println(employee.getName());
		leaveBillService.saveLeaveBill(leaveBill,employee);
		return "redirect:/leaveBillController/home";
	}
	
	/**
	 * 删除，请假申请
	 * 
	 */
	@RequestMapping("delete")
	public String delete(LeaveBill leaveBill){
		//1：获取请假单ID
		Long id = leaveBill.getId();
		//执行删除
		leaveBillService.deleteLeaveBillById(id);
		return "redirect:/leaveBillController/home";
	}
	
}
