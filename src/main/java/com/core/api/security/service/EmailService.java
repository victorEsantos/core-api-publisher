package com.core.api.security.service;

import com.core.api.cliente.domain.Cliente;
import com.core.api.pedido.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService
{
	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);

	void sendOrderConfirmationHtmlEmail(Pedido obj);

	void sendHtmlEmail(MimeMessage msg);

    void sendNewPasswordEmail(Cliente cliente, String novaSenha);
}
