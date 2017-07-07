package com.scxys.activiti.rest.service.workflow.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.scxys.activiti.bean.ActFlowclassify;
import com.scxys.activiti.dao.ActFlowclassifyDao;
import com.scxys.activiti.service.ActFlowclassifyService;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月3日 下午2:05:40 
* @description 说明:
*/
@Service(version="1.0.0")
public class ActFlowclassifyServiceImpl implements ActFlowclassifyService{

	@Autowired
	ActFlowclassifyDao flowclassifyDao;
	@Override
	public List<ActFlowclassify> findRoot() {
		List<ActFlowclassify> list=flowclassifyDao.findRoot();
		return list;
	}

	
	@Override
	public List<ActFlowclassify> findAll() {
		List<ActFlowclassify> list=flowclassifyDao.findAll();
		return list;
	}


	@Override
	public List<ActFlowclassify> findChildrens(String parent_code) {
		List<ActFlowclassify> list=flowclassifyDao.findChildren(parent_code);
		return list;
	}

	@Override
	public void addFlowclassify(ActFlowclassify flowclassify) {
		flowclassifyDao.save(flowclassify);
	}

	@Override
	public void deleteFlowclassifyByCode(String classifyCode) {
		flowclassifyDao.deleteByClassifyCode(classifyCode);
	}

	@Override
	public void updateFlowclassify(ActFlowclassify actFlowclassify) {
		flowclassifyDao.save(actFlowclassify);
	}


	@Override
	public ActFlowclassify findByCode(String classifyCode) {
		return flowclassifyDao.findByCode(classifyCode);
	}

	
}
 