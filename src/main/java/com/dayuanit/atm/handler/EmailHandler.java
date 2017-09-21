package com.dayuanit.atm.handler;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.dayuanit.atm.controller.BaseController;
import com.dayuanit.atm.domain.Card;
import com.dayuanit.atm.domain.DelayTransfer;
import com.dayuanit.atm.domain.User;
import com.dayuanit.atm.mapper.CardMapper;
import com.dayuanit.atm.mapper.UserMapper;

@Component
public class EmailHandler extends BaseController implements InitializingBean {
	
	private final static Logger log = LoggerFactory.getLogger(EmailHandler.class);
	
	private final static ConcurrentLinkedQueue<DelayTransfer> queue = new ConcurrentLinkedQueue<DelayTransfer>();

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SimpleMailMessage templateMessage;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CardMapper cardMapper;
	

	public void sendEmail() {
		while (true) {
			log.info("----begin send email--------");
			DelayTransfer transfer = queue.poll();
			if (null == transfer) {
				try {
					log.info("---- send email sleep--------");
					Thread.sleep(5000);
				} catch (InterruptedException e) {

				}
				continue;
			}
			
			SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
			
			Card exitcard = cardMapper.getCardByCardNum(transfer.getOutCardNum());
			User exitUser = userMapper.queryById(exitcard.getUserId());
			
			msg.setTo(exitUser.getEmail());
			msg.setText("尊敬的用户, 转入卡号为" + transfer.getInCardNum() + "，   您的转账已经到位,请注意查收");
			mailSender.send(msg);
			log.info("---------send email over------------");
		}
	}
	
	public void submit(DelayTransfer transfer) {
		queue.offer(transfer);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				sendEmail();
			}
		}).start();
	}


}
