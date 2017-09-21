package com.dayuanit.atm.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dayuanit.atm.domain.Card;
import com.dayuanit.atm.domain.Detail;
import com.dayuanit.atm.mapper.DetailMapper;
import com.dayuanit.utils.PageUtils;


@ContextConfiguration("/spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DetailMapperTest {
	
	@Autowired
	private DetailMapper detailMapper;
	
	private Detail detail;
	
	
	@Before
	public void init() {
		detail = new Detail();
		detail.setAmount(10);
		detail.setCardNum("111");
		detail.setFlowDesc("biubiu");
		detail.setOptType(0);
		detail.setUserId(12);
		detail.setStatus(1);
	}
	
	
	@Test
	@Rollback
	public void testAddDetail() {
		int rows = detailMapper.addDetail(detail);
		assertEquals(1, rows);
	}
	
	@Test
	@Rollback
	public void getCardDetailTotal() {
		int rows = detailMapper.addDetail(detail);
		int account = detailMapper.getCardDetailTotal(detail.getCardNum());
		assertEquals(1, account);
	}
	

	@Test
	@Rollback
	public void getDetailByCardNum() {
		int rows = detailMapper.addDetail(detail);
		List<Detail> detailList = detailMapper.getDetailByCardNum(detail.getCardNum(), 0, 1);
		assertEquals(detailList.size(), 1);
		
	}
	

}
