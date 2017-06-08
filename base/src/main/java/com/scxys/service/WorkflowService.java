package com.scxys.service; 

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import com.scxys.bean.Employee;
import com.scxys.bean.LeaveBill;
import com.scxys.bean.WorkflowBean;

/** 
 * @author 作者: 
 * @version 创建时间:2017年3月22日 下午1:55:09 
 * @description 说明:
 */
public interface WorkflowService {

	/**
	 * 
	 * @author 作者: qiuxinlin
	 * @version 创建时间:2017年3月22日 下午2:01:32 
	 * @description 说明:部署流程
	 */
	 public void deploymentProcessDefinition(File file, String filename);
	 /**
	  * 
	  * @author 作者: qiuxinlin
	  * @version 创建时间:2017年3月22日 下午2:02:05 
	  * @description 说明:查询部署对象信息
	  */
	 public List<Deployment> findDeploymentList();
	 /**
	  * 
	  * @author 作者: qiuxinlin
	  * @version 创建时间:2017年3月22日 下午2:05:17 
	  * @description 说明:查询流程定义信息
	  */
	 public List<ProcessDefinition> findProcessDefinitionList();
	 /**
	  * 
	  * @author 作者: qiuxinlin
	  * @version 创建时间:2017年3月22日 下午2:05:21 
	  * @description 说明:使用部署对象ID和资源图片名称，查询流程图片
	  */
	 public InputStream findImageInputStream(String deploymentId,String imageName);
	 /**
	  * 
	  * @author 作者: qiuxinlin
	  * @version 创建时间:2017年3月22日 下午2:05:26 
	  * @description 说明:使用部署对象ID，删除流程定义
	  */
	 public void deleteProcessDefinitionByDeploymentId(String deploymentId);
	 /**
	  * 
	  * @author 作者: qiuxinlin
	  * @version 创建时间:2017年3月22日 下午2:05:31 
	  * @description 说明:使用当前用户名查询正在执行的任务表
	  */
	 public List<Task> findTaskListByName(String name);
	 /**
	  * 
	  * @author 作者: qiuxinlin
	  * @version 创建时间:2017年3月27日 下午2:57:06 
	  * @description 说明:启动请假流程
	  */
	 public void saveStartProcess(WorkflowBean workflowBean,Employee employee);
	String findTaskFormKeyByTaskId(String taskId);
	LeaveBill findLeaveBillByTaskId(String taskId);
	List<String> findOutComeListByTaskId(String taskId);
	void saveSubmitTask(WorkflowBean workflowBean,String userName);
	List<Comment> findCommentByTaskId(String taskId);
	List<Comment> findCommentByLeaveBillId(Long id);
	ProcessDefinition findProcessDefinitionByTaskId(String taskId);
	Map<String, Object> findCoordingByTask(String taskId);
	String findTaskName(String taskId);
}
 