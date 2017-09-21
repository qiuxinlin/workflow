package com.scxys.activiti.rest.service.workflow.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.scxys.activiti.bean.ActFlowclassify;
import com.scxys.activiti.dao.ActFlowclassifyDao;
import com.scxys.activiti.service.ActFlowclassifyService;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月3日 下午2:05:40 
* @description 说明:
*/
@Service(version="1.0.0")
public class ActFlowclassifyServiceImpl implements ActFlowclassifyService{

	@Autowired
	ActFlowclassifyDao flowclassifyDao;
	@Override
	public List<ActFlowclassify> findRoot() {
		List<ActFlowclassify> list=flowclassifyDao.findRoot();
		return list;
	}

	@Override
	public List<ActFlowclassify> findAll() {
		List<ActFlowclassify> list=flowclassifyDao.findAll();
		return list;
	}

	@Override
	public List<ActFlowclassify> findChildrens(String parent_code) {
		List<ActFlowclassify> list=flowclassifyDao.findChildren(parent_code);
		return list;
	}

	@Override
	public void addFlowclassify(ActFlowclassify flowclassify) {
		flowclassifyDao.save(flowclassify);
	}

	@Override
	public void deleteFlowclassifyByCode(String classifyCode) {
		flowclassifyDao.deleteByClassifyCode(classifyCode);
	}

	@Override
	public void updateFlowclassify(ActFlowclassify actFlowclassify) {
		flowclassifyDao.save(actFlowclassify);
	}

	@Override
	public ActFlowclassify findByCode(String classifyCode) {
		return flowclassifyDao.findByCode(classifyCode);
	}

	@Override
	public String getNextClassifyCode(String currentId){
		List<ActFlowclassify> codeList=flowclassifyDao.findChildrensCode(currentId);
		if (codeList.size()==0){
			if(("0").equals(currentId)){
				return "01";
			}
			return currentId+"01";
		}
		ActFlowclassify temp;
		for(int i=0;i<codeList.size()-1;i++){
			if(Integer.parseInt(codeList.get(i).getClassifyCode())>Integer.parseInt(codeList.get(i+1).getClassifyCode())){
				temp=codeList.get(i);
				codeList.set(i,codeList.get(i+1));
				codeList.set(i+1,temp);
			}
		}
		int resultInt=Integer.parseInt(codeList.get(codeList.size()-1).getClassifyCode())+1;
		String resultString=null;
		if (resultInt<10){
			resultString="0"+resultInt;
		}else {
			resultString=String.valueOf(resultInt);
		}
		if (("0").equals(currentId)){
			return  resultString;
		}
		return currentId+resultString;
	}

	@Override
	public String getFullPath(String parentCode,String currentClassifyName){
		List<String> list =new ArrayList<>();
		while (("-1").equals(parentCode)==false){
			ActFlowclassify flowclassify=flowclassifyDao.findByCode(parentCode);
			if(flowclassify!=null){
				list.add(flowclassify.getClassifyName());
			}
			parentCode=flowclassify.getParentCode();
		}
		Collections.reverse(list);
		list.add(currentClassifyName);
		String result="";
		for(String str:list){
			result+=str+"/";
		}
		return result;
	}
}
 