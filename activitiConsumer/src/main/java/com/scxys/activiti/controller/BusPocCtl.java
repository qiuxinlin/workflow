package com.scxys.activiti.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.scxys.activiti.bean.businessBean.BusPoc;
import com.scxys.activiti.service.BusPocService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: qiuxinlin
 * @Dercription:
 * @Date: 16:16 2017/9/5
 */
@RestController
public class BusPocCtl {
    @Reference(version = "1.0.0")
    private
    BusPocService busPocService;

    @RequestMapping(value = "/busPoc",method = RequestMethod.POST)
    public String add(@RequestBody BusPoc busPoc){
        busPocService.add(busPoc);
        return busPoc.getCode();
    }
    @RequestMapping(value = "/busPoc/{id}",method = RequestMethod.GET)
    public BusPoc findOne(@PathVariable Long id){
        return busPocService.findOne(id);
    }

    @RequestMapping(value = "/busPoc",method = RequestMethod.GET)
    public BusPoc findByCode(@RequestParam Map<String, String> requestParams){
        if(requestParams.containsKey("code")){
            return busPocService.findByCode(requestParams.get("code"));
        }
        return null;
    }
}
