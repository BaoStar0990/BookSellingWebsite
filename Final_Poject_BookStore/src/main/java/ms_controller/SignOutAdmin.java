package ms_controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
@WebServlet(name = "SignOutAdmin",urlPatterns ="/signoutadmin")
public class SignOutAdmin extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        //remove tài khoảng người dùng khỏi session
        session.removeAttribute("admin");

        //remove tài khoảng người dùng khỏi cookie vì người dùng đã logout
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
                if (cookie.getName().equals("password")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        response.sendRedirect(String.format("%s/", getServletContext().getContextPath()));
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        processRequest(request, response);
    }
}
