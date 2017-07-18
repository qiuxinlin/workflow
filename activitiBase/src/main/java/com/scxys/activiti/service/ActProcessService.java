package com.scxys.activiti.service;

import java.util.List;
import java.util.Map;

import com.scxys.activiti.bean.ActProcess;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月4日 下午2:31:13 
* @description 说明:
*/
public interface ActProcessService {

	void addProcess(ActProcess actProcess);
	void updateProcess(ActProcess actProcess);
	ActProcess findProcessByCode(String processCode);
	void deleteProcessByCode(String processCode);
	List<Map> findTreeByPid(String pid);
}
 