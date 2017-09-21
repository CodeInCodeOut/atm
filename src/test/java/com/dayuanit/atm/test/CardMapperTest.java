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

import com.dayuanit.atm.domain.Card;
import com.dayuanit.atm.mapper.CardMapper;


@ContextConfiguration("/spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CardMapperTest {
	
	@Autowired
	private CardMapper cardMapper;
	
	private Card card;
	
	@Before
	public void init() {
		card = new Card();
		card.setCardNum("11111");
		card.setStatus(1);
		card.setUserId(1);
		card.setBalance(12);
	}
	@Ignore
	@Test
	@Rollback
	public void testAddCard() {
		int rows = cardMapper.addCard(card);
		assertEquals(1, rows);
	}
	@Ignore
	@Test
	@Rollback
	public void testGetCardByCardNum() {
		int rows = cardMapper.addCard(card);
		Card exitCard = cardMapper.getCardByCardNum(card.getCardNum());
		assertEquals(exitCard.getCardNum(), card.getCardNum());
	}
	@Ignore
	@Test
	@Rollback
	public void testGetCardTotal() {
		int rows = cardMapper.addCard(card);
		int account = cardMapper.getCardTotal(card.getUserId());
		assertEquals(account, 1);
	}
	@Ignore
	@Test
	@Rollback
	public void TestGetCardByUserId() {
		int rows = cardMapper.addCard(card);
		List<Card> listCard = cardMapper.getCardByUserId(card.getUserId(), 0, 1);
		assertEquals(listCard.size(), 1);
	}
	@Ignore
	@Test
	@Rollback
	public void TestUpdateBalance() {
		cardMapper.addCard(card);
		int amount = 10;
		int rows = cardMapper.updateBalance(card.getUserId(), card.getCardNum(), amount);
		assertEquals(rows, 1);
	}
	@Ignore
	@Test
	@Rollback
	public void TestUpdateBalanceByCardNum() {
		cardMapper.addCard(card);
		int amount = 10;
		int rows = cardMapper.updateBalanceByCardNum(card.getCardNum(), amount);
		assertEquals(rows, 1);
	}
	@Ignore
	@Test
	@Rollback
	public void TestChangeStatus() {
		cardMapper.addCard(card);
		
		int rows = cardMapper.changeStatus(card.getUserId(),card.getCardNum());
		assertEquals(rows, 1);
	}
	
	
	@Test
	@Rollback
	public void TesGetCardByCardNumForUpdate() {
		cardMapper.addCard(card);
		Card exitcard = cardMapper.getCardByCardNumForUpdate(card.getCardNum());
		assertEquals(exitcard.getBalance(), card.getBalance());
	}
	
	
	

}
