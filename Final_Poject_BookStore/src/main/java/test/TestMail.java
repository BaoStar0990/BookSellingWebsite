package test;

import mail.Mail;

public class TestMail {
    public static void main(String[] args) {
        String code = Mail.generatedCode();
        Mail.sendCodeToCustomer("hadangquang1408@gmail.com",code);
    }
}
