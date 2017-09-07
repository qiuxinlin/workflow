package com.scxys.activiti.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.neoinfo.pojo.CommRes;
import com.scxys.activiti.bean.WorkflowBean;
import com.scxys.activiti.loadserviceImpl.WorkflowLoadServiceImpl;
import com.scxys.activiti.service.ActDelegationService;
import com.scxys.activiti.service.WorkflowService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author 作者:qiuxinlin
 * @version 创建时间:2017年3月27日 下午2:00:46
 * @description 说明:
 */
@RestController
public class WorkflowController {

    @Reference(version = "1.0.0")
    WorkflowService workflowService;
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
        if (afterDate == null || afterDate.equals("") ) {
            return "请选择开始时间";
        }
        if (beforeDate == null || beforeDate.equals("")) {
            return "请选择结束时间";
        }
        if (delegateUser == null || delegateUser.equals("")) {
            return "请选择委托人";
        }
        workflowService.delegateTasks(afterDate, beforeDate, assignee, delegateUser);
        Long delegationId=Long.parseLong(id);
        delegationService.updateStatus(delegationId);
        return "委托成功";
    }

    /**
     * 发布流程
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/deployment",method = RequestMethod.POST ,produces = "application/json")
    public CommRes deployment(String name, String diagramData, String svgData ) {
        if(name==null||name.isEmpty()){
            return CommRes.errorRes("400","部署名称为空");
        }
        if(diagramData==null||diagramData.isEmpty()){
            return CommRes.errorRes("400","流程图数据为空");
        }
        if(svgData==null||svgData.isEmpty()){
            return CommRes.errorRes("400","图片数据为空");
        }
        workflowService.deployment(name,diagramData,svgData);
        return CommRes.successRes();
    }

    /**
     * @Author: qiuxinlin
     * @Dercription: 根据流程实例ID获取BusinessKey
     * @Date: 2017/9/7
     */
    @RequestMapping(value = "businessKey/{processInstanceId}",method = RequestMethod.GET)
    public CommRes findBusinessKeyByPiId(@PathVariable String processInstanceId){
        if(processInstanceId==null||processInstanceId.isEmpty()){
            return CommRes.errorRes("400","流程实例ID不能为空");
        }
        return CommRes.success(workflowService.findBusinessKeyByPiId(processInstanceId));
    }
}
