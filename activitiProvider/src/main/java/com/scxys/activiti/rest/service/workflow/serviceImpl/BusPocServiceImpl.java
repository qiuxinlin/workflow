package com.scxys.activiti.rest.service.workflow.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.scxys.activiti.bean.businessBean.BusPoc;
import com.scxys.activiti.dao.BusPocDao;
import com.scxys.activiti.service.BusPocService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: qiuxinlin
 * @Dercription:
 * @Date: 16:05 2017/9/5
 */
@Service(version="1.0.0")
public class BusPocServiceImpl implements BusPocService {
    @Autowired
    BusPocDao busPocDao;
    @Override
    public void add(BusPoc busPoc) {
        busPocDao.save(busPoc);
    }

    @Override
    public List findAll() {
        List list=busPocDao.findAll();
        return list;
    }

    @Override
    public BusPoc findOne(Long id) {
        BusPoc busPoc=busPocDao.findOne(id);
        return busPoc;
    }

    @Override
    public BusPoc findByCode(String code) {
        return busPocDao.findByCode(code);
    }

    @Override
    public void delete(Long id) {
        busPocDao.delete(id);
    }

    @Override
    public void update(BusPoc busPoc) {
        busPocDao.save(busPoc);
    }
}
