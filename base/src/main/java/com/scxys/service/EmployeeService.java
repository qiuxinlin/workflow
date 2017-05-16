package com.scxys.service;

import com.scxys.bean.Employee;

public interface EmployeeService {

	Employee findByName(String name);
	void delete(Long id);
	Employee findById(Long id);
}
