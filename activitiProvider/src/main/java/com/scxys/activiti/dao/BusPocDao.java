package com.scxys.activiti.dao;

import com.scxys.activiti.bean.businessBean.BusPoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: qiuxinlin
 * @Dercription:
 * @Date: 15:59 2017/9/5
 */
@Transactional
@Repository
public interface BusPocDao extends JpaRepository<BusPoc,Long>{
    BusPoc findByCode(String code);
}
