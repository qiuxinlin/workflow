package com.scxys.activiti.service; 
/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月3日 下午1:56:13 
* @description 说明:
*/

import java.util.List;
import java.util.Map;

import com.scxys.activiti.bean.ActFlowclassify;

public interface ActFlowclassifyService {

	List<ActFlowclassify> findRoot();
	List<ActFlowclassify> findAll();
	List<ActFlowclassify> findChildrens(String parent_code);
	void addFlowclassify(ActFlowclassify flowclassify);
	ActFlowclassify findByCode(String classifyCode);
	void deleteFlowclassifyByCode(String classifyCode);
	void updateFlowclassify(ActFlowclassify actFlowclassify);
}
 