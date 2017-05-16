package com.exampile.client;

import org.apache.cxf.jaxrs.client.WebClient;

import com.example.entity.Person;
import com.example.entity.Room;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年5月4日 下午5:56:26 
* @description 说明:
*/
public class Client {

	static WebClient client;  
	  
    public static void main(String[] args) {  
        client = WebClient.create("http://localhost:9999/");  
        //post();
        //put();  
        postPerson();
    }  
  
    static void get() {  
        Room room = client.path("roomservice/room/001")  
                .accept("application/xml").get(Room.class);  
        System.out.println("get the room which id is:" + room.getId());  
    }  
  
    static void post() {  
        Room room = new Room();  
        room.setId("003");  
        client.path("roomservice/room").accept("application/xml")  
                .post(room, Room.class);  
    }  
  
    static void delete() {  
        client.path("roomservice/room/002").accept("application/xml").delete();  
    }  
  
    static void put() {  
        Room room = new Room();  
        room.setId("006");  
        client.path("roomservice/room/003").accept("application/xml").put(room);  
    }
    
    static void postPerson() {
    	Person person=new Person();
    	person.setName("qxllll");
    	person.setSex("男");
    	//第二个参数是告诉client要将server返回的response转化为的POJO的类型。第二个参数可以不写，如果不写，收到的将是一个response。
    	client.path("roomservice/room/006").accept("application/xml").post(person,Person.class); // Person.class可写可不写
    }
}
 