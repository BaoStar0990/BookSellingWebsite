/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import PasswordUtil.PasswordUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import Utils.authentication.Regex;
import dbmodel.CustomerDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

import model.Bill;
import model.Customer;
import model.StatusOrder;

/**
 *
 * @author hadan
 */
@WebServlet(name = "SignupController", urlPatterns = {"/signup"})
public class SignupController extends HttpServlet {

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
       request.getServletContext().getRequestDispatcher("/signup.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //set UTF8 - Tiếng việt
        request.setCharacterEncoding("UTF-8");

        String alertMessage = "";

        String email = request.getParameter("email");
        String numberPhone = request.getParameter("numberPhone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullname = request.getParameter("fullName");
        String dateStr = request.getParameter("dob"); // Chuỗi dạng yyyy-MM-dd

        //Set for display on form register
        request.setAttribute("email", email);
        request.setAttribute("numberPhone", numberPhone);
        request.setAttribute("password", password);

        request.setAttribute("alertMessage", alertMessage);
        request.setAttribute("fullName", fullname);
        request.setAttribute("dateStr", dateStr);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateOfBirth = LocalDate.parse(dateStr, formatter);
        //Check input invalid
        if (dateOfBirth.isAfter(LocalDate.now())) {
            System.out.println(LocalDate.now());
            alertMessage = "Ngày thàng năm sinh không hợp lệ";
            request.setAttribute("alertMessage", alertMessage);
            doGet(request, response);
        } else if (Regex.isEmailByRegex(email) == false) {
            alertMessage = "Email không hợp lệ, vui lòng nhập lại email";
            request.setAttribute("alertMessage", alertMessage);
            doGet(request, response);
        } else if (Regex.validPhoneNumber(numberPhone) == false) {
            alertMessage = "Số điện thoại không hợp lệ, vui lòng nhập số điện thoại khác";
            request.setAttribute("alertMessage", alertMessage);
            doGet(request, response);
        }else if(password.equals(confirmPassword) == false){
            alertMessage = "Mật khẩu không khớp";
            request.setAttribute("alertMessage", alertMessage);
            doGet(request, response);
        }else {
            Customer c = CustomerDB.getInstance().checkExistCustomerHaveEmail(email);
            if (c != null) {
                alertMessage = "Email đã tồn tại, vui lòng nhập email khác";
                request.setAttribute("alertMessage", alertMessage);
                doGet(request, response);
            } else {
                try {
                    // lấy salt 
                    String salt = PasswordUtil.getSalt();
                    // mã hóa mật khẩu
                    String passHash = PasswordUtil.HashPassword(password + salt);
                    //Email, numberphone,password, dob, fullname
                    Customer newCustomer = new Customer(passHash, salt, fullname, numberPhone, email, dateOfBirth);
                    if(CustomerDB.getInstance().insertCustomer(newCustomer)){
                        // tạo cart mới cho khách hàng

                        alertMessage = "Vui lòng nhập tài khoảng của bạn để đăng nhập";
                        request.setAttribute("alertMessage", alertMessage);
                        request.getRequestDispatcher("/signin.jsp").forward(request, response);
                    }else{
                        alertMessage = "Lỗi trong quá trình đăng kí, mời bạn đăng ký lại";
                        request.setAttribute("alertMessage", alertMessage);
                        doGet(request, response);
                    }
                }catch (NoSuchAlgorithmException ex) {
                    System.out.println(ex);
                    request.setAttribute("alertMessage", "Lỗi trong quá trình đăng ký.");
                }

            }
        }



    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
