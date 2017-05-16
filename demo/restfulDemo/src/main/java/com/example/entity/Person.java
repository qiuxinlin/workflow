package com.example.entity;

import javax.xml.bind.annotation.XmlRootElement;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年5月4日 下午3:26:12 
* @description 说明:
*/
@XmlRootElement(name="Person")  //让person的信息在POJO和XML之间转换
public class Person {  
    private String name;  
    private String sex;  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public String getSex() {  
        return sex;  
    }  
    public void setSex(String sex) {  
        this.sex = sex;  
    }  
      
}  
 