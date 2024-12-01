package ms_controller;

import dbmodel.DashboardDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.StatusOrder;
import java.io.IOException;

@WebServlet(name = "MSDashboardController", urlPatterns ="/ms/msdashboard")
public class MSDashboardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String timeRange = request.getParameter("timeRange");
        if (timeRange == null) {
            timeRange = "1month"; // Default time range
        }

        setDashboardAttributes(request, timeRange);

        request.getRequestDispatcher("/Management-System/ms-dashboard.jsp").forward(request, response);
    }

    private void setDashboardAttributes(HttpServletRequest request, String timeRange) {
        request.setAttribute("selectedTimeRange", timeRange);

        request.setAttribute("totalRevenue", DashboardDB.getTotalRevenue(timeRange));
        request.setAttribute("totalProfit", DashboardDB.getTotalProfit(timeRange));
        request.setAttribute("numberOfCustomers", DashboardDB.getNumberOfCustomers());
        request.setAttribute("numberOfBooks", DashboardDB.getNumberOfBooks());

        request.setAttribute("numberOfOrders", DashboardDB.getNumberOfOrders());
        request.setAttribute("numberOfProcessingOrders", DashboardDB.getNumberOfOrdersByStatus(StatusOrder.Processing, timeRange));
        request.setAttribute("numberOfPackingOrders", DashboardDB.getNumberOfOrdersByStatus(StatusOrder.Packing, timeRange));
        request.setAttribute("numberOfDeliveringOrders", DashboardDB.getNumberOfOrdersByStatus(StatusOrder.Delivering, timeRange));
        request.setAttribute("numberOfDeliveredOrders", DashboardDB.getNumberOfOrdersByStatus(StatusOrder.Delivered, timeRange));
        request.setAttribute("numberOfCompletedOrders", DashboardDB.getNumberOfOrdersByStatus(StatusOrder.Completed, timeRange));
        request.setAttribute("numberOfCancelledOrders", DashboardDB.getNumberOfOrdersByStatus(StatusOrder.Cancelled, timeRange));

        request.setAttribute("topFiveBestSellingBooks", DashboardDB.getTopFiveBestSellingBooks(timeRange));
        request.setAttribute("topFiveFavoriteAuthors", DashboardDB.getTopFiveFavoriteAuthors(timeRange));
        request.setAttribute("revenueByCategory", DashboardDB.getRevenueByCategory(timeRange));
        request.setAttribute("revenueByTimeRange", DashboardDB.getRevenueByTimeRange(timeRange));
        request.setAttribute("revenueByCampaign", DashboardDB.getRevenueByCampaign(timeRange));
    }
}
