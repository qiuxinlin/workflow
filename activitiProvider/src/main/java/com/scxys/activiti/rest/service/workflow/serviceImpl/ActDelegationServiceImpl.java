package com.scxys.activiti.rest.service.workflow.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.scxys.activiti.bean.ActDelegation;
import com.scxys.activiti.dao.ActDelegationDao;
import com.scxys.activiti.service.ActDelegationService;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月17日 下午2:18:50 
* @description 说明:
*/
@Service(version="1.0.0")
public class ActDelegationServiceImpl implements ActDelegationService{

	@Autowired
	ActDelegationDao delegationDao;
	@Override
	public List<ActDelegation> findAll() {
		List<ActDelegation> list=delegationDao.findAll();
		return list;
	}

	@Override
	public List<ActDelegation> findByOwner(String owner) {
		List<ActDelegation> list=delegationDao.findByOwner(owner);
		return list;
	}

	@Override
	public void add(ActDelegation delegation) {

		delegationDao.saveAndFlush(delegation);
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		
	}

}
 