package com.example.insuranceregistrationsystem.service;

import com.example.insuranceregistrationsystem.exception.InsuranceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    /********************************************************************************************************************************
     * Ability to send a otpNumber to given emailId for emailVerification
     * @param mail
     * @param otpNumber
     *******************************************************************************************************************************/

    public void sendEmail(String mail, int otpNumber) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("immanuveljeeva2000@gmail.com");
            message.setTo(mail);
            message.setSubject("Email Verification ....");
            message.setText("Verification code : " + otpNumber);
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
}
