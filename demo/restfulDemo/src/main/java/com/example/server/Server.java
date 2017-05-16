package com.example.server;



import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import com.example.entity.Person;
import com.example.entity.Room;
import com.example.entity.Rooms;
import com.example.service.RoomService;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年5月4日 下午4:10:22 
* @description 说明:
*/
public class Server {

	public static void main(String[] args) {  
        RoomService service = new RoomService();  
  
        // Service instance  
        JAXRSServerFactoryBean restServer = new JAXRSServerFactoryBean();  
        restServer.setResourceClasses(Room.class,Person.class,Rooms.class);  
        restServer.setServiceBean(service);  
        restServer.setAddress("http://localhost:9999/");  
        restServer.create();  
    }  
}