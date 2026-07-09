package com.horseracing.project3.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        try {
            // Note: Since we might use a dummy config, sending real mail might fail.
            // We log the OTP to console so it can be easily tested without a real inbox.
            logger.info("================================================");
            logger.info("SENDING EMAIL TO: {}", to);
            logger.info("SUBJECT: {}", subject);
            logger.info("BODY: \n{}", body);
            logger.info("================================================");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            // TẠM THỜI TẮT GỬI MAIL THẬT ĐỂ TRÁNH LỖI ĐỎ DO DÙNG EMAIL GIẢ
             mailSender.send(message);

        } catch (Exception e) {
            logger.error("Failed to send email to {}", to, e);
            // We don't rethrow to avoid blocking the API just because fake SMTP failed.
        }
    }
}
