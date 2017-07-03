package com.scxys.activiti.deviceException.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cmd.StartProcessInstanceByMessageCmd;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.scxys.activiti.bean.DeviceException;
import com.scxys.activiti.bean.WorkflowBean;
import com.scxys.activiti.deviceException.dao.DeviceExceptionDao;
import com.scxys.activiti.utils.SessionContext;

/**
 * @author 作者:qiuxinlin
 * @version 创建时间:2017年5月24日 下午1:10:49
 * @description 说明:
 */
@Controller
@RequestMapping("/deviceExceptionController/")
public class DeviceExceptionController {

	@Autowired
	DeviceExceptionDao deviceExceptionDao;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	ProcessEngine processEngine;

	@RequestMapping("home")
	public String home(Model model) {
		List<DeviceException> list = deviceExceptionDao.findAll();
		model.addAttribute("list", list);
		return "deviceException/list";
	}

	@RequestMapping("input")
	public String input(Model model, DeviceException deviceException) {
		Long id = deviceException.getId();
		// 修改
		if (id != null) {
			DeviceException dException = deviceExceptionDao.findOne(id);
			model.addAttribute("dException", dException);
			return "deviceException/input";
		}
		// 新增
		return "deviceException/input";
	}

	@RequestMapping("save")
	public String save(DeviceException deviceException) {
		deviceExceptionDao.save(deviceException);
		return "redirect:/deviceExceptionController/home";
	}

	@RequestMapping(value = "start/{businessId}")
	public String start(@PathVariable("businessId") String businessId) {
		String key = DeviceException.class.getSimpleName();
		System.out.println("--------------" + key);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("applyUserId", SessionContext.applyUserId);
		runtimeService.startProcessInstanceByKey(/*key*//*"leave"*/"testassignee", businessId, variables);
		return "redirect:/deviceExceptionController/listTask";
	}

	@RequestMapping(value="startByMessage/{messageName}")
	public String startByMessage(@PathVariable("messageName") String messageName) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("applyUserId", SessionContext.applyUserId);
		runtimeService.startProcessInstanceByMessage(messageName, variables);
		return "redirect:/deviceExceptionController/listTask";
	}
	
	@RequestMapping("listTask")
	public String listTask(Model model) {
		List<Task> list = taskService.createTaskQuery()//
				// .taskAssignee(SessionContext.applyUserId)// 指定个人任务查询
				.orderByTaskCreateTime().asc()// 升序
				.list();
		model.addAttribute("list", list);
		return "deviceException/task";
	}

	@RequestMapping(value = "findResource/{deploymentId}", method = RequestMethod.GET, produces = "text/xml")
	public String findResource(@PathVariable("deploymentId") String deploymentId, HttpServletResponse response)
			throws IOException {
		//Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
		ProcessDefinition processDefinition=repositoryService.
										createProcessDefinitionQuery().
										deploymentId(deploymentId).singleResult();
		String resourceName=processDefinition.getResourceName();
		//String resourceName = deployment.getName() + ".bpmn20.xml";
		// 获取资源文件表（act_ge_bytearray）中资源图片输入流InputStream
		InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
		// 从response对象获取输出流
		OutputStream out = response.getOutputStream();
		// 将输入流中的数据读取出来，写到输出流中
		for (int b = -1; (b = inputStream.read()) != -1;) {
			out.write(b);
		}
		out.close();
		inputStream.close();
		return null;
	}

	@RequestMapping("viewTaskForm")
	public String viewTaskForm(Model model, String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		// 4：使用流程实例对象获取BUSINESS_KEY
		String buniness_key = pi.getBusinessKey();
		DeviceException deviceException = deviceExceptionDao.findOne(Long.parseLong(buniness_key));
		model.addAttribute("dException", deviceException);
		model.addAttribute("taskId", taskId);
		return "deviceException/viewform";
	}

	@RequestMapping("submitTask")
	public String submitTask(String taskId, String result) {
		Map<String, Object> variables = new HashMap<>();
		variables.put("result", result);
		taskService.complete(taskId, variables);
		return "redirect:/deviceExceptionController/listTask";
	}

	@RequestMapping("claim")
	/** 拾取任务，将组任务分配给个人任务，指定任务的办理人 */
	public String claim(String taskId, String userId) {
		// 分配的个人任务（可以是组任务中的成员，也可以是非组任务的成员）
		taskService.claim(taskId, userId);
		System.out.println("分配任务成功");
		return "redirect:/deviceExceptionController/listTask";
	}
}
