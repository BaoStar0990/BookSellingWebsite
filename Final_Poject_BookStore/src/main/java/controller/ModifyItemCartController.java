/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dbmodel.OrderDetailDB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import model.OrderDetail;

/**
 *
 * @author PC
 */
@WebServlet(name = "ModifyItemCartController", urlPatterns = {"/modifyitemcart"})
public class ModifyItemCartController extends HttpServlet {

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
        
        String url = "/viewcart";
        // Lấy session
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null){
            url = "/signin.jsp";
        }
        else{
            // lấy action
            String action = request.getParameter("action");
            // lấy orderdertail
            String orderIdStr = request.getParameter("orderDetailId");
            try{
                
                // find OrderDetail
                int orderId = Integer.parseInt(orderIdStr);
                OrderDetail order = OrderDetailDB.getInstance().selectByID(orderId);
                if(order != null){
                    if(action.equals("delete")){
                        if(!OrderDetailDB.getInstance().delete(orderId, OrderDetail.class))
                            System.out.println("Xóa không thành công");                       
                    }
                    else{
                        if(action.equals("increate")){
                            if(order.getBook().getStocks() - order.getQuantity() >= 1){
                                order.setQuantity(order.getQuantity() + 1);
                                if(!OrderDetailDB.getInstance().update(order)){
                                    System.out.println("Cập nhật không thành công");
                                }
                            }
                            else
                                System.out.println("Hết hàng");
                        }                            
                        else if(action.equals("decreate")){
                            if(order.getQuantity() > 1){
                                order.setQuantity(order.getQuantity() - 1);
                                if(!OrderDetailDB.getInstance().update(order)){
                                        System.out.println("Cập nhật không thành công");
                                }
                            }
                        }
                    }
                }
                
            }catch(NumberFormatException ex){
                System.out.println("Vui lòng nhập đúng dữ liệu");
            }catch (NoSuchElementException ex){
                System.out.println("Không tìm thấy sách");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        // chuyển trang
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
        processRequest(request, response);
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
