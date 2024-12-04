package controller;

import Utils.authentication.TokenService;
import dbmodel.BillDB;
import dbmodel.BookDB;
import dbmodel.OrderDetailDB;
import java.io.IOException;
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
@WebServlet(name = "AddCartController", urlPatterns = {"/addcart"})
public class AddCartController extends HttpServlet {

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
        response.setContentType("application/json");
        
        System.out.println("aaaaaaaaaaaa");
        
        String errorMessage = null;
        // Lấy session
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null){
            System.out.println("oooooooooo");
                // Tạo một đối tượng JSON với cả thông tin chuyển hướng và thông báo lỗi
            String jsonResponse = "{\"redirect\":\"/signin.jsp\"}";
            response.getWriter().write(jsonResponse);
        }
        else{
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
            
            String quantityStr = request.getParameter("quantity");
            String csrfToken = request.getParameter("csrfToken");
            String idBookStr = request.getParameter("bookId");
            String action = request.getParameter("action");
            
            try{
                // find sách
                int idBook = Integer.parseInt(idBookStr);
                int quantity = Integer.parseInt(quantityStr);
                // kiểm tra token
                TokenService tokenService = (TokenService) session.getAttribute("tokenService");
                
                if (tokenService == null || !tokenService.validateToken(csrfToken, idBook)) {
                    response.getWriter().write("{\"errorMessage\":\"Đừng có ngịch.\"}");
                    return;
                }
                    
                // lấy sách trong database
                Book book = BookDB.getInstance().selectByID(idBook);
                    
                if(book != null){
                    // check lỗi khách hàng ko có giỏ hàng
                    if(cart != null){

                        // sửa lại lấy trong session
                        // kiểm tra cuốn sách đó có trong cart chưa, nếu có thì tăng số lượng lên 
                        OrderDetail orderDetail = null;
                        if(orderDetails != null){
                            orderDetail = orderDetails.stream()
                                .filter(o -> o.getBook().equals(book))
                                .findFirst()
                                .orElse(null);

                            // 

                            if(orderDetail == null){
                               if(BillDB.getInstance().addBookBill(book, quantity, cart) == false){
                                    errorMessage = "Lỗi thêm vào giỏ hàng";
                               } 
                               else{
                                   // thêm OrderDetail vào session
                                   // tìm OrderDetail vừa thêm vào
                                   OrderDetail findOrder = BillDB.getInstance().FindOrderDetailFromBill(book, cart);
                                   if(findOrder != null)
                                       orderDetails.add(findOrder);
                                   else
                                       errorMessage = "Lỗi cập nhật session";
                               }
                            }
                            else{
                                orderDetail.setQuantity(orderDetail.getQuantity()+quantity);
                                OrderDetailDB.getInstance().update(orderDetail);
                                // cập nhật OrderDetail vào session
                                int orderId = orderDetail.getId();
                                orderDetails.stream()
                                    .filter(o -> o.getId() == orderId) 
                                    .findFirst()  
                                    .ifPresent(o -> o.setQuantity(o.getQuantity()));
                            }
                        }

                    }
                    
                }
//                Book book = BookDB.getInstance().selectByID(idBook);
                
                
            }catch(NumberFormatException ex){
                errorMessage = "Vui lòng nhập đúng dữ liệu";
            }catch (NoSuchElementException ex){
               errorMessage = "Không tìm thấy sách";
            }            
            if(action.equals("muaNgay")){
                System.out.println("mua ngay ----------");
                // Trả về thông báo về chuyển hướng
                String jsonResponse = "{\"redirect\":\"/payment\", \"errorMessage\":\"" + errorMessage + "\"}";
                response.getWriter().write(jsonResponse); 
            }
            else{
                response.getWriter().write("{\"errorMessage\":\"" + errorMessage + "\"}"); 
            }
                    
        }
//        // chuyển trang
//        request.getServletContext().getRequestDispatcher(url).forward(request, response);
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
