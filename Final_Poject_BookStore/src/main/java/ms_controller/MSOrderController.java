package ms_controller;

import dbmodel.BillDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Bill;
import model.StatusOrder;
import model.StatusPayment;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "MSOrderController", urlPatterns = {"/msorder"})
public class MSOrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equalsIgnoreCase("updateStatus")) {
            updateOrderStatus(request, response);
        } else {
            handleBillRequest(request, response);
        }
    }

    private void handleBillRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String billIdParam = request.getParameter("billId");
        if (billIdParam != null && !billIdParam.isEmpty()) {
            try {
                int billId = Integer.parseInt(billIdParam);
                Bill bill = BillDB.getInstance().selectByID(billId);
                
                request.setAttribute("bill", bill);
                request.getRequestDispatcher("/Management-System/ms-orderdetail.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid bill ID");
            }
        } else {
            processRequest(request, response);
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Bill> allBill = BillDB.getInstance().selectAll()
                .stream().filter(b -> !("Storing".equals(b.getStatusOrder().toString())))
                .collect(Collectors.toList());
        List<StatusOrder> statusOrders = Arrays.stream(StatusOrder.values())
                .filter(status -> status != StatusOrder.Storing)
                .collect(Collectors.toList());
        StatusPayment[] statusPayments = StatusPayment.values();

        request.setAttribute("bills", allBill);
        request.setAttribute("StatusOrder", statusOrders);
        request.setAttribute("StatusPayment", statusPayments);
        request.getRequestDispatcher("/Management-System/ms-order.jsp").forward(request, response);
    }

    private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String billIdParam = request.getParameter("billId");
        String redirectUrl = request.getParameter("redirectUrl");
        if (billIdParam != null && !billIdParam.isEmpty()) {
            try {
                int billId = Integer.parseInt(billIdParam);
                Bill bill = BillDB.getInstance().selectByID(billId);
                if (bill != null) {
                    //Log the current status of the order
                    System.out.println("Current status of the order: " + bill.getStatusOrder());

                    StatusOrder currentStatus = bill.getStatusOrder();
                    StatusOrder nextStatus = getNextStatus(currentStatus);
                    if (nextStatus != null) {
                        bill.setStatusOrder(nextStatus);
                        boolean isUpdated = BillDB.getInstance().update(bill);
                        if (isUpdated) {
                            //Log the new status of the order
                            System.out.println("New status of the order: " + bill.getStatusOrder());
                            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                                handleBillRequest(request, response);
                            } else {
                                response.sendRedirect(request.getContextPath() + "/msorder?billId=" + billId);
                            }
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
            default:
                return null;
        }
    }
}
