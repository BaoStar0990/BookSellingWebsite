package mail;

import java.security.SecureRandom;
import java.util.Properties;
import java.util.Set;
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
import model.Bill;
import model.OrderDetail;

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
                message.setContent(body, "text/html; charset=UTF-8");
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
    
    public static boolean sendOrderToCustomer(String toEmail, Bill order, Set<OrderDetail> orderDeatails){
        if(order != null){
            String subject = "Hóa đơn mua hàng từ Naoki";
            // tính tổng tiền
            double totalCost = order.getOrderDetails().stream()
                            .mapToDouble(o -> o.getTotalPrice())
                            .sum();
            StringBuilder dsSanPham = new StringBuilder();
            orderDeatails.stream()
                    .forEach(o ->{
                        String chiTietSP = """
                                           <tr>
                                                <td style="padding: 8px;">""" + o.getBook().getTitle()+ """
                                                                                  </td>
                                                <td style="padding: 8px; text-align: center;">""" + o.getQuantity()+ """
                                                                                                      </td>
                                                <td style="padding: 8px; text-align: right;">""" + o.getUnitPrice()+ """
                                                                                                     VND</td>
                                                <td style="padding: 8px; text-align: right;">""" + o.getTotalPrice()+ """
                                                                                                     VND</td>
                                            </tr>
                                           """;
                        dsSanPham.append(chiTietSP);
                    });

            // Nội dung HTML của hóa đơn
            String body = """
                <h2 style="text-align: center;">Hóa đơn mua hàng</h2>
                <div style="text-align: center;">
                    <img src="https://firebasestorage.googleapis.com/v0/b/imageofbookandauthor.appspot.com/o/logo-1.png?alt=media" alt="Logo Naoki" style="width: 150px; margin-bottom: 20px;">
                </div>
                <p>Cảm ơn bạn đã mua sắm tại Naoki. Dưới đây là thông tin hóa đơn của bạn:</p>
                <p><strong>Mã hóa đơn: </strong>""" + order.getId() + 
                """
                </p>
                <p><strong>Tổng tiền: </strong>""" + totalCost + """
                                                       VND</p>
                <p><strong>Phí vận chuyển: </strong>""" + order.getShippingFee() + """
                                                           VND</p>
                <p><strong>Trạng thái: </strong>""" + order.getStatusOrder()+ """
                                                       </p>
                <h3>Danh sách sản phẩm: </h3>
                <table border="1" style="border-collapse: collapse; width: 100%;">
                    <tr>
                        <th style="padding: 8px;">Tên sản phẩm</th>
                        <th style="padding: 8px;">Số lượng</th>
                        <th style="padding: 8px;">Đơn giá</th>
                        <th style="padding: 8px;">Tổng</th>
                    </tr>
            """ 
                + dsSanPham + 
            """
                </table>
                <p>Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi qua email hoặc hotline.</p>
                <p>Trân trọng,</p>
                <p>Đội ngũ hỗ trợ khách hàng</p>
            """;
            return sendMail(toEmail, subject, body, true);
        }
        else
            return false;
    }
}
