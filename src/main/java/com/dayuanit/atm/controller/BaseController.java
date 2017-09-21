package com.dayuanit.atm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dayuanit.atm.Exception.AtmException;
import com.dayuanit.atm.domain.User;

public class BaseController {
	
	protected static final String LOGIN_FLAG = "loginUser";
	
	protected User getUser(HttpServletRequest req) {
	
	
	Object objectUser = req.getSession().getAttribute(LOGIN_FLAG);
	if (null == objectUser) {
		throw new AtmException("用户未登录 ");
	}
	
	return (User)objectUser;
	
	}
	
	protected Integer getUserId(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Object objectUser = session.getAttribute(LOGIN_FLAG);
		
		if (null == objectUser) {
			throw new AtmException("登录用户为空");
		}
		
		return ((User) objectUser).getId();	
	}
	
	protected String getUsername(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Object objectUser = session.getAttribute(LOGIN_FLAG);
		
		if (null == objectUser) {
			throw new AtmException("登录用户为空");
		}
		
		return ((User) objectUser).getUserName();
	}
	
	protected String getUserEmail(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Object objectUser = session.getAttribute(LOGIN_FLAG);
		
		if (null == objectUser) {
			throw new AtmException("登录用户为空");
		}
		
		return ((User)objectUser).getEmail();
	}
	
}
