package com.dayuanit.atm.serviceImpl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayuanit.atm.Exception.AtmException;
import com.dayuanit.atm.domain.User;
import com.dayuanit.atm.mapper.UserMapper;
import com.dayuanit.atm.service.UserService;

@Service
public  class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User login(String userName, String password) {
		// TODO Auto-generated method stub
		
		if ("".equals(userName) || null == userName) {
			throw new AtmException("用户名不能为空");
		}
		
		if ("".equals(password) || null == password) {
			throw new AtmException("密码不能为空");
		}
		
		User exitUser = userMapper.query(userName);
		
		if (null == exitUser) {
			throw new AtmException("用户名或者密码不正确");
		}
		
		// todo 登录密码 加密 校验
		password = DigestUtils.md5Hex(password + userName);
		
		if (!exitUser.getPassword().equals(password)) {
			throw new AtmException("用户名或者密码不正确");
		}
		
		
		return exitUser;
		
	}


	@Override
	public void regist(String userName, String password, String copyPassword, String email) {
		// TODO Auto-generated method stub
		User exitUser = userMapper.query(userName);
		
		if (null != exitUser) {
			throw new AtmException("用户名被注册");
		}
		
		if ("".equals(userName) || null == userName) {
			throw new AtmException("用户名不能为空");
		}
		
		if ("".equals(password) || null == password) {
			throw new AtmException("注册密码不能为空");
		}
		
		if ("".equals(copyPassword) || null == copyPassword) {
			throw new AtmException("校验密码不能为空");
		}
		
		if ("".equals(email) || null == email) {
			throw new AtmException("校验密码不能为空");
		}
		
		if (!password.equals(copyPassword)) {
			throw new AtmException("注册密码与校验密码不相同 注册失败");
		}
		
		// todo 注册密码 加密
		password = DigestUtils.md5Hex(password + userName);
		
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setEmail(email);
		int rows = userMapper.add(user);
		
		if (1 != rows) {
			throw new AtmException("注册失败");
		}
		
	}
	
	

}
