package com.scxys.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.scxys.bean.Employee;
import com.scxys.bean.LeaveBill;

public interface LeaveBillService {

	List<LeaveBill> findLeaveBillList();

	void saveLeaveBill(LeaveBill leaveBill,Employee employee);

	LeaveBill findLeaveBillById(Long id);

	void deleteLeaveBillById(Long id);

}
