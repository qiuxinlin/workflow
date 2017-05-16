package com.scxys.activiti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scxys.bean.Employee;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {
	
	/**使用用户名作为查询条件，查询用户对象*/
//	@Query("select * from employee where name=?1")
	public Employee findByName(String name);
}
