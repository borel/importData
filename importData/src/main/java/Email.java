package main.java;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import main.ressources.Ressources;

public class Email {

	private Session session = null;
	private Transport transport = null;

	public void send(MessageData messageData) throws NoSuchProviderException, MessagingException {
			this.connect(Ressources.EMAIL_HOST, Ressources.EMAIL_USER,Ressources.EMAIL_PASSWORD);
			this.send(Ressources.EMAIL_USER, Ressources.EMAIL_DESTINATION,
					"Error in message : " + messageData.getSubject(),
					"Error in message : " + messageData.getPriority());
	
	}

	private void connect(String host, String user, String password)
			throws NoSuchProviderException, MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");
		// properties.setProperty("mail.smtp.port", "587");
		this.session = Session.getDefaultInstance(props, null);
		this.transport = this.session.getTransport();
		this.transport.connect(host, user, password);
	}

	private void send(String from, String to, String subject, String body)
			throws MessagingException {
		MimeMessage message = new MimeMessage(this.session);
		message.setSubject(subject);
		message.setContent(body, "text/plain");
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setFrom(new InternetAddress(from));
		this.transport.sendMessage(message, message
				.getRecipients(Message.RecipientType.TO));
		this.transport.close();
	}

}
