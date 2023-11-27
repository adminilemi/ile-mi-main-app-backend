package com.thistlestechnology.ilemimainapp.serviceImplementation;

import com.thistlestechnology.ilemimainapp.dto.request.EmailRequest;
import com.thistlestechnology.ilemimainapp.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Async
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Override
    public void sendOTP(EmailRequest emailRequest) {
        try{
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, "Utf-8");
            mimeMessageHelper.setSubject("Email Verification One Time Password");
            mimeMessageHelper.setTo(emailRequest.getReceiver());
            mimeMessageHelper.setFrom("okoloebelechukwu93@gmail.com");
            mimeMessageHelper.setText(emailRequest.getMessage(), true);
            javaMailSender.send(mailMessage);
        } catch (MessagingException e){
            log.info("Problem 1: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (MailException e){
            log.info("Problem 2" + e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
