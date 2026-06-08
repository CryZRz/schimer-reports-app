package com.schimer.reportsapp.services.email;

import com.schimer.reportsapp.domain.entities.EmailAccountEntity;
import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.util.Properties;

public class EmailService {

    public void sendReport(String email, String subject, String body, EmailAccountEntity account, File file) {
        var props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", account.getUrl());
        props.put("mail.smtp.port", account.getPort());
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        //TODO hashear esta info xd!!!
        var authenticator = getAuthenticator(account.getEmail(), account.getPassword());
        var session = Session.getInstance(props,authenticator);

        try{
            var message = new MimeMessage(session);
            message.setFrom(new InternetAddress(account.getEmail()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);

            var multipart = new MimeMultipart();
            var textBody = new MimeBodyPart();
            textBody.setContent(body, "text/html; charset=utf-8");
            multipart.addBodyPart(textBody);

            if (file != null && file.exists()) {
                var attachmentPart = new MimeBodyPart();
                var source = new FileDataSource(file);
                attachmentPart.setDataHandler(new DataHandler(source));
                attachmentPart.setFileName(file.getName());
                multipart.addBodyPart(attachmentPart);
            }

            message.setContent(multipart);
            Transport.send(message);
        }catch(MessagingException e){
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo electronico");
        }
    }


    private Authenticator getAuthenticator(String username, String password) {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
    }
}
