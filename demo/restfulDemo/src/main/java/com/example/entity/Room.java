package com.example.entity;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年5月4日 下午3:28:22 
* @description 说明:
*/
@XmlRootElement(name="Room")  ////让Room的信息在POJO和XML之间转换
public class Room {  
    public Room()  
    {  
        persons=new HashMap<String,Person>();  
    }  
    String id;  
    Map<String,Person> persons;  
      
    public String getId() {  
        return id;  
    }  
    public void setId(String id) {  
        this.id = id;  
    }  
    public Map<String, Person> getPersons() {  
        return persons;  
    }  
    public void setPersons(Map<String, Person> persons) {  
        this.persons = persons;  
    }  
}  
 