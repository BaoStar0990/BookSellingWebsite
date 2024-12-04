/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package ms_controller;

import java.io.*;

import dbmodel.AdminDB;
import dbmodel.LogSigninAdminDB;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import model.Admin;
import model.LogSinginAdmin;

/**
 *
 * @author hadan
 */
@WebFilter(filterName = "SigninAdminFilter", urlPatterns = {"/signin/manage/admin"})
public class SigninAdminFilter implements Filter {

    private FilterConfig filterConfig = null;

    public SigninAdminFilter() {
    }

    private boolean doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String method = httpRequest.getMethod();

        // Xử lý request GET
        if ("GET".equalsIgnoreCase(method)) {
            if(session.getAttribute("admin") != null) 
            {
                return true;
            }
            System.out.println("Call filter admin with get method");
            request.getServletContext().getRequestDispatcher("/signinadmin.jsp").forward(request, response);
            return false;
        }
        // Xử lý request POST
        if ("POST".equalsIgnoreCase(method)) {
            System.out.println("Call filter admin with post method");
            String username = request.getParameter("email");
            String password = request.getParameter("password");
            System.out.println("POST request from " + request.getRemoteAddr() + " for username: " + username);
            int loginFailCount = session.getAttribute("loginFailCount") != null ? (Integer) session.getAttribute("loginFailCount") : 0;

            //Neu dang nhap fail qua 5 lan
            if (loginFailCount >= 5) {
                session.setAttribute("status",false);
                request.setAttribute("alertMessage","Quá nhiều lần đăng nhập, tài khoảng đã bị khóa");
                System.out.println("Account locked due to too many failed login attempts.");
                request.getServletContext().getRequestDispatcher("/signinadmin.jsp").forward(request, response);
                return false;
            }else{
                session.setAttribute("status",true);
            }

            boolean status_login = (boolean) session.getAttribute("status");

            if(status_login){
                Admin admin = AdminDB.getInstance().checkLogin(username, password);
                if (admin != null) {
                    if(!admin.getStatus()) {
                        try {
                            Double latitude = Double.parseDouble(request.getParameter("latitude"));
                            Double longitude = Double.parseDouble(request.getParameter("longitude"));
                            // Kiểm tra latitude và longitude hợp lệ
                            if (latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180) {
                                String ip = request.getRemoteAddr();
                                LocalDateTime timein = LocalDateTime.now();

                                LogSinginAdmin log = new LogSinginAdmin(ip, timein, latitude.toString(), longitude.toString());
                                LogSigninAdminDB.getInstance().insert(log);

                                admin.setStatus(true);
                                AdminDB.getInstance().update(admin);
                                session.setAttribute("admin", admin);
                                session.setAttribute("log",log);

                                //Set mỗi phiên lam việc của admin là 30p, test 10s
                                session.setMaxInactiveInterval(30*60);
                                System.out.println("Login successful. Latitude: " + latitude + ", Longitude: " + longitude);
                                return true;
                            } else {
                                System.out.println("Invalid format for latitude or longitude.");
                                request.setAttribute("alertMessage","Bạn phải cung cấp vị trí bạn đang đăng nhập");
                                request.getServletContext().getRequestDispatcher("/signinadmin.jsp").forward(request, response);
                                return false;
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid format for latitude or longitude.");
                            request.setAttribute("alertMessage","Bạn phải cung cấp vị trí bạn đang đăng nhập");
                            request.getServletContext().getRequestDispatcher("/signinadmin.jsp").forward(request, response);
                            return false;
                        }
                    }else{
                        //loginFailCount++;
                        session.setAttribute("loginFailCount", loginFailCount);
                        request.setAttribute("alertMessage","Tài khoảng này đang được đăng nhập ở một nơi khác");
                        System.out.println("Invalid login attempt. Total failures: " + loginFailCount);
                        request.getServletContext().getRequestDispatcher("/signinadmin.jsp").forward(request, response);
                        return false;
                    }
                } else{
                    loginFailCount++;
                    session.setAttribute("loginFailCount", loginFailCount);
                    request.setAttribute("alertMessage","Nhập sai tài khoảng hoặc mật khẩu");
                    System.out.println("Invalid login attempt. Total failures: " + loginFailCount);
                    request.getServletContext().getRequestDispatcher("/signinadmin.jsp").forward(request, response);
                    return false;
                }
            }else{
                request.setAttribute("alertMessage","Quá nhiều lần đăng nhập, tài khoảng đã bị khóa");
                System.out.println("Account locked due to too many failed login attempts.");
                request.getServletContext().getRequestDispatcher("/signinadmin.jsp").forward(request, response);
                return false;
            }
        }
        return false;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (doBeforeProcessing(request, response))
        {
            System.out.println("Call chain.dofilter");
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
}
