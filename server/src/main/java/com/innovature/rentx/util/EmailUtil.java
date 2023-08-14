package com.innovature.rentx.util;

import com.innovature.rentx.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

// public class EmailUtil {

//     private static final String MAIL_NOT_SEND = "mail.not.send";

//     private static final String MAIL_NOT_SEND_LOG = "mail not send";

//     Logger log = LoggerFactory.getLogger(EmailUtil.class);

//     @Value("${spring.mail.username}")
//     private String mailSender;

//     @Value("${spring.mail.password}")
//     private String passwordSender;

//     @Autowired
//     private LanguageUtil languageUtil;

//     public void sendEmail(String to, String subject, String content) {
//         String senderEmail = mailSender;
//         String senderPassword = passwordSender;
//         Properties properties = new Properties();
//         properties.put("mail.smtp.auth", "true");
//         properties.put("mail.smtp.starttls.enable", "true");
//         properties.put("mail.smtp.host", "smtp.gmail.com");
//         properties.put("mail.smtp.port", "587");
//         Session session = Session.getInstance(properties, new Authenticator() {
//             @Override
//             protected PasswordAuthentication getPasswordAuthentication() {
//                 return new PasswordAuthentication(senderEmail, senderPassword);
//             }
//         });
//         try {
//             MimeMessage msg = new MimeMessage(session);
//             msg.setFrom(new InternetAddress(senderEmail));
//             msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//             msg.setSubject(subject);
//             msg.setText(content);
//             Transport.send(msg);
//         } catch (MessagingException m) {
//             log.error(MAIL_NOT_SEND_LOG);
//             throw new BadRequestException(languageUtil.getTranslatedText(MAIL_NOT_SEND, null, "en"), m);
//         } catch (Exception e) {
//             log.error(MAIL_NOT_SEND_LOG);
//             throw new BadRequestException(languageUtil.getTranslatedText(MAIL_NOT_SEND, null, "en"), e);
//         }
//     }

// }
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EmailUtil {
    private static final String MAIL_NOT_SEND = "mail.not.send";

    private static final String MAIL_NOT_SEND_LOG = "mail not send";

    Logger log = LoggerFactory.getLogger(EmailUtil.class);

    @Value("${spring.mail.username}")
    private String mailSender;

    @Value("${spring.mail.password}")
    private String passwordSender;

    @Autowired
    private LanguageUtil languageUtil;

    public void sendEmail(String to, String subject, String content) {
        String senderEmail = mailSender;
        String senderPassword = passwordSender;
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(senderEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);

            MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true);
            messageHelper.setText(content, true);

            Transport.send(msg);
        } catch (MessagingException m) {
            log.error(MAIL_NOT_SEND_LOG);
            throw new BadRequestException(languageUtil.getTranslatedText(MAIL_NOT_SEND, null, "en"), m);
        } catch (Exception e) {
            log.error(MAIL_NOT_SEND_LOG);
            throw new BadRequestException(languageUtil.getTranslatedText(MAIL_NOT_SEND, null, "en"), e);
        }
    }

    public String generateVerificationEmailContent(String otp) {
        try {
            ClassPathResource resource = new ClassPathResource("email_template.html");
            String template = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            String content = template.replace("{{OTP}}", otp);
            return content;
        } catch (IOException e) {
            throw new BadRequestException(languageUtil.getTranslatedText(MAIL_NOT_SEND, null, "en"), e);

        }

    

    }
}
