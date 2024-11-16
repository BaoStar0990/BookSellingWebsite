package controller;

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
import java.util.NoSuchElementException;
import java.util.Set;
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
        
        String url = "/viewcart";
        // Lấy session
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null){
            url = "/signin.jsp";
        }
        else{
            // Lấy giỏ hàng của khách hàng
            Customer c = (Customer) session.getAttribute("user");
            Set<Bill> bills = c.getBills();
            Bill cart = bills.stream()
                    .filter(b -> "Storing".equals(b.getStatusOrder().toString()))
                    .findFirst()
                    .orElse(null);
            request.setAttribute("cart", cart);
            
            String quantityStr = request.getParameter("quantity");
            String idBookStr = request.getParameter("bookId");
            try{
                // find sách
                int idBook = Integer.parseInt(idBookStr);
                int quantity = Integer.parseInt(quantityStr);
                Book book = BookDB.getInstance().selectByID(idBook);
                if(book != null){
                    // kiểm tra số lượng thêm vào có vượt quá số lượng trong kho không
                    if(book.getStocks() >= quantity){
                        // check lỗi khách hàng ko có giỏ hàng
                        if(cart != null){
                            // kiểm tra cuốn sách đó có trong cart chưa, nếu có thì tăng số lượng lên 
                            Set<OrderDetail> orderDetails = cart.getOrderDetails();
                            OrderDetail orderDetail = orderDetails.stream()
                                    .filter(o -> o.getBook().equals(book))
                                    .findFirst()
                                    .orElse(null);
                            if(orderDetail == null){
                               if(BillDB.getInstance().addBookBill(book, quantity, cart) == false){
                                    System.out.println("Lỗi thêm vào giỏ hàng");
                                } 
                            }
                            else{
                                orderDetail.setQuantity(orderDetail.getQuantity()+quantity);
                                OrderDetailDB.getInstance().update(orderDetail);
                            }
                        }
                    }
                }
                
            }catch(NumberFormatException ex){
                System.out.println("Vui lòng nhập đúng dữ liệu");
            }catch (NoSuchElementException ex){
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
