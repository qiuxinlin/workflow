package com.scxys.activiti.rest.service.workflow.serviceImpl;

import static org.hamcrest.CoreMatchers.endsWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.scxys.activiti.bean.ActFlowclassify;
import com.scxys.activiti.bean.ActProcess;
import com.scxys.activiti.dao.ActFlowclassifyDao;
import com.scxys.activiti.dao.ActProcessDao;
import com.scxys.activiti.service.ActProcessService;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年7月4日 下午2:37:27 
* @description 说明:
*/
@Service(version="1.0.0")
public class ActProcessServiceImpl implements ActProcessService{

	@Autowired
	ActProcessDao processDao;
	@Autowired
	ActFlowclassifyDao flowclassifyDao;
	
	@Autowired
	DataSource dataSource;
	
	@Override
	public void addProcess(ActProcess actProcess) {
		processDao.save(actProcess);
	}

	@Override
	public List<Map> findTreeByPid(String pid) {
		NamedParameterJdbcTemplate jbdcTemplate = new NamedParameterJdbcTemplate(dataSource);
		Map param = new HashMap<>();
		param.put("pid", pid);
		List list = jbdcTemplate.queryForList("SELECT * from(SELECT af.classify_code as 'id' , af.classify_name as 'name' ,af.parent_code as 'pid' from act_flowclassify af UNION SELECT ap.process_code as 'id' , ap.process_name as 'name', ap.parent_code as 'pid' from act_process ap ) v  where pid = :pid"
				, param);
		return list;
	}

	@Override
	public ActProcess findProcessByCode(String processCode) {
		return processDao.findProcessByCode(processCode);
	}

	@Override
	public void updateProcess(ActProcess actProcess) {
		processDao.save(actProcess);
	}

	@Override
	public void deleteProcessByCode(String processCode) {
		processDao.deleteByProcessCode(processCode);
	}

}
 