/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dbmodel.BillDB;
import dbmodel.CustomerDB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import model.Bill;
import model.Customer;
import model.OrderDetail;
import model.StatusOrder;
import model.StatusPayment;

/**
 *
 * @author PC
 */
@WebServlet(name = "OrderController", urlPatterns = {"/order"})
public class OrderController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final ReentrantLock lock = new ReentrantLock();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //set UTF8 - Tiếng việt
        request.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = "/confirmation.jsp";

        // Lấy session
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            url = "/signin.jsp";
            request.getServletContext().getRequestDispatcher(url).forward(request, response);
        } else {
            String address = request.getParameter("addressSelected");
            String name = request.getParameter("name");
            String phone = request.getParameter("phonenumber");
            // Lấy giỏ hàng của khách hàng           
//            String cartIdStr = request.getParameter("cartId");
            
            Bill cart = null;
            List<OrderDetail> sortedOrderDetails = null;
            Set<OrderDetail> orderDetails = null;
            // kiểm tra trong session có giỏ hàng chưa, nếu chưa thì lấy dưới database
            if(session.getAttribute("cart") == null){
                // Lấy giỏ hàng của khách hàng
                Customer c = (Customer) session.getAttribute("user");
                Set<Bill> bills = c.getBills();
                cart = bills.stream()
                        .filter(b -> "Storing".equals(b.getStatusOrder().toString()))
                        .findFirst()
                        .orElse(null);
                session.setAttribute("cart", cart);
                // lấy các orderDetail trong cart
                if(cart != null){
                    orderDetails = cart.getOrderDetails();
                    session.setAttribute("orderDetails", orderDetails);         
                    sortedOrderDetails = orderDetails.stream()
                                               .sorted(Comparator.comparingInt(OrderDetail::getId))
                                               .collect(Collectors.toList());
                }
            }
            else {
                cart = (Bill) session.getAttribute("cart");
                orderDetails = (Set<OrderDetail>) session.getAttribute("orderDetails");
                if(orderDetails != null){
                    sortedOrderDetails = orderDetails.stream()
                                               .sorted(Comparator.comparingInt(OrderDetail::getId))
                                               .collect(Collectors.toList());
                }
            }
    
            request.setAttribute("address", address);
            request.setAttribute("fullName", name);
            request.setAttribute("phonenumber", phone);
            try {
                if (cart != null) {
                    request.setAttribute("cartId", cart.getId());
                    request.setAttribute("listOrderDetails", sortedOrderDetails);

                    // cập nhật các thuộc tính
                    cart.setOrderDate(LocalDate.now()); // ngày đặt hàng
                    cart.setDeliveryDate(LocalDate.now().plusDays(4)); // ngày nhận hàng dự kiến
                    cart.setVAT(0.05); // thuế giá trị gia tăng

                    cart.setStatusPayment(StatusPayment.Unpaid); // trạng thái thanh toán                      
                    cart.setShippingAddress(name + "; " + phone + "; " + address);
                    cart.setNotes(request.getParameter("notes"));

                    // Kiểm tra giá trị của "paymentMethod" trước khi sử dụng equals
                    String paymentMethod = request.getParameter("payment");
                    if (paymentMethod != null && paymentMethod.equals("cod")) {
                        cart.setPaymentMethod("COD");
                    }

                    // Kiểm tra giá trị của "shippingMethod" trước khi sử dụng equals
                    String shippingMethod = request.getParameter("shipping");
                    if (shippingMethod != null && shippingMethod.equals("homeDelivery")) {
                        cart.setShippingFee(40000.0);
                    }

                    // gọi phương thức đặt hàng
                    Customer user = (Customer) session.getAttribute("user");
                    if (user.makeAnOrder(cart)) { // nếu đặt hàng thành công, chuyển trang
                        // xóa cart và orderDetail trong session
                        session.removeAttribute("cart");
                        session.removeAttribute("orderDetails");
                        request.setAttribute("order", cart);
                    } else {
                        url = "/payment";
                        System.out.println("Lỗi đặt hàng");
                    }

                }
            } catch (NumberFormatException ex) {
                System.out.println("Vui lòng nhập đúng dữ liệu");
            } catch (NoSuchElementException ex) {
                System.out.println("Không tìm thấy sách");
            }
        }
        // chuyển trang
        request.getServletContext().getRequestDispatcher(url).forward(request, response);
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
