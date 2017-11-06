package com.scxys.activiti.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.neoinfo.pojo.CommRes;
import com.scxys.activiti.bean.commonBean.User;
import com.scxys.activiti.service.ActDelegationService;
import com.scxys.activiti.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author 作者:qiuxinlin
 * @version 创建时间:2017年3月27日 下午2:00:46
 * @description 说明:
 */
@Controller
public class WorkflowController {

    @Reference(version = "1.0.0")
    private
    WorkflowService workflowService;
    @Reference(version = "1.0.0")
    private
    ActDelegationService delegationService;
    @Autowired User user;

    /**
     * @author 作者: qiuxinlin
     * @version 创建时间:2017年7月11日 下午3:16:23
     * @description 说明:全任务委托
     */
    @RequestMapping(value="/delegateTasks", method=RequestMethod.GET)
    @ResponseBody
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
    @ResponseBody
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
     * @Dercription: 获取从别的系统登陆过来的用户
     * @Date: 2017/9/19
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET )
    public String login(String userName){
        user.setName(userName);
        System.out.println(user.getName());
        return "redirect:/businessProcessManager/html/process-list.html";
    }
}
