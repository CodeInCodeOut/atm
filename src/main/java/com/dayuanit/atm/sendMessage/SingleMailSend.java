package com.dayuanit.atm.sendMessage;

import javax.mail.MessagingException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SingleMailSend {
	
	static ApplicationContext actx = new ClassPathXmlApplicationContext(
			"/spring/spring-mail2.xml");
	static MailSender sender = (MailSender) actx.getBean("mailSender");
	static SimpleMailMessage mailMessage = (SimpleMailMessage) actx.getBean("mailMessage");
	public static void main(String args[]) throws MessagingException {
		
		mailMessage.setSubject("dayuan");
		mailMessage.setText("hello");
		mailMessage.setTo("wangxuetaobuzhidao@163.com");
		sender.send(mailMessage);
	}

}
