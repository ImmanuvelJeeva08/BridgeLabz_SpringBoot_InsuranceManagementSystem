package com.example.insuranceregistrationsystem.service;

import com.example.insuranceregistrationsystem.exception.InsuranceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public static String url = "http://localhost:8080/activate";
    String content="<a href='"+url+"'>"+"Click Me to activate Your Account"+"</a>";

    /********************************************************************************************************************************
     * Ability to send a otpNumber to given emailId for emailVerification
     * @param mail
     * @param subject
     * @param text
     *******************************************************************************************************************************/

    public void sendEmail(String mail, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("immanuveljeeva2000@gmail.com");
            message.setTo(mail);
            message.setSubject(subject);
            message.setText(text);
            message.setSentDate(new Date());
            javaMailSender.send(message);
        }catch (Exception e){
            throw new InsuranceException("Mail was not sent");
        }
    }

    /*******************************************************************************************************************************
     * Ability to send an email with attachment like photo,file to given emailId
     * @param mail
     ******************************************************************************************************************************/

    public void sendEmailwithAttachment(String mail) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setFrom("immanuveljeeva2000@gmail.com");
            messageHelper.setTo(mail);
            messageHelper.setSubject("Sucessful....");
            messageHelper.setText("Sucessfully Your Insurance Details Added");
            messageHelper.setSentDate(new Date());

            File file = new File("E:\\bhagatsing.png");

            messageHelper.addAttachment(file.getName(), file);

            javaMailSender.send(message);

        } catch (Exception e) {
            throw new InsuranceException("Mail was not sent with Attachment");
        }
    }

    public void sendLink(String emailId, String subject, String text){
        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            message.setFrom("immanuveljeeva2000@gmail.com");
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(emailId));

            message.setSubject(subject);
            // Create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setText(text + "\n" +content,"UTF-8","html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Put parts in message
            message.setContent(multipart);

            // Send the message
            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new InsuranceException("Message Not sent");
        }
    }
}
