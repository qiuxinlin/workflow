package com.scxys.activiti.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scxys.activiti.bean.ActFlowclassify;


/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月3日 下午1:53:43 
* @description 说明:
*/
@Transactional
@Repository
public interface ActFlowclassifyDao extends JpaRepository<ActFlowclassify, Long>{

	@Query(value="select * from act_flowclassify where classify_code=0",nativeQuery=true)
	public List<ActFlowclassify> findRoot();
	@Query(value="select * from act_flowclassify where parent_code=?1",nativeQuery=true)
	public List<ActFlowclassify> findChildren(String parent_code);
	@Query(value="select * from act_flowclassify where classify_code=?1",nativeQuery=true)
	public ActFlowclassify findByCode(String classifyCode);
	@Query(value="DELETE FROM act_flowclassify WHERE classify_code=?1",nativeQuery=true)
	public void deleteByCode(String classifyCode);
	void deleteByClassifyCode(String classifyCode);
}
 