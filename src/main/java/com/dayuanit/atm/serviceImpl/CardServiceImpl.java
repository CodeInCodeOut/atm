package com.dayuanit.atm.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dayuanit.atm.Exception.AtmException;
import com.dayuanit.atm.domain.Card;
import com.dayuanit.atm.domain.DelayTransfer;
import com.dayuanit.atm.domain.Detail;
import com.dayuanit.atm.dto.CardDTO;
import com.dayuanit.atm.enumUtils.AsnyTransStatusEnum;
import com.dayuanit.atm.enumUtils.OptType;
import com.dayuanit.atm.enumUtils.StatusEnum;
import com.dayuanit.atm.mapper.CardMapper;
import com.dayuanit.atm.mapper.DelayTransferMapper;
import com.dayuanit.atm.mapper.DetailMapper;
import com.dayuanit.atm.service.CardService;
import com.dayuanit.utils.PageUtil;



@Service
public class CardServiceImpl implements CardService{
	
	@Autowired
	private CardMapper cardMapper;
	
	@Autowired
	private DetailMapper detailMapper;
	
	@Autowired
	private DelayTransferMapper delayTransferMapper;
	
	
	
	
	// 没用上 判断是否为数字
	public boolean isNumeric(String str) {
		for (int i = str.length(); --i > 0; ) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	
	private String createBankNum() {
		Random random = new Random();
		String cardNum = "";
		for (int i=0; i<5;i++) {
			int num = random.nextInt(10);
			cardNum += String.valueOf(num);
		}
		
		return cardNum;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String openAccount(Integer userId, Integer amount) {
		// TODO Auto-generated method stub
		String cardNum = createBankNum();
		Card exitCard = cardMapper.getCardByCardNum(cardNum);
		if (null != exitCard) {
			throw new AtmException("卡号已经存在 开户失败");
		}
		
		Card card = new Card();
		card.setBalance(amount);
		card.setCardNum(cardNum);
		card.setStatus(1);
		card.setUserId(userId);
		int rows = cardMapper.addCard(card);
		
		if (1 != rows) {
			System.out.println("卡号" + cardNum + "开户失败");
			throw new AtmException("开户失败");
		}
		
		
		Detail detail = new Detail();
		detail.setAmount(amount);
		detail.setCardNum(cardNum);
		detail.setStatus(1);
		detail.setOptType(0);
		detail.setUserId(userId);
		detail.setFlowDesc("开户存款");
		
		rows = detailMapper.addDetail(detail);
		if (1 != rows) {
			throw new AtmException("开户增加流水失败");
		}
		
		return createBankNum();
	}

	@Override
	public Map<String, Object> cardList(Integer userId, Integer pageNum) {
		// TODO Auto-generated method stub
		Integer cardTotal = cardMapper.getCardTotal(userId);
		new PageUtil(pageNum, cardTotal);
		
		List<Card> listCard = cardMapper.getCardByUserId(userId, PageUtil.getStartNum(), PageUtil.cardSize);
		
		if (null == listCard) {
			throw new AtmException("找不到卡");	
		}
		
		List<CardDTO> cdList = new ArrayList<CardDTO>(listCard.size());
		
		for (Card cd : listCard) {
			CardDTO cdto = new CardDTO();
			cdto.setId(cd.getId());
			cdto.setBalance(String.valueOf(cd.getBalance()));
			cdto.setCardNum(cd.getCardNum());
			StatusEnum se = StatusEnum.getEnum(cd.getStatus());
			cdto.setStatus(se.getValue());
			cdto.setCreateTime(new SimpleDateFormat("yyyy-mm-dd HH-mm-ss").format(cd.getCreateTime()));
			cdto.setModifyTime(new SimpleDateFormat("yyyy-mm-dd HH-mm-ss").format(cd.getModifyTime()));
			cdList.add(cdto);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNum", pageNum);
		map.put("cardList", cdList);
		map.put("lastPage", PageUtil.getLastPage(cardTotal));
		return map;
	}

	@Override
	public Card getCardByCardNum(String cardNum) {
		// TODO Auto-generated method stub
		
		Card exitCard = cardMapper.getCardByCardNum(cardNum);
		
		if (null == exitCard) {
			throw new AtmException("卡号不存在");
		}
		
		return exitCard;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void deposit(Integer userId, String cardNum, Integer amount) {
		// TODO Auto-generated method stub
		
		Card exitCard = cardMapper.getCardByCardNum(cardNum);
		
		if (userId != exitCard.getUserId()) {
			throw new AtmException("无权操作");
		}
		
		int rows = cardMapper.updateBalance(userId, cardNum, amount);
		
		if (1 != rows) {
			throw new AtmException("存款失败");
		}
		
		Detail detail = new Detail();
		detail.setAmount(amount);
		detail.setCardNum(cardNum);
		detail.setStatus(1);
		detail.setOptType(1);
		detail.setUserId(userId);
		detail.setFlowDesc("存款");
		
		rows = detailMapper.addDetail(detail);
		if (1 != rows) {
			throw new AtmException("存款增加流水失败");
		}
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void transfer(String outCardNum, Integer amount, String inCardNum) {
		// TODO Auto-generated method stub
		
		Card OutExitCard = cardMapper.getCardByCardNum(outCardNum);
		if (null == OutExitCard) {
			throw new AtmException("卡号不存在 转账支出失败");
		}
		
		int rows = cardMapper.updateBalanceByCardNum(outCardNum, -amount);
		
		if (1 != rows) {
			throw new AtmException("转账支出失败");
		}
		
		Detail detail = new Detail();
		detail.setAmount(-amount);
		detail.setCardNum(outCardNum);
		detail.setStatus(1);
		detail.setOptType(3);
		detail.setUserId(OutExitCard.getUserId());
		detail.setFlowDesc("转账支出");
		
		rows = detailMapper.addDetail(detail);
		if (1 != rows) {
			throw new AtmException("转账支出流水失败");
		}
		
		Card InExitCard = cardMapper.getCardByCardNum(inCardNum);
		if (null == InExitCard) {
			throw new AtmException("卡号不存在 转账收入失败");
		}
		
		rows = cardMapper.updateBalanceByCardNum(inCardNum, amount);
		
		if (1 != rows) {
			throw new AtmException("转账收入失败");
		}
		
		detail = new Detail();
		detail.setAmount(amount);
		detail.setCardNum(inCardNum);
		detail.setStatus(1);
		detail.setOptType(4);
		detail.setUserId(InExitCard.getUserId());
		detail.setFlowDesc("转账收入");
		
		rows = detailMapper.addDetail(detail);
		if (1 != rows) {
			throw new AtmException("转账收入增加流水失败");
		}
		
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void draw(Integer userId, String cardNum, Integer amount) {
		// TODO Auto-generated method stub
		
		Card exitCard = cardMapper.getCardByCardNum(cardNum);
		
		if (userId != exitCard.getUserId()) {
			throw new AtmException("无权操作");
		}
		
		int rows = cardMapper.updateBalance(userId, cardNum, -amount);
		
		if (1 != rows) {
			throw new AtmException("存款失败");
		}
		
		Detail detail = new Detail();
		detail.setAmount(-amount);
		detail.setCardNum(cardNum);
		detail.setStatus(1);
		detail.setOptType(2);
		detail.setUserId(userId);
		detail.setFlowDesc("取款");
		
		rows = detailMapper.addDetail(detail);
		if (1 != rows) {
			throw new AtmException("取款增加流水失败");
		}
		
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void deleteCardVirtual(Integer userId, String cardNum) {
		// TODO Auto-generated method stub
		
		Card exitCard = cardMapper.getCardByCardNum(cardNum);
		
		if (userId != exitCard.getUserId()) {
			throw new AtmException("无权操作");
		}
		
		int rows = cardMapper.changeStatus(userId, cardNum);
		
		if (1 != rows) {
			throw new AtmException("删除银行卡失败");
		}
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void asynTransferOut(String outCardNum, Integer amount, String inCardNum) {
		// TODO Auto-generated method stub
		
		Card OutExitCard = cardMapper.getCardByCardNum(outCardNum);
		if (null == OutExitCard) {
			throw new AtmException("卡号不存在 转账支出失败");
		}
		
		int rows = cardMapper.updateBalanceByCardNum(outCardNum, -amount);
		
		if (1 != rows) {
			throw new AtmException("转账支出失败");
		}
		
		Detail detail = new Detail();
		detail.setAmount(-amount);
		detail.setCardNum(outCardNum);
		detail.setStatus(1);
		detail.setOptType(3);
		detail.setUserId(OutExitCard.getUserId());
		detail.setFlowDesc("转账支出");
		
		rows = detailMapper.addDetail(detail);
		if (1 != rows) {
			throw new AtmException("转账支出流水失败");
		}
		
		Card InExitCard = cardMapper.getCardByCardNum(inCardNum);
		if (null == InExitCard) {
			throw new AtmException("转账收入卡号不存在");
		}
		
		
		
		DelayTransfer delayTransfer = new DelayTransfer();
		delayTransfer.setAmount(amount);
		delayTransfer.setInCardId(InExitCard.getId());
		delayTransfer.setInCardNum(inCardNum);
		delayTransfer.setOutCardId(OutExitCard.getId());
		delayTransfer.setOutCardNum(outCardNum);
		delayTransfer.setTransactionStatus(AsnyTransStatusEnum.UN_TRANS.getCode());
		
		rows = delayTransferMapper.addDelayTransfer(delayTransfer);
		if (1 != rows) {
			throw new AtmException("转账支出失败");
		}
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void asynTransferIn(DelayTransfer delayTransfer) {
		try {
			Card InExitCard = cardMapper.getCardByCardNumForUpdate(delayTransfer.getInCardNum());
			if (null == InExitCard) {
				throw new AtmException("卡号不存在 转账收入失败");
			}
			
			delayTransfer = delayTransferMapper.getTransferByInCardNumStatus(AsnyTransStatusEnum.UN_TRANS.getCode(), delayTransfer.getInCardNum());
			if (null == delayTransfer) {
				throw new AtmException("卡号不存在 转账收入失败");
			}
			
			int rows = cardMapper.updateBalanceByCardNum(delayTransfer.getInCardNum(), delayTransfer.getAmount());
			
			if (1 != rows) {
				throw new AtmException("转账收入失败");
			}
			
			Detail detail = new Detail();
			detail.setAmount(delayTransfer.getAmount());
			detail.setCardNum(delayTransfer.getInCardNum());
			detail.setStatus(1);
			detail.setOptType(4);
			detail.setUserId(InExitCard.getUserId());
			detail.setFlowDesc("转账收入");
			
			rows = detailMapper.addDetail(detail);
			if (1 != rows) {
				throw new AtmException("转账收入增加流水失败");
			}
			
			delayTransfer.setTransactionStatus(AsnyTransStatusEnum.TRANSFERED.getCode());
			
			rows = delayTransferMapper.updateStatus(delayTransfer);
			if (1 != rows) {
				throw new AtmException("转账状态异常");
			}
			
			int i = 1/0;
			
		} catch (Exception e) {
			
			BackMoney(delayTransfer);
		}
		
		
	}
	
	
	@Override
	@Transactional(rollbackFor=Exception.class)
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
