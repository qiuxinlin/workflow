package com.scxys.activiti.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.scxys.activiti.bean.Employee;
import com.scxys.activiti.bean.LeaveBill;
import com.scxys.activiti.bean.WorkflowBean;
import com.scxys.activiti.loadserviceImpl.WorkflowLoadServiceImpl;
import com.scxys.activiti.service.ActDelegationService;
import com.scxys.activiti.service.LeaveBillService;
import com.scxys.activiti.service.WorkflowService;
import com.scxys.activiti.utils.SessionContext;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 作者:qiuxinlin
 * @version 创建时间:2017年3月27日 下午2:00:46
 * @description 说明:
 */
@RestController
//@RequestMapping("/workflowController/")
public class WorkflowController {

    @Reference(version = "1.0.0")
    WorkflowService workflowService;
    @Reference(version = "1.0.0")
    LeaveBillService leaveBillService;
    @Autowired
    WorkflowLoadServiceImpl updownLoadService;
    @Reference(version = "1.0.0")
    ActDelegationService delegationService;

    /**
     * @author 作者: qiuxinlin
     * @version 创建时间:2017年7月11日 下午3:16:23
     * @description 说明:全任务委托
     */
    @RequestMapping(value="/delegateTasks", method=RequestMethod.GET)
    public String delegateTasks(@RequestParam("id") String id, @RequestParam("startDate") String afterDate, @RequestParam("endDate") String beforeDate, @RequestParam("owner") String assignee, @RequestParam("assignee") String delegateUser) {
        if (afterDate.equals("") || afterDate == null) {
            return "请选择开始时间";
        }
        if (beforeDate.equals("") || beforeDate == null) {
            return "请选择结束时间";
        }
        if (delegateUser.equals("") || delegateUser == null) {
            return "请选择委托人";
        }
        workflowService.delegateTasks(afterDate, beforeDate, assignee, delegateUser);
        Long delegationId=Long.parseLong(id);
        delegationService.updateStatus(delegationId);
        return "委托成功";
    }

    /**
     * 部署管理首页显示
     *
     * @return
     */
    @RequestMapping("deployHome")
    public String deployHome(Model model) {
        // 1:查询部署对象信息，对应表（act_re_deployment）
        List<Deployment> depList = workflowService.findDeploymentList();
        // 2:查询流程定义的信息，对应表（act_re_procdef）
        List<ProcessDefinition> pdList = workflowService.findProcessDefinitionList();
        // 放置到上下文对象中
        // ValueContext.putValueContext("depList", depList);
        // ValueContext.putValueContext("pdList", pdList);
        model.addAttribute("depList", depList);
        model.addAttribute("pdList", pdList);
        return "workflow/workflow";
    }

