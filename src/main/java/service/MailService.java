package service;

import java.util.Properties;
import constant.Mail;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.Message;
import javax.mail.Transport;

public final class MailService {

	public void sendMail(String to, String subject, String text) throws Exception {

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", Mail.HOST.get());
		Session session = Session.getDefaultInstance(properties);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(Mail.FROM.get()));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		message.setText(text);

		Transport.send(message);
	}
}
