/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package StaffController;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

import dbmodel.StaffDB;
import dbmodel.LogSigninStaffDB;
import dbmodel.LogSigninStaffDB;
import dbmodel.StaffDB;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.Staff;
import model.LogSinginStaff;
import model.LogSinginStaff;
import model.Staff;

/**
 *
 * @author hadan
 */
@WebFilter(filterName = "SigninStaffFilter", urlPatterns = {"/signin/manage/staff"})
public class SigninStaffFilter implements Filter {
    private FilterConfig filterConfig = null;

    public SigninStaffFilter() {
    }

    private boolean doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String method = httpRequest.getMethod();


        System.out.println("Call filter sign in staff");
        // Xử lý request GET
        if ("GET".equalsIgnoreCase(method)) {
            System.out.println("GET request to login page staff.");
            request.getServletContext().getRequestDispatcher("/signinstaff.jsp").forward(request, response);
            return false;
        }
        // Xử lý request POST
        if ("POST".equalsIgnoreCase(method)) {
            String username = request.getParameter("email");
            String password = request.getParameter("password");
            System.out.println("POST request from " + request.getRemoteAddr() + " for username: " + username);
            int loginFailCount = session.getAttribute("loginFailCount") != null ? (Integer) session.getAttribute("loginFailCount") : 0;

            //Neu dang nhap fail qua 5 lan
            if (loginFailCount >= 5) {
                session.setAttribute("status",false);
                request.setAttribute("alertMessage","Quá nhiều lần đăng nhập, tài khoản đã bị khóa");
                System.out.println("Account locked due to too many failed login attempts.");
                request.getServletContext().getRequestDispatcher("/signinstaff.jsp").forward(request, response);
                return false;
            }else{
                session.setAttribute("status",true);
            }

            boolean status_login = (boolean) session.getAttribute("status");

            if(status_login){
                Staff staff = StaffDB.getInstance().checkLogin(username, password);
                if (staff != null) {
                    if(staff.getStatus() == false) {
                        try {
                            Double latitude = Double.parseDouble(request.getParameter("latitude"));
                            Double longitude = Double.parseDouble(request.getParameter("longitude"));
                            // Kiểm tra latitude và longitude hợp lệ
                            if (latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180) {
                                String ip = request.getRemoteAddr();
                                LocalDateTime timein = LocalDateTime.now();

                                LogSinginStaff log = new LogSinginStaff(ip, timein, latitude.toString(), longitude.toString());
                                LogSigninStaffDB.getInstance().insert(log);

                                staff.setStatus(true);
                                StaffDB.getInstance().update(staff);
                                session.setAttribute("staff", staff);
                                session.setAttribute("log",log);


                                return true;
                            } else {
                                System.out.println("Invalid format for latitude or longitude.");
                                request.setAttribute("alertMessage","Bạn phải cung cấp vị trí bạn đang đăng nhập");
                                request.getServletContext().getRequestDispatcher("/signinstaff.jsp").forward(request, response);
                                return false;
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid format for latitude or longitude.");
                            request.setAttribute("alertMessage","Bạn phải cung cấp vị trí bạn đang đăng nhập");
                            request.getServletContext().getRequestDispatcher("/signinstaff.jsp").forward(request, response);
                            return false;
                        }
                    }else{
                        loginFailCount++;
                        session.setAttribute("loginFailCount", loginFailCount);
                        request.setAttribute("alertMessage","Tài khoản này đang được đăng nhập ở một nơi khác");
                        System.out.println("Invalid login attempt. Total failures: " + loginFailCount);
                        request.getServletContext().getRequestDispatcher("/signinstaff.jsp").forward(request, response);
                        return false;
                    }
                } else{
                    loginFailCount++;
                    session.setAttribute("loginFailCount", loginFailCount);
                    request.setAttribute("alertMessage","Nhập sai tài khoản hoặc mật khẩu");
                    System.out.println("Invalid login attempt. Total failures: " + loginFailCount);
                    request.getServletContext().getRequestDispatcher("/signinstaff.jsp").forward(request, response);
                    return false;
                }
            }else{
                request.setAttribute("alertMessage","Quá nhiều lần đăng nhập, tài khoản đã bị khóa");
                System.out.println("Account locked due to too many failed login attempts.");
                request.getServletContext().getRequestDispatcher("/signinstaff.jsp").forward(request, response);
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
