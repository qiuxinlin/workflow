package com.scxys.activiti.service;

import com.scxys.activiti.bean.Employee;

public interface EmployeeService {

	Employee findByName(String name);
	void delete(Long id);
	Employee findById(Long id);
}
