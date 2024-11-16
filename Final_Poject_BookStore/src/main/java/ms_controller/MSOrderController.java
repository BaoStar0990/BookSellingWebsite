package ms_controller;

import dbmodel.BillDB;
import dbmodel.OrderDetailDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Bill;
import model.OrderDetail;
import model.StatusOrder;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "MSOrderController", urlPatterns = {"/msorder"})
public class MSOrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (session.getAttribute("bills") == null) {
            List<Bill> bills = BillDB.getInstance().selectAll();
            bills.sort((b1, b2) -> Integer.compare(b2.getId(), b1.getId()));
            session.setAttribute("bills", bills);
        }

        List<Bill> bills = (List<Bill>) session.getAttribute("bills");
        long storingCount = bills.stream().filter(b -> b.getStatusOrder() == StatusOrder.Storing).count();
        long processingCount = bills.stream().filter(b -> b.getStatusOrder() == StatusOrder.Processing).count();
        long packingCount = bills.stream().filter(b -> b.getStatusOrder() == StatusOrder.Packing).count();
        long deliveringCount = bills.stream().filter(b -> b.getStatusOrder() == StatusOrder.Delivering).count();
        long deliveredCount = bills.stream().filter(b -> b.getStatusOrder() == StatusOrder.Delivered).count();
        long cancelledCount = bills.stream().filter(b -> b.getStatusOrder() == StatusOrder.Cancelled).count();
        long completedCount = bills.stream().filter(b -> b.getStatusOrder() == StatusOrder.Completed).count();

        req.setAttribute("storingCount", storingCount);
        req.setAttribute("processingCount", processingCount);
        req.setAttribute("packingCount", packingCount);
        req.setAttribute("deliveringCount", deliveringCount);
        req.setAttribute("deliveredCount", deliveredCount);
        req.setAttribute("cancelledCount", cancelledCount);
        req.setAttribute("completedCount", completedCount);

        req.getServletContext().getRequestDispatcher("/Management-System/ms-order.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "edit": {
                    OrderDetailDB.getInstance().update(new OrderDetail(Integer.parseInt(req.getParameter("id")), Integer.parseInt(req.getParameter("quantity")), null));
                    break;
                }
                case "delete": {
                    OrderDetailDB.getInstance().delete(Integer.parseInt(req.getParameter("id")));
                    break;
                }
                case "approve": {
                    Bill bill = BillDB.getInstance().selectByID(Integer.parseInt(req.getParameter("id")));
                    if (bill != null) {
                        bill.setStatusOrder(StatusOrder.Processing);
                        BillDB.getInstance().update(bill);
                    }
                    break;
                }
                case "updateStatus": {
                    Bill bill = BillDB.getInstance().selectByID(Integer.parseInt(req.getParameter("id")));
                    if (bill != null) {
                        StatusOrder statusOrder = StatusOrder.valueOf(req.getParameter("status"));
                        bill.setStatusOrder(statusOrder);
                        BillDB.getInstance().update(bill);
                    }
                    break;
                }
            }
            HttpSession session = req.getSession();
            List<Bill> bills = BillDB.getInstance().selectAll();
            session.removeAttribute("bills");
            session.setAttribute("bills", bills);
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/msorder");
    }
}
