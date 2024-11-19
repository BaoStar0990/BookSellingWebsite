package mail;

import java.security.SecureRandom;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author hadan
 */
public class Mail {

    //
    private static final String fromEmail = "naoki8386maidinh@gmail.com";
    private static final String password = "oiwy fiut lsgy pbyy";
    public static boolean sendMail(String to,String subject, String body, boolean bodyIsHTML) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail,password);
            }
        });

        session.setDebug(true);

        Message message = new MimeMessage(session);
        try {
            Address fromAddress = new InternetAddress(fromEmail);
            Address toAddress = new InternetAddress(to);

            message.setFrom(fromAddress);
            message.setRecipient(Message.RecipientType.TO, toAddress);
            if (bodyIsHTML) {
                message.setContent(body, "text/html");
            } else {
                message.setText(body);
            }

            message.setSubject(subject);
            if (bodyIsHTML) {
                message.setContent(body, "text/html");
            } else {
                message.setText(body);
            }
            Transport.send(message);
            System.out.println("Send");
            return true;

        } catch (AddressException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    /*
    * Generate code
    * */
    public static String generatedCode(){
        //Generated code
        String characters = "ABCDEFGHIKLMNPQabcdefghiklmnpq1234567890!@#$%^&*()";
        SecureRandom random = new SecureRandom(); // for random
        int length;
        int min_length = 6;  // min length of code
        int max_length = 10; // max length of code
        length = random.nextInt((max_length - min_length) + 1) + min_length;
        StringBuilder code = new StringBuilder(length); //code send to customer
        for(int i = 0; i < length; i++){
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }
    /*
    - Send code reset password to customer
     */
    public static boolean sendCodeToCustomer(String toEmail,String code){
        //Send to customer
        boolean checkSendFinish = false;
        checkSendFinish =  Mail.sendMail(toEmail,"Your code: ",code.toString(),false);
        return checkSendFinish;
    }

}
