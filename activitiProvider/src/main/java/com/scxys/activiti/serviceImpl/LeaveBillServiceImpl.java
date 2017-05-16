package com.scxys.activiti.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.scxys.activiti.dao.LeaveBillDao;
import com.scxys.bean.Employee;
import com.scxys.bean.LeaveBill;
import com.scxys.service.LeaveBillService;

@Service(version="1.0.0")
public class LeaveBillServiceImpl implements LeaveBillService{

	@Autowired
	LeaveBillDao leaveBillDao;
	/**查询自己的请假单的信息*/
	@Override
	public List<LeaveBill> findLeaveBillList() {
		List<LeaveBill> list = leaveBillDao.findAll();
		return list;
	}
	
	/**保存请假单*/
	@Override
	public void saveLeaveBill(LeaveBill leaveBill,Employee employee) {
		//获取请假单ID
		Long id = leaveBill.getId();
		/**新增保存*/
		if(id==null){
			//1：从Session中获取当前用户对象，将LeaveBill对象中user与Session中获取的用户对象进行关联
			leaveBill.setUser(employee);//建立管理关系
			//2：保存请假单表，添加一条数据
			leaveBillDao.save(leaveBill);
		}
		/**更新保存*/
		else{
			//1：执行update的操作，完成更新
			leaveBillDao.save(leaveBill);
		}
		
	}
	
	/**使用请假单ID，查询请假单的对象*/
	@Override
	public LeaveBill findLeaveBillById(Long id) {
		LeaveBill bill = leaveBillDao.findOne(id);
		return bill;
	}
	
	/**使用请假单ID，删除请假单*/
	@Override
	public void deleteLeaveBillById(Long id) {
		leaveBillDao.delete(id);
	}

}
