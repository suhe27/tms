package com.intel.cid.utils;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailHelper {

	//public static final String SMTP_SERVER = "smtp.intel.com";
	public static final String SMTP_SERVER = "10.7.209.21";
	//public static final String SMTP_SERVER = "mailhost.tfn.com";
	
	public static void sendHtmlMail(String subject, String content,
			String sender, String receiver,Map <String,File> fileList) throws MessagingException {
		 
		sender = "tms@intel.com";

		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		senderImpl.setHost(MailHelper.SMTP_SERVER);
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true);
		InternetAddress[] iaToList;
		iaToList = InternetAddress.parse(receiver);
		mailMessage.setRecipients(Message.RecipientType.TO, iaToList);
		mailMessage.setSentDate(new Date());
		messageHelper.setFrom(sender);
		messageHelper.setSubject(subject);
		messageHelper.setText(content, true);
		if(null !=fileList && fileList.size()>0){
			Iterator iter = fileList.keySet().iterator();
			while(iter.hasNext()){
				String key =  (String)iter.next();
				File file = fileList.get(key);
				messageHelper.addAttachment(key, file); 
			}
		}
		Properties prop = new Properties();
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
		senderImpl.send(mailMessage);
	}
	
	public static void sendMailNew(String subject, String content, String receiver) throws MessagingException {
		 
		String sender = "tms@intel.com";

		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		senderImpl.setHost(MailHelper.SMTP_SERVER);
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true);
		InternetAddress[] iaToList;
		iaToList = InternetAddress.parse(receiver);
		mailMessage.setRecipients(Message.RecipientType.TO, iaToList);
		mailMessage.setSentDate(new Date());
		messageHelper.setFrom(sender);
		messageHelper.setSubject(subject);
		messageHelper.setText(content, true);

		Properties prop = new Properties();
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
		senderImpl.send(mailMessage);
	}
}