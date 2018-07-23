package com.scxys.activiti.service;

import java.util.List;

import com.scxys.activiti.bean.ActDelegation;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月17日 下午2:15:52 
* @description 说明:
*/
public interface ActDelegationService {

	List<ActDelegation> findAll();
	List<ActDelegation> findByOwner(String owner);
	void add(ActDelegation delegation);
	void deleteById(long id);
	void  updateStatus(long id);
}
 