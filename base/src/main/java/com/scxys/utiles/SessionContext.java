package com.scxys.utiles;

import javax.servlet.http.HttpSession;

import com.scxys.bean.Employee;

public class SessionContext {

	public static final String GLOBLE_USER_SESSION = "globle_user";
	
	public static void setUser(Employee user,HttpSession httpSession){
		if(user!=null){
			httpSession.setAttribute(GLOBLE_USER_SESSION, user);
		}else{
			httpSession.removeAttribute(GLOBLE_USER_SESSION);
		}
	}
	
	public static Employee get(HttpSession httpSession){
		return (Employee) httpSession.getAttribute(GLOBLE_USER_SESSION);
	}
}
