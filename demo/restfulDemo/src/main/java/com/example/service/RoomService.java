package com.example.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.example.dao.RoomDao;
import com.example.entity.Person;
import com.example.entity.Room;
import com.example.entity.Rooms;

/** 
* @author 作者:qiuxinlin 
* @version 创建时间:2017年5月4日 下午3:58:47 
* @description 说明:
*/
@Path("/roomservice")  
@Produces("application/xml")  
public class RoomService {  
      
    @GET  
    @Path("/room/{id}")  
    @Consumes("application/xml")  
    public Room getRoom(@PathParam("id")String id )  
    {  
        System.out.println("get room by id= "+id);  
        Room room=RoomDao.getRoom(id);  
        return room;  
    }  
    @GET  
    @Path("/room")  
    @Consumes("application/xml")  
    public Rooms getAllRoom()  
    {  
        System.out.println("get all room");  
        Rooms rooms=RoomDao.getRooms();  
        return rooms;  
    }  
      
    @POST  
    @Path("/room")  
    @Consumes("application/xml")  
    public void addRoom(Room room)  
    {  
        System.out.println("add room which id is:"+room.getId());  
        RoomDao.addRoom(room);  
    }  
    @PUT  
    @Path("/room/{id}")  
    @Consumes("application/xml")  
    public void updateRoom(@PathParam("id")String id,Room room)  
    {  
        System.out.println("update room which original id is:"+room.getId());  
        RoomDao.updateRoom(id,room);  
    }  
    @DELETE  
    @Path("/room/{id}")  
    @Consumes("application/xml")  
    public void deleteRoom(@PathParam("id")String id)  
    {  
        System.out.println("remove room by id= "+id);  
        RoomDao.deleteRoom(id);  
    }  
    @POST  
    @Path("/room/{id}")  
    @Consumes("application/xml")  
    public void addPerson(@PathParam("id") String id,Person person)  
    {  
        System.out.println("add person who's name is:"+person.getName());  
        RoomDao.addPerson(id, person);  
    }  
    @DELETE  
    @Path("/room/{id}/{name}")  
    @Consumes("application/xml")  
    public void deletePerson(@PathParam("id")String id,@PathParam("name")String name)  
    {  
        System.out.println("remove person who's name is: "+name);  
        RoomDao.deletePerson(id, name);  
    }  
}  
 