package org.example.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendOTPService {

    public static void sendOTP(String email, String otp) {
        String to = email;
        String from = "vyomkakani28@gmail.com";
        String host = "smtp.gmail.com";
        String port = "465";

        // Email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.ssl.enable", "true");

        // Creating a session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "izcr zvxp xubs kmdv"); // Use environment variable
            }
        });

        session.setDebug(true); // Enable debugging for troubleshooting

        try {
            // Create email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("File Enc OTP");
            message.setText("Your One Time Password for File Hider is " + otp);

            // Send the email
            System.out.println("Sending OTP to " + to);
            Transport.send(message);
            System.out.println("OTP sent successfully!");

        } catch (AddressException e) {
            System.err.println("Invalid email address: " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println("Error while sending email: " + e.getMessage());
        }
    }
}
