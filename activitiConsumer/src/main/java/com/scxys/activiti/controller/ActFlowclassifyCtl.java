package com.scxys.activiti.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.scxys.activiti.bean.ActFlowclassify;
import com.scxys.activiti.service.ActFlowclassifyService;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月3日 下午2:50:18 
* @description 说明:分类管理controller
*/
@RestController
public class ActFlowclassifyCtl {

	@Reference(version="1.0.0")
	ActFlowclassifyService flowclassifyService;
	
	@RequestMapping(value="/actFlowclassify", method = RequestMethod.POST, produces = "application/json")
	public void addFlowclassify(@RequestBody ActFlowclassify flowclassify, HttpServletRequest request, HttpServletResponse response){
		flowclassifyService.addFlowclassify(flowclassify);
	}
	
	@RequestMapping(value="/actFlowclassify", method = RequestMethod.PUT, produces = "application/json")
	public void updateFlowclassify(@RequestBody ActFlowclassify flowclassify, HttpServletRequest request, HttpServletResponse response){
		flowclassifyService.updateFlowclassify(flowclassify);
	}
	
	@RequestMapping(value="/actFlowclassify", method=RequestMethod.GET, produces="application/json")
	public List<Map<String, Object>> getFlowclassify(){
		//List<ActFlowclassify> list=flowclassifyService.findAll();
		List<ActFlowclassify> root=flowclassifyService.findRoot();
		//List<ActFlowclassify> childrens=flowclassifyService.findChildrens("0");
		List<Map<String, Object>> listMap=getChildrens(root);
		return listMap;
	}

	@DeleteMapping
	@RequestMapping(value="/actFlowclassify/{classifyCode}", method=RequestMethod.DELETE)
	public String deleteFlowclassifyByCode(@PathVariable("classifyCode") String classifyCode) {
		flowclassifyService.deleteFlowclassifyByCode(classifyCode);
		ActFlowclassify actFlowclassify=flowclassifyService.findByCode(classifyCode);
		if(actFlowclassify==null) {
			return "删除成功";
		}
		return "删除失败";
	}
	
	public List<Map<String, Object>> getChildrens(List<ActFlowclassify> root){
		List<Map<String , Object >> listMap=new ArrayList<>();
		for(ActFlowclassify actFlowclassify:root) {
			List<ActFlowclassify> childrens=flowclassifyService.findChildrens(actFlowclassify.getClassifyCode());
			Map<String, Object> temp=new HashMap<>();
			temp.put("id", actFlowclassify.getId());
			temp.put("classifyCode",actFlowclassify.getClassifyCode());
			temp.put("classifyName", actFlowclassify.getClassifyName());
			temp.put("parentCode", actFlowclassify.getParentCode());
			temp.put("describtion", actFlowclassify.getDescribtion());
			temp.put("fullPath", actFlowclassify.getFullPath());
			if(childrens.size()>0) {
				temp.put("children", getChildrens(childrens));
			}
			listMap.add(temp);
		}
		return listMap;
	}
}
 