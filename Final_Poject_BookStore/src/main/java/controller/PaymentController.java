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
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import model.Address;
import model.Bill;
import model.Customer;
import model.OrderDetail;

/**
 *
 * @author PC
 */
@WebServlet(name = "PaymentController", urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {

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
        
        String url = "/deliveryinfo.jsp";
        String errorMessage = null;
        
        // Lấy session
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null){
            url = "/signin.jsp";
        }
        else{
            // Lấy giỏ hàng của khách hàng
            Customer c = (Customer) session.getAttribute("user");
            Bill cart = null;
            List<OrderDetail> sortedOrderDetails = null;
            // kiểm tra trong session có giỏ hàng chưa, nếu chưa thì lấy dưới database
            if(session.getAttribute("cart") == null){
                Set<Bill> bills = c.getBills();
                cart = bills.stream()
                        .filter(b -> "Storing".equals(b.getStatusOrder().toString()))
                        .findFirst()
                        .orElse(null);
                session.setAttribute("cart", cart);
                // lấy các orderDetail trong cart
                if(cart != null){               
                    Set<OrderDetail> orderDetails = cart.getOrderDetails();
                    session.setAttribute("orderDetails", orderDetails);
                    if(orderDetails != null){
                        sortedOrderDetails = cart.getOrderDetails().stream()
                                               .sorted(Comparator.comparingInt(OrderDetail::getId))
                                               .collect(Collectors.toList());
                    }
                }
            }
            else {
                cart = (Bill) session.getAttribute("cart");
                Set<OrderDetail> orderDetails = (Set<OrderDetail>) session.getAttribute("orderDetails");
                if(orderDetails != null){
                    sortedOrderDetails = orderDetails.stream()
                                               .sorted(Comparator.comparingInt(OrderDetail::getId))
                                               .collect(Collectors.toList());
                }
            }
            
            if(sortedOrderDetails == null){
                url = "/viewcart";
                errorMessage = "Vui lòng thêm sản phẩm vào giỏ hàng trước khi thanh toán.";
                request.setAttribute("errorMessage", errorMessage);
                System.out.println("aaaaaaaaaaaaaaaa");
            }
            else if(sortedOrderDetails.isEmpty()){
                url = "/viewcart";
                errorMessage = "Vui lòng thêm sản phẩm vào giỏ hàng trước khi thanh toán.";
                request.setAttribute("errorMessage", errorMessage);
                System.out.println("aaaaaaaaaaaaaaaa");
            }
            else{
                // lấy danh sách các địa chỉ của khách hàng
                List<Address> addresses = c.getAddresses().stream()
                    .sorted(Comparator.comparing(Address::isDefaultAddress).reversed() 
                    .thenComparingInt(Address::getId)) //sắp xếp theo id
                    .collect(Collectors.toList());

                if(cart != null){
                    request.setAttribute("listOrderDetails", sortedOrderDetails);
                } 
                request.setAttribute("addresses", addresses);
            }
        }
        // chuyển trang
        request.getServletContext().getRequestDispatcher(url).forward(request, response);
        
    }

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
