package com.scxys.activiti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scxys.activiti.bean.LeaveBill;

@Repository
public interface LeaveBillDao extends JpaRepository<LeaveBill,Long>{

	
}
