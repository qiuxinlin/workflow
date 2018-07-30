package com.scxys.activiti.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.scxys.activiti.bean.ActDelegation;
import com.scxys.activiti.dao.ActDelegationDao;
import com.scxys.activiti.service.ActDelegationService;
import org.springframework.stereotype.Service;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月17日 下午2:18:50 
* @description 说明:
*/
@Service
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

		delegationDao.save(delegation);
	}

	@Override
	public void deleteById(long id) {
		delegationDao.delete(id);
	}
	@Override
	public void  updateStatus(long id){
		ActDelegation actDelegation=delegationDao.findOne(id);
		actDelegation.setStatus(1);
		delegationDao.saveAndFlush(actDelegation);
	}

}
 