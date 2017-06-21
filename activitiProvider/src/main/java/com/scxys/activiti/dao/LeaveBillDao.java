package com.scxys.activiti.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scxys.bean.LeaveBill;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveBillDao extends JpaRepository<LeaveBill,Long>{

	
}
