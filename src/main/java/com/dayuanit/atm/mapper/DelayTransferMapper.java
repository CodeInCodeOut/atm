package com.dayuanit.atm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuanit.atm.domain.DelayTransfer;

public interface DelayTransferMapper {
	
	int addDelayTransfer(DelayTransfer delayTransfer);
	
	List<DelayTransfer> transferInCard();
	
	int updateStatus(DelayTransfer delayTransfer);
	
	DelayTransfer getTransferByInCardNumStatus(@Param("transactionStatus")Integer transactionStatus, @Param("inCardNum")String inCardNum);
	
	DelayTransfer getTransferByOutCardForUpdate(@Param("transactionStatus")Integer transactionStatus, @Param("inCardNum")String outCardNum);
}
