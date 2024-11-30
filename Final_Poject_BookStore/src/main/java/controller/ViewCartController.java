package controller;


import dbmodel.BillDB;
import dbmodel.BookDB;
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
import model.Bill;
import model.Book;
import model.Customer;
import model.OrderDetail;

/**
 *
 * @author PC
 */
@WebServlet(name = "ViewCartController", urlPatterns = {"/viewcart"})
public class ViewCartController extends HttpServlet {

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
        
//        String pathInfo = request.getPathInfo();
//        // Kiểm tra nếu pathInfo có chứa tham số
//        if (pathInfo != null && pathInfo.contains("?")) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
//            response.getWriter().write("Invalid path or query parameters are not accepted.");
//            return;
//        }
        
        //set UTF8 - Tiếng việt
        request.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        System.out.println(request.getParameter("bookId"));
        
        String url = "/cart.jsp";
        // Lấy session
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null){
            url = "/signin.jsp";
        }
        else{ 
            Bill cart = null;
            List<OrderDetail> sortedOrderDetails = null;
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
                    session.setAttribute("orderDetails", cart.getOrderDetails());
                    sortedOrderDetails = cart.getOrderDetails().stream()
                                               .sorted(Comparator.comparingInt(OrderDetail::getId))
                                               .collect(Collectors.toList());
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
            if(cart != null){
                request.setAttribute("cartId", cart.getId());
                request.setAttribute("listOrderDetails", sortedOrderDetails);
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
