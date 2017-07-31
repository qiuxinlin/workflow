package com.scxys.activiti.controller;

import java.util.List;
import javax.servlet.http.HttpSession;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.neoinfo.pojo.CommRes;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.dubbo.config.annotation.Reference;
import com.scxys.activiti.bean.LeaveBill;
import com.scxys.activiti.service.LeaveBillService;

@RestController
@RequestMapping("/leaveBillController/")
public class LeaveBillController {

	@Reference(version = "1.0.0")
	LeaveBillService leaveBillService;
	@Autowired
	RuntimeService runtimeService;

	@RequestMapping(value = "leaveBill",method = RequestMethod.GET)
	public CommRes<List<LeaveBill>> findAll(){
		//1：查询所有的请假信息（对应a_leavebill），返回List<LeaveBill>
		List<LeaveBill> list = leaveBillService.findLeaveBillList();
		if(list.size()==0){
			return  CommRes.errorRes("200","未查到相关信息");
		}
		return CommRes.success(list);
	}

	@RequestMapping(value = "leaveBill/{id}",method =RequestMethod.GET)
	public CommRes findById(@PathVariable("id") Long id){
		LeaveBill bill = leaveBillService.findLeaveBillById(id);
		if (bill==null){
			return CommRes.errorRes("200","未查到相关信息");
		}
		return CommRes.success(bill);
	}

	@RequestMapping(value = "leaveBill",method = RequestMethod.POST,produces = "application/json")
	public CommRes save(@RequestBody LeaveBill leaveBill, HttpSession httpSession) {
		if(leaveBill.getEndDate()==null){
			return CommRes.errorRes("400","请假结束日期不能为空");
		}
		if (leaveBill.getUser()==null||leaveBill.getUser().equals("")){
			return  CommRes.errorRes("400","请假人不能为空");
		}
		leaveBillService.saveLeaveBill(leaveBill);
		return CommRes.successRes();
	}

	@RequestMapping(value = "leaveBill",method = RequestMethod.DELETE)
	public CommRes<Object> delete(Long id){
		leaveBillService.deleteLeaveBillById(id);
		return CommRes.successRes();
	}
	
}
