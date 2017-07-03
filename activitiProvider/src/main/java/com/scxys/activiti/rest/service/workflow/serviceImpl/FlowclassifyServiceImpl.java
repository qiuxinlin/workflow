package com.scxys.activiti.rest.service.workflow.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.scxys.activiti.bean.Flowclassify;
import com.scxys.activiti.dao.FlowclassifyDao;
import com.scxys.activiti.service.FlowclassifyService;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月3日 下午2:05:40 
* @description 说明:
*/
@Service(version="1.0.0")
public class FlowclassifyServiceImpl implements FlowclassifyService{

	@Autowired
	FlowclassifyDao flowclassifyDao;
	@Override
	public List<Flowclassify> findRoot() {
		flowclassifyDao.findRoot();
		return null;
	}

	
	@Override
	public List<Flowclassify> findAll() {
		List<Flowclassify> list=flowclassifyDao.findAll();
		return list;
	}


	@Override
	public List<Flowclassify> findChildrens() {
		return null;
	}

	@Override
	public void addFlowclassify(Flowclassify flowclassify) {
		flowclassifyDao.save(flowclassify);
	}

	@Override
	public void deleteFlowclassifyById() {
		
	}

	@Override
	public void updateFlowclassifyById() {
		
	}

}
 