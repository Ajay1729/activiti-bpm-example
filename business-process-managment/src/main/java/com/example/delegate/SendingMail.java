package com.example.delegate;

import com.example.service.MailService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static com.example.constants.Constants.SEF_VECA_FAKULTETA;

/**
 * Created by aloha on 09-Feb-17.
 */
@Service
public class SendingMail implements JavaDelegate {


    //@Autowired
    //MailService mailService;

    Expression userIdExp;
    Expression titleExp;
    Expression msgExp;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String userId = (String) userIdExp.getValue(delegateExecution);
        String title = (String) titleExp.getValue(delegateExecution);
        String msg = (String) msgExp.getValue(delegateExecution);

        System.out.println("SENDING MAIL TO USER WITH ID:" + userId);

        //------------------------------------------------------------------------
        //INJECTION DOESNT WORK WITH ACTIVITI DELEGATE CLASS
        //mailService.sendMail("radomir.aloha@gmail.com","FROM DELEGATEEEEEEE");
        //------------------------------------------------------------------------

        //SO I HAVE TO SEND eMAIL LIKE AN ANIMAL
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("mydummyemail007@gmail.com","dummyprovider");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@no-spam.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("radomir.aloha@gmail.com"));
            message.setSubject(title);
            message.setText(msg +
                    "\n\n  ");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }



    }


}
