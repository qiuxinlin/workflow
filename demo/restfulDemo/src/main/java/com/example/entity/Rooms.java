package com.example.entity;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.example.dao.RoomDao;
import com.fasterxml.jackson.annotation.JsonRootName;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年5月4日 下午3:31:41 
* @description 说明:
*/
@XmlRootElement(name="rooms")
public class Rooms {  
    Map<String,Room> rooms;  
    public Rooms()  
    {  
        rooms=RoomDao.getMapOfRooms();  
    }  
    public Map<String, Room> getRooms() {  
        return rooms;  
    }  
    public void setRooms(Map<String, Room> rooms) {  
        this.rooms = rooms;  
    }  
}  