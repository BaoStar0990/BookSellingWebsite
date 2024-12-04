package test;

import dbmodel.BillDB;
import mail.Mail;
import model.Bill;

public class TestMail {
    public static void main(String[] args) {
        String code = Mail.generatedCode();
//        Mail.sendCodeToCustomer("hadangquang1408@gmail.com",code);
        Bill order = BillDB.getInstance().selectByID(97);
        
        if(Mail.sendOrderToCustomer("22110156@student.hcmute.edu.vn", order, order.getOrderDetails()))
            System.out.println("Gửi mail thành công");
        else
            System.out.println("Lỗi gửi mail");
    }
}
