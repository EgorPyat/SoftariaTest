package ru.nsu.pyataev.softariatest.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Email {
    public static void sendByHostProvider(Session session, String to, String subject, String text){
        try {
            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setFrom(new InternetAddress(session.getProperty("username")));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static Session createAuthSession(Properties props){
        Session session;
        session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(props.getProperty("username"), props.getProperty("password"));
                    }
                });
        return session;
    }

    public static Properties getProperties(InputStream stream){
        Properties props = new Properties();

        try{
            props.load(stream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return props;
    }
}
