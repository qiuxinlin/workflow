package com.scxys.activiti.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.scxys.activiti.bean.ActProcess;
import com.scxys.activiti.service.ActProcessService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 作者:qiuxinlin
 * @version 创建时间:2017年7月4日 下午2:36:12
 * @description 说明:流程controller
 */
@RestController
public class ActProcessCtl {

	@Reference(version = "1.0.0")
	private
	ActProcessService actProcessService;

	@RequestMapping(value = "/actProcess", method = RequestMethod.POST, produces = "application/json")
	public void addProcess(@RequestBody ActProcess actProcess, HttpServletRequest request,
			HttpServletResponse response) {
		actProcessService.addProcess(actProcess);
	}

	@RequestMapping(value = "/actProcess", method = RequestMethod.PUT, produces = "application/json")
	public void updateProcess(@RequestBody ActProcess actProcess, HttpServletRequest request,
			HttpServletResponse response) {
		actProcessService.updateProcess(actProcess);
	}
	
	@RequestMapping(value = "/actProcess", method = RequestMethod.GET, produces = "application/json")
	public List<Map> getProcessTree(String pid) {
		return actProcessService.findTreeByPid(pid);
	}
	
	@RequestMapping(value = "/actProcess/{processCode}", method = RequestMethod.GET, produces = "application/json")
	public ActProcess getProcessByCode(@PathVariable("processCode") String processCode) {
		return actProcessService.findProcessByCode(processCode);
	}
	
	@RequestMapping(value = "/actProcess/{processCode}", method = RequestMethod.DELETE)
	public String deleteProcessByCode(@PathVariable("processCode") String processCode) {
		actProcessService.deleteProcessByCode(processCode);
		ActProcess actProcess=actProcessService.findProcessByCode(processCode);
		if(actProcess==null) {
			return "删除成功";
		}
		return "删除失败";
	}
}
