package com.scxys.activiti.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scxys.activiti.bean.Flowclassify;


/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月3日 下午1:53:43 
* @description 说明:
*/
@Repository
public interface FlowclassifyDao extends JpaRepository<Flowclassify, Long>{

	@Query(value="select * from act_flowclassify where classifyCode=0",nativeQuery=true)
	public List<Flowclassify> findRoot();
}
 