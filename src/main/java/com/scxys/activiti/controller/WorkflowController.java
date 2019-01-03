package com.scxys.activiti.controller;

import com.scxys.activiti.bean.ResponseVO;
import com.scxys.activiti.rest.service.api.runtime.task.TaskBaseResource;
import com.scxys.activiti.service.ActDelegationService;
import com.scxys.activiti.service.WorkflowService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 作者:qiuxinlin
 * @version 创建时间:2017年3月27日 下午2:00:46
 * @description 说明:
 */
@RestController
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private ActDelegationService delegationService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    /**
     * @author 作者: qiuxinlin
     * @version 创建时间:2017年7月11日 下午3:16:23
     * @description 说明:全任务委托
     */
    @RequestMapping(value = "/delegateTasks", method = RequestMethod.GET)
    public String delegateTasks(@RequestParam("id") String id, @RequestParam("startDate") String afterDate, @RequestParam("endDate") String beforeDate, @RequestParam("owner") String assignee, @RequestParam("assignee") String delegateUser) {
        if (afterDate == null || afterDate.isEmpty()) {
            return "请选择开始时间";
        }
        if (beforeDate == null || beforeDate.isEmpty()) {
            return "请选择结束时间";
        }
        if (delegateUser == null || delegateUser.isEmpty()) {
            return "请选择委托人";
        }
        workflowService.delegateTasks(afterDate, beforeDate, assignee, delegateUser);
        Long delegationId = Long.parseLong(id);
        delegationService.updateStatus(delegationId);
        return "委托成功";
    }

    /**
     * 发布流程
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/deployment", method = RequestMethod.POST, produces = "application/json")
    public ResponseVO deployment(String name, String diagramData, String svgData, String category) {
        if (name == null || name.isEmpty()) {
            return ResponseVO.errorRes(400, "部署名称为空");
        }
        if (diagramData == null || diagramData.isEmpty()) {
            return ResponseVO.errorRes(400, "流程图数据为空");
        }
        if (svgData == null || svgData.isEmpty()) {
            return ResponseVO.errorRes(400, "图片数据为空");
        }
        workflowService.deployment(name, diagramData, svgData, category);
        return ResponseVO.successRes();
    }

    /**
     * @Author: qiuxinlin
     * @Dercription: 获取流程定义中所有用户任务的候选人
     * @Date: 2018/7/31
     */
    @GetMapping(value="/candidateUsers/{processDefinitionId}", produces = "application/json")
    public Map getCandidateUsers(@PathVariable String processDefinitionId){
        Map result=new HashMap();
        BpmnModel bpmnModel=repositoryService.getBpmnModel(processDefinitionId);
        ArrayList<FlowElement> elements= (ArrayList) bpmnModel.getProcesses().get(0).getFlowElements();
        for(FlowElement flowElement:elements){
            if(flowElement instanceof UserTask){
                List candidateUsers=((UserTask) flowElement).getCandidateUsers();
                if(!candidateUsers.isEmpty()){
                    result.put(flowElement.getId(),((UserTask) flowElement).getCandidateUsers());
                }
            }
        }
        return result;
    }
}
