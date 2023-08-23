package ec.edu.espe.notification.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
	private JavaMailSender mailSender;
	
	public void  enviarCorreo(String email,String subject,String body) {
		SimpleMailMessage mensaje=new SimpleMailMessage();
		mensaje.setFrom("gpr.soporte.mec@gmail.com");
		mensaje.setTo(email);
		mensaje.setSubject(subject);
		mensaje.setText(body);
		mailSender.send(mensaje);
	}
}
