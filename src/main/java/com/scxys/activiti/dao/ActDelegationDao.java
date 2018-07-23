package com.scxys.activiti.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scxys.activiti.bean.ActDelegation;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月17日 下午2:13:05 
* @description 说明:全任务委托
*/
@Transactional
@Repository
public interface ActDelegationDao extends JpaRepository<ActDelegation, Long>{

	List<ActDelegation> findByOwner(String owner);
}
 