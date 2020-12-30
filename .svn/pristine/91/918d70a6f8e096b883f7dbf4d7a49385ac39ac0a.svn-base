/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.chunqiu.mrjuly.common.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Properties;


/**
 * 发送电子邮件
 */
public class EmailUtils {

	private final static Logger logger = LoggerFactory.getLogger(EmailUtils.class);

	/**
	 * 发送邮件 可包含附件
	 * @param fileName   文件名
	 * @param emailAddress 发送地址
	 * @throws GeneralSecurityException
	 * @throws UnsupportedEncodingException
	 */
	public static void mailtest(String fileName, List<String> emailAddress) throws GeneralSecurityException, UnsupportedEncodingException {

		if(!emailAddress.isEmpty()) {
			// 发件人电子邮箱
			String from = "1980242157@qq.com";

			// 指定发送邮件的主机为 smtp.qq.com
			String host = "smtp.qq.com"; //QQ 邮件服务器

			// 获取系统属性
			Properties properties = System.getProperties();

			// 设置邮件服务器
			properties.setProperty("mail.smtp.host", host);

			properties.put("mail.smtp.auth", "true");
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.ssl.socketFactory", sf);
			// 获取默认session对象
			Session session = Session.getDefaultInstance(properties,new Authenticator(){
				public PasswordAuthentication getPasswordAuthentication()
				{  //qq邮箱服务器账户、第三方登录授权码
					return new PasswordAuthentication("1980242157@qq.com", "mclpqwzyfjrafbae"); //发件人邮件用户名、密码
				}
			});

			try{
				// 创建默认的 MimeMessage 对象
				MimeMessage message = new MimeMessage(session);

				// Set From: 头部头字段
				message.setFrom(new InternetAddress(from));

				// Set Subject: 主题文字
				message.setSubject("测试邮件结果");

				// 创建消息部分
				BodyPart messageBodyPart = new MimeBodyPart();

				// 消息
				messageBodyPart.setText("你好：这是*********************");

				// 创建多重消息
				Multipart multipart = new MimeMultipart();

				// 设置文本消息部分
				multipart.addBodyPart(messageBodyPart);

				if(fileName !=null && !"".equals(fileName)){
					// 附件部分
					messageBodyPart = new MimeBodyPart();
					//设置要发送附件的文件路径
					String filename = "E:/chunqiu/chen/"+ fileName;
					DataSource source = new FileDataSource(filename);
					messageBodyPart.setDataHandler(new DataHandler(source));

					messageBodyPart.setFileName(filename);
					//处理附件名称中文（附带文件路径）乱码问题
					messageBodyPart.setFileName(MimeUtility.encodeText(filename));
					multipart.addBodyPart(messageBodyPart);
				}

				// 发送完整消息
				message.setContent(multipart );

				for(int i = 0; i < emailAddress.size(); i++) {
					String to = emailAddress.get(i);
					// Set To: 头部头字段
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					// 发送消息
					Transport.send(message);
					System.out.println("Sent message successfully....");
				}
//            mailtest mailtest = new mailtest();
//            mailtest.exportExcel();
			}catch (MessagingException mex) {
				mex.printStackTrace();
			}
		}
	}


	public static void main(String[] args) throws GeneralSecurityException, UnsupportedEncodingException {
		/*List<String> emailAddress=new ArrayList<String>();
		String emailParam1="643374738@qq.com";
		emailAddress.add(emailParam1);
		mailtest(null,emailAddress);*/
	}

}
