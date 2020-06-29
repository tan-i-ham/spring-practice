package com.hannah.amazingtalkerhw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class SendMailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendRegisterSuccessMail(String destinationEmail) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("no-reply@amazingtalker.com");
        msg.setTo(destinationEmail);

        msg.setSubject("Hello from Amazing talker");
        msg.setText("Register Success");

        javaMailSender.send(msg);
    }
}
