/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;

import Utils.authentication.CSRFUtil;
import Utils.authentication.Regex;
import dbmodel.CustomerDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Customer;
import PasswordUtil.PasswordUtil;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author hadan
 */
@WebServlet(name = "SigninController", urlPatterns = {"/signin"})
public class SigninController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null) {
            String url = "/signin.jsp";
            request.getServletContext().getRequestDispatcher(url).forward(request, response);

        }else{
            response.sendRedirect("/home");
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String alertMessage = "";
        HttpSession session = request.getSession();
        //Lấy email
        String email = request.getParameter("email");
        if (!Regex.isEmailByRegex(email)) { // nếu email không đúng format
            alertMessage = "Vui lòng nhập lại email của bạn";
            request.setAttribute("alertMessage", alertMessage);

            request.getServletContext().getRequestDispatcher("/signin.jsp").forward(request, response);
        }
        else
        {
            String password = request.getParameter("password");
            String rememberMe = request.getParameter("rememberMe");
            String csrfToken = null;
            if (session.getAttribute("csrfToken") == null) { // Lần đăng nhập đầu tiên
                Customer customer = loginCustomer(email, password);
                if (customer != null) { // đăng nhập đúng
                    csrfToken = CSRFUtil.getCSRFToken();
                    request.setAttribute("csrfToken", csrfToken);
                    System.out.println("First request" + "email: " + email + "password: " 
                            + customer.getPassword() + "csrfToken: " + csrfToken);

                    //Set customer trong session
                    session.setAttribute("csrfToken", csrfToken);
                    session.setAttribute("user", customer);

                    //Sử dụng cookie lưu người dùng
                    if (rememberMe != null) {
                        if (rememberMe.equalsIgnoreCase("on")) {
                            Cookie cookieEmail = new Cookie("email", email);
                            Cookie cookiePassword = new Cookie("password", customer.getPassword());
                            Cookie cookieCsrfToken = new Cookie("csrfToken", csrfToken);

                            // Cookie tồn tại trong 30 ngày
                            cookieEmail.setMaxAge(30 * 24 * 60 * 60);
                            cookiePassword.setMaxAge(30 * 24 * 60 * 60);
                            cookieCsrfToken.setMaxAge(30 * 24 * 60 * 60);


                            response.addCookie(cookieEmail);
                            response.addCookie(cookiePassword);
                            response.addCookie(cookieCsrfToken);

                        }
                    }
                    //Chuyen trang
                    response.sendRedirect(String.format("%s/", getServletContext().getContextPath()));
                } else { //Nếu login không thành công
                    alertMessage = "Sai tài khoản hoặc mật khẩu";
                    request.setAttribute("alertMessage", alertMessage);
                    request.getServletContext().getRequestDispatcher("/signin.jsp").forward(request, response);
                }
            } else
            {
                response.sendRedirect("/home");
            }
        }

    }

    /*
    Lúc login sẽ set đối tượng customer và csrf trong session và csrf token trong request.
    * */
    private Customer loginCustomer(String email, String password) {
//       Customer  customer =   CustomerDB.getInstance().selectCustomerByEmailPassWord(email, password);
        Customer  customer =   CustomerDB.getInstance().selectCustomerByEmail(email);
        if(customer != null) {
            try {
                // lấy salt của customer
                String salt = customer.getSalt();
                String passHash = PasswordUtil.HashPassword(password + salt);
                if(passHash.equalsIgnoreCase(customer.getPassword()))
                    return customer;
            } catch (NoSuchAlgorithmException e) {
                System.out.println(e);
                return null;
            }
        }
        return null;
    }

    private boolean loginAdmin(String email, String password, HttpServletRequest request, HttpServletResponse response) {
        return false;
    }
}
