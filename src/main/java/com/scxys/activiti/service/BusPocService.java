package com.scxys.activiti.service;

import com.scxys.activiti.bean.businessBean.BusPoc;

import java.util.List;

/**
 * @Author: qiuxinlin
 * @Dercription:
 * @Date: 16:02 2017/9/5
 */
public interface BusPocService {
    void add(BusPoc busPoc);
    List findAll();
    BusPoc findOne(Long id);
    BusPoc findByCode(String code);
    void delete(Long id);
    void update(BusPoc busPoc);
}
