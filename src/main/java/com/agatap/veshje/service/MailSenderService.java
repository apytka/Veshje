package com.agatap.veshje.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailContentService mailContentService;

    public void sendMail(String to,
                         String subject,
                         String text,
                         boolean isHtmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        String content = mailContentService.contentBuilder(text);
        mimeMessageHelper.setText(content, isHtmlContent);
        javaMailSender.send(mimeMessage);
    }
}