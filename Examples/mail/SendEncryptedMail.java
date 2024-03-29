package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.didisoft.pgp.mail.PGPMailLib;

/**
 * Demonstrates sending PGP/MIME encrypted emails
 *
 * Copyright (c) DidiSoft 2015
 */
public class SendEncryptedMail {
		
	public static void main(String[] args) {
		  // Recipient's email
	      String to = "recipient@company.com";
	      // Sender email
	      String from = "me@organization.com";

	      // SMTP host
	      String host = "mail.organization.com";
	      
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);
	      properties.setProperty("mail.smtp.port", "25");
	      
	      properties.setProperty("mail.user", from);
	      properties.setProperty("mail.password", "my SMTP password");	      

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("This is the Subject Line!");

	         // Now set the actual message
	         message.setText("This is actual message");
	         
	         // PGP encrypt here the message
	         PGPMailLib mailUtil = new PGPMailLib();
	         String publicKeyFileName = "examples/DataFiles/public.key";
	         MimeMessage pgpMessage = mailUtil.encryptMessage(session, message, publicKeyFileName);

	         // Send message
	         Transport.send(pgpMessage);
	         System.out.println("Sent message successfully....");
	      }catch (com.didisoft.pgp.PGPException ex) {
		     ex.printStackTrace();	         
	      }catch (java.io.IOException ex) {
		     ex.printStackTrace();	         
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	 }
}
