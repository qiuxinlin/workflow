package com.scxys.activiti.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.scxys.activiti.bean.ActDelegation;
import com.scxys.activiti.service.ActDelegationService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月17日 下午2:32:57 
* @description 说明:
*/
@RestController
public class ActDelegationCtl {

	@Reference(version="1.0.0")
	private
	ActDelegationService delegationService;
	
	@RequestMapping(value="/actDelegation", method = RequestMethod.POST, produces = "application/json")
	public void add(@RequestBody ActDelegation delegation, HttpServletRequest request, HttpServletResponse response){
		delegationService.add(delegation);
	}
	@RequestMapping(value="/actDelegation", method=RequestMethod.GET)
	public Map findAll(){
		List<ActDelegation> list=delegationService.findAll();
		Map map=new HashMap();
		map.put("data",list);
		map.put("total",list.size());
		return map;
	}
	@RequestMapping(value = "/actDelegationDel",method = RequestMethod.POST)
	public String delete(@RequestParam("selectStr") String selectStr){
		String[] array=selectStr.split("-");
		if(array.length>0){
			for (String anArray : array) {
				delegationService.deleteById(Long.parseLong(anArray));
			}
			return "删除成功";
		}
		return "删除失败";
	}
}
 