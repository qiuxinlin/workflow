package com.scxys.activiti.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scxys.activiti.bean.ActProcess;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月4日 下午2:38:49 
* @description 说明:
*/
public interface ActProcessDao extends JpaRepository<ActProcess, Long>{
	@Query(value="select * from act_process where process_code=0",nativeQuery=true)
	public List<ActProcess> findRoot();
	@Query(value="select * from act_process where parent_code=?1",nativeQuery=true)
	public List<ActProcess> findChildren(String parent_code);
	@Query(value="select * from act_process where process_code=?1",nativeQuery=true)
	public ActProcess findProcessByCode(String processCode);
}
 