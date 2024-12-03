/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dbmodel.BillDB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import model.Bill;
import model.Customer;
import model.StatusOrder;

/**
 *
 * @author PC
 */
@WebServlet(name = "ConfirmCancelOrderController", urlPatterns = {"/confirmcancelorder"})
public class ConfirmCancelOrderController extends HttpServlet {

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
        
        String errorMessage = null;
        String url = "/usersetting.jsp?setting=orders";
        // Lấy session
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null){
            url = "/signin.jsp";
        }
        else{         
            // lấy khách hàng
            Customer customer = (Customer) session.getAttribute("user");
            String action = request.getParameter("action");
            String idStr = request.getParameter("orderId");
            try{
                int id = Integer.parseInt(idStr);
                Bill order = BillDB.getInstance().selectByID(id);
                if(order != null){
                    if(order.getCustomer().getId() == customer.getId()){
                        if(action.equals("confirm") && order.getStatusOrder().equals(StatusOrder.Delivered))
                            order.setStatusOrder(StatusOrder.Completed);

                        if(!BillDB.getInstance().update(order)){
                            request.setAttribute("errorMessage", "Lỗi cập nhật đơn hàng!");
                            // chuyển trang
                            request.getServletContext()
                                    .getRequestDispatcher(url).forward(request, response);
                        }
                    }
                    else
                        errorMessage = "Sao nỡ làm vậy trời ơi!!!!";
                }
                else{
                        errorMessage = "Lỗi tìm đơn hàng!";

                    }
                }catch(NumberFormatException ex){
                    errorMessage = "Vui lòng nhập đúng dữ liệu";
                }catch (NoSuchElementException ex){
                    errorMessage =  "Không tìm thấy đơn hàng";

                }
            
        }
        if(errorMessage != null){
            request.setAttribute("errorMessage", errorMessage);
            request.getServletContext()
                       .getRequestDispatcher(url).forward(request, response);
        }
        else
            response.sendRedirect(url);
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
