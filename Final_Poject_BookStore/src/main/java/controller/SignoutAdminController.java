/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dbmodel.AdminDB;
import dbmodel.LogSigninAdminDB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import model.Admin;
import model.LogSinginAdmin;
import org.threeten.bp.LocalDate;

/**
 *
 * @author hadan
 */
@WebServlet(name = "SignoutAdminController", urlPatterns = {"/signout/manage/admin"})
public class SignoutAdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        LogSinginAdmin log = (LogSinginAdmin) session.getAttribute("log");
        try {
            if (admin != null && log != null) {
                admin.setStatus(false);
                AdminDB.getInstance().update(admin);
                session.removeAttribute("admin");
                session.removeAttribute("log");
                java.time.LocalDateTime timeout = java.time.LocalDateTime.now();
                log.setTimeout(timeout);
                LogSigninAdminDB.getInstance().update(log);
                System.out.println("Invalid format for latitude or longitude.");
                request.getServletContext().getRequestDispatcher("/signinadmin.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
