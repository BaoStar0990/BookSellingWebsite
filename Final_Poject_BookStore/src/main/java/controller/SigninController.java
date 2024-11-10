/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpCookie;
import java.util.Currency;

import dbmodel.CustomerDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Customer;

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
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = "signin.jsp";
        if (session.getAttribute("user") != null) { //nếu đã đăng nhập rồi mà request đến signin
            url = "404error.jsp";
        }
        request.getRequestDispatcher(url).forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) { // Neu chua ton tai user trong cookie, noi dung hon la truoc do user chua truy cap hoac da truy cap roi dang xuat, khong luu tai khoang mat khau
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String username = request.getParameter("username");
            String rememberMe = request.getParameter("rememberMe");

            Customer customer = CustomerDB.getInstance().selectCustomerByEmailPassWord(email,password);
            if(customer != null) { // Neu email, tai khoang, mat khau dung
                //store information of user in session
                session.setAttribute("user", customer);

                //If customer want to remember password
                if(rememberMe != null) {
                    if(rememberMe.equalsIgnoreCase("on")){
                        Cookie cookieEmail = new Cookie("email",email);
                        Cookie cookiePassword = new Cookie("password",password);
                        //Cookie cookieUsername = new Cookie("username",username);

                        // Cookie tồn tại trong 30 ngày
                        cookieEmail.setMaxAge(30 * 24 * 60 * 60);
                        cookiePassword.setMaxAge(30 * 24 * 60 * 60);
                        //cookieUsername.setMaxAge(30*24*60*60);

                        response.addCookie(cookieEmail);
                        response.addCookie(cookiePassword);
                       // response.addCookie(cookieUsername);
                    }
                }

                //chuyeen trang
                response.sendRedirect("/home");
            }
            else{

                System.out.println("Customer not found");
            }
        }else{
            response.sendRedirect("/home");
        }

    }
}
