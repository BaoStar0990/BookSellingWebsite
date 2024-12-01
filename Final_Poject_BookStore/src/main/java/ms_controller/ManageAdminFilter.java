/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package ms_controller;

import dbmodel.AdminDB;
import dbmodel.LogSigninAdminDB;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
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
@WebFilter(filterName = "ManageAdminFilter", urlPatterns = {"/ms/*"})
public class ManageAdminFilter implements Filter {

    private FilterConfig filterConfig = null;

    public ManageAdminFilter() {
    }

    private boolean doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        String method = httpRequest.getMethod();

        // Xử lý request GET
        if ("GET".equalsIgnoreCase(method)) {
            if (session.getAttribute("admin") != null) {
                return true;
            } else {
                httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND,"Không tìm thấy trang");
                return false;
            }

        }
        // Xử lý request POST
        if ("POST".equalsIgnoreCase(method)) {
            if(session.getAttribute("admin") != null) {
                return true;
            }
            else{
                httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND,"Không tìm thấy trang");
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
