package tcb.shms.core.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	private final static Logger log = LogManager.getLogger(EmailService.class);

	public static void main(String[] args) {
	      // Recipient's email ID needs to be mentioned.
	      String to = "mark3835@tcb-bank.com.tw";

	      // Sender's email ID needs to be mentioned
	      String from = "test1234@tcb-bank.com.tw";

	      // Assuming you are sending email through relay.jangosmtp.net
	      String host = "172.29.3.30";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "false");
	      props.put("mail.smtp.starttls.enable", "false");
          props.put("mail.smtp.auth.plain.disable", "true");
          props.put("mail.smtp.ehlo", "false");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "25");

	      // Get the Session object.
	      Session session = Session.getInstance(props
//	    		  ,
//	         new javax.mail.Authenticator() {
//	            protected PasswordAuthentication getPasswordAuthentication() {
//	               return new PasswordAuthentication(username, password);
//		   }
//	         }
	      );

	      try {
		   // Create a default MimeMessage object.
		   Message message = new MimeMessage(session);
		
		   // Set From: header field of the header.
		   message.setFrom(new InternetAddress(from));
		
		   // Set To: header field of the header.
		   message.setRecipients(Message.RecipientType.TO,
	               InternetAddress.parse(to));
		
		   // Set Subject: header field
		   message.setSubject("Testing Subject");
		
		   // Now set the actual message
		   message.setText("Hello, this is sample for to check send " +
			"email using JavaMailAPI ");

		   // Send message
		   Transport.send(message);

		   System.out.println("Sent message successfully....");

	      } catch (MessagingException e) {
	         throw new RuntimeException(e);
	      }
	   }
	
	
	public boolean mailSend(String to, String subject, String content) {
		boolean result = false;
		
		
		return result;
	}
}
