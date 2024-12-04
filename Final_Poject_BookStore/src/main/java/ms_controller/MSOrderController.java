package ms_controller;

import database.DBUtil;
import dbmodel.BillDB;
import dbmodel.OrderDetailDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Bill;
import model.StatusOrder;
import model.StatusPayment;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import mail.Mail;
import model.Book;
import model.OrderDetail;

@WebServlet(name = "MSOrderController", urlPatterns = {"/ms/msorder"})
public class MSOrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action != null && (action.equalsIgnoreCase("updateStatus") 
                || action.equalsIgnoreCase("cancelOrder") 
                    || action.equalsIgnoreCase("deleteOrder")
                        || action.equalsIgnoreCase("checkSoLuong")
                            || action.equalsIgnoreCase("deleteOrderDetail")
                                || action.equalsIgnoreCase("editOrderDetail"))) {
            if(action.equalsIgnoreCase("deleteOrderDetail")){
                try{
                    String billIdParam = request.getParameter("billId");
                    if (billIdParam != null && !billIdParam.isEmpty()) {
                        // nếu đơn hàng chỉ có 1 sản phẩm thì chuyển đơn hàng sang cancel
                        try {
                            int billId = Integer.parseInt(billIdParam);
                            Bill bill = BillDB.getInstance().selectByID(billId);
                            if (bill != null) {
                                // kiểm tra số lượng sản phẩm trong Bill
                                if (bill.getOrderDetails() != null && bill.getOrderDetails().size() == 1) { 
                                    bill.setStatusOrder(StatusOrder.Cancelled);
                                    boolean isUpdate = BillDB.getInstance().update(bill);
                                    if (!isUpdate) {
                                       response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update order");
                                    }
                                    else{
                                        handleBillRequest(request, response);
                                        return;
                                    }
                                } // chỉ xóa sản phẩm
                                else{
                                    String orderDetailID_Str = request.getParameter("orderDetailID");
                                    int orderDetailID = Integer.parseInt(orderDetailID_Str);
                                    if(!OrderDetailDB.getInstance().delete(orderDetailID, OrderDetail.class)){
                                        request.setAttribute("errorMessage", "Xóa sản phẩm không thành công.");
                                    }
                                    else {
                                        handleBillRequest(request, response);
                                        return;
                                    }
                                }
                            } else {
                                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bill not found");
                            }
                        } catch (NumberFormatException e) {
                            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid bill ID");
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bill ID is required");
                    }                   
                } catch (NumberFormatException ex) {
                    request.setAttribute("errorMessage", "Vui lòng nhập đúng dữ liệu");
                } catch (NoSuchElementException ex) {
                    request.setAttribute("errorMessage", "Không tìm thấy sách");
                }

                request.getServletContext()
                        .getRequestDispatcher("/Management-System/ms-orderdetail.jsp")
                        .forward(request, response);
                
            }
            else if (action.equalsIgnoreCase("editOrderDetail")){
                try {
                    // lấy OrderDetailID
                    String orderDetailID_Str = request.getParameter("orderDetailID");
                    int orderDetailID = Integer.parseInt(orderDetailID_Str);
                    // lấy số lượng cần cập nhật
                    String quantity_Str = request.getParameter("quantity");
                    int quantity = Integer.parseInt(quantity_Str);
                    
                    OrderDetail order = OrderDetailDB.getInstance().selectByID(orderDetailID);
                    if(order != null){
                        order.setQuantity(quantity);
                        if(!OrderDetailDB.getInstance().update(order))
                            request.setAttribute("errorMessage", "Cập nhật không thành công.");
                    }
                    else{
                        request.setAttribute("errorMessage", "Không tìm thấy chi tiết đơn hàng.");
                    }
                    String billIdParam = request.getParameter("billIdEdit");
                    int billId = Integer.parseInt(billIdParam);
                    Bill bill = BillDB.getInstance().selectByID(billId);
                    if (bill != null) {
                        request.setAttribute("bill", bill);
                        request.getRequestDispatcher("/Management-System/ms-orderdetail.jsp")
                                    .forward(request, response);
                    } else {
                        processRequest(request, response);
                    } 
                } catch (NumberFormatException ex) {
                     request.setAttribute("errorMessage", "Vui lòng nhập đúng dữ liệu");
                } catch (NoSuchElementException ex) {
                    request.setAttribute("errorMessage", "Không tìm thấy sách");
                }
            }
            else if (action.equalsIgnoreCase("deleteOrder")) {
                handleDeleteOrder(request, response);
            } else {
                handleOrderStatusUpdate(request, response);
            }
        } else {
            handleBillRequest(request, response);
        }
    }
    /*
     * Handle bill request
     * - Get bill ID from request
     * - If the bill ID is not empty, get the bill from the database
     * - Set the bill as an attribute of the request
     * - Forward the request to the order detail page
     * - If the bill ID is empty, process the request
     */
    private void handleBillRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String billIdParam = request.getParameter("billId");
        if (billIdParam != null && !billIdParam.isEmpty()) {
            try {
                int billId = Integer.parseInt(billIdParam);
                Bill bill = BillDB.getInstance().selectByID(billId);
                
                request.setAttribute("bill", bill);
                request.getRequestDispatcher("/Management-System/ms-orderdetail.jsp")
                        .forward(request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid bill ID");
            }
        } else {
            processRequest(request, response);
        }
    }

    /*
     * Process request
     * - Get all bills except storing status
     * - Sort by status order and bill ID (descending)
     * - Set the bills, status order, and status payment as attributes of the request
     * - Forward the request to the order page
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get all bills except storing status
        // Sort by status order and bill ID (descending)
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        List<Bill> allBill = BillDB.getInstance().selectAll()
                .stream()
                .filter(b -> !("Storing".equals(b.getStatusOrder().toString())))
                .sorted((b1, b2) -> {
                    int statusComparison = compareStatusOrder(b1.getStatusOrder(), b2.getStatusOrder());
                    if (statusComparison != 0) {
                        return statusComparison;
                    }
                    return Integer.compare(b2.getId(), b1.getId());
                })
                .collect(Collectors.toList());
        List<StatusOrder> statusOrders = Arrays.stream(StatusOrder.values())
                .filter(status -> status != StatusOrder.Storing)
                .collect(Collectors.toList());
        StatusPayment[] statusPayments = StatusPayment.values();

        request.setAttribute("bills", allBill);
        request.setAttribute("StatusOrder", statusOrders);
        request.setAttribute("StatusPayment", statusPayments);
        request.getRequestDispatcher("/Management-System/ms-order.jsp")
                .forward(request, response);
    }

    // Compare the order of status in the enum
    private int compareStatusOrder(StatusOrder s1, StatusOrder s2) {
        List<StatusOrder> order = Arrays.asList(
                StatusOrder.Processing,
                StatusOrder.Packing,
                StatusOrder.Delivering,
                StatusOrder.Delivered,
                StatusOrder.Completed,
                StatusOrder.Cancelled
        );
        return Integer.compare(order.indexOf(s1), order.indexOf(s2));
    }

    /* 
        Handle order status update request
        - Get bill ID from request
        - Get the bill from the database
        - Get the action from the request
        - Get the current status of the bill
        - Get the next status based on the current status
        - Update the status of the bill
        - If the next status is Delivered, update the payment status and delivery date
        - Update the bill in the database
        - If the update is successful, handle the bill request
        - If the update is failed, return an internal server error
     */
    private void handleOrderStatusUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String billIdParam = request.getParameter("billId");
        if (billIdParam != null && !billIdParam.isEmpty()) {
            try {
                int billId = Integer.parseInt(billIdParam);
                Bill bill = BillDB.getInstance().selectByID(billId);
                if (bill != null) {
                    String action = request.getParameter("action");
                    StatusOrder currentStatus = bill.getStatusOrder();
                    StatusOrder nextStatus = null;

                    if (action.equalsIgnoreCase("cancelOrder")) {
                        StatusPayment currentPaymentStatus = bill.getStatusPayment();
                        if ((currentStatus == StatusOrder.Processing || currentStatus == StatusOrder.Packing || currentStatus == StatusOrder.Delivering)
                                && currentPaymentStatus == StatusPayment.Unpaid) {
                            nextStatus = StatusOrder.Cancelled;
                        } else {
                            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order cannot be cancelled");
                            return;
                        }
                    } else {
                        nextStatus = getNextStatus(currentStatus);
                    }
                    if(action.equalsIgnoreCase("checkSoLuong")){
                        List<String> booksExceedingStocks = bill.getOrderDetails().stream()
                                .filter(o -> o.getBook().getStocks() < o.getQuantity()) 
                                .map(o -> '\"' + o.getBook().getTitle() + '\"' + " hiện còn " + o.getBook().getStocks() + " cuốn") 
                                .collect(Collectors.toList()); 

                        if (!booksExceedingStocks.isEmpty()) {
                            request.setAttribute("errorMessage", String.join(", ", booksExceedingStocks));
                        }
                        else{
                            request.setAttribute("successMessage", "Các sản phẩm còn hàng.");
                            System.out.println("dddddddd");
                        }
                        handleBillRequest(request, response);
                        return;
                    }
                    if (nextStatus != null) {
                        bill.setStatusOrder(nextStatus);
                        
                        if(nextStatus == StatusOrder.Packing){
                            EntityManager emFinal = DBUtil.getEmFactory().createEntityManager();
                            EntityTransaction tr = emFinal.getTransaction();

                            try {
                                tr.begin();
                                Set<OrderDetail> orderDetails = bill.getOrderDetails();
                                // kiểm tra số lượng đặt có vượt quá số lượng trong kho
                                List<String> booksExceedingStocks = orderDetails.stream()
                                        .filter(o -> o.getBook().getStocks() < o.getQuantity()) 
                                        .map(o -> '\"' + o.getBook().getTitle() + '\"' + " hiện còn " + o.getBook().getStocks() + " cuốn") 
                                        .collect(Collectors.toList()); 

                                if (!booksExceedingStocks.isEmpty()) {
                                    request.setAttribute("errorMessage", String.join(", ", booksExceedingStocks));
                                    handleBillRequest(request, response);
                                    return;
                                }
                                else {
                                    orderDetails.forEach(o -> {
                                        // lấy cuốn sách cập nhật số lượng trong kho
                                        var book = emFinal.find(Book.class, o.getBook().getId());
//                                        book.setStocks(book.getStocks() - o.getQuantity());
                                        book.increaseStocks(o.getQuantity());
                                        emFinal.merge(book);
                                    });                                  
                                }
                                tr.commit();
                                // gửi mail cho khách hàng
                                String email = bill.getCustomer().getEmail();
                                if(Mail.sendOrderToCustomer(email, bill, orderDetails))
                                    request.setAttribute("successMessage", "Gửi mail thành công");
                                else
                                    request.setAttribute("errorMessage", "Lỗi gửi mail.");
                                
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                tr.rollback();
                                request.setAttribute("errorMessage", "Lỗi xác nhận đơn hàng.");
                                handleBillRequest(request, response);
                                return;
                            }
                        }
                        // hủy đơn hàng
                        else if(nextStatus == StatusOrder.Cancelled && 
                                (currentStatus == StatusOrder.Delivering || currentStatus == StatusOrder.Packing)){
                            // thay đổi cơ sở dữ liệu
                            EntityManager emFinal = DBUtil.getEmFactory().createEntityManager();
                            EntityTransaction tr = emFinal.getTransaction();

                            try {
                                tr.begin();
                                bill.getOrderDetails().forEach(o -> {
                                    // lấy cuốn sách cập nhật số lượng trong kho
                                    var book = emFinal.find(Book.class, o.getBook().getId());
//                                    book.setStocks(book.getStocks() + o.getQuantity());
                                    book.decreaseStocks(o.getQuantity());
                                    emFinal.merge(book);
                                });
                                tr.commit();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                tr.rollback();
                                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Hủy đơn hàng không thành công.");
                                return;
                            }
                        }
                        else if (nextStatus == StatusOrder.Delivered) {
                            bill.setStatusPayment(StatusPayment.Paid);
                            bill.setDeliveryDate(LocalDate.now());
                        }

                        if (BillDB.getInstance().update(bill)) {
                            handleBillRequest(request, response);
                        } else {
                           response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update order status");
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order status transition");
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bill not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid bill ID");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bill ID is required");
        }
    }

    // Handle delete order request
    private void handleDeleteOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String billIdParam = request.getParameter("billId");
        if (billIdParam != null && !billIdParam.isEmpty()) {
            try {
                int billId = Integer.parseInt(billIdParam);
                Bill bill = BillDB.getInstance().selectByID(billId);
                if (bill != null) {
                    if (bill.getStatusOrder() == StatusOrder.Cancelled) {
                        boolean isDeleted = BillDB.getInstance().delete(bill.getId(), Bill.class);
                        if (isDeleted) {
                            response.sendRedirect(request.getContextPath() + "/ms/msorder");
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete order");
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Only cancelled orders can be deleted");
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bill not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid bill ID");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bill ID is required");
        }
    }

    // Get the next status based on the current status
    private StatusOrder getNextStatus(StatusOrder currentStatus) {
        switch (currentStatus) {
            case Processing:
                return StatusOrder.Packing;
            case Packing:
                return StatusOrder.Delivering;
            case Delivering:
                return StatusOrder.Delivered;
            case Delivered:
                return StatusOrder.Completed;
            case Cancelled:
            case Completed:
                return null; // No transition from Cancelled or Completed
            default:
                return null;
        }
    }
}
