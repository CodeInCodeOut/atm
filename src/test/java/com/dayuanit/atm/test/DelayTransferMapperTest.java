package com.dayuanit.atm.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dayuanit.atm.domain.DelayTransfer;
import com.dayuanit.atm.mapper.DelayTransferMapper;

@ContextConfiguration("/spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DelayTransferMapperTest {
	
	@Autowired
	private DelayTransferMapper delayTransferMapper;
	
	private DelayTransfer delayTransfer;
	
	@Before
	public void init() {
		delayTransfer = new DelayTransfer();
		delayTransfer.setAmount(10);
		delayTransfer.setInCardId(90);
		delayTransfer.setInCardNum("8909");
		delayTransfer.setOutCardId(9098);
		delayTransfer.setOutCardNum("9907");
		delayTransfer.setTransactionStatus(1);
	}
	
	@Ignore
	@Test
	@Rollback
	public void testAdd() {
		int rows = delayTransferMapper.addDelayTransfer(delayTransfer);
		assertEquals(1, rows);
	}
	@Ignore
	@Test
	@Rollback
	public void testTransferInCard() {
		delayTransferMapper.addDelayTransfer(delayTransfer);
		List<DelayTransfer> listTransferInCard = delayTransferMapper.transferInCard();
		assertEquals(1, listTransferInCard.size());
	}
	@Ignore
	@Test
	@Rollback
	public void testGetTransferByInCardNumStatus() {
		delayTransferMapper.addDelayTransfer(delayTransfer);
		DelayTransfer dt = delayTransferMapper.getTransferByInCardNumStatus(delayTransfer.getTransactionStatus(), delayTransfer.getInCardNum());
		assertEquals(delayTransfer.getTransactionStatus(), dt.getTransactionStatus());
	}
	
	
	@Test
	@Rollback
	public void testUpdateStatus() {
		delayTransferMapper.addDelayTransfer(delayTransfer);
		delayTransfer.setTransactionStatus(2);
		int rows = delayTransferMapper.updateStatus(delayTransfer);
		assertEquals(1, rows);
	}
	

}
