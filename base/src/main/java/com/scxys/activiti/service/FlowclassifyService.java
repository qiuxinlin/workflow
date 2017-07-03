package com.scxys.activiti.service; 
/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月3日 下午1:56:13 
* @description 说明:
*/

import java.util.List;

import com.scxys.activiti.bean.Flowclassify;

public interface FlowclassifyService {

	List<Flowclassify> findRoot();
	List<Flowclassify> findAll();
	List<Flowclassify> findChildrens();
	void addFlowclassify(Flowclassify flowclassify);
	void deleteFlowclassifyById();
	void updateFlowclassifyById();
}
 