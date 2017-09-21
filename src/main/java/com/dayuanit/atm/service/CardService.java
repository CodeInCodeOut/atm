package com.dayuanit.atm.service;

import java.util.Map;

import com.dayuanit.atm.domain.Card;
import com.dayuanit.atm.domain.DelayTransfer;

public interface CardService {
	
	Card getCardByCardNum(String cardNum);
	
	String openAccount(Integer userId, Integer amount);
	
	Map<String, Object> cardList(Integer userId, Integer pageNum);
	
	void deposit(Integer userId, String cardNum, Integer amount);
	
	void transfer(String outCardNum, Integer amount, String inCardNum);

	void draw(Integer userId, String cardNum, Integer amount);
	
	void deleteCardVirtual(Integer userId, String cardNum);
	
	void asynTransferOut(String outCardNum, Integer amount, String inCardNum);
	
	void asynTransferIn(DelayTransfer delayTransfer);
	
	void BackMoney(DelayTransfer delayTransfer);
	 
}
