package com.fprojects.articles_list;

import com.fprojects.articles_list.services.EMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@SpringBootApplication
@EnableScheduling
public class ArticlesListApplication {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private EMailService eMailService;

	public static void main(String[] args) {
		SpringApplication.run(ArticlesListApplication.class, args);
	}

	@Scheduled(fixedDelay = 5000)
	public void receiveEmails() throws MessagingException {
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
	}

}
