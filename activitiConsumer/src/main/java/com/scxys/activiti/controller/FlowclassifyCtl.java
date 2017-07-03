package com.scxys.activiti.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.scxys.activiti.bean.Flowclassify;
import com.scxys.activiti.service.FlowclassifyService;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月3日 下午2:50:18 
* @description 说明:
*/
@RestController
public class FlowclassifyCtl {

	@Reference(version="1.0.0")
	FlowclassifyService flowclassifyService;
	
	@RequestMapping(value="/flowclassify", method = RequestMethod.POST, produces = "application/json")
	public void addFlowclassify(@RequestBody Flowclassify flowclassify, HttpServletRequest request, HttpServletResponse response){
		flowclassifyService.addFlowclassify(flowclassify);
	}
	
	@RequestMapping(value="/flowclassify", method=RequestMethod.GET, produces="application/json")
	public List<Flowclassify> getFlowclassify(){
		List<Flowclassify> list=flowclassifyService.findAll();
		return list;
	}
}
 