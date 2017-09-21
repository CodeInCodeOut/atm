package com.dayuanit.atm.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dayuanit.atm.domain.DelayTransfer;
import com.dayuanit.atm.handler.EmailHandler;
import com.dayuanit.atm.mapper.DelayTransferMapper;
import com.dayuanit.atm.service.BackMoneyForOutNumService;
import com.dayuanit.atm.service.CardService;

@Component("transferTask")
public class TransferTask {
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private DelayTransferMapper delayTransferMapper;
	
	@Autowired
	private BackMoneyForOutNumService backMoney;
	
	@Autowired
	private EmailHandler emailHandler;
	
	Logger log = LoggerFactory.getLogger(TransferTask.class);
	
	public void doTask() {
		System.out.println("-----dotask--------------------------");
		log.info("----------------dotask--------------------");
		List<DelayTransfer> inCardList = delayTransferMapper.transferInCard();
		for (DelayTransfer dt : inCardList) {
			try {
				log.info("----------" + "正在转账{},金额{}",dt.getInCardNum(),dt.getAmount());
				cardService.asynTransferIn(dt);
			} catch (Exception e) {
				log.error("异步处理转账异常{}", e.getMessage(), e);
//				cardService.BackMoney(dt);
//				backMoney.BackMoney(dt);
				continue;
				//TODO 将转账取消 钱退回
			} finally {
				try {
					emailHandler.submit(dt);
				} catch (Exception ee) {
					log.error("邮件发送异常{}", ee.getMessage(), ee);
				}
				
			}
			
		}
	}
	
}
