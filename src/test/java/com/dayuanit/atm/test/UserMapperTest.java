package com.dayuanit.atm.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dayuanit.atm.domain.User;
import com.dayuanit.atm.mapper.UserMapper;

@ContextConfiguration("/spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserMapperTest {
	
	@Autowired
	private UserMapper userMapper;
	
	private User user;
	
	@Before
	public void init() {
		user = new User();
		user.setId(5);
		user.setUserName("111111");
		user.setPassword("123435");
		user.setEmail("88888");
	}
	
	@Ignore
	@Test
	@Rollback
	public void testAdd() {
		int rows = userMapper.add(user);
		assertEquals(1, rows);
	}
	
	@Ignore
	@Test
	@Rollback
	public void testQuery() {
		int rows = userMapper.add(user);
		User exitUser = userMapper.query(user.getUserName());
		assertEquals(exitUser.getUserName(), user.getUserName());
		
	}
	
	@Test
	@Rollback
	public void testQueryById() {
		int rows = userMapper.add(user);
		User exitUser = userMapper.queryById(user.getId());
		assertEquals(exitUser.getUserName(), user.getUserName());
		
	}
	

}
