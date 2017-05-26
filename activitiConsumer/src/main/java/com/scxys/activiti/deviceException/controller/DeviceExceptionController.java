package com.scxys.activiti.deviceException.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.scxys.activiti.bean.DeviceException;
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
			// 3：将请假单信息放置到栈顶，页面使用struts2的标签，支持表单回显
			// ValueContext.putValueStack(bill);
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

	@RequestMapping("start")
	public String start() {
		String key = DeviceException.class.getSimpleName();
		System.out.println("--------------" + key);

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("applyUserId", SessionContext.applyUserId);// 表示惟一用户
		runtimeService.startProcessInstanceByKey(key, variables);
		return "redirect:/deviceExceptionController/listTask";
	}

	@RequestMapping("listTask")
	public String listTask(Model model) {
		List<Task> list = taskService.createTaskQuery()//
				.taskAssignee(SessionContext.applyUserId)// 指定个人任务查询
				.orderByTaskCreateTime().asc()// 升序
				.list();
		model.addAttribute("list", list);
		return "deviceException/task";
	}
	@RequestMapping(value="findResource/{deploymentId}", method = RequestMethod.GET, produces = "text/xml")
	public String findResource(@PathVariable("deploymentId") String deploymentId, HttpServletResponse response)
			throws IOException {
		 Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
		 String resourceName=deployment.getName()+".bpmn20.xml";
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
	/*@RequestMapping(value="/repository/deployments/{deploymentId}/resources/**", method = RequestMethod.GET, produces = "application/json")
	  public DeploymentResourceResponse getDeploymentResource(@PathVariable("deploymentId") String deploymentId, 
	      HttpServletRequest request) {
	    
	    // Check if deployment exists
	    Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
	    if (deployment == null) {
	      throw new ActivitiObjectNotFoundException("Could not find a deployment with id '" + deploymentId + "'.");
	    }
	    
	    String pathInfo = request.getPathInfo();
	    String resourceName = pathInfo.replace("/repository/deployments/" + deploymentId + "/resources/", "");
	    
	    List<String> resourceList = repositoryService.getDeploymentResourceNames(deploymentId);
	    
	    if (resourceList.contains(resourceName)) {
	      // Build resource representation
	      DeploymentResourceResponse response = restResponseFactory.createDeploymentResourceResponse(deploymentId, resourceName, 
	          contentTypeResolver.resolveContentType(resourceName));
	      return response;
	      
	    } else {
	      // Resource not found in deployment
	      throw new ActivitiObjectNotFoundException("Could not find a resource with id '" + resourceName
	              + "' in deployment '" + deploymentId + "'.");
	    }
	  }*/
}
