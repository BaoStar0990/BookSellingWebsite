/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package StaffController;

import dbmodel.LogSigninStaffDB;
import dbmodel.StaffDB;
import dbmodel.StaffDB;
import dbmodel.LogSigninStaffDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.LogSinginStaff;
import model.Staff;
import model.Staff;
import model.LogSinginStaff;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 *
 * @author hadan
 */
@WebServlet(name = "SignoutStaffController", urlPatterns = {"/staff/signoutstaff"})
public class SignoutStaffController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        LogSinginStaff log = (LogSinginStaff) session.getAttribute("log");
        try {
            if (staff != null && log != null) {
                staff.setStatus(false);
                StaffDB.getInstance().update(staff);
                session.removeAttribute("staff");
                session.removeAttribute("log");
                LocalDateTime timeout = LocalDateTime.now();
                log.setTimeout(timeout);
                LogSigninStaffDB.getInstance().update(log);
                System.out.println("Invalid format for latitude or longitude.");
                request.getServletContext().getRequestDispatcher("/signinstaff.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
