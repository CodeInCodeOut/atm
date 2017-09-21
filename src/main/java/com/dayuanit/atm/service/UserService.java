package com.dayuanit.atm.service;

import com.dayuanit.atm.domain.User;

public interface UserService {
	
	void regist(String userName, String password, String copyPassword, String email);
	
	User login(String userName, String password);

}
