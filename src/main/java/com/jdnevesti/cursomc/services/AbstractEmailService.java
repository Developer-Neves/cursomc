package com.jdnevesti.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.jdnevesti.cursomc.domain.Cliente;
import com.jdnevesti.cursomc.domain.Pedido;


public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override // confirmar pedido email TEXTO
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}
    
	// Montando um email no formato Texto
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	// Criando o contexto do email no formato HTML
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEngine.process("email/confirmacaoPedido", context);		
	}
	
	
	@Override // confirmar pedido email HTML
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}
	}
	
	// Montando um email no formato HTML
	private MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
	    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	    MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail());
	    mmh.setFrom(sender);
	    mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
	    mmh.setSentDate(new Date(System.currentTimeMillis()));
	    mmh.setText(htmlFromTemplatePedido(obj), true);
	    
		return mimeMessage;
	}
	
	// Solicitação de nova senha
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage np = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(np);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage np = new SimpleMailMessage();
		np.setTo(cliente.getEmail());
		np.setFrom(sender);
		np.setSubject("Solicitação de nova senha");
		np.setSentDate(new Date(System.currentTimeMillis()));
		np.setText("Nova senha: " + newPass);
		return np;
	}
}
