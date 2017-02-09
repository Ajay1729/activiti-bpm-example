package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by aloha on 09-Feb-17.
 */
@Service
public class MailService{

    @Autowired
    JavaMailSender javaMailSender;


    public void sendMail(String recieverMail, String message){

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("mydummyemail007@gmail.com");
            messageHelper.setTo(recieverMail);
            messageHelper.setSubject("Doktorat Aktivnost");
            messageHelper.setText(message);
        };
        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
        }
    }


}
