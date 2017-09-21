package com.dayuanit.atm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuanit.atm.domain.Detail;

public interface DetailMapper {
	
	int addDetail(Detail detail);
	
	int getCardDetailTotal(String cardNum);
	
	List<Detail> getDetailByCardNum(@Param("cardNum")String cardNum, @Param("startNum")Integer startNum, @Param("detailSize")Integer detailSize);

	List<Detail> getAllDetailByCardNum(@Param("cardNum")String cardNum, @Param("userId")Integer userId);
}
