package controller;

import PasswordUtil.PasswordUtil;
import Utils.authentication.CSRFUtil;
import Utils.authentication.ExpiringToken;
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
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ForgotPasswordController", urlPatterns ="/forgotpassword")
public class ForgotPasswordController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Nếu trong session không tồn tại user thì mới quên mật khẩu được
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        System.out.println("call forgot password");
        if (session.getAttribute("user") == null) {


            System.out.println("-----------------------------------------------------");
            System.out.println("Bắt đầu phiên reset mật khẩu");
            String tokenAuthenticateForgotPassword = CSRFUtil.getCSRFToken();
            ExpiringToken tokenAuth = new ExpiringToken(tokenAuthenticateForgotPassword, 300, "tokenSessionResetPassword");
            session.setAttribute("tokenSessionResetPassword", tokenAuth);

            request.getServletContext().getRequestDispatcher("/enteremailtoresetpassword.jsp").forward(request, response);

        }
        else {
            System.out.println("redirect to home because it have signed in");
            request.setAttribute("alertMessage", "Vui lòng đăng xuất khỏi tài khoảng trước khi đăng nhập");
            response.sendRedirect("/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String url="";
        if(session.getAttribute("tokenSessionResetPassword") != null) {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            String action = request.getParameter("action");
            System.out.println(action);
            String code = "";
            PrintWriter out = response.getWriter();
            if (action != null) {
                if (action.equalsIgnoreCase("RequestCode")) { // Khi da nhap email de reset mat khau
                    String email = request.getParameter("email");
                    Customer customer = CustomerDB.getInstance().checkExistCustomerHaveEmail(email);
                    if (customer != null) { // neu ton tai email
                        session.setAttribute("customer", customer);
                        code = Mail.generatedCode();
                        if (Mail.sendCodeToCustomer(email, code) == true) {
                            ExpiringToken codeInSession = new ExpiringToken(code, 30, "code");
                            session.setAttribute("code", codeInSession);
                        }
                        //Chuyen trang
                        url = "/enteringcoderesetpassword.jsp";
                        request.getServletContext().getRequestDispatcher(url).forward(request, response);
                    } else { // neu khong ton tai
                        out.println("Email not exist");
                    }
                } else if(action.equalsIgnoreCase("ResendCodeResetPassword")) {
                    Customer customer = (Customer) session.getAttribute("customer");
                    if (customer != null) { // neu ton tai email
                        code = Mail.generatedCode();
                        if (Mail.sendCodeToCustomer(customer.getEmail(), code) == true) {
                            ExpiringToken codeInSession = new ExpiringToken(code, 30, "code");
                            session.setAttribute("code", codeInSession);
                        }
                        //Chuyen trang
                        url = "/enteringcoderesetpassword.jsp";
                        request.getServletContext().getRequestDispatcher(url).forward(request, response);
                    }
                }
                else if (action.equalsIgnoreCase("EnteredCodeResetPassword")) { // Khi da nhap code gui ve email
                    String codeEntered = request.getParameter("code");
                    //Lay code luu trong session
                    //neu session time out
                    if (session.getAttribute("code") != null) {
                        String codeInSession = ((ExpiringToken) session.getAttribute("code")).getValue();
                        //So sanh code
                        if (codeEntered.equals(codeInSession)) {
                            // chuyen den trang nhap mat khau moi
//                            doGet(request,response);
                            url = "/enteringnewpassword.jsp";
                            request.getServletContext().getRequestDispatcher(url).forward(request, response);
                        } else {
                            out.println("Code not match");
                        }
                    }
                    //neu session timeout tra ve trang dang nhap
                    else {
                        //chuyển lại trang nhập code
                        request.setAttribute("alertMessage", "Code hết hạn sử dụng, vui lòng thực hiện lại");
                        url = "/enteremailtoresetpassword.jsp";
                        request.getServletContext().getRequestDispatcher(url).forward(request, response);
                    }
                }
                else if (action.equalsIgnoreCase("EnteredCodeNewPassword")) { // khi da nhap mat khau moi
                    String newPassword = request.getParameter("password");
                    String confirmNewPassword = request.getParameter("confirmpassword");
                    if (newPassword.equals(confirmNewPassword)) {
//                    Chỗ này cần phải xử lý theo hướng lấy Customer, sau đó update password cho customer
                        Customer customer = (Customer) session.getAttribute("customer");
                        if (customer != null) {
                            try {
                                // lấy salt 
                                String salt = PasswordUtil.getSalt();
                                // mã hóa mật khẩu
                                String passHash = PasswordUtil.HashPassword(newPassword + salt);
                                customer.setPassword(passHash);
                                customer.setSalt(salt);
                                
                                if (CustomerDB.getInstance().updateCustomer(customer) == true) {
                                    //System.out.println("Hết phiên reset mật khẩu của bạn");
                                    request.setAttribute("alertMessage", "Reset mật khẩu thành công, vui lòng đăng nhập lại");
                                    url = "/signin.jsp";
                                    request.getServletContext().getRequestDispatcher(url).forward(request, response);
                                } else {
                                    out.println("Error when update password");
                                }
                            
                            }catch(NoSuchAlgorithmException ex) {
                                System.out.println(ex);
                                request.setAttribute("alertMessage", "Lỗi trong quá trình đặt lại mật khẩu.");
                           
                            } 
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            
                        }
                    }
                }

            }
        }else{
            System.out.println("Hết phiên reset mật khẩu của bạn");
            request.setAttribute("alertMessage", "Hết phiên reset mật khẩu của bạn");
            doGet(request,response);
        }
    }
}


