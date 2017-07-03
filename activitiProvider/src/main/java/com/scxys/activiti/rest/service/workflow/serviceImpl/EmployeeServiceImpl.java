package com.scxys.activiti.rest.service.workflow.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.scxys.activiti.bean.Employee;
import com.scxys.activiti.dao.EmployeeDao;
import com.scxys.activiti.service.EmployeeService;

@Service(version="1.0.0")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao employeeDao;
	
	@Override
	public Employee findByName(String name){
		Employee employee=employeeDao.findByName(name);
		System.out.println(employee.getName());
		return employee;
	}
	
	@Override
	public void delete(Long id){
		employeeDao.delete(id);
	}
	
	@Override
	public Employee findById(Long id){
		return employeeDao.findOne(id);
	}
}
