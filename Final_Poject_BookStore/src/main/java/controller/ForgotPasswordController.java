package controller;

import dbmodel.CustomerDB;
import firebasecloud.FirebaseStorageUploader;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import mail.Mail;
import model.Customer;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@WebServlet("/forgotpasword")
public class ForgotPasswordController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println(action);
        String url = "";
        String code = "";
        PrintWriter out = response.getWriter();
        if(action != null) {
            if (action.equalsIgnoreCase("PressedForgotPassword"))// Khi moi nhan nut quen mat khau
            {
                url = "/forgotpassword.jsp";
                request.getRequestDispatcher(url).forward(request, response);
            } else if (action.equalsIgnoreCase("RequestCode")) { // Khi da nhap email de reset mat khau
                String email = request.getParameter("email");
                Customer customer = CustomerDB.getInstance().checkExistEmail(email);
                if (customer != null) { // neu ton tai email
                    HttpSession session = request.getSession();
                    session.setAttribute("customer", customer);

                    code = Mail.generatedCode();
                    Mail.sendCodeToCustomer(email, code);

                    //cho nay khong bao mat !!!
                        // luu trong session de chuyen trang lay code ra so sanh
                    session.setAttribute("code",code);
                    //Chuyen trang
                    url = "/enteringcoderesetpassword.jsp";
                    request.getRequestDispatcher(url).forward(request, response);
                } else { // neu khong ton tai
                    out.println("Email not exist");
                }
            } else if (action.equalsIgnoreCase("EnteredCodeResetPassword")) { // Khi da nhap code gui ve email
                 String codeEntered = request.getParameter("code");

                 //Lay code luu trong session
                 HttpSession session = request.getSession();
                 String codeInSession = String.valueOf(session.getAttribute("code"));
                 //So sanh code

                 if(codeEntered.equals(codeInSession)) {
                     // chuyen den trang nhap mat khau moi
                     url = "/enteringnewpassword.jsp";
                     request.getRequestDispatcher(url).forward(request, response);
                 }else{
                     out.println("Code not match");
                 }
            } else if(action.equalsIgnoreCase("EnteredCodeNewPassword")){ // khi da nhap mat khau moi
                String newPassword = request.getParameter("password");
                /*
                Chỗ này cần phải xử lý theo hướng lấy Customer, sau đó update password cho customer
                **/
                HttpSession session = request.getSession();
                Customer customer = (Customer) session.getAttribute("customer");
                try{
                    customer.setPassword(newPassword);
                    CustomerDB.getInstance().updateCustomer(customer);
                    out.println("Cập nhật thành công");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }

}

