package com.core.api.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

public class SMTPEmailService extends AbstractEmailService
{
	private static final Logger LOG = LoggerFactory.getLogger(SMTPEmailService.class);

	@Autowired
	private MailSender mailSender;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendEmail(SimpleMailMessage msg)
	{
		LOG.info("Simulando envio de email...");
		LOG.info(msg.toString());
		mailSender.send(msg);
		LOG.info("Email enviado");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg)
	{
		LOG.info("Simulando envio de email...");
		LOG.info(msg.toString());
		javaMailSender.send(msg);
		LOG.info("Email enviado");
	}
}
