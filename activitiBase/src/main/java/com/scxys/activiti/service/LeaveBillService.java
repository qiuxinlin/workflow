package com.scxys.activiti.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.scxys.activiti.bean.Employee;
import com.scxys.activiti.bean.LeaveBill;

public interface LeaveBillService {

	List<LeaveBill> findLeaveBillList();

	void saveLeaveBill(LeaveBill leaveBill);

	LeaveBill findLeaveBillById(Long id);

	void deleteLeaveBillById(Long id);

}