    @RequestMapping(value = "getDeployments", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<DeploymentEntity> getDeployments() {
        // 查询部署对象信息，对应表（act_re_deployment）
        List<Deployment> depList = workflowService.findDeploymentList();
        List<DeploymentEntity> depEntityList = new ArrayList<DeploymentEntity>();
        for (Deployment deployment : depList) {
            DeploymentEntity deploymentEntity = (DeploymentEntity) deployment;
            deploymentEntity.setResources(null);
            depEntityList.add(deploymentEntity);
        }
        return depEntityList;
    }

    @RequestMapping("getProcdef")
    public String getProcdef(Model model) {
        List<ProcessDefinition> pdList = workflowService.findProcessDefinitionList();
        model.addAttribute("pdList", pdList);
        return "process/pages/process.html";
    }

    /**
     * 发布流程
     *
     * @return
     * @throws IOException
     */
    @RequestMapping("newdeploy")
    public String newdeploy(String filename, @RequestParam("file") MultipartFile file2) throws IOException {
        // 获取页面传递的值
        // 1：获取页面上传递的zip格式的文件，格式是File类型
        // File file = workflowBean.getFile();
        //System.out.println(file2.getSize());
        // System.out.println(file.length());
        // 文件名称
        // String filename = workflowBean.getFilename();
        // 完成部署
        updownLoadService.deploymentProcessDefinition(file2.getInputStream(), filename);
        return "redirect:/workflowController/deployHome";
    }

    /**
     * 删除部署信息
     */
    @RequestMapping("delDeployment")
    public String delDeployment(WorkflowBean workflowBean) {
        // 1：获取部署对象ID
        String deploymentId = workflowBean.getDeploymentId();
        // 2：使用部署对象ID，删除流程定义
        workflowService.deleteProcessDefinitionByDeploymentId(deploymentId);
        return "redirect:/workflowController/deployHome";
    }

    /**
     * 查看流程图
     *
     * @throws Exception
     */
    @RequestMapping("viewImage")
    public String viewImage(HttpServletResponse response, WorkflowBean workflowBean) throws Exception {
        // 1：获取页面传递的部署对象ID和资源图片名称
        // 部署对象ID
        String deploymentId = workflowBean.getDeploymentId();
        // 资源图片名称
        String imageName = workflowBean.getImageName();
        // 2：获取资源文件表（act_ge_bytearray）中资源图片输入流InputStream
        InputStream in = updownLoadService.findImageInputStream(deploymentId, imageName);
        // 3：从response对象获取输出流
        OutputStream out = response.getOutputStream();
        // 4：将输入流中的数据读取出来，写到输出流中
        for (int b = -1; (b = in.read()) != -1; ) {
            out.write(b);
        }
        out.close();
        in.close();
        // 将图写到页面上，用输出流写
        return null;
    }

    // 启动流程
    @RequestMapping("startProcess")
    public String startProcess(HttpSession httpSession, WorkflowBean workflowBean) {
        // 更新请假状态，启动流程实例，让启动的流程实例关联业务
        Employee employee = SessionContext.get(httpSession);
        workflowService.saveStartProcess(workflowBean, employee);
        return "redirect:/workflowController/listTask";
    }

    /**
     * 任务管理首页显示
     *
     * @return
     */
    @RequestMapping("listTask")
    public String listTask(Model model, HttpSession httpSession) {
        // 1：从Session中获取当前用户名
        String name = SessionContext.get(httpSession).getName();
        // 2：使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task>
        List<Task> list = workflowService.findTaskListByName(name);
        // ValueContext.putValueContext("list", list);
        model.addAttribute("list", list);
        return "workflow/task";
    }

    /**
     * 打开任务表单
     */
    @RequestMapping("viewTaskForm")
    public String viewTaskForm(Model model, WorkflowBean workflowBean) {
        // 任务ID
        String taskId = workflowBean.getTaskId();
        // 获取任务表单中任务节点的url连接
        String url = workflowService.findTaskFormKeyByTaskId(taskId);
        url += "?taskId=" + taskId;
        // ValueContext.putValueContext("url", url);
        model.addAttribute("url", url);
        return "redirect:/" + url;
    }

    // 准备表单数据
    @RequestMapping("audit")
    public String audit(Model model, WorkflowBean workflowBean) {
        // 获取任务ID
        String taskId = workflowBean.getTaskId();
        model.addAttribute("taskId", taskId);
        /** 一：使用任务ID，查找请假单ID，从而获取请假单信息 */
        LeaveBill leaveBill = workflowService.findLeaveBillByTaskId(taskId);
        // ValueContext.putValueStack(leaveBill);
        model.addAttribute("bill", leaveBill);
        /** 二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中 */
        List<String> outcomeList = workflowService.findOutComeListByTaskId(taskId);
        // ValueContext.putValueContext("outcomeList", outcomeList);
        model.addAttribute("outcomeList", outcomeList);
        /** 三：查询所有历史审核人的审核信息，帮助当前人完成审核，返回List<Comment> */
        List<Comment> commentList = workflowService.findCommentByTaskId(taskId);
        // ValueContext.putValueContext("commentList", commentList);
        model.addAttribute("commentList", commentList);
        return "workflow/taskForm";
    }

    /**
     * 提交任务
     */
    @RequestMapping("submitTask")
    public String submitTask(HttpSession httpSession, WorkflowBean workflowBean) {
        String userName = SessionContext.get(httpSession).getName();
        workflowService.saveSubmitTask(workflowBean, userName);
        return "redirect:/workflowController/listTask";
    }

    /**
     * 查看当前流程图（查看当前活动节点，并使用红色的框标注）
     */
    @RequestMapping("viewCurrentImage")
    public String viewCurrentImage(Model model, WorkflowBean workflowBean, RedirectAttributes attr) {
        // 任务ID
        String taskId = workflowBean.getTaskId();
        //一：查看流程图
        // 1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
        ProcessDefinition pd = workflowService.findProcessDefinitionByTaskId(taskId);
        // 二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中
        Map<String, Object> map = workflowService.findCoordingByTask(taskId);
        // ValueContext.putValueContext("acs", map);
        model.addAttribute("acs", map);
        String deploymentId = pd.getDeploymentId();
        String imageName = pd.getDiagramResourceName();
        model.addAttribute("deploymentId", deploymentId);
        model.addAttribute("imageName", imageName);

        //---------分隔符----------
        String taskName = workflowService.findTaskName(taskId);
        System.out.println("--------------------taskname=" + taskName);
        model.addAttribute("taskName", taskName);
        attr.addAttribute("taskName", taskName);
        return "redirect:../bpmn-js/index-async.html?deploymentId=" + deploymentId;
    }

    // 查看历史的批注信息
    @RequestMapping("viewHisComment")
    public String viewHisComment(WorkflowBean workflowBean) {
        // 获取清单ID
        Long id = workflowBean.getId();
        // 1：使用请假单ID，查询请假单对象，将对象放置到栈顶，支持表单回显
        LeaveBill leaveBill = leaveBillService.findLeaveBillById(id);
        // ValueContext.putValueStack(leaveBill);
        // 2：使用请假单ID，查询历史的批注信息
        List<Comment> commentList = workflowService.findCommentByLeaveBillId(id);
        // ValueContext.putValueContext("commentList", commentList);
        return "viewHisComment";
    }
}
