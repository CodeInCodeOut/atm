package com.dayuanit.atm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuanit.atm.domain.Card;

public interface CardMapper {
	
	int addCard(Card card);
	
	Card getCardByCardNum(String cardNum);
	
	List<Card> getCardByUserId(@Param("userId")Integer userId, @Param("startNum")Integer startNum, @Param("cardSize")Integer cardSize);

	int getCardTotal(Integer userId);
	
	int updateBalance(@Param("userId")Integer userId, @Param("cardNum")String cardNum, @Param("amount")Integer amount);
	
	int updateBalanceByCardNum(@Param("cardNum")String cardNum, @Param("amount")Integer amount);
	
	int changeStatus(@Param("userId")Integer userId, @Param("cardNum")String cardNum);
	
	Card getCardByCardNumForUpdate(String cardNum);
}
