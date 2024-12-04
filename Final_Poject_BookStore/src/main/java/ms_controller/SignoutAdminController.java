/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ms_controller;

import dbmodel.AdminDB;
import dbmodel.LogSigninAdminDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Admin;
import model.LogSinginAdmin;

import java.io.IOException;
import java.time.LocalDateTime;

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
                LocalDateTime timeout = LocalDateTime.now();
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
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
