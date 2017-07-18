package com.scxys.activiti.service;

import org.springframework.web.bind.annotation.RestController;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年6月30日 上午11:21:49 
* @description 说明:自定义restService
*/
public interface RepositoryService {

	/**
	 * @author 作者: qiuxinlin
	 * @version 创建时间:2017年6月30日 下午4:44:33 
	 * @description 说明:保存画完的流程图 xml和svg资源
	 */
	void saveResources();
	
}
 