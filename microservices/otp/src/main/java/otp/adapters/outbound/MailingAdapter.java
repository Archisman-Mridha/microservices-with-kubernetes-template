package otp.adapters.outbound;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import otp.ports.outbound.MailingPort;

public class MailingAdapter implements MailingPort {
    private Session session;

    public MailingAdapter( ) {

        var properties= new Properties( );

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable","true");    
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.port", "587");   

        var authenticator= new javax.mail.Authenticator( ) {

            @Override
            protected PasswordAuthentication getPasswordAuthentication( ) {
                return new javax.mail.PasswordAuthentication("archismanmridha12345@gmail.com", "zuhgbzhlrqnlavsb");
            }
        };

        this.session= Session.getInstance(properties, authenticator);
        this.session.setDebug(true);
    }

    public Boolean sendMail(String address, String content) {
        try {
            var message= new MimeMessage(session);
            message.setFrom(new InternetAddress("archismanmridha12345@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(address));
            message.setSubject("OTP Verification for `Microservices with Kubernetes Template` app");
            message.setText(content);

            Transport.send(message);

            return true;
        }
            catch(MessagingException error) {
                System.out.println("error sending mail : " + error.getMessage( ));

                return false;
            }
    }

}