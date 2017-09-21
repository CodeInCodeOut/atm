package com.dayuanit.atm.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dayuanit.atm.Exception.AtmException;
import com.dayuanit.atm.domain.Card;
import com.dayuanit.atm.domain.DelayTransfer;
import com.dayuanit.atm.domain.Detail;
import com.dayuanit.atm.enumUtils.AsnyTransStatusEnum;
import com.dayuanit.atm.enumUtils.OptType;
import com.dayuanit.atm.enumUtils.StatusEnum;
import com.dayuanit.atm.mapper.CardMapper;
import com.dayuanit.atm.mapper.DelayTransferMapper;
import com.dayuanit.atm.mapper.DetailMapper;
import com.dayuanit.atm.service.BackMoneyForOutNumService;


@Service
public class BackMoneyForOutNumServiceImpl implements BackMoneyForOutNumService {

	@Autowired
	private DetailMapper detailMapper;
	
	@Autowired
	private CardMapper cardMapper;
	
	@Autowired
	private DelayTransferMapper delayTransferMapper;
	
	@Override
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public void BackMoney(DelayTransfer delayTransfer) {
		// TODO Auto-generated method stub
		
		if (delayTransfer.getTransactionStatus() != AsnyTransStatusEnum.UN_TRANS.getCode()) {
			return;
		}
		
		Card exitOutCard = cardMapper.getCardByCardNumForUpdate(delayTransfer.getOutCardNum());
		if (null == exitOutCard) {
			throw new AtmException("卡号不存在 转账支出回滚失败");
		}
		
		int rows = cardMapper.updateBalanceByCardNum(delayTransfer.getInCardNum(), delayTransfer.getAmount());
		if (1 != rows) {
			throw new AtmException("转账支出回滚失败");
		}
		
		delayTransfer.setTransactionStatus(AsnyTransStatusEnum.QUITTRANSFER.getCode());
		rows = delayTransferMapper.updateStatus(delayTransfer);
		if (1 != rows) {
			throw new AtmException("转账支出回滚失败");
		}
		
		Detail detail = new Detail();
		detail.setAmount(delayTransfer.getAmount());
		detail.setCardNum(exitOutCard.getCardNum());
		detail.setStatus(StatusEnum.USABLE.getCode());
		detail.setOptType(OptType.BACK_MONEY.getCode());
		detail.setUserId(exitOutCard.getUserId());
		detail.setFlowDesc("转账支出回滚");
		
		rows = detailMapper.addDetail(detail);
		if (1 != rows) {
			throw new AtmException("转账支出回滚流水失败");
		}
		
	}

}
