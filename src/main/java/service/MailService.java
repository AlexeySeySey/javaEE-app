package service;

import java.util.Properties;
import constant.Mail;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


public final class MailService {

	public synchronized void sendMail(String to, String subject, String text) throws Exception {

		Properties properties = System.getProperties();
		properties.setProperty("mail.transport.protocol", "smtp"); 
		properties.setProperty("mail.smtp.host", Mail.HOST.get());
		properties.put("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.port", "587");
		properties.put("mail.smtp.starttls.required", "true");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		properties.put("mail.smtp.auth", "true"); 
		properties.put("mail.from", Mail.FROM.get());
		Session session = Session.getInstance(properties,
		          new Authenticator() {
		            protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(Mail.FROM.get(), Mail.PASSWD.get());
		            }
		          });
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(Mail.FROM.get()));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		message.setText(text);

		Transport.send(message);
	}
}
