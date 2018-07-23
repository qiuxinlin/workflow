package com.scxys.activiti.service; 
/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月3日 下午1:56:13 
* @description 说明:
*/

import com.scxys.activiti.bean.ActFlowclassify;

import java.util.List;

public interface ActFlowclassifyService {

	List<ActFlowclassify> findRoot();
	List<ActFlowclassify> findAll();
	List<ActFlowclassify> findChildrens(String parent_code);
	void addFlowclassify(ActFlowclassify flowclassify);
	ActFlowclassify findByCode(String classifyCode);
	void deleteFlowclassifyByCode(String classifyCode);
	void updateFlowclassify(ActFlowclassify actFlowclassify);
	/**
	 * @Author: qiuxinlin
	 * @Dercription: 获取下一个分类的编码
	 * @Date: 2017/7/31
	 */
	String getNextClassifyCode(String currentId);
	/**
	 * @Author: qiuxinlin
	 * @Dercription: 添加分类时自动生成全路径
	 * @Date: 2017/7/31
	 */
	String getFullPath(String parentCode, String currentClassifyName);
}
 