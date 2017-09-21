package com.dayuanit.atm.mapper;

import com.dayuanit.atm.domain.User;

public interface UserMapper {
	
	int add(User user);
	
	User query(String userName);
	
	User queryById(Integer userId);
	
}
