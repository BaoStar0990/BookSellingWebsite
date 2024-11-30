/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dbmodel.CustomerDB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Customer;
import PasswordUtil.PasswordUtil;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author PC
 */
@WebServlet(name = "ChangePasswordController", urlPatterns = {"/changepassword"})
public class ChangePasswordController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //set UTF8 - Tiếng việt
        request.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        
         String url = "/usersetting.jsp?setting=change-password";
        // Lấy session
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null){
            url = "/signin.jsp";
            request.getServletContext().getRequestDispatcher(url).forward(request, response);
        }
        else{
            String newPass = request.getParameter("newPassword");
            Customer c = (Customer) session.getAttribute("user");
            if(c != null){
                try {
                    // kiểm tra pass nhập hiện tại đúng chưa
                    String currentPass = request.getParameter("currentPassword");
                    // lấy satl hiện tại
                    String currentSalt = c.getSalt();
                    String currentPassHash = PasswordUtil.HashPassword(currentPass + currentSalt);
                    if(currentPassHash.equalsIgnoreCase(c.getPassword())){ // nếu mật khẩu đúng
                        // lấy salt 
                        String salt = PasswordUtil.getSalt();
                        // mã hóa mật khẩu
                        String passHash = PasswordUtil.HashPassword(newPass + salt);
                        c.setSalt(salt); // lưu salt
                        c.setPassword(passHash); // lưu pass
        //                CustomerDB.getInstance().update(c);
                        // sau khi cập nhật xong đăng xuất tài khoản
                        if(CustomerDB.getInstance().update(c)){
                            response.sendRedirect(String.format("%s/signout", getServletContext().getContextPath()));
                        }
                        else
                            request.setAttribute("errorMessage", "Đổi mật khẩu không thành công!");
                    }
                    else
                        request.setAttribute("errorMessage", "Mật khẩu hiện tại không đúng.");
                }catch (NoSuchAlgorithmException ex) {
                    System.out.println(ex);
                    request.setAttribute("errorMessage", "Đổi mật khẩu không thành công!");
                }
            }
        }
        // chuyển trang

    }

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
//        processRequest(request, response);
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
        processRequest(request, response);
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
