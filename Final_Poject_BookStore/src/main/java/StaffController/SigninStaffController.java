/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package StaffController;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author hadan
 */
@WebServlet(name = "SigninStaffController", urlPatterns = {"/signin/manage/staff"})
public class SigninStaffController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------------------------");
        System.out.println("Call servlet login staff with post method");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.sendRedirect(String.format("%s/staff/staffcategory", getServletContext().getContextPath()));
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
